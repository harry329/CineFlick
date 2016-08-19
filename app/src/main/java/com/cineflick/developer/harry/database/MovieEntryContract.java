package com.cineflick.developer.harry.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by harry on 4/4/16.
 */
public class MovieEntryContract {

    public static final String CONTENT_AUTHORITY = "com.cineflick.developer.harry.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movieTable";

    public static final String TEXT_TYPE = " TEXT NOT NULL";
    public static final String INT_TYPE = " INTEGER NOT NULL";
    public static final String REAL_TYPE = " REAL NOT NULL";
    public static final String COMMA_SEP = ",";

    public MovieEntryContract() {
    }

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE=
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE=
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "movieTable";
        public static final String POSTER_PATH = "posterPath";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "releaseDate";
        public static final String ORIGINAL_TITLE = "originalTitle";
        public static final String ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String TITLE = "title";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "voteCount";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "voteAverage";
        public static final String FAVORITE= "favorite";

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

    }
}
