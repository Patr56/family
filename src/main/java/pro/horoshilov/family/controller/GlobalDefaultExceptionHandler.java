package pro.horoshilov.family.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.horoshilov.family.entity.FailureResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ExceptionHandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse> handleErrorDefault(final Exception ex, final WebRequest request) {
        return prepareError(ex);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<FailureResponse> handleError404(final HttpServletRequest request, final Exception ex) {
        return prepareError(ex);
    }

    private ResponseEntity<FailureResponse> prepareError(final Exception ex) {
        logger.error("Request error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new FailureResponse(ex.getMessage()), HttpStatus.OK);
    }
}
