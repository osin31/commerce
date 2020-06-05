<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 회원 목록 화면  초기 세팅. --%>
		abc.biz.member.member.initSetObj();
		
		// enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});
		
		
		<%-- 회원 목록 그리드 초기 세팅. --%>
		abc.biz.member.member.initMemberSheet();
		
		<%-- 회원검색 선택 이벤트. --%>
		$("#memberSearchType").change(function(){
			var searchType = $(this).val();
			console.log(searchType);
			abc.biz.member.member.memberSearchTypeChange(searchType);
		});
		
		<%-- 휴대폰번호 입력 시 숫자만 입력되도록 설정 --%>
		$("#hdphnNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 휴대폰 번호 뒷자리 입력 시 숫자만 입력되도록 설정 --%>
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		<%-- 캘린더 버튼 설정(주)--%>
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		<%-- 캘린더 버튼 설정(월) --%>
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		<%-- 캘린더 버튼 설정(1년) --%>
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		$("#searchBtn").click(function(){
			abc.biz.member.member.memberDoAction('search');
		});
		
		<%-- 이메일 변경 시 세팅 --%>
		$("#selEmail").change(function(){
			var selEmailVal = $(this).val();
			abc.biz.member.member.changeEmail(selEmailVal);
		});
		
		<%-- 초기화. --%>
		$("#resetBtn").click(function(){
			$('#frmSearch')[0].reset();
			abc.biz.member.member.formResetInit();
			$("a[name^=per1Month]").trigger('click');
		});
		
		<%-- 페이지 목록 개수 클릭시 --%>
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		
		<%-- 기본기간설정 한달 --%>
		$("a[name^=per1Month]").trigger('click');
		
		$("#btnSendTextMsg").off().on("click", function(f) {
			var _params = {
					siteNo : "",
					memberNo : abc.consts.ADMIN_SEND_MSG_TO_MEMBER,
					recvTelNoText : "",
					rcvrName : abc.consts.ADMIN_SEND_MSG_TO_MEMBER
			};
			abc.sendSmsPopup(_params);
		});
	});
	
	<%-- 그리드 Click 이벤트 --%>
	function memberSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( memberSheet.ColSaveName(Col) == "gridMemberName" && Value != "" ) {
				var url = "/member/member-detail-pop";
				var params = {}
				params.memberNo = memberSheet.GetCellValue(Row, "memberNo");
				
				abc.open.popup({
					winname :	"detailPop",
					url 	:	url,
					width 	:	1480,
					height	:	985,
					params	:	params
				});
			}
		}
	}
	
	function memberSheet_OnSearchEnd(code, msg, stCode, stMsg, responseText) {
		if (code != 0) {
			alert("오류가 발생했습니다. - " + msg);
		}
	}
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">회원관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>회원관리</li>
						<li>회원관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">회원검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="emailAddrText" name="emailAddrText">
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>회원검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">회원유형</th>
							<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkMemberTypeAll" name="chkMemberTypeAll" type="checkbox" checked>
										<label for="chkMemberTypeAll">전체</label>
									</span>
								</li>
								<c:forEach var="memberTypeCode" items="${codeList.MEMBER_TYPE_CODE}" varStatus="status">
									<c:if test="${memberTypeCode.codeDtlNo ne CommonCode.MEMBER_TYPE_CODE_NONMEMBER}">
									<li>
										<span class="ui-chk">
											<input id="chkMemberType${memberTypeCode.codeDtlNo}" value="${memberTypeCode.codeDtlNo}"  custom="memberTypeCode" name="memberTypeCodes"  type="checkbox" checked="checked">
											<label for="chkMemberType${memberTypeCode.codeDtlNo}">${memberTypeCode.codeDtlName}</label>
										</span>
									</li>
									</c:if>
								</c:forEach>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkMemberTypeERP" name="chkMemberTypeERP" custom="memberTypeCode" type="checkbox" checked>
										<label for="chkMemberTypeERP">임직원</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
							<td></td>
							<th scope="row">회원상태</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="memberState" id="memberStatAll" value="" checked>
											<label for=memberStatAll>전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="memberState" id="radioCase02" value="01">
											<label for="radioCase02">정상회원</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="memberState" id="radioCase03" value="02">
											<label for="radioCase03">휴면예정회원</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">성별</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="genderType" id="radioGender01" value="" checked>
											<label for="radioGender01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="genderType" id="radioGender02" value="M">
											<label for="radioGender02">남자</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="genderType" id="radioGender03" value="F">
											<label for="radioGender03">여자</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<!-- s : 20190417 수정 // 회원예외유형 영역 수정 -->
							<th scope="row">회원예외유형</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="otsVipYn" id="chkClass04" value="Y">
											<label for="chkClass04">OTS VIP</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="blackListYn" id="chkClass05" value="Y">
											<label for="chkClass05">블랙리스트</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="resellerDoubtYn" id="chkClass06" value="Y">
											<label for="chkClass06">리셀러의심</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select id="dtmType" class="ui-sel" title="기간 검색 선택">
										<option value="">선택</option>
										<option value="01">회원가입일</option>
										<option value="02">최종로그인일</option>
									</select>
									<span class="date-box">
										<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0);" name="perYear" class="btn-sm btn-func">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">회원</th>
							<td class="input" colspan="4">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="memberSearchType" class="ui-sel" title="회원 정보 선택">
										<option value="01">회원이름</option>
										<option value="02">회원아이디</option>
										<option value="03">회원번호</option>
									</select>
									<input type="text" id="memberName" name="memberName" class="ui-input" title="이름입력" placeholder="이름입력" maxlength="10">
									<input type="text" id="hdphnBackNoText" name="hdphnBackNoText" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
									<input type="text" id="loginId" name="loginId" class="ui-input" title="아이디입력" placeholder="아이디입력">
									<input type="text" id="memberNo" name="memberNo" class="ui-input" title="회원번호입력" placeholder="회원번호입력" maxlength="10">
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">휴대폰번호</th>
							<td class="input">
								<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="-없이 전체 입력" maxlength="11">
							</td>
							<td></td>
							<th scope="row">이메일</th>
							<td class="input">
								<!-- S : email-box -->
								<span class="email-box">
									<span class="email-ip">
										<input type="text" id="emailAddr" class="ui-input" title="메일주소 아이디 입력" maxlength="30">
										<span class="txt">@</span>
										<input type="text" id="emailAddrDtl" class="ui-input mail-domain" title="메일주소 도메인 직접 입력">
									</span>
									<select id="selEmail" class="ui-sel domain-sel" title="메일주소 도메인 선택">
										<c:forEach var="emailInfo" items="${codeList.EMAIL_SITE}">
											<option value="<c:if test="${emailInfo.codeDtlName ne '직접입력'}">${emailInfo.codeDtlName}</c:if>">${emailInfo.codeDtlName}</option>
										</c:forEach>
									</select>
								</span>
								<!-- E : email-box -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<button type="button" id="btnSendTextMsg" class="btn-normal btn-link">SMS 보내기</button>
						<a href="#" id="searchBtn" name="searchBtn" class="btn-normal btn-func">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		</form>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">회원목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount" name="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="memberGrid" style="width:100%; height:429px;">
				
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/member/abcmart.member.member.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>