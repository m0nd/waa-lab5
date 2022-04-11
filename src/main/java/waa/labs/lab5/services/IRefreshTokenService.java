package waa.labs.lab5.services;

import waa.labs.lab5.entities.RefreshToken;

public interface IRefreshTokenService {
    void saveRefreshToken(RefreshToken token);
}
