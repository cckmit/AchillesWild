select * from account;
update account set balance=1000,version=0 where user_id='wild';

select *from account_transaction_flow order by id desc limit 1000;
select count(*) from account_transaction_flow;
delete from account_transaction_flow where id <100000;

select * from account_inter;

update account_inter set balance=10000,version=0 where user_id='Achilles' limit 1000;

select *from account_transaction_flow_inter order by id desc;
select count(*) from account_transaction_flow_inter;
delete from account_transaction_flow_inter where id <100000;
