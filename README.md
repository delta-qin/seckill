# seckill

基于领域模型

## 技术

|                   |            |      |
| ----------------- | ---------- | ---- |
| springboot        |            |      |
| Knife4j           | API文档     |      |
| Slf4j + logback   |            |      |
| lombok   | 日志注解，builder |      |
| Mybatis           |            |      |
| Mybatis-generator |            |      |
| druid             |            |      |
| Redis             |            |      |
| guava             |            |      |
| rocketmq          | 事务消息   |      |
| Joda-time         | 高效和安全 |      |
| Docker            | 部署方便   |      |

## 项目改进记录

### v1.0（已实现）

- 基本功能实现（使用数据库实现）
    - 用户模块
    - 商品模块
    - 订单模块
    - 秒杀模块

### v2.0（已实现）

- 使用缓存优化
- 固定大小的线程池的无界队列缓存用户请求
- 兼容旧的版本，没有删除redisTemplate。同时增加了自定义的redisService

### v3.0（已实现）

- mq升级：
- 事务消息

## v5.0

- 项目结构换血：使用idea的模块化开发，按照领域分层结构划分

## 项目搭建

- 启动项目之后查看文档：http://localhost:8080/doc.html#/

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
│               │   ├── constant  常量封装
│               │   ├── entities  封装返回类型
│               │   ├── exception 封装异常类型
│               │   ├── serializer 自定义时间序列化方式
│               │   ├── utils 常用工具类封装
│               │   └── validator 参数校验工具
│               ├── config Redis以及文档的相关配置
│               ├── controller 接口层
│               ├── dao 基础设施层
│               ├── dataobject 实体对象
│               ├── model 领域模型
│               ├── service 逻辑层
│               │   └── impl
│               └── vo 返回视图对象
└── resources 
    ├── application-dev.properties 开发相关配置
    ├── application-prod.properties 部署相关配置
    ├── application.properties 
    └── mybatis-generator.xml 基础设施层生成配置

```
