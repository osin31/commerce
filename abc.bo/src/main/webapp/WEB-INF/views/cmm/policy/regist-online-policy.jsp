<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	var plcySeq = '<c:out value="${param.plcySeq}"/>';
	
	$(document).ready(function(){
		$("#plcyStartYmd").datepicker("option", "minDate", new Date());
		
		<%-- 혜택 영역 '추가' 버튼 클릭 --%>
		$(document).on("click","[id^='btnAdd'],[id=addBenefitBtn]",function(){
			abc.biz.system.onlinepolicy.getBenefitArea('');
		});

		<%-- 초기화 --%>
		$("#init").click(function(f) {
			abc.biz.system.onlinepolicy.init();
		});

		<%-- 목록 --%>
		$("#toList").click(function(f) {
			abc.biz.system.onlinepolicy.getOnlinePolicyList();
		});

		<%-- 저장 --%>
		$("#create").click(function(f) {
			abc.biz.system.onlinepolicy.setBenefitCpnCreate();
		});

		<%-- 혜택의 쿠폰 '검색' 버튼 클릭 시 --%>
		$(document).on("click","[id^='benefit_cpn_search']",function(){
			abc.biz.system.onlinepolicy.getCouponData($(this).val());
		});

		<%-- 혜택 영역의 삭제 버튼 클릭 시 --%>
		$(document).on("click","[id^='btnDel']",function(){
			abc.biz.system.onlinepolicy.setDeleteArea($(this).val());
		});

		<%-- focusout 숫자만 남기기 --%>
		$(document).on("focusout", '[id^=buyAmt],[id^=buyQty]', function(){
			var value = parseInt($(this).val()).toString();
			$(this).val(value.replace(/[^0-9]/g,""));
		});

		<%-- keyup 숫자만 남기기 --%>
		$(document).on("keyup", '[id^=buyAmt],[id^=buyQty]', function(){
			var value = parseInt($(this).val()).toString();
			$(this).val(value.replace(/[^0-9]/g,""));
		});
		
		<%-- 온라인 회원가입 쿠폰 --%>
		$("#onlnMemberJoinCpnNoBtn, #mbshpMemberJoinCpnNoBtn, #birthDayCpnNoBtn, #soldOutCmpnsCpnNoBtn, #artAppLoginCpnNoBtn, #otsAppLoginCpnNoBtn").click(function(f) {
			var couponBtnObj = $(this);
			abc.biz.system.onlinepolicy.getCouponSearch(couponBtnObj);
		});
		
		<%-- 온라인 회원가입 쿠폰 삭제--%>
		$(document).on("click", '[id=onlnMemberJoinCpnNoDel],[id=mbshpMemberJoinCpnNoDel],[id=birthDayCpnNoDel],[id=soldOutCmpnsCpnNoDel],[id=artAppLoginCpnNoDel],[id=otsAppLoginCpnNoDel]', function(){
			var couponAreaObj = $(this);
			abc.biz.system.onlinepolicy.deleteOnlineCoupon(this);
        });

		<%-- focusout 숫자만 남기기 --%>
		$("#memberJoinPointAmt, #firstBuyPointAmt, #buyPointRate, #annvrFirstBuyPointMultCount, #minUsePointAmt, #pointExtnYearCount, #prdtRvwPointAmt, #photoPrdtRvwPointAmt, #monthBestRvwCount, #monthBestRvwPointAmt, #onlnSaveTermCount, #oflnSaveTermCount").focusout(function(){
			var value = parseInt($(this).val()).toString();
			$(this).val(value.replace(/[^0-9]/g,""));
		});

		<%-- keyup 숫자만 남기기 --%>
		$("#memberJoinPointAmt, #firstBuyPointAmt, #buyPointRate, #annvrFirstBuyPointMultCount, #minUsePointAmt, #pointExtnYearCount, #prdtRvwPointAmt, #photoPrdtRvwPointAmt, #monthBestRvwCount, #monthBestRvwPointAmt, #onlnSaveTermCount, #oflnSaveTermCount").keyup(function(){
			var value = parseInt($(this).val()).toString();
			$(this).val(value.replace(/[^0-9]/g,""));
		});
		
		<%-- 혜택 영역 '삭제' 버튼 클릭 --%>
		$(document).on("click","[id^='cpn_delete']",function(){
			abc.biz.system.onlinepolicy.setBenefitCpnDelete($(this).val());
		});
	});
