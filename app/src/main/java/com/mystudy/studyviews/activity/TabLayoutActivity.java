package com.mystudy.studyviews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mystudy.studyviews.R;
import com.mystudy.studyviews.fragment.TabLayoutItemFragment;
import com.mystudy.studyviews.util.DisplayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends BaseActivity {

    @BindView(R.id.tbl_tablayout)
    TabLayout tblTablayout;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragments.add(TabLayoutItemFragment.newInstance("111111", "111111"));
        fragments.add(TabLayoutItemFragment.newInstance("222222", "222222"));
        fragments.add(TabLayoutItemFragment.newInstance("333333", "333333"));
        fragments.add(TabLayoutItemFragment.newInstance("444444", "444444"));
        titles.add("fragment1");
        titles.add("fragment2");
        titles.add("fragment3");
        titles.add("fragment4");
        for (int i = 0; i < titles.size(); i++) {
            tblTablayout.addTab(tblTablayout.newTab().setText(titles.get(i)));
        }
        vpViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tblTablayout.setupWithViewPager(vpViewpager);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void setIndicatorWidth(TabLayout tabLayout) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    LinearLayout slidingTabIndicator = (LinearLayout) tabLayout.getChildAt(0);
                    //将dp转换成px
                    int dp10 = DisplayUtil.dip2px(tabLayout.getContext(), 10);
                    for (int i = 0; i < slidingTabIndicator.getChildCount(); i++) {
                        View tabView = slidingTabIndicator.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();

                        }
                        //设置tab左右间距 ，因为源码中线的宽度是根据tabView的宽度来设置的，所以得注意这里不能使用Padding                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();                    //指示器宽度值设置                    params.width = width;                    //设置一下tabview的margin，不设置会连在一起                    params.leftMargin = dp10;                    params.rightMargin = dp10;                    tabView.setLayoutParams(params);                    tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
