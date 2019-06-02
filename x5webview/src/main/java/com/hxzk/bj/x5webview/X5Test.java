package com.hxzk.bj.x5webview;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zjt.commonlib.intface.X5CompService;

import static com.zjt.commonlib.common.AouterConfig.COMPONENT_X5CLASS;

/**
 * 作者：Created by ${XZT} on 2019/6/2
 * 文件描述：
 */
@Route(path =COMPONENT_X5CLASS)
public class X5Test implements X5CompService {

    Context mContext ;
    @Override
    public void x5CompserviceMethod(String url) {
        Toast.makeText(mContext,url,Toast.LENGTH_LONG).show();
    }

    @Override
    public void init(Context context) {
        mContext=context;
    }
}
