package com.dyst.DyMsg.entities;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.annotations.GenericGenerator;

/**
 * Dyxxsp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DYXXSP", catalog = "dc")
public class Dyxxsp implements java.io.Serializable {

	// Fields

	private Integer id;
	private Dyxx dyxx;
	private String sprjh;
	private String sprxm;
	private String sprdw;
	private String sprdwmc;
	private Timestamp spsj;
	private String spjg;
	private String spms;
	private String bzw;
	private Integer xdid;
	private String bzsm;
	private String splx;
	private Integer cxid;
	private Timestamp sqsj;
	private Integer swid;

	
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

	/** default constructor */
	public Dyxxsp() {
	}

	/** full constructor */
	public Dyxxsp(Dyxx dyxx, String sprjh, String sprxm, String sprdw,
			String sprdwmc, Timestamp spsj, String spjg, String spms,
			String bzw, Integer xdid, String bzsm, String splx, Integer cxid,
			Timestamp sqsj, Integer swid) {
		this.dyxx = dyxx;
		this.sprjh = sprjh;
		this.sprxm = sprxm;
		this.sprdw = sprdw;
		this.sprdwmc = sprdwmc;
		this.spsj = spsj;
		this.spjg = spjg;
		this.spms = spms;
		this.bzw = bzw;
		this.xdid = xdid;
		this.bzsm = bzsm;
		this.splx = splx;
		this.cxid = cxid;
		this.sqsj = sqsj;
		this.swid = swid;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dyxxId2")
	public Dyxx getDyxx() {
		return this.dyxx;
	}

	public void setDyxx(Dyxx dyxx) {
		this.dyxx = dyxx;
	}

	@Column(name = "sprjh", length = 10)
	public String getSprjh() {
		return this.sprjh;
	}

	public void setSprjh(String sprjh) {
		this.sprjh = sprjh;
	}

	@Column(name = "sprxm", length = 20)
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

	@Column(name = "spjg", length = 2)
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

	@Column(name = "bzw", length = 2)
	public String getBzw() {
		return this.bzw;
	}

	public void setBzw(String bzw) {
		this.bzw = bzw;
	}

	@Column(name = "xdid")
	public Integer getXdid() {
		return this.xdid;
	}

	public void setXdid(Integer xdid) {
		this.xdid = xdid;
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

	@Column(name = "swid")
	public Integer getSwid() {
		return this.swid;
	}

	public void setSwid(Integer swid) {
		this.swid = swid;
	}
	
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
}