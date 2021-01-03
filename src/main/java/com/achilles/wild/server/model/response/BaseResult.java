package com.achilles.wild.server.model.response;

import java.io.Serializable;

public class BaseResult implements  Serializable {
	
	private static final long serialVersionUID = 2663890170416765007L;
	
	private String code;
	private boolean success;
	private String message;
	

	public BaseResult() {
		this.success = true;
		this.code = ResultCode.SUCCESS.code;
		this.message = ResultCode.SUCCESS.message;
	}

	public static BaseResult fail() {
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setCode(ResultCode.FAIL.code);
		result.setMessage(ResultCode.FAIL.message);
		return result;
	}

	public static BaseResult fail(ResultCode resultCode) {
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setCode(resultCode.code);
		result.setMessage(resultCode.message);
		return result;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
