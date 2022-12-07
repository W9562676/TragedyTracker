package com.example.tragedytracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class WildFireFragment extends Fragment {

    public WildFireFragment() {
        // Required empty public constructor
    }

   @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return tInflater.inflate(R.layout.fragment_wild_fire, tContainer, false);
    }
}