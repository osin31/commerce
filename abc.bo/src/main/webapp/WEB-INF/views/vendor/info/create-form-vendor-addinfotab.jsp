<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
$(function() {
	//입점사정보등록 초기화
	abc.biz.vendor.info.add.init();
});
</script>


<!-- S:tab_content -->
<form id="vendorAddForm" name="vendorAddForm">
<input type="hidden" id="vndrNo" name="vndrNo" value="<c:out value='${vendorInfo.vndrNo}'/>">

	<!-- 업체예외수수료 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/addtab/vendor-exception-commission.jsp" %>
	<!-- 업체예외수수료 정보  -->

	<!-- 업체브랜드임직원할인 정보  -->
	<%-- 	<%@include file="/WEB-INF/views/vendor/info/addtab/vendor-brand-empdiscount.jsp" %> --%>
	<!-- 업체브랜드임직원할인 정보  -->

	<!-- 상품전체공지 정보  -->
	<%-- <%@include file="/WEB-INF/views/vendor/info/addtab/vendor-all-notc.jsp" %> --%>
	<!-- 상품전체공지 정보  -->

	<!-- 입점사 입출고 설정  -->
	<%@include file="/WEB-INF/views/vendor/info/addtab/vendor-dlvy-address.jsp" %>
	<!-- 입점사 입출고 설정  -->

	<!-- S : tbl-desc-wrap -->
	<div class="tbl-desc-wrap">
		<!-- <div class="fl">
			<a href="#" class="btn-normal btn-del">삭제</a>
		</div> -->
		<div class="fr">
			<a href="javascript:void(0);" id="vendorInfoGoList1" class="btn-normal btn-link">목록</a>
		</div>
	</div>
	<!-- E : tbl-desc-wrap -->

	<!-- S : content-bottom -->
	<div class="content-bottom">
		<a href="javascript:void(0);" id="vedorAddInfoSave" class="btn-lg btn-save">부가정보 등록</a>
	</div>
	<!-- E : content-bottom -->
</form>
<!-- E:tab_content -->

<script src="/static/common/js/biz/vendor/abcmart.vendor.info.addInfoTab.js<%=_DP_REV%>"></script>