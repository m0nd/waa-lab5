package waa.labs.lab5.filters;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import waa.labs.lab5.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.getUsernameFromToken(token);
            }
            catch(ExpiredJwtException eJwtEx) {
                System.out.println("JWToken has expired: " + eJwtEx.getMessage());
            }
        }
        else {
            System.out.println("No Auth Header / No Bearer Token Detected");
        }

        /*
         Since our auth is stateless on the server - set in our config by:
          sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         we ensure that there is no auth in the server session for the current request
         before validating the token
         */
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Get the user identity/details so we can later set it on the Security Context
            var userDetails = userDetailsService.loadUserByUsername(email);
            boolean isValidToken = jwtUtil.validateToken(token);
            if (isValidToken) {
                // Get an authentication instance using the user details
                UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set the details of our auth instance using a Details object obtained from the HTTP request
                userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set our Security Context with our user auth
                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }

        // Continue along the filter chain
        filterChain.doFilter(request, response);
    }
}
