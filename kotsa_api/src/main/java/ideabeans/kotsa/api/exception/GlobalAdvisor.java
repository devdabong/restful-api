package ideabeans.kotsa.api.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import ideabeans.kotsa.api.vo.ApiCommonCode;
import ideabeans.kotsa.api.vo.ResponseVO;
import ideabeans.kotsa.api.vo.ResponseWithMessageVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RestController
public class GlobalAdvisor {

	/**
     * 공통 응답 헤더
     * 
     * @return
     */
    private MultiValueMap<String, String> getCommonResponseHeader() {
        MultiValueMap<String, String> commonHeader = new LinkedMultiValueMap<>();
        commonHeader.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return commonHeader;
    }
    
    /**
     * 404 예외 핸들러
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<? extends ResponseVO> notFoundExceptio(NoHandlerFoundException e) {
        ResponseWithMessageVO res = new ResponseWithMessageVO(HttpStatus.NOT_FOUND.value() + "", "페이지를 찾을 수 없습니다.");
        return new ResponseEntity<>(res, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * 바인딩 예외 핸들러
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    public ResponseEntity<? extends ResponseVO> bindingException(BindingException e) {
        ResponseWithMessageVO res = new ResponseWithMessageVO(e.getCode(), e.getMessage());
        if (e.hasData()) {
            log.warn("[BindingAdvisor] code='{}', message='{}', data='{}'", e.getCode(), e.getMessage(), e.getData());
        } else {
            log.warn("[BindingAdvisor] code='{}', message='{}'", e.getCode(), e.getMessage());
        }
        
        return new ResponseEntity<>(res, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * 공통 비즈니스 로직 예외 핸들러(의도적 에러)
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(EgovBizException.class)
    public ResponseEntity<? extends ResponseVO> egovBizException(EgovBizException e) {
        ResponseVO data = new ResponseVO(ApiCommonCode.INTERNAL_PROCESS_ERROR);
        log.warn("[EgovBizAdvisor] message='{}'", e.getMessage());
        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * 내부처리 예외 핸들러
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(InternalProcessException.class)
//    public ResponseEntity<? extends ResponseVO> internalProcessException(InternalProcessException e) {
//        ResponseVO data = e.hasData() ? new ResponseWithMessageVO(e.getCode(), e.getMessage()) : new ResponseVO(e.getCode());
//        log.error("[InternalAdvisor] data='{}'", data, e);
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    }
    
    /**
     * 디비처리 예외 핸들러
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<? extends ResponseVO> dataAccessException(DataAccessException e) {
        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.INTERNAL_PROCESS_ERROR, "데이터 처리 오류입니다.");
        log.error("[DataAccessAdvisor] data='{}', message='{}'", data, e.getMessage(), e);
        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * 카카오 예외 핸들러
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(KakaoException.class)
//    public ResponseEntity<String> kakaoException(KakaoException e) {
//        log.error("[KakaosAdvisor] data='{}', message='{}'", e.getData(), e.getMessage());
//        return new ResponseEntity<>(e.getData().toString(), getCommonResponseHeader(), HttpStatus.OK);
//    } 
    
    /**
     * 회원 미존재 예외 핸들러
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(NotExistMemberException.class)
//    public ResponseEntity<? extends ResponseVO> notExistMemberException(NotExistMemberException e) {
//        ResponseVO data = new ResponseVO(ApiCommonCode.NOT_EXIST_DATA);
//        log.error("[NotExistMemberAdvisor] data='{}', message='{}'", e.getData(), e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    } 
    
    /**
     * 발신번호 정합성 예외
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(IllegalCallbackException.class)
//    public ResponseEntity<? extends ResponseVO> illegalCallbackException(IllegalCallbackException e) {
//        ResponseVO data = new ResponseVO(ApiCommonCode.ILLEGAL_PARAMETER);
//        log.error("[IllegalCallbackAdvisor] data='{}', message='{}'", e.getData(), e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    } 
    
    /**
     * 단가정보 미존재 예외
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(NotExistUnitPriceInfoException.class)
//    public ResponseEntity<? extends ResponseVO> notExistUnitPriceInfoException(NotExistUnitPriceInfoException e) {
//        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.ILLEGAL_PARAMETER, "단가 정보가 존재하지 않는 회원입니다.");
//        log.error("[NotExistUnitPriceInfoAdvisor] data='{}', message='{}'", e.getData(), e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    } 
    
    /**
     * 만료일이 지난 전송결과 조회 예외
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(DataExpiredException.class)
//    public ResponseEntity<? extends ResponseVO> dataExpiredException(DataExpiredException e) {
//        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.NOT_EXIST_DATA, "요청한 데이터가 존재하지 않습니다.");
//        log.error("[DataExpiredAdvisor] message='{}'", e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    }
    
    /**
     * 유효하지 않은 IP 예외
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(InvalidIpException.class)
//    public ResponseEntity<? extends ResponseVO> invalidIpException(InvalidIpException e) {
//        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.UNAUTHORIZED, "유효하지 않은 IP에서 접근하였습니다.");
//        log.error("[InvalidIpAdvisor] message='{}'", e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    }
    
    /**
     * 지원하지 않는 예외
     * 
     * @param e
     * @return
     */
//    @ExceptionHandler(UnsupportedOperationException.class)
//    public ResponseEntity<? extends ResponseVO> unsupportedException(UnsupportedOperationException e) {
//        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.ILLEGAL_PARAMETER, e.getMessage());
//        log.error("[UnsupportedAdvisor] message='{}'", e.getMessage());
//        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
//    }    
    
    /**
     * 예기치 못한 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends ResponseVO> exception(Exception e) {
        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.INTERNAL_PROCESS_ERROR, "서버 오류입니다.");
        log.error("[UnexpectedAdvisor] message='{}'", e.getMessage(), e);
        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * 지원하지 않는 메서드 형식 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<? extends ResponseVO> exception(HttpRequestMethodNotSupportedException e) {
        ResponseWithMessageVO data = new ResponseWithMessageVO(ApiCommonCode.ILLEGAL_METHOD, "지원하지 않는 HTTP 메서드입니다.");
        log.error("[NotSupportedMethodAdvisor] message='{}'", e.getMessage());
        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
    }
    
    /**
     * Request json 형식 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<? extends ResponseVO> exception(HttpMessageNotReadableException e) {
        ResponseWithMessageVO data = new ResponseWithMessageVO("", "json 형식이 올바르지 않습니다.");
        log.error("[HttpMessageNotReadableException] message='{}'", e.getMessage());
        return new ResponseEntity<>(data, getCommonResponseHeader(), HttpStatus.OK);
    }
    
}
