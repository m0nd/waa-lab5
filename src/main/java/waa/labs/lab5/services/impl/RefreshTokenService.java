package waa.labs.lab5.services.impl;

import waa.labs.lab5.entities.RefreshToken;
import waa.labs.lab5.repositories.IRefreshTokenRepo;
import waa.labs.lab5.services.IRefreshTokenService;

public class RefreshTokenService implements IRefreshTokenService {
    IRefreshTokenRepo refreshTokenRepo;

    @Override
    public void saveRefreshToken(RefreshToken token) {
        refreshTokenRepo.save(token);
    }
}
