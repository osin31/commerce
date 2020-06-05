<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<form id="search-form">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품 Q&amp;A 검색</caption>
				<colgroup>
					<col style="width:110px;">
					<col>
					<col style="width:79px;">
					<col style="width:110px;">
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
									<option value="style-info">스타일</option>
									<option value="title-cont">제목+내용</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
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
						<th scope="row" rowspan="2">상품코드</th>
						<td class="input clear-float" rowspan="2">
							<!-- S : prod-code-box -->
							<div class="prod-code-box">
								<!-- S : 20190314 수정 // option 수정 -->
								<select class="ui-sel" title="상품코드 선택" name="prdtCodeType">
									<!-- S : 20190314 수정 // option 수정 -->
									<option value="prdt-code-erp" selected>업체상품코드</option>
									<option value="prdt-code-online">상품코드</option>
									<!-- E : 20190314 수정 // option 수정 -->
								</select>
								<!-- E : 20190314 수정 // option 수정 -->
	
								<!-- DESC : 20190228 수정 // 상품코드 입력 textarea 변경 -->
								<textarea class="ui-textarea size-sm" title="상품코드 입력" name="prdtCodeKeyword"></textarea>
							</div>
							<!-- E : prod-code-box -->
						</td>
						<td></td>
						<th scope="row">브랜드</th>
						<td class="input">
							<!-- S : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" name="brandName" id="brand-name" value=""/>
								<!-- <input type="hidden" id="brand-no" name="brandNo" /> -->
								<a href="#" class="btn-search" id="search-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
							<!-- E : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
						</td>
					</tr>
					<!-- S : PO관리자 조회화면인 경우, 미노출 영역  -->
					<tr>
						<td></td>
						<th class="vndr-tr" scope="row">자사 / 입점</th>
						<td class="input vndr-tr">
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
									<span class="ip-search-box" >
										<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" name="vndrName" placeholder="입점사명" value="" readonly/>
										<!-- <input type="hidden" id="vndr-no" name="vndrNo" /> -->
										<a href="javascript:void(0);" id="search-vndr" class="btn-search disabled"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!-- E : PO관리자 조회화면인 경우, 미노출 영역  -->
					<tr>
						<th scope="row">답변상태</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="aswr-stat-code-all" name="aswrStatCode" type="radio" value="" ${empty param.aswrStatCode ? 'checked' : ''}>
										<label for="aswr-stat-code-all">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${aswrStatCode}">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="aswr-stat-code-${item.codeDtlNo }" name="aswrStatCode" type="radio" value="${item.codeDtlNo }" ${param.aswrStatCode eq item.codeDtlNo ? 'checked' : ''}>
											<label for="aswr-stat-code-${item.codeDtlNo }">${item.codeDtlName}</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">문의유형</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select id="cnsl-type-code" name="cnslTypeCode" class="ui-sel" title="문의유형 선택">
									<option value="">문의유형</option>
									<c:forEach var="item" items="${cnslTypeCode}">
										<option value="${item.codeDtlNo}">${item.codeDtlName}</option>
									</c:forEach>
								</select>
								<select id="cnsl-type-dtl-code" name="cnslTypeDtlCode" class="ui-sel" title="문의구분 선택">
									<option value="">문의구분</option>
									<c:forEach var="item" items="${cnslTypeDtlCode}">
										<option value="${item.codeDtlNo}">${item.codeDtlName}</option>
									</c:forEach>
								</select>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">회원정보</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchMemberType">
									<option value="member-name" selected>회원명</option>
									<option value="login-id">회원 ID</option>
									<option value="member-email">회원이메일</option>
									<!-- DESC : 20190314 삭제 // 회원연락처, 회원이메일 option 삭제 -->
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchMember">
								<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
						<th scope="row">답변자 정보</th>
						<td class="input">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchAdminType">
									<option value="admin-name" selected>답변자 이름</option>
									<option value="login-id">답변자ID</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchAdmin">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">기간선택</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" name="periodType" title="기간 선택">
									<option value="inqry-dtm" selected>작성일</option>
									<option value="aswr-dtm">답변일</option>
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
					<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="#" class="btn-normal btn-func" id="search">검색</a>
				</div>
			</div>
		</div>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</form>
</div>
<!-- E : search-wrap -->
