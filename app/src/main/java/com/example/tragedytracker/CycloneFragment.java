package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tCycloneentriesList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CycloneFragment extends BaseFragment {

    public CycloneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        tRootView =  tInflater.inflate(R.layout.fragment_cyclone, tContainer, false);

        Log.e("status",""+tCycloneentriesList.size());
        RecyclerView tRecyclerTable = tRootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tRecyclerTable.setHasFixedSize(true);
        tRecyclerTable.setLayoutManager(tLayoutManager);
        DividerItemDecoration tDividerItemDecoration = new DividerItemDecoration(tRecyclerTable.getContext(),
                tLayoutManager.getOrientation());
        tRecyclerTable.addItemDecoration(tDividerItemDecoration);
        generalAdapter tAdapter = new generalAdapter(tCycloneentriesList, getActivity());
        tRecyclerTable.setAdapter(tAdapter);

        return tRootView;
    }
}