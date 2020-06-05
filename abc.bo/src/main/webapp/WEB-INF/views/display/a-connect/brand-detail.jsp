<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">브랜드관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
<!-- 						<button type="button" class="btn-favorites"> -->
<!-- 							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button> -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>브랜드관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">브랜드관리</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>브랜드관리</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">국문브랜드명</th>
							<td>${dpBrand.brandName}</td>
							<th scope="row">영문 브랜드명</th>
							<td>${dpBrand.brandEnName}</td>
						</tr>
						<tr>
							<th scope="row">브랜드 ID</th>
							<td colspan="3">${dpBrand.brandNo}</td>
							<!-- <th scope="row">베스트 여부</th>
							<td>아니오</td> -->
						</tr>
						<tr>
							<th scope="row">브랜드사용여부</th>
							<td>아니오</td>
							<th scope="row">브랜드키워드</th>
							<td>${dpBrand.brandKeyWordText}</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th scope="row">모바일 Hot 브랜드여부</th> -->
<!-- 							<td colspan="3">아니오</td> -->
<!-- 						</tr> -->
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${dpBrand.moderNo}');">${UtilsMasking.adminName(dpBrand.moderId, dpBrand.moderName)}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${dpBrand.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<form id="saveForm" name="saveForm">
							<input type="hidden" name="brandNo" value="${dpBrand.brandNo}">
							<tr>							
								<th scope="row">
									<span class="th-required">A-Connect전시여부</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="aconnectDispYn" id="radioDisplay02" value="Y" ${dpBrand.aconnectDispYn eq 'Y' ? 'checked' : ''}>
												<label for="radioDisplay02">전시</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input type="radio" name="aconnectDispYn" id="radioDisplay03" value="N" ${dpBrand.aconnectDispYn eq 'N' ? 'checked' : ''}>
												<label for="radioDisplay03">전시안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
						</form>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl"></div>
					<div class="fr">
						<a href="/display/a-connect/brand" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="#" class="btn-lg btn-save" id="saveBtn">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.aconnect.brand.detail.js<%=_DP_REV%>"></script
<%@include file="/WEB-INF/views/common/footer.jsp" %>