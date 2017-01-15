package com.akhil.securemyworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.akhil.securemyworld.async.AsyncResponse;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.FileInputStream;
import java.io.IOException;

import vo.ApplicationConstants;
import vo.ImageInformation;

/**
 * Created by akhil on 1/13/2017.
 */
public class AnalyzeUserImage extends Activity {
    public static final String EMPTY = "";
    private static final String IMAGE_RESULT = "imageResult";
    private static final String IMAGE_BIT_MAP = "imageBitMap";
    private ImageInformation imageInformation;
    private VisionServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.image_subscription_key));
            try {
                AsyncResponse<AnalysisResult> response = new AsyncResponse<AnalysisResult>() {
                    @Override
                    public void onPostTask(AnalysisResult output) {
                        Intent intent = new Intent(AnalyzeUserImage.this, AnalyzeUserEmotion.class);
                        String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
                        intent.putExtra(IMAGE_BIT_MAP, filename);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(IMAGE_RESULT, imageInformation);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                };
                new doRequest(response).execute();
            } catch (Exception ignored) {
            }
        }
    }

    private AnalysisResult process() throws VisionServiceException, IOException {
        Gson gson = new Gson();
        String[] features = {ApplicationConstants.IMAGE_TYPE.getValue(), ApplicationConstants.COLOR.getValue(),
                ApplicationConstants.FACES.getValue(), ApplicationConstants.ADULT.getValue(),
                ApplicationConstants.CATEGORIES.getValue()};
        String[] details = {};
        String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
        FileInputStream fileInputStream = this.openFileInput(filename);

        AnalysisResult v = this.client.analyzeImage(fileInputStream, features, details);

        String result = gson.toJson(v);
        Log.d("result", result);

        return v;
    }

    private class doRequest extends AsyncTask<String, String, AnalysisResult> {
        private AsyncResponse<AnalysisResult> asyncResponse;
        private Exception e = null;

        public doRequest(AsyncResponse<AnalysisResult> asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected AnalysisResult doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(AnalysisResult result) {
            super.onPostExecute(result);

            StringBuilder customCategories = new StringBuilder();
            if (result != null) {
                for (Category category : result.categories) {
                    customCategories.append(category.name).append(" : ").append(category.score).append(System.lineSeparator());
                }

                imageInformation = new ImageInformation(
                        result.adult.isAdultContent, result.adult.adultScore,
                        result.adult.isRacyContent, result.adult.racyScore, customCategories.toString(), EMPTY, EMPTY);
            }
            asyncResponse.onPostTask(result);
        }
    }
}

