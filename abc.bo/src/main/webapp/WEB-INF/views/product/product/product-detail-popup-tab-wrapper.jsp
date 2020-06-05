<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="content-header">
	<div class="fl"><%-- BO/PO 모두 채널영역에 색상으로 현재 작업중인 내용이 표기되므로 별도표시 안함 --%>
		<input type="hidden" id="type" name="type" value="${type }"/><%-- 등록/수정/임시저장 여부 --%>
		<input type="hidden" id="prdt-no" name="prdtNo" value="${product.prdtNo }"/><%-- 상품번호 --%>
		<input type="hidden" id="site-no" name="siteNo" value="${siteNo }"/><%-- 사이트번호 --%>
		<input type="hidden" id="chnnl-no" name="chnnlNo" value="${chnnlNo }"/><%-- 채널번호 --%>
		<input type="hidden" id="mmny-prdt-yn" name="mmnyPrdtYn" value="${product.mmnyPrdtYn }"/><%-- 자사상품여부 --%>
		<input type="hidden" id="vndr-no" name="vndrNo" value="${product.vndrNo }"/><%-- 업체번호 --%>
		<fmt:formatDate var="formatDateFirstWrhsDay" pattern="yyyy-MM-dd hh:mm:ss" value="${product.firstWrhsDay }"/>
		<input type="hidden" id="first-wrhs-day" name="firstWrhsDay" value="${formatDateFirstWrhsDay }"/><!-- 최초입고일 -->
		<fmt:formatDate var="formatDateLastWrhsDay" pattern="yyyy-MM-dd hh:mm:ss" value="${product.lastWrhsDay }"/>
		<input type="hidden" id="last-wrhs-day" name="lastWrhsDay" value="${formatDateLastWrhsDay }"/><!-- 최종입고일 -->
		<input type="hidden" id="disp-flag-text" name="dispFlagText" value="${product.dispFlagText }"/><!-- 티어플래그 -->
	</div>
</div>

<div class="anchor-tab-wrap" style="height: 693px;">
	<div id="tabContent1" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item ui-tabs-active"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab1-basic.jsp"%>
	</div>
	<div id="tabContent2" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab2-display.jsp"%>
	</div>
	<div id="tabContent3" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab3-stockoption.jsp"%>
	</div>
<%-- 상품이미지 탭 --%>
	<div id="tabContent4" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab4-image.jsp"%>
	</div>
<%-- 상세 설명 탭 --%>
	<div id="tabContent5" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab5-description.jsp"%>
	</div>
<%-- 상품 상세정보고시 탭 --%>
	<div id="tabContent6" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab6-noticeinfo.jsp"%>
	</div>
	<div id="tabContent7" class="tab-wrap anchor-tab">
		<ul class="tabs">
			<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
			<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
			<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
			<li class="tab-item"><a href="#tabContent4" class="tab-link">상품이미지</a></li>
			<li class="tab-item"><a href="#tabContent5" class="tab-link">상세 설명</a></li>
			<li class="tab-item"><a href="#tabContent6" class="tab-link">상품 정보제공고시</a></li>
			<li class="tab-item ui-tabs-active"><a href="#tabContent7" class="tab-link">관리자 메모</a></li>
		</ul>
		<%@include file="/WEB-INF/views/product/product/tab/product-detail-popup-tab7-memo.jsp"%>
	</div>
</div>
<div class="window-btn-group">
	<c:choose>
		<c:when test="${type ne 'read' }">
			<%-- 등록/수정 화면인 경우 --%>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<a href="javascript:void(0);" class="btn-normal btn-save" data-button="save">저장</a>
				</c:when>
				<c:otherwise>
					<c:if test="${empty product.aprvStatCode or product.aprvStatCode eq CommonCode.APRV_STAT_CODE_TEMPORARY or product.aprvStatCode eq CommonCode.APRV_STAT_CODE_REJECT }">
						<%-- 신규등록상품이거나 기존 상품상태가 임시저장 또는 승인반려인 경우에만 버튼 노출함 --%>
						<a href="javascript:void(0);" class="btn-normal btn-save" data-button="temporary">임시저장</a>
					</c:if>
					<a href="javascript:void(0);" class="btn-normal btn-func" data-button="save">승인요청</a>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<%-- 읽기전용 화면인 경우 --%>
			<a href="javascript:void(0);" class="btn-normal btn-save" data-button="close">닫기</a>
		</c:otherwise>
	</c:choose>
</div>
