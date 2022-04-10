package waa.labs.lab5.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import waa.labs.lab5.dtos.UserDto;
import waa.labs.lab5.entities.ExceptionTracker;
import waa.labs.lab5.entities.Logger;
import waa.labs.lab5.services.IExceptionTrackerService;
import waa.labs.lab5.services.ILoggerService;
import waa.labs.lab5.services.IUserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    ILoggerService loggerService;
    IExceptionTrackerService exceptionTrackerService;
    IUserService userService;

    public AdminController(ILoggerService loggerService, IExceptionTrackerService exceptionTrackerService, IUserService userService) {
        this.loggerService = loggerService;
        this.exceptionTrackerService = exceptionTrackerService;
        this.userService = userService;
    }

    @GetMapping
    public UserDto getAdminInfo(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @GetMapping("/logs")
    public List<Logger> getAllLogs() {
        return loggerService.getAllLogs();
    }

    @GetMapping("/exceptions")
    public List<ExceptionTracker> getAllExceptions() {
        return exceptionTrackerService.getAllExceptions();
    }

}
