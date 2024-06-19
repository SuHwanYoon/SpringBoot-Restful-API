-- H2DB사용시에 사용할 데이터 파일

-- Hibernate Entity 자동 생성 sql 문은 카멜 표기법을 스네이크 표기법으로 자동으로 바꾼다(birthDate -> birth_date)
--Hibernate: drop table if exists user_details cascade 
--Hibernate: drop sequence if exists user_details_seq
--Hibernate: create sequence user_details_seq start with 1 increment by 50
--Hibernate: create table user_details (birth_date date, id integer not null, name varchar(255), primary key (id))
insert into user_details(id,birth_date,name)
values(10001, current_date(), 'Yoon');

insert into user_details(id,birth_date,name)
values(10002, current_date(), 'Kim');

insert into user_details(id,birth_date,name)
values(10003, current_date(), 'Park');

insert into post(id,description,user_id)
values(20002,'정보처리기사취득', 10001);

insert into post(id,description,user_id)
values(20003,'컴퓨터공학 학사취득', 10002);

insert into post(id,description,user_id)
values(20004,'I want to learn React', 10002);

insert into post(id,description,user_id)
values(20005,'I want to learn TypeScript', 10003);

insert into post(id,description,user_id)
values(20006,'I want to learn AWS', 10003);

insert into post(id,description,user_id)
values(20007,'I want to learn SpringCloud', 10003);