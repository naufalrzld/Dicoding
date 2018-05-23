package com.naufalrzld.favmoviecatalogue;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.naufalrzld.favmoviecatalogue.model.MovieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.CONTENT_URI;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.MOVIE_ID;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.POPULARITY;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.POSTER_PATH;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.img_poster)
    ImageView imgPoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_movie_desc)
    TextView tvOverview;
    @BindView(R.id.tv_movie_popular)
    TextView tvPopularity;
    @BindView(R.id.fav_button)
    MaterialFavoriteButton btnFav;

    public static int REQUEST_DETAIL = 100;
    public static int RESULT_DELETE = 101;

    private MovieModel movieModel;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(R.string.detail_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uri = getIntent().getData();

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if(cursor.moveToFirst()) movieModel = new MovieModel(cursor);
            btnFav.setFavorite(true);
            cursor.close();
        }

        String img_url = "http://image.tmdb.org/t/p/original" + movieModel.getPosetPath();
        Glide.with(this)
                .load(img_url)
                .override(250, Target.SIZE_ORIGINAL)
                .into(imgPoster);
        tvMovieTitle.setText(movieModel.getMovieName());
        tvReleaseDate.setText(movieModel.getReleaseDate());
        tvOverview.setText(movieModel.getDescription());
        tvPopularity.setText(String.valueOf(movieModel.getPopularity()));

        btnFav.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (!favorite) {
                    getContentResolver().delete(uri,null,null);
                    Intent i = getIntent();
                    i.putExtra("title", movieModel.getMovieName());
                    setResult(RESULT_DELETE, i);
                    finish();
                }
            }
        });
    }
}
