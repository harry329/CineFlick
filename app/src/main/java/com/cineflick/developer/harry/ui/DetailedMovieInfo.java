package com.cineflick.developer.harry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.settings.SettingsActivity;
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
            Log.d(TAG, getString(R.string.bundle_not_null_detail_activity));
            DetailedMovieInfoFragment detailedMovieInfoFragment = new DetailedMovieInfoFragment();
            detailedMovieInfoFragment.setArguments(intentBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, detailedMovieInfoFragment).commit();
        } else {
            Log.d(TAG, getString(R.string.bundle_null_detail_activity));
        }


    }

}