package com.naufalrzld.favmoviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.MOVIE_ID;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.POPULARITY;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.POSTER_PATH;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.FavouriteColumns.TITLE;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.getColumnDouble;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.getColumnInt;
import static com.naufalrzld.favmoviecatalogue.db.DatabaseContract.getColumnString;

/**
 * Created by Naufal on 29/03/2018.
 */

public class MovieModel implements Parcelable {
    private int movieID;
    private String title;
    private String description;
    private String posterPath;
    private String releaseDate;
    private Double popularity;

    public MovieModel(Cursor cursor) {
        this.movieID = getColumnInt(cursor, MOVIE_ID);
        this.title = getColumnString(cursor, TITLE);
        this.description = getColumnString(cursor, OVERVIEW);
        this.posterPath = getColumnString(cursor, POSTER_PATH);
        this.releaseDate = getColumnString(cursor, RELEASE_DATE);
        this.popularity = getColumnDouble(cursor, POPULARITY);
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
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
        dest.writeDouble(this.popularity);
    }

    protected MovieModel(Parcel in) {
        this.movieID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.popularity = in.readDouble();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
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
