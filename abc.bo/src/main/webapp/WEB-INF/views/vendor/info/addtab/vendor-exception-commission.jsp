<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		var vendorCategoryListArr = [];
		var vendorBrandListArr = [];
		var vendorCategoryList = {};
		var vendorBrandList = {};
		
		<c:forEach var="category" items="${vendorCategoryList}">
			vendorBrandList = {stdCtgrName : '${category.stdCtgrName}', stdCtgrNo : '${category.stdCtgrNo}'};
			vendorCategoryListArr.push(vendorBrandList);
		</c:forEach>
		
		<c:forEach var="vendorBrand" items="${vendorBrandList}">
			vendorBrandList = {brandName : '${vendorBrand.brandName}', brandNo : '${vendorBrand.brandNo}'};
			vendorBrandListArr.push(vendorBrandList);
		</c:forEach>
		
		abc.biz.vendor.info.add.vendorCategoryList = vendorCategoryListArr;
		abc.biz.vendor.info.add.vendorBrandList = vendorBrandListArr;
		
		$("[id^=trCommission_]").each(function(){
			var idx= $(this).attr('id').split("_").pop();
			var commissionApplyStartYmd = $("#iCommissionApplyStartYmd_"+idx).val();
			if(abc.date.dateDiff(new Date(), commissionApplyStartYmd) <= 0){
				$(this).find('input').attr("disabled", "disabled");
				$(this).find('select').attr("disabled", "disabled");
			} 
		});
		
		
	});
</script>
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">예외 수수료 정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : 20190130 추가, 수정 // 기획변경으로 예외수수료 정보 테이블 > 관리 > 추가, 삭제버튼 tbl-controller 영역으로 수정 -->
	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<a href="javascript:void(0);" id="delCommissionBtn" class="btn-sm btn-del">삭제</a>
		</div>
		<div class="fr">
			<!-- DESC : html/vendor/BO-VD-01003.html 파일 확인 해주세요 -->
			<a href="javascript:void(0);" id="commissionHistoryPop" class="btn-sm btn-link">예외수수료 적용이력 조회</a>
			<a href="javascript:void(0);" id="commissionMoreBtn" class="btn-sm btn-func">행추가</a>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : tbl-col -->
	<table class="tbl-col">
		<caption>예외 수수료 정보</caption>
		<colgroup>
			<col style="width: 5%">
			<col>
			<col>
			<col>
			<col style="width: 245px">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th scope="col" class="only-chk">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="chkCommissionAll" type="checkbox" name="chkCommission">
						<label for="chkCommissionAll"></label>
					</span>
				</th>
				<th scope="col">표준카테고리</th>
				<th scope="col">브랜드</th>
				<th scope="col">적용대상 수수료율(%)</th>
				<th scope="col">적용기간</th>
				<th scope="col">관리자 메모</th>
			</tr>
		</thead>
		<tbody id="tbodyCommission">
		
		<c:forEach var="commission" items="${commissionList}" varStatus="cmsStatus">
			<tr id="trCommission_${cmsStatus.count}">
				<input type="hidden" id="commissionDelYn_${cmsStatus.count}" name="exceptionCommissionList.delYn" value="${commission.delYn}">
				<input type="hidden" id="commissionRowIndex_${cmsStatus.count}" name="exceptionCommissionList.commissionRowIndex" value="${cmsStatus.count}">
				<input type="hidden" id="vndrExceptCmsnSeq_${cmsStatus.count}" name="exceptionCommissionList.vndrExceptCmsnSeq" value="${commission.vndrExceptCmsnSeq}" >
				<input type="hidden" id="commissionApplyStartYmd_${cmsStatus.count}" name="exceptionCommissionList.applyStartYmd">
				<input type="hidden" id="commissionApplyEndYmd_${cmsStatus.count}" name="exceptionCommissionList.applyEndYmd">
				<input type="hidden" id="sApplyStartYmd_${cmsStatus.count}" value="${commission.applyStartYmd}">
				<td class="only-chk">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="chkCommission_${cmsStatus.count}" type="checkbox" name="chkCommission">
						<label for="chkCommission_${cmsStatus.count}"></label>
					</span>
				</td>
				<td class="input">
					<select class="ui-sel" name="exceptionCommissionList.stdCtgrNo" id="commissionStdCtgrNo_${cmsStatus.count}" custoum="required" title="표준카테고리 선택">
						<option value="">선택</option>
						<c:forEach var="vendorCategory" items="${vendorCategoryList}">
							<option value="${vendorCategory.stdCtgrNo}" <c:if test="${vendorCategory.stdCtgrNo eq commission.stdCtgrNo}">selected</c:if> >${vendorCategory.stdCtgrName}</option>
						</c:forEach>	
					</select>
				</td>
				<td class="input text-center clear-float">
					<select class="ui-sel" name="exceptionCommissionList.brandNo" id="commissionBrandNo_${cmsStatus.count}" custoum="required" title="브랜드 선택">
						<option value="">선택</option>
						<c:forEach var="vendorBrand" items="${vendorBrandList}">
							<option value="${vendorBrand.brandNo}" <c:if test="${vendorBrand.brandNo eq commission.brandNo}">selected</c:if> >${vendorBrand.brandName}</option>
						</c:forEach>	
					</select>
				</td>
				<td class="input clear-float text-center">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" class="ui-input num-unit100" id="cmsnRate_${cmsStatus.count}" name="exceptionCommissionList.cmsnRate" custoum="required" title="적용대상 수수료율 입력" value="${commission.cmsnRate}">
						<span class="text">%</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
				<td class="input">
					<!-- S : term-date-wrap -->
					<span class="term-date-wrap">
						<span class="date-box">
							<input type="text" id="iCommissionApplyStartYmd_${cmsStatus.count}" custoum="required" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" value='<fmt:formatDate value="${commission.applyStartYmd}" pattern="yyyy.MM.dd"/>'>
						</span>
						<span class="text">~</span>
						<span class="date-box">
							<input type="text" id="iCommissionApplyEndYmd_${cmsStatus.count}" custoum="required" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" value='<fmt:formatDate value="${commission.applyEndYmd}" pattern="yyyy.MM.dd"/>'>
						</span>
					</span>
					<!-- E : term-date-wrap -->
				</td>
				<td class="input">
					<input type="text" class="ui-input" name="exceptionCommissionList.noteText" title="관리자 메모 입력" value="${commission.noteText}">
				</td>
			</tr>
		</c:forEach>

		</tbody>
	</table>
	<!-- E : tbl-col -->
	<!-- E : 20190130 추가, 수정 // 기획변경으로 예외수수료 정보 테이블 > 관리 > 추가, 삭제버튼 tbl-controller 영역으로 수정 -->
