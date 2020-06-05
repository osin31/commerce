<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		// 관리자 상세 초기 탭페이지 설정 및 데이터 세팅
		abc.biz.system.admin.admin.initAdminDetailData();

		// 이메일 상세정보
		var emailText = '<c:out value="${detailInfo.emailAddrText}"/>';
		abc.biz.system.admin.admin.detailEmailSet(emailText);
		
		// 접근허용 아이피 영역 세팅
		var authSysType = '<c:out value="${detailInfo.authApplySystemType}"/>'
		abc.biz.system.admin.admin.detailAccessIpAreaSet(authSysType);

		// 탭 클릭
		$("#tabDetail").on("tabsactivate", function(event, ui) {
			abc.biz.system.admin.admin.tabHandler(event, ui);
		});

		<%-- 권한그룹명 변경 시 권한적용시스템 설정 --%>
		$("#authNo").change(function(){
			var authNo = $(this).val();
			var authName = $("#authNo option:selected").text();
			$("#authName").val(authName);
			abc.biz.system.admin.admin.authGroupChange(authNo);
		});

		<%-- 휴대폰 번호 키 체크 --%>
		$("#hdphnNoText").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});

		<%-- 비밀번호 초기화 --%>
		$("#resetBtn").click(function(){
			abc.biz.system.admin.admin.pswdReset();
		});

		<%-- 접근허용 아이피 추가 --%>
		$("#ipAddBtn").click(function(){
			abc.biz.system.admin.admin.addAccessIpText();
		});

		<%-- 접근허용 아이피 삭제 --%>
		$(document).on("click", ".btn-item-del", function(event){
			var idx = $(this).parent().index();
			var itemList = $(".item-list > li").eq(idx);
			itemList.remove();
		});

		<%-- 이메일 변경 시 세팅 --%>
		$("#selEmail").change(function(){
			var selEmailVal = $(this).val();
			abc.biz.system.admin.admin.changeEmail(selEmailVal);
		});

		<%-- 관리자 정보 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.system.admin.admin.updateAdmin();
		});

		<%-- 로그인이력 탭 목록개수 변경  --%>
		$("#loginPageCount").change(function(){
			abc.biz.system.admin.admin.DetailDoAction('loginSearch');
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

		<%-- 캘린더 버튼 설정(전체) --%>
		$("a[name^=perAll]").click(function(){
			abc.date.all(this);
		});

		<%-- 변경이력 조회 --%>
		$("#changeSearchBtn").click(function(){
			if(!abc.date.searchValidate()){
				return;
			}

			abc.biz.system.admin.admin.DetailDoAction('changeSearch');
		});
		
		//관리자 팝업 링크
		$(".adminPopLink").click(function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		//변경이력 초기화 버튼
		$("#changeHistoryFormReset").click(function(){
			$.form("#changeHistoryForm").reset();
			$("a[name^=per1Month]").trigger("click");
		});

	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 수정</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title"><c:out value="${detailInfo.loginId}"/>(<c:out value="${detailInfo.adminName}"/>) 관리자</h2>
				</div>
				<div class="fr">
					<span class="guide-text">최종 로그인 : <fmt:formatDate value="${userDetails.lastLoginDtm}" pattern="yyyy-MM-dd HH:mm:ss"/> 최종 로그인 IP <c:out value="${userDetails.lastLoginIpText}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tab-wrap -->
			<div class="tab-wrap"  id="tabDetail">
				<ul class="tabs">
					<li class="tab-item">
						<a href="#tabContent1" class="tab-link" id="tabAdminDefault">기본정보</a>
					</li>
					<li class="tab-item"><a href="#tabContent2" class="tab-link" id="tabLogin">로그인 이력</a></li>
					<li class="tab-item"><a href="#tabContent3" class="tab-link" id="tabHistory">변경 이력</a></li>
				</ul>

				<!------------------------------------------ S:tab_content1 --------------------------------------
				<!-- S : system/abcmart.system.admin.admin.js에서 상세 조회 값을 제어하기 위해 hidden 처리하여 값을 세팅 -->
				<input type="hidden" id="batchErrorVal" value='<c:out value="${detailInfo.batchErrorAlertYn}"/>'>
				<input type="hidden" id="memberInfoVal" value='<c:out value="${detailInfo.memberInfoMgmtYn}"/>'>
				<input type="hidden" id="longUnusedVal" value='<c:out value="${detailInfo.longUnusedYn}"/>'>
				<input type="hidden" id="pswdDscordVal" value='<c:out value="${detailInfo.pswdDscordYn}"/>'>
				<input type="hidden" id="pswdInitVal" value='<c:out value="${detailInfo.pswdInitYn}"/>'>
				<input type="hidden" id="authVal" value='<c:out value="${detailInfo.authNo}"/>'>
				<input type="hidden" id="useVal" value='<c:out value="${detailInfo.useYn}"/>'>
				<!-- E : system/abcmart.system.admin.admin.js에서 상세 조회 값을 제어하기 위해 hidden 처리하여 값을 세팅 -->

				<form id="adminDetailForm" name="adminDetailForm" method="post" onsubmit="return false;">
				<div id="tabContent1" class="tab-content">
					<input type="hidden" id="adminNo" name="adminNo" value='<c:out value="${detailInfo.adminNo}"/>'>
					<input type="hidden" id="authName" name="authName" value='<c:out value="${detailInfo.authName}"/>'>
					<input type="hidden" id="confirmLoginId" value="">
					<input type="hidden" id="emailAddrText" name="emailAddrText">
					<input type="hidden" id="accessIpText" name="accessIpText">
					<input type="hidden" id="viewType" name="viewType" value="U">

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">기본정보</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>기본정보</caption>
						<colgroup>
							<col style="width: 135px;">
							<col>
							<col style="width: 135px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">권한그룹명</th>
								<td class="input">
									<select id="authNo" name="authNo" class="ui-sel" title="권한그룹명 선택">
										<option value="">선택</option>
										<c:forEach var="authGroup" items="${authGroup}">
											<option value="${authGroup.authNo}">${authGroup.authName}</option>
										</c:forEach>
									</select>
								</td>
								<th scope="row">권한적용 시스템</th>
								<td id="authSystemType"><c:out value="${detailInfo.authApplySystemType}"/></td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">관리자 id</span>
								</th>
								<td colspan="3"><c:out value="${detailInfo.loginId}"/></td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">관리자 이름</span>
								</th>
								<td class="input" colspan="3">
									<input type="text" id="adminName" name="adminName" class="ui-input" title="관리자 이름 입력" value='<c:out value="${detailInfo.adminName}"/>' maxlength="20">
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">비밀번호</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#" id="resetBtn" class="btn-sm btn-func">비밀번호 초기화</a>
									<!-- E : 20190114 수정 -->
									<c:if test="${detailInfo.pswdInitYn eq 'N'}">
										<span class="text">(비밀번호 초기화 여부 : <span class="reset-check"><c:out value="${detailInfo.pswdInitYn}"/></span>)</span>
									</c:if>
									<c:if test="${detailInfo.pswdInitYn eq 'F'}">
										<span class="text">(비밀번호 초기화 여부 : <span class="reset-check"><c:out value="N"/></span>)</span>
									</c:if>
									<c:if test="${detailInfo.pswdInitYn eq 'Y'}">
										<span class="text">(비밀번호 초기화 여부 : <span class="reset-check"><c:out value="${detailInfo.pswdInitYn}"/></span> 비밀번호 변경기한 : <span class="change-term"><fmt:formatDate value="${detailInfo.pswdChngDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></span>)</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">이메일</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : email-box -->
									<span class="email-box">
										<span class="email-ip">
											<input type="text" id="emailAddr" name="emailAddr" class="ui-input" title="메일주소 아이디 입력" maxlength="50">
											<span class="txt">@</span>
											<input type="text" id="emailAddrDtl" name="emailAddrDtl" class="ui-input mail-domain" title="메일주소 도메인 직접 입력" maxlength="50">
										</span>
										<select id="selEmail" name="selEmail" class="ui-sel domain-sel" title="메일주소 도메인 선택">
											<c:forEach var="emailSiteCode" items="${emailSiteCode}">
												<option value="<c:if test="${emailSiteCode.codeDtlName ne '직접입력'}">${emailSiteCode.codeDtlName}</c:if>">${emailSiteCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<!-- E : email-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">휴대폰번호</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="- 없이 입력" value="<c:out value="${detailInfo.hdphnNoText}"/>" maxlength="15">

											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="batchErrorAlertYn" name="batchErrorAlertYn" type="checkbox" value="Y">
												<label for="batchErrorAlertYn">배치에러 SMS발송</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">개인정보 취급여부</span>
								</th>
								<td class="input" colspan="3">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="memberInfoMgmtY" name="memberInfoMgmtYn" type="radio" value="Y">
												<label for="memberInfoMgmtY">가능</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="memberInfoMgmtN" name="memberInfoMgmtYn" type="radio" value="N">
												<label for="memberInfoMgmtN">불가능</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr id="accessIpArea">
								<th scope="row">접근 허용 아이피</th>
								<td class="input" colspan="3">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" id="accessIp" class="ui-input" title="접근 허용 아이피 입력">
										<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
										<a href="#" id="ipAddBtn" class="btn-sm btn-func">추가</a>
										<!-- E : 20190114 수정 -->
									</span>
									<!-- E : ip-text-box -->

									<!-- S : item-list -->
									<ul class="item-list">
										<c:forEach var="accessIpData" items="${accessIpData}">
											<li>
												<input type="hidden" name="accessIpTexts" value="${accessIpData.accessIpText}">
												<span class="subject">${accessIpData.accessIpText}</span>
												<button type="button" class="btn-item-del">
													<span class="ico ico-item-del"><span class="offscreen">IP 삭제</span></span>
												</button>
											</li>
										</c:forEach>
									</ul>
									<!-- E : item-list -->
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">관리자 계정</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>관리자 계정</caption>
						<colgroup>
							<col style="width: 140px;">
							<col>
							<col style="width: 140px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">장기미사용</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="longUnusedN" name="longUnusedYn" type="radio" value="N">
												<label for="longUnusedN">장기미사용해제</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="longUnusedY" name="longUnusedYn" type="radio" value="Y">
												<label for="longUnusedY">장기미사용</label>
											</span>
										</li>
									</ul>
								</td>
								<th scope="row">비밀번호 불일치 블록</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pswdDscordY" name="pswdDscordYn" type="radio" value="Y">
												<label for="pswdDscordY">블록</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pswdDscordN" name="pswdDscordYn" type="radio" value="N">
												<label for="pswdDscordN">블록해제</label>
											</span>
										</li>
									</ul>
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
												<input id="useY" name="useYn" type="radio" value="Y">
												<label for="useY">사용</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="useN" name="useYn" type="radio" value="N">
												<label for="useN">사용안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0)" class="adminPopLink" data-admin="${detailInfo.rgsterNo}"><c:out value="${detailInfo.rgstName}"/></a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${detailInfo.rgstDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:void(0)" class="adminPopLink" data-admin="${detailInfo.moderNo}"><c:out value="${detailInfo.moderName}"/></a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${detailInfo.modDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</tbody>
					</table>
					<!-- S : tbl-row -->

					<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
					<!-- S : tbl-desc-wrap -->
					<!-- <div class="tbl-desc-wrap">
						<div class="fl">
							<a href="#" class="btn-normal btn-del">삭제</a>
						</div>
					</div> -->
					<!-- E : tbl-desc-wrap -->
					<!-- E : 20190117 추가  -->

					<!-- S : window-btn-group -->
					<div class="window-btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" id="saveBtn" class="btn-normal btn-save">저장</a>
						<!-- E : 20190114 수정 -->
					</div>
					<!-- E : window-btn-group -->
				</div>
				</form>
				<!------------------------------------------ E:tab_content1 ---------------------------------------->

				<!------------------------------------------ S:tab_content2 ---------------------------------------->
				<form id="loginHistoryForm" name="loginHistoryForm" method="post" onsubmit="return false;">
				<input type="hidden" id="adminNo" name="adminNo" value='<c:out value="${detailInfo.adminNo}"/>'>

				<div id="tabContent2" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">로그인 이력</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="loginPageCount">목록개수</label>
							<select id="loginPageCount" name="loginPageCount" class="ui-sel">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="loginGrid" style="width:100%; height:429px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				</form>
				<!------------------------------------------ E:tab_content2 ---------------------------------------->

				<!------------------------------------------ S:tab_content3 ---------------------------------------->
				<form id="changeHistoryForm" name="changeHistoryForm" method="post" onsubmit="return false;">
				<input type="hidden" id="adminNo" name="adminNo" value='<c:out value="${detailInfo.adminNo}"/>'>
				<div id="tabContent3" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">변경 이력 검색</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>변경 이력 검색</caption>
								<colgroup>
									<col style="width:7%;">
									<col style="width:17%">
									<col style="width:79px;">
									<col style="width:7%;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">구분</th>
										<td class="input">
											<select id="chngField" name="chngField" class="ui-sel" title="구분 선택">
												<option value="">전체</option>
												<option value="authName">권한그룹</option>
												<option value="adminName">관리자 이름</option>
												<option value="">비밀번호</option>
												<option value="memberInfoMgmtYn">개인정보 취급여부</option>
												<option value="accessIpText">접근 허용 아이피</option>
											</select>
										</td>
										<td></td>
										<th scope="row">수정일</th>
										<td class="input">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
												</span>
												<span class="btn-group">
													<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
													<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
													<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
													<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="javascript:void(0);" name="perAll" class="btn-sm btn-func">전체</a>
													<!-- E : 20190114 수정 -->
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
								</tbody>
							</table>

							<div class="confirm-box">
								<div class="fl">
									<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
									<a href="#" id="changeHistoryFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
									<!-- E : 20190114 수정 -->
								</div>
								<div class="fr">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#" id="changeSearchBtn" class="btn-normal btn-func">검색</a>
									<!-- E : 20190114 수정 -->
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					<!-- E : search-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">변경 이력</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="changePageCount">목록개수</label>
								<select id="changePageCount" name="changePageCount" class="ui-sel">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="changeGrid" style="width:100%; height:429px;">

						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				</form>
				<!------------------------------------------ E:tab_content3 ---------------------------------------->
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>