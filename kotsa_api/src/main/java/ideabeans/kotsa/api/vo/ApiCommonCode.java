package ideabeans.kotsa.api.vo;

public final class ApiCommonCode {
	
	 /** 권한 없음 */
    public static final String UNAUTHORIZED = "S403";
    /** 파라미터 정합성 체크 오류 */
    public static final String ILLEGAL_PARAMETER = "S405";
    /** 지원하지 않는 메서드 형식 오류 */
    public static final String ILLEGAL_METHOD = "S406";
    /** 서버 오류 */
    public static final String INTERNAL_PROCESS_ERROR = "S500";
    /** 서버에 데이터가 존재하지 않음 */
    public static final String NOT_EXIST_DATA = "S508";
	
    private ApiCommonCode() {}
    
}
