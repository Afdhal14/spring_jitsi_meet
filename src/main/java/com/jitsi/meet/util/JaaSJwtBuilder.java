package com.jitsi.meet.util;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

public class JaaSJwtBuilder {

    private final JWTCreator.Builder jwtBuilder;
    private final Map<String, Object> userClaims = new HashMap<>();
    private final Map<String, Object> featureClaims = new HashMap<>();

    public JaaSJwtBuilder() {
        this.jwtBuilder = JWT.create();
    }

    public JaaSJwtBuilder withApiKey(String apiKey) {
        jwtBuilder.withKeyId(apiKey);
        return this;
    }

    public JaaSJwtBuilder withAppID(String appId) {
        jwtBuilder.withClaim("sub", appId);
        return this;
    }

    public JaaSJwtBuilder withUser(String name, String email, boolean moderator) {
        userClaims.put("name", name);
        userClaims.put("email", email);
        userClaims.put("moderator", moderator);
        userClaims.put("id", UUID.randomUUID().toString());
        return this;
    }

    public JaaSJwtBuilder withDefaults() {
        long now = Instant.now().getEpochSecond();
        jwtBuilder.withClaim("exp", now + 7200) // 2 hours expiry
                .withClaim("nbf", now - 10)
                .withClaim("room", "*")
                .withClaim("iss", "chat")
                .withClaim("aud", "jitsi");
        featureClaims.put("recording", true);
        featureClaims.put("livestreaming", true);
        featureClaims.put("transcription", true);
        return this;
    }

    public String signWith(RSAPrivateKey privateKey) {
        Algorithm algorithm = Algorithm.RSA256(null, privateKey);
        Map<String, Object> context = new HashMap<>();
        context.put("user", userClaims);
        context.put("features", featureClaims);
        return jwtBuilder.withClaim("context", context).sign(algorithm);
    }

}
