package com.ruoyi.website.domain.dto;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponseDTO {

    private String token;
    private String username;
    private String realName;
    private String avatar;

}
