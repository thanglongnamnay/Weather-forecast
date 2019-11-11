package com.example.weatherforcast.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.weatherforcast.AddNotiActivity;
import com.example.weatherforcast.R;
import com.example.weatherforcast.adapter.NotiAdapter;
import com.example.weatherforcast.IO.NotiIO;

public class NotiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private NotiAdapter notiAdapter;

    public NotiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotiFragment newInstance(String param1, String param2) {
        NotiFragment fragment = new NotiFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        notiAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_noti, container, false);
        notiAdapter = new NotiAdapter(getContext(), NotiIO.getInstance(getContext()).getNotiList());
        ((ListView)root.findViewById(R.id.lv_noti)).setAdapter(notiAdapter);
        root.findViewById(R.id.btn_noti_add).setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddNotiActivity.class);
            getContext().startActivity(intent);
        });
        return root;
    }
}
