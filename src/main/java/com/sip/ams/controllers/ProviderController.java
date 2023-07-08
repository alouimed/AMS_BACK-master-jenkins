package com.sip.ams.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;
import com.sip.ams.services.ProviderService;


@RestController
@RequestMapping({"/providers","/hom*"})
@CrossOrigin(origins="*")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	
	@GetMapping("/") //on peut écrire  @GetMapping("/list")
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }
	
	@PostMapping("/")////on peut écrire  @PostMapping("/add")
    public Provider createProvider(@Valid @RequestBody Provider provider) {
        return providerService.saveProvider(provider);
    }
	
	@PutMapping("{idProvider}")
    public Provider updateProvider(@PathVariable Long idProvider, @Valid @RequestBody Provider providerRequest) {
		return providerService.updateProvider(idProvider, providerRequest);
    }


    @DeleteMapping("/{idProvider}")
    public Provider deleteProvider(@PathVariable Long idProvider) {
       return providerService.deleteProvider(idProvider);
    }
    
    @GetMapping("/{providerId}")
    public Provider getProvider(@PathVariable Long providerId) {
    	
    	return providerService.getOneProvider(providerId);
    	
    }
	

}

