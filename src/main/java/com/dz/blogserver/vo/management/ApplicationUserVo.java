package com.dz.blogserver.vo.management;

public class ApplicationUserVo {
    private String id;
    private String userAccount;
    private String password;
    private String userName;

//    private List<ApplicationRole> applicationRolesList;

    public String getId() {
        return id;
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
}
