package cn.snow.interviewapp.activity;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;

import cn.snow.interviewapp.R;
import cn.snow.interviewapp.base.BaseActivity;
import cn.snow.interviewapp.databinding.ActivityWebBinding;

public class ActivityWeb extends BaseActivity<ActivityWebBinding> {

    private WebSettings webSettings = null;
    private String webUrl;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_web;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {

        webUrl = getIntent().getStringExtra("url");
        webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAllowFileAccess(true);    // 可以读取文件缓存


        binding.webView.loadUrl(webUrl);

    }
}