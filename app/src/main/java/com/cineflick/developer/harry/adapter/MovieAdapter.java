package com.cineflick.developer.harry.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.data.model.MovieDataModel;
import com.cineflick.developer.harry.utils.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by harry on 3/8/16.
 */
public class MovieAdapter extends BaseAdapter {
    private String TAG = MovieAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList mArrayList;

    public MovieAdapter(Context context,ArrayList arrayList){
        mContext = context;
        mArrayList = arrayList;

    }
    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, mContext.getString(R.string.times) + position);
        ViewHolder viewHolder;
        if(convertView==null){
            Log.d(TAG,mContext.getString(R.string.convertView_called)+position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_adapter,null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView)convertView.findViewById(R.id.movie_adapter_imageView);
           // viewHolder.mTextView = (TextView)convertView.findViewById(R.id.movie_adapter_textView);
            convertView.setTag(viewHolder);
        }else {
            Log.d(TAG,mContext.getString(R.string.convert_View_Called)+position);
            viewHolder = (ViewHolder)convertView.getTag();
        }
        MovieDataModel movieDataModel = (MovieDataModel)mArrayList.get(position);
        Picasso.with(mContext).load(AppConstants.BASE_URL + AppConstants.IMAGE_SIZE_DEFAUlT + movieDataModel.getPosterPath()).into(viewHolder.mImageView);

            // viewHolder.mTextView.setText(movieDataModel.getOriginalTitle());
        return convertView;
    }

    private static class ViewHolder{
        ImageView mImageView;
        TextView mTextView;
    }
}
