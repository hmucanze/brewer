package com.mucanze.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mucanze.brewer.controller.page.PageWrapper;
import com.mucanze.brewer.model.Cidade;
import com.mucanze.brewer.repository.CidadeRepository;
import com.mucanze.brewer.repository.EstadoRepository;
import com.mucanze.brewer.repository.filter.CidadeFilter;
import com.mucanze.brewer.service.CidadeService;
import com.mucanze.brewer.service.exception.NomeCidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		
		mv.addObject("estados", estadoRepository.findAll());
		
		return mv;
	}
	
	@Cacheable(value = "cidades", key = "#estadoId")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> buscarPorEstadoId(
			@RequestParam(name = "estado", defaultValue = "-1")Long estadoId) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		return cidadeRepository.findByEstadoId(estadoId);
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, @PageableDefault(size = 6) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");
		
		mv.addObject("estados", estadoRepository.findAll());
		
		PageWrapper<Cidade> pagina = new PageWrapper<Cidade>(cidadeRepository.filtrar(cidadeFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@CacheEvict(value = "cidades", key = "#cidade.estado.id", condition = "#cidade.temEstado()")
	@RequestMapping(value="/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(cidade);
		}
		
		try {
			cidadeService.salvar(cidade);
		} catch (NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso");
		return new ModelAndView("redirect:/cidades/novo");
	}
	
}
