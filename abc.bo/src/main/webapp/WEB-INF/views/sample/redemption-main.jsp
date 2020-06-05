<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(function() {
		<%-- 환불 목록 초기 세팅. --%>
		abc.biz.claim.redemptionList.initRedemptionListSheet();
		
		
	});

</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">환수 금액 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>환수 금액 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">환수 클레임건 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>환수 클레임건 검색</caption>
					<colgroup>
						<col style="width: 110px;">
						<col>
						<col style="width: 79px">
						<col style="width: 110px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">사이트</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoSiteModule" id="rdoSite01" checked>
											<label for="rdoSite01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoSiteModule" id="rdoSite02" >
											<label for="rdoSite02">통합몰</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoSiteModule" id="rdoSite03" >
											<label for="rdoSite03">OTS</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">클레임구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoClaimModule" id="rdoClaim01" checked>
											<label for="rdoClaim01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoClaimModule" id="rdoClaim02" >
											<label for="rdoClaim02">교환</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoClaimModule" id="rdoClaim03" >
											<label for="rdoClaim03">환불</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoClaimModule" id="rdoClaim04" >
											<label for="rdoClaim04">취소</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoClaimModule" id="rdoClaim05" >
											<label for="rdoClaim05">반품</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">주문</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" title="유형 선택">
										<option>주문자명</option>
										<option>주문자 아이디</option>
										<option>휴대폰번호</option>
									</select>
									<input type="text" class="ui-input" title="검색어 입력">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<td></td>
							<th scope="row">주문번호</th>
							<td class="input">
								<input type="text" class="ui-input" title="주문번호 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">수급상태</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoPaymentModule" id="rdoPayment01" checked>
											<label for="rdoPayment01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoPaymentModule" id="rdoPayment02" >
											<label for="rdoPayment02">입금대기</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoPaymentModule" id="rdoPayment04" >
											<label for="rdoPayment04">입금기한만료</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rdoPaymentModule" id="rdoPayment03" >
											<label for="rdoPayment03">입금완료</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">처리불가여부</th>
							<td class="input">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkModule" type="checkbox">
									<label for="chkModule">처리불가</label>
								</span>
							</td>
						</tr>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
									<span class="btn-group">
										<a href="#" class="btn-sm btn-func">오늘</a>
										<a href="#" class="btn-sm btn-func">일주일</a>
										<a href="#" class="btn-sm btn-func">한달</a>
										<a href="#" class="btn-sm btn-func text-center">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">환불 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="selTermsModule">목록개수</label>
					<select class="ui-sel" id="selTermsModule">
						<option>15개 보기</option>
					</select>
				</span>
				<span class="opt-group">
					<label class="title" for="selTermsModule02">선택항목 처리불가</label>
					<input type="text" id="selTermsModule02" class="ui-input" title="처리불가 사유 입력" placeholder="처리불가 사유 입력">
					<a href="#" class="btn-sm btn-func">일괄적용</a>
				</span>
			</div>
			<div class="fr">
				<a href="#" class="btn-sm btn-func">엑셀 선택 다운로드</a>
				<a href="#" class="btn-sm btn-func">엑셀 전체 다운로드</a>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="redemptionListGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/claim/abcmart.claim.redemptionList.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>