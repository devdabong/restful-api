package ideabeans.kotsa.api.controller;

import java.util.Locale;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ideabeans.kotsa.api.vo.SmsRequestVO;
import lombok.extern.slf4j.Slf4j;


/**
 * 메시지 통합 발송 API
 * 
 */ 
@Slf4j
@RestController
@RequestMapping(value = "/kotsa", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class KotsaController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("#{common['kotsa.rest.url']}")
	private String restfulURL;
	
	
	@PostMapping(value = "send")
	public ResponseEntity<?> send(@RequestHeader(value = "X-Api-Key", required = false) String apiKey, @RequestHeader(value = "X-Api-User", required = false) String apiUser, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		
		log.info("[{}] /kotsa/send Request---> apiKey[{}] apiUser[{}] {}", "123", apiKey, apiUser, rBody);
		
		
		
		
		// SMS 개별 메세지 발송 request URL - /sms/send
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Api-Key", apiKey);
		headers.add("X-Api-User", apiUser);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		try {
			str = mapper.writeValueAsString(rBody);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		HttpEntity<String> params = new HttpEntity<>(str, headers);
		
		String smsResponse = restTemplate.postForObject(restfulURL + "/sms/send", params, String.class);
		
		//json 형태
		log.info(smsResponse);
		
		
		return null;
	}
}
