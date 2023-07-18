package cn.snow.interviewapp.service;

import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import cn.snow.interviewapp.base.BaseService;
import cn.snow.interviewapp.viewmodel.LDTest;

public class TestService extends BaseService {

    public TestService() {
        super();
    }

    Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LDTest.getInstance().postValue("Service" + SystemClock.currentThreadTimeMillis());
            }
        }, 1000 * 1, 1000 * 3);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
