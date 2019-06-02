package com.hxzk.bj.x5webview;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.zjt.commonlib.util.LogUtil;


/**
 * Used 处理解析，渲染网页等浏览器做的事情。辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
 */
public class X5WebChromeClient extends WebChromeClient {



	private static final String TAG = "X5WebChromeClient";

	/**依赖的窗口*/
	private Context mContext;
	private X5WebView x5WebView;

	public X5WebChromeClient(Context context, X5WebView x5WebView) {
		this.mContext = context;
		this.x5WebView = x5WebView;
	}

	//更改加载进度值
	//throw、throws：这是用于抛出异常，区别在于throw是在语句中抛出、throws是在方法体抛出；
	@Override
	public void onProgressChanged(WebView view, int newProgress) throws NullPointerException {
		super.onProgressChanged(view, newProgress);

			if(newProgress == 100){
				ViewGroup contentParent = (ViewGroup) view.getParent();
				if(contentParent != null){
					if(contentParent.getChildCount() > 1){
						contentParent.removeViewAt(1);
					}
				}else{
					LogUtil.e(TAG,"{contentParent为空}");
			}
		}

	}



	/*=========================================实现webview打开文件管理器功能==============================================*/
	/**
	 * HTML界面：
	 * <input accept="image/*" capture="camera" id="imgFile" name="imgFile" type="file">
	 * <input type="file" capture="camera" accept="* /*" name="image">
	 *  */

	/**
	 * 重写WebChromeClient 的openFileChooser方法
	 * 这里有个漏洞，4.4.x的由于系统内核发生了改变，没法调用以上方法，现在仍然找不到解决办法，唯一的方法就是4.4直接使用手机浏览器打开，这个是可以的。
	 * */

	private static  android.webkit.ValueCallback<Uri> mUploadMessage;//5.0--版本用到的
	private  static android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;//5.0++版本用到的



	//5.0及以上系统回调onShowFileChooser
	@Override
	public boolean onShowFileChooser(WebView webView,
									 ValueCallback<Uri[]> filePathCallback,
									 FileChooserParams fileChooserParams) {

		openFileChooserImplForAndroid5(filePathCallback);
		return true;
	}

   //5.0以下系统回调openFileChooser方法(没有包括3.0及其以下,因为几乎不存在3.0的手机了，所以没有考虑)
	@Override
	public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
		openFileChooserImpl(valueCallback);
	}




	//5.0--的调用
	private void openFileChooserImpl(android.webkit.ValueCallback<Uri> uploadMsg) {
		mUploadMessage = uploadMsg;
		dispatchTakePictureIntent();
	}
	//5.0++的调用
	private void openFileChooserImplForAndroid5(android.webkit.ValueCallback<Uri[]> uploadMsg) {
		mUploadCallbackAboveL = uploadMsg;
		dispatchTakePictureIntent();
	}

	//拍照或者打开文件管理器
	private void dispatchTakePictureIntent() {
		if(mUploadMessage!=null){
			LogUtil.e(TAG,"mUploadMessage.toString()="+mUploadMessage.toString());
		}
		if(mUploadCallbackAboveL!=null) {
			LogUtil.e(TAG, "mUploadCallbackAboveL.toString()=" + mUploadCallbackAboveL.toString());
		}


	}

	public android.webkit.ValueCallback<Uri> getmUploadMessage() {
		return mUploadMessage;
	}

	public android.webkit.ValueCallback<Uri[]> getmUploadCallbackAboveL() {
		return mUploadCallbackAboveL;
	}

	public void setmUploadMessage(android.webkit.ValueCallback<Uri> mUploadMessage) {
		X5WebChromeClient.mUploadMessage = mUploadMessage;
	}

	public void setmUploadCallbackAboveL(android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL) {
		X5WebChromeClient.mUploadCallbackAboveL = mUploadCallbackAboveL;
	}





}
