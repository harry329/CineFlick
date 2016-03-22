package com.cineflick.developer.harry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cineflick.developer.harry.utils.AppConstants;

public class DetailedMovieInfo extends AppCompatActivity {
    private static final String TAG = DetailedMovieInfo.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle intentBundle = intent.getBundleExtra(AppConstants.EXTRA_MOVIE_INFO);
        if (intentBundle != null) {
            Log.d(TAG, "intent bundle is not null in activity");
        } else {
            Log.d(TAG, "intent bundle is null in activity");
        }
        DetailedMovieInfoFragment detailedMovieInfoFragment = new DetailedMovieInfoFragment();
        detailedMovieInfoFragment.setArguments(intentBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, detailedMovieInfoFragment).commit();

    }
}