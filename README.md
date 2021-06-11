# seckill

基于领域模型

## 技术

|                   |            |      |
| ----------------- | ---------- | ---- |
| springboot        |            |      |
| Knife4j           |            |      |
| Slf4j + logback   |            |      |
| Mybatis           |            |      |
| Mybatis-generator |            |      |
| druid             |            |      |
| Redis             |            |      |
| rocketmq          |            |      |
| Joda-time         | 高效和安全 |      |

## 项目功能实现

- 用户模块
- 商品模块
- 订单模块
- 秒杀模块

## 项目优化

### v1.0

基本功能实现

### v2.0

缓存升级

固定大小的线程池的无界队列缓存用户请求

### v3.0

mq升级：

事务消息

## v4.0

缓存异步通知更新

## v5.0

项目结构换血：使用idea的模块化开发，按照领域分层结构划分



## 项目搭建



## 常见问题汇总



## 项目结构

```
.
├── java
│   └── com
│       └── deltaqin
│           └── seckill
│               ├── SecKillApplcation.java
│               ├── common
│               │   ├── constant
│               │   ├── entities
│               │   ├── exception
│               │   ├── serializer
│               │   ├── utils
│               │   └── validator
│               ├── config
│               ├── controller
│               ├── dao
│               ├── dataobject
│               ├── model
│               ├── service
│               │   └── impl
│               └── vo
└── resources
    ├── application-dev.properties
    ├── application-prod.properties
    ├── application.properties
    └── mybatis-generator.xml

```
