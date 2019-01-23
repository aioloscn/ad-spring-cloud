package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Aiolos
 * @date 2019-01-23 21:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String username;

    public boolean validate() {

        return !StringUtils.isEmpty(username);
    }
}
