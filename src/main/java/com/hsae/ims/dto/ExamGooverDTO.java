package com.hsae.ims.dto;

import java.io.Serializable;

import javax.persistence.Lob;

public class ExamGooverDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/** 试题类型1：单选 2：多选 3：判断 4：主观 **/
	private Integer type;
	/** 试题难度：1：低；2：中；3：高 **/
	private Integer difficulty;
	/** 题目 **/
	@Lob
	private String subject;
	/** 选项内容 **/
	@Lob
	private String optionContents;
	/** 选项个数 **/
	private Integer options;
	/**选项答案，多选时以逗号分隔**/
	private String answers;
	/** 试题解析 **/
	private String analysis;
	/** 答案 **/
	private String ownAnswer;
	/** 试题编号 **/
	private String qnum;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOptionContents() {
		return optionContents;
	}
	public void setOptionContents(String optionContents) {
		this.optionContents = optionContents;
	}
	public Integer getOptions() {
		return options;
	}
	public void setOptions(Integer options) {
		this.options = options;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getOwnAnswer() {
		return ownAnswer;
	}
	public void setOwnAnswer(String ownAnswer) {
		this.ownAnswer = ownAnswer;
	}
	public String getQnum() {
		return qnum;
	}
	public void setQnum(String qnum) {
		this.qnum = qnum;
	}
	
}
