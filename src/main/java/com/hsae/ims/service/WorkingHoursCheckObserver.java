package com.hsae.ims.service;
import com.hsae.ims.entity.DailyReport;
/***
 * 工时核对观察者
 * @author panchaoyang
 *
 */
public interface WorkingHoursCheckObserver {
	/**优先级*/
	public int getPriority();
	public void update(DailyReport dailyReport);
}
