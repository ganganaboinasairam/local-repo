// ChatTokenManager.java
package testproject.auth;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChatTokenManager {
    private static class Info {
        final String custId;
        final Instant expiresAt;
        volatile boolean used;
        Info(String custId, Instant expiresAt) { this.custId = custId; this.expiresAt = expiresAt; this.used = false; }
    }

    private static final Map<String, Info> TOKENS = new ConcurrentHashMap<>();
    // create token (valid for N seconds)
    public static String create(String custId, int validSeconds) {
        String token = UUID.randomUUID().toString();
        TOKENS.put(token, new Info(custId, Instant.now().plusSeconds(validSeconds)));
        return token;
    }

    // validate and optionally mark used
    public static synchronized boolean validateAndUse(String token, String custId, boolean markUsed) {
        Info info = TOKENS.get(token);
        if (info == null) return false;
        if (info.used) return false;
        if (!info.custId.equals(custId)) return false;
        if (Instant.now().isAfter(info.expiresAt)) {
            TOKENS.remove(token);
            return false;
        }
        if (markUsed) {
            info.used = true;
            TOKENS.remove(token); // optional: remove after use
        }
        return true;
    }
}