</script>

	<!-- S : container -->
	<form id="onlinePolicyForm" name="onlinePolicyForm" method="post" onsubmit="return false;">
	<input type="hidden" id="plcySeq" name="plcySeq" value="">
	<input type="hidden" id="couponIssueCount" name="couponIssueCount" value="<%=Const.COUPON_ISSUE_FIVE%>">
	<input type="hidden" id="benefitIndex" name="benefitIndex" value="0">
	<input type="hidden" id="plcyDtlSeq" name="plcyDtlSeq" value="">
	<input type="hidden" id="pageCount" name="pageCount" value="<c:out value="${param.pageCount}"/>">
	
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">혜택 관리</h2>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>기본설정</li>
							<li>혜택 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">온라인 회원 혜택 기본설정</h3>
				</div>
				<div class="fr">
					<div class="btn-group">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="init"><i class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>온라인 회원 혜택 기본설정</caption>
				<colgroup>
					<col style="width: 160px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">온라인 회원가입쿠폰</span>
						</th>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="onlnMemberJoinCpnNoArea">
								<li class="btn">
									<button class="btn-sm btn-link" id="onlnMemberJoinCpnNoBtn" value="onlnMemberJoinCpnNo">검색</button>
									<input type="hidden" id="onlnMemberJoinCpnNo" name="onlnMemberJoinCpnNo" value="">
								</li>
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
					<h3 class="content-title">통합멤버십 회원 혜택 기본설정</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>통합멤버십 회원 혜택 기본설정</caption>
				<colgroup>
					<col style="width: 160px;">
					<col>
					<col style="width: 160px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<!-- 20190109 삭제 // 기획 수정으로 적용 기간 영역이 삭제되었습니다. -->
						<th scope="row">
							<span class="th-required">회원가입</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input num-unit10000000" title="포인트 입력" maxlength="8" id="memberJoinPointAmt" name="memberJoinPointAmt">
								<span class="text">Point</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
						<th scope="row">
							<span class="th-required">첫 구매</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input num-unit10000000" title="포인트 입력" maxlength="8" id="firstBuyPointAmt" name="firstBuyPointAmt">
								<span class="text">Point</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">구매 포인트</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<span class="text">구매 금액의</span>
								<input type="text" class="ui-input num-unit100" title="포인트 비율 입력" maxlength="2" id="buyPointRate" name="buyPointRate">
								<span class="text">% Point</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
						<th scope="row">
							<span class="th-required">기념일 첫 구매</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="annvrFirstBuyPointPayYn_Y" name="annvrFirstBuyPointPayYn" type="radio" checked="checked" value="Y">
										<label for="annvrFirstBuyPointPayYn_Y">Point</label>
									</span>
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" class="ui-input num-unit10" title="배 수 입력" maxlength="2" id="annvrFirstBuyPointMultCount" name="annvrFirstBuyPointMultCount">
										<span class="text">배 지급</span>
									</span>
									<!-- E : ip-text-box -->
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="annvrFirstBuyPointPayYn_N" name="annvrFirstBuyPointPayYn" type="radio" value="N">
										<label for="annvrFirstBuyPointPayYn_N">사용안함</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">최소 사용 포인트</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input num-unit10000000" title="포인트 입력" maxlength="8" id="minUsePointAmt" name="minUsePointAmt">
								<span class="text">Point</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
						<th scope="row">
							<span class="th-required">포인트 자동 소멸 년 수</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input num-unit1" title="년 수 입력" maxlength="2" id="pointExtnYearCount" name="pointExtnYearCount">
								<span class="text">년</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">상품 구매 후기</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box full-size">
								<span class="text">일반후기</span>
								<input type="text" class="ui-input num-unit100000" title="포인트 입력" maxlength="8" id="prdtRvwPointAmt" name="prdtRvwPointAmt">
								<span class="text">Point / 포토후기</span>
								<input type="text" class="ui-input num-unit100000" title="포인트 입력" maxlength="8" id="photoPrdtRvwPointAmt" name="photoPrdtRvwPointAmt">
								<span class="text">Point</span>
							</span>
							<!-- E : ip-text-box -->

							<!-- S : ip-text-box -->
							<span class="ip-text-box full-size">
								<span class="text">월별 베스트 후기</span>
								<input type="text" class="ui-input num-unit100000" title="명 수 입력" maxlength="5" id="monthBestRvwCount" name="monthBestRvwCount">
								<span class="text">명에게</span>
								<input type="text" class="ui-input num-unit100000" title="포인트 입력" maxlength="8" id="monthBestRvwPointAmt" name="monthBestRvwPointAmt">
								<span class="text">Point</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
						<th scope="row">
							<span class="th-required">포인트 사후 적립기간</span>
						</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<span class="text">온라인 배송완료</span>
								<input type="text" class="ui-input num-unit10" title="일 수 입력" maxlength="5" id="onlnSaveTermCount" name="onlnSaveTermCount">
								<span class="text">일 / 오프라인</span>
								<input type="text" class="ui-input num-unit10" title="일 수 입력" maxlength="8" id="oflnSaveTermCount" name="oflnSaveTermCount">
								<span class="text">일</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">통합 멤버십<br/> 회원가입 쿠폰</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : item-list -->
							<ul class="item-list" id="mbshpMemberJoinCpnNoArea">
								<!-- S : 20190109 수정 // btn 클래스 추가 -->
								<li class="btn">
									<button type="button" class="btn-sm btn-link" id="mbshpMemberJoinCpnNoBtn" value="mbshpMemberJoinCpnNo">검색</button>
									<input type="hidden" id="mbshpMemberJoinCpnNo" name="mbshpMemberJoinCpnNo" value="">
								</li>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">생일쿠폰</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : item-list -->
							<ul class="item-list" id="birthDayCpnNoArea">
								<li class="btn">
									<button type="button" class="btn-sm btn-link" id="birthDayCpnNoBtn" value="birthDayCpnNo">검색</button>
									<input type="hidden" id="birthDayCpnNo" name="birthDayCpnNo" value="">
								</li>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<div class="tbl-desc-wrap" id="addBenefitArea">
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-link" id="addBenefitBtn">조건&혜택 추가</a>
				</div>
			</div>

			<!-- S : content-header -->
			<div class="content-header" id="commentArea" style="display:none;">
				<div class="fr">
					<span class="guide-text">* 조건 설정 시 매월 1일 최근 6개월 온라인 구매실적 기준으로 지급</span>
				</div>
			</div>
			<!-- E : content-header -->
			
			<div class="tbl-append-wrap" id='benefitArea'></div>	<%-- 혜택영역 html이 append 되는 영역 --%>
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">온라인&통합멤버십 회원 추가설정</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>온라인&통합멤버십 회원 추가설정</caption>
				<colgroup>
					<col style="width: 160px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">A-RT APP 설치 <br/>첫 로그인 쿠폰</th>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="artAppLoginCpnNoArea">
								<li class="btn">
									<button class="btn-sm btn-link" id="artAppLoginCpnNoBtn" value="artAppLoginCpnNo">검색</button>
									<input type="hidden" id="artAppLoginCpnNo" name="artAppLoginCpnNo" value="">
								</li>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">OTS APP 설치 <br/>첫 로그인 쿠폰</th>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="otsAppLoginCpnNoArea">
								<li class="btn">
									<button class="btn-sm btn-link" id="otsAppLoginCpnNoBtn" value="otsAppLoginCpnNo">검색</button>
									<input type="hidden" id="otsAppLoginCpnNo" name="otsAppLoginCpnNo" value="">
								</li>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
					
					
					<tr>
						<th scope="row">품절보상 쿠폰</th>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="soldOutCmpnsCpnNoArea">
								<li class="btn">
									<button class="btn-sm btn-link" id="soldOutCmpnsCpnNoBtn" value="soldOutCmpnsCpnNo">검색</button>
									<input type="hidden" id="soldOutCmpnsCpnNo" name="soldOutCmpnsCpnNo" value="">
								</li>
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption></caption>
				<colgroup>
					<col style="width: 160px;">
					<col>
					<col style="width: 160px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">시행일자</span>
						</th>
						<td class="input" colspan="3">
							<span class="date-box">
								<input type="text" id="plcyApplyYmd" name="plcyApplyYmd" data-role="datepicker" value="<c:out value="${DATA.plcyApplyYmd}"/>" class="ui-cal js-ui-cal" title="시행 일자 선택" />
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">작성자</th>
						<td></td>
						<th scope="row">작성일시</th>
						<td></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<div class="fr">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0);" class="btn-normal btn-link" id="toList">목록</a>
					<!-- E : 20190114 수정 -->
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<a href="javascript:void(0);" id="create" class="btn-lg btn-save">저장</a>
			</div>
			<!-- E : content-bottom -->
		</div>
	</div>
	</form>
	<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.onlinepolicy.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>	