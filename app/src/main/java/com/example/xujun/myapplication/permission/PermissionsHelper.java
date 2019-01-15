package com.example.xujun.myapplication.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by jun xu on 19-1-8.
 */
public class PermissionsHelper {

    private static final String TAG_PERMISSION = "TAG_PERMISSION";
    private static final String TAG_EACH_PERMISSION = "TAG_EACH_PERMISSION";
    private final FragmentActivity mActivity;


    public PermissionsHelper(FragmentActivity activity) {
        mActivity = activity;
    }

    public static PermissionsHelper init(FragmentActivity fragmentActivity) {
        return new PermissionsHelper(fragmentActivity);
    }


    public void requestEachPermissions(String[] permissions, IPermissionListenerWrap.IEachPermissionListener eachPermissionListener) {
        getEachPermissionFragment(mActivity).requestEachPermissions(permissions, eachPermissionListener);
    }

    public void requestPermissions(String[] permissions, IPermissionListenerWrap.IPermissionListener requestpermissionlistener) {
        getEachPermissionFragment(mActivity).requestPermissions(permissions, requestpermissionlistener);
    }

    public boolean checkPermission(Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private EachPermissionFragment getEachPermissionFragment(FragmentActivity activity) {
        EachPermissionFragment fragment = (EachPermissionFragment) mActivity.getSupportFragmentManager().findFragmentByTag(TAG_EACH_PERMISSION);
        if (fragment == null) {
            fragment = EachPermissionFragment.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG_PERMISSION)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }


    private PermissionFragment getPermissionFragment(FragmentActivity activity) {
        PermissionFragment permissionFragment = findPermissionFragment(activity);
        if (permissionFragment == null) {
            permissionFragment = PermissionFragment.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionFragment, TAG_PERMISSION)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionFragment;
    }

    private PermissionFragment findPermissionFragment(FragmentActivity activity) {
        return (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG_PERMISSION);
    }

    /**
     * 用户勾选不再显示并点击拒绝，弹出打开设置页面申请权限，也可以自定义实现
     *
     * @param context Context
     * @param title   弹窗标题
     * @param message 申请权限解释说明
     * @param confirm 确认按钮的文字，默认OK
     * @param cancel  取消按钮呢的文字，默认不显示取消按钮
     */

    public static void requestDialogAgain(final Activity context, @NonNull String title, @NonNull String message, String confirm, String cancel) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message);
            builder.setPositiveButton(confirm == null ? "OK" : confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startSettingActivity(context);
                    dialog.dismiss();
                }
            });
            if (null != cancel) {
                builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开设置页面打开权限
     *
     * @param context
     */
    public static void startSettingActivity(@NonNull Activity context) {

        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +
                            context.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivityForResult(intent, 10); //这里的requestCode和onActivityResult中requestCode要一致
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
