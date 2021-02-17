/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : info

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2021-02-17 23:46:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `account_type` int(11) NOT NULL DEFAULT '1' COMMENT '账户类型,1:主账户,2:收款账户,3:付款账户',
  `balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '可用余额',
  `freeze_balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结余额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号，账户余额改变时每次加1(不包括冻结约与可用余额的相互调整)',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_code` (`account_code`,`status`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Table structure for account_inter
-- ----------------------------
DROP TABLE IF EXISTS `account_inter`;
CREATE TABLE `account_inter` (
  `id` bigint(20) NOT NULL,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `account_type` int(11) NOT NULL COMMENT '账户类型,0:备付金,3:收款账户,4:付款账户',
  `balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '可用余额',
  `freeze_balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结余额',
  `user_id` varchar(32) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号，账户余额改变时每次加1(不包括冻结约与可用余额的相互调整)',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_status` (`account_code`,`status`),
  KEY `type_status` (`account_type`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内部账户';

-- ----------------------------
-- Table structure for account_lock
-- ----------------------------
DROP TABLE IF EXISTS `account_lock`;
CREATE TABLE `account_lock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `locked` int(11) NOT NULL DEFAULT '0' COMMENT '状态: 1-正常,0-锁定中',
  `unlock_time` datetime(3) DEFAULT NULL COMMENT '先存预计解锁时间，再改为实际解锁时间',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '先存预计解锁时间，再改为实际解锁时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_code` (`account_code`),
  KEY `account_lock` (`account_code`,`locked`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2925 DEFAULT CHARSET=utf8 COMMENT='账户锁表';

-- ----------------------------
-- Table structure for account_rule_collect
-- ----------------------------
DROP TABLE IF EXISTS `account_rule_collect`;
CREATE TABLE `account_rule_collect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `weight` int(11) NOT NULL COMMENT '支付顺序',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_weight` (`user_id`,`account_code`,`weight`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='收款规则';

-- ----------------------------
-- Table structure for account_rule_pay
-- ----------------------------
DROP TABLE IF EXISTS `account_rule_pay`;
CREATE TABLE `account_rule_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `weight` int(11) NOT NULL COMMENT '支付顺序',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_weight` (`user_id`,`account_code`,`weight`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='支付规则';

-- ----------------------------
-- Table structure for account_summary
-- ----------------------------
DROP TABLE IF EXISTS `account_summary`;
CREATE TABLE `account_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '交易类型:1-加，0-减',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `day` int(11) NOT NULL COMMENT '交易日期',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`account_code`,`day`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易汇总表';

-- ----------------------------
-- Table structure for account_transaction_flow
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction_flow`;
CREATE TABLE `account_transaction_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(70) NOT NULL COMMENT '交易流水号',
  `type` int(11) NOT NULL COMMENT '交易类型:1-加，0-减',
  `idempotent` varchar(70) NOT NULL COMMENT '幂等',
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `trade_day` int(11) NOT NULL COMMENT '交易日20201223',
  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '版本号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`,`status`),
  UNIQUE KEY `account_version` (`account_code`,`version`,`status`),
  UNIQUE KEY `idempotent` (`idempotent`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=25356 DEFAULT CHARSET=utf8 COMMENT='账户交易流水';

-- ----------------------------
-- Table structure for account_transaction_flow_add
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction_flow_add`;
CREATE TABLE `account_transaction_flow_add` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(70) NOT NULL COMMENT '交易流水号',
  `idempotent` varchar(70) NOT NULL COMMENT '幂等',
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `trade_day` int(11) NOT NULL COMMENT '交易日20201223',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`,`status`),
  UNIQUE KEY `idempotent_status` (`idempotent`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4001 DEFAULT CHARSET=utf8 COMMENT='账户交易流水-加';

-- ----------------------------
-- Table structure for account_transaction_flow_inter
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction_flow_inter`;
CREATE TABLE `account_transaction_flow_inter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(70) NOT NULL COMMENT '交易流水号',
  `type` int(11) NOT NULL COMMENT '交易类型:1-加，0-减',
  `idempotent` varchar(70) NOT NULL COMMENT '幂等',
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `trade_day` int(11) NOT NULL COMMENT '交易日20201223',
  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '版本号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`,`status`),
  UNIQUE KEY `idempotent_status` (`idempotent`,`status`),
  UNIQUE KEY `account_version` (`account_code`,`version`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=13957 DEFAULT CHARSET=utf8 COMMENT='账户交易流水减(内部)';

-- ----------------------------
-- Table structure for account_transaction_flow_inter_add
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction_flow_inter_add`;
CREATE TABLE `account_transaction_flow_inter_add` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(70) NOT NULL COMMENT '交易流水号',
  `idempotent` varchar(70) NOT NULL COMMENT '幂等',
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `trade_day` int(11) NOT NULL COMMENT '交易日20201223',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`,`status`),
  UNIQUE KEY `idempotent_status` (`idempotent`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4001 DEFAULT CHARSET=utf8 COMMENT='账户交易流水加(内部)';

-- ----------------------------
-- Table structure for account_transaction_flow_reduce
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction_flow_reduce`;
CREATE TABLE `account_transaction_flow_reduce` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(70) NOT NULL COMMENT '交易流水号',
  `idempotent` varchar(70) NOT NULL COMMENT '幂等',
  `account_code` varchar(64) NOT NULL COMMENT '账户编码',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `trade_date` datetime NOT NULL COMMENT '交易日期',
  `trade_day` int(11) NOT NULL COMMENT '交易日20201223',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`,`status`),
  UNIQUE KEY `idempotent_status` (`idempotent`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=8012 DEFAULT CHARSET=utf8 COMMENT='账户交易流水-减';

-- ----------------------------
-- Table structure for citizen
-- ----------------------------
DROP TABLE IF EXISTS `citizen`;
CREATE TABLE `citizen` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL COMMENT '唯一标示',
  `name` varchar(40) NOT NULL COMMENT '姓名',
  `id_no` varchar(18) NOT NULL COMMENT '身份证',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '删除状态:0使用中，1已删除',
  `create_date` datetime NOT NULL,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idno` (`name`,`id_no`,`is_del`)
) ENGINE=InnoDB AUTO_INCREMENT=114066 DEFAULT CHARSET=utf8 COMMENT='citizen';

-- ----------------------------
-- Table structure for citizen_detail
-- ----------------------------
DROP TABLE IF EXISTS `citizen_detail`;
CREATE TABLE `citizen_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL COMMENT '唯一标示',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `birthday` varchar(20) DEFAULT NULL,
  `nick_name` varchar(40) DEFAULT NULL COMMENT '昵称',
  `address` varchar(150) DEFAULT NULL COMMENT '地址',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '删除状态:0使用中，1已删除',
  `create_date` datetime NOT NULL,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=114017 DEFAULT CHARSET=utf8 COMMENT='citizen_detail';

-- ----------------------------
-- Table structure for crm_client
-- ----------------------------
DROP TABLE IF EXISTS `crm_client`;
CREATE TABLE `crm_client` (
  `idd` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL COMMENT '唯一标示',
  `invite_code` varchar(20) DEFAULT NULL,
  `id` varchar(18) DEFAULT NULL,
  `realName` varchar(30) DEFAULT NULL,
  `mobile` varchar(18) DEFAULT NULL,
  `idCardNo` varchar(18) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `age` varchar(3) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `registerTime` varchar(100) DEFAULT NULL,
  `bindTime` varchar(40) DEFAULT NULL,
  `nativePlace` varchar(80) DEFAULT NULL,
  `isReal` varchar(10) DEFAULT NULL,
  `attributesVersion` varchar(100) DEFAULT NULL,
  `delFlag` varchar(1) DEFAULT NULL,
  `isLive` varchar(100) DEFAULT NULL,
  `spendInCount` varchar(100) DEFAULT NULL,
  `channelCode` varchar(40) DEFAULT NULL,
  `fromMemberId` varchar(18) DEFAULT NULL,
  `withdrawPwd` varchar(100) DEFAULT NULL,
  `spendCount` varchar(40) DEFAULT NULL,
  `updateTime` varchar(30) DEFAULT NULL,
  `wuId` varchar(100) DEFAULT NULL,
  `createTime` varchar(100) DEFAULT NULL,
  `isHf` varchar(1) DEFAULT NULL,
  `attributes` varchar(100) DEFAULT NULL,
  `flag` varchar(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`idd`),
  UNIQUE KEY `idno` (`invite_code`,`idCardNo`,`realName`)
) ENGINE=InnoDB AUTO_INCREMENT=115168 DEFAULT CHARSET=utf8 COMMENT='crm_client';

-- ----------------------------
-- Table structure for crm_order
-- ----------------------------
DROP TABLE IF EXISTS `crm_order`;
CREATE TABLE `crm_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL COMMENT '唯一标示',
  `memberId` varchar(40) DEFAULT NULL,
  `orderNo` varchar(50) DEFAULT NULL,
  `sourceOrderNo` varchar(50) DEFAULT NULL,
  `payStatusEnum` varchar(50) DEFAULT NULL,
  `spendTime` varchar(50) DEFAULT NULL,
  `interestTime` varchar(50) DEFAULT NULL,
  `redemptionTime` varchar(50) DEFAULT NULL,
  `period` varchar(10) DEFAULT NULL,
  `payAmount` varchar(50) DEFAULT NULL,
  `spendAmount` varchar(50) DEFAULT NULL,
  `productId` varchar(50) DEFAULT NULL,
  `productName` varchar(50) DEFAULT NULL,
  `orderStatusEnum` varchar(50) DEFAULT NULL,
  `parentOrderNo` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `continueProductId` varchar(50) DEFAULT NULL,
  `countProfit` varchar(50) DEFAULT NULL,
  `expandProfit` varchar(50) DEFAULT NULL,
  `expctedEarning` varchar(50) DEFAULT NULL,
  `exprieProcessMode` varchar(50) DEFAULT NULL,
  `exprieProcessModeEnum` varchar(50) DEFAULT NULL,
  `haveparent` varchar(50) DEFAULT NULL,
  `makertProfit` varchar(100) DEFAULT NULL,
  `newProduct` varchar(80) DEFAULT NULL,
  `orderChannelEnum` varchar(50) DEFAULT NULL,
  `oredrProfit` varchar(100) DEFAULT NULL,
  `payModeEnum` varchar(50) DEFAULT NULL,
  `productCat` varchar(50) DEFAULT NULL,
  `productCatCodeEnum` varchar(100) DEFAULT NULL,
  `profit` varchar(100) DEFAULT NULL,
  `tradeNo` varchar(100) DEFAULT NULL,
  `userCoupon` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idno` (`memberId`,`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=77988 DEFAULT CHARSET=utf8 COMMENT='crm_order';

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(32) NOT NULL COMMENT '参数key',
  `key` varchar(32) NOT NULL COMMENT '参数key',
  `val` varchar(32) NOT NULL COMMENT '参数值',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '同一group下的顺序',
  `description` varchar(64) NOT NULL COMMENT '描述',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group` (`group`,`key`,`val`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Table structure for exception_logs
-- ----------------------------
DROP TABLE IF EXISTS `exception_logs`;
CREATE TABLE `exception_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '异常类型，0:非自定义异常，1:自定义异常',
  `message` varchar(500) NOT NULL COMMENT '异常信息',
  `clz` varchar(96) NOT NULL COMMENT '类路径',
  `method` varchar(32) NOT NULL COMMENT '方法名',
  `params` varchar(300) DEFAULT NULL COMMENT '入参，json',
  `trace_id` varchar(64) DEFAULT NULL COMMENT 'trace_id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `class_method` (`clz`,`method`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='异常日志';

-- ----------------------------
-- Table structure for lcs_course
-- ----------------------------
DROP TABLE IF EXISTS `lcs_course`;
CREATE TABLE `lcs_course` (
  `id` int(15) NOT NULL COMMENT '主键',
  `course_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '课程标题',
  `vido_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '音频/视频链接地址',
  `ppt_url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ppt课件链接地址',
  `course_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '课程类型 1 视频 2 音频',
  `category_id` int(15) DEFAULT NULL COMMENT '课程分类id',
  `course_explain` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '课程简介',
  `study_number` int(20) DEFAULT NULL COMMENT '学习人数',
  `study_times` int(20) DEFAULT NULL COMMENT '学习次数',
  `start_show_time` datetime DEFAULT NULL COMMENT '开始展示时间',
  `end_show_time` datetime DEFAULT NULL COMMENT '结束展示时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '删除标识 0是未删除 1 已删除',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for lcs_member
-- ----------------------------
DROP TABLE IF EXISTS `lcs_member`;
CREATE TABLE `lcs_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_uuid` varchar(100) NOT NULL COMMENT 'uuid',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `header_img` varchar(100) DEFAULT NULL COMMENT '头像',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `isAuth` char(2) NOT NULL DEFAULT '0' COMMENT '是否实名认证：1是；0否',
  `real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `idcard_no` varchar(18) DEFAULT NULL COMMENT '身份证',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `money_pwd` varchar(50) DEFAULT NULL COMMENT '提现密码',
  `invite_code` varchar(20) DEFAULT NULL COMMENT '邀请码',
  `industry` char(2) DEFAULT NULL COMMENT '从事行业(00:保险;01:银行理财;02:基金;03:信托;04其他)',
  `ageScope` char(2) DEFAULT NULL COMMENT '从业年限(00:1年以下；01:1-3年; 02:3-5年; 03:5-10年; 04:10年以上;05:其他)',
  `cityName` varchar(50) DEFAULT NULL COMMENT '所在地区名称',
  `company` varchar(50) DEFAULT NULL COMMENT '所在机构',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `login_times` int(11) NOT NULL DEFAULT '0' COMMENT '登陆次数',
  `usercenter_uuid` varchar(100) DEFAULT '' COMMENT '用户中心的uuid',
  `terminal` varchar(50) DEFAULT NULL COMMENT '终端设备,如pc,微信,移动端',
  `invite_partner_num` int(11) DEFAULT '0' COMMENT '本月已邀请合伙人数',
  `belong_to_partner` bigint(20) NOT NULL DEFAULT '0' COMMENT '属于哪个合伙人',
  `partner_date` datetime DEFAULT NULL,
  `belong_to_lcs` bigint(20) NOT NULL DEFAULT '0' COMMENT '属于哪个理财师',
  `lcs_date` datetime DEFAULT NULL,
  `is_lcs` int(11) NOT NULL DEFAULT '0' COMMENT '0:默认普通客户,1:理财师',
  `quaState` char(2) NOT NULL DEFAULT '0' COMMENT '理财师资质认证状态：0：未提交资质；1：审核中；2：审核通过；3：审核未通过',
  `quaImgUrl` varchar(100) DEFAULT NULL COMMENT '理财师资质图片url',
  `imgType` char(2) DEFAULT NULL COMMENT '理财师资质类型 1:银行从业资格证书',
  `is_new` int(11) NOT NULL DEFAULT '0' COMMENT '用户新消息提示。11表示不提示 12用户提示  21合伙人提示 22全部提示',
  `is_admin` int(11) NOT NULL DEFAULT '0' COMMENT '默认是0:普通用户 ， 1:管理员',
  `customer_num` int(11) NOT NULL DEFAULT '0' COMMENT '客户数',
  `total_sale_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '销售总金额',
  `is_crm` char(10) NOT NULL DEFAULT '0' COMMENT '是否已注册crm：0：未注册；1：已注册',
  `newbie_close_time` datetime DEFAULT NULL COMMENT '新手活动最后被关闭的时间',
  `is_9jia_member` int(11) DEFAULT '0' COMMENT '职业类型。0网络理财师、1玖加职能、2玖加销售、3渠道、4独立理财师、5向阳花、6沈阳渠道、7深圳、8重庆、9离职员工',
  `fuyou_status` char(2) DEFAULT '0' COMMENT '富友金账号开通状态：0:未开通，1:已开通',
  `register_crm_date` datetime DEFAULT NULL COMMENT '理财师注册crm时间',
  `is_j9_customer` int(11) DEFAULT '0' COMMENT '是否玖加客户.0:未知,1:玖加客户,2不是玖加客户,3忽略',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `is_modify_money_pwd` smallint(6) DEFAULT '0' COMMENT '已否修改过取现密码.0:未改过，1:修改过',
  `visit_status` smallint(6) DEFAULT '0' COMMENT '''理财师属性 0:待跟进,1:已放弃,2:已成交'',',
  `ass_level` decimal(10,2) DEFAULT '0.00' COMMENT '理财师考核等级',
  `data_origin` varchar(200) DEFAULT '',
  `manage_id` bigint(20) DEFAULT '0' COMMENT '管理人id',
  `manage_date` datetime DEFAULT NULL COMMENT '管理时间',
  `op_dimission_time` datetime DEFAULT NULL COMMENT '一键离职时间',
  `bind_switch` char(2) DEFAULT '0' COMMENT '理财师绑定老客户开关0:开 1:关',
  `belong_region` varchar(50) DEFAULT NULL COMMENT '手机归属地',
  PRIMARY KEY (`id`),
  KEY `lcs_member_mobile` (`mobile`),
  KEY `lcs_member_is_9jia_member` (`is_9jia_member`),
  KEY `lcs_member_del_flag` (`del_flag`),
  KEY `lcs_member_invite_code` (`invite_code`),
  KEY `lcs_user_uuid` (`user_uuid`),
  KEY `lcs_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1803 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for params
-- ----------------------------
DROP TABLE IF EXISTS `params`;
CREATE TABLE `params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(32) NOT NULL COMMENT '参数key',
  `val` varchar(32) NOT NULL COMMENT '参数值',
  `description` varchar(64) NOT NULL COMMENT '描述',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='参数配置表';

-- ----------------------------
-- Table structure for time_logs
-- ----------------------------
DROP TABLE IF EXISTS `time_logs`;
CREATE TABLE `time_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clz` varchar(96) NOT NULL COMMENT '类路径',
  `method` varchar(32) NOT NULL COMMENT '方法名',
  `params` varchar(300) DEFAULT NULL COMMENT '入参，json',
  `time` int(11) NOT NULL DEFAULT '0' COMMENT '调用耗费时间（毫秒）',
  `trace_id` varchar(64) DEFAULT NULL COMMENT 'trace_id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `class_method` (`clz`,`method`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='调用耗费时间日志';

-- ----------------------------
-- Table structure for token_record
-- ----------------------------
DROP TABLE IF EXISTS `token_record`;
CREATE TABLE `token_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_uuid` varchar(32) NOT NULL COMMENT 'uuid',
  `token` varchar(50) NOT NULL COMMENT 'token',
  `terminal_id` varchar(50) DEFAULT NULL COMMENT '终端id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='token记录表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) NOT NULL COMMENT 'uuid',
  `nick_name` varchar(32) NOT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别:0-其他，1-男，2-女',
  `img` varchar(100) DEFAULT NULL COMMENT '头像',
  `login_times` int(11) NOT NULL DEFAULT '0' COMMENT '登陆次数',
  `proved` int(11) NOT NULL DEFAULT '0' COMMENT '是否实名: 0-否,1-是',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) NOT NULL COMMENT 'uuid',
  `id_no` varchar(18) NOT NULL COMMENT '身份证',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细表';
