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

    public static int tSourceID = 0;
    private DrawerLayout tNavigationDrawer;
    private Toolbar tCustomToolbar;
    private NavigationView tNavigationViewDrawer;
    private ActionBarDrawerToggle tMyDrawerToggle;
    ArrayList<String> tUrls = new ArrayList<>();
    private static final String t_USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private ArrayList<String> tType ;
    private ProgressDialog tProgressDialog;
    private String tGeneralUrl;
    public static ArrayList<EarthquakeEntries> tDroughtentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> tFloodentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> tCycloneentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> tLandslideentriesList = new ArrayList<>();
    public static ArrayList<EarthquakeEntries> tQuakeEntriesList = new ArrayList<>();
    public static ArrayList<Integer>   tLattitudeList = new ArrayList<>();
    public static ArrayList<Integer>  tLongitudeList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        tCustomToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(tCustomToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tNavigationDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        tMyDrawerToggle = new ActionBarDrawerToggle(this, tNavigationDrawer, tCustomToolbar, R.string.drawer_open,  R.string.drawer_close);
        if(tMyDrawerToggle == null){
            Log.e("error","hello");
        }

        tMyDrawerToggle.setDrawerIndicatorEnabled(true);
        tMyDrawerToggle.syncState();

        tNavigationDrawer.addDrawerListener(tMyDrawerToggle);

        tNavigationViewDrawer = (NavigationView) findViewById(R.id.side_navigation);
//      Setting up the custom base fragment\
        LoaderManager tLoaderManager = getSupportLoaderManager();
        Loader<ArrayList<ArrayList<EarthquakeEntries>>> tLoader = tLoaderManager.getLoader(0);
        tLoaderManager.initLoader(0,null,this);
        Fragment tHome = new EathquakeFragment();
        FragmentManager tManager = getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,tHome);
        tTransaction.commit();

        setupDrawer(tNavigationViewDrawer);

        tType = new ArrayList<>();
        tType.add("drought");
        tType.add("extratropical");
        tType.add("flood");
        tType.add("land");
        tType.add("tsunami");
        tType.add("volcano");
        tType.add("wild");
        tGeneralUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+tType.get(tSourceID)+")%20AND%20(date.created%3A%3E%3D2009-08-05%20OR%20date.created%3A%3C2021-08-13)&limit=50";

    }


    private void setupDrawer(NavigationView tNavigationView) {
        tNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem tMenuItem) {
                        //                      setting up the menuItem
                        selectDrawerItem(tMenuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem tMenuItem) {
        Fragment tFragment = null;
        LoaderManager tLoaderManager;
        Loader<ArrayList<ArrayList<EarthquakeEntries>>> tLoader;
        switch(tMenuItem.getItemId()) {
            case R.id.Earthquake:
                tSourceID = 0;
                tFragment = new EathquakeFragment();
                getSupportLoaderManager().restartLoader(0,null,this);
                break;
            case R.id.Drought:
                tSourceID = 1;
                tFragment = new DroughtFragment();
                tLoaderManager = getSupportLoaderManager();
                tLoader = tLoaderManager.getLoader(1);
                tLoaderManager.restartLoader(1,null,this);
                break;
            case R.id.Cyclone:
                tSourceID = 2;
                tFragment = new DroughtFragment();
                tLoaderManager = getSupportLoaderManager();
                tLoader = tLoaderManager.getLoader(2);
                tLoaderManager.restartLoader(2,null,this);
                break;
            case R.id.Flood:
                tSourceID = 3;
                tFragment = new DroughtFragment();
                tLoaderManager = getSupportLoaderManager();
                tLoader = tLoaderManager.getLoader(3);
                tLoaderManager.restartLoader(3,null,this);
                break;
            case R.id.landslide:
                tSourceID = 4;
                tFragment = new DroughtFragment();
                tLoaderManager = getSupportLoaderManager();
                tLoader = tLoaderManager.getLoader(4);
                tLoaderManager.restartLoader(4,null,this);
                break;
//            case R.id.myProfile:
//                tFragment = new profileFragement();
//                break;
            default:
                tFragment = new EathquakeFragment();
                break;
        }
//      Getting fragment and setting the main framelayout
        FragmentManager tManager = getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,tFragment);
        tTransaction.commit();

        tMenuItem.setChecked(true);
        //      Setting the title on the drawer layout
        setTitle(tMenuItem.getTitle());
        //       closing the drawer
        tNavigationDrawer.closeDrawers();

    }


    private void fireFragment(Fragment tFragment,boolean backstack){
        FragmentManager tManager = getSupportFragmentManager();
        FragmentTransaction tTransaction = tManager.beginTransaction();
        tTransaction.replace(R.id.myframeLayout,tFragment);
        tTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        tMyDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tMyDrawerToggle.syncState();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Loader<ArrayList<ArrayList<EarthquakeEntries>>> onCreateLoader(int id, @Nullable Bundle args) {
        tProgressDialog = new ProgressDialog(DashBoardActivity.this);
        tProgressDialog.show();
        tProgressDialog.setContentView(R.layout.spinner);
        DateTimeFormatter tDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime tNow = LocalDateTime.now();
        System.out.println(tDtf.format(tNow));
        if(tSourceID == 0){
            SharedPreferences tSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            Log.e("Manan ",t_USGS_REQUEST_URL);
            String tMinMagnitude = tSharedPrefs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
            String tOrderBy = tSharedPrefs.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));
            String tMaxMagnitude = tSharedPrefs.getString(getString(R.string.setting_max_magnitude_key),getString(R.string.setting_max_magnitude_default));
            String tNumberOfEarthquakes = tSharedPrefs.getString(getString(R.string.settings_number_of_earthquake_key),getString(R.string.settings_number_of_earthquakes_default));
            String tStartDate = tSharedPrefs.getString(getString(R.string.settings_start_date_key),getString(R.string.settings_start_date_default));
            String tEndDate = tSharedPrefs.getString(getString(R.string.settings_end_date_key),getString(R.string.settings_end_date_default));
            tStartDate = tStartDate.replace('/','-');
            tEndDate = tEndDate.replace('/','-');
            Uri tBaseUri = Uri.parse(t_USGS_REQUEST_URL);
            Uri.Builder tUriBuilder = tBaseUri.buildUpon();
            tUriBuilder.appendQueryParameter("format", "geojson");
            tUriBuilder.appendQueryParameter("limit", ""+50);
            tUriBuilder.appendQueryParameter("minmag", tMinMagnitude);
            tUriBuilder.appendQueryParameter("orderby", tOrderBy);
            tUriBuilder.appendQueryParameter("maxmag",tMaxMagnitude);
            Boolean tRes1 = true;
            Boolean tRes2 = true;
            if(tRes1&&tRes2){
                tUriBuilder.appendQueryParameter("starttime",tStartDate);
                tUriBuilder.appendQueryParameter("endtime",tEndDate);
            }else{
                tEndDate = tDtf.format(tNow);
                Log.e("Manan ",tUriBuilder.toString()+"  "+tStartDate+tEndDate);
                tUrls.add(t_USGS_REQUEST_URL);
                return new EarthQuakeHttpClassLoader(this,tUrls);
            }
            Log.e("Manan ",tUriBuilder.toString());
            tUrls.add(tUriBuilder.toString());
            return new EarthQuakeHttpClassLoader(this,tUrls);
        }else{
            SharedPreferences tSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String tNumberOfEntries = tSharedPrefs.getString(getString(R.string.number_of_entries),"10");
            String tStartDate = tSharedPrefs.getString(getString(R.string.settings_start_date_key),getString(R.string.settings_start_date_default));
            String tEndDate = tSharedPrefs.getString(getString(R.string.settings_end_date_key),getString(R.string.settings_end_date_default));
//        Boolean check = tSharedPrefs.getBoolean(getString(R.string.include_country),false);
            tStartDate = tStartDate.replace('/','-');
            tEndDate = tEndDate.replace('/','-');
            Boolean tRes1 = true;
            Boolean tRes2 = true;

            if(tRes1&&tRes2){
                tNumberOfEntries = ""+50;
                if(tSourceID == 0){
                    tSourceID = 1;
                }
                tGeneralUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+tType.get(tSourceID-1)+")%20AND%20(date.created%3A%3E%3D"+tStartDate+"%20OR%20date.created%3A%3C"+tEndDate+")&limit="+tNumberOfEntries;
            }else{
                tEndDate = tDtf.format(tNow);
                tGeneralUrl = "https://api.reliefweb.int/v1/disasters?appname=apidocc&profile=list&preset=latest&slim=1&query[value]=("+tType.get(tSourceID-1)+")%20AND%20(date.created%3A%3E%3D2009-08-05%20OR%20date.created%3A%3C2021-08-13)&limit="+tNumberOfEntries;
            }
            Log.e("----------result ","Loader created" + tGeneralUrl);
            ArrayList<String> tUrlArr = new ArrayList<>();
            tUrlArr.add(tGeneralUrl);
            return new EarthQuakeHttpClassLoader(this,tUrlArr);
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<ArrayList<EarthquakeEntries>>> tLoader, ArrayList<ArrayList<EarthquakeEntries>> tData) {
        Log.e("status","onLoadFinished Call");
        if (tProgressDialog != null && tProgressDialog.isShowing()) {
            tProgressDialog.dismiss();
        }
//            Log.e("Status - entries",""+data.get(0).size());

        if(tSourceID == 0){
            if(tData.size()>0){
                tQuakeEntriesList = tData.get(0);
            }
            Fragment tDroughtFragment = new EathquakeFragment();
            fireFragment(tDroughtFragment,false);
        }
        else if(tSourceID == 1){
            if(tData.size()>0){
                tDroughtentriesList = tData.get(0);
            }
            Fragment tExtratropicalCycloneFragment = new DroughtFragment();
            fireFragment(tExtratropicalCycloneFragment,false);
        }
        else if(tSourceID == 2){

            if(tData.size()>0){
                tFloodentriesList = tData.get(0);
            }
            Fragment tFloodFragment = new FloodFragment();
            fireFragment(tFloodFragment,false);
        }
        else if(tSourceID == 3){

            if(tData.size()>0){
                tLandslideentriesList = tData.get(0);
            }
            Fragment tLandSlideFragment = new LandslideFragment();
            fireFragment(tLandSlideFragment,false);
        }
        else if(tSourceID == 4){
            if(tData.size()>0){
                tCycloneentriesList = tData.get(0);
            }
            Fragment tTropicalCycloneFragment = new CycloneFragment();
            fireFragment(tTropicalCycloneFragment,false);
        }
        tLoader.reset();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<ArrayList<EarthquakeEntries>>> tLoader) {
        Log.e("status","loader reset");

    }
}