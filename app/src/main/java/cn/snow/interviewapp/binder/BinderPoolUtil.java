package cn.snow.interviewapp.binder;

import android.os.IBinder;
import android.os.RemoteException;

import cn.snow.interviewapp.aidl.IAidlSnowImpl;
import cn.snow.interviewapp.aidl.IBinderPool;

public class BinderPoolUtil {

    private BinderPoolUtil() {

    }

    public static BinderPoolUtil getInstance() {
        return INSTANCE.binderPoolUtil;
    }

    private static class INSTANCE {
        private static final BinderPoolUtil binderPoolUtil = new BinderPoolUtil();
    }


    public static class BinderPoolImp extends IBinderPool.Stub {
        @Override
        public IBinder getBinderFromPool(int binderID) throws RemoteException {
            if(binderID==0){
                return new IAidlSnowImpl();
            }
            return null;
        }
    }
}
