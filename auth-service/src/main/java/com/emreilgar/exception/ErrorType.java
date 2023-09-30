package com.emreilgar.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ErrorType {
        LOGIN_ERROR_WRONG(1000, "Kullanıcı adı yada şifre hatalı", INTERNAL_SERVER_ERROR),
        INVALID_ACTIVATE_CODE(1006, "Activate Code Bulunamdı", INTERNAL_SERVER_ERROR),
        BAD_REQUEST(4100,"Parametre Hatası",HttpStatus.BAD_REQUEST),
        USERNAME_DUPLICATE(4110,"Kullanıcı adı kayıtlı !" ,HttpStatus.BAD_REQUEST),
        USER_NOT_FOUND(4111,"Kullanıcı bulunamadı" ,HttpStatus.BAD_REQUEST),
        LOGIN_ERROR(4112,"Kullanıcı adı veya şifre hatalı" ,HttpStatus.BAD_REQUEST),
        STATUS_NOT_FOUND(4113,"Böyle bir kullanıcı durumu bulunamadı" ,HttpStatus.BAD_REQUEST),
        ROLE_NOT_FOUND(4114,"Böyle bir kullanıcı role bulunamadı" ,HttpStatus.BAD_REQUEST),
        USER_NOT_CREATED(4116,"Kullanıcı Olusturalamadı",HttpStatus.BAD_REQUEST),
        LOGIN_STATUS_ERROR(4117,"Kullanıcı Aktif değil,lütfen hesabınızı aktif ediniz",HttpStatus.BAD_REQUEST),
        INVALID_TOKEN(4117,"Geçersiz Token",HttpStatus.BAD_REQUEST),
        INTERNAL_ERROR(5100,"Sunucuda beklenmeyen hata oluştu",HttpStatus.INTERNAL_SERVER_ERROR);






        int code;
    String message;
    HttpStatus httpStatus;


}
