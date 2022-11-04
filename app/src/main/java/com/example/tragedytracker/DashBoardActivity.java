package com.example.tragedytracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ArrayList<EarthquakeEntries>>>{

    public static int sourceID = 0;
    private DrawerLayout NavigationDrawer;
    private Toolbar customToolbar;
    private NavigationView navigationViewDrawer;
    private ActionBarDrawerToggle myDrawerToggle;
    ArrayList<String> urls = new ArrayList<>();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private ArrayList<String> type ;
    private ProgressDialog progressDialog;
    private String generalUrl;
    public static ArrayList<EarthquakeEntries> droughtentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> floodentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> cycloneentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> landslideentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> quakeEntriesList = new ArrayList<>();
    public static ArrayList<Integer>   lattitudeList = new ArrayList<>();
    public static ArrayList<Integer>  longitudeList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        customToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myDrawerToggle = new ActionBarDrawerToggle(this, NavigationDrawer, customToolbar, R.string.drawer_open,  R.string.drawer_close);
        if(myDrawerToggle == null){
            Log.e("error","hello");
        }

        myDrawerToggle.setDrawerIndicatorEnabled(true);
        myDrawerToggle.syncState();

        NavigationDrawer.addDrawerListener(myDrawerToggle);

        navigationViewDrawer = (NavigationView) findViewById(R.id.side_navigation);
//      Setting up the custom base fragment\
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<ArrayList<EarthquakeEntries>>> loader = loaderManager.getLoader(0);
        loaderManager.initLoader(0,null,this);
        Fragment home = new EathquakeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myframeLayout,home);
        transaction.commit();

        setupDrawer(navigationViewDrawer);

        type = new ArrayList<>();
        type.add("drought");
        type.add("extratropical");
        type.add("flood");
        type.add("land");
        type.add("tsunami");
        type.add("volcano");
        type.add("wild");
        generalUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+type.get(sourceID)+")%20AND%20(date.created%3A%3E%3D2009-08-05%20OR%20date.created%3A%3C2021-08-13)&limit=50";

    }


    private void setupDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //                      setting up the menuItem
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        LoaderManager loaderManager;
        Loader<ArrayList<ArrayList<EarthquakeEntries>>> loader;
        switch(menuItem.getItemId()) {
            case R.id.Earthquake:
                sourceID = 0;
                fragment = new EathquakeFragment();
                getSupportLoaderManager().restartLoader(0,null,this);
                break;
            case R.id.Drought:
                sourceID = 1;
                fragment = new DroughtFragment();
                loaderManager = getSupportLoaderManager();
                loader = loaderManager.getLoader(1);
                loaderManager.restartLoader(1,null,this);
                break;
            case R.id.Cyclone:
                sourceID = 2;
                fragment = new DroughtFragment();
                loaderManager = getSupportLoaderManager();
                loader = loaderManager.getLoader(2);
                loaderManager.restartLoader(2,null,this);
                break;
            case R.id.Flood:
                sourceID = 3;
                fragment = new DroughtFragment();
                loaderManager = getSupportLoaderManager();
                loader = loaderManager.getLoader(3);
                loaderManager.restartLoader(3,null,this);
                break;
            case R.id.landslide:
                sourceID = 4;
                fragment = new DroughtFragment();
                loaderManager = getSupportLoaderManager();
                loader = loaderManager.getLoader(4);
                loaderManager.restartLoader(4,null,this);
                break;
            case R.id.myProfile:
                fragment = new profileFragement();
                break;
            default:
                fragment = new EathquakeFragment();
                break;
        }
