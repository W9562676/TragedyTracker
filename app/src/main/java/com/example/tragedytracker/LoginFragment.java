package com.example.tragedytracker;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tRootView = tInflater.inflate(R.layout.fragment_login, tContainer, false);
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
        return tRootView;
    }

    @Override
    public void onActivityResult(int tRequestCode, int tResultCode, @Nullable Intent tData) {
        super.onActivityResult(tRequestCode, tResultCode, tData);
        if(tRequestCode == 00001){
            if(tResultCode == Activity.RESULT_OK){
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