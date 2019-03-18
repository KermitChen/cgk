package com.dyst.systemmanage.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MESSAGE", catalog = "dc")
public class Message implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5276420204649872226L;
	// Fields

	private Integer messageid;
	private String sendid;
	private String recid;
	private String statue;
	private String topic;
	private String content;
	private String url;
	private Timestamp sendtime;
	private String hasread;
	private String ywid;

	// Constructors

	/** default constructor */
	public Message() {
		this.sendtime = new Timestamp(new Date().getTime());
		this.statue = "1";
		this.hasread = "0";
	}

	/** full constructor */
	public Message(String sendid, String recid, String statue,String topic, String content,
			String url,Timestamp sendtime, String hasread,String ywid) {
		this.sendid = sendid;
		this.recid = recid;
		this.statue = statue;
		this.topic = topic;
		this.content = content;
		this.url = url;
		this.sendtime = sendtime;
		this.hasread = hasread;
		this.ywid = ywid;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "MESSAGEID", unique = true, nullable = false)
	public Integer getMessageid() {
		return this.messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	@Column(name = "SENDID", length = 20)
	public String getSendid() {
		return this.sendid;
	}

	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

	@Column(name = "RECID", length = 400)
	public String getRecid() {
		return this.recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	@Column(name = "STATUE", length = 2)
	public String getStatue() {
		return this.statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}
	
	@Column(name = "TOPIC", length = 100)
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "URL", length = 500)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "SENDTIME", length = 19)
	public Timestamp getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "HASREAD", length = 2)
	public String getHasread() {
		return this.hasread;
	}

	public void setHasread(String hasread) {
		this.hasread = hasread;
	}
	
	@Column(name = "YWID", length = 20)
	public String getYwid() {
		return ywid;
	}

	public void setYwid(String ywid) {
		this.ywid = ywid;
	}

}