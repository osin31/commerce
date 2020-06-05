<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		
		abc.biz.cmm.send.management.sendTargetGridSheet();
		abc.biz.cmm.send.management.doActionAppPush('search');
		
		if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_READY
				&& '${messageDetail.sendTrgtMemberCount }' == 0) {
			abc.biz.cmm.send.management.doActionAppPush('delete');
		}
		
		// 수정시 예약발송, 일반발송일 경우
		if('<c:out value="${messageDetail.sendType}" />' === 'R') {
			$('#sendTypeReserv').trigger('click');
		}
		if('<c:out value="${messageDetail.sendGbnType}" />' === 'R') {
			$('#sendGbnTypeRegular').trigger('click');
		}
		if('<c:out value="${messageDetail.pushSendTrgtCode}" />' !== abc.consts.PUSH_SEND_TRGT_CODE_FILEUPLOAD) {
			$('#excelWrap').hide();
		}
		if(abc.text.isBlank('<c:out value="${messageDetail.pushSendTrgtCode}" />')) {
			$('input[name=pushSendTrgtCode]').eq(0).trigger('click');
		}
		
	});
	
	// 테스트 발송 대상자 확인 그리드에서 대상자 삭제 클릭시
	function sendTargetGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if(sendTargetGridSheet.ColSaveName(Col) === 'deleteTarget'){
				if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_PROGRESS) {
					alert('이미 발송 또는 예약발송 진행 중 입니다.');
					return false;
				}else if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_SUCCESS) {
					alert('이미 발송 완료된 메시지입니다.');
					return false;
				}
				
				if(confirm('정말 삭제하시겠습니까?')) {
					var	_params = {};
					_params.pushMesgNo = sendTargetGridSheet.GetRowData(Row).pushMesgNo;
					_params.memberNo   = sendTargetGridSheet.GetRowData(Row).memberNo;
					_params.siteNo	   = $('#siteNo').val();
					
					abc.biz.cmm.send.management.doActionAppPush('delete', _params);
				}
			}
		}
	};
