package com.akhil.securemyworld;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import util.PermissionUtil;
import vo.ApplicationConstants;

public class ImageRecognition extends Activity implements View.OnClickListener {

    private static final String IMAGE_BIT_MAP = "imageBitMap";
    private static final String JPG = ".jpg";
    private static final String MESSAGE = "There was an error creating the Mobile Service. Verify the URL";
    private static final String ERROR = "Error";
    private static final String APP_URL = "https://securemyworld.azurewebsites.net";
    private static final String ADD_PHOTO = "Add Photo!";
    private static final String DATA = "data";
    private static final String IMAGE = "image/*";
    private static final String SELECT_FILE_TEXT = "Select File";
    private static final String TAG = ImageRecognition.class.getSimpleName();
    private MobileServiceClient mClient;
    private int REQUEST_CAMERA = 0, SELECT_FILE_CODE = 1;
    private ImageView imageView;
    private String userChosenTask;
    private Bitmap thumbnail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        imageView = (ImageView) findViewById(R.id.image_view);

        try {
            mClient = new MobileServiceClient(
                    APP_URL,
                    this);

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception(MESSAGE), ERROR);
        } catch (Exception e) {
            createAndShowDialog(e, ERROR);
        }
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    public void captureUserEmotion(View view) {
        final CharSequence[] options = {ApplicationConstants.TAKE_PHOTO.getValue(),
                ApplicationConstants.CHOOSE_FROM_LIBRARY.getValue(),
                ApplicationConstants.CANCEL.getValue(),
                ApplicationConstants.REMOVE_PHOTO.getValue()
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ADD_PHOTO);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionUtil.checkPermission(ImageRecognition.this);

                if (options[item].equals(ApplicationConstants.TAKE_PHOTO.getValue())) {
                    userChosenTask = ApplicationConstants.TAKE_PHOTO.getValue();
                    if (result)
                        cameraIntent();

                } else if (options[item].equals(ApplicationConstants.CHOOSE_FROM_LIBRARY.getValue())) {
                    userChosenTask = ApplicationConstants.CHOOSE_FROM_LIBRARY.getValue();
                    if (result)
                        galleryIntent();

                } else if (options[item].equals(ApplicationConstants.CANCEL.getValue())) {
                    dialog.dismiss();
                } else if (options[item].equals(ApplicationConstants.REMOVE_PHOTO.getValue())) {
                    imageView.setImageDrawable(getDrawable(R.drawable.default_image));
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(IMAGE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, SELECT_FILE_TEXT), SELECT_FILE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChosenTask.equals(ApplicationConstants.TAKE_PHOTO.getValue()))
                        cameraIntent();
                    else if (userChosenTask.equals(ApplicationConstants.CHOOSE_FROM_LIBRARY.getValue()))
                        galleryIntent();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_CODE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageView.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get(DATA);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + JPG);

        FileOutputStream fo;
        try {
            final boolean file = destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(thumbnail);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_analyze) {
            Intent intent = new Intent(this, AnalyzeUserEmotion.class);
            String fileName = "capturedImage.png";
            try {
                FileOutputStream fileOutputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                thumbnail.recycle();

            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra(IMAGE_BIT_MAP, fileName);
            startActivity(intent);
            finish();
        }
    }
}