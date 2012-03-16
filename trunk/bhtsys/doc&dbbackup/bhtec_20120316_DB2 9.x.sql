--==============================================================
-- DBMS name:      IBM DB2 UDB 9.x Common Server
-- Created on:     2012-3-16 16:48:57
--==============================================================
/*命令行执行*/
db2cmd
db2
db2stop force
db2start
drop database BHTSYS
create database BHTSYS using codeset utf-8 territory CN COLLATE USING SYSTEM PAGESIZE 32768
/*db2客户端Quest Central中执行*/
CREATE SCHEMA BHTEC AUTHORIZATION BHTSYS


--==============================================================
-- Domain: CREATE_TIME
--==============================================================
create distinct type CREATE_TIME as TIMESTAMP with comparisons;

--==============================================================
-- Domain: CREATOR
--==============================================================
create distinct type CREATOR as VARCHAR(15) with comparisons;

--==============================================================
-- Domain: MEMO
--==============================================================
create distinct type MEMO as VARCHAR(100) with comparisons;

--==============================================================
-- Domain: STATUS
--==============================================================
create distinct type STATUS as VARCHAR(15) with comparisons;


SET CURRENT SCHEMA='BHTEC';
/*
drop table PORTAL;

drop table SYSPL_ACCESSORY;

drop table SYSPL_AFFICHE;

drop table SYSPL_CODE;

drop table SYSPL_DB_OPT_LOG;

drop table SYSPL_DIC_BIG_TYPE;

drop index BIG_TYPE_ID_INDEX;

drop table SYSPL_DIC_SMALL_TYPE;

drop index DIS_NAME_INDEX;

drop table SYSPL_DISTRICT;

drop index MOD_NAME_INDEX;

drop table SYSPL_MODULE_MEMU;

drop index MODULE_ID_INDEX;

drop table SYSPL_MOD_OPT_REF;

drop table SYSPL_OPERATE;

drop table SYSPL_SCHEDULER_JOB;

drop index OPT_UOGAN_INDEX;

drop index OPT_UROLE_INDEX;

drop index OPT_UNAME_INDEX;

drop index OPT_MOD_NAME_INDEX;

drop index OPT_TIME_INDEX;

drop table SYSPL_SYS_OPT_LOG;

drop index PARA_NAME_INDEX;

drop table SYSPL_SYS_PARAMETER;

drop table UUM_GROUP;

drop table UUM_GROUP_MEMBER;

drop index ORG_NAME_INDEX;

drop table UUM_ORGAN;

drop table UUM_ORG_ROLE_REF;

drop index OWNER_TYPE_INDEX;

drop index PRI_TYPE_INDEX;

drop table UUM_PRIVILEGE;

drop table UUM_ROLE;

drop table UUM_ROLE_USER_REF;

drop table UUM_USER;

drop table UUM_USER_AGENT;

drop table UUM_USER_AGENT_PRIVILEGE;

drop table UUM_USER_COMMFUN;

drop table UUM_USER_PAGE_LAYOUT;

drop distinct type CREATE_TIME;

drop distinct type CREATOR;

drop distinct type MEMO;

drop distinct type STATUS;

drop sequence PORTAL_SEQ;

drop sequence SYSPL_ACCESSORY_SEQ;

drop sequence SYSPL_AFFICHE_SEQ;

drop sequence SYSPL_CODE_SEQ;

drop sequence SYSPL_DB_OPT_LOG_SEQ;

drop sequence SYSPL_DIC_BIG_TYPE_SEQ;

drop sequence SYSPL_DIC_SMALL_TYPE_SEQ;

drop sequence SYSPL_DISTRICT_SEQ;

drop sequence SYSPL_FUNC_AREA_SEQ;

drop sequence SYSPL_MODULE_CODE_SEQ;

drop sequence SYSPL_MODULE_MEMU_SEQ;

drop sequence SYSPL_MOD_OPT_REF_SEQ;

drop sequence SYSPL_OPERATE_SEQ;

drop sequence SYSPL_PAGE_DICTIONARY_SEQ;

drop sequence SYSPL_PAGE_PATTERN_SEQ;

drop sequence SYSPL_SCHEDULER_JOB_SEQ;

drop sequence SYSPL_SYS_OPT_LOG_SEQ;

drop sequence SYSPL_SYS_PARAMETER_SEQ;

drop sequence UUM_GROUP_MEMBER_SEQ;

drop sequence UUM_GROUP_SEQ;

drop sequence UUM_ORGAN_SEQ;

drop sequence UUM_ORG_ROLE_REF_SEQ;

drop sequence UUM_PRIVILEGE_SEQ;

drop sequence UUM_ROLE_SEQ;

drop sequence UUM_ROLE_USER_REF_SEQ;

drop sequence UUM_USER_AGENT_PRIVILEGE_SEQ;

drop sequence UUM_USER_AGENT_SEQ;

drop sequence UUM_USER_SEQ;
*/
create sequence PORTAL_SEQ
increment by 1
start with 5;

