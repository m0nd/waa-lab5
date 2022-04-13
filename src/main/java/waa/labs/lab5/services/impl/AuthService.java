package waa.labs.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.request.RegisterRequestDto;
import waa.labs.lab5.dtos.response.LoginResponseDto;
import waa.labs.lab5.dtos.response.MessageResponseDto;
import waa.labs.lab5.dtos.response.RefreshTokenResponseDto;
import waa.labs.lab5.entities.RefreshToken;
import waa.labs.lab5.entities.User;
import waa.labs.lab5.repositories.IRefreshTokenRepo;
import waa.labs.lab5.repositories.IRoleRepo;
import waa.labs.lab5.repositories.IUserRepo;
import waa.labs.lab5.services.IAuthService;
import waa.labs.lab5.utils.JwtUtil;
import waa.labs.lab5.utils.UserRole;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService implements IAuthService {
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final IUserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final IRefreshTokenRepo refreshTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final IRoleRepo roleRepo;

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
        final Lab5UserDetails userDetails = (Lab5UserDetails) userDetailsService.loadUserByUsername(loginRequest.getEmail());

        var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        // Generate the access token with claims containing the user roles
        var claims = new HashMap<String, Object>();
        claims.put("roles", roles);
        final String accessToken = jwtUtil.buildToken(claims, userDetails.getUsername());

        // Generate refresh token
        final String refreshTokenStr = jwtUtil.generateRefreshToken(loginRequest.getEmail());

        // Check for an existing refresh token for current user in the DB
        RefreshToken refreshToken = refreshTokenRepo.findByAssociatedUser_Id(userDetails.getId()).orElse(null);

        if (refreshToken == null) {
            refreshToken = new RefreshToken(refreshTokenStr);
        }
        refreshToken.setAssociatedUser(userRepo.findById(userDetails.getId()).orElse(null));

        // Save refresh token to DB
        refreshTokenRepo.save(refreshToken);

        return new LoginResponseDto(accessToken, refreshTokenStr);
    }

    @Override
    public MessageResponseDto register(RegisterRequestDto registerRequestDto) throws Exception {
        // check if email already exists
        if (userRepo.findByEmail(registerRequestDto.getEmail()) != null) {
            throw new Exception("Email already in use");
        }

        User newUser = new User();
        modelMapper.map(registerRequestDto, newUser);

        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        // Add the roles
        for (String role: registerRequestDto.getRoles()) {
            var currentRole = roleRepo.findByRole(UserRole.valueOf(role.toUpperCase(Locale.ROOT)));
            if (currentRole == null) {
                throw new Exception("Invalid Role specified for user");
            }
            newUser.addRole(currentRole);
        }

        userRepo.save(newUser);
        return new MessageResponseDto("User successfully registered");
    }



    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenDto) throws Exception {
        String userRefreshToken = refreshTokenDto.getRefreshToken();

        // check if the token submitted by the user is present in the DB
        var storedRefreshToken = refreshTokenRepo.findByBody(userRefreshToken).orElse(null);

        // If the user did not previously have a refresh token then they cannot be given a refresh/access token
        if (storedRefreshToken == null || !storedRefreshToken.getBody().equals(userRefreshToken)) {
            throw new Exception("Invalid Refresh Token Submitted");
        }

        // Validate the user's refresh token
        if (!jwtUtil.validateToken(userRefreshToken)) {
            throw new Exception("Refresh Token may have Expired");
        }

        String subject = jwtUtil.getSubjectFromToken(userRefreshToken);

        final Lab5UserDetails userDetails = (Lab5UserDetails) userDetailsService.loadUserByUsername(subject);
        var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        // Generate the access token with claims containing the user roles
        var claims = new HashMap<String, Object>();
        claims.put("roles", roles);

        String newAccessToken = jwtUtil.buildToken(claims, subject);
        String newRefreshToken = jwtUtil.generateRefreshToken(subject);

        // Update the refresh token from the DB with the newly generated refresh token
        storedRefreshToken.setBody(newRefreshToken);
        refreshTokenRepo.save(storedRefreshToken);

        return new RefreshTokenResponseDto(newAccessToken, newRefreshToken);
    }
}
