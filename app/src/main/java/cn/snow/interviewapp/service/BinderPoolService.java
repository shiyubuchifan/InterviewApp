package cn.snow.interviewapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import cn.snow.interviewapp.binder.BinderPoolUtil;


public class BinderPoolService extends Service {

    BinderPoolUtil.BinderPoolImp binderPoolImp = new BinderPoolUtil.BinderPoolImp();

    public BinderPoolService() {
    }

//    BinderPoolImp binderPoolImp = BinderPoolImp.getInstance();

    @Override
    public IBinder onBind(Intent intent) {
        try {
            return binderPoolImp.getBinderFromPool(intent.getIntExtra("type", -1));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}