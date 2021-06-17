package com.mucanze.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mucanze.brewer.controller.page.PageWrapper;
import com.mucanze.brewer.model.Cerveja;
import com.mucanze.brewer.model.Origem;
import com.mucanze.brewer.model.Sabor;
import com.mucanze.brewer.repository.CervejaRepository;
import com.mucanze.brewer.repository.EstiloRepository;
import com.mucanze.brewer.repository.filter.CervejaFilter;
import com.mucanze.brewer.service.CervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	@Autowired
	private CervejaService cervejaService;
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estiloRepository.findAll());
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		
		mv.addObject("estilos", estiloRepository.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		
		// Page<Cerveja> pagina = cervejaRepository.filtrar(cervejaFilter,pageable);
		PageWrapper<Cerveja> pagina = new PageWrapper<Cerveja>(cervejaRepository.filtrar(cervejaFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		cervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");
		return new ModelAndView("redirect:/cervejas/novo");
	}

}
