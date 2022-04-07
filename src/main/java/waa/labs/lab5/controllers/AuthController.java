package waa.labs.lab5.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.services.IAuthService;

@RestController
@RequestMapping("/api/v1/authenticate")
@CrossOrigin
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        var loginResponse = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto token) {
        return ResponseEntity.ok().body(authService.refreshToken(token));
    }
}
