<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/common/subHeader.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){	
		
		<%-- 값 넘어오는지를 확인하여 등록인지 수정인지 결정 --%>
		if(abc.text.allNull("${param.codeDtlNo}")){
			abc.biz.system.commoncode.downCodeBottomHide();
		}else{
			$("input:radio[name='useYn'][value="+"${syCodeDetail.getUseYn()}"+"]").prop('checked', true);
			$("#upCodeDtlNo").val("${syCodeDetail.getUpCodeDtlNo()}");
			var systemCodeYn = '${systemCodeYn}';
			if(systemCodeYn == abc.consts.BOOLEAN_TRUE){
				abc.biz.system.commoncode.upDownCodeSystemHide();
			}
			
		}
		//정렬순서 숫자만 입력되게 하기
		$("#sortSeq").off().on('input', function(event) {
			$(this).val($(this).val().replace(/[^0-9\.]/g, ''));
		});
		//초기화 버튼
		$("#fReset").off().on('click', function(){
			abc.biz.system.commoncode.downFormReload();
		});
		//코드삭제 버튼
		$("#deleteCodeButton").off().on('click', function(){
			abc.biz.system.commoncode.doAction('removeDownCode');
		});
		//코드 저장 버튼
		$("#saveCodeButton").off().on('click', function(){
			abc.biz.system.commoncode.doAction('downCodeCheck');
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});

		
	});
	
</script>
<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1 id="codeStatus">하위코드 수정/상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">하위코드</h2>
				</div>
				<div class="fr">
					<div class="btn-group">
						<a href="javascript:void(0)" id="fReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="downCodeForm" name="downCodeForm" method="post" onsubmit="return false;">
				<table class="tbl-row">
					<caption>하위코드</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">코드그룹</span>
							</th>
							<td class="input"colspan="3">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<span class="text"><input type="hidden" name="codeField" id="codeField" value="${param.codeField}">${param.codeField} &gt;</span>
										<select class="ui-sel" title="코드 선택" id="upCodeDtlNo" name="upCodeDtlNo" >
											<c:forEach items="${downCodeList}" var="list">
												<option value="${list.getCodeDtlNo()}">${list.getCodeDtlName()}</option> 
											</c:forEach> 
										</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">코드번호</span>
							</th>
							<td colspan="3" id = "codeDtlNo_td"><input type="hidden" name="codeDtlNo" id="codeDtlNo" value="${syCodeDetail.getCodeDtlNo()}">${syCodeDetail.getCodeDtlNo()}</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">코드명</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" name="codeDtlName" id="codeDtlName" value="${syCodeDetail.getCodeDtlName()}" maxlength="50" title="코드명 입력" placeholder="코드명 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">설명</th>
							<td class="input" colspan="3">
								<textarea class="ui-textarea" name="noteText" id="noteText" maxlength="250" title="설명 입력">${syCodeDetail.getNoteText()}</textarea>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">정렬순서</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" name="sortSeq" id="sortSeq" title="정렬순서 입력" value="${syCodeDetail.getSortSeq()}" placeholder="숫자만 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">참조값1</th>
							<td class="input">
								<input type="text" name="addInfo1" id="addInfo1" class="ui-input" value="${syCodeDetail.getAddInfo1()}" maxlength="50" title="참조값 입력">
							</td>
							<th scope="row">참조값2</th>
							<td class="input">
								<input type="text" name="addInfo2" id="addInfo2" class="ui-input" value="${syCodeDetail.getAddInfo2()}" maxlength="50" title="참조값 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">참조값3</th>
							<td class="input">
								<input type="text" name="addInfo3" id="addInfo3" class="ui-input" value="${syCodeDetail.getAddInfo3()}" maxlength="50" title="참조값 입력">
							</td>
							<th scope="row">참조값4</th>
							<td class="input">
								<input type="text" name="addInfo4" id="addInfo4" class="ui-input" value="${syCodeDetail.getAddInfo4()}" maxlength="50" title="참조값 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">참조값5</th>
							<td class="input">
								<input type="text" name="addInfo5" id="addInfo5" class="ui-input" value="${syCodeDetail.getAddInfo5()}" maxlength="50" title="참조값 입력">
							</td>
							<th scope="row">참조값6</th>
							<td class="input">
								<input type="text" name="addInfo6" id="addInfo6" class="ui-input" value="${syCodeDetail.getAddInfo6()}" maxlength="50" title="참조값 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useYn_y" name="useYn" type="radio" value="Y">
											<label for="useYn_y">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useYn_n" name="useYn" type="radio" value="N">
											<label for="useYn_n">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr id="createCheck1">
							<th scope="row">작성자</th>
							<td name="rgsterName" id="rgsterName"><a href="javascript:void(0)" class="adminPopTag" data-admin="${syCodeDetail.rgsterNo}" style="text-decoration: underline;">${syCodeDetail.getRgsterDetailDpName()}</a></td>
							<th scope="row">작성일시</th>
							<td name="rgstDtm" id="rgstDtm"><fmt:formatDate value="${syCodeDetail.getRgstDtm()}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr id="createCheck2">
							<th scope="row">수정자</th>
							<td name="moderName" id="moderName"><a href="javascript:void(0)" class="adminPopTag" data-admin="${syCodeDetail.moderNo}" style="text-decoration: underline;">${syCodeDetail.getModerDetailDpName()}</a></td>
							<th scope="row">수정일시</th>
							<td name="modDtm" id="modDtm"><fmt:formatDate value="${syCodeDetail.getModDtm()}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				
			</form>
				<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
					<input type="hidden" name="codeField" id="codeField" value="${param.codeField}">
				</form>
			<!-- E : tbl-row -->
			
			<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap" id="removeCheck">
				<div class="fl">
					<a href="#" id="deleteCodeButton" class="btn-normal btn-del">삭제</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 20190117 추가  -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="saveCodeButton" class="btn-lg btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/system/abcmart.system.commoncode.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp"%>
