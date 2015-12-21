package com.hsae.ims.view;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hsae.ims.entity.BookInfo;
@Service
public class BookReportExportXlsView extends AbstractExcelView {
	
	private String[] titles = { "编码", "名称", "价格", "作者", "出版社", "描述", "状态" };
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook bookinfo, HttpServletRequest request, HttpServletResponse response)throws Exception {
		List<BookInfo> book= (List<BookInfo>) model.get("book");
		HSSFSheet sheet=bookinfo.createSheet();
		createHead(sheet);
		createBody(sheet,book);
		styleBeautify(sheet,bookinfo);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+new String(((String)model.get("fileName")).getBytes(),"iso-8859-1") );   
        OutputStream ouputStream = response.getOutputStream();   
        bookinfo.write(ouputStream);   
        ouputStream.flush();   
        ouputStream.close();   
	}
	/***
	 * 创建表头
	 * @param sheet
	 */
	private void createHead(HSSFSheet sheet){
		HSSFRow headRow=sheet.createRow(0);
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}
	}
	/***
	 * 创建表身
	 * @param sheet
	 * @param dailyReports
	 */
	private void createBody(HSSFSheet sheet,List<BookInfo> book){
		int i=1;
		for(BookInfo bookInfo:book){
			HSSFRow row=sheet.createRow(i++);
			row.createCell(0).setCellValue(bookInfo.getCode()==null ?"":bookInfo.getCode());
			row.createCell(1).setCellValue(bookInfo.getBookName()==null ?"":bookInfo.getBookName());
			row.createCell(2).setCellValue(bookInfo.getPrice()==null ?"":bookInfo.getPrice().toString());
			row.createCell(3).setCellValue(bookInfo.getAuthor()==null ?"":bookInfo.getAuthor());
			row.createCell(4).setCellValue(bookInfo.getPublish()==null ?"":bookInfo.getPublish());
			row.createCell(5).setCellValue(bookInfo.getDescription()==null ?"":bookInfo.getDescription());
			if (bookInfo.getStatus() == 0){
				row.createCell(6).setCellValue("正常");
			}else{
				row.createCell(6).setCellValue("借出");
			}
		}
	}
	/***
	 * 格式调整
	 */
	private void styleBeautify(HSSFSheet sheet,HSSFWorkbook bookinfo){
		/**内容部分样式  加表格线*/
		CellStyle styleBody = bookinfo.createCellStyle();
		styleBody.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/**表头部分样式 加表格线 字体加粗*/
		CellStyle styleHead = bookinfo.createCellStyle();
		styleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		Font font = bookinfo.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("Tahoma");
		styleHead.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		styleHead.setFont(font);
		styleHead.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		/**列宽设置*/
		for (int i = 0; i < titles.length; i++) {
			sheet.autoSizeColumn((short)i);
		}
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 9999);
		sheet.setColumnWidth(2, 2500);
		sheet.setColumnWidth(3, 9999);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 2500);

		/**表格内容加表格线*/
		for(int j=1;j<sheet.getPhysicalNumberOfRows();j++){
			Row row=sheet.getRow((short)j);
			for(int k=0;k<row.getPhysicalNumberOfCells();k++){
				row.getCell(k).setCellStyle(styleBody);
			}
		}
		/**表头加粗等*/
		Row row=sheet.getRow((short)0);
		for(int k=0;k<row.getPhysicalNumberOfCells();k++){
			row.getCell(k).setCellStyle(styleHead);
		}
		
	} 
}
