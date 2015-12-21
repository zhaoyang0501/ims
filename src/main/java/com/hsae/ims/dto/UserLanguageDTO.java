package com.hsae.ims.dto;


/**
 * 
 * @author zhaozhou
 *  语言信息。
 *
 */

public class UserLanguageDTO {
	
	private long id;
	
	//语言种类。
	private String language;

	//熟练度  0:一般 1:熟练。
	private String master ;
	
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

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
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
