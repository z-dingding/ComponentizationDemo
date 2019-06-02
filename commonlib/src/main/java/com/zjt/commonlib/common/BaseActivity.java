package com.zjt.commonlib.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjt.commonlib.bean.Event;
import com.zjt.commonlib.util.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Created by Ding on 2019/5/26
 * 文件描述：
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(setLayoutId() != 0){
            setContentView(setLayoutId());
        }
        unbinder =ButterKnife.bind(this);


        //加入Activity管理器
        BaseApplication.getApplication().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBusUtils.register(this);
        }

        ARouter.getInstance().inject(this);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
        //将Activity从管理器移除
        BaseApplication.getApplication().getActivityManage().removeActivity(this);
    }



    public abstract  int setLayoutId();
    public abstract  void initView();
    public abstract  void initData();
    public abstract  void initEvent();



    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

}
