package com.dyst.BaseDataMsg.entities;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.annotations.GenericGenerator;

/**
 * Jjhomdsp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JJHOMDSP", catalog = "dc")
public class Jjhomdsp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459478741070668201L;
	private Integer id;
//	private Integer homdid;
	private Jjhomd jjhomd;
	private String sprjh;
	private String sprxm;
	private String sprdw;
	private String sprdwmc;
	private Timestamp spsj;
	private String spjg;
	private String spms;
	private String bzsm;
	private String splx;
	private Integer cxid;
	private Timestamp sqsj;
	/** 任务号 */
    @Transient
    private Task Task;
    /** 运行中的流程实例 */
    @Transient
    private ProcessInstance processInstance;
    /** 流程定义 */
    @Transient
    private ProcessDefinition processDefinition;
    /** 历史的流程实例 */
    @Transient
    private HistoricProcessInstance historicProcessInstance;
    @Transient
    private Map<String, Object> variables;

	// Constructors
    @Transient
	public Task getTask() {
		return Task;
	}

	public void setTask(Task task) {
		Task = task;
	}
    @Transient
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
    @Transient
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
    @Transient
	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}
    @Transient
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/** default constructor */
	public Jjhomdsp() {
	}

	/** full constructor */
	public Jjhomdsp(Integer homdid, String sprjh, String sprxm, String sprdw,
			String sprdwmc, Timestamp spsj, String spjg, String spms,
			String bzsm, String splx, Integer cxid, Timestamp sqsj) {
//		this.homdid = homdid;
		this.sprjh = sprjh;
		this.sprxm = sprxm;
		this.sprdw = sprdw;
		this.sprdwmc = sprdwmc;
		this.spsj = spsj;
		this.spjg = spjg;
		this.spms = spms;
		this.bzsm = bzsm;
		this.splx = splx;
		this.cxid = cxid;
		this.sqsj = sqsj;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
/*	@Column(name = "homdid")
	public Integer getHomdid() {
		return this.homdid;
	}

	public void setHomdid(Integer homdid) {
		this.homdid = homdid;
	}*/
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="homdid")
	public Jjhomd getJjhomd(){
		return this.jjhomd;
	}
	public void setJjhomd(Jjhomd jjhomd){
		this.jjhomd = jjhomd;
	}

	@Column(name = "sprjh", length = 30)
	public String getSprjh() {
		return this.sprjh;
	}

	public void setSprjh(String sprjh) {
		this.sprjh = sprjh;
	}

	@Column(name = "sprxm", length = 80)
	public String getSprxm() {
		return this.sprxm;
	}

	public void setSprxm(String sprxm) {
		this.sprxm = sprxm;
	}

	@Column(name = "sprdw", length = 20)
	public String getSprdw() {
		return this.sprdw;
	}

	public void setSprdw(String sprdw) {
		this.sprdw = sprdw;
	}

	@Column(name = "sprdwmc", length = 80)
	public String getSprdwmc() {
		return this.sprdwmc;
	}

	public void setSprdwmc(String sprdwmc) {
		this.sprdwmc = sprdwmc;
	}

	@Column(name = "spsj", length = 19)
	public Timestamp getSpsj() {
		return this.spsj;
	}

	public void setSpsj(Timestamp spsj) {
		this.spsj = spsj;
	}

	@Column(name = "spjg", length = 1)
	public String getSpjg() {
		return this.spjg;
	}

	public void setSpjg(String spjg) {
		this.spjg = spjg;
	}

	@Column(name = "spms", length = 1024)
	public String getSpms() {
		return this.spms;
	}

	public void setSpms(String spms) {
		this.spms = spms;
	}

	@Column(name = "bzsm", length = 1024)
	public String getBzsm() {
		return this.bzsm;
	}

	public void setBzsm(String bzsm) {
		this.bzsm = bzsm;
	}

	@Column(name = "splx", length = 2)
	public String getSplx() {
		return this.splx;
	}

	public void setSplx(String splx) {
		this.splx = splx;
	}

	@Column(name = "cxid")
	public Integer getCxid() {
		return this.cxid;
	}

	public void setCxid(Integer cxid) {
		this.cxid = cxid;
	}

	@Column(name = "sqsj", length = 19)
	public Timestamp getSqsj() {
		return this.sqsj;
	}

	public void setSqsj(Timestamp sqsj) {
		this.sqsj = sqsj;
	}

}