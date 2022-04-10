package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.ExceptionTracker;

import java.util.List;

public interface IExceptionTrackerRepo extends CrudRepository<ExceptionTracker, Long> {
    List<ExceptionTracker> findAll();
}
