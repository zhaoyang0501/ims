package com.hsae.ims.task;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

/**
 * 考勤汇总定时任务执行
 * @author pzy
 *
 */
@Service
public class AttenceStatisticsJob {
	@Autowired
	private JdbcTemplate jdbcTemplateMysql;
	
	private static final Logger log =  LoggerFactory.getLogger(AttenceStatisticsJob.class);
	
	/**插入当月的数据*/
	private final String INIT_THIS_MONTH=" replace INTO ims_attence_statistics(USER,MONTH,start_date,end_date,create_time,current_increase,current_reduce,current_rest,last_rest) "+
			" SELECT id,?,?,?, now() ,0,0,0," +
			" IFNULL((SELECT SUM(t2.current_rest) FROM ims_attence_statistics t2  WHERE t2.user=t1.id AND t2.month=?),0)"+ 
			" FROM  ims_system_user t1 where t1.freeze=0 and  id!=1 and   id!=32 and id!=0";
	
	/**计算本月新增减少*/
	private final String UPDATE_INCREASE_AND_REDUCE="  UPDATE ims_attence_statistics t1 set "+  
		    "t1.current_increase= "+
		    " IFNULL((SELECT SUM(t2.check_hours) FROM ims_attence_overtime t2 WHERE t2.user=t1.user AND  oa_state=1 and t2.overtime_date>=t1.start_date AND t2.overtime_date<t1.end_date ),0), "+
		    "t1.current_reduce="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=30 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff10="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=10 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff20="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=20 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff40="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=40 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff50="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=50 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff60="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=60 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff70="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=70 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff80="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=80 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff90="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=90 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff100="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=100 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff110="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=110 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff120="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=120 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0),"+
		    "t1.dayoff130="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=130 and  t1.user=t2.user AND t2.dayoff_date>=t1.start_date AND t2.dayoff_date<t1.end_date) ,0), "+
		    " t1.dayoff10last_rest="+
		    " IFNULL((SELECT  SUM(last_rest) FROM ims_attence_statisticsyear t2 WHERE t1.user=t2.user AND t2.year=year(t1.start_date)) ,0),"+
		    " t1.dayoff10current_increase="+
		    " IFNULL((SELECT  SUM(current_increase) FROM ims_attence_statisticsyear t2 WHERE t1.user=t2.user AND t2.year=year(t1.start_date)) ,0),"+
		    " t1.dayoff10current_rest="+
		    " IFNULL((SELECT  SUM(spend_hours) FROM ims_attence_dayoff t2 WHERE t2.dayoff_type=10 and  t1.user=t2.user AND year(t2.dayoff_date)=?) ,0) "+
		    " WHERE t1.month=?";
	/**计算本月剩余*/
	
	private final String UPDATE_REST=" UPDATE ims_attence_statistics t1 set  t1.current_rest=(t1.last_rest+t1.current_increase-t1.current_reduce),t1.dayoff10current_rest=(t1.dayoff10last_rest+t1.dayoff10current_increase-t1.dayoff10current_rest)"+
									 "  WHERE t1.month=? ";
	/**计算绩效加班*/
	private final String UPDATE_OVERTIME_YEAR_1="UPDATE ims_attence_statistics t1 SET t1.over_time=t1.current_increase,t1.current_increase=0 "
			+ "WHERE t1.month=? and EXISTS (SELECT * FROM ims_user_resume t2 WHERE t2.user=t1.user AND t2.POSITION<=6) AND t1.current_increase+t1.last_rest>40 AND t1.last_rest>=40";
			
	
	private final String UPDATE_OVERTIME_YEAR_2="UPDATE ims_attence_statistics t1 SET t1.over_time=(t1.current_increase+t1.last_rest-40),"
			+ " t1.current_increase=t1.current_increase-t1.over_time  WHERE t1.month=? and EXISTS (SELECT * FROM ims_user_resume t2 WHERE t2.user=t1.user AND t2.POSITION<=6) AND t1.current_increase+t1.last_rest>40 AND t1.last_rest<40";
	
	private final String UPDATE_OVERTIME_MONTH="  UPDATE ims_attence_statistics t1 SET t1.over_time=t1.current_increase-18 ,t1.current_increase=18"
			+ " WHERE t1.month=? and EXISTS (SELECT * FROM ims_user_resume t2 WHERE t2.user=t1.user AND t2.POSITION>6) AND t1.current_increase>=18;";
			
	
	/***
	 * job 入口点
	 */
	public void execute() {
		/**处理上个月*/
		handleAttence(DateUtils.addMonths(new Date(),-1));
		/**处理本月*/
		handleAttence(new Date());
	}
	
	/****
	 * 处理date所在月份的数据
	 * @param date
	 */
	private  void handleAttence(Date date){
		final String  currentMonthStr=DateFormatUtils.format(date, "yyyy-MM");
		final String  currentYearStr=DateFormatUtils.format(date, "yyyy");
		final String lastMonthStr=DateFormatUtils.format(DateUtils.addMonths(date,-1), "yyyy-MM");
		final Date startDate=DateUtils.truncate(date,  Calendar.MONTH);
		final Date endDate=DateUtils.addMonths( DateUtils.truncate(date,  Calendar.MONTH), 1);
		log.info("处理[{}]月份",currentMonthStr);
		/**1初始化本月数据*/
		int size=jdbcTemplateMysql.update(INIT_THIS_MONTH,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
				ps.setObject(2, startDate);
				ps.setObject(3, endDate);
				ps.setObject(4, lastMonthStr);
			} 
		});
		log.info("step1:当月调休新增或者更新{}条数",size);
		
		/**2计算本月新增以及减少*/
		size=jdbcTemplateMysql.update(UPDATE_INCREASE_AND_REDUCE,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentYearStr);
				ps.setObject(2, currentMonthStr);
			} 
		});
		log.info("step2:计算本月新增或减少受影响的{}条记录",size);
		
		/**3计算本月剩余*/
		size=jdbcTemplateMysql.update(UPDATE_REST,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
			} 
		});
		log.info("step3:计算本月剩余受影响的{}条记录",size);
		
		/**4计算主任工程师的绩效加班1*/
		size=jdbcTemplateMysql.update(UPDATE_OVERTIME_YEAR_1,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
			} 
		});
		log.info("step4-1:计算主任工程师绩效加班{}条记录",size);
		
		/**4计算主任工程师的绩效加班2*/
		size=jdbcTemplateMysql.update(UPDATE_OVERTIME_YEAR_2,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
			} 
		});
		log.info("step4-2:计算主任工程师绩效加班{}条记录",size);
		
		/**4计算普通员工加班*/
		size=jdbcTemplateMysql.update(UPDATE_OVERTIME_MONTH,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
			} 
		});
		log.info("step4-3:计算普通员工绩效加班{}条记录",size);
		
		size=jdbcTemplateMysql.update(UPDATE_REST,new PreparedStatementSetter() { 
			public void setValues(PreparedStatement ps) throws SQLException { 
				ps.setObject(1, currentMonthStr);
			} 
		});
		log.info("step5:计算本月剩余受影响的{}条记录",size);
		
	}
}
