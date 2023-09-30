package com.emreilgar.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequestDto {
    @NotBlank
    @Size(min =3, max =20,message = "Şifre en az 3 maksimum 32 karakter olmalıdır.")
    private String username;
    @NotBlank
    @Size(min =8, max =20,message = "Şifre en az 8 maksimum 32 karakter olmalıdır.")
    private String password;


}
