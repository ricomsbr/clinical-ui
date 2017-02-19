(function ($, undefined) {
	
	/* Busca volumes no período indicado*/
	$("#main-content").on("click", "#receivedFormId #btnReportReceivedByFileSector", function (event) {
		event.preventDefault();
		
		$("#messageResult").empty();

		var initialDate = $("#initialDate").val();
		var finalDate = $("#finalDate").val();
		
	   	$.ajax({  
	        url : "report-received-by-file-sector",   
	        method : "POST",
	        data: {initialDate : initialDate, finalDate : finalDate},
	        success : function(response) {
	        	
	        	if(response.url) {
	        		window.location = response.url;
	        	} else {
	        		$("#messageResult").html('<p class="text-danger">'+response.messageError+'</p>');
	        	}
	        },  
	        error : function(e) {  
	        	$("#messageResult").html('<p class="text-danger">Sistema temporariamente indisponível, por favor, tente mais tarde.</p>');
	        }  
	    });
	});
		
})(jQuery);