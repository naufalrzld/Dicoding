package com.naufalrzld.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.naufalrzld.moviecatalogue.Model.MovieModel;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "MOVIE";

    private ImageView imgPoster;
    private TextView tvMovieTitle, tvReleaseDate, tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        imgPoster = (ImageView) findViewById(R.id.img_poster);
        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOverview = (TextView) findViewById(R.id.tv_movie_desc);

        MovieModel movieModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String img_url = "http://image.tmdb.org/t/p/original" + movieModel.getPosetPath();
        Glide.with(this)
                .load(img_url)
                .override(250, Target.SIZE_ORIGINAL)
                .into(imgPoster);
        tvMovieTitle.setText(movieModel.getMovieName());
        tvReleaseDate.setText(movieModel.getReleaseDate());
        tvOverview.setText(movieModel.getDescription());
    }
}
