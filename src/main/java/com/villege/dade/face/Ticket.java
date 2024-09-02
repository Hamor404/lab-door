package com.villege.dade.face;

public class Ticket {
	private String value;
	private String expire_in;
	private String expire_time;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExpire_in() {
		return expire_in;
	}

	public void setExpire_in(String expire_in) {
		this.expire_in = expire_in;
	}

	public String getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}

	@Override
	public String toString() {
		return "Ticket [value=" + value + ", expire_in=" + expire_in + ", expire_time=" + expire_time + "]";
	}

}
