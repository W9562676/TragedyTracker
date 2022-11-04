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
    protected CircleImageView Image;
    protected EditText displayName;
    protected AppCompatButton updateProfile,logoutButton;
    protected ProgressBar updateProfileProgress;
    protected String name;
    protected FirebaseStorage storage = FirebaseStorage.getInstance();
    protected StorageReference reference  = storage.getReference();

    public profileFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_fragement, container, false);
        Image =  rootView.findViewById(R.id.profile_image);
        displayName =   rootView.findViewById(R.id.displayNameEditText);
        updateProfile =   rootView.findViewById(R.id.updateProfileButton);
        updateProfileProgress =  rootView.findViewById(R.id.upadtaProfileProgressBar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            displayName.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){
                Glide.with(requireActivity())
                        .load(user.getPhotoUrl())
                        .into(Image);
            }
        }


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatingProfile();
            }
        });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileImage();
            }
        });
        logoutButton = rootView.findViewById(R.id.logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(getActivity());
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            if(resultCode == RESULT_OK){
                Bitmap currImage = (Bitmap)data.getExtras().get("data");
                Image.setImageBitmap(currImage);
                uploadImage(currImage);
            }
        }
    }


    protected void updateProfileImage() {
        Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(capture.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(capture,1234);
        }
    }

    protected void  uploadImage(Bitmap image){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArray);
        StorageReference currReference =  FirebaseStorage.getInstance().getReference()
                .child("profile Images")
                .child(userId+".jpeg");
        currReference.putBytes(byteArray.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("Status","uploaded");
                        getProfileImageUrl(currReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    protected void getProfileImageUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("status",uri.toString());
                        updateUserProfileUrl(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void updateUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileImageChangeRequest = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileImageChangeRequest)
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
        updateProfile.setVisibility(View.GONE);
        String currName = displayName.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest nameChangeRequest  = new UserProfileChangeRequest.Builder().setDisplayName(currName).build();

        user.updateProfile(nameChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                updateProfile.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateProfile.setVisibility(View.VISIBLE);
                    }
                });
        }
}