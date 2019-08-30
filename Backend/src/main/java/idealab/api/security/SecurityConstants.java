package idealab.api.security;

public class SecurityConstants {
    public static final String SECRET = "ideaLabSecretKey";
    public static final long EXPIRATION_TIME = 43_200; // 12 Hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String LOGIN_URL = "/users/login";
}
