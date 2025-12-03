import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private static final Map<String, User> USERS = new HashMap<>();

    static {
        USERS.put("admin", new User("admin", "admin", "ADMIN"));
        USERS.put("moder", new User("moder", "moder", "MODERATOR"));
        USERS.put("user", new User("user", "user", "USER"));
    }

    public static User findByUsername(String username) {
        if (username == null) return null;
        return USERS.get(username);
    }
}

