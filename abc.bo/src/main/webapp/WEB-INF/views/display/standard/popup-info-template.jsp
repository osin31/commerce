<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 전시위치 template -->
<template id="pcDispPosition-firstTmpl">
	<li>
		<select name="bdPopupDisplayPositionPcArr.popupDispPostnCode" class="ui-sel pcDisplaySelect" title="전시 위치 선택">
			<c:forEach var="code" items="${dispPostnCodeList}">
				<c:if test="${code.codeDtlNo ne '10001'}">
					<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="text" name="bdPopupDisplayPositionPcArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
		<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="PC">추가</button>
	</li>
</template>

<template id="moDispPosition-firstTmpl">
	<li>
		<select name="bdPopupDisplayPositionMoArr.popupDispPostnCode" class="ui-sel moDisplaySelect" title="전시 위치 선택">
			<c:forEach var="code" items="${dispPostnCodeList}">
				<c:if test="${code.codeDtlNo ne '10001'}">
					<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="text" name="bdPopupDisplayPositionMoArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
		<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="MO">추가</button>
	</li>
</template>

<template id="pcDispPosition-tmpl">
	<li>
		<select name="bdPopupDisplayPositionPcArr.popupDispPostnCode" class="ui-sel pcDisplaySelect" title="전시 위치 선택">
			<c:forEach var="code" items="${dispPostnCodeList}">
				<c:if test="${code.codeDtlNo ne '10001'}">
					<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="text" name="bdPopupDisplayPositionPcArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
		<button type="button" class="btn-sm btn-func del-dispPosition">삭제</button>
	</li>
</template>

<template id="moDispPosition-tmpl">
	<li>
		<select name="bdPopupDisplayPositionMoArr.popupDispPostnCode" class="ui-sel moDisplaySelect" title="전시 위치 선택">
			<c:forEach var="code" items="${dispPostnCodeList}">
				<c:if test="${code.codeDtlNo ne '10001'}">
					<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="text" name="bdPopupDisplayPositionMoArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
		<button type="button" class="btn-sm btn-func del-dispPosition">삭제</button>
	</li>
</template>

