# Portfolio

勤怠管理システムを作成しました。

管理者側と従業員側のログイン方法はDBに以下のコードを記入してください。


管理者側
create table m_user(
	user_id varchar (24) not null,
	password varchar (32) not null
);

insert into m_user(user_id , password) 
values('管理者テストです', 'abcd1234');

従業員側
create table m_employee(
employee_code int not null primary key,
last_name varchar(20),
first_name varchar(20),
last_kana_Name varchar(20),
first_kana_Name varchar(20),
gender int,
birth_day varchar(20),
section_code varchar(20),
hire_date varchar(20),
password varchar(20)
);

INSERT INTO m_employee (employee_code, last_name, first_name, last_kana_Name, first_kana_Name, gender, birth_day, section_code, hire_date, password)
VALUES (1, '本町', '太郎', 'ほんまち', 'たろう', 0, '1999-12-01', '1', '2023-06-01', 'tarou123');

下記は従業員登録や部署を確認するときに使うデータベースです。

従業員の部署
create table m_section (
section_code varchar(20) not null primary key,
section_name varchar(20)
);

insert into m_section(section_code , section_name)
values('1' , '総務部');

下記はタイムカードの記入やタイムシートの確認に使うデータベースです。

CREATE TABLE t_work_time (
  timecard_id INT PRIMARY KEY AUTO_INCREMENT,
  employee_code INT NOT NULL,
  work_date DATE,
  start_time TIME,
  finish_time TIME,
  break_start_time TIME,
  break_finish_time TIME,
  break_time TIME,
  working_hours TIME,
  CONSTRAINT fk_employee
    FOREIGN KEY (employee_code)
    REFERENCES m_employee (employee_code)
);

改善点
・ break_time（休憩時間　休憩入り＋休憩戻り)
working_hours(出勤・退勤時間から休憩時間を差し引いたもの)がまだデータベース上に反映されていない（Webアプリケーション上ではちゃんと計算されている）
・ハッシュ化の機能がまだできていない。
・タイムシートに備考欄が追加できていない。


