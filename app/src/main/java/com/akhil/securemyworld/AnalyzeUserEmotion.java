package com.akhil.securemyworld;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import java.io.FileInputStream;

/**
 * Created by akhil on 1/13/2017.
 */
public class AnalyzeUserEmotion extends Activity {
    private static final String IMAGE_BIT_MAP = "imageBitMap";

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap;
        String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
        try {
            FileInputStream fileInputStream = this.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            System.out.println(bitmap.getByteCount());
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

