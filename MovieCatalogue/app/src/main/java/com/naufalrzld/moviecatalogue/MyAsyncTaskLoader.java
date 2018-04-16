package com.naufalrzld.moviecatalogue;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.naufalrzld.moviecatalogue.Model.MovieModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieModel>> {
    private static final String API_KEY = "1157aaf4cd28105165a7f5f43b853aae";

    private ArrayList<MovieModel> movieList;
    private boolean mHasResult = false;

    private String movieName;

    public MyAsyncTaskLoader(Context context, String movieName) {
        super(context);

        onContentChanged();
        this.movieName = movieName;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(movieList);
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieModel> data) {
        super.deliverResult(data);
        movieList = data;
        mHasResult = true;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            movieList = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieModel> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieModel> movieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                API_KEY + "&language=en-US&query=" + movieName;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray list = jsonResponse.getJSONArray("results");

                    for (int i=0; i<list.length(); i++) {
                        JSONObject movieObject = list.getJSONObject(i);
                        MovieModel movieModel = new MovieModel(movieObject);
                        movieItems.add(movieModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

        return movieItems;
    }
}
