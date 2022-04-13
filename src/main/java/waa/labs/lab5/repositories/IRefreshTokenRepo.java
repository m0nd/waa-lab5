package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.RefreshToken;

import java.sql.Ref;
import java.util.Optional;

public interface IRefreshTokenRepo extends CrudRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByAssociatedUser_Id(Long userId);
    Optional<RefreshToken> findByBody(String tokenBody);
}
