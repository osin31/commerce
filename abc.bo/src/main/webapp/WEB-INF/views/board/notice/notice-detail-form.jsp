<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("notcContText", {
			width:"100%",
			height:"300px"
		});
		abc.biz.board.notice.noticeDetailInitSet('${bdNotice.dispYn}', '${bdNotice.topNotcYn}');
		abc.biz.board.notice.topNotcYnCheck = '${bdNotice.topNotcYn}';
		//저장 버튼
		$("#btnNoticeSave").click(function(){
			abc.biz.board.notice.doAction("btnNoticeSave");
		});
		//목록 버튼
		$("#pageBack").click(function(){
			abc.biz.board.notice.pageBack();
		});
		//삭제버튼
		$("#btnNoticeDelete").click(function(){
			abc.biz.board.notice.doAction("noticeDelete");
		});
		
		//전시여부에 따라 상단공지 컨트롤
		$("input:radio[name=dispYn]").click(function(){
			abc.biz.board.notice.topNoticeArea();
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		//초기화
		$("#resetBtn").click(function(){
			$.form("#noticeForm").reset();
			abc.biz.board.notice.noticeReset('${bdNotice.dispYn}', '${bdNotice.topNotcYn}');
			abc.biz.board.notice.topNoticeArea();
		});
		
	});

	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">공지사항관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
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
				<h3 class="content-title">공지사항 등록</h3>
			</div>
			<div class="fr">
				<div class="btn-group">
					<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
				</div>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-row -->
		
		<input type="hidden" name="topNotcARTCount" id="topNotcARTCount">
		<input type="hidden" name="topNotcOTSCount" id="topNotcOTSCount">
		<input type="hidden" name="topNotcCount" id="topNotcCount">
		<input type="hidden" id="orgTopNotcYn" value="${bdNotice.topNotcYn}" />
		<input type="hidden" id="orgSiteNo" value="${bdNotice.siteNo}" />
		<input type="hidden" name="saveCheck" id="saveCheck" value="1">
		<form id="noticeForm" name="noticeForm" ">
		<input type="hidden" id="notcSeq" name="notcSeq" value="<c:out value='${bdNotice.notcSeq}'/>"/>
		<input type="hidden" id="menuNo" name="menuNo" value="<c:out value='${param.menuNo}'/>"/>
		<input type="hidden" id="listPageNum" name="listPageNum" value="${param.listPageNum}"/>
		<input type="hidden" id="currentRow" name="currentRow" value="${param.currentRow}"/>
		<input type="hidden" id="fromDate" name="fromDate" value="${param.fromDate}"/>
		<input type="hidden" id="toDate" name="toDate" value="${param.toDate}"/>
		<input type="hidden" id="searchSiteNo" name="searchSiteNo" value="${param.siteNo}"/>
		<input type="hidden" id="searchDispYn" name="searchDispYn" value="${param.dispYn}"/>
		<input type="hidden" id="searchKey" name="searchKey" value="${param.searchKey}"/>
		<input type="hidden" id="searchValue" name="searchValue" value="${param.searchValue}"/>
		<input type="hidden" id="pageCount" name="pageCount" value="${param.pageCount}"/>
		<table class="tbl-row">
			<caption>공지사항 등록</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">
						<span class="th-required">공지사이트</span>
					</th>
					<td class="input"  colspan="3">
						<select class="ui-sel" id="siteNo" name="siteNo" value="${bdNotice.siteNo}" title="공지사이트 선택">
							<option value="">선택</option>
							<option value="ALL" <c:if test="${bdNotice.siteNo eq 'ALL'}"> selected</c:if>>공통</option>
							<c:forEach var="siteList" items="${siteList}">
								<option value="${siteList.siteNo}" <c:if test="${bdNotice.siteNo eq siteList.siteNo}"> selected</c:if>><c:out value="${siteList.siteName}"/></option>
							</c:forEach>
						</select>
					</td>
					
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">제목</span>
					</th>
					<td colspan="3" class="input">
						<input type="text" id="notcTitleText" name="notcTitleText" class="ui-input" value="${bdNotice.notcTitleText}" maxlength="50" title="제목 입력">
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">내용</span>
					</th>
					<td colspan="3" class="input">
						<!-- S : editor-wrap -->
						<div class="editor-wrap">
							<textarea name="notcContText" id="notcContText" maxlength="2000" class="w100">${bdNotice.notcContText}</textarea>
						</div>
						<!-- E : editor-wrap -->
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">전시여부</span>
					</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="dispYn_Y" name="dispYn" value="Y" type="radio" checked>
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
					<th scope="row">
						<span class="th-required">상단 공지설정</span>
					</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="topNotcYn_Y" name="topNotcYn" value="Y" type="radio" >
									<label for="topNotcYn_Y">설정</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="topNotcYn_N" name="topNotcYn" value="N" type="radio" checked>
									<label for="topNotcYn_N">설정안함</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdNotice.rgsterNo}" style="text-decoration: underline;"><c:out value="${bdNotice.rgsterDetailDpName}"/></a></td>
					<th scope="row">작성일시</th>
					<td><fmt:formatDate value="${bdNotice.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
				<tr>
					<th scope="row">수정자</th>
					<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdNotice.moderNo}" style="text-decoration: underline;"><c:out value="${bdNotice.moderDetailDpName}"/></a></td>
					<th scope="row">수정일시</th>
					<td><fmt:formatDate value="${bdNotice.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
			</tbody>
		</table>
		</form>
		<!-- E : tbl-row -->

		<!-- S : tbl-desc-wrap -->
		
		<div class="tbl-desc-wrap">
			<c:if test="${bdNotice.notcSeq ne null}">
				<div class="fl">
					<a href="javascript:void(0)" id="btnNoticeDelete" class="btn-normal btn-del">삭제</a>
				</div>
			</c:if>
			<div class="fr">
				<a href="javascript:void(0)" id="pageBack" class="btn-normal btn-link">목록</a>
			</div>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-bottom -->
		<div class="content-bottom">
			<a href="javascript:void(0)" id="btnNoticeSave" class="btn-lg btn-save">저장</a>
		</div>
		<!-- E : content-bottom -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.notice.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>