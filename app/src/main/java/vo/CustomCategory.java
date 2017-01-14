package vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.microsoft.projectoxford.vision.contract.Category;

/**
 * Created by akhil on 1/14/2017.
 */

public class CustomCategory extends Category implements Parcelable {
    public static final Parcelable.Creator<CustomCategory> CREATOR = new Parcelable.Creator<CustomCategory>() {
        @Override
        public CustomCategory createFromParcel(Parcel source) {
            return new CustomCategory(source);
        }

        @Override
        public CustomCategory[] newArray(int size) {
            return new CustomCategory[size];
        }
    };
    private Category category;

    public CustomCategory(Category category) {
        this.category = category;
    }

    protected CustomCategory(Parcel in) {
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.detail = in.readParcelable(Object.class.getClassLoader());
        this.name = in.readString();
        this.score = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.category, flags);
        dest.writeParcelable((Parcelable) this.detail, flags);
        dest.writeString(this.name);
        dest.writeDouble(this.score);
    }
}
