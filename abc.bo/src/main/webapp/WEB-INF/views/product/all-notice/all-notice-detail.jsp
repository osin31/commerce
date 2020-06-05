<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">상품전체공지관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>상품관리</li>
						<li>상품전체공지관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->
		
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">상품전체공지 설정정보</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>상품전체공지 설정정보</caption>
			<colgroup>
				<col style="width:170px;">
				<col>
				<col style="width:170px;">
				<col>
			</colgroup>
			<tbody>
				<form id="detail-form">
					<input type="hidden" name="vndrPrdtAllNotcSeq" value="${allNotice.vndrPrdtAllNotcSeq }">
					<input type="hidden" name="type" value="${empty allNotice ? Const.CRUD_C : Const.CRUD_U }">
					<c:choose>
						<c:when test="${not empty allNotice }">
							<tr>
								<th scope="row">입점사명</th>
								<td colspan="3">
									${allNotice.vndrName }
								</td>
							</tr>
						</c:when>
						<c:otherwise>
						
						</c:otherwise>
					</c:choose>
					<tr>
						<th scope="row"><span class="th-required">상품 전체 공지 전시여부</span></th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse01" name="dispYn" type="radio" value="N" ${allNotice.dispYn eq 'N' or empty allNotice.dispYn ? 'checked' : ''}>
										<label for="radioUse01">전시안함</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse02" name="dispYn" type="radio" value="Y" ${allNotice.dispYn eq 'Y' ? 'checked' : ''}>
										<label for="radioUse02">전시</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">상품 전체 공지 전시기간</span></th>
						<td class="input" colspan="3">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<span class="date-box">
									<fmt:formatDate value="${allNotice.allNotcStartYmd}" pattern="yyyy.MM.dd" var="allNotcStartYmd"/>
									<input type="text" id="fromDate" name="allNotcStartYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" value="${allNotcStartYmd }">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<fmt:formatDate value="${allNotice.allNotcEndYmd}" pattern="yyyy.MM.dd" var="allNotcEndYmd"/>
									<input type="text" id="toDate" name="allNotcEndYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" value="${allNotcEndYmd }">
								</span>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">제목</span></th>
						<td class="input" colspan="3">
							<input type="text" class="ui-input" title="상품전체공지 제목 입력" name="allNotcTitleText" value="${allNotice.allNotcTitleText }">
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">내용</span></th>
						<td colspan="3">
							<textarea id="all-notc-cont-text" name="allNotcContText" rows="10" cols="80">${allNotice.allNotcContText }</textarea>
						</td>
					</tr>
					<c:if test="${not empty allNotice }">
						<tr>
							<th scope="row">등록자</th>
							<td><a href="javascript:abc.adminDetailPopup('${allNotice.rgsterNo}');" class="link">${UtilsMasking.adminName(allNotice.rgsterId, allNotice.rgsterName) }</a></td>
							<th scope="row">등록일시</th>
							<td><fmt:formatDate value="${allNotice.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${allNotice.moderNo}');" class="link">${UtilsMasking.adminName(allNotice.moderId, allNotice.moderName) }</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${allNotice.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</c:if>
				</form>
			</tbody>
		</table>
		<!-- E : tbl-row -->
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap">
			<!-- <div class="fl">
				<a href="#" class="btn-normal btn-del">삭제</a>
			</div> -->
			<div class="fr">
				<a href="#" class="btn-normal btn-link" id="list">목록</a>
			</div>
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
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.all.notice.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/menu.jsp" %>