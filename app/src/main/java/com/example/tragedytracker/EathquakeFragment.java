package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.quakeEntriesList;

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

public class EathquakeFragment extends BaseFragment {

    public EathquakeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_eathquake, container, false);
        AppCompatButton locate = rootView.findViewById(R.id.locateButton);

         RecyclerView recyclerTable = rootView.findViewById(R.id.recyclerTable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerTable.setHasFixedSize(true);
        recyclerTable.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerTable.getContext(),
                layoutManager.getOrientation());
        recyclerTable.addItemDecoration(dividerItemDecoration);
        EarthquakeAdapter adapter = new EarthquakeAdapter(quakeEntriesList, getActivity());
        recyclerTable.setAdapter(adapter);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapsFragment();
                fireFragment(fragment,true);
            }
        });
        return rootView ;
    }
    private void fireFragment(Fragment fragment,boolean backstack){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myframeLayout,fragment);
        transaction.commit();
    }
}