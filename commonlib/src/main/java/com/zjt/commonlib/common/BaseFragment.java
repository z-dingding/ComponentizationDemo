package com.zjt.commonlib.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjt.commonlib.bean.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Created by Ding on 2019/5/26
 * 文件描述：
 */
public abstract class BaseFragment extends Fragment {


    Context mContext;
    private View rootView;
    private Unbinder unBinder;


    /**
     * 创建fragment的静态方法，方便传递参数
     *
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment> T getInstance(Class clazz, Bundle args) {
        T mFragment = null;
        try {
            mFragment = (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (args != null) {
            mFragment.setArguments(args);
        }
        return mFragment;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(setLayoutId() != 0){
            rootView = inflater.inflate(setLayoutId(), container, false);
        }
        unBinder = ButterKnife.bind(this, rootView);
        if (regEvent()) {
            EventBus.getDefault().register(this);
        }

        ARouter.getInstance().inject(this);

        initView();
        initData();
        initEvent();

        return rootView;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (regEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

    /**
     * 需要接收事件 重新该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }


    protected abstract int setLayoutId();

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initEvent();
}
