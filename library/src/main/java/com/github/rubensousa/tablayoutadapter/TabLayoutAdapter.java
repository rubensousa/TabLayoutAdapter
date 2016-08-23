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

    private static final int ALPHA_UNSELECTED = 179;
    public static final int ALPHA_SELECTED = 255;
    public static final String SAVE_STATE = "TabLayoutAdapterState";

    private List<Drawable> mDrawables;
    private List<Integer> mIcons;
    private List<CharSequence> mTitles;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int mCurrentTab;
    private int mScrollState;
    private int mPreviousScrollState;

    public TabLayoutAdapter(FragmentManager fm, TabLayout tabLayout, ViewPager viewPager) {
        super(fm);
        mIcons = new ArrayList<>();
        mDrawables = new ArrayList<>();
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
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
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
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

    public void createTabs() {
        mViewPager.setAdapter(this);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mFragments.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null && mIcons.size() > i) {
                tab.setIcon(mIcons.get(i));
                if (tab.getIcon() != null) {
                    tab.getIcon().setAlpha(ALPHA_UNSELECTED);
                }
            }

            if (tab != null && mDrawables.size() > i) {
                tab.setIcon(mDrawables.get(i));
                mDrawables.get(i).setAlpha(ALPHA_UNSELECTED);
            }
        }
    }

    public void addItem(Fragment fragment, View customView) {
        mFragments.add(fragment);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setCustomView(customView);
        mTabLayout.addTab(tab);
    }

    public void addItem(Fragment fragment, @StringRes int text) {
        addItem(fragment, text, 0);
    }

    public void addItem(Fragment fragment, CharSequence text) {
        addItem(fragment, text, null);
    }

    public void addItem(Fragment fragment, @StringRes int text, @DrawableRes int icon) {
        addItem(fragment, mViewPager.getContext().getString(text), icon);
    }

    public void addItem(Fragment fragment, CharSequence text, @DrawableRes int icon) {
        mFragments.add(fragment);

        if (text != null) {
            mTitles.add(text);
        }

        if (icon != 0) {
            mIcons.add(icon);
        }
    }

    public void addItem(Fragment fragment, CharSequence text, Drawable icon) {
        mFragments.add(fragment);

        if (text != null) {
            mTitles.add(text);
        }

        if (icon != null) {
            mDrawables.add(icon);
        }
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
        mCurrentTab = tab.getPosition();

        View customView = tab.getCustomView();
        if (customView != null) {
            customView.setAlpha(1f);
        }

        selectTabIcon(tab, true);
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

        selectTabIcon(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Copied from TabLayout. We need to change the icon alpha too.
        if (mScrollState != ViewPager.SCROLL_STATE_SETTLING ||
                mPreviousScrollState == ViewPager.SCROLL_STATE_DRAGGING) {
            if (positionOffset < 0.5) {
                selectTabIcon(mTabLayout.getTabAt(position), true);
                selectTabIcon(mTabLayout.getTabAt(position + 1), false);
            } else {
                selectTabIcon(mTabLayout.getTabAt(position), false);
                selectTabIcon(mTabLayout.getTabAt(position + 1), true);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (tab != null) {
            selectTabIcon(tab, true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mPreviousScrollState = mScrollState;
        mScrollState = state;
    }

    private void selectTabIcon(TabLayout.Tab tab, boolean selected) {
        if (tab != null && tab.getIcon() != null) {
            tab.getIcon().setAlpha(selected ? ALPHA_SELECTED : ALPHA_UNSELECTED);
        }
    }
}
