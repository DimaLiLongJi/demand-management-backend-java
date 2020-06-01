package com.demand.management.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class ExtendPage<T> extends Page<T> {
    private String pageIndex;
    private String pageSize;

    public ExtendPage(String pageIndex, String pageSize) {
        if (pageIndex != null && pageSize != null) {
            this.setCurrent( Long.parseLong(pageIndex));
            this.setSize(Long.parseLong(pageSize));
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }

    /**
     * 如果不存在pageIndex和pageSize
     * 返回空对象，则查全部
     *
     * @return Page
     */
    public Page<T> getPageForFind() {
        if (this.pageIndex == null || this.pageSize == null) return null;
        else return this;
    }

    /**
     * 如果不存在pageIndex和pageSize
     * 则用records的长度当做total，其实就是用全部的数量
     *
     * @param records List<T>
     * @return Page
     */
    public Page<T> setTotalAndRecord(List<T> records) {
        this.setRecords(records);
        if (this.pageIndex == null || this.pageSize == null) this.setTotal(records.size());
        return this;
    }
}
