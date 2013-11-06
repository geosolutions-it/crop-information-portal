/**
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *  Create a footer for the application
 *
 * @author Alejandro Diaz
 */
$(function() {

	var idFooter = "#footer";
	var idDialog = "#creditsDialog";
	var idOpener = "#creditsOpener";
	var idCloser = "#creditsDialog button";
	var idBackDrop = "#hiddenDiv";

	/**
	 *	api: method[jqueryMapstorFooter]
	 * 	Create a footer like mapstore with a 'Credits' button
	 *  idFooter ´´String´´ footer div to put at the end of the page
	 *  idDialog ´´String´´ dialog to show with a fade in
	 *  idOpener ´´String´´ button to handle the dialog open
	 *  idCloser ´´String´´ button to handle the dialog close
	 *  idBackDrop ´´String´´ div to show modal backrop
	 **/
	function jqueryMapstorFooter(idFooter, idDialog, idOpener, idCloser, idBackDrop){

		function changeFooterMargin(idFooter){
			var footer = $(idFooter);
			var pos = footer.position();
			var height = $(window).height();
			height = height - pos.top;
			height = height - footer.height();
			    if (height > 0) {
				  footer.css({'margin-top' : height-2+'px'});
			  }
		}

		$(idOpener).click(function() {
	      var creditsDialog = $(idDialog)[0];
		  creditsDialog.className = creditsDialog.className.replace("hide fade", "fade in");
		  creditsDialog.className = creditsDialog.className.replace("fade out", "fade in");
		  $(idBackDrop)[0].className = "modal-backdrop";
	    });
	    
		$(idCloser).on("click", function() {
			var creditsDialog = $(idDialog)[0];
			creditsDialog.className = creditsDialog.className.replace("fade in", "fade out");
			$(idBackDrop)[0].className = "";
		});

		// we need to calculate twice to put in correct position
		changeFooterMargin(idFooter);
		changeFooterMargin(idFooter);

		$(window).bind("resize", function() {
			changeFooterMargin(idFooter);
		});
	}

	// Create footer
	jqueryMapstorFooter(idFooter, idDialog, idOpener, idCloser, idBackDrop);
});