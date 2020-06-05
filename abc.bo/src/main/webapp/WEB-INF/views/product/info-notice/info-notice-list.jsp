<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function(){
	abc.biz.product.info.codeCombo = ${codeCombo};
})
</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상품정보고시관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>상품관리</li>
								<li>상품기준 정보관리</li>
								<li>상품정보고시관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품정보고시 정보 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="infoForm">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>상품정보고시 정보 검색</caption>
							<colgroup>
								<col style="width:15%;">
								<col>
								<col style="width:79px;">
								<col style="width:15%;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">품목명 선택</th>
									<td class="input">
										<select id="itemCode" class="ui-sel" title="품목명 선택" name="itemCode">
											<c:forEach var="code" items="${itemCode}">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioUse01" name="useYn" type="radio" value checked>
													<label for="radioUse01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioUse02" name="useYn" type="radio" value="Y">
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioUse03" name="useYn" type="radio" value="N">
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">필수여부</th>
									<td class="input" colspan="4">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioNecessary01" name="reqYn" type="radio" value checked>
													<label for="radioNecessary01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioNecessary02" name="reqYn" type="radio" value="Y">
													<label for="radioNecessary02">예</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioNecessary03" name="reqYn" type="radio" value="N">
													<label for="radioNecessary03">아니오</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
							</tbody>
						</table>

						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="refreshBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->


				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품정보고시 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount" name="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
							<a href="#" class="btn-sm btn-func" id="saveBtn">변경 적용</a>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<input type="hidden" name="selectedItemCode" />
						<a href="#" class="btn-sm btn-link" id="registBtn">등록</a>
					</div>
				</div>
				</form>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="infoSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/product/abcmart.product.info.notice.list.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>