package com.villege.dade.face;

import com.villege.dade.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class AccessToken {
	private String code;
	private String msg;
	private String bizSeqNo;
	private boolean success;
	private String transactionTime;
	private String access_token;
	private String expire_time;
	private String expire_in;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}

	public String getExpire_in() {
		return expire_in;
	}

	public void setExpire_in(String expire_in) {
		this.expire_in = expire_in;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		if(!success) {
			return false;
		}
		
		//判断是否过期(50分钟内有效)，过期直接返回false
		Date tranTime = null;
		try {
			tranTime = DateUtil.getDateTime(transactionTime, "yyyyMMddHHmmss");
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		Date curDate = new Date();
		tranTime = DateUtils.addMinutes(tranTime, 50);
		if(curDate.before(tranTime)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "AccessToken [code=" + code + ", msg=" + msg + ", bizSeqNo=" + bizSeqNo + ", success=" + success
				+ ", transactionTime=" + transactionTime + ", access_token=" + access_token + ", expire_time="
				+ expire_time + ", expire_in=" + expire_in + "]";
	}
}