<script type="text/x-template" id="popupType1Template">
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">제목</span>
		</th>
		<td class="input">
			<input type="text" name="popupTitleText" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" value="">
		</td>
		<th scope="row">
			<span class="th-required">우선순위</span>
		</th>
		<td class="input">
			<span class="ip-text-box">
				<input type="text" name="priorSeq" class="ui-input num-unit100" title="우선순위 입력" value="">
				<span class="text">2개 이상 노출 시, 1순위가 가장 위에 노출됩니다.</span>
			</span>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">사용여부</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="useYn" id="radioUse02" checked value="Y" >
						<label for="radioUse02">사용</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="useYn" id="radioUse03" value="N">
						<label for="radioUse03">사용안함</label>
					</span>
				</li>
			</ul>
		</td>
		<th scope="row">
			<span class="th-required">특정기간 그만보기</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="dispLimitType" id="radioView01" checked value="D">
						<label for="radioView01">오늘 하루 그만 보기</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="dispLimitType" id="radioView02" value="W">
						<label for="radioView02">일주일 그만 보기</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="dispLimitType" id="radioView03" value="N">
						<label for="radioView03">사용안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">전시 사이트</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<c:forEach items="${siteList}" var="site" varStatus="i">
					<li>
						<span class="ui-rdo">
							<input type="radio" name="siteNo" id="radioDisplaySite${i.count }" ${i.count eq 1 ? 'checked' : ''} value="${site.siteNo }">
							<label for="radioDisplaySite${i.count}">${site.siteName }</label>
						</span>
					</li>
				</c:forEach>
			</ul>
		</td>
		
		<th scope="row">
			<span class="th-required">디바이스</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="deviceCode" id="radioDevice0" checked value="">
						<label for="radioDevice0">전체</label>
					</span>
				</li>
				<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
					<c:if test="${code.codeDtlNo ne '10002'}">
						<li>
							<span class="ui-rdo">
								<input type="radio" name="deviceCode" id="radioDevice${i.count }" value="${code.codeDtlNo}">
								<label for="radioDevice${i.count}">${code.codeDtlName}</label>
							</span>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea displayArea pcDisplayArea">
		<th scope="row">
			<span class="th-required">PC 전시 위치</span>
		</th>
		<td class="input" colspan="3">
			<ul id="pcDispPositionArea" class="ip-box-list vertical">
				<li>
					<select name="bdPopupDisplayPositionPcArr.popupDispPostnCode" class="ui-sel pcDisplaySelect" title="전시 위치 선택">
						<c:forEach var="code" items="${dispPostnCodeList}">
							<c:if test="${code.codeDtlNo ne '10001'}">
								<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
							</c:if>
						</c:forEach>
					</select>
					<input type="text" name="bdPopupDisplayPositionPcArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
					<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="PC">추가</button>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea displayArea moDisplayArea">
		<th scope="row">
			<span class="th-required">MOBILE 전시 위치</span>
		</th>
		<td class="input" colspan="3">
			<ul id="moDispPositionArea" class="ip-box-list vertical">
				<li>
					<select name="bdPopupDisplayPositionMoArr.popupDispPostnCode" class="ui-sel moDisplaySelect" title="전시 위치 선택">
						<c:forEach var="code" items="${dispPostnCodeList}">
							<c:if test="${code.codeDtlNo ne '10001'}">
								<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
							</c:if>
						</c:forEach>
					</select>
					<input type="text" name="bdPopupDisplayPositionMoArr.dispPostnUrl" readonly class="ui-input url" placeholder="url 입력" title="url 입력">
					<button type="button" class="btn-sm btn-func add-dispPosition" data-device-type="MO">추가</button>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea pcPopupTypeArea">
		<th scope="row">
			<span class="th-required">PC 팝업타입</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcPopupType" id="radioPopupType01" checked value="N">
						<label for="radioPopupType01">윈도우</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcPopupType" id="radioPopupType02" value="L">
						<label for="radioPopupType02">레이어</label>
					</span>
				</li>
			</ul>
		</td>
		<th scope="row">
			<span class="th-required">PC 팝업창 위치</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ip-text-box">
						<span class="text">X :</span>
						<input type="text" name="bdPopupDevice.pcPopupXPostn" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 X위치 입력" value="0">
						<span class="text">pixel</span>
					</span>
				</li>
				<li>
					<span class="ip-text-box">
						<span class="text">Y :</span>
						<input type="text" name="bdPopupDevice.pcPopupYPostn" class="ui-input num-unit10000" placeholder="0 이상" title="팝업창 Y위치 입력" value="0">
						<span class="text">pixel</span>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea deviceArea pcDeviceArea">
		<th scope="row">
			<span class="th-required">PC 팝업 이미지</span>
			<div>(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
		</th>
		<td class="input" colspan="3">
			<div class="file-wrap">
				<ul class="file-list">
					<li>
						<span class="btn-box">
							<input type="hidden" id="pcImageNamePopup" name="bdPopupDevice.pcImageName" class="imageName" value="">
							<input type="file" id="pcImageFilePopup" class="imageFile" name="pcImageFile" title="첨부파일 추가">
							<label for="pcImageFilePopup">찾아보기</label>
						</span>
					</li>
					<li>
						<a href="javascript:void(0);" class="subject"></a>
						<button type="button" class="btn-file-del" style="display: none;">
							<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
						</button>
					</li>
				</ul>
				<div class="alt-wrap">
					<input type="text" name="bdPopupDevice.pcArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="">
				</div>
				<div class="img-wrap"></div>
			</div>
		</td>
	</tr>
	<tr class="popupTypeArea deviceArea pcDeviceArea">
		<th scope="row">
			<span class="th-required">PC 팝업 링크연결<br /> 방법</span>
		</th>
		<td class="input" colspan="3">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType01" checked value="U" data-position="general-pc">
						<label for="radioPCLinkType01">랜딩 URL</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType02" value="M" data-position="general-pc">
						<label for="radioPCLinkType02">이미지맵</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType03" value="N" data-position="general-pc">
						<label for="radioPCLinkType03">연결안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea general-pc-area deviceArea pcDeviceArea">
		<th scope="row">
			<span class="th-required">PC 랜딩 URL</span>
		</th>
		<td class="input" colspan="3">
			<span class="ip-text-box">
				<select name="bdPopupDevice.pcTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
					<option value="S">현재창</option>
					<option value="N">새창</option>
				</select>
				<input type="text" name="bdPopupDevice.pcLinkInfo" class="ui-input url general-pc" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
					title="url 입력" value="" />
			</span>
		</td>
	</tr>
	<tr class="popupTypeArea general-pc-area deviceArea" style="display: none;">
		<th scope="row">
			<span class="th-required">PC 랜딩 URL</span>
		</th>
		<td class="input">
			<textarea class="ui-textarea general-pc" title="랜딩 url 입력" name="bdPopupDevice.pcLinkInfo" disabled></textarea>
		</td>
	</tr>
	<tr class="popupTypeArea deviceArea moDeviceArea">
		<th scope="row">
			<span class="th-required">Mobile 팝업 이미지</span>
			<div>이미지사이즈 640*640<br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
		</th>
		<td class="input" colspan="3">
			<div class="file-wrap">
				<ul class="file-list">
					<li>
						<span class="btn-box">
							<input type="hidden" id="moImageNamePopup" name="bdPopupDevice.moImageName" class="imageName" value="">
							<input type="file" id="moImageFilePopup" class="imageFile" name="moImageFile" title="첨부파일 추가">
							<label for="moImageFilePopup">찾아보기</label>
						</span>
					</li>
					<li>
						<a href="javascript:void(0);" class="subject"></a>
						<button type="button" class="btn-file-del" style="display: none;">
							<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
						</button>
					</li>
				</ul>
				<div class="alt-wrap">
					<input type="text" name="bdPopupDevice.moArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="">
				</div>
				<div class="img-wrap"></div>
			</div>
		</td>
	</tr>
	<tr class="popupTypeArea deviceArea moDeviceArea">
		<th scope="row">
			<span class="th-required">Mobile 팝업<br />링크연결 방법</span>
		</th>
		<td class="input" colspan="3">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType01" checked value="U" data-position="general-mobile">
						<label for="radioMobileLinkType01">랜딩 URL</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType02" value="M" data-position="general-mobile">
						<label for="radioMobileLinkType02">이미지맵</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType03" value="N" data-position="general-mobile">
						<label for="radioMobileLinkType03">연결안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea general-mobile-area deviceArea">
		<th scope="row">
			<span class="th-required">Mobile 랜딩 URL</span>
		</th>
		<td class="input" colspan="3">
			<span class="ip-text-box">
				<select name="bdPopupDevice.moTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
					<option value="S">현재창</option>
					<option value="N">새창</option>
				</select>
				<input type="text" name="bdPopupDevice.moLinkInfo" class="ui-input url general-mobile" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
							title="url 입력" value="" />
			</span>
		</td>
	</tr>
	<tr class="popupTypeArea general-mobile-area deviceArea moDeviceArea" style="display: none;">
		<th scope="row">
			<span class="th-required">MOBILE 랜딩 URL</span>
		</th>
		<td class="input">
			<textarea class="ui-textarea general-mobile" title="랜딩 url 입력" name="bdPopupDevice.moLinkInfo" disabled></textarea>
		</td>
	</tr>
</script>

<script type="text/x-template" id="popupType2Template">
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">제목</span>
		</th>
		<td class="input">
			<input type="text" name="popupTitleText" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" value="">
			<input type="hidden" name="siteNo" value="10000" />
		</td>
		<th scope="row">
			<span class="th-required">사용여부</span>
		</th>
		<td class="input">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="useYn" id="radioUse02" checked value="Y" >
						<label for="radioUse02">사용</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="useYn" id="radioUse03" value="N">
						<label for="radioUse03">사용안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">이벤트 팝업 이미지</span>
			<div>권장사이즈 101*130 <br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
		</th>
		<td class="input" colspan="3">
			<div class="file-wrap">
				<ul class="file-list">
					<li>
						<span class="btn-box">
							<input type="hidden" id="pcImageNameEvent" name="bdPopupDevice.pcImageName" class="imageName" value="">
							<input type="file" id="pcImageFileEvent" class="imageFile" name="pcImageFile" title="첨부파일 추가">
							<label for="pcImageFileEvent">찾아보기</label>
						</span>
					</li>
					<li>
						<a href="javascript:void(0);" class="subject"></a>
						<button type="button" class="btn-file-del" style="display: none;">
							<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
						</button>
					</li>
				</ul>
				<div class="alt-wrap">
					<input type="text" name="bdPopupDevice.pcArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="">
				</div>
				<div class="img-wrap"></div>
			</div>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">이벤트 팝업 링크연결<br /> 방법</span>
		</th>
		<td class="input" colspan="3">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType01" checked value="U" data-position="event-normal">
						<label for="radioPCLinkType01">랜딩 URL</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType02" value="M" data-position="event-normal">
						<label for="radioPCLinkType02">이미지맵</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType03" value="P" data-position="event-normal" data-position="event-folding" data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호">
						<label for="radioPCLinkType03">기획전</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType04" value="B" data-position="event-normal" data-link-url="/brand/detail?brndNo=" data-hint="브랜드번호">
						<label for="radioPCLinkType04">브랜드</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.pcCnnctnType" id="radioPCLinkType05" value="N" data-position="event-normal">
						<label for="radioPCLinkType05">연결안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea event-normal-area">
		<th scope="row">
			<span class="th-required">이벤트 팝업 랜딩 URL</span>
		</th>
		<td class="input" colspan="3">
			<span class="ip-text-box">
				<select name="bdPopupDevice.pcTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
					<option value="S">현재창</option>
					<option value="N">새창</option>
				</select>
				<input type="text" name="bdPopupDevice.pcLinkInfo" class="ui-input url event-normal" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
					title="url 입력" value="" />
			</span>
		</td>
	</tr>	
	<tr class="popupTypeArea event-normal-area" style="display: none;">
		<th scope="row">
			<span class="th-required">랜딩 URL</span>
		</th>
		<td class="input">
			<textarea class="ui-textarea event-normal" title="랜딩 url 입력" name="bdPopupDevice.pcLinkInfo" disabled></textarea>
		</td>
	</tr>
	<tr class="popupTypeArea event-normal-area" style="display: none;">
		<th scope="row">
			<span class="th-required">랜딩 URL</span>
		</th>
		<td class="input" colspan="">
			<span class="ip-text-box">
				<span class="text event-normal-link-url"></span>
				<input type="text" class="ui-input event-normal event-normal-input-info" placeholder="" title="" name="bdPopupDevice.pcLinkInfo" disabled>
			</span>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">폴딩 이벤트 배너 이미지</span>
			<div>권장사이즈 920*413 <br />(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
		</th>
		<td class="input" colspan="3">
			<div class="file-wrap">
				<ul class="file-list">
					<li>
						<span class="btn-box">
							<input type="hidden" id="moImageNameEvent" name="bdPopupDevice.moImageName" class="imageName" value="">
							<input type="file" id="moImageFileEvent" class="imageFile" name="moImageFile" title="첨부파일 추가">
							<label for="moImageFileEvent">찾아보기</label>
						</span>
					</li>
					<li>
						<a href="javascript:void(0);" class="subject"></a>
						<button type="button" class="btn-file-del" style="display: none;">
							<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
						</button>
					</li>
				</ul>
				<div class="alt-wrap">
					<input type="text" name="bdPopupDevice.moArtrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="">
				</div>
				<div class="img-wrap"></div>
			</div>
		</td>
	</tr>
	<tr class="popupTypeArea">
		<th scope="row">
			<span class="th-required">폴딩 이벤트 배너<br />링크연결 방법</span>
		</th>
		<td class="input" colspan="3">
			<ul class="ip-box-list">
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType01" checked value="U" data-position="event-folding">
						<label for="radioMobileLinkType01">랜딩 URL</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType02" value="M" data-position="event-folding">
						<label for="radioMobileLinkType02">이미지맵</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType03" value="P" data-position="event-folding"  data-position="event-folding" data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호">
						<label for="radioMobileLinkType03">기획전</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType04" value="B" data-position="event-folding" data-link-url="/brand/detail?brndNo=" data-hint="브랜드번호">
						<label for="radioMobileLinkType04">브랜드</label>
					</span>
				</li>
				<li>
					<span class="ui-rdo">
						<input type="radio" name="bdPopupDevice.moCnnctnType" id="radioMobileLinkType05" value="N" data-position="event-folding">
						<label for="radioMobileLinkType05">연결안함</label>
					</span>
				</li>
			</ul>
		</td>
	</tr>
	<tr class="popupTypeArea event-folding-area">
		<th scope="row">
			<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
		</th>
		<td class="input" colspan="3">
			<span class="ip-text-box">
				<select name="bdPopupDevice.moTargetType" class="ui-sel" title="랜딩 페이지 화면 유형 선택">
					<option value="S">현재창</option>
					<option value="N">새창</option>
				</select>
				<input type="text" name="bdPopupDevice.moLinkInfo" class="ui-input url event-folding" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" 
							title="url 입력" value="" />
			</span>
		</td>
	</tr>
	<tr class="popupTypeArea event-folding-area" style="display: none;">
		<th scope="row">
			<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
		</th>
		<td class="input">
			<textarea class="ui-textarea event-folding" title="랜딩 url 입력" name="bdPopupDevice.moLinkInfo" disabled></textarea>
		</td>
	</tr>
	<tr class="popupTypeArea event-folding-area" style="display: none;">
		<th scope="row">
			<span class="th-required">폴딩 이벤트 팝업 랜딩 URL</span>
		</th>
		<td class="input" colspan="">
			<span class="ip-text-box">
				<span class="text event-folding-link-url"></span>
				<input type="text" class="ui-input event-folding event-folding-input-info" placeholder="" title="" name="bdPopupDevice.moLinkInfo" disabled>
			</span>
		</td>
	</tr>
</script>