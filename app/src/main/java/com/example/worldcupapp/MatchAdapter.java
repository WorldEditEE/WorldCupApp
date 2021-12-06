package com.example.worldcupapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.Month;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Match> matches;
    private Context context;
    private onMatchListener onMatchListener;


    public MatchAdapter(List<Match> list, Context context, onMatchListener onMatchListener){
        this.matches = list;
        this.context = context;
        this.onMatchListener = onMatchListener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item,parent,false);

        return new MatchViewHolder(view, onMatchListener);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {

        Match currentMatch = matches.get(position);
        String firstTeam = currentMatch.getFirstTeam();
        String secondTeam = currentMatch.getSecondTeam();
        String time = currentMatch.getTime();
        String stadium = currentMatch.getStadiumName();

        String urlToImage = currentMatch.getUrlImage();
        if(urlToImage != null && !urlToImage.isEmpty()){
            Picasso.get().load(urlToImage).into(holder.imageViewMatch);
        }else {
            Picasso.get().load("https://ic.wampi.ru/2021/12/07/matchtest.jpg").into(holder.imageViewMatch);
        }

        holder.textViewFirstTeam.setText(firstTeam);
        holder.textViewSecondTeam.setText(secondTeam);
        holder.textViewTimeMatch.setText(time);
        holder.textViewStadium.setText(stadium);

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageViewMatch;
        private TextView textViewFirstTeam;
        private TextView textViewSecondTeam;
        private TextView textViewTimeMatch;
        private TextView textViewStadium;
        private onMatchListener onMatchListener;


        public MatchViewHolder(@NonNull View itemView, onMatchListener onMatchListener) {
            super(itemView);
            imageViewMatch = itemView.findViewById(R.id.match_image);
            textViewFirstTeam = itemView.findViewById(R.id.textViewFirstTeam);
            textViewSecondTeam = itemView.findViewById(R.id.textViewSecondTeam);
            textViewTimeMatch = itemView.findViewById(R.id.timeMatchInfo);
            textViewStadium = itemView.findViewById(R.id.stadiumMatchInfo);
            this.onMatchListener = onMatchListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface onMatchListener{
        void onMatchClick(Match match);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setNews(List<Match> matches){
        this.matches = matches;
        notifyDataSetChanged();
    }

}
