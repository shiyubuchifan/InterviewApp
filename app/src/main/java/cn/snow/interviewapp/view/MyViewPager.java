package cn.snow.interviewapp.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    public MyViewPager(@NonNull Context context) {
        super(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float dx = 0, dy = 0;
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                dx = getX();
                dy = getY();
                intercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(getX() - dx) > Math.abs(getY() - dy)) {//如果x轴移动大于y轴移动 左右滑动
                    intercept = true;
                }
                break;
        }

        return intercept;


    }
}
