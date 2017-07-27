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
    > mvn install
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

