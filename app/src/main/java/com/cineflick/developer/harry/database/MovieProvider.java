package com.cineflick.developer.harry.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by harry on 4/16/16.
 */
public class MovieProvider extends ContentProvider {
    private static final String LOG_TAG = MovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDataBase mMovieHelper;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieEntryContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,MovieEntryContract.MovieEntry.TABLE_NAME,100);
        matcher.addURI(authority,MovieEntryContract.MovieEntry.TABLE_NAME +"/#",101);
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
        Cursor retCursor;
        switch(sUriMatcher.match(uri)) {
            case 100:
                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieEntryContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            case 101:
/*                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieEntryContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieEntryContract.MovieEntry._ID + " = ?",
                        new String[] (String.valueOf(ContentUris.parseId(uri))),
                    null,
                    null,
                    sortOrder);*/
                return null;
            default:
                throw new UnsupportedOperationException("Unknow uri: "+ uri);
        }
        
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
