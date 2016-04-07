package com.cineflick.developer.harry.database;

import android.provider.BaseColumns;

import com.cineflick.developer.harry.data.model.MovieDataModel;

/**
 * Created by harry on 4/4/16.
 */
public class MovieEntryContract {

    public static final String TEXT_TYPE =" TEXT NOT NULL";
    public static final String INT_TYPE=" INTEGER NOT NULL";
    public static final String REAL_TYPE=" REAL NOT NULL";
    public static final String COMMA_SEP=",";

    public MovieEntryContract() {
    }

    public static abstract class MovieEntry implements BaseColumns {
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
    }
}
