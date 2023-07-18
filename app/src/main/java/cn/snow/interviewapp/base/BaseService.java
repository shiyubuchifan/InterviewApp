package cn.snow.interviewapp.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import cn.snow.interviewapp.utils.LogUtil;

public class BaseService extends Service {

    private String TAG = "BaseService";

    public BaseService() {
        super();
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate: " + TAG + "---" + android.os.Process.myPid());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand: " + TAG);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy: " + TAG);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind: " + TAG);
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtil.d("onRebind: " + TAG);
    }
}
