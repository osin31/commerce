<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function() {
	
		abc.biz.afterservice.afterservicemain.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.afterservice.afterservicemain.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
	
		abc.biz.afterservice.afterservicemain.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.afterservice.afterservicemain.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.afterservice.afterservicemain.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.afterservice.afterservicemain.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.afterservice.afterservicemain.fromDash = "${fromDash}";
		
		<%-- AS 관리  초기 세팅. --%>
		abc.biz.afterservice.afterservicemain.initafterserviceSheet();
		
		<%-- A/S 관리 목록 초기 호출 --%>
		//abc.biz.afterservice.afterservicemain.afterserviceDoAction('search');
		
		<%-- 상품 selectBox 선택 후, 입력 input --%>
		abc.biz.afterservice.afterservicemain.prdtInfoSelect();
		<%--  결제 구분 선택시 --%>
		abc.biz.afterservice.afterservicemain.deviceCodeCheck();
		
		<%-- AS번호/주문번호 선택 후, 입력 input --%>
		abc.biz.afterservice.afterservicemain.asOrderNoSelect();
		
		<%-- 주문 selectBox 선택 후, 입력 input --%>
		abc.biz.afterservice.afterservicemain.orderMemberInfoSelect();
		
		<%-- 상품 selectBox 선택 후, 입력 input --%>
		abc.biz.afterservice.afterservicemain.prdtInfoSelect();
		
		<%-- 진행상태  checkBox 선택 시 --%>
		abc.biz.afterservice.afterservicemain.asStatCodeCheck();
		
		<%-- 미처리 여부 라디오 버튼 선택시 --%>
		abc.biz.afterservice.afterservicemain.unProcYnSelect();
	
		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#prdtInfoSelect").trigger("change");
		
		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#asOrderNoSelect").trigger("change");
		
		<%-- 기본 한달  --%>
		$("a[name^=per1Month]").trigger("click");
		
		<%-- dash 보드로 부터 접속 시  --%>
		abc.biz.afterservice.afterservicemain.fromDashboard();
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
	});

	<%-- 그리드 Click 이벤트  및 재접수 버튼 이벤트 --%>
	function asSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.afterservice.afterservicemain.asSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- AS완료 , AS불가 , 접수취소 일때만 변경 --%>
	function asSheet_OnRowSearchEnd(row, col, value){
		var colModify = asSheet.GetRowData(row).colModify;
		//10001 접수취소, 10011 AS완료 ,10012 AS불가 
		if(colModify == abc.biz.afterservice.constCode.asStatCodeAcceptCancel || colModify == abc.biz.afterservice.constCode.asStatCodeAsFinish || colModify == abc.biz.afterservice.constCode.asStatCodeAsReject){
			asSheet.InitCellProperty(row,3, {'Type': 'Button'});
			asSheet.SetCellValue(row, "asStatCodeName", "재접수");
			asSheet.SetCellEditable(row,"asStatCodeName",1);
		}else{
			asSheet.SetMergeCell(row, 2, 1 , 2 );
		}
	}
	
	<%-- 그리드 체크박스 전체선택 체크 후 이벤트 --%>
	function asSheet_OnCheckAllEnd(Col, Value){
		
		if(Value == 0){
			$("#frmDownload").empty();
			
		}else if(Value == 1){
			var indexArray = asSheet.FindCheckedRow(1).split('|');
			
			for(var i=0; i<indexArray.length; i++){
				var hiddenInput = "<input type='hidden' name='asAcceptNo' value='" + indexArray[i] + "'>";
				$("#frmDownload").append(hiddenInput);
			}
		}
	}

