package waa.labs.lab5.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.request.RegisterRequestDto;
import waa.labs.lab5.dtos.response.MessageResponseDto;
import waa.labs.lab5.services.IAuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        var loginResponse = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        try {
            return ResponseEntity.ok(authService.register(registerRequestDto));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(ex.getMessage()));
        }

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto token) {
        try {
            return ResponseEntity.ok().body(authService.refreshToken(token));
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(ex.getMessage()));
        }
    }
}
