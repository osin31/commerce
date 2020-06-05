<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
		$(function() { 
				//초기화
				abc.biz.store.pick.delivery.change.popup.init();
		});
	  
	  //수령지 보기
	  function setDeliveryAddressView(type){
		abc.biz.store.pick.delivery.change.popup.getDeliveryAddressView(type);
	  }

	//상품상세 보기
	  function setPorductDetail(prdtNo){
		  abc.biz.store.pick.delivery.change.popup.getPorductDetail(prdtNo);
	  }

</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>택배전환 정보입력</h1>
		</div>
		<div class="window-content">
		<form id="gForm" name="gForm">			
			<input type="hidden" id="orgRcvrName" name="orgRcvrName"	value="${ocOrder.buyerName}">
			<input type="hidden" id="orgRcvrTelNoText" name="orgRcvrTelNoText"	value="${ocOrder.buyerTelNoText}">
			<input type="hidden" id="orgRcvrHdphnNoText" name="orgRcvrHdphnNoText" value="${ocOrder.buyerHdphnNoText}">
			<input type="hidden" id="orgRcvrPostCodeText" name="orgRcvrPostCodeText" value="${ocOrder.buyerPostCodeText}">
			<input type="hidden" id="orgRcvrPostAddrText" name="orgRcvrPostAddrText" value="${ocOrder.buyerPostAddrText}">
			<input type="hidden" id="orgRcvrDtlAddrText" name="orgRcvrDtlAddrText" value="${ocOrder.buyerDtlAddrText}">
			<input type="hidden" id="orgOrderNo" name="orgRcvrDtlAddrText" value="${ocOrder.orderNo}">
			<input type="hidden" id="orderNo" name="orderNo" value="${ocOrder.orderNo}">
			<input type="hidden" id="deliveryAddressEmptyCnt" name="deliveryAddressEmptyCnt" value="${deliveryAddressEmptyCnt}">
			<input type="hidden" id="callBackFunc" name="callBackFunc" value="${callBackFunc}">
			<input type="hidden" id="callType" name="callType" value="${callType}">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">매장픽업 주문 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
			<caption>매장픽업 주문 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">매장픽업 지점명</th>
						<td>${ocOrder.storeName}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>매장픽업 주문 정보</caption>
				<colgroup>
					<col>
					<col>
					<col>
					<col>
					<col>
					<!-- S : 20190308 추가 // 보기 필드 추가되어 col 추가 -->
					<col>
					<!-- E : 20190308 추가 // 보기 필드 추가되어 col 추가 -->
				</colgroup>
				<thead>
					<tr>
						<th scope="col">상품이미지</th>
						<th scope="col">상품코드</th>
						<th scope="col">상품명</th>
						<th scope="col">옵션</th>
						<th scope="col">결제금액</th>
						<th scope="col"> 보기</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty ocOrderProductDeliveryList}">
					<c:forEach var="ocOrderProduct" items="${ocOrderProductDeliveryList}" varStatus="status">
						<tr>
							<td class="prod-box">
							<c:choose>
									<c:when test="${ocOrderProduct.imageUrl eq null}"> 
										<img src="/static/images/common/no_image.png" height="100" width="100" alt="임시이미지">
									</c:when>
									<c:otherwise>
										<img src="${ocOrderProduct.imageUrl}?shrink=100:100" alt="${ocOrderProduct.prdtName}">
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">${ocOrderProduct.prdtNo}</td>
							<td class="text-center"><a href="javascript:setPorductDetail('${ocOrderProduct.prdtNo}');" class="link">${ocOrderProduct.prdtName}</a></td>
							<td class="text-center">${ocOrderProduct.optnName}</td>
							<td class="text-center"><fmt:formatNumber value="${ocOrderProduct.sellAmt}" pattern="#,##0" />원</td>
							<td class="text-center">
								<c:if test="${ocOrderProduct.deliveryAddressEmptyYn =='Y'}">
								<a href="javascript:setDeliveryAddressView(${status.count});"   class="link">수령지 보기</a>
								</c:if>
							</td> 
						</tr>
						<input type="hidden" id="dlvyIdText" name="dlvyIdText" value="${ocOrderProduct.dlvyIdText}">
						<input type="hidden" id="orderNo" name="orderNo" value="${ocOrderProduct.orderNo}">
						<input type="hidden" id="orderPrdtSeq" name="orderPrdtSeq" value="${ocOrderProduct.orderPrdtSeq}">
						<input type="hidden" id="orderDlvyHistSeq" name="orderDlvyHistSeq" value="${ocOrderProduct.orderDlvyHistSeq}">
						
						<input type="hidden" id="rcvrName${status.count}" name="rcvrName${status.count}" value="${ocOrderProduct.rcvrName}">
						<input type="hidden" id="rcvrTelNoText${status.count}" name="rcvrTelNoText${status.count}" value="${ocOrderProduct.rcvrTelNoText}">
						<input type="hidden" id="rcvrHdphnNoText${status.count}" name="rcvrHdphnNoText${status.count}" value="${ocOrderProduct.rcvrHdphnNoText}">
						<input type="hidden" id="rcvrPostCodeText${status.count}" name="rcvrPostCodeText${status.count}" value="${ocOrderProduct.rcvrPostCodeText}">
						<input type="hidden" id="rcvrPostAddrText${status.count}" name="rcvrPostAddrText${status.count}" value="${ocOrderProduct.rcvrPostAddrText}">
						<input type="hidden" id="rcvrDtlAddrText${status.count}" name="rcvrDtlAddrTextv${status.count}" value="${ocOrderProduct.rcvrDtlAddrText}">
						<input type="hidden" id="dlvyMemoText${status.count}" name="dlvyMemoText${status.count}" value="${ocOrderProduct.dlvyMemoText}">
						<input type="hidden" id="dlvyStatCodeNm${status.count}" name="dlvyStatCodeNm${status.count}" value="${ocOrderProduct.dlvyStatCodeNm}">
						<input type="hidden" id="logisVndrCodeNm${status.count}" name="logisVndrCodeNm${status.count}" value="${ocOrderProduct.logisVndrCodeNm}">
						<input type="hidden" id="waybilNoText${status.count}" name="waybilNoText${status.count}" value="${ocOrderProduct.waybilNoText}">
						</c:forEach>
				</c:if>
				    
