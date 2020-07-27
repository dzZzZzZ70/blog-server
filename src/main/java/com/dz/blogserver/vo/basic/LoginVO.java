package com.dz.blogserver.vo.basic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dz
 * @date 2020/7/6
 * @time 10:39
 */
public class LoginVO {
    @NotNull(message = "登录账号不能为空")
    @NotEmpty(message = "登录账号不能为空")
    private String userAccount;

    @NotEmpty(message = "登录密码不能为空")
    @NotNull(message = "登录密码不能为空")
    private String password;

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

    @Override
    public String toString() {
        return "LoginVO{" +
                "userAccount='" + userAccount + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
