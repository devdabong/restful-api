package ideabeans.kotsa.api.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

public class NotExistMemberException extends RuntimeException {

    private static final long serialVersionUID = 8329660903958476073L;
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원입니다.";
    
    private String data;
    
    public NotExistMemberException(String memberNo) {
        super(DEFAULT_MESSAGE);
        this.data = memberNo;
    }
    
    public NotExistMemberException(String memberNo, Throwable e) {
        super(DEFAULT_MESSAGE, e);
        this.data = memberNo;
    }
    
    public String getData() {
        return this.data;
    }
}
