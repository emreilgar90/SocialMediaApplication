package com.emreilgar.repository.entity;

import com.emreilgar.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data //getter setter
@Builder
@Document
public class UserProfile implements Serializable { //seriazable redis için yaptık sonradan
    @Id
    private String id;
    private Long authId;
    @Indexed(unique = true)
    private String username;
    private String name;      //update
    private String email;     //update
    private String phone;     //update
    private String avatar;    //update
    private String address;   //update
    private String about;     //update
    @Builder.Default
    private Status status= Status.PENDING;
    Long createDate;
    Long updatedDate;


}
