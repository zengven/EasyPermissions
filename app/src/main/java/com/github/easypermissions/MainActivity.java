package com.github.easypermissions;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.permission.EasyPermissions;
import com.github.permission.SimplePermissionCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View camera = findViewById(R.id.btn_camera);
        View fragment = findViewById(R.id.btn_fragment);
        camera.setOnClickListener(this);
        fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        switch (vid) {
            case R.id.btn_camera:
                if (EasyPermissions.checkAndRequestPermissions(this, 1, Manifest.permission.CAMERA)) {
                    //do anything ...
                    Toast.makeText(getApplicationContext(), "do anything ...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(this, FragmentPermissionActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new SimplePermissionCallback(this) {
            @Override
            public void onPermissionsFullGranted(int requestCode) {
                //do anything ...
                Toast.makeText(getApplicationContext(), "Permissions Full Granted do anything ...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
