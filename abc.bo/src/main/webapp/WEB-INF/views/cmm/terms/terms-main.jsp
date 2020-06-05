<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function(){
		
		//약관설정 그리드 
		abc.biz.cmm.terms.termsOfUseSheet();
		
		$("#tabDiv").on("tabsactivate", function(event, ui) {
			if (ui.newTab.prevObject.attr('codevalue') === "<c:out value='${termsTypeCode.get(1).getCodeDtlNo()}'/>") {
				if (typeof privacySheet === "undefined") {
					abc.biz.cmm.terms.privacySheet();
					abc.biz.cmm.terms.doAction('readPrivacyGrid');
				}
			}else if(ui.newTab.prevObject.attr('codevalue') === "<c:out value='${termsTypeCode.get(2).getCodeDtlNo()}'/>"){
				if (typeof signUpSheet === "undefined") {
					abc.biz.cmm.terms.signUpSheet();
					$("#signUpSearch").trigger('click');
				}
			}else if(ui.newTab.prevObject.attr('codevalue') === "<c:out value='${termsTypeCode.get(3).getCodeDtlNo()}'/>"){
				if (typeof orderSheet === "undefined") {
					abc.biz.cmm.terms.orderSheet();
					$("#orderSearch").trigger('click');
				}
			}else if(ui.newTab.prevObject.attr('codevalue') === "<c:out value='${termsTypeCode.get(0).getCodeDtlNo()}'/>"){
				$("#btnSelectTermsOfUse").trigger('click');
			}

		});
		//페이지 목록 개수 클릭시
		$("#usePageCount").change(function(){
			$("#btnSelectTermsOfUse").trigger('click');
		});
		//페이지 목록 개수 클릭시
		$("#privacyPageCount").change(function(){
			abc.biz.cmm.terms.doAction('readPrivacyGrid');
		});
		//페이지 목록 개수 클릭시
		$("#signupPageCount").change(function(){
			$("#signUpSearch").trigger('click');
		});
		//페이지 목록 개수 클릭시
		$("#orderPageCount").change(function(){
			$("#orderSearch").trigger('click');
		});
		abc.biz.cmm.terms.tabFocus();
		
		<c:choose>
			<c:when test="${not empty param.useListPageNum}">
				$('#usePageCount').val('${param.usePageCount}');
				$("#termsDtlCode").val('${param.useTermsDtlCode}');
				$("#btnSelectTermsOfUse").trigger('click');
			</c:when>
			<c:when test="${not empty param.privacyListPageNum}">
				$('#privacyPageCount').val('${param.privacyPageCount}');
				abc.biz.cmm.terms.doAction('readPrivacyGrid');
			</c:when>
			<c:when test="${not empty param.signupListPageNum}">
				$('#signupPageCount').val('${param.signupPageCount}');
				$("#signUpForm input:radio[name='termsDtlCode'][value='${param.signupTermsDtlCode}']").prop('checked', true);
				$("#signUpSearch").trigger('click');
			</c:when>
			<c:when test="${not empty param.orderListPageNum}">
				$('#orderPageCount').val('${param.orderPageCount}');
				$("#orderForm input:radio[name='termsDtlCode'][value='${param.orderTermsDtlCode}']").prop('checked', true);
				$("#orderSearch").trigger('click');
			</c:when>
			<c:otherwise>
				$("#btnSelectTermsOfUse").trigger('click');
			</c:otherwise>
		</c:choose>
		
		
	});
	
	<%-- 이용약관 그리드 Click 이벤트 --%>
	function termsOfUseGrid_OnClick(Row, Col, Value) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (termsOfUseGrid.ColSaveName(Col) == "termsApplyYmd") {
				var termsSeq = termsOfUseGrid.GetCellValue(Row, "termsSeq");
				var termsDtlCode = termsOfUseGrid.GetCellValue(Row, "termsDtlCode");
				var termsDtlSeq = termsOfUseGrid.GetCellValue(Row, "termsDtlSeq");
				var termsTypeCode = termsOfUseGrid.GetCellValue(Row, "termsTypeCode");
				abc.biz.cmm.terms.readUseTermsDetail(termsSeq, termsDtlCode, termsDtlSeq);
			}
		}
	}
	
	<%-- 개인정보 취급방침 그리드 Click 이벤트 --%>
	function privacyGrid_OnClick(Row, Col, Value) {
		if ( Row != 0) {
			if (privacyGrid.ColSaveName(Col) == "termsApplyYmd") {
				var termsSeq = privacyGrid.GetCellValue(Row, "termsSeq");
				var termsDtlCode = privacyGrid.GetCellValue(Row, "termsDtlCode");
				var termsDtlSeq = privacyGrid.GetCellValue(Row, "termsDtlSeq");
				abc.biz.cmm.terms.readPrivacyTermsDetail(termsSeq, termsDtlCode, termsDtlSeq);
			}
		}
	}
	
	<%-- 회원가입동의 약관  그리드 Click 이벤트 --%>
	function signUpGrid_OnClick(Row, Col, Value) {
		if ( Row != 0) {
			if (signUpGrid.ColSaveName(Col) == "termsApplyYmd") {
				var termsSeq = signUpGrid.GetCellValue(Row, "termsSeq");
				var termsDtlCode = signUpGrid.GetCellValue(Row, "termsDtlCode");
				var termsDtlSeq = signUpGrid.GetCellValue(Row, "termsDtlSeq");
				abc.biz.cmm.terms.readSignUpTermsDetail(termsSeq, termsDtlCode, termsDtlSeq);
			}
		}
	}
	
	<%-- 주문동의 약관  그리드 Click 이벤트 --%>
	function orderGrid_OnClick(Row, Col, Value) {
		if ( Row != 0) {
			if (orderGrid.ColSaveName(Col) == "termsApplyYmd") {
				var termsSeq = orderGrid.GetCellValue(Row, "termsSeq");
				var termsDtlCode = orderGrid.GetCellValue(Row, "termsDtlCode");
				var termsDtlSeq = orderGrid.GetCellValue(Row, "termsDtlSeq");
				abc.biz.cmm.terms.readOrderTermsDetail(termsSeq, termsDtlCode, termsDtlSeq);
			}
		}
	}
	
	
	
	
