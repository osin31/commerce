<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<div class="search-inner">
		<form id="search-form">
			<table class="tbl-search">
				<caption>승인대상 상품 검색</caption>
				<colgroup>
					<col style="width:130px;">
					<col>
					<col style="width:79px;">
					<col style="width:130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">검색어</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchKeywordType">
									<option value="prdt-name" selected>상품명</option>
									<option value="moder-name">수정자명</option>
									<option value="moder-id">수정자 ID</option>
									<option value="aprver-name">승인처리자명</option>
									<option value="aprver-id">승인처리자 ID</option>
									<!--  
									<option value="style-info">스타일</option>
									<option value="rgster-name">등록자명</option>
									<option value="rgster-id">등록자 아이디</option>
									-->
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
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
											<input id="chnnl-no-${item.chnnlNo }" name="chnnlNo" type="radio"  value="${item.chnnlNo}" ${param.chnnlNo eq item.chnnlNo ? 'checked' : ''}>
											<label for="chnnl-no-${item.chnnlNo }">${item.chnnlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="2">상품코드</th>
						<td class="input clear-float" rowspan="2">
							<!-- S : prod-code-box -->
							<div class="prod-code-box">
								<select class="ui-sel" title="상품코드 선택" name="prdtCodeType">
									<option value="prdt-code-online" selected>상품코드</option>
									<!-- <option value="prdt-code-erp">업체 상품코드</option> -->
								</select>
	
								<!-- DESC : 20190228 수정 // 상품코드 입력 textarea 변경 -->
								<textarea class="ui-textarea size-sm" title="상품코드 입력" id="prdt-keyword" name="prdtCodeKeyword"></textarea>
							</div>
							<!-- E : prod-code-box -->
						</td>
						<td></td>
						<th scope="row">브랜드</th>
						<td class="input">
							<!-- S : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName">
								<!-- <input type="hidden" name="brandName" id="brand-no"> -->
								<a href="#" class="btn-search" id="search-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
							<!-- E : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
						</td>
					</tr>
					<tr>
						<td></td>
						<th scope="row">입점사명</th>
						<td class="input">
							<span class="ip-search-box">
								<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" placeholder="입점사명" name="vndrName"/>
								<a href="javascript:void(0);" id="search-vndr" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
								<!-- <input type="hidden" id="vndr-no" name="vndrNo" /> -->
							</span>
						</td>
						<!-- 
						<th scope="row">승인 처리자</th>
						<td class="input">
							<select class="ui-sel" title="승인처리자 선택" name="aprverNo">
								<option value="">관리자명/ID</option>
								<c:forEach var="item" items="${searchAprvAdminList }">
									<option value="${adminNo }">${item.adminName }/${item.loginId }</option>	
								</c:forEach>
							</select>
						</td>
						 -->
					</tr>
					<tr>
						<th scope="row">승인구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioAdmissionType01" name="prdtAprvReqCode" type="radio" value="" checked>
										<label for="radioAdmissionType01">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionPrdtAprvReqCodeList }">
									<li>
										<span class="ui-rdo">
											<input id="radioAdmissionType-${item.codeDtlNo }" name="prdtAprvReqCode" type="radio" value="${item.codeDtlNo}"/>
											<label for="radioAdmissionType-${item.codeDtlNo }">${item.codeDtlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">승인상태</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<input id="aprv-stat-code-all" type="checkbox" ${empty param.aprvStatCode ? 'checked' : ''}/>
										<label for="aprv-stat-code-all">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionAprvStatCodeList }">
									<c:if test="${item.codeDtlName ne '승인대기' }">
										<li>
											<!-- S : 20190215 수정 // label값 수정 -->
											<span class="ui-chk">
												<input id="aprv-stat-code-${item.codeDtlNo }" class="aprv-stat-code-all" name="aprvStatCodeArr" type="checkbox" value="${item.codeDtlNo}" ${(param.aprvStatCode eq item.codeDtlNo) or empty param.aprvStatCode ? 'checked' : ''} />
												<label for="aprv-stat-code-${item.codeDtlNo }">${item.codeDtlName }</label>
											</span>
											<!-- E : 20190215 수정 // label값 수정 -->
										</li>
									</c:if>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!-- DESC : 20190314 삭제 // 표준카테고리, 전시카테고리 삭제 -->
					<tr>
						<th scope="row">기간</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" title="기간 선택" name="periodType">
									<option value="aprver-request-dtm" selected>승인요청일</option>
									<option value="rgst-dtm">상품등록일</option>
									<option value="aprver-return-dtm">반려처리일</option>
									<option value="aprver-complete-dtm">승인완료일</option>
								</select>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="periodStartDate">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="periodEndDate">
								</span>
								<span class="btn-group area-calendar-btn-group">
									<a href="javascript:void(0);" class="btn-sm btn-func calendar-today">오늘</a>
									<a href="javascript:void(0);" class="btn-sm btn-func calendar-week">일주일</a>
									<a href="javascript:void(0);" class="btn-sm btn-func calendar-month">한달</a>
									<a href="javascript:void(0);" class="btn-sm btn-func calendar-year">1년</a>
								</span>
							</span>
							<!-- E : term-date-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div class="confirm-box">
			<div class="fl">
				<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
			</div>
			<div class="fr">
				<a href="#" class="btn-normal btn-func" id="search">검색</a>
			</div>
		</div>
	</div>
	<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
</div>
<!-- E : search-wrap -->
