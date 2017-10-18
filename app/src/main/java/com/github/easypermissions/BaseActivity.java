package com.github.easypermissions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.permission.EasyPermissions;
import com.github.permission.SimplePermissionCallback;

import java.util.List;

/**
 * author: zengven
 * date: 2017/10/1 11:35
 * desc: activity 基类,权限处理
 */

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallback {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new SimplePermissionCallback(this) {
            @Override
            public void onPermissionsFullGranted(int requestCode) {
                BaseActivity.this.onPermissionsFullGranted(requestCode);
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms) {
                super.onPermissionsDenied(requestCode, perms);
                BaseActivity.this.onPermissionsDenied(requestCode, perms);
            }

            @Override
            public void onPermissionsGranted(int requestCode, List<String> perms) {
                super.onPermissionsGranted(requestCode, perms);
                BaseActivity.this.onPermissionsGranted(requestCode, perms);
            }
        });
    }

    @Override
    public void onPermissionsFullGranted(int requestCode) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
