<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		abc.biz.cmm.terms.getSignUpListArea();
		abc.biz.cmm.terms.saveCheck = true;
		$("#termsApplyYmd").datepicker("option", "minDate", "1");
		//저장버튼
		$("#btnSaveSignUp").off().on('click', function(){
			var signUpIndex = $("#signUpListIndex").val();
			abc.biz.cmm.terms.setTermsInfo(signUpIndex);
			abc.biz.cmm.terms.doAction('signUpSaveCheck');
		});
		//추가버튼
		$(document).on("click","[id^='btnTableAdd']",function(){
			abc.biz.cmm.terms.getSignUpListArea();
		});
		//삭제
		$(document).on("click","[id^='btnTableDelete']",function(){
			if(confirm("해당 영역 삭제시 내용은 복구되지 않습니다. 삭제하시겠습니까?")){
				var deleteIndex = $(this).attr("value");
				abc.biz.cmm.terms.deleteSignUpList(deleteIndex);
			}
		});
		
		//초기화 버튼
		$("#reloadSignUpForm").off().on('click', function(){
			abc.biz.cmm.terms.reloadSignUpForm('${cmTerms.getTermsDtlCode()}');
		});
		
	});
	
</script>
<form id="signUpForm" name="signUpForm" method="post">
	<input type="hidden" id="termsSeq" name="termsSeq" value="">
	<input type="hidden" id="termsDtlSeq" name="termsDtlSeq" value="">
	<input type="hidden" id="termsTypeCode" name="termsTypeCode" value="${param.termsTypeCode}">
	<input type="hidden" id="signUpListIndex" name="signUpListIndex" value="0">
	<input type="hidden" id="deleteCount" name="deleteCount" value="0">
	<input type="hidden" id="tabIndex" name="tabIndex" value="${tabIndex}">
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
							<select class="ui-sel" name="termsDtlCode" id="termsDtlCode" title="회원구분 선택">
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
						<a href="javascript:void(0)" id="reloadSignUpForm" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
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
								<input type="text" name="termsApplyYmd" id="termsApplyYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="시행 일자 선택"/>
							</span>
						</td>
					</tr>
					<tr id="saveHide">
						<th scope="row">작성자</th>
						<td></td>
						<th scope="row">작성일시</th>
						<td></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<div class="fr">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="#" id="btnPageBack" class="btn-normal btn-link">목록</a>
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
	
<script src="/static/common/js/biz/cmm/abcmart.cmm.terms.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>