package com.cineflick.developer.harry.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harry on 4/4/16.
 */
public class MovieDataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movies.db";

    private static final String MOVIE_SQL_CREATE_TABLE = "CREATE TABLE " +
            MovieEntryContract.MovieEntry.TABLE_NAME + " (" +
            MovieEntryContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieEntryContract.MovieEntry.TITLE + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.ORIGINAL_LANGUAGE + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.ORIGINAL_TITLE + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.ADULT + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.OVERVIEW + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.POSTER_PATH + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.RELEASE_DATE + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.VIDEO + MovieEntryContract.TEXT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.VOTE_AVERAGE + MovieEntryContract.REAL_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.VOTE_COUNT + MovieEntryContract.INT_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.POPULARITY + MovieEntryContract.REAL_TYPE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.FAVORITE + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.REVIEW + MovieEntryContract.COMMA_SEP +
            MovieEntryContract.MovieEntry.VIDEO_ID +
            " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MovieEntryContract.MovieEntry.TABLE_NAME;

    public MovieDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
