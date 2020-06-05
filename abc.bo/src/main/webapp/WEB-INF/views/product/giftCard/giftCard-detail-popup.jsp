<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>기프트카드 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : tbl-row -->
			<form id="detail-form">
				<input type="hidden" name="giftCardNo" value="${giftCard.giftCardNo }">
				<table class="tbl-row">
					<caption>기프트카드 상세</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">카드 유형</span></th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkCardType02" name="giftCardTypeCodeArr" type="radio" value="10000" ${giftCard.giftCardTypeCode eq '10000' or giftCard.giftCardTypeCode eq '10002' or empty giftCard.giftCardTypeCode ? 'checked' : '' }>
											<label for="chkCardType02">기프트카드(PVC)</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkCardType03" name="giftCardTypeCodeArr" type="radio" value="10001" ${giftCard.giftCardTypeCode eq '10001' or giftCard.giftCardTypeCode eq '10002' or empty giftCard.giftCardTypeCode ? 'checked' : '' }>
											<label for="chkCardType03">e-기프트카드(MMS)</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">카드구분</span>
							</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="gift-card-gbn-type-1" name="giftCardGbnType" value="1" type="radio" ${giftCard.giftCardGbnType eq '1' or empty giftCard.giftCardGbnType ? 'checked' : '' }>
											<label for="gift-card-gbn-type-1">충전형</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="gift-card-gbn-type-2" name="giftCardGbnType" value="0" type="radio" ${giftCard.giftCardGbnType eq '0' ? 'checked' : '' }>
											<label for="gift-card-gbn-type-2">권종형(비충전형)</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr id="sell-amt-tr">
							<th scope="row">
								<span class="th-required">판매가</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="기프트카드명 입력" name="sellAmt" value="${giftCard.sellAmt }">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">유효기간</span>
							</th>
							<td class="input">
								<select class="ui-sel" title="유효기간 선택" name="validTermType">
									<option value="1" ${giftCard.validTermType eq '1' ? ' selected' : '' }>3개월</option>
									<option value="2" ${giftCard.validTermType eq '2' ? ' selected' : '' }>1년</option>
									<option value="3" ${giftCard.validTermType eq '3' ? ' selected' : '' }>3년</option>
									<option value="4" ${giftCard.validTermType eq '4' or empty giftCard.validTermType ? ' selected' : '' }>5년</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="use-yn-y" name="useYn" value="Y" type="radio" ${giftCard.useYn eq 'Y' or empty giftCard.useYn ? 'checked' : '' }>
											<label for="use-yn-y">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="use-yn-n" name="useYn" value="N" type="radio" ${giftCard.useYn eq 'N' ? 'checked' : '' }>
											<label for="use-yn-n">미사용</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">기프트카드명</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="기프트카드명 입력" name="giftCardName" value="${giftCard.giftCardName }">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">이미지코드</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="이미지코드 입력" name="mgmtNoText" value="${giftCard.mgmtNoText }" maxlength="10">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">기프트카드 이미지</span>
								<div>320*202 (최대 10MB까지 등록가능 <br />파일유형 : jpg, png, gif)</div>
							</th>
							<td class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" name="imageName" id="image-name">
												<input type="file" id="image" title="첨부파일 추가" name="imageFile">
												<label for="image">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${giftCard.imageName }</a>
											<c:if test="${userDetails.adminNo eq giftCard.rgsterNo }">
												<button type="button" class="btn-file-del">
													<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</c:if>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="altrnText" value="${giftCard.altrnText }">
									</div>
									<div class="img-wrap"><img alt="${giftCard.altrnText}" src="${giftCard.imageUrl}"></div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" onclick="javascript:self.close();" class="btn-normal btn-del">취소</a>
				<a href="#" id="save" class="btn-normal btn-link">${empty giftCard.giftCardNo ? '등록' : '수정'}</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.giftCard.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>