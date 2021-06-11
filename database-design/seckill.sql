/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/6/10 16:30:20                           */
/*==============================================================*/


drop table if exists good;

drop table if exists item_stock;

drop table if exists order_info;

drop table if exists seckill_good;

drop table if exists sequence_info;

drop table if exists stock_log;

drop table if exists user_info;

drop table if exists user_password;

/*==============================================================*/
/* Table: good                                                  */
/*==============================================================*/
create table good
(
   good_id              int not null  comment '',
   good_name            varchar(64)  comment '',
   good_price           decimal(8,2)  comment '',
   good_description     varchar(255)  comment '',
   good_sales           int  comment '',
   good_img_url         varchar(255)  comment '',
   primary key (good_id)
);

/*==============================================================*/
/* Table: item_stock                                            */
/*==============================================================*/
create table item_stock
(
   stock_id             int not null  comment '',
   stock_count          int  comment '',
   item_id              int  comment '',
   primary key (stock_id)
);

/*==============================================================*/
/* Table: order_info                                            */
/*==============================================================*/
create table order_info
(
   order_id             int not null  comment '',
   user_id              int  comment '',
   item_id              int  comment '',
   item_price           decimal(8,2)  comment '一个时刻的当前价格',
   item_count           int  comment '',
   total_price          decimal(8,2)  comment '单价*个数',
   seckill_id           int  comment '秒杀商品的id',
   primary key (order_id)
);

/*==============================================================*/
/* Table: seckill_good                                          */
/*==============================================================*/
create table seckill_good
(
   sec_id                 int not null  comment '',
   seckill_name         varchar(64)  comment '',
   start_time           datetime  comment '',
   end_time             datetime  comment '',
   seckill_price        decimal(8,2)  comment '',
   primary key (sec_id)
);

/*==============================================================*/
/* Table: sequence_info                                         */
/*==============================================================*/
create table sequence_info
(
   seq_name             varchar(255) not null  comment '',
   current_value        int  comment '',
   step                 int  comment '',
   primary key (seq_name)
);

/*==============================================================*/
/* Table: stock_log                                             */
/*==============================================================*/
create table stock_log
(
   stock_log_id         int not null  comment '',
   item_id              int  comment '',
   item_stock_count     int  comment '',
   item_status          smallint  comment '1表示初始状态，2表示下单扣减库存成功，3表示下单回滚',
   primary key (stock_log_id)
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   id                   int not null  comment '',
   name                 varchar(64) not null  comment '',
   gender               smallint not null  comment '默认0，男',
   age                  int not null  comment '',
   telphone             varchar(255) not null  comment '',
   register_mode        varchar(255) not null  comment '',
   third_party_id       int not null  comment '',
   primary key (id)
);

alter table user_info comment '用户表';

/*==============================================================*/
/* Table: user_password                                         */
/*==============================================================*/
create table user_password
(
   pass_id              int not null  comment '',
   password             varchar(64)  comment '',
   user_id              int  comment '',
   primary key (pass_id)
);

alter table user_password comment '用户密码表';

