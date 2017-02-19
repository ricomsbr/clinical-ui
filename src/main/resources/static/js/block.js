(function ($, undefined) {
	
	/* Salva motivos de bloqueio */
	$("#main-content").on("submit", "#block-form", function (event) {
		event.preventDefault();
		
		var btn = $('#button-block-reason').button('loading');
		
		var formData = $("#block-form").serializeArray();
		
		$.post("block-new",formData).done(function (response) {
			$('#main-content').html(response);
			btn.button('reset');
		}).fail(function (response) {
			alert(response);
			$('#messageResult').html("<p class='text-danger'>Sistema temporariamente indisponível. Por favor, tente mais tarde.</p>");
			btn.button('reset');
		});
	});
	
	/* Adiciona motivo de bloqueio */
	$("#main-content").on("click", "#button-add-reason", function (event) {
		event.preventDefault();
		
		var formData = $("#block-form").serializeArray();
		
		$.post("block-add-reason",formData).done(function (response) {
			$('#main-content').html(response);
		});
	});
	
})(jQuery);