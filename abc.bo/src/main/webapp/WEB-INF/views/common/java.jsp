<%@page import="kr.co.abcmart.user.LoginManager"%>
<%@page import="kr.co.abcmart.user.UserDetails"%>
<%@page import="kr.co.abcmart.common.request.Parameter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//파라메터 조작이 필요할 경우.
	Parameter<?> parameter = new Parameter(request,response);
	// 스크립트 릿 ,jstl을 이용 하여  값을 전달 받을 수 있다. 
	UserDetails userDetail = LoginManager.getUserDetails();
	pageContext.setAttribute("userDetails", userDetail);
%>