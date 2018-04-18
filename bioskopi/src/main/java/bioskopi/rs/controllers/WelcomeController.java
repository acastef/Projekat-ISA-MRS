package bioskopi.rs.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@Autowired(required = false)
	private WelcomeService service;
	
	@RequestMapping("/welcome")
	public String welcome() {
		return service.message();
	}
	
	
	
}

@Component
class WelcomeService {
	
	public String message() {
		return "Date: " + new Date();
	}
	
}