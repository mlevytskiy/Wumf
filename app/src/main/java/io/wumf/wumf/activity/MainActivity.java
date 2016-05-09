package io.wumf.wumf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.PrepareDataActivity;
import io.wumf.wumf.fragment.AppListFragment;
import io.wumf.wumf.fragment.TimelineFragment;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.OnAppItemClickEvent;

public class MainActivity extends PrepareDataActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;

    @Override
    protected void onCreateAfterDataPreparation(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab_animation);

        appBarLayout = (AppBarLayout) findViewById(R.id.tabanim_appbar);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout.setBackgroundColor(getResources().getColor(R.color.primary_700));
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.primary_700));
                        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
                        break;
                    case 1:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        tabLayout.setTabTextColors(Color.BLACK, Color.BLACK);
                        break;
                    case 2:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.accent_material_light));
                        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
                        break;
                    case 3:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.accent_700));
                        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AppListFragment(), "apps");
        adapter.addFrag(new TimelineFragment(), "timeline");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Subscribe
    public void onItemClickEvent(OnAppItemClickEvent event) {
        startActivity(new Intent(this, AppActivity.class).putExtra(AppActivity.APP_ID, event.appId));
    }

    @Override public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    private static class AppDrawerToggle extends ActionBarDrawerToggle {
        private final AppCompatActivity mActivity;

        public AppDrawerToggle(AppCompatActivity mainActivity, DrawerLayout drawerLayout,
                               int navigation_drawer_open, int navigation_drawer_close) {
            super(mainActivity, drawerLayout, navigation_drawer_open, navigation_drawer_close);
            mActivity = mainActivity;
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            mActivity.supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            mActivity.supportInvalidateOptionsMenu();
        }
    }
}
