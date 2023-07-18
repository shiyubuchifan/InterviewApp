package cn.snow.interviewapp.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import cn.snow.interviewapp.base.BaseBinder;
import cn.snow.interviewapp.base.BaseService;
import cn.snow.interviewapp.utils.LogUtil;

/**
 * 通过bindService 方式启动的service
 * 生命周期和其绑定的组件有关系
 */

public class ShortLiftService extends BaseService {

    private MyBind myBind;

    public ShortLiftService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        super.onBind(intent);
        LogUtil.d("Service:" + this);
        myBind = new MyBind();
        return myBind;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public class MyBind extends BaseBinder {
        String curTime;

        public MyBind() {
            super();
        }

        public String getCurTime() {
            curTime = "" + System.currentTimeMillis();
            return curTime;
        }
    }
}
