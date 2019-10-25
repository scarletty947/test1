package com.example.test;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
//管理viewPager
public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new HomeFragment();
        }else if(position==1){
            return new FuncFragment();
        }else{
            return new SettingFragment();
        }
    }
    public CharSequence getPageTitle(int position) {
        return "Title" + position;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
