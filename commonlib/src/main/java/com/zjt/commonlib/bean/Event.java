package com.zjt.commonlib.bean;


/**
 * Describe：EventBus事件类
 * Created by 吴天强 on 2018/10/22.
 */



public class Event<T> {

    private String action;
    private T data;


    public Event() {
    }

    public Event(String action) {
        this.action = action;
    }


    public Event(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
