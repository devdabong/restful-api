package ideabeans.kotsa.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ideabeans.kotsa.api.exception.BindingException;
import ideabeans.kotsa.api.service.SendService;
import ideabeans.kotsa.api.util.CommonUtil;
import ideabeans.kotsa.api.vo.AtalkRequestVO;
import ideabeans.kotsa.api.vo.SmsRequestVO;
import ideabeans.kotsa.api.vo.SmsTranVO;
import ideabeans.kotsa.api.vo.TranVO;
import ideabeans.kotsa.api.vo.SmsRequestVO.SmsRcpt;
import lombok.extern.slf4j.Slf4j;

/**
 *  개별 발송 API
 *  
 */
@Slf4j
@RestController
@RequestMapping(value = {"/kakao", "/{type}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SendController {
	
	@Autowired
	private SendService sendService;
	
	@Autowired
	private static CommonUtil commonUtil;
	
	@Value("#{common['kotsa.sms.send.maxsize']}")
	private String maxSize;
	
	@Value("#{common['kotsa.rest.url']}")
	private String restfulURL;
	
	
	/**
	 * SMS/LMS 개별 발송
	 * 
	 */ 
	@PostMapping(value = "send")
	public ResponseEntity<?> send(@PathVariable String type, @RequestHeader(value = "X-Api-Key", required = false) String apiKey, @RequestHeader(value = "X-Api-User", required = false) String apiUser, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		log.info("[{}] /{}/send Request---> apiKey[{}] apiUser[{}] {}", "123", type, apiKey, apiUser, rBody);
		
		//예외처리 해야 할 것 - 에러메세지 
		
		//throw new Exception("ERR:-3,reqDate parse error");
		//throw new Exception(String.format("ERR:-4,발송량 초과(%s년%s월 총쿼타:%d, 사용건수:%d, 발송시도건수:%d)", new Object[] { syear, smonth, Integer.valueOf(quota), Integer.valueOf(succnt), Integer.valueOf(rcptList.size()) }));  
		//throw new Exception(String.format("ERR:-5,1회 최대 발송량 초과(maxsize: %d건)", new Object[] { Integer.valueOf(this.sendMaxSize) }));
		//throw new Exception("ERR:-9,가변영역값이 없습니다. msgParam을 확인하신후 다시 시도해주십시오.(잘못된필드:%{" + m1.group(1) + "})");

		Map<String, Object> resultMap = new LinkedHashMap<>();
	    Map<String, Object> headerMap = new LinkedHashMap<>();
	    Map<String, Object> bodyMap = new LinkedHashMap<>();
		
		try {
			
			//인증 로직
			//throw new Exception("ERR:-2,Auth Failed"); 
			
			//request parameter validation check
			if (bind.hasErrors()) {
				System.out.println("binding error");
				throw BindingException.make(bind);
		    }
			
			//인증 시 유저 select 할 때 필요한 조건
			String getDate = commonUtil.getDate(rBody.getReqDate());
			//throw new Exception("ERR:-3,reqDate parse error");
			
			//유저 정보 select
			//Map<String, Object> usedInfo = this.authService.getUsedInfo(syear + smonth, apiUser, apiKey);
			
			
			headerMap.put("apiKey", apiKey);
		    headerMap.put("apiUser", apiUser);
		    headerMap.put("requestId", "1");
		    headerMap.put("resultCode", Integer.valueOf(0));
	        headerMap.put("resultDesc", "발송성공"); 
	        
	        //bodyMap.put("sendCount", Integer.valueOf(tranList.size()));
	        bodyMap.put("sendCount", Integer.valueOf(1));
	        
	        String rcptNo = "";
	        Map<String, String> msgParam = null;
	        
	        //받은 수신 리스트
	        List<SmsRcpt> rcpList = (List<SmsRcpt>)rBody.getRcptList();
	        //DB insert 할 리스트 
	        List<SmsTranVO> tranList = new ArrayList<SmsTranVO>(); 
	        Map<String, Object> tranMap = new HashMap<String, Object>();
	        
	        String tranType = "4";
	        String sendMsg = "";
	        
	        for (SmsRcpt rcpt : rcpList) {
	        	
	        	SmsTranVO sms = new SmsTranVO();
	        	//sms.setMsgGrpId(apiKey);
	        	sms.setMsgGrpId(apiKey);
	        	sms.setTranCallback(rBody.getCallback());
	        	sms.setTranType(tranType);
	        	sms.setTranDate(rBody.getReqDate());
	        	rcptNo = rcpt.getRcptNo();
	        	
	        	if(rcptNo.isEmpty()) {
					throw new NullPointerException();
	        	}
	        	sms.setTranPhone(rcpt.getRcptNo());
	        	
	        	msgParam = rcpt.getMsgParam();
	        	if(msgParam != null) {
	        		log.info(msgParam.toString());
	        		
	        		sendMsg = rBody.getMsg();
	        		// HashMap -> Key, Value 출력
	        		Matcher m1 = Pattern.compile("\\[# ?(.*?) ?#]").matcher(sendMsg);
	        		StringBuffer sb1 = new StringBuffer();
	        		while (m1.find()) {
	        			String replaceVal = msgParam.get(m1.group(1));
	        			if(replaceVal == null)
	        				throw new Exception("ERR:-9,가변영역값이 없습니다. msgParam을 확인하신후 다시 시도해주십시오.(잘못된필드:%{" + m1.group(1) + "})");
	        			log.info("PATTERN REPLACE-->[" + m1.group(1) + "]\t[" + replaceVal + "]");
	        			m1.appendReplacement(sb1, replaceVal);
	        		}
	        		m1.appendTail(sb1);
	        		sendMsg = sb1.toString();
	        		
	        		// HashMap -> Key, Value 출력
//		        	for (Entry<String, String> elem : msgParam.entrySet()) {
//		        		System.out.println("key[" + elem.getKey() + "], value[" + elem.getValue() + "]");
//		        	}
	        	} else {
	        		sendMsg = rBody.getMsg();
	        	}
	        	sms.setTranMsg(sendMsg);
	        	
	        	tranList.add(sms);
	        }
	        tranMap.put("list", tranList);
	        //sendService.insertSendMsg(tranList);
	        
	        
	        sendService.insertSendMsg(tranMap);
	        
	        
	        //=====[response]=====
	        List<Map<String, Object>> resultList = new ArrayList<>();
	        for (int i = 0; i < 1; i++) {
	        	Map<String, Object> resObj = new HashMap<>();
	        	resObj.put("seq", Integer.valueOf(1));
	            resObj.put("phone", rBody.getCallback());
	            resObj.put("msg", rBody.getMsg());
	            resObj.put("sendDate", "");
	            resultList.add(resObj);
	        }
	        bodyMap.put("sendList", resultList);
	        
	        resultMap.put("header", headerMap);
	        resultMap.put("body", bodyMap);
	        
		} catch (NullPointerException e) {
			e.printStackTrace();
	        int resultCode = -11;
	        String resultDesc = "수신자 번호가 없습니다. rcptNo를 확인하신 후 다시 시도해주십시오.";
	        String _errMsg = e.getMessage();
	        if (_errMsg != null && _errMsg.startsWith("ERR:")) {
	          resultCode = Integer.parseInt(_errMsg.substring(4, _errMsg.indexOf(",")));
	          resultDesc = _errMsg.substring(_errMsg.indexOf(",") + 1);
	        } 
	        resultMap.put("resultCode", Integer.valueOf(resultCode));
	        resultMap.put("resultDesc", resultDesc);
		} catch (Exception e) {
			e.printStackTrace();
	        int resultCode = -1;
	        String resultDesc = "발송실패(관리자에게 문의해주세요.)";
	        String _errMsg = e.getMessage();
	        if (_errMsg != null && _errMsg.startsWith("ERR:")) {
	          resultCode = Integer.parseInt(_errMsg.substring(4, _errMsg.indexOf(",")));
	          resultDesc = _errMsg.substring(_errMsg.indexOf(",") + 1);
	        } 
	        resultMap.put("resultCode", Integer.valueOf(resultCode));
	        resultMap.put("resultDesc", resultDesc);
		}
        
	    return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 알림톡 개별 발송(ATALK)
	 */
	@PostMapping(value = "atalk/send")
	public ResponseEntity<?> send(@RequestHeader(value = "X-Api-Key", required = false) String apiKey, @RequestHeader(value = "X-Api-User", required = false) String apiUser, @RequestBody @Valid AtalkRequestVO rBody, BindingResult bind){
		log.info("[{}] /kakao/atalk/send Request---> apiKey[{}] apiUser[{}] {}", "123", apiKey, apiUser, rBody);
		
		//예외처리 해야 할 것 - 에러메세지 
		//throw new Exception("ERR:-2,Auth Failed"); 
		//throw new Exception("ERR:-3,reqDate parse error");
		//throw new Exception(String.format("ERR:-4,발송량 초과(%s년%s월 총쿼타:%d, 사용건수:%d, 발송시도건수:%d)", new Object[] { syear, smonth, Integer.valueOf(quota), Integer.valueOf(succnt), Integer.valueOf(rcptList.size()) }));  
		//throw new Exception(String.format("ERR:-5,1회 최대 발송량 초과(maxsize: %d건)", new Object[] { Integer.valueOf(this.sendMaxSize) }));
		//throw new Exception("ERR:-9,가변영역값이 없습니다. msgParam을 확인하신후 다시 시도해주십시오.(잘못된필드:%{" + m1.group(1) + "})");
		
		Map<String, Object> resultMap = new LinkedHashMap<>();
	    Map<String, Object> headerMap = new LinkedHashMap<>();
	    Map<String, Object> bodyMap = new LinkedHashMap<>();
	    
		try {
			if (bind.hasErrors()) {
				System.out.println("binding error");
				throw BindingException.make(bind);
		    }
			if (rBody.getRcptList().get(0) == null || rBody.getRcptList().get(0).equals("")) {
				System.out.println("binding error");
				throw BindingException.make(bind);
			}
			
			
			headerMap.put("apiKey", "1");
		    headerMap.put("apiUser", "1");
		    headerMap.put("requestId", "1");
		    headerMap.put("resultCode", Integer.valueOf(0));
	        headerMap.put("resultDesc", "카카오 알림톡"); 
	        
	        //bodyMap.put("sendCount", Integer.valueOf(tranList.size()));
	        bodyMap.put("sendCount", Integer.valueOf(1));
	        
	        List<Map<String, Object>> resultList = new ArrayList<>();
	        for (int i = 0; i < 1; i++) {
	        	Map<String, Object> resObj = new HashMap<>();
	        	resObj.put("seq", Integer.valueOf(1));
	            resObj.put("phone", rBody.getCallback());
	            //resObj.put("msg", rBody.getMsg());
	            resObj.put("sendDate", "");
	            resultList.add(resObj);
	        }
	        bodyMap.put("sendList", resultList);
	        
	        resultMap.put("header", headerMap);
	        resultMap.put("body", bodyMap);
		} catch (Exception e) {
			e.printStackTrace();
	        int resultCode = -1;
	        String resultDesc = "발송실패(관리자에게 문의해주세요.)";
	        String _errMsg = e.getMessage();
	        if (_errMsg != null && _errMsg.startsWith("ERR:")) {
	          resultCode = Integer.parseInt(_errMsg.substring(4, _errMsg.indexOf(",")));
	          resultDesc = _errMsg.substring(_errMsg.indexOf(",") + 1);
	        } 
	        resultMap.put("resultCode", Integer.valueOf(resultCode));
	        resultMap.put("resultDesc", resultDesc);
		} 
        
	    return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 발송 메시지 조회(SMS / LMS / ATALK)
	 * 
	 * selector : msgid / date
	 * messageId 또는 기간(startReqDate, endReqDate, logDate)으로 조회
	 */ 
	@PostMapping(value = "/**/findMessage/{selector}")
	public ResponseEntity<?> findMessage(@PathVariable String type, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 예약 발송 메시지 조회(SMS / LMS / ATALK)
	 */ 
	@PostMapping(value = "/**/findRsvMessage")
	public ResponseEntity<?> findRsvMessage(@PathVariable String type, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 예약 발송 메시지 취소(SMS / LMS / ATALK)
	 */ 
	@PostMapping(value = "/**/cancelRsvMessage")
	public ResponseEntity<?> cancelRsvMessage(@PathVariable String type, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 템플릿 조회(ATALK)
	 */ 
	@PostMapping(value = "atalk/findTemplate")
	public ResponseEntity<?> findTemplate(@PathVariable String type, @RequestBody @Valid SmsRequestVO rBody, BindingResult bind, Locale locale){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}