<!-- 
					<tr>
						<td class="input text-center clear-float">
							<img src="../../static/images/temp/temp_prod_img.jpg" alt="임시이미지">
						</td>
						<td class="text-center">0048571</td>
						<td class="text-center"><a href="#" class="link">[누오보] MISSY 2 BEIGE</a></td>
						<td class="text-center">230</td>
						<td class="text-center">59,000원</td>
						< !-- S : 20190308 추가 // 보기 필드 추가 -- >
						<td class="text-center"><a href="#" class="link">보기</a></td>
						< !-- E : 20190308 추가 // 보기 필드 추가 -- >
					</tr>
 -->

				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">택배전환 수령지 정보</h3>
				</div>
				<div class="fr">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<c:if test="${deliveryLogisCnt < 1 }">
						<!-- 정보 조회가 아닌경우만 노출 -->
						<input  type="checkbox" id="chkDeliveryModule"  name="chkDeliveryModule">
						<label for="chkDeliveryModule">주문자 정보와 동일</label>
						</c:if>
					</span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<!-- DESC : 정보 조회 화면인 경우, input 전체 disabled 속성 추가 해주세요 -->
			<table class="tbl-row">
				<caption>택배전환 수령지 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<!-- S : 20190308 추가 // 택배전환일시 추가 --
					<tr>
						<th scope="row">택배전환일시</th>
						<td>2019.03.08 10:49:56</td>
					</tr>
					-- E : 20190308 추가 // 택배전환일시 추가 -->
					<tr>
						<th scope="row">
							<span class="th-required">수령자명</span>
						</th>
						<td class="input">
							<input type="text" name="rcvrName" id="rcvrName"   maxlength="50" class="ui-input" title="수령자명 입력" required="수령자명">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td class="input">
							<input type="text" name="rcvrHdphnNoText" id="rcvrHdphnNoText"   maxlength="15"  class="ui-input" title="휴대폰번호 입력" required="휴대폰번호">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">전화번호</span>
						</th>
						<td class="input">
							<input type="text" name="rcvrTelNoText" id="rcvrTelNoText"   maxlength="15"  class="ui-input" title="전화번호 입력" required="전화번호">
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
									<input type="text" name="rcvrPostCodeText" id="rcvrPostCodeText" class="ui-input" title="우편번호 입력" readonly required="주소 ">
									<c:if test="${deliveryLogisCnt < 1 }">
									<!-- 정보 조회가 아닌경우만 노출 -->									
									<button type="button" id="postSearchBtn" name="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
									</c:if>
								</span>
								<span class="address-wrap">
									<input type="text" id="rcvrPostAddrText" name="rcvrPostAddrText" maxlength="200"   class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly>
									<input type="text" id="rcvrDtlAddrText" name="rcvrDtlAddrText" maxlength="100" class="ui-input" placeholder="상세주소" required="상세주소 " title="상세주소 입력">
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
									<textarea id="dlvyMemoText" name="dlvyMemoText" class="ui-textarea" placeholder="배송 메시지는 40자 이내로 입력해 주세요." title="배송 메세지 입력"></textarea>
									<span class="text-limit">
										<span class="desc">(<span id="counter" class="state-num">0</span>/40자)</span>
									</span>
								</span>
							</span>
							<!-- E : msg-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			
			<c:if test="${deliveryLogisCnt > 0 }">
			<!-- S : 정보 조회 화면일 경우 노출되는 테이블 -->
			<!-- S : tbl-row --> 
			<table class="tbl-row">
				<caption>택배전환 수령지 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">택배전환 진행상태</th>
						<td><div id="dlvyStatCodeNm"></div></td>
					</tr>
					<!-- S : 택배전환 진행상태 > 접수일 경우, 운송장정보 tr 영역 삭제 해주세요 -->
					<tr>
						<th scope="row">운송장정보</th>
						<td><div id="logisVndrCodeNm"></div> / <div id="waybilNoText"></div></td>
					</tr>
					<!-- E : 택배전환 진행상태 > 접수일 경우, 운송장정보 tr 영역 삭제 해주세요 -->
				</tbody>
			</table>
			 <!-- E : tbl-row -->
			<!-- E : 정보 조회 화면일 경우 노출되는 테이블 -->
			</c:if>

			<!-- S : window-btn-group -->
			<c:if test="${deliveryLogisCnt < 1 }">
			<div class="window-btn-group" id="buttonDiv">
				<a href="javascript:void(0);"  id="popupCancel"  name="popupCancel" class="btn-normal btn-del">취소</a>
				<a href="javascript:void(0);"  id="pupupSave" name="pupupSave" class="btn-normal btn-link">확인</a>
			</div>
			</c:if>
			<c:if test="${deliveryLogisCnt > 0 }">
						<div class="window-btn-group" id="buttonDivClose">
							<a href="javascript:void(0);"  id="popupCancel"  name="popupCancel"class="btn-normal btn-del">닫기</a>
						</div>
			</c:if>
			<!-- E : window-btn-group -->
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.store.pick.delivery.change.popup.js<%=_DP_REV%>"></script>