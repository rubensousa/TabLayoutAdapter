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

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.rubensousa.tablayoutadapter.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity{

    private TabLayoutAdapter mTabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), tabLayout, viewPager);
        mTabLayoutAdapter.addItem(new DummyFragment(), "Recents", R.drawable.ic_call_24dp);
        mTabLayoutAdapter.addItem(new DummyFragment(), "Favorites", R.drawable.ic_favorite_24dp);
        mTabLayoutAdapter.addItem(new DummyFragment(), "Nearby", R.drawable.ic_nearby_24dp);
        //noinspection ConstantConditions
        viewPager.setAdapter(mTabLayoutAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //noinspection ConstantConditions
            findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTabLayoutAdapter.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTabLayoutAdapter.restoreInstanceState(savedInstanceState);
    }
}
