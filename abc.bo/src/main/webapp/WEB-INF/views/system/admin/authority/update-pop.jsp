<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		//초기화면 세팅
		abc.biz.system.admin.authgroupreg.init();

		var noteText = '${authGroup.noteText}';
		$('#noteText').val(noteText.split('&lt;br/&gt;').join("\r\n"));


		//권한그룹명 입력시 카운트 event
		$("#authName").keyup(function(e){
			abc.biz.system.admin.authgroupreg.authNameCount();
		});


		//권한그룹명 입력시 카운트 event
		$("#noteText").keyup(function(e){
			abc.biz.system.admin.authgroupreg.noteTextCount();
		});

		//권한적용시스템 selectBox change event
		$("#selAuthApplySystemType").change(function(){
			$("#authApplySystemType").val($(this).val());
		});

		//사용여부 change event
		$("input:radio[name=radioUseModule]").change(function(){
			$("#useYn").val($(this).val());
		});

		//권한그룹 저장
		$("#authGroupSave").click(function(){
			abc.biz.system.admin.authgroupreg.authGroupSave();
		});

		//권한그룹 저장
		$("#authGroupRemove").click(function(){
			abc.biz.system.admin.authgroupreg.authGroupRemove();
		});

		//초기화
		$("#authReset").click(function(){
			$("#authName").val('<c:out value="${authGroup.authName}"/>')
			$("#selAuthApplySystemType").val('<c:out value="${authGroup.authApplySystemType}"/>');
			$("#authApplySystemType").val('<c:out value="${authGroup.authApplySystemType}"/>');
			$("#noteText").val('<c:out value="${authGroup.noteText}"/>');
			$("#useYn").val('<c:out value="${authGroup.useYn}" default="Y"/>');

			if($("#useYn").val() == 'N'){
				$("#radioUseY").prop('checked', false);
				$("#radioUseN").prop('checked', true);
			}else{
				$("#radioUseY").prop('checked', true);
				$("#radioUseN").prop('checked', false);
			}

			abc.biz.system.admin.authgroupreg.authNameCount();
		});
	});



</script>
<form id="frmSearch" name="frmSearch">
<input type="hidden" id="status" name="status" value='<c:out value="${status}"/>'>
<input type="hidden" id="authNo" name="authNo" value='<c:out value="${authGroup.authNo}"/>'>
<input type="hidden" id="authApplySystemType" name="authApplySystemType" value='<c:out value="${authGroup.authApplySystemType}"/>'>
<input type="hidden" id="useYn" name="useYn" value='<c:out value="${authGroup.useYn}" />'>

<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>권한그룹 (등록·수정/상세)</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">권한그룹 정보</h2>
				</div>
				<div class="fr">
					<div class="btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="javascript:void(0);" id="authReset" class="btn-sm btn-func">
							<i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>권한그룹 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>

					<tr>
						<th scope="row">
							<span class="th-required">권한 그룹명</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input text-unit20" placeholder="공백 포함하여 입력" id="authName" name="authName"
									title="공백 포함하여 입력" value='<c:out value="${authGroup.authName}" default=""/>'>
								<span class="text" id="authNameCnt">(0 / 20자)</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">권한 적용 시스템</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="권한 적용 시스템 선택" id="selAuthApplySystemType">
								<option value="">선택</option>
								<option value="B">BO(관리자시스템)</option>
								<option value="P">PO(파트너시스템)</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">설명</th>
						<td class="input">
							<textarea class="ui-textarea" title="설명 입력" id="noteText" name="noteText"></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">사용여부</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUseY" name="radioUseModule" type="radio" value="Y">
										<label for="radioUseY">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUseN" name="radioUseModule" type="radio" value="N">
										<label for="radioUseN">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
			<!-- S : tbl-desc-wrap -->
			<sec:authorize access="hasRole('ROLE_20000')">
			<c:if test="${status == 'U'}">
				<div class="tbl-desc-wrap">
					<div class="fl">
						<a href="javascript:void(0);" id="authGroupRemove" class="btn-normal btn-del">삭제</a>
					</div>
				</div>
			</c:if>
			</sec:authorize>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 20190117 추가  -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="javascript:void(0);" id="authGroupSave" class="btn-normal btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>

<script src="/static/common/js/biz/system/abcmart.system.admin.authgroupreg.js<%=_DP_REV%>"></script>
</body>
</form>
