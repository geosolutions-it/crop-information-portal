<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container width700">
	<form class="form-horizontal span6 ">
		<div class="control-group">
			<label class="control-label">Regions</label>
			<div class="controls">
				<c:forEach items="${regions.elements}" var="elem">
					<label class="radio"> <input type="radio" name="region"
						value="${elem.value}">${elem.label}
					</label>
				</c:forEach>
				<c:if test="${not empty regions.fileBrowser }">
					<label class="radio"> <input class="custom-selector"
						type="radio" name="region" value="file">Custom
					</label>
					<select name="region_file" id="region_file"
						class="input-block-level" disabled>
						<c:forEach items="${regions.fileBrowser.files}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">Mask</label>
			<div class="controls">
				<c:forEach items="${masks.elements}" var="elem">
					<label class="radio"> <input type="radio" name="mask"
						value="${elem.value}">${elem.label}
					</label>
				</c:forEach>
				<c:if test="${not empty masks.fileBrowser }">
					<label class="radio"> <input class="custom-selector"
						type="radio" name="mask" value="file">Custom
					</label>
					<select name="mask_file" id="mask_file" class="input-block-level"
						disabled>
						<c:forEach items="${masks.fileBrowser.files}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Granule</label>
			<div class="controls">

				<select name="mask_file" id="mask_file" class="input-block-level"
					disabled>
					<c:forEach items="${masks.fileBrowser.files}" var="file">
						<option value="${file}">${file}</option>
					</c:forEach>
				</select>
			</div>
			</div>
			<button class="btn pull-right" type="submit">Generate
				Statistics</button>
	</form>
</div>
<script>
	$(function() {

		$(':radio').on('change', function() {
			var category = $(this).val();
			var fileselector = $('#' + $(this).attr('name') + "_file");

			if (category == 'file') {
				fileselector.removeAttr('disabled');
			} else {
				// OR you can set attr to "" 
				fileselector.attr('disabled', '');
			}
		});

	})
</script>
