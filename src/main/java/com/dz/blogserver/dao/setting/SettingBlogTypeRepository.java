package com.dz.blogserver.dao.setting;

import com.dz.blogserver.entity.setting.SettingBlogType;
import com.dz.blogserver.vo.setting.SettingBlogTypeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingBlogTypeRepository extends CrudRepository<SettingBlogType, String>, JpaSpecificationExecutor<SettingBlogType> {
    SettingBlogType findByIdAndDataFlag(String id, String dataFlag);
    Integer countByEditUserAndTypeAndDataFlag(String editUser, String type, String dataFlag);
    Page<SettingBlogType> findByEditUserAndDataFlag(String editUser, String dataFlag, Pageable page);
    List<SettingBlogType> findByEditUserAndDataFlag(String editUser, String dataFlag);
}
