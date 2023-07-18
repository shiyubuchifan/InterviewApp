package cn.snow.interviewapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import cn.snow.interviewapp.R;


public class FragmentTest2 extends Fragment {


    public FragmentTest2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test2, container, false);

        TextView tv = view.findViewById(R.id.tv_1);
        tv.setTextColor(Color.YELLOW);

        return view;
    }
}