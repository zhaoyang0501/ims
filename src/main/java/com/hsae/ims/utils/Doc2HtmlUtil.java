package com.hsae.ims.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class Doc2HtmlUtil {
	Log log = LogFactory.getLog(Doc2HtmlUtil.class);

	private static Doc2HtmlUtil doc2HtmlUtil;

	public static synchronized Doc2HtmlUtil getDoc2HtmlInstance() {
		if (doc2HtmlUtil != null) {
			doc2HtmlUtil = new Doc2HtmlUtil();
		}
		return doc2HtmlUtil;
	}

	/**
	 * 文件转换
	 * 
	 * @return
	 */
	public String doc2html(InputStream fromFileInputStream, File toFileFolder) {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timesuffix = sdf.format(date);
		String htmFileName = "htmlfile" + timesuffix + ".html";
		String docFileName = "docfile" + timesuffix + ".doc";

		File htmlOutputFile = new File(toFileFolder.toString() + File.separatorChar + htmFileName);
		File docInputFile = new File(toFileFolder.toString() + File.separatorChar + docFileName);
		log.debug("########htmlOutputFile：" + toFileFolder.toString() + File.pathSeparator + htmFileName);

		/**
		 * 由fromFileInputStream构建输入文件
		 */
		try {
			OutputStream os = new FileOutputStream(docInputFile);
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			fromFileInputStream.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		try {
			connection.connect();
		} catch (ConnectException e) {
			System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
			log.error(e.getMessage(), e);
		}
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(docInputFile, htmlOutputFile);
		connection.disconnect();

		// 转换完之后删除word文件
		docInputFile.delete();
		log.debug("删除上传文件：" + docInputFile.getName());
		return htmFileName;
	}
}
