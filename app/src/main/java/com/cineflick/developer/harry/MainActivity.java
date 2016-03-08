package com.cineflick.developer.harry;

import android.content.Context;
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

import com.cineflick.developer.harry.parser.JSONParser;
import com.cineflick.developer.harry.utils.AppConstants;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(isNetworkAvailable()){
            //Start background Thread
            new MovieQuerryClass().execute(getResources().getString(R.string.popularity));


        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                JSONParser jsonParser = new JSONParser(movieJsonStr);

                System.out.println("Length of ArrayList is " + jsonParser.parseJSON());
                Log.v(TAG,movieJsonStr);
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

    }
}