create sequence SYSPL_ACCESSORY_SEQ
increment by 1
start with 5;

create sequence SYSPL_AFFICHE_SEQ
increment by 1
start with 5;

create sequence SYSPL_CODE_SEQ
increment by 1
start with 5;

create sequence SYSPL_DB_OPT_LOG_SEQ
increment by 1
start with 5;

create sequence SYSPL_DIC_BIG_TYPE_SEQ
increment by 1
start with 20;

create sequence SYSPL_DIC_SMALL_TYPE_SEQ
increment by 1
start with 20;

create sequence SYSPL_DISTRICT_SEQ
increment by 1
start with 1000;

create sequence SYSPL_FUNC_AREA_SEQ
increment by 1
start with 20;

create sequence SYSPL_MODULE_CODE_SEQ
increment by 1
start with 1;

create sequence SYSPL_MODULE_MEMU_SEQ
increment by 1
start with 200;

create sequence SYSPL_MOD_OPT_REF_SEQ
increment by 1
start with 150;

create sequence SYSPL_OPERATE_SEQ
increment by 1
start with 100;

create sequence SYSPL_PAGE_DICTIONARY_SEQ
increment by 1
start with 20;

create sequence SYSPL_PAGE_PATTERN_SEQ
increment by 1
start with 20;

create sequence SYSPL_SCHEDULER_JOB_SEQ
increment by 1
start with 20;

create sequence SYSPL_SYS_OPT_LOG_SEQ
increment by 1
start with 1;

create sequence SYSPL_SYS_PARAMETER_SEQ
increment by 1
start with 20;

create sequence UUM_GROUP_MEMBER_SEQ
increment by 1
start with 1;

create sequence UUM_GROUP_SEQ
increment by 1
start with 1;

create sequence UUM_ORGAN_SEQ
increment by 1
start with 5;

create sequence UUM_ORG_ROLE_REF_SEQ
increment by 1
start with 10;

create sequence UUM_PRIVILEGE_SEQ
increment by 1
start with 1;

create sequence UUM_ROLE_SEQ
increment by 1
start with 5;

create sequence UUM_ROLE_USER_REF_SEQ
increment by 1
start with 10;

create sequence UUM_USER_AGENT_PRIVILEGE_SEQ
increment by 1
start with 1;

create sequence UUM_USER_AGENT_SEQ
increment by 1
start with 1;

create sequence UUM_USER_SEQ
increment by 1
start with 10;

--==============================================================
-- Table: PORTAL
--==============================================================
create table PORTAL
(
   PORTAL_ID            BIGINT                 not null generated by default as identity,
   PORTAL_USERID        BIGINT,
   PORTLET1             VARCHAR(20),
   PORTLET2             VARCHAR(20),
   PORTLET3             VARCHAR(20),
   PORTLET4             VARCHAR(20),
   PORTLET5             VARCHAR(20),
   PORTLET6             VARCHAR(20),
   PORTLET7             VARCHAR(20),
   PORTLET8             VARCHAR(20),
   PORTLET9             VARCHAR(20),
   constraint "P_Key_1" primary key (PORTAL_ID)
);

--==============================================================
-- Table: SYSPL_ACCESSORY
--==============================================================
create table SYSPL_ACCESSORY
(
   ACCESSORY_ID         BIGINT                 not null generated by default as identity,
   INFO_ID              BIGINT,
   ACCESSORY_NAME       VARCHAR(100),
   constraint "P_Key_1" primary key (ACCESSORY_ID)
);

--==============================================================
-- Table: SYSPL_AFFICHE
--==============================================================
create table SYSPL_AFFICHE
(
   AFFICHE_ID           BIGINT                 not null generated by default as identity,
   AFFICHE_TITLE        VARCHAR(100),
   AFFICHE_INVALIDATE   DATE,
   AFFICHE_PULISH       SMALLINT,
   AFFICHE_CONTENT      LONG VARCHAR,
   constraint "P_Key_1" primary key (AFFICHE_ID)
);

comment on table SYSPL_AFFICHE is
'系统公告';