//      Getting fragment and setting the main framelayout
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myframeLayout,fragment);
        transaction.commit();

        menuItem.setChecked(true);
        //      Setting the title on the drawer layout
        setTitle(menuItem.getTitle());
        //       closing the drawer
        NavigationDrawer.closeDrawers();

    }


    private void fireFragment(Fragment fragment,boolean backstack){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myframeLayout,fragment);
        transaction.commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myDrawerToggle.syncState();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Loader<ArrayList<ArrayList<EarthquakeEntries>>> onCreateLoader(int id, @Nullable Bundle args) {
        progressDialog = new ProgressDialog(DashBoardActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.spinner);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        if(sourceID == 0){
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            Log.e("Manan ",USGS_REQUEST_URL);
            String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
            String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));
            String maxMagnitude = sharedPrefs.getString(getString(R.string.setting_max_magnitude_key),getString(R.string.setting_max_magnitude_default));
            String NumberOfEarthquakes = sharedPrefs.getString(getString(R.string.settings_number_of_earthquake_key),getString(R.string.settings_number_of_earthquakes_default));
            String startDate = sharedPrefs.getString(getString(R.string.settings_start_date_key),getString(R.string.settings_start_date_default));
            String endDate = sharedPrefs.getString(getString(R.string.settings_end_date_key),getString(R.string.settings_end_date_default));
            startDate = startDate.replace('/','-');
            endDate = endDate.replace('/','-');
            Uri baseUri = Uri.parse(USGS_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("format", "geojson");
            uriBuilder.appendQueryParameter("limit", ""+50);
            uriBuilder.appendQueryParameter("minmag", minMagnitude);
            uriBuilder.appendQueryParameter("orderby", orderBy);
            uriBuilder.appendQueryParameter("maxmag",maxMagnitude);
            Boolean res1 = true;
            Boolean res2 = true;
            if(res1&&res2){
                uriBuilder.appendQueryParameter("starttime",startDate);
                uriBuilder.appendQueryParameter("endtime",endDate);
            }else{
                endDate = dtf.format(now);
                Log.e("Manan ",uriBuilder.toString()+"  "+startDate+endDate);
                urls.add(USGS_REQUEST_URL);
                return new EarthQuakeHttpClassLoader(this,urls);
            }
            Log.e("Manan ",uriBuilder.toString());
            urls.add(uriBuilder.toString());
            return new EarthQuakeHttpClassLoader(this,urls);
        }else{
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String numberOfEntries = sharedPrefs.getString(getString(R.string.number_of_entries),"10");
            String startDate = sharedPrefs.getString(getString(R.string.settings_start_date_key),getString(R.string.settings_start_date_default));
            String endDate = sharedPrefs.getString(getString(R.string.settings_end_date_key),getString(R.string.settings_end_date_default));
//        Boolean check = sharedPrefs.getBoolean(getString(R.string.include_country),false);
            startDate = startDate.replace('/','-');
            endDate = endDate.replace('/','-');
            Boolean res1 = true;
            Boolean res2 = true;

            if(res1&&res2){
                numberOfEntries = ""+50;
                if(sourceID == 0){
                    sourceID = 1;
                }
                generalUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+type.get(sourceID-1)+")%20AND%20(date.created%3A%3E%3D"+startDate+"%20OR%20date.created%3A%3C"+endDate+")&limit="+numberOfEntries;
            }else{
                endDate = dtf.format(now);
                generalUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+type.get(sourceID-1)+")%20AND%20(date.created%3A%3E%3D2009-08-05%20OR%20date.created%3A%3C2021-08-13)&limit="+numberOfEntries;
            }
            Log.e("----------result ","Loader created" + generalUrl);
            ArrayList<String> urlArr = new ArrayList<>();
            urlArr.add(generalUrl);
            return new EarthQuakeHttpClassLoader(this,urlArr);
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<ArrayList<EarthquakeEntries>>> loader, ArrayList<ArrayList<EarthquakeEntries>> data) {
        Log.e("status","onLoadFinished Call");
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
//            Log.e("Status - entries",""+data.get(0).size());

        if(sourceID == 0){
            if(data.size()>0){
                quakeEntriesList = data.get(0);
            }
            Fragment droughtFragment = new EathquakeFragment();
            fireFragment(droughtFragment,false);
        }
        else if(sourceID == 1){
            if(data.size()>0){
                droughtentriesList = data.get(0);
            }
            Fragment extratropicalCycloneFragment = new DroughtFragment();
            fireFragment(extratropicalCycloneFragment,false);
        }
        else if(sourceID == 2){

            if(data.size()>0){
                floodentriesList = data.get(0);
            }
            Fragment floodFragment = new FloodFragment();
            fireFragment(floodFragment,false);
        }
        else if(sourceID == 3){

            if(data.size()>0){
                landslideentriesList = data.get(0);
            }
            Fragment landSlideFragment = new LandslideFragment();
            fireFragment(landSlideFragment,false);
        }
        else if(sourceID == 4){
            if(data.size()>0){
                cycloneentriesList = data.get(0);
            }
            Fragment tropicalCycloneFragment = new CycloneFragment();
            fireFragment(tropicalCycloneFragment,false);
        }
        loader.reset();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<ArrayList<EarthquakeEntries>>> loader) {
        Log.e("status","loader reset");

    }
}