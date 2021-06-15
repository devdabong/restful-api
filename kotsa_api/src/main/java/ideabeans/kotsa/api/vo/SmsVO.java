//package ideabeans.kotsa.api.vo;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.validation.constraints.NotEmpty;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//public class SmsVO {
//
//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	public static class Rcpt {
//		/** 수신자 번호 */
//		private String rcptNo;
//		/** 가변영역 치환데이터 */
//		private Map<String, String> msgParam;
//	}
//	
//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	public static class MsgParam {
//		/** 메세지에서 가변영역 이름 (예: [# 이름 #]에서 이름치환 */
//		private String key;
//		/** 가변영역을 치환할 값 */
//		private String value;
//	}
//	
//	/** 수신자 리스트 배열 (1회 최대 1000개의 수신자만 발신가능) */
//	@NotEmpty(message = "{SmsVO.rcptList.NotEmpty}")
//	private List<Rcpt> rcptList;
//	
//	/** 수신자 번호 */
//	@NotEmpty(message = "{rcptNo.callback.NotEmpty}")
//	private String rcptNo;
//
//	/** SMS 메시지 내용 */
//	@NotEmpty(message = "{SmsVO.msg.NotEmpty}")
//	private String msg;
//
//
//	/** 수신자 번호 */
//	@NotEmpty(message = "{SmsVO.rcptNo.NotEmpty}")
//
//	private int num = 0;
//
//	private String phone;
//
//	private String callback;
//
//	private String msg;
//
//	private String senddate;
//
//	private String rsltstat;
//
//	private String id;
//
//	private String apikey;
//
//	private String apireqcode;
//
//	private int insertNum = 0;
//}
