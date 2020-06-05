<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>배치 업무 등록</h1>
		</div>
		<div class="window-content">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">기본정보</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>기본정보</caption>
				<colgroup>
					<col style="width: 135px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">프로젝트명</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="프로젝트 선택">
							<c:forEach items="${siteList}" var="site" varStatus="status">
								<option value="${site.siteNo}">${site.siteName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">업무 그룹</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="업무 그룹 선택">
							<c:forEach items="${jobGroupList}" var="code" varStatus="status">
								<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">업무명</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="업무명 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">업무 설명</th>
						<td class="input">
							<textarea class="ui-textarea" title="업무 설명 입력"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
			<!-- S : tbl-desc-wrap -->
<!-- 			<div class="tbl-desc-wrap"> -->
<!-- 				<div class="fl"> -->
<!-- 					<a href="#" class="btn-normal btn-del">삭제</a> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<!-- E : tbl-desc-wrap -->
			<!-- E : 20190117 추가  -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="#" class="btn-normal btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
</html>