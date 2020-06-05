<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>템플릿 검색</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">템플릿 검색</h3>
				</div>
			</div>
			
			<form name="searchForm">
			<input type="hidden" value="Y" name="useYn">
			<input type="hidden" value="${dpDisplayTemplate.menuType }" id="menuType">
			<input type="hidden" value="${dpDisplayTemplate.callback }" id="callback">
			<input type="hidden" value="${dpDisplayTemplate.chnnlNo }" name="chnnlNo">
			<input type="hidden" value="${dpDisplayTemplate.deviceCode }" name="deviceCode">
			<c:forEach var="code" items="${codeList.TMPL_TYPE_CODE}" varStatus="status">
				<c:if test="${code.addInfo1 eq 'C' && dpDisplayTemplate.menuType eq 'C'}">
					<input name="tmplTypeCodeArr" type="hidden" value="${code.codeDtlNo }"/>
				</c:if>
				<c:if test="${code.addInfo1 ne 'C' && dpDisplayTemplate.menuType ne 'C'}">
					<input name="tmplTypeCodeArr" type="hidden" value="${code.codeDtlNo }"/>
				</c:if>
			</c:forEach>
			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>템플릿 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">검색어</th>
								<td class="input">
									<!-- TODO : Search Drop down 기능 협의 후 수정 -->
									<!-- S : search-dropdown-box -->
									<!-- <span class="search-dropdown-box">
										<select class="search-dropdown" title="검색어 자동완성" data-placeholder="템플릿명 혹은 템플릿 코드 입력"></select>
									</span> -->
									<div class="opt-keyword-box">
										<select class="ui-sel" title="검색어 타입 선택" name="keywordType">
											<option value="01">템플릿명</option>
											<option value="02">템플릿코드</option>
											<option value="03">작성자이름</option>
											<option value="04">수정자이름</option>
											<option value="05">작성자ID</option>
											<option value="06">수정자ID</option>
										</select>
										<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="keyword">
									</div>
									<!-- E : search-dropdown-box -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fr">
							<button type="submit" class="btn-normal btn-func" id="btn-search">검색</button>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->
			</form>
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">템플릿 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selTermsModule">목록개수</label>
						<select class="ui-sel" id="selTermsModule">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="templateSheet" style="width:100%; height:429px;">
					ibsheet grid영역(div 삭제 필요)
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
</html>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.template.pop.js<%=_DP_REV%>"></script>		
<%@include file="/WEB-INF/views/common/footer.jsp" %>