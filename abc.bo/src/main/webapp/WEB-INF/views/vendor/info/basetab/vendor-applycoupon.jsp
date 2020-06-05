<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function(){
		//업체쿠폰 적용설정
		<c:forEach var="applyCoupon" items="${applyCouponList}" varStatus="status">
			$("#chkCoupon_"+${applyCoupon.cpnTypeCode}).prop("checked", true);
			<c:if test="${applyCoupon.cpnTypeCode == '10006'}">
				$("#shareRate").val('${applyCoupon.shareRate}');
				$("#shareRate").prop("disabled", ($("#chkCoupon_"+${applyCoupon.cpnTypeCode}).is(":checked") ? false : true));
			</c:if>
		</c:forEach>
	});
</script>

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">업체쿠폰 적용설정</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>업체쿠폰 적용설정</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
			<col style="width: 130px">
			<col style="width: 15%">
			<col style="width: 100px">
			<col style="width: 100px">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">배송관련쿠폰 적용</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<c:forEach var="cpnTypeCode" items="${codeList.CPN_TYPE_CODE}" varStatus="status">
							<c:if test="${cpnTypeCode.addInfo1 == 'Y' and cpnTypeCode.codeDtlNo != '10006'}">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="checkbox" name="applyCouponList.cpnTypeCode" id="chkCoupon_${cpnTypeCode.codeDtlNo}" value="${cpnTypeCode.codeDtlNo}" >
										<label for="chkCoupon_${cpnTypeCode.codeDtlNo}">${cpnTypeCode.codeDtlName}</label>
									</span>
								</li>
							</c:if>
						</c:forEach>
					</ul>
					<!-- E : ip-box-list -->
				</td>
				<th scope="row">품절보상쿠폰 적용</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-chk">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<c:forEach var="cpnTypeCode" items="${codeList.CPN_TYPE_CODE}" varStatus="status">
									<c:if test="${cpnTypeCode.addInfo1 == 'Y' and cpnTypeCode.codeDtlNo == '10006'}">
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="checkbox" name="applyCouponList.cpnTypeCode" id="chkCoupon_${cpnTypeCode.codeDtlNo}" value="${cpnTypeCode.codeDtlNo}" >
											<label for="chkCoupon_${cpnTypeCode.codeDtlNo}">${cpnTypeCode.codeDtlName}</label>
										</span>
									</c:if>
								</c:forEach>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
				<th scope="row">
					<span class="th-required">분담률</span>
				</th>
				<td class="input">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<!-- DESC : 품절보상쿠폰 선택시, input 영역 disabled="disabled" 속성 제거해주세요 -->
						<input type="text" class="ui-input num-unit100" title="분담률 입력" maxlength="3" id="shareRate" disabled="disabled" value="">
						<span class="text">%</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
