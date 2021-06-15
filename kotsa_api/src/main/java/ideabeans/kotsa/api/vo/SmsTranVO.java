package ideabeans.kotsa.api.vo;

import lombok.Data;

@Data
public class SmsTranVO {
	
	//수신자 번호
	private String tranPhone;
	//발신번호
	private String tranCallback;
	//메세지 발송 타입(sms/lms:4, atalk:7)
	private String tranType;
	//발송(희망)시간(현재시간:즉시발송, 미래시간:예약발송)
	private String tranDate;
	//발송 메세지(SMS, LMS)
	private String tranMsg;
	//메세지 그룹 아이디
	private String msgGrpId;
}
