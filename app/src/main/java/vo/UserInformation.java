package vo;

/**
 * Created by akhil on 1/11/2017.
 */

public class UserInformation {
    private String name;
    private String email;
    private String phoneNumber;
    private String status;

    public UserInformation() {
    }

    public UserInformation(String name, String email, String phoneNumber, String status) {

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }
}
