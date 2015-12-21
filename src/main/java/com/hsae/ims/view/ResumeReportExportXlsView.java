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

import com.hsae.ims.dto.UserResumeDTO;
@Service
public class ResumeReportExportXlsView extends AbstractExcelView {
	
	private String[] titles = { "序号", "工号", "姓名", "性别", "籍贯", "民族", "婚否", "政治面貌", "户口所在地", "身份证号码", "身份证住址", "现住址"
			, "外语水平/等级", "最高学历", "最高学位", "毕业院校", "专业", "手机", "生日", "毕业时间", "入职时间", "转正时间", "职称", "任职部门", "现岗位", "职位"
			, "职等", "合同开始日期", "合同结束日期", "社保选择", "公积金选择", "服务年限", "公司邮箱", "兴趣爱好", "技能擅长"};
	
	private String[] titles1 = { "序号", "工号", "姓名","入职时间", "离职时间","离职原因", "性别", "籍贯", "民族", "婚否", "政治面貌", "户口所在地", "身份证号码", "身份证住址", "现住址"
			, "外语水平/等级", "最高学历", "最高学位", "毕业院校", "专业", "手机", "生日", "毕业时间", "转正时间", "职称", "任职部门", "岗位", "职位"
			, "职等", "合同开始日期", "合同结束日期", "社保选择", "公积金选择", "服务年限", "公司邮箱", "兴趣爱好", "技能擅长"};
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)throws Exception {
		List<UserResumeDTO> resume= (List<UserResumeDTO>) model.get("resume");
		HSSFSheet sheet=workbook.createSheet("在职员工");
		HSSFSheet sheet1=workbook.createSheet("离职员工");

		createHead(sheet);
		createBody(sheet,resume);
		
		createHead1(sheet1);
		createBody1(sheet1,resume);

		styleBeautify(sheet,workbook);
		styleBeautify1(sheet1,workbook);
		
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
	
	private void createHead1(HSSFSheet sheet1){
		HSSFRow headRow=sheet1.createRow(0);
		for (int i = 0; i < titles1.length; i++) {
			headRow.createCell(i).setCellValue(titles1[i]);
		}
	}
	/***
	 * 创建表身
	 * @param sheet
	 * @param dailyReports
	 */
	private void createBody(HSSFSheet sheet,List<UserResumeDTO> resume){
		int i=1;
		int index = 1;

		for(UserResumeDTO ur:resume){
			if (ur.getState()==0){
				HSSFRow row=sheet.createRow(i++);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(ur.getEmpnumber());
				row.createCell(2).setCellValue(ur.getUserName());
				row.createCell(3).setCellValue(ur.getSex());
				row.createCell(4).setCellValue(ur.getPlace());
				row.createCell(5).setCellValue(ur.getNation());
				row.createCell(6).setCellValue(ur.getMarrige());
				row.createCell(7).setCellValue(ur.getPoliticsStatus());
				row.createCell(8).setCellValue(ur.getDomicilePlace());
				row.createCell(9).setCellValue(ur.getIdNumber());
				row.createCell(10).setCellValue(ur.getIdAddress());
				row.createCell(11).setCellValue(ur.getAddress());
				row.createCell(12).setCellValue(ur.getLanguage());//ur.getLanguage().toString()
				row.createCell(13).setCellValue(ur.getEducation());
				row.createCell(14).setCellValue(ur.getDegree());
				row.createCell(15).setCellValue(ur.getSchool());//毕业院校
				row.createCell(16).setCellValue(ur.getMajor());
				row.createCell(17).setCellValue(ur.getMobile());
				row.createCell(18).setCellValue(ur.getBirthday());
				row.createCell(19).setCellValue(ur.getGraduateTime());
				row.createCell(20).setCellValue(ur.getJoinTime());
				row.createCell(21).setCellValue(ur.getConversionTime());
				row.createCell(22).setCellValue(ur.getTitle());
				row.createCell(23).setCellValue(ur.getDept());
				row.createCell(24).setCellValue(ur.getPost());
				row.createCell(25).setCellValue(ur.getPosition());
				row.createCell(26).setCellValue(ur.getGrade());
				row.createCell(27).setCellValue(ur.getContractStartDate());
				row.createCell(28).setCellValue(ur.getContractEndDate());
				row.createCell(29).setCellValue(ur.getSocialMoney());
				row.createCell(30).setCellValue(ur.getPublicMoney());
				row.createCell(31).setCellValue(ur.getWorkYear());
				row.createCell(32).setCellValue(ur.getEmail());
				row.createCell(33).setCellValue(ur.getHobbies());
				row.createCell(34).setCellValue(ur.getSkill());
				index++;
			}
		}
	}
	
	private void createBody1(HSSFSheet sheet1,List<UserResumeDTO> resume){
		int i=1;
		int index = 1;

		for(UserResumeDTO ur:resume){
			if (ur.getState()==1){
				HSSFRow row1=sheet1.createRow(i++);
				row1.createCell(0).setCellValue(index);
				row1.createCell(1).setCellValue(ur.getEmpnumber());
				row1.createCell(2).setCellValue(ur.getUserName());
				row1.createCell(3).setCellValue(ur.getJoinTime());
				row1.createCell(4).setCellValue(ur.getDepartureTime());
				row1.createCell(5).setCellValue(ur.getLeavingReasons());
				row1.createCell(6).setCellValue(ur.getSex());
				row1.createCell(7).setCellValue(ur.getPlace());
				row1.createCell(8).setCellValue(ur.getNation());
				row1.createCell(9).setCellValue(ur.getMarrige());
				row1.createCell(10).setCellValue(ur.getPoliticsStatus());
				row1.createCell(11).setCellValue(ur.getDomicilePlace());
				row1.createCell(12).setCellValue(ur.getIdNumber());
				row1.createCell(13).setCellValue(ur.getIdAddress());
				row1.createCell(14).setCellValue(ur.getAddress());
				row1.createCell(15).setCellValue(ur.getLanguage());//ur.getLanguage().toString()
				row1.createCell(16).setCellValue(ur.getEducation());
				row1.createCell(17).setCellValue(ur.getDegree());
				row1.createCell(18).setCellValue(ur.getSchool());//毕业院校
				row1.createCell(19).setCellValue(ur.getMajor());
				row1.createCell(20).setCellValue(ur.getMobile());
				row1.createCell(21).setCellValue(ur.getBirthday());
				row1.createCell(22).setCellValue(ur.getGraduateTime());
				row1.createCell(23).setCellValue(ur.getConversionTime());
				row1.createCell(24).setCellValue(ur.getTitle());
				row1.createCell(25).setCellValue(ur.getDept());
				row1.createCell(26).setCellValue(ur.getPost());
				row1.createCell(27).setCellValue(ur.getPosition());
				row1.createCell(28).setCellValue(ur.getGrade());
				row1.createCell(29).setCellValue(ur.getContractStartDate());
				row1.createCell(30).setCellValue(ur.getContractEndDate());
				row1.createCell(31).setCellValue(ur.getSocialMoney());
				row1.createCell(32).setCellValue(ur.getPublicMoney());
				row1.createCell(33).setCellValue(ur.getWorkYear());
				row1.createCell(34).setCellValue(ur.getEmail());
				row1.createCell(35).setCellValue(ur.getHobbies());
				row1.createCell(36).setCellValue(ur.getSkill());
				index++;
			}
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
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 2500);
		sheet.setColumnWidth(2, 2500);
		sheet.setColumnWidth(3, 2000);
		sheet.setColumnWidth(4, 2500);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 2000);
		sheet.setColumnWidth(7, 2500);
		sheet.setColumnWidth(8, 9999);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 9999);
		sheet.setColumnWidth(11, 9000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 2500);
		sheet.setColumnWidth(14, 2500);
		sheet.setColumnWidth(15, 5000);
		sheet.setColumnWidth(16, 5000);
		sheet.setColumnWidth(17, 4000);
		sheet.setColumnWidth(18, 3000);
		sheet.setColumnWidth(19, 3000);
		sheet.setColumnWidth(20, 3000);
		sheet.setColumnWidth(21, 3000);
		sheet.setColumnWidth(22, 2500);
		sheet.setColumnWidth(23, 3000);
		sheet.setColumnWidth(24, 3000);
		sheet.setColumnWidth(25, 3000);
		sheet.setColumnWidth(26, 3000);
		sheet.setColumnWidth(27, 3000);
		sheet.setColumnWidth(28, 3000);
		sheet.setColumnWidth(29, 3000);
		sheet.setColumnWidth(30, 2500);
		sheet.setColumnWidth(31, 2000);
		sheet.setColumnWidth(32, 9999);
		sheet.setColumnWidth(33, 9999);
		sheet.setColumnWidth(34, 9999);
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
	
	private void styleBeautify1(HSSFSheet sheet1,HSSFWorkbook workbook){
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
		
		for (int i = 0; i < titles1.length; i++) {
			sheet1.autoSizeColumn((short)i);
		}
		sheet1.setColumnWidth(0, 2000);
		sheet1.setColumnWidth(1, 2500);
		sheet1.setColumnWidth(2, 2500);
		sheet1.setColumnWidth(3, 3000);
		sheet1.setColumnWidth(4, 3000);
		sheet1.setColumnWidth(5, 9999);
		sheet1.setColumnWidth(6, 2000);
		sheet1.setColumnWidth(7, 2500);
		sheet1.setColumnWidth(8, 2000);
		sheet1.setColumnWidth(9, 2000);
		sheet1.setColumnWidth(10, 3000);
		sheet1.setColumnWidth(11, 7000);
		sheet1.setColumnWidth(12, 5000);
		sheet1.setColumnWidth(13, 7000);
		sheet1.setColumnWidth(14, 7000);
		sheet1.setColumnWidth(15, 5000);
		sheet1.setColumnWidth(16, 3000);
		sheet1.setColumnWidth(17, 2500);
		sheet1.setColumnWidth(18, 5000);
		sheet1.setColumnWidth(19, 5000);
		sheet1.setColumnWidth(20, 3000);
		sheet1.setColumnWidth(21, 3000);
		sheet1.setColumnWidth(22, 3000);
		sheet1.setColumnWidth(23, 3000);
		sheet1.setColumnWidth(24, 2500);
		sheet1.setColumnWidth(25, 3000);
		sheet1.setColumnWidth(26, 3000);
		sheet1.setColumnWidth(27, 3000);
		sheet1.setColumnWidth(28, 2000);
		sheet1.setColumnWidth(29, 3000);
		sheet1.setColumnWidth(30, 3000);
		sheet1.setColumnWidth(31, 2500);
		sheet1.setColumnWidth(32, 2500);
		sheet1.setColumnWidth(33, 2000);
		sheet1.setColumnWidth(34, 9999);
		sheet1.setColumnWidth(35, 9999);
		sheet1.setColumnWidth(36, 9999);
		/**表格内容加表格线*/
		for(int j=1;j<sheet1.getPhysicalNumberOfRows();j++){
			Row row=sheet1.getRow((short)j);
			for(int k=0;k<row.getPhysicalNumberOfCells();k++){
				row.getCell(k).setCellStyle(styleBody);
			}
		}
		/**表头加粗等*/
		Row row=sheet1.getRow((short)0);
		for(int k=0;k<row.getPhysicalNumberOfCells();k++){
			row.getCell(k).setCellStyle(styleHead);
		}
		
		

	} 
}
