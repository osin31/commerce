<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<script type="text/javascript">
			$(function() {
				$("#save").click(function(f) {
					abc.biz.cmm.forbiddenword.save();
				});

				$("#resetBtn").click(function(f) {
					$.form("#forbiddenWordForm").reset();
				});
				
				$("#register, #modifier").click(function(f) {
					abc.adminDetailPopup($(this).attr("data"));
				});
			});
		</script>

		<!-- S : container -->
		<form id="forbiddenWordForm">
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">금칙어 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>기본설정</li>
								<li>금칙어 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">금칙어 등록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="#" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>금칙어 등록 항목</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">금칙어</th>
							<td class="input" colspan="3">
								<textarea class="ui-textarea" title="금칙어 입력" name="forbidWordText">${cmForbiddenWord.forbidWordText}</textarea>
							</td>
						</tr>
						<tr>
							<th scope="row">작성자</th>
							<td>
								<a href="#" id="register" data="${cmForbiddenWord.rgster.adminNo}">${cmForbiddenWord.rgsterDisplayName}</a>
							</td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${cmForbiddenWord.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td>
								<a href="#" id="modifier" data="${cmForbiddenWord.moder.adminNo}">${cmForbiddenWord.moderDisplayName}</a>
							</td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${cmForbiddenWord.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<ol class="tbl-desc-list">
						<li>* 다수의 금칙어를 입력하시려면 공백없이 ’,(쉼표)’로 붙여서 등록해주세요. (예시) 금칙어1, 금칙어2,금칙어3</li>
					</ol>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="#" class="btn-lg btn-save" id="save">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
		</form>

		<script src="/static/common/js/biz/cmm/abcmart.cmm.forbiddenword.js<%=_DP_REV%>"> </script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>