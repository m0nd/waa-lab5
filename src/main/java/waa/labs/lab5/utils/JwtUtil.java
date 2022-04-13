package waa.labs.lab5.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // Note: Normally we would store our secret in an environment var
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long accessExpiration = 2 * 60 * 1000; // 2 mins
    private final long refreshExpiration = 30 * 60 * 1000; // ~30 mins

    /*
    Take the user details and use the helper method doGenerateToken()
    to do the "dirty work" of generating and returning a token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return buildToken(claims, userDetails.getUsername());
    }

    public String generateAccessToken(String subject) {
        return buildToken(subject, accessExpiration);
    }

    public String buildToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String buildToken(String subject, long expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        return buildToken(subject, refreshExpiration);
    }

    public String getSubjectFromToken(String token) {
        String result = null;
        try {
            result = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        catch(ExpiredJwtException eJwtEx) {
            System.out.println(eJwtEx.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            // at this point with no exception thrown from parseClaimsJws() we will have a valid token
            return true;
        }
        catch(SignatureException sigEx) {
            System.out.println(sigEx.getMessage());
        }
        catch(JwtException jwtEx) {
            System.out.println(jwtEx.getMessage());
        }

        return false;
    }
}
