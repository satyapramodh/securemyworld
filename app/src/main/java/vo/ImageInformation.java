package vo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

/**
 * Created by akhil on 1/11/2017.
 */

public class ImageInformation implements Parcelable {

    public static final Creator<ImageInformation> CREATOR = new Creator<ImageInformation>() {
        @Override
        public ImageInformation createFromParcel(Parcel source) {
            return new ImageInformation(source);
        }

        @Override
        public ImageInformation[] newArray(int size) {
            return new ImageInformation[size];
        }
    };
    private String imageFormat;
    private int imageWidth;
    private int imageClipArtType;
    private int imageLineDrawingType;
    private boolean imageAdultContent;
    private float imageAdultContentScore;
    private boolean imageRacyContent;
    private float imageRacyContentScore;
    private String category;
    private String face;
    private String recognizeResults;


    public ImageInformation(String imageFormat, int imageWidth, int imageClipArtType, int imageLineDrawingType, boolean imageAdultContent, float imageAdultContentScore, boolean imageRacyContent,
                            float imageRacyContentScore, String category, String face, String recognizeResults) {
        this.imageFormat = imageFormat;
        this.imageWidth = imageWidth;
        this.imageClipArtType = imageClipArtType;
        this.imageLineDrawingType = imageLineDrawingType;
        this.imageAdultContent = imageAdultContent;
        this.imageAdultContentScore = imageAdultContentScore;
        this.imageRacyContent = imageRacyContent;
        this.imageRacyContentScore = imageRacyContentScore;
        this.category = category;
        this.face = face;
        this.recognizeResults = recognizeResults;
    }

    protected ImageInformation(Parcel in) {
        this.imageFormat = in.readString();
        this.imageWidth = in.readInt();
        this.imageClipArtType = in.readInt();
        this.imageLineDrawingType = in.readInt();
        this.imageAdultContent = in.readByte() != 0;
        this.imageAdultContentScore = in.readFloat();
        this.imageRacyContent = in.readByte() != 0;
        this.imageRacyContentScore = in.readFloat();
        this.category = in.readString();
        this.face = in.readString();
        this.recognizeResults = in.readString();
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageClipArtType() {
        return imageClipArtType;
    }

    public int getImageLineDrawingType() {
        return imageLineDrawingType;
    }

    public boolean isImageAdultContent() {
        return imageAdultContent;
    }

    public float getImageAdultContentScore() {
        return imageAdultContentScore;
    }

    public boolean isImageRacyContent() {
        return imageRacyContent;
    }

    public float getImageRacyContentScore() {
        return imageRacyContentScore;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public String getCategory() {
        return category;
    }

    public String getFace() {
        return face;
    }

    public String getRecognizeResults() {
        return recognizeResults.toString();
    }

    public void setRecognizeResults(String recognizeResults) {
        this.recognizeResults = recognizeResults != null ? recognizeResults.toString() : recognizeResults;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return
                System.lineSeparator() + " Image Format : " + imageFormat +
                        System.lineSeparator() + " Image Width : " + imageWidth +
                        System.lineSeparator() + " Image Clip Art Type : " + imageClipArtType +
                        System.lineSeparator() + " Image Line Drawing Type : " + imageLineDrawingType +
                        System.lineSeparator() + " Image Adult Content : " + imageAdultContent +
                        System.lineSeparator() + " Image Adult Content Score : " + imageAdultContentScore +
                        System.lineSeparator() + " Image Racy Content : " + imageRacyContent +
                        System.lineSeparator() + " Image Racy Content Score : " + imageRacyContentScore +
                        System.lineSeparator() + " Category : " + category +
                        System.lineSeparator() + " Face : " + face +
                        System.lineSeparator() + "Recognize Results : " + recognizeResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageFormat);
        dest.writeInt(this.imageWidth);
        dest.writeInt(this.imageClipArtType);
        dest.writeInt(this.imageLineDrawingType);
        dest.writeByte(this.imageAdultContent ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.imageAdultContentScore);
        dest.writeByte(this.imageRacyContent ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.imageRacyContentScore);
        dest.writeString(this.category);
        dest.writeString(this.face);
        dest.writeString(this.recognizeResults);
    }
}
