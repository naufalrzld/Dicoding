package com.naufalrzld.moviecatalogue;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.naufalrzld.moviecatalogue.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieModel>> {
    private ArrayList<MovieModel> movieList;
    private boolean mHasResult = false;

    private String movieName;
    private String type;

    public MyAsyncTaskLoader(Context context, String movieName, String type) {
        super(context);

        onContentChanged();
        this.movieName = movieName;
        this.type = type;
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
        movieList = data;
        mHasResult = true;
        super.deliverResult(data);
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
        String url;
        if (type.equals("search")) {
            url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                    BuildConfig.API_KEY + "&language=en-US&query=" + movieName;
        } else {
            url = "https://api.themoviedb.org/3/movie/" + type + "?api_key=" +
                    BuildConfig.API_KEY + "&language=en-US";
        }

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

            }
        });

        return movieItems;
    }
}
