package com.hd.test.elastic.common;

import java.util.List;

/**
 * 分页数据信息
 *
 * @author Sands
 */
public class ElasticPageInfo<T> {
    private long pageIndex;
    private long pageSize;
    private long total;
    private List<T> data;

    public ElasticPageInfo() {

    }

    public ElasticPageInfo(long pageIndex, long pageSize, long total, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ElasticPageInfo{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
