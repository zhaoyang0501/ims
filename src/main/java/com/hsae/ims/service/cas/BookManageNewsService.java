package com.hsae.ims.service.cas;
public class BookManageNewsService<RemoteNews> extends RemoteMesgService{
	@Override
	public String buildRequestAddr() {
		return "http://192.168.20.8:8080/bookmanagecas/getnews.action";
	}
}
