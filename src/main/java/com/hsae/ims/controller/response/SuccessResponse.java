package com.hsae.ims.controller.response;

/**
 * Android接口调用-操作成功返回结果
 * 
 * @author shishun
 *
 */
public class SuccessResponse implements Response {
	private String code;
	private String msg;
    /**
     * 无参构造方法，初始化数据
     */
	public SuccessResponse(String msg){
		this.code = CODE_SUCCESS;
		this.msg=msg;
	}
    public SuccessResponse() {
        this.code = CODE_SUCCESS;
        this.msg = MSG_SUCCESS;
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
