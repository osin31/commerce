<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {

		//질문 첨부파일 화면 호출 
		var inqryFileArray = new Array();
		<c:forEach items="${inqryCounselAttachFiles}" var="inqryCounselAttachFiles">
			inqryFileArray.push({
				inqryAtchFileSeq  : "${inqryCounselAttachFiles.atchFileSeq}" ,
				inqryAtchFileName : "${inqryCounselAttachFiles.atchFileName}",
				inqryAtchFileUrl  : "${inqryCounselAttachFiles.atchFileUrl}" 
			});
		</c:forEach>
		abc.biz.vendor.individual.inquiry.inqryAtchCnt = inqryFileArray.length;	// 파일 업로드 등록/취소 카운트
		abc.biz.vendor.individual.inquiry.inqryFileCnt = inqryFileArray.length;	// 파일의 화면 호출 시, 첨부파일 갯수
		abc.biz.vendor.individual.inquiry.inqryFileArray = inqryFileArray.length;
		abc.biz.vendor.individual.inquiry.MaxInqryFileCnt = abc.consts.MEMBER_COUNSEL_FILE_MAX_CNT; // 최대 첨부파일 갯수
		abc.biz.vendor.individual.inquiry.MaxInqryFileMB = abc.consts.MEMBER_COUNSEL_FILE_MB; // 최대 첨부파일 용량
		
		var checkExtStr = abc.consts.MEMBER_COUNSEL_FILE_EXT;
		var maxSize = abc.consts.MEMBER_COUNSEL_FILE_MAX_MB;
		//첨부된 파일이 있을때/없을때
		if('${inqryCounselAttachFiles[0].atchFileSeq eq null}'){
			$("#inqryFileLi0").show();
		}else{
			$("#inqryFileLi0").hide();
		}
		
		//답변 첨부파일 화면 호출 
		var aswrFileArray = new Array();
		<c:forEach items="${aswrCounselAttachFiles}" var="aswrCounselAttachFiles">
			aswrFileArray.push({
				aswrAtchFileSeq  : "${aswrCounselAttachFiles.atchFileSeq}" ,
				aswrAtchFileName : "${aswrCounselAttachFiles.atchFileName}",
				aswrAtchFileUrl  : "${aswrCounselAttachFiles.atchFileUrl}" 
			});
		</c:forEach>
		abc.biz.vendor.individual.inquiry.aswrAtchCnt = aswrFileArray.length;	// 파일 업로드 등록/취소 카운트
		abc.biz.vendor.individual.inquiry.aswrFileCnt = aswrFileArray.length;	// 파일의 화면 호출 시, 첨부파일 갯수
		abc.biz.vendor.individual.inquiry.aswrFileArray = aswrFileArray.length;
		abc.biz.vendor.individual.inquiry.MaxAswrFileCnt = abc.consts.MEMBER_COUNSEL_FILE_MAX_CNT; // 최대 첨부파일 갯수
		
		//가장 마지막 [찾아보기] 버튼을 제외하고 숨긴다.
		for(var i=1; i <= abc.biz.vendor.individual.inquiry.MaxAswrFileCnt; i++){
			if(i != 1 + abc.biz.vendor.individual.inquiry.aswrAtchCnt){
				$("#aswrFileLi" + i).hide();
			}
		}
		
		//첨부파일 등록시
		$("input[name='aswrUpLoadFile']").change(function(){
			abc.biz.vendor.individual.inquiry.checkFileUpload = true;
			abc.biz.vendor.individual.inquiry.aswrAtchCnt++;
			var aswrFileId = "aswrFile" + abc.biz.vendor.individual.inquiry.aswrAtchCnt;
			if(abc.biz.vendor.individual.inquiry.fileValidate(aswrFileId, checkExtStr, maxSize) == false){
				return;
			}
			//첨부된 파일이 있을때/없을때
			if(Number(abc.biz.vendor.individual.inquiry.aswrAtchCnt) == 0){
				$("#aswrFileLi0").show();
			}else{
				$("#aswrFileLi0").hide();
			}
			
			//화면에 등록 대기중인 파일 보여주는 메소드
			$("#aswrFileLi" + abc.biz.vendor.individual.inquiry.aswrAtchCnt).hide();
			$("#aswrFileLi" + (Number(abc.biz.vendor.individual.inquiry.aswrAtchCnt) + 1)).show();
			abc.biz.vendor.individual.inquiry.drawAswrFile(aswrFileId);
		}); 
		
		// 저장 버튼
		$("#saveBtn").off().on('click', function() {
			abc.biz.vendor.individual.inquiry.doActionCounselScript("save");
		});
		// 관리자 메모 저장 버튼
		$("#memoSaveBtn").off().on('click', function() {
			if(!(abc.text.allNull($("#adminMemoText").val()))){
				if(confirm("저장하시겠습니까?")){
					abc.biz.vendor.individual.inquiry.doActionCounselScript("memoSave");
				}
			}else{
				alert("관리자 메모를 입력해주세요");
				$("#adminMemoText").focus();
			}
		});
		//답변 첨부파일 다운로드
		$("li[name^=aswrFileName]").children("span[name=fileName]").off().on('click', function() {
			abc.biz.vendor.individual.inquiry.fileDownLoad(this, "A");
		});

		//문의 첨부파일 다운로드
		$("li[name^=inquiryfileName]").off().on('click', function() {
			abc.biz.vendor.individual.inquiry.fileDownLoad(this, "Q");
		});
		
		//첨부파일 삭제 버튼
		$(document).on("click", ".btn-file-del", function(event){	
			if(confirm("삭제 하시겠습니까?")){
				abc.biz.vendor.individual.inquiry.removeAswrFile(this, Number("${bdMemberCounsel.memberCnslSeq}"));
			} 
		});
		
		//관리자메모 삭제 버튼
		$(document).on("click", ".btn-msg-list-del", function(event){
			if(abc.consts.BOOLEAN_TRUE == $(this).attr('escalationYn')){
				alert("이관메모는 삭제 불가능 합니다.");
				return ;
			}
			var rgsterNo = $(this).attr('rgsterNo');
			if($("#adminNo").val() == rgsterNo){
				if(confirm("삭제 하시겠습니까?")){
					var cnslMemoSeq = $(this).attr("value");
					var memberCnslSeq = $("#memberCnslSeq").val();
					
					abc.biz.vendor.individual.inquiry.removeAdminMemo(memberCnslSeq,cnslMemoSeq);
				}
			}else{
				alert("메모삭제는 작성자만 가능합니다.");
			}
		});
		
		//주문번호 클릭시 주문상세
		$("#orderPop").off().on("click", function(){
			var orderNo = "orderNo="+ $(this).attr("value") ; 
			abc.orderVendorDetailPopup('', '', '', '', orderNo);
		});

		
		//상품코드 클릭시 BO상품 상세
		$("#prdtNoPop").off().on("click", function(){
			abc.readonlyProductDetailPopup({prdtNo : $(this).attr('value')});
		});
		
		//상품명 클릭시 FO상품 상세
// 		$("#prdtNamePop").click(function(){
//			// FO 상품상세
//			var prdtNo = $(this).attr("value");
//		}); 
		
		// 상세화면 로딩시 답변보류일시 답변보류 처리
		if($("#aswrStatCode").val() === abc.consts.ASWR_STAT_CODE_HOLD_ASWR) {
			$("#aswrHoldCheck").prop("checked", true);			
		}
		
		// 상세화면 로딩시 답변완료일시 답변보류체크 disabled
		if($("#aswrStatCode").val() === abc.consts.ASWR_STAT_CODE_COM_ASWR) {
			$("#aswrHoldCheck").prop("disabled", true);			
		}
		
		// 초기화 클릭시
		$("#resetDetailBtn").off().on("click", function() {
			$.form("#frmInquiry").reset();
			
			if($("#aswrStatCode").val() === abc.consts.ASWR_STAT_CODE_HOLD_ASWR) {
				$("#aswrHoldCheck").prop("checked", true);			
			}
		});

	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>답변등록</h1>
		</div>
		<div class="window-content">

			<!-- S : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->
			<!-- S : col-wrap -->
			<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
			<form id="frmInquiry" name="frmInquiry"  method="post" onsubmit="return false;">
			<input type="hidden" id="memberCnslSeq" name="memberCnslSeq" value="${bdMemberCounsel.memberCnslSeq}"/>
			<input type="hidden" id="vndrNo" name="vndrNo" value="${bdMemberCounsel.vndrNo}"/>
			<input type="hidden" id="memberNo" name="memberNo" value="${bdMemberCounsel.memberNo}"/>
			<input type="hidden" id="siteNo" name="siteNo" value="${bdMemberCounsel.siteNo}"/>
			<input type="hidden" id="dispYn" name="dispYn" value="${Const.BOOLEAN_TRUE}"/>
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
								<th scope="row">작성자 / 회원구분</th>
								<td><c:out value="${bdMemberCounsel.memberDetailInfo}"/> / <c:out value="${memberInfo.memberTypeCodeName}"/></td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호 / 이메일</th>
								<td><c:out value="${memberInfo.hdphnNoText}"/> / <c:out value="${memberInfo.emailAddrText}"/></td>
							</tr>
							<tr>
								<th scope="row">문의사이트</th>
								<td><c:out value="${bdMemberCounsel.siteName}"/></td>
							</tr>
							<tr>
								<th scope="row">문의유형</th>
								<td>${bdMemberCounsel.cnslTypeName}</td>
							</tr>
							<tr>
								<th scope="row">주문번호</th>
								<td><a href="javascript:void(0)" id="orderPop" class="link" value="${bdMemberCounsel.orderNo}"><c:out value="${bdMemberCounsel.orderNo}"/></a></td>
							</tr>
							<tr>
								<th scope="row">상품</th>
								<td>
									<c:if test="${bdMemberCounsel.prdtNo ne null}">
										<!-- S : td-box-both -->
										<span class="td-box-both">
											<span class="fl">
												<ul class="td-text-list">
													<li><span class="text">상품코드</span><a href="javascript:void(0)" id="prdtNoPop" class="link" value="${bdMemberCounsel.prdtNo}"><c:out value="${bdMemberCounsel.prdtNo}" /></a></li>
													<c:if test="${bdMemberCounsel.vndrPrdtNoText ne null}"><li><span class="text">업체상품코드</span><span class="text">${bdMemberCounsel.vndrPrdtNoText}</span></li></c:if>
													<li><a href="${Const.SERVICE_DOMAIN_ART_FO}/product/redirect?prdtNo=${bdMemberCounsel.prdtNo}" id="prdtNamePop" class="link" target="_blank" value="${bdMemberCounsel.prdtNo}">${bdMemberCounsel.prdtName}</a><span class="text"><c:if test="${not empty bdMemberCounsel.optnName}">(${bdMemberCounsel.optnName})</c:if></span></li>
												</ul>
											</span>
											<span class="fr">
												<c:if test="${bdMemberCounsel.mmnyPrdtYn eq Const.BOOLEAN_FALSE}">
													<span class="text">(<c:out value='${bdMemberCounsel.vndrNo}'/>) </span>
													<a href="javascript:void(0)" id="vndrPop" value="${bdMemberCounsel.vndrNo}" class="link">${bdMemberCounsel.vndrName}</a>
												</c:if>
											</span>
										</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td><c:out value="${bdMemberCounsel.inqryTitleText}"/></td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea id="inqryContText" name="inqryContText" class="ui-textarea" title="답변내용 입력" readonly><c:out value="${bdMemberCounsel.inqryContText}"/></textarea>
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
														<input type="hidden" name="inquiryAtchFilePathText" value="${inqryCounselAttachFiles.atchFilePathText}"/>
														<a href="javascript:void(0);" class="file-name"><c:out value="${inqryCounselAttachFiles.atchFileName}"/></a>
													</li>
												</c:if>
											</c:forEach>
										</c:otherwise>
										</c:choose>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">이관일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.vndrAssignDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
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
								<a href="javasript:void(0)" id="resetDetailBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
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
								<td id="aswrStatCodeTd" name="aswrStatCodeTd"><c:out value="${bdMemberCounsel.aswrStatName}"/></td>
								<input type="hidden" id="aswrStatCode" name="aswrStatCode" value="${bdMemberCounsel.aswrStatCode}"/>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">
										답변내용
									</span>
								</th>
								<td class="input">
									<textarea id="aswrContText" name="aswrContText" class="ui-textarea" title="답변내용 입력" style="min-height: 324px;">${bdMemberCounsel.aswrContText}</textarea>
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
											<c:choose>
											<c:when test="${aswrCounselAttachFiles[0].atchFileSeq eq null}">
												<li id="aswrFileLi0"></li>
											</c:when>
											<c:otherwise>
												<c:forEach var="aswrCounselAttachFiles" end="3" items="${aswrCounselAttachFiles}" varStatus="idx">
													<c:if test="${!empty aswrCounselAttachFiles.atchFileName}">
														<li id="${aswrCounselAttachFiles.atchFileSeq}" name="aswrFileName${aswrCounselAttachFiles.atchFileSeq}" class="file-download">
															<input type="hidden" name="aswrDownLoadFileName" value="${aswrCounselAttachFiles.atchFileName}"/>
															<input type="hidden" name="aswrAtchFilePathText" value="${aswrCounselAttachFiles.atchFilePathText}"/>
															<span class="subject" name="fileName">${aswrCounselAttachFiles.atchFileName}</span>
															<button type="button" value="${aswrCounselAttachFiles.atchFileSeq}" class="btn-file-del" id="removeAswrFile${idx.count}">
															<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</c:if>
												</c:forEach>
											</c:otherwise>
											</c:choose>
										</ul>
									</div>
								</td>
							</tr>
							<!-- S : 20190308 추가 // 답변보류 영역 추가 -->
							<tr>
								<th scope="row">답변보류</th>
								<td class="input">
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="aswrHoldCheck" type="checkbox" >
										<label for="aswrHoldCheck">답변보류</label>
									</span>
								</td>
							</tr>
							<!-- E : 20190308 추가 // 담당업체이관, 답변보류 영역 추가 -->
							
							<tr>
								<th scope="row">작성자</th>
								<td><c:out value="${bdMemberCounsel.getAswrDetailInfo()}"/></td>
							</tr>
							<tr>
								<th scope="row">최종답변일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.aswrDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
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
									<a href="#" value="${bdMemberCounselMemo.cnslMemoSeq}" rgsterNo="${bdMemberCounselMemo.rgsterNo}" escalationYn="${bdMemberCounselMemo.escalationYn}" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
								</span>
							</span>
							<p class="msg-desc">
								<c:out value="${bdMemberCounselMemo.memoText}" escapeXml="false"/>
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
<script src="/static/common/js/biz/vendor/abcmart.vendor.individual.inquiry.js<%=_DP_REV%>">
</script>