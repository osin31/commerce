<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="tab-content">
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">상품정보제공고시</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>상품정보제공고시</caption>
		<colgroup>
			<col style="width: 130px;"/>
			<col/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">품목명 선택</th>
				<td class="input">
					<select class="ui-sel" title="품목명 선택" id="item-code" name="itemCode" data-default-value="${product.itemCode }">
						<option value="">선택</option>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : 20190322 수정 // IBSheet > table 수정 -->
	<!-- S : tbl-col -->
	<table class="tbl-col">
		<caption>상품정보제공고시</caption>
		<colgroup>
			<col style="width: 20%"/>
			<col style="width: 15%"/>
			<col style="width: 10%"/>
			<col/>
			<col style="width: 10%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="display-separate-all" type="checkbox" data-checkbox="check-all" data-target="display-separate"/>
						<label for="display-separate-all">전체 상품정보 별도 표시</label>
					</span>
				</th>
				<th scope="col">항목명</th>
				<th scope="col" colspan="2">상세내용</th>
				<th scope="col">필수여부</th>
			</tr>
		</thead>
		<tbody id="add-info-area">
			<%-- 상품정보 --%>
			<c:if test="${not empty product and not empty product.productAddInfo }">
				<c:forEach items="${product.productAddInfo }" var="item" varStatus="status">
					<c:choose>
						<c:when test="${item.addInfoType eq 'I' and product.chnnlNo ne '10003'}">
							<c:if test="${item.prdtInfoNotcCode ne '10005'}">
								<tr class="info-notice-row">
									<input type="hidden" name="productAddInfo.divider" value=""/>
									<input type="hidden" name="productAddInfo.prdtAddInfoSeq" value="${item.prdtAddInfoSeq }" /><%-- 상품추가정보순번 --%>
									<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value="${item.prdtAddInfoRltnSeq }"/>
									<input type="hidden" name="productAddInfo.safeCrtfcNoText" value=""/>
									<input type="hidden" name="productAddInfo.prdtSafeTypeCode" value=""/>
									<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
									<input type="hidden" name="productAddInfo.addInfoType" value="I" /><%-- 추가정보구분 --%>
									<input type="hidden" name="productAddInfo.dispYn" value="${item.dispYn }"/><%-- 전시여부 --%>
									<input type="hidden" name="productAddInfo.prdtMaterlText" value=""/>
									<input type="hidden" name="productAddInfo.sortSeq" value="${item.sortSeq }" />
									<input type="hidden" name="productAddInfo.reqYn" value="${item.reqYn }" />
									<td class="input text-center clear-float">
										<span class="ui-chk">
											<input id="prdt-info-notc-seq-${status.index }" data-checkbox="product-add-info-display-separate" type="checkbox" data-target="display-separate" value=""<c:if test="${item.dispYn eq 'Y' }"> checked</c:if>/>
											<label for="prdt-info-notc-seq-${status.index }">상품정보 별도 표시</label>
										</span>
									</td>
									<td class="text-center" name="productAddInfo.infoNotcName">${item.infoNotcName }</td>
									<td class="input" colspan="2">
										<input type="text" class="ui-input" title="상세내용 입력" name="productAddInfo.prdtAddInfo" data-notice-type="information" data-required="${item.reqYn }" maxlength="1000" value="${item.prdtAddInfo }" placeholder="${item.infoNotcDfltValue }"/>
									</td>
									<td class="text-center" id="reqYnArea">
										<%-- <input type="hidden" id="product-add-info-req-yn-${status.index }" name="productAddInfo.reqYn" value="${item.reqYn }"/> --%>
										${item.reqYn eq 'Y' ? '필수' : '' }
									</td>
								</tr>
							</c:if>
						</c:when>
						<c:when test="${item.addInfoType eq 'I' and product.chnnlNo eq '10003'}">
							<tr class="info-notice-row">
								<input type="hidden" name="productAddInfo.divider" value=""/>
								<input type="hidden" name="productAddInfo.prdtAddInfoSeq" value="${item.prdtAddInfoSeq }" /><%-- 상품추가정보순번 --%>
								<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value="${item.prdtAddInfoRltnSeq }"/>
								<input type="hidden" name="productAddInfo.safeCrtfcNoText" value=""/>
								<input type="hidden" name="productAddInfo.prdtSafeTypeCode" value=""/>
								<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
								<input type="hidden" name="productAddInfo.addInfoType" value="I" /><%-- 추가정보구분 --%>
								<input type="hidden" name="productAddInfo.dispYn" value="${item.dispYn }"/><%-- 전시여부 --%>
								<input type="hidden" name="productAddInfo.prdtMaterlText" value=""/>
								<input type="hidden" name="productAddInfo.sortSeq" value="${item.sortSeq }" />
								<input type="hidden" name="productAddInfo.reqYn" value="${item.reqYn }" />
								<td class="input text-center clear-float">
									<span class="ui-chk">
										<input id="prdt-info-notc-seq-${status.index }" data-checkbox="product-add-info-display-separate" type="checkbox" data-target="display-separate" value=""<c:if test="${item.dispYn eq 'Y' }"> checked</c:if>/>
										<label for="prdt-info-notc-seq-${status.index }">상품정보 별도 표시</label>
									</span>
								</td>
								<td class="text-center" name="productAddInfo.infoNotcName">${item.infoNotcName }</td>
								<td class="input" colspan="2">
									<input type="text" class="ui-input" title="상세내용 입력" name="productAddInfo.prdtAddInfo" data-notice-type="information" data-required="${item.reqYn }" maxlength="1000" value="${item.prdtAddInfo }" placeholder="${item.infoNotcDfltValue }"/>
								</td>
								<td class="text-center" id="reqYnArea">
									<%-- <input type="hidden" id="product-add-info-req-yn-${status.index }" name="productAddInfo.reqYn" value="${item.reqYn }"/> --%>
									${item.reqYn eq 'Y' ? '필수' : '' }
								</td>
							</tr>
						</c:when>
						<c:when test="${item.addInfoType eq 'P' }">
							<c:set var="prdtAddInfoSeqTypeP" value="${item.prdtAddInfoSeq }"/>
							<c:set var="safeCrtfcNoTextTypeP" value="${item.safeCrtfcNoText }"/>
							<c:set var="prdtMaterlTextTypeP" value="${item.prdtMaterlText }"/>
							<c:set var="prdtAddInfoTypeP" value="${item.prdtAddInfo }"/>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
			<%-- 취급 시 주의사항 --%>
			<tr id="addInfo">
				<input type="hidden" name="productAddInfo.divider" value="" />
				<input type="hidden" name="productAddInfo.prdtAddInfoSeq" value="${prdtAddInfoSeqTypeP }" /><%-- 상품추가정보순번 --%>
				<input type="hidden" name="productAddInfo.addInfoType" value="P"/><%-- 추가정보구분 --%>
				<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value=""/>
				<input type="hidden" name="productAddInfo.dispYn" value="Y"/><%-- 전시여부 --%>
				<input type="hidden" name="productAddInfo.safeCrtfcNoText" value=""/>
				<input type="hidden" name="productAddInfo.prdtSafeTypeCode" value=""/>
				<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
				<input type="hidden" name="productAddInfo.sortSeq" value="" />
				<td rowspan="2"></td>
				<td class="text-center" rowspan="2">취급시 주의사항</td>
				<td class="text-center">소재</td>
				<td class="input">
					<select class="ui-sel" title="소재 선택" id="prdt-materl-title-text" name="productAddInfo.prdtMaterlText" data-default-value="${prdtMaterlTextTypeP }"><%-- 소재 --%>
						<option value="">선택</option>
					</select>
				</td>
				<td class="text-center"></td>
			</tr>
			<tr>
				<td class="text-center">소재별 관리방법</td>
				<td class="input">
					<textarea title="소재별 관리방법 입력" class="ui-textarea" id="prdt-materl-cont-text" name="productAddInfo.prdtAddInfo" maxlength="1000">${prdtAddInfoTypeP }</textarea><%-- 소재별 관리방법 --%>
				</td>
				<td class="text-center"></td>
			</tr>
			<%-- 제품안전 인증정보 --%>
			<c:set var="isFirstTypeA" value="true"/>
			<c:set var="isExistAddInfoTypeA" value="false"/>
			<c:forEach items="${product.productAddInfo }" var="item" varStatus="status">
				<c:if test="${item.addInfoType eq 'A' }">
					<c:set var="isExistAddInfoTypeA" value="true"/>							
					<tr>
						<input type="hidden" name="productAddInfo.divider" value="" />
						<input type="hidden" name="productAddInfo.prdtAddInfoSeq" value="${item.prdtAddInfoSeq }" /><%-- 상품추가정보순번 --%>
						<input type="hidden" name="productAddInfo.addInfoType" value="A" /><%-- 추가정보구분 --%>
						<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value=""/>
						<input type="hidden" name="productAddInfo.dispYn" value="Y"/><%-- 전시여부 --%>
						<td rowspan="4" class="input text-center clear-float">
							<c:choose>
								<c:when test="${isFirstTypeA eq 'true' }">
									<a href="javascript:void(0);" class="btn-sm btn-func" id="addSafeCrtfc">인증정보 추가</a>
									<c:set var="isFirstTypeA" value="false"/>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" class="btn-sm btn-func safety-remove-btn">삭제</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center" rowspan="4">제품안전 인증정보</td>
						<td class="text-center">확인대상 품목</td>
						<td class="input">
							<select class="ui-sel" title="확인대상 품목 선택" name="productAddInfo.prdtSafeTypeCode" data-event-change="prdt-safe-type-code">
								<option value="">없음</option>
								<c:forEach items="${prdtSafeTypeCodeList }" var="prdtSafeTypeCode">
									<option value="${prdtSafeTypeCode.codeDtlNo }"${prdtSafeTypeCode.codeDtlNo eq item.prdtSafeTypeCode ? ' selected' : '' }>${prdtSafeTypeCode.codeDtlName }</option>
								</c:forEach>
							</select>
						</td>
						<td class="text-center"></td>
					</tr>
					<tr>
						<td class="text-center">문구</td>
						<td class="input">
							<input type="text" class="ui-input prdtAddInfo" title="상세내용 입력" name="productAddInfo.prdtAddInfo" maxlength="1000" value="${item.prdtAddInfo }"/>
						</td>
						<td class="text-center"></td>
					</tr>
					<tr>
						<td class="text-center">인증번호</td>
						<td class="input">
							<input type="text" class="ui-input safeCrtfcNoText" title="상세내용 입력" name="productAddInfo.safeCrtfcNoText" maxlength="25" value="${item.safeCrtfcNoText }"/>
						</td>
						<td class="text-center"></td>
					</tr>
					<tr>
						<td class="text-center">인증마크</td>
						<td>
							<img class="safeCrtfcImageUrl" src="${item.safeCrtfcImageUrl }"/>
							<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="${item.safeCrtfcImageUrl }" />
						</td>
						<td class="text-center"></td>
					</tr>
				</c:if>
			</c:forEach>
			<c:if test="${isExistAddInfoTypeA eq 'false' }">
				<%-- 등록된 정보가 없는 경우, 기본 폼 출력 --%>
				<tr>
					<input type="hidden" name="productAddInfo.divider" value="" />
					<input type="hidden" name="productAddInfo.prdtAddInfoSeq" /><%-- 상품추가정보순번 --%>
					<input type="hidden" name="productAddInfo.addInfoType" value="A" /><%-- 추가정보구분 --%>
					<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value=""/>
					<input type="hidden" name="productAddInfo.dispYn" value="Y"/><%-- 전시여부 --%>
					<td rowspan="4" class="input text-center clear-float">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="addSafeCrtfc">인증정보 추가</a>
					</td>
					<td class="text-center" rowspan="4">제품안전 인증정보</td>
					<td class="text-center">확인대상 품목</td>
					<td class="input">
						<select class="ui-sel prdtSafeTypeCode" title="확인대상 품목 선택" name="productAddInfo.prdtSafeTypeCode">
							<option value="">없음</option>
						</select>
					</td>
					<td class="text-center"></td>
				</tr>
				<tr>
					<td class="text-center">문구</td>
					<td class="input">
						<input type="text" class="ui-input prdtAddInfo" title="상세내용 입력" name="productAddInfo.prdtAddInfo" maxlength="1000" disabled/>
					</td>
					<td class="text-center"></td>
				</tr>
				<tr>
					<td class="text-center">인증번호</td>
					<td class="input">
						<input type="text" class="ui-input safeCrtfcNoText" title="상세내용 입력" name="productAddInfo.safeCrtfcNoText" maxlength="25" disabled/>
					</td>
					<td class="text-center"></td>
				</tr>
				<tr>
					<td class="text-center">인증마크</td>
					<td>
						<img class="safeCrtfcImageUrl" src=""/>
						<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
					</td>
					<td class="text-center"></td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<!-- E : tbl-col -->
	<!-- E : 20190322 수정 // IBSheet > table 수정 -->

	<%-- 상세정보고시 템플릿 --%>	
	<script type="text/x-template" id="info-notice-tmpl">
		<tr class="info-notice-row">
			<input type="hidden" name="productAddInfo.divider" value=""/>
			<input type="hidden" name="productAddInfo.addInfoType" value="I" />
			<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value=""/>
			<input type="hidden" name="productAddInfo.dispYn" value="" />
			<input type="hidden" name="productAddInfo.prdtMaterlText" value=""/>
			<input type="hidden" name="productAddInfo.prdtSafeTypeCode" value=""/>
			<input type="hidden" name="productAddInfo.safeCrtfcNoText" value=""/>
			<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
			<input type="hidden" name="productAddInfo.sortSeq" value="" />
			<input type="hidden" name="productAddInfo.reqYn" value="" />
			<td class="input text-center clear-float">
				<span class="ui-chk">
					<input id="prdt-info-notc-seq-" data-checkbox="product-add-info-display-separate" type="checkbox" data-target="display-separate">
					<label for="prdt-info-notc-seq-">상품정보 별도 표시</label>
				</span>
			</td>
			<td class="text-center" id="infoNoticeName" name="productAddInfo.infoNotcName"></td>
			<td class="input" colspan="2">
				<input type="text" class="ui-input" title="상세내용 입력" name="productAddInfo.prdtAddInfo" data-notice-type="information" data-required="" maxlength="1000"/>
			</td>
			<td class="text-center" id="reqYnArea"></td>
		</tr>
	</script>
	
	<%-- 인증정보 템플릿 --%>
	<script type="text/x-template" id="safety-tmpl">
		<tr>
			<input type="hidden" name="productAddInfo.divider" value=""/>
			<input type="hidden" name="productAddInfo.addInfoType" value="A" />
			<input type="hidden" name="productAddInfo.prdtAddInfoRltnSeq" value=""/>
			<input type="hidden" name="productAddInfo.dispYn" value="Y" />
			<input type="hidden" name="productAddInfo.prdtMaterlText" value=""/>
			<td rowspan="4" class="input text-center clear-float">
				<a href="javascript:void(0);" class="btn-sm btn-func safety-remove-btn">삭제</a>
			</td>
			<td class="text-center" rowspan="4">제품안전 인증정보</td>
			<td class="text-center">확인대상 품목</td>
			<td class="input">
				<select class="ui-sel prdtSafeTypeCode" title="확인대상 품목 선택" name="productAddInfo.prdtSafeTypeCode" data-event-change="prdt-safe-type-code">
					<option value="">없음</option>
				</select>
			</td>
			<td class="text-center"></td>
		</tr>
		<tr>
			<td class="text-center">문구</td>
			<td class="input">
				<input type="text" class="ui-input prdtAddInfo" title="상세내용 입력" name="productAddInfo.prdtAddInfo" disabled/>
			</td>
			<td class="text-center"></td>
		</tr>
		<tr>
			<td class="text-center">인증번호</td>
			<td class="input">
				<input type="text" class="ui-input safeCrtfcNoText" title="상세내용 입력" name="productAddInfo.safeCrtfcNoText" disabled/>
			</td>
			<td class="text-center"></td>
		</tr>
		<tr>
			<td class="text-center">인증마크</td>
			<td>
				<img class="safeCrtfcImageUrl" src=""/>
				<input type="hidden" name="productAddInfo.safeCrtfcImageUrl" value="" />
			</td>
			<td class="text-center"></td>
		</tr>
	</script>
</div>
