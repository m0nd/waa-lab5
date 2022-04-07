package waa.labs.lab5.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // Note: Normally we would store our secret in an environment var
    private final String secret = "not-so-secret";
    private final long expiration = 5 * 60 * 60 * 60; // ~18mins

    /*
    Take the user details and use the helper method doGenerateToken()
    to do the "dirty work" of generating and returning a token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return GenerateTokenWithUserDetails(claims, userDetails.getUsername());
    }

    public String GenerateTokenWithUserDetails(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }
}
