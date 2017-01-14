package vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;

import java.util.List;

/**
 * Created by akhil on 1/11/2017.
 */

public class ImageInformation implements Parcelable {
    public static final Creator<ImageInformation> CREATOR = new Creator<ImageInformation>() {
        @Override
        public ImageInformation createFromParcel(Parcel in) {
            return new ImageInformation(in);
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
        imageFormat = in.readString();
        imageWidth = in.readInt();
        imageClipArtType = in.readInt();
        imageLineDrawingType = in.readInt();
        imageAdultContent = in.readByte() != 0;
        imageAdultContentScore = in.readFloat();
        imageRacyContent = in.readByte() != 0;
        imageRacyContentScore = in.readFloat();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageFormat);
        parcel.writeString(String.valueOf(imageWidth));
        parcel.writeString(String.valueOf(imageClipArtType));
        parcel.writeString(String.valueOf(imageLineDrawingType));
        parcel.writeString(String.valueOf(imageAdultContent));
        parcel.writeString(String.valueOf(imageAdultContentScore));
        parcel.writeString(String.valueOf(imageRacyContent));
        parcel.writeString(String.valueOf(imageRacyContentScore));
        parcel.writeString(String.valueOf(category));
        parcel.writeList(face);
        parcel.writeList(recognizeResults);
    }
}
