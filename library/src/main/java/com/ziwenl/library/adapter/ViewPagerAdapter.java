package com.ziwenl.library.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * PackageName : com.ziwenl.library.adapter
 * Author : Ziwen Lan
 * Date : 2019/2/28
 * Time : 9:18
 * Introduction : ViewPager适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_fragments;
    private List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list_fragments) {
        super(fm);
        if (list_fragments == null) {
            this.list_fragments = new ArrayList<>();
        } else {
            this.list_fragments = list_fragments;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @NonNull
    @Override
    public Fragment getItem(int arg0) {
        return list_fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return list_fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0) {
            return titles.get(position);
        }
        return "";
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    public void setTitleData(List<String> title) {
        this.titles = title;
    }

    public void updateTitle(String title, int position) {
        titles.set(position, title);
    }


}
