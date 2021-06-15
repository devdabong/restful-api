package ideabeans.kotsa.api.service;

import java.util.List;
import java.util.Map;

import ideabeans.kotsa.api.vo.SmsTranVO;

public interface SendService {
	
	//public void insertSendMsg(List<SmsTranVO> vo) throws Exception;
	public void insertSendMsg(Map<String, Object> map) throws Exception;
}
