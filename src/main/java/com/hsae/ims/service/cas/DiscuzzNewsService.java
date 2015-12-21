package com.hsae.ims.service.cas;
/**8
 * 获取远程网站文字信息
 * @author panchaoyang
 *
 * @param <RemoteNews>
 */
public class DiscuzzNewsService<RemoteNews>  extends RemoteMesgService{

	@Override
	protected String buildRequestAddr() {
		return "http://192.168.20.7/forum/getnews.php";
	}

}
