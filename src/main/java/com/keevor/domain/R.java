package com.keevor.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> R<T> success() {
        R<T> result = new R<T>();
        result.code = 1;
        return result;
    }

    public static <T> R<T> success(T object) {
        R<T> result = new R<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> R<T> error(String msg) {
        R result = new R();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}

