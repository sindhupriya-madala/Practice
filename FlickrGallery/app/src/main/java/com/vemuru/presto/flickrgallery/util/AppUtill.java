package com.vemuru.presto.flickrgallery.util;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.vemuru.presto.flickrgallery.App;
import com.vemuru.presto.flickrgallery.R;

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
public class AppUtill {
    public static void bindImage(String url, ImageView target, TextView dim, boolean centerCrop) {
        Drawable drawable = ContextCompat.getDrawable(target.getContext(), R.drawable.ic_image_24dp);
        DrawableRequestBuilder<String> builder = Glide.with(App.getContext())
                .load(url)
                .error(R.drawable.ic_broken_image_24dp)
                .placeholder(drawable)
                .crossFade();
        if (centerCrop) builder.centerCrop();
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                int width = resource.getIntrinsicWidth();
                int height = resource.getIntrinsicHeight();
                dim.setText("Dimensions : Width : " + width + " Height : "+height);
                target.setImageDrawable(resource);

            }
        });
    }
}
