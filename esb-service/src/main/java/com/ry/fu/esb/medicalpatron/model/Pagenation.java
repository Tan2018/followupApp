package com.ry.fu.esb.medicalpatron.model;

import com.ry.fu.esb.common.response.BaseModel;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/2 16:17
 * @description 公共分页类，此分页可以废用
 */
public class Pagenation<T> extends BaseModel {

    private static final long serialVersionUID = -7676473092187864385L;
    /**
     * 当前页从第几个开始（从1开始计算），如一页显示5条，那么第二页就是从第6条数据开始
     */
    private Integer startRow = 1;

    /**
     * 当前页从第几个开始（从1开始计算），如一页显示5条，那么第二页就是从第6条数据开始
     */
    private Integer endRow;

    /**
     * 每页显示的数量
     */
    private Integer pageSize = 10;

    /**
     * 总条数
     */
    private Integer totalRow;

    /**
     * 当前页 页码
     */
    private Integer currentPage = 1;

    /**
     * 下一页的页码号
     */
    private Integer nextPageNum;

    /**
     * 总的页面数量
     */
    private Integer totalPage;

    private T result;

    public Pagenation() {
    }

    /**
     *  总数量，当前页默认1，每页大小默认10
     * @param totalRow 数据总条数
     */
    public Pagenation(Integer totalRow) {
        this.totalRow = totalRow;

        this.startRow = pageSize * (currentPage - 1) + 1;
        this.endRow = this.startRow + this.pageSize - 1;
        this.nextPageNum = currentPage + 1;
        if (totalRow / pageSize == 0) {
            this.totalPage = totalRow % pageSize == 0 ? 0 : 1;
        } else {
            this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
        }
    }

    /**
     *
     * @param currentPage 当前页
     * @param totalRow 数据总条数
     */
    public Pagenation(Integer currentPage, Integer totalRow) {
        this.currentPage = currentPage;
        this.totalRow = totalRow;

        this.startRow = pageSize * (currentPage - 1) + 1;
        this.endRow = this.startRow + this.pageSize - 1;
        this.nextPageNum = currentPage + 1;
        if (totalRow / pageSize == 0) {
            this.totalPage = totalRow % pageSize == 0 ? 0 : 1;
        } else {
            this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
        }
    }

    public Pagenation(Integer currentPage, Integer totalRow, Integer pageSize) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRow = totalRow;

        this.startRow = pageSize * (currentPage - 1) + 1;
        this.endRow = this.startRow + this.pageSize - 1;
        this.nextPageNum = currentPage + 1;
        if (totalRow / pageSize == 0) {
            this.totalPage = totalRow % pageSize == 0 ? 0 : 1;
        } else {
            this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
        }
    }

    public Integer getStartRow() {
        return pageSize * (currentPage - 1) + 1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNextPageNum() {
        return currentPage + 1;
    }

    public Integer getEndRow() {
        return this.startRow + this.pageSize - 1;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