--==============================================================
-- Table: SYSPL_CODE
--==============================================================
create table SYSPL_CODE
(
   CODE_ID              BIGINT                 not null generated by default as identity,
   CODE_ENG_NAME        VARCHAR(20),
   CODE_NAME            VARCHAR(20),
   MODULE_NAME          VARCHAR(20),
   DELIMITE             VARCHAR(2),
   PART_NUM             INTEGER,
   PART1                VARCHAR(20),
   PART1_CON            VARCHAR(20),
   PART2                VARCHAR(20),
   PART2_CON            VARCHAR(20),
   PART3                VARCHAR(20),
   PART3_CON            VARCHAR(20),
   PART4                VARCHAR(20),
   PART4_CON            VARCHAR(20),
   CODE_EFFECT          VARCHAR(50),
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (CODE_ID)
);

--==============================================================
-- Table: SYSPL_DB_OPT_LOG
--==============================================================
create table SYSPL_DB_OPT_LOG
(
   OPT_ID               BIGINT                 not null generated by default as identity,
   OPT_MOD_NAME         VARCHAR(20),
   OPT_NAME             VARCHAR(10),
   OPT_TIME             DATE,
   OPT_PC_NAME          VARCHAR(25),
   OPT_PC_IP            VARCHAR(25),
   OPT_SQL              VARCHAR(100),
   OPT_USER_NAME        VARCHAR(20),
   constraint "P_Key_1" primary key (OPT_ID)
);

--==============================================================
-- Table: SYSPL_DIC_BIG_TYPE
--==============================================================
create table SYSPL_DIC_BIG_TYPE
(
   BIG_TYPE_ID          BIGINT                 not null generated by default as identity,
   BIG_TYPE_NAME        VARCHAR(20),
   BIG_TYPE_CODE        VARCHAR(15),
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (BIG_TYPE_ID)
);

--==============================================================
-- Table: SYSPL_DIC_SMALL_TYPE
--==============================================================
create table SYSPL_DIC_SMALL_TYPE
(
   SMALL_TYPE_ID        BIGINT                 not null generated by default as identity,
   BIG_TYPE_ID          BIGINT,
   SMALL_TYPE_NAME      VARCHAR(20),
   SMALL_TYPE_CODE      VARCHAR(15),
   constraint "P_Key_1" primary key (SMALL_TYPE_ID)
);

--==============================================================
-- Index: BIG_TYPE_ID_INDEX
--==============================================================
create index BIG_TYPE_ID_INDEX on SYSPL_DIC_SMALL_TYPE (
   BIG_TYPE_ID          ASC
);

--==============================================================
-- Table: SYSPL_DISTRICT
--==============================================================
create table SYSPL_DISTRICT
(
   DISTRICT_ID          BIGINT                 not null generated by default as identity,
   DISTRICT_NAME        VARCHAR(20),
   DISTRICT_CODE        VARCHAR(20),
   DISTRICT_POSTAL      VARCHAR(6),
   DISTRICT_TELCODE     VARCHAR(10),
   DISTRICT_LEVEL       VARCHAR(20),
   DIS_PARENT_ID        BIGINT,
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (DISTRICT_ID)
);

--==============================================================
-- Index: DIS_NAME_INDEX
--==============================================================
create index DIS_NAME_INDEX on SYSPL_DISTRICT (
   DISTRICT_NAME        ASC
);

--==============================================================
-- Table: SYSPL_MODULE_MEMU
--==============================================================
create table SYSPL_MODULE_MEMU
(
   MODULE_ID            BIGINT                 not null generated by default as identity,
   MOD_NAME             VARCHAR(30),
   MOD_EN_ID            VARCHAR(40),
   MOD_IMG_CLS          VARCHAR(20),
   MOD_LEVEL            VARCHAR(2),
   MOD_ORDER            INTEGER,
   MOD_PAGE_TYPE        VARCHAR(20),
   BELONG_TO_SYS        VARCHAR(15),
   MOD_PARENT_ID        BIGINT,
   STATUS               STATUS                 not null,
   MEMO                 MEMO,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (MODULE_ID)
);

comment on column SYSPL_MODULE_MEMU.MOD_PAGE_TYPE is
'模块页面类型js html jsp ';

comment on column SYSPL_MODULE_MEMU.BELONG_TO_SYS is
'所属子系统';

--==============================================================
-- Index: MOD_NAME_INDEX
--==============================================================
create index MOD_NAME_INDEX on SYSPL_MODULE_MEMU (
   MOD_NAME             ASC
);

--==============================================================
-- Table: SYSPL_MOD_OPT_REF
--==============================================================
create table SYSPL_MOD_OPT_REF
(
   MOD_OPT_ID           BIGINT                 not null generated by default as identity,
   MODULE_ID            BIGINT,
   OPERATE_ID           BIGINT,
   MOD_OPT_LINK         VARCHAR(20),
   constraint "P_Key_1" primary key (MOD_OPT_ID)
);

