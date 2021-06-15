package ideabeans.kotsa.api.vo;

import lombok.Data;

@Data
public class TranVO {
	
	//em_tran primary key 
	private String tranPr;
	//여분 필드(참조 값)
	private String tranRefkey;
	//여분 필드(참조 값, 숫자/영문만 입력 가능)
	private String tranId;
	//수신자 번호
	private String tranPhone;
	//발신번호
	private String tranCallback;
	//메세지 발송 타입(sms/lms:4, atalk:7)
	private String tranType;
	//발송상태(발송대기:1, 발송후대기:2, 발송완료:3)
	private String tranStatus;
	//발송(희망)시간(현재시간:즉시발송, 미래시간:예약발송)
	private String tranDate;
	//휴대폰 수신시간(이통사에서 전달한 시간)
	private String tranRsltdate;
	//결과 리포트 수신 시간
	private String tranReportdate;
	//메세지 발송 결과코드
	private String tranRslt;
	//카카오 발송 결과코드(알림톡)
	private String tranKaoRslt;
	//이통사 정보(SKT/KTF/LGT/KAO/ETC)
	private String tranNet;
	//발송 메세지(SMS, LMS)
	private String tranMsg;
	//부서코드
	private String tranEtc1;
	//시스템코드
	private String tranEtc2;
	//목적코드
	private String tranEtc3;
	//여분필드  
	private String tranEtc4;
	//여분필드
	private String tranEtc5;
	//여분필드
	private String tranEtc6;
	//여분필드
	private String tranEtc7;
	//여분필드
	private String tranEtc8;
	//여분필드
	private String tranEtc9;
	//em_tran_mms테이블의 mms_seq 값 매칭
	private String mmsSeq;
	//메세지 그룹 아이디
	private String msgGrpId;
	//메시지발송시간제한(-1:사용안함, (분))
	private String tranExpired;
}
