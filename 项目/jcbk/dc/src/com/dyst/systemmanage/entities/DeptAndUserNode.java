package com.dyst.systemmanage.entities;

public class DeptAndUserNode implements java.io.Serializable {
	
	private static final long serialVersionUID = 100000000004L;
	
	private Integer id;
	private String nodeNo;
	private String nodeName;
	private String nodeParent;
	private String systemNo;
	private String flag;
	private String icon;
	private boolean isParent;
	private String infoSource;//信息来源

	// Constructors

	/** default constructor */
	public DeptAndUserNode() {
		
	}

	/** full constructor */
	public DeptAndUserNode(Integer id, String nodeNo, String nodeName, String nodeParent, String systemNo, String flag, String icon) {
		this.id = id;
		this.nodeNo = nodeNo;
		this.nodeName = nodeName;
		this.nodeParent = nodeParent;
		this.systemNo = systemNo;
		this.flag = flag;
		this.icon = icon;
	}
	
	/** full constructor */
	public DeptAndUserNode(Integer id, String nodeNo, String nodeName, String nodeParent, String systemNo, String flag, String icon, boolean isParent) {
		this.id = id;
		this.nodeNo = nodeNo;
		this.nodeName = nodeName;
		this.nodeParent = nodeParent;
		this.systemNo = systemNo;
		this.flag = flag;
		this.icon = icon;
		this.isParent = isParent;
	}
	
	/** full constructor */
	public DeptAndUserNode(Integer id, String nodeNo, String nodeName, String nodeParent, String systemNo, String icon, boolean isParent, String infoSource) {
		this.id = id;
		this.nodeNo = nodeNo;
		this.nodeName = nodeName;
		this.nodeParent = nodeParent;
		this.systemNo = systemNo;
		this.icon = icon;
		this.isParent = isParent;
		this.infoSource = infoSource;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeParent() {
		return nodeParent;
	}

	public void setNodeParent(String nodeParent) {
		this.nodeParent = nodeParent;
	}
	
	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}
}