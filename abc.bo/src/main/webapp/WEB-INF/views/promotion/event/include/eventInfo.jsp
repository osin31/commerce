<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tr class="dispTrArea benefit2">
	<th scope="row" id="benefit2Th">참여유형</th>
	<td class="input">
		<div id="eventJoinTypeArea" class="tbl-header">
			<c:choose>
				<c:when test="${updateByEventProgressStatus eq 'Y'}">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="radioType00" name="eventJoinType" type="radio" ${empty evEvent.eventJoinType ? 'checked' : ''} value="">
								<label for="radioType00">없음</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="radioType01" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'C' ? 'checked' : ''} value="C">
								<label for="radioType01">쿠폰형</label>
							</span>
							<a href="javascript:void(0);" class="btn-sm btn-link searchCouponPop targetEventJoinType">쿠폰조회</a>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="radioType02" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'D' ? 'checked' : ''} value="D">
								<label for="radioType02">추첨형</label>
							</span>
							<span class="text"> 난수 </span><input type="text" name="eventPblicteNoCnt" class="ui-input num-unit100000000 targetEventJoinType" maxlength="4" value="${evEvent.eventPblicteNoCnt}" /><span class="text">개 생성 </span>
							<c:if test="${not empty evEvent.eventNo and evEvent.eventPblicteNoCnt > 0}">
								<a href="javascript:void(0);" id="pblicteNoDownBtn" class="btn-sm btn-link targetEventJoinType">난수 다운로드</a>
								<input type="hidden" name="pblicteNoYn" value="Y" />
							</c:if>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="radioType03" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'L' ? 'checked' : ''} value="L">
								<label for="radioType03">ID입력여부</label>
							</span>
							<span class="text">(</span>

							<ul class="ip-box-list">
								<%-- <li>
									<span class="ui-rdo">
										<input id="radioIDType01" name="loginIdDupPermYn" type="radio" ${empty evEvent.loginIdDupPermYn and evEvent.eventJoinType eq 'L' ? 'checked' : ''}>
										<label for="radioIDType01">없음</label>
									</span>
								</li> --%>
								<li>
									<%-- <span class="ui-rdo">
										<input id="radioIDType02" name="loginIdDupPermYn" type="radio" ${evEvent.loginIdDupPermYn eq 'Y' ? 'checked' : ''} value="Y">
										<label for="radioIDType02">있음</label>
									</span> --%>
									<span class="ui-chk">
										<input type="hidden" name="loginIdDupPermYn" value="${evEvent.loginIdDupPermYn}" />
										<input id="chkIDType02" type="checkbox" class="targetEventJoinType" ${evEvent.loginIdDupPermYn eq 'Y' ? 'checked' : ''} value="Y">
										<label for="chkIDType02">ID 중복 허용</label>
									</span>
									<span class="text">)</span>
								</li>
							</ul>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<span class="ui-rdo" style="display:none;">
						<input id="radioType01" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'C' ? 'checked' : ''} value="C">
						<input id="radioType02" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'D' ? 'checked' : ''} value="D">
						<input id="radioType03" name="eventJoinType" type="radio" ${evEvent.eventJoinType eq 'L' ? 'checked' : ''} value="L">
					</span>
					<c:choose>
						<c:when test="${empty evEvent.eventJoinType}"><span class="text">없음</span></c:when>
						<c:when test="${evEvent.eventJoinType eq 'C'}"><span class="text">쿠폰형</span></c:when>
						<c:when test="${evEvent.eventJoinType eq 'D'}">
							<span class="text"></span>
							<span class="text">추첨형 - 난수 ${evEvent.eventPblicteNoCnt}개 &nbsp;&nbsp;</span>
							<input type="hidden" name="eventPblicteNoCnt" value="${evEvent.eventPblicteNoCnt}" />
							<c:if test="${not empty evEvent.eventNo and evEvent.eventPblicteNoCnt > 0}">
								<a href="javascript:void(0);" id="pblicteNoDownBtn" class="btn-sm btn-link targetEventJoinType">난수 다운로드</a>
								<input type="hidden" name="pblicteNoYn" value="Y" />
							</c:if>
						</c:when>
						<c:when test="${evEvent.eventJoinType eq 'L'}">
							<span class="text">ID입력여부 ${evEvent.loginIdDupPermYn eq 'Y' ? '(ID 중복 허용)' : '(ID 중복 불가)'}</span>
							<input type="hidden" name="loginIdDupPermYn" value="${evEvent.loginIdDupPermYn}" />
						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</td>
