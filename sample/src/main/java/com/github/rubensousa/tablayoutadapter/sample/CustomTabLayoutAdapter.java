package com.github.rubensousa.tablayoutadapter.sample;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.github.rubensousa.tablayoutadapter.TabLayoutAdapter;


public class CustomTabLayoutAdapter extends TabLayoutAdapter {


    public CustomTabLayoutAdapter(FragmentManager fm, TabLayout tabLayout, ViewPager viewPager) {
        super(fm, tabLayout, viewPager);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        super.instantiateItem(container, position);
        Fragment fragment = getItem(position);

        // Set custom listener to fragment here
        // fragment.setActionModeCallback();

        return fragment;
    }
}
