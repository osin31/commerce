<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		//상담유형 상세코드 변경 및 답변상태에 따라 답변보류 영역 조정
		abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
		
		//이관상태에 따라 이관영역 조정
// 		abc.biz.board.inquiry.initEscalationCheck('${bdMemberCounsel.vndrPrdtNoText}');
		
		// 문의유형 코드 변경
		$("#cnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#cnslTypeCode option:selected");		// 문의유형코드

			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
		});
		
		// 상담유형 코드 변경
		$("#aswrCnslTypeCode").off().on('change', function(f) {
			$("#aswrCnslTypeDtlCode").show();
			var aswrCnslTypeCode = $("#aswrCnslTypeCode option:selected");		// 상담유형코드
			
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", aswrCnslTypeCode.val(), "aswrCnslTypeDtlCode");
			abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
		});

		//상담스크립트 제목 조회
		$("#aswrCnslTypeDtlCode").off().on('change', function(f) {
			var _cnslTypeCode = $("#aswrCnslTypeCode").val();		// 상담유형코드
			var _cnslTypeDtlCode = $("#aswrCnslTypeDtlCode").val();	// 상담분류코드

			abc.getCounselScriptTitle(_cnslTypeCode, _cnslTypeDtlCode, "cnslScriptTitleText");
		});
		
		// 답변 내용 조회
		$("#cnslScriptTitleText").off().on('change', function(f) {
			var _cnslScriptSeq = $("#cnslScriptTitleText").val();	// 상담분류코드

			abc.getCounselScript(_cnslScriptSeq, "aswrContText");
		});
		
		//질문 첨부파일 화면 호출 
		var inqryFileArray = new Array();
		<c:forEach items="${inqryCounselAttachFiles}" var="inqryCounselAttachFiles">
			inqryFileArray.push({
				inqryAtchFileSeq  : "${inqryCounselAttachFiles.atchFileSeq}" ,
				inqryAtchFileName : "${inqryCounselAttachFiles.atchFileName}",
				inqryAtchFileUrl  : "${inqryCounselAttachFiles.atchFileUrl}" 
			});
		</c:forEach>
		abc.biz.board.inquiry.inqryAtchCnt = inqryFileArray.length;	// 파일 업로드 등록/취소 카운트
		abc.biz.board.inquiry.inqryFileCnt = inqryFileArray.length;	// 파일의 화면 호출 시, 첨부파일 갯수
		abc.biz.board.inquiry.inqryFileArray = inqryFileArray.length;
		abc.biz.board.inquiry.MaxInqryFileCnt = abc.consts.MEMBER_COUNSEL_FILE_MAX_CNT; // 최대 첨부파일 갯수
		abc.biz.board.inquiry.MaxInqryFileMB = abc.consts.MEMBER_COUNSEL_FILE_MB; // 최대 첨부파일 용량
		
		var checkExtStr = abc.consts.MEMBER_COUNSEL_FILE_EXT;
		var maxSize = abc.consts.MEMBER_COUNSEL_FILE_MAX_MB;
		
		//답변 첨부파일 화면 호출 
		var aswrFileArray = new Array();
		<c:forEach items="${aswrCounselAttachFiles}" var="aswrCounselAttachFiles">
			aswrFileArray.push({
				aswrAtchFileSeq  : "${aswrCounselAttachFiles.atchFileSeq}" ,
				aswrAtchFileName : "${aswrCounselAttachFiles.atchFileName}",
				aswrAtchFileUrl  : "${aswrCounselAttachFiles.atchFileUrl}" 
			});
		</c:forEach>
		abc.biz.board.inquiry.aswrAtchCnt = aswrFileArray.length;	// 파일 업로드 등록/취소 카운트
		abc.biz.board.inquiry.aswrFileCnt = aswrFileArray.length;	// 파일의 화면 호출 시, 첨부파일 갯수
		abc.biz.board.inquiry.aswrFileArray = aswrFileArray.length;
		abc.biz.board.inquiry.MaxAswrFileCnt = abc.consts.MEMBER_COUNSEL_FILE_MAX_CNT; // 최대 첨부파일 갯수
		
		//가장 마지막 [찾아보기] 버튼을 제외하고 숨긴다.
		for(var i=1; i <= abc.biz.board.inquiry.MaxAswrFileCnt; i++){
			if(i != 1 + abc.biz.board.inquiry.aswrAtchCnt){
				$("#aswrFileLi" + i).hide();
			}
		}
		
		//첨부파일 등록시
		$("input[name='aswrUpLoadFile']").change(function(){
			abc.biz.board.inquiry.checkFileUpload = true;
			abc.biz.board.inquiry.aswrAtchCnt++;
			var aswrFileId = "aswrFile" + abc.biz.board.inquiry.aswrAtchCnt;
			if(abc.biz.board.inquiry.fileValidate(aswrFileId, checkExtStr, maxSize) == false){
				return;
			}
			
			//화면에 등록 대기중인 파일 보여주는 메소드
			$("#aswrFileLi" + abc.biz.board.inquiry.aswrAtchCnt).hide();
			$("#aswrFileLi" + (Number(abc.biz.board.inquiry.aswrAtchCnt) + 1)).show();
			abc.biz.board.inquiry.drawAswrFile(aswrFileId);
		}); 
		
		$("#frontProductPop").click(function(){
			abc.productFrontUrl($(this).data("prdtno"));
		});
		
		// 저장 버튼
		$("#saveBtn").off().on('click', function() {
			if($("#leaveYn").val() == abc.consts.BOOLEAN_FALSE){
				abc.biz.board.inquiry.doActionCounselScript("save");
			}else{
				abc.biz.board.inquiry.doActionCounselScript("saveInquiryLeave");
			}
		});
		
		// 관리자 메모 저장 버튼
		$("#memoSaveBtn").off().on('click', function() {
			if(!(abc.text.allNull($("#adminMemoText").val()))){
				if(confirm("저장하시겠습니까?")){
					abc.biz.board.inquiry.doActionCounselScript("memoSave");
				}
			}else{
				alert("관리자 메모를 입력해주세요");
				$("#adminMemoText").focus();
			}
		});
		
		//첨부파일 삭제 버튼
		$(document).on("click", ".btn-file-del", function(event){
			if(confirm("삭제 하시겠습니까?")){
				console.log(this);
				abc.biz.board.inquiry.removeAswrFile(this, Number("${bdMemberCounsel.memberCnslSeq}"));
			} 
		});
		
		//관리자메모 삭제 버튼
		$(document).on("click", ".btn-msg-list-del", function(event){
			if(abc.consts.BOOLEAN_TRUE == $(this).attr('escalationYn')){
				alert("이관메모는 삭제 불가능 합니다.");
				return ;
			}
			var rgsterNo = $(this).attr('rgsterno');
			if($("#adminNo").val() == rgsterNo){
				if(confirm("삭제 하시겠습니까?")){
					var cnslMemoSeq = $(this).attr("value");
					var memberCnslSeq = $("#memberCnslSeq").val();
					
	 				abc.biz.board.inquiry.removeAdminMemo(memberCnslSeq,cnslMemoSeq);
				}
			}else{
				alert("메모삭제는 작성자만 가능합니다.");
			}
		});
		
		//이관 여부 체크시 답변보류도 같이 체크
		$("#chkEscalation").change(function(){
			if($("#chkEscalation").prop("checked")){
				$("#aswrHoldCheck").prop("checked", true);
			}else{
				$("#aswrHoldCheck").prop("checked", false);
			}
		});
		
		$("#aswrHoldCheck").change(function(){
			if($("#chkEscalation").prop("checked")){
				$("#aswrHoldCheck").prop("checked", true);
				alert("담당업체 이관시 답변보류로 이관됩니다.");
			}
		});
			
		//주문번호 클릭시 주문상세
		$("#orderPop").click(function(){
			var orderNo = "orderNo="+ $(this).attr("value") ; 
			abc.orderDetailPopup('', '', '', '', orderNo);
		});
		
		//회원정보 클릭시 회원상세
		$("#memberPop").click(function(){
			var memberNo = $(this).attr("value");
			abc.memberDetailPopup(memberNo);
		});
		
		//상품정보 클릭시 상품 상세
		$("#productPop").click(function(){
			var prdtNo = $(this).attr("value");
			var _param = {
					 prdtNo : prdtNo
			 };
			abc.readonlyProductDetailPopup(_param);
		}); 
		
		//AS상세 팝업
		$("#asPop").click(function(){
			abc.biz.board.inquiry.asPopUp($(this).attr("value"), $(this).attr("asAcceptPrdtSeq"));
		});
		
		//입점업체 팝업
		$("#vndrPop").click(function(){
			var vndrNo = $(this).data("vndrno");
			abc.vendorInfoPop(vndrNo);
		});
		
		//초기화 버튼
		$("#resetBtn").off().on('click', function(){
			location.reload(); 
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		//상담 스크립트 있을시에
		if("" != '${counselScript.cnslScriptSeq}'){
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#aswrCnslTypeCode").val(), "aswrCnslTypeDtlCode");
			abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
			$("#aswrCnslTypeDtlCode").val('${counselScript.cnslTypeDtlCode}');
			
			abc.getCounselScriptTitle($("#aswrCnslTypeCode").val(), $("#aswrCnslTypeDtlCode").val(), "cnslScriptTitleText");
			var cnslScriptSeq = '${counselScript.cnslScriptSeq}'
			$("#cnslScriptTitleText").val(cnslScriptSeq);
		}
		
		//첨부파일 뷰
		$(document).on("click","[name=popupImage]",function(){
			var atchFileUrl = $(this).data("fileurl"); // DB에 저장된 실 URL
			window.open(atchFileUrl,'imagePopup','');
		});
		
		if('${memberInfo.leaveYn}' != abc.consts.BOOLEAN_FALSE){
			$("input[name=aswrUpLoadFile]").prop("disabled", true);
			$("#chkEscalation").prop("disabled", true);
			$("#saveBtn").text("저장(탈퇴회원)");
		}
		
	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>1:1문의 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">1:1문의 상세</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->
			<!-- S : col-wrap -->
			<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
			<form id="frmInquiry" name="frmInquiry" method="post" >
			<input type="hidden" id="memberCnslSeq" name="memberCnslSeq" value="${bdMemberCounsel.memberCnslSeq}"/>
			<input type="hidden" id="vndrNo" name="vndrNo" value="${bdMemberCounsel.vndrNo}"/>
			<input type="hidden" id="siteNo" name="siteNo" value="${bdMemberCounsel.siteNo}"/>
			<input type="hidden" id="cnslScriptSeq" name="cnslScriptSeq" value="${bdMemberCounsel.cnslScriptSeq}"/>
			<input type="hidden" id="dispYn" name="dispYn" value="${Const.BOOLEAN_TRUE}"/>
			<input type="hidden" id="vndrAssignYn" name="vndrAssignYn" value="${bdMemberCounsel.vndrAssignYn}" />
			<input type="hidden" id="vndrAsnrNo" name="vndrAsnrNo" value="${bdMemberCounsel.vndrAsnrNo}"/>
			<input type="hidden" id="memberNo" name="memberNo" value="${bdMemberCounsel.memberNo}"/>
			<input type="hidden" id="checkAswrDtm" name="checkAswrDtm" value="${bdMemberCounsel.aswrDtm}"/>
			<input type="hidden" id="mmnyPrdtYn" name="mmnyPrdtYn" value="${bdMemberCounsel.mmnyPrdtYn}"/>
			<input type="hidden" id="cnslGbnCode" value="${bdMemberCounsel.cnslGbnCode}"/>
			<input type="hidden" id="leaveYn" value="${memberInfo.leaveYn}"/>
			<div class="col-wrap">
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">게시물 조회</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>게시물 조회</caption>
						<colgroup>
							<col style="width: 140px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">회원</th>
								<td><a href="javascript:void(0)" id="memberPop" class="link" value="${bdMemberCounsel.memberNo}"><c:out value="${bdMemberCounsel.memberDetailInfo}"/></a> / <c:out value="${memberInfo.memberTypeCodeName}"/></td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호 / 이메일</th>
								<td><c:out value="${memberInfo.detailHdphnNoText}"/> / <c:out value="${memberInfo.detailEmailAddrText}"/></td>
							</tr>
							<tr>
								<th scope="row">문의사이트</th>
								<td><c:out value="${bdMemberCounsel.siteName}"/></td>
							</tr>
							<tr>
								<th scope="row">문의유형</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" title="문의유형 선택">
											<option value="">문의유형</option>
											<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}"<c:if test="${cnslTypeCode.codeDtlNo eq bdMemberCounsel.cnslTypeCode}"> selected</c:if>>${cnslTypeCode.codeDtlName}</option>
											</c:forEach>
										</select>
										<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" title="문의구분 선택">
											<option value="">문의구분</option>
											<c:forEach var="cnslTypeDtlCode" items="${cnslTypeDtlCode}">
												<option value="${cnslTypeDtlCode.codeDtlNo}"<c:if test="${cnslTypeDtlCode.codeDtlNo eq bdMemberCounsel.cnslTypeDtlCode}"> selected</c:if>>${cnslTypeDtlCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">주문번호</th>
								<td><a href="javascript:void(0)" id="orderPop" class="link" value="${bdMemberCounsel.orderNo}"><c:out value="${bdMemberCounsel.orderNo}"/></a></td>
							</tr>
							<tr>
								<th scope="row">상품</th>
								<!-- s : 20190517 수정 // 상품 td 수정 -->
								<td class="input">
									<c:if test="${bdMemberCounsel.prdtNo ne null}">
										<!-- S : td-box-both -->
										<span class="td-box-both">
											<span class="fl">
												<ul class="td-text-list">
													<li><span class="text">상품코드</span><a href="javascript:void(0)" id="productPop" class="link" value="${bdMemberCounsel.prdtNo}"><c:out value="${bdMemberCounsel.prdtNo}" /></a></li>
													<c:if test="${bdMemberCounsel.vndrPrdtNoText ne null}"><li><span class="text">업체상품코드</span><span class="text">${bdMemberCounsel.vndrPrdtNoText}</span></li></c:if>
													<li><a href="javascript:void(0)" id="frontProductPop" class="link" data-prdtno="${bdMemberCounsel.prdtNo}">${bdMemberCounsel.prdtName}</a><span class="text"><c:if test="${not empty bdMemberCounsel.optnName}">(${bdMemberCounsel.optnName})</c:if></span></li>
												</ul>
											</span>
											<span class="fr">
												<c:if test="${bdMemberCounsel.mmnyPrdtYn eq Const.BOOLEAN_FALSE}">
													<span class="text">(<c:out value='${bdMemberCounsel.vndrNo}'/>) </span>
													<a href="javascript:void(0)" id="vndrPop" data-vndrno="${bdMemberCounsel.vndrNo}" class="link">${bdMemberCounsel.vndrName}</a>
												</c:if>
											</span>
										</span>
										<!-- E : td-box-both -->
									</c:if>
								</td>
								<!-- e : 20190517 수정 // 상품 td 수정 -->
							</tr>
							<tr>
								<th scope="row">AS접수번호</th>
								<td><a href="javascript:void(0)" id="asPop" class="link" value="${bdMemberCounsel.asAcceptNo}" asAcceptPrdtSeq="${bdMemberCounsel.asAcceptPrdtSeq}" ><c:out value="${bdMemberCounsel.asAcceptNo}"/></a></td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td><c:out value="${bdMemberCounsel.inqryTitleText}" escapeXml="false"/></td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea id="inqryContText" name="inqryContText" class="ui-textarea" title="답변내용 입력" onkeypress="return false;" ><c:out value="${bdMemberCounsel.inqryContText}" escapeXml="false"/></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">첨부파일</th>
								<td class="input">
									<ul class="attach-file-list" id="inqryFileArea">
										<c:choose>
										<c:when test="${inqryCounselAttachFiles[0].atchFileSeq eq null}">
											<li id="inqryFileLi0"></li>
										</c:when>
										<c:otherwise>
											<c:forEach var="inqryCounselAttachFiles" end="3" items="${inqryCounselAttachFiles}" varStatus="idx">
												<c:if test="${!empty inqryCounselAttachFiles.atchFileName}">
													<li name="inquiryfileName${inqryCounselAttachFiles.atchFileName}">
													<input type="hidden" name="inquiryDownLoadFileName" value="${inqryCounselAttachFiles.atchFileName}"/>
													<input type="hidden" name="inquiryDownLoadFileName" value="${inqryCounselAttachFiles.atchFileName}"/>
													<input type="hidden" name="inquiryAtchFilePathText" value="${inqryCounselAttachFiles.atchFilePathText}"/>
													<a href="${inqryCounselAttachFiles.atchFileUrl}" class="file-name" target="_blank"><c:out value="${inqryCounselAttachFiles.atchFileName}"/></a>
													</li>
												</c:if>
											</c:forEach>
										</c:otherwise>
										</c:choose>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">문의일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">답변관리</h3>
						</div>
						<div class="fr">
							<div class="btn-group">
								<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>답변관리</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<!-- S : 20190308 수정 // 기존 답변상태 tr 위치 변경 -->
							<tr>
								<th scope="row">답변상태</th>
								<td id="aswrStatCodeTd" name="aswrStatCodeTd">
									<c:out value="${bdMemberCounsel.aswrStatName}"/>
									<input type="hidden" id="aswrStatCode" name="aswrStatCode" value="${bdMemberCounsel.aswrStatCode}">
								</td>
							</tr>
							<!-- E : 20190308 수정 // 기존 답변상태 tr 위치 변경 -->
							<tr>
								<th scope="row">답변 스크립트 선택</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select id="aswrCnslTypeCode" name="aswrCnslTypeCode" class="ui-sel" title="상담유형 선택">
											<option value="etc">직접입력</option>
											<c:forEach var="aswrCnslTypeCode" items="${aswrCnslTypeCode}">
												<option value="${aswrCnslTypeCode.codeDtlNo}"<c:if test="${aswrCnslTypeCode.codeDtlNo eq bdMemberCounsel.aswrCnslTypeCode}"> selected</c:if>>${aswrCnslTypeCode.codeDtlName}</option>
											</c:forEach>
										</select>
										<select id="aswrCnslTypeDtlCode" name="aswrCnslTypeDtlCode" class="ui-sel" title="상담분류 선택">
											<option value="">상담분류</option>
											<c:forEach var="aswrCnslTypeDtlCode" items="${aswrCnslTypeDtlCode}">
												<option value="${aswrCnslTypeDtlCode.codeDtlNo}"<c:if test="${aswrCnslTypeDtlCode.codeDtlNo eq bdMemberCounsel.aswrCnslTypeDtlCode}"> selected</c:if>>${aswrCnslTypeDtlCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">답변내용</th>
								<td class="input">
									<select id="cnslScriptTitleText" name="cnslScriptTitleText" class="ui-sel" title="스크립트제목 선택">
										<option value="">스크립트제목</option>
									</select>
									<textarea id="aswrContText" name="aswrContText" class="ui-textarea" title="답변내용 입력" maxlength="2000"><c:out value="${bdMemberCounsel.aswrContText}" escapeXml="false"/></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">첨부파일</th>
								<td class="input">
									<div class="file-wrap">
										<ul class="file-list" id="aswrFileArea">
											<c:forEach begin='1' end="3" varStatus="idx">
												<li id="aswrFileLi${idx.count}">
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="file" name="aswrUpLoadFile" id="aswrFile${idx.count}" title="첨부파일 추가">
														<label for="aswrFile${idx.count}">찾아보기</label>
													</span>
												</li>
											</c:forEach>
											<c:forEach var="aswrCounselAttachFiles" end="3" items="${aswrCounselAttachFiles}" varStatus="idx">
												<c:if test="${!empty aswrCounselAttachFiles.atchFileName}">
													<li id="${aswrCounselAttachFiles.atchFileSeq}" name="aswrFileName${aswrCounselAttachFiles.atchFileSeq}" class="file-download">
														<input type="hidden" name="aswrDownLoadFileName" value="${aswrCounselAttachFiles.atchFileName}"/>
														<input type="hidden" name="aswrAtchFilePathText" value="${aswrCounselAttachFiles.atchFilePathText}"/>
														<span class="subject" name="popupImage" data-fileurl="${aswrCounselAttachFiles.atchFileUrl}">${aswrCounselAttachFiles.atchFileName}</span>
														<button type="button" value="${aswrCounselAttachFiles.atchFileSeq}" class="btn-file-del" id="removeAswrFile${idx.count}">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
														</button>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</td>
							</tr>
							<!-- S : 20190308 추가 // 담당업체이관, 답변보류 영역 추가 -->
							<tr>
								<th scope="row">담당업체이관</th>
								<td class="input">
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkEscalation" name="chkEscalation" type="checkbox" <c:if test="${(bdMemberCounsel.mmnyPrdtYn eq Const.BOOLEAN_TRUE) or (bdMemberCounsel.vndrAsnrNo ne null) or (bdMemberCounsel.prdtNo eq null)}" >disabled</c:if>>
										<label for="chkEscalation">이관</label>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">답변보류</th>
								<td class="input">
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="aswrHoldCheck" name="aswrHoldCheck" type="checkbox" <c:if test="${bdMemberCounsel.aswrStatCode eq '10001'}">checked</c:if> >
										<label for="aswrHoldCheck">답변보류</label>
									</span>
								</td>
							</tr>
							<!-- E : 20190308 추가 // 담당업체이관, 답변보류 영역 추가 -->
							<input type="hidden" name="checkV">
							
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdMemberCounsel.aswrNo}" style="text-decoration: underline;"><c:out value="${bdMemberCounsel.getAswrDetailInfo()}"/></a></td>
							</tr>
							<tr>
								<th scope="row">답변일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.aswrDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row" colspan="2">※ 담당업체 이관 체크 시 해당 문의내용은 담당 입점사에게 전달 됩니다</th>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
			</div>
			</form>
			<!-- E : col-wrap -->
			<!-- E : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->

			<!-- S : window-btn-group -->
			<form id="frmAdminMemo" name="frmAdminMemo">
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->

			<!-- S : content-header -->
			<input type="hidden" name="memberCnslSeq" value="${bdMemberCounsel.memberCnslSeq}"/>
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관리자 메모</h3>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : manager-msg-wrap -->
				<div class="manager-msg-wrap">
					<div class="msg-box">
						<textarea id="adminMemoText" name="memoText" title="관리자 메모 입력" maxlength="500"></textarea>
					</div>
					<button id="memoSaveBtn" type="button" class="btn-normal btn-save">저장</button>
				</div>
				<!-- E : manager-msg-wrap -->
				<c:if test="${bdMemberCounselMemo != null}">
				<!-- S : msg-list-wrap -->
				<div class="msg-list-wrap">
					<div class="wrapper"></div>
					<ul id="memoAddArea" class="msg-list">
					<c:forEach var="bdMemberCounselMemo" items="${bdMemberCounselMemo}" varStatus="idx">
						<li id="adminMemoArea${bdMemberCounselMemo.cnslMemoSeq}">
							<span class="msg-list-info">
								<span class="user-info">
									<span class="user-id">${bdMemberCounselMemo.rgsterDpName}</span>
								</span>
								<span class="regist-date-wrap">
									<span class="regist-date"><fmt:formatDate value="${bdMemberCounselMemo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
									<a href="#" value="${bdMemberCounselMemo.cnslMemoSeq}" rgsterno="${bdMemberCounselMemo.rgsterNo}" escalationYn="${bdMemberCounselMemo.escalationYn}" name="memoDeleteBtn" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
								</span>
							</span>
							<p class="msg-desc">
								${bdMemberCounselMemo.memoText}
							</p>
						</li>
					</c:forEach>
					</ul>
				</div>
				</c:if>
				<!-- E : msg-list-wrap -->
			</form>
			<!-- </form> -->
		</div>
	</div>
</body>

<script type="text/javascript" src="/static/common/js/biz/board/abcmart.board.inquiry.js<%=_DP_REV%>"></script>
