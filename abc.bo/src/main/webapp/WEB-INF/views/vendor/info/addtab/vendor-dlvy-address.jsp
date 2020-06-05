<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function(){
		var _vendorInfo = {
				postCodeText : $.trim('${vendorInfo.postCodeText}')
			  , postAddrText : $.trim('${vendorInfo.postAddrText}')
			  , dtlAddrText  : $.trim('${vendorInfo.dtlAddrText}')
		};

		abc.biz.vendor.info.add.vendorInfo = _vendorInfo;

		//입점사 기본출고지 설정
		<c:if test="${empty dlvyAddList.D}">
			$("#exWrhsSetup_V").prop("checked", true);
			$("#wrhsDlvyName_D").val('${vendorInfo.vndrName}');
			abc.biz.vendor.info.add.setVendorAddress("D");
		</c:if>
		<c:if test="${!empty dlvyAddList.D}">
			$("#wrhsDlvyName_D").val('${dlvyAddList.D[0].wrhsDlvyName}');
			$("#postCodeText_D").val('${dlvyAddList.D[0].postCodeText}');
			$("#postAddrText_D").val('${dlvyAddList.D[0].postAddrText}');
			$("#dtlAddrText_D").val('${dlvyAddList.D[0].dtlAddrText}');
		</c:if>

		<c:if test="${empty dlvyAddList.W}">
			$("#inWrhsSetup_V").prop("checked", true);
			$("#wrhsDlvyName_W").val('${vendorInfo.vndrName}');
			abc.biz.vendor.info.add.setVendorAddress("W");
		</c:if>
		<c:if test="${!empty dlvyAddList.W}">
			$("#wrhsDlvyName_W").val('${dlvyAddList.W[0].wrhsDlvyName}');	
			$("#postCodeText_W").val('${dlvyAddList.W[0].postCodeText}');
			$("#postAddrText_W").val('${dlvyAddList.W[0].postAddrText}');
			$("#dtlAddrText_W").val('${dlvyAddList.W[0].dtlAddrText}');
			$("#telNoText_W").val('${dlvyAddList.W[0].telNoText}');
		</c:if>

		var postCodeText_D = $.trim($("#postCodeText_D").val());
		var postAddrText_D = $.trim($("#postAddrText_D").val());
		var dtlAddrText_D = $.trim($("#dtlAddrText_D").val());
		if(_vendorInfo.postCodeText == postCodeText_D 
			&& _vendorInfo.postAddrText == postAddrText_D
			&& _vendorInfo.dtlAddrText == dtlAddrText_D){
			$("#exWrhsSetup_V").prop("checked", true);
			$("#postSearchBtn_D").hide();
		}
		
		var postCodeText_W = $.trim($("#postCodeText_W").val());
		var postAddrText_W = $.trim($("#postAddrText_W").val());
		var dtlAddrText_W = $.trim($("#dtlAddrText_W").val());
		if(_vendorInfo.postCodeText == postCodeText_W 
			&& _vendorInfo.postAddrText == postAddrText_W
			&& _vendorInfo.dtlAddrText == dtlAddrText_W){
			$("#inWrhsSetup_V").prop("checked", true);
			$("#postSearchBtn_W").hide();
		}else if(postCodeText_D == postCodeText_W
				&& postAddrText_D == postAddrText_W
				&& dtlAddrText_D == dtlAddrText_W){
			$("#inWrhsSetup_V").prop("checked", true);
			$("#postSearchBtn_W").hide();	
		}else{
			$("#inWrhsSetup_A").prop("checked", true);
		}
		
	});
</script>


	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 기본출고지 설정</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
	<input type="hidden" name="dlvyAddressList.wrhsDlvyGbnType" value="D">
	<input type="hidden" name="dlvyAddressList.telNoText">
		<caption>입점사 기본출고지 설정</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">출고지 설정</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="exWrhsSetup_V" name="exWrhsSetup" type="radio" value="V">
								<label for="exWrhsSetup_V">업체 주소와 동일</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="exWrhsSetup_A" name="exWrhsSetup" type="radio" value="A">
								<label for="exWrhsSetup_A">다른 주소 입력</label>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<tr>
				<th scope="row">출고지명</th>
				<td class="input">
					<input type="text" id="wrhsDlvyName_D" name="dlvyAddressList.wrhsDlvyName" class="ui-input" title="출고지명">
				</td>
			</tr>
			<tr>
				<th scope="row">출고지 주소</th>
				<td class="input">
					<!-- S : address-box -->
					<span class="address-box">
						<span class="zip-code-wrap">
							<input type="text" id="postCodeText_D" name="dlvyAddressList.postCodeText" class="ui-input" title="우편번호 입력" readonly >
							<button type="button" id="postSearchBtn_D" name="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
						</span>
						<span class="address-wrap">
							<input type="text" id="postAddrText_D" name="dlvyAddressList.postAddrText" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly >
							<input type="text" id="dtlAddrText_D" name="dlvyAddressList.dtlAddrText" class="ui-input" placeholder="상세주소" title="상세주소 입력" >
						</span>
					</span>
					<!-- E : address-box -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 기본 회수지 설정</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
	<input type="hidden" name="dlvyAddressList.wrhsDlvyGbnType" value="W">
	<input type="hidden" id="logisVndrCode_W" name="dlvyAddressList.logisVndrCode" value="${vendorInfo.logisVndrCode}">
		<caption>입점사 기본회수지 설정</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">회수지 설정</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="inWrhsSetup_V" name="inWrhsSetup" type="radio" value="V">
								<label for="inWrhsSetup_V">업체 주소와 동일</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="inWrhsSetup_D" name="inWrhsSetup" type="radio" value="D">
								<label for="inWrhsSetup_D">기본출고지와 동일</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="inWrhsSetup_A" name="inWrhsSetup" type="radio" value="A">
								<label for="inWrhsSetup_A">다른 주소 입력</label>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<!-- S : 다른 주소 입력 선택시 노출되는 영역 -->
			<tr>
				<th scope="row">회수지명</th>
				<td class="input">
					<input type="text" id="wrhsDlvyName_W" name="dlvyAddressList.wrhsDlvyName" class="ui-input" title="회수지명 입력" >
				</td>
			</tr>
			<tr>
				<th scope="row">회수지 주소</th>
				<td class="input">
					<!-- S : address-box -->
					<span class="address-box">
						<span class="zip-code-wrap">
							<input type="text" id="postCodeText_W" name="dlvyAddressList.postCodeText" class="ui-input" title="우편번호 입력" readonly >
							<button type="button" id="postSearchBtn_W" name="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
						</span>
						<span class="address-wrap">
							<input type="text" id="postAddrText_W" name="dlvyAddressList.postAddrText" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" readonly >
							<input type="text" id="dtlAddrText_W" name="dlvyAddressList.dtlAddrText" class="ui-input" placeholder="상세주소" title="상세주소 입력" >
						</span>
					</span>
					<!-- E : address-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">회수지 전화번호</th>
				<td class="input">
					<input type="text" class="ui-input" id="telNoText_W" name="dlvyAddressList.telNoText" title="회수지 전화번호 입력" placeholder="‘-’ 없이 입력 (예: 01012345678)">
				</td>
			</tr>
			<!-- E : 다른 주소 입력 선택시 노출되는 영역 -->
		</tbody>
	</table>
	<!-- E : tbl-row -->
