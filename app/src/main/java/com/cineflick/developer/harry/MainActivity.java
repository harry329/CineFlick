package com.cineflick.developer.harry;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cineflick.developer.harry.adapter.MovieAdapter;
import com.cineflick.developer.harry.data.model.MovieDataModel;
import com.cineflick.developer.harry.parser.JSONParser;
import com.cineflick.developer.harry.utils.AppConstants;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<MovieDataModel> mArrayList;
    private GridView mGridView;
    private static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable()){
            //Start background Thread
            new MovieQuerryClass().execute(getResources().getString(R.string.popularity));

        }
        mGridView = (GridView)findViewById(R.id.gridview);
    }

    private boolean isNetworkAvailable(){
        //Check Network state
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() != false){
            Log.d(TAG,getResources().getString(R.string.network_availbale));
            return true;
        }else{
            Log.d(TAG,getResources().getString(R.string.network_not_availbale));
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayMovies(){
        if(mArrayList != null) {
            Log.d(TAG,"array list is not null");
            Log.d(TAG, "array list is not null and its size is " + mArrayList.size());
            final MovieAdapter movieAdapter = new MovieAdapter(this, mArrayList);
            mGridView.setAdapter(movieAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                MovieDataModel movieDataModel;

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    movieDataModel = mArrayList.get(position);
                    Intent intent = new Intent(getApplicationContext(),DetailedMovieInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.KEY_MOVIE_NAME,movieDataModel.getOriginalTitle());
                    bundle.putString(AppConstants.KEY_MOVIE_DESC,movieDataModel.getOverview());
                    bundle.putString(AppConstants.KEY_MOVIE_POSTER,movieDataModel.getPosterPath());
                    bundle.putString(AppConstants.KEY_AVERAGE_RATINGS, ((Double) movieDataModel.getPopularity()).toString());
                    bundle.putString(AppConstants.KEY_RELEASE_DATE,movieDataModel.getReleaseDate());
                    intent.putExtra(AppConstants.EXTRA_MOVIE_INFO,bundle);
                    startActivity(intent);
                }
            });
        }else{
            Log.d(TAG,"array list is null");
        }

    }

    private class MovieQuerryClass extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {
            String selectionParam =AppConstants.POPULARITY_DESC;
            if(params[0].equals(getResources().getString(R.string.rating))){
                selectionParam = AppConstants.RATING_DESC;
            }
            String urlWithAppKey = AppConstants.URL+selectionParam+AppConstants.API_KEY;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;
            JSONParser jsonParser =null;
            try{
                URL url = new URL(urlWithAppKey);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream =urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    Log.e(TAG,getResources().getString(R.string.input_stream_null));
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line +"\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
                jsonParser = new JSONParser(movieJsonStr);

                Log.d(TAG, movieJsonStr);
                mArrayList = jsonParser.parseJSON();
            } catch (IOException e) {
                Log.e(TAG, getResources().getString(R.string.error), e);
                return null;
            } catch(JSONException e){
                Log.e(TAG, getResources().getString(R.string.error), e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, getResources().getString(R.string.error_closing_stream), e);
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(Void result){
            Log.d(TAG, "In post execute");
                displayMovies();
        }
    }
}
