var Brewer = Brewer || {};

Brewer.Multiselecao = (function() {

	function Multiselecao() {
		this.btnStatus = $(".js-btn-status");
		this.statusCheckbox = $(".js-status-checkbox");
		this.statusCheckboxTodos = $(".js-status-checkbox-todos");
	}
	
	Multiselecao.prototype.iniciar = function() {
		this.btnStatus.on("click", onBtnStatusClick.bind(this));
		this.statusCheckboxTodos.on("click", onStatusCheckboxTodosClick.bind(this));
		this.statusCheckbox.on("click", onStatusCheckboxClick.bind(this));
	}
	
	function onBtnStatusClick(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data("status");
		var url = botaoClicado.data("url");
		
		var checkboxSelecionados = this.statusCheckbox.filter(":checked");
		var usuariosId = $.map(checkboxSelecionados, function(checkbox){
			return $(checkbox).data("id");
		});
	
		if(usuariosId.length > 0) {
			$.ajax({
				url: url,
				method: "PUT",
				data: {
					usuariosId: usuariosId,
					status: status
				},
				success: function() {
					window.location.reload();
				}
			})
		}
	}
	
	function onStatusCheckboxTodosClick() {
		var status = this.statusCheckboxTodos.prop("checked");
		this.statusCheckbox.prop("checked", status);
		abilitarBotoesAccao.call(this, status);
	}
	
	function onStatusCheckboxClick() {
		var statusCheckboxSelecionados = this.statusCheckbox.filter(":checked");
		
		this.statusCheckboxTodos.prop("checked", statusCheckboxSelecionados.length == this.statusCheckbox.length);
		abilitarBotoesAccao.call(this, statusCheckboxSelecionados.length)
	}
	
	function abilitarBotoesAccao(selecionado) {
		selecionado ? this.btnStatus.removeClass("disabled") : this.btnStatus.addClass("disabled");
	}
	
	return Multiselecao;
		
}());


$(function() {
	var multiselecao = new Brewer.Multiselecao();
	multiselecao.iniciar();
});