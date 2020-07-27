package com.dz.blogserver.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dz
 * @date 2020/7/10
 * @time 22:59
 */
public class UserAccountQuery {
    @NotNull(message = "账户不能为空")
    @NotEmpty(message = "账户不能为空")
    private String userAccount;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "UserAccountQuery{" +
                "userAccount='" + userAccount + '\'' +
                '}';
    }
}
