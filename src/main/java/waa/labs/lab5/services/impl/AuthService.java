package waa.labs.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.response.LoginResponseDto;
import waa.labs.lab5.entities.RefreshToken;
import waa.labs.lab5.services.IAuthService;
import waa.labs.lab5.services.IRefreshTokenService;
import waa.labs.lab5.utils.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    IRefreshTokenService refreshTokenService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            /*
             Authenticate the principal (user) using the username and
             password from the loginRequest
             */
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }
        catch (BadCredentialsException bcEx) {
            System.out.println("Bad Credentials Specified");
            log.info("Bad Credentials Specified");
        }
        catch (AuthenticationException authEx) {
            System.out.println(authEx.getMessage());
        }

        // Get user details using our UserDetailsService
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Generate our access and refresh tokens
        final String accessToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getEmail());

        // Save refresh token to DB
        refreshTokenService.saveRefreshToken(new RefreshToken(refreshToken));

        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto token) {

        return null;
    }
}
