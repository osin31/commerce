<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		CKEDITOR.replace("emailContText", {
			width:"100%",
			height:"550px"
		});
		
		<%-- 목록 가기 --%>
		$("#list").click(function(f) {
			abc.biz.system.mailsend.history.getMailHistoryList();
		});

		// 회원 클릭시		
		$("#memberInfo").off().on("click", function() {
			if(abc.text.allNull(this.name)) {
				return false;
			}
			
			abc.memberDetailPopup(this.name);
			
			return false;
		});
		
		// 관리자 클릭시
		$("#adminInfo").off().on("click", function() {
			if(this.name === "SY00000000") {
				return false;
			}
			
			abc,adminDetailPopup(this.name);
			return false;
		});
		
	});
</script>

<!-- S : container -->
<form id="mailHistoryForm" name="mailHistoryForm" method="post" onsubmit="return false;">
	<input type="hidden" id="emailSndrSeq" name="emailSndrSeq" value="<c:out value="${param.emailSndrSeq}"/>">
	<input type="hidden" id="siteNo" name="siteNo" value="<c:out value="${param.siteNo}"/>">
	<input type="hidden" id="rcvrSearchType" name="rcvrSearchType" value="<c:out value="${param.rcvrSearchType}"/>">
	<input type="hidden" id="rcvrSearchText" name="rcvrSearchText" value="<c:out value="${param.rcvrSearchText}"/>">
	<input type="hidden" id="emailTitleText" name="emailTitleText" value="<c:out value="${param.emailTitleText}"/>">
	<input type="hidden" id="dateSearchType" name="dateSearchType" value="<c:out value="${param.dateSearchType}"/>">
	<input type="hidden" id="fromDate" name="fromDate" value="<c:out value="${param.fromDate}"/>">
	<input type="hidden" id="toDate" name="toDate" value="<c:out value="${param.toDate}"/>">
	<input type="hidden" id="adminSendYn" name="adminSendYn" value="<c:out value="${param.adminSendYn}"/>">
	<input type="hidden" id="arrSendYn" name="arrSendYn" value="<c:out value="${param.arrSendYn}"/>">
	<input type="hidden" id="listPageCount" name="listPageCount" value="<c:out value="${param.listPageCount}"/>">
	<input type="hidden" id="listPageNum" name="listPageNum" value="<c:out value="${param.listPageNum}"/>">
	
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">메일 발송 이력</h2>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home">
								<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>시스템 관리</li>
							<li>메일 발송 이력</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">메일 발송 내용</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>메일 발송 내용</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">받는 사람</th>
						<td>
							<c:choose>
								<c:when test="${DATA.memberNo eq null }">
									<a href="#" id="memberInfo" name="${DATA.memberNo }" ><c:out value="${DATA.memberInfo}"/></a>
								</c:when>
								<c:otherwise>
									<a href="#" id="memberInfo" name="${DATA.memberNo }" class="link"><c:out value="${DATA.memberInfo}"/></a>
								</c:otherwise>
							</c:choose>
						</td>	
						<th scope="row">받는 이메일</th>
						<td><c:out value="${DATA.rcvrEmailInfo}"/></td>
					</tr>
					<tr>
						<th scope="row">보낸 사람</th>
						<td>
							<c:choose>
								<c:when test="${DATA.adminNo eq 'SY00000000' }">
									<a href="#" id="adminInfo" name="${DATA.adminNo }" ><c:out value="${DATA.adminInfo}"/></a>
								</c:when>
								<c:otherwise>
									<a href="#" id="adminInfo" name="${DATA.adminNo }" class="link"><c:out value="${DATA.adminInfo}"/></a>
								</c:otherwise>
							</c:choose>
						</td>
						<th scope="row">보낸 이메일</th>
						<td><c:out value="${DATA.sendEmailInfo}"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : mail-wrap -->
			<div class="mail-wrap" class="input">
				<textarea class="input" id="emailContText" name="emailContText" rows="10" cols="80">${DATA.emailContText}</textarea>
			</div>
			<!-- E : mail-wrap -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<%--
				<div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div>
				--%>
				<div class="fr">
					<a href="javascript:void(0);" id="list" class="btn-normal btn-link">목록</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->
		</div>
	</div>
</form>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.mailsend.history.js<%=_DP_REV%>" ></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>