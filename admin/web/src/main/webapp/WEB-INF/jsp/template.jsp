<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Login Page</title>
<link href="css/bootstrap.min.css" media="all" type="text/css"
	rel="stylesheet">
<link href="css/nrl.css" media="all" type="text/css"
	rel="stylesheet">
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
	<%@ include file="common/banner.jsp"%>
	<div class="container-flow">
	
	<%@ include file="common/navbar.jsp"%>
	<jsp:include page="context/${context}.jsp" />

	</div>
</body>
</html>