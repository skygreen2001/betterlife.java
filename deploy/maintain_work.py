#!/usr/bin/python
#coding=utf-8

import urllib2
import sys, os
import subprocess

import smtplib
from email.mime.text import MIMEText
from email.header import Header

watch_server     = 'http://www.bb.com/'
action_exception = 'service nginx stop'
is_bash_file     = False # True  是否执行bash 命令行文件
bash_file        = './test.sh' # 如果is_bash_file 执行bash文件的话，执行该bash文件
mail = {
    'host': 'smtp.mxhichina.com',
    'port': '465', # 25: SMTP 端口号, 465: ssl 端口
    'user': 'cms@bb.com', #用户名
    'pass': 'Aa12345678', #口令
    'is_ssl'   : True,
    'sender'   : 'cms@bb.com',
    'receivers': ['skygreen@qq.com', 'skygreen2001@sohu.com'],  # 接收邮件，可设置为你的QQ邮箱或者其他邮箱
    
    'subject'  : '生产服务器tomcat发生异常',
    'content'  : 'bb apache 挂了,需要尽快进行处理！',
    'header': {
        'from'   : '上海BB网络科技有限公司',
        'to'     : 'BB科技IT运维组'
    }
}

try:
    response = urllib2.urlopen(watch_server)
    html = response.read()
    print html
except urllib2.HTTPError as e:
    if e.code == 502:
        # os.system(action_exception)
        if (is_bash_file):    
            os.system('chmod -R 755 ./')
            subprocess.call(bash_file)
        
        print '关闭 nginx 成功'
        try:
            if (mail['is_ssl']):
                smtpObj = smtplib.SMTP_SSL()
            else:
                smtpObj = smtplib.SMTP()
            
            smtpObj.connect(mail['host'], mail['port'])    
            smtpObj.login(mail['user'],mail['pass'])
                        
            # 三个参数：第一个为文本内容，第二个 plain 设置文本格式，第三个 utf-8 设置编码
            message = MIMEText(mail['content'], 'plain', 'utf-8')
            message['Subject'] = Header(mail['subject'], 'utf-8')
            message['From'] = Header(mail['header']['from'], 'utf-8')   # 发送者
            message['To'] =  Header(mail['header']['to'], 'utf-8')        # 接收者
            
            smtpObj.sendmail(mail["sender"], mail["receivers"], message.as_string())
            print "邮件发送成功"
        except smtplib.SMTPException as e:
            print "Error: 无法发送邮件:" + str(e.reason)
    else:
        print '服务端发生其它异常: ' + str(e.reason)
except urllib2.URLError as e:
    print '连接服务器失败.'
    print '失败原因: ', e.reason
else:
    print  'everything is good'