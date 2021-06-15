package ideabeans.kotsa.api.vo;

import com.google.gson.Gson;

import lombok.Getter;

/**
 * 공통 응답 객체
 * 
 */
@Getter
public class ResponseWithMessageVO extends ResponseVO {
	
	/** 응답 메시지 */
    private String message;
    
    public ResponseWithMessageVO(String code, String message) {
        super(code);
        this.message = message;
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
