package com.hxzk.bj.x5webview;

import android.app.Application;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.zjt.commonlib.common.BaseApplication;

import java.util.HashMap;

/**
 * 作者：created by ${zjt} on 2019/3/5
 * 描述:
 */
public class X5Application extends BaseApplication {

    static X5Application x5Application;
    @Override
    public void onCreate() {
        super.onCreate();
        x5Application =new X5Application();
        //X5Webview的首次初始化比较耗时，部分手机会出现黑屏,在调用TBS初始化、创建WebView之前进行如下配置，以开启多进程优化方案
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        //配置腾讯浏览服务
        initQbSdk();
    }


    //配置腾讯浏览服务
    private void initQbSdk() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    public static Application getX5Application(){
        return x5Application;
    }
}
