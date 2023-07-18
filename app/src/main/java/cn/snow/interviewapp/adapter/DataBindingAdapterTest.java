package cn.snow.interviewapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class DataBindingAdapterTest {
    @BindingAdapter(value = {"adapterTestString"})
    public static void setAdapterTestString(TextView test, String str) {
        test.setText(str);
        test.setTextColor(Color.BLUE);
        test.setBackground(new ColorDrawable(Color.YELLOW));
    }


}
