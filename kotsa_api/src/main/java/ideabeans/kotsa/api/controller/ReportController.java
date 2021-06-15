package ideabeans.kotsa.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportController {
	
	/**
	 * 인증
	 */
	@GetMapping("/auth")
	public void auth() {
		//API Key, API ID 비교 
		
	}
	
	/**
	 * report API
	 */
	@GetMapping("/report")
	public String report() {
		// DB 3개에서 조인으로 select해서 출력해주기 (날짜 제한 미정.. maybe 하루 또는 일주일)
		return null;
	}
}	