</tr>

<tr class="dispTrArea cpnTrArea">
	<td class="input">
		<div class="tbl-header">
		<div class="fr">
			<c:if test="${updateByEventProgressStatus eq 'Y'}"><a href="javascript:void(0);" id="couponDelBtn" class="btn-sm btn-del">선택 삭제</a></c:if>
		</div>
	</div>
		<div class="ibsheet-wrap">
			<div id="eventCouponSheet"></div>
		</div>
	</td>
</tr>

<tr class="dispTrArea benefit1">
	<th scope="row">응모제한조건</th>
	<td class="input">
		<table class="tbl-col">
			<caption>응모제한조건</caption>
			<colgroup>
				<col>
				<col>
			</colgroup>
			<tbody id="benefitTemplate1Tbody">
				<c:set var="memberTypeCheck" value="Y" /> <!-- 멤버쉽은 로우가 1개이상 쌓일 수도 있으므로 -->
				<c:forEach var="row" items="${evEventTargetGradeList}">
					<c:if test="${memberTypeCheck eq 'Y' or row.memberTypeCode ne CommonCode.MEMBER_TYPE_MEMBERSHIP}">
						<tr>
							<td class="text-center targetGradeName"></td>
							<td class="input">
								<select name="evEventTargetGradeArr.joinLimitTypeCode" class="ui-sel" title="제한조건 선택">
									<c:forEach var="code" items="${joinLimitTypeCodeList}">
										<c:if test="${code.codeDtlNo ne '10005'}">
											<c:set var="selectYn" value="" />
											<c:if test="${row.joinLimitTypeCode eq code.codeDtlNo}"><c:set var="selectYn" value="selected" /></c:if>
											<option ${selectYn} value="${code.codeDtlNo}">${code.codeDtlName}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
					</c:if>
					<c:if test="${row.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}"><c:set var="memberTypeCheck" value="N" /></c:if>
				</c:forEach>
			</tbody>
		</table>
	</td>
</tr>
<tr class="dispTrArea benefit1" style="display:none;">
	<th scope="row">포인트차감여부</th>
	<td class="input">
		<ul class="ip-box-list">
			<li>
				<span class="ui-rdo">
					<input id="radioPoint01" name="pointDdctYn" type="radio" ${evEvent.pointDdctYn eq 'N' or empty evEvent.pointDdctYn ? 'checked' : ''} value="N">
					<label for="radioPoint01">없음</label>
				</span>
			</li>
			<li>
				<span class="ui-rdo">
					<input id="radioPoint02" name="pointDdctYn" type="radio" ${evEvent.pointDdctYn eq 'Y' ? 'checked' : ''} value="Y">
					<label for="radioPoint02">있음</label>
				</span>

				<span class="ip-text-box ddctPointAmtArea" style="${evEvent.pointDdctYn eq 'Y' ? '' : 'display:none;'}">
					<input type="text" name="ddctPointAmt" class="ui-input num-unit100000000" maxlength="8" title="포인트 입력" value="${not empty evEvent.ddctPointAmt ? evEvent.ddctPointAmt : '0'}">
					<span class="text">포인트</span>
				</span>
			</li>
		</ul>
	</td>
