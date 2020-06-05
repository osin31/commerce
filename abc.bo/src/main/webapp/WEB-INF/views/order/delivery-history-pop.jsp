<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		abc.biz.order.delivery.history.init();
		
		abc.biz.order.delivery.history.SITE_NO_ART = "${Const.SITE_NO_ART}";
		abc.biz.order.delivery.history.ABC_FO_URL  = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.order.delivery.history.OTS_FO_URL  = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		$("#boPrdt").click(function(){
			abc.biz.order.delivery.history.boPrdtDetailPop(this);
		});
		
		$("#foPrdt").click(function(){
			abc.biz.order.delivery.history.foPrdtDetailPop(this);
		});
		
		$("#vndrDetail").click(function(){
			abc.biz.order.delivery.history.vndrDetailPop(this);
		});
	});
	
	function listSheet_OnRowSearchEnd(row){ 
		if(listSheet.GetRowData(row).adminId != null && listSheet.GetRowData(row).adminName != null && listSheet.GetRowData(row).adminId != "" && listSheet.GetRowData(row).adminName != ""){
			var info = listSheet.GetRowData(row).adminId + "("+listSheet.GetRowData(row).adminName+")"
			listSheet.SetCellValue(row, "adminInfo", info);
		} else {
			var info = "사용자";
			listSheet.SetCellValue(row, "adminInfo", info);
			listSheet.SetCellFontUnderline( row ,"adminInfo" , 0 ) ; // 사용자 링크 제거
		}
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function listSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( listSheet.ColSaveName(Col) == "adminInfo" && Value != "" ) {
				var saveName = "rgsterNo";
				var rgsterNo = listSheet.GetRowData(Row).rgsterNo;
				if( !abc.text.allNull( rgsterNo ) && !rgsterNo.includes("MB") ) {
					abc.adminDetailPopup(rgsterNo);
				}
			}
		}
	}
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>주문배송 히스토리</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<input type="hidden" id="siteNo" value="${ocOrder.siteNo}">
				<div class="fl">
					<h3 class="content-title">[주문번호 <span>${productInfo.orderNo}</span>]</h3>
				</div>				
				<div class="fr">
					<span class="guide-text">주문일시 : <fmt:formatDate value="${productInfo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>주문 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">상품코드</th>
						<td><a href="javascript:void(0)" class="link" id="boPrdt" prdtno="${productInfo.prdtNo}">${productInfo.prdtNo}</a></td>
						<th scope="row">상품명</th>
						<td><a href="javascript:void(0)" class="link" id="foPrdt" prdtno="${productInfo.prdtNo}">${productInfo.prdtName}</a></td>
					</tr>
					<tr>
						<th scope="row">입점사</th>
						<td><a href="javascript:void(0)" class="link" id="vndrDetail" vndrno="${productInfo.vndrNo}">${productInfo.vndrName}(${productInfo.vndrNo})</a></td>
						<th scope="row">업체상품코드</th>
						<td>${productInfo.vndrPrdtNoText}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<form id="listForm" name="listForm" method="post" onsubmit="return false;">
					<input type="hidden" name="orderNo" value="${productInfo.orderNo}">
					<input type="hidden" name="orderPrdtSeq" value="${productInfo.orderPrdtSeq}">
				</form>
				<div  id="listGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.history.js<%=_DP_REV%>">
</script>