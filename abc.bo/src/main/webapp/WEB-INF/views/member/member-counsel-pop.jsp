<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		<%-- 초기값 세팅 --%>
		abc.biz.member.member.inquiry.viewType = "${viewType}";
		abc.biz.member.member.inquiry.cnslTypeCode = "${detailInfo.cnslTypeCode}";
		abc.biz.member.member.inquiry.cnslTypeDtlCode = "${detailInfo.cnslTypeDtlCode}";
		abc.biz.member.member.inquiry.aswrStatCode = "${detailInfo.aswrStatCode}";
		abc.biz.member.member.inquiry.siteNo = "${detailInfo.siteNo}";
		abc.biz.member.member.inquiry.orderNo = "${detailInfo.orderNo}";
		abc.biz.member.member.inquiry.vndrNo = "${detailInfo.vndrNo}";
		abc.biz.member.member.inquiry.asAcceptNo = "${detailInfo.asAcceptNo}";
		abc.biz.member.member.inquiry.asPrdtNo = "${detailInfo.prdtNo}";
		
		<%-- 유형변경 --%>
		$("#cnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#cnslTypeCode option:selected");
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
			var cnslAddInfo = $("#cnslTypeCode option:selected").attr('addInfo3');
			abc.biz.member.member.inquiry.orderAsAreaSet(cnslAddInfo);
		});
		
		<%-- 화면 세팅  --%>
		abc.biz.member.member.inquiry.memberCounselinit();
		
		<%-- 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.member.member.inquiry.saveCounsel();
		});
		
		<%-- 메모등록  --%>
		$("#memoSaveBtn").off().on('click', function() {
			if(!(abc.text.allNull($("#adminMemoText").val()))){
				if(confirm("저장하시겠습니까?")){
					abc.biz.member.member.inquiry.saveMemo();
				}
			}else{
				alert("관리자 메모를 입력해주세요");
				$("#adminMemoText").focus();
			}
		});
		
		<%-- 관리자메모 삭제 버튼  --%>
		$(document).on("click", ".btn-msg-list-del", function(event){	
			if(confirm("삭제 하시겠습니까?")){
				var cnslMemoSeq = $(this).attr("value");
				var memberCnslSeq = $("#memberCnslSeq").val();
				abc.biz.member.member.inquiry.removeAdminMemo(memberCnslSeq,cnslMemoSeq);
			}
		});
		
		//초기화
		$("#frmReset").click(function(){
			abc.biz.member.member.inquiry.frmReset();
		});
		
		//회원 링크
		$("#memberLink").click(function(){
			abc.memberDetailPopup($(this).data("memberno"));
		});
		
	});
	
</script>

