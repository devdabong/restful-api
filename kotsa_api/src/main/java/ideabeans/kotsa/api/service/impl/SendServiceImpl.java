package ideabeans.kotsa.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ideabeans.kotsa.api.dao.SendDAO;
import ideabeans.kotsa.api.service.SendService;
import ideabeans.kotsa.api.vo.SmsRequestVO;
import ideabeans.kotsa.api.vo.SmsTranVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendServiceImpl implements SendService{
	
	@Autowired
	private SendDAO sendDao;

	@Override
	public void insertSendMsg(Map<String, Object> map) throws Exception {
		log.info("insertSendMsg()");
		
		sendDao.insertSendMsg(map);
	}

	
//	
//	@Override
//	public void insertSendMsg(List<SmsTranVO> vo) throws Exception {
//		log.info("insertSendMsg()");
//		
//		log.info(vo.toString());
//		
////		Map<String, Object> map = new HashMap<>();
////		map.put("callback", vo.getCallback());
////		map.put("msg", vo.getMsg());
////		map.put("reqDate", vo.getReqDate());
////		map.put("rcptList", vo.getRcptList());
////		map.put("tranType", 4);
////		map.put("msgGrpId", "test");
//		
//		sendDao.insertSendMsg(vo);
//		
//		/*
//		 * json 다루기 
//		 
//		//vo(object) to json
//		Gson gson = new Gson();
//		String json = gson.toJson(vo);
//		
//		//json parsing
//		JsonParser parser = new JsonParser();
//		JsonElement el = parser.parse(json);
//		String element1 = el.getAsJsonObject().get("callback").getAsString();
//		*/
//		
//	}
	
}
