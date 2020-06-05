<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">

	$(function() {
		
		abc.biz.claim.history.pop.init();

		abc.biz.claim.history.pop.NON_MEMBER_NO			= "${Const.NON_MEMBER_NO}";
		
		abc.biz.claim.history.pop.CLM_GBN_CODE_CANCEL	= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.claim.history.pop.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.claim.history.pop.CLM_GBN_CODE_RETURN	= "${CommonCode.CLM_GBN_CODE_RETURN}";
		
		abc.biz.claim.history.pop.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.history.pop.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.claim.history.pop.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.history.pop.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		$("#clmNoClick").click(function(){
			
			var clmNo 		= "${ocClaimProduct.clmNo}";
			var clmGbnCode 	= "${ocClaimProduct.clmGbnCode}";
			
			abc.biz.claim.history.pop.openClaimDetailPop(clmNo, clmGbnCode);
		});
		
		$("#boPrdt").click(function(){
			abc.biz.claim.history.pop.boPrdtDetailPop();
		});
		
		$("#foPrdt").click(function(){
			abc.biz.claim.history.pop.foPrdtDetailPop();
		});
		
		$("#vndrDetail").click(function(){
			abc.biz.claim.history.pop.vndrDetailPop();
		});
	});
	
	<%-- 그리드 Click 이벤트 --%>
	function listSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.claim.history.pop.listSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 그리드 Search 후 이벤트 --%>
	function listSheet_OnRowSearchEnd(Row){
		
		abc.biz.claim.history.pop.listSheetOnRowSearchEnd(Row);
	}
	
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>클레임 상품 이력</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title"><a href="#" class="link" id="clmNoClick">클레임 번호  <span>${ocClaimProduct.clmNo}</span></a></h3>
				</div>				
				<div class="fr">
					<span class="guide-text">클레임 일시 : <fmt:formatDate value="${ocClaimProduct.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>클레임 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<!-- BO/FO 상품 상세 팝업 호출 위한 input:hidden -->
						<input type="hidden" id="prdtNo" value="${ocClaimProduct.prdtNo}">
						<input type="hidden" id="siteNo" value="${ocClaimProduct.siteNo}">
						<th scope="row">상품코드</th>
						<td><a href="javascript:void(0)" class="link" id="boPrdt">${ocClaimProduct.prdtNo}</a></td>
						<th scope="row">상품명</th>
						<td><a href="javascript:void(0)" class="link" id="foPrdt">${ocClaimProduct.prdtName}</a></td>
					</tr>
					<tr>
						<!-- 입점사 상세 팝업 호출 위한 input:hidden -->
						<input type="hidden" id="vndrNo" value="${ocClaimProduct.vndrNo}">
						<th scope="row">입점사</th>
						<td><a href="javascript:void(0)" class="link" id="vndrDetail">${ocClaimProduct.vndrName}&nbsp;(${ocClaimProduct.vndrNo})</a></td>
						<th scope="row">업체상품코드</th>
						<td>${ocClaimProduct.vndrPrdtNoText}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<form id="listForm" name="listForm" method="post" onsubmit="return false;">
					<input type="hidden" name="clmNo" value="${ocClaimProduct.clmNo}">
					<input type="hidden" name="clmPrdtSeq" value="${ocClaimProduct.clmPrdtSeq}">
				</form>
				<div  id="listGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/claim/abcmart.claim.history.pop.js<%=_DP_REV%>">
</script>