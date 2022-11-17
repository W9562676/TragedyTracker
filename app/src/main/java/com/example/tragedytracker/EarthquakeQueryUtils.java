package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.tSourceID;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeQueryUtils {
    public static final String LOG_TAG = "";

    private EarthquakeQueryUtils() {
    }

    public static ArrayList<EarthquakeEntries> fetchEarthquakeData(String tStringUrl) throws IOException, JSONException {
        URL tUrl = generateUrl(tStringUrl);
        String JsonResponse = "";
        try{
            JsonResponse = makeHttpRequest(tUrl);
        }catch (IOException e){
            return null;
        }
        ArrayList<EarthquakeEntries> tEntires = extractJsonFeatures(JsonResponse);
        return tEntires;
    }

    private static ArrayList<EarthquakeEntries> extractJsonFeatures(String SAMPLE_JSON_RESPONSE) throws JSONException {
        if(TextUtils.isEmpty(SAMPLE_JSON_RESPONSE)){
            return null;
        }
        if(tSourceID == 0){
            ArrayList<EarthquakeEntries> tEarthQuakesInfo = new ArrayList<>();
            try {
                JSONObject tMainRoot = new JSONObject(SAMPLE_JSON_RESPONSE);
                JSONArray tFeature = tMainRoot.getJSONArray("features");
                String tLocation;
                Double tMagnitude;
                long dateTimee;
                String tWebUrl;
                SimpleDateFormat tDateFormatt = new SimpleDateFormat("MMM dd,yyyy");
                SimpleDateFormat tTimeFormatt = new SimpleDateFormat("HH:ss a");
                Date tResult;

                int tLen = tFeature.length();
                for (int j = 0; j < tLen; j++) {
                    JSONObject tTemp = tFeature.getJSONObject(j);
                    JSONObject tProperties = tTemp.getJSONObject("properties");
                    tMagnitude = tProperties.getDouble("mag");
                    tLocation = tProperties.getString("place");
                    tWebUrl = tProperties.getString("url");
                    tResult = new Date(tProperties.getLong("time"));
                    JSONArray tGeometry = tTemp.getJSONObject("geometry").getJSONArray("coordinates");
                    try{
                        String[] tLocPart = tLocation.split("of");
                        tEarthQuakesInfo.add(new EarthquakeEntries(tMagnitude,tLocPart[0]+"of",tLocPart[1],tDateFormatt.format(tResult),tTimeFormatt.format(tResult),tWebUrl,(Double)tGeometry.get(0),(Double)tGeometry.get(1)));
                    }catch (ArrayIndexOutOfBoundsException ef){
                        tEarthQuakesInfo.add(new EarthquakeEntries(tMagnitude,"",tLocation,tDateFormatt.format(tResult),tTimeFormatt.format(tResult),tWebUrl,(Double)tGeometry.get(0),(Double)tGeometry.get(1)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("QueryUtils", "Promblem in parsing the earthQuake JSON results ", e);
            }
            return tEarthQuakesInfo;
        }else{
            ArrayList<EarthquakeEntries> tReport = new ArrayList<>();

            JSONObject tMainRoot = new JSONObject(SAMPLE_JSON_RESPONSE);
            int tNoResponse = tMainRoot.getInt("count");
            JSONArray tFeature = tMainRoot.getJSONArray("data");
            JSONObject tMainRoot2 ;
            JSONObject tDateeee;
            JSONObject tFeildsOfReeds;
            String tCreatedOn ;
            String tDateCreated = null;
            String tCurrenttime = null;
            String tCurrname = null;
            String tWeburl = null;
            String tCurrStatus = null;
            double tScore;
            System.out.println(tNoResponse);
            for(int i=0;i<tNoResponse;i++) {
                tMainRoot2 = tFeature.getJSONObject(i);
                tFeildsOfReeds = tMainRoot2.getJSONObject("fields");
                tScore = tMainRoot2.getDouble("score");
                tDateeee = tFeildsOfReeds.getJSONObject("date");
                tCreatedOn = tDateeee.getString("created");
                tDateCreated = tCreatedOn.substring(0,10);
                tCurrenttime = tCreatedOn.substring(11,19);
                tCurrname = tFeildsOfReeds.getString("name");
                tWeburl = tFeildsOfReeds.getString("url");
                tCurrStatus = tFeildsOfReeds.getString("status");
                tReport.add(new EarthquakeEntries(tDateCreated,tCurrenttime,tCurrStatus,tCurrname,tWeburl,tScore));
            };
            return tReport;
        }
    }

    private static URL generateUrl(String tStringUrl){
        URL tUrl = null;
        try{
            tUrl = new URL(tStringUrl);
        }catch(MalformedURLException e) {
            Log.e(LOG_TAG,"Error in creating the url "+e);
        }
        Log.e("current Status "," "+tUrl);
        return tUrl;
    }

    private static String makeHttpRequest(URL tUrl) throws IOException {
        String tJsonReason = "";
        HttpURLConnection tConnectionRequest = null;
        InputStream tMyInputStream = null;

        try{
            tConnectionRequest = (HttpURLConnection)tUrl.openConnection();
            tConnectionRequest.setReadTimeout(10000);
            tConnectionRequest.setConnectTimeout(150000);
            tConnectionRequest.setRequestMethod("GET");
            tConnectionRequest.connect();
            if (tConnectionRequest.getResponseCode() == 200) {
                tMyInputStream =tConnectionRequest.getInputStream();
                tJsonReason = readTheStream(tMyInputStream);
            } else {
            }
        }catch(IOException e){
        }finally{
            if(tConnectionRequest != null){
                tConnectionRequest.disconnect();
            }
            if(tMyInputStream != null){
                tMyInputStream.close();
            }
        }
        return tJsonReason;
    }



    private static String readTheStream(InputStream tResponse) throws IOException{
        StringBuilder tCurrentJsonResponse = new StringBuilder();
        if(tResponse == null){
            return tCurrentJsonResponse.toString();
        }else{
            InputStreamReader tStreamReader = new InputStreamReader(tResponse, Charset.forName("UTF-8"));
            BufferedReader tReader =  new BufferedReader(tStreamReader);
            String tLineResponse = tReader.readLine();
            while(tLineResponse != null){
                tCurrentJsonResponse.append(tLineResponse);
                tLineResponse = tReader.readLine();
            }
            return tCurrentJsonResponse.toString();
        }
    }
}
