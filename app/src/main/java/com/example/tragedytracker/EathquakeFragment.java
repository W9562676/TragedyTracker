package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tQuakeEntriesList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EathquakeFragment extends BaseFragment {

    public EathquakeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         tRootView = tInflater.inflate(R.layout.fragment_eathquake, tContainer, false);
        AppCompatButton tLocate = tRootView.findViewById(R.id.locateButton);

         RecyclerView tRecyclerTable = tRootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tRecyclerTable.setHasFixedSize(true);
        tRecyclerTable.setLayoutManager(tLayoutManager);
        DividerItemDecoration tDividerItemDecoration = new DividerItemDecoration(tRecyclerTable.getContext(),
                tLayoutManager.getOrientation());
        tRecyclerTable.addItemDecoration(tDividerItemDecoration);
        EarthquakeAdapter tAdapter = new EarthquakeAdapter(tQuakeEntriesList, getActivity());
        tRecyclerTable.setAdapter(tAdapter);

        tLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tFragment = new MapsFragment();
                fireFragment(tFragment,true);
            }
        });
        return tRootView ;
    }
    private void fireFragment(Fragment tFragment,boolean backstack){
        FragmentManager tManager = getActivity().getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,tFragment);
        tTransaction.commit();
    }
}