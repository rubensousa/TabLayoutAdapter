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
