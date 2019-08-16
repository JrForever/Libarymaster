package com.jian.retrorx.bean;

import java.util.List;

public class GameInfo<T> {
    /**
     * info : []
     * page : {"total":0,"pagesize":10,"page":1,"page_total":0}
     * state : success
     */

    private PageBean page;
    private String state;
    private List<T> info;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }

    public static class PageBean {
        /**
         * total : 0
         * pagesize : 10
         * page : 1
         * page_total : 0
         */

        private int total;
        private int pagesize;
        private int page;
        private int page_total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_total() {
            return page_total;
        }

        public void setPage_total(int page_total) {
            this.page_total = page_total;
        }
    }
}
