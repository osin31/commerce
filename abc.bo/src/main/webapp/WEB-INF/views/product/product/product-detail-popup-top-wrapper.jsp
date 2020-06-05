<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${product.mmnyPrdtYn eq 'Y' }">
		<%@include file="/WEB-INF/views/product/product/top/product-detail-popup-top-channel-bo.jsp"%>
	</c:when>
	<c:when test="${product.mmnyPrdtYn eq 'N' }">
		<%@include file="/WEB-INF/views/product/product/top/product-detail-popup-top-channel-po.jsp"%>
	</c:when>
	<c:otherwise>
		상품 자사상품여부값이 불확실합니다.(${product.mmnyPrdtYn })
	</c:otherwise>
</c:choose>