</tr>
<tr class="dispTrArea benefit11" style="display:none;">
	<th scope="row"  rowspan="2">혜택정보</th>
	<td class="input">
		<ul class="ip-box-list">
			<li>
				<span class="ui-rdo">
					<input id="radioGeBenefitGbn01" name="geBenefitYn" type="radio" ${empty evEvent.geBenefitType ? 'checked' : ''} ${updateByEventProgressStatus eq 'Y' ? '' : ' onclick="return(false);"'} value="N">
					<label for="radioGeBenefitGbn01">없음</label>
				</span>
			</li>
			<li>
				<span class="ui-rdo">
					<input id="radioGeBenefitGbn02" name="geBenefitYn" type="radio" ${not empty evEvent.geBenefitType ? 'checked' : ''} ${updateByEventProgressStatus eq 'Y' ? '' : ' onclick="return(false);"'} value="Y">
					<label for="radioGeBenefitGbn02">있음</label>
				</span>

				<c:if test="${updateByEventProgressStatus eq 'Y'}">
					<span class="ip-text-box geBenefitArea" style="${not empty evEvent.geBenefitType ? '' : 'display:none;'}">
						<select id="selGeBenefitType" class="ui-sel" title="유형 선택">
							<option value="">선택</option>
							<option value="C">쿠폰</option>
							<option value="P">포인트</option>
						</select>
						<button type="button" id="addGeBenefit" class="btn-sm btn-func geBenefitArea">추가</button>
					</span>
				</c:if>
				<c:if test="${updateByEventProgressStatus eq 'N'}">
					<span class="text">* 진행중 또는 종료된 이벤트는 혜택정보를 수정할 수 없습니다.</span>
				</c:if>
			</li>
		</ul>
	</td>
</tr>
<tr class="dispTrArea benefit11">
	<td class="input">
		<ul id="geBenefitArea" class="ip-box-list vertical">
			<c:if test="${not empty evEvent.geBenefitType}">
				<li>
					<span class="ip-text-box">
						<c:choose>
							<c:when test="${updateByEventProgressStatus eq 'Y'}">
								<select name="geBenefitType" class="ui-sel" title="유형 선택">
									<option selected value="${evEvent.geBenefitType}">${evEvent.geBenefitType eq 'C' ? '쿠폰' : '포인트'}</option>
								</select>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="geBenefitType" value="${evEvent.geBenefitType}" />
								<c:choose>
									<c:when test="${evEvent.geBenefitType eq 'C'}"><span class="text">쿠폰 -</span></c:when>
									<c:otherwise><span class="text">포인트 -</span></c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${updateByEventProgressStatus eq 'Y'}">
								<c:choose>
									<c:when test="${evEvent.geBenefitType eq 'C'}">
										<input type="hidden" name="geBenefitValue" value="${evEvent.geBenefitValue}" />
										<input type="hidden" name="geBenefitName" value="${evEvent.geBenefitName}" />
										<button type="button" class="btn-sm btn-link searchBenefitCouponPop">쿠폰조회</button>
										<span class="search-item">
											<span class="subject">${evEvent.geBenefitName}</span>
											<button type="button" class="btn-item-del btnDelGeBenefit">
												<span class="ico ico-item-del"><span class="offscreen">쿠폰 삭제</span></span>
											</button>
										</span>
									</c:when>
									<c:otherwise>
										<input type="text" name="geBenefitValue" class="ui-input num-unit100000000" title="포인트 입력" value="${evEvent.geBenefitValue}">
										<input type="hidden" name="geBenefitName" value="포인트" />
										<span class="text">포인트</span>
									</c:otherwise>
								</c:choose>
								<button type="button" class="btn-sm btn-del btnDelGeBenefit">삭제</button>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${evEvent.geBenefitType eq 'C'}">
										<input type="hidden" name="geBenefitValue" value="${evEvent.geBenefitValue}" />
										<span class="text">${evEvent.geBenefitName}</span>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="geBenefitValue" value="${evEvent.geBenefitValue}">
										<span class="text">${evEvent.geBenefitValue} 포인트</span>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</span>
				</li>
			</c:if>
		</ul>
	</td>
</tr>

<tr class="dispTrArea benefit3">
	<th scope="row" rowspan="2">기본혜택</th>
	<td class="input">
		<span class="ip-text-box">
			<input type="text" id="standardDayCount" class="ui-input num-unit100000000 inputAtendDayCount" maxlength="2" title="출석일수입력">
			<span class="text">일 출석 시</span>
			<select id="standardType" class="ui-sel" title="유형 선택">
				<option value="">선택</option>
				<option value="C">쿠폰</option>
				<option value="P">포인트</option>
			</select>
			<c:if test="${updateByEventProgressStatus eq 'N'}"><span class="text">* 진행중 또는 종료된 이벤트는 혜택정보를 수정할 수 없습니다.</span></c:if>
		</span>
		<c:if test="${updateByEventProgressStatus eq 'Y'}"><button type="button" id="addBasicBenefit" class="btn-sm btn-func">추가</button></c:if>
	</td>
