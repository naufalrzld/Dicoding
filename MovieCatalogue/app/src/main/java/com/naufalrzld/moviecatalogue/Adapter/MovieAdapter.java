package com.naufalrzld.moviecatalogue.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.naufalrzld.moviecatalogue.Model.MovieModel;
import com.naufalrzld.moviecatalogue.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieModel> movieList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieModel> data) {
        movieList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }

    @Override
    public MovieModel getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.movie_item, null);
            holder.movieItem = (LinearLayout)view.findViewById(R.id.movie_items);
            holder.imgMoviePoseter = (ImageView)view.findViewById(R.id.movie_poster);
            holder.movieTitle = (TextView)view.findViewById(R.id.movie_title);
            holder.movieDesc = (TextView)view.findViewById(R.id.movie_desc);
            holder.movieReleaseDate = (TextView)view.findViewById(R.id.movie_release_date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String img_url = "http://image.tmdb.org/t/p/w154" + movieList.get(position).getPosetPath();

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

        holder.movieTitle.setText(movieList.get(position).getMovieName());
        holder.movieDesc.setText(movieList.get(position).getDescription());
        holder.movieReleaseDate.setText(releaseDate);
        return view;
    }

    private static class ViewHolder {
        LinearLayout movieItem;
        ImageView imgMoviePoseter;
        TextView movieTitle;
        TextView movieDesc;
        TextView movieReleaseDate;
    }
}
