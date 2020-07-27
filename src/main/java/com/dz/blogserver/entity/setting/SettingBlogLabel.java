package com.dz.blogserver.entity.setting;

import com.dz.blogserver.entity.management.ApplicationUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SettingBlogLabel implements Serializable {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 30, nullable = false)
    private String label;

    @Column(length = 2, nullable = false)
    private String able;

    @Column(length = 100)
    private String editUser;

    @Column(nullable = false)
    private Date createDate;

    private Date updateDate;

    @Column(length = 2, nullable = false)
    private String dataFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser applicationUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getAble() {
        return able;
    }

    public void setAble(String able) {
        this.able = able;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }
}
