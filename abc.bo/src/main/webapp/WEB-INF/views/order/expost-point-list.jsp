<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function() {
		abc.biz.order.expost.list.init();

 		listSheet.InitDataCombo(0 , "siteNo" , "오프라인|"+${siteCombo}.text , "|"+${siteCombo}.code  );
	});

	function listSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row  != 0) {
			if(listSheet.ColSaveName(Col) == "saveMemberName" && Value != "" ){
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
			} else if  (listSheet.ColSaveName(Col) == "manager" && Value != "" ){
				if(listSheet.GetRowData(Row).adminRgstYn != null && listSheet.GetRowData(Row).adminRgstYn == 'Y'){
					window.abc.adminDetailPopup(listSheet.GetCellValue(Row, "adminNo"));
				}
				if(listSheet.GetRowData(Row).adminRgstYn != null && listSheet.GetRowData(Row).adminRgstYn == 'N'){
					var url = "/member/member-detail-pop";
					var params = {}
					params.memberNo = listSheet.GetCellValue(Row, "rgsterMemberNo");

					abc.open.popup({
						winname :	"detailPop",
						url 	:	url,
						width 	:	1480,
						height	:	985,
						params	:	params
					});
				}

			}
		}
	}

	function listSheet_OnRowSearchEnd(row){

		/* if(listSheet.GetRowData(row).onlnBuyYn != null && listSheet.GetRowData(row).onlnBuyYn == 'N') {
			if(listSheet.GetRowData(row).buyNoText != null && listSheet.GetRowData(row).buyNoText != "")
				listSheet.SetCellValue(row, "orderNo", listSheet.GetRowData(row).buyNoText);
		}	 */
		if(listSheet.GetRowData(row).adminRgstYn != null && listSheet.GetRowData(row).adminRgstYn == 'Y'){
			if(listSheet.GetRowData(row).adminName != null && listSheet.GetRowData(row).adminName != "") {
				listSheet.SetCellValue(row, "manager", listSheet.GetRowData(row).adminName);
			}
		}
		if(listSheet.GetRowData(row).adminRgstYn != null && listSheet.GetRowData(row).adminRgstYn == 'N') {
			if(listSheet.GetRowData(row).rgsterName != null && listSheet.GetRowData(row).rgsterName != "") {
				listSheet.SetCellValue(row, "manager", listSheet.GetRowData(row).rgsterName);
			}
		}
	}
</script>
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">포인트 사후적립 주문관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>포인트 사후적립 주문관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">포인트 사후적립 주문 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="listForm" name="listForm" method="post" onsubmit="return false;">
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>포인트 사후적립 주문 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">적립구분</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="onlnBuyYn" id="onlnBuyYnAll" value="" checked>
												<label for="onlnBuyYnAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="onlnBuyYn" id="onlnBuyYnTrue" value="Y">
												<label for="onlnBuyYnTrue">온라인</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for onlnBuyYn 맞춰주세요 -->
												<input type="radio" name="onlnBuyYn" id="onlnBuyYnFalse" value="N">
												<label for="onlnBuyYnFalse">오프라인(매장)</label>
											</span>
										</li>
									</ul>
								</td>
								<td></td>
								<th scope="row">적립채널</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminRgstYn" id="adminRgstYnAll" value="" checked>
												<label for="adminRgstYnAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminRgstYn" id="adminRgstYnFO" value="N">
												<label for="adminRgstYnFO">FO(Front)</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminRgstYn" id="adminRgstBO" value="Y">
												<label for="adminRgstBO">BO (Backoffice)</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">주문/적립</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="orderSearchKey" name="searchKey" title="주문/적립 유형 선택">
											<option value="buyerName" selected>주문자명</option>
											<option value="buyerLoginId">주문자ID</option>
											<option value="saveId">적립ID</option>
										</select>
										<input type="text" id="orderSearchText" name="searchText" class="ui-input" title="주문/적립 검색어 입력" placeholder="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
								<td></td>
								<th scope="row">주문(거래)번호</th>
								<td class="input">
									<input type="text" id="orderNo" name="orderNo" class="ui-input" title="주문번호 입력" maxlength="13">
								</td>
							</tr>
							<tr>
								<th scope="row">적립기간</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
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
				<h3 class="content-title">포인트 사후적립 주문 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<option value="15">15개 보기</option>
						<option value="30">30개 보기</option>
						<option value="50">50개 보기</option>
						<option value="100">100개 보기</option>
					</select>
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
<script src="/static/common/js/biz/order/abcmart.order.expost.point.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>