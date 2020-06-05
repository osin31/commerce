<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="kr.co.abcmart.common.request.Parameter"%>
<%@ page import="kr.co.abcmart.common.config.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="kr.co.abcmart.constant.Const" var="Const" />
<un:useConstants className="kr.co.abcmart.constant.CommonCode" var="CommonCode" />

<%
	//※ 삭제 금지. 배포시  배포날짜+revision 경로로 취합하기 위함. 
	String _DP_REV = Config.getString("deploy.static.rev","");
%>