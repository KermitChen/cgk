/*订阅管理模块 数据库表 记录文件*/
/*=====================DYXX======订阅条件记录表=====================*/
create table DYXX
(
  id     int not null,
  lxid   int,
  jyjh   VARCHAR(30) not null,
  hphm   VARCHAR(400),
  cplx   VARCHAR(2),
  jcdid  VARCHAR(4000),
  lrsj   datetime not null,
  qssj   datetime not null,
  jzsj   datetime not null,
  dylx   VARCHAR(2) not null,
  dxjshm VARCHAR(400),
  tzfs   VARCHAR(10) not null,
  swid   int not null,
  dylxzl VARCHAR(4000),
  jlzt   VARCHAR(2),
  ywzt   VARCHAR(2),
  jyxm   VARCHAR(80),
  bzsm   VARCHAR(1024),
  cxyy   VARCHAR(500)
)

/*===============XYXXSP==============订阅信息审批表=============================*/
create table DYXXSP
(
  id      int not null,
  swid    int,
  sprjh   VARCHAR(10),
  sprxm   VARCHAR(20),
  sprdw   VARCHAR(20),
  sprdwmc VARCHAR(80),
  spsj    datetime,
  spjg    VARCHAR(2),
  spms    VARCHAR(1024),
  bzw     VARCHAR(2),
  xdid    int,
  bzsm    VARCHAR(1024),
  splx    CHAR(2),
  cxid    int,
  sqsj    datetime
)