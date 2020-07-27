package com.dz.blogserver.vo.setting;

import com.dz.blogserver.vo.PageQuery;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dz
 * @date 2020/7/10
 * @time 21:24
 */
public class SettingBlogTypeQuery extends PageQuery {
    @NotNull(message = "所属用户不能为空")
    @NotEmpty(message = "所属用户不能为空")
    private String editUser;

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }
}
