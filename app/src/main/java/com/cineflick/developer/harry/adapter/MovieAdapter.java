package com.cineflick.developer.harry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cineflick.developer.harry.R;
import com.cineflick.developer.harry.data.model.MovieDataModel;

import java.util.ArrayList;

/**
 * Created by harry on 3/8/16.
 */
public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList mArrayList;

    public MovieAdapter(Context context,ArrayList arrayList){
        mContext = context;
        mArrayList = arrayList;

    }
    @Override
    public int getCount() {
        return 0;
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
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_adapter,null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.mTextView = (TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mTextView.setText(((MovieDataModel)mArrayList.get(position)).getOriginalTitle());
        return convertView;
    }

    private static class ViewHolder{
        ImageView mImageView;
        TextView mTextView;
    }
}
