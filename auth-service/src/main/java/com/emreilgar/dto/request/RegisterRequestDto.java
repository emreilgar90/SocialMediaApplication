package com.emreilgar.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotBlank
    @Size(min =3, max =20,message = "Şifre en az 3 maksimum 32 karakter olmalıdır.")
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min =8, max =20,message = "Şifre en az 8 maksimum 32 karakter olmalıdır.")
    private String password;

}
