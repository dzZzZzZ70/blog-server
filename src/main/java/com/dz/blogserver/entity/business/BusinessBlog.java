package com.dz.blogserver.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.txw2.annotation.XmlCDATA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class BusinessBlog implements Serializable {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = true)
    private String blogTypeId;

    @Column(length = 100, nullable = true)
    private String blogType;

    /**
     * 简介
     */
    private String shortContent;

//    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 100, nullable = true)
    private String blogLabel;

    @Column(length = 100, nullable = false)
    private String editUser;

    @Column
    private String author;

    @Column(length = 100, nullable = false)
    private String createUser;

    @Column(nullable = false)
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @Column(length = 1, nullable = false)
    private String dataFlag;

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

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BusinessBlog{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", blogTypeId='" + blogTypeId + '\'' +
            ", blogType='" + blogType + '\'' +
            ", shortContent='" + shortContent + '\'' +
            ", content='" + content + '\'' +
            ", blogLabel='" + blogLabel + '\'' +
            ", editUser='" + editUser + '\'' +
            ", author='" + author + '\'' +
            ", createUser='" + createUser + '\'' +
            ", createDate=" + createDate +
            ", updateDate=" + updateDate +
            ", dataFlag='" + dataFlag + '\'' +
            '}';
    }
}
