package com.sip.ams.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;

@Service //tu veux dire que cette classe est singleton 
public class ProviderService {

	@Autowired
	ProviderRepository providerRepository ;
	
	  public List<Provider> getAllProviders() {
	        return  providerRepository.findAll();
	    }
	
	  public Provider getOneProvider(Long idProvider) {
	    	
	    	return providerRepository.findById(idProvider).orElseThrow(() -> new IllegalArgumentException("ProviderId " + idProvider + " not found"));
	    	}
	  
	  public  Provider saveProvider(Provider provider) {
	        return providerRepository.save(provider);
	    }
	   
	  public   Provider updateProvider(Long idProvider, Provider provider) {
	      
		  Provider temp =null;
	       Optional<Provider> opt= providerRepository.findById(idProvider);
	        if(opt.isPresent()) {
	        	temp = opt.get();
	        	temp.setName(provider.getName());
	        	temp.setAddress(provider.getAddress());
	        	temp.setEmail(provider.getEmail());
	        	providerRepository.save(temp);
	        	}
	        if(temp==null) throw new IllegalArgumentException("Provider with id="+idProvider+"not found");
			return temp;
	    }
	    
	    public  Provider deleteProvider(Long idProvider) {
	    	Provider temp =null;
	       Optional<Provider> opt= providerRepository.findById(idProvider);
	        if(opt.isPresent()) {
	        	temp = opt.get();
	        	providerRepository.delete(temp);
	        	}
	        if(temp==null) throw new IllegalArgumentException("Provider with id="+idProvider+"not found");
			return temp;
	    }
}
