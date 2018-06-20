package com.north.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class R extends HashMap implements Serializable{

	/**
	 * 错误返回码
	 */
	private int code;

	/**
	 * 描述
	 */
	private String msg;

	/**
	 * 返回消息体：JsonObject类型，定义了需要给请求方的数据
	 */
//	private Map<String, Object> msgInfo = new HashMap<String, Object>();

	public R() {

	}

	public static R error (){
		R r = new R();
		r.putObject("code",500);
		return r;
	}
	public static R error (String errorMsg){
		R r = new R();
		r.putObject("code",500);
		r.putObject("msg",errorMsg);
		return r;
	}

	public static R error (int errorCode, String errorMsg){
		R r = new R();
		r.putObject("code",errorCode);
		r.putObject("msg",errorMsg);
		return r;
	}

	public static R error (int errorCode, String errorMsg,Throwable e){
		R r = new R();
		r.putObject("code",errorCode);
		r.putObject("msg",errorMsg);
		r.put(e.getClass().getName(),e.getMessage());
		return r;
	}

	public static R ok(){
		R r = new R();
		r.putObject("code",200);
		r.putObject("msg","");
		return r;
	}

	public static R ok(String msg){
		R r = new R();
		r.putObject("code",200);
		r.putObject("msg",msg);
		return r;
	}

	public static R ok(String key, Object value){
		R r = new R();
		r.putObject("code",200);
		r.putObject("msg","");
		r.put(key, value);
		return r;
	}

	public static R ok(Map<String,Object> map){
		R r = new R();
		r.putObject("code",200);
		r.putObject("msg","");
		r.putAll(map);
		return r;
	}

//	public R put(String key, Object value) {
//		this.put(key, value);
//		return this;
//	}

	public R putObject(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

//	public Map<String, Object> getMsgInfo() {
//		return msgInfo;
//	}

//	public void setMsgInfo(Map<String, Object> msgInfo) {
//		this.msgInfo = msgInfo;
//	}
}
