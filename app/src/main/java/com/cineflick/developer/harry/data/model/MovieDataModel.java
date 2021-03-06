package com.cineflick.developer.harry.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harry on 3/2/16.
 */
public class MovieDataModel implements Parcelable {
    public String getAdult() {
        return mAdult;
    }

    public void setAdult(String adult) {
        mAdult = adult;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        mVoteCount = voteCount;
    }

    public MovieDataModel() {
        super();
    }

    private String mPosterPath;
    private String mAdult;
    private String mOverview;
    private String mReleaseDate;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private double mPopularity;
    private int mVoteCount;
    private String mVideo;
    private double mVoteAverage;
    private String mVideoId;
    private String mReviewURL;
    private int mMovieId;


    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getReviewURL() {
        return mReviewURL;
    }

    public void setReviewURL(String mReviewURL) {
        this.mReviewURL = mReviewURL;
    }



    public MovieDataModel(Parcel input) {
        mPosterPath = input.readString();
        mAdult = input.readString();
        mVideo = input.readString();
        mOverview = input.readString();
        mReleaseDate = input.readString();
        mOriginalTitle = input.readString();
        mOriginalLanguage = input.readString();
        mTitle = input.readString();
        mPopularity = input.readDouble();
        mVoteCount = input.readInt();
        mVoteAverage = input.readDouble();
        mVideoId = input.readString();
        mReviewURL = input.readString();
        mMovieId = input.readInt();


    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mPosterPath);
        out.writeString(mAdult);
        out.writeString(mVideo);
        out.writeString(mOverview);
        out.writeString(mReleaseDate);
        out.writeString(mOriginalTitle);
        out.writeString(mOriginalLanguage);
        out.writeString(mTitle);
        out.writeDouble(mPopularity);
        out.writeInt(mVoteCount);
        out.writeDouble(mVoteAverage);
        out.writeString(mVideoId);
        out.writeString(mReviewURL);
        out.writeInt(mMovieId);
    }

    public static final Parcelable.Creator<MovieDataModel> CREATOR
            = new Parcelable.Creator<MovieDataModel>() {

        public MovieDataModel createFromParcel(Parcel in) {
            return new MovieDataModel(in);
        }

        public MovieDataModel[] newArray(int size) {
            return new MovieDataModel[size];
        }

    };
}
