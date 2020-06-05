<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		<%-- 초기 값 세팅 --%>
		abc.biz.member.member.inquiry.memberNo = "${memberNo}";
		abc.biz.member.member.inquiry.codeCombo = ${codeCombo};
		
		<%-- 회원 문의내역 그리드 세팅 --%>
		abc.biz.member.member.inquiry.initInquirySheet();
		
		<%-- 검색 --%>
		$("#searchBtn").click(function(){
			abc.biz.member.member.inquiry.memberInquiryDoAction('search');
		});
		
		<%-- 분류에 선택에 따른 유형 변경 --%>
		$("input:radio[name=cnslGbnCode]").click(function(){
			abc.biz.member.member.inquiry.cnslGbnCodeChange();
		});
		
		<%-- 유형변경 --%>
		$("#searchCnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#searchCnslTypeCode option:selected");
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "searchCnslTypeDtlCode");
		});
		
		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		<%-- 캘린더 버튼 설정(주)--%>
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		<%-- 캘린더 버튼 설정(월) --%>
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		<%-- 캘린더 버튼 설정(1년) --%>
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});
		
		<%-- 회원 유선상담 등록 팝업 호출 --%>
		$("#regiCounselBtn").click(function(){
			abc.registMemberCounsel(abc.biz.member.member.inquiry.memberNo);
		});
		
		$("a[name^=per1Month]").trigger("click");
		
		$("#searchChageVndr").off().on('change', function(){
			var val = $("#searchChageVndr").val();

			if (val == "vndrNm") {
				$('#searchChageVndrValue').show();
			} else {
				$('#searchChageVndrValue').hide();
			}
		});
		
		<%-- 초기화. --%>
		$("#resetBtn").click(function(){
			$('#frmSearch')[0].reset();
			$('#searchChageVndrValue').hide();
			abc.biz.member.member.inquiry.cnslGbnCodeChange();
			$("a[name^=per1Month]").trigger("click");
		});
		
		<%-- 회웑벙보 탭에서 이동시 세탕 --%>
		abc.biz.member.memberDetail.moveTab("radio");
		
		$('#searchBtn').trigger("click");
	})
	
	function inquirySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		var _url;
		var _width;
		var _height;
		var _cnslGbnCode;
		
		
		if ( Row != 0) {
			if (inquirySheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
				
				_cnslGbnCode = inquirySheet.GetCellValue(Row, "cnslGbnCode");
				
				if(_cnslGbnCode == abc.consts.CNSL_GBN_CODE_INQRY){
					_url = "/board/inquiry/read-detail-pop";
					_width = 1270;
					_height = 990;
				}else if(_cnslGbnCode == abc.consts.CNSL_GBN_CODE_VOC){
					_url = "/board/voiceofcustomer/read-detail-pop";
					_width = 1270;
					_height = 900;
				}else if(_cnslGbnCode == abc.consts.CNSL_GBN_CODE_CALL){
					_url = "/member/member-counsel-pop";
					_width = 1270;
					_height = 885;
				}
				
				var _params = {}
				_params.memberCnslSeq = inquirySheet.GetCellValue(Row, "memberCnslSeq");
				_params.memberNo = abc.biz.member.member.inquiry.memberNo;
				abc.open.popup({
					winname :   "DetailPop",
					url : _url,
					width : _width,
					height : _height,
					params : _params
		        });
			}
		}
	}
	
	function inquirySheet_OnRowSearchEnd(row){
		var checkAtchFile = inquirySheet.GetCellValue(row, "checkAtchFile");
		var vndrGbnType = inquirySheet.GetCellValue(row, "vndrGbnType");
		var cnslGbnCode = inquirySheet.GetCellValue(row, "cnslGbnCode");
		
		if (checkAtchFile > 0) {
			var inqryTitleText = inquirySheet.GetCellValue(row, "inqryTitleText");
			var htmlIcon = "<i class='far fa-file-alt' style='float: right;'></i>"
				inquirySheet.SetCellValue(row, "inqryTitleText", inqryTitleText + htmlIcon);
		}
		
		if('V' != vndrGbnType){
			inquirySheet.SetCellValue(row, 'vndrName', "ABC");
		}
// 		if(cnslGbnCode == "10003"){
// 			inquirySheet.SetCellValue(row, "inqryTitleText", inqryTitleText + htmlIcon);
// 		}else{
// 			inquirySheet.SetCellValue(row, "inqryTitleText", inqryTitleText + htmlIcon);
// 		}
	}
	
