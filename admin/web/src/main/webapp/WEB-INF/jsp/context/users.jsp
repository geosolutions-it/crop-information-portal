<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<table class="table">
		
		      
		   

		
		<thead>
			<tr>
				<th>#</th>
				<th>name</th>
				<th>role</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td> ${user.name} </td>
				<td> ${user.role} </td>
				<td><a class="btn" href="<c:url value="users/remove/1"/>">Edit</a>
				<a class="btn disabled" href="">Delete</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>