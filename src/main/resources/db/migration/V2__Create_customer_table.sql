create table customers (
 id int(11) not null auto_increment comment '顧客ID',
 name varchar(255) not null comment '顧客名',
 created_at datetime not null default current_timestamp() comment '作成日時',
 modified_at datetime not null default current_timestamp() on update current_timestamp() comment '更新日時',
 primary key (id)
) engine=innodb default charset=utf8mb4 comment='顧客';