</script>
<body>
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">약관설정</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>기본설정</li>
							<li>약관설정</li>
							<!-- DESC : 3depth 메뉴명 하단 탭별 활성화시 tab-item명과 동일하게 지정해야합니다. -->
							<li>이용약관 설정</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : tab-wrap -->
			<div class="tab-wrap" id="tabDiv">
				<input type="hidden" id="currentPage">
				<ul class="tabs">
					<c:forEach var="list" items="${termsTypeCode}" varStatus="idx">
						<li class="tab-item"><a href="#tabContent${idx.count}" codevalue="${list.getCodeDtlNo()}" class="tab-link"><c:out value="${list.getCodeDtlName()}"></c:out></a></li>
					</c:forEach>
				</ul>
				<!-- S:tab_content1 -->
				<div id="tabContent1" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">이용약관 검색</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="termsOfUseForm" name="termsOfUseForm" >
					<input type="hidden" id="useListPageNum" name="useListPageNum" value="${param.useListPageNum}"/>
						<div class="search-wrap">
							<div class="search-inner">
								<table class="tbl-search">
									<caption>이용약관 검색영역</caption>
									<colgroup>
										<col style="width:15%;">
										<col>
										<col style="width:79px;">
										<col style="width:15%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">약관구분</th>
											<td class="input">
												<input type="hidden" id="useTermsTypeCode" name="termsTypeCode" value="${termsTypeCode.get(0).getCodeDtlNo()}">
												<select class="ui-sel" title="약관구분 선택" id="termsDtlCode" name="termsDtlCode">
														<option value="all">전체</option>
													<c:forEach items="${termsUseCode}" var="divisionList">
														<option value="${divisionList.getCodeDtlNo()}">${divisionList.getCodeDtlName()}</option>
													</c:forEach>
												</select>
											</td>
											<td></td>
										</tr>
									</tbody>
								</table>
	
								<div class="confirm-box">
									<div class="fl">
										<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
										<a href="javascript:void(0)" value="#termsOfUseForm" id="termsOfUseReload" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
										<!-- E : 20190114 수정 -->
									</div>
									<div class="fr">
										<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
										<a href="javascript:void(0)" id="btnSelectTermsOfUse" class="btn-normal btn-func">검색</a>
										<!-- E : 20190114 수정 -->
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
							<h3 class="content-title">이용약관 설정 내역 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="usePageCount">목록개수</label>
								<select class="ui-sel" id="usePageCount" name="usePageCount" >
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
						<div class="fr">
							<!-- DESC : html/common/BO-CM-15005.html 파일 확인 해주세요. -->
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="javascript:void(0)" id="btnCreateTermsOfUse" value="<c:out value='${termsTypeCode.get(0).getCodeDtlNo()}'/>" class="btn-sm btn-link">등록</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="termsOfUseGrid" style="width:100%; height:429px;"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content1 -->
				
				<!-- S:tab_content2 -->
				<div id="tabContent2" class="tab-content">

					<!-- S : search-wrap -->
					<form id="privacyForm" name="privacyForm" method="post" onsubmit="return false;">
						<input type="hidden" id="privacyTermsTypeCode" name="termsTypeCode" value="${termsTypeCode.get(1).getCodeDtlNo()}">
						<input type="hidden" id="privacyListPageNum" name="privacyListPageNum" value="${param.privacyListPageNum}"/>
					</form>
					<!-- E : search-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">개인정보 취급방침 변경 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="privacyPageCount">목록개수</label>
								<select class="ui-sel" id="privacyPageCount" name="privacyPageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
						<div class="fr">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="#" id="btnCreatePrivacy" value="<c:out value='${termsTypeCode.get(1).getCodeDtlNo()}'/>" class="btn-sm btn-link">등록</a>
							<input type="hidden" id="privacyCode" value="<c:out value='${privacyCodeList.get(0).getCodeDtlNo()}'/>">
							<!-- E : 20190114 수정 -->
						</div> 
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="privacyGrid" style="width:100%; height:429px;"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content2 -->
				
				<!-- S:tab_content3 -->
				<div id="tabContent3" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">회원가입 동의 검색</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="signUpForm" name="signUpForm" method="post" onsubmit="return false;">
						<input type="hidden" id="signUpTermsTypeCode" name="termsTypeCode" value="${termsTypeCode.get(2).getCodeDtlNo()}">
						<input type="hidden" id="signupListPageNum" name="signupListPageNum" value="${param.signupListPageNum}"/>
						<div class="search-wrap">
							<div class="search-inner">
								<table class="tbl-search">
									<caption>회원가입 동의 검색</caption>
									<colgroup>
										<col style="width:13%;">
										<col>
										<col style="width:79px;">
										<col style="width:13%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">회원구분</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_all" name="termsDtlCode" type="radio" value="all" checked="checked">
															<label for="termsDtlCode_all">전체</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_memberShip" value="${signUpCodeList.get(1).getCodeDtlNo()}" name="termsDtlCode" type="radio">
															<label for="termsDtlCode_memberShip"><c:out value="${signUpCodeList.get(1).getCodeDtlName()}"/></label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_online" value="${signUpCodeList.get(0).getCodeDtlNo()}" name="termsDtlCode" type="radio">
															<label for="termsDtlCode_online"><c:out value="${signUpCodeList.get(0).getCodeDtlName()}"/></label>
														</span>
													</li>
												</ul>
											</td>
											<td></td>
										</tr>
									</tbody>
								</table>
	
								<div class="confirm-box">
									<div class="fl">
										<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
										<a href="javascript:void(0)" class="btn-sm btn-func" id="signUpReload"><i class="ico ico-refresh"></i>초기화</a>
										<!-- E : 20190114 수정 -->
									</div>
									<div class="fr">
										<div class="fr">
											<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
											<a href="javascript:void(0)" id="signUpSearch" class="btn-normal btn-func">검색</a>
											<!-- E : 20190114 수정 -->
										</div>
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
							<h3 class="content-title">회원가입 동의 변경 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="signupPageCount">목록개수</label>
								<select class="ui-sel" id="signupPageCount" name="signupPageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
						<div class="fr">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="#" id="btnCreateSignUp" value="<c:out value='${termsTypeCode.get(2).getCodeDtlNo()}'/>" class="btn-sm btn-link">등록</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="signUpGrid" style="width:100%; height:429px;"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content3 -->
				
				<!-- S:tab_content4 -->
				<div id="tabContent4" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">주문 동의 검색</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="orderForm" name="orderForm" method="post" onsubmit="return false;">
						<input type="hidden" id="orderTermsTypeCode" name="termsTypeCode" value="${termsTypeCode.get(3).getCodeDtlNo()}">
						<input type="hidden" id="orderListPageNum" name="orderListPageNum" value="${param.orderListPageNum}"/>
						<div class="search-wrap">
							<div class="search-inner">
								<table class="tbl-search">
									<caption>주문 동의 검색</caption>
									<colgroup>
										<col style="width:15%;">
										<col>
										<col style="width:79px;">
										<col style="width:15%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">회원구분</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_memall" name="termsDtlCode" value="all" type="radio" checked="checked">
															<label for="termsDtlCode_memall">전체</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_member" name="termsDtlCode" value="${orderCodeList.get(0).getCodeDtlNo()}" type="radio">
															<label for="termsDtlCode_member"><c:out value="${orderCodeList.get(0).getCodeDtlName()}"/></label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="termsDtlCode_noMember" name="termsDtlCode" value="${orderCodeList.get(1).getCodeDtlNo()}"  type="radio">
															<label for="termsDtlCode_noMember"><c:out value="${orderCodeList.get(1).getCodeDtlName()}"/></label>
														</span>
													</li>
												</ul>
											</td>
											<td></td>
										</tr>
									</tbody>
								</table>
	
								<div class="confirm-box">
									<div class="fl">
										<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
										<a href="javascript:void(0)" id="orderReload" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
										<!-- E : 20190114 수정 -->
									</div>
									<div class="fr">
										<div class="fr">
											<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
											<a href="javascript:void(0)" id="orderSearch" class="btn-normal btn-func">검색</a>
											<!-- E : 20190114 수정 -->
										</div>
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
							<h3 class="content-title">주문 동의 변경 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="orderPageCount">목록개수</label>
								<select class="ui-sel" id="orderPageCount" name="orderPageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
						<div class="fr">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="#" id="btnCreateOrder" value="<c:out value='${termsTypeCode.get(3).getCodeDtlNo()}'/>" class="btn-sm btn-link">등록</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="orderGrid" style="width:100%; height:429px;"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content4 -->
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
	<!-- E : container -->

</body>
<script src="/static/common/js/biz/cmm/abcmart.cmm.terms.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
