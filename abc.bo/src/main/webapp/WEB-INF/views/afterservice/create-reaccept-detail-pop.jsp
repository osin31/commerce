<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<jsp:useBean id="toDay" class="java.util.Date" />
<script type="text/javascript">
$(document).ready(function(){
	
	abc.biz.afterservice.commonpop.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
	abc.biz.afterservice.commonpop.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
	
	abc.biz.afterservice.commonpop.SITE_NO_ART	= "${Const.SITE_NO_ART}";
	abc.biz.afterservice.commonpop.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
	
	<%-- AS 저장 --%>
	$("#acceptReSaveBtn").click(function(){
		abc.biz.afterservice.reaccept.acceptReSaveBtn($('#asAcceptNo').val(),$('#asAcceptPrdtSeq').val());
	});
	
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

	<%--  미처리 CHK 여부 --%>
	$("#unProcYnChk").change(function(){
        if($("#unProcYnChk").is(":checked")){
        	$('#unProcYn').val('N');
        }else{
        	$('#unProcYn').val('Y');
        }
    });
	
	<%--  회원상세   팝업  --%>
	var memberNo = '<c:out value="${asMemberDeliInfo.memberNo}"/>';
	$("#memberInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.memberInfoPop(memberNo);
	});
	
	<%--  접수자  팝업  재접수 관리자만 할수 있음.  --%>
	var adminYn = $('#adminAcceptYn').val();
	var rgsterNo = $('#rgsterNo').val();
	$("#regsterInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.regsterInfoPop(adminYn,rgsterNo);
	});
	
	<%--  처리자 팝업  --%>
	var moderNo = '<c:out value="${asOldInfo.moderNo}"/>';
	$("#moderInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.moderInfoPop(moderNo);
	});
	
	<%--  주문번호 팝업  --%>
	var orderNo = '<c:out value="${asOldInfo.orderNo}"/>';
	$("#orderNoInfoPop").off().on('click', function(){
		abc.biz.afterservice.commonpop.orderNoInfoPop(orderNo);
	});
	
	<%--  BO 상품상세   --%>
	var prdtNo = '<c:out value="${asProductOldInfo.prdtNo}"/>';
	$("#prdtPop").click(function(){
		abc.biz.afterservice.commonpop.prdtPop(prdtNo);
	});
	
	<%--  FO 상품상세   --%>
	var prdtNo = '<c:out value="${asProductOldInfo.prdtNo}"/>';
	$("#prdtInfoPop").click(function(){
		abc.biz.afterservice.commonpop.prdtInfoPop(prdtNo);
	});
	
	// 접수 기본정보 배송주소와 동일 체크 트리거
	$("input:checkbox[id='chkDeliveryModule']").trigger("click");
	$("input:checkbox[id='chkDeliveryModule02']").trigger("click");
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>A/S 상세</h1>
		</div>
		
		<form id="frmInfo" name="frmInfo" method="post" onsubmit="return false;">
			<input type="hidden" id="orgBuyerPostCodeText" name="orgBuyerPostCodeText" value="${asOldInfo.buyerPostCodeText}">
			<input type="hidden" id="orgBuyerPostAddrText" name="orgBuyerPostAddrText" value="${asOldInfo.buyerPostAddrText}">
			<input type="hidden" id="orgBuyerDtlAddrText" name="orgBuyerDtlAddrText" value="${asOldInfo.buyerDtlAddrText}">
			<input type="hidden" id="dlvyTypeCode" name="dlvyTypeCode" value="${asOldInfo.dlvyTypeCode}">
			<input type="hidden" id="orgBuyerName" name="orgBuyerName" value="${asOldInfo.buyerName}">
			<input type="hidden" id="orgBuyerHdphnNotext" name="orgBuyerHdphnNotext" value="${asOldInfo.buyerHdphnNoText}">
			<input type="hidden" id="prdtName" name="prdtName" value="${asProductOldInfo.prdtName}">
			<input type="hidden" id="asAcceptContText" name="asAcceptContText" value="">
			<input type="hidden" id="siteNo" name="siteNo" value="${asOldInfo.siteNo}">
			<input type="hidden" id="memberNo" name="memberNo" value="${asOldInfo.memberNo}">
			<input type="hidden" id="memberTypeCode" name="memberTypeCode" value="${asOldInfo.memberTypeCode}">
			<input type="hidden" id="mbshpGradeCode" name="mbshpGradeCode" value="${asOldInfo.mbshpGradeCode}">
			<input type="hidden" id="deviceCode" name="deviceCode" value="${asOldInfo.deviceCode}">
			<input type="hidden" id="orderNo" name="orderNo" value="${asOldInfo.orderNo}">
			<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${asOldInfo.orgOrderNo}">
			<input type="hidden" id="orgOrderDtm" name="orgOrderDtm" value="${asOldInfo.orgOrderDtm}">
			<input type="hidden" id="empYn" name="empYn" value="${asOldInfo.empYn}">
			<input type="hidden" id="otsVipYn" name="otsVipYn" value="${asOldInfo.otsVipYn}">
			<input type="hidden" id="prdtOptnNo" name="prdtOptnNo" value="${asProductOldInfo.prdtOptnNo}">
			<input type="hidden" id="prdtColorCode" name="prdtColorCode" value="${asProductOldInfo.prdtColorCode}">
			<input type="hidden" id="prdtTypeCode" name="prdtTypeCode" value="${asProductOldInfo.prdtTypeCode}">
			<input type="hidden" id="prdtNo" name="prdtNo" value="${asProductOldInfo.prdtNo}">
			<input type="hidden" id="optnName" name="optnName" value="${asProductOldInfo.optnName}">
			<input type="hidden" id="styleInfo" name="styleInfo" value="${asProductOldInfo.styleInfo}">
			<input type="hidden" id="adminAcceptYn" name="adminAcceptYn" value="Y">
			<input type="hidden" id="asAcceptPrdtSeq" name="asAcceptPrdtSeq" value="${asProductOldInfo.asAcceptPrdtSeq}">
			<input type="hidden" id="asAcceptNo" name="asAcceptNo" value="${asOldInfo.asAcceptNo}">
			<input type="hidden" id="rgsterNo" name="rgsterNo" value="${userInfo.getAdminNo() }">
			<input type="hidden" id="unProcYn" name="unProcYn" value="N">
			
			<div class="window-content">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">[A/S번호] <a href="javascript:void(0)" id="memberInfoPop"  class="link"><span>${asMemberDeliInfo.loginId}</span> (<span>${asMemberDeliInfo.memberName}</span>)</a>님 A/S 정보</h3>
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
							<th scope="row">진행상태</th>
							<td class="input">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="unProcYnChk" type="checkbox" <c:if test="${asOldInfo.unProcYn eq Const.BOOLEAN_TRUE}">checked</c:if>>
									<label for="unProcYnChk">미처리표시</label>
								</span>
							</td>
							<th scope="row">접수자</th>
							<td><a href="javascript:void(0)"  id="regsterInfoPop" class="link">${userInfo.getLoginId()}(${userInfo.getUsername() })</a></td>
							<th scope="row">접수일시</th>
							<td><fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<th scope="row">사이트·주문번호</th>
							<td>${siteName}&nbsp;<a href="javascript:void(0)" id="orderNoInfoPop" class="link">{asOldInfo.orgOrderNo}</a></td>
							<th scope="row">처리자</th>
							<td></td>
							<th scope="row">처리일시</th>
							<td></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
	
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">자사 상품 A/S 정보</h3>
					</div>
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
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center"><a href="javascript:void(0)" id="prdtPop"  class="link">${asProductOldInfo.prdtNo}</a></td>
								<td class="input clear-float">
									<!-- S : prod-box -->
									<span class="prod-box">
										<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
													썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
													이미지 크기 변경시 css 수정 필요 -->
										<c:choose>
											<c:when test="${asProductOldInfo.imageUrl eq null}">
												<span class="thumb-box" style="background-image:url('/static/images/common/no_image.png');"></span>
											</c:when>
											<c:otherwise>
												<span class="thumb-box" style="background-image:url(${asProductOldInfo.imageUrl}?shrink=100:100); background-size: 100px 100px;"></span>
											</c:otherwise>
										</c:choose>
										<a href="javascript:void(0)" id="prdtInfoPop"  class="link">${asProductOldInfo.prdtName}</a>
									</span>
									<!-- E : prod-box -->
								</td>
								<td class="text-center">${asProductOldInfo.optnName}</td>
								<td class="text-center"><fmt:formatNumber value="${asProductOldInfo.orderAmt}" pattern="#,##0" />원</td>
								<td class="text-center">${asProductOldInfo.styleInfo}</td>
								<td class="input clear-float text-center">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel" title="A/S사유 선택" id="gbnCode" name="asGbnCode">
											<c:if test="${!empty codeList.AS_GBN_CODE}">
												<c:forEach items="${codeList.AS_GBN_CODE}" var="asGbnCode" >
													<option value="${asGbnCode.codeDtlNo}">${asGbnCode.codeDtlName}</option>
												</c:forEach>
											</c:if>
										</select>
										<select class="ui-sel" title="A/S사유 선택" id="rsnCode" name="asRsnCode">
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
									<button type="button" id="reasonBtn" class="btn-sm btn-link">등록</button>
								</td>
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
													<input type="text" class="ui-input" id="buyerPostCodeText" name="buyerPostCodeText" title="우편번호 입력" readonly="readonly" value="">
													<button type="button" id="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
												</span>
												<span class="fr">
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDeliveryModule" type="checkbox">
														<label for="chkDeliveryModule">배송주소와 동일</label>
													</span>
												</span>
											</span>
											<span class="address-wrap">
												<input type="text" class="ui-input" id="buyerPostAddrText" name="buyerPostAddrText"placeholder="시/군/구 동" title="시/군/구 동 입력" readonly="readonly" value="">
												<input type="text" class="ui-input" id="buyerDtlAddrText" name="buyerDtlAddrText" placeholder="상세주소" title="상세주소 입력" value="">
											</span>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">회수 택배정보</th>
									<td></td>
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">발송인 명/연락처</span>
									</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<input type="text" id="buyerName" name="buyerName"class="ui-input name" title="발송인명 입력" value="">
											<span class="text">/</span>
											<input type="text" id="buyerHdphnNoText" name="buyerHdphnNoText"class="ui-input phone-number" title="연락처 입력" value="" maxlength="11"/>
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
													<input type="text" id="rcvrPostCodeText" name="rcvrPostCodeText" class="ui-input" title="우편번호 입력" readonly="readonly" value="">
													<button type="button" id="rePostSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
												</span>
												<span class="fr">
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDeliveryModule02" type="checkbox">
														<label for="chkDeliveryModule02">배송주소와 동일</label>
													</span>
												</span>
											</span>
											<span class="address-wrap">
												<input type="text" id="rcvrPostAddrText" name="rcvrPostAddrText" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly="readonly" value="">
												<input type="text" id="rcvrDtlAddrText" name="rcvrDtlAddrText" class="ui-input" placeholder="상세주소" title="상세주소 입력"  value="">
											</span>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">재수령자명/연락처</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<input type="text" id="rcvrName" name="rcvrName" class="ui-input name" title="재수령자명 입력" value="">
											<span class="text">/</span>
											<input type="text" id="rcvrHdphnNoText" name="rcvrHdphnNoText" class="ui-input phone-number" title="연락처 입력" value="" maxlength="11">
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
								<tr>
									<th scope="row">배송비</th>
									<td class="input">현 화면에서 입력하는 값이 없습니다. </td>
								</tr>
								<!-- S : 결제 금액 노출 -->
								<tr>
									<th scope="row">A/S비용</th>
									<td></td>
								</tr>
								<!-- E : 결제 금액 노출 -->
	
								<!-- S : 가상계좌재발급하는 경우 -->
								<tr>
									<th scope="row">결제수단</th>
									<td class="input">현 화면에서 입력하는 값이 없습니다. </td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->
					</div>
				</div>
				<!-- E : col-wrap -->
	
				<!-- S : window-btn-group -->
				<div class="window-btn-group" >
					<!-- DESC : 화면별 버튼 변경시 버튼 UI .btn-normal.btn-func 사용 접수완료??-->
					<a href="javascript:void(0)" id="acceptReSaveBtn" class="btn-normal btn-func" >AS접수</a>
				</div>
				<!-- E : window-btn-group -->
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//$("#buyerHdphnNoText").val(abc.text.validateStringSignRemove($("#buyerHdphnNoText").val()));
	//$("#rcvrHdphnNoText").val(abc.text.validateStringSignRemove($("#rcvrHdphnNoText").val()));
});
</script>
</html>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.reaccept.js<%=_DP_REV%>"></script>
<!--  공통으로 쓰는 팝업 모음-->
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.commonpop.js<%=_DP_REV%>"></script>