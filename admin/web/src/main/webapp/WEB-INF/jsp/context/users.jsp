<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<h2>Users</h2>

	<a data-toggle="modal" class="btn btn-success pull-right" href="create"
		data-target="#create">Create User</a>
	<table class="table table-hover">
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
					<td>${user.name}</td>
					<td>${user.role}</td>

					<td><c:if test="${user.name != 'admin'}">
							<a data-toggle="modal" class="btn" href="remote.html"
								data-target="#create">Edit</a>
							<a class="btn disabled" href="">Delete</a>
						</c:if></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div id="create" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">Create User</h3>
	</div>
	<form:form method="post" action="create" class="form-horizontal">
	<div class="modal-body">
		<p>One fine body</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		<button  type="submit" class="btn btn-primary">Create</button>
	</div>
	</form:form>
</div>