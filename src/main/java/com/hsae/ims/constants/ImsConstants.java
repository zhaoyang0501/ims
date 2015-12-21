package com.hsae.ims.constants;
import java.util.HashMap;
import java.util.Map;
public interface ImsConstants {
		public interface WorkFlowConstants{
			final String DAILY_REPORT = "dailyReport";
			final String REIMBURSE = "reimburse";
			final String TRAINING_REQUIRE_GATHER = "trainingRequireGather";
			final String INTERNAL_TRAINING = "internalTraining";
			final String ABSENTEE = "absentee";
			final String WORLFLOW_DAYOFF = "workflowDayoff";
			final String WORLFLOW_OVERTIME = "workflowOverTime";
			final String WORLFLOW_AWAY = "workflowAway";
			@SuppressWarnings("serial")
			final static Map<String,String> WORKFLOWNAME_MAP = new HashMap<String,String>() {
				{    
				    put(DAILY_REPORT, "周报审核流程");    
				    put(REIMBURSE, "餐费报销流程");
				    put(TRAINING_REQUIRE_GATHER, "培训需求收集流程");
				    put(ABSENTEE, "补打卡申请流程");
				    put(WORLFLOW_DAYOFF, "请假流程");
				    put(WORLFLOW_OVERTIME, "加班流程");
				    put(WORLFLOW_AWAY, "外出申请流程");
				}};
		}
		public interface CasConstants{
			final Integer BOOKMANAGE_APPID = 1;
			final Integer DISCUZZ_APPID = 2;
			final Integer RTC_APPID = 3;
			final Integer CONFLUENCE_APPID = 4;
		}
		public interface DailyReportConstants{
			final String DAYYOFF_TYPE="6";
			final String OVERTIME_TYPE="7";
			final String PROJECT_TYPE="1";
		} 
		public interface reimburse{
			final Double STANDARD = 12D;
		}
		public interface training{
			/**
			 * 讲师类型
			 */
			@SuppressWarnings("serial")
			final static Map<String,String> USER_TYPE = new HashMap<String,String>() {
				{    
				    put("1", "主任");    
				    put("2", "普通");  
				}};
		}
		public interface project{
			@SuppressWarnings("serial")
			final static Map<String,String> COMPLEX = new HashMap<String,String>() {
				{    
				    put("0", "创建");
				    put("1", "进行中");
				    put("2", "结束");
				    put("3", "关闭");
				}};
		}
		public interface BookConfig{
			final String SEND_SMTP = "smtp.163.com";
			final String EMAIL_ID ="hsaeyangzhou@163.com";
			final String EMAIL_PWD = "@WSX3edc";
		}
		public interface WebSoket{
			final String USERNAME = "websoket_username";
			final String USERID = "websoket_userid";
			
		}
}
