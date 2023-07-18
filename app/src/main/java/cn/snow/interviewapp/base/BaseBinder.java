package cn.snow.interviewapp.base;

import android.os.Binder;

import cn.snow.interviewapp.utils.LogUtil;

public class BaseBinder extends Binder {

    private String TAG = "BaseBinder";

    public BaseBinder() {
        super();
        TAG = getClass().getSimpleName();
        LogUtil.d("BaseBinder: " + TAG);
    }

}
