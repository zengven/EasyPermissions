package com.github.easypermissions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.permission.EasyPermissions;
import com.github.permission.SimplePermissionCallback;

import java.util.List;

/**
 * author: zengven
 * date: 2017/10/18 10:34
 * desc: TODO
 */

public abstract class BaseFragment extends Fragment implements EasyPermissions.PermissionCallback {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new SimplePermissionCallback(getActivity()) {
            @Override
            public void onPermissionsFullGranted(int requestCode) {
                BaseFragment.this.onPermissionsFullGranted(requestCode);
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms) {
                super.onPermissionsDenied(requestCode, perms);
                BaseFragment.this.onPermissionsDenied(requestCode, perms);
            }

            @Override
            public void onPermissionsGranted(int requestCode, List<String> perms) {
                super.onPermissionsGranted(requestCode, perms);
                BaseFragment.this.onPermissionsGranted(requestCode, perms);
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
