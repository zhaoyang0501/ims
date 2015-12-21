package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  家庭信息。
 *
 */
public class UserFamilyDTO {
	
	private long id;
	
	//家庭成员。
	private String familyName;

	//成员年龄。
	private int familyAge ;
	
	//成员称谓。
	private String familyTitle;
	
	//成员单位及职务。
	private String familyCompany;
	
	//成员联系方式。
	private String familyMobile;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public int getFamilyAge() {
		return familyAge;
	}

	public void setFamilyAge(int familyAge) {
		this.familyAge = familyAge;
	}

	public String getFamilyTitle() {
		return familyTitle;
	}

	public void setFamilyTitle(String familyTitle) {
		this.familyTitle = familyTitle;
	}

	public String getFamilyCompany() {
		return familyCompany;
	}

	public void setFamilyCompany(String familyCompany) {
		this.familyCompany = familyCompany;
	}

	public String getFamilyMobile() {
		return familyMobile;
	}

	public void setFamilyMobile(String familyMobile) {
		this.familyMobile = familyMobile;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

}
