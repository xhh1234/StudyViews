package com.mystudy.studyviews.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mystudy.studyviews.R;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class ViewGroupFragment extends Fragment {



    public ViewGroupFragment() {
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
        View view=inflater.inflate(R.layout.fragment_view_group, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}
