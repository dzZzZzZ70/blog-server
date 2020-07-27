package com.dz.blogserver.dao.management;

import com.dz.blogserver.entity.management.ApplicationUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, String>, JpaSpecificationExecutor<ApplicationUser> {
    ApplicationUser findByUserAccount(String userAccount);
}
