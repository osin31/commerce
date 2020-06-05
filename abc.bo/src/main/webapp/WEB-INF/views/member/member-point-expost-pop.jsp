<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 사후적립 그리드 세팅 --%>
		abc.biz.member.member.point.initPointExpostSheet();

		<%-- 화면초기 세팅 --%>
		abc.biz.member.member.point.initExpost();

		<%-- 화면초기 세팅 --%>
		$("input[name='memberTypeCode']").change(function(){
			var memberTypeCodeVal = $(this).val();
			abc.biz.member.member.point.changeMemberType(memberTypeCodeVal);
		});

		<%-- 결제금액 입력(숫자만)--%>
		$("#pymntAmt").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});


		<%-- 검색 --%>
		$("#searchBtn").click(function(){
			abc.biz.member.member.point.searchNonMemberPurchaseList();
		});

		<%-- 매장검색 --%>
		$("#searchStore").click(function(){
			abc.storeSearchPopup('abc.biz.member.member.point.setStoreCallBack', false);
		});

		$("#saveBtn").click(function(){
			abc.biz.member.member.point.offlinePointSave();
		});
	});

	<%-- 온라인, 비회원 구매목록 포인트 적립 버튼 노출 --%>
	function expostSheet_OnRowSearchEnd(row){
		var orderNo = expostSheet.GetCellValue(row, "orderNo");
		var savepointYn = expostSheet.GetCellValue(row, "savepointYn");
		var orderCancelYn = expostSheet.GetCellValue(row, "orderCancelYn");
		var savepointDtm = expostSheet.GetCellValue(row, "savepointDtm");
		
		var buttonHtml;
		
		if(savepointYn == abc.consts.BOOLEAN_FALSE && orderCancelYn == abc.consts.BOOLEAN_FALSE && abc.text.allNull(savepointDtm)){
			buttonHtml = '<button class="GMCellButtonBase GMCellButton" type="button" onclick="abc.biz.member.member.point.pointSave('+row+')">포인트적립</button>';
		}else if(orderCancelYn == abc.consts.BOOLEAN_TRUE){
			buttonHtml = "-";
		}else{
			buttonHtml = savepointDtm;
		}

		expostSheet.SetCellValue(row, "savepointInfo", buttonHtml);
	}

</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>포인트 사후적립</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">포인트 사후적립</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>! 사후적립 안내 : 온라인 구매는 배송완료일로부터 30일 이내, 매장 구매는 결제일로부터 30일 이내에 적립 가능합니다.</li>
				</ol>
			</div>

			<!-- S : tab-wrap -->
			<div class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item"><a href="#tabContent1" class="tab-link">온라인</a></li>
					<li class="tab-item"><a href="#tabContent2" class="tab-link">오프라인</a></li>
				</ul>
				<!-- S:tab_content -->
				<div id="tabContent1" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">구매내역 조회</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
					<input type="hidden" id="memberNo" name="memberNo" value="${member.memberNo}">
					<table class="tbl-row">
						<caption>구매내역 조회</caption>
						<colgroup>
							<!-- DESC : 온라인회원 구매 선택시, 73,74 line
								<col style="width: 130px;">
								<col> 제거 해주세요
							-->
							<col style="width: 130px;">
							<col>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">적립/사용 내용</th>
								<!-- DESC : 온라인회원 구매 선택시, colspan="3" 속성 제거 해주세요 -->
								<td class="input" colspan="3">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="memberTypeCode" id="radioUse01" value="10000" checked>
												<label for="radioUse01">온라인회원 구매</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="memberTypeCode" id="radioUse02" value="10002">
												<label for="radioUse02">비회원 구매</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- S : 비회원 구매 선택시 노출되는 영역 -->
							<tr>
								<th scope="row">주문번호</th>
								<td class="input">
									<input type="text" id="orderNo" name="orderNo" class="ui-input" title="주문번호 입력" maxlength="13">
								</td>
								<th scope="row">인증번호</th>
								<td class="input">
									<input type="text" id="crtfcNoText" name="crtfcNoText" class="ui-input" title="인증번호 입력" maxlength="10">
								</td>
							</tr>
							<tr>
								<th scope="row">구매일자</th>
								<td class="input" colspan="3">
									<span class="date-box">
										<input type="text" id="orderDtm" name="orderDtm" data-role="datepicker" class="ui-cal js-ui-cal" title="구매일자 선택">
									</span>
								</td>
							</tr>
							<!-- E : 비회원 구매 선택시 노출되는 영역 -->
						</tbody>
					</table>
					</form>
					<!-- E : tbl-row -->

					<!-- S : 비회원 구매 선택시 노출되는 영역 -->
					<!-- S : tbl-desc-wrap -->
					<div class="tbl-desc-wrap text-center">
						<a href="#" id="searchBtn" class="btn-normal btn-func">내역조회</a>
					</div>
					<!-- E : tbl-desc-wrap -->

					<!-- S : 온라인회원 구매 선택시 노출되는 영역 -->
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">구매내역</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="selView05">목록개수</label>
								<select class="ui-sel" id="pageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="expostGrid" style="width:100%; height:429px;">

						</div>
					</div>
					<!-- E : ibsheet-wrap -->
					<!-- E : 온라인회원 구매 선택시 노출되는 영역 -->
				</div>
				<!-- E:tab_content -->
				<!-- S:tab_content -->
				<div id="tabContent2" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">오프라인 구매 적립</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<form id="frmSave" name="frmSave" method="post" onsubmit="return false;">
						<caption>오프라인 구매 적립</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									<span class="th-required">주문(거래)번호</span>
								</th>
								<td class="input">
									<input type="text" id="buyNoText" name="buyNoText" class="ui-input" title="주문(거래)번호 입력" maxlength="8">
								</td>
								<th scope="row">
									<span class="th-required">인증번호</span>
								</th>
								<td class="input">
									<input type="text" id="offCrtfcNoText" name="offCrtfcNoText" class="ui-input" title="인증번호 입력" maxlength="10">
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">구매일자</span>
								</th>
								<td class="input">
									<span class="date-box">
										<input type="text" id="buyYmd" name="buyYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="구매일자 선택">
									</span>
								</td>
								<th scope="row">
									<span class="th-required">결제금액</span>
								</th>
								<td class="input">
									<input type="text" id="pymntAmt" name="pymntAmt" class="ui-input" title="결제금액 입력">
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">매장명</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
									<span class="ip-search-box">
										<input type="text" id="storeNm" name="storeNm" class="ui-input" title="검색어 입력" readonly>
										<input type="hidden" id="storeNo" name="storeNo">
										<a href="javascript:void(0)" id="searchStore" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									</span>
									<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
								</td>
							</tr>
						</tbody>
						</form>
					</table>
					<!-- E : tbl-row -->

					<!-- S : window-btn-group -->
					<div class="window-btn-group">
						<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-func">포인트 적립</a>
					</div>
					<!-- S : window-btn-group -->
				</div>
				<!-- E:tab_content -->
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/member/abcmart.member.member.point.js<%=_DP_REV%>">
</script>