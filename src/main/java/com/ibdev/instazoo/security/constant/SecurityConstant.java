package com.ibdev.instazoo.security.constant;

public class SecurityConstant {

    public static final String SIGN_UP = "/api/auth/**";
    public static final String SECRET_KEY = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000; // 10 min
}
