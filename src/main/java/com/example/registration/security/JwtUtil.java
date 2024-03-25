package com.example.registration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

@Configuration
public class JwtUtil {
@Value("${jwt_secret}")
    private String secret;
    public String generateToken(String email, String role) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
                .withClaim("authorities", role)
                .withIssuedAt(new Date())
                .withIssuer("registration")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("registration")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
