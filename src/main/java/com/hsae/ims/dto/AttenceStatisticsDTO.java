package com.hsae.ims.dto;


/**
 * @author zhaozhou
 */
public class AttenceStatisticsDTO {
	
		private String index;
		
		/**调休人*/
		private String userName;
		
		/**部门*/
		private String deptName;
		
		/**所属月份 yyyymm*/
		private String month;
		
		/**上月结余*/
		private String lastRest;
		
		/**本月新增*/
		private String currentIncrease;
		
		/**本月减少*/
		private String currentReduce;
		
		/**本月剩余*/
		private String currentRest;
		
		/**统计日期起*/
		private String startDate;
		
		/**统计日期止*/
		private String endDate;
		
		/**生成日期*/
		private String createTime;
		
		
		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getLastRest() {
			return lastRest;
		}

		public void setLastRest(String lastRest) {
			this.lastRest = lastRest;
		}

		public String getCurrentIncrease() {
			return currentIncrease;
		}

		public void setCurrentIncrease(String currentIncrease) {
			this.currentIncrease = currentIncrease;
		}

		public String getCurrentReduce() {
			return currentReduce;
		}

		public void setCurrentReduce(String currentReduce) {
			this.currentReduce = currentReduce;
		}

		public String getCurrentRest() {
			return currentRest;
		}

		public void setCurrentRest(String currentRest) {
			this.currentRest = currentRest;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

}
