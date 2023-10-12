package com.emreilgar.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ErrorType {
        INTERNAL_ERROR(5200,"Sunucuda beklenmeyen hata oluştu",HttpStatus.INTERNAL_SERVER_ERROR),
        BAD_REQUEST(4300,"Parametre Hatası",HttpStatus.BAD_REQUEST),
        POST_NOT_FOUND(4310,"Post bulunumadı.",HttpStatus.BAD_REQUEST),
        POST_NOT_CREATED(4311,"Post oluşturulamadı !" ,HttpStatus.BAD_REQUEST);

        private int code;
        private String message;
        HttpStatus httpStatus;


}
