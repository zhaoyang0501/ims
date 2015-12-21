package com.hsae.ims.controller.response;

import java.util.Set;

public class SetResponse<T> implements Response {
	private String code;
	private String msg;
    private Set<T> datas;// 返回数据

    /**
     * 有参构造方法
     * 
     * @param datas
     */
    public SetResponse(Set<T> datas) {
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
    public SetResponse(String code, String msg, Set<T> datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    /**
     * 获取数据
     * 
     * @return
     */
    public Set<T> getDatas() {
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
