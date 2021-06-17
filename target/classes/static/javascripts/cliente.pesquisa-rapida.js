var Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function() {
	
	PesquisaRapidaCliente = function() {
		this.inputNomeCliente = $("#nomeClienteModal");
		this.btnPesquisaCliente = $(".js-pesquisa-rapida-cliente-btn");
		this.modalPesquisaCliente = $("#pesquisaRapidaClientes");
		this.mensageErroAlert = $(".js-mensagem-erro");
		this.containerTabelaPesquisa = $("#containerTabelaPequisaRapidaClientes");
		this.htmlTabelaPesquisa = $("#tabela-pesquisa-rapida-cliente").html();
		
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
	}
	
	PesquisaRapidaCliente.prototype.iniciar = function() {
		this.btnPesquisaCliente.on("click", onBtnPesquisaClick.bind(this))
	}
	
	function onBtnPesquisaClick(event) {
		event.preventDefault();
		$.ajax({
			url: this.modalPesquisaCliente.find("form").attr("action"),
			method: "GET",
			contentType: "application/json",
			data: {
				nome : this.inputNomeCliente.val()
				},
			success: onPesquisaConcluido.bind(this),
			error: onErroPesquisa.bind(this)
		})
	}
	
	function onPesquisaConcluido(resultado) {
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		this.mensageErroAlert.addClass("hidden");
	}
	
	function onErroPesquisa() {
		this.mensageErroAlert.removeClass("hidden");
	}
	
	return PesquisaRapidaCliente;
}());

$(function() {
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});