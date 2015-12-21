package com.hsae.ims.controller.response;

import java.util.List;

/**
 * Android接口调用-返回List结果
 * 
 * @author shishun
 *
 * @param <T>
 */
public class ListResponse<T> implements Response {
	private String code;
	private String msg;
    private List<T> datas;// 返回数据
    private long totalSize;// 总记录数

    /**
     * 有参构造方法
     * 
     * @param datas
     */
    public ListResponse(List<T> datas) {
        this.code = Response.CODE_SUCCESS;
        msg = MSG_SUCCESS;
        this.datas = datas;
    }

    /**
     * 有参构造方法
     * 
     * @param datas
     * @param totalSize
     */
    public ListResponse(List<T> datas, long totalSize) {
        this.code = Response.CODE_SUCCESS;
        msg = MSG_SUCCESS;
        this.datas = datas;
        this.totalSize = totalSize;
    }

    /**
     * 有参构造方法
     * 
     * @param code
     * @param msg
     * @param datas
     */
    public ListResponse(String code, String msg, List<T> datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    /**
     * 获取数据
     * 
     * @return
     */
    public List<T> getDatas() {
        return datas;
    }

    public long getTotalSize() {
        return totalSize;
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
