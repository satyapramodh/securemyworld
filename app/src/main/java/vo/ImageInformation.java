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
    private boolean imageAdultContent;
    private float imageAdultContentScore;
    private boolean imageRacyContent;
    private float imageRacyContentScore;
    private String category;
    private String face;
    private String emotionAnalysis;

    public ImageInformation(boolean imageAdultContent, float imageAdultContentScore, boolean imageRacyContent,
                            float imageRacyContentScore, String category, String face, String emotionAnalysis) {
        this.imageAdultContent = imageAdultContent;
        this.imageAdultContentScore = imageAdultContentScore;
        this.imageRacyContent = imageRacyContent;
        this.imageRacyContentScore = imageRacyContentScore;
        this.category = category;
        this.face = face;
        this.emotionAnalysis = emotionAnalysis;
    }

    protected ImageInformation(Parcel in) {
        this.imageAdultContent = in.readByte() != 0;
        this.imageAdultContentScore = in.readFloat();
        this.imageRacyContent = in.readByte() != 0;
        this.imageRacyContentScore = in.readFloat();
        this.category = in.readString();
        this.face = in.readString();
        this.emotionAnalysis = in.readString();
    }

    public void updateEmotionAnalysis(String emotionAnalysis) {
        this.emotionAnalysis = emotionAnalysis;
    }

    public String getEmotionAnalysis() {
        return emotionAnalysis;
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

    public String getCategory() {
        return category;
    }

    public String getFace() {
        return face;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return
                System.lineSeparator() + " Adult Content : " + imageAdultContent + ", " + imageAdultContentScore +
                        System.lineSeparator() + " Race Content : " + imageRacyContent + ", " + imageRacyContentScore +
                        System.lineSeparator() + " Category : " + category +
                        System.lineSeparator() + " Face : " + face +
                        System.lineSeparator() + " Emotions : " + emotionAnalysis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.imageAdultContent ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.imageAdultContentScore);
        dest.writeByte(this.imageRacyContent ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.imageRacyContentScore);
        dest.writeString(this.category);
        dest.writeString(this.face);
        dest.writeString(this.emotionAnalysis);
    }
}
