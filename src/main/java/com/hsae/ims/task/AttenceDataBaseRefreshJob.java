package com.hsae.ims.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hsae.ims.dto.UserLateDTO;
import com.hsae.ims.entity.AttenceRefleshLog;
import com.hsae.ims.entity.WorkAndHolidaySetting;
import com.hsae.ims.service.AttenceRefleshLogService;
import com.hsae.ims.service.WorkAndHolidaySettingService;
/***
 * 考勤数据迁移
 * @author panchaoyang
 *
 */
@Service
public class AttenceDataBaseRefreshJob {
	
	private static final Logger log = LoggerFactory.getLogger(AttenceDataBaseRefreshJob.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateMysql;
	
	@Autowired
	private JdbcTemplate jdbcTemplateSqlserver;
	
	@Autowired
	private AttenceRefleshLogService attenceRefleshLogService;
	
	@Autowired
	private WorkAndHolidaySettingService workAndHolidaySettingService;
	
	/**获取迟到信息*/
	private String QUERY_LATE="SELECT t1.empnumber,t2.latemonth ,t3.lateyear FROM "+
					 " (SELECT empnumber FROM ims_system_user   WHERE attendance='1') t1"+
					 "    LEFT JOIN"+
					 "  (SELECT person_no  , COUNT(1) latemonth FROM ims_attence_brushrecord  WHERE state=70  AND MONTH(brush_date)=MONTH(?) AND year(brush_date)=year(?) GROUP BY person_id) t2"+
					 "    ON t1.empnumber=t2.person_no"+
					 "  LEFT JOIN "+
					 "  (SELECT person_no  , COUNT(1) lateyear FROM ims_attence_brushrecord  WHERE state=70  AND year(brush_date)=year(?) GROUP BY person_id) t3"+
					 "    ON t1.empnumber=t3.person_no";
	
	/**从sqlserver 取数据*/
	private String QUERY_BRUSHCARD_FROM_SQLSERVER="	SELECT    dbo.KQ_BrushCard.id_key,  dbo.KQ_BrushCard.Person_ID, dbo.V_STPerson.person_no,  "+
			 " 		dbo.V_STPerson.person_name, dbo.V_STPerson.dept_no, dbo.V_STPerson.dept_name,"+
			 "  	dbo.KQ_BrushCard.Card_No, dbo.KQ_BrushCard.Brush_Date, dbo.KQ_BrushCard.Brush_Data"+
			 " FROM      dbo.KQ_BrushCard LEFT OUTER JOIN dbo.V_STPerson "+
			 " 		ON dbo.KQ_BrushCard.Person_ID = dbo.V_STPerson.person_id"+
			 " where  dbo.KQ_BrushCard.Brush_Date=?"; 
	
	private String DELETE_BRUSHCARD_FROM_MYSQL="delete from ims_attence_sqlserver_brushrecord where brush_date=?";
	
	/**插入考勤数据同步表*/
	private String INSERT_BRUSHCARD_IN_MYSQL="INSERT INTO ims_attence_sqlserver_brushrecord VALUES(?,?,?,?,?,?,?,?,?)";
	
	/**插入考勤业务表*/
	private String INSERT_IMSBRUSHCARD_IN_MYSQL="  INSERT INTO ims_attence_brushrecord(brush_date,person_no,state,person_name,person_id)  SELECT ?, t1.empnumber, '60' ,chinesename,id  FROM ims_system_user t1  WHERE t1.attendance='1'";
	
	/**检索考勤业务表有没有当天的数据*/
	private String COUNT_IMSBRUSHCARD_IN_MYSQL="select count(1) from ims_attence_brushrecord where brush_date=? ";
	
	private String UPDATE_IMSBRUSHCARD_IN_MYSQL="UPDATE ims_attence_brushrecord t1, "+
						" (SELECT   * FROM ims_attence_sqlserver_brushrecord  ) t2"+
						" set t1.brush_data=t2.brush_data,t1.card_no=t2.card_no,t1.dept_name=t2.dept_name,t1.person_name=t2.person_name ,t1.state='60' "+
						"  ,t1.transfertime=NOW()"+
						" WHERE t1.brush_date=t2.brush_date AND t1.person_no= t2.person_no and t1.brush_date=?";
	
	/**修改正班状态*/
	private String UPDATE_IMSBRUSHCARD_STATE_IN_MYSQL="UPDATE ims_attence_brushrecord t1 SET  t1.state='10' WHERE t1.brush_date=? AND t1.person_no=?";
	
	/**修改迟到状态*/
	private String UPDATE_IMSBRUSHCARD_LATE_STATE_IN_MYSQL="UPDATE ims_attence_brushrecord t1 SET  t1.state='70' WHERE t1.brush_date=? AND t1.person_no=?";
	
	/**修改节假日状态*/
	private String UPDATE_IMSBRUSHCARD_HOLIDAY_STATE_IN_MYSQL="UPDATE ims_attence_brushrecord t1 set  t1.state='80' WHERE t1.brush_date=?  AND t1.brush_data IS null";
	
	/**修改请假*/
	private String UPDATE_IMSBRUSHCARD_DAYOFF_STATE_IN_MYSQL="  UPDATE ims_attence_brushrecord t1 set t1.state='20' WHERE  t1.brush_date=? "
			+ "AND EXISTS(SELECT 1 FROM ims_attence_dayoff t2 WHERE TO_DAYS(t2.end_time)>=TO_DAYS(t1.brush_date) AND TO_DAYS(t2.start_time)<=TO_DAYS(t1.brush_date)  AND t2.user=t1.person_id)";
   
	/**修改出差*/
	private String UPDATE_IMSBRUSHCARD_TRAVEL_STATE_IN_MYSQL="  UPDATE ims_attence_brushrecord t1 set t1.state='40' WHERE  t1.brush_date=? "
			+ "AND EXISTS(SELECT 1 FROM ims_attence_travel t2 WHERE TO_DAYS(t2.end_time)>=TO_DAYS(t1.brush_date) AND TO_DAYS(t2.start_time)<=TO_DAYS(t1.brush_date)  AND t2.user=t1.person_id)";
   

	/***
	 * 迁移某天的数据
	 * @throws SQLException 
	 * @throws ParseException
	 */
	public synchronized  AttenceRefleshLog reflashAttenceData(final Date date){
		
		Long startExecuteTime=System.currentTimeMillis();
		AttenceRefleshLog attenceRefleshLog=new AttenceRefleshLog();
		log.info(DateFormatUtils.format(date, "yyyy-MM-dd  HH:mm")+ "开始进行数据迁移！迁移[{}]的数据",date);
		attenceRefleshLog.setAttenceDate(date);
		attenceRefleshLog.setBeginTime(new java.sql.Timestamp(System.currentTimeMillis()));
		attenceRefleshLog.setState("10");
		/**获取所有用户的迟到记录**/
		List<UserLateDTO> userLates=jdbcTemplateMysql.query(QUERY_LATE, new Object[] { date,date,date},
				new RowMapper<UserLateDTO>() {  
	            public UserLateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {  
	            	UserLateDTO user = new UserLateDTO(); 
	            	   user.setPersonNo(rs.getString("empnumber"));
	            	   user.setLateMonth(rs.getInt("latemonth"));  
	            	   user.setLateYear(rs.getInt("lateyear"));  
	                return user;  
	            }  
        });
		Map<String, UserLateDTO> userlatesMap= new  HashMap<String, UserLateDTO>();
		for(UserLateDTO userLateDTO:userLates){
			userlatesMap.put(userLateDTO.getPersonNo(), userLateDTO);
		}
		
		/**第一步 取出sqlserver的考勤数据*/
		final List<Map<String,Object>> brushcardFromSqlservers = jdbcTemplateSqlserver.queryForList(QUERY_BRUSHCARD_FROM_SQLSERVER,new Object[] {date});
		log.info("第一步：取考勤数据, 考勤数据库中[{}]共取出 [{}]条数据",date,brushcardFromSqlservers.size());
		attenceRefleshLog.setRecordNum(brushcardFromSqlservers.size());
		if(brushcardFromSqlservers==null||brushcardFromSqlservers.size()==0) return attenceRefleshLog;
		
		/**第二步 删除mysql对应表的当天的记录*/
		jdbcTemplateMysql.update("delete from ims_attence_refleshlog where attence_date=? ", new Object[] {date});
        Integer size=jdbcTemplateMysql.update(DELETE_BRUSHCARD_FROM_MYSQL, new Object[] {date});
        log.info("第二步：删除IMS中当天考勤记录,IMS系统中[{}]共删除[{}]",date,size);
       
        /**第三步 插入mysql数据*/
        BatchPreparedStatementSetter set = new BatchPreparedStatementSetter(){
        	public int getBatchSize() {
        		return brushcardFromSqlservers.size();
        	}
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)
					throws SQLException {
				Map<String,Object> map = brushcardFromSqlservers.get(i);
				ps.setObject(1, map.get("id_key"));
				ps.setLong(2, (Integer)map.get("person_id"));
				ps.setObject(3, map.get("person_no"));
				ps.setObject(4, map.get("person_name"));
				ps.setObject(5, map.get("dept_no"));
				ps.setObject(6, map.get("dept_name"));
				ps.setObject(7, map.get("card_no"));
				ps.setObject(8, map.get("brush_date"));
				ps.setObject(9, map.get("brush_data"));
			}};
		int[] sizeAray=jdbcTemplateMysql.batchUpdate(INSERT_BRUSHCARD_IN_MYSQL, set);
		log.info("第三步：插入MYSQL中的考勤同步表,IMS系统中共插入[{}]条数据",sizeAray.length);
		
		/**第四部，判断mysql的*/
		Integer count=jdbcTemplateMysql.queryForObject(COUNT_IMSBRUSHCARD_IN_MYSQL,new Object[] {date}, java.lang.Integer.class);
		log.info("第四步：插入MYSQL考勤业务表,[{}]共有[{}]记录",date,count);
		if(count==0){
			/**插入*/
			size=jdbcTemplateMysql.update(INSERT_IMSBRUSHCARD_IN_MYSQL,new PreparedStatementSetter() { 
				public void setValues(PreparedStatement ps) throws SQLException { 
					ps.setObject(1, date);
				} 
			});
			log.info("第四步：插入MYSQL考勤业务表,共插入[{}]记录",size);
		}
		/**第五步，更新刷卡数据*/
		size=jdbcTemplateMysql.update(UPDATE_IMSBRUSHCARD_IN_MYSQL,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, date);
			} 
		});
		log.info("第五步：更新mysql考勤业务表的刷卡数据,共更新[{}]记录",size);
		
		/**第六步，判断正班状态*/
		WorkAndHolidaySetting workAndHolidaySetting=workAndHolidaySettingService.findWorkAndHolidaySetting(date);
		final List<Map<String,Object>> brushDataOkLists= new ArrayList<Map<String,Object>>();
		final List<String> brushDataLateLists= new ArrayList<String>();
		 for(Map<String,Object> map:brushcardFromSqlservers ){
			 String brush_Data=(String)map.get("Brush_Data");
			 String str[]=brush_Data.split(" ");
			 String user_no=(String)map.get("person_no");
			 try {
				if(isBrushDataOk(str,date,workAndHolidaySetting)){
					brushDataOkLists.add(map);
				 }
				/**每月有没有超限**/
				else if(isBrushDataLate(str,date,workAndHolidaySetting)&&
						userlatesMap.get(user_no).getLateMonth()<UserLateDTO.MONTH_MAX&&
						userlatesMap.get(user_no).getLateYear()<=UserLateDTO.YEAR_MAX){
					brushDataLateLists.add(user_no);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("判断正班失败数据迁移成功！检查刷卡记录时候有误"+str);
			}
	     } 
		 BatchPreparedStatementSetter updateStateSet = new BatchPreparedStatementSetter(){
	        	public int getBatchSize() {
	        		return brushDataOkLists.size();
	        	}
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i)
						throws SQLException {
					Map<String,Object> map = brushDataOkLists.get(i);
					ps.setObject(2, map.get("person_no"));
					ps.setObject(1, map.get("brush_date"));
				}};
		sizeAray= jdbcTemplateMysql.batchUpdate(UPDATE_IMSBRUSHCARD_STATE_IN_MYSQL, updateStateSet);
		log.info("第六步：判断正班状态，共更新[{}]记录",sizeAray.length);
		
		/**第7步，判断迟到状态*/
		BatchPreparedStatementSetter updateLateStateSet = new BatchPreparedStatementSetter(){
	        	public int getBatchSize() {
	        		return brushDataLateLists.size();
	        	}
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i)
						throws SQLException {
					ps.setObject(2, brushDataLateLists.get(i));
					ps.setObject(1, date);
				}};
		sizeAray= jdbcTemplateMysql.batchUpdate(UPDATE_IMSBRUSHCARD_LATE_STATE_IN_MYSQL, updateLateStateSet);
		log.info("第七步：判断迟到状态，共更新[{}]记录",sizeAray.length);
		
		/**第八步，更新请假状态*/
		size=jdbcTemplateMysql.update(UPDATE_IMSBRUSHCARD_DAYOFF_STATE_IN_MYSQL,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, date);
			} 
		});
		log.info("第八步：更新mysql考勤业务表的请假状态,共更新[{}]记录",size);
		
		/**第九步，更新请假状态*/
		size=jdbcTemplateMysql.update(UPDATE_IMSBRUSHCARD_TRAVEL_STATE_IN_MYSQL,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, date);
			} 
		});
		log.info("第九步：更新mysql考勤业务表的请假状态,共更新[{}]记录",size);
		
		/**第十步，判断节假日*/
		if(workAndHolidaySetting.getType()==0){
			size=jdbcTemplateMysql.update(UPDATE_IMSBRUSHCARD_HOLIDAY_STATE_IN_MYSQL,new PreparedStatementSetter() { 
				public void setValues(PreparedStatement ps) throws SQLException { 
					ps.setObject(1, date);
				} 
			});
			log.info("第十步：判断节假日状态，共更新[{}]记录",size);
		}
		
		log.info("数据迁移成功！共用时[{}]秒",(System.currentTimeMillis()-startExecuteTime)/1000);
		attenceRefleshLog.setLog(MessageFormat.format("数据迁移成功！共用时[{0}]秒",(System.currentTimeMillis()-startExecuteTime)/1000));
		attenceRefleshLog.setEndTime(new Timestamp(System.currentTimeMillis()));
		attenceRefleshLog.setTotalTime(((System.currentTimeMillis()-startExecuteTime)/1000));
		return attenceRefleshLog;
	}
	
	/***
	 * 迁移当天的
	 * @throws ParseException
	 */
	public void executeToday() throws ParseException{
		AttenceRefleshLog attenceRefleshLog = this.reflashAttenceData(DateUtils.truncate(new java.util.Date(),  Calendar.DAY_OF_MONTH));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceRefleshLog);
	}
	
	/***
	 * 迁移昨天的
	 * @throws ParseException
	 */
	public void executeYesterday() throws ParseException{
		Date yesterday=DateUtils.addDays(DateUtils.truncate(new java.util.Date(),  Calendar.DAY_OF_MONTH), -1);
		AttenceRefleshLog attenceRefleshLogYesterday = this.reflashAttenceData(yesterday);
		attenceRefleshLogService.saveAttenceRefleshLog(attenceRefleshLogYesterday);
		
		/**如果是礼拜一迁移周6and周5*/
		if(new DateTime(new java.util.Date()).getDayOfWeek()==1){
			Date  saturday=DateUtils.addDays(DateUtils.truncate(new java.util.Date(),  Calendar.DAY_OF_MONTH), -2);
			Date friday=DateUtils.addDays(DateUtils.truncate(new java.util.Date(),  Calendar.DAY_OF_MONTH), -3);
			
			AttenceRefleshLog attenceRefleshLogSaturday = this.reflashAttenceData(saturday);
			attenceRefleshLogService.saveAttenceRefleshLog(attenceRefleshLogSaturday);
			
			AttenceRefleshLog attenceRefleshLogFriday= this.reflashAttenceData(friday);
			attenceRefleshLogService.saveAttenceRefleshLog(attenceRefleshLogFriday);
		}
		
	}

	/***
	 * 判断是否正班
	 * @param brushdate 刷卡数据
	 * @param curdate 当前日期
	 * @return
	 * @throws ParseException
	 */
	private Boolean isBrushDataOk(String brushdate[],Date curdate,WorkAndHolidaySetting workAndHolidaySetting) throws ParseException{
		Assert.notNull(workAndHolidaySetting);
		/**如果是节日，不能算正班*/
		if(workAndHolidaySetting.getType()==0) return false;
		if(brushdate==null) return false;
		/**8:31*/
		Date startWorkTime=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "
					+workAndHolidaySetting.getForeworktime(),"yyyy-MM-dd HH:mm"),1);
		/**17:59*/
		Date endWorkTime=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "
					+workAndHolidaySetting.getAfterresttime(),"yyyy-MM-dd HH:mm"),-1);
		/**换算成日期方便计算*/
		Date[] dates= new Date[brushdate.length];
		for(int i=0;i<dates.length;i++){
			dates[i]= DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+brushdate[i],"yyyy-MM-dd HH:mm");
		}
		
		if(brushdate.length>=2&&dates[0].before(startWorkTime)&&dates[brushdate.length-1].after(endWorkTime)
				&&!isBrushDataInWorkTime(dates,curdate,workAndHolidaySetting)){
			return true;
		}
		else
			return false;
	}
	/***
	 * 判断是否迟到
	 * @param brushdate 刷卡数据
	 * @param curdate 当前日期
	 * @return
	 * @throws ParseException
	 */
	private Boolean isBrushDataLate(String brushdate[],Date curdate,WorkAndHolidaySetting workAndHolidaySetting) throws ParseException{
		Assert.notNull(workAndHolidaySetting);
		/**如果是节日，不能算迟到*/
		if(workAndHolidaySetting.getType()==0) return false;
		
		if(brushdate==null) return false;
		/**8:31*/
		Date startWorkTime=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "
					+workAndHolidaySetting.getForeworktime(),"yyyy-MM-dd HH:mm"),1);
		Date lateDeadLine=DateUtils.addMinutes(startWorkTime,30);
		/**17:59*/
		Date endWorkTime=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "
					+workAndHolidaySetting.getAfterresttime(),"yyyy-MM-dd HH:mm"),-1);
		/**换算成日期方便计算*/
		Date[] dates= new Date[brushdate.length];
		for(int i=0;i<dates.length;i++){
			dates[i]= DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+brushdate[i],"yyyy-MM-dd HH:mm");
		}
		List<Date> lateDatesList=new ArrayList<Date>();
		for(int i=0;i<dates.length;i++){
			if(dates[i].after(lateDeadLine)) lateDatesList.add(dates[i]);
		}
		/**首次打卡时间在上班时间与迟到死亡线之间，最后一次打卡在下班时间后*/
		if(brushdate.length>=2&&dates[0].after(startWorkTime)&&dates[0].before(lateDeadLine)&&dates[brushdate.length-1].after(endWorkTime)
				&&!isBrushDataInWorkTime(lateDatesList.toArray(new  Date[lateDatesList.size()]),curdate,workAndHolidaySetting)){
			return true;
		}
		else
			return false;
	}
	/***
	 * 判断打卡记录是不是在上班时间打的卡，如果是上班时间打的卡视为无效
	 * @param dates
	 * @param curdate
	 * @return
	 * @throws ParseException
	 */
	private Boolean isBrushDataInWorkTime( Date[] dates, Date curdate,WorkAndHolidaySetting workAndHolidaySetting) throws ParseException{
		Date startAm=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+workAndHolidaySetting.getForeworktime(),"yyyy-MM-dd HH:mm"),1);
		Date endAm=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+workAndHolidaySetting.getForeresttime(),"yyyy-MM-dd HH:mm"),-1);
		Date startPm=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+workAndHolidaySetting.getAfterworktime(),"yyyy-MM-dd HH:mm"),+1);
		Date endPm=DateUtils.addMinutes(DateUtils.parseDate(DateFormatUtils.format(curdate, "yyyy-MM-dd")+" "+workAndHolidaySetting.getAfterresttime(),"yyyy-MM-dd HH:mm"),-1);
		for(int i=0;i<dates.length;i++){
			if((dates[i].after(startAm)&&dates[i].before(endAm))||(dates[i].after(startPm)&&dates[i].before(endPm)))
				return true;
		}
		return false;
	}
}
