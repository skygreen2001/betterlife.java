/*
Navicat PGSQL Data Transfer

Source Server         : localhost
Source Server Version : 90405
Source Host           : localhost:5432
Source Database       : betterlife
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90405
File Encoding         : 65001

Date: 2016-10-18 11:25:02
*/


-- ----------------------------
-- Sequence structure for bb_log_loginlog_loginlog_id_seq
-- ----------------------------
DROP SEQUENCE "public"."bb_log_loginlog_loginlog_id_seq";
CREATE SEQUENCE "public"."bb_log_loginlog_loginlog_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 84
 CACHE 1;
SELECT setval('"public"."bb_log_loginlog_loginlog_id_seq"', 84, true);

-- ----------------------------
-- Sequence structure for bb_user_user_user_id_seq
-- ----------------------------
DROP SEQUENCE "public"."bb_user_user_user_id_seq";
CREATE SEQUENCE "public"."bb_user_user_user_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."bb_user_user_user_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for contacts_id_seq
-- ----------------------------
DROP SEQUENCE "public"."contacts_id_seq";
CREATE SEQUENCE "public"."contacts_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 12
 CACHE 1;
SELECT setval('"public"."contacts_id_seq"', 12, true);

-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
DROP SEQUENCE "public"."hibernate_sequence";
CREATE SEQUENCE "public"."hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 11
 CACHE 1;
SELECT setval('"public"."hibernate_sequence"', 11, true);

-- ----------------------------
-- Sequence structure for seq_bb_core_blog
-- ----------------------------
DROP SEQUENCE "public"."seq_bb_core_blog";
CREATE SEQUENCE "public"."seq_bb_core_blog"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 11
 CACHE 1;
SELECT setval('"public"."seq_bb_core_blog"', 11, true);

-- ----------------------------
-- Sequence structure for seq_bb_core_comment
-- ----------------------------
DROP SEQUENCE "public"."seq_bb_core_comment";
CREATE SEQUENCE "public"."seq_bb_core_comment"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 14
 CACHE 1;
SELECT setval('"public"."seq_bb_core_comment"', 14, true);

-- ----------------------------
-- Sequence structure for seq_bb_user_user
-- ----------------------------
DROP SEQUENCE "public"."seq_bb_user_user";
CREATE SEQUENCE "public"."seq_bb_user_user"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 62
 CACHE 1;
SELECT setval('"public"."seq_bb_user_user"', 62, true);