</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">A/S관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>클레임 관리</li>
								<li>A/S관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">A/S 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>A/S 검색</caption>
								<colgroup>
									<col style="width: 110px;">
									<col>
									<col style="width: 79px">
									<col style="width: 110px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">사이트</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="rdoSiteAll" value="" checked>
														<label for="rdoSiteAll">전체</label>
													</span>
												</li>
												<c:forEach items="${siteList}" var="sySite" varStatus="status">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="siteNo" id="rdoSite${sySite.siteNo}" value="${sySite.siteNo}">
															<label for="rdoSite${sySite.siteNo}">${sySite.siteName}</label>
														</span>
													</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">A/S 구분</th>
										<td class="input">
											<!-- S : ip-text-box -->
											<span class="ip-text-box">
												<select class="ui-sel" title="A/S 구분 선택" id="asGbnCode" name="asGbnCode">
													<option value="">전체</option>
													<c:if test="${!empty asGbnCode}">
														<c:forEach items="${asGbnCode}" var="asGbnCode" >
															<option value="${asGbnCode.codeDtlNo}">${asGbnCode.codeDtlName}</option>
														</c:forEach>
													</c:if>
												</select>
												<select class="ui-sel" title="A/S 유형 선택" id="asRsnCode" name="asRsnCode" style="display:none;">
													<option value="">전체</option>
													<c:if test="${!empty asRsnCode}">
														<c:forEach items="${asRsnCode}" var="asRsnCode">
															<option value="${asRsnCode.codeDtlNo}">${asRsnCode.codeDtlName}</option>
														</c:forEach>
													</c:if>
												</select>
											</span>
											<!-- E : ip-text-box -->
										</td>
									</tr>
									<tr>
										<th scope="row">결제구분</th>
										<td class="input" colspan="4">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="chkDeviceCodeModuleAll" id="chkDeviceCodeModuleAll" value="" checked>
														<label for="chkDeviceCodeModuleAll">전체</label>
													</span>
												</li>
												<c:if test="${!empty deviceCode}">
													<c:forEach items="${deviceCode}" var="deviceCode">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																	<input id="chkDeviceCode${deviceCode.codeDtlNo}" value="${deviceCode.codeDtlNo}" custom="deviceCode" name="deviceCodeList.deviceCode" type="checkbox" checked="checked">
														<label for="chkDeviceCode${deviceCode.codeDtlNo}">${deviceCode.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
												</c:if>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">A/S번호·주문번호</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<select class="ui-sel" id="asOrderNoSelect"title="유형 선택">
													<option value="asAcceptNo">A/S번호</option>
													<option value="orderNo">주문번호</option>
												</select>
												<input type="text" class="ui-input" name="asAcceptNo" id="asOrderNoInp" value="" title="검색어 입력" maxlength="13">
											</div>
											<!-- E : opt-keyword-box -->
										</td>
										<td></td>
										<th scope="row">주문</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<select class="ui-sel" id="orderMemberInfoSelect" title="검색어 타입 선택">
													<option value="buyerName">주문자명</option>
													<option value="rcvrName">수령자</option>
													<option value="buyerHdphnNoText">휴대폰 번호</option>
													<option value="loginId">주문자ID</option>
												</select>
												<!-- DESC : 휴대폰 번호 선택시 placeholder="-없이 전체 입력" 속성 추가 해주세요 -->
												<input type="text" class="ui-input" name="buyerName" id="orderMemberInfoInp" title="검색어 입력">
												<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4" />
											</div>
											<!-- E : opt-keyword-box -->
										</td>
									</tr>
									<tr>
										<th scope="row">상품</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<select class="ui-sel" id="prdtInfoSelect" title="택배사 선택">
													<option value="prdtNo" selected="selected">상품코드</option>
													<option value="prdtName" >상품명</option>
													<option value="styleInfo">스타일코드</option>
												</select>
												<input type="text" class="ui-input" name="prdtName" id="prdtInfoInp" title="검색어 입력">
											</div>
											<!-- E : opt-keyword-box -->
										</td>
										<td></td>
										<th scope="row">브랜드</th>
										<td class="input">
											<span class="ip-search-box">
												<!-- <input type="hidden" id="brandNo" name="brandNo" value=""> -->
												<input type="text" class="ui-input" id="brandName" name="brandName" value="" title="검색어 입력">
												<a href="javascript:void(0);" class="btn-search" id="btnBrand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
											</span>
										</td>
									</tr>
									<tr>
										<th scope="row">접수일</th>
										<td class="input" colspan="4">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" >
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" >
												</span>
												<span class="btn-group">
												<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
													<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
													<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="javascript:void(0);"   name="per1Year"  class="btn-sm btn-func">1년</a>
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
									<tr>
										<th scope="row">진행상태</th>
										<td class="input" colspan="4">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkAsStatModuleAll" name="chkAsStatModuleAll" type="checkbox" value="" checked="checked">
														<label for="chkAsStatModuleAll">전체</label>
													</span>
												</li>
												<c:if test="${!empty asStatCode}">
													<c:forEach items="${asStatCode}" var="asStatCode">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkAsStat${asStatCode.codeDtlNo}" value="${asStatCode.codeDtlNo}" custom="asStatCode" name="asStatCodeList.asStatCode" type="checkbox" checked="checked">
																<label for="chkAsStat${asStatCode.codeDtlNo}">${asStatCode.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
												</c:if>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">미처리여부</th>
										<td class="input" colspan="4">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="unProcYn" id="rdoUnProcYnAll" value="" checked>
														<label for="rdoUnProcYnAll">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="unProcYn" id="rdoUnProcYnY" value="Y">
														<label for="rdoUnProcYnY">처리</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="unProcYn" id="rdoUnProcYnN" value="N">
														<label for="rdoUnProcYnN">미처리</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
								</tbody>
							</table>
							<div class="confirm-box">
								<div class="fl">
									<a href="javascript:void(0)" class="btn-sm btn-func" id="btnReset"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<a href="javascript:void(0)" class="btn-normal btn-func" id="btnSearch">검색</a>
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					<!-- E : search-wrap -->
					<input type="hidden" name="sortColumn" id="sortColumn" value="">
					<input type="hidden" name="sortType" id="sortType" value="">
				</form>
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">A/S 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-sm btn-func" id="downloadExcel">엑셀 선택 다운로드</a>
						<a href="javascript:void(0)" class="btn-sm btn-func" id="downloadAllExcel">엑셀 전체 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="asGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
				
				<!-- 다운로드할 클레임 목록의 Form -->
				<form id="frmDownload" name="frmDownload" method="post" onsubmit="return false;">
				</form>
			</div>
		</div>
		<!-- E : container -->

<script src="/static/common/js/biz/afterservice/abcmart.afterservice.afterservicemain.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.constCode.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>