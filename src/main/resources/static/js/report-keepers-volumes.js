(function ($, undefined) {
	
	/* Busca volumes no período indicado*/
	$("#main-content").on("submit", "#report-keepers-volumes-form", function (event) {
		event.preventDefault();
		
		$("#messageResult").empty();

		var params = {
				initialBoxNumber: $('#initialBoxNumber').val(),
				finalBoxNumber: $('#finalBoxNumber').val()
		};
		var url = $('#keepers-form').attr('action');
		var btn = $('#btnReportKeepersVolumes').button('loading');
	    
		$.get(url, params).then(success, failure);
		
		function success(response, textStatus, jqXHR) {
			$('#main-content').html(response).find('[autofocus]').focus();
		}
		
		function failure(jqXHR, textStatus, errorThrown) {
			$('#messageResult').html(jqXHR.responseText);
		}
	});
		
})(jQuery);