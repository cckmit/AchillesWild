/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : info

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2021-03-07 23:38:29
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
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('158', 'ACCOUNT_20200618172720043_1_0', '4', '0', '0', 'wild', '172', '1', '2020-06-18 17:27:20', '2021-01-30 12:07:29');
INSERT INTO `account` VALUES ('159', 'ACCOUNT_20200618172720043_1_1', '4', '0', '0', 'wild', '100', '1', '2020-06-18 17:27:20', '2021-01-31 23:20:18');
INSERT INTO `account` VALUES ('160', 'ACCOUNT_20200618172720043_1_2', '4', '0', '0', 'wild', '100', '1', '2020-06-18 17:27:20', '2021-02-02 22:10:28');
INSERT INTO `account` VALUES ('161', 'ACCOUNT_20200618172720044_1_3', '4', '480', '0', 'wild', '52', '1', '2020-06-18 17:27:20', '2021-02-19 22:06:32');
INSERT INTO `account` VALUES ('162', 'ACCOUNT_20200618172720044_1_4', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-07-26 18:54:48');
INSERT INTO `account` VALUES ('163', 'ACCOUNT_20200618172720044_1_5', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-07-26 18:54:48');
INSERT INTO `account` VALUES ('164', 'ACCOUNT_20200618172720044_1_6', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-07-26 18:54:48');
INSERT INTO `account` VALUES ('165', 'ACCOUNT_20200618172720044_1_7', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-07-26 18:54:48');
INSERT INTO `account` VALUES ('166', 'ACCOUNT_20200618172720044_1_8', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-07-26 18:54:48');
INSERT INTO `account` VALUES ('167', 'ACCOUNT_2021618172720044_1_9', '4', '1000', '0', 'wild', '0', '1', '2020-06-18 17:27:20', '2020-09-30 16:19:03');

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
-- Records of account_inter
-- ----------------------------
INSERT INTO `account_inter` VALUES ('8', 'PROVISION_ACCOUNT', '0', '10000', '0', 'Achilles', '0', '1', '2020-05-30 18:50:08', '2020-06-20 18:29:33');
INSERT INTO `account_inter` VALUES ('9', 'PAY_ACCOUNT_1', '4', '6480', '0', 'Achilles', '424', '1', '2020-05-30 18:50:36', '2021-02-19 22:06:32');
INSERT INTO `account_inter` VALUES ('10', 'PAY_ACCOUNT_2', '4', '10000', '0', 'Achilles', '0', '1', '2020-05-30 18:50:41', '2020-07-26 18:54:48');
INSERT INTO `account_inter` VALUES ('11', 'PAY_ACCOUNT_3', '4', '10000', '0', 'Achilles', '0', '1', '2020-05-30 18:51:00', '2020-07-26 18:54:48');
INSERT INTO `account_inter` VALUES ('12', 'PAY_ACCOUNT_4', '4', '10000', '0', 'Achilles', '0', '1', '2020-05-30 18:51:08', '2020-07-26 18:54:48');
INSERT INTO `account_inter` VALUES ('13', 'COLLECTION_ACCOUNT_1', '3', '10000', '0', 'Achilles', '0', '1', '2021-01-24 16:33:44', '2021-01-24 16:33:44');

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
-- Records of account_lock
-- ----------------------------

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
-- Records of account_rule_collect
-- ----------------------------

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
-- Records of account_rule_pay
-- ----------------------------

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
-- Records of account_summary
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=25357 DEFAULT CHARSET=utf8 COMMENT='账户交易流水';

-- ----------------------------
-- Records of account_transaction_flow
-- ----------------------------

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
-- Records of account_transaction_flow_add
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=13958 DEFAULT CHARSET=utf8 COMMENT='账户交易流水减(内部)';

-- ----------------------------
-- Records of account_transaction_flow_inter
-- ----------------------------

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
-- Records of account_transaction_flow_inter_add
-- ----------------------------

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
-- Records of account_transaction_flow_reduce
-- ----------------------------

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
-- Records of citizen
-- ----------------------------

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
-- Records of citizen_detail
-- ----------------------------

-- ----------------------------
-- Table structure for config_dict
-- ----------------------------
DROP TABLE IF EXISTS `config_dict`;
CREATE TABLE `config_dict` (
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
-- Records of config_dict
-- ----------------------------

-- ----------------------------
-- Table structure for config_params
-- ----------------------------
DROP TABLE IF EXISTS `config_params`;
CREATE TABLE `config_params` (
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
-- Records of config_params
-- ----------------------------

-- ----------------------------
-- Table structure for log_exception_info
-- ----------------------------
DROP TABLE IF EXISTS `log_exception_info`;
CREATE TABLE `log_exception_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '异常类型，0:非自定义异常，1:自定义异常',
  `message` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常信息',
  `clz` varchar(96) NOT NULL COMMENT '类路径',
  `method` varchar(32) NOT NULL COMMENT '方法名',
  `params` varchar(300) DEFAULT NULL COMMENT '入参，json',
  `trace_id` varchar(64) DEFAULT NULL COMMENT 'trace_id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `class_method` (`clz`,`method`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='异常日志';

-- ----------------------------
-- Records of log_exception_info
-- ----------------------------

-- ----------------------------
-- Table structure for log_filter_info
-- ----------------------------
DROP TABLE IF EXISTS `log_filter_info`;
CREATE TABLE `log_filter_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
  `type` varchar(10) NOT NULL COMMENT '请求类型，post,get..',
  `time` int(11) NOT NULL DEFAULT '0' COMMENT '调用耗费时间（毫秒）',
  `trace_id` varchar(64) DEFAULT NULL COMMENT 'trace_id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `url` (`uri`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8 COMMENT='过滤器耗费时间日志';

-- ----------------------------
-- Records of log_filter_info
-- ----------------------------

-- ----------------------------
-- Table structure for log_time_info
-- ----------------------------
DROP TABLE IF EXISTS `log_time_info`;
CREATE TABLE `log_time_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(64) NOT NULL COMMENT 'url',
  `type` varchar(10) NOT NULL COMMENT '请求类型，post,get..',
  `layer` int(11) NOT NULL COMMENT '应用哪一层',
  `clz` varchar(96) NOT NULL DEFAULT '0' COMMENT '类路径',
  `method` varchar(32) NOT NULL DEFAULT '0' COMMENT '方法名',
  `params` varchar(300) DEFAULT NULL COMMENT '入参，json',
  `time` int(11) NOT NULL DEFAULT '0' COMMENT '调用耗费时间（毫秒）',
  `trace_id` varchar(64) DEFAULT NULL COMMENT 'trace_id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `class_method` (`clz`,`method`),
  KEY `url` (`uri`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='调用耗费时间日志';

-- ----------------------------
-- Records of log_time_info
-- ----------------------------

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
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '97a87026d8a4475a9679bb03e305cbaf', 'AchillesWild', 'A699E23DD2179DBB3A705BD652AA3C06', 'wild@qq.com', null, '0', null, '0', '0', '1', '2021-02-12 18:28:46', '2021-02-12 18:32:30');

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

-- ----------------------------
-- Records of user_detail
-- ----------------------------

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_uuid` varchar(32) NOT NULL COMMENT 'uuid',
  `token` varchar(50) NOT NULL COMMENT 'token',
  `terminal_id` varchar(50) DEFAULT NULL COMMENT '终端id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态: 1-使用中,0-已删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='token记录表';

-- ----------------------------
-- Records of user_token
-- ----------------------------
