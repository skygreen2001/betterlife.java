<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" language="java" pageEncoding="UTF-8"%>
<br/><br/><br/><br/>
	<div class="container">
		<h1>
			Hello ${user.userName},欢迎进入本系统!  
		</h1>
		
		<P>服务器时间： ${serverTime}. </P>
	
	</div>
	
	<div class="container">
		<h2>通讯薄清单</h2>
		<p>共计${countContact}个联系人，创建通讯薄联系人清单列表如下：</p>      
		<div class="table-responsive">          
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>名称</th>
						<th>手机</th>
						<th>电话</th>
						<th>邮箱地址</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contacts}" var="contact">
					<tr>
						<td>${contact.name}</td>
						<td>${contact.mobile}</td>
						<td>${contact.phone}</td>
						<td>${contact.email}</td>
						<td><a class="btn btn-success" href="viewEditContact?id=${contact.contact_id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-success" href="deleteContact?id=${contact.contact_id}">删除</a></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<a class="btn btn-success" href="viewEditContact">新增联系人</a>
	</div>
    <br/><br/><br/>