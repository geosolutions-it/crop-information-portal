<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">id</label>
		 <div class="controls">
			<input id="name" name="name" type="text" value="${crop.id}" data-required  ${context=='create' ? '':'disabled'}	>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" >Label</label>
		 <div class="controls">
			<input id="label" name="label" type="text" value="${crop.label}" data-required  ${context=='create' ? '':'disabled'} >
		</div>
	</div>
	
	<div class="control-group">
		 <label class="control-label  " >Season</label>
		 <div class="controls checkbox">
				<input type="checkbox" name="season" value="RABI">Rabi</input><br/>
				<input type="checkbox" name="season" value="KHARIF">Kharif</input>
		</div>
	</div>

 