package com.aiolos.ad.service.impl;

import com.aiolos.ad.constant.Constants;
import com.aiolos.ad.dao.AdUserRepository;
import com.aiolos.ad.entity.AdUser;
import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.service.IUserService;
import com.aiolos.ad.utils.CommonUtils;
import com.aiolos.ad.vo.CreateUserRequest;
import com.aiolos.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aiolos
 * @date 2019-01-23 21:31
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
