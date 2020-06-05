<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function() {
		abc.biz.order.accounttransfer.faillist.init();

		listSheet.InitDataCombo(0 , "siteNo" , ${siteCombo}.text , ${siteCombo}.code  );

	});

	function listSheet_OnRowSearchEnd(row){
		if(listSheet.GetRowData(row).procYn == 'Y')
			listSheet.SetCellValue(row, "refundAccount", "보기");
		else if(listSheet.GetRowData(row).pymntOrganNoText != null && listSheet.GetRowData(row).pymntOrganNoText != "")
			listSheet.SetCellValue(row, "refundAccount", "계좌보기");
		else
			listSheet.SetCellValue(row, "refundAccount", "계좌등록");
	}

	function listSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row  != 0) {
			if(listSheet.ColSaveName(Col) == "orderNo" && Value != "" ){
				window.abc.orderDetailPopup(listSheet, "orderNo", Row, Col);
			} else if  (listSheet.ColSaveName(Col) == "buyerName" && Value != "" ){
				var url = "/member/member-detail-pop";
				var params = {}
				params.memberNo = listSheet.GetCellValue(Row, "memberNo");

				abc.open.popup({
					winname :	"detailPop",
					url 	:	url,
					width 	:	1480,
					height	:	985,
					params	:	params
				});
			} else if (listSheet.ColSaveName(Col) == "moderName" && Value != ""){
				var saveName = "moderNo";
				if(listSheet.GetRowData(Row).moderNo != null && listSheet.GetRowData(Row).moderNo != '' && listSheet.GetRowData(Row).moderNo != 'SY00000000')
					window.abc.adminDetailPopup(listSheet.GetCellValue(Row, "moderNo"))
			}

		}
	}

	function listSheet_OnButtonClick(row, col) {
		var param = {}
		param.orderNo = listSheet.GetRowData(row).orderNo;
		param.orderPymntSeq = listSheet.GetRowData(row).orderPymntSeq;
		abc.biz.order.accounttransfer.faillist.popupRefundInfo(param);
	}

</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<form id="listForm" name="listForm" method="post" onsubmit="return false;">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">무통장 입금 오류 리스트</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>주문관리</li>
							<li>무통장 입금 오류 리스트</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">무통장 입금 오류 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>무통장 입금 오류 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사이트</th>
								<td class="input" colspan="4">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="siteNo" id="siteNoAll" value="all" checked>
												<label for="siteNoAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty siteInfo}">
											<c:forEach items="${siteInfo}" var="site">
												<li>
													<span class="ui-rdo">
														<input type="radio" name="siteNo" id="siteNo${site.siteNo}" value="${site.siteNo}">
														<label for="siteNo${site.siteNo}">${site.siteName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">주문자정보</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="orderSearchKey" name="searchKey" title="주문자정보 유형 선택">
											<option value="buyerName">주문자명</option>
											<option value="recieverName">수령자명</option>
											<option value="buyerId">주문자ID</option>
											<option value="hdphnNo">휴대폰번호</option>
										</select>
										<input type="text" id="orderSearchText" name="searchText" class="ui-input" title="주문자정보 검색어 입력" placeholder="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
								<td></td>
								<th scope="row">주문번호</th>
								<td class="input">
									<input type="text" id="orderNo" name="orderNo" class="ui-input" title="주문번호 입력" maxlength="13">
								</td>
							</tr>
							<tr>
								<th scope="row">환불처리여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="procYn" id="procYnAll" value='all' checked>
												<label for="procYnAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="procYn" id="procYnTrue" value="Y">
												<label for="procYnTrue">처리</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="procYn" id="procYnFalse" value="N">
												<label for="procYnFalse">미처리</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">환불계좌 정보</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="searchRefundAccount" id="searchRefundAccountAll" value='all' checked>
												<label for="searchRefundAccountAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="searchRefundAccount" id="searchRefundAccountTrue" value="Y">
												<label for="searchRefundAccountTrue">유</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="searchRefundAccount" id="searchRefundAccountFalse" value="N">
												<label for="searchRefundAccountFalse">무</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<!-- S : 20190318 수정 // 기간검색 selectbox 추가 -->
										<select class="ui-sel" id="searchDateType" name="searchDateType">
											<option value="orderDtm">주문일시</option>
											<option value="procDtm">처리일시</option>
										</select>
										<!-- E : 20190318 수정 // 기간검색 selectbox 추가 -->
										<span class="date-box">
											<input type="text" data-role="datepicker" id="fromDate" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="toDate" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group" id="termsBtn">
											<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);" name="perMonth" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);" name="perYear" class="btn-sm btn-func">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="searchReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="searchBtn" class="btn-normal btn-func">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
		</form>
		<!-- E : search-wrap -->
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">무통장입금 오류 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<select class="ui-sel" id="pageCount">
						<option value="15">15개 보기</option>
						<option value="30">30개 보기</option>
						<option value="50">50개 보기</option>
						<option value="100">100개 보기</option>
					</select>
					<label class="opt-desc" for="makeClaimSelect">선택항목</label>
					<button type="button" class="btn-sm btn-func" id="refundBtn" >환불처리 완료</button>
				</span>
			</div>
		</div>
		<!-- E : tbl-controller -->
		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="listGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>

</div>
<!-- E : container -->
<script src="/static/common/js/biz/order/abcmart.order.accounttransfer.faillist.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>