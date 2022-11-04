package com.example.tragedytracker;

import static com.example.tragedytracker.DashBoardActivity.sourceID;

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

    public static ArrayList<EarthquakeEntries> fetchEarthquakeData(String stringUrl) throws IOException, JSONException {
        URL url = generateUrl(stringUrl);
        String JsonResponse = "";
        try{
            JsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            return null;
        }
        ArrayList<EarthquakeEntries> entires = extractJsonFeatures(JsonResponse);
        return entires;
    }

    private static ArrayList<EarthquakeEntries> extractJsonFeatures(String SAMPLE_JSON_RESPONSE) throws JSONException {
        if(TextUtils.isEmpty(SAMPLE_JSON_RESPONSE)){
            return null;
        }
        if(sourceID == 0){
            ArrayList<EarthquakeEntries> earthQuakesInfo = new ArrayList<>();
            try {
                JSONObject mainRoot = new JSONObject(SAMPLE_JSON_RESPONSE);
                JSONArray feature = mainRoot.getJSONArray("features");
                String location;
                Double magnitude;
                long dateTimee;
                String webUrl;
                SimpleDateFormat dateFormatt = new SimpleDateFormat("MMM dd,yyyy");
                SimpleDateFormat timeFormatt = new SimpleDateFormat("HH:ss a");
                Date result;

                int len = feature.length();
                for (int i = 0; i < len; i++) {
                    JSONObject temp = feature.getJSONObject(i);
                    JSONObject properties = temp.getJSONObject("properties");
                    magnitude = properties.getDouble("mag");
                    location = properties.getString("place");
                    webUrl = properties.getString("url");
                    result = new Date(properties.getLong("time"));
                    JSONArray geometry = temp.getJSONObject("geometry").getJSONArray("coordinates");
                    try{
                        String[] locPart = location.split("of");
                        earthQuakesInfo.add(new EarthquakeEntries(magnitude,locPart[0]+"of",locPart[1],dateFormatt.format(result),timeFormatt.format(result),webUrl,(Double)geometry.get(0),(Double)geometry.get(1)));
                    }catch (ArrayIndexOutOfBoundsException ef){
                        earthQuakesInfo.add(new EarthquakeEntries(magnitude,"",location,dateFormatt.format(result),timeFormatt.format(result),webUrl,(Double)geometry.get(0),(Double)geometry.get(1)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("QueryUtils", "Promblem in parsing the earthQuake JSON results ", e);
            }
            return earthQuakesInfo;
        }else{
            ArrayList<EarthquakeEntries> report = new ArrayList<>();

            JSONObject mainRoot = new JSONObject(SAMPLE_JSON_RESPONSE);
            int noResponse = mainRoot.getInt("count");
            JSONArray feature = mainRoot.getJSONArray("data");
            JSONObject mainRoot2 ;
            JSONObject dateeee;
            JSONObject feildsOfReeds;
            String createdOn ;
            String dateCreated = null;
            String currenttime = null;
            String currname = null;
            String weburl = null;
            String currStatus = null;
            double score;
            System.out.println(noResponse);
            for(int i=0;i<noResponse;i++) {
                mainRoot2 = feature.getJSONObject(i);
                feildsOfReeds = mainRoot2.getJSONObject("fields");
                score = mainRoot2.getDouble("score");
                dateeee = feildsOfReeds.getJSONObject("date");
                createdOn = dateeee.getString("created");
                dateCreated = createdOn.substring(0,10);
                currenttime = createdOn.substring(11,19);
                currname = feildsOfReeds.getString("name");
                weburl = feildsOfReeds.getString("url");
                currStatus = feildsOfReeds.getString("status");
                report.add(new EarthquakeEntries(dateCreated,currenttime,currStatus,currname,weburl,score));
            };
            return report;
        }
    }

    private static URL generateUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e) {
            Log.e(LOG_TAG,"Error in creating the url "+e);
        }
        Log.e("current Status "," "+url);
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonReason = "";
        HttpURLConnection connectionRequest = null;
        InputStream MyInputStream = null;

        try{
            connectionRequest = (HttpURLConnection)url.openConnection();
            connectionRequest.setReadTimeout(10000);
            connectionRequest.setConnectTimeout(150000);
            connectionRequest.setRequestMethod("GET");
            connectionRequest.connect();
            if (connectionRequest.getResponseCode() == 200) {
                MyInputStream =connectionRequest.getInputStream();
                jsonReason = readTheStream(MyInputStream);
            } else {
            }
        }catch(IOException e){
        }finally{
            if(connectionRequest != null){
                connectionRequest.disconnect();
            }
            if(MyInputStream != null){
                MyInputStream.close();
            }
        }
        return jsonReason;
    }



    private static String readTheStream(InputStream response) throws IOException{
        StringBuilder currentJsonResponse = new StringBuilder();
        if(response == null){
            return currentJsonResponse.toString();
        }else{
            InputStreamReader streamReader = new InputStreamReader(response, Charset.forName("UTF-8"));
            BufferedReader reader =  new BufferedReader(streamReader);
            String lineResponse = reader.readLine();
            while(lineResponse != null){
                currentJsonResponse.append(lineResponse);
                lineResponse = reader.readLine();
            }
            return currentJsonResponse.toString();
        }
    }
}
