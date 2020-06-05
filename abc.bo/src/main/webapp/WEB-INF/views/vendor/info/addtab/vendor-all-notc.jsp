<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	<%-- CKEDITOR 셋팅--%>
	CKEDITOR.replace("allNotcContText", {
		width:"100%",
		height:"300px"
	});
</script>

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">상품전체공지 정보 </h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>상품전체공지 정보 </caption>
		<colgroup>
			<col style="width: 160px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">상품 전체 공지 전시여부</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="allNotcDispY" name="allNotcDispYn" type="radio" value="N" <c:if test="${vendorInfo.allNotcDispYn == 'N'}">checked="checked"</c:if> >
								<label for="allNotcDispY">전시안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="allNotcDispN" name="allNotcDispYn" type="radio" value="Y" <c:if test="${(vendorInfo.allNotcDispYn == 'Y') or (empty vendorInfo.allNotcDispYn)}">checked="checked"</c:if>>
								<label for="allNotcDispN">전시</label>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<tr>
				<th scope="row">상품 전체 공지 전시 기간</th>
				<td class="input">
					<!-- S : term-date-wrap -->
					<span class="term-date-wrap">
						<span class="date-box">
							<input type="hidden" name="allNotcStartYmd">
							<c:choose>
								<c:when test="${!empty vendorInfo.allNotcStartYmd}">
									<fmt:parseDate value="${vendorInfo.allNotcStartYmd}" var="allNotcStartYmdDate" pattern="${Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS}"/>
									<input type="text" data-role="datepicker" id="iAllNotcStartYmd" class="ui-cal js-ui-cal" title="시작일 선택" value='<fmt:formatDate value="${allNotcStartYmdDate}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>'>
								</c:when>
								<c:otherwise>
									<input type="text" data-role="datepicker" id="iAllNotcStartYmd" class="ui-cal js-ui-cal" title="시작일 선택" >
								</c:otherwise>
							</c:choose>
						</span>
						<span class="text">~</span>
						<span class="date-box">
							<input type="hidden" name="allNotcEndYmd">
							<c:choose>
								<c:when test="${!empty vendorInfo.allNotcEndYmd}">
									<fmt:parseDate value="${vendorInfo.allNotcEndYmd}" var="allNotcEndYmdDate" pattern="${Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS}"/>
									<input type="text" data-role="datepicker" id="iAllNotcEndYmd" class="ui-cal js-ui-cal" title="종료일 선택" value='<fmt:formatDate value="${allNotcEndYmdDate}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>'>
								</c:when>
								<c:otherwise>
									<input type="text" data-role="datepicker" id="iAllNotcEndYmd" class="ui-cal js-ui-cal" title="시작일 선택" >
								</c:otherwise>
							</c:choose>
						</span>
					</span>
					<!-- E : term-date-wrap -->
				</td>
			</tr>
			<tr>
				<th scope="row">상품전체 공지 내용</th>
				<td class="input">
					<!-- S : editor-wrap -->
					<div class="editor-wrap">
						<textarea rows="30" cols="100" id="allNotcContText" name="allNotcContText"><c:out value="${vendorInfo.allNotcContText}"/></textarea>
					</div>
					<!-- E : editor-wrap -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
