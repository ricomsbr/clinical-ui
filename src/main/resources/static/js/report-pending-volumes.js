(function ($, undefined) {

	module(ReportPendingVolumes);
	
	function ReportPendingVolumes(api) {
		
		/* Reset quadro de mensagens */
		$("#main-content").on("change", "#report-pending-volumes-form #locationId", function (event) {
			$("#messageResult").empty();
			$("#locationId").parent().parent().removeClass("has-error");
		});
		
		/* Reset quadro de mensagens */
		$("#main-content").on("click", "#report-pending-volumes-form #option1", function (event) {
			$("#messageResult").empty();
			$("#option1").parent().parent().parent().removeClass("has-error");
		});
		
		/* Reset quadro de mensagens */
		$("#main-content").on("click", "#report-pending-volumes-form #option2", function (event) {
			$("#messageResult").empty();
			$("#option1").parent().parent().parent().removeClass("has-error");
		});
		
		$("#main-content").on("click", "#report-pending-volumes-form #btnReportPengindVolumes", function (event) {
			event.preventDefault();
			
			$("#messageResult").empty();
			var hasError = false;
			var messageError = "";
			
			var locationId = parseInt($("#locationId").val());
			if(!locationId || locationId <= 0) {
				hasError = true;
				messageError = '<p class="text-warning">Por favor, preencha o campo localização.</p>';
				$("#locationId").parent().parent().addClass("has-error");
			}
			
			var optionId = $('input[name=options]:checked').val();
			if(!optionId) {
				hasError = true;
				messageError = messageError.concat('<p class="text-warning">Por favor, preencha o campo opção.</p>');
				$("#option1").parent().parent().parent().addClass("has-error");
			}
			
			if(!hasError) {
				var initialDate = $("#initialDate").val();
				var finalDate = $("#finalDate").val();
				
			   	$.ajax({  
			        url : "report-pending-volumes",   
			        method : "POST",
			        data: {initialDate : initialDate, finalDate : finalDate, locationId : locationId, optionId : optionId},
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
				$("#messageResult").html(messageError);
			}
		});
	}
	
})(jQuery);