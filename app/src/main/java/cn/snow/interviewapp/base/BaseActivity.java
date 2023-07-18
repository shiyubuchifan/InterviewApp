package cn.snow.interviewapp.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleRegistry;

import cn.snow.interviewapp.utils.LogUtil;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected Context mContext;
    protected String TAG = this.getClass().getSimpleName();
    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(getClass().getSimpleName() + "onCreate");
        mContext = this;
        binding = DataBindingUtil.setContentView(this, getLayoutID());


        initView();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(getClass().getSimpleName() + "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(getClass().getSimpleName() + "onStart");
        ((LifecycleRegistry) getLifecycle()).setCurrentState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(getClass().getSimpleName() + "onResume");
        ((LifecycleRegistry) getLifecycle()).setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(getClass().getSimpleName() + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(getClass().getSimpleName() + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(getClass().getSimpleName() + "onDestroy");
    }

    protected abstract int getLayoutID();

    protected abstract void initView();

    protected void initData() {
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d(getClass().getSimpleName() + "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(getClass().getSimpleName() + "onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        newConfig.toString();
        LogUtil.d(getClass().getSimpleName() + "onConfigurationChanged" + newConfig.toString());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        setIntent(intent);//设置新的Intent  不然getIntent（） 依然是获取的旧Intent
    }
}
