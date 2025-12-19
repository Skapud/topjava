package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "user.duplicateEmail";
    public static final String EXCEPTION_DUPLICATE_DATETIME = "meal.duplicateDateTime";

    private static MessageSourceAccessor messageSourceAccessor;

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "meal_unique_user_datetime_idx", EXCEPTION_DUPLICATE_DATETIME);

    public ExceptionInfoHandler(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFoundError(HttpServletRequest req, NotFoundException e) {
        String message = ValidationUtil.getRootCause(e).getMessage();
        return logAndGetErrorInfo(req, e,false, DATA_NOT_FOUND, message);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String message = ValidationUtil.getRootCause(e).getMessage();
        String lowerCaseMessage = message.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
                if (lowerCaseMessage.contains(entry.getKey())) {
                    message = messageSourceAccessor.getMessage(entry.getValue());
                }
            }
        return logAndGetErrorInfo(req, e,false, DATA_ERROR, message);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo validationError(HttpServletRequest req, Exception e) {
        String message = ValidationUtil.getRootCause(e).getMessage();
        return logAndGetErrorInfo(req, e,false, VALIDATION_ERROR, message);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo validationUIError(HttpServletRequest req, BindException e) {
        String[] messages = e.getFieldErrors().stream()
                .map(error -> "[" + error.getField() + "] " + error.getDefaultMessage())
                .toArray(String[]::new);
        return logAndGetErrorInfo(req, e,false, VALIDATION_ERROR, messages);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo internalError(HttpServletRequest req, Exception e) {
        String message = ValidationUtil.getRootCause(e).getMessage();
        return logAndGetErrorInfo(req, e,true, APP_ERROR, message);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... message) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        CharSequence url = req.getRequestURL();
        if (logException) {
            log.error(errorType + " at request " + url, rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, url, message);
        }
        return new ErrorInfo(url, errorType, message);
    }
}