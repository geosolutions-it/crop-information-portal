<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/js/bootstrap-datepicker.js"/>"></script>
<link href="<c:url value="/css/datepicker.css"/>" media="all"
	type="text/css" rel="stylesheet">

<div class="container width700" id="statistics_container">
	<form class="form-horizontal span6 " method="post" action="../operation/NDVIStatistics" id="statistics_form">
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
						<c:forEach items="${regions.fileBrowser.fileNames}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Masks</label>
			<div class="controls">
				<c:forEach items="${masks.elements}" var="elem">
					<label class="radio"> <input type="radio" name="mask"
						value="${elem.value}">${elem.label}
					</label>
				</c:forEach>
				<label class="radio"> <input type="radio" name="mask"
						value="disabled" checked="checked">Disabled
					</label>
				<!-- Disable custom mask
				<c:if test="${not empty masks.fileBrowser }">
					<label class="radio"> <input class="custom-selector"
						type="radio" name="mask" value="file">Custom
					</label>
					<select name="mask_file" id="mask_file" class="input-block-level"
						disabled>
						<c:forEach items="${masks.fileBrowser.fileNames}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>-->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Dekad</label>
			<div class="controls">

				<span class="input-append " data-date-format="yyyy mm">
					<input id="granule_mounth" class="span2" size="16" type="text" name="month">
					<span class="add-on"><i class="icon-calendar"></i></span>
				</span>
				<select class="input-small" name="dekad" id="dekad">
					<option>1</option>
					<option>2</option>
					<option>3</option>
				</select>
			</div>
		</div>
		<button class="btn pull-right" type="submit" id="statistics_button">Generate Statistics</button>
	</form>
</div>

<div id="ndvi" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">NDVI</h3>
	</div>
	<div class="modal-body">
	</div>
</div>

	
<script type="text/javascript">
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

		$('#granule_mounth').datepicker({
			format : "yyyy-mm",
			viewMode : "months",
			minViewMode : "months"
		});
	});
	    
	$("#statistics_form").submit(function() {
		var options = {
			success: function(html) {
				$("#ndvi .modal-body").replaceWith($('.modal-body', $(html)));
				var link =  $('#ndvi .modal-body a')[0];
				if(link){
					var url =  link.href;
					if(url.indexOf('/operationManager/flowstatus/?id') < 0
						&& url.indexOf('/flowstatus/?id') > -1){
						url = url.replace("/flowstatus/?id", '/operationManager/flowstatus/?id');
					}
					link.href = url;
					$('#ndvi .modal-body a').replaceWith(link);
				}
				var ndvi = $("#ndvi")[0];
				ndvi.className = ndvi.className.replace("hide fade", "fade in");
				ndvi.className = ndvi.className.replace("fade out", "fade in");
				$('#hiddenDiv')[0].className = "modal-backdrop";
			},
			failure: function(html) {
				$("#ndvi .modal-body").replaceWith($('.modal-body', $(html)));
				var ndvi = $("#ndvi")[0];
				ndvi.className = ndvi.className.replace("hide fade", "fade in");
				ndvi.className = ndvi.className.replace("fade out", "fade in");
				$('#hiddenDiv')[0].className = "";
			}
		};

		$(this).ajaxSubmit(options);
		return false;
	});


	    
	$("#ndvi button").on("click", function() {
		var ndvi = $("#ndvi")[0];
		// ndvi.fadeIn();
		ndvi.className = ndvi.className.replace("fade in", "fade out");
		$('#hiddenDiv')[0].className = "";
	});

</script>