package com.example.tragedytracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
    View tRootView;

    protected void launchFragment(androidx.fragment.app.Fragment fragment, boolean add) {
        FragmentManager tManager = getActivity().getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.mainframe, fragment);
        tTransaction.commit();
    }

}