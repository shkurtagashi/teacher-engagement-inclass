package com.example.android.teacher.PhotoGrid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android.teacher.R;

/**
 * Created by shkurtagashi on 22.01.17.
 */

public class ImageAdapter extends BaseAdapter{

    String imageDescription;
    private Context context;

    public ImageAdapter(Context c){
        context = c;
    }
    @Override
    public int getCount() {
        return imageIds.length;
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
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(273, 379));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageIds[position]);
        return imageView;
    }

    private Integer[]  imageIds = {

            R.drawable.affraid,             R.drawable.tense,
            R.drawable.excited,             R.drawable.delighted,
            R.drawable.frustrated,          R.drawable.angry,
            R.drawable.happy,               R.drawable.glad,
            R.drawable.miserable,           R.drawable.sad,
            R.drawable.calm,                R.drawable.satisfied,
            R.drawable.gloomy,              R.drawable.tired,
            R.drawable.sleepy,              R.drawable.serene
    };
}
