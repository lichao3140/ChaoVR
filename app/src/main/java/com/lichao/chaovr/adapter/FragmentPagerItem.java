package com.lichao.chaovr.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by ChaoLi on 2018/5/10 0010 - 10:27
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class FragmentPagerItem {
    private String title;
    private Fragment fragment;
    private Class<? extends Fragment> clazz;
    private Bundle args;

    private FragmentPagerItem(String title, @NonNull Fragment fragment) {
        this(title, fragment.getClass(), fragment.getArguments());
        this.fragment = fragment;
    }

    private FragmentPagerItem(String title, Class<? extends Fragment> clazz, Bundle args) {
        this.title = title;
        this.clazz = clazz;
        this.args = args;
    }

    public static FragmentPagerItem create(String title, @NonNull Fragment fragment) {
        return new FragmentPagerItem(title, fragment);
    }

    public static FragmentPagerItem create(String title, Class<? extends Fragment> clazz) {
        return create(title, clazz, null);
    }

    private static FragmentPagerItem create(String title, Class<? extends Fragment> clazz, Bundle args) {
        return new FragmentPagerItem(title, clazz, args);
    }

    public String getPageTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Fragment newInstance(Context context) {
        return Fragment.instantiate(context, clazz.getName(), args);
    }

    public Bundle getArgs() {
        return args;
    }
}
