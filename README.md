# SpringCloud-LearningProjects

## 【图灵课堂】Spring Cloud Alibaba微服务课程

### 1. project1 项目
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
> 该项目展示了：
> * 如何在客户端进行配置使得多个服务可以注册到Nacos注册中心
> * 如何在本地以单机模式（standalone）开启Nacos服务并接受客户端的服务注册与发现
> * 介绍了一些客户端注册服务时常见的配置（具体而言就是yml文件中的配置）
> * 最后介绍了如何搭建Nacos服务器集群（未实现）

### 2. project2 项目
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
> 该项目展示了：
> * OpenFeign的基本使用————让访问远程服务像访问本地方法一样简单！即OpenFeign代替了RestTemplate
> * 对Feign进行自定义配置，可采用2种方式：自定义配置类和配置yml文件，本项目演示了如何自定义Feign的调用日志输出级别
> * 如何在Nacos服务端UI界面中新建配置文件并发布
> * 如何从Nacos-Config配置中心动态拉取配置文件（由于用的是较新版的Nacos，而且官网教程真的垃圾，心累...不过还是成功了）
![image](https://github.com/user-attachments/assets/ec6ad0b3-ed1a-42f7-8c0d-b92eb7d5a172)
![image](https://github.com/user-attachments/assets/3a7331ac-3c65-42a4-8ff4-91cdb9125bb8)

### 3. project3 项目
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
> 该项目展示了：
> * Sentinel的分布式原生使用方法（即不与SpringCloud Alibaba进行整合）
> * 使用Sentinel对资源进行"可靠性（Reliability）"与"弹性（Resiliency）"配置的2种方法（加上DashBoard总共是3种），第一种即HelloController中的原生try-catch定义资源+规则控制，第二种即UserController中的使用@SentinelResource注解对资源进行配置，这两种方法都需要结合@PostConstruct注解干预Bean的生命周期
> * 详细解释了@SentinelResource注解的用法
> * 详细解释了Sentinel中的资源"被流控"、"被降级"（也就是"被熔断"）、"出现异常"这3种情况的本质区别，以及分别的处理方式
> * 介绍了如何集成Sentinel DashBoard，详细步骤见"笔记.txt"
