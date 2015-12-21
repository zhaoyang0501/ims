package com.hsae.ims.dto;
/***
 * 加班工作流计算最高加班时间
 * @author panchaoyang
 *
 */
public class OverTimeMaxHour {
	private Double maxHours;
	private Double nowHours;
	/**1按月，2按年*/
	private String type;
	public Double getMaxHours() {
		return maxHours;
	}
	public void setMaxHours(Double maxHours) {
		this.maxHours = maxHours;
	}
	public Double getNowHours() {
		return nowHours;
	}
	public void setNowHours(Double nowHours) {
		this.nowHours = nowHours;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
