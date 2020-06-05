<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		
		abc.biz.vendor.email.event();
		
		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("emailContText", {
			width:"100%",
			height:"100%"
		});
		
	});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>메일보내기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fr">
					<div class="btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="javascript:void(0);" id="vendorEmailReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="vendorEmailForm" name="vendorEmailForm">
			<input type="hidden" name="tmsGubun" id="tmsGubun" value="${param.tmsGubun}">
			<input type="hidden" name="recvEmailAddrTextCount" id="recvEmailAddrTextCount" value="0">
			
			<table class="tbl-row">
				<caption>메일보내기</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">수신대상</th>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="rcvrUl">
								<c:choose>
									<c:when test="${param.tmsGubun == 'ALL'}">
										<li>
										<span class="subject">거래중인 입점사 전체 발송 (${vendorCount})</span>
										</li>
										
										<li id="receiveTarget">
										</li>
									</c:when>
									<c:otherwise>
										<c:forEach items="${vendorInfoList}" var="vendorInfo" varStatus="status">
											<li>
												<input type="hidden" name="vndrNo" id="vndrNo" value="${vendorInfo.vndrNo}" >
												<span class="subject">${vendorInfo.vndrName} _${vendorInfo.vndrNo}</span>
												<button type="button" name="${vendorInfo.vndrNo}" class="btn-item-del" id="rcvrDel_${status.count}">
													<span class="ico ico-item-del"><span class="offscreen">수신대상 삭제</span>
												</button>
											</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">대상지정</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio"  name="radioAnswerModule"  id="radioAnswer01"  value="allVendor" checked>
										<label for="radioAnswer01">모든 담당자</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio"  name="radioAnswerModule"  id="radioAnswer02"  value="repVendor">
										<label for="radioAnswer02">대표 담당자만</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">메일 제목</span>
						</th>
						<td class="input">
							<input type="text" id="emailTitleText" name="emailTitleText" class="ui-input" title="메일제목 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">내용</span>
						</th>
						<td class="input">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea rows="10" cols="100" name="emailContText" id="emailContText" class="w100" title="내용 입력"></textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" id="sendMailBtn" class="btn-normal btn-link">메일 발송</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/vendor/abcmart.vendor.email.js<%=_DP_REV%>"></script>
</body>
</html>