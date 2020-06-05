<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<form id="search-form">
	<div class="search-wrap">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품전체공지 검색</caption>
				<colgroup>
					<col style="width:15%;">
					<col>
					<col style="width:79px;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<c:if test="${authApplySystemType eq 'B'}">
						<tr>
							<th scope="row">자사 / 입점 구분</th>
							<td class="input" colspan="4">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse01" name="vndrGbnType" type="radio" value="" ${authApplySystemType eq 'B' ? 'checked' : 'disabled'}>
											<label for="radioUse01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse02" name="vndrGbnType" type="radio" value="${Const.VNDR_GBN_TYPE_C }" ${authApplySystemType eq 'P' ? 'disabled' : ''}>
											<label for="radioUse02">자사</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse03" name="vndrGbnType" type="radio" value="${Const.VNDR_GBN_TYPE_V }" ${authApplySystemType eq 'P' ? 'checked' : ''}>
											<label for="radioUse03">입점</label>
										</span>
										<span class="ip-search-box" style="display: ${authApplySystemType eq 'P' ? 'block;' : 'none;'}">
											<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" placeholder="입점사명" name="vndrName">
											<a href="javascript:void(0);" id="search-vndr" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
					</c:if>
					<tr>
						<th scope="row">검색어</th>
						<td class="input" colspan="4">
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchKeywordType">
									<option value="all-notc-title-text" selected>제목</option>
									<option value="rgster-name">등록자</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
						<td></td>
						<th scope="row">전시여부</th>
						<td class="input" colspan="4">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse04" name="dispYn" type="radio" value="" checked>
										<label for="radioUse04">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse05" name="dispYn" type="radio" value="Y">
										<label for="radioUse05">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse06" name="dispYn" type="radio" value="N">
										<label for="radioUse06">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">기간 검색</th>
						<td class="input" colspan="6">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" name="periodType" title="기간 선택">
									<option value="rgst-dtm" selected>등록일</option>
									<option value="all-notc-ymd">전시일</option>
								</select>
								<span class="date-box">
									<input type="text" name="periodStartDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" name="periodEndDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
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
</form>
<!-- E : search-wrap -->
