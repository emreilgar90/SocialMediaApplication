package com.emreilgar.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static com.emreilgar.exception.ErrorType.*;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    /**Exception hataların çatısıdır, diğer yazılımsal tüm hataların yakalanmasında yeterlidir.
     * Exception dan türetilmiştir.*/
    public ResponseEntity<ErrorMessage> handlerRuntimeException(RuntimeException exception){
        System.out.println("Hata oluştu");
        ErrorType errorType = INTERNAL_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /***********************************************************************************************/



    /**Bizim kendi oluşturduğumuz ErrorType'larını dönen metot.**/
    @ExceptionHandler(UserManagerException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlerAuthMicroserviceException(UserManagerException exception){
        return new ResponseEntity<>(createErrorMessage(exception,exception.getErrorType()), HttpStatus.BAD_REQUEST);
    }



    /*****Hazır metotlar******/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ErrorType errorType = BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        ErrorType errorType = BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {
        ErrorType errorType = BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MissingPathVariableException exception) {

        ErrorType errorType = BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ErrorType errorType = BAD_REQUEST;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(exception,errorType);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }




  /***********************************************************************************************/





    /**Hata yakalama işlemleri bir çok hata için ayrı ayrı yapılmalıdır. Bu nedenle tüm hataların
     içerisine log alma işlemi yazmak zorunda kalırız. By işlemleri tekelleştirmek ve hata log kayıtlarını
     toplamak için tek bir method kullanmak daha doğru olacaktır.*/
    private ErrorMessage createErrorMessage(Exception exception, ErrorType errorType){
        System.out.println("Tüm hataların geçtiği nokta..."+exception.getMessage());
        return ErrorMessage.builder()
                .message(errorType.getMessage())
                .code(errorType.getCode())
                .build();
    }
}
