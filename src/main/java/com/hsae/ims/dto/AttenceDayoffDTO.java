package com.hsae.ims.dto;

/**
 * @author zhaozhou
 */
public class AttenceDayoffDTO {
	
		private String index;
		
		/** 请假日期 */
		private String dayoffDate;
		
		/** 请假人 */
		private String userName;
		
		/** 请假人部门 */
		private String deptName;

		/** 请假开始日期 */
		private String startTime;
		
		/** 请假结束日期 */
		private String endTime;
		
		/** 总工时 */
		private Float spendHours;
		
		/** 假别
		 * 1: 调休
		 * 2：  事假
		 * 3：  产假
		 * 4： 哺乳假
		 * 5： 陪产假
		 *  
		 *  */
		private String dayoffType;
		
		/** 请假原因 */
		private String remark;
		
		/** 录入时间 */
		private String saveTime;


		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getDayoffDate() {
			return dayoffDate;
		}

		public void setDayoffDate(String dayoffDate) {
			this.dayoffDate = dayoffDate;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		
		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public Float getSpendHours() {
			return spendHours;
		}

		public void setSpendHours(Float spendHours) {
			this.spendHours = spendHours;
		}

		public String getDayoffType() {
			return dayoffType;
		}

		public void setDayoffType(String dayoffType) {
			this.dayoffType = dayoffType;
		}

		public String getSaveTime() {
			return saveTime;
		}

		public void setSaveTime(String saveTime) {
			this.saveTime = saveTime;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

}
