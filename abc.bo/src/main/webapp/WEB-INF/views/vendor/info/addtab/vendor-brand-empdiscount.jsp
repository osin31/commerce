<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">임직원 할인 여부</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : 20190130 추가, 수정 // 기획변경으로 예외수수료 정보 테이블 > 관리 > 추가, 삭제버튼 tbl-controller 영역으로 수정 -->
	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<a href="javascript:void(0);" id="delDiscountBrandChkBtn" class="btn-sm btn-del">삭제</a>
		</div>
		<div class="fr">
			<a href="javascript:void(0);" id="discountBrandMoreBtn" class="btn-sm btn-func">추가</a>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : tbl-col -->
	<table class="tbl-col">
		<caption>임직원 할인 여부</caption>
		<colgroup>
			<col style="width: 7%">
			<col style="width: 20%">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th scope="col" class="only-chk">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="chkBrandAll" type="checkbox" name="chkBrand">
						<label for="chkBrandAll"></label>
					</span>
				</th>
				<th scope="col">적용대상 할인율(%)</th>
				<th scope="col">적용브랜드</th>
			</tr>
		</thead>
		<tbody id="tbodyDiscountBrand">
		<c:forEach var="discount" items="${discountList}" varStatus="dcStatus">
			<tr name="trDiscount_${dcStatus.count}">
				<input type="hidden" id="chkDiscountBrandDelYn_${dcStatus.count}" name="chkDiscountBrandDelYn" value="N">
				<td class="only-chk" rowspan="2">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="chkBrand_${dcStatus.count}" type="checkbox" name="chkBrand">
						<label for="chkBrand_${dcStatus.count}"></label>
					</span>
				</td>
				<td class="input clear-float text-center" rowspan="2">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="iDscntRate_${dcStatus.count}" name="iDscntRate" class="ui-input num-unit100" title="적용 할인율 입력" value="${discount[0].dscntRate}">
						<span class="text">%</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
				<td class="input">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<select class="ui-sel" id="selDiscountBrandNo_${dcStatus.count}" title="브랜드 찾기">
							<option value="">선택</option>
							<c:forEach var="vendorBrand" items="${vendorBrandList}">
								<option value="${vendorBrand.brandNo}">${vendorBrand.brandName}</option>
							</c:forEach>	
						</select>
						<a href="javascript:void(0);" id="addDiscountBrandBtn_${dcStatus.count}" class="btn-sm btn-func">추가</a>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr name="trDiscount_${dcStatus.count}">
				<td class="input">
					<ul class="item-list" id="ulDiscountBrand_${dcStatus.count}">
						<c:forEach var="discountBrand" items="${discount}" varStatus="dcBrandStatus">
							<li>
								<input type="hidden" name="employeeDiscountList.discountBrandIndex" value="${dcStatus.count}${dcBrandStatus.count}">
								<input type="hidden" id="discountBrandNo_${dcStatus.count}_${dcBrandStatus.count}" name="employeeDiscountList.brandNo" value="${discountBrand.brandNo}">
								<input type="hidden" name="employeeDiscountList.vndrBrandEmpDscntSeq" value="${discountBrand.vndrBrandEmpDscntSeq}" >
								<input type="hidden" id="dscntRate_${dcStatus.count}_${dcBrandStatus.count}" name="employeeDiscountList.dscntRate" value="${discount[0].dscntRate}">
								<input type="hidden" id="discountBrandDelYn_${dcStatus.count}_${dcBrandStatus.count}" name="employeeDiscountList.delYn" value="N">
								<c:forEach var="vendorBrand" items="${vendorBrandList}">
									<c:if test="${vendorBrand.brandNo eq discountBrand.brandNo}"><span class="subject">${vendorBrand.brandName}</span></c:if>
								</c:forEach>
								<button type="button" id="delDiscountBrandBtn_${dcStatus.count}_${dcBrandStatus.count}" class="btn-item-del">
									<span class="ico ico-item-del"><span class="offscreen">해당 브랜드 삭제</span></span>
								</button>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- E : tbl-col -->
	<!-- E : 20190130 추가, 수정 // 기획변경으로 예외수수료 정보 테이블 > 관리 > 추가, 삭제버튼 tbl-controller 영역으로 수정 -->
