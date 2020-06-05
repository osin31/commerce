<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<form id="search-form">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품후기 검색</caption>
				<colgroup>
					<col style="width:10%;">
					<col>
					<col style="width:79px;">
					<col style="width:10%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">후기구분</th>
						<td class="input" colspan="4">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioReview01" name="rvwType" type="radio" value="" checked>
										<label for="radioReview01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioReview02" name="rvwType" type="radio" value="P">
										<label for="radioReview02">포토</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioReview03" name="rvwType" type="radio" value="G">
										<label for="radioReview03">일반</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">회원 검색</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchMemberType">
									<option value="order-no">주문번호</option>
									<option value="login-id">회원ID</option>
									<option value="member-name">회원명</option>
									<option value="hdphn-no-text">회원연락처</option>
									<option value="email-addr-text">회원이메일</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchMember">
								<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4" style="display:none;">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
						<th scope="row">구입처</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBuy01" name="onlnBuyYn" type="radio" value="" checked>
										<label for="radioBuy01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBuy02" name="onlnBuyYn" type="radio" value="Y">
										<label for="radioBuy02">온라인</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBuy03" name="onlnBuyYn" type="radio" value="N">
										<label for="radioBuy03">오프라인</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">검색어</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchKeywordType">
									<option value="prdt-name" selected>상품명</option>
									<option value="style-info">스타일</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
						<th scope="row">브랜드</th>
						<td class="input">
							<!-- S : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName" value=""/>
								<!-- <input type="hidden" id="brand-no" name="brandNo" /> -->
								<a href="#" class="btn-search" id="search-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
							<!-- E : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="2">상품코드</th>
						<td class="input clear-float" rowspan="2">
							<!-- S : prod-code-box -->
							<div class="prod-code-box">
								<select class="ui-sel" title="상품코드 선택" name="prdtCodeType">
									<!-- S : 20190314 수정 // option 수정 -->
									<option value="prdt-code-erp" selected>업체상품코드</option>
									<option value="prdt-code-online">온라인 상품코드</option>
									<!-- E : 20190314 수정 // option 수정 -->
								</select>

								<!-- DESC : 20190228 수정 // 상품코드 입력 textarea 변경 -->
								<textarea class="ui-textarea size-sm" title="상품코드 입력" name="prdtCodeKeyword"></textarea>
							</div>
							<!-- E : prod-code-box -->
						</td>
						<td></td>
						<th scope="row">자사 / 입점</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioStore01" name="mmnyPrdtYn" type="radio" value="" ${empty param.mmnyPrdtYn ? 'checked' : ''}>
										<label for="radioStore01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioStore02" name="mmnyPrdtYn" type="radio" value="Y" ${param.mmnyPrdtYn eq 'Y' ? 'checked' : ''}>
										<label for="radioStore02">자사</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioStore03" name="mmnyPrdtYn" type="radio" value="N" ${param.mmnyPrdtYn eq 'N' ? 'checked' : ''}>
										<label for="radioStore03">입점</label>
									</span>
									<span class="ip-search-box">
										<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" placeholder="입점사명" name="vndrName" value="" readonly/>
										<!-- <input type="hidden" id="vndr-no" name="vndrNo" /> -->
										<a href="javascript:void(0);" id="search-vndr" class="btn-search disabled"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<td></td>
						<th scope="row">전시여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay01" name="dispYn" type="radio" value="" checked>
										<label for="radioDisplay01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay02" name="dispYn" type="radio" value="Y">
										<label for="radioDisplay02">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay03" name="dispYn" type="radio" value="N">
										<label for="radioDisplay03">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<%--
						<th scope="row">채널 구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chnnl-no-1" name="chnnlNo" type="radio" value="" checked>
										<label for="chnnl-no-1">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionSiteChannelList }">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chnnl-no-${item.chnnlNo }" name="chnnlNo" type="radio"  value="${item.chnnlNo }">
											<label for="chnnl-no-${item.chnnlNo }">${item.chnnlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						--%>
						<th scope="row">사이트 구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-no-1" name="siteNo" type="radio" value="" checked>
										<label for="site-no-1">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionSiteList }">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="site-no-${item.siteNo }" name="siteNo" type="radio"  value="${item.siteNo }">
											<label for="site-no-${item.siteNo }">${item.siteName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">회원구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioGrade01" name="memberTypeCode" type="radio" checked value="">
										<label for="radioGrade01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioGrade02" name="memberTypeCode" type="radio" value="O">
										<label for="radioGrade02">온라인</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioGrade03" name="memberTypeCode" type="radio" value="M">
										<label for="radioGrade03">멤버십</label>
									</span>
								</li>
								<!--
								<li>
									<span class="ui-rdo">
										<input id="radioGrade04" name="memberTypeCode" type="radio" value="V">
										<label for="radioGrade04">VIP</label>
									</span>
								</li>
								-->
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">답변자 검색</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchAdminType">
									<option value="">전체</option>
									<option value="aswr-admin-name">답변자 이름</option>
									<option value="aswr-login-id">답변자ID</option>
									<option value="moder-admin-name">수정자 이름</option>
									<option value="moder-login-id">수정자ID</option>
									<!--
									<option value="login-id">관리자ID</option>
									<option value="admin-name">관리자 이름</option>
									-->
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchAdmin">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
						<th scope="row">확인여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioConfirm01" name="cnfrmYn" type="radio" value="" checked>
										<label for="radioConfirm01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioConfirm02" name="cnfrmYn" type="radio" value="Y">
										<label for="radioConfirm02">확인완료</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioConfirm03" name="cnfrmYn" type="radio" value="N">
										<label for="radioConfirm03">미확인</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">답변여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioSend01" name="aswrStatCode" type="radio" value="" ${empty param.aswrStatCode ? 'checked' : ''}>
										<label for="radioSend01">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchAswrStatCodeList }">
									<li>
										<span class="ui-rdo">
											<input id="aswr-stat-code-${item.codeDtlNo }" name="aswrStatCode" type="radio" value="${item.codeDtlNo }" ${param.aswrStatCode eq item.codeDtlNo ? 'checked' : ''}>
											<label for="aswr-stat-code-${item.codeDtlNo }">${item.codeDtlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">포인트지급여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioPoint01" name="pointPayYn" type="radio" value="" checked>
										<label for="radioPoint01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioPoint02" name="pointPayYn" type="radio" value="Y">
										<label for="radioPoint02">지급</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioPoint03" name="pointPayYn" type="radio" value="N">
										<label for="radioPoint03">미지급</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioPoint04" name="pointPayYn" type="radio" value="C">
										<label for="radioPoint04">지급취소</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioPoint05" name="pointPayYn" type="radio" value="I">
										<label for="radioPoint05">지급불가</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">표준카테고리</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select class="ui-sel stdCtgrSel 1depth" title="대카테고리 선택" name="stdCtgrNoDepth1"><option value="">대카테고리 선택</option></select>
								<select class="ui-sel stdCtgrSel 2depth" title="중카테고리 선택" name="stdCtgrNoDepth2"><option value="">중카테고리 선택</option></select>
								<select class="ui-sel stdCtgrSel 3depth" title="소카테고리 선택" name="stdCtgrNoDepth3"><option value="">소카테고리 선택</option></select>
							</span>
							<!-- E : ip-text-box -->
						</td>
						<td></td>
						<th scope="row">결제금액</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="search-price-yn-all" name="searchPriceYn" type="radio" checked value="">
										<label for="search-price-yn-all">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="search-price-yn-y" name="searchPriceYn" type="radio" value="Y">
										<label for="search-price-yn-y">2만원 이상</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="search-price-yn-n" name="searchPriceYn" type="radio" value="N">
										<label for="search-price-yn-n">2만원 미만</label>
									</span>
								</li>
							</li>
						</td>
					</tr>
					<!-- DESC : 20190314 삭제 // 전시카테고리 삭제 -->
					<tr>
						<th scope="row">작성일 기준</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioWrite01" name="writeDtm" type="radio" checked value="">
										<label for="radioWrite01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioWrite02" name="writeDtm" type="radio" value="within-30-days">
										<label for="radioWrite02">구매확정일 30일 이내</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioWrite03" name="writeDtm" type="radio" value="after-30-days">
										<label for="radioWrite03">구매확정일 30일 이후</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">베스트 여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBest01" name="bestYn" type="radio" value="" checked>
										<label for="radioBest01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBest02" name="bestYn" type="radio" value="Y">
										<label for="radioBest02">예</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioBest03" name="bestYn" type="radio" value="N">
										<label for="radioBest03">아니오</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">기간</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" name="periodType" title="기간 선택">
									<!-- <option value="" selected>선택</option> -->
									<option value="write-dtm">작성일자</option>
									<option value="cnfrm-dtm">확인일자</option>
									<option value="aswr-dtm">답변일자</option>
									<option value="point-pay-dtm">포인트지급일자</option>
									<option value="point-pay-cncl-dtm">포인트환수일자</option>
								</select>
								<span class="date-box">
									<input type="text" name="periodStartDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" name="periodEndDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
								</span>
								<span class="btn-group">
									<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
									<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
									<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
									<a href="javascript:void(0);"   name="perYear"   class="btn-sm btn-func text-center">1년</a>
								</span>
							</span>
							<!-- E : term-date-wrap -->
						</td>
					</tr>
				</tbody>
			</table>

			<div class="confirm-box">
				<div class="fl">
					<a href="javascript:void(0);" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-func" id="search">검색</a>
				</div>
			</div>
		</div>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</form>
</div>
<!-- E : search-wrap -->
