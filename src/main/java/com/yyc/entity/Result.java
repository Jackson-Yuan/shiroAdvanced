package com.yyc.entity;

public class Result<T> {
    private T data;

    private Boolean token;

    public Result() {
    }

    public Result(T data, Boolean token) {
        this.data = data;
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getToken() {
        return token;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", token=" + token +
                '}';
    }
}
