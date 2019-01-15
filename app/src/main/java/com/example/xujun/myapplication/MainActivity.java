package com.example.xujun.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xujun.myapplication.activityresult.ActivityResultHelper;
import com.example.xujun.myapplication.activityresult.TestResultActivity;
import com.example.xujun.myapplication.permission.IPermissionListenerWrap;
import com.example.xujun.myapplication.permission.Permission;
import com.example.xujun.myapplication.permission.PermissionsHelper;

public class MainActivity extends AppCompatActivity {

    private Button mBtn1;
    private Button mBtn2;

    private static final String TAG = "MainActivity";
    private Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn1 = findViewById(R.id.btn_1);
        mBtn2 = findViewById(R.id.btn_2);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION});
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(new String[]{Manifest.permission.READ_CONTACTS});
            }
        });
        mBtn3 = findViewById(R.id.btn_3);
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestResultActivity.class);
                ActivityResultHelper.init(MainActivity.this).startActivityForResult(intent, new ActivityResultHelper.Callback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        String result = data.getStringExtra(TestResultActivity.KEY_RESULT);
                        show(" resultCode = " + resultCode + "  result = " + result);
                    }
                });
            }
        });
    }

    private void requestPermission(final String[] permissions) {
        PermissionsHelper.init(MainActivity.this).requestEachPermissions(permissions, new IPermissionListenerWrap.IEachPermissionListener() {
            @Override
            public void onAccepted(Permission permission) {
                show(permission);
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    private void show(Permission permission) {
        if (permission.granted) {
            show("授予权限 ：" + permission.name);
        } else {
            if (permission.shouldShowRequestPermissionRationale) {
                show("没有勾选不再提醒，拒绝权限 ：" + permission.name);
            } else {
                show("勾选不再提醒，拒绝权限 ：" + permission.name);
            }
        }
    }

    void show(CharSequence text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "show: text =" + text);

    }
}
