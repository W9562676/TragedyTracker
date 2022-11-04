package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.landslideentriesList;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_landslide, container, false);
        RecyclerView recyclerTable = rootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerTable.setHasFixedSize(true);
        recyclerTable.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerTable.getContext(),
                layoutManager.getOrientation());
        recyclerTable.addItemDecoration(dividerItemDecoration);
        generalAdapter adapter = new generalAdapter(landslideentriesList, getActivity());
        recyclerTable.setAdapter(adapter);
        return rootView;
    }
}