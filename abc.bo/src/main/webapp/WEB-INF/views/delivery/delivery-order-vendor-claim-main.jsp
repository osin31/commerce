<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() {  
	 
		abc.biz.delivery.order.vendor.claim.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.vendor.claim.main.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.delivery.order.vendor.claim.main.CLM_GBN_CODE_CANCEL	= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.delivery.order.vendor.claim.main.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.delivery.order.vendor.claim.main.CLM_GBN_CODE_RETURN	= "${CommonCode.CLM_GBN_CODE_RETURN}";
		
		abc.biz.delivery.order.vendor.claim.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.vendor.claim.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.vendor.claim.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.vendor.claim.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		// 목록 그리드 초기화
		abc.biz.delivery.order.vendor.claim.main.initDataListSheet();

		//초기화 셋팅
		abc.biz.delivery.order.vendor.claim.main.init();

		//조회영역 셋팅
		abc.biz.delivery.order.vendor.claim.main .event();
		
		// 기본 검색 한달
		//$("a[name='perOneMonth']").trigger("click");
	});

	/**
	 *  업체 검색 결과 callback 이벤트
	 */
	function setSelectedVendors(data){
		$("#vndrName").val(data.arrayVndrName);
		$("#vndrNo").val(data.arrayVndrNo);
	}
 
	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response)  {
		
		//변경 대상 옵션 초기화
		var select = $("#newClmPrdtStatCode"); //변경 클레임상태
				select.find("option").remove();
					select.append(new Option("선택", ""));

		//그리드 수정 불가 초기화
		dataListSheet.SetColEditable("logisVndrCode",0);    // 택배사
		dataListSheet.SetColEditable("waybilNoText",0);   //송장번호
		dataListSheet.SetColEditable("dlvyProcDtm",0);   // 발송일자

		  if(StCode =="200"){
				var clmGbnCode = $("#clmGbnCode").val(); //검색조건 배송상태
 				//교환 선택시
				if(clmGbnCode =="E"){
					var clmPrdtStatEArr10006 = $("#clmPrdtStatEArr10006").prop("checked") ; //접수
					var clmPrdtStatEArr10009 = $("#clmPrdtStatEArr10009").prop("checked") ;  //수거지시 
					var clmPrdtStatEArr10010 = $("#clmPrdtStatEArr10010").prop("checked") ;  //수령완료 
					
					var clmPrdtStatEArr10011 = $("#clmPrdtStatEArr10011").prop("checked") ;  //심의완료 
					var clmPrdtStatEArr10014 = $("#clmPrdtStatEArr10014").prop("checked") ;  //교환배송지시 
				    var checkECnt = $('[name="clmPrdtStatEArr"]:checked').length;
					  
					 if (checkECnt == 1 &&  clmPrdtStatEArr10006){
					 //교환접수 -> 수거지시
						 	select.append(new Option("수거지시", "10009"));
					 }else if (checkECnt == 1 &&  clmPrdtStatEArr10009){
					 //수거지시 -> 수령완료  심의완료
						 	select.append(new Option("수령완료", "10010"));
					 }else if (checkECnt == 1 &&  clmPrdtStatEArr10010){
					 //수령완료 -> 심의완료
						 	select.append(new Option("심의완료", "10011"));
					 }else  if (checkECnt == 1 &&  clmPrdtStatEArr10011){
					 	 //심의완료 -> 교환배송지시
						 dataListSheet.SetColEditable("logisVndrCode",1);    // 택배사
						dataListSheet.SetColEditable("waybilNoText",1);   //송장번호
						dataListSheet.SetColEditable("dlvyProcDtm",1);   // 발송일자

						 	select.append(new Option("교환배송지시", "10014"));
					 } else if (checkECnt == 1 &&  clmPrdtStatEArr10014){
	 					  	 //교환배송지시 -> 교환배송중
						 	select.append(new Option("교환배송중", "10015"));
					 } 

				}else if(clmGbnCode =="R"){
				//반품 선택시
					var clmPrdtStatRArr10018 = $("#clmPrdtStatRArr10018").prop("checked") ;
					var clmPrdtStatRArr10021 = $("#clmPrdtStatRArr10021").prop("checked") ;
					var checkRCnt = $('[name="clmPrdtStatRArr"]:checked').length;

					 //반품접수 -> 수거지시
					 if (checkRCnt == 1 &&  clmPrdtStatRArr10018){
						 	select.append(new Option("수거지시", "10021"));
					 }else  if (checkRCnt == 1 &&  clmPrdtStatRArr10021){
		 					  //수거지시 -> 심의완료
						 	select.append(new Option("심의완료", "10023"));
					 }
				} 
		  } //end    if(StCode =="200"){ 
	} //end function  
	
//일괄 재배송 결과	
function dataListSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	  if(StCode =="200"){
		  alert("변경 처리되었습니다.");
		  abc.biz.delivery.order.vendor.claim.main.doActionJs("search");
	  }else{
		  alert("변경  처리가 실패되었습니다. \n 다시 시도해주세요.");
	  }
} 

