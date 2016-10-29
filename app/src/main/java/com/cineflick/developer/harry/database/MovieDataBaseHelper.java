package com.cineflick.developer.harry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cineflick.developer.harry.data.model.MovieDataModel;
import com.cineflick.developer.harry.utils.AppConstants;

import java.util.ArrayList;

/**
 * Created by harry on 4/5/16.
 */
public class MovieDataBaseHelper {
    Context mContext;
    ArrayList<MovieDataModel> mMovieList;
    private static final String TAG = MovieDataBaseHelper.class.getSimpleName();


    public MovieDataBaseHelper(Context context) {
        mContext = context;
        mMovieList = new ArrayList<>();
    }

    public void insertMovies(ArrayList<MovieDataModel> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.d(TAG, "insert Movies" + i);
                MovieDataModel movieDataModel = arrayList.get(i);
                ContentValues values = new ContentValues();
                values.put(MovieEntryContract.MovieEntry.TITLE, movieDataModel.getTitle());
                values.put(MovieEntryContract.MovieEntry.ORIGINAL_LANGUAGE, movieDataModel.getOriginalLanguage());
                values.put(MovieEntryContract.MovieEntry.ORIGINAL_TITLE, movieDataModel.getOriginalTitle());
                values.put(MovieEntryContract.MovieEntry.ADULT, movieDataModel.getAdult());
                values.put(MovieEntryContract.MovieEntry.OVERVIEW, movieDataModel.getOverview());
                values.put(MovieEntryContract.MovieEntry.POSTER_PATH, movieDataModel.getPosterPath());
                values.put(MovieEntryContract.MovieEntry.RELEASE_DATE, movieDataModel.getReleaseDate());
                values.put(MovieEntryContract.MovieEntry.VIDEO, movieDataModel.getVideo());
                values.put(MovieEntryContract.MovieEntry.VOTE_COUNT, movieDataModel.getVoteCount());
                values.put(MovieEntryContract.MovieEntry.POPULARITY, movieDataModel.getPopularity());
                values.put(MovieEntryContract.MovieEntry.VOTE_AVERAGE, movieDataModel.getVoteAverage());
                values.put(MovieEntryContract.MovieEntry.REVIEW, movieDataModel.getReviewURL());
                values.put(MovieEntryContract.MovieEntry.VIDEO_ID, movieDataModel.getVideoId());
                String mSelectionClause = MovieEntryContract.MovieEntry.TITLE + " = ?";
                String[] mSelectionArgs = {movieDataModel.getTitle()};
                Cursor cursor = mContext.getContentResolver().query(MovieEntryContract.MovieEntry.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
                if (cursor.getCount() == 0) {
                    Log.d(TAG, "in inserthelperMethod " + movieDataModel.getTitle());
                    mContext.getContentResolver().insert(MovieEntryContract.MovieEntry.CONTENT_URI, values);
                }
            }
        }
    }

    public ArrayList<MovieDataModel> getMovieList(String fav) {
        Cursor c;
        String mSelectionClause = MovieEntryContract.MovieEntry.FAVORITE + " = ?";
        String[] mSelectionArgument = {AppConstants.YES};
        if (AppConstants.YES.equals(fav)) {
            c = mContext.getContentResolver().query(MovieEntryContract.MovieEntry.CONTENT_URI,
                    null,
                    mSelectionClause,
                    mSelectionArgument,
                    null
            );
        } else {
            c = mContext.getContentResolver().query(MovieEntryContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }
        c.moveToFirst();
        Log.d(TAG, "in query method and cursor count " + c.getCount());
        for (int count = 1; count <= c.getCount(); count++) {
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
            movieData.setReviewURL(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.REVIEW)));
            movieData.setVideoId(c.getString(c.getColumnIndex(MovieEntryContract.MovieEntry.VIDEO_ID)));
            mMovieList.add(movieData);
            Log.d(TAG, "in query method and counting " + count);
            c.moveToNext();

        }
        c.close();

        return mMovieList;
    }

    public void update(String movieName, String favValue) {
        ContentValues values = new ContentValues();
        String mSelectionClause = MovieEntryContract.MovieEntry.TITLE + " = ?";
        String[] mSelectionArgs = {movieName};
        Log.d(TAG, "movieName is " + movieName);
        values.put(MovieEntryContract.MovieEntry.FAVORITE, favValue);
        mContext.getContentResolver().update(MovieEntryContract.MovieEntry.CONTENT_URI,
                values,
                mSelectionClause,
                mSelectionArgs);

    }
}