--==============================================================
-- Index: MODULE_ID_INDEX
--==============================================================
create index MODULE_ID_INDEX on SYSPL_MOD_OPT_REF (
   MODULE_ID            ASC
);

--==============================================================
-- Table: SYSPL_OPERATE
--==============================================================
create table SYSPL_OPERATE
(
   OPERATE_ID           BIGINT                 not null generated by default as identity,
   OPERATE_NAME         VARCHAR(20),
   OPT_FUN_LINK         VARCHAR(20),
   OPT_IMG_LINK         VARCHAR(20),
   OPT_ORDER            INTEGER,
   OPT_GROUP            INTEGER,
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (OPERATE_ID)
);

--==============================================================
-- Table: SYSPL_SCHEDULER_JOB
--==============================================================
create table SYSPL_SCHEDULER_JOB
(
   JOB_ID               BIGINT                 not null generated by default as identity,
   JOB_NAME             VARCHAR(30),
   JOB_CLASS_DESCRIPT   VARCHAR(100),
   TRIGGER_TYPE         VARCHAR(20),
   TIME_EXPRESS         VARCHAR(100),
   START_TIME           TIMESTAMP,
   END_TIME             TIMESTAMP,
   REPEAT_TIME          INTEGER,
   SPLIT_TIME           BIGINT,
   STATUS               STATUS                 not null,
   MEMO                 MEMO,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (JOB_ID)
);

--==============================================================
-- Table: SYSPL_SYS_OPT_LOG
--==============================================================
create table SYSPL_SYS_OPT_LOG
(
   OPT_ID               BIGINT                 not null generated by default as identity,
   OPT_MOD_NAME         VARCHAR(20),
   OPT_NAME             VARCHAR(20),
   OPT_CONTENT          VARCHAR(1000),
   OPT_BUSINESS_ID      VARCHAR(20),
   OPT_TIME             TIMESTAMP,
   OPT_PC_NAME          VARCHAR(25),
   OPT_PC_IP            VARCHAR(25),
   OPT_USER_NAME        VARCHAR(20),
   OPT_USER_ROLE        VARCHAR(20),
   OPT_USER_OGAN        VARCHAR(20),
   constraint "P_Key_1" primary key (OPT_ID)
);

--==============================================================
-- Index: OPT_TIME_INDEX
--==============================================================
create index OPT_TIME_INDEX on SYSPL_SYS_OPT_LOG (
   OPT_TIME             ASC
);

--==============================================================
-- Index: OPT_MOD_NAME_INDEX
--==============================================================
create index OPT_MOD_NAME_INDEX on SYSPL_SYS_OPT_LOG (
   OPT_MOD_NAME         ASC
);

--==============================================================
-- Index: OPT_UNAME_INDEX
--==============================================================
create index OPT_UNAME_INDEX on SYSPL_SYS_OPT_LOG (
   OPT_USER_NAME        ASC
);

--==============================================================
-- Index: OPT_UROLE_INDEX
--==============================================================
create index OPT_UROLE_INDEX on SYSPL_SYS_OPT_LOG (
   OPT_USER_ROLE        ASC
);

--==============================================================
-- Index: OPT_UOGAN_INDEX
--==============================================================
create index OPT_UOGAN_INDEX on SYSPL_SYS_OPT_LOG (
   OPT_USER_OGAN        ASC
);

--==============================================================
-- Table: SYSPL_SYS_PARAMETER
--==============================================================
create table SYSPL_SYS_PARAMETER
(
   PARA_ID              BIGINT                 not null generated by default as identity,
   PARA_NAME            VARCHAR(20),
   PARA_KEY_NAME        VARCHAR(20),
   PARA_VALUE           VARCHAR(20),
   PARA_TYPE            VARCHAR(20),
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (PARA_ID)
);

comment on column SYSPL_SYS_PARAMETER.PARA_ID is
'主键';

--==============================================================
-- Index: PARA_NAME_INDEX
--==============================================================
create index PARA_NAME_INDEX on SYSPL_SYS_PARAMETER (
   PARA_KEY_NAME        ASC
);

--==============================================================
-- Table: UUM_GROUP
--==============================================================
create table UUM_GROUP
(
   GROUP_ID             BIGINT                 not null generated by default as identity,
   GROUP_TYPE           VARCHAR(15),
   GROUP_NAME           VARCHAR(20),
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (GROUP_ID)
);

--==============================================================
-- Table: UUM_GROUP_MEMBER
--==============================================================
create table UUM_GROUP_MEMBER
(
   GROUP_MEMBER_ID      BIGINT                 not null generated by default as identity,
   GROUP_MEMBER_TYPE    VARCHAR(15),
   MEMBER_RESOURCE_ID   BIGINT,
   GROUP_ID             BIGINT,
   constraint "P_Key_1" primary key (GROUP_MEMBER_ID)
);

