package com.naufalrzld.moviecatalogue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.naufalrzld.moviecatalogue.DetailMovieActivity;
import com.naufalrzld.moviecatalogue.Model.MovieModel;
import com.naufalrzld.moviecatalogue.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<MovieModel> movieList;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieModel> data) {
        movieList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieModel movieModel = movieList.get(position);

        String img_url = "http://image.tmdb.org/t/p/w154" + movieModel.getPosetPath();

        Glide.with(context)
                .load(img_url)
                .override(150, Target.SIZE_ORIGINAL)
                .into(holder.imgMoviePoseter);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String releaseDate = "-";
        if (!movieList.get(position).getReleaseDate().equals("")) {
            Date date = null;
            try {
                date = dateFormat.parse(movieList.get(position).getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            releaseDate = sdf.format(date);
        }

        holder.movieTitle.setText(movieModel.getMovieName());
        holder.movieDesc.setText(movieModel.getDescription());
        holder.movieReleaseDate.setText(releaseDate);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieModel);
                context.startActivity(i);
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, context.getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView imgMoviePoseter;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_desc)
        TextView movieDesc;
        @BindView(R.id.movie_release_date)
        TextView movieReleaseDate;
        @BindView(R.id.btn_detail)
        Button btnDetail;
        @BindView(R.id.btn_share)
        Button btnShare;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
