package com.akhil.securemyworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.contract.Metadata;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.FileInputStream;
import java.io.IOException;

import vo.ApplicationConstants;
import vo.ImageInformation;

/**
 * Created by akhil on 1/13/2017.
 */
public class AnalyzeUserImage extends Activity {
    private static final String IMAGE_RESULT = "imageResult";
    private static final String IMAGE_BIT_MAP = "imageBitMap";
    ImageInformation imageInformation;
    private VisionServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.image_subscription_key));
            doAnalyze();
        }
    }

    public void doAnalyze() {
        try {
            new doRequest().execute();
            Intent intent = new Intent(this, AnalyzeUserEmotion.class);
            String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
            intent.putExtra(IMAGE_BIT_MAP, filename);
            Bundle bundle = new Bundle();
            bundle.putParcelable(IMAGE_RESULT, imageInformation);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } catch (Exception ignored) {
        }
    }

    private String process() throws VisionServiceException, IOException {
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

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        private Exception e = null;

        doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            Gson gson = new Gson();
            AnalysisResult result = gson.fromJson(data, AnalysisResult.class);

            System.out.println("Image format: " + result.metadata.format + "\n");
            System.out.println("Image width: " + result.metadata.width + ", height:" + result.metadata.height + "\n");
            System.out.println("Clip Art Type: " + result.imageType.clipArtType + "\n");
            System.out.println("Line Drawing Type: " + result.imageType.lineDrawingType + "\n");
            System.out.println("Is Adult Content:" + result.adult.isAdultContent + "\n");
            System.out.println("Adult score:" + result.adult.adultScore + "\n");
            System.out.println("Is Racy Content:" + result.adult.isRacyContent + "\n");
            System.out.println("Racy score:" + result.adult.racyScore + "\n\n");

            final Metadata metadata = result.metadata;
            imageInformation = new ImageInformation(metadata.format, metadata.width, result.imageType.clipArtType,
                    result.imageType.lineDrawingType, result.adult.isAdultContent, result.adult.adultScore,
                    result.adult.isRacyContent, result.adult.racyScore, result.categories, result.faces, null);

            for (Category category : result.categories) {
                System.out.println("Category: " + category.name + ", score: " + category.score + "\n");
            }

            int faceCount = 0;
            for (Face face : result.faces) {
                faceCount++;
                System.out.println("face " + faceCount + ", gender:" + face.gender + "(score: " + face.genderScore + "), age: " + +face.age + "\n");
                System.out.println("    left: " + face.faceRectangle.left + ",  top: " + face.faceRectangle.top + ", width: " + face.faceRectangle.width + "  height: " + face.faceRectangle.height + "\n");
            }
            if (faceCount == 0) {
                System.out.println("No face is detected");
            }
            System.out.println("\n");

            System.out.println("\nDominant Color Foreground :" + result.color.dominantColorForeground + "\n");
            System.out.println("Dominant Color Background :" + result.color.dominantColorBackground + "\n");

            System.out.println("\n--- Raw Data ---\n\n");
            System.out.println(data);
        }
    }
}

