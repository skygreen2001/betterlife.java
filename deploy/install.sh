#!/usr/bin/env bash

#系统变量
OS=$(awk '/DISTRIB_ID=/' /etc/*-release | sed 's/DISTRIB_ID=//' | tr '[:upper:]' '[:lower:]')
ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')
VERSION=$(awk '/DISTRIB_RELEASE=/' /etc/*-release | sed 's/DISTRIB_RELEASE=//' | sed 's/[.]0/./')

#通用变量
PATH_MSG_SERVER=/var/msgserver

if [ -z "$OS" ]; then
    OS=$(awk '{print $1}' /etc/*-release | tr '[:upper:]' '[:lower:]')
fi

if [ -z "$VERSION" ]; then
    VERSION=$(awk '{print $3}' /etc/*-release)
fi

echo "--------------------------*.系统安装需求 Info-----------------------------"
echo "安装系统: Ubuntu 14.04以上"

echo "--------------------------*.系统信息预览 Info-----------------------------"
echo $OS $VERSION $ARCH "位"

#调试退出指令
#exit 1

echo "--------------------------*.安装 Rabbitmq Info-----------------------------"
echo "1. 添加Rabbitmq 下载源"
echo "deb http://www.rabbitmq.com/debian/ testing main" | sudo tee --append /etc/apt/sources.list > /dev/null

echo "2. 更新Ubuntu系统"
sudo apt-get update
sudo apt-get upgrade

echo "3. 安装Erlang"
cd /tmp
wget http://packages.erlang-solutions.com/ubuntu/erlang_solutions.asc
sudo apt-key add erlang_solutions.asc
sudo apt-get update
sudo apt-get install erlang
sudo apt-get install erlang-nox

echo "4. 执行安装Rabbitmq"
sudo apt-get install rabbitmq-server
sudo update-rc.d rabbitmq-server defaults

echo "5*.启用rabbitmq-management插件：sudo rabbitmq-plugins enable rabbitmq_management"
sudo rabbitmq-plugins enable rabbitmq_management

echo "6. 设置雪橇爱闯闯Go APP所需的账户: itaskTour2006"
sudo rabbitmqctl add_user itaskTour2006 skygreen2001
sudo rabbitmqctl set_user_tags itaskTour2006 administrator
sudo rabbitmqctl set_permissions -p / itaskTour2006 '.*' '.*' '.*'
sudo rabbitmqctl list_users
echo "6_end. 设置成功: 雪橇爱闯闯Go APP所需的账户"

echo "7.rabbitmq-server 需要开放15672端口"
sudo iptables -A INPUT -p tcp --dport 15672 -j ACCEPT
sudo iptables -A OUTPUT -p tcp --sport 15672 -j ACCEPT

VERSION_V=${VERSION%.*}
if [[ $VERSION_V -ge 16 ]]; then
    echo "8. systemctl启动RabbitMQ服务"
    sudo systemctl enable rabbitmq-server
    sudo systemctl start rabbitmq-server
elif [[ $VERSION_V -ge 14 ]]; then
    invoke-rc.d rabbitmq-server start
fi

echo "--------------------------*.安装 Redis-----------------------------"
sudo mkdir $PATH_MSG_SERVER && cd $PATH_MSG_SERVER
sudo wget http://download.redis.io/releases/redis-4.0.1.tar.gz
sudo tar xzf redis-4.0.1.tar.gz && cd redis-4.0.1
if [[ $VERSION_V -ge 16 ]]; then
    sudo apt-get install make && sudo apt-get install build-essential
fi
sudo make && sudo make test && sudo make install

sudo cp src/redis-server /usr/local/bin/ && sudo cp src/redis-cli /usr/local/bin/
sudo mkdir /etc/redis && sudo mkdir /var/redis
sudo cp utils/redis_init_script /etc/init.d/redis_6379
#sudo vi /etc/init.d/redis_6379
sudo cp redis.conf /etc/redis/6379.conf
sudo mkdir /var/redis/6379
sudo sed -i -e 's/'"daemonize no"'/'"daemonize yes"'/g' /etc/redis/6379.conf
tmp_logfile="logfile \"\""
tmp_logfile_n="logfile \"\/var\/log\/redis_6379.log\""
sudo sed -i -e 's/'"$tmp_logfile"'/'"$tmp_logfile_n"'/g' /etc/redis/6379.conf
sudo sed -i -e 's/'"dir .\/"'/'"dir \/var\/redis\/6379"'/g' /etc/redis/6379.conf

echo "--------------------------*.运行 Redis-----------------------------"
sudo update-rc.d redis_6379 defaults
sudo /etc/init.d/redis_6379 start
echo "*.运行 Redis成功"

echo "--------------------------*. 常用 Rabbitmq 指令-----------------------------"
if [[ $VERSION_V -ge 16 ]]; then
    echo "*.查看RabbitMQ服务状态：sudo systemctl status rabbitmq-server"
    echo "*.重启RabbitMQ：sudo systemctl restart rabbitmq-server"
elif [[ $VERSION_V -ge 14 ]]; then
    echo "# To start the service:"
    echo "service rabbitmq-server start"
    echo "# To stop the service:"
    echo "service rabbitmq-server stop"
    echo "# To restart the service:"
    echo "service rabbitmq-server restart"
    echo "# To check the status:"
    echo "service rabbitmq-server status"
else
    echo "old ubuntu system lower than 14.04,nothing to do."
fi

echo "--------------------------*.与Redis交互-----------------------------"
echo "/var/msgserver/redis-4.0.1/src/./redis-cli"
echo "参考:https://redis.io/download"


