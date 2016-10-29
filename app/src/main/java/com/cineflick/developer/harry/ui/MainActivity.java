package com.cineflick.developer.harry.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.settings.SettingsActivity;
import com.cineflick.developer.harry.utils.AppConstants;
import com.cineflick.developer.harry.utils.NetworkUtils;


public class MainActivity extends AppCompatActivity
        implements MainActivityMovieFragment.OnFragmentInteractionListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private boolean mDualPane;
    private Context mContext;
    private NetworkUtils mNetworkUtils;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create Called");
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        mContext = this;
        mNetworkUtils = new NetworkUtils();
        if (mNetworkUtils.isNetworkAvailable(mContext) && !mNetworkUtils.isWiFi()) {
            //TODO show Dialog
            WiFiConnectionAlertDialog wiFiConnectionAlertDialog = WiFiConnectionAlertDialog.newInstance();
            wiFiConnectionAlertDialog.show(getFragmentManager(), getString(R.string.wifi_dialog));
        } else {
            initView();
        }

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.fragment) != null) {
            mDualPane = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DetailedMovieInfoFragment()).commit();
        }
    }

    private void prefChanged() {
        MainActivityMovieFragment movieFragment = MainActivityMovieFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, movieFragment).commit();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
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
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i(TAG, "Positive click!");
        initView();
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i(TAG, "Negative click!");
        finish();
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {
        Log.d(TAG, "value of dualPane is !" + mDualPane);
        if (mDualPane) {
            DetailedMovieInfoFragment detailedMovieInfoFragment = new DetailedMovieInfoFragment();
            detailedMovieInfoFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, detailedMovieInfoFragment).commit();
        } else {
            Intent intent = new Intent(mContext, DetailedMovieInfo.class);
            intent.putExtra(AppConstants.EXTRA_MOVIE_INFO, bundle);
            startActivity(intent);
        }
    }

    public void moviesDownloadCallBack() {
        if (mDualPane) {
            MainActivityMovieFragment movieInfoFragment = (MainActivityMovieFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            if (movieInfoFragment != null) {
                Log.d(TAG, "Fragment is not null");
                onFragmentInteraction(movieInfoFragment.getMovieInfoBundle(0));
            } else {
                Log.d(TAG, "Fragment is null");
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_sync))) {
            Log.d(TAG, "I was in listener...");
            recreate();
        }
    }
}
