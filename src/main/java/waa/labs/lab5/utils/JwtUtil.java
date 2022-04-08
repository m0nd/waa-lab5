package waa.labs.lab5.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // Note: Normally we would store our secret in an environment var
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
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
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
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

        // might need to place this return in a finally block?
        return false;
    }
}
