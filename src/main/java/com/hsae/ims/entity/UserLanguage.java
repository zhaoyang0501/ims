package com.hsae.ims.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author zhaozhou
 *  语言信息。
 *
 */

@Entity
@Table(name="ims_user_language")
public class UserLanguage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	//语言种类。
	private String language;

	//熟练度  0:"" 1:一般  2:熟练。
	private Integer master ;
	
	//语言等级。
	private String level;
	
	//简历编号。
	private long resume;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getMaster() {
		return master;
	}

	public void setMaster(Integer master) {
		this.master = master;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}
	
}
