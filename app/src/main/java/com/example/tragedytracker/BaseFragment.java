package com.example.tragedytracker;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseFragment extends Fragment {
    View tRootView;

    protected void launchFragment(Fragment fragment, boolean add) {
        FragmentManager tManager = getActivity().getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.mainframe, fragment);
        tTransaction.commit();
    }

}