package vo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 1/11/2017.
 */

public class ImageInformation implements Parcelable {
    public static final Parcelable.Creator<ImageInformation> CREATOR = new Parcelable.Creator<ImageInformation>() {
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
    private List<Category> category;
    private List<Face> face;
    private List<RecognizeResult> recognizeResults;

    public ImageInformation(String imageFormat, int imageWidth, int imageClipArtType, int imageLineDrawingType, boolean imageAdultContent, float imageAdultContentScore, boolean imageRacyContent,
                            float imageRacyContentScore, List<Category> category, List<Face> face, List<RecognizeResult> recognizeResults) {
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
        this.category = new ArrayList<Category>();
        in.readList(this.category, Category.class.getClassLoader());
        this.face = new ArrayList<Face>();
        in.readList(this.face, Face.class.getClassLoader());
        this.recognizeResults = new ArrayList<RecognizeResult>();
        in.readList(this.recognizeResults, RecognizeResult.class.getClassLoader());
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

    public List<Category> getCategory() {
        return category;
    }

    public List<Face> getFace() {
        return face;
    }

    public List<RecognizeResult> getRecognizeResults() {
        return recognizeResults;
    }

    public void setRecognizeResults(List<RecognizeResult> recognizeResults) {
        this.recognizeResults = recognizeResults;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return
                System.lineSeparator() + "imageFormat='" + imageFormat +
                        System.lineSeparator() + "imageWidth=" + imageWidth +
                        System.lineSeparator() + "imageClipArtType=" + imageClipArtType +
                        System.lineSeparator() + "imageLineDrawingType=" + imageLineDrawingType +
                        System.lineSeparator() + "imageAdultContent=" + imageAdultContent +
                        System.lineSeparator() + "imageAdultContentScore=" + imageAdultContentScore +
                        System.lineSeparator() + "imageRacyContent=" + imageRacyContent +
                        System.lineSeparator() + "imageRacyContentScore=" + imageRacyContentScore +
                        System.lineSeparator() + "category=" + category +
                        System.lineSeparator() + "face=" + face +
                        System.lineSeparator() + "recognizeResults=" + recognizeResults;
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
        dest.writeList(this.category);
        dest.writeList(this.face);
        dest.writeList(this.recognizeResults);
    }
}
