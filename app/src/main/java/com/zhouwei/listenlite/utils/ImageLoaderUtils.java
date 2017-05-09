package com.zhouwei.listenlite.utils;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by zhouwei on 17/5/3.
 */

public class ImageLoaderUtils {
    public static void loadImage(String url, ImageView imageView){
       // Picasso.with(imageView.getContext()).load(url).into(imageView);
        ImageLoader.getInstance().displayImage(url,imageView);
    }
    public static void loadImage(String url, int placeHolder, ImageView imageView){
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(placeHolder)
                .into(imageView);
    }
}
