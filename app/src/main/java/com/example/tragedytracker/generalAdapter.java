package com.example.tragedytracker;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class generalAdapter extends RecyclerView.Adapter<generalAdapter.ViewHolder> {

    List<EarthquakeEntries> tList;
    Context tContext;
    public generalAdapter(List<EarthquakeEntries> tList, Context tContext) {
        this.tList = tList;
        this.tContext = tContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alllayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int tPosition) {
        holder.bind(tList.get(tPosition));
    }


    @Override
    public int getItemCount() {
        return tList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tGeneralscore;
        public TextView tGeneralname;
        public ImageView tGeneralstatusColor;
        public TextView tGeneralstatusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tGeneralscore = itemView.findViewById(R.id.quakeMagnitude);
            tGeneralname = itemView.findViewById(R.id.quakeLocation1);
            tGeneralstatusColor = itemView.findViewById(R.id.status);
            tGeneralstatusText = itemView.findViewById(R.id.statusTextView);

        }

        @Override
        public void onClick(View v) {
//            generalEntry currentPos = root.get(currentPosition);

        }
        public void bind(EarthquakeEntries tCurrent){
            tGeneralname.setText(tCurrent.getName());
            tGeneralstatusText.setText(tCurrent.getStatus());

            double tMagnitude = tCurrent.getScore();

            int tMagnitudeBackgroundColor =  ContextCompat.getColor(tContext, R.color.magnitude10plus);
            GradientDrawable tMagnitudeColor = (GradientDrawable) tGeneralscore.getBackground();

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
            tGeneralscore.setText((""+tCurrent.getScore()).substring(0,3));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                    tIntent.putExtra(SearchManager.QUERY, tCurrent.getUrl());
                    if (tIntent.resolveActivity(tContext.getPackageManager()) != null) {
                        tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        tContext.startActivity(tIntent);
                    }
                }
            });
        }
    }
}
