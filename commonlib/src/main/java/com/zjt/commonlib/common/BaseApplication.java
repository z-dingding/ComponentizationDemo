package com.zjt.commonlib.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zjt.commonlib.util.ActivityManage;

/**
 * 作者：Created by Ding on 2019/5/25
 * 文件描述：
 */
public class BaseApplication extends Application {
    //是否开启调试
    private  boolean isDebug =true;
    //全局唯一的context
    private static BaseApplication application;
    //Activity管理器
    private ActivityManage activityManage;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        activityManage = new ActivityManage();

        //初始化路由
        initRouter();
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        activityManage.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 初始化路由
     */
    private void initRouter() {
        //必须在初始化之前写入这两行
        if (isDebug) {
            //打印日志
            ARouter.openLog();
            //开始调试
            ARouter.openDebug();
        }
        //ARouter的实例化
        ARouter.init(this);
    }


    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static BaseApplication getApplication() {
        return application;
    }


    /**
     * 返回Activity管理器
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }
}
