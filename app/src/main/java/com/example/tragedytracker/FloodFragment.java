package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tFloodentriesList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FloodFragment extends BaseFragment {



    public FloodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tRootView =  tInflater.inflate(R.layout.fragment_flood, tContainer, false);

        Log.e("status",""+tFloodentriesList.size());
        RecyclerView tRecyclerTable = tRootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tRecyclerTable.setHasFixedSize(true);
        tRecyclerTable.setLayoutManager(tLayoutManager);
        DividerItemDecoration tDividerItemDecoration = new DividerItemDecoration(tRecyclerTable.getContext(),
                tLayoutManager.getOrientation());
        tRecyclerTable.addItemDecoration(tDividerItemDecoration);
        generalAdapter tAdapter = new generalAdapter(tFloodentriesList, getActivity());
        tRecyclerTable.setAdapter(tAdapter);

        return tRootView;
    }
}