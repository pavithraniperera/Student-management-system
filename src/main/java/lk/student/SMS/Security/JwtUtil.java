package lk.student.SMS.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Inject secret key from application.properties or environment
    private String jwtKey;

    // Extract the username from the token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generate a token for the user
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Validate the token by checking if it's expired and matching username
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Generate a refreshed token
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(), userDetails);
    }

    // Extract a specific claim from the token
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Parse the token and extract all claims
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generate a signing key from the secret key
    private Key getSignKey() {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(jwtKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    // Generate the JWT token with claims, subject, and expiration
    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        claims.put("role", userDetails.getAuthorities());
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 60 * 60 * 24); // Token expires in 24 hours

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generate a refresh token
    private String refreshToken(Map<String, Object> claims, UserDetails userDetails) {
        claims.put("role", userDetails.getAuthorities());
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 60 * 60 * 24); // New access token expires in 24 hours
        Date refreshExpire = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 7); // Refresh token expires in 7 days

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