</tr>
<tr class="dispTrArea benefit3">
	<td class="input">
		<ul id="basicBenefitArea" class="ip-box-list vertical">
			<c:forEach var="row" items="${evEventAttendanceCheckBenefitList}">
				<li>
					<span class="ip-text-box">
						<input type="text" name="evEventAttendanceCheckBenefitArr.atendDayCount" class="ui-input num-unit100000000" title="출석일수입력"
									${updateByEventProgressStatus eq 'Y' ? '' : 'readonly'} maxlength="2" value="${row.atendDayCount}">
						<span class="text">일 출석 시</span>
						<c:choose>
							<c:when test="${updateByEventProgressStatus eq 'Y'}">
								<select name="evEventAttendanceCheckBenefitArr.benefitType" class="ui-sel" title="유형 선택">
									<option value="">선택</option>
									<option ${row.benefitType eq 'C' ? 'selected' : ''} value="C">쿠폰</option>
									<option ${row.benefitType eq 'P' ? 'selected' : ''} value="P">포인트</option>
								</select>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitType" value="${row.benefitType}" />
								<c:choose>
									<c:when test="${row.benefitType eq 'C'}">쿠폰-</c:when>
									<c:otherwise>포인트-</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${updateByEventProgressStatus eq 'Y'}">
								<c:choose>
									<c:when test="${row.benefitType eq 'C'}">
										<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitValue" class="benefitRemovePoint" value="${row.benefitValue}" />
										<button type="button" class="btn-sm btn-link searchBenefitCouponPop benefitRemovePoint">쿠폰조회</button>
										<span class="search-item">
											<span class="subject">${row.cpnName}</span>
											<button type="button" class="btn-item-del btnDelCpn">
												<span class="ico ico-item-del"><span class="offscreen">쿠폰 삭제</span></span>
											</button>
										</span>
									</c:when>
									<c:otherwise>
										<input type="text" name="evEventAttendanceCheckBenefitArr.benefitValue" class="ui-input num-unit100000000 benefitRemovePoint" title="포인트 입력" value="${row.benefitValue}">
										<span class="text benefitRemovePoint">포인트</span>
									</c:otherwise>
								</c:choose>
								<button type="button" class="btn-sm btn-del btnDelBenefit">삭제</button>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${row.benefitType eq 'C'}">
										<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitValue" value="${row.benefitValue}" />
										<span class="text">${row.cpnName}</span>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitValue" value="${row.benefitValue}">
										<span class="text">${row.benefitValue} 포인트</span>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</span>
				</li>
			</c:forEach>
		</ul>
	</td>
</tr>
<tr class="dispTrArea benefit4">
	<th scope="row">대상상품지정</th>
	<td class="input">
		<div class="tbl-header">
			<a href="javascript:void(0);" class="btn-sm btn-link productSearchPop">상품검색추가</a>
			<div class="fr">
				<a href="javascript:void(0);" id="productDelBtn" class="btn-sm btn-del">상품 삭제</a>
			</div>
		</div>
		<div id="productSheet" class="ibsheet-wrap"></div>
	</td>
