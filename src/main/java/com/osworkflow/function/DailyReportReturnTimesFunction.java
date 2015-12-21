package com.osworkflow.function;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.WorkflowEntry;
@Service
public class DailyReportReturnTimesFunction implements FunctionProvider {
	@Autowired
	UserService userService;
	@Autowired
	DailyReportService dailyReportService;
	@Autowired
	WeekReportService weekReportService;
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		  WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
		  WeekReport weekReport=weekReportService.findByOsworkflow(entry.getId());
		  weekReport.setRejects(weekReport.getRejects()+1);
		  weekReportService.saveWeekReport(weekReport);
	}

}
