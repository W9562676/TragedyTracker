package com.example.tragedytracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SplashFragment extends BaseFragment {

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View tRootView =  tInflater.inflate(R.layout.fragment_splash, tContainer, false);
        new Thread(){
            public void run(){
                try{

//                    Sleeping the system for loading
                    sleep(5000);
                }catch(Exception e){
                }

                finally {
//                   Checking the weather the user had logged in the application or not.
                    if(FirebaseAuth.getInstance().getCurrentUser() != null){
                        Intent DashboardActivity = new Intent(getActivity(),DashBoardActivity.class);
                        DashboardActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(DashboardActivity);
                    }else{
//                       launching the login fragment if the user is new to the app
                        launchFragment(new LoginFragment(),false);
                    }
                }
            }
        }.start();
        return tRootView;
    }
}