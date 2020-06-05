<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
$(function() {
	/*
	 * 사유입력시 200자 이상 체크 
	 */ 
	$("#reasonText").keyup(function(e){
		$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		var content = $(this).val();
		abc.biz.afterservice.reason.stringLengthCheck(content);
	});
	
	<%-- 첨부파일 화면 호출 시 적용되는 구문 --%>
	var atchFileArray = new Array();
	<c:forEach items="${fileList}" var="file">
		atchFileArray.push({
			atchFileSeq  : "${file.atchFileSeq}" ,
			atchFileName : "${file.atchFileName}",
			atchFileUrl  : "${file.atchFileUrl}"
		});
	</c:forEach>

	abc.biz.afterservice.reason.atchCnt = atchFileArray.length; // 파일 업로드 등록/취소 카운트
	abc.biz.afterservice.reason.atchFileAmt = atchFileArray.length; // 파일의 화면 호출 시, 첨부파일 갯수
	abc.biz.afterservice.reason.atchFileArray = atchFileArray;


	for(var i=1; i<=abc.biz.afterservice.reason.maxFileCnt; i++){
		if(i != 1 + Number(abc.biz.afterservice.reason.atchCnt)){
			//가장 마지막 [찾아보기] 버튼을 제외하고 숨긴다.
			$("#inputFileLi" + i).hide();
		}
	}
	
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>A/S 사유</h1>
		</div>
		<div class="window-content">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">A/S사유</h3>
				</div>
				<div class="fr">
					<c:choose>
						<c:when test="${asAcceptContText eq null || asAcceptContText eq ''}">
							<span class="text-limit">(<span id="counter" class="state-num">0</span>/200자)</span>
						</c:when>
						<c:otherwise>
							<span class="text-limit">(<span id="counter" class="state-num">${fn:length(asAcceptContText)}</span>/200자)</span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>A/S사유</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">상품명</th>
						<td>${prdtName}</span></td>
					</tr>
					<tr>
						<th scope="row">A/S사유</th>
						<td class="input">
							<span class="text">${asGbnCode} > ${asRsnCode}</span>
							<textarea id="reasonText" class="ui-textarea" title="사유 입력"> ${asAcceptContText}</textarea>
						</td>
					</tr>
					<c:choose>
						<c:when test="${adminAcceptYn eq 'Y'}">
						</c:when>
						<c:otherwise>
							<tr>
								<th scope="row">첨부파일</th>
								<td class="input">
									<ul class="attach-file-list">
										<c:choose>
												<c:when test="${fileList[0].atchFileSeq eq null}">
													<li id="inputFileLi0">첨부파일이 없습니다.</li>
												</c:when>
												<c:otherwise>
												<c:forEach var="list" end="${AS_FILE_MAX_CNT}" items="${fileList}" varStatus="status">
													<c:if test="${!empty list.atchFileName}">
														<li id="${list.atchFileSeq}" name="attachFileName${list.atchFileSeq}">
															<input type="hidden" name="downLoadFileName" value="${list.atchFileName}"/>
															<input type="hidden" name="atchFilePathText" value="${list.atchFilePathText}"/>
															<input type="hidden" name="atchFileUrl" value="${list.atchFileUrl}"/>
															<a href="javascript:void(0);" name="popupImage" >${list.atchFileName}</a>
														</li>
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</ul>
								</td>
							</tr>
					</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" id="reg" class="btn-normal btn-link">확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script>
	$(document).ready(function(){
		// 접수상세 내용 확인시 수령완료 이상 되면 READONLY 
		var asStatCode = '${asStatCode}';
		if(Number(asStatCode) > Number(abc.biz.afterservice.constCode.asStatCodeReceiveFinish)){
			$('#reasonText').attr("readonly",true);
		}else{
			$('#reasonText').prop("readonly",false);
		}
	}); 
</script>
</html>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.reason.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/afterservice/abcmart.afterservice.constCode.js<%=_DP_REV%>"></script>