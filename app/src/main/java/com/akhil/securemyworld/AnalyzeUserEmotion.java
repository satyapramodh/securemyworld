package com.akhil.securemyworld;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.FaceRectangle;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import vo.ImageInformation;

/**
 * Created by akhil on 1/13/2017.
 */
public class AnalyzeUserEmotion extends Activity {
    private static final String IMAGE_BIT_MAP = "imageBitMap";
    private static final String IMAGE_RESULT = "imageResult";
    private EmotionServiceRestClient client;
    private ImageInformation imageInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        imageInformation = bundle.getParcelable(IMAGE_RESULT);
        if (client == null) {
            client = new EmotionServiceRestClient(getString(R.string.emotion_subscription_key));
            doAnalyze();

            Intent intent = new Intent(this, ImageRecognition.class);
            bundle = new Bundle();
            bundle.putParcelable(IMAGE_RESULT, imageInformation);
            intent.putExtras(bundle);

            startActivity(intent);
            finish();

        }
    }

    public void doAnalyze() {
        try {
            new doRequest(false).execute();
        } catch (Exception ignored) {
        }
    }

    private List<RecognizeResult> processWithAutoFaceDetection() throws EmotionServiceException, IOException {
        Log.d("emotion", "Start emotion detection with auto-face detection");

        Gson gson = new Gson();

        String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
        FileInputStream fileInputStream = this.openFileInput(filename);
        long startTime = System.currentTimeMillis();
        List<RecognizeResult> result;
        result = this.client.recognizeImage(fileInputStream);

        String json = gson.toJson(result);
        Log.d("result", json);

        Log.d("emotion", String.format("Detection done. Elapsed time: %d ms", (System.currentTimeMillis() - startTime)));
        return result;
    }


    private List<RecognizeResult> processWithFaceRectangles() throws EmotionServiceException, com.microsoft.projectoxford.face.rest.ClientException, IOException {
        Log.d("emotion", "Do emotion detection with known face rectangles");
        Gson gson = new Gson();

        String filename = getIntent().getStringExtra(IMAGE_BIT_MAP);
        FileInputStream fileInputStream = this.openFileInput(filename);

        long timeMark = System.currentTimeMillis();
        Log.d("emotion", "Start face detection using Face API");
        FaceRectangle[] faceRectangles = null;
        String faceSubscriptionKey = getString(R.string.face_subscription_key);
        FaceServiceRestClient faceClient = new FaceServiceRestClient(faceSubscriptionKey);
        com.microsoft.projectoxford.face.contract.Face[] faces = faceClient.detect(fileInputStream, false, false, null);
        Log.d("emotion", String.format("Face detection is done. Elapsed time: %d ms", (System.currentTimeMillis() - timeMark)));
        if (faces != null) {
            faceRectangles = new FaceRectangle[faces.length];

            for (int i = 0; i < faceRectangles.length; i++) {
                com.microsoft.projectoxford.face.contract.FaceRectangle rect = faces[i].faceRectangle;
                faceRectangles[i] = new com.microsoft.projectoxford.emotion.contract.FaceRectangle(rect.left, rect.top, rect.width, rect.height);
            }
        }

        List<RecognizeResult> result = null;
        if (faceRectangles != null) {
            imageInformation.setRecognizeResults(result);
            fileInputStream.reset();

            timeMark = System.currentTimeMillis();
            Log.d("emotion", "Start emotion detection using Emotion API");
            result = this.client.recognizeImage(fileInputStream, faceRectangles);

            String json = gson.toJson(result);
            Log.d("result", json);
            Log.d("emotion", String.format("Emotion detection is done. Elapsed time: %d ms", (System.currentTimeMillis() - timeMark)));
        }
        return result;
    }

    private class doRequest extends AsyncTask<String, String, List<RecognizeResult>> {
        private Exception e = null;
        private boolean useFaceRectangles = false;

        public doRequest(boolean useFaceRectangles) {
            this.useFaceRectangles = useFaceRectangles;
        }

        @Override
        protected List<RecognizeResult> doInBackground(String... args) {
            if (this.useFaceRectangles == false) {
                try {
                    return processWithAutoFaceDetection();
                } catch (Exception e) {
                    this.e = e;
                }
            } else {
                try {
                    return processWithFaceRectangles();
                } catch (Exception e) {
                    this.e = e;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RecognizeResult> result) {
            super.onPostExecute(result);
            if (e != null) {
                this.e = null;
            } else {
                Integer count = 0;

                for (RecognizeResult r : result) {
                    System.out.println(String.format("\nFace #%1$d \n", count));
                    System.out.println(String.format("\t anger: %1$.5f\n", r.scores.anger));
                    System.out.println(String.format("\t contempt: %1$.5f\n", r.scores.contempt));
                    System.out.println(String.format("\t disgust: %1$.5f\n", r.scores.disgust));
                    System.out.println(String.format("\t fear: %1$.5f\n", r.scores.fear));
                    System.out.println(String.format("\t happiness: %1$.5f\n", r.scores.happiness));
                    System.out.println(String.format("\t neutral: %1$.5f\n", r.scores.neutral));
                    System.out.println(String.format("\t sadness: %1$.5f\n", r.scores.sadness));
                    System.out.println(String.format("\t surprise: %1$.5f\n", r.scores.surprise));
                    System.out.println(String.format("\t face rectangle: %d, %d, %d, %d", r.faceRectangle.left, r.faceRectangle.top, r.faceRectangle.width, r.faceRectangle.height));
                    count++;
                }
            }
        }
    }
}

