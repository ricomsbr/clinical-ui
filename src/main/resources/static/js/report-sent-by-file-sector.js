(function ($, undefined) {
	
	/* Reset quadro de mensagens */
	$("#main-content").on("change", "#report-sent-by-file-sector-form #locationId", function (event) {
		$("#messageResult").empty();
		$("#locationId").parent().parent().removeClass("has-error");
	});
	
	$("#main-content").on("click", "#report-sent-by-file-sector-form #btnReportSentByFileSector", function (event) {
		event.preventDefault();
		
		$("#messageResult").empty();
		
		var locationId = parseInt($("#locationId").val());
		
		if(locationId || locationId > 0) {
			
			var initialDate = $("#initialDate").val();
			var finalDate = $("#finalDate").val();
			
		   	$.ajax({  
		        url : "report-sent-by-file-sector",   
		        method : "POST",
		        data: {initialDate : initialDate, finalDate : finalDate, locationId : locationId},
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
		} else {
			$("#messageResult").html('<p class="text-warning">Por favor, preencha o campo localização.</p>');
			$("#locationId").parent().parent().addClass("has-error");
		}
	});
	
})(jQuery);