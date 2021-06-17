var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $(".js-radio-tipo-pessoa");
		this.labelCpfCnpj = $("[for=cpfOuCnpj]"); 
		this.inputCpfCnpj = $("#cpfCnpj");
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on("change", onTipoPessoaAlterado.bind(this));
		var tipoPessoaSelecionada = this.radioTipoPessoa.filter(":checked")[0];
		
		if(tipoPessoaSelecionada) {
			aplicarMascara.call(this,$(tipoPessoaSelecionada));
		}
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		aplicarMascara.call(this,tipoPessoaSelecionada);
		
		this.inputCpfCnpj.val("");
	}
	
	function aplicarMascara(tipoPessoaSelecionada) {
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data("documento"));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data("mascara"));
		this.inputCpfCnpj.removeAttr("disabled");
	}
	
	return MascaraCpfCnpj;
}());

Brewer.MascaraCep = (function() {
	function MascaraCep() {
		this.inputCep = $(".js-input-cep");
	}
	
	MascaraCep.prototype.iniciar = function() {
		this.inputCep.mask("00000-000")
	}
	
	return MascaraCep;
}());

$(function() {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
	
	var mascaraCep = new Brewer.MascaraCep();
	mascaraCep.iniciar();
});