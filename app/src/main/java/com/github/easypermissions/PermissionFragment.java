package com.github.easypermissions;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.permission.EasyPermissions;
import com.github.permission.SimplePermissionCallback;

/**
 * author: zengven
 * date: 2017/9/14
 * Desc:
 */
public class PermissionFragment extends Fragment implements View.OnClickListener {

    public static PermissionFragment newInstance() {
        PermissionFragment fragment = new PermissionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View gps = view.findViewById(R.id.btn_gps);
        gps.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.btn_gps:
                if (EasyPermissions.checkAndRequestPermissions(this, 1, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //do anything ...
                    Toast.makeText(getContext(), "do anything ...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new SimplePermissionCallback(getActivity()) {
            @Override
            public void onPermissionsFullGranted(int requestCode) {
                //do anything ...
                Toast.makeText(getContext(), "Permissions Full Granted do anything ...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
