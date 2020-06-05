<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 관리자 공지 목록 초기 세팅. --%>
		abc.biz.board.adminnotice.initAdminNoticeSheet();
		
		<%-- 검색 : 검색어 선택박스 --%>
		$("#searchSelectBox").change(function(){
			//검색어 선택박스 선택 시 히든값 초기화
			$("#notcTitleText").val('');
			$("#loginId").val('');
			$("#adminName").val('');
			//검색어 선택박스 선택 시 검색어 인풋 박스 초기화
// 			$("#searchInputBox").val('');
		});
		
		<%-- 등록화면 호출 --%>
		$("#registAdminNotice").click(function() {
			location.href = "/board/admin-notice/create-form";
		});

		<%-- 검색 조건 초기화 --%>
		$("#btnSearchReset").click(function(){
			$('#frmSearch').each(function(){
				this.reset();
			});
			
			$("#applySystemType").val('');
			$("#popupYn").val('');
			$("#dispYn").val('');
			$("#notcTitleText").val('');
			$("#adminName").val('');
			$("#loginId").val('');
			$("#searchInputBox").val('');
		});
		
		// 검색
		$("#searchBtn").off().on("click", function() {
			abc.biz.board.adminnotice.adminnoticeDoAction('search');
			return false;
		});
		
		// enter 검색
		$(".search-wrap")
		.find(".ui-input")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.off().on("keyup", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
		    }
		});

		<%-- 검색조건 유지를 위한 --%>
		<c:if test="${not empty param.adminNotcSeq}">
			$("input:radio[name='radioUseDisplay'][value='<c:out value='${param.searchDisplay}'/>']").prop('checked', true);
			$("input:radio[name='radioUseMainPopup'][value='<c:out value='${param.searchMain}'/>']").prop('checked', true);
			$('#selApplySystemType').val('<c:out value="${param.searchSystemType}"/>');
			$('#searchSelectBox').val('<c:out value="${param.searchType}"/>');
			$('#searchInputBox').val('<c:out value="${param.searchText}"/>');
			$('#pageNum').val('<c:out value="${param.listPageCount}"/>');
		</c:if>
			
		<%-- 관리자 공지 목록 화면 호출 시 첫 검색 --%>
		//abc.biz.board.adminnotice.adminnoticeDoAction("search");
		
		$("#deleteAdminNotice").click(function(){
			abc.biz.board.adminnotice.adminnoticeDoAction('delete');
		});
	});

	<%-- 그리드 Click 이벤트 --%>
	function adminnoticeSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.board.adminnotice.adminnoticeSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	function adminnoticeSheet_OnSaveEnd(Code, Msg, StCode, StMsg, Response){
		abc.biz.board.adminnotice.adminnoticeSheetOnSaveEnd(Code, Msg, StCode, StMsg, Response);
 	}
	
	function adminnoticeSheet_OnRowSearchEnd(row){
		abc.biz.board.adminnotice.adminnoticeSheetOnRowSearchEnd(row);
	}
	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">관리자 공지 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>시스템 관리</li>
						<li>관리자 공지 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">공지 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
<!-- 			<input type="hidden" id="applySystemType" name="applySystemType" value=""/> -->
			<input type="hidden" id="popupYn"		  name="popupYn"		 value=""/>
			<input type="hidden" id="dispYn"		  name="dispYn"		 	 value=""/>
			<input type="hidden" id="notcTitleText"	  name="notcTitleText"	 value=""/>
			<input type="hidden" id="loginId"		  name="loginId"		 value=""/>
			<input type="hidden" id="adminName"		  name="adminName"		 value=""/>
			<%-- 검색조건 유지를 위한 --%>
			<input type="hidden" id="adminNotcSeqMain" name="adminNotcSeq" value=""/>
			<input type="hidden" id="listPageNum" name="listPageNum" value="${param.listPageNum}"/>
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>공지 검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span>대상 시스템</span>
							</th>
							<td class="input">
								<select class="ui-sel" id="selApplySystemType" name="applySystemType" title="대상 시스템 선택">
									<option value="" seleted>전체</option>
									<option value="A">ALL(관리자+파트너)</option>
									<option value="B">BO(관리자 시스템)</option>
									<option value="P">PO(파트너 시스템)</option>
								</select>
							</td>
							<td></td>
							<th scope="row">
								<span>관리자 메인 팝업</span>
							</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseMainPopup" id="radioUseMainPopupAll" checked>
											<label for="radioUseMainPopupAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseMainPopup" id="radioUseMainPopupUse" value="Y">
											<label for="radioUseMainPopupUse">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseMainPopup" id="radioUseMainPopupNone" value="N">
											<label for="radioUseMainPopupNone">사용안함</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span>전시여부</span>
							</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseDisplay" id="radioUseDisplayAll" checked>
											<label for="radioUseDisplayAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseDisplay" id="radioUseDisplayUse" value="Y">
											<label for="radioUseDisplayUse">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="radioUseDisplay" id="radioUseDisplayNone" value="N">
											<label for="radioUseDisplayNone">전시안함</label>
										</span>
									</li>
								</ul>
							</td>
							<td></td>
							<th scope="row">
								<span>검색어</span>
							</th>
							<td class="input">
								<div class="opt-keyword-box">
									<select class="ui-sel" id="searchSelectBox" name="searchSelectBox" title="검색어 타입 선택">
										<option value="" seleted>선택</option>
										<option value="notcTitleText">제목</option>
										<option value="loginId">작성자 ID</option>
										<option value="adminName">작성자 이름</option>
									</select>
									<input type="text" class="ui-input" id="searchInputBox" name="searchInputBox" placeholder="검색어 입력" title="검색어 입력" maxlength="100">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="#" class="btn-sm btn-func" id="btnSearchReset"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" id="searchBtn" class="btn-normal btn-func">검색</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">관리자 공지 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<span class="btn-group">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0)" class="btn-sm btn-del" id="deleteAdminNotice">선택 항목 삭제</a>
					<!-- E : 20190114 수정 -->
				</span>
			</div>
			<div class="fr">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<button type="button" class="btn-sm btn-link" id="registAdminNotice">등록</button>
				<!-- E : 20190114 수정 -->
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="noticeGrid" style="width:100%; height:429px;">
			</div>
		</div>
		</form>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.adminnotice.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>