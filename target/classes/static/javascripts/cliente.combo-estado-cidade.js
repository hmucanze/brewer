var Brewer = Brewer || {};

Brewer.ComboEstado = (function() {
	function ComboEstado() {
		this.combo = $("#estado");
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboEstado.prototype.iniciar = function() {
		this.combo.on("change", onEstadoAlterado.bind(this));
	}
	
	function onEstadoAlterado() {
		this.emitter.trigger("alterado", this.combo.val())
	}
	
	return ComboEstado;

}());


Brewer.ComboCidade = (function() {
	function ComboCidade(comboEstado) {
		this.comboEstado = comboEstado;
		this.combo = $("#cidade");
		this.imgLoading = $(".js-img-loading");
		this.inputHiddenCidadeSelecionada = $("#inputHiddenCidadeSelecionada");
	}
	
	ComboCidade.prototype.iniciar = function() {
		reset.call(this);
		this.comboEstado.on("alterado", onEstadoAlterado.bind(this));
		var estadoSelecionado = this.comboEstado.combo.val();
		
		inicializarCidade.call(this, estadoSelecionado);
	}
	
	function onEstadoAlterado(event, estadoId) {
		this.inputHiddenCidadeSelecionada.val("");
		inicializarCidade.call(this, estadoId);
	}
	
	function inicializarCidade(estadoId) {
		if(estadoId) {
			resposta = $.ajax({
				url: this.combo.data("url"),
				method: "GET",
				contentType: "application/json",
				data: { "estado": estadoId },
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			
			resposta.done(onBuscarCidadesFinalizado.bind(this))
		}
		else {
			reset.call(this);
		}
	}
	
	function onBuscarCidadesFinalizado(cidades) {
		var options = [];
		cidades.forEach(function(cidade){
			options.push('<option value="' + cidade.id + '">' + cidade.nome + '</option>')
		});
		this.combo.html(options);
		this.combo.removeAttr("disabled");
		
		var cidadeSelecionadaId = this.inputHiddenCidadeSelecionada.val();
		if(cidadeSelecionadaId) {
			this.combo.val(cidadeSelecionadaId);
		}
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	function reset() {
		this.combo.html('<option value="">escolha uma cidade</option>');
		this.combo.val("");
		this.combo.attr("disabled", "disabled");
	}
	
	return ComboCidade;
}());

$(function() {
	var comboEstado = new Brewer.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.iniciar();
})