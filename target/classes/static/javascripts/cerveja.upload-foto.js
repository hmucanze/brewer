var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {

	function UploadFoto(){
	
		this.uploadElement = $(".js-upload-element");
							
		this.inputNomeFoto = $("input[name=foto]");
		this.inputContentType = $("input[name=contentType]");
		
		this.htmlFotoCervejaTemplate = $("#foto-cerveja").html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate); 
		
		this.containerFotoCerveja = $(".js-container-foto-cerveja");
	}
	
	UploadFoto.prototype.iniciar = function(){
		var settings = {
			type: "json",
			allow: "*.(jpg|jpeg|png)",
			url: this.containerFotoCerveja.data("foto-url"),
			beforeSend: adicionarCsrfToken,
			complete: onCompleteUpload.bind(this)
		}
		
		UIkit.upload(this.uploadElement, settings);
		
		if(this.inputNomeFoto.val()) {
			var response = { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val() };
			onCompleteUpload.call(this, { response });
		}
	}
	
	function adicionarCsrfToken(xhr) {
		// var header = $('input[name="_csrf_header"]').val();
		var token = $('input[name="_csrf"]').val();
		
		xhr.headers = { 'X-CSRF-TOKEN' : token }	
	}
	
	function onCompleteUpload(obj) {
		var response = obj.response;
		
		this.inputNomeFoto.val(response.nome);
		this.inputContentType.val(response.contentType);
		this.uploadElement.addClass("hidden");
		
		var htmlFotoCerveja = this.template({ nomeFoto: response.nome });
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$(".js-button-remove-foto").on("click", onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto() {
		$(".js-foto-cerveja").remove();
		this.uploadElement.removeClass("hidden");
		this.inputNomeFoto.val("");
		this.inputContentType.val("");
	}
	
	return UploadFoto;

})(); 

$(function(){
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});