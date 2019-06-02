package com.hxzk.bj.x5webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage;
import android.widget.FrameLayout;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Used 主要处理解析，渲染网页等浏览器做的事情。
 * 帮助WebView处理各种通知、请求事件：比如页面加载的开始、结束、失败时的对话框提示
 */
public class X5WebViewClient extends WebViewClient {

    private static final String TAG = "X5WebViewClient";

    private static boolean  isJoinSeller = false;

    /**
     * 依赖的窗口
     */
    private  static Context context;
    private static X5WebView x5WebView;



    private boolean needClearHistory = false;//是否需要清除历史记录

    public X5WebViewClient(Context context, X5WebView x5WebView) {
        this.context = context;
        this.x5WebView = x5WebView;
    }

    /**
     * 重写此方法表明点击网页内的链接由自己处理，而不是新开Android的系统browser中响应该链接。
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            context.startActivity(intent);
            return true;
        } else {
            webView.loadUrl(url);
            return false;//表示当前的webview可以处理打开新网页的请求，不用借助系统浏览器,【false 显示frameset, true 不显示Frameset】
        }
    }

    /**
     * 网页加载开始时调用，显示加载提示旋转进度条
     */
    @Override
    public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        super.onPageStarted(webView, url, bitmap);


        ViewGroup contentParent = (ViewGroup) webView.getParent();
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        //添加一个加载的骨架
//        View placeholderView = layoutInflater.inflate(R.layout.layout_placeholder, null);
//        contentParent.addView(placeholderView, 1, new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 网页加载完成时调用，比如：隐藏加载提示旋转进度条
     */
    @Override
    public void onPageFinished(WebView webView,  String url) {
        super.onPageFinished(webView, url);

        ViewGroup contentParent = (ViewGroup) webView.getParent();
        if (contentParent.getChildCount() > 1) {
            contentParent.removeViewAt(1);
        }

    }




    /**
     * 网页加载失败时调用，隐藏加载提示旋转进度条
     * 捕获的是 文件找不到，网络连不上，服务器找不到等问题
     */

    @Override
    public void onReceivedError(WebView webView, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }


    /**
     * 直接捕获到404
     */
    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        String url = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            url = webResourceRequest.getUrl().toString();
        } else {
            url = webResourceRequest.toString();
        }

    }







    /*=================================根据需要清除历史记录=================================*/
    @Override
    public void doUpdateVisitedHistory(WebView webView, String url, boolean isReload) {
        super.doUpdateVisitedHistory(webView, url, isReload);
        if (needClearHistory) {
            webView.clearHistory();//清除历史记录
            needClearHistory = false;
        }
    }

    public void setNeedClearHistory(boolean needClearHistory) {
        this.needClearHistory = needClearHistory;
    }

    /**
     * 扩充数据库的容量
     */
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(estimatedSize * 2);
    }
}
