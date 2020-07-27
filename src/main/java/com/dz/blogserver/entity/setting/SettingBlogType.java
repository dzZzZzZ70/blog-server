package com.dz.blogserver.entity.setting;

import com.dz.blogserver.entity.management.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SettingBlogType implements Serializable {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 100)
    private String parentId;

    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private String editUser;

    private Date updateDate;

    @Column(length = 2, nullable = false)
    private String dataFlag;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private ApplicationUser applicationUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
}
