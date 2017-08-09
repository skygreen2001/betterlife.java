# Betterlife.Java 框架核心代码: bb.springboot

本应用使用 Spring Boot + Jetty + Spring Rest + Sprig Amqp[RabbitMQ]  + Spring Websocket

书籍参考: [Packt.Spring.Microservices.2016.6](https://github.com/PacktPublishing/Spring-Microservices)

技术帮助文档支持可查看: [https://jhipster.github.io](https://jhipster.github.io)


## 初始化

- 下载代码到本地
  ```
  > cd ~/ && mkdir SpringProjects && cd SpringProjects

  > git clone -b spring.boot git@github.com:skygreen2001/betterlife.java.git Betterlife
  或者
  > git clone -b spring.boot https://github.com/skygreen2001/betterlife.java.git Betterlife

  ```
- 在IDE工具里打开工程
  - [IntelliJ IDEA]
    > 选择菜单 File > Open... 选择路径 [~/SpringProjects/Betterlife]

## 开发

  * **本目录下运行**

    - 分别在两个命令行工具运行以下指令，可使浏览器自动刷新查看到您最新的修改内容

    ```
    > mvn -Dmaven.test.skip=true install
    > java -jar target/*.jar
    ```

    - 浏览器里访问 [http://localhost:8080/](http://localhost:8080/)

## 发布
- 发布到测试服务器
  


- 发布到正式服务器


- 查看log
  ```
  > tail -f -n 400 catalina.out
  ```
  
## 运行RabbitMQ Server
    - [Ubuntu: Start and Stop the RabbitMQ Server] (https://pubs.vmware.com/vfabric53/index.jsp?topic=/com.vmware.vfabric.rabbitmq.3.2/getstart/install-start-server-ubuntu.html)
    - [RabbitMQ安装和使用](https://chyufly.github.io/blog/2016/04/10/rabbitmq-setup/)
    - 安装路径
        [Mac系统](/usr/local/Cellar/rabbitmq/)
    
    - 启动:
    ```
    > invoke-rc.d rabbitmq-server start
    ```
    
    - 停止:
    ```
    > invoke-rc.d rabbitmq-server stop
    ```

    - 管理:
    ```
    > rabbitmq-plugins enable rabbitmq_management
    ```
    
    - 外网浏览器访问管理:
    测试账户: itaskTour2006 
    密码: skygreen2001
    设置角色: sudo rabbitmqctl set_user_tags itaskTour2006 administrator
    
    访问: http://server-name:15672/


## 参考

- [物联网通信协议](https://github.com/ruizeng/blog/blob/master/IoT/iot-protocols.md)
- [RabbitMQ Quick（快速手册）](https://geewu.gitbooks.io/rabbitmq-quick/content/RabbitMQ%E4%BB%8B%E7%BB%8D.html)