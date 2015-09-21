package com.example.gallerypractice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class ImageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        int position = getIntent().getIntExtra("position", -1);
        System.out.println("position in details " + position);
        if (position < 0) {
            ((ImageView)findViewById(R.id.image_item)).setImageResource(R.drawable.placeholder);
        } else {
            ((ImageView)findViewById(R.id.image_item))
                    .setImageBitmap(GalleryContent.IMAGES.get(position));
        }
    }
}
