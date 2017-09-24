package com.kyivstar.common.protocol;

import java.io.Serializable;

/**
 * Created by igor on 24.09.17.
 */
public class Action<T extends Serializable> {

    private static final long serialVersionUID = 3373910816445037302L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Action{" +
                "data=" + data +
                '}';
    }
}
