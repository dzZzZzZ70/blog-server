package com.dz.blogserver.vo.result;

import com.dz.blogserver.ApplicationConstants;

import java.util.List;

public class ResultEntity {
    private final static String ERROR_MESSAGE = "未知异常,请联系系统管理员.";
    private String flag;
    private String message;
    private Object result;
    private PageEntity pageEntity;

    public ResultEntity() {
        this.flag = ApplicationConstants.ResultFlag.SUCCESS.getIndex();
    }

    public boolean isSuccess() {
        if (ApplicationConstants.ResultFlag.SUCCESS.getIndex().equals(this.flag)) {
            return true;
        } else {
            return false;
        }
    }

    public void failed(String resultMessage) {
        this.flag = ApplicationConstants.ResultFlag.FAILED.getIndex();
        this.message = resultMessage;
    }

    public void failed() {
        this.flag = ApplicationConstants.ResultFlag.FAILED.getIndex();
    }

    public void success() {
        this.flag = ApplicationConstants.ResultFlag.SUCCESS.getIndex();
    }

    public void error(String resultMessage) {
        this.flag = ApplicationConstants.ResultFlag.ERROR.getIndex();
        this.message = resultMessage;
    }

    public void error() {
        this.flag = ApplicationConstants.ResultFlag.ERROR.getIndex();
        this.message = ERROR_MESSAGE;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public PageEntity getPageEntity() {
        return pageEntity;
    }

    public void setPageEntity(PageEntity pageEntity) {
        this.pageEntity = pageEntity;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", pageEntity=" + pageEntity +
                '}';
    }
}
