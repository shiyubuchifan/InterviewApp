package cn.snow.interviewapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

import cn.snow.interviewapp.R;
import cn.snow.interviewapp.aidl.IPCTestBean;
import cn.snow.interviewapp.aidl.IaidlSnowInterface;
import cn.snow.interviewapp.base.BaseActivity;
import cn.snow.interviewapp.databinding.ServiceAcitivtyBindingImpl;
import cn.snow.interviewapp.service.IPCService;
import cn.snow.interviewapp.service.LongLiftService;
import cn.snow.interviewapp.service.ShortLiftService;
import cn.snow.interviewapp.utils.LogUtil;

public class ServiceAcitivty extends BaseActivity<ServiceAcitivtyBindingImpl> {


    @Override
    protected int getLayoutID() {
        return R.layout.service_acitivty;
    }

    @Override
    protected void initView() {
        binding.tvBind.setText("Do Something:");
        binding.setHandle(new OnClickHandle());

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(12188);
                    Socket socket = serverSocket.accept();

                    InputStream inputStream = socket.getInputStream();
                    byte[] buf = new byte[1024*1024];
                    int len;
                    while ((len=inputStream.read(buf))!=-1){
                        System.out.println(new String(buf,0,len));
                    }
                    System.out.println( socket.getInetAddress().getHostName() +"-" + socket.getInetAddress().getHostAddress()+"-"+ Arrays.toString(socket.getInetAddress().getAddress()));
                    inputStream.close();
//                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    private void doSomething(Object o) {
        String str = "Do Something:" + o == null ? "" : (String) o;
        binding.tvBind.setText(str);

    }

    ShortLiftService.MyBind myBind;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务绑定成功
            LogUtil.d("onServiceConnected: " + name.toShortString());
            myBind = (ShortLiftService.MyBind) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 当服务异常终止时会调用。
            // 注意！unbindService时不会调用
            LogUtil.d("onServiceDisconnected: " + name.toShortString());
        }

        @Override
        public void onNullBinding(ComponentName name) {
            //Service的onBind()返回null时将会调用这个方法，并不会调用onServiceConnected()
            LogUtil.d("onNullBinding: " + name.toShortString());
        }
    };

    IaidlSnowInterface iaidlSnow;
    //跨进程服务连接  同一个APP 不同进程之间的通信
    ServiceConnection ipcServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //通过aidl取出数据
            iaidlSnow = IaidlSnowInterface.Stub.asInterface(service);

            StringBuilder sb = new StringBuilder();
            sb.append("IPC onServiceConnected\n");
            try {
                sb.append("My Main Thread Id = ").append(Thread.currentThread().getId()).append("\n").append("My Remote Process Id = ").append(iaidlSnow.getProcessId()).append("\n").append("My Remote Process Name = ").append(iaidlSnow.getProcessName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //打印
            LogUtil.d(sb.toString());

            //显示到activity
            doSomething(sb.toString());

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //打印
            LogUtil.d("IPC onServiceDisconnected");

            //显示到activity
            doSomething("IPC onServiceDisconnected");

        }
    };


    private int type = 0;

    public class OnClickHandle {
        public void onClickStartLongLifeService(View view) {
            startService(new Intent(ServiceAcitivty.this, LongLiftService.class));

        }

        public void onClickStopLongLifeService(View view) {
            stopService(new Intent(ServiceAcitivty.this, LongLiftService.class));

        }

        public void onClickStartBindService(View view) {
            Intent intent = new Intent(ServiceAcitivty.this, ShortLiftService.class);

            //绑定服务
            //通过unBindService 停止服务或者当绑定的组件被销毁
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            doSomething("StartBindService");
        }

        public void onClickStartBindService1(View view) {
            Intent intent = new Intent(ServiceAcitivty.this, IPCService.class);
            intent.putExtra("type", 0);
            //绑定IPC服务
            //通过unBindService 停止服务或者当绑定的组件被销毁
            bindService(intent, ipcServiceConnection, BIND_AUTO_CREATE);
        }

        public void onClickStartBindService2(View view) {
            Intent intent = new Intent(ServiceAcitivty.this, IPCService.class);
            //解绑IPC服务
            //通过unBindService 停止服务或者当绑定的组件被销毁
            if (iaidlSnow != null) {
                unbindService(ipcServiceConnection);
                iaidlSnow = null;
                doSomething("Stop IPC Service");
                return;
            }
            doSomething("IPC No Bind");
        }

        public void onClickStopBindService(View view) {
            if (myBind == null) {
                Toast.makeText(mContext, "还未绑定Service", Toast.LENGTH_SHORT).show();
                return;
            }
            unbindService(mServiceConnection);
            myBind = null;
            doSomething("StopBindService");
        }


        public void onClickBindServiceDoSomething(View view) {
            //
            if (myBind != null) doSomething(myBind.getCurTime());
            else Toast.makeText(mContext, "还未绑定Service", Toast.LENGTH_SHORT).show();
        }

        public void onClickSetIPCData(View view) {
            if (iaidlSnow != null) {
                IPCTestBean bean = new IPCTestBean();
                bean.setAge(27);
                bean.setName("InterView App");

                try {
                    iaidlSnow.setIPCTestBean(bean);
                    doSomething("Set Parcel Data." + (iaidlSnow.getIPCTestBean() == null ? "null" : iaidlSnow.getIPCTestBean().toString()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onClickGetIPCData(View view) {
            if (iaidlSnow != null) {
                try {
                    doSomething(iaidlSnow.getIPCTestBean() == null ? "null" : iaidlSnow.getIPCTestBean().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onClickSetIPCDataMessager(View view) {

            if (mServerMessenger == null) {
                Intent intent = new Intent(ServiceAcitivty.this, IPCService.class);
                intent.putExtra("type", 1);
                bindService(intent, messagerServiceConnection, BIND_AUTO_CREATE);
                return;
            }


            //测试一下能否设置数据
            Message test = Message.obtain(null, MSG_SET_VALUE);
            Bundle bundle = new Bundle();
//            bundle.putString("data", new IPCTestBean("Main App", 27));
            bundle.putString("data", new String("Main App" + new Random().nextInt()));
            test.setData(bundle);
            try {
                if (mServerMessenger != null) mServerMessenger.send(test);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        public void onClickGetIPCDataMessager(View view) {

            unbindService(messagerServiceConnection);

        }
    }

    private ServiceConnection messagerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //服务端的Messenger
            mServerMessenger = new Messenger(service);

            //现在开始构client用来传递和接收消息的messenger
            Messenger clientMessenger = new Messenger(new ClientHandler());
            try {
                //将client注册到server端
                Message register = Message.obtain(null, MSG_REGISTER_CLIENT);
                register.replyTo = clientMessenger;//这是注册的操作，我们可以在上面的Server代码看到这个对象被取出
                mServerMessenger.send(register);

                Toast.makeText(ServiceAcitivty.this, "绑定成功", Toast.LENGTH_SHORT).show();

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    //这些类型要和Server端想对应
    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_VALUE = 3;
    static final int MSG_CLIENT_SET_VALUE = 4;


    Messenger mServerMessenger;

    class ClientHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            LogUtil.d("Client:" + msg.what);
            if (msg.what == MSG_CLIENT_SET_VALUE) {
//                doSomething(((IPCTestBean) msg.obj) == null ? "null" : ((IPCTestBean) msg.obj).toString());
                doSomething(msg.getData().getString("data"));
            } else {
                super.handleMessage(msg);
            }
        }

    }

}