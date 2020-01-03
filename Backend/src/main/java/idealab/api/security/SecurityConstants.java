package idealab.api.security;

public class SecurityConstants {
    public static final String SECRET = "ideaLabSecretKey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    /*
     * Place ADMIN_RESTRICTED urls below
     * 
     */
    // Audit Controller - none
    public static final String ALL_AUDIT = "/api/audit/**";

    // Color Type Controller
    public static final String ALL_COLORS = "/api/colors/**";
    public static final String ACTIVE_COLORS = "/api/colors/active";

    // Dropbox Controller
    public static final String DROPBOX_UPDATE = "/api/dropbox/file-path";
    public static final String UPDATE_DROPBOX_TOKEN = "/api/dropbox/token";

    // Email Message Controller
    public static final String ALL_MESSAGE = "/api/message";

    // Mail Controller
    public static final String EMAIL_UPDATE_INFO = "/api/mail/info";
    public static final String ALL_EMAIL = "/api/mail/**";

    // Print Job Controller
    public static final String ALL_PRINT_JOB = "/api/print-jobs/**";

    // User Controller
    public static final String ALL_USERS = "/users/**";
    public static final String LOGIN_URL = "/users/login";
}
