<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">쿠폰 현황 통계</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>프로모션통계 </li>
						<li>쿠폰 현황 통계</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="search-form">
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px;">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">사이트 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoSiteAll" name="siteNo" type="radio" value="" checked />
											<label for="rdoSiteAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoSite01" name="siteNo" type="radio" value="10000" />
											<label for="rdoSite01">A-RT</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoSite02" name="siteNo" type="radio" value="10001" />
											<label for="rdoSite02">OTS</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">쿠폰 분류</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
									<span class="ui-chk">
										<input id="cpnUseGbnAll" type="checkbox" data-check="btnEventAll" name="cpnUseGbnTypeAll" value="" checked />
										<label for="cpnUseGbnAll">전체</label>
									</span>
									</li>
									<li>
									<span class="ui-chk">
										<input id="cpnUseGbn01" type="checkbox" name="cpnUseGbnTypeArr" data-check="btnEventSub" value="E" checked />
										<label for="cpnUseGbn01">행사_일반</label>
									</span>
									</li>
									<li>
									<span class="ui-chk">
										<input id="cpnUseGbn04" type="checkbox" name="cpnUseGbnTypeArr" data-check="btnEventSub" value="F" checked />
										<label for="cpnUseGbn04">행사_다운로드</label>
									</span>
									</li>
									<li>
									<span class="ui-chk">
										<input id="cpnUseGbn02" type="checkbox" name="cpnUseGbnTypeArr" data-check="btnEventSub" value="C" checked />
										<label for="cpnUseGbn02">CS</label>
									</span>
									</li>
									<li>
									<span class="ui-chk">
										<input id="cpnUseGbn03" type="checkbox" name="cpnUseGbnTypeArr" data-check="btnEventSub" value="D" checked />
										<label for="cpnUseGbn03">eDM</label>
									</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">쿠폰 유형</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="cpnTypeCodeAll" name="chkCpnTypeCodeAll" type="checkbox" data-check="btnEventAll" value="" checked />
											<label for="cpnTypeCodeAll">전체</label>
										</span>
									</li>
									<c:if test="${!empty cpnSrchCodeList.CPN_TYPE_CODE}">
										<c:forEach items="${cpnSrchCodeList.CPN_TYPE_CODE}" var="cpnTypeCode" varStatus="cpnStatus">
											<li>
												<span class="ui-chk">
													<input id="cpnTypeCode0${cpnStatus.count}" value="${cpnTypeCode.codeDtlNo}" name="cpnTypeCodeArr" type="checkbox" data-check="btnEventSub" checked />
													<label for="cpnTypeCode0${cpnStatus.count}">${cpnTypeCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">쿠폰 속성</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="normalCpnYnAll" name="normalCpnYn" type="radio" value="" checked />
											<label for="normalCpnYnAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="normalCpnYn01" name="normalCpnYn" type="radio" value="Y" />
											<label for="normalCpnYn01">일반쿠폰</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="normalCpnYn02" name="normalCpnYn" type="radio" value="N" />
											<label for="normalCpnYn02">플러스쿠폰</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">사용 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="useYnAll" name="useYn" type="radio" value="" checked />
											<label for="useYnAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="useYn01" name="useYn" type="radio" value="Y" />
											<label for="useYn01">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="useYn02" name="useYn" type="radio" value="N" />
											<label for="useYn02">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">전시 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYnAll" name="dispYn" type="radio" value="" checked />
											<label for="dispYnAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYn01" name="dispYn" type="radio" value="Y" />
											<label for="dispYn01">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="dispYn02" name="dispYn" type="radio" value="N" />
											<label for="dispYn02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">쿠폰 기간</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select class="ui-sel" name="issueType" title="기간 선택">
										<option value="startDtm">발급시작일</option>
										<option value="endDtm">발급종료일</option>
									</select>
									<span class="date-box" id="dayFromDate">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dayFromDate">
									</span>
									<span class="text" id="dayWaveMark">~</span>
									<span class="date-box" id="dayToDate">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dayToDate">
									</span>
									<span class="btn-group">
										<a href="#" class="btn-sm btn-func" id="toToday">오늘</a>
										<a href="#" class="btn-sm btn-func" id="toWeek">일주일</a>
										<a href="#" class="btn-sm btn-func" id="toMonth">한달</a>
										<a href="#" class="btn-sm btn-func text-center" id="toYear">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">쿠폰 사용처</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="usePlaceGbnTypeAll" name="usePlaceGbnTypeAll" data-check="btnEventAll" type="checkbox" checked />
											<label for="usePlaceGbnTypeAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="usePlaceGbnType01" name="usePlaceGbnTypeArr" data-check="btnEventSub" type="checkbox" value="O" checked />
											<label for="usePlaceGbnType01">온라인</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="usePlaceGbnType02" name="usePlaceGbnTypeArr" data-check="btnEventSub" type="checkbox" value="F" checked />
											<label for="usePlaceGbnType02">오프라인</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="usePlaceGbnType03" name="usePlaceGbnTypeArr" data-check="btnEventSub" type="checkbox" value="A" checked />
											<label for="usePlaceGbnType03">온라인 및 오프라인</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">쿠폰 생성 형태</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="cpnCrtTypeAll" name="cpnCrtType" type="radio" value="" checked />
											<label for="cpnCrtTypeAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="cpnCrtType01" name="cpnCrtType" type="radio" value="O" />
											<label for="cpnCrtType01">온라인생성</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="cpnCrtType02" name="cpnCrtType" type="radio" value="P" />
											<label for="cpnCrtType02">지류생성</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr class="ononly">
							<th scope="row">디바이스</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="deviceCodeAll" name="deviceCodeAll" data-check="btnEventAll" type="checkbox" checked />
											<label for="deviceCodeAll">전체</label>
										</span>
									</li>
									<c:if test="${!empty cpnSrchCodeList.DEVICE_CODE}">
										<c:forEach items="${cpnSrchCodeList.DEVICE_CODE}" var="deviceCode" varStatus="deviceStatus">
											<li>
												<span class="ui-chk">
													<input id="deviceCode0${deviceStatus.count}" value="${deviceCode.codeDtlNo}" name="deviceCodeArr" data-check="btnEventSub" type="checkbox" checked />
													<label for="deviceCode0${deviceStatus.count}">${deviceCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">채널</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="chnnlNoAll" name="chnnlNoAll" type="checkbox" value="" data-check="btnEventAll" checked />
											<label for="chnnlNoAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input id="chnnlNo01" name="chnnlNoArr" type="checkbox" value="10000" data-check="btnEventSub" checked />
											<label for="chnnlNo01">A-RT</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input id="chnnlNo02" name="chnnlNoArr" type="checkbox" value="10001" data-check="btnEventSub" checked />
											<label for="chnnlNo02">ABC-MART</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input id="chnnlNo03" name="chnnlNoArr" type="checkbox" value="10002" data-check="btnEventSub" checked />
											<label for="chnnlNo03">GS</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input id="chnnlNo04" name="chnnlNoArr" type="checkbox" value="10003" data-check="btnEventSub" checked />
											<label for="chnnlNo04">OTS</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">쿠폰 검색</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" name="cpnSrchKey" title="유형 선택">
										<option value="cpnName">쿠폰명</option>
										<option value="cpnNo">쿠폰번호</option>
									</select>
									<input type="text" class="ui-input" name="cpnSrchVal" value="" title="검색어 입력" />
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<td></td>
							<th scope="row">릴레이쿠폰 사용여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="relayCpnUseYnAll" name="relayCpnUseYn" type="radio" value="" checked />
											<label for="relayCpnUseYnAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="relayCpnUseYn01" name="relayCpnUseYn" type="radio" value="Y" />
											<label for="relayCpnUseYn01">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="relayCpnUseYn02" name="relayCpnUseYn" type="radio" value="N" />
											<label for="relayCpnUseYn02">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>

					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func" id="search">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		</form>
		<!-- E : search-wrap -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 쿠폰 통계 자료는 매출발생 시점 기준(결제완료 기준) 조회 통계이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
				<li>* 쿠폰 통계 자료는 당일 발생 매출 데이터가 포함됩니다.</li>
				<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
			</ul>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<!-- E : opt-group -->
			</div>
			<div class="fr">
				<a href="#" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div style="width:100%; height:429px;" id="coupon-list">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.promotion.coupon.js<%=_DP_REV%>"></script>