</script>

<!-- S : 문의 내역 목록 -->
<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">문의 현황</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-col -->
<table class="tbl-col">
	<caption>문의 현황</caption>
	<colgroup>
		<col>
		<col>
		<col>
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">미답변</th>
			<th scope="col">답변보류</th>
			<th scope="col">답변완료</th>
			<th scope="col">합계</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="text-center"><c:out value="${inquiryData.unasCnt}"/>건</td>
			<td class="text-center"><c:out value="${inquiryData.holdCnt}"/>건</td>
			<td class="text-center"><c:out value="${inquiryData.compCnt}"/>건</td>
			<td class="text-center"><c:out value="${inquiryData.totalCnt}"/>건</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-col -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">문의 검색</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : search-wrap -->
<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
<div class="search-wrap">
	<div class="search-inner">
		<table class="tbl-search">
			<caption>문의 검색</caption>
			<colgroup>
				<col style="width:15%;">
				<col>
				<col style="width:79px;">
				<col style="width:15%;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">답변상태</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="radio" name="searchAswrStatCode" id="radioConfirm01" value=""  checked="checked">
									<label for="radioConfirm01">전체</label>
								</span>
							</li>
							<c:forEach var="aswrStat" items="${codeList.ASWR_STAT_CODE}" varStatus="status">
								<li>
									<span class="ui-rdo">
										<input type="radio" name="searchAswrStatCode" id="radioConfirm${status.count}" value="${aswrStat.codeDtlNo}">
										<label for="radioConfirm${status.count}">${aswrStat.codeDtlName}</label>
									</span>
								</li>
							</c:forEach>
							<!-- <li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioConfirmModule" id="radioConfirm02">
									<label for="radioConfirm02">답변완료</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioConfirmModule" id="radioConfirm03">
									<label for="radioConfirm03">미답변</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioConfirmModule" id="radioConfirm04">
									<label for="radioConfirm04">답변보류</label>
								</span>
							</li> -->
						</ul>
						<!-- E : ip-box-list -->
					</td>
					<td></td>
					<th scope="row">사이트</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="radio" name="siteNo" id="radioSite01" value="" checked="checked">
									<label for="radioSite01">전체</label>
								</span>
							</li>
							<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
								<li>
									<span class="ui-rdo">
										<input type="radio" name="siteNo" id="radioSite${status.count}" value="${siteInfo.siteNo}">
										<label for="radioSite${status.count}">${siteInfo.siteName}</label>
									</span>
								</li>
							</c:forEach>
							<!-- <li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioSiteModule" id="radioSite02">
									<label for="radioSite02">통합몰</label>
								</span>
							</li>
							S : 유선상담인 경우, OTS 미노출
							<li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioSiteModule" id="radioSite03">
									<label for="radioSite03">OTS</label>
								</span>
							</li> -->
							<!-- E : 유선상담인 경우, OTS 미노출 -->
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr>
					<th scope="row">분류</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="radio" name="cnslGbnCode" id="radioQuestionType01" value="" checked="checked">
									<label for="radioQuestionType01">전체</label>
								</span>
							</li>
							<c:forEach var="cnslGbn" items="${codeList.CNSL_GBN_CODE}" varStatus="status">
								<c:if test="${(cnslGbn.codeDtlNo eq '10000' || cnslGbn.codeDtlNo eq '10001' || cnslGbn.codeDtlNo eq '10002')}">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="cnslGbnCode" id="radioQuestionType${status.count}" value="${cnslGbn.codeDtlNo}">
											<label for="radioQuestionType${status.count}">${cnslGbn.codeDtlName}</label>
										</span>
									</li>
								</c:if>
							</c:forEach>
							<!-- <li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioQuestionTypeModule" id="radioQuestionType02">
									<label for="radioQuestionType02">온라인 접수</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									DESC : input id / label for 동일하게 맞춰주세요
									<input type="radio" name="radioQuestionTypeModule" id="radioQuestionType03">
									<label for="radioQuestionType03">유선상담</label>
								</span>
							</li> -->
						</ul>
						<!-- E : ip-box-list -->
					</td>
					<td></td>
					<th scope="row">구분</th>
					<td class="input">
						<span class="ip-text-box">
							<select class="ui-sel" id="searchCnslTypeCode" name="searchCnslTypeCode" title="유형 선택">
								<option value="">전체</option>
								<c:forEach var="cnslType" items="${codeList.CNSL_TYPE_CODE}">
									<option value="${cnslType.codeDtlNo}">${cnslType.codeDtlName}</option>
								</c:forEach>
							</select>
							<select class="ui-sel" id="searchCnslTypeDtlCode" name="searchCnslTypeDtlCode" title="유형 선택">
								<option value="">전체</option>
							</select>
						</span>
						<!-- S : 분류 > 온라인접수, 유선상담 / 카테고리 > 1:1문의 선택시 -->
						<!-- S : ip-text-box -->
						<!--
						<span class="ip-text-box">
							<select class="ui-sel" title="문의유형 선택">
								<option>문의유형 선택</option>
							</select>
							<select class="ui-sel" title="문의구분 선택">
								<option>문의구분 선택</option>
							</select>
						</span>
						 -->
						<!-- E : ip-text-box -->
						<!-- E : 분류 > 온라인접수, 유선상담 / 카테고리 > 1:1문의 선택시 -->
					</td>
				</tr>
				<tr>
					<th scope="row">담당업체</th>
					<td class="input">
						<!-- S : ip-direct-search-box -->
						<span class="ip-direct-search-box">
							<select id="searchChageVndr" name="searchChageVndr" class="ui-sel" title="담당업체 선택">
								<option value="">전체</option>
								<option value="cs">ABC고객센터</option>
								<option value="vndr">입점사</option>
								<option value="vndrNm">직접검색</option>
							</select>
							<input id="searchChageVndrValue" name="searchChageVndrValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" style="display:none">
						</span>
						<!-- E : ip-direct-search-box -->
					</td>
					<td></td>
				</tr>
				<tr>
					<th scope="row">기간검색</th>
					<td class="input" colspan="4">
						<!-- S : term-date-wrap -->
						<span class="term-date-wrap">
							<select class="ui-sel" id="searchDateKey" name="searchDateKey" title="기간 선택">
								<option value="inqryDtm">등록일시</option>
								<option value="aswrDtm">최종답변일시</option>
							</select>
							<span class="date-box">
								<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10">
							</span>
							<span class="text">~</span>
							<span class="date-box">
								<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
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
				<tr>
					<th scope="row">검색어</th>
					<td class="input" colspan="4">
						<input type="text" id="inqryTitleText" name="inqryTitleText" class="ui-input" placeholder="제목 검색" title="검색어 입력">
					</td>
				</tr>
			</tbody>
		</table>
		<div class="confirm-box">
			<div class="fl">
				<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
			</div>
			<div class="fr">
				<a href="#" id="searchBtn" class="btn-normal btn-func">검색</a>
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
		<h3 class="content-title">문의 내역</h3>
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
	<div class="fr">
		<a href="#" id="regiCounselBtn" class="btn-sm btn-link">유선상담 등록</a>
	</div>
</div>
<!-- E : tbl-controller -->

<!-- S : ibsheet-wrap -->
<div class="ibsheet-wrap">
	<div id="inquiryGrid" style="width:100%; height:429px;">
	</div>
</div>
<!-- E : ibsheet-wrap -->
<!-- E : 문의 내역 목록 -->

<script src="/static/common/js/biz/member/abcmart.member.member.inquiry.js<%=_DP_REV%>">
</script>