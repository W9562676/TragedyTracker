package com.example.tragedytracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TusunamiFragment extends BaseFragment {


    public TusunamiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tRootView = tInflater.inflate(R.layout.fragment_tusunami, tContainer, false);
        return tRootView;
    }
}