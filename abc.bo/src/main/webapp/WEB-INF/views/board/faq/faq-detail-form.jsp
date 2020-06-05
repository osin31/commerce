<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function() {
		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("faqContText", {
			width:"100%",
			height:"300px"
		});
		
		abc.biz.board.faq.faqDetailInitSet('${bdFaq.cnslTypeCode}', '${bdFaq.cnslTypeDtlCode}', '${bdFaq.dispYn}', '${bdFaq.top10SetupYn}');
		abc.biz.board.faq.top10Area();
		// 문의유형 코드 변경
		$("#cnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#cnslTypeCode option:selected");		// 상담유형코드
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
		});
		
		$("input:radio[name=dispYn]").click(function(){
			abc.biz.board.faq.top10Area();
		});

		
		//목록 버튼
		$("#pageBack").click(function(){
			abc.biz.board.faq.pageBack(abc.param.getParams().currentRow);
		});
		
		//저장 버튼
		$("#btnFaqSave").click(function(){
			abc.biz.board.faq.doAction("btnFaqSave");
		});
		
		//삭제버튼
		$("#btnFaqDelete").click(function(){
			abc.biz.board.faq.doAction("faqDelete");
		});
		
		//초기화 버튼
		$("#faqRelad").click(function(){
			abc.biz.board.faq.faqReload('${bdFaq.dispYn}', '${bdFaq.top10SetupYn}', '${bdFaq.cnslTypeDtlCode}');
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
	});

</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">FAQ관리</h2>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="javascript:void(0)"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
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
				<h3 class="content-title">FAQ 등록</h3>
			</div>
			<div class="fr">
				<div class="btn-group">
					<a href="javascript:void(0)" id="faqRelad" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
				</div>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-row -->
		<input type="hidden" id="saveCheck" name="saveCheck" value="1"/>
		<form id="faqForm" name="faqForm">
		<input type="hidden" name="faqSeq" value="${bdFaq.faqSeq}">
		<input type="hidden" id="currentTop10Setup" name="currentTop10Setup" value="${bdFaq.top10SetupYn}">
		<input type="hidden" name="top10SortSeq" value="${bdFaq.top10SortSeq}">
		<input type="hidden" id="top10Count">
		<input type="hidden" name="menuNo">
		<table class="tbl-row">
			<caption>FAQ 등록</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">
						<span class="th-required">FAQ 유형</span>
					</th>
					<td class="input" colspan="3">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" title="FAQ유형 대분류 선택">
								<option value="">선택</option>
								<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
									<option value="${cnslTypeCode.codeDtlNo}" <c:if test="${bdFaq.cnslTypeCode eq cnslTypeCode.codeDtlNo}"> selected</c:if>>${cnslTypeCode.codeDtlName}</option>
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
					<th scope="row">
						<span class="th-required">질문</span>
					</th>
					<td colspan="3" class="input">
						<input type="text" id="faqTitleText" name="faqTitleText" value="${bdFaq.faqTitleText}" maxlength="50" class="ui-input" title="질문 입력">
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">답변</span>
					</th>
					<td colspan="3" class="input">
						<!-- S : editor-wrap -->
						<div class="editor-wrap">
							<textarea name="faqContText" id="faqContText" maxlength="2000" class="w100">${bdFaq.faqContText}</textarea>
						</div>
						<!-- E : editor-wrap -->
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
					<th scope="row">TOP 10 설정</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
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
									<input id="top10SetupYn_N" name="top10SetupYn" value="N" type="radio" checked>
									<label for="top10SetupYn_N">설정안함</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdFaq.rgsterNo}" style="text-decoration: underline;"><c:out value="${bdFaq.getRgsterDetailDpName()}"/></a></td>
					<th scope="row">작성일시</th>
					<td><fmt:formatDate value="${bdFaq.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
				<tr>
					<th scope="row">수정자</th>
					<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdFaq.moderNo}" style="text-decoration: underline;"><c:out value="${bdFaq.getModerDetailDpName()}"/></a></td>
					<th scope="row">수정일시</th>
					<td><fmt:formatDate value="${bdFaq.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
			</tbody>
		</table>
		</form>
		<!-- E : tbl-row -->
		
		<!-- S : 20190308 추가 // top10 설명 문구 영역 추가 -->
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap">
			<ol class="tbl-desc-list">
				<li>* TOP10이 설정된 FAQ는 FAQ관리 목록 화면에서 TOP10설정 팝업을 통하여 순서 설정이 가능합니다.</li>
				<li>* TOP10설정은 최대 30개까지 등록 가능하며, 10순위 까지만 FO(사용자)화면에 노출 됩니다. </li>
			</ol>
		</div>
		<!-- E : tbl-desc-wrap -->
		<!-- E : 20190308 추가 // top10 설명 문구 영역 추가 -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap">
			<div class="fl" id="deleteBtnArea" >
				<a href="javascript:void(0)" id="btnFaqDelete" class="btn-normal btn-del">삭제</a>
			</div>
			<div class="fr">
				<a href="javascript:void(0)" id="pageBack" class="btn-normal btn-link">목록</a>
			</div>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-bottom -->
		<div class="content-bottom">
			<a href="javascript:void(0)" id="btnFaqSave" class="btn-lg btn-save">저장</a>
		</div>
		<!-- E : content-bottom -->
	</div>
</div>
<!-- E : container -->
	<form id="frmSearch" name="frmSearch" method="get">
	<input type="hidden" name="menuNo" 					value="${param.menuNo}"/>
	<input type="hidden" name="searchCnslTypeCode" 		value="${param.cnslTypeCode}"/>
	<input type="hidden" name="searchCnslTypeDtlCode"	value="${param.cnslTypeDtlCode}"/>
	<input type="hidden" name="searchDispYn" 			value="${param.dispYn}"/>
	<input type="hidden" name="searchKey" 				value="${param.searchKey}"/>
	<input type="hidden" name="searchValue" 			value="${param.searchValue}"/>
	<input type="hidden" name="pageCount" 				value="${param.pageCount}"/>
	<input type="hidden" name="listPageNum" 			value="${param.listPageNum}"/>
	<input type="hidden" name="searchTop10SetupYn" 		value="${param.top10SetupYn}"/>
	</form>

<script src="/static/common/js/biz/board/abcmart.board.faq.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>