comment on column UUM_GROUP_MEMBER.GROUP_MEMBER_ID is
'主键';

comment on column UUM_GROUP_MEMBER.GROUP_MEMBER_TYPE is
'用户角色';

comment on column UUM_GROUP_MEMBER.MEMBER_RESOURCE_ID is
'用户角色ID';

--==============================================================
-- Table: UUM_ORGAN
--==============================================================
create table UUM_ORGAN
(
   ORG_ID               BIGINT                 not null generated by default as identity,
   ORG_SIMPLE_NAME      VARCHAR(10),
   ORG_FULL_NAME        VARCHAR(60),
   ORG_CODE             VARCHAR(10),
   ORG_ADDRESS1         VARCHAR(100),
   ORG_ADDRESS2         VARCHAR(100),
   ORG_TEL1             VARCHAR(25),
   ORG_TEL2             VARCHAR(25),
   ORG_BEGIN_DATE       DATE,
   ORG_TYPE             VARCHAR(20),
   ORG_FAX              VARCHAR(20),
   ORG_POSTAL           VARCHAR(6),
   ORG_LEGAL            VARCHAR(20),
   ORG_TAX_NO           VARCHAR(25),
   ORG_REG_NO           VARCHAR(25),
   ORG_BELONG_DIST      BIGINT,
   ORG_PARENT           BIGINT,
   STATUS               STATUS                 not null,
   MEMO                 MEMO,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (ORG_ID)
);

--==============================================================
-- Index: ORG_NAME_INDEX
--==============================================================
create index ORG_NAME_INDEX on UUM_ORGAN (
   ORG_SIMPLE_NAME      ASC
);

--==============================================================
-- Table: UUM_ORG_ROLE_REF
--==============================================================
create table UUM_ORG_ROLE_REF
(
   ORG_ROLE_ID          BIGINT                 not null generated by default as identity,
   ORG_ID               BIGINT,
   ROLE_ID              BIGINT,
   constraint "P_Key_1" primary key (ORG_ROLE_ID)
);

--==============================================================
-- Table: UUM_PRIVILEGE
--==============================================================
create table UUM_PRIVILEGE
(
   PRIVILEGE_ID         BIGINT                 not null generated by default as identity,
   RESOURCE_ID          VARCHAR(20),
   OWNER_ID             BIGINT,
   OWNER_TYPE           VARCHAR(10),
   PRIVILEGE_SCOPE      VARCHAR(10),
   PRIVILEGE_TYPE       VARCHAR(10),
   constraint "P_Key_1" primary key (PRIVILEGE_ID)
);

comment on column UUM_PRIVILEGE.OWNER_TYPE is
'rol 角色
usr 用户';

comment on column UUM_PRIVILEGE.PRIVILEGE_SCOPE is
'针对操作
inc  包含
exc 不包含
all   全部
针对行
org 机构
rol  角色
usr 用户
';

comment on column UUM_PRIVILEGE.PRIVILEGE_TYPE is
'row  行
opt   操作';

--==============================================================
-- Index: PRI_TYPE_INDEX
--==============================================================
create index PRI_TYPE_INDEX on UUM_PRIVILEGE (
   PRIVILEGE_TYPE       ASC
);

--==============================================================
-- Index: OWNER_TYPE_INDEX
--==============================================================
create index OWNER_TYPE_INDEX on UUM_PRIVILEGE (
   OWNER_TYPE           ASC
);

--==============================================================
-- Table: UUM_ROLE
--==============================================================
create table UUM_ROLE
(
   ROLE_ID              BIGINT                 not null generated by default as identity,
   ROLE_NAME            VARCHAR(20),
   ROLE_LEVEL           SMALLINT,
   STATUS               STATUS                 not null,
   MEMO                 MEMO,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   constraint "P_Key_1" primary key (ROLE_ID)
);

--==============================================================
-- Table: UUM_ROLE_USER_REF
--==============================================================
create table UUM_ROLE_USER_REF
(
   ORG_ROLE_USER_ID     BIGINT                 not null generated by default as identity,
   USER_ID              BIGINT,
   ORG_ROLE_ID          BIGINT,
   IS_DEFAULT           VARCHAR(2),
   constraint "P_Key_1" primary key (ORG_ROLE_USER_ID)
);

