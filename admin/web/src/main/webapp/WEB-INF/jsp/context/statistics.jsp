<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container width700">
<form class="form-horizontal span6 ">
	<div class="control-group">
		 <label class="control-label">Regions</label>
		 <div class="controls">
			<select name="region_file" class="input-block-level">
			<c:forEach items="${regions}" var="file">
				<option value="${file}">${file}</option>
			</c:forEach>
		</select> 
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label">Mask</label>
		 <div class="controls">
			<select name="crop_file" class="input-block-level">
			<c:forEach items="${masks}" var="mask">
				<option value="${mask}">${mask}</option>
			</c:forEach>
			</select>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" >Granule</label>
		 <div class="controls">
			<select name="crop_file" class="input-block-level">
			<!-- TODO -->
			</select>
		</div>
	</div>
	<button class="btn pull-right" type="submit">Generate Statistics</button>
		
	</form>
</div>

