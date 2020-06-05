<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<form id="search-form">
	<div class="search-wrap">
		<div class="search-inner">
			<input type="hidden" name="vndrGbnType" value="${Const.VNDR_GBN_TYPE_C }">
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
						<th scope="row">상품명</th>
						<td class="input">
							<input type="hidden" name="searchKeywordType" value="prdt-name">
							<input type="text" class="ui-input" placeholder="상품명 입력" title="상품명 입력" name="searchKeyword">
						</td>
						<td></td>
						<th scope="row">브랜드</th>
						<td class="input">
							<!-- S : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName">
								<!-- <input type="hidden" id="brand-no" name="brandNo"/> -->
								<a href="#" class="btn-search" id="search-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
							<!-- E : 20190314 수정 // 버튼 > 단일검색으로 수정 -->
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="3">상품코드<br/>(20개까지 가능합니다. 개행검색)</th>
						<td class="input clear-float" rowspan="3">
							<!-- S : prod-code-box -->
							<div class="prod-code-box">
								<input type="hidden" name="prdtCodeType" value="prdt-code-erp">
								<textarea class="ui-textarea" title="상품코드 입력" name="prdtCodeKeyword"></textarea>
							</div>
							<!-- E : prod-code-box -->
						</td>
						<td></td>
						<th scope="row">채널 구분</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="chnnl-no-1" name="maejangCd" type="radio" value="" checked>
										<label for="chnnl-no-1">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${searchConditionSiteChannelList }">
									<li>
										<span class="ui-rdo">
											<input id="chnnl-no-${item.chnnlNo }" name="maejangCd" type="radio"  value="${item.chnnlNo }">
											<label for="chnnl-no-${item.chnnlNo }">${item.chnnlName }</label>
										</span>
									</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<td></td>
						<%-- <th scope="row">매장등급코드</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="tierFlagCodeAll" name="tierFlagCodeAll" type="checkbox" checked>
										<label for="tierFlagCodeAll">전체</label>
									</span>
								</li>
								<c:forEach items="${tierFlagCodeList}" var="tierFlagCode" varStatus="status">
									<li>
										<span class="ui-chk">
											<input id="tierFlagCode${tierFlagCode.codeDtlName}" value="${tierFlagCode.codeDtlName}"  custom="tierFlagCode" name="tierFlagCode" class="tierFlagCode" type="checkbox" checked="checked">
											<label for="tierFlagCode${tierFlagCode.codeDtlName}">${tierFlagCode.addInfo1}</label>
										</span>
									</li>
								</c:forEach>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="tierFlagCodeEmpty" name="tierFlagCode" value="-" custom="tierFlagCode" class="tierFlagCode" type="checkbox" checked>
										<label for="tierFlagCodeEmpty">없음</label>
									</span>
								</li>
							</ul>
						</td> --%>
					</tr>
					<tr>
						<td></td>
					</tr>
					<!-- DESC : 20190311 삭제 // 표준카테고리, 전시카테고리 삭제 -->
					<tr>
						<th scope="row">ARS 등록일시</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
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
