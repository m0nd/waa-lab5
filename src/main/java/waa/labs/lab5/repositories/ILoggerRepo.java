package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.Logger;

public interface ILoggerRepo extends CrudRepository<Logger, Long> {
}
