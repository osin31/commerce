<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#baseInfoStatus").val() == 'C'){
			var strHtml = ""
			if(!$("#dlvyGuideInfo_10000").val().replace(/(^\s*)|(\s*$)/gi, "")){
				strHtml = ""
				strHtml += '<p class="notice-title">배송비</p>';
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">배송비 : 동일 업체 상품 5만원 미만 구매 시 2,500원, 5만원 이상 구매 시 무료 (제주/도서산간 지역은 추가 운임이 발생할 수 있습니다.)</li>';
				strHtml += '</ul>';
				strHtml += '<p class="notice-title">평균 배송일</p>';
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">본 상품은 결제완료 후 평균 2일 이내에 발송 예정입니다. (주말 및 공휴일 제외)</li>';
				strHtml += '	<li class="bullet-text">상품에 따라(예약 상품, 제작 상품 등) 배송 기간이 다를 수 있습니다.</li>';
				strHtml += '	<li class="bullet-text">택배사 사정에 따라 배송이 다소 지연될 수 있습니다.</li>';
				strHtml += '</ul>';
				strHtml += '<p class="notice-title">교환/반품/AS 안내</p>';
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">입점 업체 상품으로 ABC-MART 매장에서 교환/반품/AS 불가합니다.</li>';
				strHtml += '	<li class="bullet-text">교환/반품은 마이페이지 > 쇼핑내역 > 취소/교환/반품 신청 페이지 또는 고객센터를 통해 접수해주시기 바랍니다.</li>';
				strHtml += '</ul>';

				$("#dlvyGuideInfo_10000").val(strHtml);
			}

			if(!$("#dlvyGuideInfo_10001").val().replace(/(^\s*)|(\s*$)/gi, "")){
				strHtml = ""
				strHtml += '<ul class="notice-list">';
				strHtml += '	<li class="line-text">제품을 받으신 날부터 7일 이내(상품불량인 경우 30일)에 접수해주시기 바랍니다.</li>';
				strHtml += '	<li class="line-text">접수 시 왕복 택배비가 부과됩니다. (단, 상품 불량, 오배송의 경우 택배비를 환불해드립니다.)</li>';
				strHtml += '	<li class="line-text">접수 후 14일 이내에 상품이 반품지로 도착하지 않을 경우 접수가 취소됩니다.(배송 지연 제외)</li>';
				strHtml += '	<li class="line-text">상품을 외부에서 착용한 경우, 상품이 훼손된 경우, 제품에 붙어있는 택(TAG)을 분실/훼손한 경우, 함께 발송된 사은품이 분실/훼손된 경우, 브랜드 박스가 분실/훼손된 경우에는 교환/반품이 불가능합니다.</li>';
				strHtml += '</ul>';
				strHtml += '<p class="notice-title">교환/반품(환불) 시 박스 포장 예</p>';
				strHtml += '<p class="bullet-text">브랜드 박스의 훼손이 없도록 택배 박스 및 타 박스로 포장하여 발송해주시기 바랍니다.</p>';
				strHtml += '<div class="packing-img-wrap">';
				strHtml += '	<div class="img-box">';
				strHtml += '		<img src="/static/images/product/prod_entering_comp_exchange.png" alt="올바른 박스 포장과 교환, 환불이 불가한 포장의 예">';
				strHtml += '	</div>';
				strHtml += '</div>';
				strHtml += '<p class="notice-title">교환/반품(환불) 배송비 안내</p>';
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">왕복 택배비 : 최초 배송비 (2500원) + 반품 배송비(2500원) = 총 5,000원 이 부과됩니다. (선결제 또는 환불금액에서 차감 선택)</li>';
				strHtml += '	<li class="bullet-text">단, 보내주신 상품이 불량 또는 오배송으로 확인 될 경우 택배비를 환불해드립니다. (선택하신 결제수단으로 택배비 환불)</li>';
				strHtml += '	<li class="bullet-text">업체 지정 택배 외 타택배 이용 시 추가로 발생되는 금액은 고객님께서 부담해주셔야 합니다.</li>';
				strHtml += '	<li class="bullet-text">제주/도서산간 지역은 추가 운임이 발생할 수 있습니다.</li>';
				strHtml += '</ul>';

				$("#dlvyGuideInfo_10001").val(strHtml);
			}

			if(!$("#dlvyGuideInfo_10002").val().replace(/(^\s*)|(\s*$)/gi, "")){
				strHtml = ""
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">입점 업체 상품의 AS 신청은 상품 정보고시의 A/S 책임자와 전화번호 정보로 연락해주시기 바랍니다.</li>';
				strHtml += '</ul>';
				strHtml += '<p class="notice-title">심의 AS</p>';
				strHtml += '<ul class="bullet-text-list">';
				strHtml += '	<li class="bullet-text">불량으로 확인되는 내용을 자세하게(사진 첨부 가능) 기재해주시면 처리 시 도움이 될 수 있습니다.</li>';
				strHtml += '	<li class="bullet-text">상품 불량으로 인한 심의 접수 시 왕복 택배비는 ABC-MART에서 부담합니다. (대한통운 택배 이용 권장)</li>';
				strHtml += '</ul>';
				strHtml += '<div class="claim-process-step type03">';
				strHtml += '	<ul class="step-box">';
				strHtml += '		<li>';
				strHtml += '			<div class="title-box">';
				strHtml += '				<span class="num">01</span><span class="step-title">AS 접수</span>';
				strHtml += '			</div>';
				strHtml += '			<p class="desc">온라인 쇼핑몰에 로그인 마이 페이지 &gt; 쇼핑내역 &gt; 반품/교환/AS 또는 고객센터를 통해 접수 &gt; AS신청 (1588-9667 / 080-701-7770)</p>';
				strHtml += '		</li>';
				strHtml += '		<li>';
				strHtml += '			<div class="title-box">';
				strHtml += '				<span class="num">02</span><span class="step-title">접수완료</span>';
				strHtml += '			</div>';
				strHtml += '			<p class="desc">마이페이지 &gt; 쇼핑내역 &gt; 반품/교환/AS &gt; AS신청 &gt; 접수확인 상태 확인</p>';
				strHtml += '		</li>';
				strHtml += '		<li>';
				strHtml += '			<div class="title-box">';
				strHtml += '				<span class="num">03</span><span class="step-title">ABC-MART로 상품 발송</span>';
				strHtml += '			</div>';
				strHtml += '			<p class="desc">주소 : 서울특별시 중구 을지로 100, B동 21층(을지로 2가, 파인에비뉴) ABCMART 온라인 AS담당자 앞</p>';
				strHtml += '		</li>';
				strHtml += '		<li>';
				strHtml += '			<div class="title-box">';
				strHtml += '				<span class="num">04</span><span class="step-title">ABC-MART에 상품도착 브랜드사 또는 수선업체 접수</span>';
				strHtml += '			</div>';
				strHtml += '			<p class="desc">';
				strHtml += '				수선 기간은 총 2주 정도 소요 / 수선 완료 시 개별 유선 통보 <br />';
				strHtml += '				불량 판정 시 무상 교환 또는 환불 처리 / 불량이 아닐 경우 유선 안내 후 상품 반송 (2차 심의 접수 가능)';
				strHtml += '			</p>';
				strHtml += '		</li>';
				strHtml += '	</ul>';
				strHtml += '</div>';

				$("#dlvyGuideInfo_10002").val(strHtml);
			}
		}
	});


</script>

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">배송안내</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>배송안내</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
		<c:forEach var="dlvyGuideBgnCode" items="${codeList.DLVY_GUIDE_BGN_CODE}" varStatus="status">
			<tr>
				<th scope="row">${dlvyGuideBgnCode.codeDtlName}</th>
				<td class="input">
					<!-- S : editor-wrap -->
					<div class="editor-wrap">
						<input type="hidden" name="deliveryGuideList.dlvyGuideBgnCode" value="${dlvyGuideBgnCode.codeDtlNo}">
						<textarea rows="10" cols="100" name="deliveryGuideList.dlvyGuideInfo" id="dlvyGuideInfo_${dlvyGuideBgnCode.codeDtlNo}" class="ckeditor">
							<c:forEach var="guide" items="${guideList}" varStatus="status">
								<c:if test="${guide.dlvyGuideBgnCode == dlvyGuideBgnCode.codeDtlNo}">
									${guide.dlvyGuideInfo}
								</c:if>
							</c:forEach>
						</textarea>
					</div>
					<!-- E : editor-wrap -->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- E : tbl-row -->
