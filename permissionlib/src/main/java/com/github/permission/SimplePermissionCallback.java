package com.github.permission;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: zengven
 * date: 2017/9/14 17:23
 * desc: 请求失败弹出dialog 跳转至设置界面
 */
public abstract class SimplePermissionCallback implements EasyPermissions.PermissionCallback {

    private WeakReference<Activity> mActivityWeakReference;
    private static Map<String, Integer> sPermissionDesc = new HashMap<>();

    static {
        // TODO: 2017/8/21 此处添加权限拒接描述
        sPermissionDesc.put(Manifest.permission.CAMERA, R.string.camera_denied_message);
        sPermissionDesc.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.write_external_storage_denied_message);
        sPermissionDesc.put(Manifest.permission.ACCESS_FINE_LOCATION, R.string.fine_location_denied_message);
    }

    public SimplePermissionCallback(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        createPermissionDialog(sPermissionDesc.get(perms.get(0))).show();
    }

    private Dialog createPermissionDialog(int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivityWeakReference.get(), R.style.PermissionDialog);
        builder.setTitle(R.string.permission_title);
        builder.setMessage(mActivityWeakReference.get().getString(messageId, AppUtil.getAppName(mActivityWeakReference.get())));
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.goto_setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", mActivityWeakReference.get().getPackageName(), null);
//                intent.setData(uri);
                mActivityWeakReference.get().startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}