--==============================================================
-- Table: UUM_USER
--==============================================================
create table UUM_USER
(
   USER_ID              BIGINT                 not null generated by default as identity,
   USER_CODE            VARCHAR(20),
   USER_NAME            VARCHAR(20),
   USER_PASSWORD        VARCHAR(20),
   USER_GENDER          VARCHAR(2),
   USER_POSITION        VARCHAR(30),
   USER_PHOTO_URL       VARCHAR(200),
   USER_QQ              VARCHAR(20),
   USER_MSN             VARCHAR(20),
   USER_MOBILE          VARCHAR(20),
   USER_MOBILE2         VARCHAR(20),
   USER_OFFICE_TEL      VARCHAR(20),
   USER_ADDRESS         VARCHAR(100),
   USER_FAMILY_TEL      VARCHAR(20),
   USER_EMAIL           VARCHAR(30),
   USER_AVIDATE         DATE,
   USER_IS_AGENT        VARCHAR(2),
   USER_BELONGTO_ORG    BIGINT,
   MEMO                 MEMO,
   STATUS               STATUS                 not null,
   CREATOR              CREATOR,
   CREATE_DATE          CREATE_TIME,
   USER_NAME_PY         VARCHAR(20),
   constraint "P_Key_1" primary key (USER_ID)
);

--==============================================================
-- Table: UUM_USER_AGENT
--==============================================================
create table UUM_USER_AGENT
(
   AGENT_ID             BIGINT                 not null,
   USER_ID              BIGINT,
   AGENT_USER_ID        BIGINT,
   ORG_ROLE_ID          BIGINT,
   BEGIN_DATE           DATE,
   END_DATE             DATE,
   REASON               VARCHAR(200),
   constraint "P_Key_1" primary key (AGENT_ID)
);

--==============================================================
-- Table: UUM_USER_AGENT_PRIVILEGE
--==============================================================
create table UUM_USER_AGENT_PRIVILEGE
(
   USER_AGENT_PRI_ID    BIGINT                 not null,
   AGENT_ID             BIGINT,
   PRIVILEGE_ID         VARCHAR(20),
   PRIVILEGE_TYPE       VARCHAR(10),
   constraint "P_Key_1" primary key (USER_AGENT_PRI_ID)
);

--==============================================================
-- Table: UUM_USER_COMMFUN
--==============================================================
create table UUM_USER_COMMFUN
(
   USER_ID              BIGINT                 not null,
   MODULE_ID            BIGINT                 not null,
   constraint "P_Key_2" primary key (USER_ID, MODULE_ID)
);

--==============================================================
-- Table: UUM_USER_PAGE_LAYOUT
--==============================================================
create table UUM_USER_PAGE_LAYOUT
(
   USER_ID              BIGINT                 not null,
   HEADER               VARCHAR(15),
   NAVIGATE             VARCHAR(15),
   OUTLOOKBAR           VARCHAR(15),
   MAINPAGE             VARCHAR(15),
   STATEBAR             VARCHAR(15),
   constraint "P_Key_1" primary key (USER_ID)
);

delete from syspl_district where district_id = 0;
delete from uum_role_user_ref where org_role_user_id = 1;
delete from uum_org_role_ref where org_role_id in(1,2);
delete from uum_user where user_id = 1;
delete from uum_role where role_id in(0,1);
delete from uum_organ where org_id =0;

/*初始化总部机构*/
INSERT INTO uum_organ(
            org_id, org_simple_name,                         
            org_parent, status)
    VALUES (0, '总部', 0, 'enable');
/*无角色用户 角色*/
INSERT INTO uum_role(
            role_id, role_name, status, memo, creator)
    VALUES (0, '无角色用户', 'enable', '', 'admin');


    
/*管理员角色 角色*/
INSERT INTO uum_role(
            role_id, role_name, status, memo, creator,role_level,create_date)
    VALUES (1, '管理员', 'enable', '', 'admin',1,current timestamp); 
    
/*机构角色关系表 无角色*/
INSERT INTO uum_org_role_ref(
            org_role_id, org_id, role_id)
    VALUES (1, 0, 0);
/*管理员机构角色关系表*/
INSERT INTO uum_org_role_ref(
            org_role_id, org_id, role_id)
    VALUES (2, 0, 1);
/*管理员用户*/
INSERT INTO uum_user(
            user_id, user_code, user_name, user_password, 
            user_belongto_org, status, creator,create_date)
    VALUES (1, 'admin', '管理员', 'UlFQV1ZV',
            0, 'enable', 'admin',current timestamp);
/*管理员角色用户*/
INSERT INTO uum_role_user_ref(
            org_role_user_id, user_id, org_role_id, is_default)
    VALUES (1, 1, 2, 'Y');

/*初始化地区*/
INSERT INTO syspl_district(
            district_id, district_name, district_code,dis_parent_id,status)
    VALUES (0, '地区树', '0', 0, 'enable');

delete from bhtec.syspl_module_memu;
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (0,'模块树','moduletree','modtree','0',1,1,'enable','','admin',current timestamp,'','');

INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (48,'角色组管理','roleGroupMgr','rolegroupmgr','3',2,23,'enable','','3',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (50,'工作流','workflowId','workflow','1',3,0,'enable','','admin',current timestamp,'','');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (60,'测试','test','','2',1,50,'enable','','admin',current timestamp,'','');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (52,'省市地区','priCityMgr','priCityMgrTitle','2',3,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (12,'系统管理','systemMgr','systemMgr','2',1,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (9,'字典管理','dicmgr','dicMgr','2',4,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (6,'模块操作管理','modoptmgr','moduleOp','2',3,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (3,'主页框架管理','mainframemgr','frameMgr','2',2,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (23,'群组管理','groupmgr','groupmgr','2',4,2,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (22,'用户管理','usermgr','usermgrtitle','2',3,2,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (61,'待办任务','underwayTaskId','','3',1,60,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (2,'统一用户','uusmgr','uus','1',2,0,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (1,'系统平台','pltmgr','platform','1',1,0,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (21,'角色管理','rolemgr','rolemgrtitle','2',2,2,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (19,'机构管理','organmgr','organmgrtitle','2',1,2,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (35,'角色分配管理','roleOrganMgr','roleassignmgr','3',1,21,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (34,'用户信息管理','userMgr','usermgr','3',1,22,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (33,'角色信息管理','roleMgr','rolemgr','3',1,21,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (32,'机构信息管理','organMgr','organmgr','3',1,19,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (28,'普通组管理','generalGroupMgr','gernalgroupmgr','3',3,23,'disable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (27,'用户组管理','userGroupMgr','usergroupmgr','3',1,23,'enable','','admin',current timestamp,'uum','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (17,'日志管理','logMgr','logMgr','2',5,1,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (18,'系统日志管理','sysLogMgr','sysLogMgr','3',1,17,'enable','','admin',current timestamp,'platform','jsp');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (7,'模块菜单管理','moduleMgr','moduleMgr','3',1,6,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (4,'页面功能区管理','mainFrameFunMgr','pageFun','3',1,3,'enable','','admin',current timestamp,'platform','jsp');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (53,'系统调度管理','schedulerMgr','schedulerMgr','3',5,12,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (49,'系统公告管理','afficheMgr','afficheMgr','3',4,12,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (25,'系统配置管理','sysConMgr','sysConMgr','3',1,12,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (15,'系统参数管理','systemParaMgr','systemParaMgr','3',3,12,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (13,'系统编码管理','codeMgr','codMgr','3',2,12,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (14,'省市地区管理','priCityMgr','priCityMgr','3',1,52,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (8,'操作按钮管理','operateMgr','oprateMgr','3',2,6,'enable','','admin',current timestamp,'platform','js');
INSERT INTO bhtec.syspl_module_memu(module_id, mod_name, mod_en_id, mod_img_cls, mod_level, mod_order,mod_parent_id, status, memo, creator, create_date, belong_to_sys, mod_page_type) VALUES (10,'类别字典管理','typeDicMgr','typeDicMgr','3',1,9,'enable','','admin',current timestamp,'platform','js');


INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (5,'启用','enable',5,1,'','enable','admin',current timestamp,'enable');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (6,'停用','disable',6,1,'停用','enable','admin',current timestamp,'disable');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (2,'修改','table_edit',2,1,'','enable','admin',current timestamp,'modify');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (4,'查看','table',4,1,'','enable','admin',current timestamp,'view');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (7,'模块分配操作','assign',7,1,'','enable','admin',current timestamp,'optassign');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (8,'模块操作权限','assign',8,1,'','enable','admin',current timestamp,'optpri');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (9,'角色分配','assign',6,1,'','enable','admin',current timestamp,'roleassign');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (10,'角色删除','table_delete',7,1,'','enable','admin',current timestamp,'roledel');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (11,'分配角色','rolemgr',6,1,'','enable','admin',current timestamp,'assignrole');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (1,'增加','table_add',1,1,'增加','enable','admin',current timestamp,'add');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (13,'重置密码','resetpwd',7,1,'重置密码','enable','admin',current timestamp,'resetpwd');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (12,'行权限','privialmgr',8,1,'','enable','admin',current timestamp,'rowprivilege');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (3,'删除','table_delete',3,1,'','enable','admin',current timestamp,'delete');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (14,'模块标签','module_label',7,1,'','enable','admin',current timestamp,'moduleLabel');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (15,'保留时间','save_time',5,1,'','enable','admin',current timestamp,'saveTime');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (16,'启动调度器','scheduler_start',5,1,'','enable','admin',current timestamp,'schedulerStart');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (17,'停止调度器','scheduler_stop',6,1,'','enable','admin',current timestamp,'schedulerStop');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (18,'启动任务','job_start',7,1,'','enable','admin',current timestamp,'jobStart');
INSERT INTO syspl_operate(
            operate_id, operate_name, opt_img_link, opt_order, opt_group, 
            memo, status, creator, create_date, opt_fun_link)
    VALUES (19,'停止任务','job_stop',8,1,'','enable','admin',current timestamp,'jobStop');


INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (1,4,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (2,4,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (3,4,3);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (4,4,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (5,7,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (6,7,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (7,7,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (112,48,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (113,48,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (10,8,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (11,8,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (12,8,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (13,8,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (14,8,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (15,10,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (16,10,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (17,10,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (18,10,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (19,10,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (114,48,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (115,48,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (116,28,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (117,28,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (118,28,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (25,13,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (26,13,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (27,13,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (28,13,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (29,13,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (30,14,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (31,14,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (119,28,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (33,14,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (34,14,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (35,14,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (36,15,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (37,15,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (38,15,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (39,15,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (40,15,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (44,18,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (45,32,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (46,32,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (47,32,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (48,32,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (49,32,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (50,33,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (51,33,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (52,33,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (53,33,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (54,33,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (55,33,8);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (56,35,9);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (57,35,10);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (58,34,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (59,34,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (60,34,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (61,34,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (62,34,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (64,34,8);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (65,27,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (66,27,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (68,27,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (69,27,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (87,7,7);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (89,7,5);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (90,7,6);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (91,34,11);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (92,7,12);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (97,34,13);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (98,49,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (99,49,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (100,49,3);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (101,49,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (102,7,14);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (104,53,1);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (105,53,2);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (106,53,3);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (107,53,4);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (108,53,16);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (109,53,17);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (110,53,18);
INSERT INTO syspl_mod_opt_ref(mod_opt_id, module_id, operate_id)VALUES (111,53,19);


INSERT INTO syspl_scheduler_job(job_id, job_name, job_class_descript, trigger_type, time_express,start_time, end_time, repeat_time, split_time, status, memo, creator, create_date)VALUES (1,'logJob','com.bhtec.service.impl.platform.job.LoggerJob','Cron','* * 0 15 * ?',current timestamp,current timestamp,2,2,'disable','每月15日0点清除日志','admin',current timestamp);
INSERT INTO syspl_scheduler_job(job_id, job_name, job_class_descript, trigger_type, time_express,start_time, end_time, repeat_time, split_time, status, memo, creator, create_date)VALUES (3,'postgresqlBbBackup','com.bhtec.service.impl.platform.job.PostgresqlDbBackupJob','Cron','0/10 * * * * ?',current timestamp,current timestamp,2,2,'enable','每天0点备份','admin',current timestamp);


INSERT INTO syspl_sys_parameter(para_id, para_name, para_key_name, para_value, para_type, memo,status, creator, create_date)VALUES (1,'用户默认密码','userdefaultpassword','123456','uus','','enable','admin',current timestamp);
INSERT INTO syspl_sys_parameter(para_id, para_name, para_key_name, para_value, para_type, memo,status, creator, create_date)VALUES (2,'用户有效期','uservalidate','6','uus','','enable','admin',current timestamp);


INSERT INTO bhtec.syspl_dic_big_type(big_type_id, big_type_name, big_type_code, memo, status, creator,create_date) VALUES (25,'页面类型','modPageType','','enable','admin',current timestamp);
INSERT INTO bhtec.syspl_dic_big_type(big_type_id, big_type_name, big_type_code, memo, status, creator,create_date) VALUES (21,'系统类别','systemType','','enable','admin',current timestamp);
INSERT INTO bhtec.syspl_dic_big_type(big_type_id, big_type_name, big_type_code, memo, status, creator,create_date) VALUES (23,'机构类型','organType','','enable','admin',current timestamp);
INSERT INTO bhtec.syspl_dic_big_type(big_type_id, big_type_name, big_type_code, memo, status, creator,create_date) VALUES (22,'地区级别','district','','enable','admin',current timestamp);

INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (26,22,'省','province');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (27,22,'县','town');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (28,22,'区','district');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (29,22,'市','city');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (43,25,'js','js');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (44,25,'html','html');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (45,25,'flex','flex');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (46,25,'jsp','jsp');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (37,21,'系统平台','platform');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (38,21,'统一用户','uum');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (39,23,'县','town');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (40,23,'总部','HQ');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (41,23,'省','province');
INSERT INTO bhtec.syspl_dic_small_type(small_type_id, big_type_id, small_type_name, small_type_code) VALUES (42,23,'市','city');
