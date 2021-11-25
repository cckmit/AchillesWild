DELETE from account_lock;
DELETE from account_rule_collect;
DELETE from account_rule_pay;
DELETE from account_summary;
DELETE from account_summary;
DELETE from account_transaction_flow;
DELETE from account_transaction_flow_add;
DELETE from account_transaction_flow_inter;
DELETE from account_transaction_flow_inter_add;
DELETE from account_transaction_flow_reduce;

DELETE from citizen;
DELETE from citizen_detail;

DELETE from log_time_info;
DELETE from log_exception_info;

SELECT COUNT(*) from log_time_info;
SELECT * from log_time_info ORDER BY time desc;

DELETE from user_token;

----------------------------------------------------------
select * from account;

update account set balance=10000000,version=0 where user_id='wild';

select *from account_transaction_flow order by id desc limit 1000;

select count(*) from account_transaction_flow;

delete from account_transaction_flow;

SELECT COUNT(*) from account_transaction_flow;

select * from account_inter;

update account_inter set balance=10000,version=0 where user_id='Achilles' limit 1000;

select *from account_transaction_flow_inter order by id desc;
select count(*) from account_transaction_flow_inter;
delete from account_transaction_flow_inter where id <100000;

--------------------------------------------------------------------

#打开/关闭记录sql日志
SET GLOBAL general_log = 'on';
SET GLOBAL general_log = 'off';
#日志写到表里
SET GLOBAL log_output = 'table';
#查询日志
SELECT
	event_time,
	thread_id,
	server_id,
	command_type,
	CONVERT (argument USING utf8) AS argument,
	user_host
FROM
	mysql.general_log
ORDER BY
	event_time DESC;

#用户权限
SHOW GRANTS;

#服务器信息
SHOW STATUS;

SHOW ENGINE INNODB STATUS;

SHOW VARIABLES LIKE '%timeout%';

#查询是否锁表
show OPEN TABLES where In_use > 0;

行锁
show status like 'innodb_row_lock%';
#死锁信息
SHOW ENGINE INNODB STATUS;
SELECT*FROM information_schema.innodb_locks;
SELECT*from information_schema.INNODB_lock_waits;
SELECT*from information_schema.INNODB_trx;


SHOW PROCESSLIST;
SELECT
	*
FROM
	information_schema.`PROCESSLIST`
WHERE
	info LIKE '%sele%';
SHOW FULL PROCESSLIST;

SELECT @@autocommit


show variables like "%max_connections%";

set global max_connections = 20000;

SET persist max_connections = 20000;

set global mysqlx_max_connections = 10000;

SELECT  VERSION();

SELECT * FROM information_schema.INNODB_TRX;
kill 89;

#服务器启动以来的冲突
SELECT
	*
FROM
	`performance_schema`.mutex_instances
WHERE
	LOCKED_BY_THREAD_ID IS NOT NULL;

#谁在等待冲突（THREAD_ID内部,mysqld分配给线程的实际编号，，而不是产生死锁的连接线程的编号）
SELECT
	THREAD_ID,
	EVENT_ID,
	EVENT_NAME,
	SOURCE,
	TIMER_START,
	OBJECT_INSTANCE_BEGIN,
	OPERATION
FROM
	`performance_schema`.events_waits_current
WHERE
	THREAD_ID IN (
		SELECT
			THREAD_ID
		FROM
			`performance_schema`.mutex_instances
		WHERE
			LOCKED_BY_THREAD_ID IS NOT NULL
	);

#连接线程编号
SELECT
	*
FROM
	`performance_schema`.threads

