package com.mucanze.brewer.service;

import com.mucanze.brewer.repository.UsuarioRepository;

public enum UsuarioStatus {
	
	ACTIVAR {
		@Override
		public void executar(Long[] usuariosId, UsuarioRepository usuarioRepository) {
			usuarioRepository.findByIdIn(usuariosId).forEach(u -> u.setActivo(true));
		}
	},
	
	DESACTIVAR {
		@Override
		public void executar(Long[] usuariosId, UsuarioRepository usuarioRepository) {
			usuarioRepository.findByIdIn(usuariosId).forEach(u -> u.setActivo(false));
		}
	};
	
	public abstract void executar(Long[] usuariosId, UsuarioRepository usuarioRepository);

}
