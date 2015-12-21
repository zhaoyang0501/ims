package com.hsae.ims.service.cas;
/***
 * 获取图片新闻
 * @author panchaoyang
 *
 * @param <RemoteImages>
 */
public class DiscuzzImgageService<RemoteImages>  extends RemoteMesgService{

	@Override
	protected String buildRequestAddr() {
		return "http://192.168.20.7/forum/getimages.php";
	}

}
