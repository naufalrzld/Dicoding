package com.naufalrzld.moviecatalogue;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.naufalrzld.moviecatalogue.db.MovieHelper;
import com.naufalrzld.moviecatalogue.model.MovieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.naufalrzld.moviecatalogue.db.DatabaseContract.CONTENT_URI;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.MOVIE_ID;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.POPULARITY;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.POSTER_PATH;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    public static final String EXTRA_MOVIE = "MOVIE";
    public static final String EXTRA_CURSOR = "isCursor";
    public static int REQUEST_DETAIL = 100;
    public static int RESULT_DELETE = 101;

    private MovieModel movieModel;
    private Uri uri;
    private boolean isCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.detail_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uri = getIntent().getData();
        isCursor = getIntent().getBooleanExtra(EXTRA_CURSOR, false);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (isCursor) {
                if(cursor.moveToFirst()) movieModel = new MovieModel(cursor);
                btnFav.setFavorite(true);
            } else {
                movieModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
                if (cursor.getCount() > 0) {
                    btnFav.setFavorite(true);
                }
            }
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
                if (favorite) {
                    ContentValues values = new ContentValues();
                    values.put(MOVIE_ID, movieModel.getMovieID());
                    values.put(TITLE, movieModel.getMovieName());
                    values.put(OVERVIEW, movieModel.getDescription());
                    values.put(POSTER_PATH, movieModel.getPosetPath());
                    values.put(RELEASE_DATE, movieModel.getReleaseDate());
                    values.put(POPULARITY, movieModel.getPopularity());

                    getContentResolver().insert(CONTENT_URI, values);
                    showSnackbarMessage(movieModel.getMovieName() + " ditambahkan sebagai favorit");
                } else {
                    if (isCursor) {
                        getContentResolver().delete(uri,null,null);
                        Intent i = getIntent();
                        i.putExtra("title", movieModel.getMovieName());
                        setResult(RESULT_DELETE, i);
                        finish();
                    } else {
                        getContentResolver().delete(uri,null,null);
                        showSnackbarMessage(movieModel.getMovieName() + " dihapus dari favorit");
                    }
                }
            }
        });
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(tvPopularity, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
