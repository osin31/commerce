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
						<h2 class="page-title">상품정보고시관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
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
						<h3 class="content-title">상품정보고시 상세</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<form id="infoForm">
				<input type="hidden" name="prdtInfoNotcSeq" value="${info.prdtInfoNotcSeq}">
				<input type="hidden" name="itemCode" value="${info.itemCode}">
				<table class="tbl-row">
					<caption>상품정보고시 정보 등록</caption>
					<colgroup>
						<col style="width:160px;">
						<col>
						<col style="width:160px;">
						<col>
					</colgroup>
					<tbody>
						<tr>							
							<th scope="row">품목명 </th>
							<td>
								<c:forEach var="code" items="${itemCode}">
									<c:if test="${code.codeDtlNo == info.itemCode}">
										${code.codeDtlName}
									</c:if>
								</c:forEach>
							</td>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse01" name="useYn" type="radio" value="Y" ${info.useYn == 'Y' ? 'checked' : ''}>
											<label for="radioUse01">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse02" name="useYn" type="radio" value="N" ${info.useYn == 'N' || info.useYn == null ? 'checked' : ''}>
											<label for="radioUse02">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">항목명</span>
							</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" title="항목명 선택" name="prdtInfoNotcCode" id="prdtInfoNotcCode">
										<option value>항목명</option>
										<c:forEach var="code" items="${prdtInfoNotcCode}">
											<option value="${code.codeDtlNo}" ${code.codeDtlNo == info.prdtInfoNotcCode ? 'selected' : ''}>${code.codeDtlName}</option>
										</c:forEach>
									</select>
									<input type="text" class="ui-input" id="infoNotcName" name="infoNotcName" placeholder="항목명 직접입력" title="항목명 직접입력" value="${info.infoNotcName}" maxlength="100">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<th scope="row">
								<span class="th-required">필수여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioNecessary01" name="reqYn" type="radio" value="Y" ${info.reqYn == 'Y' ? 'checked' : ''}>
											<label for="radioNecessary01">필수</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioNecessary02" name="reqYn" type="radio" value="N" ${info.reqYn == 'N' || info.reqYn == null ? 'checked' : ''}>
											<label for="radioNecessary02">비필수</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">노출 우선순위
							</th>
							<td class="input" colspan="3">
								<input type="number" class="ui-input" id="sortSeq" name="sortSeq" title="노출 우선순위 입력" value="${info.sortSeq}">
							</td>
						</tr>
						<tr>
							<th scope="row">항목 상품정보 기본값</th>
							<td class="input" colspan="3">
								<span class="ip-text-box">
									<!-- TODO : 기획 fix 후 제한문구 byte 수정 -->
									<input type="text" class="ui-input text-unit20" name="infoNotcDfltValue" title="항목 상품정보 기본값 입력" value="${info.infoNotcDfltValue}" maxlength="500">
									<span class="text">* 항목 상품정보 입력란의 기본값 설정</span>
								</span>
							</td>
						</tr>
						<tr>
							<th scope="row">항목 상품정보 안내문구</th>
							<td class="input" colspan="3">
								<!-- TODO : 기획 fix 후 제한문구 byte 수정 -->
								<!-- S : msg-wrap -->
								<span class="msg-wrap">
									<span class="msg-box ip-size-lg">
										<textarea class="ui-textarea" name="infoNotcWriteInfo" title="항목 상품정보 안내문구 입력" maxlength="500">${info.infoNotcWriteInfo}</textarea>
										<!-- <span class="text-limit">
											<span class="desc">(<span>0</span>/40 자)</span>
										</span> -->
									</span>
								</span>
								<!-- E : msg-wrap -->

								<ul class="td-text-list">
									<li>* 항목 상품정보 입력 시 필요한 안내문구 노출</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				<!-- E : tbl-row -->

				<!-- S : tbl-row -->
				<c:if test="${not empty info.prdtInfoNotcSeq}">
					<table class="tbl-row">
						<caption>상품정보고시 정보 등록 작성 정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">수정자 </th>
								<td><a href="javascript:abc.adminDetailPopup('${info.moderNo}');">${UtilsMasking.adminName(info.moderId, info.moderName)}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${info.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl"></div>
					<div class="fr">
						<a href="/product/info-notice" class="btn-normal btn-link">목록</a>
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

<script src="/static/common/js/biz/product/abcmart.product.info.notice.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>