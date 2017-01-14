package vo;

import java.util.Date;

/**
 * Created by akhil on 1/10/2017.
 */

public class MessageInformation {
    private String text;
    private String user;
    private long timeStamp;

    public MessageInformation(String text, String user) {

        this.text = text;
        this.user = user;
        this.timeStamp = new Date().getTime();
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
