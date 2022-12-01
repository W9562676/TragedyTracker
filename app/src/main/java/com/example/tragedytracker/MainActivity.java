package com.example.tragedytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BiometricManager manager = BiometricManager.from(this);
        switch (manager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "No Fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "FingerPrint Not Working", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No Fingerprint assigned", Toast.LENGTH_SHORT).show();
                break;
        }


        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,executor,new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();

                Fragment tSplashScreen = new SplashFragment();
                FragmentManager tManager = getSupportFragmentManager();
                FragmentTransaction tTransaction = tManager.beginTransaction();
                tTransaction.replace(R.id.mainframe,tSplashScreen);
                tTransaction.commit();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                Fragment tSplashScreen = new SplashFragment();
                FragmentManager tManager = getSupportFragmentManager();
                FragmentTransaction tTransaction = tManager.beginTransaction();
                tTransaction.replace(R.id.mainframe,tSplashScreen);
                tTransaction.commit();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Login Failed ", Toast.LENGTH_SHORT).show();
            }


        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Tragedy Tracker").setDescription("").setDeviceCredentialAllowed(true).build();
        biometricPrompt.authenticate(promptInfo);

    }
}
