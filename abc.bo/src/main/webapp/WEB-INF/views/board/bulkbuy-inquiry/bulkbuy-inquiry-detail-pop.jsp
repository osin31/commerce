<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {

		// 닫기 버튼
		$("#btnBulkBuyClose").off().on('click', function() {
			window.close();
		});
		// 관리자 메모 저장 버튼
		$("#memoSaveBtn").off().on('click', function() {
			if(!(abc.text.allNull($("#adminMemoText").val()))){
				if(confirm("저장하시겠습니까?")){
					abc.biz.board.bulkbuy.doAction("memoSave");
				}
			}else{
				alert("관리자 메모를 입력해주세요");
				$("#adminMemoText").focus();
			}
		});
		
		//회원정보 클릭시 회원상세
		$("#memberPop").click(function(){
			var memberNo = $(this).attr("value");
			abc.memberDetailPopup(memberNo);
		});
		
		//관리자메모 삭제 버튼
		$(document).on("click", ".btn-msg-list-del", function(event){
			var rgsterNo = $(this).attr('rgsterNo');
			if($("#adminNo").val() == rgsterNo){
				if(confirm("삭제 하시겠습니까?")){
					var cnslMemoSeq = $(this).attr("value");
					var blkBuyInqrySeq = $("#blkBuyInqrySeq").val();
					
					abc.biz.board.bulkbuy.removeAdminMemo(blkBuyInqrySeq,cnslMemoSeq);
				}
			}else{
				alert("메모삭제는 작성자만 가능합니다.");
			}
		});

	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>단체주문 문의 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">단체주문 문의 상세</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->
			<!-- S : col-wrap -->
			<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
			<form id="frmInquiry" name="frmInquiry"  method="post" onsubmit="return false;">
			<input type="hidden" name="blkBuyInqrySeq" id="blkBuyInqrySeq" value="${bdBulkBuyDetail.blkBuyInqrySeq}">
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
								<td><a href="javascript:void(0)" id="memberPop" class="link" value="${memberInfo.memberNo}"><c:out value="${memberInfo.loginId}"/></a> /<c:out value="${memberInfo.memberTypeCodeName}"/></td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호 / 이메일</th>
								<td><c:out value="${memberInfo.hdphnNoText}"/>/ <c:out value="${memberInfo.emailAddrText}"/></td>
							</tr>
							<tr>
								<th scope="row">문의사이트</th>
								<td><c:out value="${bdBulkBuyDetail.siteName}"/></td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td><c:out value="${bdBulkBuyDetail.inqryTitleText}" escapeXml="false"/></td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea class="ui-textarea" title="문의내용 입력"  readonly="readonly"><c:out value="${bdBulkBuyDetail.inqryContText}" escapeXml="false"/></textarea>
								</td>
							</tr>
							<!-- DESC : 20190312 삭제 // 첨부파일 삭제 -->
							<tr>
								<th scope="row">문의일시</th>
								<td><fmt:formatDate value="${bdBulkBuyDetail.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
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
				<!-- DESC : 20190318 버튼클래스, 버튼명 수정 -->
				<a href="javascript:void(0)" id="btnBulkBuyClose" class="btn-normal btn-link">닫기</a>
			</div>
			<!-- E : window-btn-group -->
			
			<!-- S : content-header -->
			<input type="hidden" name="blkBuyInqrySeq" value="${bdBulkBuyDetail.blkBuyInqrySeq}"/>
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
			<c:if test="${bdBulkBuyMemo != null}">
			<!-- S : msg-list-wrap -->
			<div class="msg-list-wrap">
				<div class="wrapper"></div>
				<ul id="memoAddArea" class="msg-list">
				<c:forEach var="bdBulkBuyMemo" items="${bdBulkBuyMemo}" varStatus="idx">
					<li id="adminMemoArea${bdBulkBuyMemo.blkBuyMemoSeq}">
						<span class="msg-list-info">
							<span class="user-info">
								<span class="user-id">${bdBulkBuyMemo.rgsterDpName}</span>
							</span>
							<span class="regist-date-wrap">
								<span class="regist-date"><fmt:formatDate value="${bdBulkBuyMemo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
								<a href="javascript:void(0)" value="${bdBulkBuyMemo.blkBuyMemoSeq}" rgsterno="${bdBulkBuyMemo.rgsterNo}" name="memoDeleteBtn" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
							</span>
						</span>
						<p class="msg-desc">
							<c:out value="${bdBulkBuyMemo.memoText}"/>
						</p>
					</li>
				</c:forEach>
				</ul>
			</div>
			</c:if>
			</form>
			<!-- E : msg-list-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/board/abcmart.board.bulkbuyinquiry.js<%=_DP_REV%>">
</script>