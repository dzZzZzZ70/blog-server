package com.dz.blogserver.dao.setting;

import com.dz.blogserver.entity.setting.SettingBlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface SettingBlogTypeRepository extends CrudRepository<SettingBlogType, String>, JpaSpecificationExecutor<SettingBlogType> {
    @Query(value = "SELECT" +
        " a.id AS id, a.type AS type, COUNT(b.id) AS qty " +
        "FROM SettingBlogType a LEFT JOIN BusinessBlog b ON a.id = b.blogTypeId" +
        "  WHERE a.dataFlag = '0' AND a.editUser = ?1 \n" +
        "GROUP BY \n" +
        "  a.id,\n" +
        "\t a.type")
    List<Map<String, Object>> countByType(String crateUser);
    SettingBlogType findByIdAndDataFlag(String id, String dataFlag);
    Integer countByEditUserAndTypeAndDataFlag(String editUser, String type, String dataFlag);
    Page<SettingBlogType> findByEditUserAndDataFlag(String editUser, String dataFlag, Pageable page);
    List<SettingBlogType> findByEditUserAndDataFlag(String editUser, String dataFlag);
}
