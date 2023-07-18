package cn.snow.interviewapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.locks.ReentrantLock;

import cn.snow.interviewapp.activity.ActivityAcitivty;
import cn.snow.interviewapp.activity.ActivityFragment;
import cn.snow.interviewapp.activity.ActivityMap;
import cn.snow.interviewapp.activity.ActivityWeb;
import cn.snow.interviewapp.base.BaseActivity;
import cn.snow.interviewapp.databinding.ActivityMainBinding;
import cn.snow.interviewapp.service.TestService;
import cn.snow.interviewapp.utils.LogUtil;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {


        binding.setTestData("test data");

//        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(@NonNull MotionEvent e) {
//                LogUtil.d("onDown");
//                return false;
//            }
//
//            @Override
//            public void onShowPress(@NonNull MotionEvent e) {
//                LogUtil.d("onShowPress");
//            }
//
//            @Override
//            public boolean onSingleTapUp(@NonNull MotionEvent e) {
//                LogUtil.d("onSingleTapUp");
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
//                LogUtil.d("onScroll");
//                return false;
//            }
//
//            @Override
//            public void onLongPress(@NonNull MotionEvent e) {
//                LogUtil.d("onLongPress");
//            }
//
//            @Override
//            public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
//                LogUtil.d("onFling");
//                return false;
//            }
//        });

        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                LogUtil.d("onSingleTapConfirmed");
                Scroller scroller = new Scroller(MainActivity.this);

                binding.btnActivity.scrollBy(50, 0);
                LogUtil.d("onSingleTapConfirmed" + binding.btnActivity.getScrollX() + "-" + binding.btnActivity.getScaleX());

                ObjectAnimator.ofFloat(binding.btnActivity, "translationX", 0, 500).setDuration(500).start();
                return false;
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                LogUtil.d("onDoubleTap");
                binding.btnActivity.scrollBy(-50, 0);
                LogUtil.d("onDoubleTap" + binding.btnActivity.getScrollX() + "-" + binding.btnActivity.getScaleX());
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
                LogUtil.d("onDoubleTapEvent");
                return false;
            }
        });

        //设置事件绑定
        binding.setEventListener(new EventListener());

//        ContentResolver类是ContentProvider的管理类 其他进程通过这个类来进行对ContentProvider中数据的操作
        Uri uri = Uri.parse("content://cn.snow.interviewapp.provider.test/user");

        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Tom");
        // 获取ContentResolver
        ContentResolver resolver = getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri, values);
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri, new String[]{"_id", "name"}, null, null, null);

        try {
            while (cursor.moveToNext()) {
                System.out.println("query user:" + cursor.getInt(0) + " " + cursor.getString(1));
                // 将表中数据全部输出
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int X = (int) event.getRawX();
                int Y = (int) event.getRawY();


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.d("DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:


//                        ObjectAnimator.ofFloat(binding.btnActivity, "translationX", lx, X - lx).start();
//                        ObjectAnimator.ofFloat(binding.btnActivity, "translationY", ly, Y - ly).start();

                        break;
                    case MotionEvent.ACTION_UP:

                        ValueAnimator o = ObjectAnimator.ofInt(0, 1).setDuration(1000);
                        o.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                                float f = animation.getAnimatedFraction();
//                                LogUtil.d("onAnimationUpdate" + f);
                            }
                        });
                        o.start();

                        Scroller scroller = new Scroller(MainActivity.this);
                        scroller.startScroll(0, 0, 50, 0);
                        binding.btnActivity.setScroller(scroller);
                        binding.btnActivity.computeScroll();


                        break;
                }
                lx = X;
                ly = Y;

//                return gestureDetector.onTouchEvent(event);

                return false;
            }
        });


        sp = getSharedPreferences("snowsp", MODE_PRIVATE);

    }

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int lx, ly;


    public class EventListener {
        public void onClickTest(View v) {
            String dataString = Long.toString(System.currentTimeMillis());
            binding.setTestData(dataString);

        }

        public void onClickActivity(View view) {
            startActivity(new Intent(mContext, ActivityAcitivty.class));

        }

        public void onClickFragment(View view) {
//            Intent intent = new Intent(mContext, ActivityWeb.class);
//            intent.putExtra("url", "https://xiazdong.github.io/2017/06/15/android-fragment/");
//            startActivity(intent);

            Intent intent = new Intent(mContext, ActivityFragment.class);
            startActivity(intent);
        }

        public void onClickService(View view) {
//            Intent intent = new Intent(mContext, ActivityWeb.class);
//            intent.putExtra("url", "https://juejin.cn/post/6844903781541347341");
//            startActivity(intent);

            Intent intent = new Intent(mContext, TestService.class);
            startService(intent);
        }

        public void onClickServiceAct(View view) {
//            Intent intent = new Intent(mContext, ServiceAcitivty.class);
//            Intent intent = new Intent(mContext, CameraActivity.class);
//            intent.putExtra("url", "https://juejin.cn/post/6844903781541347341");
//            startActivity(intent);
        }

        public void onClickCp(View view) {
            Intent intent = new Intent(mContext, ActivityWeb.class);
            intent.putExtra("url", "https://segmentfault.com/a/1190000039241548");
            startActivity(intent);
        }

        public void onClickCpApp(View view) {
            Intent intent = new Intent("test.activity.action_1");
//            intent.setClassName("cn.snow.testbinder", "cn.snow.testbinder.MainActivity");

//            intent.addCategory(Intent.CATEGORY_APP_BROWSER);

            intent.setDataAndType(Uri.parse("https://video.weibo.com/show?fid=1034:4898726267781125"), "video/*");
//            intent.setType("video/*");

            ResolveInfo resolveInfo = getApplication().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            ResolveInfo resolveInfo = getApplication().getPackageManager().resolveActivity(intent,PackageManager.MATCH_ALL);

            boolean b = resolveInfo != null;
            Toast.makeText(mContext, b ? "YES" + resolveInfo.activityInfo.toString() : "NO", Toast.LENGTH_SHORT).show();

            LogUtil.d(b ? "YES" + resolveInfo.activityInfo.toString() : "NO");


            if (b) {
                startActivity(intent);
            }


//            try {
//                startActivity(intent);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        public void onClickBr(View view) {
            Intent intent = new Intent(mContext, ActivityWeb.class);
            intent.putExtra("url", "https://juejin.cn/post/6844903989901967368");
            startActivity(intent);
        }

        public void onClickMap(View view) {
            Intent intent = new Intent(mContext, ActivityMap.class);
            startActivity(intent);
        }

    }

    VelocityTracker tracker;


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (tracker == null) tracker = VelocityTracker.obtain();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                tracker.addMovement(event);
//                LogUtil.d("DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                tracker.computeCurrentVelocity(1000);
//                float xv = tracker.getXVelocity();
//                float yv = tracker.getYVelocity();
//                LogUtil.d(xv + "-" + yv);
//                break;
//            case MotionEvent.ACTION_UP:
//                tracker.clear();
//                tracker.recycle();
//                tracker = null;
//                break;
//        }
//
//        return super.onTouchEvent(event);
//    }

    ReentrantLock lock = new ReentrantLock();

    Object f = new Object();

}