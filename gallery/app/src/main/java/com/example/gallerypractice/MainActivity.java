package com.example.gallerypractice;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        double displayInch = getDisplayInch();
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display mDisplay = mWindowManager.getDefaultDisplay();
        int size = Surface.ROTATION_0 == mDisplay.getOrientation() ? 1 : 2;
        System.out.println("display is " + displayInch + " and size is " + size);
        if (displayInch >= 7.0) {
            size++;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, size));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, GalleryContent.URLS);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                animateIntent(view, position);
            }
        }));
    }

    public void animateIntent(View view, int position) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("position", position);
        System.out.println("position is " + position);
        if (Build.VERSION.SDK_INT >= 21)
            view.setTransitionName(getString(R.string.app_name));
        String transitionName = getString(R.string.app_name);
        View viewStart = findViewById(R.id.image_item);
        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        viewStart,
                        transitionName
                );
        ActivityCompat.startActivity(this, intent, options.toBundle());

    }

    private double getDisplayInch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

}