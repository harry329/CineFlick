package com.cineflick.developer.harry.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.settings.SettingsActivity;
import com.cineflick.developer.harry.utils.AppConstants;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailedMovieInfoFragment extends Fragment {
    private static final String TAG= DetailedMovieInfoFragment.class.getSimpleName();
    private TextView mMovieName;
    private TextView mReleaseDate;
    private TextView mRatings;
    private ImageView mMoviePoster;
    private TextView mDescription;

    public DetailedMovieInfoFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_movie_info, container, false);
        mMovieName = (TextView)view.findViewById(R.id.movieName);
        mReleaseDate = (TextView)view.findViewById(R.id.releaseDate);
        mRatings = (TextView)view.findViewById(R.id.ratings);
        mDescription = (TextView)view.findViewById(R.id.descriptions);
        mMoviePoster =(ImageView)view.findViewById(R.id.imagePoster);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle intentBundle =getArguments();
        if(intentBundle != null){
            mMovieName.setText(intentBundle.getString(AppConstants.KEY_MOVIE_NAME));
            mReleaseDate.setText(intentBundle.getString(AppConstants.KEY_RELEASE_DATE));
            mRatings.setText(intentBundle.getString(AppConstants.KEY_AVERAGE_RATINGS));
            mDescription.setText(intentBundle.getString(AppConstants.KEY_MOVIE_DESC));
            Picasso.with(getActivity()).load(AppConstants.BASE_URL +AppConstants.IMAGE_SIZE_342 +intentBundle.getString(AppConstants.KEY_MOVIE_POSTER)).into(mMoviePoster);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detailed_movie_info, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareActionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (shareActionProvider != null ) {
                            shareActionProvider.setShareIntent(new Intent());
                      } else {


                      }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

























