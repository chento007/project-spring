package co.istad.photostad.exception;

import co.istad.photostad.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {

    /**
     * use to handle when client forget put data in json body
     *
     * @param e exception
     * @return message specific error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseError<?> handleBodyJsonException(HttpMessageNotReadableException e) {

        return BaseError.builder()
                .message("something when wrong ... please check")
                .status(false)
                .timestamp(LocalDateTime.now())
                .code(500)
                .errors(e.getMessage())
                .build();

    }

    /**
     * use to handle when meet some problem in our program
     *
     * @param e exception
     * @return message specific error
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleServiceImp(ResponseStatusException e) {

        return BaseError.builder()
                .message("something when wrong ... please check")
                .status(false)
                .timestamp(LocalDateTime.now())
                .code(e.getStatusCode().value())
                .errors(e.getReason())
                .build();

    }

    /**
     * use to handle when client forget in fin then message require
     *
     * @param e exception
     * @return message specific error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e) {

        List<Map<String, String>> body = new ArrayList<>();

        for (FieldError error : e.getFieldErrors()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("name", error.getField());
            errorDetails.put("message", error.getDefaultMessage());
            body.add(errorDetails);
        }

        return BaseError.builder()
                .message("Validation is error , please check message detail")
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(body)
                .build();

    }


    /**
     * use to handle when upload image to high
     *
     * @param e exception
     * @return message specific error
     */
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseError<?> handleValidationException(MaxUploadSizeExceededException e) {

        return BaseError.builder()
                .status(false)
                .code(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .message("something when wrong  ... please check")
                .timestamp(LocalDateTime.now())
                .errors("Maximum upload size exceeded: 1000KB")
                .build();

    }


    /**
     * use to handle when it doesn't have resource upload
     *
     * @param e exception
     * @return message specific error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MultipartException.class)
    public BaseError<?> handleMultipartException(MultipartException e) {

        return BaseError.builder()
                .status(false)
                .code(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .message("something when wrong ... please check")
                .timestamp(LocalDateTime.now())
                .errors("Current request is not a multipart request")
                .build();

    }
}
