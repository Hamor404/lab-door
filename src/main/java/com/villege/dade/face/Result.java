package com.villege.dade.face;

import com.alibaba.fastjson.JSON;

public class Result {

	private String code;
	private String msg;
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
