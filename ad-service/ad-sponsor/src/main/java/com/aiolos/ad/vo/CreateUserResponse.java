package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Aiolos
 * @date 2019-01-23 21:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private Long userId;
    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;
}
