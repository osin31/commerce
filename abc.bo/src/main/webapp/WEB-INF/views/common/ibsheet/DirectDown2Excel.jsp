﻿<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.io.*"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@page import="java.util.Map"%>
%><%@page import="java.util.HashMap"%>
%><%@page import="com.ibleaders.ibsheet.IBSheetDown"
%><%@page import="com.ibleaders.ibsheet.excel.ExcelPrintSetup"
%><%

    response.setContentType("application/octet-stream");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Disposition", "");

    IBSheetDown down = null;

    try {
        
        out.clear();
        out = pageContext.pushBody();

        down = new IBSheetDown();
System.out.println("1");
        //====================================================================================================
        // [ 사용자 환경 설정 #0 ]
        //====================================================================================================
        // Html 페이지의 인코딩이 UTF-8 로 구성되어 있으면 "down.setEncoding("UTF-8");" 로 설정하십시오.
        // LoadExcel.jsp 도 동일한 값으로 바꿔 주십시오.
        // setService 전에 설정해야 합니다.
        //====================================================================================================
        down.setEncoding("UTF-8");

        //====================================================================================================
        // [ 사용자 환경 설정 #1 ]
        //====================================================================================================
        // 엑셀 전문의 MarkupTag Delimiter 사용자 정의 시 설정하세요.
        // 설정 값은 IBSheet7 환경설정(ibsheet.cfg)의 MarkupTagDelimiter 설정 값과 동일해야 합니다.
        //====================================================================================================      
        //down.setMarkupTagDelimiter("[s1]","[s2]","[s3]","[s4]");
        
        //====================================================================================================
		// [ 사용자 환경 설정 #2 ]
		//====================================================================================================
		// 시트에 포함될 문자열 중 STX(\u0002), ETX(\u0003) 이 포함된 경우에만 설정해주세요.
		// 설정을 원하지 않는 경우 주석처리해주세요.
		// 0 : 시트 구분자로 STX, ETX 문자를 사용합니다. (기본값)
		// 1 : 시트 구분자로 변형된 문자열을 사용합니다. (시트에 설정이 되어 있어야 합니다.)
		//====================================================================================================
		//down.setDelimMode(1);
			
		//====================================================================================================
        // [ 사용자 환경 설정 #3 ]
        //====================================================================================================
        // HttpServletRequest, HttpServletResponse를 IBSheet 서버모듈에 등록합니다.
        //====================================================================================================
        down.setService(request, response);
        System.out.println("2");
        //====================================================================================================
        // [ 사용자 환경 설정 #4 ]
        //====================================================================================================
        // 다운로드 받을 문서의 타입을 설정하십시오.
        // xls   : xls 형식으로 다운로드
        // xlsx  : xlsx 형식으로 다운로드
        // excel : 시트에 FileName에 설정값으로 다운로드, 확장자가 없는 경우 기본 xls 형식으로 다운로드
        //====================================================================================================        
        down.setFileType("excel");
        System.out.println("3");
        //====================================================================================================
        // [ 사용자 환경 설정 #5 ]
        //====================================================================================================
        // 엑셀 문서에 사용될 폰트를 설정하십시오.
        // 헤더 폰트이름도 함께 설정 됩니다.
        //====================================================================================================
        //down.setFontName("맑은고딕");          

        //====================================================================================================
        // [ 사용자 환경 설정 #6 ]
        //====================================================================================================
        // 엑셀 문서에 사용될 폰트크기를 설정하십시오.
        // 헤더 폰트크기도 함께 설정 됩니다. 
        //====================================================================================================
        //down.setFontSize(20);

        //====================================================================================================
        // [ 사용자 환경 설정 #7 ]
        //====================================================================================================
        // 엑셀 다운로드 시 헤더행의 배경색을 적용하고 싶은 경우에 설정하세요.
        // #3366FF 형태의 웹 컬러로 설정해주세요.
        // 설정을 원하지 않는 경우 주석처리해주세요. 
        //====================================================================================================
        //down.setHeaderBackColor("#FF2233");

        //====================================================================================================
        // [ 사용자 환경 설정 #8 ]
        //====================================================================================================
        // 엑셀 다운로드 시 헤더행의 폰트 Bold 스타일을 적용하고 싶은 경우에 설정하세요.
        // 설정을 원하지 않는 경우 주석처리해주세요.
        //====================================================================================================
        //down.setHeaderFontBold(false);

        //====================================================================================================
        // [ 사용자 환경 설정 #9 ]
        //====================================================================================================
        // 엑셀 다운로드 시 헤더행의 글자색을 적용하고 싶은 경우에 설정하세요.
        // #3366FF 형태의 웹 컬러로 설정해주세요.
        // 설정을 원하지 않는 경우 주석처리해주세요.
        //====================================================================================================
        //down.setHeaderFontColor("#FF2233");

        //====================================================================================================
        // [ 사용자 환경 설정 #10 ]
        //====================================================================================================
        // 엑셀 다운로드 시 헤더행의 폰트를 적용하고 싶은 경우에 설정하세요.
        // 설정을 원하지 않는 경우 주석처리해주세요.
        //====================================================================================================
        //down.setHeaderFontName("궁서");

        //====================================================================================================
        // [ 사용자 환경 설정 #11 ]
        //====================================================================================================
        // 엑셀 다운로드 시 헤더행의 폰트 크기를 적용하고 싶은 경우에 설정하세요.
        // 설정을 원하지 않는 경우 주석처리해주세요.
        //====================================================================================================
        //down.setHeaderFontSize(20);

        //====================================================================================================
        // [ 사용자 환경 설정 #12 ]
        //====================================================================================================
        // 줄바꿈 설정을 다음에서 설정한 값으로 강제적으로 적용합니다.
        // 사용하지 않으시려면 주석처리 하세요.
        //====================================================================================================
        //down.setWordWrap(false);

        //====================================================================================================
        // [ 사용자 환경 설정 #13 ]
        //====================================================================================================
        // 엑셀 다운로드 시 서버에 위치한 디자인 파일을 사용하는 경우 디자인 파일이 있는 폴더 위치를 설정하세요.
        // 디자인 파일을 사용하지 않는 경우 주석처리하세요. 
        //====================================================================================================
        //down.setTempRoot("D:/SVN/src/IBSheet7.TestPage");

        //====================================================================================================
        // [ 사용자 환경 설정 #14 ]
        //====================================================================================================
        // 트리 컬럼에서 레벨별로 … 를 덧붙여서 레벨별로 보기 좋게 만듭니다.
        // 만약 … 대신 다른 문자를 사용하기를 원하시면 아래 유니코드 \u2026 (16진수형태) 대신 다른 문자를 입력하십시오.
        // 트리 컬럼이 없으면 설정하지 마세요.
        //====================================================================================================
        //down.setTreeChar("\u2026");

        //====================================================================================================
        // [ 사용자 환경 설정 #15 ]
        //====================================================================================================
        // 엑셀에 포함될 이미지의 URL 이 같은 도메인에 있지만 "/image/imgDown.jsp?idx=365" 등과 같은 
        // 이미지 로딩 방식을 사용한다면 웹서버 도메인을 설정하세요.
        //====================================================================================================
        //down.setWebServerDomain("http://www.ibleaders.co.kr");
		
		//====================================================================================================
        // [ 사용자 환경 설정 #16 ]
        //====================================================================================================
        // 엑셀에 포함될 숫자 형식 데이터를 [통화] 나 [사용자정의] 가 아닌 [숫자] 서식으로 다운로드 합니다.
        // 시트에서 Down2Excel 옵션으로 설정한 NumberExMode를 무시하고 적용됩니다.
        //====================================================================================================
        //down.setNumberExMode(true);
		
		//====================================================================================================
		// [ 사용자 환경 설정 #17 ]
		//====================================================================================================
		// 엑셀 다운로드 시 포함된 이미지의 비율을 맞추고 싶을때 설정하세요..
		// 설정을 원하지 않는 경우 주석처리해주세요.
		// 0 : 셀의 가로/세로에 꽉 차게 이미지를 처리합니다. (기본값)
		// 1 : 셀의 중앙에 이미지를 원본 크기로 표시합니다.
		// 2 : 이미지의 원본 가로/세로 비율을 유지하면서 셀에 맞춥니다.
		//====================================================================================================
		//down.setImageProcessType(0);
		
		//====================================================================================================
		// [ 사용자 환경 설정 #18 ]
		//====================================================================================================
		// 엑셀 파일의 인쇄 항목을 설정하고 싶은 경우에 설정하세요.
		// 설정을 원하지 않는 경우 주석처리해주세요.
		//====================================================================================================
		/**
		ExcelPrintSetup printSetup = new ExcelPrintSetup();
		//컬러 인쇄 여부를 설정합니다. 기본값은 true입니다. false로 설정하면 흑백인쇄 모드가 됩니다.
		printSetup.setColorPrint(true);
		//용지 사이즈를 설정합니다. 설정 가능한 용지는 Letter, Legal, A3, A4, A5, B4, B5 입니다.
		printSetup.setPageSize("A4");
		//용지 방향을 설정합니다. true는 가로, false는 세로 방향입니다.
		printSetup.setLandscape(true);
		//페이지를 나눌때 셀이 잘리지 않도록 설정합니다.
		printSetup.setAutoBreak(true);
		//용지 내에 페이지를 맞춰서 인쇄할 때 사용합니다.
		printSetup.setFitToPage(true);
		// 페이지 내에 열맞춤을 설정합니다. 이 옵션을 사용하려면 setFitToPage(true); 와 setFitHeight(false); 를 함께 설정해야 합니다.
		printSetup.setFitWidth(true);
		// 페이지 내에 행맞춤을 설정합니다. 이 옵션을 사용하려면 setFitToPage(true); 와 setFitWidth(false); 를 함께 설정해야 합니다.
		printSetup.setFitHeight(false);
		//머리글 부분의 여백을 Cm 단위로 설정합니다.
		printSetup.setHeaderMargin(0d);
		//꼬리글 부분의 여백을 Cm 단위로 설정합니다.
		printSetup.setFooterMargin(0d);
		//위쪽 여백을 Cm 단위로 설정합니다.
		printSetup.setTopMargin(0.5);
		//아래쪽 여백을 Cm 단위로 설정합니다.
		printSetup.setBottomMargin(0.5);
		//왼쪽 여백을 Cm 단위로 설정합니다.
		printSetup.setLeftMargin(0.5);
		//오른쪽 여백을 Cm 단위로 설정합니다.
		printSetup.setRightMargin(0.5);
		//엑셀 파일의 인쇄 설정을 파일에 적용합니다.
		down.setPrintSetup(printSetup);
		**/
		//System.out.println(" setData ::::" + down.setData()  );

        // 다운로드 1. Request에 SHEETDATA로 저장된 데이터를 시트에 포함
        //down.setDirectRequestData();
        
		//System.out.println(" setDirectRequestData ::::" + down.setDirectRequestData()  );
	
		
		
		
        // 다운로드 2. 데이터를 직접 insert 하는 방법(appendDataList, appendDataMap 등의 API를 이용)
        // TODO
        /*
        // (예제 1. appendDataMap)
        for (int i = 0; i < 100; i++) {
           Map<String, String> map = new HashMap<String, String>();           
           // SaveName이 sName인 컬럼에 ib 데이터를 set하여 엑셀 파일로 다운로드 
           map.put("sName", "ib");
           down.appendDataMap(map);
        }*/
        
        /*
        // (예제 2. appendDataArr)
        for (int i = 0; i < 100; i++) {
            String[] x = {"data1", "data2", "data3"};
            down.appendDataArr(x);
        }*/
        System.out.println("4");
        // 생성된 문서를 브라우저를 통해 다운로드
        down.downToBrowser();      
        
    } catch (Exception e) {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "");

        out.println("<script>alert('엑셀 다운로드중 에러가 발생하였습니다.');</script>");
        
        e.printStackTrace();

        /* out.print()/out.println() 방식으로 메시지가 정상적으로 출력되지 않는다면 다음과 같은 방식을 사용한다.
        OutputStream out2 = response.getOutputStream();
        out2.write(("오류 메시지").getBytes());
        out2.flush();
        */

    } catch (Error e) {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "");

        out.println("<script>alert('엑셀 다운로드중 에러가 발생하였습니다.');</script>");
        e.printStackTrace();
    } finally {
        if (down != null) {
            try {
                down.close();
            } catch (Exception ex) {}
        }
        down = null;
    }

    // 파일 정상 다운로드시 아래 구문을 실행하지 않으면 서버 Servlet에서  java.lang.IllegalStateException 이 발생한다.
    // 파일 최 하단에서 호출하도록 하면 다운로드 에러로 인한 Exception 메시지가 출력되지 않으므로 정상 다운시에만 처리하도록 한다.
    // out.flush();
    // out = pageContext.pushBody();
%>