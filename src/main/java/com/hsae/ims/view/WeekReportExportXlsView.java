package com.hsae.ims.view;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportWorkStageService;
@Service
public class WeekReportExportXlsView extends AbstractExcelView {
	
	private String[] titles = { "员工姓名", "日期", "类型", "项目名称", "日报详情", "异常情况/工作难点", "工时", "工作阶段" };
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private DailyReportWorkStageService dailyReportWorkStageService;
	
	
	private Map<String,String> codeMap=new  HashMap<String,String>();
	
	private Map<Integer,String> workStep=new  HashMap<Integer,String>();
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)throws Exception {
		codeMap=(Map<String, String>) model.get("codeMap");
		workStep=(Map<Integer, String>) model.get("workStep");
		List<Deptment> deptments=(List<Deptment>) model.get("deptments");
		Map<Long,List<DailyReport>> dailys=(Map<Long,List<DailyReport>>) model.get("dailys");
		for(Deptment deptment:deptments){
			HSSFSheet sheet=workbook.createSheet(deptment.getName());
			createHead(sheet);
			createBody(sheet,dailys.get(deptment.getId()));
			styleBeautify(sheet,workbook);
		}
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+new String(((String)model.get("fileName")).getBytes(),"iso-8859-1") );   
        OutputStream ouputStream = response.getOutputStream();   
        workbook.write(ouputStream);   
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
	private void createBody(HSSFSheet sheet,List<DailyReport> dailyReports){
		
		int i=1;
		for(DailyReport dailyReport:dailyReports){
			HSSFRow row=sheet.createRow(i++);
			row.createCell(0).setCellValue(dailyReport.getUser().getChinesename());
			row.createCell(1).setCellValue(DateFormatUtils.format(dailyReport.getReportDate(), "yyyy-MM-dd"));
			row.createCell(2).setCellValue(codeMap.get(dailyReport.getType()));
			row.createCell(3).setCellValue(dailyReport.getProject()==null?"":dailyReport.getProject().getProjectName());
			row.createCell(4).setCellValue(dailyReport.getSummary());
			row.createCell(5).setCellValue(dailyReport.getDifficulty());
			row.createCell(6).setCellValue(dailyReport.getSpendHours());
			row.createCell(7).setCellValue(workStep.get(dailyReport.getProjectStep()));
		}
	}
	/***
	 * 格式调整
	 */
	private void styleBeautify(HSSFSheet sheet,HSSFWorkbook workbook){
		/**内容部分样式  加表格线*/
		CellStyle styleBody = workbook.createCellStyle();
		styleBody.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/**表头部分样式 加表格线 字体加粗*/
		CellStyle styleHead = workbook.createCellStyle();
		styleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		Font font = workbook.createFont();
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
		sheet.setColumnWidth(1, 2500);
		sheet.setColumnWidth(2, 2500);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 9999);
		sheet.setColumnWidth(5, 9999);
		sheet.setColumnWidth(6, 2500);
		sheet.setColumnWidth(7, 2500);
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
		/**合并单元格*/
		int rownum=sheet.getPhysicalNumberOfRows();
		int start=1,end=2;
		while(start!=(rownum-1)&&rownum>2){
			for(int k=end;k<rownum;k++){
				if(k==(rownum-1)&&sheet.getRow(k).getCell(0).getStringCellValue().equals(sheet.getRow(k-1).getCell(0).getStringCellValue())){
					sheet.addMergedRegion(new CellRangeAddress(start,k,0,0));
					start=k; end =k+1;
					break;
				}else if(!sheet.getRow(k).getCell(0).getStringCellValue().equals(sheet.getRow(k-1).getCell(0).getStringCellValue())){
					sheet.addMergedRegion(new CellRangeAddress(start,k-1,0,0));
					start=k; end =k+1;
					break;
				}
				else
					end++;
			}
		}
	} 
}
