package com.naufalrzld.moviecatalogue;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.naufalrzld.moviecatalogue.Adapter.MovieAdapter;
import com.naufalrzld.moviecatalogue.Model.MovieModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<MovieModel>>, AdapterView.OnItemClickListener {
    private EditText etSearch;
    private Button btnSearch;
    private ListView lvMovie;

    private ProgressBar progressBar;

    private MovieAdapter adapter;

    private static final String EXTRA_MOVIE_NAME = "MOVIE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        lvMovie = (ListView) findViewById(R.id.lv_movie);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        lvMovie.setAdapter(adapter);
        lvMovie.setOnItemClickListener(this);

        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String movieName = etSearch.getText().toString();

        if (TextUtils.isEmpty(movieName)) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE_NAME, movieName);
        getLoaderManager().restartLoader(0, bundle, MainActivity.this);
    }

    @Override
    public Loader<ArrayList<MovieModel>> onCreateLoader(int id, Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);
        lvMovie.setVisibility(View.GONE);
        String movieName = "";
        if (bundle != null) {
            movieName = bundle.getString(EXTRA_MOVIE_NAME);
        }
        return new MyAsyncTaskLoader(this, movieName);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieModel>> loader, ArrayList<MovieModel> movieModels) {
        adapter.setData(movieModels);
        progressBar.setVisibility(View.GONE);
        lvMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieModel>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        MovieModel movieModel = adapter.getItem(position);
        Intent i = new Intent(this, DetailMovieActivity.class);
        i.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieModel);
        startActivity(i);
    }
}
