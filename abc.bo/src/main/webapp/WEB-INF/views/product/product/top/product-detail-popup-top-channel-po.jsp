<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="tbl-row">
	<caption>상품상세 정보</caption>
	<colgroup>
		<col style="width: 200px"/>
		<c:forEach items="${channelList }" var="itemChannel"><%-- 채널 수에 따른 테이블 칸 설정 --%>
			<col/>
		</c:forEach>
		<col/>
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" class="th-lborder">채널구분</th>
			<c:forEach items="${channelList }" var="itemChannel">
				<td class="text-center">${itemChannel.chnnlName }</td><%-- 채널이름출력 --%>
			</c:forEach>
			<td class="text-center">조회중인 상품 복사</td>
		</tr>
		<tr>
			<th scope="row">상품코드</th>
			<%-- 채널별 온라인상품코드 출력 --%>
			<c:forEach items="${channelList }" var="itemChannel">
				<c:set var="productInfoReadonly" value="false"/><%-- 읽기전용여부 --%>
				<c:set var="productInfoVisible" value="false"/><%-- 정보 표시 여부 --%>
				<c:set var="productInfoButtonAttributes" value=""/><%-- 버튼 내 속성 --%>
				<c:set var="productInfoPrdtNo" value=""/><%-- 상품번호 --%>
				<c:set var="productInfoStatus" value=""/><%-- 상태정보 (판매상태, 전시상태) --%>
				<c:choose>
					<c:when test="${not empty product and itemChannel.chnnlNo eq product.chnnlNo }">
						<%-- 조회중인 온라인상품 정보 --%>
						<c:set var="productInfoReadonly" value="false"/>
						<c:set var="productInfoVisible" value="true"/>
						<c:set var="productInfoButtonAttributes" value=" data-button='return-self' data-site-no='${itemChannel.siteNo }' data-chnnl-no='${itemChannel.chnnlNo }' data-chnnl-name='${itemChannel.chnnlName }' data-prdt-no='${associatedProducts[itemChannel.chnnlNo].prdtNo }'"/>
						<c:set var="productInfoPrdtNo" value="${product.prdtNo }"/>
						<c:forEach items="${sellStatCodeList }" var="itemSellStatCode">
							<c:if test="${itemSellStatCode.codeDtlNo eq product.sellStatCode }">
								<c:set var="productInfoStatus" value="${itemSellStatCode.codeDtlName }"/>
							</c:if>
						</c:forEach>
						<c:set var="productInfoStatus" value="${productInfoStatus } / ${product.dispYn eq 'Y' ? '전시' : '미전시' }"/>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${not empty associatedProducts[itemChannel.chnnlNo] }">
								<%-- 연관채널에 등록된 상품정보 --%>
								<c:set var="productInfoReadonly" value="false"/>
								<c:set var="productInfoVisible" value="true"/>
								<c:set var="productInfoButtonAttributes" value=" data-button='href' data-channel-no='${itemChannel.chnnlNo }' data-uri='/product/product/detail?prdtNo=${associatedProducts[itemChannel.chnnlNo].prdtNo }&siteNo=${associatedProducts[itemChannel.chnnlNo].siteNo }&chnnlNo=${associatedProducts[itemChannel.chnnlNo].chnnlNo }&vndrPrdtNoText=${associatedProducts[itemChannel.chnnlNo].vndrPrdtNoText }'"/>
								<c:set var="productInfoPrdtNo" value="${associatedProducts[itemChannel.chnnlNo].prdtNo }"/>
								<c:forEach items="${sellStatCodeList }" var="itemSellStatCode">
									<c:if test="${itemSellStatCode.codeDtlNo eq associatedProducts[itemChannel.chnnlNo].sellStatCode }">
										<c:set var="productInfoStatus" value="${itemSellStatCode.codeDtlName }"/>
									</c:if>
								</c:forEach>
								<c:set var="productInfoStatus" value="${productInfoStatus } / ${associatedProducts[itemChannel.chnnlNo].dispYn eq 'Y' ? '전시' : '미전시' }"/>
							</c:when>
							<c:otherwise>
								<%-- 상품추가 --%>
								<c:set var="productInfoReadonly" value="false"/>
								<c:set var="productInfoVisible" value="true"/>
								<c:set var="productInfoButtonAttributes" value=" id='add-product' data-button='add-product' data-site-no='${itemChannel.siteNo }' data-chnnl-no='${itemChannel.chnnlNo }' data-chnnl-name='${itemChannel.chnnlName }'"/>
								<c:set var="productInfoPrdtNo" value="상품추가"/>
								<c:set var="productInfoStatus" value=""/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- 정보 출력 --%>
				<c:if test="${productInfoVisible eq true }">
					<td class="text-center${not empty chnnlNo and chnnlNo eq itemChannel.chnnlNo ? ' td-spot' : '' }" data-channel-area="po">
						<c:choose>
							<c:when test="${productInfoReadonly eq true }">
								${productInfoStatus }
							</c:when>
							<c:otherwise>
								<button type="button" class="link"${productInfoButtonAttributes }>${productInfoPrdtNo }</button>
								${productInfoStatus }
							</c:otherwise>
						</c:choose>
					</td>
				</c:if>
			</c:forEach>
			<td class="text-center">
				<c:choose>
					<c:when test="${type eq 'registry' }">
						-<%-- 최초등록인 경우 --%>
					</c:when>
					<c:otherwise>
						<button type="button" class="link" data-button="regist-by-copy">복사등록</button><%-- 수정상태일 경우 --%>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>
