<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input type="hidden" id="bizNoText" name="bizNoText" value="<c:out value='${vendorInfo.bizNoText}'/>"> <!-- 사업자번호 -->
<input type="hidden" id="crprtNoText" name="crprtNoText" > <!-- 법인번호 -->
<input type="hidden" id="mailBizNoText" name="mailBizNoText" > <!-- 통신판매번호 -->

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 업체정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>입점사 업체정보</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">업체명</span>
				</th>
				<td class="input">
				<c:choose>
					<c:when test="${vendorInfo.status != 'U'}">
						<input type="text" id="vndrName" name="vndrName" class="ui-input" title="업체명 입력" maxlength="50">
					</c:when>
					<c:otherwise>
						<span class="text"><c:out value="${vendorInfo.vndrName} (${vendorInfo.vndrNo})"/></span>
						<a href="javascript:void(0);" id="vendorHoliday" class="btn-sm btn-link">휴일정보 보기</a>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">입점사 코드 (ERP)</span>
				</th>
				<c:choose>
					<c:when test="${vendorInfo.status != 'U'}">
						<!-- S : ip-text-box -->
					<td class="input">
						<span class="ip-text-box">
							<input type="text" id="insdMgmtInfoText" name="insdMgmtInfoText" class="ui-input" title="입점사 코드 입력" maxlength="50">
							<button type="button" id="checkInsdMgmtInfo" class="btn-sm btn-func">중복확인</button>
						</span>
						<!-- E : ip-text-box -->
					</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:out value="${vendorInfo.insdMgmtInfoText}"/>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">사업자 번호</span>
				</th>
				<c:choose>
					<c:when test="${vendorInfo.status != 'U'}">
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" name="bizNo1" id="bizNo1" maxlength="3" class="ui-input num-unit100" title="사업자 번호 입력">
								<span class="text">-</span>
								<input type="text" name="bizNo2" id="bizNo2" maxlength="2" class="ui-input num-unit10" title="사업자 번호 입력">
								<span class="text">-</span>
								<input type="text" name="bizNo3" id="bizNo3" maxlength="5" class="ui-input num-unit10000" title="사업자 번호 입력">
								<button type="button" id="checkbizNo" class="btn-sm btn-func">중복확인</button>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:out value="${vendorInfo.bizNo1}"/>
							<span class="text">-</span>
							<c:out value="${vendorInfo.bizNo2}"/>
							<span class="text">-</span>
							<c:out value="${vendorInfo.bizNo3}"/>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<th scope="row">관리자 메모</th>
				<td class="input">
					<input type="text" name="adminMemoText" id="adminMemoText" class="ui-input" maxlength="500" title="관리자 메모 입력" value='<c:out value="${vendorInfo.adminMemoText}"/>'>
				</td>
			</tr>
		</tbody>
	</table>

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>입점사 업체정보</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">기업형태</span>
				</th>
				<c:if test="${vendorInfo.status == 'U'}">
					<c:if test="${vendorInfo.crprtType == 'C'}">
						<td>법인</td>
					</c:if>
					<c:if test="${vendorInfo.crprtType == 'I'}">
						<td>개인</td>
					</c:if>
				</c:if>
				<c:if test="${vendorInfo.status != 'U'}">
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="crprtType_C" name="crprtType" type="radio" value="C" checked="checked">
									<label for="crprtType_C">법인</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="crprtType_I" name="crprtType" type="radio" value="I">
									<label for="crprtType_I">개인</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</c:if>
				<th scope="row">
					<span>법인번호</span>
				</th>
				<td class="input">
					<!-- TODO : 법인번호 글자수 기획 fix 이후 수정 예정 -->
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="crprtNo1" class="ui-input num-unit100000" maxlength="6" title="법인번호 입력" value='<c:out value="${vendorInfo.crprtNo1}"/>' >
						<span class="text">-</span>
						<input type="text" id="crprtNo2" class="ui-input num-unit10000000" maxlength="8" title="법인번호 입력" value='<c:out value="${vendorInfo.crprtNo2}"/>' >
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">업태</span>
				</th>
				<td class="input">
					<input type="text" id="busCondText" name="busCondText" maxlength="30" class="ui-input" title="업태 입력" value='<c:out value="${vendorInfo.busCondText}"/>' <c:if test="${stauts == 'U'}">readonly="readonly"</c:if>>
				</td>
				<th scope="row">
					<span class="th-required">업종</span>
				</th>
				<td class="input">
					<input type="text" id="busTypeText" name="busTypeText" maxlength="30" class="ui-input" title="업종 입력" value='<c:out value="${vendorInfo.busTypeText}"/>' <c:if test="${stauts == 'U'}">readonly="readonly"</c:if>>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">대표자명</span>
				</th>
				<td class="input">
					<input type="text" id="bossName" name="bossName" class="ui-input" maxlength="30" title="대표자명 입력" value='<c:out value="${vendorInfo.bossName}"/>'>
				</td>
				<th scope="row">
					<span class="th-required">대표전화</span>
				</th>
				<td class="input">
					<input type="text" id="rprsntTelNoText" name="rprsntTelNoText" maxlength="15" class="ui-input" title="대표전화 입력" value='<c:out value="${vendorInfo.rprsntTelNoText}"/>'>
				</td>
			</tr>
			<tr>
				<th scope="row">팩스</th>
				<td class="input" colspan="3">
					<input type="text" id="faxNoText" name="faxNoText" class="ui-input" maxlength="15" title="팩스번호 입력" value='<c:out value="${vendorInfo.faxNoText}"/>'>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">주소</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : address-box -->
					<span class="address-box">
						<span class="zip-code-wrap">
							<input type="text" id="postCodeTextBase" name="postCodeText" class="ui-input" title="우편번호 입력" readonly value='<c:out value="${vendorInfo.postCodeText}"/>'>
							<button type="button" id="postSearchBtnBase" class="btn-sm btn-link">우편번호 찾기</button>
						</span>
						<span class="address-wrap">
							<input type="text" id="postAddrTextBase" name="postAddrText" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly value='<c:out value="${vendorInfo.postAddrText}" />'>
							<input type="text" id="dtlAddrTextBase" name="dtlAddrText" maxlength="100" class="ui-input" placeholder="상세주소" title="상세주소 입력" value='<c:out value="${vendorInfo.dtlAddrText}"/>'>
						</span>
					</span>
					<!-- E : address-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">통신판매번호</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input id="mailBizNo1" name="mailBizNo1" type="text" class="ui-input" maxlength="20" title="통신판매번호 입력" value='<c:out value="${vendorInfo.mailBizNo1}"/>'>
						<span class="text">-</span>
						<input id="mailBizNo2" name="mailBizNo2" type="text" class="ui-input" maxlength="20" title="통신판매번호 입력" value='<c:out value="${vendorInfo.mailBizNo2}"/>'>
						<span class="text">-</span>
						<input id="mailBizNo3" name="mailBizNo3" type="text" class="ui-input" maxlength="20" title="통신판매번호 입력" value='<c:out value="${vendorInfo.mailBizNo3}"/>'>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">배송업체 지정</span>
				</th>
				<td class="input">
					<!-- S : 20190215 수정 // 배송업체지정 search dropdown > select로 변경 -->
					<select class="ui-sel" id="logisVndrCode" name="logisVndrCode" title="기본배송업체 " data-placeholder="배송업체명 (업체코드)">
						<option value="">선택</option>
						<c:forEach var="logisVndrCode" items="${codeList.LOGIS_VNDR_CODE}" varStatus="status">
							<option value="${logisVndrCode.codeDtlNo}" <c:if test="${vendorInfo.logisVndrCode eq logisVndrCode.codeDtlNo}">selected</c:if> >${logisVndrCode.codeDtlName}</option>
						</c:forEach>
					</select>
					<!-- E : 20190215 수정 // 배송업체지정 search dropdown > select로 변경 -->
				</td>
				<th scope="row">
					<span class="th-required">배송약정일</span>
				</th>
				<td class="input">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<!-- TODO : 배송소요일 제한 일수 기획 fix 후 수정 -->
						<span class="text">고객결제일 기준</span>
						<input type="text" id="dlvyDayCount" name="dlvyDayCount" class="ui-input num-unit10 text-right" maxlength="2" title="배송소요일 입력" value='<c:out value="${vendorInfo.dlvyDayCount}" default="3"/>'>
						<span class="text">일 이내 출고</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">기본 배송비</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="freeDlvyStdrAmt" name="freeDlvyStdrAmt" class="ui-input num-unit100000000 text-right" maxlength="7" title="상품 총 금액 입력" value='<c:out value="${vendorInfo.freeDlvyStdrAmt}"/>'>
						<span class="text">원 미만</span>
					</span>
					<!-- E : ip-text-box -->
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<span class="text">배송비</span>
						<input type="text" id="dlvyAmt" name="dlvyAmt" class="ui-input num-unit100000000 text-right" maxlength="5" title="배송비 금액 입력" value='<c:out value="${vendorInfo.dlvyAmt}"/>'>
						<span class="text">원</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">패널티 부과비율</span>
				</th>
				<td class="input">
					<input type="text" id="penltyLevyRate" name="penltyLevyRate" class="ui-input num-unit10 text-right" maxlength="2" title="패널티 부과비율 입력" value='<c:out value="${vendorInfo.penltyLevyRate}" default="4"/>'>
					<span class="text">%</span>
				</td>
				<th scope="row">
					<span class="th-required">패널티 면제비율</span>
				</th>
				<td class="input">
					<input type="text" id="penltyExmtRate" name="penltyExmtRate" class="ui-input num-unit100 text-right" maxlength="3" title="패널티 면제비율 입력" value='<c:out value="${vendorInfo.penltyExmtRate}" default="2"/>'>
					<span class="text">%</span>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->



