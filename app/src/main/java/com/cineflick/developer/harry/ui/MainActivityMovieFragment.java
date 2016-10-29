package com.cineflick.developer.harry.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.adapter.MovieAdapter;
import com.cineflick.developer.harry.data.model.MovieDataModel;
import com.cineflick.developer.harry.database.MovieDataBaseHelper;
import com.cineflick.developer.harry.parser.JSONParser;
import com.cineflick.developer.harry.utils.AppConstants;
import com.cineflick.developer.harry.utils.NetworkUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivityMovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivityMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityMovieFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = MainActivityMovieFragment.class.getSimpleName();
    private ArrayList<MovieDataModel> mArrayList;
    private Context mContext;
    private GridView mGridView;
    private MovieDataBaseHelper mMovieDataBaseHelper;
    private OnFragmentInteractionListener mListener;
    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    public MainActivityMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainActivityMovieFragment.
     */

    public static MainActivityMovieFragment newInstance() {
        MainActivityMovieFragment fragment = new MainActivityMovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void moviePreference() {
        //check network availability before calling network operations
        if (new NetworkUtils().isNetworkAvailable(mContext)) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
            String syncConnPref = sharedPref.getString(getString(R.string.pref_sync),mContext.getResources().getString(R.string.rating));
            switch (syncConnPref){
                case AppConstants.RATINGS :
                    new MovieQuerryClass().execute(AppConstants.RATING);
                    break;
                case AppConstants.POPULARITY :
                    new MovieQuerryClass().execute(AppConstants.POPULARITY);
                    break;
                default:
                    new MovieQuerryClass().execute(AppConstants.FAVORITE);
            }
        } else {
            new MovieQuerryClass().execute(AppConstants.FAVORITE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity_movie, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt("SELECTED_KEY");
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        moviePreference();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Logic to show movie
    public void displayMovies() {
        if (mArrayList != null) {
            Log.d(TAG, getString(R.string.array_list_not_null_size) + mArrayList.size());
            final MovieAdapter movieAdapter = new MovieAdapter(mContext, mArrayList);
            mGridView.setAdapter(movieAdapter);

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPosition = position;
                    mListener.onFragmentInteraction(getMovieInfoBundle(position));
                }
            });

        } else {
            Log.d(TAG, getString(R.string.array_list_null));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public Bundle getMovieInfoBundle(int position) {
        MovieDataModel movieDataModel = mArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.KEY_MOVIE_NAME, movieDataModel.getOriginalTitle());
        bundle.putString(AppConstants.KEY_MOVIE_DESC, movieDataModel.getOverview());
        bundle.putString(AppConstants.KEY_MOVIE_POSTER, movieDataModel.getPosterPath());
        bundle.putString(AppConstants.KEY_AVERAGE_RATINGS, ((Double) movieDataModel.getVoteAverage()).toString());
        bundle.putString(AppConstants.KEY_RELEASE_DATE, movieDataModel.getReleaseDate());
        bundle.putString(AppConstants.KEY_VIDEO_ID,movieDataModel.getVideoId());
        bundle.putString(AppConstants.KEY_REVIEW,movieDataModel.getReviewURL());
        return bundle;
    }

    //async task to perform all network related tasks
    private class MovieQuerryClass extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {
            mMovieDataBaseHelper = new MovieDataBaseHelper(mContext);
            String selectionParam = AppConstants.POPULARITY_DESC+AppConstants.API_KEY_STRING;
            if (params[0].equals(AppConstants.RATING)) {
                selectionParam = AppConstants.RATING_DESC+AppConstants.API_KEY_STRING;
            } else if (params[0].equals(AppConstants.FAVORITE)) {
                mArrayList = mMovieDataBaseHelper.getMovieList(AppConstants.YES);
                return null;
            }
            String urlWithAppKey = AppConstants.BASE_URL +AppConstants.URL_DISCOVER + selectionParam + AppConstants.API_KEY;
            String movieJsonStr = null;
            JSONParser jsonParser = null;
            try {
                movieJsonStr = getURLResponse(urlWithAppKey);

                jsonParser = new JSONParser(movieJsonStr);

                Log.d(TAG, movieJsonStr);
                mArrayList = jsonParser.parseJSON();
                for(MovieDataModel movieDataModels: mArrayList){
                    int id = movieDataModels.getMovieId();
                    Log.d(TAG, "movie id is " + id);
                    String videoURL = AppConstants.BASE_URL +AppConstants.MOVIE+id+AppConstants.SLASH+AppConstants.VIDEO+AppConstants.API_KEY_STRING+AppConstants.API_KEY;
                    movieJsonStr = getURLResponse(videoURL);
                    Log.d(TAG, "video id string " +movieJsonStr);
                    if(movieJsonStr != null) {
                        jsonParser = new JSONParser(movieJsonStr, true);
                        String videoId = jsonParser.parseJSONVideo();
                        Log.d(TAG, "video id  is " + videoId);
                        movieDataModels.setVideoId(videoId);
                    }

                    String reviewURL = AppConstants.BASE_URL +AppConstants.MOVIE+id+AppConstants.SLASH+AppConstants.REVIEWS+AppConstants.API_KEY_STRING+AppConstants.API_KEY;
                    movieJsonStr = getURLResponse(reviewURL);
                    Log.d(TAG, "review id string " +movieJsonStr);
                    if(movieJsonStr != null) {
                        jsonParser = new JSONParser(movieJsonStr,true);
                        String review = jsonParser.parseJSONReview();
                        Log.d(TAG, "review  id  is " + review);
                        movieDataModels.setReviewURL(review);

                    }

                }
                mMovieDataBaseHelper.insertMovies(mArrayList);
            }  catch (JSONException e) {
                Log.e(TAG, getResources().getString(R.string.error), e);
                return null;
            }
            return null;
        }

        private String getURLResponse(String urlLink) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urlLink);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    Log.e(TAG, getResources().getString(R.string.input_stream_null));
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, getResources().getString(R.string.error), e);
                return null;
            } catch (IOException e) {
                Log.e(TAG, getResources().getString(R.string.error), e);
                return null;
            } finally {
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
        }

        protected void onPostExecute(Void result) {
            Log.d(TAG, getResources().getString(R.string.in_post_execute));
            mListener.moviesDownloadCallBack();
            displayMovies();
            if(mPosition != GridView.INVALID_POSITION) {
                mGridView.smoothScrollToPosition(mPosition);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle bundle);

        void moviesDownloadCallBack();
    }
}
