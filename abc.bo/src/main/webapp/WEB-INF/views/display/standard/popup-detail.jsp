<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		
	<form id="frm" name="frm">
		<input  type="hidden" name="popupSeq" id="popupSeq" value="${bdPopup.popupSeq}" />
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">팝업 관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="/"><i class="icosico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시 관리</li>
								<li>전시 기준정보 관리</li>
								<li>팝업 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">팝업 등록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>팝업 등록</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">팝업 유형</th>
							<td class="input" colspan="3">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoPopupType01" type="radio" name="popupType" ${bdPopup.popupType eq 'G' or empty bdPopup.popupType ? 'checked' : ''} value="G">
											<label for="rdoPopupType01">일반 팝업</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoPopupType02" type="radio" name="popupType" ${bdPopup.popupType eq 'E' ? 'checked' : ''} value="E">
											<label for="rdoPopupType02">이벤트 팝업 (A-RT만 사용 가능)</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">전시기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplay01" ${bdPopup.dispYn eq 'Y' or empty bdPopup.popupSeq ? 'checked' : ''} value="Y">
											<label for="radioDisplay01">전시</label>
										</span>
										<!-- S : term-date-wrap -->
										<!-- DESC : 전시시간 전시안함 선택시 input, select disabled 속성 추가해주세요 -->
										<span class="term-date-wrap">
											<span class="date-box">
												<fmt:formatDate value="${bdPopup.dispStartDtm}" pattern="yyyy.MM.dd" var="dispStartYmdDot"/>
												<fmt:formatDate value="${bdPopup.dispStartDtm}" pattern="HH" var="dispStartH"/>
												<fmt:formatDate value="${bdPopup.dispStartDtm}" pattern="mm" var="dispStartM"/>
												<input type="text" name="paramStartYmd" data-role="datepicker" class="ui-cal js-ui-cal displayDate" title="시작일 선택" value="${dispStartYmdDot}">
											</span>
											<select name="paramStartH" class="ui-sel displayDate" title="시작시 선택">
												<c:forEach var="row" begin="0" end="23">
													<c:choose>
														<c:when test="${row < 10}">
															<c:set var="paramStartH" value="0${row}" />
															<option ${dispStartH eq paramStartH ? 'selected' : ''} value="${paramStartH}">${paramStartH}시</option>
														</c:when>
														<c:otherwise><option ${dispStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
											<select name="paramStartM" class="ui-sel displayDate" title="시작분 선택">
												<c:forEach var="row" begin="0" end="59">
													<c:choose>
														<c:when test="${row < 10}">
															<c:set var="paramStartM" value="0${row}" />
															<option ${dispStartM eq paramStartM ? 'selected' : ''} value="${paramStartM}">${paramStartM}분</option>
														</c:when>
														<c:otherwise><option ${dispStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
											<span class="text">~</span>
											<span class="date-box">
												<fmt:formatDate value="${bdPopup.dispEndDtm}" pattern="yyyy.MM.dd" var="dispEndYmdDot"/>
												<fmt:formatDate value="${bdPopup.dispEndDtm}" pattern="HH" var="dispEndH"/>
												<fmt:formatDate value="${bdPopup.dispEndDtm}" pattern="mm" var="dispEndM"/>
												<input type="text" name="paramEndYmd" data-role="datepicker" class="ui-cal js-ui-cal displayDate" title="종료일 선택" value="${dispEndYmdDot}">
											</span>
											<select name="paramEndH" class="ui-sel displayDate" title="종료시 선택">
												<c:forEach var="row" begin="0" end="23" varStatus="i">
													<c:choose>
														<c:when test="${row < 10}">
															<c:set var="paramEndH" value="0${row}" />
															<option ${dispEndH eq paramEndH ? 'selected' : ''} value="${paramEndH}">${paramEndH}시</option>
														</c:when>
														<c:otherwise><option ${dispEndH eq row or (empty bdPopup.popupSeq and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
											<select name="paramEndM" class="ui-sel displayDate" title="종료분 선택">
												<c:forEach var="row" begin="0" end="59" varStatus="i">
													<c:choose>
														<c:when test="${row < 10}">
															<c:set var="paramEndM" value="0${row}" />
															<option ${dispEndM eq paramEndM ? 'selected' : ''} value="${paramEndM}">${paramEndM}분</option>
														</c:when>
														<c:otherwise><option ${dispEndM eq row or (empty bdPopup.popupSeq and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</span>
										<!-- E : term-date-wrap -->
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplay02" ${bdPopup.dispYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioDisplay02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr id="popupTypeAppendArea">
							<th scope="row">
								<span class="th-required">특정일 전시</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispDaySetupYn" id="radioConfigDisplay01" ${bdPopup.dispDaySetupYn eq 'Y' or empty bdPopup.dispDaySetupYn ? 'checked' : ''} value="Y">
											<label for="radioConfigDisplay01">사용</label>
										</span>

										<!-- S : ip-box-list -->
										<!-- DESC : 특정일 전시 사용안함 선택시, input disabled 속성 추가해주세요 -->
										<c:set var="checkDayList">monYn_월_${bdPopup.monYn},tueYn_화_${bdPopup.tueYn},wedYn_수_${bdPopup.wedYn},thuYn_목_${bdPopup.thuYn},friYn_금_${bdPopup.friYn},satYn_토_${bdPopup.satYn},sunYn_일_${bdPopup.sunYn}</c:set>
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkDay0" class="checkAllDay" type="checkbox">
													<label for="chkDay0">전체</label>
												</span>
											</li>
											<c:forEach var="row" items="${checkDayList}" varStatus="i">
												<li>
													<span id="checkDayArea" class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDay${i.count}" data-name="${fn:split(row, '_')[0]}" class="checkDay" type="checkbox" ${fn:split(row, '_')[2] eq 'Y' or empty bdPopup.popupSeq ? 'checked' : ''}>
														<label for="chkDay${i.count}">${fn:split(row, '_')[1]}</label>
													</span>
												</li>
											</c:forEach>
											<li>
												<!-- DESC : 특정일 전시 사용안함 선택시 select disabled 속성 추가 해주세요 -->
												<!-- S : term-date-wrap -->
												<span class="term-date-wrap">
													<select name="paramDayStartH" class="ui-sel selectDisplayTime" title="시작시 선택">
														<c:forEach var="row" begin="0" end="23">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayStartH" value="0${row}" />
																	<option ${fn:split(bdPopup.dispDayStartTime, '::')[0] eq paramDayStartH ? 'selected' : ''} value="${paramDayStartH}">${paramDayStartH}시</option>
																</c:when>
																<c:otherwise><option ${fn:split(bdPopup.dispDayStartTime, '::')[0] eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<select name="paramDayStartM" class="ui-sel selectDisplayTime" title="시작분 선택">
														<c:forEach var="row" begin="0" end="59">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayStartM" value="0${row}" />
																	<option ${fn:split(bdPopup.dispDayStartTime, '::')[1] eq paramDayStartM ? 'selected' : ''} value="${paramDayStartM}">${paramDayStartM}분</option>
																</c:when>
																<c:otherwise><option ${fn:split(bdPopup.dispDayStartTime, '::')[1] eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<span class="text">~</span>
													<select name="paramDayEndH" class="ui-sel selectDisplayTime" title="종료시 선택">
														<c:forEach var="row" begin="0" end="23" varStatus="i">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayEndH" value="0${row}" />
																	<option ${fn:split(bdPopup.dispDayEndTime, '::')[0] eq paramDayEndH or (empty bdPopup.popupSeq and i.last) ? 'selected' : ''} value="${paramDayEndH}">${paramDayEndH}시</option>
																</c:when>
																<c:otherwise><option ${fn:split(bdPopup.dispDayEndTime, '::')[0] eq row or (empty bdPopup.popupSeq and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<select name="paramDayEndM" class="ui-sel selectDisplayTime" title="종료분 선택">
														<c:forEach var="row" begin="0" end="59" varStatus="i">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayEndM" value="0${row}" />
																	<option ${fn:split(bdPopup.dispDayEndTime, '::')[1] eq paramDayEndM ? 'selected' : ''} value="${paramDayEndM}">${paramDayEndM}분</option>
																</c:when>
																<c:otherwise><option ${fn:split(bdPopup.dispDayEndTime, '::')[1] eq row or (empty bdPopup.popupSeq and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</span>
												<!-- E : term-date-wrap -->
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispDaySetupYn" id="radioConfigDisplay02" ${bdPopup.dispDaySetupYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioConfigDisplay02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<c:choose>
							<c:when test="${bdPopup.popupType eq 'G' or empty bdPopup.popupType}"><!-- 일반 팝업 -->
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">제목</span>
									</th>
									<td class="input">
										<!-- TODO : 제목 글자수 변경시, placeholder 수정 필요 -->
										<input type="text" name="popupTitleText" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" value="${bdPopup.popupTitleText}">
									</td>
									<th scope="row">
										<span class="th-required">우선순위</span>
									</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<input type="text" name="priorSeq" class="ui-input num-unit100" title="우선순위 입력" value="${bdPopup.priorSeq}">
											<span class="text">2개 이상 노출 시, 1순위가 가장 위에 노출됩니다.</span>
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">사용여부</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse02" ${bdPopup.useYn eq 'Y' or empty bdPopup.useYn ? 'checked' : ''} value="Y" >
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse03" ${bdPopup.useYn eq 'N' ? 'checked' : ''} value="N">
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<th scope="row">
										<span class="th-required">특정기간 그만보기</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispLimitType" id="radioView01" ${bdPopup.dispLimitType eq 'D' or empty bdPopup.dispLimitType ? 'checked' : ''} value="D">
													<label for="radioView01">오늘 하루 그만 보기</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispLimitType" id="radioView02" ${bdPopup.dispLimitType eq 'W' ? 'checked' : ''} value="W">
													<label for="radioView02">일주일 그만 보기</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispLimitType" id="radioView03" ${bdPopup.dispLimitType eq 'N' ? 'checked' : ''} value="N">
													<label for="radioView03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">전시 사이트</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<c:forEach items="${siteList}" var="site" varStatus="i">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="radioDisplaySite${i.count }" ${site.siteNo == bdPopup.siteNo or (empty bdPopup.siteNo and i.count eq 1) ? 'checked' : ''} value="${site.siteNo }">
														<label for="radioDisplaySite${i.count}">${site.siteName }</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									
									<th scope="row">
										<span class="th-required">디바이스</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="deviceCode" id="radioDevice0" ${empty bdPopup.deviceCode ? 'checked' : ''} value="">
														<label for="radioDevice0">전체</label>
													</span>
											</li>
											<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
												<c:if test="${code.codeDtlNo ne '10002'}">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="deviceCode" id="radioDevice${i.count }" ${code.codeDtlNo eq bdPopup.deviceCode ? 'checked' : ''} value="${code.codeDtlNo}">
															<label for="radioDevice${i.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr class="popupTypeArea displayArea pcDisplayArea">
									<th scope="row">
										<span class="th-required">PC 전시 위치</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul id="pcDispPositionArea" class="ip-box-list vertical">
											<c:if test="${empty bdPopupDisplayPositionList}">
												<li>
													<select name="bdPopupDisplayPositionPcArr.popupDispPostnCode" class="ui-sel pcDisplaySelect" title="전시 위치 선택">
														<c:forEach var="code" items="${dispPostnCodeList}">
															<c:if test="${code.codeDtlNo ne '10001'}">
																<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
															</c:if>
														</c:forEach>
													</select>
													<!-- DESC : 직접입력 선택시 input 노출 -->
													<input type="text" name="bdPopupDisplayPositionPcArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
													<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="PC">추가</button>
												</li>
											</c:if>
											<c:forEach var="row" items="${bdPopupDisplayPositionList}" varStatus="i">
												<c:if test="${row.deviceCode eq CommonCode.DEVICE_PC}">
													<li>
														<select name="bdPopupDisplayPositionPcArr.popupDispPostnCode" class="ui-sel pcDisplaySelect" title="전시 위치 선택">
															<c:forEach var="code" items="${dispPostnCodeList}">
																<c:if test="${code.codeDtlNo ne '10001'}">
																	<option ${code.codeDtlNo eq row.popupDispPostnCode ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
																</c:if>
															</c:forEach>
														</select>
														<!-- DESC : 직접입력 선택시 input 노출 -->
														<input type="text" name="bdPopupDisplayPositionPcArr.dispPostnUrl" class="ui-input url" ${row.popupDispPostnCode eq '10000' ? 'readonly' : ''} placeholder="url 입력" title="url 입력" value="${row.popupDispPostnCode eq '10000' ? '' : row.dispPostnUrl}">
														<c:choose>
															<c:when test="${i.count eq 1}"><button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="PC">추가</button></c:when>
															<c:otherwise><button type="button" class="btn-sm btn-func del-dispPosition">삭제</button></c:otherwise>
														</c:choose>
													</li>
												</c:if>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr class="popupTypeArea displayArea moDisplayArea">
									<th scope="row">
										<span class="th-required">MOBILE 전시 위치</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul id="moDispPositionArea" class="ip-box-list vertical">
											<c:if test="${empty bdPopupDisplayPositionList}">
												<li>
													<select name="bdPopupDisplayPositionMoArr.popupDispPostnCode" class="ui-sel moDisplaySelect" title="전시 위치 선택">
														<c:forEach var="code" items="${dispPostnCodeList}">
															<c:if test="${code.codeDtlNo ne '10001'}">
																<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
															</c:if>
														</c:forEach>
													</select>
													<!-- DESC : 직접입력 선택시 input 노출 -->
													<input type="text" name="bdPopupDisplayPositionMoArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
													<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="MO">추가</button>
												</li>
											</c:if>
											<c:set var="dispFirst" value="Y" />
											<c:forEach var="row" items="${bdPopupDisplayPositionList}" varStatus="i">
												<c:if test="${row.deviceCode eq CommonCode.DEVICE_MOBILE}">
													<li>
														<select name="bdPopupDisplayPositionMoArr.popupDispPostnCode" class="ui-sel moDisplaySelect" title="전시 위치 선택">
															<c:forEach var="code" items="${dispPostnCodeList}">
																<c:if test="${code.codeDtlNo ne '10001'}">
																	<option ${code.codeDtlNo eq row.popupDispPostnCode ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
																</c:if>
															</c:forEach>
														</select>
														<!-- DESC : 직접입력 선택시 input 노출 -->
														<input type="text" name="bdPopupDisplayPositionMoArr.dispPostnUrl" class="ui-input url" ${row.popupDispPostnCode eq '10000' ? 'readonly' : ''} placeholder="url 입력" title="url 입력" value="${row.popupDispPostnCode eq '10000' ? '' : row.dispPostnUrl}">
														<c:choose>
															<c:when test="${dispFirst eq 'Y'}">
																<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="MO">추가</button>
																<c:set var="dispFirst" value="N" />
															</c:when>
															<c:otherwise><button type="button" class="btn-sm btn-func del-dispPosition">삭제</button></c:otherwise>
														</c:choose>
													</li>
												</c:if>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr class="popupTypeArea pcPopupTypeArea">
									<th scope="row">
										<span class="th-required">PC 팝업타입</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcPopupType" id="radioPopupType01"  ${pcBdPopupDevice.popupType eq 'N' or empty pcBdPopupDevice.popupType ? 'checked' : ''} value="N">
													<label for="radioPopupType01">윈도우</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcPopupType" id="radioPopupType02" ${pcBdPopupDevice.popupType eq 'L' ? 'checked' : ''} value="L">
													<label for="radioPopupType02">레이어</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<th scope="row">
										<span class="th-required">PC 팝업창 위치</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">X :</span>
													<input type="text" name="bdPopupDevice.pcPopupXPostn" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 X위치 입력" value="${pcBdPopupDevice.popupXPostn}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">Y :</span>
													<input type="text" name="bdPopupDevice.pcPopupYPostn" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 Y위치 입력" value="${pcBdPopupDevice.popupYPostn}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<%-- <tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">PC 팝업창 크기</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">width :</span>
													<input type="text" name="bdPopupDevice.pcPopupWidthNum" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 가로크기 입력" value="${pcBdPopupDevice.popupWidthNum}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">height :</span>
													<input type="text" name="bdPopupDevice.pcPopupHeightNum" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 세로크기 입력" value="${pcBdPopupDevice.popupHeightNum}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<th scope="row">
										<span class="th-required">Mobile 팝업창 크기</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">width :</span>
													<input type="text" name="bdPopupDevice.moPopupWidthNum" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 가로크기 입력" value="${moBdPopupDevice.popupWidthNum}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">height :</span>
													<input type="text" name="bdPopupDevice.moPopupHeightNum" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 세로크기 입력" value="${moBdPopupDevice.popupHeightNum}">
													<span class="text">pixel</span>
												</span>
												<!-- E : ip-text-box -->
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr> --%>
								<tr class="popupTypeArea deviceArea pcDeviceArea">
									<th scope="row">
										<span class="th-required">PC 팝업 이미지</span>
										<div>(최대 10MB까지 등록가능<br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
									</th>
									<td class="input" colspan="3">
										<!-- S : file-wrap -->
										<div class="file-wrap">
											<ul class="file-list">
												<li>
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="hidden" id="pcImageNamePopup" name="bdPopupDevice.pcImageName" class="imageName" value="${pcBdPopupDevice.imageName}">
														<input type="file" id="pcImageFilePopup" class="imageFile" name="pcImageFile" title="첨부파일 추가">
														<label for="pcImageFilePopup">찾아보기</label>
													</span>
												</li>
												<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
												<li>
													<a href="javascript:void(0);" class="subject">${pcBdPopupDevice.imageName}</a>
													<button type="button" class="btn-file-del" style="${pcBdPopupDevice.imageName != null and pcBdPopupDevice.imageName != '' ? '' : 'display: none;' }">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
													</button>
												</li>
											</ul>
											<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="alt-wrap">
												<input type="text" name="bdPopupDevice.pcArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcBdPopupDevice.altrnText}">
											</div>
											<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="img-wrap">
												<c:if test="${pcBdPopupDevice.imageName != null and pcBdPopupDevice.imageName != ''}">
													<img alt="${pcBdPopupDevice.imageName}" src="${pcBdPopupDevice.imageUrl}" class="targetImage">
												</c:if>
											</div>
										</div>
										<!-- E : file-wrap -->
									</td>
								</tr>
								<tr class="popupTypeArea deviceArea pcDeviceArea">
									<th scope="row">
										<span class="th-required">PC 팝업 링크연결<br /> 방법</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType01" ${pcBdPopupDevice.cnnctnType eq 'U' or empty pcBdPopupDevice.cnnctnType ? 'checked' : ''} value="U" data-position="general-pc">
													<label for="radioPCLinkType01">랜딩 URL</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType02" ${pcBdPopupDevice.cnnctnType eq 'M' ? 'checked' : ''} value="M" data-position="general-pc">
													<label for="radioPCLinkType02">이미지맵</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType03" ${pcBdPopupDevice.cnnctnType eq 'N' ? 'checked' : ''} value="N" data-position="general-pc">
													<label for="radioPCLinkType03">연결안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
								<tr class="popupTypeArea general-pc-area deviceArea pcDeviceArea" ${pcBdPopupDevice.cnnctnType eq 'U' or empty pcBdPopupDevice.cnnctnType ? '' : 'style="display: none;"'}>
									<th scope="row">
										<span class="th-required">PC 랜딩 URL</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select name="bdPopupDevice.pcTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
												<option ${pcBdPopupDevice.linkTargetType eq 'S' or empty pcBdPopupDevice.linkTargetType ? 'selected' : ''} value="S">현재창</option>
												<option ${pcBdPopupDevice.linkTargetType eq 'N' ? 'selected' : ''} value="N">새창</option>
											</select>
											<input type="text" name="bdPopupDevice.pcLinkInfo" class="ui-input url general-pc" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
												title="url 입력" value="${pcBdPopupDevice.cnnctnType eq 'U' ? pcBdPopupDevice.linkInfo : ''}" />
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea general-pc-area deviceArea" ${pcBdPopupDevice.cnnctnType eq 'M' ? '' : 'style="display: none;"'}>
									<th scope="row">
										<span class="th-required">PC 랜딩 URL</span>
									</th>
									<td class="input">
										<textarea class="ui-textarea general-pc" title="랜딩 url 입력" name="bdPopupDevice.pcLinkInfo">${pcBdPopupDevice.cnnctnType eq 'M' ? pcBdPopupDevice.linkInfo : ''}</textarea>
									</td>
								</tr>
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->	
								<tr class="popupTypeArea deviceArea moDeviceArea">
									<th scope="row">
										<span class="th-required">Mobile 팝업 이미지</span>
										<div>이미지 사이즈 640*640 <br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
									</th>
									<td class="input" colspan="3">
										<!-- S : file-wrap -->
										<div class="file-wrap">
											<ul class="file-list">
												<li>
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="hidden" id="moImageNamePopup" name="bdPopupDevice.moImageName" class="imageName" value="${moBdPopupDevice.imageName}">
														<input type="file" id="moImageFilePopup" class="imageFile" name="moImageFile" title="첨부파일 추가">
														<label for="moImageFilePopup">찾아보기</label>
													</span>
												</li>
												<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
												<li>
													<a href="javascript:void(0);" class="subject">${moBdPopupDevice.imageName}</a>
													<button type="button" class="btn-file-del" style="${moBdPopupDevice.imageName != null and moBdPopupDevice.imageName != '' ? '' : 'display: none;' }">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
													</button>
												</li>
											</ul>
											<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="alt-wrap">
												<input type="text" name="bdPopupDevice.moArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moBdPopupDevice.altrnText}">
											</div>
											<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="img-wrap">
												<c:if test="${moBdPopupDevice.imageName != null and moBdPopupDevice.imageName != ''}">
													<img alt="${moBdPopupDevice.imageName}" src="${moBdPopupDevice.imageUrl}" class="targetImage">
												</c:if>
											</div>
										</div>
										<!-- E : file-wrap -->
									</td>
								</tr>
								<tr class="popupTypeArea deviceArea moDeviceArea">
									<th scope="row">
										<span class="th-required">Mobile 팝업<br />링크연결 방법</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType01" ${moBdPopupDevice.cnnctnType eq 'U' or empty moBdPopupDevice.cnnctnType ? 'checked' : ''} value="U" data-position="general-mobile">
													<label for="radioMobileLinkType01">랜딩 URL</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType02" ${moBdPopupDevice.cnnctnType eq 'M' ? 'checked' : ''} value="M" data-position="general-mobile">
													<label for="radioMobileLinkType02">이미지맵</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType03" ${moBdPopupDevice.cnnctnType eq 'N' ? 'checked' : ''} value="N" data-position="general-mobile">
													<label for="radioMobileLinkType03">연결안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea general-mobile-area deviceArea moDeviceArea" ${moBdPopupDevice.cnnctnType eq 'N' ? 'style="display:none;"' : ''}>
									<th scope="row">
										<span class="th-required">Mobile 랜딩 URL</span>
									</th>
									<td class="input" colspan="3">
										<!-- <textarea class="ui-textarea" title="랜딩 url 입력"></textarea> -->
										<span class="ip-text-box">
											<select name="bdPopupDevice.moTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
												<option ${moBdPopupDevice.linkTargetType eq 'S' or empty moBdPopupDevice.linkTargetType ? 'selected' : ''} value="S">현재창</option>
												<option ${moBdPopupDevice.linkTargetType eq 'N' ? 'selected' : ''} value="N">새창</option>
											</select>
											<input type="text" name="bdPopupDevice.moLinkInfo" class="ui-input url general-mobile" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
														title="url 입력" value="${moBdPopupDevice.cnnctnType eq 'U' ? moBdPopupDevice.linkInfo : ''}" />
										</span>
									</td>
								</tr>
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea link-type-map general-mobile-area deviceArea" ${moBdPopupDevice.cnnctnType ne 'M' ? 'style="display: none;"' : ""}>
									<th scope="row">
										<span class="th-required">Mobile 랜딩 URL</span>
									</th>
									<td class="input">
										<textarea class="ui-textarea general-mobile" title="랜딩 url 입력" name="bdPopupDevice.moLinkInfo">${moBdPopupDevice.cnnctnType eq 'M' ? moBdPopupDevice.linkInfo : ''}</textarea>
									</td>
								</tr>
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->	
							</c:when>
							<c:otherwise>
								<!-- 이벤트 팝업 -->
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">제목</span>
									</th>
									<td class="input">
										<!-- TODO : 제목 글자수 변경시, placeholder 수정 필요 -->
										<input type="text" name="popupTitleText" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" value="${bdPopup.popupTitleText}">
										<input type="hidden" name="siteNo" value="10000" /> <!-- 이벤트 팝업시 siteNo default(10000)  -->
									</td>
									<th scope="row">
										<span class="th-required">사용여부</span>
									</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse02" ${bdPopup.useYn eq 'Y' or empty bdPopup.useYn ? 'checked' : ''} value="Y" >
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse03" ${bdPopup.useYn eq 'N' ? 'checked' : ''} value="N">
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">이벤트 팝업 이미지</span>
										<div>권장사이즈 101*130 <br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif,bmp)</div>
									</th>
									<td class="input" colspan="3">
										<!-- S : file-wrap -->
										<div class="file-wrap">
											<ul class="file-list">
												<li>
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="hidden" id="pcImageNameEvent" name="bdPopupDevice.pcImageName" class="imageName" value="${pcBdPopupDevice.imageName}">
														<input type="file" id="pcImageFileEvent" class="imageFile" name="pcImageFile" title="첨부파일 추가">
														<label for="pcImageFileEvent">찾아보기</label>
													</span>
												</li>
												<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
												<li>
													<a href="javascript:void(0);" class="subject">${pcBdPopupDevice.imageName}</a>
													<button type="button" class="btn-file-del" style="${pcBdPopupDevice.imageName != null and pcBdPopupDevice.imageName != '' ? '' : 'display: none;' }">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
													</button>
												</li>
											</ul>
											<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="alt-wrap">
												<input type="text" name="bdPopupDevice.pcArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcBdPopupDevice.altrnText}">
											</div>
											<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="img-wrap">
												<c:if test="${pcBdPopupDevice.imageName != null and pcBdPopupDevice.imageName != ''}">
													<img alt="${pcBdPopupDevice.imageName}" src="${pcBdPopupDevice.imageUrl}" class="targetImage">
												</c:if>
											</div>
										</div>
										<!-- E : file-wrap -->
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">이벤트 팝업 링크연결<br /> 방법</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType01" ${pcBdPopupDevice.cnnctnType eq 'U' or empty pcBdPopupDevice.cnnctnType ? 'checked' : ''} value="U" data-position="event-normal">
													<label for="radioPCLinkType01">랜딩 URL</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType02" ${pcBdPopupDevice.cnnctnType eq 'M' ? 'checked' : ''} value="M" data-position="event-normal">
													<label for="radioPCLinkType02">이미지맵</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType03" ${pcBdPopupDevice.cnnctnType eq 'P' ? 'checked' : ''} value="P" data-position="event-normal" data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호">
													<label for="radioPCLinkType03">기획전</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType04" ${pcBdPopupDevice.cnnctnType eq 'B' ? 'checked' : ''} value="B" data-position="event-normal" data-link-url="/brand/detail?brndNo=" data-hint="브랜드번호">
													<label for="radioPCLinkType04">브랜드</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType05" ${pcBdPopupDevice.cnnctnType eq 'N' ? 'checked' : ''} value="N" data-position="event-normal">
													<label for="radioPCLinkType05">연결안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-normal-area" ${pcBdPopupDevice.cnnctnType ne 'U' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select name="bdPopupDevice.pcTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
												<option ${pcBdPopupDevice.linkTargetType eq 'S' or empty pcBdPopupDevice.linkTargetType ? 'selected' : ''} value="S">현재창</option>
												<option ${pcBdPopupDevice.linkTargetType eq 'N' ? 'selected' : ''} value="N">새창</option>
											</select>
											<input type="text" name="bdPopupDevice.pcLinkInfo" class="ui-input url event-normal" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
												title="url 입력" value="${pcBdPopupDevice.linkInfo}" ${pcBdPopupDevice.cnnctnType ne 'U' ? 'disabled' : ''} />
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-normal-area" ${pcBdPopupDevice.cnnctnType ne 'M' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input">
										<textarea class="ui-textarea event-normal" title="랜딩 url 입력" name="bdPopupDevice.pcLinkInfo" ${pcBdPopupDevice.cnnctnType ne 'M' ? 'disabled' : ''}>${pcBdPopupDevice.cnnctnType eq 'M' ? pcBdPopupDevice.linkInfo : ''}</textarea>
									</td>
								</tr>
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-normal-area" ${pcBdPopupDevice.cnnctnType ne 'P' and pcBdPopupDevice.cnnctnType ne 'B' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input" colspan="">
										<span class="ip-text-box">
											<span class="text event-normal-link-url">${pcBdPopupDevice.cnnctnType eq 'P' ? '/promotion/planning-display/detail?plndpNo=' : '/brand/detail?brndNo='}</span>
											<input type="text" class="ui-input event-normal event-normal-input-info" placeholder="" title=""name="bdPopupDevice.pcLinkInfo" value="${pcBdPopupDevice.cnnctnType eq 'P' or pcBdPopupDevice.cnnctnType eq 'B' ? pcBdPopupDevice.linkInfo : ''}" ${pcBdPopupDevice.cnnctnType ne 'P' and pcBdPopupDevice.cnnctnType ne 'B' ? 'disabled' : ''}>
										</span>
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">폴딩 이벤트 배너 이미지</span>
										<div>권장사이즈 920*413 <br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
									</th>
									<td class="input" colspan="3">
										<!-- S : file-wrap -->
										<div class="file-wrap">
											<ul class="file-list">
												<li>
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="hidden" id="moImageNameEvent" name="bdPopupDevice.moImageName" class="imageName" value="${moBdPopupDevice.imageName}">
														<input type="file" id="moImageFileEvent" class="imageFile" name="moImageFile" title="첨부파일 추가">
														<label for="moImageFileEvent">찾아보기</label>
													</span>
												</li>
												<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
												<li>
													<a href="javascript:void(0);" class="subject">${moBdPopupDevice.imageName}</a>
													<button type="button" class="btn-file-del" style="${moBdPopupDevice.imageName != null and moBdPopupDevice.imageName != '' ? '' : 'display: none;' }">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
													</button>
												</li>
											</ul>
											<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="alt-wrap">
												<input type="text" name="bdPopupDevice.moArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moBdPopupDevice.altrnText}">
											</div>
											<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
											<div class="img-wrap">
												<c:if test="${moBdPopupDevice.imageName != null and moBdPopupDevice.imageName != ''}">
													<img alt="${moBdPopupDevice.imageName}" src="${moBdPopupDevice.imageUrl}" class="targetImage">
												</c:if>
											</div>
										</div>
										<!-- E : file-wrap -->
									</td>
								</tr>
								<tr class="popupTypeArea">
									<th scope="row">
										<span class="th-required">폴딩 이벤트 배너<br />링크연결 방법</span>
									</th>
									<td class="input" colspan="3">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType01" ${moBdPopupDevice.cnnctnType eq 'U' or empty moBdPopupDevice.cnnctnType ? 'checked' : ''} value="U" data-position="event-folding">
													<label for="radioMobileLinkType01">랜딩 URL</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType02" ${moBdPopupDevice.cnnctnType eq 'M' ? 'checked' : ''} value="M" data-position="event-folding">
													<label for="radioMobileLinkType02">이미지맵</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType03" ${moBdPopupDevice.cnnctnType eq 'P' ? 'checked' : ''} value="P" data-position="event-folding" data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호">
													<label for="radioMobileLinkType03">기획전</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType04" ${moBdPopupDevice.cnnctnType eq 'B' ? 'checked' : ''} value="B" data-position="event-folding" data-link-url="/brand/detail?brndNo=" data-hint="브랜드번호">
													<label for="radioMobileLinkType04">브랜드</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType05" ${moBdPopupDevice.cnnctnType eq 'N' ? 'checked' : ''} value="N" data-position="event-folding">
													<label for="radioMobileLinkType05">연결안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-folding-area" ${moBdPopupDevice.cnnctnType ne 'U' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input" colspan="3">
										<!-- <textarea class="ui-textarea" title="랜딩 url 입력"></textarea> -->
										<span class="ip-text-box">
											<select name="bdPopupDevice.moTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
												<option ${moBdPopupDevice.linkTargetType eq 'S' or empty moBdPopupDevice.linkTargetType ? 'selected' : ''} value="S">현재창</option>
												<option ${moBdPopupDevice.linkTargetType eq 'N' ? 'selected' : ''} value="N">새창</option>
											</select>
											<input type="text" name="bdPopupDevice.moLinkInfo" class="ui-input url event-folding" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
														title="url 입력" value="${moBdPopupDevice.linkInfo}" ${moBdPopupDevice.cnnctnType ne 'U' ? 'disabled' : ''} />
										</span>
									</td>
								</tr>								
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-folding-area" ${moBdPopupDevice.cnnctnType ne 'M' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input">
										<textarea class="ui-textarea event-folding" title="랜딩 url 입력" name="bdPopupDevice.moLinkInfo" ${moBdPopupDevice.cnnctnType ne 'M' ? 'disabled' : ''}>${moBdPopupDevice.cnnctnType eq 'M' ? moBdPopupDevice.linkInfo : ''}</textarea>
									</td>
								</tr>
								<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
								<tr class="popupTypeArea event-folding-area" ${moBdPopupDevice.cnnctnType ne 'P' and moBdPopupDevice.cnnctnType ne 'B' ? 'style="display: none;"' : ''}>
									<th scope="row">
										<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
									</th>
									<td class="input" colspan="">
										<span class="ip-text-box">
											<span class="text event-folding-link-url">${moBdPopupDevice.cnnctnType eq 'P' ? '/promotion/planning-display/detail?plndpNo=' : '/brand/detail?brndNo='}</span>
											<input type="text" class="ui-input event-folding event-folding-input-info" placeholder="" title=""name="bdPopupDevice.moLinkInfo" value="${moBdPopupDevice.cnnctnType eq 'P' or moBdPopupDevice.cnnctnType eq 'B' ? moBdPopupDevice.linkInfo : ''}" ${moBdPopupDevice.cnnctnType ne 'P' and moBdPopupDevice.cnnctnType ne 'B' ? 'disabled' : ''}>
										</span>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : tbl-row -->
				<c:if test="${not empty bdPopup.popupSeq}">
					<table class="tbl-row">
						<caption>팝업 등록 작성 정보</caption>
						<colgroup>
							<col style="width:130px">
							<col>
							<col style="width:130px">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:abc.adminDetailPopup('${bdPopup.rgsterNo}');">${UtilsMasking.adminName(bdPopup.rgsterId, bdPopup.rgsterName)}</a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${bdPopup.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:abc.adminDetailPopup('${bdPopup.moderNo}');">${UtilsMasking.adminName(bdPopup.moderId, bdPopup.moderName)}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${bdPopup.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<c:if test="${not empty bdPopup.popupSeq}">
							<a href="javascript:void(0);" id="del-popup" class="btn-normal btn-del">삭제</a>
						</c:if>
						<!-- <a href="javascript:void(0);" id="popupPreview" class="btn-normal btn-link">미리보기</a> -->
					</div>
					<div class="fr">
						<a href="/display/standard/popup" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" id="save-popup" class="btn-lg btn-save">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		
		<!-- popupDevice param  -->
		<c:forEach var="code"  items="${deviceCodeList}" varStatus="i">
			<c:if test="${CommonCode.DEVICE_PC eq code.codeDtlNo or CommonCode.DEVICE_MOBILE eq code.codeDtlNo}">
				<input type="hidden" name="deviceCodes" value="${code.codeDtlNo}" />
			</c:if>
		</c:forEach>
		<!-- E : container -->
	</form>
	
<%@include file="/WEB-INF/views/display/standard/popup-info-template.jsp" %>
<%@include file="/WEB-INF/views/common/footer.jsp" %>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/standard/abcmart.display.standard.popup.detail.js<%=_DP_REV%>"></script>