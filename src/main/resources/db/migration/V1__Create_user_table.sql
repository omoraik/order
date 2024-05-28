create table users (
  id int(11) not null primary key auto_increment comment 'ユーザID',
  email varchar(255) not null unique comment 'メールアドレス',
  password varchar(255) not null comment 'パスワード',
  created_at datetime not null default current_timestamp() comment '作成日時',
  modified_at datetime not null default current_timestamp() on update current_timestamp() comment '更新日時'
) engine=innodb default charset=utf8mb4 comment='ユーザ';