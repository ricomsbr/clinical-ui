;(function ($, undefined) {
	$(function() { /* $.ready() - executa funções quando o DOM estiver totalmente carregado */
		
		$("#main-content").on("shown.bs.tab", "a[data-toggle]", function (e) {
			var target = $(e.target);
			var url = target.data('src');
			var target = target.data('target');
			$.get(url).then(function (data) {
				$(target).html(data);
			});
		});
		
		/* Confirma transação do volume */
		$("#main-content").on("submit",	"#confirm-receipt-form", function(event) {
			event.preventDefault();
			var transactionIds = $(this).serializeArray();
			$.post("transactions/confirm-transaction", transactionIds)
					.done(function(response, textStatus, jqXHR) {
						$('#main-content').html(response);
					}).fail(function(jqXHR, textStatus, errorThrown) {
						/* FIXME */
					});
		});
		
//		/* Confirma transação do volume */
//		$("#main-content").on("submit",	"#confirm-return-volume-form", function(event) {
//					event.preventDefault();
//					var volumeIds = $(this).serializeArray();
//					$.post("return-volume/confirm-return-to-file-sector", volumeIds)
//							.done(function(response, textStatus, jqXHR) {
//								$('#main-content').html(response);
//							}).fail(function(jqXHR, textStatus, errorThrown) {
//								/* FIXME */
//							});
//				});
		
		/* Seleciona ou não seleciona todos */
		$("#main-content").on("change", "#confirmation-transactions #tab-content #rowSelectAll", function (event) {
			
			var c = this.checked;
		    $('.row-select').prop('checked',c);
		    
		    countSelectedCheckBox();
		});
		
		/* Conta quantidade de selecionados ao mudar algum */
		$("#main-content").on("change", "#confirmation-transactions #tab-content .row-select", function (event) {
			
			var count = countSelectedCheckBox();
			verifyAllSelectedCheckBox(count);
		});
		
		
		$.validator.addMethod("dateITATime", function(value, element) {
			var values = value.split(" ");
			if(values.length <= 1) {
				return this.optional(element) || false;
			}
			var dateIsValid = jQuery.validator.methods.dateITA.call(this, values[0], element);
			var timeIsValid = this.optional(element) || /^([01]\d|2[0-3])(:[0-5]\d){1,2}(:[0-5]\d){1,2}$/.test(values[1]);
			return dateIsValid && timeIsValid;
		}, "Digite uma data e horário válidos. Formato: 00/00/0000 00:00:00");
		
		/**
		 *  Transposition 
		 *  
		 */
		/* Busca usuário para o setor selectionado */
		$("#main-content").on("change", ".select-destination-sector", function (event) {
			var elem = $(event.currentTarget);
			var request = {sectorId: elem.val(), volumeId: elem.data('volume-id')};
			var targetId = elem.data("target");
			var buttonId = elem.data("button");
			$("#messageResult").empty();
			$.get('addressess', request)
				.done(function (data, textStatus, jqXHR) {
					$(targetId).html(data).prop('disabled', false);
					$(buttonId).prop('disabled', true);
				})
				.fail(function (jqXHR, textStatus, errorThrown) {
					
				});
		});
		
		$("#main-content").on("change", ".select-destination-user", function (event) {
			$($(event.currentTarget).data('button')).prop('disabled', false);
		});
		
		$("#main-content").on("click.transposition", "button[data-type=reserve]", function (event) {
			var elem = $(event.currentTarget);
			var sectorId = elem.data("sector-id");
			var volumeId = elem.data("volume-id");
			var userId = elem.data("user-id");
			$("#sectorCombo" + volumeId).val(sectorId);
			$.get('addressess', {sectorId: sectorId, volumeId: volumeId})
			.done(function (data, textStatus, jqXHR) {
				$('#selectUserCombo' + volumeId).html(data).val(userId).prop('disabled', false);
				$('#button' + volumeId).prop('disabled', false);
			})
			.fail(function (jqXHR, textStatus, errorThrown) {
				
			});
		});
		
		
		/* Busca usuários para o setor selecionado */
		$("#main-content").on("change", ".select-sector", function (event) {
			var sectorId = $(this).val();
			var combo = $("#" + $(this).parent().attr('name')).children();
			
			$("#messageResult").empty();
			
			
			$.ajax({  
		        url : "search-user",   
		        method : "POST",
		        data: { sectorId : sectorId },
		        success : function(response) {
		        	
		        	var validationError = response.indexOf("validationId") > -1;
		        	if(validationError) {
		        		$("#messageResult").html('<p class="text-warning">Por favor, selecione um setor corretamente.</p>');
		        		$('html,body').animate({
		        	        scrollTop: $("#messageResult").offset().top},
		        	        'slow');
		        	} else {
		        		var containsError = response.indexOf("errorId") > -1;
			        	if(containsError) {
			        		$(combo).val(0);
			        		$(combo).attr('disabled','disabled');
			        		$("#messageResult").html('<p class="text-warning">Nenhum usuário encontrado para esse setor</p>');
			        		$('html,body').animate({
			        	        scrollTop: $("#messageResult").offset().top},
			        	        'slow');
			        	} else {
			        		$(combo).empty();
				        	$(combo).html('<option value="0" selected="selected">Selecione um usuário</option>');
				        	$(combo).append(response);
				        	$(combo).removeAttr('disabled');
			        	}
		        	}
		        },  
		        error : function(e) {  
		        	alert('Error: ' + e);   
		        }  
		    });
		});
		
		/* Busca usuários para o setor selecionado */
		$("#main-content").on("change", ".select-sector-filtered", function (event) {
			var sectorId = $(this).val();
			var combo = $("#" + $(this).parent().attr('name')).children();
			
			$("#messageResult").empty();
			
			
			$.ajax({  
		        url : "search-user-filtered",   
		        method : "POST",
		        data: { sectorId : sectorId },
		        success : function(response) {
		        	
		        	var validationError = response.indexOf("validationId") > -1;
		        	if(validationError) {
		        		$("#messageResult").html('<p class="text-warning">Por favor, selecione um setor corretamente.</p>');
		        		$('html,body').animate({
		        	        scrollTop: $("#messageResult").offset().top},
		        	        'slow');
		        	} else {
		        		var containsError = response.indexOf("errorId") > -1;
			        	if(containsError) {
			        		$(combo).val(0);
			        		$(combo).attr('disabled','disabled');
			        		$("#messageResult").html('<p class="text-warning">Nenhum usuário encontrado para esse setor</p>');
			        		$('html,body').animate({
			        	        scrollTop: $("#messageResult").offset().top},
			        	        'slow');
			        	} else {
			        		$(combo).empty();
				        	$(combo).html('<option value="0" selected="selected">Selecione um usuário</option>');
				        	$(combo).append(response);
				        	$(combo).removeAttr('disabled');
			        	}
		        	}
		        },  
		        error : function(e) {  
		        	alert('Error: ' + e);   
		        }  
		    });
		});
		
		/* Conta quantidade de selecionados*/
		function countSelectedCheckBox() {
			
			var i = 0;
			
			$('input:checkbox:checked.row-select').each(function() {
				i++;
			});
			
			$("#selectedCount").html(i);
			
			return i;
		}
		
		/* Marca select all para (un)checked*/
		function verifyAllSelectedCheckBox(count) {
			
			if(count == parseInt($("#selectedAllCount").val())) {
				$('#rowSelectAll').prop('checked',true);
			} else {
				$('#rowSelectAll').prop('checked',false);
			}
		}
		
		function hideElm ($elm) {
			if ($elm.hasClass("hidden")) {return;}
			$elm.addClass("hidden");
		}
		
		function showElm ($elm) {
			if (!$elm.hasClass("hidden")) {return;}
			$elm.removeClass("hidden");
		}
		
		function resetVolumeInfo() {
			$("#messageResult").empty();
			$("#searchResult").empty();
		}
		
		function resetFolderTypeCombo() {
			
			$("#folderTypeCombo").empty();
			
			var reset = '<select class="form-control" disabled="disabled" id="folderTypeId" name="folderTypeId">' +
						'<option value="0" disabled="disabled" selected="selected">Selecione o tipo da pasta</option>' +
						'</select>';
						
			$("#folderTypeCombo").html(reset);
			
		}
		
		function resetVolumeNumberCombo() {
			
			$("#volumeNumberCombo").empty();
			
			var reset = '<select class="form-control" disabled="disabled" id="volumeNumber" name="volumeNumber">' +
						'<option value="0" disabled="disabled" selected="selected">Selecione o número do volume</option>' +
						'</select>';
						
			$("#volumeNumberCombo").html(reset);
			
		}
	});
	
})(jQuery);

