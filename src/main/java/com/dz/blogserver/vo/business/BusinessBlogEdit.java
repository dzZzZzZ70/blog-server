package com.dz.blogserver.vo.business;

public class BusinessBlogEdit {
    private String id;
    private String title;
    private String blogTypeId;
    private String blogType;
    private String content;
    private String blogLabel;
    private String userAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlogLabel() {
        return blogLabel;
    }

    public void setBlogLabel(String blogLabel) {
        this.blogLabel = blogLabel;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "BusinessBlogEdit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", blogTypeId='" + blogTypeId + '\'' +
                ", blogType='" + blogType + '\'' +
                ", content='" + content + '\'' +
                ", blogLabel='" + blogLabel + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }
}