/*
  엑셀 다운로드 후 호출
*/
function dataListSheet_OnDownFinish(downloadType, result) {
		alert(downloadType + ' 다운이 완료되었습니다. ' );
	}

 

</script> 
		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm"  method="post" onsubmit="return false;">
			 <input type="hidden" id="excelType" name="excelType" value="ALL">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">업체 클레임 배송관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>업체 클레임 배송관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">업체 클레임 배송관리</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="guide-text">최근 3개월 이내 클레임 기준</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" id="icoRefresh"   class="btn-sm btn-func"><i class="ico ico-refresh"></i>새로고침</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>업체 클레임 배송관리</caption>
					<colgroup>
						<col>
						<col>
						<col>
						<col>
						<!-- <col> -->
					</colgroup>
					<thead>
						<tr>
							<th scope="col">클레임 접수 (미확인 클레임)</th>
							<th scope="col">회수중</th>
							<th scope="col">교환품 배송중</th>
							<th scope="col">클레임 완료</th>
							<!-- <th scope="col">클레임 불가 요청</th> -->
						</tr>
					</thead>
					<tbody>
							<tr>
								<td class="text-center"><span id="orderPrdtStatCodeClaim">0</span>건</td>
								<td class="text-center"><span id="orderPrdtStatCodeDeliveryReturn">0</span>건</td>
								<td class="text-center"><span id="orderPrdtStatCodeExchangeDelivery">0</span>건</td>
								<td class="text-center"><span id="orderPrdtStatCodeClaimComplete">0</span>건</td>
								<!-- <td class="text-center"><span id="orderPrdtStatCodeClaimCancel">0</span>건</td> -->
							</tr>
					</tbody>
				</table>
				<!-- E : tbl-col -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>업체 클레임 배송관리 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">업체구분</th>
									<td>
									<span class="ip-search-box">
											<c:if test="${isAdmin}">
												<!-- S : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->
												<!-- DESC : td영역 input 클래스 추가 해주세요 -->
												<!-- S : ip-text-box -->											
												<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" readonly>
												<input type="text" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly>
												<a href="javascript:void(0);" class="btn-search" id="searchVendor"><i class="ico-search"><span class="offscreen" >검색</span></i></a>											
												<!-- E : ip-text-box -->
												<!-- E : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->			
												<input type="hidden" name="vndrGbnType" id="vndrGbnType" value="N">
										</c:if>
										<c:if test="${!isAdmin}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" readonly value="${vndrName}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly value="${vndrNo}">
												<input type="hidden" name="vndrGbnType"  id="vndrGbnType" value="Y">
										</c:if>
										</span>
										<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
										<!-- S : 자사 A-RT BO담당자인 경우, 노출되는 영역 -->
										<!-- DESC : td영역 input 클래스 추가 해주세요 -->
										<!-- S : ip-text-box -->
										<!--
										<span class="ip-text-box">
											<select class="ui-sel" title="입점사 선택">
												<option>전체</option>
											</select>
											<span class="ip-search-box">
												<input type="text" class="ui-input" title="검색어 입력" readonly>
												<a href="#" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
											</span>
										</span>
										 -->
										<!-- E : ip-text-box -->
										<!-- E : 자사 A-RT BO담당자인 경우, 노출되는 영역 -->
										<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
									</td>
									<td></td>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : 20190215 수정 // radio 버튼으로 수정 -->
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											  <li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNoArr" id="siteNoArr0" value="" checked>
													<label for="siteNoArr0">전체</label>
												</span>
											</li>
											  <c:forEach var="siteParam" items="${siteList}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNoArr" id="siteNoArr${status.count}"  clickYn="Y"   value="${siteParam.siteNo}">
													<label for="siteNoArr${status.count}"><c:out value="${siteParam.siteName}"/></label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
										<!-- E : 20190215 수정 // radio 버튼으로 수정 -->
									</td>
								</tr>
								<tr>
									<th scope="row">주문</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select name="searchOrderKey"   id="searchOrderKey"  class="ui-sel" title="검색어 타입 선택">
												<option value="buyerName">주문자</option>
												<option value="rcvrName">수령자</option>
												<option value="loginId">주문자ID</option>
												<option value="buyerHdphnNoText">주문자휴대폰</option>
											</select>
											<input type="text" id="searchOrderValue" class="ui-input" title="검색어 입력"> 
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">주문번호</th>
									<td class="input">
										<input type="text" class="ui-input" name="orderNo" id="orderNo"  title="주문번호 입력">
									</td>
								</tr>
								<tr>
									<!-- DESC : 20190215 수정 // th 타이틀 수정 -->
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" name="searchDateKey" id="searchDateKey" title="조회기간 선택">
												<option  value="orderDtm">주문일</option>
												<option  value="cRgstDtm">접수일</option>
											</select> 
											<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">클레임 진행상태</th>
									<td class="input" colspan="4">
										<!-- DESC : 클레임 유형에 따라 checkbox 항목 변경 -->
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<select class="ui-sel" title="클레임 유형 선택" id="clmGbnCode" name="clmGbnCode">
													<option  value="ALL">전체</option>
													<option value="E">교환</option>
													<option value="R">반품</option>
													<option value="C">취소</option>
												</select>
											</li>

											<span id="claimGbnCodeE" style="display:none">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="clmPrdtStatEArr" id="clmPrdtStatEArr" checked value="ALL">
														<label for="clmPrdtStatEArr">전체</label>
													</span>
												</li>	
												<!-- 교환 -->
												<c:forEach items="${codeList.CLM_PRDT_STAT_CODE}" var="code"  varStatus="status">
													<c:if test="${code.addInfo1 eq CommonCode.CLM_GBN_CODE_EXCHANGE}">
														<li>
															<span class="ui-chk">
																<input id="clmPrdtStatEArr${code.codeDtlNo}" name="clmPrdtStatEArr" type="checkbox"  clickYn="clmPrdtStatEArr"  checked value="${code.codeDtlNo}">
																<label for="clmPrdtStatEArr${code.codeDtlNo}">${code.codeDtlName}</label>
															</span>
														</li>
														</c:if>
													</c:forEach>
											</span>
												<!-- 취소 -->
											<span id="claimGbnCodeC" style="display:none"> 
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="clmPrdtStatCArr" id="clmPrdtStatCArr" checked value="ALL">
														<label for="clmPrdtStatCArr">전체</label>
													</span>
												</li>	

												<c:forEach items="${codeList.CLM_PRDT_STAT_CODE}" var="code"  varStatus="status">
													<c:if test="${code.addInfo1 eq CommonCode.CLM_GBN_CODE_CANCEL}">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="clmPrdtStatCArr${code.codeDtlNo}" name="clmPrdtStatCArr" type="checkbox"  clickYn="clmPrdtStatCArr"  checked value="${code.codeDtlNo}">
																<label for="clmPrdtStatCArr${code.codeDtlNo}">${code.codeDtlName}</label>
															</span>
														</li>
														</c:if>
													</c:forEach>
											</span>
												<!-- 반품 -->
											<span id="claimGbnCodeR" style="display:none"> 
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="clmPrdtStatRArr" id="clmPrdtStatRArr" checked value="ALL">
														<label for="clmPrdtStatRArr">전체</label>
													</span>
												</li>	

												<c:forEach items="${codeList.CLM_PRDT_STAT_CODE}" var="code"  varStatus="status">
													<c:if test="${code.addInfo1 eq CommonCode.CLM_GBN_CODE_RETURN}">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="clmPrdtStatRArr${code.codeDtlNo}" name="clmPrdtStatRArr" type="checkbox"  clickYn="clmPrdtStatRArr"  checked value="${code.codeDtlNo}">
																<label for="clmPrdtStatRArr${code.codeDtlNo}">${code.codeDtlName}</label>
															</span>
														</li>
														</c:if>
													</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="gFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);"  id="gFormSearch"  class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ol class="tbl-desc-list">
						<li>1. 택배사 코드는 다운로드하여 참고해주시기 바랍니다. </li>
						<li>2. 클레임 상태에 따라 배송상태를 일괄 적용하실 수 있습니다.</li>
						<!-- <li>2. 엑셀 업로드 발송처리 시 처리하고자 하는 주문건에 대한 "선택 다운로드" 후 택배사코드, 운송장번호를 입력하여 업로드하시기 바랍니다. </li>
						<li>3. 엑셀 업로드 발송처리의 경우 필수항목 (택배사코드, 운송장번호) 미입력시 등록오류 처리되어 배송중으로 변경되지 않습니다. (정상등록건만 발송처리됩니다.)</li> -->
					</ol>
					<div class="fr">
						<a href="javascript:void(0)" id="logisVndrCodeExcelDown" class="btn-sm btn-func">택배사 코드 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selViewModule01">목록개수</label>
							<select id="pageCount" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="opt-group">
							<label class="title" for="newClmPrdtStatCode">선택한 상품</label>
							<select class="ui-sel" name="newClmPrdtStatCode"  id="newClmPrdtStatCode">
								<option>선택</option>
							</select>
						</span>
						<span class="btn-group">
							<a href="javasccript:void(0)"  id="clmPrdtStatCodeUpdate" class="btn-sm btn-func">일괄적용</a>
							<span class="gap"></span>
							<!-- DESC : html/delivery/BO-DL-01007.html 파일 확인 해주세요 -->
							<!-- <a href="javascript:void(0)" id="orderExcelUpload" class="btn-sm btn-func">엑셀 일괄 업로드</a> -->
								<!-- <a href="javascript:void(0)" id="orderClaimCancelPopup"  name="orderClaimCancelPopup"  class="btn-sm btn-func">클레임 불가 요청</a> -->
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="claimGbnCodeSave" class="btn-sm btn-save">저장</a>
						<a href="javascript:void(0)" id="excelDownSelect" class="btn-sm btn-func">엑셀 선택 다운로드</a>
						<a href="javascript:void(0)" id="excelDownAll" class="btn-sm btn-func">엑셀 전체 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
			</form>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.claim.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>