</script>
	<div class="window-wrap">`
		<div class="window-title">
			<h1>PUSH 메시지 발송관리</h1>
		</div>
		<form id="sendManageForm" name="sendManageForm">
		<input type="hidden" id="pushMesgNo" name="pushMesgNo" value="${messageDetail.pushMesgNo }" />
		<input type="hidden" id="siteNo" name="siteNo" value="${messageDetail.siteNo }" />
		<input type="hidden" id="pushIngStatCode" name="pushIngStatCode" value="${messageDetail.pushIngStatCode }" />
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">발송설정</h3>
				</div>
			</div>
			<!-- E : content-header -->
		
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>발송설정</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">메시지 제목</th>
						<td>${messageDetail.pushTitleText }</td>
						<th scope="row">
							<span class="th-required">발송형태</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="sendTypeIm" name="sendType" value="I" type="radio" checked>
										<label for="sendTypeIm">즉시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="sendTypeReserv" name="sendType" value="R" type="radio">
										<label for="sendTypeReserv">예약</label>
									</span>
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap hidden" id="reserveDateForm">
										<span class="date-box">
											<input type="text" data-role="datepicker" name="pushDate" id="pushDate" class="ui-cal js-ui-cal" title="시작일 선택" value="${messageDetail.pushDate }">
										</span>
										<select name="pushHours" class="ui-sel" title="시간 선택">
											<c:forEach begin="0" end="23" step="1" var="hours">
												<option value="<fmt:formatNumber value='${hours}' pattern='00'/>" <c:if test="${hours eq messageDetail.pushHours}">selected</c:if>>
													<c:choose>
														<c:when test="${hours eq messageDetail.pushHours}">
															${messageDetail.pushHours}시</option>
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value='${hours}' pattern='00'/>시</option>	
														</c:otherwise>
													</c:choose>
											</c:forEach>
										</select>
										<select name="pushMin" class="ui-sel" title="분 선택">
											<c:forEach begin="0" end="50" step="10" var="minutes">
												<option value="<fmt:formatNumber value='${minutes}' pattern='00'/>" <c:if test="${minutes eq messageDetail.pushMin}">selected</c:if>>
													<c:choose>
														<c:when test="${minutes eq messageDetail.pushMin}">
															${messageDetail.pushMin}분</option>
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value='${minutes}' pattern='00'/>분</option>
														</c:otherwise>
													</c:choose>
											</c:forEach>
										</select>
									</span>
									<!-- E : term-date-wrap -->
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">설정일시</th>
						<td><fmt:formatDate value="${messageDetail.setupDtm }" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						<th scope="row">발송일시</th>
						<td><fmt:formatDate value="${messageDetail.sendIrDtm }" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">발송 구분</span>
						</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="sendGbnTypeTest" name="sendGbnType" value="T" type="radio" checked>
										<label for="sendGbnTypeTest">테스트 발송</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="sendGbnTypeRegular" name="sendGbnType" value="R" type="radio">
										<label for="sendGbnTypeRegular">일반발송</label>
									</span>
								</li>
							</ul>
							<ul class="td-text-list" name="testGbnTypeArea">
								<li>* 테스트 발송 시에는 10명까지만 발송가능합니다.</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">발송대상 선택</span>
						</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<c:forEach var="codeList" items="${trgtCodeList }" varStatus="status">
									<li <c:if test="${status.index ne 0 }">name="nomalGbnTypeArea" class="hidden"</c:if> >
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioTargetModule${status.count }" name="pushSendTrgtCode" value="${codeList.codeDtlNo }" type="radio" <c:if test="${codeList.codeDtlNo eq messageDetail.pushSendTrgtCode }">checked</c:if>>
											<label for="radioTargetModule${status.count }">${codeList.codeDtlName } <c:if test="${status.index eq 0 }"> (파일업로드)</c:if> </label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- s : 지정사용자(파일업로드) -->
							<div id="excelWrap">
								<a href="#" id="excelFormDown" class="btn-sm btn-func">엑셀 폼 다운로드</a>
								<div class="file-wrap inline">
									<ul class="file-list" id="fileList">
										<li>
											<span class="btn-box">
												<input type="file" name="excelFile" id="excelFile" title="첨부파일 추가">
												<label for="excelFile">찾아보기</label>
											</span>
										</li>
										<li id="fileDisp" class="hidden">
											<a href="javascript:void(0);" class="subject"></a>
											<button type="button" id="fileDelBtn" class="btn-file-del">
												<span class="ico ico-fdel">
													<span class="offscreen">첨부파일 삭제</span>
												</span>
											</button>
										</li>
									</ul>
								</div>
							</div>
							<!-- e : 지정사용자(파일업로드) -->
						</td>
					</tr>
				</tbody>
			</table>

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="targetCheckBtn" class="btn-normal btn-func">대상자 확인</a>
			</div>
			<!-- E : window-btn-group -->


			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">발송대상</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<!-- S : 발송구분 > 테스트 발송인 경우 -->
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap" name="testGbnTypeArea">
				<div id="sendTargetGrid" style="width:100%; height:429px;">
					
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
			<!-- E : 발송구분 > 테스트 발송인 경우 -->

			<!-- S : 발송구분 > 일반발송인 경우 -->
			<!-- S : tbl-row -->
			<table class="tbl-row hidden" name="nomalGbnTypeArea">
				<caption>발송대상</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">대상자 수</th>
						<td class="input">
							<span class="text"><span id="targetCount" class="tc-red tc-bold">
								<c:choose>
									<c:when test="${messageDetail.sendTrgtMemberCount ne null}">${messageDetail.sendTrgtMemberCount }</c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose>
							</span>건(1인 다수 Device 포함)</span>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			<!-- E : 발송구분 > 일반발송인 경우 -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">Push 메시지 전송 이력</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<table class="tbl-col">
				<caption>Push 메시지 전송 이력</caption>
				<colgroup>
					<col style="width: 100px">
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">발송구분</th>
						<th scope="col">진행상태 <br /> (대기 &gt; 진행 &gt; 완료)</th>
						<th scope="col">발송대상</th>
						<th scope="col">전송대기</th>
						<th scope="col">전송완료</th>
						<th scope="col">성공</th>
						<th scope="col">실패</th>
						<th scope="col">발송일시</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${messageDetail.pushIngStatCode eq '10000' }">
							<tr>
								<td class="text-center" colspan="8">전송이력이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="text-center">${messageDetail.sendGbnTypeName }</td>
								<td class="text-center">${messageDetail.pushIngStatCodeName }</td>
								<td class="text-center">${messageDetail.sendTrgtMemberCount }</td>
								<td class="text-center">
									<c:choose>
										<c:when test="${messageDetail.sendType eq 'R' }">${messageDetail.sendTrgtMemberCount }</c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
								</td>
								
								<td class="text-center">${messageDetail.pushSendCount }</td>
								<td class="text-center">${messageHistory.successCount }</td>
								<td class="text-center">${messageHistory.failCount }</td>
								<td class="text-center">
									<fmt:formatDate value="${messageDetail.sendIrDtm }" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
			<c:if test="${messageDetail.pushIngStatCode eq '10000'}">
				<a href="#" id="appPushSend" class="btn-normal btn-save">발송 / 예약발송</a>
			</c:if>	
			<c:if test="${messageDetail.pushIngStatCode eq '10001'}">
				<a href="#" id="cancelSend" class="btn-normal btn-save">발송 취소</a>
			</c:if>
			</div>
			<!-- E : window-btn-group -->
		</div>
		</form>
	</div>
	
<script src="/static/common/js/biz/cmm/abcmart.cmm.apppush.send.management.js<%=_DP_REV%>"></script>	
	
<%@include file="/WEB-INF/views/common/subFooter.jsp" %>	