</tr>
<tr class="dispTrArea benefit4">
	<th scope="row"><span class="th-required">픽업매장</span></th>
	<td class="input">
		<ul class="ip-box-list">
			<li>
				<ul class="ip-box-list">
					<%-- <li>
						<span class="ui-chk">
							<input id="chkStore02" name="prdtRecptStoreType" type="checkbox" ${evEvent.prdtRecptStoreType eq 'A' ? 'checked' : ''} value="A">
							<label for="chkStore02">ABC-MART 매장</label>
						</span>
						<c:choose>
							<c:when test="${evEvent.prdtRecptStoreType eq 'A'}"><a href="javascript:void(0);" class="btn-sm btn-link storePopup">매장선택</a></c:when>
							<c:otherwise><a href="javascript:void(0);" class="btn-sm btn-link storePopup" style="display:none;">매장선택</a></c:otherwise>
						</c:choose>
					</li>
					<li>
						<span class="ui-chk">
							<input id="chkStore03" name="prdtRecptStoreType" type="checkbox" ${evEvent.prdtRecptStoreType eq 'T' ? 'checked' : ''} value="T">
							<label for="chkStore03">OTS 매장</label>
						</span>
					</li>
					<li>
						<span class="ui-chk">
							<input id="chkStore04" name="prdtRecptStoreType" type="checkbox" ${evEvent.prdtRecptStoreType eq 'G' ? 'checked' : ''} value="G">
							<label for="chkStore04">GS</label>
						</span>
					</li> --%>
					<li>
						<span class="ui-chk">
							<input id="chkStore05" class="checkOnln" type="checkbox" ${evEvent.onlnRecptYn eq 'Y'  ? 'checked' : ''} value="">
							<label for="chkStore05">온라인</label>
							<input type="hidden" name="onlnRecptYn" value="${evEvent.onlnRecptYn}" />
						</span>
					</li>
					<li>
						<span class="ui-chk">
							<input id="chkStore06" class="checkStore" type="checkbox" ${not empty evEventProductReceiptStoreList  ? 'checked' : ''}>
							<label for="chkStore06">오프라인</label>
						</span>
						<a href="javascript:void(0);" class="btn-sm btn-link storePopup" ${not empty evEventProductReceiptStoreList ? '' : 'style="display:none;"'} >매장선택</a>
					</li>
				</ul>
			</li>
		</ul>
		<ul id="storeArea" class="item-list">
			<c:forEach var="row" items="${evEventProductReceiptStoreList}">
				<li>
					<input type="hidden" name="evEventProductReceiptStoreArr.storeNo" value="${row.storeNo}" />
					<span class="subject">${row.storeName}</span>
					<button type="button" class="btn-item-del btnDelStore">
						<span class="ico ico-item-del"><span class="offscreen">매장 삭제</span></span>
					</button>
				</li>
			</c:forEach>
		</ul>
	</td>
</tr>
<tr class="dispTrArea offlinePickupArea" ${not empty evEventProductReceiptStoreList ? '' : 'style="display:none;"'}>
	<th scope="row"><span class="th-required">오프라인 픽업가능기간</span></th>
	<td class="input">
		<span class="term-date-wrap">
			<span class="date-box">
				<fmt:formatDate value="${evEvent.pickupStartYmd}" pattern="yyyy.MM.dd" var="pickupStartYmd"/>
				<input type="text" name="pickupStartYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget" title="시작일 선택" value="${pickupStartYmd}">
			</span>
			<span class="text">~</span>
			<span class="date-box">
				<fmt:formatDate value="${evEvent.pickupEndYmd}" pattern="yyyy.MM.dd" var="pickupEndYmd"/>
				<input type="text" name="pickupEndYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget" title="종료일 선택" value="${pickupEndYmd}">
			</span>
		</span>
	</td>
