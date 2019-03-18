package com.dyst.sjjk.entities;

public class YwtjEnitity {
	private int sn;
	private String bmbh;
	private String bmmc;
	
	private Integer bkspjss;//布控审批及时数
	private Integer bkspcss;//布控审批超时数
	private Integer bkwsps;//布控未审批数
	private Integer bkspzs;//布控审批总数
	private String bkspjsl;//布控审批及时率
	
	private Integer ckspjss;//撤控审批及时数
	private Integer ckspcss;//撤控审批超时数
	private Integer ckwsps;//撤控未审批数
	private Integer ckspzs;//撤控审批总数
	private String ckspjsl;//撤控审批及时率
	
	private Integer bkqsjss;//布控签收及时数
	private Integer bkqscss;//布控签收超时数
	private Integer bkwqss;//布控未签收数
	private Integer bkqszs;//布控签收总数
	private String bkqsjsl;//布控签收及时率
	
	private Integer ckqsjss;//撤控签收及时数
	private Integer ckqscss;//撤控签收超时数
	private Integer ckwqss;//撤控未签收数
	private Integer ckqszs;//撤控签收总数
	private String ckqsjsl;//撤控签收及时率
	
	private Integer yjqsjss;//预警签收及时数
	private Integer yjqscss;//预警签收超时数
	private Integer yjwqss;//预警未签收数
	private Integer yjqszs;//预警签收总数
	private String yjqsjsl;//预警签收及时率
	
	private Integer zlxdjss;//指令下达及时数
	private Integer zlxdcss;//指令下达超时数
	private Integer zlxdzs;//指令下达总数
	private String zlxdjsl;//指令下达及时率
	
	private Integer zlqsjss;//指令签收及时数
	private Integer zlqscss;//指令签收超时数
	private Integer zlwqss;//指令未签收数
	private Integer zlqszs;//指令签收总数
	private String zlqsjsl;//指令签收及时率
	
	private Integer zlfkjss;//指令反馈及时数
	private Integer zlfkcss;//指令反馈超时数
	private Integer zlwfks;//指令未反馈数
	private Integer zlfkzs;//指令反馈总数
	private String zlfkjsl;//指令反馈及时率
	 
