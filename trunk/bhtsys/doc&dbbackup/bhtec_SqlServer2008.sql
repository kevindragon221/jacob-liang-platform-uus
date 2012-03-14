/* --外键
ALTER TABLE dbo.uum_user_agent
	DROP CONSTRAINT FK_uum_user_agent_uum_user
GO	
ALTER TABLE dbo.uum_privilege
	DROP CONSTRAINT FK_uum_privilege_uum_user
GO	
ALTER TABLE dbo.uum_user
	DROP CONSTRAINT FK_uum_user_Uum_Organ
GO	
ALTER TABLE dbo.uum_privilege
	DROP CONSTRAINT FK_uum_privilege_Syspl_Mod_Opt_Ref
GO	
ALTER TABLE dbo.uum_role_user_ref
	DROP CONSTRAINT FK_uum_role_user_ref_Uum_Org_Role_Ref
GO	
ALTER TABLE dbo.Uum_Role_User_Ref
	DROP CONSTRAINT FK_uum_Role_User_Ref_uum_user
GO	
ALTER TABLE dbo.uum_org_role_ref
	DROP CONSTRAINT FK_uum_org_role_ref_Uum_Role
GO
ALTER TABLE dbo.uum_org_role_ref
	DROP CONSTRAINT FK_uum_org_role_ref_Uum_Organ
GO
ALTER TABLE dbo.uum_group_member
	DROP CONSTRAINT FK_uum_group_member_Uum_Group
GO
ALTER TABLE dbo.Syspl_Mod_Opt_Ref
	DROP CONSTRAINT FK_Syspl_Mod_Opt_Ref_syspl_operate
GO
ALTER TABLE dbo.syspl_mod_opt_ref
	DROP CONSTRAINT FK_syspl_mod_opt_ref_Syspl_Module_Memu
GO
ALTER TABLE dbo.syspl_dic_small_type
	DROP CONSTRAINT FK_syspl_dic_small_type_Syspl_Dic_Big_Type
GO */

---表

DROP TABLE uum_group;

