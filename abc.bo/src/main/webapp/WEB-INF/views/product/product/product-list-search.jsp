<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="PRDT_TYPE_CODE_FREEGIFT" value="10003"/>
<input type="hidden" id="vndr-no" value="${not empty pageContext.getAttribute('userDetails').getVndrNo() and pageContext.getAttribute('userDetails').getVndrNo() ne 'null' ? pageContext.getAttribute('userDetails').getVndrNo() : ''}"/>
<!-- S : search-wrap -->
<form id="search-form" name="search-form">
	<div class="search-wrap">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품정보 검색</caption>
				<colgroup>
					<col style="width:130px;"/>
					<col/>
					<col style="width:79px;"/>
					<col style="width:130px;"/>
					<col/>
				</colgroup>
				<tbody>
					<c:if test="${Const.AUTH_APPLY_SYSTEM_TYPE_BO eq userAuthority }">
						<tr>
							<th scope="row">자사 / 입점 구분</th>
							<td class="input">
								<select class="ui-sel" title="상품코드 구분 선택" id="mmny-prdt-yn" name="mmnyPrdtYn" style="width:120px;">
									<option value="">전체</option>
									<option value="Y" ${param.mmnyPrdtYn eq 'Y' ? 'selected' : ''}>자사</option>
									<option value="N" ${param.mmnyPrdtYn eq 'N' ? 'selected' : ''}>입점</option>
								</select>
								<select class="ui-sel" title="상품코드 구분 선택" id="site-no" name="siteNo" style="width:120px;">
									<option value="">전체</option>
									<c:forEach items="${searchConditionSiteList }" var="item">
										<option value="${item.siteNo }">${item.siteName }</option>
									</c:forEach>
								</select>
								<select class="ui-sel" title="상품코드 구분 선택" id="chnnl-no" name="chnnlNo" style="width:120px;">
									<option value="">전체</option>
									<c:forEach items="${searchConditionSiteChannelList }" var="item">
										<c:if test="${not empty param.chnnlNo}">
											<option value="10000" ${param.chnnlNo eq '10000' ? 'selected' : ''}>A-RT</option>
										</c:if>
										<c:if test="${not empty item.vndrNo }">
											<option value="${item.chnnlNo }" ${param.chnnlNo eq item.chnnlNo ? 'selected' : ''}>${item.chnnlName }</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td></td>
							<th scope="row">입점사명</th>
							<td class="input">
								<span class="ip-search-box">
									<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" placeholder="입점사명" name="vndrName" value="" />
									<input type="hidden" name="vndrNo" data-item-search="vndr-no"/>
									<a href="javascript:void(0);" id="search-vndr" class="btn-search" data-button-popup="find-vendor"><i class="ico-search"><span class="offscreen">검색</span></i></a>
								</span>
							</td>
						</tr>
					</c:if>
					<tr>
						<th scope="row">검색어</th>
						<td class="input">
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" id="search-keyword-type" name="searchItemType">
									<option value="prdt-name">상품명(국문/영문)</option>
									<option value="style-info">스타일</option>
									<option value="prdt-color-info">색상코드</option>
									<option value="srch-key-word-text">상품검색어</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" id="search-keyword" name="searchItemKeyword"/>
							</div>
						</td>
						<td></td>
						<th scope="row">브랜드</th>
						<td class="input">
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName" value=""/>
<!-- 								<input type="hidden" id="brand-no" name="brandNo" value=""/> -->
								<a href="javascript:void(0);" class="btn-search" data-button-popup="find-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="2">상품코드 <br />(100개까지 가능합니다. 개행검색)</th>
						<td class="input clear-float" rowspan="2">
							<div class="prod-code-box">
								<select class="ui-sel" title="상품코드 선택" name="prdtCodeType">
									<option value="prdt-code-erp">업체상품코드</option>
									<option value="prdt-code-online">상품코드</option>
								</select>
								<textarea class="ui-textarea" title="상품코드 입력" id="prdt-keyword" name="prdtCodeKeyword"></textarea>
							</div>
						</td>
						<td></td>
						<th scope="row">상품관리자</th>
						<td class="input">
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchManagerType">
									<option value="aprver-id">승인자 ID</option>
									<option value="aprver-name">승인자명</option>
									<option value="rgster-id">등록자ID</option>
									<option value="rgster-name">등록자명</option>
									<option value="moder-id">수정자 ID</option>
									<option value="moder-name">수정자명</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchManagerKeyword"/>
							</div>
						</td>
					</tr>
					<tr>
						<td></td>
						<th scope="row">판매상태</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<input id="sell-stat-code-all" name="sellStatCodes" type="checkbox" value="" ${empty param.sellStatCode ? 'checked' : ''}/>
										<label for="sell-stat-code-all">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionSellStatCodeList }">
									<li>
										<span class="ui-chk">
											<input id="sell-stat-code-${item.codeDtlNo }" name="sellStatCodes" class="sellStatCodes" type="checkbox" value="${item.codeDtlNo }" ${empty param.sellStatCode or param.sellStatCode eq item.codeDtlNo ? 'checked' : ''}/>
											<label for="sell-stat-code-${item.codeDtlNo }">${item.codeDtlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">매장등급코드</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="tierFlagCodeAll" name="tierFlagCodeAll" type="checkbox" >
										<label for="tierFlagCodeAll">전체</label>
									</span>
								</li>
								<c:forEach items="${tierFlagCodeList}" var="tierFlagCode" varStatus="status">
									<li>
										<span class="ui-chk">
											<input id="tierFlagCode${tierFlagCode.codeDtlName}" value="${tierFlagCode.codeDtlName}"  custom="tierFlagCode" name="tierFlagCode" class="tierFlagCode" type="checkbox" >
											<label for="tierFlagCode${tierFlagCode.codeDtlName}">${tierFlagCode.addInfo1}</label>
										</span>
									</li>
								</c:forEach>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="tierFlagCodeEmpty" name="tierFlagCode" value="-" custom="tierFlagCode" class="tierFlagCode" type="checkbox" >
										<label for="tierFlagCodeEmpty">없음</label>
									</span>
								</li>
							</ul>
						</td>
						<td></td>
						<th scope="row">전시여부</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="disp-yn-all" name="dispYn" type="radio" value="" ${empty param.dispYn ? 'checked' : ''} />
										<label for="disp-yn-all">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="disp-yn-y" name="dispYn" type="radio" value="Y" ${param.dispYn eq 'Y' ? 'checked' : ''} />
										<label for="disp-yn-y">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="disp-yn-n" name="dispYn" type="radio" value="N"/>
										<label for="disp-yn-n">전시안함</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">표준카테고리</th>
						<td class="input"<%--  colspan="4" --%>>
							<span class="ip-text-box stdStgrArea">
								<select class="ui-sel stdCtgrSel 1depth" title="대카테고리 선택" name="stdCtgrNo1">
									<option value="">대카테고리 전체</option>
								</select>
								<select class="ui-sel stdCtgrSel 2depth" title="중카테고리 선택" name="stdCtgrNo2">
									<option value="">중카테고리 전체</option>
								</select>
								<select class="ui-sel stdCtgrSel 3depth" title="소카테고리 선택" name="stdCtgrNo3">
									<option value="">소카테고리 전체</option>
								</select>
							</span>
						</td>
						<td></td>
						<th scope="row">기간</th>
						<td class="input">
							<span class="term-date-wrap">
								<select class="ui-sel" title="기간 선택" name="periodType">
									<option value="mod_dtm">수정일</option>
									<option value="rgst_dtm">등록일</option>
									<option value="aprver-dtm">승인완료일</option>
									<option value="sell-start-dtm" ${not empty param.sellStatCode ? 'selected' : ''}>판매시작일</option>
									<option value="sell-end-dtm">판매종료일</option>
									<option value="rsv-dlvy-ymd">예상출고일</option>
								</select>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="periodStartDate" value="${not empty param.sellStatCode ? param.productSearchStartDtm : ''}"/>
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="periodEndDate" value="${not empty param.sellStatCode ? param.productSearchEndDtm : ''}"/>
								</span>
								<span class="btn-group">
									<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="today">오늘</a>
									<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="week">일주일</a>
									<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="month">한달</a>
									<a href="javascript:void(0);" class="btn-sm btn-func text-center" data-button-period="year">1년</a>
								</span>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">전시 채널 <br />/ 전시카테고리</th>
						<td class="input" colspan="${Const.AUTH_APPLY_SYSTEM_TYPE_BO eq userAuthority ? '2' : '4' }">
							<span class="ip-text-box dp-category-area">
								<select class="ui-sel dp-category-select chnnl-no" title="전시채널 선택">
									<option value="">전시채널 전체</option>
									<c:forEach var="item" items="${searchConditionSiteChannelList }">
										<option value="${item.chnnlNo }">${item.chnnlName }</option>
									</c:forEach>
								</select>
								<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택">
									<option value="">대카테고리 전체</option>
								</select>
								<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택">
									<option value="">중카테고리 전체</option>
								</select>
								<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택" name="ctgrNo">
									<option value="">소카테고리 전체</option>
								</select>
							</span>
						</td>
						<c:if test="${Const.AUTH_APPLY_SYSTEM_TYPE_BO eq userAuthority }">
							<th scope="row">온라인/매장전용</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="prdt-type-code-online-yn-all" name="prdtTypeCode" type="radio" value="" checked/>
											<label for="prdt-type-code-online-yn-all">전체</label>
										</span>
									</li>
									<c:forEach items="${searchConditionPrdtTypeCodeList }" var="item">
										<c:choose>
											<c:when test="${item.addInfo1 eq 'N' or item.codeDtlNo eq PRDT_TYPE_CODE_FREEGIFT }">
												<%-- 배송비나 사은품인 경우 출력하지 않음 --%>
											</c:when>
											<c:otherwise>
												<li>
													<span class="ui-rdo">
														<input id="prdt-type-code-online-yn-${item.codeDtlNo }" name="prdtTypeCode" type="radio" value="${item.codeDtlNo }"/>
														<label for="prdt-type-code-online-yn-${item.codeDtlNo }">${item.codeDtlName }</label>
													</span>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</ul>
							</td>
						</c:if>
					</tr>
				</tbody>
			</table>
			<div class="search-addition">
				<div class="addition-btn-wrap">
					<button type="button" class="btn-sm btn-func btn-addition-toggle">추가검색<i class="fa fa-caret-down"></i><span class="offscreen">펼침</span></button>
					<input type="hidden" id="use-advanced-search" name="useAdvancedSearch" value="N"/>
				</div>
				<table class="tbl-search">
					<caption>상품정보 추가검색</caption>
					<colgroup>
						<col style="width:15%;"/>
						<col/>
						<col style="width:79px;"/>
						<col style="width:15%;"/>
						<col/>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">테마/성별</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="gender-gbn-code" name="genderGbnCode"  type="radio" value="" checked/>
											<label for="gender-gbn-code">전체</label>
										</span>
									</li>
									<c:forEach var="item" items="${searchConditionGenderGbnCodeList }">
										<li>
											<span class="ui-rdo">
												<input id="gender-gbn-code-${item.codeDtlNo }" name="genderGbnCode"  type="radio" value="${item.codeDtlNo }"$/>
												<label for="gender-gbn-code-${item.codeDtlNo }">${item.codeDtlName }</label>
											</span>
										</li>
									</c:forEach>
								</ul>
							</td>
							<td></td>
							<th scope="row">예약상품 여부</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rsv-prdt-yn-all" name="rsvPrdtYn" type="radio" value="" checked/>
											<label for="rsv-prdt-yn-all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rsv-prdt-yn-n" name="rsvPrdtYn" type="radio" value="N"/>
											<label for="rsv-prdt-yn-n">일반</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rsv-prdt-yn-y" name="rsvPrdtYn" type="radio" value="Y"/>
											<label for="rsv-prdt-yn-y">예약</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">무료배송 상품여부</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="free-dlvy-yn-all" name="freeDlvyYn" type="radio" value="" checked>
											<label for="free-dlvy-yn-all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="free-dlvy-yn-y" name="freeDlvyYn" type="radio" value="Y"/>
											<label for="free-dlvy-yn-y">예</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="free-dlvy-yn-n" name="freeDlvyYn" type="radio" value="N"/>
											<label for="free-dlvy-yn-n">아니오</label>
										</span>
									</li>
								</ul>
							</td>
							<td></td>
							<th scope="row">임직원 할인제외여부</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="emp-dscnt-yn-all" name="empDscntYn" type="radio" value="" checked/>
											<label for="emp-dscnt-yn-all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="emp-dscnt-yn-n" name="empDscntYn" type="radio" value="N"/>
											<label for="emp-dscnt-yn-n">할인제외</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="emp-dscnt-yn-y" name="empDscntYn" type="radio" value="Y"/>
											<label for="emp-dscnt-yn-y">할인적용</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<c:if test="${Const.AUTH_APPLY_SYSTEM_TYPE_BO eq userAuthority }">
							<tr>
								<th scope="row">감가 제외 여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="dprc-except-yn-all" name="dprcExceptYn" type="radio" value="" checked/>
												<label for="dprc-except-yn-all">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="dprc-except-yn-y" name="dprcExceptYn" type="radio" value="Y"/>
												<label for="dprc-except-yn-y">감가제외</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="dprc-except-yn-n" name="dprcExceptYn" type="radio" value="N"/>
												<label for="dprc-except-yn-n">감가적용</label>
											</span>
										</li>
									</ul>
								</td>
								<td></td>
								<th scope="row">통합재고연동여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="stock-intgr-yn-all" name="stockIntgrYn" type="radio" value="" checked/>
												<label for="stock-intgr-yn-all">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="stock-intgr-yn-y" name="stockIntgrYn" type="radio" value="Y"/>
												<label for="stock-intgr-yn-y">연동</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="stock-intgr-yn-n" name="stockIntgrYn" type="radio" value="N"/>
												<label for="stock-intgr-yn-n">연동안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">매장등급</th>
								<td class="input">
									<select class="ui-sel" title="매장등급 선택" name="dispFlagText">
										<option value="">전체</option>
										<option value="st">ST</option>
										<option value="ms">MS</option>
										<option value="gsmg">GS+MG</option>
									</select>
								</td>
								<td></td>
								<th scope="row">매장픽업 가능여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="store-pickup-psblt-yn-all" name="storePickupPsbltYn" type="radio" value="" checked/>
												<label for="store-pickup-psblt-yn-all">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="store-pickup-psblt-yn-y" name="storePickupPsbltYn" type="radio" value="Y"/>
												<label for="store-pickup-psblt-yn-y">가능</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="store-pickup-psblt-yn-n" name="storePickupPsbltYn" type="radio" value="N"/>
												<label for="store-pickup-psblt-yn-n">불가능</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">제휴사 전송 여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="afflts-send-yn-all" name="affltsSendYn" type="radio" value="" checked/>
												<label for="afflts-send-yn-all">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="afflts-send-yn-y" name="affltsSendYn" type="radio" value="Y"/>
												<label for="afflts-send-yn-y">전송</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="afflts-send-yn-n" name="affltsSendYn" type="radio" value="N"/>
												<label for="afflts-send-yn-n">전송안함</label>
											</span>
										</li>
									</ul>
								</td>
								<td></td>
								<th scope="row">사방넷 연동 여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="sabangnet-send-yn-all" name="sabangnetSendYn" type="radio" value="" checked/>
												<label for="sabangnet-send-yn-all">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="sabangnet-send-yn-y" name="sabangnetSendYn" type="radio" value="Y"/>
												<label for="sabangnet-send-yn-y">전송</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="sabangnet-send-yn-n" name="sabangnetSendYn" type="radio" value="N"/>
												<label for="sabangnet-send-yn-n">전송안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
						</c:if>
						<tr>
							<th scope="row">판매가</th>
							<td class="input" colspan="4">
								<span class="ip-text-box">
									<input type="text" class="ui-input num-unit100000000" title="시작가 입력" name="sellPriceStartWith" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" style="IME-MODE:disabled;"/>
									<span class="text">원</span>
									<span class="text">~</span>
									<input type="text" class="ui-input num-unit100000000" title="종료가 입력" name="sellPriceEndWith" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" style="IME-MODE:disabled;"/>
									<span class="text">원</span>
								</span>
							</td>
						</tr>
						<c:if test="${Const.AUTH_APPLY_SYSTEM_TYPE_BO eq userAuthority }">
							<tr>
								<th scope="row">재입고 상품조회</th>
								<td class="input" colspan="4">
									<span class="ip-text-box">
										<input type="text" class="ui-input num-unit100" title="조회 일자 입력" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" style="IME-MODE:disabled;" name="restockWithinDays" maxlength="3"/>
										<span class="text">일 이내 재입고된 상품 중</span>
										<input type="text" class="ui-input num-unit100" title="족 수 입력" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" style="IME-MODE:disabled;" name="restockMoreThanCount" maxlength="3"/>
										<span class="text">족 이상 재고보유 상품 조회</span>
									</span>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<!-- E : 20190215 추가 // 추가검색영역 추가 -->

			<div class="confirm-box">
				<div class="fl">
					<a href="javascript:void(0);" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<!-- <a href="javascript:void(0);" class="btn-normal btn-func" id="search">검색</a> -->
					<a href="javascript:void(0);" class="btn-normal btn-func" id="searchNew">검색</a>
				</div>
			</div>
		</div>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</div>
</form>
<!-- E : search-wrap -->
