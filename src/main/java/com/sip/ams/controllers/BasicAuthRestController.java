package com.sip.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.sip.ams.entities.AuthenticationBean;
import com.sip.ams.entities.User;
import com.sip.ams.services.UserService;

@CrossOrigin(origins="*")
@RestController
public class BasicAuthRestController {
	
	
	@Autowired
	private UserService userService;
	@GetMapping(path ="/basicauth")
	
	/*public AuthenticationBean basicauth() {
		//on doit faire la vérification au niveau de la base 
		return new AuthenticationBean("You are authenticated");
	}*/
	
	public User basicauth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user);
        return user;
    }
}