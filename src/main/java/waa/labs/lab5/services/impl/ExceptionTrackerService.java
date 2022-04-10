package waa.labs.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waa.labs.lab5.entities.ExceptionTracker;
import waa.labs.lab5.repositories.IExceptionTrackerRepo;
import waa.labs.lab5.services.IExceptionTrackerService;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional
public class ExceptionTrackerService implements IExceptionTrackerService {
    IExceptionTrackerRepo exceptionTrackerRepo;

    public ExceptionTrackerService(IExceptionTrackerRepo exceptionTrackerRepo) {
        this.exceptionTrackerRepo = exceptionTrackerRepo;
    }

    @Override
    public List<ExceptionTracker> getAllExceptions() {
        return exceptionTrackerRepo.findAll();
    }
}
