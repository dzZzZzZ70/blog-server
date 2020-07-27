package com.dz.blogserver.dao.business;

import com.dz.blogserver.entity.business.BusinessBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusinessBlogRepository extends CrudRepository<BusinessBlog, String>, JpaSpecificationExecutor<BusinessBlog> {
    List<BusinessBlog> findByCreateUserAndDataFlag(String createUser, String dataFlag);

    Page<BusinessBlog> findByBlogTypeIdAndDataFlag(String blogTypeId, String dataFlag, Pageable pageable);

    BusinessBlog findByIdAndDataFlag(String id, String dataFlag);
    Integer countByCreateUserAndDataFlagAndTitleAndBlogType(String userId, String dataFlag, String title, String blogType);
}
