package com.emreilgar.dto.response;

import com.emreilgar.repository.enums.Status;
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
public class UpdateResponseDto {

    private String username;  //update
    private String name;      //update

    private String email;     //update
    private String phone;     //update
    private String avatar;    //update
    private String address;   //update
    private String about;     //update
    long updatedDate;
    private Status status;

}
