package com.hsae.ims.service;

import java.util.Comparator;
/***
 * 工时核对观察者优先级比较器
 * @author panchaoyang
 *
 */
public class WorkingHoursCheckObserverComparator  implements Comparator<WorkingHoursCheckObserver> {
	@Override
	public int compare(WorkingHoursCheckObserver o1,WorkingHoursCheckObserver o2) {
		if(o1.getPriority()<o2.getPriority()) return -1;
		if(o1.getPriority()>o2.getPriority()) return 1;
		return 0;
	}

}
