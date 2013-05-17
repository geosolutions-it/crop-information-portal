<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
<form>
<select name="region_file">
   <c:forEach items="${regions}" var="file">
       <option value="${file}">${file}</option>
   </c:forEach>
</select>
</form>
</div>

