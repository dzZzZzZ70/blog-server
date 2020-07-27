package com.dz.blogserver.entity.management;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ApplicationPermission {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200, nullable = false)
    private String permission;

    @Column(nullable = false)
    private Date createDate;

    @Column
    private Date updateDate;

    @Column(length = 100)
    private String editUser;

    @Column(length = 2, nullable = false)
    private String dataFla;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ApplicationRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<ApplicationRole> applicationRoleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public String getDataFla() {
        return dataFla;
    }

    public void setDataFla(String dataFla) {
        this.dataFla = dataFla;
    }

    public List<ApplicationRole> getApplicationRoleList() {
        return applicationRoleList;
    }

    public void setApplicationRoleList(List<ApplicationRole> applicationRoleList) {
        this.applicationRoleList = applicationRoleList;
    }
}
