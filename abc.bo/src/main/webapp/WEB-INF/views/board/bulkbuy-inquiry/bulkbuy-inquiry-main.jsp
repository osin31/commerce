<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		// 결과 목록 그리드 init
		abc.biz.board.bulkbuy.bulkBuyGridSheet();
		
		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
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

		//검색 버튼
		$("#searchBtn").off().on('click', function() {
			if(abc.date.searchValidate()){
				$("#currentRow").val("1");
				abc.biz.board.bulkbuy.doAction("search");
			}else{
				bulkBuyGridSheet.RemoveAll();
			}
		});
		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.board.bulkbuy.searchValueSet();
		});

		$("#resetBtn").click(function(f) {
			abc.biz.board.bulkbuy.resetButton();
			$("a[name^=per1Month]").trigger("click");
		});

		$("a[name^=per1Month]").trigger("click");
		
		if(abc.text.allNull(abc.param.getParams().fromDash)){
			$("#searchBtn").trigger('click');
		}else{
			// 대시보드에서 더보기 클릭으로 왔을때 미답변 검색
			abc.biz.board.bulkbuy.fromDashSearch('<c:out value="${fromDash}"/>', '<c:out value="${tabIdx}"/>');
		}
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});

	});

	// 단체주문 문의 상세 페이지 이동
	function bulkBuyGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( bulkBuyGridSheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
				$("#currentRow").val(Row);

				var _url = "/board/bulkbuy-inquiry/read-detail-pop";

				var _params = {}
				_params.blkBuyInqrySeq = bulkBuyGridSheet.GetCellValue(Row, "blkBuyInqrySeq")

				abc.open.popup({
					winname :	"bulkBuyGridInqryDetailPop",
					url 	:	_url,
					width 	:	1270,
					height	:	800,
					params	:	_params
				});

			} else if ( bulkBuyGridSheet.ColSaveName(Col) == "mngrDpName" && Value != "" ) {
				if ("" != bulkBuyGridSheet.GetCellValue(Row, "memberNo")) {
					abc.memberDetailPopup(bulkBuyGridSheet.GetCellValue(Row, "memberNo"));
				}
			} 
		}
	}


	//그리드 search후 컬럼 선택
	function bulkBuyGridSheet_OnSearchEnd(row){
		row = $("#currentRow").val();
		bulkBuyGridSheet.SetSelectCol("inqryTitleText");
		bulkBuyGridSheet.SetSelectRow(row);


	}


</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">단체주문 문의관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>고객응대관리</li>
						<li>단체주문 문의관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">단체주문 문의 관리</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<input type="hidden" name="currentRow" id="currentRow" value="1"/>
		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="get">
		<input type="hidden" name="blkBuyInqrySeq" id="blkBuyInqrySeq"/>
		<input type="hidden" name="menuNo" id="menuNo" />
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>단체주문 문의 관리</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
<!-- 									<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택"> -->
<!-- 										<option name="">선택</option> -->
<!-- 										<option value="inqryDtm" selected>문의일</option> -->
<!-- 									</select> -->
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
							<th scope="row">사이트 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="siteNo" id="siteNo0" value="" checked>
											<label for="siteNo0">전체</label>
										</span>
									</li>
									<c:forEach var="siteNoList" items="${siteNoList}" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="siteNo<c:out value="${status.count}" />"
												       value="<c:out value="${siteNoList.siteNo}" />" >
												<label for="siteNo<c:out value="${status.count}" />" >
													<c:out value="${siteNoList.siteName}" />
												</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
										<option value="">선택</option>
										<option value="inqryTitleText">제목</option>
										<option value="loginId">담당자id</option>
										<option value="memberName">담당자명</option>
									</select>
									<input id="searchValue" name="searchValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="30">
									<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4" style="display:none;">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0)" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-normal btn-func" id="searchBtn">검색</a>
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
				<h3 class="content-title">단체주문 문의 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount" name="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="bulkBuyGrid" style="width:100%; height:429px;">
			</div>				
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.bulkbuyinquiry.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>