package com.dz.blogserver.dao.setting;

import com.dz.blogserver.entity.setting.SettingBlogLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingBlogLabelRepository extends CrudRepository<SettingBlogLabel, String>, JpaSpecificationExecutor<SettingBlogLabel> {
    Integer countByEditUserAndLabelAndDataFlag(String editUser, String label, String dataFlag);
    SettingBlogLabel findByIdAndDataFlag(String id, String dataFlag);
    List<SettingBlogLabel> findByEditUser(String editUser);
}
