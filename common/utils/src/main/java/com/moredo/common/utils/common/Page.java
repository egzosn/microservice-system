package com.moredo.common.utils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表，保存了分页内容，当前页，每页记录数，总记录数和页数
 *
 * @param <T>
 * @author Linjie
 */
public class Page<T> {

    private int page = 1; //  当前页

    private int pageSize = 10; // 每页记录数

    private int pages = 1; // 总页数

    private long total = 0; // 所有记录数

    private List<T> rows; // 当前页内容

    /**
     * 获取当前页
     *
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页
     *
     * @param page
     */
    public void setPage(int page) {
        if (page <= 0)
            page = 1;
        this.page = page;
    }

    /**
     * 获取每页记录数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getPages() {
        return pages;
    }

    /**
     * 获取总记录数
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置记录数
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
        // 根据总的记录数生成分页
        setPages();
    }

    /**
     * 获取当前页内容
     *
     * @return
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置当前页内容
     *
     * @param rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setPages() {
        if (total > 0) {
            this.pages = (int) Math.ceil((float) total / pages);
        }
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    public Page() {
        rows = new ArrayList<>();
    }

    public Page(int page, int pageSize, long total, List<T> rows) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;
        setPages();
    }

}
