package cn.snow.interviewapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import cn.snow.interviewapp.R;
import cn.snow.interviewapp.base.BaseActivity;
import cn.snow.interviewapp.databinding.ActivityAcitivtyBinding;
import cn.snow.interviewapp.viewmodel.VMTest;

public class ActivityAcitivty extends BaseActivity<ActivityAcitivtyBinding> {

    VMTest vmTest;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_acitivty;
    }

    @Override
    protected void initView() {

        vmTest = new VMTest();
        binding.setVmet(vmTest);

        binding.setMDataBean("testString");

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAcitivty.this, ActivityWeb.class);
                intent.putExtra("url", "https://juejin.cn/post/6996906294519005192");
                startActivity(intent);
            }
        });

        binding.btnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "No me", Toast.LENGTH_SHORT).show();
            }
        });
    }
}