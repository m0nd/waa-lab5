package waa.labs.lab5.services;

import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.response.LoginResponseDto;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    LoginResponseDto refreshToken(RefreshTokenRequestDto token);
}
