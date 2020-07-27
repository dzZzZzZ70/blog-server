package com.dz.blogserver.vo.setting;

import java.util.Date;

public class SettingBlogLabelVo {
    private String label;
    private String able;
    private String editUser;
    private Date createDate;
    private Date updateDate;
    private String dataFlag;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAble() {
        return able;
    }

    public void setAble(String able) {
        this.able = able;
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

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    @Override
    public String toString() {
        return "SettingBlogLabelVo{" +
                "label='" + label + '\'' +
                ", able='" + able + '\'' +
                ", editUser='" + editUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", dataFlag='" + dataFlag + '\'' +
                '}';
    }
}
