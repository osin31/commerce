package kr.co.abcmart.common.util;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UtilsCookie {

	public static final String DEFAULT_COOKIE_ENCODING = "UTF-8";
	
	
	private static String encodeCookie(String value) throws Exception {
		return URLEncoder.encode(value, DEFAULT_COOKIE_ENCODING);
	}
	
	
    /**
     * 쿠키객체를 생성
     * 
     * @param name 쿠키 이름을 지정한다
     * @param value 쿠키 값을 지정한다
     * @return cookie
     * @throws Exception
     */
    public static Cookie setCookie(String name, String value) throws Exception {
        return new Cookie(name, encodeCookie(value));
    }

    /**
     * 쿠키객체를 생성
     * 
     * @param name 쿠키 이름을 지정
     * @param value 쿠키 값을 지정
     * @param path 쿠키 접근 경로 지정
     * @param maxAge 쿠키 유지 기간 ex) 60*60*24*365 1년
     * @return cookie
     * @throws Exception
     */
    public static Cookie setCookie(String name, String value, String path, int maxAge) throws Exception {
        Cookie cookie = new Cookie(name, encodeCookie(value));
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    /**
     * 쿠키객체를 생성
     * 
     * @param name 쿠키 이름을 지정
     * @param value 쿠키 값을 지정
     * @param domain 도메인 접근 지정
     * @param path 	이 쿠키값은 시작 URL의 값을 지정
     * @param maxAge 쿠키 유지 기간 ex) 60*60*24*365 1년
     * @return cookie
     * @throws Exception
     */
    public static Cookie setCookie(String name, String value, String domain, String path, int maxAge) throws Exception {
        Cookie cookie = new Cookie(name, encodeCookie(value));
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
    
    /**
     * 쿠키를 추가 
     * @param cookie 쿠키 객체 Cookie
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {
    	response.addCookie(cookie);
    }
    
    /**
     * 쿠키를 추가 
     * @param name 쿠키 이름을 지정
     * @param value 쿠키 값을 지정
     * @throws Exception
     */
    public static void addCookie(HttpServletResponse response, String name, String value) throws Exception {
    	addCookie(response,setCookie(name, value));
    }
    
    /**
     * 쿠키를 추가 
     * @param name 쿠키 이름을 지정
     * @param value 쿠키 값을 지정
     * @param path 쿠키 접근 경로 지정
     * @param maxAge 쿠키 유지 기간 ex) 60*60*24*365 1년
     * @throws Exception
     */
    public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) throws Exception {
    	addCookie(response,setCookie(name, value, path, maxAge));
    }
    
    /**
     * 쿠키를 추가 
     * @param name 쿠키 이름을 지정
     * @param value 쿠키 값을 지정
     * @param domain 도메인 접근 지정
     * @param path 	이 쿠키값은 시작 URL의 값을 지정
     * @param maxAge 쿠키 유지 기간 ex) 60*60*24*365 1년
     * @throws Exception
     */
    public static void addCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge) throws Exception {
    	addCookie(response,setCookie(name, value, domain, path, maxAge));
    }


    /**
     * 쿠키 객체를 가져온다
     * 
     * @param name 쿠키 이름
     * @return Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
    	
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        
        if (cookies != null) {

        	int cookieLength = cookies.length;
        	for (int i = 0; i < cookieLength; i++) {
        		if(name.equals(cookies[i].getName())) {
        			cookie = cookies[i];
        			break;
        		}
            }
        }
        return cookie;
    }

    /**
     * 쿠키의 값을 가져온다
     * 
     * @param name 쿠키 이름
     * @return String 
     * @throws Exception
     */
    public static String getValue(HttpServletRequest request,String name) {
        return getValue(request,name, null);
    }
    
    /**
     * 쿠키의 값을 가져온다
     * 
     * @param name 쿠키 이름
     * @param defaultValue 값이 존재 하지 않을 경우 기본 값.
     * @return String 
     * @throws Exception
     */
    public static String getValue(HttpServletRequest request,String name,String defaultValue) {
        Cookie cookie = getCookie(request,name);
        if (cookie == null) return defaultValue;
        
        String value = defaultValue;
        try {
        	value = encodeCookie(cookie.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
    }

    /**
     * 쿠키 삭제
     * 
     * @param name 쿠키 이름
     * @return
     */
    public static Cookie removeCookie(HttpServletRequest request,String name) {
    	
        Cookie cookie = null;
        
        if (isExist(request,name)) {
            cookie = getCookie(request,name);
            
            // 쿠키생성시에 setPath()가 설정되어 있으면 삭제시에도 해당 패스를 다시 삭제하기위해서 생성된 쿠키에 재설정하고 setMaxAge(0)으로 설정하며 삭제합니다.

            if (cookie.getPath() != null) {
                cookie.setPath(cookie.getPath());
            } else {
                cookie.setPath("/");
            }
            if (cookie.getDomain() != null) {
                cookie.setDomain(cookie.getDomain());
            }
            cookie.setMaxAge(0);
        }
        return cookie;
    }

    /**
     * 쿠키 존재 여부
     * 
     * @param name 쿠키 이름
     * @return boolean 
     */
    public static boolean isExist(HttpServletRequest request,String name) {
        return getCookie(request,name) != null;
    }


}

