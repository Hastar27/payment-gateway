package security;

import java.util.HashMap;
import java.util.Map;

public class SecurityManager {

    // Hard coding it because of time constraints
    public static Map<String, String> credentials;

    static {
        credentials = new HashMap<>();
        credentials.put("key", "value");
    }

}
