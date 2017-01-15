package util;

/**
 * Created by akhil on 1/7/2017.
 */

public class KeyUtils {
    public static String encodeFireBaseKey(String key) {
        return key.replace("%", "%25")
                .replace(".", "%2E")
                .replace("#", "%23")
                .replace("$", "%24")
                .replace("[", "%5B")
                .replace("]", "%5D");

    }

    public static String decodeFireBaseKey(String key) {
        return key.replace("%25", "%")
                .replace("%2E", ".")
                .replace("%23", "#")
                .replace("%24", "$")
                .replace("%5B", "[")
                .replace("%5D", "]");

    }
}
