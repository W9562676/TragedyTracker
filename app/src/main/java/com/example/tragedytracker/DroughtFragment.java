package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tDroughtentriesList;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DroughtFragment extends BaseFragment {

    public DroughtFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tRootView =  tInflater.inflate(R.layout.fragment_drought, tContainer, false);

        Log.e("status",""+tDroughtentriesList.size());
        RecyclerView tRecyclerTable = tRootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tRecyclerTable.setHasFixedSize(true);
        tRecyclerTable.setLayoutManager(tLayoutManager);
        DividerItemDecoration tDividerItemDecoration = new DividerItemDecoration(tRecyclerTable.getContext(),
                tLayoutManager.getOrientation());
        tRecyclerTable.addItemDecoration(tDividerItemDecoration);
        generalAdapter tAdapter = new generalAdapter(tDroughtentriesList, getActivity());
        tRecyclerTable.setAdapter(tAdapter);

        return tRootView;
    }

    private void fireFragment(Fragment tFragment,boolean backstack){
        FragmentManager tManager = getActivity().getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,tFragment);
        tTransaction.commit();
    }
}