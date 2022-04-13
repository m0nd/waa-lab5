package waa.labs.lab5.services;

import waa.labs.lab5.dtos.request.LoginRequestDto;
import waa.labs.lab5.dtos.request.RefreshTokenRequestDto;
import waa.labs.lab5.dtos.request.RegisterRequestDto;
import waa.labs.lab5.dtos.response.LoginResponseDto;
import waa.labs.lab5.dtos.response.MessageResponseDto;
import waa.labs.lab5.dtos.response.RefreshTokenResponseDto;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception;
    MessageResponseDto register(RegisterRequestDto registerRequestDto) throws Exception;
    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto token) throws Exception;
}
