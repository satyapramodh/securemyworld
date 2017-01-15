package com.akhil.securemyworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import util.KeyUtils;
import vo.ApplicationConstants;
import vo.User;

/**
 * Created by akhil on 12/29/2016.
 */
public class DeleteUserAccountActivity extends AppCompatActivity {

    private static final String MESSAGE = "Are you sure you want to delete this account?";
    private static final String CONFIRMATION = "Yes, delete it!";
    private static final String REJECTION = "No";
    private static final String USER_EMAIL_ID = "USER_EMAIL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        deleteAccount();
    }

    private void deleteAccount() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(MESSAGE)
                .setPositiveButton(CONFIRMATION, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserCredentialsFromDatabase();
                        deleteUserProfileFromDatabase();
                        clearSharedPreferences();
                    }
                })
                .setNegativeButton(REJECTION, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(DeleteUserAccountActivity.this, ImageRecognition.class));
                        finish();
                    }
                })
                .create();

        dialog.show();
    }

    private void deleteUserProfileFromDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ApplicationConstants.APPLICATION_DB_ROOT_REFERENCE.getValue());
        final SharedPreferences preferences = this.getSharedPreferences(ApplicationConstants.APPLICATION_PACKAGE_NAME.getValue(),
                Context.MODE_PRIVATE);
        String email = preferences.getString(USER_EMAIL_ID, null);
        final String key = KeyUtils.encodeFireBaseKey(email);
        databaseReference = databaseReference.child(key);
        databaseReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                final User mutableDataValue = mutableData.getValue(User.class);
                if (mutableDataValue == null) {
                    return Transaction.success(mutableData);
                }
                mutableData.setValue(null);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }

    private void clearSharedPreferences() {
        this.getSharedPreferences(ApplicationConstants.APPLICATION_PACKAGE_NAME.getValue(),
                Context.MODE_PRIVATE).edit().clear().apply();
    }

    private void deleteUserCredentialsFromDatabase() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(DeleteUserAccountActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }
}
