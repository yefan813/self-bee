package com.dmall.bee.domain;

public class RemoteResult {
	
	public static final String SUCCESS_CODE = "1000";
	
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
	
	private RemoteResult(String errCode) {
		super();
		this.code = errCode;
	}
	
	private RemoteResult(String errCode, String errMsg) {
		super();
		this.code = errCode;
		this.msg = errMsg;
	}
	
	private RemoteResult(String errCode, String errMsg, Object data) {
		super();
		this.code = errCode;
		this.msg = errMsg;
		this.data = data;
	}
	
	private RemoteResult(String errCode, Object data) {
		super();
		this.code = errCode;
		this.data = data;
	}
	
	public static RemoteResult failure(String code){
		return new RemoteResult(code);
	}
	

	
	public static RemoteResult failure(String code, String msg){
		return new RemoteResult(code, msg);
	}

	public static RemoteResult failure(String code, String msg,Object data){
		return new RemoteResult(code, msg, data);
	}
	
	public static RemoteResult success(){
		return new RemoteResult(SUCCESS_CODE);
	}
	
	public static RemoteResult success(Object data){
		return new RemoteResult(SUCCESS_CODE, data);
	}
}
