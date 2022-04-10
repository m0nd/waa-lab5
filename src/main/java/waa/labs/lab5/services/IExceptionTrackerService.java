package waa.labs.lab5.services;

import waa.labs.lab5.entities.ExceptionTracker;

import java.util.List;

public interface IExceptionTrackerService {
    List<ExceptionTracker> getAllExceptions();
}
