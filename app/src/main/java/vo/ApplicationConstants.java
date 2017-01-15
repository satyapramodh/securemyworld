package vo;

/**
 * Created by akhil on 1/7/2017.
 */

public enum ApplicationConstants {

    TAKE_PHOTO("Take Photo"),
    CHOOSE_FROM_LIBRARY("Choose from Gallery"),
    CANCEL("Cancel"),
    REMOVE_PHOTO("Remove Photo"), IMAGE_TYPE("ImageType"), COLOR("Color"), FACES("Faces"), ADULT("Adult"), CATEGORIES("Categories"),
    APPLICATION_DB_ROOT_REFERENCE("user"),
    APPLICATION_PACKAGE_NAME("com.akhil.findmyroommate");

    private String value;

    ApplicationConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
