package com.hsae.ims.entity.message;
import java.util.Date;

import javax.persistence.Entity;

import com.hsae.ims.entity.User;
@Entity
public class AttenceMessage extends Message {
	public AttenceMessage(){
		super();
	}
	public AttenceMessage(String context,Long user){
		this.type="100";
		this.title="考勤提醒";
		this.context=context;
		this.link="home/myattence";
		this.user=new User(user);
		this.createDate=new Date();
	} 
	
}
