package com.hsae.ims.dto;

/**
 * @author zhaozhou
 */
public class AttenceOvertimeDTO {
	
		private String index;
		
		private String projectName;
		
		private String deptName;
		
		/**加班人*/
		private String userName;
		
		/**加班日期*/
		private String overtimeDate;
		
		/**加班开始时间*/
		private String startTime;
		
		/**加班结束时间*/
		private String endTime;
		
		/**核对工时*/
		private Float checkHours;
		
		/**加班类型
		 * 1 平时
		 * 2 周末
		 * */
		private String overtimeType;
		
		/**刷卡记录*/
		private String brushRecord;
		
		/**备注*/
		private String remark;
		
		/**录入时间*/
		private String saveTime;

		
		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getOvertimeDate() {
			return overtimeDate;
		}

		public void setOvertimeDate(String overtimeDate) {
			this.overtimeDate = overtimeDate;
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

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Float getCheckHours() {
			return checkHours;
		}

		public void setCheckHours(Float checkHours) {
			this.checkHours = checkHours;
		}

		public String getOvertimeType() {
			return overtimeType;
		}

		public void setOvertimeType(String overtimeType) {
			this.overtimeType = overtimeType;
		}

		public String getBrushRecord() {
			return brushRecord;
		}

		public void setBrushRecord(String brushRecord) {
			this.brushRecord = brushRecord;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getSaveTime() {
			return saveTime;
		}

		public void setSaveTime(String saveTime) {
			this.saveTime = saveTime;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		
		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

}
