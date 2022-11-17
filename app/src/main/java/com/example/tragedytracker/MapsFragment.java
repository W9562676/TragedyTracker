package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tQuakeEntriesList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap tGoogleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            for(int j=0;j<tQuakeEntriesList.size();j++){
                EarthquakeEntries tCurrloc = tQuakeEntriesList.get(j);
                LatLng tSydney = new LatLng(tCurrloc.getLongitude(),tCurrloc.getLattitude());
                tGoogleMap.addMarker(new MarkerOptions().position(tSydney));
                Log.e("status",tCurrloc.getLattitude()+" "+tCurrloc.getLongitude()+" "+tCurrloc.getName());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater tInflater,
                             @Nullable ViewGroup tContainer,
                             @Nullable Bundle savedInstanceState) {
        return tInflater.inflate(R.layout.fragment_maps, tContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment tMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (tMapFragment != null) {
            tMapFragment.getMapAsync(callback);
        }
    }
}