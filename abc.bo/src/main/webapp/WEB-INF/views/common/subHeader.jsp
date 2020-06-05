<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="kr.co.abcmart.constant.Const" var="Const" />
<un:useConstants className="kr.co.abcmart.constant.CommonCode" var="CommonCode" />
<!DOCTYPE html>
<html>
<head>
	<title><c:choose>
			<c:when test="${pageContext.request.serverName eq Const.LOGIN_DOMAIN_BO}">A-RT BACK OFFICE</c:when>
			<c:otherwise>3TOP PARTNER OFFICE</c:otherwise>
		</c:choose>
	</title>
	<meta charset="UTF-8">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, width=device-width">
	<%@include file="/WEB-INF/views/common/java.jsp" %>
	<%@include file="/WEB-INF/views/common/static.jsp" %>
</head>
