package com.jian.retrorx.bean;

import java.util.List;

public class BaseEntity<T> {

    /**
     * next : true
     * list :
     * nextpage : 1
     */

    private String next;
    private String nextpage;
    private T list;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getNextpage() {
        return nextpage;
    }

    public void setNextpage(String nextpage) {
        this.nextpage = nextpage;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
