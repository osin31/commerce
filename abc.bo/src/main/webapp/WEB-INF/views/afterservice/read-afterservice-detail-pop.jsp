<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	
	abc.biz.afterservice.commonpop.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
	abc.biz.afterservice.commonpop.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
	
	abc.biz.afterservice.commonpop.SITE_NO_ART	= "${Const.SITE_NO_ART}";
	abc.biz.afterservice.commonpop.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
	
	<%-- AS 저장 --%>
	/* $("#acceptSaveBtn").click(function(){
		abc.biz.abcmart.afterservice.detailInfo.acceptSaveBtn();
	}); */
	
	<%-- 접수번호가 조회가 안될때  --%>
	var errorMessage = '<c:out value="${errorMessage}"/>';
	if(errorMessage != null && errorMessage != ""){
		alert(errorMessage);
		self.close();
	}
	
	<%-- 수령지 우편번호 입력(숫자만) --%>
	$("#buyerPostCodeText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	});
	
	<%-- 회수지 우편번호 입력(숫자만) --%>
	$("#rcvrPostCodeText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	});
	
	<%-- 재수령인 연락처(숫자만) --%>
	$("#rcvrHdphnNoText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	});
	
	<%-- 발송인 연락처(숫자만) --%>
	$("#buyerHdphnNoText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	});
	
	$("#buyerName").keyup(function(){
		$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
	});	
	$("#rcvrName").keyup(function(){
		$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
	});	
	
	<%-- 내용보기 --%>
	$("#reasonBtn").click(function(){
		abc.biz.afterservice.detailInfo.readReason();
	});
	
	<%--  단계별로 상태 업데이트  --%>
	$("#statusChange").off().on('click', function(){
		abc.biz.afterservice.detailInfo.statusChange();
	});
	
	<%-- 회원 관리자 메모 저장 --%>
	$("#memoSaveBtn").click(function(){
		abc.biz.afterservice.detailInfo.createMemo();
	});
	
	<%-- 관리자 메모 글자수 체크 --%>
	$("#memoText").keyup(function(e){
		var content = $(this).val();
		abc.biz.afterservice.detailInfo.stringLengthCheck(content);
	});
	
	<%-- 회원 관리자 메모 삭제 --%>
	$(document).on("click", ".btn-msg-list-del", function(event){
		var idx = $(this).parent().parent().parent("li").index();
		var classNm = $(this).parent().parent().attr("class");
		
		abc.biz.afterservice.detailInfo.removeMemo(idx, classNm);
	});
	
	<%-- 사유 1차 선택한값  --%>
	var asGbnCode = '<c:out value="${asInfo.asGbnCode}"/>';
	abc.biz.afterservice.detailInfo.detailAsGbnCodeSet(asGbnCode);

	<%-- 사유 2차 선택한값  --%>
	var asRsnCode = '<c:out value="${dataSubInfo.asRsnCode}"/>';
	abc.biz.afterservice.detailInfo.detailAsRsnCodeSet(asRsnCode);

	<%-- 처리내용  --%>
	var asProcTypeCode = '<c:out value="${dataSubInfo.asProcTypeCode}"/>';
	abc.biz.afterservice.detailInfo.detailAsProcTypeCodeSet(asProcTypeCode);
	
	<%-- 처리  2차 선택한값  --%>
	var asProcTypeDtlCode = '<c:out value="${dataSubInfo.asProcTypeDtlCode}"/>';
	abc.biz.afterservice.detailInfo.detailAsProcTypeDtlCodeSet(asProcTypeDtlCode);

	
	<%-- 배송비용  --%>
	var addDlvyAmt = '<c:out value="${asInfo.addDlvyAmt}"/>';
	abc.biz.afterservice.detailInfo.detailAddDlvyAmtCodeSet(addDlvyAmt);

	<%-- 철회유형 선택값   --%>
	var asWthdrawRsnCode = '<c:out value="${asInfo.asWthdrawRsnCode}"/>';
	abc.biz.afterservice.detailInfo.detailAsWthdrawRsnCodeSet(asWthdrawRsnCode);
	
	<%-- 배송유형 선택값   --%>
	var logisVndrCode = '<c:out value="${asInfo.logisVndrCode}"/>';
	abc.biz.afterservice.detailInfo.detailLogisVndrCodeSet(logisVndrCode);

	<%--  임시저장 --%>
	$("#temporaryStorage").off().on('click', function(){
		abc.biz.afterservice.detailInfo.temporaryStorage();
	});
	
	<%--  A/S 접수 철회 --%>
	$("#withdrawAccept").off().on('click', function(){
		abc.biz.afterservice.detailInfoStatusCheck.withdrawAccept();
	});
	
	<%-- A/S 비용 금액(숫자만)  --%>
	$("#asAmt").keyup(function(){
		abc.text.validateOnlyNumber(this);
		// 금액을 DB에서 불러온 금액이 아니라 키업했을경우 
		$("#asAmtDisplay").text($(this).val()+"원");
		if($("#addDlvyAmt option:selected").val() == '0' || $("#addDlvyAmt option:selected").val() == '999'){
			//console.log("123");
			$("#totalPymntAmtDisp").text($(this).val()+"원");
		}else{
			if('${asInfo.asGbnCode}' == abc.biz.afterservice.constCode.asGbnCodeRepair){
				//console.log("456");
				$("#totalPymntAmtDisp").text(Number($(this).val())+"원");
			}else{
				//console.log("789");
				$("#totalPymntAmtDisp").text(Number($("#addDlvyAmt option:selected").val())+Number($(this).val())+"원");
			}
		}
	});
	
	<%-- 송장번호(숫자만)  --%>
	$("#waybilNoText").keyup(function(){
		abc.text.validateOnlyNumber(this);
	});
	
	<%--  결제 취소 버튼 --%>
	$("#dlvyPayCancel").off().on('click', function(){
		abc.biz.afterservice.detailInfo.dlvyPayCancel();
	});
	
	
	<%--  미처리 CHK 여부 --%>
	$("#unProcYnChk").change(function(){
        if($("#unProcYnChk").is(":checked")){
        	$('#unProcYn').val('N');
        }else{
        	$('#unProcYn').val('Y');
        }
    });
	
	<%--  회원상세   팝업  --%>
	var memberNo = '<c:out value="${asInfo.memberNo}"/>';
	$("#memberInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.memberInfoPop(memberNo);
	});
	
	<%--  접수자  팝업  --%>
	var adminYn = '<c:out value="${asInfo.adminAcceptYn}"/>';
	var asRgsterNo = '<c:out value="${asInfo.asRgsterNo}"/>';
	$("#regsterInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.regsterInfoPop(adminYn,asRgsterNo);
	});
	
	<%--  처리자 팝업  --%>
	var moderNo = '<c:out value="${asInfo.moderNo}"/>';
	$("#moderInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.moderInfoPop(moderNo);
	});
	
	<%--  주문번호 팝업  --%>
	var orderNo = '<c:out value="${asInfo.orgOrderNo}"/>';
	$("#orderNoInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.orderNoInfoPop(orderNo);
	});
	
	<%--  BO 상품상세   --%>
	var prdtNo = '<c:out value="${dataSubInfo.prdtNo}"/>';
	$("#prdtPop").click(function(){
		abc.biz.afterservice.commonpop.prdtPop(prdtNo);
	});
	
	<%--  FO 상품상세   --%>
	var prdtNo = '<c:out value="${dataSubInfo.prdtNo}"/>';
	$("#prdtInfoPop").click(function(){
		abc.biz.afterservice.commonpop.prdtInfoPop(prdtNo);
	});
	
	<%--  회수택배팝업   --%>
	$(".rtrLogisVndrPopup").click(function(){
		abc.biz.afterservice.commonpop.rtrLogisVndrPopup(this);
	});
	
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>A/S 상세</h1>
		</div>
		
		<form id="frmInfo" name="frmInfo" method="post" onsubmit="return false;">
			<input type="hidden" id="asAcceptNo" name="asAcceptNo" value="${asInfo.asAcceptNo}">
			<input type="hidden" id="asStatCode" name="asStatCode" value="${asInfo.asStatCode}">
			<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${asInfo.orgOrderNo}">
			<input type="hidden" id="orderNo" name="orderNo" value="${asInfo.orderNo}">
			<input type="hidden" id="asRgsterNo" name="asRgsterNo" value="${asInfo.asRgsterNo}">
			<input type="hidden" id="memberNo" name="memberNo" value="${asInfo.memberNo}">
			<input type="hidden" id="asAcceptPrdtSeq" name="asAcceptPrdtSeq" value="${dataSubInfo.asAcceptPrdtSeq}">
			
			<input type="hidden" id="asStatCodeName" name="asStatCodeName" value="${asInfo.asStatCodeName}">
			<input type="hidden" id="orgBuyerPostCodeText" name="orgBuyerPostCodeText" value="${asInfo.buyerPostCodeText}">
			<input type="hidden" id="orgBuyerPostAddrText" name="orgBuyerPostAddrText" value="${asInfo.buyerPostAddrText}">
			<input type="hidden" id="orgBuyerDtlAddrText" name="orgBuyerDtlAddrText" value="${asInfo.buyerDtlAddrText}">
			<input type="hidden" id="orgBuyerName" name="orgBuyerName" value="${asInfo.buyerName}">
			<input type="hidden" id="orgBuyerHdphnNotext" name="orgBuyerHdphnNotext" value="${asInfo.buyerHdphnNoText}">
			
			<input type="hidden" id="orgRcvrPostCodeText" name="orgRcvrPostCodeText" value="${asInfo.rcvrPostCodeText}">
			<input type="hidden" id="orgRcvrPostAddrText" name="orgRcvrPostAddrText" value="${asInfo.rcvrPostAddrText}">
			<input type="hidden" id="orgRcvrDtlAddrText" name="orgRcvrDtlAddrText" value="${asInfo.rcvrDtlAddrText}">
			<input type="hidden" id="orgRcvrName" name="orgRcvrName" value="${asInfo.rcvrName}">
			<input type="hidden" id="orgRcvrHdphnNotext" name="orgRcvrHdphnNotext" value="${asInfo.rcvrHdphnNoText}">
			<input type="hidden" id="asGbnCode" name="asGbnCode" value="${asInfo.asGbnCode}">
			<input type="hidden" id="asRsnCode" name="asRsnCode" value="${dataSubInfo.asRsnCode}">
			<input type="hidden" id="prdtName" name="prdtName" value="${dataSubInfo.prdtName}">
			<input type="hidden" id="asAcceptContText" name="asAcceptContText" value="${dataSubInfo.asAcceptContText}">
			<input type="hidden" id="adminAcceptYn" name="adminAcceptYn" value="${asInfo.adminAcceptYn}">
			<input type="hidden" id="totalAsAmt" name="totalAsAmt" value="${asInfo.totalAsAmt}">
			<input type="hidden" id="unProcYn" name="unProcYn" value="">
			<%-- 심의완료에서 수선불가 선택 후 무상이나 유상으로 돌릴시 이전 기억값 --%>
			<input type="hidden" id="hisStatVal" name="hisStatVal">
			<input type="hidden" id="hisStatCode" name="hisStatCode">
			<%-- 심의완료에서 수선불가 선택 후 무상이나 유상으로 돌릴시 이전 기억값 --%>
			
			<%-- 상태별 alert 문구 --%>
			<input type="hidden" id="messageVal" name="messageVal" value="">
			
			<%-- FO상품상세 통합몰/OTS 구분 --%>
			<input type="hidden" id="siteNo" value="${asInfo.orgSiteNo}">
			
			<div class="window-content">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">[A/S번호 ${asInfo.asAcceptNo}] <a href="javascript:void(0)"  id="memberInfoPop"  class="link">${asInfo.loginId}&nbsp;(${asInfo.memberName})</a>님 A/S 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>A/S 상세 정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">진행상태${asInfo.unProcYn}</th>
							<td class="input">
								<%-- <span class="text" id="asStatCodeNameDis">${asInfo.asStatCodeName}</span> --%>
								<span class="text">${asInfo.asStatCodeName}</span>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="unProcYnChk"  type="checkbox" <c:if test="${asInfo.unProcYn eq Const.BOOLEAN_FALSE}">checked</c:if>>
									<label for="unProcYnChk">미처리표시</label>
								</span>
							</td>
							<th scope="row">접수자</th>
							<td><a href="javascript:void(0)" id="regsterInfoPop"  class="link">${asInfo.rgsterName}</a></td>
							
							<th scope="row">접수일시</th>
							<td><fmt:formatDate value="${asInfo.asAcceptDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">사이트·주문번호</th>
							<td>${asInfo.siteNo}·<a href="javascript:void(0)" id="orderNoInfoPop"  class="link">${asInfo.orgOrderNo}</a></td>
							<th scope="row">처리자</th>
							<td>
							<c:choose>
								<c:when test="${asInfo.moderId eq null and asInfo.moderName eq null}">
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0)" id="moderInfoPop" class="link">${asInfo.moderName}</a>
								</c:otherwise>
							</c:choose>
							</td>
							<th scope="row">처리일시</th>
							<td><fmt:formatDate value="${asInfo.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
				
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">자사 상품 A/S 정보</h3>
					</div>
				<!-- 	<div class="fr">
						<a href="#" id="updateProduct" class="btn-sm btn-save">저장</a>
					</div> -->
				</div>
				<!-- E : content-header -->
	
				<!-- S : 20190304 수정 // IBSheet > table로 변경 -->
				<div class="tbl-wrap scroll-x">
					<table class="tbl-col">
						<caption>자사 상품 A/S 정보</caption>
						<colgroup>
							<col style="width: 100px;">
							<col style="width: 300px;">
							<col style="width: 100px;">
							<col style="width: 100px;">
							<col style="width: 100px;">
							<col style="width: 200px;">
							<col style="width: 80px;">
							<col style="width: 370px;">
							<col style="width: 200px;">
							<col style="width: 270px;">
							<!-- <col style="width: 100px;"> -->
						</colgroup>
						<thead>
							<tr>
								<th scope="col">상품코드</th>
								<th scope="col">상품명</th>
								<th scope="col">옵션</th>
								<th scope="col">결제금액</th>
								<th scope="col">스타일코드</th>
								<th scope="col"><span class="th-required">A/S사유</span></th>
								<th scope="col"><span class="th-required">내용</span></th>
								<th scope="col">처리(심의)유형/내용</th>
								<th scope="col">A/S 비용</th>
								<th scope="col">A/S 배송정보</th>
								<!--<th scope="col">A/S 불가</th>  -->
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center"><a href="javascript:void(0)" id="prdtPop" class="link">${asInfo.prdtNo}</a></td>
								<td class="input clear-float">
									<!-- S : prod-box -->
									<span class="prod-box">
										<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
													썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
													이미지 크기 변경시 css 수정 필요 -->
										<c:choose>
											<c:when test="${asInfo.imageUrl eq null}">
												<span class="thumb-box" style="background-image:url('/static/images/common/no_image.png');"></span>
											</c:when>
											<c:otherwise>
												<span class="thumb-box" style="background-image:url(${asInfo.imageUrl}?shrink=100:100); background-size: 100px 100px;"></span>
											</c:otherwise>
										</c:choose>
										<a href="javascript:void(0)" id="prdtInfoPop" class="link">${asInfo.prdtName}</a>
									</span>
									<!-- E : prod-box -->
								</td>
								<td class="text-center">${asInfo.optnName}</td>
								<td class="text-center"><fmt:formatNumber value="${asInfo.orderAmt}" pattern="#,##0" />원</td>
								<td class="text-center">${asInfo.styleInfo}</td>
								<td class="input clear-float text-center">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel Reason" title="A/S사유 선택" id="gbnCode" name="asGbnCode">
											<c:if test="${!empty codeList.AS_GBN_CODE}">
												<c:forEach items="${codeList.AS_GBN_CODE}" var="asGbnCode" >
													<option value="${asGbnCode.codeDtlNo}">${asGbnCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
										<select class="ui-sel Reason" title="A/S사유 선택" id="rsnCode" name="asRsnCode">
											<c:if test="${!empty codeList.AS_RSN_CODE}">
												<c:forEach items="${codeList.AS_RSN_CODE}" var="asRsnCode">
													<option value="${asRsnCode.codeDtlNo}">${asRsnCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
									</span>
									<!-- E : ip-text-box -->
								</td>
								<td class="input clear-float text-center">
								<c:choose>
									<c:when test="${asInfo.asAcceptContText eq null}">
										<a href="javascript:void(0)" id="reasonBtn" class="btn-sm btn-link" >등록</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="reasonBtn" class="btn-sm btn-link" >보기</a>
									</c:otherwise>
								</c:choose>
									
								</td>
								<td class="input clear-float text-center">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel " title="처리(심의)유형 선택" id="asProcTypeCode" name="asProcTypeCode">
												<option value="">선택하세요</option>
											<c:if test="${!empty codeList.AS_PROC_TYPE_CODE}">
												<c:forEach items="${codeList.AS_PROC_TYPE_CODE}" var="asProcTypeCode">
													<option value="${asProcTypeCode.codeDtlNo}">${asProcTypeCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
										<select class="ui-sel" title="처리(심의)유형 선택" id="asProcTypeDtlCode" name="asProcTypeDtlCode">
											<c:if test="${!empty codeList.AS_PROC_TYPE_DTL_CODE}">
												<c:forEach items="${codeList.AS_PROC_TYPE_DTL_CODE}" var="asProcTypeDtlCode">
													<option value="${asProcTypeDtlCode.codeDtlNo}" data-info="${asProcTypeDtlCode.addInfo1}" data-price="${asProcTypeDtlCode.addInfo2}">${asProcTypeDtlCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
										<input type="text" id="asProcContText" name="asProcContText" class="ui-input" title="내용 입력" placeholder="내용" value="${dataSubInfo.asProcContText}">
									</span>
									<!-- E : ip-text-box -->
								</td>
								<td class="input clear-float text-center">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" id="asAmt" name="asAmt" class="ui-input num-unit100000000" title="a/s비용 입력" value="${dataSubInfo.asAmt == 0 ? 0 : dataSubInfo.asAmt}" maxlength="10">
										<span class="text">원</span>
									</span>
									<!-- E : ip-text-box -->
								</td>
								<td class="input clear-float text-center">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel LastChild" title="택배사 선택" id="logisVndrCode" name="logisVndrCode">
											<option value="">선택하세요</option>
											<c:if test="${!empty codeList.LOGIS_VNDR_CODE}">
												<c:forEach items="${codeList.LOGIS_VNDR_CODE}" var="loCode">
													<option value="${loCode.codeDtlNo}">${loCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
										<input type="text" class="ui-input LastChild" id="waybilNoText" name="waybilNoText" title="송장번호 입력" placeholder="송장번호" value="${asInfo.waybilNoText }">
									</span>
									<!-- E : ip-text-box -->
								</td>
								<!-- <td class="input clear-float text-center">
									<a href="#" class="btn-sm btn-link">A/S불가</a>
								</td> -->
							</tr>
						</tbody>
					</table>
				</div>
				<!-- E : 20190304 수정 // IBSheet > table로 변경 -->
	
				<!-- S : col-wrap -->
				<div class="col-wrap">
					<div class="col">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">접수 기본정보</h3>
							</div>
							<!-- <div class="fr">
								<a href="#" class="btn-sm btn-save">저장</a>
							</div> -->
						</div>
						<!-- E : content-header -->
	
						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>접수 기본정보</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">접수처</th>
									<td>온라인</td>
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">A/S상품 회수지</span>
									</th>
									<td class="input">
										<span class="address-box">
											<span class="zip-code-wrap">
												<span class="fl">
													<input type="text" class="ui-input" id="buyerPostCodeText" name="buyerPostCodeText"title="우편번호 입력" readonly="readonly" value="${asInfo.buyerPostCodeText}">
													<button type="button" id="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
												</span>
												<span class="fr">
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDeliveryModule" name="Dlvr01" type="checkbox">
														<label for="chkDeliveryModule">배송주소와 동일</label>
													</span>
												</span>
											</span>
											<span class="address-wrap">
												<input type="text" class="ui-input" id="buyerPostAddrText" name="buyerPostAddrText" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly="readonly" value="${asInfo.buyerPostAddrText}">
												<input type="text" class="ui-input" id="buyerDtlAddrText" name="buyerDtlAddrText" placeholder="상세주소" title="상세주소 입력" value="${asInfo.buyerDtlAddrText}">
											</span>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">회수 택배정보</th>
									<td>${asInfo.rtrvlVndrCodeName}&nbsp;<a href="javascript:void(0)" class="link rtrLogisVndrPopup">${asInfo.rtrvlWaybilNoText}</a></td>
									<input type="hidden" name="rtrvlLogisVndrCodeAddInfo1" value="${asInfo.rtrvlLogisVndrCodeAddInfo1}">
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">발송인 명/연락처</span>
									</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<input type="text" id="buyerName" name="buyerName" class="ui-input name" title="발송인명 입력" value="${asInfo.buyerName}">
											<span class="text">/</span>
											<input type="text" id="buyerHdphnNoText" name="buyerHdphnNoText" class="ui-input phone-number" title="연락처 입력" value="${asInfo.buyerHdphnNoText}" maxlength="11">
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">A/S상품 <br />재발송 수령지</span>
									</th>
									<td class="input">
										<span class="address-box">
											<span class="zip-code-wrap">
												<span class="fl">
													<input type="text" id="rcvrPostCodeText" name="rcvrPostCodeText" class="ui-input" title="우편번호 입력" readonly="readonly" value="${asInfo.rcvrPostCodeText}">
													<button type="button" id="rePostSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
												</span>
												<span class="fr">
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDeliveryModule02" name="Dlvr02" type="checkbox">
														<label for="chkDeliveryModule02">배송주소와 동일</label>
													</span>
												</span>
											</span>
											<span class="address-wrap">
												<input type="text" id="rcvrPostAddrText" name="rcvrPostAddrText" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly="readonly" value="${asInfo.rcvrPostAddrText}">
												<input type="text" id="rcvrDtlAddrText" name="rcvrDtlAddrText" class="ui-input" placeholder="상세주소" title="상세주소 입력" value="${asInfo.rcvrDtlAddrText}">
											</span>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">재수령자명/연락처</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<input type="text" id="rcvrName" name="rcvrName"   class="ui-input name" title="재수령자명 입력" value="${asInfo.rcvrName}">
											<span class="text">/</span>
											<input type="text" id="rcvrHdphnNoText" name="rcvrHdphnNoText" class="ui-input phone-number" title="연락처 입력" value="${asInfo.rcvrHdphnNoText}" maxlength="11">
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->
					</div>
					
					<div class="col">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">A/S비용</h3>
							</div>
							<!-- <div class="fr">
								<a href="#" id="updateAmt" class="btn-sm btn-save">저장</a>
							</div> -->
						</div>
						<!-- E : content-header -->
	
						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>A/S비용</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<c:choose>
									<c:when test="${asInfo.adminAcceptYn eq 'Y' or  asInfo.asGbnCode eq CommonCode.AS_GBN_CODE_REVIEW}">
										<tr>
											<th scope="row">배송비</th>
											<td class="input">
												<!-- S : 20190318 수정 // 배송비영역 결제취소 버튼 추가 -->
												<span class="ip-text-box">
													<select class="ui-sel" id="addDlvyAmt"  name="addDlvyAmt" title="배송비 선택">
														<option value="">선택</option>
														<option value="0">무료</option>
														<option value="2500">2,500</option>
														<option value="5000">5,000</option>
													</select>
												</span>
												<!-- E : 20190318 수정 // 배송비영역 결제취소 버튼 추가 -->
											</td>
										</tr>
									</c:when>
									<c:when test="${!empty dataPayInfo and asInfo.adminAcceptYn eq 'N' and  asInfo.asGbnCode eq CommonCode.AS_GBN_CODE_REPAIR}">
										<tr>
											<th scope="row">배송비</th>
											<td class="input">
												<!-- S : td-box-both -->
												<span class="td-box-both">
													<span class="fl">
														<input type="hidden" id="asPymntSeq" name="asPymntSeq" value="${dataPayInfo.asPymntSeq}"/>
														<input type="hidden" id="pymntAmt" name="pymntAmt" value="${dataPayInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL ? asInfo.addDlvyAmt : dataPayInfo.pymntAmt}"/>
														<a href="javascript:void(0)" id="dlvyPayCancel" class="btn-sm btn-del" style="display: none;">결제취소</a>
														<span class="text">${dataPayInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL ? asInfo.addDlvyAmt : dataPayInfo.pymntAmt}원</span>
													</span>
												</span>
												<!-- E : td-box-both -->
												<span class="text payment-state">
													<c:choose>
														<c:when test="${dataPayInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_CARD}">
															<span class="tc-blue">[${dataPayInfo.pymntStatCodeName}]</span> 
															<c:choose>
																<c:when test="${dataPayInfo.cardGbnType eq 'N'}">
																	<span>일반카드</span>
																</c:when>
																<c:when test="${dataPayInfo.cardGbnType eq 'C'}">
																	<span>체크카드</span>	
																</c:when>
																<c:otherwise>
											 						<span></span>
																</c:otherwise>
															</c:choose>
															<span>(${dataPayInfo.pymntOrganName}-</span><span><c:out value="${dataPayInfo.instmtTermCount eq '0' ? '일시불' : dataPayInfo.instmtTermCount}"/><c:out value="${dataPayInfo.instmtTermCount eq '0' ? '' : '개월'}"/>)</span>
															<span>${dataPayInfo.pymntCmlptDate}</span>
														</c:when>
														<c:otherwise>
															<span class="tc-blue">[${dataPayInfo.pymntStatCodeName}]</span><span>${dataPayInfo.pymntOrganName}</span><span>${dataPayInfo.pymntCmlptDate}</span></span>
														</c:otherwise>
													</c:choose>
												</span>
											</td>
										</tr>
									</c:when>
								</c:choose>
								<!-- E : 20190318 수정 // 고객 선결제 완료인경우 -->
								<!-- S : 결제 금액 노출 -->
								<tr>
									<th scope="row">A/S비용</th>
									<td><span id="asAmtDisplay"></span></td>
								</tr>
								<tr>
									<th scope="row">합계</th>
									<input type="hidden" id="totalPymntAmt" name="totalPymntAmt" value="${asInfo.totalPymntAmt}"/>
									<td><span id="totalPymntAmtDisp"></span></td>
								</tr>
								<!-- E : 결제 금액 노출 -->
	
								<tr>
									<th scope="row">대표계좌</th>
									<td class="input">
										<span class="text">
											<!-- <span class="tc-blue">[입금완료 or 결제완료]</span> 
											<span class="tc-red">[입금기한만료]</span> -->
											<c:choose>
												<c:when test="${asInfo.orgSiteNo eq Const.SITE_NO_ART}">
													국민은행 385001-04-017427   (예금주 : (주)에이비씨마트코리아)
												</c:when>
												<c:when test="${asInfo.orgSiteNo eq Const.SITE_NO_OTS}">
													국민은행 375301-04-004127   (예금주 : (주)에이비씨마트코리아)
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>
										</span>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->
					</div>
				</div>
				<!-- E : col-wrap -->
				<c:choose>
					<c:when test="${asInfo.asStatCode eq CommonCode.AS_STAT_CODE_ACCEPT || asInfo.asStatCode eq CommonCode.AS_STAT_CODE_WITHDRAWAL_ACCEPT }">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">A/S 접수 철회 정보</h3>
							</div>
						</div>
						<!-- E : content-header -->
			
						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>A/S 접수 철회 정보</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">철회 사유</th>
									<td class="input">
										<select class="ui-sel" id="asWthdrawRsnCode" name="asWthdrawRsnCode" title="철회 사유 선택">
											<option value="">철회유형</option>
											<c:if test="${!empty codeList.AS_WTHDRAW_RSN_CODE}">
														<c:forEach items="${codeList.AS_WTHDRAW_RSN_CODE}" var="asWthdrawRsnCode">
															<option value="${asWthdrawRsnCode.codeDtlNo}">${asWthdrawRsnCode.codeDtlName}</option>
														</c:forEach>
													</c:if>
										</select>
			
										<textarea id="asWthdrawContText" name="asWthdrawContText" class="ui-textarea" title="철회 사유 입력">${asInfo.asWthdrawContText}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row">철회일시</th>
									<td><fmt:formatDate value="${asInfo.asWthdrawDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->
						</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<!-- S : window-btn-group -->
				<div class="window-btn-group">
					<!-- DESC : 화면별 버튼 변경시 버튼 UI .btn-normal.btn-func 사용 -->
					<a href="javascript:void(0)" id="withdrawAccept" class="btn-normal btn-func">A/S접수철회</a>
					<a href="javascript:void(0)" id="temporaryStorage" class="btn-normal btn-func">내용저장</a>
					<a href="javascript:void(0)" id="statusChange" class="btn-normal btn-func">수거지시</a>
					<a href="javascript:void(0)" id="close" onclick="javascript:self.close();" class="btn-normal btn-func" style="display: none;">확인</a>
 				</div>
				<!-- E : window-btn-group -->
	
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관리자 메모</h3>
					</div>
						<div class="fr">
						<span class="text-limit">(<span id="counter" class="state-num">0</span>/500자)</span>
					</div>
				</div>
				<!-- E : content-header -->
				
				<!-- S : manager-msg-wrap -->
				<div class="manager-msg-wrap">
					<div class="msg-box">
						<textarea id="memoText" title="관리자 메모 입력"></textarea>
					</div>
					<button type="button" id="memoSaveBtn" class="btn-normal btn-save">저장</button>
				</div>
				<!-- E : manager-msg-wrap -->

				<!-- S : msg-list-wrap -->
				<div class="msg-list-wrap">
					<ul id="memoArea"  class="msg-list">
						<c:forEach var="memoList" items="${memoInfo}">
							<li>
								<span class="msg-list-info">
									<span class="user-info">
										<span class="user-id">${memoList.asMemoSeq} / <c:out value="${memoList.loginId}"/></span>
										<span class="user-name">(<c:out value="${memoList.adminName}"/>)</span>
									</span>
									<span class="regist-date-wrap">
										<span class="regist-date"><fmt:formatDate value="${memoList.rgstDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
										<input type="hidden" value="${memoList.asMemoSeq}">
										<a href="javascript:void(0)" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
									</span>
								</span>
								<p class="msg-desc">
									${memoList.memoText}
								</p>
							</li>
						</c:forEach>
					</ul>
				</div>
				<!-- E : msg-list-wrap -->
			</div>
		</form>
	</div>
</body>
<script>
 $(document).ready(function(){
	 $("#buyerHdphnNoText").val(abc.text.validateStringSignRemove($("#buyerHdphnNoText").val()));
	 $("#rcvrHdphnNoText").val(abc.text.validateStringSignRemove($("#rcvrHdphnNoText").val()));
	// 진행상태코드
	var asStatCode    = '${asInfo.asStatCode}';
	// 관리자 접수 여부 
	var adminAcceptYn = '${asInfo.adminAcceptYn}';
	// 사유 구분 코드
	var asGbnCode     = '${asInfo.asGbnCode}';
	// aas비용
	var asAmt         = '${dataSubInfo.asAmt}';
	// 결제한 배송비 금액
	var asPymntAmt    = '${dataPayInfo.pymntAmt}';
	// 관리자 선택하는 배송비용
	var addDlvyAmt    = '${asInfo.addDlvyAmt}';
	// 관리자 FO 에서 접수한 결제를 취소여부 
	var redempRfndGbnType    = '${dataPayInfo.redempRfndGbnType}';
	// 배송비 임시 변수 
	var dlvyAmt;
	// 합계에 노출용으로 쓰이는 임시 변수 
	var tempAmt;
	// 합계 금액 
	var totalPymntAmt = '${asInfo.totalPymntAmt}';
	
	//console.log("금액");
	// 배송비 불러오는 컬럼 관리자 접수이면 addDlvrAmt , 사용자 접수이면 pymntAmt
 	if(adminAcceptYn == 'N' && asGbnCode == abc.biz.afterservice.constCode.asGbnCodeRepair){
		dlvyAmt = asPymntAmt;
 		//console.log("사용자");
 		//console.log(dlvyAmt);
	}else{
		dlvyAmt = addDlvyAmt;
 		//console.log("관리자");
 		//console.log(dlvyAmt);
	} 
	
	if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview){
	//심의 > 무상 선택이면서 합계 금액 0원이면 배송중 처리 단계  
		if(Number(totalPymntAmt) == 0 && $("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeFree  && asStatCode == abc.biz.afterservice.constCode.asStatCodeRepairFinish){
			abc.biz.afterservice.detailInfoStatusCheck.detailReviewDisabled(abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip,adminAcceptYn,asGbnCode);
		}else{
			// 유상 , 수선불가 일때 
			abc.biz.afterservice.detailInfoStatusCheck.detailReviewDisabled(asStatCode,adminAcceptYn,asGbnCode);
		}
	}else{
		//수선 > 무상 선택이면서 합계 금액 0원이면 배송중 처리 단계
		if(Number(totalPymntAmt) == 0 && $("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeFree  && asStatCode == abc.biz.afterservice.constCode.asStatCodeRepairFinish){
			abc.biz.afterservice.detailInfoStatusCheck.detailRepairDisabled(abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip,adminAcceptYn,asGbnCode);
		}else{
			// 유상 , 수선불가 일때 
			abc.biz.afterservice.detailInfoStatusCheck.detailRepairDisabled(asStatCode,adminAcceptYn,asGbnCode);
		}
	}
	
	// 처리유형이 유상이 아니면 as비용 비활성화 처리
	if($("#asProcTypeCode option:selected").val() != '' && $("#asProcTypeCode option:selected").val() != null){
		if($("#asProcTypeCode option:selected").val() != abc.biz.afterservice.constCode.asPocTypeCodeRemark){
			$("#asAmt").prop("readonly", true);
		}
	}
	//console.log("START :::");
	//AS 비용 및 배송비용 존재할때 
	//if($('#asAmt').val() != '' && $('#asAmt').val() != null){
	if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeRepair){
		console.log("수선 ???");
		// AS비용 디스플레이 금액에  AS비용을 넣어줌.
		$('#asAmtDisplay').text($('#asAmt').val()+'원')
		// 관리자 접수한건이면 
		if(adminAcceptYn == 'Y'){
			//배송비 + AS비용을 합산하여 합계에 보여줌. 금액이 존재할때 
			tempAmt = Number($('select[name=addDlvyAmt]').val() == 999 ? 0:$('select[name=addDlvyAmt]').val())+Number($('#asAmt').val());
		}else{
			// 사용자가 접수하였으면 결제한 금액 이고  관리자가 접수하였으면 선택한 금액 (심의 또는 수선에 따라 보는 컬럼이 틀림 )
			if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview){
				tempAmt = Number($('select[name=addDlvyAmt]').val() == 999 ? 0:$('select[name=addDlvyAmt]').val())+Number($('#asAmt').val());
			}else{
				tempAmt = Number($('#asAmt').val());
			}
			
		}
		// 실제 DB에 값이 들어가는 컬럼 
		$('#totalPymntAmt').val(tempAmt);
		// 화면에 display 만 되는 변수용
		$('#totalPymntAmtDisp').text(tempAmt+'원');
	}else{
		//console.log("심의 ???");
		// 비용이 존재하지 않을때 관리자 접수이면 
		if(adminAcceptYn == 'Y'){
			// 배송비 0일수도있고 0보다 클수도 있음 AS비용과합산  0+10 
			tempAmt = Number(dlvyAmt)+Number($('#asAmt').val());
			//console.log("1 :::" + tempAmt);
			//console.log("1-1 :::" + Number(dlvyAmt));
			
			$('#asAmtDisplay').text($('#asAmt').val()+'원')
			// 실제 DB에 값이 들어가는 컬럼 
			$('#totalPymntAmt').val(tempAmt);
			// 화면에 display 만 되는 변수용
			$('#totalPymntAmtDisp').text(tempAmt+'원');
		}else{
			// 사용자 접수건이면서 심의건 이면 배송비 0일수도있고 0보다 클수도 있음 AS비용과합산  0+10 
			if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview){
				tempAmt = Number($('select[name=addDlvyAmt]').val() == 999 ? 0:$('select[name=addDlvyAmt]').val())+Number($('#asAmt').val());
				//console.log("3-1 :::" +  Number(addDlvyAmt));
				//console.log("3-2 :::" + Number($('#asAmt').val()));
				//console.log("3-3 :::" + tempAmt);
			}else{
				//  수선건이면 배송비 결제한 금액 + AS비용 
				tempAmt = Number($('#asAmt').val());
			}
			$('#asAmtDisplay').text($('#asAmt').val()+'원')
			// 실제 DB에 값이 들어가는 컬럼 
			$('#totalPymntAmt').val(tempAmt);
			// 화면에 display 만 되는 변수용
			$('#totalPymntAmtDisp').text(tempAmt+'원');
		}
	}
	
	// read시 배송비용 0원으므로 무료가 선택이 . 화면 로드시 일시적으로 999으로 셋팅
	// 수선완료 이상 이라면 화면에 무료 라고 선택되게 만듬
	if(Number(asStatCode) >= Number(abc.biz.afterservice.constCode.asStatCodeRepairFinish)){
	}else{
		$("#addDlvyAmt option:eq(1)").replaceWith("<option value='999'>무료</option>");	
	}
	
	
	// 배송비 금액이 0보다 클때만 노출. 
	if(Number(asPymntAmt) == 0){
		// 배송비 결제 취소 버튼 
		$("#dlvyPayCancel").hide();
	}
	
	//결제를 취소 했다면 결제취소 버튼 숨김
	if(redempRfndGbnType == '${CommonCode.REDEMP_RFND_GBN_TYPE_REFUND}'){
		$("#dlvyPayCancel").hide();
	}
	
	
}); 
</script>
</html>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.detailInfo.js<%=_DP_REV%>"></script>
<!-- 상태에따라서 처리되는 버튼 input box controller  -->
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.detailInfoStatusCheck.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.constCode.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.commonpop.js<%=_DP_REV%>"></script>