package waa.labs.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.response.LoginResponseDto;
import waa.labs.lab5.services.IAuthService;
import waa.labs.lab5.utils.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            /*
             Authenticate the principal (user) using the username and
             password from the loginRequest
             */
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }
        catch (BadCredentialsException bcEx) {
            log.info("Bad Credentials Specified");
        }
        // Get user details using our UserDetailsService
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        // Generate our access and refresh tokens
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken();
        
        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto token) {
        return null;
    }
}
