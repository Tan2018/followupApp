# SpringCloud Framework

## 项目启动顺序
eureka-server --> api-getway(可选，如果不启动，则不走网关)，其他的任意即可

## 服务讲解
+ eureka-server  注册中心，类似Zookeeper
+ api-getway 网关，路由转发
+ hystrix-dashboard-turbine 监控，监控数据
+ esb-service 类似服务，比如订单系统、购物车系统、支付系统，服务主要是直接操作数据库
+ net-service 主要是极光推送和即时通讯两个用来上网的服务，所有的需要上网的服务都连这个

## producer-service
producer-service已经集成weixin（待测试）、tk.mybatis

## 备注
需要具备知识：SpringBoot、SpringCloud  

学习地址：
1. SpringBoot/SpringCloud : http://blog.didispace.com/archives/page/17/
2. Spring官网 : https://spring.io
3. 微信：https://github.com/Wechat-Group/weixin-java-tools   https://github.com/binarywang/weixin-java-mp-demo-springboot 
4. tk.mybatis : https://gitee.com/free/Mapper   
Mybatis : https://github.com/mybatis-book/book
分页插件Mybatis-PageHelper : https://github.com/pagehelper/Mybatis-PageHelper


