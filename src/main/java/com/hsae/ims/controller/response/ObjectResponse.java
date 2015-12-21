package com.hsae.ims.controller.response;

/**
 * Android接口调用-返回Object结果
 * 
 * @author shishun
 *
 * @param <T>
 */
public class ObjectResponse<T> implements Response {
	private String code;
	private String msg;
    private T datas;// 返回数据

    /**
     * 有参构造方法
     * 
     * @param datas
     */
    public ObjectResponse(T datas) {
        this.code = CODE_SUCCESS;
        this.msg = MSG_SUCCESS;
        this.datas = datas;
    }

    /**
     * 有参构造方法
     * 
     * @param code
     * @param msg
     * @param datas
     */
    public ObjectResponse(String code, String msg, T datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    /**
     * 获取数据
     * 
     * @return
     */
    public T getDatas() {
        return datas;
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
