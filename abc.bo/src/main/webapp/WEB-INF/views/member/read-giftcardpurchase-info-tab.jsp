<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		
		abc.biz.member.member.giftcardpurchase.codeCombo = ${codeCombo}; //combo 코드 값
		<%-- 회원 문의내역 그리드 세팅 --%>
		abc.biz.member.member.giftcardpurchase.initGiftCardSheet();
		
		
		<%-- 송장번호(숫자만)  --%>
		$("#giftCardOrderNo").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 검색 --%>
		$("#searchBtn").click(function(){
			abc.biz.member.member.giftcardpurchase.cardNoSel();
		});
		
	});
	
	function giftcardSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.member.member.giftcardpurchase.giftcardSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	function giftcardSheet_OnRowSearchEnd(row){
		var pymntDtm 	= giftcardSheet.GetCellValue(row,"pymntDate");
		var orderStatCode 	= giftcardSheet.GetCellValue(row,"orderStatCode");
		var _Today = new Date();
		var pymntAmt 	= giftcardSheet.GetCellValue(row,"pymntAmt");

		// 날짜가 7일안에 있는지 확인 
		var dataDiff =  abc.date.dateDiffAbs(abc.date.formatDate(_Today),pymntDtm) <= 7 ? true : false;
		// 카드잔액과 결제 금액을 비교 
		var amtDiff = Number($('#conBalanceAmount').val()) >= Number(pymntAmt);
		
// 		console.log("33333 ::"+abc.date.dateDiffAbs(abc.date.formatDate(_Today),pymntDtm));
// 		console.log("1 ::"+dataDiff);
// 		console.log("2 ::"+amtDiff);
// 		console.log("3 ::"+Number(pymntAmt));
// 		console.log("4 ::"+Number($('#conBalanceAmount').val()));
// 		console.log("00000 ::"+orderStatCode);
 		
		// 결제 취소 완료이면 - 로 표시 
		if(orderStatCode == '${CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE}'){
			giftcardSheet.SetCellText(row, "pymntCancel","-");	
		}else{
			// 날짜가 7일 안에 있거나 카드 잔액이 더 크면 결제 취소 버튼 활성화 
			if(dataDiff  && amtDiff){
				giftcardSheet.InitCellProperty(row,10, {'Type': 'Button'});
				giftcardSheet.SetCellValue(row, "pymntCancel", "결제취소");
				giftcardSheet.SetCellEditable(row,"pymntCancel",1);
			}else{
				giftcardSheet.SetCellText(row, "pymntCancel","-");	
			}
		}
		
 		// 기프트 주문 상태 코드 
		var giftCardOrderTypeCode 	= giftcardSheet.GetCellValue(row,"giftCardOrderTypeCode");
		var mmsReSendDtm 	= giftcardSheet.GetCellValue(row,"mmsReSendDtm");
		var giftCardStatCode 	= giftcardSheet.GetCellValue(row,"giftCardStatCode");
		
// 		console.log("5 ::"+giftCardOrderTypeCode);
// 		console.log("6 ::"+mmsReSendDtm);
// 		console.log("7 ::"+giftCardStatCode);
		
 		// 재전송 버튼 노출되지 않는 조건 (충전,취소)giftCardOrderTypeCode : 충전 또는  orderStatCode :  취소 
		if(giftCardOrderTypeCode == "${CommonCode.GIFT_CARD_ORDER_TYPE_CODE_CHARGE}" || orderStatCode == "${CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE}"){
			giftcardSheet.SetCellText(row, "reSend","-");	
		}else{
			if(mmsReSendDtm == null || mmsReSendDtm == ""){
				giftcardSheet.InitCellProperty(row,9, {'Type': 'Button'});
				giftcardSheet.SetCellValue(row, "reSend", "재전송");
				giftcardSheet.SetCellEditable(row,"reSend",1);
				giftcardSheet.SetCellText(row, "reSend","재전송");	
			}else{
				giftcardSheet.SetCellText(row, "reSend","-");	
			}
		}
	}
