package com.dyst.dispatched.entities;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (CKSQ)
 * 
 * @author bianj
 * @version 1.0.0 2016-03-21
 */
@Entity
@Table(name = "CKSQ")
public class Withdraw implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1361385271170575931L;
    
    /** 控撤编号 */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "native")//需MYSQL底层数据库支持，设置ID为主键，并自动增长
	@GeneratedValue(generator = "idGenerator")
    @Column(name = "ckid", unique = true, nullable = false, length = 10)
    private Integer ckid;
    
    /** 布控信息 */
    @Transient
    private Dispatched dispatched;
    /** 撤销人电话 */
    @Column(name = "cxsqrdh", nullable = true, length = 20)
    private String cxsqrdh;
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
    
    
    
    /** 控布编号 */
    @Column(name = "bkid", nullable = true, length = 22)
    private Integer bkid;
    
    /** 撤销申请人 */
    @Column(name = "cxsqr", nullable = true, length = 128)
    private String cxsqr;
    
    /** 撤销申请人警号 */
    @Column(name = "cxsqrjh", nullable = true, length = 50)
    private String cxsqrjh;
    
    /** 撤销申请单位 */
    @Column(name = "cxsqdw", nullable = true, length = 12)
    private String cxsqdw;
    
    /** 撤控申请单位名称 */
    @Column(name = "cxsqdwmc", nullable = true, length = 128)
    private String cxsqdwmc;
    
    /** 撤销申请时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "cxsqsj", nullable = true)
    private Date cxsqsj;
    
    /** 撤控原因代码（01；02；03；04；05；99） 暂时无用*/
    @Column(name = "ckyydm", nullable = true, length = 2)
    private String ckyydm;
    
    /** 撤控原因描述 */
    @Column(name = "ckyyms", nullable = true, length = 1024)
    private String ckyyms;
    
    /** 业务状态 1、已撤控，2、审批中，3、已取消*/
    @Column(name = "ywzt", nullable = true, length = 2)
    private String ywzt;
    
    /** 更新时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "gxsj", nullable = true)
    private Date gxsj;
    
    /** 直接撤控:1是 0否*/
    @Column(name = "zjck", nullable = true, length = 1)
    private String zjck;
    
    /** 通知单位 */
    @Column(name = "tzdw", nullable = true, length = 2048)
    private String tzdw;
    
    /** 通知内容 */
    @Column(name = "tznr", nullable = true, length = 1024)
    private String tznr;
    
	private String lskhbm;//隶属考核部门
	private String lskhbmmc;//隶属考核部门名称
	private String by1;//备用1
    
    /**
     * 获取控撤编号
     * 
     * @return 控撤编号
     */
     public Integer getCkid() {
        return this.ckid;
     }
     
    /**
     * 设置控撤编号
     * 
     * @param ckid
     *          控撤编号
     */
     public void setCkid(Integer ckid) {
        this.ckid = ckid;
     }
    
    /**
     * 获取控布编号
     * 
     * @return 控布编号
     */
     public Integer getBkid() {
        return this.bkid;
     }
     
    /**
     * 设置控布编号
     * 
     * @param bkid
     *          控布编号
     */
     public void setBkid(Integer bkid) {
        this.bkid = bkid;
     }
    
    /**
     * 获取撤销申请人
     * 
     * @return 撤销申请人
     */
     public String getCxsqr() {
        return this.cxsqr;
     }
     
    /**
     * 设置撤销申请人
     * 
     * @param cxsqr
     *          撤销申请人
     */
     public void setCxsqr(String cxsqr) {
        this.cxsqr = cxsqr;
     }
    
    /**
     * 获取撤销申请人警号
     * 
     * @return 撤销申请人警号
     */
     public String getCxsqrjh() {
        return this.cxsqrjh;
     }
     
    /**
     * 设置撤销申请人警号
     * 
     * @param cxsqrjh
     *          撤销申请人警号
     */
     public void setCxsqrjh(String cxsqrjh) {
        this.cxsqrjh = cxsqrjh;
     }
    
    /**
     * 获取撤销申请单位
     * 
     * @return 撤销申请单位
     */
     public String getCxsqdw() {
        return this.cxsqdw;
     }
     
    /**
     * 设置撤销申请单位
     * 
     * @param cxsqdw
     *          撤销申请单位
     */
     public void setCxsqdw(String cxsqdw) {
        this.cxsqdw = cxsqdw;
     }
    
    /**
     * 获取撤控申请单位名称
     * 
     * @return 撤控申请单位名称
     */
     public String getCxsqdwmc() {
        return this.cxsqdwmc;
     }
     
    /**
     * 设置撤控申请单位名称
     * 
     * @param cxsqdwmc
     *          撤控申请单位名称
     */
     public void setCxsqdwmc(String cxsqdwmc) {
        this.cxsqdwmc = cxsqdwmc;
     }
    
    /**
     * 获取撤销申请时间
     * 
     * @return 撤销申请时间
     */
     public Date getCxsqsj() {
        return this.cxsqsj;
     }
     
    /**
     * 设置撤销申请时间
     * 
     * @param cxsqsj
     *          撤销申请时间
     */
     public void setCxsqsj(Date cxsqsj) {
        this.cxsqsj = cxsqsj;
     }
    
    /**
     * 获取撤控原因代码（01；02；03；04；05；99）
     * 
     * @return 撤控原因代码（01；02；03；04；05；99）
     */
     public String getCkyydm() {
        return this.ckyydm;
     }
     
    /**
     * 设置撤控原因代码（01；02；03；04；05；99）
     * 
     * @param ckyydm
     *          撤控原因代码（01；02；03；04；05；99）
     */
     public void setCkyydm(String ckyydm) {
        this.ckyydm = ckyydm;
     }
    
    /**
     * 获取撤控原因描述
     * 
     * @return 撤控原因描述
     */
     public String getCkyyms() {
        return this.ckyyms;
     }
     
    /**
     * 设置撤控原因描述
     * 
     * @param ckyyms
     *          撤控原因描述
     */
     public void setCkyyms(String ckyyms) {
        this.ckyyms = ckyyms;
     }
    
    /**
     * 获取业务状态
     * 
     * @return 业务状态
     */
     public String getYwzt() {
        return this.ywzt;
     }
     
    /**
     * 设置业务状态
     * 
     * @param ywzt
     *          业务状态
     */
     public void setYwzt(String ywzt) {
        this.ywzt = ywzt;
     }
    
    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
     public Date getGxsj() {
        return this.gxsj;
     }
     
    /**
     * 设置更新时间
     * 
     * @param gxsj
     *          更新时间
     */
     public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
     }

	public Task getTask() {
		return Task;
	}

	public void setTask(Task task) {
		Task = task;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}


	public Dispatched getDispatched() {
		return dispatched;
	}

	public void setDispatched(Dispatched dispatched) {
		this.dispatched = dispatched;
	}

	public String getCxsqrdh() {
		return cxsqrdh;
	}

	public void setCxsqrdh(String cxsqrdh) {
		this.cxsqrdh = cxsqrdh;
	}

	/** 直接撤控:1是 0否*/
	public String getZjck() {
		return zjck;
	}

	/** 直接撤控:1是 0否*/
	public void setZjck(String zjck) {
		this.zjck = zjck;
	}

	/**
	 * @return the tzdw
	 */
	public String getTzdw() {
		return tzdw;
	}

	/**
	 * @param tzdw the tzdw to set
	 */
	public void setTzdw(String tzdw) {
		this.tzdw = tzdw;
	}

	/**
	 * @return the tznr
	 */
	public String getTznr() {
		return tznr;
	}

	/**
	 * @param tznr the tznr to set
	 */
	public void setTznr(String tznr) {
		this.tznr = tznr;
	}
    
	@Column(name="LSKHBM", length=20)
	public String getLskhbm() {
		return lskhbm;
	}
	public void setLskhbm(String lskhbm) {
		this.lskhbm = lskhbm;
	}

	@Column(name="LSKHBMMC", length=80)
	public String getLskhbmmc() {
		return lskhbmmc;
	}
	public void setLskhbmmc(String lskhbmmc) {
		this.lskhbmmc = lskhbmmc;
	}

	@Column(name="BY1", length=10)
	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}
}