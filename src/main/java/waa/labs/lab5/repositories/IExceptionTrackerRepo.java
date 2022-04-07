package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.ExceptionTracker;

public interface IExceptionTrackerRepo extends CrudRepository<ExceptionTracker, Long> {
}
