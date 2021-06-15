package ideabeans.kotsa.api.exception;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import ideabeans.kotsa.api.vo.ApiCommonCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BindingException extends IllegalArgumentException {
	
	private static final long serialVersionUID = -2729050317266855206L;
    /** 결과 코드 */
    private String code;
    /** 데이터 */
    private Object data;
    
    public BindingException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public BindingException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
    
    public static BindingException make(List<ObjectError> errors) {
        String message = errors.get(0).getDefaultMessage();
        log.warn("[BindErrors] {}", errors);
        return new BindingException(ApiCommonCode.ILLEGAL_PARAMETER, message);
    }
    
    public static BindingException make(BindingResult bind) {
        List<FieldError> errors = bind.getFieldErrors();
        log.warn("[BindErrors] {}", errors);
        String message = errors.get(0).getDefaultMessage();
        return new BindingException(ApiCommonCode.ILLEGAL_PARAMETER, message);
    }
    
    public static BindingException make(BindingResult bind, Object obj) {
        List<FieldError> errors = bind.getFieldErrors();
        log.warn("[BindErrors] errors='{}', obj='{}'", errors, obj);
        String message = errors.get(0).getDefaultMessage();
        return new BindingException(ApiCommonCode.ILLEGAL_PARAMETER, message, obj);
    }
    
    public static BindingException make(MessageSource messageSource, String messageKey, String code) {
        String message = messageSource.getMessage(messageKey, null, Locale.getDefault());
        return new BindingException(code, message);
    }
    
    /**
     * 데이터 보유 여부
     * 
     * @return
     */
    public boolean hasData() {
        return this.data != null;
    }
}
