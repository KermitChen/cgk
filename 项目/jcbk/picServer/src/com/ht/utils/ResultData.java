package com.ht.utils;

import java.io.Serializable;

/**
 * 返回数据格式
 * 
 * @Title ResultData
 * @version V1.0
 */
public class ResultData implements Serializable {
	private static final long serialVersionUID = -7653031048752066399L;
	/** 返回状态码 */
	private String ret;
	/** 状态码信息 */
	private String msg;
	/** 返回结果 */
	private Object data;
	/** 返回总数 */
	private int total;

	public ResultData() {
		
	}

	public ResultData( String ret, String msg ) {
		this.ret = ret;
		this.msg = msg;
	}

	public ResultData(String ret, String msg, Object data) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.data = data;
	}

	public ResultData(String ret) {
		super();
		this.ret = ret;
	}

	public void set( String ret, String msg ) {
		this.ret = ret;
		this.msg = msg;
	}

	public void set( String ret, String msg, Object data ) {
		this.ret = ret;
		this.msg = msg;
		this.data = data;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}