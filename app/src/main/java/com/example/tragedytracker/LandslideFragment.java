package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tLandslideentriesList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LandslideFragment extends BaseFragment {



    public LandslideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        tRootView =  tInflater.inflate(R.layout.fragment_landslide, tContainer, false);
        RecyclerView tRecyclerTable = tRootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tRecyclerTable.setHasFixedSize(true);
        tRecyclerTable.setLayoutManager(tLayoutManager);
        DividerItemDecoration tDividerItemDecoration = new DividerItemDecoration(tRecyclerTable.getContext(),
                tLayoutManager.getOrientation());
        tRecyclerTable.addItemDecoration(tDividerItemDecoration);
        generalAdapter tAdapter = new generalAdapter(tLandslideentriesList, getActivity());
        tRecyclerTable.setAdapter(tAdapter);
        return tRootView;
    }
}