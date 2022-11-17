package com.example.tragedytracker;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    List<EarthquakeEntries> tList;
    Context tContext;
    public EarthquakeAdapter(List<EarthquakeEntries> tList, Context tContext) {
        this.tList = tList;
        this.tContext = tContext;
    }



    @NonNull
    @Override
    public EarthquakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quake_list,parent,false);
        return new ViewHolder(tView);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeAdapter.ViewHolder holder, int position) {
        EarthquakeEntries tEntry = tList.get(position);
        double tMagnitude = tEntry.getMagnitude();
        DecimalFormat tMagnitudeFormat = new DecimalFormat("0.0");
        int tMagnitudeBackgroundColor =  ContextCompat.getColor(tContext, R.color.magnitude10plus);
        GradientDrawable tMagnitudeColor = (GradientDrawable) holder.tQuakemag.getBackground();
        switch((int)tMagnitude){
            case 1:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude1);
                break;
            case 2:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude2);
                break;
            case 3:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude3);
                break;
            case 4:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude4);
                break;
            case 5:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude5);
                break;
            case 6:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude6);
                break;
            case 7:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude7);
                break;
            case 8:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude8);
                break;
            case 9:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude9);
                break;
            case 10:
                tMagnitudeBackgroundColor = ContextCompat.getColor(tContext, R.color.magnitude10plus);
                break;
        }
        tMagnitudeColor.setColor(tMagnitudeBackgroundColor);
        holder.tQuakemag.setText(tMagnitudeFormat.format(tEntry.getMagnitude()));
        holder.tQuakeloc.setText(tEntry.getLocation());
        holder.tQuakerange.setText(tEntry.getRange());
        holder.tQuakedate.setText(""+tEntry.getDate());
        holder.tQuaketime.setText(tEntry.getTime());

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tQuakeloc,tQuakerange,tQuakedate,tQuaketime,tQuakemag;
        public LinearLayout tQuakewhole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tQuakeloc = itemView.findViewById(R.id.quakeLocation2);
            tQuakerange = itemView.findViewById(R.id.quakeLocation1);
            tQuakedate = itemView.findViewById(R.id.quakeDate);
            tQuaketime = itemView.findViewById(R.id.quakeTime);
            tQuakemag = itemView.findViewById(R.id.quakeMagnitude);
            tQuakewhole = itemView.findViewById(R.id.quake_front_list);
        }

        @Override
        public void onClick(View view) {
            EarthquakeEntries tEntry = tList.get(getAdapterPosition());
            tQuakewhole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                    tIntent.putExtra(SearchManager.QUERY, tEntry.getUrl());
                    if (tIntent.resolveActivity(tContext.getPackageManager()) != null) {
                        tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        tContext.startActivity(tIntent);
                    }
                }
            });
        }
    }
}
