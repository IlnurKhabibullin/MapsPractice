package com.example.gallerypractice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Ильнур on 17.09.2015.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView;

    public RecyclerItemViewHolder(final View parent, ImageView itemImageView) {
        super(parent);
        imageView = itemImageView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        ImageView itemImageView = (ImageView) parent.findViewById(R.id.image_item);
        return new RecyclerItemViewHolder(parent, itemImageView);
    }

    public void setItemImage(Context context, int position) {
        if (GalleryContent.IMAGES.size() <= position) {
            BitmapTarget target = new BitmapTarget(imageView, position);
            BitmapTarget.targets.add(target);
            System.out.println(position + " image loading");
            Picasso.with(context)
                    .load(GalleryContent.URLS.get(position))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .into(target);
            System.out.println(position + " image loaded");
        } else {
            System.out.println(position + " image used");
            imageView.setImageBitmap(GalleryContent.IMAGES.get(position));
        }
    }

}