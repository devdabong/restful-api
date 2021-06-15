package ideabeans.kotsa.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface SendDAO {
	
	//public void insertSendMsg(List<SmsTranVO> vo) throws Exception;
	public void insertSendMsg(Map<String, Object> map) throws Exception;
}
