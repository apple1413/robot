package com.zty.robot.base;

import java.io.Serializable;

public class ResultUtils implements Serializable {

    private Integer code;

    private Object  msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
