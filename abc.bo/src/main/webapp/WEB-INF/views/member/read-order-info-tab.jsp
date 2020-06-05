<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		
		abc.biz.member.member.order.codeCombo = ${codeCombo};
		abc.biz.member.member.order.siteCombo = ${siteCombo};
		<%-- 그리드 세팅 --%>
		abc.biz.member.member.order.initOrderSheet();
		
		<%-- 화면 세팅 --%>
		abc.biz.member.member.order.orderViewinit();
		
		
		
		var _Today = new Date(); 
		
		if ($("#toDate").val() == '' && $("#fromDate").val() == ''){
			$("#toDate").val($.datepicker.formatDate($("#toDate").datepicker("option", "dateFormat"), _Today));
			//_Today.setDate(_Today.getDate()-365);	// -365일전  테스트 
			$("#fromDate").val($.datepicker.formatDate($("#fromDate").datepicker("option", "dateFormat"), _Today));
		}
		
		<%-- 구분에 따른 그리드 세팅 --%>
		$("input:radio[name=radioTypeModule02]").change(function(){
			var divisionType = $(this).val();
			abc.biz.member.member.order.orderChange(divisionType);
		});
		
		<%-- 검색 --%>
		$("#searchBtn").click(function(){
			
			//구분에 따라 다른 Action 호출
			var divisionType =$ ("input:radio[name='radioTypeModule02']:checked").val();
			
			if(divisionType == "online"){
				abc.biz.member.member.order.orderDoAction('onLineSearch');
			}else{
				abc.biz.member.member.order.orderDoAction('offLineSearch');
			}
		});
		
		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		// 캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		// 캘린더 버튼 설정(1년)
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		<%--  임직원 여부 --%>
		$("#empYn").change(function(){
	        if($("#empYn").is(":checked")){
	        	$('#empYn').val('Y');
	        }else{
	        	$('#empYn').val('N');
	        }
	    });
		
		<%-- 주문번호 (숫자만) --%>
		$("#orderNo").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		//$("a[name^=perYear]").trigger("click");
		
		abc.biz.member.member.order.orderDoAction('onLineSearch');
	})
	
	function onlineSheet_OnRowSearchEnd(row){
  		var inquiryCnt 			= onlineSheet.GetCellValue(row, "inquiryCnt");
		var claimReturnCnt 			= onlineSheet.GetCellValue(row, "claimReturnCnt");
		var claimExchangeCnt 			= onlineSheet.GetCellValue(row, "claimExchangeCnt");
		var claimASCnt 			= onlineSheet.GetCellValue(row, "claimASCnt");
		var prdtQnaCnt			= onlineSheet.GetCellValue(row, "prdtQnaCnt");
		var reviewCnt			= onlineSheet.GetCellValue(row, "reviewCnt");
		
		if ( inquiryCnt == "" || inquiryCnt == null) {
			onlineSheet.SetCellValue(row, "inquiryCnt", "0"); 
		} 
		if ( claimReturnCnt == "" || claimReturnCnt == null) {
			onlineSheet.SetCellValue(row, "claimReturnCnt", "0"); 
		} 
		if ( claimExchangeCnt == "" || claimExchangeCnt == null) {
			onlineSheet.SetCellValue(row, "claimExchangeCnt", "0"); 
		} 
		if ( claimASCnt == "" || claimASCnt == null) {
			onlineSheet.SetCellValue(row, "claimASCnt", "0"); 
		}
		if ( prdtQnaCnt == '' || prdtQnaCnt == null) {
			onlineSheet.SetCellValue(row, "prdtQnaCnt", "0"); 
		}
		if ( reviewCnt == '' || reviewCnt == null) {
			onlineSheet.SetCellValue(row, "reviewCnt", "0"); 
		}
	}
	
	function onlineSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response){
		var lastRow = onlineSheet.LastRow(); 
		if(lastRow == "1"){
			$('#orderCnt').text("0");
			$('#orderAmt').text("0".format("#,##0.####")+"원"); 
		}else{
			var orderCnt = onlineSheet.GetCellValue(lastRow, "orderCnt"); 
			var orderAmt = onlineSheet.GetCellValue(lastRow, "orderAmt"); 
			$('#orderCnt').text(orderCnt);
			$('#orderAmt').text(new Number(orderAmt).format("#,##0.####")+"원"); 
		}
		
	}
	
	function onlineSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.member.member.order.onlineSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	function onlineSheet_OnButtonClick(Row, Col, Value, CellX, CellY, CellW, CellH) {    
		var memberNo = $('#memberNo').val();
		abc.registMemberCounsel(memberNo);
	}
	
	function offlineSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response){
		var lastRow = offlineSheet.LastRow(); 
		if(lastRow == "0"){
			$('#orderCnt').text("0");
			$('#orderAmt').text("0".format("#,##0.####")+"원"); 
		}else{
			var orderCnt = offlineSheet.GetCellValue(lastRow, "orderCnt"); 
			var orderAmt = offlineSheet.GetCellValue(lastRow, "orderAmt"); 
			$('#orderCnt').text(orderCnt);
			$('#orderAmt').text(new Number(orderAmt).format("#,##0.####")+"원"); 
		}
	}
	

	
