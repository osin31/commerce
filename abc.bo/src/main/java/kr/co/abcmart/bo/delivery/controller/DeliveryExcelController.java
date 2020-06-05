package kr.co.abcmart.bo.delivery.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibleaders.ibsheet.IBSheetDown;

import kr.co.abcmart.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/common")
public class DeliveryExcelController extends BaseController {
	
	@RequestMapping(value="ibsheet/Down2Excel.do")
	public void Down2Excel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info(":::::::::::::::::::: Down2Excel Start");
		
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "");

		IBSheetDown down = null;
		 
		try {
			down = new IBSheetDown();
			down.setEncoding("UTF-8");
			
			down.setService(request, response);
			down.setFileType("excel");
			down.downToBrowser();
		} catch (Exception e) {
			// TODO: handle exception
//			response.setContentType("text/html;charset=UTF-8");
//		    response.setCharacterEncoding("UTF-8");
//	        response.setHeader("Content-Disposition", "");
//	        PrintWriter out = response.getWriter();
//	        out.println("<script>alert('엑셀 다운로드중 에러가 발생하였습니다.');</script>");
	        log.error(":::::::::::::::::::: excelDown Exception -> " + e.getMessage());
		} catch (Error e) {
			// TODO: handle exception
//			response.setContentType("text/html;charset=UTF-8");
//		    response.setCharacterEncoding("UTF-8");
//		    response.setHeader("Content-Disposition", "");
//		    PrintWriter out = response.getWriter();
//		    out.println("<script>alert('엑셀 다운로드중 에러가 발생하였습니다.');</script>");
		    log.error(":::::::::::::::::::: excelDown Error -> " + e.getMessage());
		} finally {
			if(down != null) {
				try {
					down.close();
				} catch (Exception e2) {
					// TODO: handle exception
					log.error(":::::::::::::::::::: downClose error -> " + e2.getMessage());
				}
			}
			down = null;
		}
		log.info(":::::::::::::::::::: Down2Excel finish");
	}
}
