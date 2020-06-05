<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<form name="frm">
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">이벤트 등록 관리</h2>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>프로모션</li>
							<li>이벤트 관리</li>
							<li>이벤트 등록 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->
	
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">이벤트 기본정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
	
			<!-- S : tbl-row -->
			<input type="hidden" id="eventNo" name="eventNo" value="${evEvent.eventNo}">
			<input type="hidden" id="drawJoinCheckCnt" name="drawJoinCheckCnt" value="${not empty evEvent.drawJoinCheckCnt ? evEvent.drawJoinCheckCnt : '0'}">
			<c:set var="updateByEventProgressStatus" value="${evEvent.eventProgressStatus ne 'ing' and evEvent.eventProgressStatus ne 'end' ? 'Y' : 'N'}" />
			<table class="tbl-row">
				<caption>이벤트 기본정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">유형</span>
						</th>
						<td class="input" colspan="3">
							<c:choose>
								<c:when test="${empty evEvent.eventNo}">
									<select name="eventTypeCode" class="ui-sel" title="유형 선택" ${not empty evEvent.eventNo and evEvent.eventJoinType eq 'D' ? 'disabled' : ''}>
										<!-- <option value="">전체</option> -->
										<c:forEach var="code" items="${eventTypeCodeList}">
											<option ${evEvent.eventTypeCode eq code.codeDtlNo ? 'selected': ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<span class="text">${evEvent.eventTypeCodeName}<input type="hidden" name="eventTypeCode" value="${evEvent.eventTypeCode}" /></span>
								</c:otherwise>							
							</c:choose>
							<input type="hidden" name="eventTypeWithConditionYn" value="${not empty evEvent.eventNo and evEvent.eventJoinType eq 'D' ? 'Y' : 'N'}" />
						</td>
					</tr>
					<tr>
						<th scope="row">이벤트 번호</th>
						<td>
							<c:if test="${not empty evEvent.eventNo}">${evEvent.eventNo}</c:if>
						</td>
						<th scope="row">내부 관리번호</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<!-- TODO : 기획 fix 후 입력 제한 글자수 변경 -->
								<input type="text" name="insdMgmtInfoText" class="ui-input text-unit20" title="내부 관리번호 입력" maxlength="100" value="${evEvent.insdMgmtInfoText}">
								<button type="button" id="duplCheck" class="btn-sm btn-func">중복확인</button>
								<c:choose>
									<c:when test="${empty evEvent.insdMgmtInfoText}"><input type="hidden" id="duplCheckVal" value="N" /></c:when>
									<c:otherwise><input type="hidden" id="duplCheckVal" value="Y" /></c:otherwise>
								</c:choose>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이벤트명</span>
						</th>
						<td class="input">
							<!-- TODO : 기획 fix 후 입력 제한 글자수 변경 -->
							<input type="text" name="eventName" class="ui-input" title="이벤트명 입력" placeholder="30자 이내로 입력" maxlength="30" value="${evEvent.eventName}">
						</td>
						<th scope="row">
							<span class="th-required">전시여부</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoDisplay02" name="dispYn" type="radio" ${evEvent.dispYn eq 'Y' ? 'checked' : ''} value="Y">
										<label for="rdoDisplay02">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoDisplay03" name="dispYn" type="radio" ${evEvent.dispYn eq 'N' or empty evEvent.eventNo ? 'checked' : ''} value="N">
										<label for="rdoDisplay03">미전시</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이벤트기간</span>
						</th>
						<td class="input" >
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioTerm01" name="eventTermSetupYn" type="radio" ${evEvent.eventTermSetupYn eq 'Y' or empty evEvent.eventTermSetupYn ? 'checked' : ''} value="Y">
										<label for="radioTerm01">수시</label>
									</span>
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<span class="date-box">
											<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="yyyy.MM.dd" var="eventStartYmd"/>
											<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="HH" var="eventStartH"/>
											<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="mm" var="eventStartM"/>
											<input type="text" name="paramEventStartYmd" data-role="datepicker" class="ui-cal js-ui-cal eventTermSetupArea" title="날짜 선택" value="${eventStartYmd}">
										</span>
										<select name="paramEventStartH" class="ui-sel eventTermSetupArea" title="시작시 선택">
											<c:forEach var="row" begin="0" end="23">
												<c:choose>
													<c:when test="${row < 10}">
														<c:set var="paramEventStartH" value="0${row}" />
														<option ${eventStartH eq paramEventStartH ? 'selected' : ''} value="${paramEventStartH}">${paramEventStartH}시</option>
													</c:when>
													<c:otherwise><option ${eventStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<select name="paramEventStartM" class="ui-sel eventTermSetupArea" title="시작분 선택">
											<c:forEach var="row" begin="0" end="59">
												<c:choose>
													<c:when test="${row < 10}">
														<c:set var="paramEventStartM" value="0${row}" />
														<option ${eventStartM eq paramEventStartM ? 'selected' : ''} value="${paramEventStartM}">${paramEventStartM}분</option>
													</c:when>
													<c:otherwise><option ${eventStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<span class="text">~</span>
										<span class="date-box">
											<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="yyyy.MM.dd" var="eventEndYmd"/>
											<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="HH" var="eventEndH"/>
											<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="mm" var="eventEndM"/>
											<input type="text" name="paramEventEndYmd" data-role="datepicker" class="ui-cal js-ui-cal eventTermSetupArea" title="종료 날짜 선택" value="${eventEndYmd}">
										</span>
										<select name="paramEventEndH" class="ui-sel eventTermSetupArea" title="종료시 선택">
											<c:forEach var="row" begin="0" end="23" varStatus="i">
												<c:choose>
													<c:when test="${row < 10}">
														<c:set var="paramEventEndH" value="0${row}" />
														<option ${eventEndH eq paramEventEndH ? 'selected' : ''} value="${paramEventEndH}">${paramEventEndH}시</option>
													</c:when>
													<c:otherwise><option ${eventEndH eq row or (empty evEvent.eventNo and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<select name="paramEventEndM" class="ui-sel eventTermSetupArea" title="종료분 선택">
											<c:forEach var="row" begin="0" end="59" varStatus="i">
												<c:choose>
													<c:when test="${row < 10}">
														<c:set var="paramEventEndM" value="0${row}" />
														<option ${eventEndM eq paramEventEndM ? 'selected' : ''} value="${paramEventEndM}">${paramEventEndM}분</option>
													</c:when>
													<c:otherwise><option ${eventEndM eq row or (empty evEvent.eventNo and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</span>
									<!-- E : term-date-wrap -->
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioTerm02" name="eventTermSetupYn" type="radio" ${evEvent.eventTermSetupYn eq 'N' ? 'checked' : ''} value="N">
										<label for="radioTerm02">고정</label>
									</span>
								</li>
							</ul>
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
										<input id="rdoUse02" name="useYn" type="radio" ${evEvent.useYn eq 'Y' or empty evEvent.useYn ? 'checked' : ''} value="Y">
										<label for="rdoUse02">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoUse03" name="useYn" type="radio" ${evEvent.useYn eq 'N' ? 'checked' : ''} value="N">
										<label for="rdoUse03">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">채널</span></th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<%--
								<c:forEach items="${channelList}" var="chnnl" varStatus="status">
									<c:if test="${chnnl.chnnlNo ne Const.SITE_CHNNL_ART}">
										<c:set var="chnnlStatus" value="${status.count == 2 || evEventTargetChannelList[0].chnnlNo eq chnnl.chnnlNo ? 'checked' : ''}" />
										<li>
											<span class="ui-rdo">
												<input id="rdoChannel${status.count}" name="chnnlNo" type="radio" value="${chnnl.chnnlNo}" ${chnnlStatus}>
												<label for="rdoChannel${status.count}">${chnnl.chnnlName}</label>
											</span>
										</li>
									</c:if>
								</c:forEach>
								--%>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkChannel01" name="chkChannelModule" type="checkbox" checked>
										<label for="chkChannel01">전체</label>
									</span>
								</li>
								<c:forEach var="channel" items="${channelList}" varStatus="i">
									<c:set var="checkYn" value="" />
									<c:if test="${empty evEventTargetChannelList}"><c:set var="checkYn" value="checked" /></c:if>
									<c:forEach var="row" items="${evEventTargetChannelList}">
										<c:if test="${channel.chnnlNo eq row.chnnlNo}"><c:set var="checkYn" value="checked" /></c:if>
									</c:forEach>
									<li>
										<span id="checkChannelArea" class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="evEventTargetChannelArr.chnnlNo" id="chkChannel${i.count }" class="checkChannel" data-site-no="${channel.siteNo}" ${checkYn} value="${channel.chnnlNo}">
											<label for="chkChannel${i.count}">${channel.chnnlName}</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr id="eventTypeBefore">
						<th scope="row">
							<span class="th-required">대상회원</span>
						</th>
						<td class="input">
							<!-- S : 20190308 수정 // 대상 회원 UI변경 일괄 적용 -->
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkMemberGrade0" class="targetMemberGrade" type="checkbox">
										<label for="chkMemberGrade0">전체</label>
									</span>
								</li>
								<c:forEach var="member" items="${memberTypeCodeList}" varStatus="i">
									<c:set var="checkYn" value="" />
									<c:forEach var="row" items="${evEventTargetGradeList}">
										<c:if test="${member.codeDtlNo eq row.memberTypeCode and row.empYn ne 'Y'}"><c:set var="checkYn" value="checked" /></c:if>
										<c:if test="${row.empYn eq 'Y'}"><c:set var="empCheckYn" value="checked" /></c:if>
									</c:forEach>
									<c:if test="${member.codeDtlNo ne CommonCode.MEMBER_TYPE_CODE_NONMEMBER}">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" name="evEventTargetGradeArr.memberTypeCode" id="chkMemberGrade${i.count }" class="targetMemberCheck targetMemberGrade" ${checkYn} value="${member.codeDtlNo}">
												<label for="chkMemberGrade${i.count}">${member.codeDtlName}</label>
											</span>
										</li>
									</c:if>
								</c:forEach>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="checkbox" name="empYn" id="chkEmp01" ${empCheckYn} class="targetMemberCheck targetMemberGrade" value="Y">
										<label for="chkEmp01">임직원</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
		
							<!-- S : member-grade-list -->
							<%-- <div class="member-grade-list">
								<span class="text">( 회원등급 :</span>
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach var="grade" items="${mbshpGradeCodeList}" varStatus="i">
										<c:set var="checkYn" value="" />
										<c:forEach var="row" items="${evEventTargetGradeList}">
											<c:if test="${row.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP and row.empYn ne 'Y'}">
												<c:if test="${grade.codeDtlNo eq row.mbshpGradeCode}"><c:set var="checkYn" value="checked" /></c:if>
											</c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" name="mbshpGradeCodes" id="chkMember${i.count }" ${checkYn} disabled class="targetMembershp" value="${grade.codeDtlNo}">
												<label for="chkMember${i.count}">${grade.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
								<span class="text">)</span>
							</div> --%>
							<!-- E : member-grade-list -->
							<!-- E : 20190308 수정 // 대상 회원 UI변경 일괄 적용 -->
							<span class="text">※ 일반 고객(온라인 회원, 통합멤버십회원, 비회원)  또는 임직원 단독으로만 체크 가능합니다.</span><br>
							<span class="text">※ 일반 고객만 체크 시 고객 화면에만 노출 되며, 임직원 화면에는 노출 되지 않습니다.</span><br>
							<span class="text">※ 임직원만 체크 시 임직원 전용으로 진행 되며, 고객 화면에는 노출 되지 않습니다.</span><br>
						</td>
						<th scope="row"><span class="th-required">디바이스</span></th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkDevice01" name="chkDeviceModule" type="checkbox" checked>
										<label for="chkDevice01">전체</label>
									</span>
								</li>
								<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
									<c:set var="checkYn" value="" />
									<c:if test="${empty evEventTargetDeviceList}"><c:set var="checkYn" value="checked" /></c:if>
									<c:forEach var="row" items="${evEventTargetDeviceList}">
										<c:if test="${code.codeDtlNo eq row.deviceCode}"><c:set var="checkYn" value="checked" /></c:if>
									</c:forEach>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="deviceCodes" id="chkDevice${i.count }" ${checkYn} value="${code.codeDtlNo}">
											<label for="chkDevice${i.count}">${code.codeDtlName}</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">이벤트 설명</th>
						<td class="input" colspan="3">
							<input type="text" name="noteText" class="ui-input" title="설명 입력" value="${evEvent.noteText}">
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">응모조건/혜택정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>응모조건/혜택정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody id="benefitTypeArea">
					<%@include file="/WEB-INF/views/promotion/event/include/eventInfo.jsp" %>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			
			<!-- S : 이벤트 유형 > 참여형(룰렛형)인 경우 노출되는 영역 -->
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap dispTrArea benefit5">
				<div class="fr">
					<ol class="tbl-desc-list">
						<li>* 쿠폰 이외 혜택은 지급수량이 무제한일 경우 9999 입력하세요</li>
					</ol>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 이벤트 유형 > 참여형(룰렛형)인 경우 노출되는 영역 -->
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">이벤트 약관 사용 여부</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>응모조건/혜택정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr> <!--  201906 모든 영역에 노출로 변경 -->
						<th scope="row">이벤트 약관 <br /> 사용 여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioTerms01" name="eventStplatUseYn" type="radio" ${evEvent.eventStplatUseYn eq 'N' or empty evEvent.eventStplatUseYn ? 'checked' : ''} value="N">
										<label for="radioTerms01">없음</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioTerms02" name="eventStplatUseYn" type="radio" ${evEvent.eventStplatUseYn eq 'Y' ? 'checked' : ''} value="Y">
										<label for="radioTerms02">있음</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
					
							<!-- S : 이벤트 약관 사용 여부 > 있음 선택시 노출 -->
							<!-- S : editor-wrap -->
							<div id="stplatArea" class="editor-wrap" ${evEvent.eventStplatUseYn eq 'N' or empty evEvent.eventStplatUseYn ? 'style="display:none;"' : ''} >
								<textarea id="eventStplatContText" name="eventStplatContText" rows="10" cols="80">${evEvent.eventStplatContText}</textarea>
							</div>
							<!-- E : editor-wrap -->
							<!-- E : 이벤트 약관 사용 여부 > 있음 선택시 노출 -->
						</td>
					</tr>
				</tbody>
			</table>
	
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">이미지 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
	
			<!-- S : tbl-row -->
			<div id="imageSetArea">
				<c:forEach  var="row" items="${evEventImageList}">
					<c:if test="${row.deviceCode eq CommonCode.DEVICE_PC and row.imageGbnType ne 'G'}">
						<c:set var="pcImageName" value="${row.imageName}" />
						<c:set var="pcImageUrl" value="${row.imageUrl}" />
						<c:set var="pcAltrnText" value="${row.altrnText}" />
					</c:if>
					<c:if test="${row.deviceCode eq CommonCode.DEVICE_MOBILE and row.imageGbnType ne 'G'}">
						<c:set var="moImageName" value="${row.imageName}" />
						<c:set var="moImageUrl" value="${row.imageUrl}" />
						<c:set var="moAltrnText" value="${row.altrnText}" />
					</c:if>
					<c:if test="${row.deviceCode eq CommonCode.DEVICE_PC and row.imageGbnType eq 'G'}">
						<c:set var="pcBgImageName" value="${row.imageName}" />
						<c:set var="pcBgImageUrl" value="${row.imageUrl}" />
						<c:set var="pcBgAltrnText" value="${row.altrnText}" />
					</c:if>
					<c:if test="${row.deviceCode eq CommonCode.DEVICE_MOBILE and row.imageGbnType eq 'G'}">
						<c:set var="moBgImageName" value="${row.imageName}" />
						<c:set var="moBgImageUrl" value="${row.imageUrl}" />
						<c:set var="moBgAltrnText" value="${row.altrnText}" />
					</c:if>
				</c:forEach>
			</div>
			<table class="tbl-row">
				<caption>이미지 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">PC 목록 섬네일</span>
							<div>388*500(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="file" id="pcEventImg" name="pcEventImg" title="첨부파일 추가">
											<input type="hidden" name="evEventImageArr.imageGbnType" value="I"/>
											<label for="pcEventImg">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" id="pcEventImgName" class="subject bannerImgName">${pcImageName}</a>
										<button type="button" class="btn-file-del" style="${not empty pcImageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="evEventImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcAltrnText}">
								</div>
								<div class="img-wrap"><img src="${pcImageUrl}" alt="${pcImageName}" /></div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">PC 상세</span>
							<div>1200*가변</div>
						</th>
						<td class="input">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea id="pcEventContText" name="pcEventContText" rows="10" cols="80">${evEvent.pcEventContText}</textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					<tr class="bgImageArea">
						<th scope="row">
							<span class="th-required">PC 룰렛 판 이미지</span>
							<div>890*890(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="file" id="pcEventBgImg" name="pcEventBgImg" title="첨부파일 추가">
											<input type="hidden" name="evEventImageArr.imageGbnType" value="G"/>
											<label for="pcEventBgImg">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" id="pcEventBgImgName" class="subject bannerImgName">${pcBgImageName}</a>
										<button type="button" class="btn-file-del" style="${not empty pcBgImageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="evEventImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcBgAltrnText}">
								</div>
								<div class="img-wrap"><img src="${pcBgImageUrl}" alt="${pcBgImageName}" /></div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">MOBILE 목록 섬네일</span>
							<div>660*850(최대10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="file" id="moEventImg" name="moEventImg" title="첨부파일 추가">
											<input type="hidden" name="evEventImageArr.imageGbnType" value="I"/>
											<label for="moEventImg">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" id="moEventImgName" class="subject bannerImgName">${moImageName}</a>
										<button type="button" class="btn-file-del" style="${not empty moImageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="evEventImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moAltrnText}">
								</div>
								<div class="img-wrap"><img src="${moImageUrl}" alt="${moImageName}" /></div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">MOBILE 상세</span>
							<div>660*가변</div>
						</th>
						<td class="input">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea id="mobileEventContText" name="mobileEventContText" rows="10" cols="80">${evEvent.mobileEventContText}</textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					<tr class="bgImageArea">
						<th scope="row">
							<span class="th-required">MOBILE 룰렛 판 이미지</span>
							<div>622*622(최대10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="file" id="moEventBgImg" name="moEventBgImg" title="첨부파일 추가">
											<input type="hidden" name="evEventImageArr.imageGbnType" value="G"/>
											<label for="moEventBgImg">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" id="moEventBgImgName" class="subject bannerImgName">${moBgImageName}</a>
										<button type="button" class="btn-file-del" style="${not empty moBgImageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="evEventImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moBgAltrnText}">
								</div>
								<div class="img-wrap"><img src="${moBgImageUrl}" alt="${moBgImageName}" /></div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
	
			<!-- S : tbl-row -->
			<c:if test="${not empty evEvent.eventNo}">
				<table class="tbl-row">
					<caption>이벤트 등록 작성 정보</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">작성자</th>
							<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${evEvent.rgsterNo}">${evEvent.rgsterInfo}</a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${evEvent.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${evEvent.moderNo}">${evEvent.moderInfo}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${evEvent.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- E : tbl-row -->
	
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<!-- <div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div> -->
				<div class="fr">
					<a href="/promotion/event" class="btn-normal btn-link">목록</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->
	
			<!-- S : content-bottom -->
			<div class="content-bottom">
				<!-- <a href="javascript:void(0);" class="btn-lg btn-del">취소</a> -->
				<c:if test="${not empty evEvent.eventNo and evEvent.przwrPblcYn eq 'Y' and evEvent.resultCount > 0}"><a href="/promotion/event/result/detail?eventNo=${evEvent.eventNo}" class="btn-lg btn-func">당첨자발표</a></c:if>
				<c:if test="${empty evEvent.eventNo or evEvent.tmprSaveYn eq 'Y'}"><a href="javascript:void(0);" data-tmpr-save-yn="Y" class="btn-lg btn-save">임시저장</a></c:if>
				<a href="javascript:void(0);" data-tmpr-save-yn="N" id="save-event" class="btn-lg btn-save">저장</a>
				<input type="hidden" name="tmprSaveYn" value="N" />
			</div>
			<!-- E : content-bottom -->
		</div>
	</div>
	<!-- E : container -->
</form>
	
<script type="text/javascript">
		var codeCombo = ${codeCombo};
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>		
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.detail.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.coupon.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.product.js<%=_DP_REV%>"></script>
<script type="text/javascript">
	//그리드 좁아짐 방지
	abc.biz.promotion.event.product.sheet.init();
	if($('#eventNo').val() != '' && $('[name=eventTypeCode]').val() == '10003') abc.biz.promotion.event.product.getList();
	
	//그리드 좁아짐 방지
	$('#benefit2Th').attr('rowspan', 2);
	abc.biz.promotion.event.coupon.sheet.init();
	if($('#eventNo').val() != "" && $('[name=eventJoinType]:checked').val() == 'C') {
		$('.cpnTrArea').show();
		abc.biz.promotion.event.coupon.getList();
	} else {
		$('#benefit2Th').attr('rowspan', 1);
		$('.cpnTrArea').hide();
		$('#eventCouponSheet').hide();
	}
</script>
<%@include file="/WEB-INF/views/promotion/event/include/eventInfoTemplate.jsp" %>
<%@include file="/WEB-INF/views/common/footer.jsp" %>