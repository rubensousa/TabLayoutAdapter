/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.tablayoutadapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TabLayoutAdapter extends FragmentStatePagerAdapter
        implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    public static final String SAVE_STATE = "state";

    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int mCurrentTab;

    public TabLayoutAdapter(FragmentManager fm) {
        this(fm, null, null);
    }

    public TabLayoutAdapter(FragmentManager fm, TabLayout tabLayout, ViewPager viewPager) {
        super(fm);
        mFragments = new ArrayList<>();
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        if (mTabLayout != null) {
            mTabLayout.addOnTabSelectedListener(this);
        }
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        // Set this object as the real instance
        if (object instanceof Fragment) {
            mFragments.set(position, (Fragment) object);
        }
        return object;
    }

    public void attachTabLayout(TabLayout tabLayout) {
        mTabLayout = tabLayout;
        if (mTabLayout != null) {
            mTabLayout.removeOnTabSelectedListener(this);
            mTabLayout.addOnTabSelectedListener(this);
        }
    }

    public void attachViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.addOnPageChangeListener(this);
        }
    }

    public void addItem(Fragment fragment, View customView) {
        mFragments.add(fragment);
    }

    public void addItem(Fragment fragment, @StringRes int text) {
        addItem(fragment, text, 0);
    }

    public void addItem(Fragment fragment, CharSequence text) {
        addItem(fragment, text, null);
    }

    public void addItem(Fragment fragment, @StringRes int text, @DrawableRes int icon) {
        mFragments.add(fragment);
        TabLayout.Tab tab = mTabLayout.newTab();

        if (text != 0) {
            tab.setText(text);
        }

        if (icon != 0) {
            tab.setIcon(icon);
        }

        mTabLayout.addTab(tab);
    }

    public void addItem(Fragment fragment, CharSequence text, @DrawableRes int icon) {
        mFragments.add(fragment);
        TabLayout.Tab tab = mTabLayout.newTab();

        if (text != null) {
            tab.setText(text);
        }

        if (icon != 0) {
            tab.setIcon(icon);
        }

        mTabLayout.addTab(tab);
    }

    public void addItem(Fragment fragment, CharSequence text, Drawable icon) {
        mFragments.add(fragment);
        TabLayout.Tab tab = mTabLayout.newTab();

        if (text != null) {
            tab.setText(text);
        }

        if (icon != null) {
            tab.setIcon(icon);
        }

        mTabLayout.addTab(tab);
    }

    public void saveInstanceState(Bundle outState) {
        outState.putInt(SAVE_STATE, mCurrentTab);
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentTab = savedInstanceState.getInt(SAVE_STATE);
            if (mCurrentTab != 0 && mTabLayout != null) {
                TabLayout.Tab tab = mTabLayout.getTabAt(mCurrentTab);
                if (tab != null) {
                    tab.select();
                }
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mCurrentTab != tab.getPosition()) {
            mViewPager.setCurrentItem(tab.getPosition());
        }
        mCurrentTab = tab.getPosition();
        View customView = tab.getCustomView();
        if (customView != null) {
            customView.setAlpha(1f);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab == null) {
            return;
        }

        View customView = tab.getCustomView();
        if (customView != null) {
            customView.setAlpha(0.7f);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentTab = position;
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