</script>

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">기프트카드 구매내역</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : search-wrap -->
<div class="search-wrap">
<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
<input type="hidden" id="memberNo" name="memberNo" value="${param.memberNo}">
<input type="hidden" id="conBalanceAmount" name=conBalanceAmount value="">
	<div class="search-inner">
		<table class="tbl-search">
			<caption>주문내역 검색</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
				<col style="width: 79px">
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">주문번호</th>
					<td class="input">
						<input type="text" class="ui-input" title="주문번호" name="giftCardOrderNo" id="giftCardOrderNo">
					</td>
					<td></td>
					<th scope="row">결제수단</th>
					<td class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box" id="devyTypeArea">
							<!-- DESC : 구분 > 오프라인(매장)선택시 select disabled 속성 추가 해주세요 -->
							<select id="pymntMeansCode" name="pymntMeansCode" class="ui-sel" title="주문배송유형 선택">
								<option value="">전체</option>
								<option value="${CommonCode.PYMNT_MEANS_CODE_CARD}">신용카드</option>
								<option value="${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}">실시간계좌이체</option>
							</select>
						</span>
						<!-- E : ip-text-box -->
					</td>
				</tr>
				<tr>
					<th scope="row">결제 일자검색</th>
					<td class="input" colspan="4">
						<!-- S : term-date-wrap -->
						<span class="term-date-wrap">
							<span class="date-box">
								<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" readonly="readonly" title="시작일 선택">
							</span>
							<span class="text">~</span>
							<span class="date-box">
								<input type="text"  id="toDate" name="toDate"  data-role="datepicker" class="ui-cal js-ui-cal" readonly="readonly" title="종료일 선택">
							</span>
							<span class="btn-group">
								<a href="javascript:void(0)" name="perToday" class="btn-sm btn-func">오늘</a>
								<a href="javascript:void(0)" name="perWeek" class="btn-sm btn-func">일주일</a>
								<a href="javascript:void(0)" name="per1Month" class="btn-sm btn-func">한달</a>
								<a href="javascript:void(0)" name="per1Year" class="btn-sm btn-func">1년</a>
							</span>
						</span>
						<!-- E : term-date-wrap -->
					</td>
				</tr>
				<tr>
					<th scope="row">보유카드 선택</th>
					<td class="input">
						<span class="ip-text-box">
							<select class="ui-sel" id="cardNoText" name="cardNoText" title="보유카드 선택">
								<option value="">카드선택</option>
								<c:forEach var="giftcardList" items="${mbMemberGiftCardData}">
									<option value="${giftcardList.cardNoText}">${giftcardList.cardNoText}&nbsp;${giftcardList.giftCardName}</option>
								</c:forEach>
							</select>
						</span>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<div class="confirm-box">
			<div class="fl">
				<a href="#" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
			</div>
			<div class="fr">
				<a href="#" id="searchBtn" class="btn-normal btn-func">검색</a>
			</div>
		</div>
	</div>
	<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
</div>
<!-- E : search-wrap -->


<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">기프트카드 구매내역</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-controller -->
<div class="tbl-controller">
	<div class="fl">
		<span class="opt-group">
			<label class="title" for="selView03">목록개수</label>
			<select class="ui-sel" id="pageCount">
				<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
			</select>
		</span>
	</div>
</div>
<!-- E : tbl-controller -->

<!-- S : ibsheet-wrap -->
<div class="ibsheet-wrap">
	<div id="giftcardGrid" style="width:100%; height:429px;">
	</div>
</div>
<!-- E : ibsheet-wrap -->
<!-- E : 문의 내역 목록 -->
</form>
<script src="/static/common/js/biz/member/abcmart.member.member.giftcardpurchase.js<%=_DP_REV%>">
</script>