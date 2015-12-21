package com.hsae.ims.service.cas;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**8
 * 获取远程网站接口信息
 * @author panchaoyang
 *
 * @param <T>
 */
public abstract class RemoteMesgService<T> {
	 /***
	  * 设置远端接口地址
	  * 子类必须实现
	  */
	 protected abstract String buildRequestAddr(); 
	 /***
	  * 对获取的参数做处理，
	  * 默认实现 子类可以覆盖
	  */
	 protected  List<T> parseResponse(String response){
		 Assert.notNull(response);
	     Gson gson = new Gson();
	     return  gson.fromJson(response, new TypeToken<List<T>>() { }.getType());
	 }
	 /***
	  * 发送请求，
	  * 默认实现 子类可以覆盖
	  * @throws HttpException
	  * @throws IOException
	  */
	 protected  String  sendRequest(String addr) throws HttpException, IOException{
		Assert.notNull(addr);
		HttpClient client = new HttpClient(); 
		HttpMethod method = new GetMethod(addr); 
		client.executeMethod(method);
		String result=method.getResponseBodyAsString();
		method.releaseConnection();
		return result;
	 }
	 /***
	  * 对外暴露接口，
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  */
	public List<T> getJsonResult() throws HttpException, IOException{
		String addr= buildRequestAddr();
		String result=sendRequest(addr);
		List<T> list=parseResponse(result);
		return list;
	}
}
