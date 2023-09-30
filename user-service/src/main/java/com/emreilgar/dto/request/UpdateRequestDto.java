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
public class UpdateRequestDto {
    private String token;
    @NotBlank
    @Size(min =3, max =20,message = "Şifre en az 3 maksimum 32 karakter olmalıdır.")
    private String username;  //update
    private String name;      //update
    @Email
    private String email;     //update
    private String phone;     //update
    private String avatar;    //update
    private String address;   //update
    private String about;     //update

}
