<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>기획전 전시 코너 관리</h1>
		</div>
		
		<form id="cornerForm" name="cornerForm">
		<input type="hidden" name="plndpNo" value="${plndpNo}">
		<input type="hidden" name="plndpCornerSeq" value="${corner.plndpCornerSeq}">
		<input type="hidden" name="imageName" value="${corner.imageName}">
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">기획전 전시 코너 관리</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>기획전 전시 코너 관리</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">코너명</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" name="plndpCornerName" title="코너명 입력" placeholder="50자 이내로 입력" maxlength="50" value="${corner.plndpCornerName}">
						</td>
						<th scope="row">
							<span class="th-required">사용여부</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="rdoUseY" name="useYn" value="Y" type="radio" ${empty corner || corner.useYn eq 'Y' ? 'checked' : ''}>
										<label for="rdoUseY">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="rdoUseN" name="useYn" value="N" type="radio" ${corner.useYn eq 'N' ? 'checked' : ''}>
										<label for="rdoUseN">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">전시코너명 노출 유형</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="rdoTitleTypeT" name="cornerNameDispType" type="radio" value="T" ${empty corner || corner.cornerNameDispType eq 'T' ? 'checked' : ''}>
										<label for="rdoTitleTypeT">텍스트</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="rdoTitleTypeI" name="cornerNameDispType" type="radio" value="I" ${corner.cornerNameDispType eq 'I' ? 'checked' : ''}>
										<label for="rdoTitleTypeI">이미지</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="rdoTitleTypeN" name="cornerNameDispType" type="radio" value="N" ${corner.cornerNameDispType eq 'N' ? 'checked' : ''}>
										<label for="rdoTitleTypeN">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<th scope="row">
							<span class="th-required">노출 순서</span>
						</th>
						<td class="input">
							<input type="number" class="ui-input" name="sortSeq" title="노출 순서 입력" value="${corner.sortSeq}">
						</td>
					</tr>
					<!-- S : 전시코너명 노출 유형 > 이미지 선택시 노출 -->
					<tr class="image-upload-area" ${corner.cornerNameDispType eq 'I' ? '' : 'style="display: none;"'}>
						<th scope="row">
							<span class="th-required">전시코너명 이미지</span>
							<div>권장사이즈 1200*100 (최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input" colspan="3">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<input type="file" id="imageFile" name="imageFile" title="첨부파일 추가">
											<label for="imageFile">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="#" class="subject">${corner.imageName}</a>
										<button type="button" class="btn-file-del" style="display: none;">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="altrnText" value="${corner.altrnText}">
								</div>
								<div class="img-wrap">
									<c:if test="${corner.imageUrl != null}">
										<img alt="${corner.altrnText}" src="${corner.imageUrl}">
									</c:if>									
								</div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<!-- E : 전시코너명 노출 유형 > 이미지 선택시 노출 -->
					<tr>
						<th scope="row">
							<span class="th-required">코너설명</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" class="ui-input" title="코너명 입력" placeholder="100자 이내로 입력" maxlength="100" name="cornerDescText" value="${corner.cornerDescText}">
						</td>
					</tr>
					<tr>
						<th scope="row">상품목록<br />PC 템플릿 선택</th>
						<td class="input" colspan="3">
							<!-- S : prod-template-list -->
							<ul class="prod-template-list">
								<c:forEach items="${pcDispTypeCode}" var="code" varStatus="status">
									<li>
										<span class="ui-rdo">
											<input id="rdoPCTemplate${status.count}" name="pcDispTypeCode" type="radio" value="${code.codeDtlNo}" ${status.count == 1 || corner.pcDispTypeCode eq code.codeDtlNo ? 'checked' : ''}>
											<label for="rdoPCTemplate${status.count}">${code.codeDtlName}</label>
										</span>
										<span class="template-img">
											<img src="${code.addInfo1}" alt="pc 템플릿 유형${status.count}">
										</span>
									</li>																	
								</c:forEach>
							</ul>
							<!-- E : prod-template-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">상품목록<br />Mobile 템플릿 선택</th>
						<td class="input" colspan="3">
							<!-- S : prod-template-list -->
							<ul class="prod-template-list">
								<c:forEach items="${mobileDispTypeCode}" var="code" varStatus="status">
									<li>
										<span class="ui-rdo">
											<input id="rdoMoTemplate${status.count}" name="mobileDispTypeCode" type="radio" value="${code.codeDtlNo}" ${status.count == 1 || corner.mobileDispTypeCode eq code.codeDtlNo ? 'checked' : ''}>
											<label for="rdoMoTemplate${status.count}">${code.codeDtlName}</label>
										</span>
										<span class="template-img">
											<img src="${code.addInfo1}" alt="mobile 템플릿 유형${status.count}">
										</span>
									</li>								
								</c:forEach>
							</ul>
							<!-- E : prod-template-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="saveBtn">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
		</form>
	</div>
	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.corner.js<%=_DP_REV%>"></script>
</body>
</html>