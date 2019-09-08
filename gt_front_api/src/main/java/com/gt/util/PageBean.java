package com.gt.util;

import java.util.List;

public class PageBean<T> {
    private int pageSize;           //页面大小
    private int pageIndex;          //当前页索引
    private int total;                  //总记录数
    private int totalPage;          //总页数
    private List<T> list;            //存放的数据

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(int total) {
        this.total=total;
        //计算总页数
        this.totalPage=this.total%pageSize==0?this.total/pageSize:this.total/pageSize+1;
    }

    public void setPageIndex(int pageIndex) {
        if(pageIndex<1){ //页码小于1的情况
            pageIndex=1;
        }
        if(pageIndex>totalPage && totalPage!=0){//页码大于总页数的情况
            pageIndex=totalPage;
        }
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
    public int getPageIndex() {
        return pageIndex;
    }


    public int getTotal() {
        return total;
    }

    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public PageBean(int pageSize, int pageIndex, int total) {
        super();
        setPageSize(pageSize);
        setTotal(total);
        setPageIndex(pageIndex);

    }
}

