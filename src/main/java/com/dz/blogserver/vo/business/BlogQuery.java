package com.dz.blogserver.vo.business;

import com.dz.blogserver.vo.PageQuery;

/**
 * @author dz
 * @date 2020/7/15
 * @time 21:11
 */
public class BlogQuery extends PageQuery {
    private String id;
    private String blogTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
            "id='" + id + '\'' +
            ", blogTypeId='" + blogTypeId + '\'' +
            '}';
    }
}
