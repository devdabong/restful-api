package ideabeans.kotsa.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {
	
	@GetMapping("hi") 
	public String hi() {
		System.out.println("hi");
		
		return "hi";
	}
}
