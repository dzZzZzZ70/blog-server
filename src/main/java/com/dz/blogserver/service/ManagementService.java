package com.dz.blogserver.service;

import com.dz.blogserver.dao.management.ApplicationUserRepository;
import com.dz.blogserver.entity.management.ApplicationUser;
import com.dz.blogserver.vo.management.ApplicationUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUserVo findByUserAccount(String userAccount) {
        try {
            ApplicationUser applicationUser = applicationUserRepository.findByUserAccount(userAccount);
            ApplicationUserVo applicationUserVo = new ApplicationUserVo();
            BeanUtils.copyProperties(applicationUser, applicationUserVo);

            return applicationUserVo;
        } catch(Exception e) {
            logger.error("根据账号查询用户信息失败");
            e.printStackTrace();

            return null;
        }
    }
}
