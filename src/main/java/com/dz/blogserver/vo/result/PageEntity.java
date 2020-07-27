package com.dz.blogserver.vo.result;

import java.util.List;

/**
 * @author dz
 * @date 2020/7/8
 * @time 14:00
 */
public class PageEntity {
    /**
     * 当前页数
     */
    private long current;

    /**
     * 每页显示多少条
     */
    private long size;

    /**
     * 总页数
     */
    private long totalPages;

    /**
     * 总数据量
     */
    private long totalElements;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否第一页
     */
    private boolean first;

    /**
     * 数据集
     */
    private List<?> records;

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

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public List<?> getRecords() {
        return records;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "current=" + current +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", hasNext=" + hasNext +
                ", first=" + first +
                ", records=" + records +
                '}';
    }
}
