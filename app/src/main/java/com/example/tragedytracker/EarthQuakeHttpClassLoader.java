package com.example.tragedytracker;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class EarthQuakeHttpClassLoader extends AsyncTaskLoader<ArrayList<ArrayList<EarthquakeEntries>>>{

    private ArrayList<String> stringUrl;
    public EarthQuakeHttpClassLoader(Context context, ArrayList<String> url) {
        super(context);
        stringUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<ArrayList<EarthquakeEntries>> loadInBackground() {
        int size=stringUrl.size();
        ArrayList<EarthquakeEntries> earthQuake = new ArrayList<>();
        ArrayList<ArrayList<EarthquakeEntries>> allEarthquakes = new ArrayList<>();
        for(int i=0;i<size;i++) {
            try {
                earthQuake = EarthquakeQueryUtils.fetchEarthquakeData(stringUrl.get(i));
                if(earthQuake!=null) allEarthquakes.add(earthQuake);
            } catch (IOException | JSONException e) {
            }
        }
        return allEarthquakes;
    }
}
