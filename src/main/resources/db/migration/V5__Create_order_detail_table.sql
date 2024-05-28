create table order_details (
 id int(11) not null auto_increment comment '注文詳細ID',
 order_id int(11) not null comment '注文ID',
 product_id int(11) not null comment '商品ID',
 unit int(11) not null comment '数量',
 created_at datetime not null default current_timestamp() comment '作成日時',
 modified_at datetime not null default current_timestamp() on update current_timestamp() comment '更新日時',
 primary key (id)
) engine=innodb default charset=utf8mb4 comment='注文詳細';