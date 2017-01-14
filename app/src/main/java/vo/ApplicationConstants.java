package vo;

/**
 * Created by akhil on 1/7/2017.
 */

public enum ApplicationConstants {

    APPLICATION_PACKAGE_NAME("com.akhil.securemyworld"),
    TAKE_PHOTO("Take Photo"),
    CHOOSE_FROM_LIBRARY("Choose from Gallery"),
    CANCEL("Cancel"),
    REMOVE_PHOTO("Remove Photo");
    private String value;

    ApplicationConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
