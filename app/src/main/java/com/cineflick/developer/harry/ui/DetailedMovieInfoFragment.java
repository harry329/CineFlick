package com.cineflick.developer.harry.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.database.MovieDataBaseHelper;
import com.cineflick.developer.harry.settings.SettingsActivity;
import com.cineflick.developer.harry.utils.AppConstants;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailedMovieInfoFragment extends Fragment {
    private static final String TAG = DetailedMovieInfoFragment.class.getSimpleName();
    private TextView mMovieName;
    private TextView mReleaseDate;
    private TextView mRatings;
    private ImageView mMoviePoster;
    private TextView mDescription;
    private ToggleButton mFavorites;
    private Button mVideoButton;
    private Button mReviewButton;
    private String mVideoId;
    private String mReviews;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private Context mContex;

    public DetailedMovieInfoFragment() {
        setHasOptionsMenu(true);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContex = context;
        mPrefs = mContex.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_movie_info, container, false);
        mMovieName = (TextView) view.findViewById(R.id.movieName);
        mReleaseDate = (TextView) view.findViewById(R.id.releaseDate);
        mRatings = (TextView) view.findViewById(R.id.ratings);
        mDescription = (TextView) view.findViewById(R.id.descriptions);
        mMoviePoster = (ImageView) view.findViewById(R.id.imagePoster);
        mFavorites = (ToggleButton) view.findViewById(R.id.toggleButton);
        mVideoButton = (Button) view.findViewById(R.id.trailer_button);
        mReviewButton = (Button) view.findViewById(R.id.review_button);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle intentBundle = getArguments();
        final String movieName;
        if (intentBundle != null) {
            movieName = intentBundle.getString(AppConstants.KEY_MOVIE_NAME);
            mVideoId = intentBundle.getString(AppConstants.KEY_VIDEO_ID);
            Log.d(TAG,"video id is " + mVideoId);
            mReviews = intentBundle.getString(AppConstants.KEY_REVIEW);
            mMovieName.setText(intentBundle.getString(AppConstants.KEY_MOVIE_NAME));
            mReleaseDate.setText(intentBundle.getString(AppConstants.KEY_RELEASE_DATE));
            mRatings.setText(intentBundle.getString(AppConstants.KEY_AVERAGE_RATINGS));
            mDescription.setText(intentBundle.getString(AppConstants.KEY_MOVIE_DESC));
            Picasso.with(getActivity()).load(AppConstants.BASE_URL_IMAGE + AppConstants.IMAGE_SIZE_342 + intentBundle.getString(AppConstants.KEY_MOVIE_POSTER)).into(mMoviePoster);
            if(mPrefs.getBoolean(movieName,false)) {
                mFavorites.setChecked(true);
            } else {
                mFavorites.setChecked(false);
            }
            mFavorites.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (mFavorites.isChecked()) {
                                                      mEditor.putBoolean(movieName,true);
                                                      mEditor.commit();
                                                      new MovieContentAsyncTask().execute(AppConstants.YES, mMovieName.getText().toString());
                                                  } else {
                                                      mEditor.putBoolean(movieName,false);
                                                      mEditor.commit();
                                                      new MovieContentAsyncTask().execute(AppConstants.NO, mMovieName.getText().toString());
                                                  }
                                              }
                                          }

            );
            mVideoButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    Log.d(TAG,"video id is " + mVideoId);
                    if(mVideoId == null || "".equals(mVideoId.trim())){
                        Toast.makeText(getActivity(), R.string.video_not_available,Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mVideoId));
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                }
            });

            mReviewButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    if(mReviews == null || "".equals(mReviews.trim())) {
                        Toast.makeText(getActivity(), R.string.review_not_available,Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReviews));
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detailed_movie_info, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (shareActionProvider != null) {
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


    private class MovieContentAsyncTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {
            MovieDataBaseHelper mMovieDataBaseHelper = new MovieDataBaseHelper(getContext());
            String favorite = params[0];
            if (favorite.equals(AppConstants.YES)) {
                mMovieDataBaseHelper.update(params[1], AppConstants.YES);
            } else {
                mMovieDataBaseHelper.update(params[1], AppConstants.NO);
            }
            return null;
        }
    }
}

























