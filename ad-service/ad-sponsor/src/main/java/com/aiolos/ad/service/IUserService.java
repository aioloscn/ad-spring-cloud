package com.aiolos.ad.service;

import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.vo.CreateUserRequest;
import com.aiolos.ad.vo.CreateUserResponse;

/**
 * @author Aiolos
 * @date 2019-01-23 21:28
 */
public interface IUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
