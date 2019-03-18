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
@Table(name = "JJHOMDCXB", catalog = "dc")
public class JjhomdCx implements java.io.Serializable {

	// Fields

	

	private Integer id;
//	private Integer homdid;
	private Timestamp sqsj;
	private Timestamp spsj;
	private String cxyy;
	private String cxbz;
	private String jlzt;
	private String spjg;
	private Jjhomd jjhomd;
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


	public JjhomdCx() {
		super();
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

	@Column(name = "sqsj", length = 19)
	public Timestamp getSqsj() {
		return this.sqsj;
	}

	public void setSqsj(Timestamp sqsj) {
		this.sqsj = sqsj;
	}
	@Column(name = "spsj", length = 19)	
	public Timestamp getSpsj() {
		return spsj;
	}

	public void setSpsj(Timestamp spsj) {
		this.spsj = spsj;
	}

	@Column(name = "cxyy", length = 1024)
	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}
	@Column(name = "cxbz", length = 1024)
	public String getCxbz() {
		return cxbz;
	}

	public void setCxbz(String cxbz) {
		this.cxbz = cxbz;
	}
	@Column(name = "jlzt", length = 5)
	public String getJlzt() {
		return jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	@Column(name = "spjg", length = 1)
	public String getSpjg() {
		return spjg;
	}

	public void setSpjg(String spjg) {
		this.spjg = spjg;
	}

}
