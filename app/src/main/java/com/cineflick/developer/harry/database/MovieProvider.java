package com.cineflick.developer.harry.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by harry on 4/16/16.
 */
public class MovieProvider extends ContentProvider {
    private static final String LOG_TAG = MovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDataBase mMovieHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieEntryContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieEntryContract.MovieEntry.TABLE_NAME, 100);
        matcher.addURI(authority, MovieEntryContract.MovieEntry.TABLE_NAME + "/#", 101);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMovieHelper = new MovieDataBase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)) {
            case 100:
                Log.d(LOG_TAG, "in querry method");
                retCursor = mMovieHelper.getWritableDatabase().query(
                        MovieEntryContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 100:
                return MovieEntryContract.MovieEntry.CONTENT_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri = null;
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case 100:
                long _id = db.insert(MovieEntryContract.MovieEntry.TABLE_NAME, null, values);
                Log.d(LOG_TAG, "in insert method and id is " + _id);
                if (_id > 0)
                    returnUri = MovieEntryContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case 101:

        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;
        Log.d(LOG_TAG, "in update method ");
        switch (sUriMatcher.match(uri)) {
            case 100:
                rowsUpdated = mMovieHelper.getWritableDatabase().update(MovieEntryContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                Log.d(LOG_TAG, "in update method " + rowsUpdated);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
