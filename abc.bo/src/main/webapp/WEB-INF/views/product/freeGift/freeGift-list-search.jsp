<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<div class="search-inner">
		<form id="search-form" name="searchForm">
			<table class="tbl-search">
				<caption>사은품 검색</caption>
				<colgroup>
					<col style="width:15%;">
					<col>
					<col style="width:79px;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">사은품명</th>
						<td class="input">
							<input type="text" class="ui-input" title="사은품명 입력" name="prdtName" maxlength="100">
							<input type="hidden" name="prdtTypeCode" value="${CommonCode.PRDT_TYPE_CODE_GIFT }">
						</td>
						<td></td>
						<th scope="row">사은품 ID</th>
						<td class="input">
							<input type="text" class="ui-input" title="사은품 ID 입력" name="prdtNo" id="prdt-no" maxlength="10">
						</td>
					</tr>
					<tr>
						<th scope="row">사용여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse01" name="useYn" type="radio" value="" checked>
										<label for="radioUse01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse02" name="useYn" type="radio" value="Y">
										<label for="radioUse02">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse03" name="useYn" type="radio" value="N">
										<label for="radioUse03">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">전시여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="disp-yn-all" name="dispYn" type="radio" value="" checked>
										<label for="disp-yn-all">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="disp-yn-y" name="dispYn" type="radio" value="Y">
										<label for="disp-yn-y">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="disp-yn-n" name="dispYn" type="radio" value="N">
										<label for="disp-yn-n">미전시</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
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

