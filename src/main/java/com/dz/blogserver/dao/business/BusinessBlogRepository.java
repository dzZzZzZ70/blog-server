package com.dz.blogserver.dao.business;

import com.dz.blogserver.entity.business.BusinessBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface BusinessBlogRepository extends CrudRepository<BusinessBlog, String>, JpaSpecificationExecutor<BusinessBlog> {
    List<BusinessBlog> findByCreateUserAndDataFlag(String createUser, String dataFlag);
    @Query(value = "SELECT" +
        " a.id, a.type, COUNT(b.id) AS qty " +
        "FROM SettingBlogType a LEFT JOIN BusinessBlog b ON a.id = b.blogTypeId" +
        "  WHERE a.dataFlag = '0' AND a.applicationUser = ?1 \n" +
        "GROUP BY \n" +
        "  a.id,\n" +
        "\t a.type")
    List<BusinessBlog> countByType(String crateUser);
    Page<BusinessBlog> findByBlogTypeIdAndDataFlag(String blogTypeId, String dataFlag, Pageable pageable);

    BusinessBlog findByIdAndDataFlag(String id, String dataFlag);
    Integer countByCreateUserAndDataFlagAndTitleAndBlogType(String userId, String dataFlag, String title, String blogType);
}
