
package com.bootx.common;

import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 结果
 * 
 * @author blackboy1987
 * @version 1.0
 */
@JsonView({BaseEntity.PageView.class, BaseEntity.ListView.class})
public final class Result {

	private Integer code;

	private String msg;

	private Object data;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	/**
	 * 构造方法
	 */
	private Result() {
	}

	public Result(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static Result success(Object obj) {
		return new Result(0,"操作成功",obj);

	}

	public static Result success() {
		return new Result(0,"操作成功",null);

	}

	public static Result success(Integer code,Object data) {
		return new Result(code,"操作成功",data);

	}

	public static Result error(String msg) {
		return new Result(-1, msg,null);
	}

	public static Result error(Integer code, String msg) {
		return new Result(code,msg,null);
	}
	public static Result error(Integer code, String msg,Object obj) {
		return new Result(code,msg,obj);
	}
}