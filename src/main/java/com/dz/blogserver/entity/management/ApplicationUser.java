package com.dz.blogserver.entity.management;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ApplicationUser {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100, nullable = false)
    private String userAccount;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String userName;

    @Column(nullable = false)
    private Date createDate;

    @Column
    private Date updateDate;

    @Column(length = 100)
    private String editUser;

    @Column(length = 2, nullable = false)
    private String dataFlag;

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "ApplicationUserRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<ApplicationRole> applicationRolesList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<ApplicationRole> getApplicationRolesList() {
        return applicationRolesList;
    }

    public void setApplicationRolesList(List<ApplicationRole> applicationRolesList) {
        this.applicationRolesList = applicationRolesList;
    }
}