CREATE TABLE uum_group (
  group_id INT NOT NULL,
  group_type varchar(15) NOT NULL,
  group_name varchar(50) NOT NULL,
  memo varchar(50) NOT NULL,
  cstatus varchar(200) DEFAULT('') NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_uum_group] PRIMARY KEY  CLUSTERED 
	(
		group_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_group_member;

CREATE TABLE uum_group_member (
  group_member_id INT NOT NULL,
  group_member_type varchar(15) NOT NULL,
  member_resource_id INT NOT NULL,
  group_id INT NULL,
  CONSTRAINT [PK_uum_group_member] PRIMARY KEY  CLUSTERED 
	(
		group_member_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE uum_organ;

CREATE TABLE uum_organ (
  org_id INT NOT NULL,
  org_simple_name varchar(20) DEFAULT('') NULL,
  org_full_name varchar(60) DEFAULT('') NULL,
  org_code varchar(10) DEFAULT('') NULL,
  org_address1 varchar(100) DEFAULT('') NULL,
  org_address2 varchar(100) DEFAULT('') NULL,
  org_tel1 varchar(25) DEFAULT('') NULL,
  org_tel2 varchar(25) DEFAULT('') NULL,
  org_begin_date DATETIME NULL,
  org_type varchar(20) DEFAULT('') NULL,
  org_fax varchar(20) DEFAULT('') NULL,
  org_postal varchar(16) NULL,
  org_legal varchar(20) DEFAULT('') NULL,
  org_tax_no varchar(25) NULL,
  org_reg_no varchar(25) DEFAULT('') NULL,
  org_belong_dist INT NULL,
  org_parent INT NOT NULL,
  cstatus varchar(300) DEFAULT('') NULL,
  memo varchar(100) NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_uum_organ] PRIMARY KEY  CLUSTERED 
	(
		org_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_org_role_ref;

CREATE TABLE uum_org_role_ref (
  org_role_id INT NOT NULL,
  org_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT [PK_uum_org_role_ref] PRIMARY KEY  CLUSTERED 
	(
		org_role_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

GO

DROP TABLE uum_privilege;

CREATE TABLE uum_privilege (
  PRIVILEGE_ID INT NOT NULL ,
  RESOURCE_ID VARCHAR(20) DEFAULT('') NULL,
  OWNER_ID INT NULL,
  OWNER_TYPE varchar(10) DEFAULT('') NULL,
  PRIVILEGE_SCOPE varchar(10) DEFAULT('') NULL,
  PRIVILEGE_TYPE varchar(10) DEFAULT('') NULL,
  CONSTRAINT [PK_uum_privilege] PRIMARY KEY  CLUSTERED 
	(
		privilege_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_role;

CREATE TABLE uum_role (
  role_id INT NOT NULL,
  role_name varchar(30) NOT NULL,
  role_level INT NULL,
  cstatus varchar(50) NOT NULL,
  memo varchar(200) DEFAULT('') NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_uum_role] PRIMARY KEY  CLUSTERED 
	(
		role_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_role_user_ref;

CREATE TABLE uum_role_user_ref (
  org_role_user_id INT NOT NULL,
  cuser_id INT NOT NULL,
  org_role_id INT NOT NULL,
  is_default varchar(2) NOT NULL,
  CONSTRAINT [PK_uum_role_user_ref] PRIMARY KEY  CLUSTERED 
	(
		org_role_user_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE uum_user;

CREATE TABLE uum_user (
  cuser_id INT NOT NULL,
  user_code varchar(20) DEFAULT('') NULL,
  cuser_name varchar(20) DEFAULT('') NULL,
  user_password varchar(20) DEFAULT('') NULL,
  user_gender varchar(20) DEFAULT('') NULL,
  user_position varchar(30) DEFAULT('') NULL,
  user_photo_url varchar(200) DEFAULT('') NULL,
  user_qq varchar(20) DEFAULT('') NULL,
  user_msn varchar(20) DEFAULT('') NULL,
  user_mobile varchar(20) DEFAULT('') NULL,
  user_mobile2 varchar(20) DEFAULT('') NULL,
  user_office_tel varchar(20) DEFAULT('')NULL,
  user_address varchar(50) DEFAULT('') NULL,
  user_family_tel varchar(20) DEFAULT('') NULL,
  user_email varchar(30) DEFAULT('') NULL,
  user_avidate DATETIME NULL,
  user_is_agent varchar(2) NULL,
  user_belongto_org INT DEFAULT(0) NULL,
  memo varchar(500) DEFAULT('') NULL,
  cstatus varchar(50) NOT NULL,
  creator varchar(50) DEFAULT('') NULL,
  create_date DATETIME NULL,
  user_name_py varchar(20) NULL,
  CONSTRAINT [PK_uum_user] PRIMARY KEY  CLUSTERED 
	(
		cuser_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_user_agent;

CREATE TABLE uum_user_agent (
  agent_id INT NOT NULL,
  cuser_id INT NOT NULL,
  agent_user_id INT NOT NULL,
  org_role_id INT NULL,
  begin_date DATETIME NULL,
  end_date DATETIME NULL,
  reason VARCHAR(200),
  CONSTRAINT [PK_uum_user_agent] PRIMARY KEY  CLUSTERED 
	(
		agent_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_user_commfun;

CREATE TABLE uum_user_commfun (
  cuser_id INT NOT NULL,
  module_id INT NOT NULL,
) ON [PRIMARY]
GO


DROP TABLE uum_user_page_layout;

CREATE TABLE uum_user_page_layout (
  cuser_id INT NOT NULL,
  header VARCHAR(15) NOT NULL,
  navigate VARCHAR(15) NOT NULL,
  outlookbar VARCHAR(15) NOT NULL,
  mainpage VARCHAR(15) NOT NULL,
  statebar VARCHAR(15) NOT NULL,
 CONSTRAINT [PK_uum_user_page_layout] PRIMARY KEY  CLUSTERED 
	(
		cuser_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE portal;

CREATE TABLE portal (
  portal_id INT NOT NULL,
  portal_userid INT NOT NULL,
  portlet1 varchar(20) NOT NULL,
  portlet2 varchar(20) NOT NULL,
  portlet3 varchar(20) DEFAULT('') NULL,
  portlet4 varchar(20) DEFAULT('') NULL,
  portlet5 varchar(20) DEFAULT('') NULL,
  portlet6 varchar(20) NOT NULL,
  portlet7 varchar(20) DEFAULT('') NULL,
  portlet8 varchar(20) DEFAULT('') NULL,
  portlet9 varchar(20) DEFAULT('') NULL,
  CONSTRAINT [PK_portal] PRIMARY KEY  CLUSTERED 
	(
		portal_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE  Syspl_Accessory

CREATE TABLE Syspl_Accessory (
  accessory_id int NOT NULL,
  info_id INT NOT NULL,
  accessory_name varchar(100) DEFAULT('') NULL,
 CONSTRAINT [PK_Syspl_Accessory] PRIMARY KEY  CLUSTERED 
	(
		accessory_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE Syspl_Affiche

CREATE TABLE Syspl_Affiche (
  affiche_id int NOT NULL,
  affiche_title varchar(100) NOT NULL,
  affiche_invalidate DATETIME NOT NULL,
  affiche_pulish VARCHAR(100) NOT NULL,
  affiche_content VARCHAR(100) NOT NULL,
  CONSTRAINT [PK_Syspl_Affiche] PRIMARY KEY  CLUSTERED 
	(
		affiche_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_code;

CREATE TABLE syspl_code (
  code_id int NOT NULL,
  code_eng_name varchar(20) NOT NULL,
  code_name VARCHAR(20),
  module_name varchar(20) NOT NULL,
  delimite varchar(2) NOT NULL,
  part_num int NOT NULL,
  part1 varchar(20) NOT NULL,
  part1_con VARCHAR(20),
  part2 VARCHAR(20) NOT NULL,
  part2_con varchar(20) NOT NULL,
  part3 VARCHAR(20) NOT NULL,
  part3_con VARCHAR(20) NOT NULL,
  part4 varchar(20) NOT NULL,
  part4_con VARCHAR(20),
  code_effect varchar(50) NOT NULL,
  memo VARCHAR(200) NOT NULL,
  cstatus VARCHAR(200) NOT NULL,
  creator VARCHAR(200) NOT NULL,
  create_date DATETIME NOT NULL,
  CONSTRAINT [PK_syspl_code] PRIMARY KEY  CLUSTERED 
	(
		code_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_dic_big_type;

CREATE TABLE syspl_dic_big_type (
  big_type_id INT NOT NULL,
  big_type_name varchar(20) NOT NULL,
  big_type_code varchar(15) NOT NULL,
  memo varchar(50) NOT NULL,
  cstatus varchar(20) DEFAULT('') NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date DATETIME DEFAULT('') NULL,
  CONSTRAINT [PK_syspl_dic_big_type] PRIMARY KEY  CLUSTERED 
	(
		big_type_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_dic_small_type;

CREATE TABLE syspl_dic_small_type (
  small_type_id INT NOT NULL,
  big_type_id INT NOT NULL,
  small_type_name varchar(20) NOT NULL,
  small_type_code varchar(15) NOT NULL,
  CONSTRAINT [PK_syspl_dic_small_type] PRIMARY KEY  CLUSTERED 
	(
		small_type_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_district;

CREATE TABLE syspl_district (
  district_id INT NOT NULL,
  district_name varchar(20) DEFAULT('') NULL,
  district_code varchar(20) DEFAULT('') NULL,
  district_postal varchar(6) DEFAULT('') NULL,
  district_telcode varchar(10) DEFAULT('') NULL,
  district_level varchar(20) DEFAULT('') NULL,
  dis_parent_id INT DEFAULT(0) NULL,
  memo varchar(200) DEFAULT('')NULL,
  cstatus varchar(15) NOT NULL,
  creator varchar(20) DEFAULT('') NULL,
  create_date DATETIME NOT NULL,
  CONSTRAINT [PK_syspl_district] PRIMARY KEY  CLUSTERED 
	(
		district_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_mod_opt_ref;

CREATE TABLE syspl_mod_opt_ref (
  mod_opt_id INT NOT NULL,
  module_id  INT NOT NULL,
  operate_id INT NOT NULL,
  MOD_OPT_LINK varchar(20) DEFAULT NULL,
  CONSTRAINT [PK_syspl_mod_opt_ref] PRIMARY KEY  CLUSTERED 
	(
		mod_opt_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_module_code;

CREATE TABLE syspl_module_code (
  code_eng_name varchar(20) NOT NULL,
  code_content varchar(20) NOT NULL,
  CONSTRAINT [PK_syspl_module_code] PRIMARY KEY  CLUSTERED 
	(
		code_eng_name
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_module_memu;

CREATE TABLE syspl_module_memu (
  module_id INT NOT NULL,
  mod_name varchar(30) NOT NULL,
  mod_en_id varchar(30) NOT NULL,
  mod_img_cls varchar(30) DEFAULT('') NULL,
  mod_level varchar(2) DEFAULT('') NULL,
  mod_order INT NULL,
  MOD_PAGE_TYPE VARCHAR(20) DEFAULT('模块页面类型js html jsp ') NULL,
  BELONG_TO_SYS VARCHAR(15) DEFAULT('所属子系统') NULL,
  mod_parent_id INT NOT NULL,
  cstatus varchar(35) NOT NULL,
  MEMO varchar(100) DEFAULT('') NULL,
  creator varchar(35) NOT NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_syspl_module_memu] PRIMARY KEY  CLUSTERED 
	(
		module_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_operate;

CREATE TABLE syspl_operate (
  operate_id INT NOT NULL,
  operate_name varchar(20) NOT NULL,
  opt_fun_link varchar(20) DEFAULT('') NULL,
  opt_img_link varchar(30) NOT NULL,
  opt_order INT NOT NULL,
  opt_group INT  NULL,
  memo varchar(300) DEFAULT('') NULL,
  cstatus varchar(20) DEFAULT('') NULL,
  creator varchar(15) NOT NULL,
  create_date varchar(50) NOT NULL,
  CONSTRAINT [PK_syspl_operate] PRIMARY KEY  CLUSTERED 
	(
		operate_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_page_dictionary;

CREATE TABLE syspl_page_dictionary (
  page_name varchar(20) NOT NULL,
  page_src_name varchar(20) NOT NULL,
  creator varchar(200) NOT NULL,
  create_date varchar(200) NOT NULL,
  CONSTRAINT [PK_syspl_page_dictionary] PRIMARY KEY  CLUSTERED 
	(
		page_name
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE syspl_page_func_area;

CREATE TABLE syspl_page_func_area (
  func_area_id INT NOT NULL,
  func_area_name varchar(25) NOT NULL,
  func_area_resource varchar(50) NOT NULL,
  cstatus varchar(50) NOT NULL,
  memo varchar(200) DEFAULT('') NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date varchar(200) DEFAULT('') NULL,
  CONSTRAINT [PK_syspl_page_func_area] PRIMARY KEY  CLUSTERED 
	(
		func_area_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_primarykey_seq;

CREATE TABLE syspl_primarykey_seq (
  pojo_name varchar(20) NOT NULL,
  primarykey_name varchar(20) NOT NULL,
  CONSTRAINT [PK_syspl_primarykey_seq] PRIMARY KEY  CLUSTERED 
	(
		pojo_name
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO


DROP TABLE syspl_scheduler_job;

CREATE TABLE syspl_scheduler_job (
  job_id INT NOT NULL,
  job_name varchar(30) NOT NULL,
  job_class_descript varchar(100) NOT NULL,
  trigger_type varchar(20) NOT NULL,
  time_express varchar(100) DEFAULT('') NULL,
  start_time varchar(29) DEFAULT('') NULL,
  end_time varchar(29) DEFAULT('') NULL,
  repeat_time INT NULL,
  split_time INT NULL,
  cstatus varchar(200) DEFAULT('') NULL,
  memo varchar(200) DEFAULT('') NULL,
  creator varchar(300) DEFAULT('') NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_syspl_scheduler_job] PRIMARY KEY  CLUSTERED 
	(
		job_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_sys_opt_log;

CREATE TABLE syspl_sys_opt_log (
  opt_id INT NOT NULL IDENTITY(1,1),
  opt_mod_name varchar(20) NOT NULL,
  opt_name varchar(10) NOT NULL,
  opt_content varchar(200) NOT NULL,
  opt_business_id varchar(20) DEFAULT('') NULL,
  opt_time DATETIME NULL,
  opt_pc_name varchar(25) DEFAULT('') NULL,
  opt_pc_ip varchar(25) DEFAULT('') NULL,
  opt_user_name varchar(20) DEFAULT('') NULL,
  opt_user_role varchar(20) DEFAULT('') NULL,
  opt_user_ogan varchar(20) DEFAULT('') NULL,
  CONSTRAINT [PK_syspl_sys_opt_log] PRIMARY KEY  CLUSTERED 
	(
		opt_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_sys_parameter;

CREATE TABLE syspl_sys_parameter (
  para_id INT NOT NULL,
  para_name varchar(20) NOT NULL,
  para_key_name varchar(20) NOT NULL,
  para_value varchar(20) NOT NULL,
  para_type varchar(20) DEFAULT('') NULL,
  memo varchar(300) DEFAULT('') NULL,
  cstatus varchar(200) DEFAULT('') NULL,
  creator varchar(200) DEFAULT('') NULL,
  create_date DATETIME NULL,
  CONSTRAINT [PK_syspl_sys_parameter] PRIMARY KEY  CLUSTERED 
	(
		para_id
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE uum_user_agent_privilege;

CREATE TABLE uum_user_agent_privilege (
  USER_AGENT_PRI_ID INT NOT NULL,
  AGENT_ID INT DEFAULT NULL,
  PRIVILEGE_ID varchar(20) DEFAULT NULL,
  PRIVILEGE_TYPE varchar(10) DEFAULT NULL,
  CONSTRAINT [PK_uum_user_agent_privilege] PRIMARY KEY  CLUSTERED 
	(
		USER_AGENT_PRI_ID
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE common_folders

CREATE TABLE common_folders (
  FOLDER_ID INT NOT NULL,
  FOLDER_NAME VARCHAR(50) DEFAULT('') NULL,
  UP_FOLDER INT NULL,
  CUSER_ID INT NULL,
  FOLDER_TYPE VARCHAR(20) DEFAULT NULL,
  CONSTRAINT [PK_common_folders] PRIMARY KEY  CLUSTERED 
	(
		FOLDER_ID
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO

DROP TABLE syspl_db_opt_log

CREATE TABLE syspl_db_opt_log (
  OPT_ID INT NOT NULL,
  OPT_MOD_NAME VARCHAR(20) DEFAULT('') NULL,
  OPT_NAME VARCHAR(10) DEFAULT('') NULL,
  OPT_TIME DATETIME NULL,
  OPT_PC_NAME VARCHAR(25) DEFAULT('') NULL,
  OPT_PC_IP VARCHAR(25) DEFAULT('') NULL,
  OPT_SQL VARCHAR(100) DEFAULT('') NULL,
  OPT_USER_NAME VARCHAR(20) DEFAULT('') NULL,
  CONSTRAINT [PK_syspl_db_opt_log] PRIMARY KEY  CLUSTERED 
	(
		OPT_ID
	)  ON [PRIMARY] 
) ON [PRIMARY]
GO



--外键
ALTER TABLE dbo.uum_user_agent ADD CONSTRAINT
	FK_uum_user_agent_uum_user FOREIGN KEY
	(
	cuser_id
	) REFERENCES dbo.uum_user
	(
	cuser_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.uum_privilege ADD CONSTRAINT
	FK_uum_privilege_uum_user FOREIGN KEY
	(
	owner_id
	) REFERENCES dbo.uum_user
	(
	cuser_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.uum_user ADD CONSTRAINT
	FK_uum_user_Uum_Organ FOREIGN KEY
	(
	user_belongto_org
	) REFERENCES dbo.Uum_Organ
	(
	org_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.uum_privilege ADD CONSTRAINT
	FK_uum_privilege_Syspl_Mod_Opt_Ref FOREIGN KEY
	(
	mod_opt_id
	) REFERENCES dbo.Syspl_Mod_Opt_Ref
	(
	mod_opt_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO	
ALTER TABLE dbo.Uum_Role_User_Ref ADD CONSTRAINT
	FK_uum_Role_User_Ref_uum_user FOREIGN KEY
	(
	cuser_id
	) REFERENCES dbo.uum_user
	(
	cuser_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.uum_role_user_ref ADD CONSTRAINT
	FK_uum_role_user_ref_Uum_Org_Role_Ref FOREIGN KEY
	(
	org_role_id
	) REFERENCES dbo.Uum_Org_Role_Ref
	(
	org_role_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
GO	
ALTER TABLE dbo.uum_org_role_ref ADD CONSTRAINT
	FK_uum_org_role_ref_Uum_Role FOREIGN KEY
	(
	role_id
	) REFERENCES dbo.Uum_Role
	(
	role_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO	
ALTER TABLE dbo.uum_org_role_ref ADD CONSTRAINT
	FK_uum_org_role_ref_Uum_Organ FOREIGN KEY
	(
	org_id
	) REFERENCES dbo.Uum_Organ
	(
	org_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO	
ALTER TABLE dbo.uum_group_member ADD CONSTRAINT
	FK_uum_group_member_Uum_Group FOREIGN KEY
	(
	group_id
	) REFERENCES dbo.Uum_Group
	(
	group_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO	
ALTER TABLE dbo.Syspl_Mod_Opt_Ref ADD CONSTRAINT
	FK_Syspl_Mod_Opt_Ref_syspl_operate FOREIGN KEY
	(
	operate_id
	) REFERENCES dbo.syspl_operate
	(
	operate_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.syspl_mod_opt_ref ADD CONSTRAINT
	FK_syspl_mod_opt_ref_Syspl_Module_Memu FOREIGN KEY
	(
	module_id
	) REFERENCES dbo.Syspl_Module_Memu
	(
	module_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.syspl_dic_small_type ADD CONSTRAINT
	FK_syspl_dic_small_type_Syspl_Dic_Big_Type FOREIGN KEY
	(
	big_type_id
	) REFERENCES dbo.Syspl_Dic_Big_Type
	(
	big_type_id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO 

--============================================================================
--
--============================================================================
DELETE Syspl_Affiche
INSERT INTO Syspl_Affiche
VALUES(1	,'huiyitongzhi'	,'2012-04-06 00:00:00.000'	,0,	'sasdasdasdas')
SELECT * FROM dbo.Syspl_Affiche
--============================================================================
--
--============================================================================
delete syspl_code
INSERT INTO syspl_code
VALUES
(1	,'store','仓库',	'系统编码管理',	'-',	1	,'char','store'	,'char'	,''	,'char'	,''	,'char','','','','enable',	'master'	,'2012-03-06 13:43:33.420'),
(2,'RKD','入库单','工作流','_',4,'char','RKD','date','yyyyMMdd','number','001','sysPara','userName','RKD_20120229_001_zhangsan','','enable','admin','2012-02-29 03:41:25');

SELECT * FROM dbo.syspl_code
--============================================================================
--
--============================================================================
DELETE syspl_dic_big_type

INSERT INTO syspl_dic_big_type 
VALUES 
(21,'系统类别','systemType','','enable','admin','2011-12-20 06:32:22'),
(22,'地区级别','district','','enable','admin','2011-12-20 07:11:29'),
(23,'机构类型','organType','','enable','admin','2011-12-21 01:33:12'),
(25,'页面类型','modPageType','','enable','admin','2012-01-13 04:09:36'),
(26,'test','test','','enable','admin','2012-03-01 03:33:35'),
(27,'aa','aa','','enable','admin','2012-03-01 03:40:01');

SELECT * FROM dbo.syspl_dic_big_type
--============================================================================
--
--============================================================================
DELETE syspl_dic_small_type
INSERT INTO syspl_dic_small_type 
VALUES 
(26,22,'省','province'),
(27,22,'县','town'),
(28,22,'区','district'),
(29,22,'市','city'),
(37,21,'系统平台','platform'),
(38,21,'统一用户','uum'),
(43,25,'js','js'),
(44,25,'html','html'),
(45,25,'flex','flex'),
(46,25,'jsp','jsp'),
(47,26,'t','t'),
(48,27,'b','b'),
(49,27,'a','a'),
(54,23,'门店','store'),
(55,23,'部门','department'),
(56,23,'分公司','branch');

SELECT * FROM syspl_dic_small_type

--============================================================================
--
--============================================================================
DELETE syspl_district

INSERT INTO syspl_district 
VALUES 
(0,'地区树','0',NULL,NULL,NULL,0,NULL,'enable',NULL,'2012-01-19 08:24:52'),
(1,'北京','','','','province',0,'','enable','admin','2012-03-05 01:53:48'),
(2,'上海','','','','city',0,'','enable','admin','2012-03-05 01:54:09'),
(3,'深圳','','','','city',0,'','enable','admin','2012-03-05 01:54:41'),
(4,'天津','','','','city',0,'','enable','admin','2012-03-05 01:55:05'),
(5,'海淀区','','','','province',1,'','enable','admin','2012-03-06 02:47:07');

SELECT * FROM dbo.syspl_district
--============================================================================
--
--============================================================================
DELETE syspl_mod_opt_ref
INSERT INTO syspl_mod_opt_ref 
VALUES 
(1,4,1,NULL),
(2,4,2,NULL),
(3,4,3,NULL),
(4,4,4,NULL),
(5,7,1,NULL),
(6,7,2,NULL),
(7,7,4,NULL),
(10,8,1,NULL),
(11,8,2,NULL),
(12,8,4,NULL),
(13,8,5,NULL),
(14,8,6,NULL),
(15,10,1,NULL),
(16,10,2,NULL),
(17,10,4,NULL),
(18,10,5,NULL),
(19,10,6,NULL),
(25,13,1,NULL),
(26,13,2,NULL),
(27,13,4,NULL),
(28,13,5,NULL),
(29,13,6,NULL),
(30,14,1,NULL),
(31,14,2,NULL),
(33,14,4,NULL),
(34,14,5,NULL),
(35,14,6,NULL),
(36,15,1,NULL),
(37,15,2,NULL),
(38,15,4,NULL),
(39,15,5,NULL),
(40,15,6,NULL),
(44,18,4,NULL),
(45,32,1,NULL),
(46,32,2,NULL),
(47,32,4,NULL),
(48,32,5,NULL),
(49,32,6,NULL),
(50,33,1,NULL),
(51,33,2,NULL),
(52,33,4,NULL),
(53,33,5,NULL),
(54,33,6,NULL),
(55,33,8,NULL),
(56,35,9,NULL),
(57,35,10,NULL),
(58,34,1,NULL),
(59,34,2,NULL),
(60,34,4,NULL),
(61,34,5,NULL),
(62,34,6,NULL),
(64,34,8,NULL),
(65,27,1,NULL),
(66,27,2,NULL),
(68,27,5,NULL),
(69,27,6,NULL),
(87,7,7,NULL),
(91,34,11,NULL),
(97,34,13,NULL),
(98,49,1,NULL),
(99,49,2,NULL),
(100,49,3,NULL),
(101,49,4,NULL),
(102,7,14,NULL),
(104,53,1,NULL),
(105,53,2,NULL),
(106,53,3,NULL),
(107,53,4,NULL),
(108,53,16,NULL),
(109,53,17,NULL),
(110,53,18,NULL),
(111,53,19,NULL),
(112,48,1,NULL),
(113,48,2,NULL),
(114,48,5,NULL),
(115,48,6,NULL),
(116,28,1,NULL),
(117,28,2,NULL),
(118,28,5,NULL),
(119,28,6,NULL),
(120,61,4,NULL);

SELECT * FROM dbo.syspl_mod_opt_ref
--============================================================================
--
--============================================================================
DELETE syspl_module_memu

INSERT INTO syspl_module_memu 
VALUES 
(0,'模块树','moduletree','modtree','0',1,'','',0,'enable','','admin','2012-01-19 08:25:40'),
(1,'系统平台','pltmgr','platform','1',1,'js','platform',0,'enable','','admin','2010-08-20 05:43:34'),
(2,'统一用户','uusmgr','uus','1',2,'js','uum',0,'enable','','admin','2010-08-20 05:44:29'),
(3,'主页框架管理','mainframemgr','frameMgr','2',2,'js','platform',1,'enable','','admin','2010-08-20 05:51:26'),
(4,'页面功能区管理','mainFrameFunMgr','pageFun','3',1,'jsp','platform',3,'enable','','admin','2010-08-20 06:03:57'),
(6,'模块操作管理','modoptmgr','moduleOp','2',3,'js','platform',1,'enable','','admin','2010-08-20 06:20:15'),
(7,'模块菜单管理','moduleMgr','moduleMgr','3',1,'js','platform',6,'enable','','admin','2010-08-20 06:21:50'),
(8,'操作按钮管理','operateMgr','oprateMgr','3',2,'js','platform',6,'enable','','admin','2010-08-20 06:22:45'),
(9,'字典管理','dicmgr','dicMgr','2',4,'js','platform',1,'enable','','admin','2010-08-20 06:27:12'),
(10,'类别字典管理','typeDicMgr','typeDicMgr','3',1,'js','platform',9,'enable','','admin','2010-08-20 06:30:13'),
(12,'系统管理','systemMgr','systemMgr','2',1,'js','platform',1,'enable','','admin','2010-08-20 06:32:28'),
(13,'系统编码管理','codeMgr','codMgr','3',2,'js','platform',12,'enable','','admin','2010-08-20 06:33:31'),
(14,'省市地区管理','priCityMgr','priCityMgr','3',1,'js','platform',52,'enable','','admin','2010-08-20 06:34:52'),
(15,'系统参数管理','systemParaMgr','systemParaMgr','3',3,'js','platform',12,'enable','','admin','2010-08-20 06:35:39'),
(17,'日志管理','logMgr','logMgr','2',5,'js','platform',1,'enable','','admin','2010-08-20 06:38:46'),
(18,'系统日志管理','sysLogMgr','sysLogMgr','3',1,'js','platform',17,'enable','','admin','2010-08-20 06:39:33'),
(19,'机构管理','organmgr','organmgrtitle','2',1,'js','uum',2,'enable','','admin','2010-08-21 05:33:29'),
(21,'角色管理','rolemgr','rolemgrtitle','2',2,'js','uum',2,'enable','','admin','2010-09-13 13:04:30'),
(22,'用户管理','usermgr','usermgrtitle','2',3,'js','uum',2,'enable','','admin','2010-09-13 13:05:20'),
(23,'群组管理','groupmgr','groupmgr','2',4,'js','uum',2,'enable','','admin','2010-09-13 13:07:28'),
(25,'系统配置管理','sysConMgr','sysConMgr','3',1,'js','platform',12,'enable','','admin','2010-09-13 13:09:13'),
(27,'用户组管理','userGroupMgr','usergroupmgr','3',1,'js','uum',23,'enable','','admin','2010-09-13 13:10:41'),
(28,'普通组管理','generalGroupMgr','gernalgroupmgr','3',3,'js','uum',23,'disable','','admin','2010-09-13 13:11:40'),
(32,'机构信息管理','organMgr','organmgr','3',1,'js','uum',19,'enable','','admin','2010-09-13 13:14:44'),
(33,'角色信息管理','roleMgr','rolemgr','3',1,'js','uum',21,'enable','','admin','2010-09-13 13:15:10'),
(34,'用户信息管理','userMgr','usermgr','3',1,'js','uum',22,'enable','','admin','2010-09-13 13:16:03'),
(35,'角色分配管理','roleOrganMgr','roleassignmgr','3',1,'js','uum',21,'enable','','admin','2010-09-14 02:29:14'),
(48,'角色组管理','roleGroupMgr','rolegroupmgr','3',2,'js','uum',23,'enable','','3','2010-09-22 05:08:11'),
(49,'系统公告管理','afficheMgr','afficheMgr','3',4,'js','platform',12,'enable','','admin','2010-12-01 02:47:27'),
(50,'工作流','workflowId','workflow','1',3,'','',0,'enable','','admin','2010-12-09 07:08:40'),
(52,'省市地区','priCityMgr','priCityMgrTitle','2',3,'js','platform',1,'enable','','admin','2011-01-17 02:16:14'),
(53,'系统调度管理','schedulerMgr','schedulerMgr','3',5,'js','platform',12,'enable','','admin','2011-01-17 02:34:43'),
(60,'测试','test','aaa','2',1,'js','platform',50,'enable','','admin','2011-12-20 05:56:54'),
(61,'待办任务','underwayTaskId','','3',1,'js','platform',60,'enable','','admin','2011-12-21 03:02:50');

SELECT * FROM [dbo].[syspl_module_memu]

--============================================================================
--
--============================================================================
DELETE syspl_operate
INSERT INTO syspl_operate
SELECT 1,	'增加',	'add',	'table_add',	1,	1,	'增加'	,'enable',	'admin',	'2010-10-28 07:16:25' UNION ALL
SELECT 2,	'修改',	'modify',	'table_edit',	2,	1,	''	,'enable',	'admin',	'2010-10-28 07:27:36' UNION ALL
SELECT 3,	'删除',	'delete',	'table_delete',	3,	1,	'',	'enable',	'admin',	'2010-10-28 07:30:29' UNION ALL
SELECT 4,	'查看',	'view',	'table',	4,	1,	'',	'enable',	'admin',	'2010-10-28 11:07:11' UNION ALL
SELECT 5,	'启用',	'enable',	'enable',	5,	1,	'',	'enable','admin',	'2010-10-29 01:01:42' UNION ALL
SELECT 6,	'停用',	'disable',	'disable',	6,	1,'停用','enable',	'admin',	'2010-10-29 01:02:11' UNION ALL
SELECT 7,	'模块分配操作',	'optassign',	'assign',	7,	1,	'',	'enable',	'admin',	'2010-11-01 12:07:40' UNION ALL
SELECT 8,	'模块操作权限',	'optpri',	'assign',	8,	1,	'',	'enable',	'admin',	'2010-11-02 03:42:42' UNION ALL
SELECT 9,	'角色分配',	'roleassign',	'assign',	6,	1,	'',	'enable',	'admin',	'2010-11-02 03:44:10' UNION ALL
SELECT 10,	'角色删除',	'roledel',	'table_delete',	7,	1,	'',	'enable',	'admin',	'2010-11-02 03:44:39' UNION ALL
SELECT 11,	'分配角色',	'assignrole',	'rolemgr',	6,	1,	'',	'enable','admin',	'2010-11-02 03:46:31' UNION ALL
SELECT 12,	'行权限',	'rowprivilege',	'privialmgr',	8,	1,	'',	'enable',	'admin',	'2010-11-07 06:01:16' UNION ALL
SELECT 13,	'重置密码',	'resetpwd',	'resetpwd',	7,	1,	'重置密码'	,'enable','admin',	'2010-11-07 10:14:23' UNION ALL
SELECT 14,	'模块标签',	'moduleLabel',	'module_label',	7,	1,	'',	'enable',	'admin',	'2010-12-26 02:23:29' UNION ALL
SELECT 15,	'保留时间',	'saveTime',	'save_time',	5,	1,	'',	'enable',	'admin',	'2010-12-30 08:05:31' UNION ALL
SELECT 16,	'启动调度器',	'schedulerStart',	'scheduler_start',	5,	1,	'',	'enable',	'admin',	'2011-01-17 06:20:06' UNION ALL
SELECT 17,	'停止调度器',	'schedulerStop',	'scheduler_stop',	6,	1,	'',	'enable',	'admin',	'2011-01-17 06:20:54' UNION ALL
SELECT 18,	'启动任务',	'jobStart',	'job_start',	7,	1,	'',	'enable',	'admin',	'2011-01-17 07:01:19' UNION ALL
SELECT 19,	'停止任务',	'jobStop',	'job_stop',	8,	1,	'',	'disable',	'admin',	'2011-01-17 07:01:19'
SELECT * FROM dbo.syspl_operate
--============================================================================
--
--============================================================================
DELETE [syspl_primarykey_seq]
INSERT INTO [syspl_primarykey_seq]
SELECT 'SysplAccessory','accessoryId' UNION ALL
SELECT 'SysplAffiche','afficheId' UNION ALL
SELECT 'SysplCode','codeId' UNION ALL
SELECT 'SysplDicBigType','bigTypeId' UNION ALL
SELECT 'SysplDicSmallType','smallTypeId' UNION ALL
SELECT 'SysplDistrict','districtId' UNION ALL
SELECT 'SysplModOptRef','modOptId' UNION ALL
SELECT 'SysplModuleMemu','moduleId' UNION ALL
SELECT 'SysplOperate','operateId' UNION ALL
SELECT 'SysplSchedulerJob','jobId' UNION ALL
SELECT 'SysplSysOptLog','optId' UNION ALL
SELECT 'SysplSysParameter','paraId' UNION ALL
SELECT 'UumGroup','groupId' UNION ALL
SELECT 'UumGroupMember','groupMemberId' UNION ALL
SELECT 'UumOrgan','orgId' UNION ALL
SELECT 'UumOrgRoleRef','orgRoleId' UNION ALL
SELECT 'UumPrivilege','privilegeId' UNION ALL
SELECT 'UumRole','roleId' UNION ALL
SELECT 'UumRoleUserRef','orgRoleUserId' UNION ALL
SELECT 'UumUser','userId'

SELECT * FROM [dbo].[syspl_primarykey_seq]

--============================================================================
--
--============================================================================
DELETE syspl_scheduler_job
INSERT INTO syspl_scheduler_job VALUES (1,'logJob','com.program.service.impl.platform.job.LoggerJob','Cron','* * 0 15 * ?','2011-01-19 07:28:44','2011-02-19 07:28:44',2,2,'disable','每月15日0点清除日志','admin','2011-01-19 07:28:44');
INSERT INTO syspl_scheduler_job VALUES (3,'postgresqlBbBackup','com.program.service.impl.platform.job.PostgresqlDbBackupJob','Cron','0/10 * * * * ?','2011-01-19 07:28:44','2011-01-19 07:28:44',2,2,'enable','每天0点备份','admin','2011-01-21 03:34:11');
SELECT * FROM dbo.syspl_scheduler_job

--============================================================================
--
--============================================================================
DELETE syspl_sys_parameter
INSERT INTO syspl_sys_parameter VALUES (1,'test','test','test','platform','','enable','admin','2010-12-25 03:30:00');
INSERT INTO syspl_sys_parameter VALUES (2,'用户默认密码','userdefaultpassword','123456','uus','','enable','admin','2010-12-11 02:26:23');
INSERT INTO syspl_sys_parameter VALUES (3,'d','d','d','uus','','enable','admin','2010-12-25 03:57:34');
INSERT INTO syspl_sys_parameter VALUES (7,'用户有效期','uservalidate','6','uus','','enable','admin','2010-12-11 02:53:32');
INSERT INTO syspl_sys_parameter VALUES (8,'aaaaaaaaaaaaaaaaaaaa','aaaaaaaaaaaaaaaaaaaa','aaaaaaaaaaaaaaaaaaaa','platform','aaaaaaaaaaaaaaaaaadddddddddffffffffccccccccccccccc','enable','admin','2011-02-15 06:06:18');
INSERT INTO syspl_sys_parameter VALUES (9,'dddddddddddddddddddd','dddddddddddddddddddd','dddddddddddddddddddd','platform','','enable','admin','2011-02-15 06:08:18');
INSERT INTO syspl_sys_parameter VALUES (10,'da','da','d','platform','','enable','admin','2011-02-15 06:08:48');

SELECT * FROM dbo.syspl_sys_parameter
--============================================================================
--
--============================================================================
DELETE uum_group

INSERT INTO uum_group 
VALUES 
(1,'roleGroup','test','','enable','admin','2012-02-29 07:49:06'),
(2,'roleGroup','a','','enable','admin','2012-02-29 07:51:41'),
(3,'roleGroup','b','','enable','admin','2012-02-29 07:55:18'),
(4,'roleGroup','c','','enable','admin','2012-02-29 07:57:45'),
(5,'roleGroup','d','','enable','admin','2012-02-29 07:58:45'),
(6,'roleGroup','e','','enable','admin','2012-02-29 08:44:51'),
(7,'roleGroup','f','','enable','admin','2012-02-29 09:11:20'),
(8,'roleGroup','g','','enable','admin','2012-03-01 03:02:40'),
(9,'roleGroup','h','','enable','admin','2012-03-01 03:09:59'),
(10,'roleGroup','t','','enable','admin','2012-03-01 03:19:48'),
(11,'roleGroup','y','','enable','admin','2012-03-01 03:21:01'),
(12,'roleGroup','u','','enable','admin','2012-03-01 03:21:52'),
(13,'roleGroup','q','','enable','admin','2012-03-01 03:27:32'),
(14,'userGroup','店长组','店长','enable','admin','2012-03-06 01:31:44'),
(15,'roleGroup','店员组','','enable','admin','2012-03-06 01:32:41'),
(16,'generalGroup','经理组','','enable','admin','2012-03-06 01:33:59');

SELECT * FROM dbo.uum_group
--============================================================================
--
--============================================================================
DELETE uum_org_role_ref

INSERT INTO uum_org_role_ref 
VALUES 
(0,0,0),
(2,0,1),
(9,3,15),
(10,3,8),
(11,0,2),
(12,0,3),
(13,5,6),
(14,6,13),
(15,7,13),
(16,7,6),
(17,6,6),
(18,2,4),
(19,3,11),
(20,10,5),
(21,10,12),
(22,12,5),
(23,12,12),
(24,13,16),
(29,7,17),
(30,7,18),
(31,7,19),
(32,7,20),
(33,16,17),
(34,16,18),
(35,16,19),
(36,16,20);

SELECT * FROM dbo.uum_org_role_ref
--============================================================================
--
--============================================================================
DELETE uum_organ

INSERT INTO uum_organ 
VALUES 
(0,'总部',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'enable',NULL,NULL,'2012-01-19 08:23:05'),
(1,'厨房部','厨房部','CFB','','','','',NULL,'HQ',NULL,'','','','',1,0,'enable','','admin','2012-03-02 02:05:28'),
(2,'人力资源部','人力资源部','YLZY','','','','',NULL,'city',NULL,'','','','',1,0,'enable','','admin','2012-03-05 01:53:03'),
(3,'培训部','','','','','','',NULL,'HQ',NULL,'','','','',1,2,'enable','','admin','2012-03-05 01:56:25'),
(4,'后台部','','','','','','',NULL,'city',NULL,'','','','',1,2,'enable','','admin','2012-03-05 01:57:13'),
(5,'运营部','运营部','YYB','','','','',NULL,'department',NULL,'','','','',1,0,'enable','','admin','2012-03-05 02:03:04'),
(6,'北京运营部','北京运营部','','','','','',NULL,'city',NULL,'','','','',1,5,'enable','','admin','2012-03-05 02:03:51'),
(7,'上海运营部','','','','','','',NULL,'city',NULL,'','','','',2,5,'enable','','admin','2012-03-05 02:04:18'),
(8,'天津运营部','','','','','','',NULL,'city',NULL,'','','','',4,5,'enable','','admin','2012-03-05 02:04:52'),
(9,'加工中心','','','','','','',NULL,'HQ',NULL,'','','','',1,1,'enable','','admin','2012-03-05 02:06:48'),
(10,'储运部','储运部','','','','','',NULL,'HQ',NULL,'','','','',1,0,'enable','','admin','2012-03-05 02:07:45'),
(11,'配送中心','','','','','','',NULL,'HQ',NULL,'','','','',1,10,'enable','','admin','2012-03-05 02:08:23'),
(12,'北京配送中心','','','','','','',NULL,'HQ',NULL,'','','','',1,11,'enable','','admin','2012-03-05 02:09:04'),
(13,'五道品中心','','','','','','',NULL,'department',NULL,'','','','',1,12,'enable','','admin','2012-03-05 02:10:00'),
(14,'上海配送中心','','','','','','',NULL,'HQ',NULL,'','','','',2,11,'enable','','admin','2012-03-05 02:12:27'),
(15,'清华大学中心','','','','','','',NULL,'HQ',NULL,'','','','',1,12,'enable','','admin','2012-03-05 02:13:30'),
(16,'五道口','','','','','','',NULL,'department',NULL,'','','','',1,6,'enable','','admin','2012-03-05 02:35:55');

SELECT * FROM dbo.uum_organ
--============================================================================
--
--============================================================================
DELETE [uum_privilege]

INSERT INTO uum_privilege 
VALUES 
(180,'44',2,'rol','inc','opt'),
(181,'all',3,'rol','all','opt'),
(182,'1',4,'rol','inc','opt'),
(183,'2',4,'rol','inc','opt'),
(184,'3',4,'rol','inc','opt'),
(185,'4',4,'rol','inc','opt'),
(186,'5',4,'rol','inc','opt'),
(187,'6',4,'rol','inc','opt'),
(188,'7',4,'rol','inc','opt'),
(189,'87',4,'rol','inc','opt'),
(192,'102',4,'rol','inc','opt'),
(193,'10',4,'rol','inc','opt'),
(194,'11',4,'rol','inc','opt'),
(195,'12',4,'rol','inc','opt'),
(196,'13',4,'rol','inc','opt'),
(197,'14',4,'rol','inc','opt'),
(198,'15',4,'rol','inc','opt'),
(199,'16',4,'rol','inc','opt'),
(200,'17',4,'rol','inc','opt'),
(201,'18',4,'rol','inc','opt'),
(202,'19',4,'rol','inc','opt'),
(203,'25',4,'rol','inc','opt'),
(204,'26',4,'rol','inc','opt'),
(205,'27',4,'rol','inc','opt'),
(206,'28',4,'rol','inc','opt'),
(207,'29',4,'rol','inc','opt'),
(208,'36',4,'rol','inc','opt'),
(209,'37',4,'rol','inc','opt'),
(210,'38',4,'rol','inc','opt'),
(211,'39',4,'rol','inc','opt'),
(212,'40',4,'rol','inc','opt'),
(213,'98',4,'rol','inc','opt'),
(214,'99',4,'rol','inc','opt'),
(215,'100',4,'rol','inc','opt'),
(216,'101',4,'rol','inc','opt'),
(217,'104',4,'rol','inc','opt'),
(218,'105',4,'rol','inc','opt'),
(219,'106',4,'rol','inc','opt'),
(220,'107',4,'rol','inc','opt'),
(221,'108',4,'rol','inc','opt'),
(222,'109',4,'rol','inc','opt'),
(223,'110',4,'rol','inc','opt'),
(224,'111',4,'rol','inc','opt'),
(225,'44',4,'rol','inc','opt'),
(226,'30',4,'rol','inc','opt'),
(227,'31',4,'rol','inc','opt'),
(228,'33',4,'rol','inc','opt'),
(229,'34',4,'rol','inc','opt'),
(230,'35',4,'rol','inc','opt'),
(231,'rol',4,'rol','rol','row'),
(232,'45',6,'rol','inc','opt'),
(233,'46',6,'rol','inc','opt'),
(234,'47',6,'rol','inc','opt'),
(235,'48',6,'rol','inc','opt'),
(236,'49',6,'rol','inc','opt'),
(237,'50',6,'rol','inc','opt'),
(238,'51',6,'rol','inc','opt'),
(239,'52',6,'rol','inc','opt'),
(240,'53',6,'rol','inc','opt'),
(241,'54',6,'rol','inc','opt'),
(242,'55',6,'rol','inc','opt'),
(243,'56',6,'rol','inc','opt'),
(244,'57',6,'rol','inc','opt'),
(245,'58',6,'rol','inc','opt'),
(246,'59',6,'rol','inc','opt'),
(247,'60',6,'rol','inc','opt'),
(248,'61',6,'rol','inc','opt'),
(249,'62',6,'rol','inc','opt'),
(250,'64',6,'rol','inc','opt'),
(251,'91',6,'rol','inc','opt'),
(252,'97',6,'rol','inc','opt'),
(253,'65',6,'rol','inc','opt'),
(254,'66',6,'rol','inc','opt'),
(255,'68',6,'rol','inc','opt'),
(256,'69',6,'rol','inc','opt'),
(257,'116',6,'rol','inc','opt'),
(258,'117',6,'rol','inc','opt'),
(259,'118',6,'rol','inc','opt'),
(260,'119',6,'rol','inc','opt'),
(261,'112',6,'rol','inc','opt'),
(262,'113',6,'rol','inc','opt'),
(263,'114',6,'rol','inc','opt'),
(264,'115',6,'rol','inc','opt'),
(265,'1',13,'rol','exc','opt'),
(266,'2',13,'rol','exc','opt'),
(267,'3',13,'rol','exc','opt'),
(268,'4',13,'rol','exc','opt'),
(269,'15',13,'rol','exc','opt'),
(270,'16',13,'rol','exc','opt'),
(271,'17',13,'rol','exc','opt'),
(272,'18',13,'rol','exc','opt'),
(273,'19',13,'rol','exc','opt'),
(274,'25',13,'rol','exc','opt'),
(275,'26',13,'rol','exc','opt'),
(276,'27',13,'rol','exc','opt'),
(277,'28',13,'rol','exc','opt'),
(278,'29',13,'rol','exc','opt'),
(279,'36',13,'rol','exc','opt'),
(280,'37',13,'rol','exc','opt'),
(281,'38',13,'rol','exc','opt'),
(282,'39',13,'rol','exc','opt'),
(283,'40',13,'rol','exc','opt'),
(284,'98',13,'rol','exc','opt'),
(285,'99',13,'rol','exc','opt'),
(286,'100',13,'rol','exc','opt'),
(287,'101',13,'rol','exc','opt'),
(288,'104',13,'rol','exc','opt'),
(289,'105',13,'rol','exc','opt'),
(290,'106',13,'rol','exc','opt'),
(291,'107',13,'rol','exc','opt'),
(292,'108',13,'rol','exc','opt'),
(293,'109',13,'rol','exc','opt'),
(294,'110',13,'rol','exc','opt'),
(295,'111',13,'rol','exc','opt'),
(296,'45',13,'rol','exc','opt'),
(297,'46',13,'rol','exc','opt'),
(298,'47',13,'rol','exc','opt'),
(299,'48',13,'rol','exc','opt'),
(300,'49',13,'rol','exc','opt'),
(301,'5',17,'rol','inc','opt'),
(302,'6',17,'rol','inc','opt'),
(303,'7',17,'rol','inc','opt'),
(304,'87',17,'rol','inc','opt'),
(307,'102',17,'rol','inc','opt'),
(308,'10',17,'rol','inc','opt'),
(309,'11',17,'rol','inc','opt'),
(310,'12',17,'rol','inc','opt'),
(311,'13',17,'rol','inc','opt'),
(312,'14',17,'rol','inc','opt'),
(313,'25',17,'rol','inc','opt'),
(314,'26',17,'rol','inc','opt'),
(315,'27',17,'rol','inc','opt'),
(316,'28',17,'rol','inc','opt'),
(317,'29',17,'rol','inc','opt'),
(318,'36',17,'rol','inc','opt'),
(319,'37',17,'rol','inc','opt'),
(320,'38',17,'rol','inc','opt'),
(321,'39',17,'rol','inc','opt'),
(322,'40',17,'rol','inc','opt'),
(323,'98',17,'rol','inc','opt'),
(324,'99',17,'rol','inc','opt'),
(325,'100',17,'rol','inc','opt'),
(326,'101',17,'rol','inc','opt'),
(327,'104',17,'rol','inc','opt'),
(328,'105',17,'rol','inc','opt'),
(329,'106',17,'rol','inc','opt'),
(330,'107',17,'rol','inc','opt'),
(331,'108',17,'rol','inc','opt'),
(332,'109',17,'rol','inc','opt'),
(333,'110',17,'rol','inc','opt'),
(334,'111',17,'rol','inc','opt'),
(335,'5',18,'rol','inc','opt'),
(336,'6',18,'rol','inc','opt'),
(337,'7',18,'rol','inc','opt'),
(338,'87',18,'rol','inc','opt'),
(341,'102',18,'rol','inc','opt'),
(342,'10',18,'rol','inc','opt'),
(343,'11',18,'rol','inc','opt'),
(344,'12',18,'rol','inc','opt'),
(345,'13',18,'rol','inc','opt'),
(346,'14',18,'rol','inc','opt'),
(347,'7',19,'rol','inc','opt'),
(348,'11',19,'rol','inc','opt'),
(349,'12',19,'rol','inc','opt'),
(350,'13',19,'rol','inc','opt'),
(351,'14',19,'rol','inc','opt'),
(432,'1',11,'usr','org','row'),
(433,'2',11,'usr','org','row'),
(434,'3',11,'usr','org','row'),
(435,'1',20,'rol','exc','opt'),
(436,'2',20,'rol','exc','opt'),
(437,'3',20,'rol','exc','opt'),
(438,'4',20,'rol','exc','opt'),
(439,'5',20,'rol','exc','opt'),
(440,'6',20,'rol','exc','opt'),
(441,'7',20,'rol','exc','opt'),
(442,'87',20,'rol','exc','opt'),
(443,'102',20,'rol','exc','opt'),
(444,'10',20,'rol','exc','opt'),
(445,'11',20,'rol','exc','opt'),
(446,'12',20,'rol','exc','opt'),
(447,'13',20,'rol','exc','opt'),
(448,'14',20,'rol','exc','opt'),
(449,'15',20,'rol','exc','opt'),
(450,'16',20,'rol','exc','opt'),
(451,'17',20,'rol','exc','opt'),
(452,'18',20,'rol','exc','opt'),
(453,'19',20,'rol','exc','opt'),
(454,'25',20,'rol','exc','opt'),
(455,'26',20,'rol','exc','opt'),
(456,'27',20,'rol','exc','opt'),
(457,'28',20,'rol','exc','opt'),
(458,'29',20,'rol','exc','opt'),
(459,'36',20,'rol','exc','opt'),
(460,'37',20,'rol','exc','opt'),
(461,'38',20,'rol','exc','opt'),
(462,'39',20,'rol','exc','opt'),
(463,'40',20,'rol','exc','opt'),
(464,'98',20,'rol','exc','opt'),
(465,'99',20,'rol','exc','opt'),
(466,'100',20,'rol','exc','opt'),
(467,'101',20,'rol','exc','opt'),
(468,'104',20,'rol','exc','opt'),
(469,'105',20,'rol','exc','opt'),
(470,'106',20,'rol','exc','opt'),
(471,'107',20,'rol','exc','opt'),
(472,'108',20,'rol','exc','opt'),
(473,'109',20,'rol','exc','opt'),
(474,'110',20,'rol','exc','opt'),
(475,'111',20,'rol','exc','opt'),
(476,'30',20,'rol','exc','opt'),
(477,'31',20,'rol','exc','opt'),
(478,'33',20,'rol','exc','opt'),
(479,'34',20,'rol','exc','opt'),
(480,'35',20,'rol','exc','opt'),
(481,'45',20,'rol','exc','opt'),
(482,'46',20,'rol','exc','opt'),
(483,'47',20,'rol','exc','opt'),
(484,'48',20,'rol','exc','opt'),
(485,'49',20,'rol','exc','opt'),
(486,'50',20,'rol','exc','opt'),
(487,'51',20,'rol','exc','opt'),
(488,'52',20,'rol','exc','opt'),
(489,'53',20,'rol','exc','opt'),
(490,'54',20,'rol','exc','opt'),
(491,'55',20,'rol','exc','opt'),
(492,'56',20,'rol','exc','opt'),
(493,'57',20,'rol','exc','opt'),
(494,'58',20,'rol','exc','opt'),
(495,'59',20,'rol','exc','opt'),
(496,'61',20,'rol','exc','opt'),
(497,'62',20,'rol','exc','opt'),
(498,'64',20,'rol','exc','opt'),
(499,'91',20,'rol','exc','opt'),
(500,'97',20,'rol','exc','opt'),
(501,'65',20,'rol','exc','opt'),
(502,'66',20,'rol','exc','opt'),
(503,'68',20,'rol','exc','opt'),
(504,'69',20,'rol','exc','opt'),
(505,'116',20,'rol','exc','opt'),
(506,'117',20,'rol','exc','opt'),
(507,'118',20,'rol','exc','opt'),
(508,'119',20,'rol','exc','opt'),
(509,'112',20,'rol','exc','opt'),
(510,'113',20,'rol','exc','opt'),
(511,'114',20,'rol','exc','opt'),
(512,'115',20,'rol','exc','opt'),
(513,'120',20,'rol','exc','opt'),
(514,'1',7,'usr','org','row');

SELECT *  FROM [dbo].[uum_privilege]
--============================================================================
--
--============================================================================
DELETE uum_role

INSERT INTO uum_role VALUES 
(0,'无角色用户',NULL,'enable','','admin','2012-01-19 08:23:20'),
(1,'管理员',1,'enable','','admin','2012-01-19 08:23:47'),
(2,'总经理',1,'enable','','admin','2012-02-29 07:47:22'),
(3,'副总经理',2,'enable','','admin','2012-03-02 07:29:59'),
(4,'行政人事经理',3,'enable','','admin','2012-03-05 02:18:01'),
(5,'储运部经理',3,'enable','','admin','2012-03-05 02:18:30'),
(6,'运营经理',3,'enable','','admin','2012-03-05 02:18:49'),
(7,'财务经理',3,'enable','','admin','2012-03-05 02:19:14'),
(8,'培训经理',3,'enable','','admin','2012-03-05 02:19:34'),
(9,'采购经理',3,'enable','','admin','2012-03-05 02:19:52'),
(10,'总经理助理',3,'enable','','admin','2012-03-05 02:20:49'),
(11,'人事主管',4,'enable','','admin','2012-03-05 02:21:12'),
(12,'储运主管',4,'enable','','admin','2012-03-05 02:21:33'),
(13,'运营主管',4,'enable','','admin','2012-03-05 02:21:42'),
(14,'财务主管',4,'enable','','admin','2012-03-05 02:21:58'),
(15,'培训主管',4,'enable','','admin','2012-03-05 02:22:17'),
(16,'采购主管',4,'enable','','admin','2012-03-05 02:22:34'),
(17,'店长',5,'enable','','admin','2012-03-05 02:23:23'),
(18,'会计',6,'enable','','admin','2012-03-05 02:23:38'),
(19,'出纳',7,'enable','','admin','2012-03-05 02:24:02'),
(20,'店员',8,'enable','','admin','2012-03-05 02:24:21');

SELECT * FROM dbo.uum_role
--============================================================================
--
--============================================================================
DELETE uum_role_user_ref

INSERT INTO uum_role_user_ref 
VALUES 
(1,1,2,'Y'),
(29,2,11,'Y'),
(30,3,12,'Y'),
(31,4,13,'Y'),
(32,5,17,'Y'),
(33,6,14,'Y'),
(35,8,34,'Y'),
(36,9,35,'Y'),
(37,10,36,'Y'),
(41,11,36,'N'),
(42,11,33,'Y'),
(43,11,0,'N'),
(44,7,33,'Y'),
(45,7,11,'N');
SELECT * FROM dbo.uum_role_user_ref
--============================================================================
--
--============================================================================
DELETE uum_user

INSERT INTO uum_user 
VALUES 
(1,'admin','管理员','123456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'enable','admin','2012-01-19 08:24:43',NULL),
(2,'sjh','石纪红','UlFQV1ZV','0','总经理','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\E3gdIr_1_jacbo.jpg','','','','','','','','','2012-09-02',NULL,0,'','enable','admin','2012-03-02 08:47:02','sjh'),
(3,'zsp','张世鹏','UlFQV1ZV','0','副总经理','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\','','','','','','','','','2012-09-02',NULL,0,'','enable','admin','2012-03-02 08:46:07','zsp'),
(4,'gby','高兵阳','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,5,'','enable','admin','2012-03-05 02:48:26','gby'),
(5,'zj','张军','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,6,'','enable','admin','2012-03-05 02:50:02','zj'),
(6,'yy','杨勇','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,6,'','enable','admin','2012-03-05 02:51:05','yy'),
(7,'wx','吴雪','UlFQV1ZV','1','','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:53:00','wx'),
(8,'zmh','张明辉','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:53:46','zmh'),
(9,'wg','吴刚','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:54:34','wg'),
(10,'blq','白力强','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:55:22','blq'),
(11,'wl','王乐','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:56:46','wl');

SELECT * FROM dbo.uum_user
--============================================================================
--
--============================================================================
DELETE syspl_accessory

INSERT INTO syspl_accessory 
VALUES (1,1,'bRF2DU_1_jacbo.jpg')
SELECT * FROM syspl_accessory

--============================================================================
--
--============================================================================
DELETE uum_group_member

INSERT INTO uum_group_member 
VALUES 
(1,'role',1,1),
(2,'role',2,1),
(3,'role',2,2),
(4,'role',1,2),
(5,'role',2,7),
(6,'role',1,7),
(7,'role',2,NULL),
(8,'role',1,NULL),
(9,'role',2,11),
(10,'role',1,11),
(11,'role',2,12),
(12,'role',1,12),
(13,'role',2,NULL),
(14,'role',1,NULL),
(15,'user',4,14),
(16,'role',19,15),
(17,'role',20,15),
(18,'role',17,15),
(19,'role',18,15),
(20,'user',4,16),
(21,'role',6,16);

SELECT * FROM uum_group_member

--============================================================================
--
--============================================================================
DELETE uum_user_agent

INSERT INTO uum_user_agent 
VALUES 
(1,1,9,2,'2012-03-06','2012-03-15',''),
(3,9,11,35,'2012-03-06','2012-03-08','');

SELECT * FROM uum_user_agent

--============================================================================
--
--============================================================================
DELETE uum_user_agent_privilege

INSERT INTO uum_user_agent_privilege 
VALUES 
(1,1,'all','opt'),
(36,3,'12','opt');

SELECT * FROM uum_user_agent_privilege
--============================================================================
--
--============================================================================
DELETE uum_user_commfun

INSERT INTO uum_user_commfun 
VALUES (1,32);

SELECT * FROM uum_user_commfun
