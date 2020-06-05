<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
		$(function() { 
		//코드
		abc.biz.delivery.order.vendor.excel.popup.codeCombo = ${codeCombo}; //combo 코드 값

		//초기화
		abc.biz.delivery.order.vendor.excel.popup.init();
		
		// 목록 그리드 초기화
		abc.biz.delivery.order.vendor.excel.popup.initDataListSheet();
		 
		});

	function dataListSheet_OnLoadExcel(result,Code,msg) {
		if (result) {
			alert("엑셀 로딩이 완료되었습니다.");

			var totalRows = dataListSheet.GetTotalRows();  //업로드 전체 건수
			
			//그리드 수정 불가 초기화
			dataListSheet.SetColEditable("logisVndrCode",1);    // 택배사 활성화
			dataListSheet.SetColEditable("waybilNoText",1);   //송장번호 활성화
			
			$('#counter').html(totalRows); //전체 건수
			$("#popupSave").show(); //저장버튼 노출
		} else {
			alert("엑셀 로딩중 오류가 발생하였습니다. \n [ Error Code :" +Code+" \n Error Massage : "+ msg+"]");
			$("#popupSave").hide();
		}
	}

 
function 	dataListSheet_OnChange(Row, Col, Value){
	  var total = dataListSheet.GetTotalRows();
}

</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>엑셀 업로드</h1>
		</div>
		<div class="window-content">
		<form id="gForm" name="gForm">
		<input type="hidden" size="10"  name="callPopupType" id="callPopupType" value="${callPopupType}">


			<!-- S : 엑셀 업로드 팝업 -->
			<!-- S : file-wrap -->
				<!--
			<div class="file-wrap white-box">
				<ul class="file-list">
				 <li>
						<span class="btn-box">
							< !-- DESC : input id / label for 동일하게 맞춰주세요 -- >
							<input type="file" id="inputFile" title="첨부파일 추가">
							<label for="inputFile">찾아보기  ${callPopupType} </label>
						</span>
					</li> 
					<li><span class="subject">파일명.xlsx (000.00 KB)</span>
						<button type="button" class="btn-file-del">
							<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
						</button>
					</li>
				</ul>
			</div>
			-->
			<!-- S : file-wrap -->
<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ol class="tbl-desc-list">
						<p class="pop-desc">
				“엑셀 일괄 발송처리” 시 기존 주문건에 대한 정보가 있을 경우 정보가 덮어쓰기 처리되니, 주의해주시기 바랍니다.
				<br /> 선택판 파일을 엑셀 업로드 진행하시겠습니까?
			</p>
					</ol>
					<div class="fr">
						<a href="javascript:void(0)" id="uploadExcelDown" class="btn-sm btn-func">양식 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->
			
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="popupCancel" name="popupCancel" class="btn-normal btn-del">취소</a>
				<a href="javascript:void(0)" id="popupLoad" name="pupupSave" class="btn-normal btn-link">업로드</a>
			</div>
			<!-- E : window-btn-group -->

			<!-- E : 엑셀 업로드 팝업 -->

			<!-- S : 엑셀 업로드 완료 팝업 -->
			<!-- DESC : 완료 팝업인 경우 노출되는 영역. 임시 div 삭제 필요 -->
			<div style="margin-top: 20px;">
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<ol class="tbl-desc-list">
						<li>* 엑셀 업로드 결과를 확인해주시기 바랍니다. 완료건만 발송처리가 되며, <br /> 실패건은 실패사유를 확인하시어 다시 발송처리해주시기 바랍니다.</li>
						<li>* 엑셀 업로드한 주문상품 건에 대해서는 "발송처리"를 꼭 처리해주시기 바랍니다</li>
					</ol>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : tbl-result-list -->
				<ul class="tbl-result-list">
					<li><span class="opt-name">총 <span class="cnt"  id="counter"  size="30" name="counter">0</span>건</span></li>
					<li><span class="opt-name">완료 <span class="cnt tc-blue"   id="successCnt"  name="successCnt">0</span>건</span></li>
					<li><span class="opt-name">실패 <span class="cnt tc-red"   id="failCnt"  name="failCnt">0</span>건</span></li>
				</ul>
				<!-- E : tbl-result-list -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid"></div>
				</div>
				<input type="hidden" name="param" value="">
				<!-- E : ibsheet-wrap -->

				<!-- S : window-btn-group -->
				<div class="window-btn-group">
					<a href="javascript:void(0)" id="popupSave" name="popupSave" class="btn-normal btn-link" style="display:none;">저장</a>
					<a href="javascript:void(0)" id="popupClose" name="popupClose" class="btn-normal btn-del" style="display:none;">닫기</a>
				</div>
				<!-- E : window-btn-group -->
			</div>
			<!-- E : 엑셀 업로드 완료 팝업 -->
		</div>

		</form>
	</div>
</body>
</html>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.excel.popup.js<%=_DP_REV%>"></script>