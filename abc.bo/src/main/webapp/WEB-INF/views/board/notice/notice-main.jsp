<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		abc.biz.board.notice.noticeGridSheet();
		abc.biz.board.notice.initSet("${currentRow}");
		
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

		//검색버튼
		$("#searchBtn").off().on('click', function() {
			if(abc.date.searchValidate()){
				abc.biz.board.notice.doAction("search");
			}else{
				noticeGridSheet.RemoveAll();
			}
		});
		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.board.notice.searchValueSet();
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			$.form("#frmSearch").reset();
			$("a[name^=per1Month]").trigger("click");
		});
		//등록 버튼
		$("#btnCreateNotice").click(function(){
			abc.biz.board.notice.doAction("createNoticeForm");
		});

		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		
		<c:choose>
			<c:when test="${not empty param.listPageNum}">
				$('#fromDate').val('${param.fromDate}');
				$('#toDate').val('${param.toDate}');
				$('#siteNo').val('${param.searchSiteNo}');
				$("input:radio[name='dispYn'][value='${param.searchDispYn}']").prop('checked', true);
				$('#searchKey').val('${param.searchKey}');
				$('#searchValue').val('${param.searchValue}');
				$('#pageCount').val('${param.pageCount}');
				$("#searchBtn").trigger('click');
			</c:when>
			<c:otherwise>
				$("a[name^=per1Month]").trigger("click");
				
				$("#searchBtn").trigger('click');
			</c:otherwise>
		</c:choose>
		
	});
	//공지사항 상세페이지 이동
	function noticeGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( noticeGridSheet.ColSaveName(Col) == "notcTitleText" && Value != "" ) {
				$("#currentRow").val(Row);
				$("#notcSeq").val(noticeGridSheet.GetCellValue(Row, "notcSeq"));
				abc.biz.board.notice.readDetailForm();

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
				<h2 class="page-title">공지사항관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>고객응대관리</li>
						<li>공지사항관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">공지사항 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="get">
		<input type="hidden" name="menuNo" id="menuNo" value='${param.menuNo}'/>
		<input type="hidden" name="notcSeq" id="notcSeq" value='${param.notcSeq}'/>
		<input type="hidden" name="listPageNum" id="listPageNum" value='${param.listPageNum}'/>
		<input type="hidden" name="currentRow" id="currentRow"/>

		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>공지사항 검색</caption>
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
									<span class="date-box">
										<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
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
							<th scope="row">사이트</th>
							<td class="input">
								<select class="ui-sel" id="siteNo" name="siteNo" title="공지사이트 선택">
									<option value="">전체</option>
									<option value="ALL">공통</option>
									<c:forEach var="siteList" items="${siteList}">
										<option value="${siteList.siteNo}"><c:out value="${siteList.siteName}"/></option>
									</c:forEach>
								</select>
							</td>
							<td></td>
							<th scope="row">전시여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYn_all" name="dispYn" value="" type="radio" checked>
											<label for="dispYn_all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYn_Y" name="dispYn" value="Y" type="radio">
											<label for="dispYn_Y">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYn_N" name="dispYn" value="N" type="radio">
											<label for="dispYn_N">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
										<option value="">선택</option>
										<option value="notcTitleText">제목</option>
										<option value="rgsterName">작성자</option>
									</select>
									<input id="searchValue" name="searchValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="30">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-normal btn-func" id="searchBtn">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">공지사항 목록</h3>
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
			<div class="fr">
				<a href="javascript:void(0)" id="btnCreateNotice" class="btn-sm btn-link">등록</a>
			</div>
		</div>
		<!-- E : tbl-controller -->
		</form>
		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="noticeGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.notice.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>