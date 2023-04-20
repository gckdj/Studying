package com.microservice.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    // vo에 데이터추가
    private String name;
    private String email;
    private String pwd;

    private String userId;
    private Date createdAt;

    private String encryptedPwd;
}
