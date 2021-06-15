package ideabeans.kotsa.api.vo;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 알림톡(ATALK)
 */
@Data
@NoArgsConstructor
public class AtalkRequestVO {
	
	/** 부여받은 서비스 번호 */
	@NotEmpty(message = "{AtalkRequestVO.service_seq_no.NotEmpty}")
	private String service_seq_no;
	
	/** 
	 *  발송요청날짜 yyyyMMddHHmmss 
	 *  default : 요청기준 현제시간
	 *  미래시간으로 요청 시 예약발송
	 */
	private String reqDate;

	/** 
	 *  발송우선순위
	 *  R00:실시간, B00:배치
	 *  기본값: 실시간(R00)
	 */
	private String jobType;
	
	/** 템플릿 코드 */
	@NotEmpty(message = "{AtalkRequestVO.tmplId.NotEmpty}")
	private String tmplId;
	
	/** 수신자 리스트 배열(1회 최대 1000개의 수신자만 발신가능) */
	@NotEmpty(message = "{AtalkRequestVO.rcptList.NotEmpty}")
	private List<AtalkRcpt> rcptList;
	
	/** 
	 *  발송실패시 부달발송여부(Y/N)
	 * 
	 *  부달발송시 90byte기준으로 SMS/LMS 자동판단하여 발송
	 *  기본값: 발송안함(N)
	 *  */
	private String backupSend;
	
	/** (부달발송시) 부달발송이 LMS일때 제목 */
	private String subject;
	
	/** (부달발송시) 발신번호 */
	private String callback;
	
	/** 버튼 이름(링크버튼이 있는 템플릿) */
	private String btnName;
	
	/** 버튼 URL(링크버튼이 있는 템플릿) */
	private String btnUrl;
	
	/** 챗버블용 버튼링크 1 */
	private String btnLink1;
	/** 챗버블용 버튼링크 2 */
	private String btnLink2;
	/** 챗버블용 버튼링크 3 */
	private String btnLink3;
	/** 챗버블용 버튼링크 4 */
	private String btnLink4;
	/** 챗버블용 버튼링크 5 */
	private String btnLink5;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AtalkRcpt {
		/** 수신자 번호 */
		@NotEmpty(message = "{AtalkRequestVO.rcptNo.NotEmpty}")
		private String rcptNo;
		
		/** 가변영역 치환데이터 */
		private Map<String, String> tmplParam;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TmplParam {
		/** 메세지에서 가변영역 이름 (예: [# 이름 #]에서 이름치환 */
		private String key;
		/** 가변영역을 치환할 값 */
		private String value;
	}
}
