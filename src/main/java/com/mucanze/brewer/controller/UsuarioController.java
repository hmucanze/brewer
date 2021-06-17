package com.mucanze.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mucanze.brewer.controller.page.PageWrapper;
import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.repository.GrupoRepository;
import com.mucanze.brewer.repository.UsuarioRepository;
import com.mucanze.brewer.repository.filter.UsuarioFilter;
import com.mucanze.brewer.service.UsuarioService;
import com.mucanze.brewer.service.UsuarioStatus;
import com.mucanze.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.mucanze.brewer.service.exception.SenhaUsuarioObrigatoriaException;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, @PageableDefault(size = 2) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		
		mv.addObject("grupos", grupoRepository.findAll());
		
		PageWrapper<Usuario> pagina = new PageWrapper<Usuario>(usuarioRepository.pesquisar(usuarioFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(usuario);
		}
		try {
			usuarioService.salvar(usuario);
		} catch(EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaUsuarioObrigatoriaException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void actualizarStatus(@RequestParam("usuariosId[]") Long [] usuariosId,
			@RequestParam("status") UsuarioStatus usuarioStatus) {
		
		usuarioService.actualizarStatus(usuariosId, usuarioStatus);
	}

}
