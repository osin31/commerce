<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<form id="search-form">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>기프트카드 검색</caption>
				<colgroup>
					<col style="width:110px;">
					<col>
					<col style="width:79px;">
					<col style="width:110px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">카드 유형</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="gift-card-type-code-all" name="giftCardTypeCode" value="" type="radio" checked>
										<label for="gift-card-type-code-all">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input class="gift-card-type-code" id="chkCardType02" name="giftCardTypeCode" value="10000" type="radio">
										<label for="chkCardType02">기프트카드(PVC)</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input class="gift-card-type-code" id="chkCardType03" name="giftCardTypeCode" value="10001" type="radio">
										<label for="chkCardType03">e-기프트카드(MMS)</label>
									</span>
								</li>
							</ul>
						</td>
						<td></td>
						<th scope="row">유효기간</th>
						<td class="input">
							<select class="ui-sel" title="유효기간 선택" name="validTermType">
								<option value="">전체</option>
								<option value="1">3개월</option>
								<option value="2">1년</option>
								<option value="3">3년</option>
								<option value="4">5년</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">카드 구분</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="gift-card-gbn-type-0" name="giftCardGbnType" value="" type="radio" checked>
										<label for="gift-card-gbn-type-0">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="gift-card-gbn-type-1" name="giftCardGbnType" type="radio" value="1">
										<label for="gift-card-gbn-type-1">충전형</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="gift-card-gbn-type-2" name="giftCardGbnType" type="radio" value="0">
										<label for="gift-card-gbn-type-2">권종형(비충전형)</label>
									</span>
								</li>
							</ul>
						</td>
						<td></td>
						<th scope="row">사용여부</th>
						<td class="input">
							<select class="ui-sel" title="사용여부 선택" name="useYn">
								<option value="">전체</option>
								<option value="Y" selected>사용</option>
								<option value="N">사용안함</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">기프트카드명</th>
						<td class="input">
							<input type="text" class="ui-input" title="기프트카드명 입력" name="giftCardName">
						</td>
					</tr>
					<tr>
						<th scope="row">기간 검색</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" name="periodType" title="기간 선택">
									<option value="rgst-dtm" selected>등록일</option>
									<option value="mod-dtm">수정일</option>
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
	</form>
</div>
<!-- E : search-wrap -->
