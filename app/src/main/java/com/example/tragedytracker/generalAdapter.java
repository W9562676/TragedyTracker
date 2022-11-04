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

    List<EarthquakeEntries> list;
    Context context;
    public generalAdapter(List<EarthquakeEntries> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public generalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alllayout,parent,false);
        return new generalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView generalscore;
        public TextView generalname;
        public ImageView generalstatusColor;
        public TextView generalstatusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            generalscore = itemView.findViewById(R.id.quakeMagnitude);
            generalname = itemView.findViewById(R.id.quakeLocation1);
            generalstatusColor = itemView.findViewById(R.id.status);
            generalstatusText = itemView.findViewById(R.id.statusTextView);

        }

        @Override
        public void onClick(View v) {
//            generalEntry currentPos = root.get(currentPosition);

        }
        public void bind(EarthquakeEntries current){
            generalname.setText(current.getName());
            generalstatusText.setText(current.getStatus());

            double magnitude = current.getScore();

            int magnitudeBackgroundColor =  ContextCompat.getColor(context, R.color.magnitude10plus);
            GradientDrawable magnitudeColor = (GradientDrawable) generalscore.getBackground();

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
            generalscore.setText((""+current.getScore()).substring(0,3));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, current.getUrl());
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
