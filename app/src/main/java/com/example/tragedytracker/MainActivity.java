package com.example.tragedytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fragment tSplashScreen = new SplashFragment();
        FragmentManager tManager = getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.mainframe,tSplashScreen);
        tTransaction.commit();
    }
}