<!-- S:tab_content -->
<div class="window-wrap">
	<div class="window-title">
		<h1>유선문의</h1>
	</div>
	<div class="window-content">
		<form id="counselRegiForm" name="counselRegiForm">
		<input type="hidden" id="memberCnslSeq" name="memberCnslSeq" value="${detailInfo.memberCnslSeq}"/>
		<input type="hidden" id="memberNo" name="memberNo" value="${memberInfo.memberNo}">
		<input type="hidden" id="inqryTitleText" name="inqryTitleText">
		<input type="hidden" id="viewType" name="viewType">
		
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">유선문의</h3>
			</div>
		</div>
		<!-- E : content-header -->
	
		<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>유선문의</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">회원</th>
					<td colspan="3"><a href="javascript:void(0);" id="memberLink" data-memberno="${memberInfo.memberNo}" class="link"><c:out value="${memberInfo.memberInfo}"/></a></td>
				</tr>
				<tr>
					<th scope="row">휴대폰</th>
					<td><c:out value="${memberInfo.detailHdphnNoText}"/></td>
					<th scope="row">이메일</th>
					<td><c:out value="${memberInfo.detailEmailAddrText}"/></td>
				</tr>
				<tr>
					<th scope="row">회원유형</th>
					<td><c:out value="${memberInfo.memberTypeCodeName}"/></td>
					<th scope="row">사이트</th>
					<td class="input">
						<ul class="ip-box-list">
						<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="radio" name="siteNo" id="radioClass${status.count}" value="${siteInfo.siteNo}">
									<label for="radioClass${status.count}">${siteInfo.siteName}</label>
								</span>
							</li>
						</c:forEach>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">유형</span>
					</th>
					<td class="input" id="tdCnslValue">
						<!-- TODO : 온라인 1:1문의와 동일 선택 항목 적용 예정 -->
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<select class="ui-sel" id="cnslTypeCode" name="cnslTypeCode" title="문의유형 선택">
								<option value="">문의구분 선택</option>
								<c:forEach var="cnslType" items="${codeList.CNSL_TYPE_CODE}">
									<option value="${cnslType.codeDtlNo}" addInfo3="${cnslType.addInfo3}">${cnslType.codeDtlName}</option>
								</c:forEach>
							</select>
							<select class="ui-sel" id="cnslTypeDtlCode" name="cnslTypeDtlCode" title="문의구분 선택">
								<option value="">문의 유형</option>
							</select>
						</span>
						<!-- E : ip-text-box -->
					</td>
					<th scope="row" id="thDtmTitle">문의일시</th>
					<td id="tdDtmValue"><fmt:formatDate value="${detailInfo.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
				<tr id="orderArea">
					<th scope="row">주문번호</th>
					<td colspan="3" class="input">
						<!-- S : 20190130 수정 // 기획수정으로 변경 -->
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							
							<c:if test="${empty detailInfo.orderNo}">
								<select class="ui-sel" title="주문번호 선택" name="orderNo" id="orderNo">
									<option value="" selected>선택하세요.</option>
									<c:forEach var="orderList" items="${orderList}" varStatus="status">
										<option value="${orderList.orderNo}" >${orderList.orderNo}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${not empty detailInfo.orderNo}">
								<select class="ui-sel" title="주문번호 선택" name="orderNo" id="orderNo">
									<c:forEach var="orderList" items="${orderList}" varStatus="status">
										<c:if test="${detailInfo.orderNo eq orderList.orderNo}">
											<option value="${orderList.orderNo}" selected>${orderList.orderNo}</option>
										</c:if>
										<c:if test="${detailInfo.orderNo ne orderList.orderNo}">
											<option value="${orderList.orderNo}">${orderList.orderNo}</option>
										</c:if>
									</c:forEach>
								</select>
							</c:if>
									
							<select class="ui-sel" title="상품명 선택" id="prdtInfo" name="prdtInfo">
								<option>상품명</option>
							</select>
						</span>
						<span class="text" id="vendorlink" name="vendorlink">
						</span>
						
						<input type="hidden" id="vndrNo" name="vndrNo" value="${detailInfo.vndrNo }">
						<!-- E : ip-text-box -->
						<!-- E : 20190130 수정 // 기획수정으로 변경 -->
					</td>
				</tr>
				<tr id="asArea">
					<th scope="row">AS 접수번호</th>
					<td colspan="3" class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box" id="spanAs">
						
							<c:if test="${empty detailInfo.asAcceptNo}">
								<select class="ui-sel" name="asAcceptNo" id="asAcceptNo" title="AS 접수번호 선택">
									<option value="" selected>선택하세요.</option>
									<c:forEach var="as" items="${asList}" varStatus="status">
										<option value="${as.prdtNo}">${as.asAcceptNo}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${not empty detailInfo.asAcceptNo}">
								<select class="ui-sel" name="asAcceptNo" id="asAcceptNo" title="AS 접수번호 선택">
									<c:forEach var="as" items="${asList}" varStatus="status">
										<c:if test="${detailInfo.asAcceptNo eq as.asAcceptNo}">
											<option value="${as.prdtNo}" selected>${as.asAcceptNo}</option>
										</c:if>
										<c:if test="${detailInfo.asAcceptNo ne as.asAcceptNo}">
											<option value="${as.prdtNo}">${as.asAcceptNo}</option>
										</c:if>
									</c:forEach>
								</select>
							</c:if>
							 
							<span class="text" >상품코드</span>
							<input type="hidden" name='prdtNo' id="prdtNo" value="${detailInfo.prdtNo}">
							<a href='javascript:void(0)' class='link' id='asPrdtNo'>${detailInfo.prdtNo}</a>
							
						</span>
						<!-- E : ip-text-box -->
					</td>
				</tr>
			</tbody>
		</table>
		<!-- E : tbl-row -->
	
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fr">
				<div class="btn-group">
					<a href="#" id="frmReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
				</div>
			</div>
		</div>
		<!-- E : content-header -->
	
		<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>유선문의 답변</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td colspan="3">유선상담 접수건 입니다.</td>
				</tr>
				<tr>
					<th scope="row">답변내용</th>
					<td class="input" colspan="3">
						<textarea name="aswrContText" class="ui-textarea" title="답변내용 입력"><c:out value="${detailInfo.aswrContText}"/></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">답변상태</span>
					</th>
					<td class="input" colspan="3">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<c:forEach var="aswrStat" items="${codeList.ASWR_STAT_CODE}" varStatus="status">
								<c:if test="${aswrStat.codeDtlNo ne '10000'}">
								<li>
									<span class="ui-rdo">
										<input type="radio" name="aswrStatCode" id="radioMsg${status.count}" value="${aswrStat.codeDtlNo}">
										<label for="radioMsg${status.count}">${aswrStat.codeDtlName}</label>
									</span>
								</li>
								</c:if>
							</c:forEach>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr id="trAswr">
					<th scope="row">답변자</th>
					<td><c:out value="${detailInfo.aswrDetailInfo}"/></td>
					<th scope="row">최종답변일시</th>
					<td><fmt:formatDate value="${detailInfo.aswrDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
				</tr>
			</tbody>
		</table>
		<!-- E : tbl-row -->
		<!-- E : 문의내역 목록 > 제목 클릭시 게시글 유형이 유선문의인 경우  -->
	
		<!-- S : 유형별 답변 테이블 하단 공통 버튼, 관리자메모 영역 -->
		<!-- S : tbl-desc-wrap -->
		<!-- <div class="tbl-desc-wrap">
			<div class="fl">
				<a href="#" class="btn-normal btn-del">삭제</a>
			</div>
		</div> -->
		<!-- E : tbl-desc-wrap -->
	
		<!-- S : window-btn-group -->
		<div class="window-btn-group">
			<a href="#" id="saveBtn" class="btn-normal btn-save">저장</a>
		</div>
		<!-- E : window-btn-group -->
		</form>
	
		<!-- S : content-header -->
		<div id="memoHeader" class="content-header">
			<div class="fl">
				<h3 class="content-title">관리자 메모</h3>
			</div>
		</div>
		<!-- E : content-header -->
	
		<!-- S : manager-msg-wrap -->
		<form id="frmAdminMemo" name="frmAdminMemo">
		<input type="hidden" name="memberCnslSeq" value="${detailInfo.memberCnslSeq}"/>
		<div class="manager-msg-wrap">
			<div class="msg-box">
				<textarea id="adminMemoText" name="memoText" title="관리자 메모 입력" maxlength="500"></textarea>
			</div>
			<button id="memoSaveBtn" type="button" class="btn-normal btn-save">저장</button>
		</div>
		<!-- E : manager-msg-wrap -->
	
		<!-- S : msg-list-wrap -->
		<c:if test="${bdMemberCounselMemo != null}">
		<div class="msg-list-wrap">
			<div class="wrapper"></div>
			<ul id="memoAddArea" class="msg-list">
			<c:forEach var="bdMemberCounselMemo" items="${bdMemberCounselMemo}" varStatus="idx">
				<li id="adminMemoArea${bdMemberCounselMemo.cnslMemoSeq}">
					<span class="msg-list-info">
						<span class="user-info">
							<span class="user-id">${bdMemberCounselMemo.rgsterDpName}</span>
<%-- 							<span class="user-name">${bdMemberCounselMemo.adminName}</span> --%>
						</span>
						<span class="regist-date-wrap">
							<span class="regist-date"><fmt:formatDate value="${bdMemberCounselMemo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
							<a href="javascript:void(0)" value="${bdMemberCounselMemo.cnslMemoSeq}" name="memoDeleteBtn" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
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
		</form>
		<!-- E : msg-list-wrap -->
		<!-- E : 유형별 답변 테이블 하단 공통 버튼, 관리자메모 영역 -->
	</div>
</div>
<!-- E:tab_content -->

<script src="/static/common/js/biz/member/abcmart.member.member.inquiry.js<%=_DP_REV%>">
</script>