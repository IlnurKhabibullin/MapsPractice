package com.example.gallerypractice;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ильнур on 18.09.2015.
 */
public class BitmapTarget implements Target {

    static Set<Target> targets = new HashSet<>();

    private ImageView imageView;
    private int position;

    public BitmapTarget(ImageView image, int pos) {
        imageView = image;
        position = pos;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        GalleryContent.addImage(position, bitmap);
        imageView.setImageBitmap(bitmap);
        targets.remove(this);
    }

    @Override
    public void onBitmapFailed(Drawable drawable) {
        imageView.setImageDrawable(drawable);
        targets.remove(this);
    }

    @Override
    public void onPrepareLoad(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }
}
