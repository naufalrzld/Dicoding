package com.naufalrzld.moviecatalogue.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naufalrzld.moviecatalogue.R;
import com.naufalrzld.moviecatalogue.adapter.MovieAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.naufalrzld.moviecatalogue.DetailMovieActivity.REQUEST_DETAIL;
import static com.naufalrzld.moviecatalogue.DetailMovieActivity.RESULT_DELETE;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    @BindView(R.id.rvList)
    RecyclerView rvMovie;

    private Cursor movieList;
    private MovieAdapter adapter;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, v);

        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MovieAdapter(getContext(), true);
        adapter.setCursorData(movieList);
        rvMovie.setAdapter(adapter);

        new LoadMovieAsync().execute();

        return v;
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);

            movieList = movies;
            adapter.setCursorData(movieList);
            adapter.notifyDataSetChanged();

            if (movieList.getCount() == 0){
                showSnackbarMessage("Tidak ada film favorit");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL) {
            if (resultCode == RESULT_DELETE) {
                String movieTitle = data.getStringExtra("title");
                new LoadMovieAsync().execute();
                showSnackbarMessage(movieTitle + " dihapus dari favorit");
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvMovie, message, Snackbar.LENGTH_LONG).show();
    }
}
