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
  - 分别在两个命令行工具运行以下指令，可使浏览器自动刷新查看到您最新的修改内容
    
    ```
    > mvn -Dmaven.test.skip=true install
    > java -jar target/*.jar
    ```
    
  - 浏览器里访问 [http://localhost:8080/](http://localhost:8080/)

## 发布

  - 上传 jar 到服务器目录
    ```
    > mvn -Dmaven.test.skip=true install
    >[本机] ssh root@message.betterlife.com "[ -d /var/msgserver ] && echo ok || mkdir -p /var/msgserver"       [只需执行一次]
    >[本机] scp ./document/msg-server.sh root@debug.itasktour.com:/var/msgserver/       [只需执行一次]
    >[本机] cd target && cp betterlife-1.0.0-SNAPSHOT.jar msgserver.jar && scp ./msgserver.jar root@message.betterlife.com:/var/msgserver/  && cd ../
    >[本机] ssh -t root@message.betterlife.com "cd /var/msgserver ; bash"
    >[服务器] service msg-server restart
    >[服务器] 如本应用不能正常运行，可直接运行: /var/msgserver/./msgserver.jar
    ```

  - 第一次需在服务器上执行以下指令
    ```
    > cd /var/msgserver/ && chmod 0755 msgserver.jar
    > cp msg-server.sh /etc/init.d/msg-server
    > service msg-server start
    > update-rc.d msg-server defaults
    ```
    
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
    - 测试账户: skygreen2001 
    - 密码: skygreen2001
    - 设置角色: 
        ```
        > sudo rabbitmqctl add_user skygreen2001 skygreen2001
        > sudo rabbitmqctl set_user_tags skygreen2001 administrator
        > sudo rabbitmqctl set_permissions -p / skygreen2001 '.*' '.*' '.*'
        > sudo rabbitmqctl list_users
        ```
  
  - 访问: http://server-name:15672/
 
## 内存持久化
- Redis
    - [下载Redis](https://redis.io/download)
    - 生成运行包
    ```
    > cd redis-4.0.1 && make && make test [第一次]
    ```
    - [启动Redis]  
    ```
    > src/redis-server
    ```
    - [在服务器上部署Redis](https://redis.io/topics/quickstart)
      - [How To Install and Configure Redis on Ubuntu 16.04](https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-redis-on-ubuntu-16-04)
        ```
        > cp src/redis-server /usr/local/bin/ && cp src/redis-cli /usr/local/bin/
        > sudo mkdir /etc/redis && sudo mkdir /var/redis
        > sudo cp utils/redis_init_script /etc/init.d/redis_6379
        > sudo vi /etc/init.d/redis_6379
        > sudo cp redis.conf /etc/redis/6379.conf
        > sudo mkdir /var/redis/6379
        > sudo vi /etc/redis/6379.conf
          - daemonize yes
          - pidfile /var/run/redis_6379.pid
          - logfile "/var/log/redis_6379.log"
          - dir /var/redis/6379
        > sudo update-rc.d redis_6379 defaults
        > sudo /etc/init.d/redis_6379 start
        ```
    
- MongoDB
    - [MacOS下载MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
    - [Linux下载MongoDB](https://docs.mongodb.com/manual/administration/install-on-linux/)

## 参考

- [Deploying Spring Boot applications](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html)
- [Ubuntu: Start and Stop the RabbitMQ Server](https://pubs.vmware.com/vfabric53/index.jsp?topic=/com.vmware.vfabric.rabbitmq.3.2/getstart/install-start-server-ubuntu.html)
- [RabbitMQ安装和使用](https://chyufly.github.io/blog/2016/04/10/rabbitmq-setup/)
- [物联网通信协议](https://github.com/ruizeng/blog/blob/master/IoT/iot-protocols.md)
- [RabbitMQ Quick（快速手册）](https://geewu.gitbooks.io/rabbitmq-quick/content/RabbitMQ%E4%BB%8B%E7%BB%8D.html)