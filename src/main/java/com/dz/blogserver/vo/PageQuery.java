package com.dz.blogserver.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dz
 * @date 2020/7/10
 * @time 21:45
 */
public class PageQuery {
    /**
     * 当前页码
     */
//    @NotNull(message = "当前页码不能为空")
//    @NotEmpty(message = "当前页码不能为空")
    private long current;

    /**
     * 每页大小
     */
//    @NotNull(message = "每页大小不能为空")
//    @NotEmpty(message = "每页大小不能为空")
    private long size;

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "current=" + current +
                ", size=" + size +
                '}';
    }
}
