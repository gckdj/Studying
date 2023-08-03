package com.spring.usercloud.dto;

import com.spring.usercloud.vo.ResponseOrder;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    // vo에 데이터추가
    private String name;
    private String email;
    private String pwd;

    private String userId;
    private String encryptedPwd;

    List<ResponseOrder> orders;
}
