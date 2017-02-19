(function ($, undefined) {
	
	/* Busca tipo de pasta pelo número do processo */
	var promise = null;
	$("#main-content").on("keyup", "#restriction-form #processNumber", function (event) {
		if (promise && promise.state() == 'pending') {
			event.preventDefault();
			return;
		}
		
		resetVolumeInfo();
		resetFolderTypeCombo();
		$("#process-number-group").removeClass("has-error");
						
		var processNumber = $("#processNumber").val().replace(/[\/-]/g, '');
		var isNumeric = $.isNumeric(processNumber); 
		
		if (isNumeric && processNumber.length < 10) { return; }
		
		if (!isNumeric || processNumber.length > 10) {
			$("#process-number-group").addClass("has-error");
			return;
		}
		
		promise = $.get('folders', {number: processNumber})
			.done(function (response, textStatus, jqXHR) {
				$('#folderTypeId').html(response).prop('disabled', false).focus();
			})
			.fail(function (jqXHR, textStatus, errorThrown) {
				$('#messages').html(jqXHR.responseText);
			});
	});
	
	/* Solicita painel de restrições para a pasta informada */
	$("#main-content").on("change", "#restriction-form #folderTypeId", function (event) {
		resetVolumeInfo();
		var process = $("#processNumber").val().replace(/[\/-]/g, '');
		var folderTypeId = $("#folderTypeId").val();
		
		$.get("restrictions", {processNumber: process, folderTypeId: folderTypeId})
			.done(function (response, textStatus, jqXHR) {
				$('#group-panel').html(response);
			})
			.fail(function (jqXHR, textStatus, errorThrown) {
				$('#messages').html(jqXHR.responseText);
			});
		
	});
		
	/* Salva opção de restrição */
	$("#main-content").on("submit", "#restriction-form", function (event) {
		event.preventDefault();
		
		var processNumber = $("#processNumber").val().replace(/[\/-]/g, '');
		$('#processNumber').val(processNumber);
		
		var request = $(event.currentTarget).serialize();
		$.post('restrictions-new', request)
			.done(function (response, textStatus, jqXHR) {
				$('#main-content').empty().html(response);
			})
			.fail(function (jqXHR, textStatus, errorThrown) {
				$('#messages').html(jqXHR.responseText);
			});
	});
	
	/* Remove restrição */
	$("#main-content").on("click", "#removeRestrictionButton", function (event) {
		event.preventDefault();

		var processNumber = $("#processNumber").val().replace(/[\/-]/g, '');
	    var folderTypeId = $("#folderTypeId").val();
	    
	    $.post('restrictions-remove', {processNumber: processNumber, folderTypeId: folderTypeId})
	    	.done(function (response, textStatus, jqXHR) {
	    		$('#main-content').html(response);
	    	})
	    	.fail(function (jqXHR, textStatus, errorThrown) {
	    		$('#messages').html(jqXHR.responseText);
	    	});
	});
	
	$("#main-content").on("click", "#add-restriction-user", function(event) {
		event.preventDefault();
		var userId = $("#selectUserCombo").val();
		var processNumber = $("#processNumber").val().replace(/[\/-]/g, '');
	    var folderTypeId = $("#folderTypeId").val();
	    var userName = "";
	    $("#selectUserCombo option:selected").each(function(){
	    	userName += $(this).text();
	    });
	    
	    var sectorName = "";
	    
	    $("#sectorCombo option:selected").each(function(){
	    	sectorName += $(this).text();
	    });
	    
	    var params = {userId: userId, processNumber: processNumber, folderTypeId: folderTypeId, groupId: -1};
	    var groupId = 0;
	    if($('button.btn-remove-user').length) {
	    	var url = $('button.btn-remove-user:first').val();
	    	var values = url.split('/');
	    	groupId = values[1];
	    	params.groupId = groupId;
	    }
	    
	    
	    if(!$('#userRestrictionList tbody tr').has('#btn-remove-user-' + userId).length) {
			$.post('add-restrictions-user', params)
	    	.done(function (response, textStatus, jqXHR) {
	    		var groupId = response.id;
	    		
		    		var tr = $('<tr></tr>');
		    		var numero = 1;
		    		if($('#userRestrictionList').has('strong.numberRow').length) {
		    			numero = parseInt($('#userRestrictionList strong.numberRow:last').text()) + 1;
		    		} 
		    		
		    		var coluna = $('<td><strong class="numberRow">' + numero + '</strong></td>');
		    		tr.append(coluna);
		    		
		    		var coluna2 = $('<td>' + userName + '</td>');
		    		tr.append(coluna2);
		    		
		    		var coluna3 = $('<td>' + sectorName + '</td>');
		    		tr.append(coluna3);
		    		
		    		var button = $('<button class="btn btn-danger btn-remove-user" id="btn-remove-user-' + userId + '" value="remove-user-restriction/' + groupId + '/' + userId + '">Remover</button>');
		    		var coluna4 = $('<td></td>');
		    		coluna4.append(button);
		    		tr.append(coluna4);
		    		
		    		$('#userRestrictionList tbody').append(tr);
	    		
	    	})
	    	.fail(function (jqXHR, textStatus, errorThrown) {
	    		
	    	});
	    }
		
	});
	
	$("#main-content").on("click", ".btn-remove-user", function(event) {
		event.preventDefault();
		var button = $(this);
		var url = button.val();
		var processNumber = $("#processNumber").val().replace(/[\/-]/g, '');
	    var folderTypeId = $("#folderTypeId").val();
		var params = {processNumber: processNumber, folderTypeId: folderTypeId};
		
		$.post(url, params).done(function(response, textStatus, jqXHR){
			button.parent().parent().remove();
		});
		
	});
	
	$("#main-content").on("click", ".collapseRestrictionForm", function(event){
		event.preventDefault();
		toogleChevronIcon($(this));
	});
	
	$("#main-content").on("click", ".collapseRestrictionForm span", function(event){
		event.preventDefault();
		$($(this).parent().attr("href")).collapse('toggle');
	});
	
	function toogleChevronIcon(sidebar) {
		
		var span = sidebar.find("span");
		if(span.hasClass("glyphicon-chevron-down")) {
			span.replaceWith('<span class="glyphicon glyphicon-chevron-up pull-right"></span>')
		} else {
			span.replaceWith('<span class="glyphicon glyphicon-chevron-down pull-right"></span>')
		}
		
	}
	
	
	function resetVolumeInfo() {
		$("#messages").empty();
		$("#group-panel").empty();
	}
	
	function resetFolderTypeCombo() {
		$('#folderTypeId').val(0).prop('disabled', true);
	}
	
})(jQuery);