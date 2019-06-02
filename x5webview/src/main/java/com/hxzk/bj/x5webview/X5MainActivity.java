package com.hxzk.bj.x5webview;


import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zjt.commonlib.bean.Event;
import com.zjt.commonlib.common.BaseActivity;
import com.zjt.commonlib.util.EventBusUtils;
import com.zjt.commonlib.util.LogUtil;

import static com.zjt.commonlib.common.AouterConfig.COMPONENT_X5;
import static com.zjt.commonlib.common.Conts.ACTION_X5;


@Route(path = COMPONENT_X5)
public class X5MainActivity extends BaseActivity  {

    @Autowired(name = "url")
    String x5url;

    Context mContext;
    private static final String TAG = "MainActivity";

    X5WebView mX5WebView;

    //内容显示区域
    private FrameLayout center_layout;

    @Override
    public int setLayoutId() {
        return R.layout.activity_x5main;
    }

    @Override
    public void initView() {
        center_layout = findViewById(R.id.center_layout);

        String webUrl = x5url;
        if (!TextUtils.isEmpty(webUrl)) {
            loadUrl(webUrl);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mX5WebView != null) {
            mX5WebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mX5WebView.removeAllViews();
            mX5WebView.clearHistory();
            ViewParent parent = mX5WebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mX5WebView);
            }
            mX5WebView.destroy();
        }
    }


    private void loadUrl(String url) {
        if (mX5WebView == null) {
            mX5WebView = new X5WebView(this, null);
        }
        //webview的getX5WebViewExtension()返回非null表示已加载了x5内核webview
        //TBS主要通过共享使用微信手Q的内核而加载x5内核
        if (mX5WebView.getX5WebViewExtension() == null) {
            LogUtil.e(TAG, "没有加载了x5内核webview");
        }

        //加载html中input有时失效
        mX5WebView.setFocusable(true);
        mX5WebView.setFocusableInTouchMode(true);
        //设置直接退出
        mX5WebView.setCanReturn(false, X5MainActivity.this);
        center_layout.addView(mX5WebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mX5WebView.loadWebUrl(url);
        Event mEvent = new Event();
        mEvent.setAction(ACTION_X5);
        mEvent.setData("成功接受X5发送回调数据");
        EventBusUtils.sendEvent(mEvent);

    }


    @Override
    protected boolean regEvent() {
        return true;
    }


}
