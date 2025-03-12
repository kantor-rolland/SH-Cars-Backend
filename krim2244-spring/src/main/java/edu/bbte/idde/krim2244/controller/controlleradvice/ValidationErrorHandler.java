package edu.bbte.idde.krim2244.controller.controlleradvice;

import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class ValidationErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final Stream<String> handleConstraintViolation(ConstraintViolationException e) {
        log.debug("ConstraintViolationException occurred", e);
        return e.getConstraintViolations().stream()
                .map(it -> it.getPropertyPath().toString() + " " + it.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final String handleServiceException(ServiceException e) {
        log.debug("ServiceException occurred", e);
        return e.getMessage();
    }

    @ExceptionHandler(RepoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final String handleServiceException(RepoNotFoundException e) {
        log.debug("RepoNotFoundException occurred", e);
        return e.getMessage();
    }

    // sikertelen adatbazis muveletek
    @ExceptionHandler(RepoOperationFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final String handleServiceException(RepoOperationFailedException e) {
        log.debug("RepoOperationFailedException occurred", e);
        return e.getMessage();
    }
}