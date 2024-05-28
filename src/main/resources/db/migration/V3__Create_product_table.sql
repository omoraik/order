create table products (
 id int(11) not null auto_increment comment '商品ID',
 name varchar(255) not null comment '商品名',
 price int(11) not null comment '価格',
 created_at datetime not null default current_timestamp() comment '作成日時',
 modified_at datetime not null default current_timestamp() on update current_timestamp() comment '更新日時',
 primary key (id)
) engine=innodb default charset=utf8mb4 comment='商品';