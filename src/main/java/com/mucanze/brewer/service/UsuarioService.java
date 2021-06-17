package com.mucanze.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.repository.UsuarioRepository;
import com.mucanze.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.mucanze.brewer.service.exception.SenhaUsuarioObrigatoriaException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioRetornado = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		if(usuarioRetornado.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("Email já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaUsuarioObrigatoriaException("Senha obrigatória para novo usuário");
		}
		
		if(usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		usuarioRepository.save(usuario);
	};
	
	@Transactional
	public void actualizarStatus(Long[] usuariosId, UsuarioStatus usuarioStatus) {
		usuarioStatus.executar(usuariosId, usuarioRepository);
	}

}
