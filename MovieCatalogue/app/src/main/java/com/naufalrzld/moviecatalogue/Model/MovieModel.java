package com.naufalrzld.moviecatalogue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MovieModel implements Parcelable {
    private int movieID;
    private String title;
    private String description;
    private String posterPath;
    private String releaseDate;

    public MovieModel(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("overview");
            String posterPath = jsonObject.getString("poster_path");
            String releaseDate = jsonObject.getString("release_date");

            this.movieID = id;
            this.title = title;
            this.description = description;
            this.posterPath = posterPath;
            this.releaseDate = releaseDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return title;
    }

    public void setMovieName(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosetPath() {
        return posterPath;
    }

    public void setPosetPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieID);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
    }

    protected MovieModel(Parcel in) {
        this.movieID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
