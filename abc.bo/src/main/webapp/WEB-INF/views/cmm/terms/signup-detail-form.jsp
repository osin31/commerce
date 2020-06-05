<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function(){
		$("#termsDtlCode").val("<c:out value='${cmTerms.getTermsDtlCode()}'/>");
		$("select[name='termsDtlCode']").attr("disabled", true);
		//상세 영역 존재하는만큼 불러오기
		<c:forEach var="cmTermsDetailList" items="${cmTermsDetailList}" varStatus="idx">
			abc.biz.cmm.terms.getSignUpDetailArea("${cmTerms.getTermsSeq()}", "${idx.count}");
		</c:forEach>
		abc.biz.cmm.terms.saveCheck = false;
		//저장버튼
		$("#btnSaveSignUp").off().on('click', function(){
			var signUpIndex = $("#signUpListIndex").val();
			abc.biz.cmm.terms.setTermsInfo(signUpIndex);
			abc.biz.cmm.terms.doAction('signUpUpdateCheck');
		});
		//삭제버튼
		$(document).on("click","[id^='btnTableDelete']",function(){
			if(abc.biz.cmm.terms.termsDispCheck()){
				if(confirm("해당 영역 삭제시 내용은 복구되지 않습니다. 삭제하시겠습니까?")){
					var deleteIndex = $(this).attr("value");
					var delCount = $("#deleteCount").val();
					$("#deleteCount").val(delCount+1);
					abc.biz.cmm.terms.deleteSignUpList(deleteIndex);
				}
			}
		});
		//추가버튼
		$(document).on("click","[id^='btnTableAdd']",function(){
			abc.biz.cmm.terms.getSignUpListArea();
		});
		//초기화 버튼
		$("#reloadSignUpForm").off().on('click', function(){
			abc.biz.cmm.terms.reloadSignUpForm('${cmTerms.getTermsDtlCode()}');
		});
		<c:forEach var="cmTermsDetailList" items="${cmTermsDetailList}" varStatus="idx">
			$("input:radio[name='reqAgreeYn_"+"${idx.count}"+"'][value="+"${cmTermsDetailList.reqAgreeYn}"+"]").prop('checked', true);
			$("input:radio[name='dispYn_"+"${idx.count}"+"'][value="+"${cmTermsDetailList.dispYn}"+"]").prop('checked', true);
		</c:forEach>
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
	});
	
</script>
<form action="/cmm/terms/update-signup-order" id="signUpForm" name="signUpForm" method="post">
	<input type="hidden" id="termsSeq" name="termsSeq" value="<c:out value='${cmTerms.termsSeq}'/>">
	<input type="hidden" id="termsDtlSeq" name="termsDtlSeq" value="">
	<input type="hidden" id="termsTypeCode" name="termsTypeCode" value="<c:out value='${cmTerms.termsTypeCode}'/>">
	<input type="hidden" id="signUpListIndex" name="signUpListIndex" value="0">
	<input type="hidden" id="deleteCount" name="deleteCount" value="0">
	<input type="hidden" id="orgTermsApplyYmd" value='<fmt:formatDate value="${cmTerms.termsApplyYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>' >
	<input type="hidden" id="directChange" name="directChange" value="${Const.BOOLEAN_FALSE}">
	<input type="hidden" id="tabTitle" value="3">
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
							<li>회원가입 동의</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">회원구분</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>회원구분</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">회원구분</th>
						<td class="input">
							<!-- DESC : 수정/상세 화면일경우, select에 disabled="disabled" 속성을 추가해야 합니다. -->
							<select class="ui-sel" id="termsDtlCode" name="termsDtlCode" title="회원구분 선택">
								<option>선택</option>
								<option value="${signUpCodeList.get(1).getCodeDtlNo()}"><c:out value="${signUpCodeList.get(1).getCodeDtlName()}"/></option>
								<option value="${signUpCodeList.get(0).getCodeDtlNo()}"><c:out value="${signUpCodeList.get(0).getCodeDtlName()}"/></option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">회원가입 동의 항목</h3>
				</div>
				<div class="fr">
					<div class="btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="#" id="reloadSignUpForm" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : 20190208 추가 : 반복되는 테이블을 감싸는 div.tbl-append-wrap 추가 -->
			<div id='signUpListArea' class="tbl-append-wrap"></div>
			<!-- E : 20190208 추가 : 반복되는 테이블을 감싸는 div.tbl-append-wrap 추가 -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>회원가입 동의 작성 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">시행일자</th>
						<td class="input" colspan="3">
							<span class="date-box">
								<input type="text" id="termsApplyYmd" name="termsApplyYmd" data-role="datepicker" value='<fmt:formatDate value="${cmTerms.getTermsApplyYmd()}" pattern="yyyy.MM.dd"/>' class="ui-cal js-ui-cal" title="시행 일자 선택"/>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">작성자</th>
						<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${cmTerms.moderNo}" style="text-decoration: underline;"><c:out value="${cmTerms.moderDetailDpName}"/></a></td>
						<th scope="row">작성일시</th>
						<td><fmt:formatDate value="${cmTerms.getModDtm()}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<div class="fl">
					<a href="javascript:void(0)" id="termsDelete" class="btn-normal btn-del">삭제</a>
				</div>
				<div class="fr">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0)" id="btnPageBack" class="btn-normal btn-link">목록</a>
					<!-- E : 20190114 수정 -->
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="#" id="btnSaveSignUp" class="btn-lg btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : content-bottom -->
		</div>
	</div>
	<!-- E : container -->
</form>
	<form id="frmSearch" name="frmSearch" method="get">
	<input type="hidden" name="menuNo" 				value="${param.menuNo}"/>
	<input type="hidden" name="signupPageCount" 	value="${param.pageCount}"/>
	<input type="hidden" name="signupListPageNum" 	value="${param.listPageNum}"/>
	<input type="hidden" name="signupTermsDtlCode" 	value="${param.searchTermsDtlCode}"/>
	<input type="hidden" name="tabIndex" 			value="${param.tabIndex}"/>
	</form>
<script src="/static/common/js/biz/cmm/abcmart.cmm.terms.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>