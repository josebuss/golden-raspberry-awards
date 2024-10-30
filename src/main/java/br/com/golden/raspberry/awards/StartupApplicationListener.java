package br.com.golden.raspberry.awards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.golden.raspberry.awards.service.UploadProdutoresPremiadosService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartupApplicationListener {

	@Autowired
	private UploadProdutoresPremiadosService service;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		service.initialLoad();
	}
}
