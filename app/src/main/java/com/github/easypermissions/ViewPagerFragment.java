package com.github.easypermissions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: zengven
 * date: 2017/10/18 11:26
 * desc: TODO
 */

public class ViewPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        PermissionFragmentPagerAdapter permissionFragmentPagerAdapter = new PermissionFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(permissionFragmentPagerAdapter);
    }

    private class PermissionFragmentPagerAdapter extends FragmentPagerAdapter {

        public PermissionFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PermissionFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
