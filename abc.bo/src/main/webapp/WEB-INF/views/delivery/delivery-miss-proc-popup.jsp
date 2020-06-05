<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
		$(function() {
			//초기화
			abc.biz.delivery.miss.proc.popup.init(); 
			
			initAcntAuth();
		});
		
	/*	비회원인 경우                         : isAcntAuth = "N" 
	 *	회원이지만 환불계좌가 없는 경우 : isAcntAuth = "N"
	 *  회원인데 환불계좌가 있는 경우    : isAcntAuth = "Y"
	 *  본 팝업화면에서 환불계좌 인증    : isAcntAuth = "Y"
	 */
	 function initAcntAuth(){
		 var isAcntExist = "${isAcntExist}";
		 
		 if( isAcntExist == "${Const.BOOLEAN_FALSE}"){
			 // 비회원이거나 환불계좌가 없는 경우
			 abc.biz.delivery.miss.proc.popup.isAcntAuth = "${Const.BOOLEAN_FALSE}";
		 }else{
			 // 환불계좌가 있는 경우
			 abc.biz.delivery.miss.proc.popup.isAcntAuth = "${Const.BOOLEAN_TRUE}";
		 }
	 }
	  
	//상품상세 보기
	  function jsProductGoDetail(prdtNo,SiteNo,type){
		var prdtNo = prdtNo;
		var siteNo = '${ocOrder.siteNo}';

		//상품번호
		if(type == "prodNo"){
			var param = { prdtNo : prdtNo };
			 abc.readonlyProductDetailPopup(param);
		}

		var OTS_FO_URL = "";
		var ABC_FO_URL = "";

		//상품명
		if(type == "prodNm"){
			if(siteNo == SiteNo){
					window.open(OTS_FO_URL + "/product?prdtNo=" + prdtNo)			
			} else {
					window.open(ABC_FO_URL + "/product?prdtNo=" + prdtNo)
			}	
		}//end if(type == "prodNm"){

	  }//end function
	  
	  function getFoPrdtDetail(el){
		  var siteNo     = "${ocOrder.siteNo}";
		  var ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		  var OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		  
		  if( siteNo == "${Const.SITE_NO_OTS}"){
			  window.open( OTS_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno") );
		  } else{
			  window.open( ABC_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno") );
		  }
	  }

</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>[주문번호 <span>${ocOrder.orderNo}</span>] 분실처리</h1>
		</div>
		<div class="window-content">
		<form id="gForm" name="gForm">			
			<input type="hidden" id="callBackFunc" name="callBackFunc" value="${callBackFunc}">
			<input type="hidden" id="callType" name="callType" value="${callType}">
			<input type="hidden" id="account" name="accountBankYn" value="N">

		 
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">배송정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>배송정보</caption>
				<colgroup>
					<col style="width: 12%">
					<col style="width: 10%">
					<col style="width: 31%">
					<col style="width: 10%">
					<col style="width: 10%">
					<col style="width: 17%">
					<col style="width: 12%">
					<col style="width: 12%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">상품코드</th>
						<th scope="col">업체상품코드</th>
						<th scope="col">상품명</th>
						<th scope="col">옵션</th>
						<th scope="col">배송상태</th>
						<th scope="col">배송ID</th>
						<th scope="col">택배사</th>
						<th scope="col">송장번호</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty ocOrderProductDeliveryList}">
					<c:forEach var="ocOrderProduct" items="${ocOrderProductDeliveryList}" varStatus="status"> 
					<tr>
						<td class="text-center"><a href="javascript:jsProductGoDetail('${ocOrderProduct.prdtNo}','','prodNo');" class="link">${ocOrderProduct.prdtNo}</a></td>
						<td class="text-center">${ocOrderProduct.vndrPrdtNoText}</td>
						<td class="input clear-float">
							<!-- S : prod-box -->
							<span class="prod-box">
								<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
											썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
											이미지 크기 변경시 css 수정 필요 -->
								<c:choose>
									<c:when test="${ocOrderProduct.imageUrl eq null}"> 
											<span class="thumb-box" style="background-image:url(/static/images/common/no_image.png);"></span>
									</c:when>
									<c:otherwise>
										<span class="thumb-box" style="background-image:url(${ocOrderProduct.imageUrl}?shrink=100:100);"></span>
									</c:otherwise>
								</c:choose> 
								<a href="javascript:void(0);" class="link" prdtno="${ocOrderProduct.prdtNo}" onclick="getFoPrdtDetail(this)">${ocOrderProduct.prdtName}</a>
							</span>
							<!-- E : prod-box -->
						</td>
						<td class="text-center">${ocOrderProduct.optnName}</td>
						<td class="text-center"><a href='javascript:abc.orderProductHistoryPopup(${ocOrderProduct.orderNo},${ocOrderProduct.orderPrdtSeq},"");' class="link">${ocOrderProduct.orderPrdtStatCodeNm}</a></td>
						<td class="text-center">${ocOrderProduct.dlvyIdText}</td>
						<td class="text-center">${ocOrderProduct.logisVndrCodeNm}</td>
						<td class="text-center">${ocOrderProduct.waybilNoText}</td>
					</tr>  
						<input type="hidden" id="dlvyIdText" name="dlvyIdText" value="${ocOrderProduct.dlvyIdText}">
						<input type="hidden" id="orderNo" name="orderNo" value="${ocOrderProduct.orderNo}">
						<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${ocOrder.orgOrderNo}">
						<input type="hidden" id="orderPrdtSeq" name="orderPrdtSeq" value="${ocOrderProduct.orderPrdtSeq}">
						<input type="hidden" id="orderDlvyHistSeq" name="orderDlvyHistSeq" value="${ocOrderProduct.orderDlvyHistSeq}">						
						<input type="hidden" id="rcvrName" name="rcvrName" value="${ocOrderProduct.rcvrName}">
						<input type="hidden" id="rcvrTelNoText" name="rcvrTelNoText" value="${ocOrderProduct.rcvrTelNoText}">
						<input type="hidden" id="rcvrHdphnNoText" name="rcvrHdphnNoText" value="${ocOrderProduct.rcvrHdphnNoText}">
						<input type="hidden" id="rcvrPostCodeText" name="rcvrPostCodeText" value="${ocOrderProduct.rcvrPostCodeText}">
						<input type="hidden" id="rcvrPostAddrText" name="rcvrPostAddrText" value="${ocOrderProduct.rcvrPostAddrText}">
						<input type="hidden" id="rcvrDtlAddrText" name="rcvrDtlAddrTextv" value="${ocOrderProduct.rcvrDtlAddrText}">
						<input type="hidden" id="dlvyMemoText" name="dlvyMemoText" value="${ocOrderProduct.dlvyMemoText}">
						<input type="hidden" id="dlvyStatCodeNm" name="dlvyStatCodeNm" value="${ocOrderProduct.dlvyStatCodeNm}">
						<input type="hidden" id="logisVndrCodeNm" name="logisVndrCodeNm" value="${ocOrderProduct.logisVndrCodeNm}">
						<input type="hidden" id="waybilNoText" name="waybilNoText" value="${ocOrderProduct.waybilNoText}">
						</c:forEach>
				</c:if>
				</tbody>
			</table>
			<!-- E : tbl-col -->
		
		  <!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">환불정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>환불정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">환불계좌</th>
						<td class="input">
							<!-- S : refund-account-box -->
							<span class="refund-account-box">
								<select id="bankCode" name="bankCode" class="ui-sel" title="은행선택">
									<c:if test="${isAcntExist eq Const.BOOLEAN_TRUE}">
										<option value="${memberInfo.bankCode}">${memberInfo.bankName}</option>
									</c:if>
									<c:if test="${isAcntExist eq Const.BOOLEAN_FALSE}">
										<option value="">은행선택</option>
										<c:forEach var="code" items="${BANK_CODE}" varStatus="status">
											<option value="${code.addInfo4}">${code.codeDtlName}</option>
										</c:forEach>
									</c:if>
								</select> 
								<span class="refund-input">
									<c:if test="${isAcntExist eq Const.BOOLEAN_TRUE}">
										<input type="text" class="ui-input account-number" id="rfndAcntText" name="rfndAcntText" value="${memberInfo.acntNoText}" readonly>
										<input type="text" class="ui-input account-holder" id="acntHldrName" name="acntHldrName" value="${memberInfo.acntHldrName}" readonly>
									</c:if>
									<c:if test="${isAcntExist eq Const.BOOLEAN_FALSE}">
										<input type="text" class="ui-input account-number" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력" id="rfndAcntText" name="rfndAcntText" maxlength="20">
										<input type="text" class="ui-input account-holder" placeholder="예금주명" title="예금주명 입력" id="acntHldrName" name="acntHldrName" maxlength="30">
										<button type="button" class="btn-sm btn-func" id="accountCertifyBtn">계좌인증</button>
									</c:if>
								</span>
							</span>
							<!-- E : refund-account-box -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" id="deliveryMissCancel" class="btn-normal btn-del">분실주문취소</a>
				<a href="javascript:void(0);" id="deliveryMissReDelivery" class="btn-normal btn-func">분실재배송</a>
			</div>
			<!-- E : window-btn-group -->
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.miss.proc.popup.js<%=_DP_REV%>"></script>