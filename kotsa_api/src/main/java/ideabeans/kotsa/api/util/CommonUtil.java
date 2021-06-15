package ideabeans.kotsa.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommonUtil {
	
	/**
	 * 아이피 정보 가져오기
	 * 
	 * @return
	 */
	public String getRemoteIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("X-Forwarded-For");
 
        if (ip == null) {
        	//프록시
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
        	//웹로직
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
 
        return ip;
	} 
	
	//현재 년+월 (yymm)
	public static String getDate(String reqDate) throws Exception {
		String syear = "";
		String smonth = "";
		
		if (StringUtils.isEmpty(reqDate)) {
			Calendar cal = Calendar.getInstance();
			syear = String.valueOf(cal.get(1));
			smonth = String.format("%02d", new Object[] { Integer.valueOf(cal.get(2) + 1) });
		} else {
			try {
				SimpleDateFormat sdf01 = new SimpleDateFormat("yyyyMMddHHmmss");
				Date reqDtmDate = sdf01.parse(reqDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(reqDtmDate);
				syear = String.valueOf(cal.get(1));
				smonth = String.format("%02d", new Object[] { Integer.valueOf(cal.get(2) + 1) });
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("ERR:-3,reqDate parse error");
			}
		}
		
		return syear+smonth;
	}
	
	//현재 날짜 yyyymmddHHmmss
	public static String getCurrentDate(String reqDate) throws Exception {
		String date = "";
		
		if (StringUtils.isEmpty(reqDate)) {
			Calendar cal = Calendar.getInstance();
			
			SimpleDateFormat sdf01 = new SimpleDateFormat("yyyyMMddHHmmss");
			Date reqDtmDate = sdf01.parse(reqDate);
			cal.setTime(reqDtmDate);
			
			date = String.valueOf(cal); 
			System.out.println("[현재 날짜 date] " + date);
		}
		
		return date;
	}
	
}
