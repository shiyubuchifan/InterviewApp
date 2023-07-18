package cn.snow.interviewapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.snow.interviewapp.aidl.IAidlSnowImpl;
import cn.snow.interviewapp.aidl.IPCTestBean;
import cn.snow.interviewapp.utils.LogUtil;

/**
 * 跨进程通信
 * <p>
 * 编写aidl文件，AS自动生成的java类实现IPC通信的代理
 * 继承自己的aidl类，实现里面的方法
 * 在onBind()中返回我们的实现类，暴露给外界
 * 需要跟Service通信的对象通过bindService与Service绑定，并在ServiceConnection接收数据。
 * <p>
 * 作者：Lebens
 * 链接：https://juejin.cn/post/6844903781931417614
 */
public class IPCService extends Service {

    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_VALUE = 3;

    //这个是给client端接收参数用的
    static final int MSG_CLIENT_SET_VALUE = 4;


    IAidlSnowImpl mProcessInfo = new IAidlSnowImpl();

    Messenger messenger = new Messenger(new ServiceHandler());

    static class ServiceHandler extends Handler {

        private final List<Messenger> mMessengerList = new ArrayList<>();

        @Override
        public void handleMessage(Message msg) {
            LogUtil.d("Service : " + msg.what + "");
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mMessengerList.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mMessengerList.remove(msg.replyTo);
                    break;
                case MSG_SET_VALUE://设置数据
                    Bundle b = msg.getData();

                    String ipcTestBean = b.getString("data");
                    LogUtil.d(ipcTestBean == null ? "ipcTestBean = null" : ipcTestBean.toString());

                    for (Messenger messenger : mMessengerList) {//发送给所有在消息列表里的的客户端
                        try {
                            Message sendMsg = Message.obtain(null, MSG_CLIENT_SET_VALUE);
                            Bundle bundle = sendMsg.getData();
                            bundle.putString("data", ipcTestBean);
                            sendMsg.setData(bundle);
                            messenger.send(sendMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("IPCService onBind. Thread id = " + Thread.currentThread().getId() + " name = " + Thread.currentThread().getName());
//        return mProcessInfo;

        if (intent.getIntExtra("type", 1) == 1) {
            return messenger.getBinder();
        } else
            return mProcessInfo;
    }
}
