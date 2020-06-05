<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		//입점사정보등록 초기화
		abc.biz.vendor.info.base.init();
	});

	$(document).ready(function(){
		//입점사 대표담당자 정보
		if($("#baseInfoStatus").val() == 'C'){
			abc.biz.vendor.info.base.managerInfo();
		}
	});
</script>


<!-- S:tab_content -->
<div id="tabContent1" class="tab-content">
<form id="vendorSearchForm" name="vendorSearchForm">
	<input type="hidden" name="s_vndrNo" value="<c:out value='${param.vndrNo}'/>">
	<input type="hidden" name="s_channelCheckAll" value="<c:out value='${param.channelCheckAll}'/>">
	<input type="hidden" name="s_selChnnlNoArr" value="<c:out value='${param.selChnnlNoArr}'/>">
	<input type="hidden" name="s_vndrStdCtgrNo" value="<c:out value='${param.vndrStdCtgrNo}'/>">
	<input type="hidden" name="s_vndrStatCode" value="<c:out value='${param.vndrStatCode}'/>">
	<input type="hidden" name="s_exceptionCommissionApplyYn" value="<c:out value='${param.exceptionCommissionApplyYn}'/>">

	<input type="hidden" name="s_vndrBrandNo" value="<c:out value='${param.vndrBrandNo}'/>">
	<input type="hidden" name="s_brandName" value="<c:out value='${param.brandName}'/>">
	<input type="hidden" name="s_searchKey" value="<c:out value='${param.searchKey}'/>">
	<input type="hidden" name="s_searchValue" value="<c:out value='${param.searchValue}'/>">
	<input type="hidden" name="s_employeeDiscountApplyYn" value="<c:out value='${param.employeeDiscountApplyYn}'/>">
	<input type="hidden" name="s_fromDate" value="<c:out value="${param.fromDate}"/>">
	<input type="hidden" name="s_toDate" value="<c:out value="${param.toDate}"/>">
	<input type="hidden" name="listPageNum" value="<c:out value="${param.listPageNum}"/>">
	<input type="hidden" name="pageCount" value="<c:out value="${param.pageCount}"/>">
</form>
<form id="vendorBaseForm" name="vendorBaseForm">
	<input type="hidden" id="managerIndex" value="0">
	<input type="hidden" id="baseInfoStatus" value="<c:out value='${vendorInfo.status}' default='C'/>">
	<input type="hidden" id="vndrNo" name="vndrNo" value="<c:out value='${vendorInfo.vndrNo}'/>">

	<!-- 입점사 업체 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-info.jsp" %>
	<!-- 입점사 업체 정보  -->

	<!-- 입점사 설정 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-setupinfo.jsp" %>
	<!-- 입점사 설정 정보  -->

	<!-- 거래 계좌 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-acntinfo.jsp" %>
	<!-- 거래 계좌 정보  -->

	<!-- 기본정산 수수료 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-dfltcmsnrate.jsp" %>
	<!-- 기본정산 수수료 정보  -->

	<!-- 입점사 대표담당자 정보  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-manager-detail.jsp" %>
	<div id="vendorManagerAddDiv" class="content-header"></div>
	<!-- 입점사 대표담당자 정보  -->
	
	<!-- s : 191018 as담당자 정보 추가 -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-asmngrtext.jsp" %>
	<!-- e : 191018 as담당자 정보 추가 -->
	
	<!-- 배송안내  -->
	<%@include file="/WEB-INF/views/vendor/info/basetab/vendor-deliveryguide.jsp" %>
	<!-- 배송안내  -->

	<!-- 업체쿠폰 적용설정  -->
	<%-- <%@include file="/WEB-INF/views/vendor/info/basetab/vendor-applycoupon.jsp" %> --%>
	<!-- 업체쿠폰 적용설정  -->


	<!-- S : tbl-desc-wrap -->
	<div class="tbl-desc-wrap">
		<!-- <div class="fl">
			<a href="#" class="btn-normal btn-del">삭제</a>
		</div> -->
		<div class="fr">
			<a href="javascript:void(0);" id="vendorInfoGoList" class="btn-normal btn-link">목록</a>
		</div>
	</div>
	<!-- E : tbl-desc-wrap -->

	<!-- S : content-bottom -->
	<div class="content-bottom">
		<a href="javascript:void(0);" id="vedorBasicInfoSave" class="btn-lg btn-save">저장</a>
	</div>
	<!-- E : content-bottom -->
</form>
</div>
<!-- E:tab_content -->

<script src="/static/common/js/biz/system/abcmart.system.holiday.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/vendor/abcmart.vendor.info.baseInfoTab.js<%=_DP_REV%>"></script>
