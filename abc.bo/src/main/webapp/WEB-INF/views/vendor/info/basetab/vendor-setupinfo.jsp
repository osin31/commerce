<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function(){
		//전시채널 설정
		<c:forEach var="chnnl" items="${chnnlList}" varStatus="status">
			$("#chkChannel_"+${chnnl.chnnlNo}).prop("checked", true);
			abc.biz.vendor.info.base.chargeMdDisplay();
			//담당MD정보 설정
			<c:forEach var="chargeMd" items="${chargeMdList}" varStatus="status">
				<c:if test="${chnnl.chnnlNo == chargeMd.chnnlNo}">
					$("#chargeMdNo_"+${chnnl.chnnlNo}).val('${chargeMd.chargeMdNo}');
					$("#spanChargeMd_"+${chnnl.chnnlNo}).text('${chargeMd.chargeMdId}(${chargeMd.chargeMdName})');
					$("#deleteChargeMdNo_"+${chnnl.chnnlNo}).css("display","");
				</c:if>
			</c:forEach>
		</c:forEach>
		
		if($("input[id^=chkChannel_]:checkbox:checked").length == $("input[id^=chkChannel_]").length){
			$("#channelCheckAll").prop("checked", true);
		}
	});
	
	// 관리자찾기 팝업 callBack함수
	function adminSearchPopupResult(data){
		var chnnlNo = abc.biz.vendor.info.base.selChnnlNo;
		
		$("#chargeMdNo_" + chnnlNo).val(data.adminNo);
		$("#spanChargeMd_"+ chnnlNo).text(data.loginId+"("+data.adminName+")");
	}
</script>

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 설정정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>입점사 설정정보</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">전시채널</span>
				</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-chk">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input type="checkbox" name="channelCheckAll" id="channelCheckAll">
								<label for="channelCheckAll">전체</label>
							</span>
						</li>
						<c:if test="${!empty channelList}">
							<c:forEach var="channelList" items="${channelList}" varStatus="status">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="checkbox" name="chnnlNoList.chnnlNo" id="chkChannel_${channelList.chnnlNo}" value="${channelList.chnnlNo}">
										<label for="chkChannel_${channelList.chnnlNo}">${channelList.chnnlName}</label>
									</span>
								</li>
							</c:forEach>
						</c:if>
					</ul>
					<!-- E : ip-box-list -->
				</td>
				<th scope="row">
					<span class="th-required">거래여부</span>
				</th>
				<td class="input">
					<ul class="ip-box-list">
						<c:forEach var="vndrStatCode" items="${codeList.VNDR_STAT_CODE}" varStatus="status">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="radio" name="vndrStatCode" id="radioDeal_${vndrStatCode.codeDtlNo}" 
										value="${vndrStatCode.codeDtlNo}" 
										<c:choose>
											<c:when test="${!empty vendorInfo}">
												<c:if test="${vndrStatCode.codeDtlNo == vendorInfo.vndrStatCode}">checked="checked"</c:if>
											</c:when>
											<c:when test="${vndrStatCode.codeDtlNo == '10002'}">checked="checked"</c:when>
										</c:choose>
										>
									<label for="radioDeal_${vndrStatCode.codeDtlNo}">${vndrStatCode.codeDtlName}</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
<!-- DESC : 20190308 삭제 // 담당 관리자 정보 영역 삭제 -->

<%-- 			<tr>
				<th scope="row" id="thMdRowspan" rowspan="1">담당관리자정보</th>
				<td id="thMdDefault"></td>
			</tr>	
			<c:forEach var="channelList" items="${channelList}" varStatus="status">
			<tr id="tr_${channelList.chnnlNo}" style="display:none;">
				<td class="input" colspan="3">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<span class="text">${channelList.chnnlName}</span>
						<a href="javascript:void(0);" id="searchChargeMdNo_${channelList.chnnlNo}" class="btn-sm btn-link">관리자 찾기</a>
					</span>
					<!-- E : ip-text-box -->

					<!-- S : search-item -->
					<span class="search-item">
						<input type="hidden" name="chargeMdList.chargeMdChnnlNo" value="${channelList.chnnlNo}">
						<input type="hidden" id="chargeMdNo_${channelList.chnnlNo}" name="chargeMdList.chargeMdNo">
						<span class="subject" id="spanChargeMd_${channelList.chnnlNo}"></span>
						<button type="button" class="btn-item-del" id="deleteChargeMdNo_${channelList.chnnlNo}" style="display:none;">
							<span class="ico ico-item-del"><span class="offscreen">담당MD 삭제</span></span>
						</button>
					</span>
					<!-- E : search-item -->
				</td>
			</tr>
			</c:forEach> --%>
<!-- DESC : 20190308 삭제 // 담당 관리자 정보 영역 삭제 -->


			<tr>
				<th scope="row" rowspan="2">
					<span class="th-required">표준카테고리 지정</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : search-dropdown-box -->
					<span class="search-dropdown-box">
						<select class="search-dropdown ui-sel" id="selStandardCategory" title="표준카테고리 자동완성" data-placeholder="카테고리명 (대)">
							<option value="">선택</option>
							<c:forEach var="standardCategory" items="${standardCategoryList}" varStatus="status">
								<option value="${standardCategory.stdCtgrNo}">${standardCategory.stdCtgrName}</option>
							</c:forEach>	
						</select>
						<button type="button" id="addStandardCategory" class="btn-sm btn-func">추가</button>
					</span>
					<!-- E : search-dropdown-box -->
				</td>
			</tr>
			<tr>
				<td class="input" colspan="3">
					<!-- S : item-list -->
					<ul class="item-list" id="ulStandardCategory">
						<c:forEach var="vendorCategory" items="${vendorCategoryList}" varStatus="stdCtgrStatus">
							<li>
								<input type="hidden" name="vendorStdCtgList.vendorStdCtgrStatus" value="U">
								<input type="hidden" name="vendorStdCtgList.vendorStdCtgrNo" id="vendorStdCtgrNo_${stdCtgrStatus.count}" value="${vendorCategory.stdCtgrNo}"> 
								<span class="subject">${vendorCategory.stdCtgrName}</span>
								<%-- <button type="button" class="btn-item-del">
									<span class="ico ico-item-del"><span class="offscreen">${category.stdCtgrName} 삭제</span></span>
								</button> --%>
							</li>
						</c:forEach>
					</ul>
					<!-- E : item-list -->
				</td>
			</tr>
			<tr>
				<th scope="row" rowspan="2">
					<span class="th-required">브랜드 지정</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : 20190130 수정 // 기획변경으로 수정 -->
					<a href="javascript:void(0);" id="serchBrandPop" class="btn-sm btn-link">브랜드 찾기</a>
					<!-- E : 20190130 수정 // 기획변경으로 수정 -->
				</td>
			</tr>
			<tr>
				<td class="input" colspan="3">
					<!-- S : item-list -->
					<ul class="item-list" id="ulVendorBrand">
						<c:forEach var="vendorBrand" items="${vendorBrandList}" varStatus="brandStatus">
							<li>
								<input type="hidden" name="vendorbrandList.vendorBrandStatus" value="U">
								<input type="hidden" name="vendorbrandList.vendorBrandNo" id="vendorBrandNo_${brandStatus.count}" value="${vendorBrand.brandNo}"> 
								<span class="subject">${vendorBrand.brandName}</span>
								<!-- <button type="button" class="btn-item-del">
									<span class="ico ico-item-del"><span class="offscreen">브랜드명 삭제</span></span>
								</button> -->
							</li>
						</c:forEach>
					</ul>
					<!-- E : item-list -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
