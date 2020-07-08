package com.lins.modulesystem.http.response;

/**
 * Created by Admin on 2017/3/14.
 */

public class HttpResponse<T> {

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "HttpResponse{" +
                "data=" + data +
                '}';
    }
}
