<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="channelCount" value="${fn:length(channelList) }"/>
<table class="tbl-row">
	<caption>상품상세 정보</caption>
	<colgroup>
		<col style="width: 130px"/>
		<col/>
		<col style="width: 130px"/>
		<c:forEach begin="1" end="${channelCount }">
			<col/>
		</c:forEach>
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" rowspan="2">
				<span class="th-required">업체상품코드</span>
			</th>
			<td rowspan="2">${vndrPrdtNoText }</td>
			<th scope="row" class="th-lborder">채널구분</th>
			<c:forEach items="${channelList }" var="item">
				<c:choose>
					<c:when test="${product.chnnlNo eq item.chnnlNo }">
						<%-- 현재 노출중인 상품정보 채널과 동일한 경우, 판매정보와 전시정보를 노출할 영역 확보 --%>
						<td class="text-center">${item.chnnlName }</td>
					</c:when>
					<c:otherwise>
						<%-- 별도 제어 없이 노출 --%>
						<td class="text-center">${item.chnnlName }</td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">상품코드</th>
			<c:forEach items="${channelList }" var="itemChannel">
				<c:set var="productInfoVisible" value="false"/><%-- 정보 표시 여부 --%>
				<c:set var="productInfoButtonAttributes" value=""/><%-- 버튼 내 속성 --%>
				<c:set var="productInfoPrdtNo" value=""/><%-- 상품번호 --%>
				<c:set var="productInfoStatus" value=""/><%-- 상태정보 (판매상태, 전시상태) --%>
				<c:choose>
					<c:when test="${not empty associatedProducts[itemChannel.chnnlNo] }">
						<c:choose>
							<c:when test="${not empty chnnlNo and chnnlNo eq itemChannel.chnnlNo }">
								<%-- 조회중인 온라인상품 정보 --%>
								<c:set var="productInfoVisible" value="true"/>
								<c:set var="productInfoButtonAttributes" value=" data-button='return-self' data-site-no='${itemChannel.siteNo }' data-chnnl-no='${itemChannel.chnnlNo }' data-chnnl-name='${itemChannel.chnnlName }' data-prdt-no='${associatedProducts[itemChannel.chnnlNo].prdtNo }'"/>
								<c:set var="productInfoPrdtNo" value="${associatedProducts[itemChannel.chnnlNo].prdtNo }"/>
								<c:forEach items="${sellStatCodeList }" var="itemSellStatCode">
									<c:if test="${itemSellStatCode.codeDtlNo eq associatedProducts[itemChannel.chnnlNo].sellStatCode }">
										<c:set var="productInfoStatus" value="${itemSellStatCode.codeDtlName }"/>
									</c:if>
								</c:forEach>
								<c:set var="productInfoStatus" value="${productInfoStatus } / ${associatedProducts[itemChannel.chnnlNo].dispYn eq 'Y' ? '전시' : '미전시' }"/>
							</c:when>
							<c:otherwise>
								<%-- 해당 상품으로 이동 링크 생성 --%>
								<c:set var="productInfoVisible" value="true"/>
								<c:set var="productInfoButtonAttributes" value=" data-button='href' data-channel-no='${itemChannel.chnnlNo }' data-uri='/product/product/detail?prdtNo=${associatedProducts[itemChannel.chnnlNo].prdtNo }&siteNo=${associatedProducts[itemChannel.chnnlNo].siteNo }&chnnlNo=${associatedProducts[itemChannel.chnnlNo].chnnlNo }&vndrPrdtNoText=${associatedProducts[itemChannel.chnnlNo].vndrPrdtNoText }'"/>
								<c:set var="productInfoPrdtNo" value="${associatedProducts[itemChannel.chnnlNo].prdtNo }"/>
								<c:forEach items="${sellStatCodeList }" var="itemSellStatCode">
									<c:if test="${itemSellStatCode.codeDtlNo eq associatedProducts[itemChannel.chnnlNo].sellStatCode }">
										<c:set var="productInfoStatus" value="${itemSellStatCode.codeDtlName }"/>
									</c:if>
								</c:forEach>
								<c:set var="productInfoStatus" value="${productInfoStatus } / ${associatedProducts[itemChannel.chnnlNo].dispYn eq 'Y' ? '전시' : '미전시' }"/>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<%-- 등록가능한 채널에 등록된 상품이 없는 경우 --%>
						<c:set var="productInfoVisible" value="true"/>
						<c:set var="productInfoButtonAttributes" value=" id='add-product' data-button='add-product' data-site-no='${itemChannel.siteNo }' data-chnnl-no='${itemChannel.chnnlNo }' data-chnnl-name='${itemChannel.chnnlName }'"/>
						<c:set var="productInfoPrdtNo" value="상품추가"/>
						<c:set var="productInfoStatus" value=""/>
					</c:otherwise>
				</c:choose>

				<c:if test="${newErpProductYn eq 'Y' and siteNo ne itemChannel.siteNo }">
					<%-- 신규등록인 경우, 해당하는 사이트를 제외한 곳에 등록하지 못하도록 제어 --%>
					<c:set var="productInfoButtonAttributes" value=""/>
					<c:set var="productInfoPrdtNo" value=""/>
					<c:set var="productInfoStatus" value="-"/>
				</c:if>

				<%-- 정보 출력 --%>
				<c:if test="${productInfoVisible eq true }">
					<td class="text-center${not empty chnnlNo and chnnlNo eq itemChannel.chnnlNo ? ' td-spot' : '' }" data-channel-area="bo">
						<button type="button" class="link"${productInfoButtonAttributes }>${productInfoPrdtNo }</button>
						${productInfoStatus }
					</td>
				</c:if>
			</c:forEach>
		</tr>
	</tbody>
</table>