function cancelRequest(requestId) {
	
	$("#messageResult").empty();
	
    $.ajax({  
        url : "requests/cancel-request",   
        method : "POST",
        data: {requestId : requestId},
        success : function(response) {
        	$( ".modal-backdrop" ).remove();
       		$("#panel-body-pending-request-id").html(response);
        },  
        error : function(e) {  
        	$( ".modal-backdrop" ).remove();
        	$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
        	$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
        }  
    });
}

function cancelReserve(requestId) {
	
	$("#messageResult").empty();
	
    $.ajax({  
        url : "reserves/cancel-reserve",   
        method : "POST",
        data: {requestId : requestId},
        success : function(response) {
        	$( ".modal-backdrop" ).remove();
       		$("#panel-body-reserves-id").html(response);
        },  
        error : function(e) {  
        	$( ".modal-backdrop" ).remove();
        	$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
        	$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
        }  
    });
}

function cancelTransaction(transactionId) {
	
	$("#messageResult").empty();
	
    $.ajax({  
        url : "transactions/cancel-transaction",   
        method : "POST",
        data: {transactionId : transactionId},
        success : function(response) {
        	$( ".modal-backdrop" ).remove();
       		$("#confirmation-transactions").html(response);
        },  
        error : function(e) {  
        	$( ".modal-backdrop" ).remove();
        	$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
        	$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
        }  
    });
}

