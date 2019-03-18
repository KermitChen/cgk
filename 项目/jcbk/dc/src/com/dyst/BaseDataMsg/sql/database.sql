drop table if exists DICTIONARY;

/*==============================================================*/
/* Table: DICTIONARY     :基础数据字典表
		1.PK_ID  	     :序列号
		2.DELETE_FLAG    :是否已经删除标志  1表示已经删除 ，0 或者null表示 未删除 
		3.TYPE_CODE      :类别代码     
		4.TYPE_SERIAL_NO :类别序号
		5.TYPE_DESC      :类别描述（车辆类型，例如黄牌、蓝牌、黑牌）
		6.MEMO			 :备注
		7.ADD_OPERATOR	 :操作员
		8.ADD_TIME		 :添加时间 
		9.EDIT_FLAG 	 :是否可以编辑 0不可编辑 1可以编辑                    */
/*==============================================================*/
create table DICTIONARY
(
   DICTIONARYID         int not null ,
   DELETE_FLAG           varchar(1),
   TYPE_CODE            varchar(110),
   TYPE_SERIAL_NO       varchar(120),
   TYPE_DESC            varchar(200),
   MEMO  				varchar(200),
   ADD_OPERATOR         varchar(20),
   ADD_TIME             datetime,
   EDIT_FLAG            varchar(1),
   
   primary key (DICTIONARYID)
);


drop table if exists BASE_DATAMSG_ROAD;

/*==============================================================*/
/* Table: BASE_DATAMSG_ROAD     :基础数据道路信息表
		1.PK_ID  	      :道路表主键
		2.DELETE_FLAG     :是否已经删除标志  1表示已经删除 ，0 或者null表示 未删除 
		3.ROAD_NO    	  :道路唯一编码 
		4.ROAD_NAME       :道路名称     
		5.CITY_ID 		  :城区ID
		6.ROADWAY_NUM     :车道数量
		7.CONTROL_BEHAVIOR:管制行为
		8.ROAD_PROPERTY	  :道路性质(属性)
		9.ROAD_GRADE	  :道路等级 
		10.NEAR_ROAD1 	  :相邻平行道路ID 1                   
		11.NEAR_ROAD2	  :相邻平行道路ID 2                   */
/*==============================================================*/
create table BASE_DATAMSG_ROAD
(
   PK_ID              int not null ,
   DELETE_FLAG           varchar(1),
   ROAD_NO              varchar(10),
   ROAD_NAME            varchar(50),
   CITY_ID       		int(10),
   ROADWAY_NUM          int(9),
   CONTROL_BEHAVIOR 	varchar(10),
   ROAD_PROPERTY        varchar(10),
   ROAD_GRADE            varchar(10),
   NEAR_ROAD1            varchar(10),
   NEAR_ROAD2            varchar(10), 
   primary key (PK_ID)
);


drop table if exists area;

/*==============================================================*/
/* Table: BASE_DATAMSG_AREA     :基础数据道路信息表
		1.PK_ID  	      :道路表主键
		2.DELETE_FLAG     :删除标志
		3.AREA_NO         :是否已经删除标志  1表示已经删除 ，0 或者null表示 未删除 
		4.AREA_NAME    	  :道路唯一编码 
		5.SUPER_AREA_NO    :道路名称     
											                    */
											                  
/*==============================================================*/
create table area
(
   ID              int not null ,
   DELETE_FLAG           varchar(1),
   AREANO              varchar(10),
   AREANAME            varchar(30),
   SUPAREA       	varchar(10),
   SPECIALAREAFLAG_NAME varchar(10),
   SPECIALAREAFLAG_DM varchar(10),
   primary key (ID)
);


drop table if exists JCD;

/*==============================================================*/
/* Table: JCD     :监测点表
		1.ID  	String 8    :主键
		2JCDMC  String 100  :监测点名称
		2.JCDX  Float(6,2)   :监测点坐标X
		2.JCDY  Float(6,2)   :监测点坐标Y
		3.DLID  String 6  :隶属道路ID
		XSFX	String10   :行驶方向
		3.JCDXZ String10      :监测点性质 
		4.SBS   tinyint  	   :设备数   
		5.JCCDS  tinyint  :监测车道数
		6.XYJCDS tinyint  :下游监测点数
  		7.HBCDJ  String 1   :黄标车最低等级
		8.WDCXXF  String 1       :外地车限行否
		9.JSJTCSF  String       :计算交通参数否
		10.DLZSXS  Float(2,1)   :监测点当量折算系数
		11.CDZSXS Float(2,1)  :监测点车道折算系数
		12.CQID  String2  :隶属城区  ID
		13SDYZ1  tinyint  :速度阈值1   
		13SDYZ2  tinyint  :速度阈值2   
		13SDYZ3  tinyint  :速度阈值3
		14 PY    String50 :拼音
		15JD     int      :经度
		15WD     int      :纬度
    	16CADX   int       CADX
    	17CADY   int       CADY
		18JCDSX  String20  :监测点属性
		19 JSDSHXXF String1 :计算单双号限行否
		20 WLDDBM   String12 违例地点编码
		21JCDLSXM   String2 监测点隶属项目
		22JCDDDSX   String2 监测点地点属性
		23LSFZX     String1 隶属分中心 默认是 't'
		24QYBZ      String1 默认是 0
		25JDSJ    date    进度时间
		26JCDDZ   String100 监测点地址
		27 BZ   Str40     备注
		28 JRBZ Str1   默认是0
		29 JLSJ date 建立时间
		30 JLR  Str10 建立人
		31 YYBZ Str10 默认是0
		32 XKZH Str6  许可证号
		33 LXDH Str20 联系电话
		34 KL   Str10 口令
		35 ZRR  Str15 责任人
		36 ZT  Str2  验收状态 默认是01
		37 ZZJGDM Str10 组织机构代码
		38 DWMC  Str100 单位名称
		39 GSYYZZ Str22 工商营业执照
		40 FWM Str32 防伪码
		41 FWMTP blob 防伪码2位图片
		42 XQID Str5 辖区ID
		43 PCSID Str12 派出所ID
											                    */
											                  
