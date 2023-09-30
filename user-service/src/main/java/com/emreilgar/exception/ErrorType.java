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
        BAD_REQUEST(4200,"Parametre Hatası",HttpStatus.BAD_REQUEST),
        USER_NOT_FOUND(4201,"Kullanıcı bulunumadı.",HttpStatus.BAD_REQUEST),
        USER_NOT_CREATED(4210,"Kullanıcı oluşturulamadı !" ,HttpStatus.BAD_REQUEST),
        INVALID_TOKEN(4211,"Geçersiz token" ,HttpStatus.BAD_REQUEST );

        private int code;
        private String message;
        HttpStatus httpStatus;


}
