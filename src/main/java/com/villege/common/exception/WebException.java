package com.villege.common.exception;

/**
 * 自定义异常
 *
 * @author Lewis 31343080@qq.com
 */
public class WebException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public WebException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WebException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public WebException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public WebException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
