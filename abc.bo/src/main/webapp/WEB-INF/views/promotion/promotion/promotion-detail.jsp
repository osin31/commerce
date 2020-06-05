<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		
	<form name="frm">
		<input type="hidden" name="promoNo" id="promoNo" value="${prPromotion.promoNo}" />
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">프로모션  관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>프로모션 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">프로모션 ${empty prPromotion.promoNo ? '등록' : '상세'}</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<c:if test="${prPromotion.promoProgressStatus ne 'end'}">
								<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</c:if>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>프로모션 등록</caption>
					<colgroup>
						<col style="width: 160px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">프로모션 유형</span>
							</th>
							<td class="input">
								<select name="promoTypeCode" class="ui-sel" title="프로모션 유형 선택">
									<option value="">선택</option>
									<c:forEach var="code" items="${promoTypeCodeList}">
										<option ${code.codeDtlNo eq prPromotion.promoTypeCode ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
									</c:forEach>
								</select>
							</td>
							<th scope="row">프로모션 번호</th>
							<td class="input">
								<span class="text">${prPromotion.promoNo}</span>
								<c:if test="${not empty prPromotion.promoNo}"><a href="javascript:void(0);" id="promoStatusPop" class="btn-sm btn-link">프로모션 현황</a></c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="useYn" id="radioUse02" ${prPromotion.useYn eq 'Y' or empty prPromotion.useYn ? 'checked' : ''} value="Y">
											<label for="radioUse02">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="useYn" id="radioUse03" ${prPromotion.useYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioUse03">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">내부 관리번호</th>
							<td class="input">
								<!-- TODO : 기획 fix 후 글자수 제한 placeholder 수정 -->
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="insdMgmtInfoText" class="ui-input text-unit20" title="내부 관리번호 입력" <c:if test="${not empty prPromotion.insdMgmtInfoText}">disabled</c:if> placeholder="숫자, 영문, 특수문자 N자 이내로 입력" value="${prPromotion.insdMgmtInfoText}">
									<c:if test="${empty prPromotion.promoNo}">
										<a href="javascript:void(0);" id="duplCheck" class="btn-sm btn-link">중복확인</a>
									</c:if>
									<input type="hidden" id="duplCheckVal" value="N" />
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">프로모션명</span>
							</th>
							<td colspan="3" class="input">
								<!-- TODO : 기획 fix 후 글자수 제한 placeholder 수정 -->
								<input type="text" name="promoName" class="ui-input" title="프로모션명 입력" placeholder="100자 이내로 입력" maxlength="100" value="${prPromotion.promoName}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">프로모션 기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<fmt:formatDate value="${prPromotion.promoStartDtm}" pattern="yyyy.MM.dd" var="promoStartYmd"/>
										<fmt:formatDate value="${prPromotion.promoStartDtm}" pattern="HH" var="promoStartH"/>
										<fmt:formatDate value="${prPromotion.promoStartDtm}" pattern="mm" var="promoStartM"/>
										<input type="text" name="paramPromoStartYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" value="${promoStartYmd}">
									</span>
									<select name="paramPromoStartH" class="ui-sel" title="시작시 선택">
										<c:forEach var="row" begin="0" end="23">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramPromoStartH" value="0${row}" />
													<option ${promoStartH eq paramPromoStartH ? 'selected' : ''} value="${paramPromoStartH}">${paramPromoStartH}시</option>
												</c:when>
												<c:otherwise><option ${promoStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramPromoStartM" class="ui-sel displayDate" title="시작분 선택">
										<c:forEach var="row" begin="0" end="59">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramPromoStartM" value="0${row}" />
													<option ${promoStartM eq paramPromoStartM ? 'selected' : ''} value="${paramPromoStartM}">${paramPromoStartM}분</option>
												</c:when>
												<c:otherwise><option ${promoStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<span class="date-box">
										<fmt:formatDate value="${prPromotion.promoEndDtm}" pattern="yyyy.MM.dd" var="promoEndYmd"/>
										<fmt:formatDate value="${prPromotion.promoEndDtm}" pattern="HH" var="promoEndH"/>
										<fmt:formatDate value="${prPromotion.promoEndDtm}" pattern="mm" var="promoEndM"/>
										<input type="text" name="paramPromoEndYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" value="${promoEndYmd}">
									</span>
									<select name="paramPromoEndH" class="ui-sel" title="종료시 선택">
										<c:forEach var="row" begin="0" end="23" varStatus="i">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramPromoEndH" value="0${row}" />
													<option ${promoEndH eq paramPromoEndH ? 'selected' : ''} value="${paramPromoEndH}">${paramPromoEndH}시</option>
												</c:when>
												<c:otherwise><option ${promoEndH eq row or (empty prPromotion.promoNo and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramPromoEndM" class="ui-sel displayDate" title="종료분 선택">
										<c:forEach var="row" begin="0" end="59" varStatus="i">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramPromoEndM" value="0${row}" />
													<option ${promoEndM eq paramPromoEndM ? 'selected' : ''} value="${paramPromoEndM}">${paramPromoEndM}분</option>
												</c:when>
												<c:otherwise><option ${promoEndM eq row or (empty prPromotion.promoNo and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr class="promotionAffltsArea promoTypeCodeArea">
							<th scope="row">
								<span class="th-required">제휴사 선택</span>
							</th>
							<td class="input">
								<select name="affltsCode" class="ui-sel" title="제휴사 선택">
									<option value="">제휴사 선택</option>
									<c:forEach var="code"  items="${affltsCodeList}">
										<option ${prPromotion.affltsCode eq code.codeDtlNo ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">재고 차감 위치</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStock01" name="stockDdctType" type="radio" ${prPromotion.stockDdctType eq 'A' or empty prPromotion.stockDdctType ? 'checked' : ''} value="A">
											<label for="radioStock01">전체 재고</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStock02" name="stockDdctType" type="radio" ${prPromotion.stockDdctType eq 'O' ? 'checked' : ''} value="O">
											<label for="radioStock02">온라인 재고 한정</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">디바이스</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
										<c:set var="checkYn" value="" />
										<c:forEach var="row" items="${prPromotionTargetDeviceList}">
											<c:if test="${code.codeDtlNo eq row.deviceCode}"><c:set var="checkYn" value="checked" /></c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" name="deviceCodes" id="chkDevice${i.count }" ${empty prPromotion.promoNo ? 'checked' : checkYn} value="${code.codeDtlNo}">
												<label for="chkDevice${i.count}">${code.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr class="cpnSmtmApplyPsbltArea">
							<%-- <th scope="row">프로모션 상품중복</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioPromotion01" name="promoDupApplyPsbltYn" type="radio" ${prPromotion.promoDupApplyPsbltYn eq 'Y' or empty prPromotion.promoDupApplyPsbltYn ? 'checked' : ''} value="Y">
											<label for="radioPromotion01">허용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioPromotion02" name="promoDupApplyPsbltYn" type="radio" ${prPromotion.promoDupApplyPsbltYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioPromotion02">허용안함</label>
										</span>
									</li>
								</ul> --%>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">쿠폰 사용 허용여부</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : 프로모션 유형 > 1+1증정일 경우, 허용 input disabled 속성 추가 해주세요 -->
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay01" name="cpnSmtmApplyPsbltYn" type="radio" ${prPromotion.cpnSmtmApplyPsbltYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioDisplay01">허용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02" name="cpnSmtmApplyPsbltYn" type="radio" ${prPromotion.cpnSmtmApplyPsbltYn eq 'N' or empty prPromotion.cpnSmtmApplyPsbltYn ? 'checked' : ''} value="N">
											<label for="radioDisplay02">허용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						 <c:forEach var="channel" items="${channelList}" varStatus="i">
							<c:set var="checkYn" value="" />
							<c:forEach var="row" items="${prPromotionTargetChannelList}">
								<c:if test="${channel.chnnlNo eq row.chnnlNo}"><c:set var="checkYn" value="checked" /></c:if>
							</c:forEach>
							<li>
								<span id="checkChannelArea" class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="hidden" name="prPromotionTargetChannelArr.chnnlNo" id="chkChannel${i.count }" class="checkChannel" data-site-no="${channel.siteNo}" ${empty prPromotion.promoNo ? 'checked' : checkYn} value="${channel.chnnlNo}">
									<label for="chkChannel${i.count}">${channel.chnnlName}</label>
								</span>
							</li>
						</c:forEach>
									
						<tr id="trGradeArea">
							<th scope="row">
								<!-- DESC : 20190312 수정 // 프로모션 대상 회원 > 회원유형 항목명 변경 -->
								<span class="th-required">대상 회원</span>
							</th>
							<td class="input" colspan="3">
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
										<c:forEach var="row" items="${prPromotionTargetGradeList}">
											<c:if test="${member.codeDtlNo eq row.memberTypeCode and row.empYn ne 'Y'}"><c:set var="checkYn" value="checked" /></c:if>
											<c:if test="${row.empYn eq 'Y'}"><c:set var="empCheckYn" value="checked" /></c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" name="prPromotionTargetGradeArr.memberTypeCode" id="chkMemberGrade${i.count }" ${checkYn} value="${member.codeDtlNo}">
												<label for="chkMemberGrade${i.count}">${member.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="empYn" id="chkEmp01" ${empCheckYn} value="Y">
											<label for="chkEmp01">임직원</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
								<ul class="td-text-list">
									<li>* 일반 고객(온라인 회원, 통합멤버십회원, 비회원)  또는 임직원 단독으로만 체크 가능합니다.</li>
									<li>* 일반 고객만 체크 시 고객 화면에만 노출 되며, 임직원 화면에는 노출 되지 않습니다.</li>
									<li>* 임직원만 체크 시 임직원 전용으로 진행 되며, 고객 화면에는 노출 되지 않습니다.</li>
								</ul>

								<!-- S : member-grade-list -->
								<%-- <div class="member-grade-list">
									<span class="text">( 회원등급 :</span>
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<c:forEach var="grade" items="${mbshpGradeCodeList}" varStatus="i">
											<c:set var="checkYn" value="" />
											<c:forEach var="row" items="${prPromotionTargetGradeList}">
												<c:if test="${row.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<c:if test="${grade.codeDtlNo eq row.mbshpGradeCode}"><c:set var="checkYn" value="checked" /></c:if>
												</c:if>
											</c:forEach>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="mbshpGradeCodes" id="chkMember${i.count }" ${empty prPromotion.promoNo ? 'checked' : checkYn} disabled value="${grade.codeDtlNo}">
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
							</td>
						</tr>
						<!-- S : 프로모션 유형 > 1+1 증정일 경우 노출되는 영역 -->
						<%-- <tr class="promoTypeCodeArea stdrSellAmtArea">
							<th scope="row">
								<span class="th-required">프로모션 상품 기준가격</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="stdrSellAmt" class="ui-input num-unit100000000" title="가격 입력" placeholder="숫자만 입력" value="${prPromotion.stdrSellAmt}">
									<span class="text">원</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr> --%>
						<!-- E : 프로모션 유형 > 1+1 증정일 경우 노출되는 영역 -->

						<!-- S : 프로모션 유형 > 다족구매 할인일 경우 노출되는 영역 -->
						<tr class="promoTypeCodeArea multiBuyDiscountArea">
							<th scope="row">
								<span class="th-required">족수별 할인율</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul id="multiBuyArea" class="ip-box-list vertical">
									<c:choose>
										<c:when test="${empty prPromotionMultiBuyDiscountList}">
											<li>
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">1족</span>
													<input type="text" name="prPromotionMultiBuyDiscountArr.dscntRate" class="ui-input num-unit100" title="할인율 입력">
													<span class="text">% 할인</span>
												</span>
												<!-- E : ip-text-box -->
												<a href="javascript:void(0);" id="multiBuyAdd" class="btn-sm btn-func">행추가</a>
												<a href="javascript:void(0);" id="multiBuyDel" class="btn-sm btn-del">행삭제</a>
											</li>
											<!-- <li>
												S : ip-text-box
												<span class="ip-text-box">
													<span class="text">2족</span>
													<input type="text" name="prPromotionMultiBuyDiscountArr.dscntRate" class="ui-input num-unit100" title="할인율 입력">
													<span class="text">% 할인</span>
												</span>
											</li> -->
										</c:when>
										<c:otherwise>
											<c:forEach var="row" items="${prPromotionMultiBuyDiscountList}" varStatus="i">
												<li>
													<span class="ip-text-box">
														<span class="text">${i.count}족</span>
														<input type="text" name="prPromotionMultiBuyDiscountArr.dscntRate" class="ui-input num-unit100" title="할인율 입력" value="${row.dscntRate}">
														<span class="text">% 할인</span>
													</span>
													<c:if test="${i.count eq 1}">
														<a href="javascript:void(0);" id="multiBuyAdd" class="btn-sm btn-func">행추가</a>
														<a href="javascript:void(0);" id="multiBuyDel" class="btn-sm btn-del">행삭제</a>
													</c:if>
												</li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- E : 프로모션 유형 > 다족구매 할인일 경우 노출되는 영역 -->

						<!-- S : 프로모션 유형 > 즉시할인, 제휴사 즉시할인, 기간한정 특가일 경우 노출되는 영역 -->
						<tr class="promoTypeCodeArea imdtlDscntTypeArea">
							<th scope="row">
								<span class="th-required">즉시할인 형태</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleType01" name="imdtlDscntType" type="radio" ${prPromotion.imdtlDscntType eq 'R' or empty prPromotion.imdtlDscntType ? 'checked' : ''} value="R">
											<label for="radioSaleType01">정률 할인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleType02" name="imdtlDscntType" type="radio" ${prPromotion.imdtlDscntType eq 'A' ? 'checked' : ''} value="A">
											<label for="radioSaleType02">정액 할인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleType03" name="imdtlDscntType" type="radio" ${prPromotion.imdtlDscntType eq 'U' ? 'checked' : ''} value="U">
											<label for="radioSaleType03">균일가</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">할인율(액)</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="imdtlDscntValue" class="ui-input num-unit100000000" title="할인율 입력" value="${prPromotion.imdtlDscntValue}">
									<span class="text">% (원)</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<!-- S : 프로모션 유형 > 기간한정 특가 미노출 -->
						<%-- <tr class="promoTypeCodeArea promoApplyTypeTopArea">
							<th scope="row">
								<span class="th-required">프로모션 적용 범위</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioRange01" name="promoApplyType" type="radio" ${prPromotion.promoApplyType eq 'P' or empty prPromotion.promoApplyType ? 'checked' : ''} value="P">
											<label for="radioRange01">상품</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioRange02" name="promoApplyType" type="radio" ${prPromotion.promoApplyType eq 'C' ? 'checked' : ''} value="C">
											<label for="radioRange02">카테고리</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioRange03" name="promoApplyType" type="radio" ${prPromotion.promoApplyType eq 'B' ? 'checked' : ''} value="B">
											<label for="radioRange03">브랜드</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr> --%>
						<!-- E : 프로모션 유형 > 기간한정 특가 미노출 -->
						<!-- E : 프로모션 유형 > 즉시할인, 제휴사 즉시할인, 기간한정 특가일 경우 노출되는 영역 -->

						<tr>
							<th scope="row">프로모션 설명</th>
							<td class="input" colspan="3">
								<!-- TODO : 기획 fix 후 글자수 제한 placeholder 수정 -->
								<input type="text" name="noteText" class="ui-input" maxlength="250" title="프로모션 설명 입력">
								<ul class="td-text-list">
									<li>* 타 프로모션에 적용되어있는 상품은 빨간줄로 노출됩니다.(중복프로모션)</li>
									<li>* 다족구매유형은 진행중인 또는 진행예정인 모든 프로모션 상품과 중복 적용되지 않습니다.</li>
									<li>* 타임특가유형은 진행중인 또는 진행예정인 타임특가 프로모션과 중복 적용되지 않습니다.</li>
								</ul>
							</td>
						</tr>
						
						<%-- <c:forEach  var="row" items="${prPromotionImageList}">
							<c:if test="${row.deviceCode eq CommonCode.DEVICE_PC}">
								<c:set var="pcImageName" value="${row.imageName}" />
								<c:set var="pcImageUrl" value="${row.imageUrl}" />
								<c:set var="pcAltrnText" value="${row.altrnText}" />
							</c:if>
							<c:if test="${row.deviceCode eq CommonCode.DEVICE_MOBILE}">
								<c:set var="moImageName" value="${row.imageName}" />
								<c:set var="moImageUrl" value="${row.imageUrl}" />
								<c:set var="moAltrnText" value="${row.altrnText}" />
							</c:if>
						</c:forEach> --%>
						<!-- S : 프로모션 유형 > 상품 사은품 증정일 경우 노출되는 영역 -->
						<%-- <tr class="promoTypeCodeArea promoBannerArea">
							<th scope="row">
								프로모션 배너 (PC)
								<div>N*N (최대 000MB까지 등록가능 <br />파일유형 : jpg, gif)</div>
							</th>
							<td class="input" colspan="3">
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" name="prPromotionImageArr.imageName" id="pcBannerImgName" value="${pcImageName}">
												<input type="file" id="pcBannerImg" name="pcBannerImg" title="첨부파일 추가">
												<label for="pcBannerImg">찾아보기</label>
											</span>
										</li>
										<li>
											<span class="subject">${pcImageName}</span>
											<button type="button" class="btn-file-del" style="${not empty pcImageName ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" name="prPromotionImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcAltrnText}">
									</div>
									<div class="img-wrap"><img alt="${pcImageName}" src="${pcImageUrl}"></div>
								</div>
							</td>
						</tr>
						<tr class="promoTypeCodeArea promoBannerArea">
							<th scope="row">
								프로모션 배너 (Mobile)
								<div>N*N (최대 000MB까지 등록가능 <br />파일유형 : jpg, gif)</div>
							</th>
							<td class="input" colspan="3">
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" name="prPromotionImageArr.imageName" id="mobileBannerImgName" value="${moImageName}">
												<input type="file" id="mobileBannerImg" name="mobileBannerImg" title="첨부파일 추가">
												<label for="mobileBannerImg">찾아보기</label>
											</span>
										</li>
										<li>
											<span class="subject">${moImageName}</span>
											<button type="button" class="btn-file-del" style="${not empty moImageName ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" name="prPromotionImageArr.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moAltrnText}">
									</div>
									<div class="img-wrap"><img alt="${moImageName}" src="${moImageUrl}"></div>
								</div>
							</td>
						</tr> --%>
						<!-- E : 프로모션 유형 > 상품 사은품 증정일 경우 노출되는 영역 -->
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : 프로모션 유형 > 즉시할인, 제휴사 즉시할인일 경우 노출되는 영역 -->
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 id="productTitleArea" class="content-title">대상 상품 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : 적용대상목록 > 상품 선택시 -->
				<!-- S : tbl-controller -->
				<div class="tbl-controller itemApplyTypeArea">
					<!-- <div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select class="ui-sel" id="selTermsModule">
								<option>15개 보기</option>
							</select>
						</span>
					</div> -->
					<div class="fr">
						<c:if test="${prPromotion.promoProgressStatus ne 'end'}">
							<a href="javascript:void(0);" class="btn-sm btn-del productDelBtn" data-type="product1list">선택삭제</a>
						</c:if>
						<a href="javascript:void(0);" class="btn-sm btn-func productSearchPop" data-type="product1list">상품검색추가</a>
						<span id="eventLimitQtyArea" class="ip-text-box" style="display:none;">
							<input type="text" id="eventLimitQtyVal" class="ui-input num-unit100000000" title="행사수량 입력" placeholder="행사수량" maxlength="3">
							<a href="javascript:void(0);" id="eventLimitQtyBtn" class="btn-sm btn-func">행사수량 적용</a>
						</span>
						<c:if test="${prPromotion.promoTypeCode ne '10005'}">
						<span class="ip-text-box">
							<input type="text" id="cmsnRateVal" class="ui-input num-unit100000000" title="수수료율 입력" placeholder="수수료율(%)" maxlength="3">
							<a href="javascript:void(0);" id="cmsnRateBtn" class="btn-sm btn-func">수수료율 적용</a>
						</span>
						</c:if>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div id="product1Sheet" class="ibsheet-wrap itemApplyTypeArea"></div>
				<!-- E : ibsheet-wrap -->
				<!-- E : 적용대상목록 > 상품 선택시 -->

				<!-- S : tbl-row -->
				<%-- <table class="tbl-row">
					<caption>적용 대상 목록</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<!-- S : 적용대상목록 > 카테고리 선택시 -->
						<tr class="categoryApplyTypeArea promoApplyTypeArea">
							<th scope="row" rowspan="2">카테고리</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box dp-category-area">
									<select class="ui-sel dp-category-select chnnl-no" title="채널 선택"></select>
									<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택"><option value="">대카테고리 선택</option></select>
									<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택"><option value="">중카테고리 선택</option></select>
									<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택"><option value="">소카테고리 선택</option></select>
									<a href="javascript:void(0);" id="addCategory" class="btn-sm btn-func">추가</a>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr class="categoryApplyTypeArea promoApplyTypeArea">
							<td class="input">
								<!-- S : item-list -->
								<ul id="categoryArea" class="item-list">
									<c:forEach var="row" items="${prPromotionTargetCategoryList}">
										<li>
											<span class="subject">[${row.chnnlName}] ${row.ctgrNamePath}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
											</button>
											<input type="hidden" name="prPromotionTargetCategoryArr.ctgrNo" value="${row.ctgrNo}" />
											
											<input type="hidden" name="prPromotionTargetCategoryArr.chnnlNo" value="${row.chnnlNo}" />
										</li>
									</c:forEach>
								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
						<!-- E : 적용대상목록 > 카테고리 선택시 -->

						<!-- S : 적용대상목록 > 브랜드 선택시 -->
						<tr class="brandApplyTypeArea promoApplyTypeArea">
							<th scope="row" rowspan="2">브랜드</th>
							<td class="input">
								<!-- DESC : 20190305 수정 // search dropdown 다중검색으로 변경 -->
								<a href="javascript:void(0);" id="searchBrandPop" class="btn-sm btn-link">브랜드 찾기</a>
							</td>
						</tr>
						<tr class="brandApplyTypeArea promoApplyTypeArea">
							<td class="input">
								<!-- S : item-list -->
								<ul id="brandArea" class="item-list">
									<c:forEach var="row" items="${prPromotionTargetBrandList}">
										<li>
											<span class="subject">${row.brandName}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
											</button>
											<input type="hidden" name="prPromotionTargetBrandArr.brandNo" value="${row.brandNo}" />
										</li>
									</c:forEach>
								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
						<!-- E : 적용대상목록 > 브랜드 선택시 -->
					</tbody>
				</table> --%>
				<!-- E : tbl-row -->
				<!-- E : 프로모션 유형 > 즉시할인, 제휴사 즉시할인일 경우 노출되는 영역 -->

				<!-- S : content-header -->
				<!-- <div class="content-header promoTypeCodeArea itemExceptArea">
					<div class="fl">
						<h3 class="content-title">제외 상품 목록</h3>
					</div>
				</div> -->
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<!-- <div class="tbl-controller promoTypeCodeArea itemExceptArea">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule02">목록개수</label>
							<select class="ui-sel" id="selTermsModule02">
								<option value="15">15개 보기</option>
								<option value="30">30개 보기</option>
								<option value="45">45개 보기</option>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del productDelBtn" data-type="product2list">선택삭제</a>
						<a href="javascript:void(0);" class="btn-sm btn-link productSearchPop" data-type="product2list">상품검색추가</a>
					</div>
				</div> -->
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<!-- <div id="product2Sheet" class="ibsheet-wrap promoTypeCodeArea itemExceptArea"></div> -->
				<!-- E : ibsheet-wrap -->

				<!-- S : 프로모션 유형 > 상품 사은품 증정일 경우 노출되는 영역 -->
				<!-- S : content-header -->
				<div class="content-header promoTypeCodeArea giftArea">
					<div class="fl">
						<h3 class="content-title">제공 사은품</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller promoTypeCodeArea giftArea">
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del giftDelBtn">삭제</a>
						<a href="javascript:void(0);" id="searchGiftPop" class="btn-sm btn-link">사은품 등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : tbl-col -->
				<table class="tbl-col promoTypeCodeArea giftArea">
					<caption>제공 사은품</caption>
					<colgroup>
						<col>
						<col>
						<col>
						<col>
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호 </th>
							<th scope="col">사은품 ID</th>
							<th scope="col">사은품명</th>
							<th scope="col">재고수량</th>
							<th scope="col">행사수량</th>
							<th scope="col">잔여수량</th>
							<th scope="col">지급수량</th>
						</tr>
					</thead>
					<tbody id="giftTbodyArea">
						<c:forEach var="row" items="${prPromotionTargetGiftList}" varStatus="i">
							<tr class="giftTr">
								<td class="text-center">${i.count}</td>
								<td class="text-center">
									${row.prdtNo}<input type="hidden" name="prPromotionTargetGiftArr.prdtNo" value="${row.prdtNo}" />
									<c:choose>
										<c:when test="${prPromotion.promoProgressStatus eq 'none' or prPromotion.promoProgressStatus eq 'wait'}"><input type="hidden" name="prPromotionTargetGiftArr.giftDelCheck" value="Y" /></c:when>
										<c:otherwise><input type="hidden" name="giftDelCheck" value="N" /></c:otherwise>
									</c:choose>
								</td>
								<td class="text-center">${row.prdtName}</td>
								<td class="text-center">${row.totalStockQty}<input type="hidden" name="giftTotalStockQty" value="${row.totalStockQty}"/></td>
								<td class="input">
									<input type="text" name="prPromotionTargetGiftArr.maxEventLimitQty" maxlength="4" class="ui-input text-center giftInputArea" title="수량 입력" value="${row.maxEventLimitQty}">
									<input type="hidden" name="prPromotionTargetGiftArr.prdtType" value="G" />
								</td>
								<td class="text-center">${row.eventLimitQty}</td>
								<td class="text-center">${row.maxEventLimitQty - row.eventLimitQty}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- E : tbl-col -->
				<!-- E : 프로모션 유형 > 상품 사은품 증정일 경우 노출되는 영역 -->

				<!-- S : tbl-row -->
				<c:if test="${not empty prPromotion.promoNo}">
					<table class="tbl-row">
						<caption>프로모션관리 등록 작성 정보</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${prPromotion.rgsterNo}">${prPromotion.rgsterInfo}</a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${prPromotion.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${prPromotion.moderNo}">${prPromotion.moderInfo}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${prPromotion.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fr">
						<a href="/promotion/promotion" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<!-- <a href="/promotion/promotion" class="btn-lg btn-del">취소</a> -->
					
					<c:if test="${prPromotion.promoProgressStatus ne 'end'}">
						<a href="javascript:void(0);" id="save-promotion" class="btn-lg btn-save">저장</a>
					</c:if>
					<input type="hidden" id="promoProgressStatus" name="promoProgressStatus" value="${not empty prPromotion.promoProgressStatus ? prPromotion.promoProgressStatus : 'none'}" />
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
	</form>
	
	<!-- template -->
	<template id="multiBuyTemplate">
		<li class="addMultiBuyLi">
			<span class="ip-text-box">
				<span class="text multiBuyIdx"></span>
				<input type="text" name="prPromotionMultiBuyDiscountArr.dscntRate" class="ui-input num-unit100" title="할인율 입력">
				<span class="text">% 할인</span>
			</span>
		</li>
	</template>
	
	<template id="LiTemplate">
		<li>
			<span class="subject"></span>
			<button type="button" class="btn-item-del liDelBtn">
				<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
			</button>
		</li>
	</template>
<script type="text/javascript">
	$(function() {
		abc.biz.promotion.promotion.product.codeCombo = ${codeCombo};
	})
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>		
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.promotion.product.js<%=_DP_REV%>"></script>		
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.promotion.detail.js<%=_DP_REV%>"></script>		
<%@include file="/WEB-INF/views/common/footer.jsp" %>