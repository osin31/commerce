package kr.co.abcmart.common.account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.sun.net.ssl.internal.ssl.Provider;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AccountAuth {
	public Map<String, Object> start(Map<String, String> param){
		SimpleDateFormat sdf 	= new SimpleDateFormat("yyyyMMdd");
		String strOrderNo 		= sdf.format(new Date()) + (Math.round(Math.random() * 10000000000L) + "");
    	Map<String, Object> result = new HashMap<String, Object>();
    	BufferedReader in 	= null;
      	PrintWriter out 	= null;
      	
      	param.put("strOrderNo", strOrderNo);
        
        try{
        	Security.addProvider(new Provider());
        	SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
          	SSLSocket soc = (SSLSocket)factory.createSocket("secure.nuguya.com", 443);
            
            // 타임아웃  +++++++++++++++++++++++++++++++++++++++++++++++++++++ 
            soc.setSoTimeout(10*1000);	// 타임아웃 10초 
        	soc.setSoLinger(true, 10);	
            soc.setKeepAlive(true);		
            // 타임아웃  +++++++++++++++++++++++++++++++++++++++++++++++++++++ 
            
            out = new PrintWriter(soc.getOutputStream());
            in  = new BufferedReader(new InputStreamReader(soc.getInputStream()), 8 * 1024);
			
			result = rlnmCheck(out, in, param);
        }catch(SocketException e){
        	log.debug("Account auth error : {} " + e.getMessage());
        }catch (Exception e){
        	e.printStackTrace();
        	log.debug("Account auth error : {} " + e.getMessage());
        }finally{
            if (out != null){
                try{
                    out.close();
                }catch (Exception e){
                }
            }
            if (in != null){
                try{
                    in.close();
                }catch (Exception e){
                }
            }
        }
		return result;
    }

   	public Map<String, Object> rlnmCheck(PrintWriter out, BufferedReader in, Map<String, String> param) throws IOException{
        Map<String, Object> resultMap = new HashMap<String, Object>();	// return
        String resultString = "";	// 계좌인증 후 결과값 저장 변수
        StringBuffer sbResult = new StringBuffer();
        String url			= param.get("url");
        String niceUid		= param.get("niceUid");       
        String svcPwd		= param.get("svcPwd");        
        String service		= param.get("service");       
        String strGbn		= param.get("strGbn");        
        String strResId		= param.get("strResId");      
        String strNm		= param.get("strNm");         
        String strBankCode	= param.get("strBankCode");   
        String strAccountNo	= param.get("strAccountNo");  
        String svcGbn		= param.get("svcGbn");        
        String strOrderNo	= param.get("strOrderNo");    
        String svc_cls		= param.get("svc_cls");       
        String inq_rsn		= param.get("inq_rsn");
        
        
        String contents = "niceUid=" + niceUid + "&svcPwd=" + svcPwd + "&service=" + service + "&strGbn=" + strGbn + "&strResId=" + strResId + "&strNm=" + strNm + "&strBankCode=" + strBankCode + "&strAccountNo=" + strAccountNo + "&svcGbn=" + svcGbn + "&strOrderNo=" + strOrderNo + "&svc_cls=" + svc_cls + "&inq_rsn=" + inq_rsn + "&seq=0000001";

        out.println(url); //UTF-8 URL
        out.println("Host: secure.nuguya.com");
        out.println("Connection: Keep-Alive");
        out.println("Content-Type: application/x-www-form-urlencoded");
        out.println("Content-Length: " + contents.length());
        out.println();
        out.println(contents);
        out.flush();
        
        String line = null;
        int i=0;
        boolean notYet = true;
        while((line = in.readLine())!= null){
            i++;
            if (notYet && line.indexOf("HTTP/1.") == -1 ){
            	continue;
            }
            if (notYet && line.indexOf("HTTP/1.") > -1 ){
            	notYet = false;
            }
            
            if (line.indexOf("HTTP/1.") > -1 ){
            	notYet = false;
            }
            if (line.startsWith("0") ){
            	break;
            }
        	
            if(i==9) sbResult.append(line);
        }
		
//        System.out.println("결과값 >>>" + sbResult.toString());
        resultString = sbResult.toString();
        if("".equals(resultString)) {
        	resultMap.put("flag", "N");
        	resultMap.put("message", "에러가 발생하였습니다.");
        }else {
        	String[] resultArr = resultString.split("\\|");
        	if("0000".equals(resultArr[1])) {
        		resultMap.put("flag", "Y");
        		resultMap.put("message", resultArr[2]);
        	}else{
        		resultMap.put("flag", "N");
        		resultMap.put("message", resultArr[2]);
        	}
        }
        return resultMap;
    }
    
	public static void main(String[] args) {
		AccountAuth accountAuth = new AccountAuth();
		Map<String, String> param = new HashMap<String, String>();
		param.put("niceUid", 		"Nebiz053");
		param.put("svcPwd", 		"1234");
		param.put("inq_rsn", 		"90");
		param.put("strGbn", 		"1");
		param.put("url", 			"POST https://secure.nuguya.com/nuguya2/service/realname/sprealnameactconfirm.do HTTP/1.1");
		param.put("service", 		"2");
		param.put("svcGbn", 		"2");
		param.put("strAccountNo", 	"44801467001019");
		param.put("strBankCode", 	"03");
		param.put("strNm", 			"최경호");
		param.put("svc_cls", 		"");
		accountAuth.start(param);
	}
}
