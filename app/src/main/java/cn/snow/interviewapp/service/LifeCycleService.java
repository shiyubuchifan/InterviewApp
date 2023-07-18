package cn.snow.interviewapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import cn.snow.interviewapp.utils.LogUtil;
import cn.snow.interviewapp.viewmodel.LDTest;

public class LifeCycleService extends Service implements LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate() {
        super.onCreate();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

        LDTest.getInstance().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogUtil.d("LifeCycleService " + LDTest.getInstance().getValue());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        return new ServiceBinder();
    }

   public class ServiceBinder extends Binder {

        public ServiceBinder() {
            super();
        }

        public Service getService() {
            return LifeCycleService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
