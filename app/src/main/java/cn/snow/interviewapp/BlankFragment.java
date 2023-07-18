package cn.snow.interviewapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import cn.snow.interviewapp.service.LifeCycleService;
import cn.snow.interviewapp.utils.LogUtil;
import cn.snow.interviewapp.viewmodel.LDTest;
import cn.snow.interviewapp.viewmodel.VMDataTest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    VMDataTest vmDataTest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LifeCycleService.ServiceBinder serviceBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = (LifeCycleService.ServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        inflate.findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(inflate).navigate(R.id.blankFragment2);
            }
        });


        inflate.findViewById(R.id.btn_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Navigation.findNavController(inflate).navigateUp()) {
                    requireActivity().finish();
                }
            }
        });

        inflate.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        inflate.findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Navigation.findNavController(inflate).navigateUp()) {
                    requireActivity().finish();
                }
            }
        });

        vmDataTest = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(VMDataTest.class);

        vmDataTest.setDataString((TextUtils.isEmpty(vmDataTest.getDataString()) ? "" : vmDataTest.getDataString()) + " Fragment1 ");

        //添加观察者
        vmDataTest.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        LDTest.getInstance().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogUtil.d("LD" + s);
            }
        });

        Transformations.map(LDTest.getInstance(), mData -> mData + "(map)");

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtil.d(vmDataTest.getDataString() == null ? "null" : vmDataTest.getDataString());

    }
}