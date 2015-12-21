package com.hsae.ims.controller.response;

/**
 * Android接口调用-返回空结果
 * 
 * @author shishun
 *
 */
public class EmptyResponse implements Response {
	private String code;
	private String msg;
    /**
     * 无参构造方法，初始化数据
     */
    public EmptyResponse() {
        this.code = CODE_EMPTY;
        this.msg = MSG_EMPTY;
    }
	@Override
	public String getCode() {
		return code;
	}
	@Override
	public String getMsg() {
		return msg;
	}
}
