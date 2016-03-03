package com.cineflick.developer.harry.parser;

import com.cineflick.developer.harry.data.model.MovieDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harry on 3/2/16.
 */
public class JSONParser {
    private String mJSONString;
    private JSONObject mJSONObject;
    private List<MovieDataModel> mListMovies;


    public JSONParser(String JSONString){
        this.mJSONString = JSONString;
        mListMovies = new ArrayList<>();
    }

    public int parseJSON() throws JSONException{
        mJSONObject = new JSONObject(mJSONString);
        JSONArray jsonArray = mJSONObject.getJSONArray("results");
        for(int i=0;i <jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MovieDataModel movieDataModel = new MovieDataModel();
            movieDataModel.setPosterPath(jsonObject.getString("poster_path"));
            movieDataModel.setAdult(jsonObject.getBoolean("adult"));
            movieDataModel.setOverview(jsonObject.getString("overview"));
            movieDataModel.setReleaseDate(jsonObject.getString("release_date"));
            movieDataModel.setOriginalTitle(jsonObject.getString("original_title"));
            movieDataModel.setOriginalLanguage(jsonObject.getString("original_language"));
            movieDataModel.setTitle(jsonObject.getString("title"));
            movieDataModel.setPopularity(jsonObject.getDouble("popularity"));
            movieDataModel.setVoteCount(jsonObject.getInt("vote_count"));
            movieDataModel.setVideo(jsonObject.getBoolean("video"));
            movieDataModel.setVoteAverage(jsonObject.getDouble("vote_average"));
            mListMovies.add(movieDataModel);
        }
        return mListMovies.size();
    }



}
