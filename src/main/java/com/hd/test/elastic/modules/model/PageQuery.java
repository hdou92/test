package com.hd.test.elastic.modules.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PageQuery {
    @NotNull
    @Min(value = 1, message = "至少为1")
    private int pageIndex;
    @NotNull
    @Min(value = 1, message = "至少为1")
    private int pageSize;

    public PageQuery() {
    }

    public PageQuery(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
