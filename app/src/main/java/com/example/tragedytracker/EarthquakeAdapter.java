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
    List<EarthquakeEntries> list;
    Context context;
    public EarthquakeAdapter(List<EarthquakeEntries> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public EarthquakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quake_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeAdapter.ViewHolder holder, int position) {
        EarthquakeEntries entry = list.get(position);
        double magnitude = entry.getMagnitude();
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        int magnitudeBackgroundColor =  ContextCompat.getColor(context, R.color.magnitude10plus);
        GradientDrawable magnitudeColor = (GradientDrawable) holder.quakemag.getBackground();
        switch((int)magnitude){
            case 1:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude1);
                break;
            case 2:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude2);
                break;
            case 3:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude3);
                break;
            case 4:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude4);
                break;
            case 5:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude5);
                break;
            case 6:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude6);
                break;
            case 7:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude7);
                break;
            case 8:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude8);
                break;
            case 9:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude9);
                break;
            case 10:
                magnitudeBackgroundColor = ContextCompat.getColor(context, R.color.magnitude10plus);
                break;
        }
        magnitudeColor.setColor(magnitudeBackgroundColor);
        holder.quakemag.setText(magnitudeFormat.format(entry.getMagnitude()));
        holder.quakeloc.setText(entry.getLocation());
        holder.quakerange.setText(entry.getRange());
        holder.quakedate.setText(""+entry.getDate());
        holder.quaketime.setText(entry.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView quakeloc,quakerange,quakedate,quaketime,quakemag;
        public LinearLayout quakewhole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quakeloc = itemView.findViewById(R.id.quakeLocation2);
            quakerange = itemView.findViewById(R.id.quakeLocation1);
            quakedate = itemView.findViewById(R.id.quakeDate);
            quaketime = itemView.findViewById(R.id.quakeTime);
            quakemag = itemView.findViewById(R.id.quakeMagnitude);
            quakewhole = itemView.findViewById(R.id.quake_front_list);
        }

        @Override
        public void onClick(View view) {
            EarthquakeEntries entry = list.get(getAdapterPosition());
            quakewhole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, entry.getUrl());
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
