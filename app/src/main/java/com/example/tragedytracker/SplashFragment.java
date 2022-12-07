package com.example.tragedytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


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
                        List<AuthUI.IdpConfig> tProviders = Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()
                        );
                        Intent tSign = AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(tProviders)
                                .setLogo(R.drawable.tragedy_logo)
                                .setAlwaysShowSignInMethodScreen(true)
                                .setIsSmartLockEnabled(false)
                                .build();
                        startActivityForResult(tSign,00001);
                    }
                }
            }
        }.start();
        return tRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 00001){
            if(resultCode == Activity.RESULT_OK){
                FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
                if(tUser.getMetadata().getCreationTimestamp() <= tUser.getMetadata().getLastSignInTimestamp() && tUser.getMetadata().getCreationTimestamp()+10 >= tUser.getMetadata().getLastSignInTimestamp()){
                }
                else{
                }
                startActivity(new Intent(getActivity(), DashBoardActivity.class));
            }
            else{
            }
        }
    }
}