	public YwtjEnitity() {
		super();
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getBmbh() {
		return bmbh;
	}

	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public Integer getBkspjss() {
		return bkspjss;
	}

	public void setBkspjss(Integer bkspjss) {
		this.bkspjss = bkspjss;
	}

	public Integer getBkspcss() {
		return bkspcss;
	}

	public void setBkspcss(Integer bkspcss) {
		this.bkspcss = bkspcss;
	}

	public Integer getBkspzs() {
		return bkspzs;
	}

	public void setBkspzs(Integer bkspzs) {
		this.bkspzs = bkspzs;
	}

	public String getBkspjsl() {
		return bkspjsl;
	}

	public void setBkspjsl(String bkspjsl) {
		this.bkspjsl = bkspjsl;
	}

	public Integer getCkspjss() {
		return ckspjss;
	}

	public void setCkspjss(Integer ckspjss) {
		this.ckspjss = ckspjss;
	}

	public Integer getCkspcss() {
		return ckspcss;
	}

	public void setCkspcss(Integer ckspcss) {
		this.ckspcss = ckspcss;
	}

	public Integer getCkspzs() {
		return ckspzs;
	}

	public void setCkspzs(Integer ckspzs) {
		this.ckspzs = ckspzs;
	}

	public String getCkspjsl() {
		return ckspjsl;
	}

	public void setCkspjsl(String ckspjsl) {
		this.ckspjsl = ckspjsl;
	}

	public Integer getBkqsjss() {
		return bkqsjss;
	}

	public void setBkqsjss(Integer bkqsjss) {
		this.bkqsjss = bkqsjss;
	}

	public Integer getBkqscss() {
		return bkqscss;
	}

	public void setBkqscss(Integer bkqscss) {
		this.bkqscss = bkqscss;
	}

	public Integer getBkqszs() {
		return bkqszs;
	}

	public void setBkqszs(Integer bkqszs) {
		this.bkqszs = bkqszs;
	}

	public String getBkqsjsl() {
		return bkqsjsl;
	}

	public void setBkqsjsl(String bkqsjsl) {
		this.bkqsjsl = bkqsjsl;
	}

	public Integer getCkqsjss() {
		return ckqsjss;
	}

	public void setCkqsjss(Integer ckqsjss) {
		this.ckqsjss = ckqsjss;
	}

	public Integer getCkqscss() {
		return ckqscss;
	}

	public void setCkqscss(Integer ckqscss) {
		this.ckqscss = ckqscss;
	}

	public Integer getCkqszs() {
		return ckqszs;
	}

	public void setCkqszs(Integer ckqszs) {
		this.ckqszs = ckqszs;
	}

	public String getCkqsjsl() {
		return ckqsjsl;
	}

	public void setCkqsjsl(String ckqsjsl) {
		this.ckqsjsl = ckqsjsl;
	}

	public Integer getYjqsjss() {
		return yjqsjss;
	}

	public void setYjqsjss(Integer yjqsjss) {
		this.yjqsjss = yjqsjss;
	}

	public Integer getYjqscss() {
		return yjqscss;
	}

	public void setYjqscss(Integer yjqscss) {
		this.yjqscss = yjqscss;
	}

	public Integer getYjqszs() {
		return yjqszs;
	}

	public void setYjqszs(Integer yjqszs) {
		this.yjqszs = yjqszs;
	}

	public String getYjqsjsl() {
		return yjqsjsl;
	}

	public void setYjqsjsl(String yjqsjsl) {
		this.yjqsjsl = yjqsjsl;
	}

	public Integer getZlxdjss() {
		return zlxdjss;
	}

	public void setZlxdjss(Integer zlxdjss) {
		this.zlxdjss = zlxdjss;
	}

	public Integer getZlxdcss() {
		return zlxdcss;
	}

	public void setZlxdcss(Integer zlxdcss) {
		this.zlxdcss = zlxdcss;
	}

	public Integer getZlxdzs() {
		return zlxdzs;
	}

	public void setZlxdzs(Integer zlxdzs) {
		this.zlxdzs = zlxdzs;
	}

	public String getZlxdjsl() {
		return zlxdjsl;
	}

	public void setZlxdjsl(String zlxdjsl) {
		this.zlxdjsl = zlxdjsl;
	}

	public Integer getZlqsjss() {
		return zlqsjss;
	}

	public void setZlqsjss(Integer zlqsjss) {
		this.zlqsjss = zlqsjss;
	}

	public Integer getZlqscss() {
		return zlqscss;
	}

	public void setZlqscss(Integer zlqscss) {
		this.zlqscss = zlqscss;
	}

	public Integer getZlqszs() {
		return zlqszs;
	}

	public void setZlqszs(Integer zlqszs) {
		this.zlqszs = zlqszs;
	}

	public String getZlqsjsl() {
		return zlqsjsl;
	}

	public void setZlqsjsl(String zlqsjsl) {
		this.zlqsjsl = zlqsjsl;
	}

	public Integer getZlfkjss() {
		return zlfkjss;
	}

	public void setZlfkjss(Integer zlfkjss) {
		this.zlfkjss = zlfkjss;
	}

	public Integer getZlfkcss() {
		return zlfkcss;
	}

	public void setZlfkcss(Integer zlfkcss) {
		this.zlfkcss = zlfkcss;
	}

	public Integer getZlfkzs() {
		return zlfkzs;
	}

	public void setZlfkzs(Integer zlfkzs) {
		this.zlfkzs = zlfkzs;
	}

	public String getZlfkjsl() {
		return zlfkjsl;
	}

	public void setZlfkjsl(String zlfkjsl) {
		this.zlfkjsl = zlfkjsl;
	}

	public Integer getBkwsps() {
		return bkwsps;
	}

	public void setBkwsps(Integer bkwsps) {
		this.bkwsps = bkwsps;
	}

	public Integer getCkwsps() {
		return ckwsps;
	}

	public void setCkwsps(Integer ckwsps) {
		this.ckwsps = ckwsps;
	}

	public Integer getBkwqss() {
		return bkwqss;
	}

	public void setBkwqss(Integer bkwqss) {
		this.bkwqss = bkwqss;
	}

	public Integer getCkwqss() {
		return ckwqss;
	}

	public void setCkwqss(Integer ckwqss) {
		this.ckwqss = ckwqss;
	}

	public Integer getYjwqss() {
		return yjwqss;
	}

	public void setYjwqss(Integer yjwqss) {
		this.yjwqss = yjwqss;
	}

	public Integer getZlwqss() {
		return zlwqss;
	}

	public void setZlwqss(Integer zlwqss) {
		this.zlwqss = zlwqss;
	}

	public Integer getZlwfks() {
		return zlwfks;
	}

	public void setZlwfks(Integer zlwfks) {
		this.zlwfks = zlwfks;
	}
}