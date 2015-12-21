package com.hsae.ims.entity.message;
import java.util.Date;

import javax.persistence.Entity;
@Entity
public class WorkFlowMessage extends Message {
	public WorkFlowMessage(){
		super();
	}
	public WorkFlowMessage(String context,String link){
		this.title="工作流发生了变化";
		this.context=context;
		this.link=link;
		this.createDate=new Date();
	}
	
}
