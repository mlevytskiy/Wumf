package io.wumf.wumf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.PrepareDataActivity;
import io.wumf.wumf.fragment.FriendsFragment;
import io.wumf.wumf.fragment.PhoneInfoFragment;
import io.wumf.wumf.fragment.TabFragment;

/**
 * Created by maks on 8/12/16.
 * email: m.levytskiy@gmail.com
 */
public class MyWallActivity extends PrepareDataActivity {

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
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout.setBackgroundColor(getResources().getColor(R.color.primary_700));
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TabFragment(), "my wall");
        adapter.addFrag(new PhoneInfoFragment(), "my apps");
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
