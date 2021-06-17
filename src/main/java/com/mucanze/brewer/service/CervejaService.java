package com.mucanze.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.brewer.model.Cerveja;
import com.mucanze.brewer.repository.CervejaRepository;
import com.mucanze.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CervejaService {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejaRepository.save(cerveja);
		
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}

}