function cancelTransposition(transactionId) {
	
	$("#messageResult").empty();
	$.post('transactions/cancel-transposition', {id: transactionId})
		.done(function (response) {
       		$("#tab-content").html(response);
		})
		.fail(function (jqXHR, textStatus, errorThrown) {
			$( ".modal-backdrop" ).remove();
        	$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
        	$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
		})
		.always(function () {
			$( ".modal-backdrop" ).remove();
		});
}

function cancelReturnTransposition(transactionId) {
	
	$("#messageResult").empty();
	$.post('return-volume/cancel-transposition', {id: transactionId})
		.done(function (response) {
       		$("#tab-content").html(response);
		})
		.fail(function (jqXHR, textStatus, errorThrown) {
			$( ".modal-backdrop" ).remove();
        	$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
        	$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
		})
		.always(function () {
			$( ".modal-backdrop" ).remove();
		});
}

function transpose(id) {
	var userId = $('#selectUserCombo' + id).val();
	
	var btn = $('#button'+id).button('loading');
	$("#messageResult").empty();
	
	$.post('transpose-volume', {volumeId: id, userId: userId})
		.done(function (data, textStatus, jqXHR) {
			$("#messageResult").html(data);
			$('#row'+id).fadeOut().remove();
			var count = $('#panel-body-transposition-volume-id').find("tr[id^='row']").length;
			if(count == 0) {
				
				var currentPage = $("#currentPage").val() - 1;
				var currentSize = $("#currentSize").val();
				
				if(currentPage < 0) {
					currentPage = 0;
				} 
				
				var url = "my-responsibility-page?page="+currentPage+"&size="+currentSize;

				$.get(url)
					.done(function (data) {
						$('#panel-body-transposition-volume-id').html(data);
					});
			}
		})
		.fail(function (jqXHR, textStatus, errorThrown) {
			$("#messageResult").html('<p class="text-danger">Erro no sistema, por favor, tente mais tarde</p>');
			$('html,body').animate({
    	        scrollTop: $("#messageResult").offset().top},
    	        'slow');
		});
}

function selectUserToReserve() {
	var elem = $(this);
	var sectorId = elem.data('sector-id');
	var volumeId = elem.data('volume-id');
	var userId = elem.data('user-id');
	$("#sectorCombo" + volumeId).val(sectorId);
	$.get('addressess', {sectorId: sectorId, volumeId: volumeId})
	.done(function (data, textStatus, jqXHR) {
		var combo = $('#selectUserCombo'+ volumeId);
    	combo.html(data);
    	combo.val(userId);
    	combo.prop('disabled', false);
	})
	.fail(function (jqXHR, textStatus, errorThrown) {
		
	});
}
