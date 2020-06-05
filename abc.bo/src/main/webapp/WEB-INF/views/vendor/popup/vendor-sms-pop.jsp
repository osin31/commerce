<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		abc.biz.vendor.tms.event();
	});
</script>

<body class="window-body">
	<div class="window-wrap">
	<form id="vendorTmsForm" name="vendorTmsForm">
	<input type="hidden" name="tmsGubun" id="tmsGubun" value="${param.tmsGubun}">
		<div class="window-title">
			<h1>SMS보내기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fr">
					<div class="btn-group">
						<a href="javascript:void(0);" id="vendorTmsReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>SMS보내기</caption>
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
										</li>
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
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio"  name="radioAnswerModule"  id="radioAnswer03"  value="toMe">
										<label for="radioAnswer03">나에게  TEST 발송</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!-- DESC : 20190308 삭제 // 받는 휴대폰번호 영역 삭제 -->
					<tr>
						<th scope="row">
							<span class="th-required">보내는 번호</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" name="inputRprsntTelNoText"  id="inputRprsntTelNoText" value="15889667" title="보내는 번호 입력" placeholder="- 없이 입력" maxlength="11" readonly="readonly">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">제목</span>
						</th>
						<td class="input">
							<input type="text" name="mesgContextTitle" class="ui-input"  id ="mesgContextTitle" value="[A-RT]" title="제목" readonly>
						</td>
					</tr>
					<!-- S : 20190308 수정 // 템플릿 내용 영역 수정 -->
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 내용</span>
						</th>
						<td class="input">
							<!-- S : msg-wrap -->
							<span class="msg-wrap">
								<span class="msg-box">
									<textarea id="mesgContText" name="mesgContText" class="ui-textarea" title="템플릿 내용 입력"></textarea>
									<span class="text-limit" id="text-limit">
										<span class="desc">(</span>
										<input type="text" id="mesgContTextByteLength" name="mesgContTextByteLength" class="ui-input num-unit100" title="입력 글자 Byte">

										<!-- DESC : SMS선택시 공백포함 80 byte, LMS선택시 공백포함 2000 byte -->
										<span class="desc">Byte/<span id="mesgContTextMaxByte" class="text">80</span> Byte)</span>
									</span>
								</span>
							</span>
							<!-- E : msg-wrap -->
						</td>
					</tr>
					<!-- E : 20190308 수정 // 템플릿 내용 영역 수정 -->
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<!-- <li>* 받는 번호에 여러 개의 전화번호를 직접 입력할 수 있습니다. (전화번호 입력 후 ',' 를 입력하면 전화번호를 추가할 수 있습니다.)</li> -->
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="sendTextMsgBtn" class="btn-normal btn-func">발송</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
		</form>
	</div>
</body>
<script src="/static/common/js/biz/vendor/abcmart.vendor.tms.js<%=_DP_REV%>"></script>
</html>