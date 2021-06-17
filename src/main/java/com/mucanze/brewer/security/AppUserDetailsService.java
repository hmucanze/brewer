package com.mucanze.brewer.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioRetornado = usuarioRepository.findByEmailAndActivo(email);
		
		Usuario usuario = usuarioRetornado.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorrectos"));
		
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		List<String> permissoes = usuarioRepository.permissoes(usuario);
		
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
		
		return authorities;
	}

}
