package com.smile.smilevideo.entity;

/**
 * Created by yaojiulong on 2017/8/18.
 */

public class HttpResult<T> {
    private T itemList;
    private int count;
    private int total;
    private String nextPageUrl;
    private long date;
    private long nextPublishTime;
    private Object dialog;

    public T getItemList() {
        return itemList;
    }

    public void setItemList(T itemList) {
        this.itemList = itemList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public Object getDialog() {
        return dialog;
    }

    public void setDialog(Object dialog) {
        this.dialog = dialog;
    }
}
