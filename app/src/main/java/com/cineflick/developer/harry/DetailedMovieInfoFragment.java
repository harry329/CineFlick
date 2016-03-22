package com.cineflick.developer.harry;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context mContext;

    public DetailedMovieInfoFragment() {
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
            Log.d(TAG,"intent bundle is not null");
            mMovieName.setText(intentBundle.getString(AppConstants.KEY_MOVIE_NAME));
            mReleaseDate.setText(intentBundle.getString(AppConstants.KEY_RELEASE_DATE));
            mRatings.setText(intentBundle.getString(AppConstants.KEY_AVERAGE_RATINGS));
            mDescription.setText(intentBundle.getString(AppConstants.KEY_MOVIE_DESC));
            Picasso.with(getActivity()).load(AppConstants.BASE_URL +AppConstants.IMAGE_SIZE +intentBundle.getString(AppConstants.KEY_MOVIE_POSTER)).into(mMoviePoster);
        }
        else {
            Log.d(TAG,"intent bundle is null");
        }

    }
}
