package com.example.xujun.myapplication.permission;

/**
 * Created by jun xu on 19-1-9.
 */
public interface IPermissionListenerWrap {

    public interface IEachPermissionListener {

        void onAccepted(Permission permission);

        void onException(Throwable throwable);
    }

    public interface IPermissionListener {

        void onAccepted(boolean isGranted);

        void onException(Throwable throwable);
    }
}
