package com.cineflick.developer.harry.parser;

import com.cineflick.developer.harry.data.model.MovieDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by harry on 3/2/16.
 */
public class JSONParser {
    private String mJSONString;
    private JSONObject mJSONObject;
    private ArrayList<MovieDataModel> mListMovies;


    public JSONParser(String JSONString) {
        this.mJSONString = JSONString;
        mListMovies = new ArrayList<>();
    }

    public JSONParser(String JSONString,boolean videoOrURL) {
        this.mJSONString = JSONString;
    }
    public ArrayList<MovieDataModel> parseJSON() throws JSONException {
        mJSONObject = new JSONObject(mJSONString);
        JSONArray jsonArray = mJSONObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MovieDataModel movieDataModel = new MovieDataModel();
            movieDataModel.setPosterPath(jsonObject.getString("poster_path"));
            movieDataModel.setAdult(((Boolean) jsonObject.getBoolean("adult")).toString());
            movieDataModel.setOverview(jsonObject.getString("overview"));
            movieDataModel.setReleaseDate(jsonObject.getString("release_date"));
            movieDataModel.setMovieId(jsonObject.getInt("id"));
            movieDataModel.setOriginalTitle(jsonObject.getString("original_title"));
            movieDataModel.setOriginalLanguage(jsonObject.getString("original_language"));
            movieDataModel.setTitle(jsonObject.getString("title"));
            movieDataModel.setPopularity(jsonObject.getDouble("popularity"));
            movieDataModel.setVoteCount(jsonObject.getInt("vote_count"));
            movieDataModel.setVideo(((Boolean) jsonObject.getBoolean("video")).toString());
            movieDataModel.setVoteAverage(jsonObject.getDouble("vote_average"));
            mListMovies.add(movieDataModel);
        }
        return mListMovies;
    }


    public String parseJSONVideo() throws JSONException{
        mJSONObject = new JSONObject(mJSONString);
        String videoId = mJSONObject.getString("id");
//        JSONArray jsonArray = mJSONObject.getJSONArray("results");

/*        String videoId = null;
        if(jsonArray != null && jsonArray.length()>0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject != null) {
                videoId = jsonObject.getString("id");
            }
        }*/
        return videoId;
    }

    public String parseJSONReview() throws JSONException{
        mJSONObject = new JSONObject(mJSONString);
        JSONArray jsonArray = mJSONObject.getJSONArray("results");

        String review = null;
        if(jsonArray != null && jsonArray.length()>0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject != null) {
                review = jsonObject.getString("url");
            }
        }
        return review;
    }
}
