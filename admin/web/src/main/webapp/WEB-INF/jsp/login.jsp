<!DOCTYPE HTML>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>




	<div id="wrap">
		<div id="main">
			<div>
				<div class="container">
					<div class="container">
						<img id="topBanner" src="./img/frabanner.png">
					</div>
					<div id="progressbar" class="modal hide fade">
						<div class="modal-header"></div>
						<div class="modal-body">

							<div id="bar"
								class="ui-progressbar ui-widget ui-widget-content ui-corner-all"
								role="progressbar" aria-valuemin="0" aria-valuemax="100"
								aria-valuenow="0">
								<div
									class="ui-progressbar-value ui-widget-header ui-corner-left"
									style="display: none; width: 0%;"></div>
							</div>
						</div>
						<div class="modal-footer"></div>
					</div>

					<div class="row" style="margin-top: 5px">
						<div class="span6">
							<img src="${pageContext.request.contextPath}/img/fra2015logo.jpg">

							<div class="form-signin">
								<h2 class="form-signin-heading" data-i18n="login_title">
									<spring:message code="login.title" />
								</h2>
								<form action="<c:url value='j_spring_security_check' />"
									method="post" class="row-fluid" style="margin-bottom: 2px">


									<label class="span6"> <span
										style="font-weight: bold; font-size: 18px"
										class="form-signin-heading" data-i18n="User">User</span>: <br />
										<input id="usernameTextField" type="text" name='j_username'
										placeholder="Username">
									</label> <label class="span5"> <span
										style="font-weight: bold; font-size: 18px"
										data-i18n="Password">Password</span>: <br /> <input
										id="passwordTextField" type="password" name="j_password"
										placeholder="Password">
									</label> <label class="span7"
										style="position: relative; left: -10px; top: -10px;">
										<span style="font-weight: bold; font-size: 18px"
										data-i18n="login_select"><spring:message
												code="login.select" /></span>: <br> <select
										id="languageSelector" class="input-block-level" ONCHANGE="location = '?lang='+this.options[this.selectedIndex].value;">
											<option value="en" ${pageContext.response.locale=='en'?'selected':''}>English</option>
											<option value="fr" ${pageContext.response.locale=='fr'?'selected':''}>Français</option>
											<option value="es" ${pageContext.response.locale=='es'?'selected':''}>Español</option>
									</select>
									</label>



									<button type="submit" id="loginBtn"
										class="btn btn-large btn-primary pull-right"
										data-i18n="login_signin">
										<spring:message code="login.signin" />
									</button>
								</form>
							</div>





						</div>
						<div class="span6">
							<img src="img/cfrqlogin.jpg" />
							<div class="alert alert-error" style="margin-top: 6px">
								<strong>Warning.</strong> This is a demo version.
							</div>
						</div>
					</div>
						
					<div id="errorPanel">
							<c:if test="${not empty error}">
								<div class="errorblock alert alert-error">
									<spring:message code="login.fail"></spring:message>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
								</div>
							</c:if>
						</div>
				</div>




			</div>
			<!-- /container -->
		</div>
	</div>
	<div id="push"></div>
	
	<%@ include file="common/loginfooter.jsp"%>




	<script type="text/javascript">
		
	</script>



</body>
</html>