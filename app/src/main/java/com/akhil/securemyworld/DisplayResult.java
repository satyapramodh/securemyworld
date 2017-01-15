package com.akhil.securemyworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import vo.ApplicationConstants;
import vo.ImageInformation;

/**
 * Created by akhil on 1/14/2017.
 */
public class DisplayResult extends Activity implements View.OnClickListener {

    private static final String IMAGE_RESULT = "imageResult";
    private static final String HOME_SCREEN = "Back to Home Page";
    private static final String SUCCESS_MESSAGE = "Message Sent successfully to ";
    private static final String FAILURE_MESSAGE = "Failed to send message to ";
    private static final String USER_NAME = "USER_NAME";
    private static final String MESSAGE = " is looking out for your help.";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String ALERT = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_analysis_result);
        Bundle bundle = getIntent().getExtras();
        ImageInformation imageInformation = bundle.getParcelable(IMAGE_RESULT);
        final ViewGroup layout = (ViewGroup) findViewById(R.id.image_analysis_result);

        TextView textView = new TextView(this);
        textView.setTextSize(20);
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
        if (ALERT.contains(imageInformation.getCategory())) {
            sendMessageToMatchedUsers();
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

    private void sendMessageToMatchedUsers() {
        final String textMessage = getUserName() + MESSAGE;
        try {
            final SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(getPhoneNumber(), null, textMessage, null, null);
            Toast.makeText(DisplayResult.this, SUCCESS_MESSAGE + getUserName(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            Toast.makeText(DisplayResult.this, FAILURE_MESSAGE + getUserName(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private String getUserName() {
        final SharedPreferences sharedPreferences =
                this.getSharedPreferences(ApplicationConstants.APPLICATION_PACKAGE_NAME.getValue(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);
    }

    private String getPhoneNumber() {
        final SharedPreferences sharedPreferences =
                this.getSharedPreferences(ApplicationConstants.APPLICATION_PACKAGE_NAME.getValue(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_NUMBER, null);
    }

}


