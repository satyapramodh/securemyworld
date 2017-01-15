package com.akhil.securemyworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import vo.ApplicationConstants;

/**
 * Created by akhil on 12/26/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_EMAIL_ID = "USER_EMAIL_ID";
    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                    Log.d(TAG, "user logged in: " + user.getEmail());
                else
                    Log.d(TAG, "user logged out.");
            }
        };
        login();
    }

    private void login() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Log.d(TAG, "initializing login screen.");
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                            ))
                            .build(), RC_SIGN_IN);

        } else {
            Log.d(TAG, firebaseAuth.getCurrentUser().getEmail());
            startActivityIntent(ImageRecognition.class);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void startActivityIntent(Class className) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d(TAG, "starting Welcome Screen activity");
            Intent intent = new Intent(this, className);
            SharedPreferences preferences = this.getSharedPreferences(ApplicationConstants.APPLICATION_PACKAGE_NAME.getValue(), Context.MODE_PRIVATE);
            preferences.edit().putString(USER_NAME, FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).apply();
            preferences.edit().putString(USER_EMAIL_ID, FirebaseAuth.getInstance().getCurrentUser().getEmail()).apply();
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            firebaseAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            startActivityIntent(ImageRecognition.class);
        }
    }
}
