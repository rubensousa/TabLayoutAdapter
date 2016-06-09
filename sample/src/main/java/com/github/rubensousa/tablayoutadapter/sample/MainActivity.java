package com.github.rubensousa.tablayoutadapter.sample;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.rubensousa.tablayoutadapter.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity {

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
