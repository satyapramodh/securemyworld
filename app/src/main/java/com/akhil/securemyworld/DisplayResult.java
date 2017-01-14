package com.akhil.securemyworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vo.ImageInformation;

/**
 * Created by akhil on 1/14/2017.
 */
public class DisplayResult extends Activity implements View.OnClickListener {

    private static final String IMAGE_RESULT = "imageResult";
    private static final String HOME_SCREEN = "Back to Home Page";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_analysis_result);
        Bundle bundle = getIntent().getExtras();
        ImageInformation imageInformation = bundle.getParcelable(IMAGE_RESULT);
        final ViewGroup layout = (ViewGroup) findViewById(R.id.image_analysis_result);

        TextView textView = new TextView(this);
        assert imageInformation != null;
        if (imageInformation.isImageRacyContent() || imageInformation.isImageAdultContent()) {
            textView.setText(imageInformation.toString());
            textView.setTextColor(Color.parseColor("#FF0000"));
            layout.addView(textView);
        } else {
            textView.setText(imageInformation.toString());
            textView.setTextColor(Color.parseColor("#000000"));
            layout.addView(textView);
        }
        Button button = new Button(this);

        button.setText(HOME_SCREEN);
        button.setOnClickListener(this);
        layout.addView(button);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ImageRecognition.class);
        startActivity(intent);
        finish();
    }
}
