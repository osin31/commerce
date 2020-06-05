<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	
	$(function() {
		<%-- 최초 화면 반송지 정보 갱신 --%>
		abc.biz.claim.regist.impossibility.initRcvrInfo();
		
		<%-- 주문자 정보 체크박스 클릭 감지 --%>
		abc.biz.claim.regist.impossibility.checkBox();
		
		<%-- 요청내용 keyup --%>
		$("#clmImpsbltContText").keyup(function() {
			abc.biz.claim.regist.impossibility.clmImpsbltContTextKeyup();
		});
		
		<%-- 배송 시 요청사항 keyup --%>
		$("#dlvyMemoText").keyup(function() {
			abc.biz.claim.regist.impossibility.dlvyMemoTextKeyup();
		});
		
		<%-- 회수지 우편번호 찾기 --%>
        $("#retrievalPostSearchBtn").click(function() {
            abc.postPopup( abc.biz.claim.regist.impossibility.setRetrievalPostPost );
        });
        
		<%-- 클레임 불가 submit --%>
		$("#btnConfirm").click(function(){
			abc.biz.claim.regist.impossibility.registImpossibility();
		});
		
		<%-- 창 닫기 --%>
		$("#btnClose").click(function(){
			window.close();
		});
		
	});
	
</script>

<body class="window-body">
	<!-- 수취인 명 -->
	<input type="hidden" id="idRcvrName" value="${ocOrder.rcvrName}">
	<!-- 수취인 핸드폰번호 -->
	<input type="hidden" id="idRcvrHdphnNoText" value="${fn:trim(fn:replace(ocOrder.rcvrHdphnNoText, '-', ''))}">
	<!-- 수취인 우편번호 -->
	<input type="hidden" id="idRcvrPostCodeText" value="${fn:trim(fn:replace(ocOrder.rcvrPostCodeText, '-', ''))}">
	<!-- 수취인 우편주소 -->
	<input type="hidden" id="idRcvrPostAddrText" value="${ocOrder.rcvrPostAddrText}">
	<!-- 수취인 상세주소 -->
	<input type="hidden" id="idRcvrDtlAddrText" value="${ocOrder.rcvrDtlAddrText}">
	<!-- 배송 시 요청사항 -->
	<input type="hidden" id="idDlvyMemoText" value="${ocOrder.dlvyMemoText}">

	<form id="frmRegist" name="frmRegist" method="post" onsubmit="return false;">
		<input type="hidden" id="clmNo" 		name="clmNo" 		value='<c:out value="${param.clmNo}"/>'>
		<input type="hidden" id="clmPrdtSeq" 	name="clmPrdtSeq"   value='<c:out value="${param.clmPrdtSeq}"/>'>
		<input type="hidden" id="orderNo" 		name="orderNo" 		value='<c:out value="${param.orderNo}"/>'>
		<input type="hidden" id="orderPrdtSeq" 	name="orderPrdtSeq" value='<c:out value="${param.orderPrdtSeq}"/>'>
		
		<div class="window-wrap">
			<div class="window-title">
				<h1>클레임 불가 처리</h1>
			</div>
			<div class="window-content">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">클레임 불가 상품 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>클레임 불가 상품 정보</caption>
					<colgroup>
						<col>
						<col>
						<col>
						<col>
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">업체상품코드</th>
							<th scope="col">상품이미지</th>
							<th scope="col">상품명</th>
							<th scope="col">상품코드</th>
							<th scope="col">옵션</th>
							<th scope="col">판매가</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						<td class="text-center"><c:out value="${ocClaimProduct.vndrPrdtNoText}"/></td>
						<c:choose>
							<c:when test="${empty ocClaimProduct.imageUrl}">
								<td class="input text-center clear-float">
									<img src="../../static/images/temp/temp_prod_img.jpg" alt="임시이미지">
								</td>
							</c:when>
							<c:otherwise>
								<!-- 상품 관련 파일이 존재할 경우 -->
								<td class="input text-center clear-float">
									<a src="#"><img src="${ocClaimProduct.imageUrl}" alt="${ocClaimProduct.altrnText}" style="width:100px; height:100px;"></a>
								</td>
							</c:otherwise>
						</c:choose>
						<td class="text-center"><c:out value="${ocClaimProduct.prdtName}"/></td>
						<td class="text-center"><c:out value="${ocClaimProduct.prdtNo}"/></td>
						<td class="text-center"><c:out value="${ocClaimProduct.optnName}"/></td>
						<td class="text-center"><fmt:formatNumber value="${ocClaimProduct.sellAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>&nbsp;원</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-col -->
	
				<!-- S : 클레임불가 처리 팝업 -->
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>클레임 불가 사유 작성</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">클레임 불가 사유</span>
							</th>
							<td class="input">
								<!-- TODO : 기획 fix 후 option 수정 -->
								<select class="ui-sel" id="clmImpsbltRsnCode" title="클레임 불가 사유 선택">
									<c:if test="${!empty CLM_IMPSBLT_RSN_CODE}">
										<option value="" selected>클레임 불가 사유 선택</option>
										<c:forEach items="${CLM_IMPSBLT_RSN_CODE}" var="code">
											<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">요청 내용</th>
							<td class="input">
								<!-- S : msg-wrap -->
								<span class="msg-wrap">
									<span class="msg-box ip-size-lg">
										<textarea class="ui-textarea" name="clmImpsbltContText" id="clmImpsbltContText" placeholder="요청 내용은 500자 이내로 입력해 주세요."  title="요청 내용 입력"></textarea>
										<span class="text-limit">
											<span class="desc">(<span id="clmImpsbltContTextLength">0</span>/500 자)</span>
										</span>
									</span>
								</span>
								<!-- E : msg-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">운송장정보</span>
							</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" id="logisVndrCode" title="택배사 선택">
										<c:if test="${!empty LOGIS_VNDR_CODE}">
											<option value="" selected>택배사 선택</option>
											<c:forEach items="${LOGIS_VNDR_CODE}" var="code">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</c:if>
									</select>
									<input type="text" class="ui-input" name="waybilNoText" id="waybilNoText" title="운송장번호 입력" placeholder="운송장번호">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">발송예정일</span>
							</th>
							<td class="input">
								<span class="date-box">
									<input type="text" data-role="datepicker" name="dlvyTodoYmd" id="dlvyTodoYmd" class="ui-cal js-ui-cal" title="발송예정일" readonly>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
	
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">반송지 정보</h3>
					</div>
					<div class="fr">
						<span class="ui-chk">
							<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
							<input id="chkModule" type="checkbox" checked>
							<label for="chkModule">주문자 정보와 동일</label>
						</span>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>반송지 정보</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">수령자명</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" name="rcvrName" id="rcvrName" title="수령자명 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">휴대폰번호</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" name="rcvrHdphnNoText" id="rcvrHdphnNoText" title="휴대폰번호 입력 " placeholder="'-' 없이 입력해주세요.">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">주소</span>
							</th>
							<td class="input">
								<!-- S : address-box -->
								<span class="address-box">
									<span class="zip-code-wrap">
										<input type="text" name="rcvrPostCodeText" id="rcvrPostCodeText" class="ui-input" title="우편번호 입력" readonly>
										<button type="button" class="btn-sm btn-link" id="retrievalPostSearchBtn">우편번호 찾기</button>
									</span>
									<span class="address-wrap">
										<input type="text" class="ui-input" name="rcvrPostAddrText" id="rcvrPostAddrText" placeholder="시/군/구 동 번지" title="시/군/구 동 번지 입력" readonly>
										<input type="text" class="ui-input" name="rcvrDtlAddrText" id="rcvrDtlAddrText" placeholder="상세주소" title="상세주소 입력">
									</span>
								</span>
								<!-- E : address-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">배송 시 요청사항</th>
							<td class="input">
								<!-- S : msg-wrap -->
								<span class="msg-wrap">
									<span class="msg-box ip-size-lg">
										<textarea class="ui-textarea" placeholder="배송 메시지는 40자 이내로 입력해 주세요." name="dlvyMemoText" id="dlvyMemoText" title="배송 메세지 입력"></textarea>
										<span class="text-limit">
											<span class="desc">(<span id="dlvyMemoTextLength">0</span>/40 자)</span>
										</span>
									</span>
								<!-- E : msg-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
	
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<ol class="tbl-desc-list">
						<li><strong>* 입력하신 내용은 처리 후 수정이 불가합니다. 정확하게 입력해주세요.</strong></li>
					</ol>
				</div>
				<!-- E : tbl-desc-wrap -->
				<!-- E : 클레임불가 처리 팝업 -->
	
				<!-- S : window-btn-group -->
				<div class="window-btn-group">
					<a href="#" id="btnClose" class="btn-normal btn-del">취소</a>
					<a href="#" id="btnConfirm" class="btn-normal btn-func">클레임 불가 처리</a>
				</div>
				<!-- E : window-btn-group -->
			</div>
		</div>
	</form>
	
	<script src="/static/common/js/biz/claim/abcmart.claim.regist.claim.impossibility.form.pop.js<%=_DP_REV%>"></script>
</body>