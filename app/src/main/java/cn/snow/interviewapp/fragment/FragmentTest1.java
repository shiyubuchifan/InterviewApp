package cn.snow.interviewapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import cn.snow.interviewapp.R;
import cn.snow.interviewapp.base.BaseFragment;
import cn.snow.interviewapp.databinding.FragmentTest1Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTest1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTest1 extends BaseFragment<FragmentTest1Binding> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTest1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTest1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTest1 newInstance(String param1, String param2) {
        FragmentTest1 fragment = new FragmentTest1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initView() {
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


    }

}