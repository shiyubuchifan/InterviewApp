package cn.snow.interviewapp.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.amap.api.maps2d.MapView;


public class BaseMapVIew extends MapView implements LifecycleEventObserver {


    public BaseMapVIew(Context context) {
        super(context);
    }

    public BaseMapVIew(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseMapVIew(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

//    @Override
//    public AMap getMap() {
//        return super.getMap();
//    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_RESUME:
                onResume();
                break;
            case ON_PAUSE:
                onPause();
                break;
            case ON_DESTROY:
                onDestroy();
                break;
        }
    }
}
