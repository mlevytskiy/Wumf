package io.wumf.wumf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.R;
import io.wumf.wumf.fragment.TimelineFragment;
import io.wumf.wumf.mock.DummyFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_animation);

        appBarLayout = (AppBarLayout) findViewById(R.id.tabanim_appbar);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        tabLayout.setTabTextColors(Color.BLACK, Color.BLACK);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
                        tabLayout.setTabTextColors(Color.BLACK, Color.BLACK);
                        break;
                    case 1:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.primary_700));
                        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
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

//        new AsyncTask<Void, Void, List<App>>() {
//
//            @Override
//            protected List<App> doInBackground(Void... params) {
//                return new AppUtils(MainActivity.this).loadAllAppsFromSystem();
//            }
//
//            protected void onPostExecute(List<App> apps) {
//                Toast.makeText(MainActivity.this, "text=" + apps, Toast.LENGTH_LONG).show();
//            }
//
//        }.execute();
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new TimelineFragment(), "timeline");
//        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.background_floating_material_light)), "timeline");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.primary_700)), "all");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.accent_material_light)), "new");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.accent_700)), "removed");
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

}
