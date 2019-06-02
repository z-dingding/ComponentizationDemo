package com.zjt.module_zixun;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zjt.commonlib.common.BaseActivity;


public class MainActivity_ZiXun extends BaseActivity {

Fragment fragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main_zixun;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        changeFragment();
    }

    @Override
    public void initEvent() {

    }


    /**
     * Fragment 发生改变
     */
    public void changeFragment() {
        //hideFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = ZiXunFragment.getInstance(ZiXunFragment.class,null);
            transaction.add(R.id.zixunmodule_fl_containner,fragment);
        }
        transaction.commitAllowingStateLoss();
    }

}
