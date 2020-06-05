<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품전체 공지 등록</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">상품전체공지 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<form id="detail-form">
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>상품전체공지 정보</caption>
					<colgroup>
						<col style="width: 160px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">상품 전체 공지 전시 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay01" name="dispYn" type="radio" value="N" checked>
											<label for="radioDisplay01">전시안함</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02" name="dispYn" type="radio" value="Y">
											<label for="radioDisplay02">전시</label>
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
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">상품 전체 공지 내용</th>
							<td class="input">
								<!-- S : editor-wrap -->
								<div class="editor-wrap" id="notc-cont-text">
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
			</form>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save" id="save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.all.notice.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>