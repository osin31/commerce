<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function() {
		abc.biz.board.faq.faqGridSheet();
		//검색 버튼
		$("#searchBtn").click(function(){
			abc.biz.board.faq.doAction("search");
		});

		// 문의유형 코드 변경
		$("#cnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#cnslTypeCode option:selected");		// 상담유형코드
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
			
			if(abc.text.isBlank($("#cnslTypeCode").val())){
				$("#cnslTypeDtlCode").hide();
			}else{
				$("#cnslTypeDtlCode").show();
			}
		});
		
		//enter 검색 이벤트
		$("#frmSearch").on('submit', function(){
			$("#searchBtn").trigger("click");
			return false;
		});

		//등록 버튼
		$("#btnCreateFaq").click(function(){
			abc.biz.board.faq.doAction("createFaqForm");
		});

		//초기화 버튼
		$("#resetBtn").click(function () {
			$.form("#frmSearch").reset();
		});

		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.board.faq.searchValueSet();
		});
		
		//TOP10 순의 설정 화면
		$("#btnTop10List").click(function(){
			abc.biz.board.faq.doAction("top10ListForm");
		});
		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		<c:choose>
			<c:when test="${not empty param.listPageNum}">
				$("input:radio[name='dispYn'][value='${param.searchDispYn}']").prop('checked', true);
				$("input:radio[name='top10SetupYn'][value='${param.searchTop10SetupYn}']").prop('checked', true);
				$('#searchKey').val('${param.searchKey}');
				$('#cnslTypeCode').val('${param.searchCnslTypeCode}');
				if(abc.text.allNull('${param.searchCnslTypeCode}')){
					$("#cnslTypeDtlCode").hide();
				}else{
					abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#cnslTypeCode").val(), "cnslTypeDtlCode");
				}
				$('#cnslTypeDtlCode').val('${param.searchCnslTypeDtlCode}');
				$('#searchValue').val('${param.searchValue}');
				$('#pageCount').val('${param.pageCount}');
			</c:when>
			<c:otherwise>
				$("#cnslTypeDtlCode").hide();
			</c:otherwise>
		</c:choose>
		$("#searchBtn").trigger('click');
		

	});

	function faqGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if (faqGridSheet.ColSaveName(Col) == "faqTitleText" && Value != "" ) {
				$("#currentRow").val(Row);
				$("#faqSeq").val(faqGridSheet.GetCellValue(Row, "faqSeq"));
				$("#listPageNum").val(faqGridSheet.GetCurrentPage());
				abc.biz.board.faq.readDetailForm();  //FAQ 상세페이지 이동

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
				<h2 class="page-title">FAQ관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>고객응대관리</li>
						<li>FAQ관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">FAQ 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="get">
		<input type="hidden" name="menuNo" id="menuNo" value='${param.menuNo}'/>
		<input type="hidden" name="faqSeq" id="faqSeq" />
		<input type="hidden" name="listPageNum" id="listPageNum" value='${param.listPageNum}'/>
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>FAQ 검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">TOP 10 설정 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="top10SetupYn_all" name="top10SetupYn" value="" type="radio" checked>
											<label for="top10SetupYn_all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="top10SetupYn_Y" name="top10SetupYn" value="Y" type="radio">
											<label for="top10SetupYn_Y">설정</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="top10SetupYn_N" name="top10SetupYn" value="N" type="radio">
											<label for="top10SetupYn_N">설정안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">FAQ 유형</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" title="FAQ유형 대분류 선택">
										<option value="">전체</option>
										<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}">${cnslTypeCode.codeDtlName}</option>
										</c:forEach>
									</select>
									<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" style="width:150px;" title="FAQ유형 소분류 선택">
										<option value="">선택</option>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
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
							<td></td>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
										<option value="">선택</option>
										<option value="faqTitleText">질문/내용</option>
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
				<h3 class="content-title">FAQ 목록</h3>
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
				<span class="btn-group">
					<!-- DESC : html/common/BO-CM-28000.html 파일 확인 해주세요 -->
					<a href="javascript:void(0)" id="btnTop10List" class="btn-sm btn-link">TOP10 순위 설정</a>
				</span>
			</div>
			<div class="fr">
				<a href="javascript:void(0)" id="btnCreateFaq" class="btn-sm btn-link">등록</a>
			</div>
		</div>
		</form>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="faqGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.faq.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>