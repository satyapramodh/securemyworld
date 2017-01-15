package vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akhil on 1/2/2017.
 */

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String name;
    private String phoneNumber;
    private String email;
    private String sex;
    private String address;

    public User(String name, String phoneNumber, String email,
                String sex, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
        this.address = address;
    }

    public User() {
    }

    protected User(Parcel parcel) {
        name = parcel.readString();
        phoneNumber = parcel.readString();
        email = parcel.readString();
        sex = parcel.readString();
        address = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(sex);
        parcel.writeString(address);
    }
}
