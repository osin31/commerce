<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/x-template" id="benefitTbodyTemplate">
<tr>
	<td class="text-center targetGradeName"></td>
	<td class="input">
		<select name="evEventTargetGradeArr.joinLimitTypeCode" class="ui-sel" title="제한조건 선택">
			<c:forEach var="code" items="${joinLimitTypeCodeList}">
				<c:if test="${code.codeDtlNo ne '10005'}">
					<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
				</c:if>
			</c:forEach>
		</select>
	</td>
</tr>
</script>

<script type="text/x-template" id="eventType1Template">
<tr class="eventType">
	<th scope="row"><span class="th-required">당첨발표여부</span></th>
	<td class="input">
		<ul class="ip-box-list">
			<li>
				<span class="ui-rdo">
					<input id="rdoWin02" name="przwrPblcYn" type="radio" ${evEvent.przwrPblcYn eq 'N' or empty evEvent.przwrPblcYn ? 'checked' : ''} value="N">
					<label for="rdoWin02">발표안함</label>
				</span>
			</li>
			<li>
				<span class="ui-rdo">
					<input id="rdoWin03" name="przwrPblcYn" type="radio" ${evEvent.przwrPblcYn eq 'Y' ? 'checked' : ''}  value="Y">
					<label for="rdoWin03">발표</label>
				</span>
	
				<span class="date-box">
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="yyyy.MM.dd" var="przwrPblcTodoYmd"/>
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="HH" var="przwrPblcTodoStartH"/>
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="mm" var="przwrPblcTodoStartM"/>
					<input type="text" name="paramPrzwrPblcTodoYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'Y' ? '' : 'disabled'} title="날짜 선택" value="${przwrPblcTodoYmd}">
				</span>
				<select name="paramPrzwrPblcTodoStartH" class="ui-sel dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'Y' ? '' : 'disabled'} title="시작시 선택">
					<c:forEach var="row" begin="0" end="23">
						<c:choose>
							<c:when test="${row < 10}">
								<c:set var="paramPrzwrPblcTodoStartH" value="0${row}" />
								<option ${przwrPblcTodoStartH eq paramPrzwrPblcTodoStartH ? 'selected' : ''} value="${paramPrzwrPblcTodoStartH}">${paramPrzwrPblcTodoStartH}시</option>
							</c:when>
							<c:otherwise><option ${przwrPblcTodoStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<select name="paramPrzwrPblcTodoStartM" class="ui-sel dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'Y' ? '' : 'disabled'} title="시작분 선택">
					<c:forEach var="row" begin="0" end="59">
						<c:choose>
							<c:when test="${row < 10}">
								<c:set var="paramPrzwrPblcTodoStartM" value="0${row}" />
								<option ${przwrPblcTodoStartM eq paramPrzwrPblcTodoStartM ? 'selected' : ''} value="${paramPrzwrPblcTodoStartM}">${paramPrzwrPblcTodoStartM}분</option>
							</c:when>
							<c:otherwise><option ${przwrPblcTodoStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</li>
		</ul>
	</td>
	<th scope="row">
		<span class="th-required">검색키워드</span>
	</th>
	<td class="input">
		<input type="text" name="srchKeyWord" class="ui-input" title="검색키워드 입력" maxlength="100" value="${evEvent.srchKeyWord}">
		<ul class="td-text-list">
			<li>* 키워드를 쉼표 (,)로 구분하여 주세요</li>
		</ul>
	</td>
</tr>
</script>

<script type="text/x-template" id="eventType2Template">
<tr class="eventType">
	<th scope="row"><span class="th-required">당첨발표여부</span></th>
	<td class="input">
		<ul class="ip-box-list">
			<li>
				<span class="ui-rdo">
					<input id="rdoWin02" name="przwrPblcYn" type="radio" ${evEvent.przwrPblcYn eq 'N' ? 'checked' : ''} value="N">
					<label for="rdoWin02">발표안함</label>
				</span>
			</li>
			<li>
				<span class="ui-rdo">
					<input id="rdoWin03" name="przwrPblcYn" type="radio" ${evEvent.przwrPblcYn eq 'Y' or empty evEvent.przwrPblcYn ? 'checked' : ''}  value="Y">
					<label for="rdoWin03">발표</label>
				</span>
	
				<span class="date-box">
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="yyyy.MM.dd" var="przwrPblcTodoYmd"/>
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="HH" var="przwrPblcTodoStartH"/>
					<fmt:formatDate value="${evEvent.przwrPblcTodoYmd}" pattern="mm" var="przwrPblcTodoStartM"/>
					<input type="text" name="paramPrzwrPblcTodoYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'N' ? 'disabled' : ''} title="날짜 선택" value="${przwrPblcTodoYmd}">
				</span>
				<select name="paramPrzwrPblcTodoStartH" class="ui-sel dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'N' ? 'disabled' : ''} title="시작시 선택">
					<c:forEach var="row" begin="0" end="23">
						<c:choose>
							<c:when test="${row < 10}">
								<c:set var="paramPrzwrPblcTodoStartH" value="0${row}" />
								<option ${przwrPblcTodoStartH eq paramPrzwrPblcTodoStartH ? 'selected' : ''} value="${paramPrzwrPblcTodoStartH}">${paramPrzwrPblcTodoStartH}시</option>
							</c:when>
							<c:otherwise><option ${przwrPblcTodoStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<select name="paramPrzwrPblcTodoStartM" class="ui-sel dateTarget pblcTodoTarget" ${evEvent.przwrPblcYn eq 'N' ? 'disabled' : ''} title="시작분 선택">
					<c:forEach var="row" begin="0" end="59">
						<c:choose>
							<c:when test="${row < 10}">
								<c:set var="paramPrzwrPblcTodoStartM" value="0${row}" />
								<option ${przwrPblcTodoStartM eq paramPrzwrPblcTodoStartM ? 'selected' : ''} value="${paramPrzwrPblcTodoStartM}">${paramPrzwrPblcTodoStartM}분</option>
							</c:when>
							<c:otherwise><option ${przwrPblcTodoStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</li>
		</ul>
	</td>
	<th scope="row"><span class="th-required">당첨자구입일</span></th>
	<td class="input">
		<span class="term-date-wrap">
			<span class="date-box">
				<fmt:formatDate value="${evEvent.przwrOrderStartYmd}" pattern="yyyy.MM.dd" var="przwrOrderStartYmd"/>
				<fmt:formatDate value="${evEvent.przwrOrderStartYmd}" pattern="HH" var="przwrOrderStartH"/>
				<fmt:formatDate value="${evEvent.przwrOrderStartYmd}" pattern="mm" var="przwrOrderStartM"/>
				<input type="text" name="paramPrzwrOrderStartYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget" title="시작일 선택" value="${przwrOrderStartYmd}">
			</span>
			<select name="paramPrzwrOrderStartH" class="ui-sel dateTarget" title="시작시 선택">
				<c:forEach var="row" begin="0" end="23">
					<c:choose>
						<c:when test="${row < 10}">
							<c:set var="paramPrzwrOrderStartH" value="0${row}" />
							<option ${przwrOrderStartH eq paramPrzwrOrderStartH ? 'selected' : ''} value="${paramPrzwrOrderStartH}">${paramPrzwrOrderStartH}시</option>
						</c:when>
						<c:otherwise><option ${przwrOrderStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<select name="paramPrzwrOrderStartM" class="ui-sel dateTarget" title="시작분 선택">
				<c:forEach var="row" begin="0" end="59">
					<c:choose>
						<c:when test="${row < 10}">
							<c:set var="paramPrzwrOrderStartM" value="0${row}" />
							<option ${przwrOrderStartM eq paramPrzwrOrderStartM ? 'selected' : ''} value="${paramPrzwrOrderStartM}">${paramPrzwrOrderStartM}분</option>
						</c:when>
						<c:otherwise><option ${przwrOrderStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>

			<span class="text">~</span>
			<span class="date-box">
				<fmt:formatDate value="${evEvent.przwrOrderEndYmd}" pattern="yyyy.MM.dd" var="przwrOrderEndYmd"/>
				<fmt:formatDate value="${evEvent.przwrOrderEndYmd}" pattern="HH" var="przwrOrderEndH"/>
				<fmt:formatDate value="${evEvent.przwrOrderEndYmd}" pattern="mm" var="przwrOrderEndM"/>
				<input type="text" name="paramPrzwrOrderEndYmd" data-role="datepicker" class="ui-cal js-ui-cal dateTarget" title="종료일 선택" value="${przwrOrderEndYmd}">
			</span>
			<select name="paramPrzwrOrderEndH" class="ui-sel dateTarget" title="종료시 선택">
				<c:forEach var="row" begin="0" end="23">
					<c:choose>
						<c:when test="${row < 10}">
							<c:set var="paramPrzwrOrderEndH" value="0${row}" />
							<option ${przwrOrderEndH eq paramPrzwrOrderEndH ? 'selected' : ''} value="${paramPrzwrOrderEndH}">${paramPrzwrOrderEndH}시</option>
						</c:when>
						<c:otherwise><option ${przwrOrderEndH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<select name="paramPrzwrOrderEndM" class="ui-sel dateTarget" title="종료분 선택">
				<c:forEach var="row" begin="0" end="59">
					<c:choose>
						<c:when test="${row < 10}">
							<c:set var="paramPrzwrOrderEndM" value="0${row}" />
							<option ${przwrOrderEndM eq paramPrzwrOrderEndM ? 'selected' : ''} value="${paramPrzwrOrderEndM}">${paramPrzwrOrderEndM}분</option>
						</c:when>
						<c:otherwise><option ${przwrOrderEndM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</span>
	</td>
</tr>
<tr class="eventType">
	<th scope="row">
		<span class="th-required">검색키워드</span>
	</th>
	<td class="input">
		<input type="text" name="srchKeyWord" class="ui-input" title="검색키워드 입력" maxlength="100" value="${evEvent.srchKeyWord}">
		<ul class="td-text-list">
			<li>* 키워드를 쉼표 (,)로 구분하여 주세요</li>
		</ul>
	</td>
</tr>
</script>

<script type="text/x-template" id="basicBenefitTemplate1">
	<li>
		<span class="ip-text-box">
			<input type="text" name="evEventAttendanceCheckBenefitArr.atendDayCount" class="ui-input num-unit100000000 inputAtendDayCount" maxlength="2" title="출석일수입력">
			<span class="text">일 출석 시</span>
			<select name="evEventAttendanceCheckBenefitArr.benefitType" class="ui-sel" title="유형 선택">
				<option value="">선택</option>
				<option value="C">쿠폰</option>
				<option value="P">포인트</option>
			</select>
			<button type="button" class="btn-sm btn-link searchBenefitCouponPop benefitRemovePoint">쿠폰조회</button>
			<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitValue" class="benefitRemovePoint" value="">
		</span>
		<button type="button" class="btn-sm btn-del btnDelBenefit">삭제</button>
	</li>
</script>

<script type="text/x-template" id="basicBenefitTemplate2">
	<li>
		<span class="ip-text-box">
			<input type="text" name="evEventAttendanceCheckBenefitArr.atendDayCount" class="ui-input num-unit100000000 inputAtendDayCount" maxlength="2" title="출석일수입력">
			<span class="text">일 출석 시</span>
			<select name="evEventAttendanceCheckBenefitArr.benefitType" class="ui-sel" title="유형 선택">
				<option value="">선택</option>
				<option value="C">쿠폰</option>
				<option value="P">포인트</option>
			</select>
			<input type="text" name="evEventAttendanceCheckBenefitArr.benefitValue" class="ui-input num-unit100000000 benefitRemovePoint" maxlength="8" title="포인트 입력">
			<span class="text benefitRemovePoint">포인트</span>
		</span>
		<button type="button" class="btn-sm btn-del btnDelBenefit">삭제</button>
	</li>
</script>

<script type="text/x-template" id="geBenefitTemplate1">
	<li>
		<span class="ip-text-box">
			<select name="geBenefitType" class="ui-sel" title="유형 선택">
				<option value="C">쿠폰</option>
			</select>
			<button type="button" class="btn-sm btn-link searchBenefitCouponPop">쿠폰조회</button>
			<input type="hidden" name="geBenefitValue" value="">
			<input type="hidden" name="geBenefitName" value="">
		</span>
		<button type="button" class="btn-sm btn-del btnDelGeBenefit">삭제</button>
	</li>
</script>

<script type="text/x-template" id="geBenefitTemplate2">
	<li>
		<span class="ip-text-box">
			<select name="geBenefitType" class="ui-sel" title="유형 선택">
				<option value="P">포인트</option>
			</select>
			<input type="text" name="geBenefitValue" class="ui-input num-unit100000000" maxlength="8" title="포인트 입력">
			<span class="text">포인트</span>
		</span>
		<button type="button" class="btn-sm btn-del btnDelGeBenefit">삭제</button>
	</li>
</script>

<script type="text/x-template" id="searchItemTemplate">
	<span class="search-item">
		<span class="subject"></span>
		<button type="button" class="btn-item-del btnDelCpn">
			<span class="ico ico-item-del"><span class="offscreen">쿠폰 삭제</span></span>
		</button>
	</span>
</script>

<script type="text/x-template" id="searchStoreTemplate">
	<li>
		<span class="subject"></span>
		<button type="button" class="btn-item-del btnDelStore">
			<span class="ico ico-item-del"><span class="offscreen">매장 삭제</span></span>
		</button>
	</li>
</script>

<script type="text/x-template" id="benefitGbnPointTemplate">
	<input type="text" name="evEventRouletteBenefitArr.benefitValue" class="ui-input num-unit100000000 benefitGbnArea benefitGbnPoint" maxlength="8" title="포인트 입력">
	<span class="text benefitGbnArea benefitGbnPoint">포인트</span>
</script>

<script type="text/x-template" id="benefitGbnCouponTemplate">
	<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" class="benefitRemovePoint benefitGbnArea" value="">
	<button type="button" class="btn-sm btn-link benefitGbnArea benefitGbnCoupon searchBenefitGbnCouponPop">쿠폰조회</button>
</script>

<script type="text/x-template" id="benefitGbnImgTemplate">	
	<div class="file-wrap benefitGbnArea benefitGbnImg">
		<ul class="file-list">
			<li>
				<span class="btn-box imgBoxArea"></span>
				<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="" />
			</li>
			<li>
				<span class="subject rouletteImgNameArea"></span>
				<button type="button" class="btn-file-del" style="display:none;">
					<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
				</button>
			</li>
		</ul>
	</div>
</script>
