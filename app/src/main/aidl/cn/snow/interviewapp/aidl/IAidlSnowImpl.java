package cn.snow.interviewapp.aidl;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;

import java.util.List;

import cn.snow.interviewapp.base.BaseApplication;

public class IAidlSnowImpl extends IaidlSnowInterface.Stub {

    IPCTestBean bean;

    @Override
    public int getProcessId() throws RemoteException {
        return android.os.Process.myPid();
    }

    @Override
    public String getProcessName() throws RemoteException {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) BaseApplication.getmAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "null";
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "null";
    }

    @Override
    public IPCTestBean getIPCTestBean() throws RemoteException {
        return bean;
    }


    @Override
    public void setIPCTestBean(IPCTestBean bean) throws RemoteException {
        this.bean = bean;
    }
}