/*==============================================================*/
create table JCD
(
   ID            varchar(8) null ,
   JCDMC           varchar(100),
   JCDX          float(6,2),
   JCDY          float(6,2),
   DLID          varchar(6),
   XSFX       		varchar(10),
   JCDXZ			varchar(10),
   SBS				tinyint,
   JCCDS			tinyint,
   XYJCDS			tinyint,
   HBCDJ   			varchar(1),
   WDCXXF   			varchar(1),
   JSJTCSF   			varchar(1),
   DLZSXS				float(2,1),
   CDZSXS				float(2,1),
   CQID					varchar(2),
   SDYZ1			tinyint,
   SDYZ2			tinyint,
   SDYZ3			tinyint,
   PY				varchar(50),
   JD               double,
   WD               double,
   CADX				int,
   CADY				int,
   JCDSX			varchar(20),
   JSDSHXXF			varchar(1),
   WLDDBM			varchar(12),
   JCDLSXM			varchar(2),
   JCDDDSX			varchar(2),
   LSFZX			varchar(1),
   QYBZ				varchar(1),
   JDSJ				datetime,
   JCDDZ			varchar(100),
   BZ				varchar(40),
   JRBZ				varchar(1),
   JLSJ				datetime,
   JLR				varchar(10),
   YYBZ				varchar(10),
   XKZH				varchar(6),
   LXDH				varchar(20),
   KL				varchar(10),
   ZRR				varchar(15),
   ZT				varchar(2),
   ZZJGDM			varchar(10),
   DWMC				varchar(100),
   GSYYZZ			varchar(22),
   FWM				varchar(32),
   FWMTP			blob,
   XQID				varchar(5),
   PCSID			varchar(12),
   
   primary key (ID)
);


drop table if exists HMD;

/*==============================================================*/
/* Table: HMD     :红名单表
		1.ID  	int 5       :主键
		2.CPH   String 14   :车牌号
		3.CPLX  String 200  :车牌类型
		3.KSSJ  Date       :开始时间 
		4.JSSJ  Date   	   :结束时间   
		5.SQDW  String 50  :申请单位
		6.SPZBH String 20  :审批者编号
  		7.ZT    String 2   :状态
		8.RKSJ  Date       :入库时间
		9.XGSJ  Date       :修改时间
		10.SYZ  String20   :使用者
		11.SQRBH String20  :申请人编号
		12.LRYY  String 200:列入原因     
											                    */
											                  
/*==============================================================*/
create table HMD
(
   ID              int not null ,
   CPH           varchar(14),
   CPLX          varchar(200),
   KSSJ              datetime,
   JSSJ            datetime,
   SQDW       		varchar(50),
   SPZBH            varchar(20),
   ZT            varchar(2),
   RKSJ            datetime,
   XGSJ            datetime,
   SYZ            varchar(20),
   SQRBH            varchar(20),
   LRYY            varchar(200),
   primary key (ID)
);


drop table if exists DEPARTMENT;

/*==============================================================*/
/* Table: HMD     :红名单表
		1.PK_ID  	int 5       :主键
		2.DEPT_NO   String 12   :部门编号
		3.DEPT_NAME  String 80  :部门名称
		4.DEPT_PARENT  String 12   :上级部门ID 
		5.BUILD_TIME    date   	   :建立时间   
		6.UPDATE_TIME  date  :更新时间
		7.BUILD_NAME String 12  :创建姓名
  		8.BUILD_PNO    String 10   :创建警号
		9.DEPT_TELEPHONE  String20  :联系电话
		10.REMARK  String 500      :备注
											                  
/*==============================================================*/
create table DEPARTMENT
(
   PK_ID              int not null ,
   DEPT_NO           varchar(12),
   DEPT_NAME          varchar(80),
   DEPT_PARENT        varchar(12),
   BUILD_TIME           datetime,
   UPDATE_TIME       	datetime,
   BUILD_NAME            varchar(12),
   BUILD_PNO            varchar(10),
   DEPT_TELEPHONE       varchar(20),
   REMARK            varchar(500),
   primary key (PK_ID)
);

/* 新建预案管理表
	ID ：主键
	Yamc :预案名称
	
*/
create table YACS(
	ID     int not null,
	Yamc   varchar(100),
	Yams   varchar(1024),
	Yazl   varchar(3),
	Yadj   varchar(10),
	By_1   varchar(20),
	By_2   varchar(20),
	By_3   varchar(20),
	primary key (ID)
);


