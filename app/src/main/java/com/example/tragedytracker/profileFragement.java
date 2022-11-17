package com.example.tragedytracker;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragement extends BaseFragment {
    protected CircleImageView tImage;
    protected EditText tDisplayName;
    protected AppCompatButton tUpdateProfile,tLogoutButton;
    protected ProgressBar tUpdateProfileProgress;
    protected String name;
    protected FirebaseStorage tStorage = FirebaseStorage.getInstance();
    protected StorageReference tReference  = tStorage.getReference();

    public profileFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater tInflater, ViewGroup tContainer,
                             Bundle savedInstanceState) {
        tRootView = tInflater.inflate(R.layout.fragment_profile_fragement, tContainer, false);
        tImage =  tRootView.findViewById(R.id.profile_image);
        tDisplayName =   tRootView.findViewById(R.id.displayNameEditText);
        tUpdateProfile =   tRootView.findViewById(R.id.updateProfileButton);
        tUpdateProfileProgress =  tRootView.findViewById(R.id.upadtaProfileProgressBar);

        FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
        if(tUser!= null){
            tDisplayName.setText(tUser.getDisplayName());
            if(tUser.getPhotoUrl() != null){
                Glide.with(requireActivity())
                        .load(tUser.getPhotoUrl())
                        .into(tImage);
            }
        }


        tUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatingProfile();
            }
        });

        tImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileImage();
            }
        });
        tLogoutButton = tRootView.findViewById(R.id.logout);

        tLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(getActivity());
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return tRootView;
    }

    @Override
    public void onActivityResult(int tRequestCode, int tResultCode, @Nullable Intent tData) {
        super.onActivityResult(tRequestCode, tResultCode, tData);
        if(tRequestCode == 1234){
            if(tResultCode == RESULT_OK){
                Bitmap tCurrImage = (Bitmap)tData.getExtras().get("data");
                tImage.setImageBitmap(tCurrImage);
                uploadImage(tCurrImage);
            }
        }
    }


    protected void updateProfileImage() {
        Intent tCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tCapture.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(tCapture,1234);
        }
    }

    protected void  uploadImage(Bitmap tImage){
        String tUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream tByteArray = new ByteArrayOutputStream();
        tImage.compress(Bitmap.CompressFormat.JPEG,100,tByteArray);
        StorageReference tCurrReference =  FirebaseStorage.getInstance().getReference()
                .child("profile Images")
                .child(tUserId+".jpeg");
        tCurrReference.putBytes(tByteArray.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("Status","uploaded");
                        getProfileImageUrl(tCurrReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    protected void getProfileImageUrl(StorageReference tReference){
        tReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri tUri) {
                        Log.e("status",tUri.toString());
                        updateUserProfileUrl(tUri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void updateUserProfileUrl(Uri tUri){
        FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest tProfileImageChangeRequest = new UserProfileChangeRequest.Builder()
                .setPhotoUri(tUri)
                .build();
        tUser.updateProfile(tProfileImageChangeRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("Status","uploaded successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        }


    protected void updatingProfile(){
        tUpdateProfile.setVisibility(View.GONE);
        String tCurrName = tDisplayName.getText().toString();
        FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest tNameChangeRequest  = new UserProfileChangeRequest.Builder().setDisplayName(tCurrName).build();

        tUser.updateProfile(tNameChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tUpdateProfile.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tUpdateProfile.setVisibility(View.VISIBLE);
                    }
                });
        }
}