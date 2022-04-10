package waa.labs.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waa.labs.lab5.entities.Logger;
import waa.labs.lab5.repositories.ILoggerRepo;
import waa.labs.lab5.services.ILoggerService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LoggerService implements ILoggerService {
    ILoggerRepo loggerRepo;

    public LoggerService(ILoggerRepo loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    @Override
    public List<Logger> getAllLogs() {
        return loggerRepo.findAll();
    }
}
