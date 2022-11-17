package com.example.tragedytracker;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class EarthQuakeHttpClassLoader extends AsyncTaskLoader<ArrayList<ArrayList<EarthquakeEntries>>>{

    private ArrayList<String> tStringUrl;
    public EarthQuakeHttpClassLoader(Context tContext, ArrayList<String> tUrl) {
        super(tContext);
        tStringUrl = tUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<ArrayList<EarthquakeEntries>> loadInBackground() {
        int tSize=tStringUrl.size();
        ArrayList<EarthquakeEntries> tEarthQuake = new ArrayList<>();
        ArrayList<ArrayList<EarthquakeEntries>> tAllEarthquakes = new ArrayList<>();
        for(int j=0;j<tSize;j++) {
            try {
                tEarthQuake = EarthquakeQueryUtils.fetchEarthquakeData(tStringUrl.get(j));
                if(tEarthQuake!=null) tAllEarthquakes.add(tEarthQuake);
            } catch (IOException | JSONException e) {
            }
        }
        return tAllEarthquakes;
    }
}
