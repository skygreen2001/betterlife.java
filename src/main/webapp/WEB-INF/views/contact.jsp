<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" language="java" pageEncoding="UTF-8"%>
	<div class="container">
		<h2>添加联系人</h2>
		<div class="table-responsive"> 
			<form:form method="post" action="editContact">
			<table class="table table-striped table-bordered">
				<tbody>
				<tr>
					<td><form:label path="name">姓名</form:label></td>
					<td><form:input path="name" /></td> 
				</tr>
				<tr>
					<td><form:label path="mobile">手机号</form:label></td>
					<td><form:input path="mobile" /></td>
				</tr>
				<tr>
					<td><form:label path="phone">电话</form:label></td>
					<td><form:input path="phone" /></td>
				</tr>
				<tr>
					<td><form:label path="email">Email</form:label></td>
					<td><form:input path="email" /></td>
				</tr>
				</tbody>
			</table>
			<form:hidden path="contact_id" />
			<input type="submit" value="确认" class="btn btn-success"/>	
		    </form:form>
	    </div>
	</div><br/><br/><br/><br/>