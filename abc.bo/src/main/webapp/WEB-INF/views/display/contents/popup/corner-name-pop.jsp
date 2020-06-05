<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>${data.contTypeCodeName }</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">${data.contTypeCodeName }</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<form name="frm">
			
			<input type="hidden" name="contTypeCode" value="${data.contTypeCode }" />
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>${data.contTypeCodeName }</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<%-- S: 공통영역 --%>
					<tr>
						<th scope="row">
							<span class="th-required">전시여부</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay01" name="dispYn" type="radio" value="Y" ${(dpCategoryCornerName.dispYn eq 'Y') or (empty dpCategoryCornerName.dispYn) ? 'checked' : ''}>
										<label for="radioDisplay01">전시</label>
									</span>

									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<span class="date-box">
											<fmt:formatDate value="${dpCategoryCornerName.dispStartYmd}" pattern="yyyy.MM.dd" var="dispStartYmdDot"/>
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dispStartYmd" value="${dispStartYmdDot }">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<fmt:formatDate value="${dpCategoryCornerName.dispEndYmd}" pattern="yyyy.MM.dd" var="dispEndYmdDot"/>
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dispEndYmd" value="${dispEndYmdDot }">
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay02" name="dispYn" type="radio" value="N" ${dpCategoryCornerName.dispYn eq 'N' ? 'checked' : ''}>
										<label for="radioDisplay02">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">노출순서</span>
						</th>
						<td class="input">
							<input type="number" class="ui-input" title="노출순서 입력" name="sortSeq">
						</td>
					</tr>
					<%-- E: 공통영역 --%>
					
					<%-- 전시코너명(텍스트) --%>
					<c:if test="${data.contTypeCode eq 'T'}">
					<tr>
						<th scope="row">
							<span class="th-required">전시코너명</span>
						</th>
						<td class="input">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="전시코너명 입력" placeholder="20자 이내로 입력" name="dispCornerName" value="${dpCategoryCornerName.dispCornerName }">
						</td>
					</tr>
					</c:if>
					<%-- 전시코너명(이미지) --%>
					<c:if test="${data.contTypeCode eq 'I'}">
					<tr>
						<th scope="row">
							<span class="th-required">전시코너명</span>
						</th>
						<td class="input">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="전시코너명 입력" placeholder="20자 이내로 입력" name="dispCornerName" value="${dpCategoryCornerName.dispCornerName }">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이미지</span>
							<div>00*00<br />(최대 000MB까지 등록가능 <br />파일유형 : jpg, gif)</div>
						</th>
						<td class="input">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="hidden" id="imageName" title="첨부파일 추가" name="imageName" value="${dpCategoryCornerName.imageName}">
											<input type="file" id="imageUpload" title="첨부파일 추가" name="imageFile" value="">
											<label for="imageUpload">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" class="subject">${dpCategoryCornerName.imageName}</a>
										<button type="button" class="btn-file-del" style="${dpCategoryCornerName.imageName != null and dpCategoryCornerName.imageName != '' ? '' : 'display: none;' }" id="del-image">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="alt-wrap">
									<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="${dpCategoryCornerName.altrnText }" />
								</div>
								<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="img-wrap" id="area-image-preview">
									<c:if test="${dpCategoryCornerName.imageName != null and dpCategoryCornerName.imageName != ''}">
										<img alt="${dpCategoryCornerName.altrnText}" src="${dpCategoryCornerName.imageUrl}">
									</c:if>
								</div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			</form>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>

	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.contents.corner.name.pop.js<%=_DP_REV%>"></script>
</body>
</html>