-- ----------------------------
-- Table structure for bb_core_blog
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_core_blog";
CREATE TABLE "public"."bb_core_blog" (
"id" int8 DEFAULT nextval('seq_bb_core_blog'::regclass) NOT NULL,
"userid" int8,
"name" varchar(255) COLLATE "default",
"content" varchar(255) COLLATE "default",
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_core_blog
-- ----------------------------
INSERT INTO "public"."bb_core_blog" VALUES ('1', '2', 'test', 'test', '2010-07-30 09:59:29');
INSERT INTO "public"."bb_core_blog" VALUES ('2', '2', 'test', 'test', '2010-07-30 09:59:48');
INSERT INTO "public"."bb_core_blog" VALUES ('3', '2', 'test', 'testaaa', '2010-07-30 10:03:44');
INSERT INTO "public"."bb_core_blog" VALUES ('4', '1', 'snooy', 'snooy', '2010-09-10 14:00:54');
INSERT INTO "public"."bb_core_blog" VALUES ('5', '1', 'item', 'bug', '2010-09-10 14:07:31');
INSERT INTO "public"."bb_core_blog" VALUES ('6', '1', 'why', 'question', '2010-09-10 16:08:34');
INSERT INTO "public"."bb_core_blog" VALUES ('7', '1', 'fine', 'good', '2010-09-13 09:10:28');
INSERT INTO "public"."bb_core_blog" VALUES ('8', '1', 'y', 's', '2010-09-13 15:55:01');
INSERT INTO "public"."bb_core_blog" VALUES ('9', '1', 'a', 'j', '2010-09-13 15:55:27');
INSERT INTO "public"."bb_core_blog" VALUES ('10', '1', '很好', '就是这样那个的', '2010-11-09 14:39:35');
INSERT INTO "public"."bb_core_blog" VALUES ('11', '1', '未按时发', '撒旦发送到', '2010-11-09 15:59:21');

-- ----------------------------
-- Table structure for bb_core_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_core_comment";
CREATE TABLE "public"."bb_core_comment" (
"id" int8 DEFAULT nextval('seq_bb_core_comment'::regclass) NOT NULL,
"userid" int8,
"comment" varchar(255) COLLATE "default",
"blogid" int8,
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_core_comment
-- ----------------------------
INSERT INTO "public"."bb_core_comment" VALUES ('1', '2', 'asdasd', '3', '2010-08-27 22:58:58');
INSERT INTO "public"."bb_core_comment" VALUES ('2', '2', 'Nobody', '3', '2010-09-04 11:32:51');
INSERT INTO "public"."bb_core_comment" VALUES ('3', '2', 'Fine', '3', '2010-09-04 11:38:28');
INSERT INTO "public"."bb_core_comment" VALUES ('4', '2', 'super', '3', '2010-09-04 11:40:34');
INSERT INTO "public"."bb_core_comment" VALUES ('5', '2', 'winter', '3', '2010-09-04 11:43:59');
INSERT INTO "public"."bb_core_comment" VALUES ('6', '2', 'autumn', '3', '2010-09-04 11:52:38');
INSERT INTO "public"."bb_core_comment" VALUES ('7', '2', 'back home', '3', '2010-09-04 11:55:14');
INSERT INTO "public"."bb_core_comment" VALUES ('8', '2', 'summer', '3', '2010-09-04 11:57:24');
INSERT INTO "public"."bb_core_comment" VALUES ('9', '2', 'spring', '3', '2010-09-04 11:58:10');
INSERT INTO "public"."bb_core_comment" VALUES ('10', '2', 'season', '3', '2010-09-04 11:58:54');
INSERT INTO "public"."bb_core_comment" VALUES ('12', '1', '不急不急，休息一下', '4', '2010-09-10 14:02:25');
INSERT INTO "public"."bb_core_comment" VALUES ('13', '1', '可以开始了', '4', '2010-09-10 14:28:23');
INSERT INTO "public"."bb_core_comment" VALUES ('14', '1', '风云再起时', '6', '2010-09-10 16:08:53');

-- ----------------------------
-- Table structure for bb_core_contact
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_core_contact";
CREATE TABLE "public"."bb_core_contact" (
"contact_id" int4 DEFAULT nextval('contacts_id_seq'::regclass) NOT NULL,
"name" varchar(100) COLLATE "default",
"email" varchar(1000) COLLATE "default",
"phones" varchar(255) COLLATE "default",
"mobile" varchar(255) COLLATE "default",
"phone" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bb_core_contact"."contact_id" IS '编号';
COMMENT ON COLUMN "public"."bb_core_contact"."name" IS '联系人名称';
COMMENT ON COLUMN "public"."bb_core_contact"."email" IS '邮件';
COMMENT ON COLUMN "public"."bb_core_contact"."phones" IS '联系电话';

-- ----------------------------
-- Records of bb_core_contact
-- ----------------------------
INSERT INTO "public"."bb_core_contact" VALUES ('1', 'John Doe', 'sina@sina.com', '{62231234,13612341234}', '13612341234', '62231234');
INSERT INTO "public"."bb_core_contact" VALUES ('2', 'Lily Bush', 'sohu@sohu.com', '{68131234,13853123467}', '13853123467', '68131234');
INSERT INTO "public"."bb_core_contact" VALUES ('3', 'William Gate', 'qq@qq.com', '{67232123,13712434567}', '13712434567', '67232123');
INSERT INTO "public"."bb_core_contact" VALUES ('4', 'hhh', '777', '{555,hhh}', 'hhh', '555');
INSERT INTO "public"."bb_core_contact" VALUES ('8', 'ppp', '64134512', '{13617172828,ppp}', 'ppp', '13617172828');
INSERT INTO "public"."bb_core_contact" VALUES ('9', 'ttt', '777888', '{777888,ttt}', 'ttt', '777888');
INSERT INTO "public"."bb_core_contact" VALUES ('11', 'ccc', 'sinagt@163.com', '{63123456,13912345434}', '13912345434', '63123456');
INSERT INTO "public"."bb_core_contact" VALUES ('12', 'ddd', '666', '{555,ddd}', 'ddd', '555');

-- ----------------------------
-- Table structure for bb_log_loginlog
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_log_loginlog";
CREATE TABLE "public"."bb_log_loginlog" (
"loginlog_id" int4 DEFAULT nextval('bb_log_loginlog_loginlog_id_seq'::regclass) NOT NULL,
"user_id" int4,
"ip" varchar(255) COLLATE "default",
"login_date" date
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bb_log_loginlog"."loginlog_id" IS '编号';
COMMENT ON COLUMN "public"."bb_log_loginlog"."user_id" IS '用户访问编号';
COMMENT ON COLUMN "public"."bb_log_loginlog"."login_date" IS '登录日期';

-- ----------------------------
-- Records of bb_log_loginlog
-- ----------------------------
INSERT INTO "public"."bb_log_loginlog" VALUES ('1', '1', '127.0.0.1', '2015-11-02');
INSERT INTO "public"."bb_log_loginlog" VALUES ('2', '1', '127.0.0.1', '2015-11-02');
INSERT INTO "public"."bb_log_loginlog" VALUES ('3', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('4', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('5', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('6', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('7', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('8', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('9', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('10', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('11', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('12', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('13', '1', '127.0.0.1', '2015-11-03');
INSERT INTO "public"."bb_log_loginlog" VALUES ('14', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('15', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('16', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('17', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('18', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('19', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('20', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('21', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('22', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('23', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('24', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('25', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('26', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('27', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('28', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('29', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('30', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('31', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('32', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('33', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('34', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('35', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('36', '2', '127.0.0.1', '2015-11-06');
INSERT INTO "public"."bb_log_loginlog" VALUES ('37', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('38', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('39', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('40', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('41', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('42', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('43', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('44', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('45', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('46', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('47', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('48', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('49', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('50', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('51', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('52', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('53', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('54', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('55', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('56', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('57', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('58', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('59', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('60', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('61', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('62', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('63', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('64', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('65', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('66', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('67', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('68', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('69', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('70', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('71', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('72', '2', '127.0.0.1', '2015-11-07');
INSERT INTO "public"."bb_log_loginlog" VALUES ('73', '2', '127.0.0.1', '2015-11-09');
INSERT INTO "public"."bb_log_loginlog" VALUES ('74', '2', '127.0.0.1', '2015-11-09');
INSERT INTO "public"."bb_log_loginlog" VALUES ('75', '2', '127.0.0.1', '2015-11-09');
INSERT INTO "public"."bb_log_loginlog" VALUES ('76', '2', '127.0.0.1', '2015-11-09');
INSERT INTO "public"."bb_log_loginlog" VALUES ('77', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('78', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('79', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('80', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('81', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('82', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('83', '2', '127.0.0.1', '2015-11-10');
INSERT INTO "public"."bb_log_loginlog" VALUES ('84', '2', '127.0.0.1', '2015-11-10');

-- ----------------------------
-- Table structure for bb_log_loguser
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_log_loguser";
CREATE TABLE "public"."bb_log_loguser" (
"id" int8 NOT NULL,
"userid" int8,
"type" varchar(3) COLLATE "default",
"content" varchar(255) COLLATE "default",
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_log_loguser
-- ----------------------------

-- ----------------------------
-- Table structure for bb_msg_msg
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_msg_msg";
CREATE TABLE "public"."bb_msg_msg" (
"id" int8 NOT NULL,
"senderid" int8,
"receiverid" int8,
"sendername" varchar(255) COLLATE "default",
"receivername" varchar(255) COLLATE "default",
"content" varchar(255) COLLATE "default",
"state" varchar(3) COLLATE "default",
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_msg_msg
-- ----------------------------

-- ----------------------------
-- Table structure for bb_msg_notice
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_msg_notice";
CREATE TABLE "public"."bb_msg_notice" (
"id" int8 NOT NULL,
"senderid" int8,
"title" varchar(255) COLLATE "default",
"content" varchar(255) COLLATE "default",
"endtime" timestamp(6),
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_msg_notice
-- ----------------------------

-- ----------------------------
-- Table structure for bb_msg_re_usernotice
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_msg_re_usernotice";
CREATE TABLE "public"."bb_msg_re_usernotice" (
"id" int8 NOT NULL,
"userid" int8,
"noticeid" int8,
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_msg_re_usernotice
-- ----------------------------

-- ----------------------------
-- Table structure for bb_user_department
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_department";
CREATE TABLE "public"."bb_user_department" (
"id" int8 NOT NULL,
"name" varchar(150) COLLATE "default",
"manager" varchar(255) COLLATE "default",
"costcenter" int8,
"businessunit" varchar(150) COLLATE "default",
"hrrep" varchar(255) COLLATE "default",
"locationstreet" varchar(150) COLLATE "default",
"locationcity" varchar(60) COLLATE "default",
"locationstate" varchar(6) COLLATE "default",
"locationzipcode" varchar(30) COLLATE "default",
"budget" int8,
"actualexpenses" int8,
"estsalary" int8,
"actualsalary" int8,
"esttravel" int8,
"actualtravel" int8,
"estsupplies" int8,
"actualsupplies" int8,
"estcontractors" int8,
"actualcontractors" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_department
-- ----------------------------
INSERT INTO "public"."bb_user_department" VALUES ('1', 'User Experience', 'Big Boss', '11234', 'Core Services', 'Sigmund Freud', '601 Townsend St.', 'San Francisco', 'CA', '94103', '395000', '412000', '375000', '375000', '10000', '15000', '10000', '12000', '0', '10000');
INSERT INTO "public"."bb_user_department" VALUES ('2', 'Engineering', 'Bill Lumburg', '34523', 'Research and Development', 'Jane Doe', '345 Park Ave', 'San Jose', 'CA', '95110', '434000', '436000', '410000', '415000', '12000', '10000', '12000', '11000', '0', '0');
INSERT INTO "public"."bb_user_department" VALUES ('3', 'Space Exploration', 'Zaphod Beeblebrox', '11456', 'Research and Development', 'Jane Doe', '345 Park Ave', 'San Jose', 'CA', '95110', '1625000', '1823000', '500000', '500000', '25000', '23000', '1100000', '1300000', '0', '0');
INSERT INTO "public"."bb_user_department" VALUES ('4', 'Corporate', 'Bruce Chizen', '11111', 'None', 'Sigmund Freud', '345 Park Ave', 'San Jose', 'CA', '95110', '660000', '705000', '500000', '500000', '100000', '120000', '20000', '30000', '40000', '55000');
INSERT INTO "public"."bb_user_department" VALUES ('5', 'Advanced Physics Research', 'Albert Einstein', '66555', 'Research and Development', 'Jane Doe', '345 Park Ave', 'San Jose', 'CA', '95110', '440000', '444000', '410000', '410000', '15000', '17000', '15000', '17000', '0', '0');
INSERT INTO "public"."bb_user_department" VALUES ('6', 'Food Services', 'Bob Dole', '85225', 'Core Services', 'Jane Doe', '345 Park Ave', 'San Jose', 'CA', '95110', '115000', '113000', '50000', '40000', '0', '0', '50000', '48000', '15000', '25000');
INSERT INTO "public"."bb_user_department" VALUES ('7', 'Product Marketing', 'Willy Loman', '55301', 'Corporate Marketing', 'Sigmund Freud', '601 Townsend St.', 'San Francisco', 'CA', '94103', '445000', '484000', '375000', '400000', '30000', '32000', '20000', '22000', '20000', '30000');

-- ----------------------------
-- Table structure for bb_user_function
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_function";
CREATE TABLE "public"."bb_user_function" (
"id" int8 NOT NULL,
"url" varchar(255) COLLATE "default",
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_function
-- ----------------------------

-- ----------------------------
-- Table structure for bb_user_re_rolefunction
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_re_rolefunction";
CREATE TABLE "public"."bb_user_re_rolefunction" (
"id" int8 NOT NULL,
"roleid" int8,
"functionid" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_re_rolefunction
-- ----------------------------

-- ----------------------------
-- Table structure for bb_user_re_userrole
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_re_userrole";
CREATE TABLE "public"."bb_user_re_userrole" (
"id" int8 NOT NULL,
"roleid" int8,
"userid" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_re_userrole
-- ----------------------------
INSERT INTO "public"."bb_user_re_userrole" VALUES ('1', '1', '2');
INSERT INTO "public"."bb_user_re_userrole" VALUES ('2', '2', '3');
INSERT INTO "public"."bb_user_re_userrole" VALUES ('3', '2', '4');
INSERT INTO "public"."bb_user_re_userrole" VALUES ('4', '3', '5');
INSERT INTO "public"."bb_user_re_userrole" VALUES ('5', '2', '6');
INSERT INTO "public"."bb_user_re_userrole" VALUES ('6', '2', '2');

-- ----------------------------
-- Table structure for bb_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_role";
CREATE TABLE "public"."bb_user_role" (
"id" int8 NOT NULL,
"name" varchar(255) COLLATE "default",
"committime" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_role
-- ----------------------------
INSERT INTO "public"."bb_user_role" VALUES ('1', '主公', '2010-09-05 15:27:30');
INSERT INTO "public"."bb_user_role" VALUES ('2', '忠臣', '2010-09-05 15:27:48');
INSERT INTO "public"."bb_user_role" VALUES ('3', '奸贼', '2010-09-05 15:27:59');
INSERT INTO "public"."bb_user_role" VALUES ('4', '侠士', '2010-09-05 15:28:37');
INSERT INTO "public"."bb_user_role" VALUES ('5', '内奸', '2010-09-05 15:28:20');

-- ----------------------------
-- Table structure for bb_user_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_user";
CREATE TABLE "public"."bb_user_user" (
"user_id" int4 DEFAULT nextval('bb_user_user_user_id_seq'::regclass) NOT NULL,
"username" varchar(255) COLLATE "default",
"password" varchar(255) COLLATE "default",
"lastip" varchar(255) COLLATE "default",
"lastvisit" date
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bb_user_user"."user_id" IS '编号';
COMMENT ON COLUMN "public"."bb_user_user"."username" IS '名称';
COMMENT ON COLUMN "public"."bb_user_user"."password" IS '密码';
COMMENT ON COLUMN "public"."bb_user_user"."lastip" IS '最新访问IP';
COMMENT ON COLUMN "public"."bb_user_user"."lastvisit" IS '最新访问时间';

-- ----------------------------
-- Records of bb_user_user
-- ----------------------------
INSERT INTO "public"."bb_user_user" VALUES ('1', 'admin', 'admin', '127.0.0.1', '2015-11-03');

-- ----------------------------
-- Table structure for bb_user_userdetail
-- ----------------------------
DROP TABLE IF EXISTS "public"."bb_user_userdetail";
CREATE TABLE "public"."bb_user_userdetail" (
"id" int8 NOT NULL,
"userid" int8,
"departmentid" int8,
"email" varchar(255) COLLATE "default",
"cellphone" varchar(255) COLLATE "default",
"committime" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bb_user_userdetail
-- ----------------------------
INSERT INTO "public"."bb_user_userdetail" VALUES ('1', '2', null, null, '13333333333', null);

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "public"."bb_log_loginlog_loginlog_id_seq" OWNED BY "bb_log_loginlog"."loginlog_id";
ALTER SEQUENCE "public"."bb_user_user_user_id_seq" OWNED BY "bb_user_user"."user_id";
ALTER SEQUENCE "public"."contacts_id_seq" OWNED BY "bb_core_contact"."contact_id";

-- ----------------------------
-- Primary Key structure for table bb_core_blog
-- ----------------------------
ALTER TABLE "public"."bb_core_blog" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_core_comment
-- ----------------------------
ALTER TABLE "public"."bb_core_comment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_core_contact
-- ----------------------------
ALTER TABLE "public"."bb_core_contact" ADD PRIMARY KEY ("contact_id");

-- ----------------------------
-- Indexes structure for table bb_log_loginlog
-- ----------------------------
CREATE INDEX "loginlog_ids" ON "public"."bb_log_loginlog" USING btree (loginlog_id);

-- ----------------------------
-- Uniques structure for table bb_log_loginlog
-- ----------------------------
ALTER TABLE "public"."bb_log_loginlog" ADD UNIQUE ("loginlog_id");

-- ----------------------------
-- Primary Key structure for table bb_log_loguser
-- ----------------------------
ALTER TABLE "public"."bb_log_loguser" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_msg_msg
-- ----------------------------
ALTER TABLE "public"."bb_msg_msg" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_msg_notice
-- ----------------------------
ALTER TABLE "public"."bb_msg_notice" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_msg_re_usernotice
-- ----------------------------
ALTER TABLE "public"."bb_msg_re_usernotice" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_user_department
-- ----------------------------
ALTER TABLE "public"."bb_user_department" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_user_function
-- ----------------------------
ALTER TABLE "public"."bb_user_function" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_user_re_rolefunction
-- ----------------------------
ALTER TABLE "public"."bb_user_re_rolefunction" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_user_re_userrole
-- ----------------------------
ALTER TABLE "public"."bb_user_re_userrole" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bb_user_role
-- ----------------------------
ALTER TABLE "public"."bb_user_role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table bb_user_user
-- ----------------------------
CREATE INDEX "user_ids" ON "public"."bb_user_user" USING btree (user_id);

-- ----------------------------
-- Uniques structure for table bb_user_user
-- ----------------------------
ALTER TABLE "public"."bb_user_user" ADD UNIQUE ("user_id");

-- ----------------------------
-- Primary Key structure for table bb_user_userdetail
-- ----------------------------
ALTER TABLE "public"."bb_user_userdetail" ADD PRIMARY KEY ("id");
