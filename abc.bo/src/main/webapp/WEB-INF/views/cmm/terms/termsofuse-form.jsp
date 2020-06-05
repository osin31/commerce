<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){

		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("termsInfo", {
			width:"100%",
			height:"300px"
		});
		
		if(abc.text.allNull("${param.termsSeq}")){
			abc.biz.cmm.terms.saveHide();
			$("#termsApplyYmd").datepicker("option", "minDate", "1");
		}else{
			$("#termsDtlCode").val("${cmTerms.getTermsDtlCode()}");
			abc.biz.cmm.terms.termsOfUseSet();
		}
		//삭제 버튼
		$("#btnDeleteTerms").off().on('click', function(){
			if(abc.biz.cmm.terms.termsDispCheck()){
				abc.biz.cmm.terms.doAction('deleteTerms');
			}
		});
		
		//이용약관 저장 버튼
		$("#btnSaveTermsOfUse").off().on('click', function(){
			abc.biz.cmm.terms.doAction('termsOfUseSaveCheck');
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		//초기화 버튼
		$("#termsOfUseFormReload").off().on('click', function(){
			$.form("#termsOfUseForm").reset();
			CKEDITOR.instances.termsInfo.setData("");
			$("#termsDtlCode").val("${cmTerms.getTermsDtlCode()}");
		});
		
	});
</script>
<body>
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">약관설정</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>기본설정</li>
							<li>약관설정</li>
							<li>이용약관 설정</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">약관구분 </h3>
					<input type="hidden" id="tabTitle" value="1">
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="termsOfUseForm" name="termsOfUseForm" method="post" onsubmit="return false;">
				<input type="hidden" id="termsSeq" name="termsSeq" value="${param.termsSeq}">
				<input type="hidden" id="termsDtlSeq" name="termsDtlSeq" value="${param.termsDtlSeq}">
				<input type="hidden" id="termsTypeCode" name="termsTypeCode" value="${param.termsTypeCode}">
				<input type="hidden" id="reqAgreeYn" name="reqAgreeYn" value="${Const.BOOLEAN_TRUE}">
				<input type="hidden" id="dispYn" name="dispYn" value="${Const.BOOLEAN_TRUE}">
				<input type="hidden" id="orgTermsApplyYmd" value='<fmt:formatDate value="${cmTerms.termsApplyYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>' >
				<input type="hidden" id="directChange" name="directChange" value="${Const.BOOLEAN_FALSE}">
				<table class="tbl-row">
					<caption>약관구분</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">약관구분</th>
							<td class="input">
								<!-- DESC : 수정/상세 화면일경우, select에 disabled="disabled" 속성을 추가해야 합니다. -->
								<select class="ui-sel" title="약관구분 선택" id="termsDtlCode" name="termsDtlCode">
									<option>선택</option>
									<c:forEach items="${termsDtlList}" var="divisionList">
										<option value="${divisionList.getCodeDtlNo()}"><c:out value="${divisionList.getCodeDtlName()}"></c:out></option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
	
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title" id="midlleValue">약관설정</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
							<a href="javascript:void(0)" id="termsOfUseFormReload" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>사이트 이용약관</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">내용</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : editor-wrap -->
								<div class="editor-wrap">
									<textarea rows="10" cols="80" name="termsInfo" id="termsInfo" maxlength="4000" class="w100">
										<c:out value="${cmTermsDetail.getTermsInfo()}"></c:out>
									</textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">시행 일자</span>
							</th>
							<td class="input" colspan="3">
								<span class="date-box">
									<input type="text" id="termsApplyYmd" name="termsApplyYmd" data-role="datepicker" value='<fmt:formatDate value="${cmTerms.getTermsApplyYmd()}" pattern="yyyy.MM.dd"/>' class="ui-cal js-ui-cal" title="시행 일자 선택"/>'>
								</span>
							</td>
						</tr>
						<tr id="saveHide">
							<th scope="row">작성자</th>
							<td id="moderName"><a href="javascript:void(0)" class="adminPopTag" data-admin="${cmTerms.moderNo}" style="text-decoration: underline;"><c:out value="${cmTerms.moderDetailDpName}"/></a></td>
							<th scope="row">작성일시</th>
							<td id="modDtm"><fmt:formatDate value="${cmTerms.getModDtm()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
	
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
					<div class="fl" id="deleteHide">
						<a href="javascript:void(0)" id="btnDeleteTerms" class="btn-normal btn-del">삭제</a>
					</div>
					<!-- E : 20190117 추가  -->
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="javascript:void(0)" id="btnPageBack"class="btn-normal btn-link" >목록</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->
	
				<!-- S : content-bottom -->
				<div class="content-bottom">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0)" id="btnSaveTermsOfUse" class="btn-lg btn-save">저장</a>
					<!-- E : 20190114 수정 -->
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
	</form>
	<!-- E : container -->
	<form id="frmSearch" name="frmSearch" method="get">
	<input type="hidden" name="menuNo" 			value="${param.menuNo}"/>
	<input type="hidden" name="usePageCount" 	value="${param.pageCount}"/>
	<input type="hidden" name="useListPageNum" 	value="${param.listPageNum}"/>
	<input type="hidden" name="useTermsDtlCode" value="${param.searchTermsDtlCode}"/>
	<input type="hidden" name="tabIndex" 		value="${param.tabIndex}"/>
	</form>
	
</body>
<script src="/static/common/js/biz/cmm/abcmart.cmm.terms.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>