package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 岗位
 * @author caowei
 *
 */
@Entity
@Table(name="ims_system_position")
public class Position implements Serializable{

	/**
	 * 职称对一般岗位没有什么影响，到专家及公司更高层次有一定的影响，具体要看相关的规定。职称评定由公司统一组织，不定期开展。评价时间会通知大家。
	 *	职位应该是分两个方面，一个是技术方向，比如工程师、主任工程师、高级工程师；另外是管理方向：科长、部长等；
	 *	岗位是你所做的工作岗位，比如你们是嵌入式软件，我是技术管理
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
