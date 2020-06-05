<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(document).ready(function(){
		//코드그룹 그리드 초기화
		abc.biz.system.commoncode.initGroupSheet();
		//상위,하위코드 그리드 초기화
		abc.biz.system.commoncode.initUpDownCodeGridSheet();
		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			var selected = $("#pageCount option:selected").val();
			$("#pageCount").val(selected).prop("selected", true);
			abc.biz.system.commoncode.doAction("readUpDownCode");
		});
		
		//코드그룹 삭제
		$("#deleteCodeGroupButton").off().on('click', function(){
			var systemCodeYn = $("#systemCodeYn").val();
			if (systemCodeYn == abc.consts.BOOLEAN_TRUE) {
				alert("시스템 코드입니다 확인해주세요.");
			} else {
				abc.biz.system.commoncode.doAction('removeCheck');
			}
		
		});
		
		//정렬순서 숫자만 입력
		$("#sortSeq").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		//코드그룹 등록 버튼
		$("#createCodeGroupButton").off().on('click', function(){
			codeGroupGridSheet.SelectCell(1,1);
			abc.biz.system.commoncode.doAction('createCodeGroup');
		});
		//코드 그룹 상세 저장 버튼
		$("#saveCodeGroupButton").off().on('click', function(){
			abc.biz.system.commoncode.doAction('saveCheck');
		});
		
		//상위코드 등록버튼
		$("#createUpCodeButton").off().on('click', function(){
			abc.biz.system.commoncode.doAction('createUpCodeForm');
		});
		
		//하위코드 등록버튼
		$("#downCodeCheck").click(function(){
			abc.biz.system.commoncode.doAction('createDownCodeForm');
		});
		
		//상위,하위코드 수정 버튼
		$("#updateUpDownCodeButton").off().on('click', function(){
			abc.biz.system.commoncode.doAction('updateUpDownCodeGrid'); 
		});
		
		//코드그룹명 특수문자 제거
		/*$("#codeName").keyup(function(){
			$("#codeName").val(abc.text.validateStringSignRemove($(this).val()));
		});*/
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		
	});
	
	//상위,하위 코드 그리드 내에서 수정뒤에 호출
	function upDownCodeGridSheet_OnSaveEnd(code, msg, StCode, StMsg, Response){
		if(StCode == "200"){
			alert("저장되었습니다.");
		}else{
			alert("에러가 발생하였습니다. - " + msg);
		}
		abc.biz.system.commoncode.doAction("readUpDownCode");
 	}
	
	<%-- 코드그룹 그리드 Click 이벤트 --%>
	function codeGroupGridSheet_OnClick(Row, Col, Value) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (codeGroupGridSheet.ColSaveName(Col) == "codeField" || codeGroupGridSheet.ColSaveName(Col) == "codeName") {
				var codeField = codeGroupGridSheet.GetCellValue(Row, "codeField");
				var sortSeq = codeGroupGridSheet.GetCellValue(Row, "sortSeq");
				abc.biz.system.commoncode.readGroupDetail(sortSeq, codeField);
				$("#codeField").val(codeField); 
				abc.biz.system.commoncode.doAction("readUpDownCode");
				
				var saveCheck = $("#saveCheck").val();
				if(saveCheck=="1"){
					$("#saveCheck").val("0");
					abc.biz.system.commoncode.saveShow();
				}
			}
		}
	}
	
	<%-- 상위,하위 코드그리드 Click 이벤트 --%>
	function upDownCodeGridSheet_OnClick(Row, Col, Value) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (upDownCodeGridSheet.ColSaveName(Col) == "codeDtlNo") {
				var codeDtlNo = upDownCodeGridSheet.GetCellValue(Row, "codeDtlNo");
				var upCodeDtlName = upDownCodeGridSheet.GetCellValue(Row, "upCodeDtlName");
				abc.biz.system.commoncode.updateUpDownCodeForm(codeDtlNo,upCodeDtlName);
			}
		}
		if(upDownCodeGridSheet.ColSaveName(Col) == "codeDtlName"){
			if(!(abc.text.allNull(upDownCodeGridSheet.GetCellValue(Row, "upCodeDtlName"))) && Row != 0){
				alert("상위코드인경우 하위코드명 셀에서 수정 불가능 합니다.");
				upDownCodeGridSheet.SelectCell(Row, Col-1);
			}
		}else if(upDownCodeGridSheet.ColSaveName(Col) == "upCodeDtlName"){
			if(!(abc.text.allNull(upDownCodeGridSheet.GetCellValue(Row, "codeDtlName"))) && Row != 0){
				alert("하위코드인경우 상위코드명 셀에서 수정 불가능 합니다.");
				upDownCodeGridSheet.SelectCell(Row, Col+1);
			}
		}
	}
	
	<%-- Grid1 DataSearch End 이벤트 --%>
	function codeGroupGridSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response) {
		if($("#firstCheck").val() == "0"){
			var codeField = $("#codeField").val();
			var sortSeq = codeGroupGridSheet.FindText("codeField", codeField);
			abc.biz.system.commoncode.readGroupDetail(sortSeq, codeField);
			abc.biz.system.commoncode.doAction("readUpDownCode"); 
			codeGroupGridSheet.SetSelectRow(sortSeq);
			codeGroupGridSheet.SetSelectCol(2);
			$("#saveCheck").val("0");
			abc.biz.system.commoncode.saveShow();
		}else{
			var sortSeq = $("#sortSeq_hidden").val();
			var codeField = codeGroupGridSheet.GetCellValue(sortSeq, "codeField");
			abc.biz.system.commoncode.readGroupDetail(sortSeq, codeField);
			$("#codeField").val(codeField);  
			abc.biz.system.commoncode.doAction("readUpDownCode"); 
			codeGroupGridSheet.SetSelectRow(sortSeq);
			codeGroupGridSheet.SetSelectCol(2);
			$("#saveCheck").val("0");
			abc.biz.system.commoncode.saveShow();
		}
		
	}
	
	<%-- Grid2 DataSearch End 이벤트 --%>
	function upDownCodeGridSheet_OnSearchEnd(){
		if(upDownCodeGridSheet.RowCount()== 0){
			$("#downCodeCheck").hide();
		}else{
			$("#downCodeCheck").show();
		}
	}
	
	//수정 validate
	function upDownCodeGridSheet_OnEditValidation(row, col, value) {
		if (upDownCodeGridSheet.ColSaveName(col) == "sortSeq" ) {
			if (abc.text.isBlank(value)) {
				alert("정렬순서를 입력해 주십시요.");
				upDownCodeGridSheet.ValidateFail(1);
				return ;
			}
			if (value > abc.consts.INT_MAX_VALUE) {
				alert("정렬순서가 최대값을 초과하였습니다.");
				upDownCodeGridSheet.ValidateFail(1);
				return ;
			}
		}
		
		if(upDownCodeGridSheet.ColSaveName(col) == "codeDtlName" || upDownCodeGridSheet.ColSaveName(col) == "upCodeDtlName"){
			if (abc.text.isBlank(value)) {
				alert("코드명을 입력해 주십시요.");
				upDownCodeGridSheet.ValidateFail(1);
				return ;
			}
			if (abc.text.isLimitLength(value, 100)) {
				alert("코드명이 최대값을 초과하였습니다.");
				upDownCodeGridSheet.ValidateFail(1);
				return ;
			}

		}
		
	}
	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">공통코드관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>시스템관리</li>
						<li>공통코드관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<div class="col-wrap col3-2by1 commmon-code-manage">
			<div class="col">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">코드그룹 목록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a id="createCodeGroupButton" class="btn-sm btn-func">코드그룹 등록</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="codeGroupGrid" style="width:100%; height:1000px;"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
			<div class="col">
				<!-- S : row-wrap -->
				<div class="row-wrap">
					<div class="row">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">코드그룹 정보</h3>
							</div>
							<div class="fr">
								<div class="btn-group">
								</div>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-row -->
						<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
							<table class="tbl-row">
								<caption>코드그룹 정보</caption>
								<colgroup>
									<col style="width: 130px;">
									<col>
									<col style="width: 130px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">
											<span class="th-required">코드그룹번호</span>
										</th>
										<td colspan="3"><input type="text" name="codeField_html" id="codeField_html" maxlength="25" class="ui-input" readonly="readonly" title="코드그룹번호 입력" placeholder="코드그룹번호 입력"></td>
									</tr>
									<tr>
										<th scope="row">
											<span class="th-required">코드그룹명</span>
										</th>
										<td class="input" colspan="3">
											<input type="text" name="codeName" id="codeName" class="ui-input" maxlength="50" title="코드그룹명 입력" placeholder="코드그룹명 입력">
										</td>
									</tr>
									<tr>
										<th scope="row">
											<span class="th-required">구분</span>
										</th>
										<td class="input" colspan="3">
											<span class="ip-text-box">
												<select class="ui-sel" title="구분 선택" name="systemCodeYn" id="systemCodeYn">
													<option value="N">일반</option>
													<option value="Y">시스템</option>
												</select>
												<span class="text">* 시스템 코드는 등록 후 수정, 삭제가 불가합니다.</span>
											</span>
										</td>
									</tr>
									<tr>
										<th scope="row">설명</th>
										<td class="input" colspan="3">
											<textarea class="ui-textarea" name="noteText" id="noteText" maxlength="250" title="설명 입력"></textarea>
										</td>
									</tr>
									<tr>
										<th scope="row">
											<span class="th-required">정렬순서</span>
										</th>
										<td class="input" colspan="3">
											<input type="text" name="sortSeq" id="sortSeq" class="ui-input" title="정렬순서 입력" maxlength="4" placeholder="숫자만 입력">
										</td>
									</tr>
									<tr>
										<th scope="row">
											<span class="th-required">사용여부</span>
										</th>
										<td class="input" colspan="3">
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" id="useYn_y" name="useYn"  value ="Y" checked>
														<label for="useYn_y">사용</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" id="useYn_n" name="useYn" value ="N" >
														<label for="useYn_n">사용안함</label>
													</span>
												</li>
											</ul>
										</td>
									</tr>
									<tr id="saveCheckValue1">
										<th scope="row">작성자</th>
										<td name="rgsterName"><a href="javascript:void(0)" id="rgsterName" class="adminPopTag" style="text-decoration: underline;"></a></td>
										<th scope="row">작성일시</th>
										<td name="rgstDtm" id="rgstDtm"></td>
									</tr>
									<tr id="saveCheckValue2">
										<th scope="row">수정자</th>
										<td name="moderName"><a href="javascript:void(0)" id="moderName" class="adminPopTag" style="text-decoration: underline;"></a></td>
										<th scope="row">수정일시</th>
										<td name="modDtm" id="modDtm"></td>
									</tr>
								</tbody>
							</table>
						<!-- E : tbl-row -->
						<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
						<!-- S : tbl-desc-wrap -->
						<div class="tbl-desc-wrap" id="deleteHide">
							<div class="fl">
								<a href="javascript:void(0)" id="deleteCodeGroupButton" class="btn-normal btn-del">삭제</a>
							</div>
						</div>
						<!-- E : tbl-desc-wrap -->
						<!-- E : 20190117 추가  -->

						<!-- S : content-bottom -->
						<div class="content-bottom">
							<a href="javascript:void(0)" id="saveCodeGroupButton" class="btn-lg btn-save">저장</a>
						</div>
						<!-- E : content-bottom -->
					</div>

					<div class="row"><!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">하위코드 정보</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-controller -->
						<div class="tbl-controller">
							<div class="fl">
								<!-- S : opt-group -->
								<span class="opt-group">
									<label class="title" for="pageCount">목록개수</label>
									<select class="ui-sel" id="pageCount" name="pageCount">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>

							<div class="fr" id="upDownBtnArea">
								<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
								<!-- DESC : html/common/BO-CM-12002.html 파일 확인 해주세요. -->
								<a href="javascript:void(0)" id="createUpCodeButton" class="btn-sm btn-link">상위코드 등록</a>
								<!-- DESC : html/common/BO-CM-12003.html 파일 확인 해주세요. -->
								<a href="javascript:void(0)" id="downCodeCheck" class="btn-sm btn-link">하위코드 등록</a>
								<!-- E : 20190114 수정 -->
							</div>
						</div>
						
						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
							<div id="upDownCodeGrid" style="width:100%; height:450px;"></div>
						</div>
						<!-- E : ibsheet-wrap -->

						<!-- S : content-bottom -->
						<div class="content-bottom">
							<a href="javascript:void(0)" id="updateUpDownCodeButton" class="btn-lg btn-save">저장</a>
						</div>
						<!-- E : content-bottom -->
					</div>
				</div>
				<!-- E : row-wrap -->
			</div>
		</div>
	</div>
</div>


<input type="hidden" id="codeField">
</form>
<input type="hidden" id="validate_hidden">
<input type="hidden" id="sortSeq_hidden">
<input type="hidden" id="saveCheck">
<input type="hidden" id="firstCheck">
<form id="detailSearch" name="detailSearch" method="post" onsubmit="return false;">	</form>
		
<script src="/static/common/js/biz/system/abcmart.system.commoncode.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
