package com.hsae.ims.dto;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.User;
public class ReimburseMyapproveDTO {
	private DailyReport dailyReport;
	private User user;
	private AttenceBrushRecord  attenceBrushRecord;
	private AttenceOverTime attenceOverTime;
	public AttenceBrushRecord getAttenceBrushRecord() {
		return attenceBrushRecord;
	}
	public void setAttenceBrushRecord(AttenceBrushRecord attenceBrushRecord) {
		this.attenceBrushRecord = attenceBrushRecord;
	}
	public DailyReport getDailyReport() {
		return dailyReport;
	}
	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}
	public AttenceOverTime getAttenceOverTime() {
		return attenceOverTime;
	}
	public void setAttenceOverTime(AttenceOverTime attenceOverTime) {
		this.attenceOverTime = attenceOverTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