</script>
<!-- S:tab_content -->
<div id="userInfoTab2" class="tab-content">
	<!-- S : 20190227 수정 // 주문내역 탭 컨텐츠 전면 수정 -->
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">주문내역</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : search-wrap -->
	<div class="search-wrap">
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="memberNo" name="memberNo" value="${param.memberNo}">
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
							<!-- S : 20190318 수정 // 임직원여부 추가 -->
							<th scope="row">구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="radioTypeModule02" id="radioType02-01" value="online" checked>
											<label for="radioType02-01">온라인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="radioTypeModule02" id="radioType02-02" value="offline">
											<label for="radioType02-02">오프라인(매장)</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">임직원여부</th>
							<td class="input">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="empYn" type="checkbox">
									<label for="empYn">임직원</label>
								</span>
							</td>
							<!-- E : 20190318 수정 // 임직원여부 추가 -->
						</tr>
						<tr>
							<th scope="row">사이트</th>
							<td class="input">
								<!-- DESC : 구분 > 오프라인(매장)선택시 input disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="siteNo" id="rdoSiteAll" value="" checked>
											<label for="rdoSiteAll">전체</label>
										</span>
									</li>
									<c:forEach items="${siteInfo}" var="sySite" varStatus="status">
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
							<th scope="row">주문배송유형</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<!-- DESC : 구분 > 오프라인(매장)선택시 select disabled 속성 추가 해주세요 -->
									<select id="dlvyTypeCode" name="dlvyTypeCode" class="ui-sel" title="주문배송유형 선택">
										<option value="">전체</option>
										<c:forEach items="${codeList.DLVY_TYPE_CODE}" var="dlvyTypeCode">
											<option value="${dlvyTypeCode.codeDtlNo}" >${dlvyTypeCode.codeDtlName}</option>
										</c:forEach>
										<option value="99999">택배전환</option>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">주문번호(거래번호)</th>
							<td class="input" colspan="4">
									<input type="text" class="ui-input" title="주문번호(거래번호) 입력" name="orderNo" id="orderNo">
							</td>
							<!-- DESC : 20190312 삭제 // 주문취소여부 삭제 -->
						</tr>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select class="ui-sel" title="기간 검색 선택" name="orderDateKey" id="orderDateKey" >
										<option value="orderDtm" >주문일</option>
										<option value="pymntDtm">입금(결제)완료일</option>
									</select>
									<span class="date-box">
										<input type="text" id="fromDate"  data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" id="toDate"  data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0)" name="perToday" class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0)" name="perWeek" class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0)" name="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0)" name="perYear" class="btn-sm btn-func">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<!--  <a href="javascript:void(0)" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>-->
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="searchBtn" class="btn-normal btn-func">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</form>
	</div>
	<!-- E : search-wrap -->
	

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">주문현황</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>주문현황</caption>
		<colgroup>
			<col style="width: 130px;">
			<col>
			<col style="width: 130px;">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">결제완료 주문건</th>
				<td><span id="orderCnt"></span></td>
				<th scope="row">총 결제금액</th>
				<td><span id="orderAmt"></span></td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="onlineGrid" style="width:100%; height:429px;">
		</div>
	</div>
	
	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="offlineGrid" style="width:100%; height:429px;">
		</div>
	</div>
	<!-- E : 20190227 수정 // 주문내역 탭 컨텐츠 전면 수정 -->
</div>
<!-- E:tab_content -->


<script src="/static/common/js/biz/member/abcmart.member.member.order.js<%=_DP_REV%>">
</script>