package waa.labs.lab5.services.impl;

import org.springframework.stereotype.Service;
import waa.labs.lab5.entities.RefreshToken;
import waa.labs.lab5.repositories.IRefreshTokenRepo;
import waa.labs.lab5.services.IRefreshTokenService;

import javax.transaction.Transactional;

@Service
@Transactional
public class RefreshTokenService implements IRefreshTokenService {
    private IRefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService(IRefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Override
    public void saveRefreshToken(RefreshToken token) {
        refreshTokenRepo.save(token);
    }
}
