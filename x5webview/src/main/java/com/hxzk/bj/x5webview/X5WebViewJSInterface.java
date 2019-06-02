package com.hxzk.bj.x5webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telecom.Call;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import static com.tencent.smtt.sdk.TbsVideo.canUseTbsPlayer;
import static com.tencent.smtt.sdk.TbsVideo.openVideo;


/**
 * Created by HaiyuKing
 * Used Java和Java script交互的工具类
 */

public class X5WebViewJSInterface {


    private static final String TAG = "X5WebViewJSInterface";

    private static X5WebViewJSInterface instance;
    /**
     * 依赖的窗口
     */
    private Context mContext;
    private static X5WebView x5WebView;


    public static String mCurrentPhotoPath = null;//拍照存储的路径,例如：/storage/emulated/0/hxzkPictures/20170608104809.jpg
    public String FolderName = "hxzkPictures";//拍照存储的父目录

    public static X5WebViewJSInterface getInstance(Context mContext, X5WebView x5WebView) {
        if (instance == null) {
            instance = new X5WebViewJSInterface();
        }
        instance.mContext = mContext;
        X5WebViewJSInterface.x5WebView = x5WebView;
        return instance;
    }

    //得到webview的对象
    public static X5WebView getX5Webview() {
        return x5WebView;
    }





    /**
     * 获取SD卡路径
     *
     * @return /storage/emulated/0/
     */
    private String getSDPath() {
        String sdPath = null;
        // 判断sd卡是否存在
        boolean sdCardExit = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExit) {
            // 获取根目录
            sdPath = Environment.getExternalStorageDirectory().toString();
        } else {
            sdPath = Environment.getDataDirectory().toString();
        }
        return sdPath;
    }

}
