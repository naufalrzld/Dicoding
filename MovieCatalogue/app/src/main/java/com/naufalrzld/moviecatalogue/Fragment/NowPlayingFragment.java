package com.naufalrzld.moviecatalogue.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.naufalrzld.moviecatalogue.Adapter.MovieAdapter;
import com.naufalrzld.moviecatalogue.Model.MovieModel;
import com.naufalrzld.moviecatalogue.MyAsyncTaskLoader;
import com.naufalrzld.moviecatalogue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieModel>> {
    @BindView(R.id.rvList)
    RecyclerView rvMovie;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MovieAdapter adapter;

    private static final String EXTRA_MOVIE_NAME = "MOVIE_NAME";
    private static final String EXTRA_NOW_PLAYING = "now_playing";

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, v);

        adapter = new MovieAdapter(getContext());
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(adapter);

        getLoaderManager().initLoader(0, null,this);

        return v;
    }

    @Override
    public Loader<ArrayList<MovieModel>> onCreateLoader(int i, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        rvMovie.setVisibility(View.GONE);
        String movieName = "";
        if (args != null) {
            movieName = args.getString(EXTRA_MOVIE_NAME);
        }
        return new MyAsyncTaskLoader(getContext(), movieName, EXTRA_NOW_PLAYING);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieModel>> loader, ArrayList<MovieModel> movieModels) {
        adapter.setData(movieModels);
        progressBar.setVisibility(View.GONE);
        rvMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieModel>> loader) {
        adapter.setData(null);
    }
}
