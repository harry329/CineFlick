package com.cineflick.developer.harry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cineflick.developer.harry.data.model.MovieDataModel;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by harry on 4/5/16.
 */
public class MovieDataBaseHelper {
    Context mContext;
    MovieDataBase mMovieDataBase;
    SQLiteDatabase mMovieSQLiteDatabase;
    ArrayList<MovieDataModel> mMovieList;

    public MovieDataBaseHelper(Context context){
        mContext = context;
        mMovieDataBase = new MovieDataBase(mContext);
        mMovieSQLiteDatabase = mMovieDataBase.getWritableDatabase();
        mMovieList = new ArrayList<>();
    }

    public void insertMovies(ArrayList<MovieDataModel> arrayList){
        if(arrayList !=null){
            for(int i= 0; i<arrayList.size();i++){
                MovieDataModel movieDataModel = arrayList.get(i);
                ContentValues values = new ContentValues();
                values.put(MovieEntryContract.MovieEntry.TITLE,movieDataModel.getTitle());
                values.put(MovieEntryContract.MovieEntry.ORIGINAL_LANGUAGE,movieDataModel.getOriginalLanguage());
                values.put(MovieEntryContract.MovieEntry.ORIGINAL_TITLE,movieDataModel.getOriginalTitle());
                values.put(MovieEntryContract.MovieEntry.ADULT,movieDataModel.getAdult());
                values.put(MovieEntryContract.MovieEntry.OVERVIEW,movieDataModel.getOverview());
                values.put(MovieEntryContract.MovieEntry.POSTER_PATH,movieDataModel.getPosterPath());
                values.put(MovieEntryContract.MovieEntry.RELEASE_DATE,movieDataModel.getReleaseDate());
                values.put(MovieEntryContract.MovieEntry.VIDEO,movieDataModel.getVideo());
                values.put(MovieEntryContract.MovieEntry.VOTE_COUNT,movieDataModel.getVoteCount());
                values.put(MovieEntryContract.MovieEntry.POPULARITY,movieDataModel.getPopularity());
                values.put(MovieEntryContract.MovieEntry.VOTE_AVERAGE,movieDataModel.getVoteAverage());
                mMovieSQLiteDatabase.insert(MovieEntryContract.MovieEntry.TABLE_NAME,null,values);
            }
        }
    }
    
    public ArrayList<MovieDataModel> getMovieList(){
        Cursor c = mMovieSQLiteDatabase.query(
                MovieEntryContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        for(int count =1;count<=c.getCount();count++){
            MovieDataModel movieData = new MovieDataModel();
            movieData.setTitle(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.TITLE)));
            movieData.setVoteCount(c.getInt(c.getColumnIndex(MovieEntryContract.MovieEntry.VOTE_COUNT)));
            movieData.setPopularity(c.getDouble(c.getColumnIndex(MovieEntryContract.MovieEntry.POPULARITY)));
            movieData.setVoteAverage(c.getDouble(c.getColumnIndex(MovieEntryContract.MovieEntry.VOTE_AVERAGE)));
            movieData.setAdult(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.ADULT)));
            movieData.setOriginalLanguage(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.ORIGINAL_LANGUAGE)));
            movieData.setOriginalTitle(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.ORIGINAL_TITLE)));
            movieData.setOverview(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.OVERVIEW)));
            movieData.setPosterPath(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.POSTER_PATH)));
            movieData.setReleaseDate(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.RELEASE_DATE)));
            movieData.setVideo(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.VIDEO)));
            mMovieList.add(movieData);
            count++;
            c.moveToNext();
        }
        
        return mMovieList;
    }
}
