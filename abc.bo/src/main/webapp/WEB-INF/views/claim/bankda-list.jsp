<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		abc.biz.claim.bankda.init();
 	});
	
	function listSheet_OnRowSearchEnd(row){
		var acctHistory = listSheet.GetRowData(row).bkjukyo + " ( " + listSheet.GetRowData(row).bkcontent + " " + listSheet.GetRowData(row).bketc + ")";
		listSheet.SetCellValue(row, "acctHistory", acctHistory);
		if(listSheet.GetRowData(row).depositinfo == null || listSheet.GetRowData(row).depositinfo == '')
			listSheet.SetCellValue(row, "depositinfo", "<button type='button' class='btn-sm btn-link' onclick='abc.biz.claim.bankda.popupDeposit("+row+");'>등록</button>");
		else
			listSheet.SetCellValue(row, "depositinfo", "<a href='#' class='link' onclick='abc.biz.claim.bankda.popupDeposit("+row+");'>"+listSheet.GetRowData(row).depositinfo+"</a>");
		
	}
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">자동 입금 확인 서비스 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>자동 입금 확인 서비스 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">자동 입금 확인 서비스 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
					<table class="tbl-search">
						<caption>클레임 검색</caption>
						<colgroup>
							<col style="width: 110px;">
							<col>
							<col style="width: 79px">
							<col style="width: 110px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">은행</th>
								<td class="input">
									<select class="ui-sel" id="acctno" name="acctno" title="은행 선택">
										<option value="13706230701011"	disabled>기업은행</option>
										<option value="26880104120581"	selected>국민은행</option>
										<option value="38500104017427"	>국민은행(ART:385001-04-017427)</option>
										<option value="37530104004127"	>국민은행(OTS:375301-04-004127)</option>
										<option value="1005901419208"	disabled>우리은행</option>
										<option value="39891001170504"	disabled>하나은행</option>
										<option value="01450601004780"	disabled>우체국</option>
									</select>
								</td>
								<td></td>
								<th scope="row">거래내역</th>
								<td class="input">
									<input type="text" class="ui-input" id="bkjukyo" name="bkjukyo" title="거래내역 입력">
								</td>
							</tr>
							<tr>
								<th scope="row">입금확인</th>
								<td class="input" colspan="4">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="depositconfirm" id="depositconfirmAll" value="all" checked>
												<label for="depositconfirmAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="depositconfirm" id="depositconfirmOK" value="Y">
												<label for="depositconfirmOK">확인완료</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="depositconfirm" id="depositconfirmWait" value="N" >
												<label for="depositconfirmWait">대기</label>
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
				</form>
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
		<!-- E : search-wrap -->
		<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>입출금 정보</caption>
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
					<th scope="row">오늘 입금액</th>
					<td id="todayInput">0원</td>
					<th scope="row">오늘 출금액</th>
					<td id="todayOutput">0원</td>
					<th scope="row">계좌잔액</th>
					<td id="jango">0원</td>
				</tr>
			</tbody>
		</table>
		<!-- E : tbl-row -->
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">자동 입금 확인 서비스 목록</h3>
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
				<span class="opt-group">
					<label class="title" for="processSel">선택 항목</label>
					<select class="ui-sel" id="processSel">
						<option value="">선택</option>
						<option value="confirm">확인완료</option>
						<option value="cancel">취소</option>
					</select>
				</span>
				<span class="btn-group">
					<button type="button" class="btn-sm btn-func" id="processBtn" >일괄적용</button>
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
<!-- E : container -->

<script src="/static/common/js/biz/claim/abcmart.claim.bankda.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>