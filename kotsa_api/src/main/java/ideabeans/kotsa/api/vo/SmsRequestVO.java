package ideabeans.kotsa.api.vo;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SMS, LMS
 */
@Data
@NoArgsConstructor
public class SmsRequestVO {
	
	/** 발송요청날짜 yyyyMMddHHmmss 
	 *  default : 요청기준 현제시간
	 *  미래시간으로 요청 시 예약발송
	 *  */
	private String reqDate;
	
	/** 발신번호 */
	@NotEmpty(message = "{SmsRequestVO.callback.NotEmpty}")
	private String callback;

	/** SMS 메시지 내용 */
	@NotEmpty(message = "{SmsRequestVO.msg.NotEmpty}")
	private String msg;
	
	/** 수신자 리스트 배열(1회 최대 1000개의 수신자만 발신가능) */
	@NotEmpty(message = "{SmsRequestVO.rcptList.NotEmpty}")
	@NotNull(message = "{SmsRequestVO.rcptList.NotEmpty}")
	private List<SmsRcpt> rcptList;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SmsRcpt {
		@NotEmpty(message = "{SmsRequestVO.rcptNo.NotEmpty}")
		@NotNull(message = "{SmsRequestVO.rcptNo.NotEmpty}")
		/** 수신자 번호 */
		private String rcptNo;
		
		/** 가변영역 치환데이터 */
		private Map<String, String> msgParam;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MsgParam {
		/** 메세지에서 가변영역 이름 (예: [# 이름 #]에서 이름치환 */
		private String key;
		/** 가변영역을 치환할 값 */
		private String value;
	}
	
}
