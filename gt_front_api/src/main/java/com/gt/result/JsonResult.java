package com.gt.result;

import java.io.Serializable;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * JSON返回实体
 * @author zhouyong
 *
 */
public class JsonResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5623325730634790730L;
	
	private int code;
	private String msg;
	private Object data;
	
	private int PageCount;
	private int PageNum;


	public JsonResult() {
		this.setCode(ResultCode.SUCCESS);
		this.setMsg("success");
	}

	public JsonResult(ResultCode code) {
		this.setCode(code);
		this.setMsg(code.msg());
	}

	public JsonResult(ResultCode code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}

	public JsonResult(ResultCode code, String msg, Object data) {
		this.setCode(code);
		this.setMsg(msg);
		this.setData(data);
	}
	
	public JsonResult(ResultCode code, String msg, Object data, int pageCount, int pageNum) {
		this.setCode(code);
		this.setMsg(msg);
		this.setData(data);
		this.setPageCount(pageCount);
		this.setPageNum(pageNum);
	}

	public String toString() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", code);
			json.put("msg", msg);
			json.put("data", data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public int getCode() {
		return code;
	}

	public void setCode(ResultCode code) {
		this.code = code.val();
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
	
	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}

	public int getPageNum() {
		return PageNum;
	}

	public void setPageNum(int pageNum) {
		PageNum = pageNum;
	}
}
