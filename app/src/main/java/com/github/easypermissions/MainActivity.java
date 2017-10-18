package com.github.easypermissions;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.permission.EasyPermissions;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View camera = findViewById(R.id.btn_camera);
        View fragment = findViewById(R.id.btn_fragment);
        View viewpagerFragment = findViewById(R.id.btn_viewpager_fragment);
        camera.setOnClickListener(this);
        fragment.setOnClickListener(this);
        viewpagerFragment.setOnClickListener(this);
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

            case R.id.btn_viewpager_fragment:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsFullGranted(int requestCode) {
        super.onPermissionsFullGranted(requestCode);
        Toast.makeText(getApplicationContext(), "permission request success !!! ", Toast.LENGTH_SHORT).show();
    }
}
