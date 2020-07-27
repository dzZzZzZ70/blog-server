package com.dz.blogserver.entity.management;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ApplicationRole {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100)
    private String roleName;

    @Column(nullable = false)
    private Date createDate;

    @Column
    private Date updateDate;

    @Column(length = 100)
    private String editUser;

    @Column(length = 2, nullable = false)
    private String dataFlag;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ApplicationUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<ApplicationUser> applicationUserList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ApplicationRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<ApplicationPermission> applicationPermissionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public List<ApplicationUser> getApplicationUserList() {
        return applicationUserList;
    }

    public void setApplicationUserList(List<ApplicationUser> applicationUserList) {
        this.applicationUserList = applicationUserList;
    }

    public List<ApplicationPermission> getApplicationPermissionList() {
        return applicationPermissionList;
    }

    public void setApplicationPermissionList(List<ApplicationPermission> applicationPermissionList) {
        this.applicationPermissionList = applicationPermissionList;
    }
}
