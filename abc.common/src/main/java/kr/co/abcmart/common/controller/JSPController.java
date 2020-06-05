package kr.co.abcmart.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import kr.co.abcmart.common.util.UtilsText;

public class JSPController extends AbstractController {
	
	public static final String EXT = ".jspa";

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    	String servletPath = request.getServletPath();
    	
    	
    	if(!UtilsText.isBlank(servletPath) && servletPath.indexOf(EXT) > 0) {
    		servletPath = servletPath.substring(0,servletPath.lastIndexOf(EXT));
    	}
    	    	
    	return new ModelAndView(servletPath);
    }
}
