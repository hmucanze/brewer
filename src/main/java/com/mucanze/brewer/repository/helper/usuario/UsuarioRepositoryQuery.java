package com.mucanze.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmailAndActivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable);

}
