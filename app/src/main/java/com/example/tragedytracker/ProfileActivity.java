package com.example.tragedytracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    protected CircleImageView tImage;
    protected EditText tDisplayName;
    protected AppCompatButton tUpdateProfile,tLogoutButton,deleteProfile;
    protected ProgressBar tUpdateProfileProgress;
    protected String name;
    protected FirebaseStorage tStorage = FirebaseStorage.getInstance();
    protected StorageReference tReference  = tStorage.getReference();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_fragement);


        tImage =  findViewById(R.id.profile_image);
        tDisplayName =   findViewById(R.id.displayNameEditText);
        tUpdateProfile =   findViewById(R.id.updateProfileButton);
        tUpdateProfileProgress =  findViewById(R.id.upadtaProfileProgressBar);
        deleteProfile = findViewById(R.id.delete);

        FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
        if(tUser!= null){
            tDisplayName.setText(tUser.getDisplayName());
            if(tUser.getPhotoUrl() != null){
                Glide.with(this)
                        .load(tUser.getPhotoUrl())
                        .into(tImage);
            }
        }

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser tUser = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest tProfileImageChangeRequest = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(null)
                        .build();
                tUser.updateProfile(tProfileImageChangeRequest)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                                ProfileActivity.this.finish();
//                                FragmentManager tManager = ProfileActivity.this.getSupportFragmentManager();
//                                FragmentTransaction tTransaction = tManager.beginTransaction();
//                                tTransaction.replace(R.id.myframeLayout,new profileFragement());
//                                tTransaction.commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

            }
        });


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
        tLogoutButton = findViewById(R.id.logout);

        tLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(ProfileActivity.this);
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                ProfileActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int tRequestCode, int tResultCode, @Nullable Intent data) {
        super.onActivityResult(tRequestCode, tResultCode, data);
        if(tRequestCode == 1234){
            if(tResultCode == RESULT_OK){
                Bitmap tCurrImage = (Bitmap)data.getExtras().get("data");
                tImage.setImageBitmap(tCurrImage);
                uploadImage(tCurrImage);
            }
        }
    }

    protected void  updateProfileImage() {
        Intent tCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tCapture.resolveActivity(ProfileActivity.this.getPackageManager()) != null){
            startActivityForResult(tCapture,1234);
        }
        FragmentManager tManager = ProfileActivity.this.getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,new profileFragement());
        tTransaction.commit();
    }

    protected void  uploadImage(Bitmap tImage){
        progressDialog
                = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
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
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress
                                = (100.0
                                * taskSnapshot.getBytesTransferred()
                                / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage(
                                "Uploaded "
                                        + (int)progress + "%");
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
                        progressDialog.dismiss();
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
                Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tUpdateProfile.setVisibility(View.VISIBLE);
            }
        });
    }
}