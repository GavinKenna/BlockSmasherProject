package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;
import android.view.Window;
import android.content.pm.ActivityInfo;


public class MainActivity extends Activity{

    private static final String UNIQUE_NAME = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // make it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set the MainGamePanel as view
        setContentView(new MainGamePanel(this));
        Log.d(UNIQUE_NAME, "View added");
    }




}
