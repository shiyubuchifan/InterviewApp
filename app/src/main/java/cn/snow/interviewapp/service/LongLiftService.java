package cn.snow.interviewapp.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import cn.snow.interviewapp.base.BaseService;
import cn.snow.interviewapp.utils.LogUtil;

/**
 * 通过{@link #startService(Intent)} 方式启动的service
 * 除非调用{@link #stopService(Intent)}或者内部调用{@link #stopSelf()} 来停止，否则会一直运行下去
 */

public class LongLiftService extends BaseService {

    private Timer timer;

    public LongLiftService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LogUtil.d("heartbeat", "alive");
            }
        }, 1000 * 0L, 1000 * 5L);


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
