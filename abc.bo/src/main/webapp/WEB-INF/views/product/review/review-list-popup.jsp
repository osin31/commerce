<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script>
(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.review.popup");
	
	/**
	 * 초기화
	 */
	 _object.init = function(){
		_object.event();
	}
	
	/**
	 * 이벤트
	 */
	_object.event = function(){
		
		// 상담유형 선택 시
		$("#cnsl-type-code").off().on('change', function(f) {
			if($(this).val() == "") {
				$('#cnsl-type-dtl-code').hide();
				abc.getCounselScriptTitle("", "", "counsel-script-list");
			} else {
				$('#cnsl-type-dtl-code').show();
				abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $(this).val(), "cnsl-type-dtl-code");
			}
		});

		// 상담분류 선택 시
		$("#cnsl-type-dtl-code").off().on('change', function(f) {
			abc.getCounselScriptTitle($("#cnsl-type-code").val(), $(this).val(), "counsel-script-list");
		});
		
		// 스크립트제목 선택 시
		$("#counsel-script-list").off().on('change', function(f) {
			abc.getCounselScript($(this).val(), "aswr-cont-text");
		});
		
		// 상담분류 조회
		abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#cnsl-type-code").val(), "cnsl-type-dtl-code");
		
		//선택 일괄답변
		$('#save').click(function() {
			if(!$('#aswr-cont-text').val()) { alert("내용을 입력해주세요."); return false; }
			
			var callback = abc.param.getParams().callback;
			var result = null;
			
			if(callback) {
				callback = "window.opener." + callback;
				result = new Function("return " + callback)();
			}
			
			// callback 실행
			if(typeof result === "function") {
				result();
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});
		
	}
	
	$(function() {
		_object.init();
	});
	
	
})();
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>답변등록</h1>
		</div>
		<div class="window-content">
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>답변등록</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">답변 스크립트 선택</th>
						<td class="input" colspan="3">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select id="cnsl-type-code" name="cnslTypeCode" class="ui-sel" title="상담유형 선택">
									<option value="">직접입력</option>
									<c:forEach var="item" items="${cnslTypeCode}">
										<c:if test="${item.codeDtlNo eq CommonCode.CNSL_TYPE_CODE_PRODUCT_REVIEW }">
											<option value="${item.codeDtlNo}" selected>${item.codeDtlName}</option>
										</c:if>
									</c:forEach>
								</select>
								<select id="cnsl-type-dtl-code" name="cnslTypeDtlCode" class="ui-sel" title="상담분류 선택">
									<option value="">상담분류</option>
								</select>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">답변내용</th>
						<td class="input" colspan="3">
							<select id="counsel-script-list" name="counselScriptList" class="ui-sel" title="스크립트제목 선택">
								<option>스크립트제목</option>
							</select>
							<textarea class="ui-textarea" title="답변내용 입력" name="aswrContText" id="aswr-cont-text"></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">확인자</th>
						<td>-</td>
						<th scope="row">확인일시</th>
						<td>-</td>
					</tr>
					<tr>
						<th scope="row">최종 수정자</th>
						<td>-</td>
						<th scope="row">최종 수정일시</th>
						<td>-</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="save" class="btn-normal btn-link">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<%@include file="/WEB-INF/views/common/footer.jsp" %>