package com.voting.web;

import com.voting.util.ValidationUtil;
import com.voting.util.exception.ErrorInfo;
import com.voting.util.exception.ErrorType;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.VoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.voting.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_LUNCH = "Lunch for this restaurant and date already exist";

    private static final Map<String, String> CONSTRAINS_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "lunches_unique_restaurant_lunch_date_idx", EXCEPTION_DUPLICATE_LUNCH);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NotFoundException.class})
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({VoteException.class})
    public ErrorInfo handleError(HttpServletRequest req, VoteException e) {
        return logAndGetErrorInfo(req, e, false, VOTE_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();

        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINS_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, entry.getValue());
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType,
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }
}
