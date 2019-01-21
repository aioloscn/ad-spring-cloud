package com.aiolos.ad.dao;

import com.aiolos.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aiolos
 * @date 2019-01-21 23:25
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * <2>根据用户名查找用户记录   </2>
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
