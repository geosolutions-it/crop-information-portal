<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">User</label>
		 <div class="controls">
			<form:input  path="user.name"></form:input>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" for="inputEmail">Role</label>
		 <div class="controls">
			<form:select  path="user.role">
				<option value="ADMIN">Admin</option>
				<option value="USER" selected>User</option>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" for="inputEmail">Password</label>
		 <div class="controls">
			<form:input type="password" path="user.newPassword"></form:input>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" for="inputEmail">Confirm Password</label>
		 <div class="controls">
			<input type="password" path=""></input>
		</div>
	</div>

 