</tr>
<tr class="dispTrArea benefit5">
	<th scope="row">혜택정보</th>
	<td class="input">
		<div class="tbl-header">
			<a href="javascript:void(0);" id="roulettePreviewBtn" class="btn-sm btn-link">영역확인</a>
		</div>
		<table class="tbl-col">
			<caption>혜택정보</caption>
			<colgroup>
				<col style="width: 6%">
				<col style="width: 15%">
				<col>
				<col style="width: 8%">
				<col style="width: 8%">
				<col style="width: 21%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">영역</th>
					<th scope="col">혜택명</th>
					<th scope="col">혜택</th>
					<th scope="col">제한수량</th>
					<th scope="col">지급수량</th>
					<th scope="col">당첨비율</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty evEventRouletteBenefitList}">
						<c:forEach var="idx" begin="1" end="6" varStatus="i">
							<tr class="benefitGbnTr">
								<td class="text-center">${idx}</td>
								<td class="input">
									<%-- <input type="hidden" name="evEventRouletteBenefitArr.eventRuletBenefitSeq" value="${i.count}" /> --%>
									<input type="text" name="evEventRouletteBenefitArr.benefitName" class="ui-input" maxlength="50" title="혜택명 입력">
								</td>
								<td class="input">
									<span class="ip-text-box benefitGbnSpan">
										<select name="evEventRouletteBenefitArr.benefitGbnCode" class="ui-sel" title="유형 선택">
											<option value="">선택</option>
											<c:forEach var="code" items="${benefitGbnCodeList}">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<td class="input">
										<input type="text" name="evEventRouletteBenefitArr.issueLimitCount" class="ui-input" title="제한수량 입력" maxlength="4" style="text-align:center;">
									</td>
								</td>
								<td class="text-center totalIssueCountArea">0<input type="hidden" name="evEventRouletteBenefitArr.issueCount" value="0" /></td>
								<td class="input">
									<span class="ip-text-box">
										<select name="evEventRouletteBenefitArr.winRateCode" class="ui-sel" title="당첨비율 선택">
											<c:forEach var="code" items="${winRateCodeList}">
												<c:choose>
													<c:when test="${code.addInfo1 ne 0}"><option data-rate="${code.addInfo1}" value="${code.codeDtlNo}">${code.addInfo1}%</option></c:when>
													<c:otherwise><option data-rate="${code.codeDtlName}" value="${code.codeDtlNo}">${code.codeDtlName}</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<input type="hidden" name="evEventRouletteBenefitArr.winRate" class="winRate" value="" />
										<input type="text" name="evEventRouletteBenefitArr.winRateValue" class="ui-input num-unit100 benefitGbnManual" title="당첨비율 입력" style="display:none;" value="0">
										<span class="text benefitGbnManual" style="display:none;">%</span>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:set var="imgIdx" value="1" />
						<c:forEach var="row" items="${evEventRouletteBenefitList}" varStatus="i">
							<tr class="benefitGbnTr">
								<td class="text-center">${i.count}<input type="hidden" name="evEventRouletteBenefitArr.eventRuletBenefitSeq" value="${row.eventRuletBenefitSeq}" /></td>
								<td class="input">
									<%-- <input type="hidden" name="evEventRouletteBenefitArr.eventRuletBenefitSeq" value="${i.count}" /> --%>
									<input type="text" name="evEventRouletteBenefitArr.benefitName" class="ui-input" title="혜택명 입력" maxlength="50" value="${row.benefitName}">
								</td>
								<td class="input">
									<span class="ip-text-box benefitGbnSpan">
										<c:choose>
											<c:when test="${updateByEventProgressStatus eq 'Y'}">
												<select name="evEventRouletteBenefitArr.benefitGbnCode" class="ui-sel" title="유형 선택">
													<option value="">선택</option>
													<c:forEach var="code" items="${benefitGbnCodeList}">
														<option ${row.benefitGbnCode eq code.codeDtlNo ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
													</c:forEach>
												</select>
											</c:when>
											<c:otherwise>
												<input type="hidden" name="evEventRouletteBenefitArr.benefitGbnCode" value="${row.benefitGbnCode}" />
												<span class="text">${row.benefitGbnCodeName}</span>
												<span class="text">${row.benefitGbnCode ne '10003' ? '-' : ''}</span>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${row.benefitGbnCode eq CommonCode.BENEFIT_GBN_CODE_COUPON}">
												<c:choose>
													<c:when test="${updateByEventProgressStatus eq 'Y'}">
														<button type="button" class="btn-sm btn-link benefitGbnArea benefitGbnCoupon searchBenefitGbnCouponPop">쿠폰조회</button>
														<span class="search-item">
														<span class="subject">${row.cpnName}</span>
															<button type="button" class="btn-item-del btnDelCpn">
																<span class="ico ico-item-del"><span class="offscreen">쿠폰 삭제</span></span>
															</button>
															<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="${row.benefitValue}" />
														</span>
													</c:when>
													<c:otherwise>
														<span class="text">${row.cpnName}</span>
														<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="${row.benefitValue}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:when test="${row.benefitGbnCode eq CommonCode.BENEFIT_GBN_CODE_POINT}">
												<c:choose>
													<c:when test="${updateByEventProgressStatus eq 'Y'}">
														<input type="text" name="evEventRouletteBenefitArr.benefitValue" class="ui-input num-unit100000000 benefitGbnArea benefitGbnPoint" maxlength="8" title="포인트 입력" value="${row.benefitValue}">
														<span class="text benefitGbnArea benefitGbnPoint">포인트</span>
													</c:when>
													<c:otherwise>
														<span class="text">${row.benefitValue}포인트</span>
														<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="${row.benefitValue}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:when test="${row.benefitGbnCode eq CommonCode.BENEFIT_GBN_CODE_ETC}">
												<c:choose>
													<c:when test="${updateByEventProgressStatus eq 'Y'}">
														<div class="file-wrap benefitGbnArea benefitGbnImg">
															<ul class="file-list">
																<li>
																	<span class="btn-box imgBoxArea">
																		<input type="file" id="benefitImgIdx_${imgIdx}" name="rouletteImages.file" title="첨부파일 추가"><label for="benefitImgIdx_${imgIdx}">찾아보기</label>
																	</span>
																	<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="" />
																</li>
																<li>
																	<span class="subject rouletteImgNameArea" id="benefitImgNameIdx_${imgIdx}">${row.imageName}</span>
																	<button type="button" class="btn-file-del" style="${row.imageName != null and row.imageName != '' ? '' : 'display: none;' }">
																		<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
																	</button>
																</li>
															</ul>
														</div>
													</c:when>
													<c:otherwise>
														<span class="text rouletteImgNameArea">${row.imageName}</span>
														<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="" />
													</c:otherwise>
												</c:choose>
												<c:set var="imgIdx" value="${imgIdx + 1}" />	<!-- 이미지 위치를 찾기위한 Index -->
											</c:when>
											<c:otherwise><div class="benefitGbnArea"><input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="" /></div></c:otherwise>
										</c:choose>
									</span>
									<td class="input">
										<input type="text" name="evEventRouletteBenefitArr.issueLimitCount" class="ui-input" title="제한수량 입력" ${row.issueLimitCount eq '9999' and (row.benefitGbnCode eq CommonCode.BENEFIT_GBN_CODE_COUPON) ? 'readonly' : ''} maxlength="4" value="${row.issueLimitCount}" style="text-align:center;">
									</td>
								</td>
								<td class="text-center totalIssueCountArea">${empty row.issueCount ? '0' : row.issueCount}<input type="hidden" name="evEventRouletteBenefitArr.issueCount" value="${empty row.issueCount ? '0' : row.issueCount}" /></td>
								<td class="input">
									<span class="ip-text-box">
										<select name="evEventRouletteBenefitArr.winRateCode" class="ui-sel" title="당첨비율 선택">
											<c:forEach var="code" items="${winRateCodeList}">
												<c:set var="winRateSelectedYn" value="" />
												<c:if test="${row.winRateCode eq code.codeDtlNo}"><c:set var="winRateSelectedYn" value="selected" /></c:if>
												<c:choose>
													<c:when test="${code.addInfo1 ne 0}"><option ${winRateSelectedYn} data-rate="${code.addInfo1}" value="${code.codeDtlNo}">${code.addInfo1}%</option></c:when>
													<c:otherwise><option ${winRateSelectedYn} data-rate="${code.codeDtlName}" value="${code.codeDtlNo}">${code.codeDtlName}</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<input type="hidden" name="evEventRouletteBenefitArr.winRate" class="winRate" value="${row.winRate}" />
										<input type="text" name="evEventRouletteBenefitArr.winRateValue" class="ui-input num-unit100 benefitGbnManual"
												title="당첨비율 입력" ${row.winRateCode eq CommonCode.WIN_RATE_CODE_INPUT ? '' : 'style="display:none;"'} value="${row.winRateValue}">
										<span class="text benefitGbnManual" ${row.winRateCode eq CommonCode.WIN_RATE_CODE_INPUT ? '' : 'style="display:none;"'}>%</span>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</td>
</tr>