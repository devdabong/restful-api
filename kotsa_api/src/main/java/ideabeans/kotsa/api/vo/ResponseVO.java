package ideabeans.kotsa.api.vo;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Getter;

/**
 * 공통 응답 객체
 * 
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO {
	
	/** 응답 코드 */
    protected String code;
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
