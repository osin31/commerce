<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
	$(function() {
		
		abc.biz.afterservice.afterserviceamount.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.afterservice.afterserviceamount.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.afterservice.afterserviceamount.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.afterservice.afterserviceamount.siteCombo = ${siteCombo}; //stie 코드 값
		<%-- AS 금액관리   초기 세팅. --%>
		abc.biz.afterservice.afterserviceamount.initafterserviceAmtSheet();
	
		<%-- 주문 selectBox 선택 후, 입력 input --%>
		abc.biz.afterservice.afterserviceamount.orderMemberInfoSelect();
		
		<%-- 주문번호 연락처(숫자만) --%>
		$("#orderNo").off().on("keydown keyup focusout", function() {
			abc.text.validateOnlyNumber(this);
		});
		
		$("#orderMemberInfoInp").off().on("keydown keyup focusout", function() {
			if( $(this).attr("name") == "buyerHdphnNoText"){
				abc.text.validateOnlyNumber(this);
			}
		});
		
		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#prdtInfoSelect").trigger("change");

		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#asOrderNoSelect").trigger("change");
		
		<%-- 기간 검색 기본 한달 --%>
		$("#per1Month").trigger("click");
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
	});

	<%-- 그리드 Click 이벤트  및 재접수 버튼 이벤트 --%>
	function asAmtSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.afterservice.afterserviceamount.asAmtSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	
	<%-- 그리드 체크박스 전체선택 체크 후 이벤트 --%>
	function asAmtSheet_OnCheckAllEnd(Col, Value){
		
		if(Value == 0){
			$("#frmDownload").empty();
			
		}else if(Value == 1){
			var indexArray = asAmtSheet.FindCheckedRow(1).split('|');
			
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
				<h2 class="page-title">A/S 금액 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>A/S 금액 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">A/S 금액 발생 내역 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>A/S 금액 발생 내역 검색</caption>
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
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);"   id="perToday"  class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0);"   id="perWeek"   class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   id="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0);"   id="per1Year"  class="btn-sm btn-func">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">주문</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" id="orderMemberInfoSelect" title="검색어 타입 선택">
										<option value="buyerName">주문자명</option>
										<option value="loginId">주문자ID</option>
										<option value="buyerHdphnNoText">휴대폰 번호</option>
									</select>
									<!-- DESC : 휴대폰 번호 선택시 placeholder="-없이 전체 입력" 속성 추가 해주세요 -->
									<input type="text" class="ui-input" name="buyerName" id="orderMemberInfoInp" title="검색어 입력">
									<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<td></td>
							<th scope="row">주문번호</th>
							<td class="input">
								<input type="text" id="orderNo" name="orderNo"  maxlength="13" class="ui-input" title="주문번호 입력" >
							</td>
						</tr>
						<tr>
							<th scope="row">수급상태</th>
							<td class="input" colspan="4">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="asStatCode" id="statCodeAll" value="" checked>
												<label for="statCodeAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="asStatCode" id="statCodeWait" value="10008" >
												<label for="statCodeWait">입금대기</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="asStatCode" id="statCodeComplete" value="10009" >
												<label for="statCodeComplete">입금완료</label>
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
				<h3 class="content-title">A/S 금액 발생 목록</h3>
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
				<a href="javascript:void(0)"    class="btn-sm btn-func" id="downloadExcel">엑셀 선택 다운로드</a>
				<a href="javascript:void(0)" 	class="btn-sm btn-func"	id="downloadAllExcel">엑셀 전체 다운로드</a>
			</div>
		</div>
		<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="asAmtGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
		
		<!-- 다운로드할 클레임 목록의 Form -->
		<form id="frmDownload" name="frmDownload" method="post" onsubmit="return false;">
		</form>
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/afterservice/abcmart.afterservice.afterserviceamount.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>