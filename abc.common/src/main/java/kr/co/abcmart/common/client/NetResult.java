package kr.co.abcmart.common.client;

import lombok.ToString;

@ToString
public class NetResult<U> {

	private int status;
	private String type;
	private String message;
	private U data;

	public void setData(U o) {
		this.data = o;
	}

	public void setData(Class<?> clazz, Object o) {
		this.data = (U) o;
	}

	public <U> U getData() {
		return (U) data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}