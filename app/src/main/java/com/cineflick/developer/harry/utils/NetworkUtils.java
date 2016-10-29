package com.cineflick.developer.harry.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.cineflick.developer.harry.R;

/**
 * Created by harry on 10/5/16.
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private NetworkInfo mNetworkInfo;

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo != null && mNetworkInfo.isConnected() != false){
            Log.d(TAG,context.getResources().getString(R.string.network_availbale));
            return true;
        }else{
            Toast.makeText(context,context.getString(R.string.network_not_availbale),Toast.LENGTH_LONG).show();
            Log.d(TAG,context.getResources().getString(R.string.network_not_availbale));
            return false;
        }
    }

    public boolean isWiFi() {
        if(mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
