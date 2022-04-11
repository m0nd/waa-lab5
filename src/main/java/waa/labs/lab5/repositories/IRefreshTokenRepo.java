package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.RefreshToken;

public interface IRefreshTokenRepo extends CrudRepository<RefreshToken, Integer> {
}
