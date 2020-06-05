/***
 * member 업무와 관련된 함수 정의.
 * 
 */
(function() {
	var _appversion = abc.object.createNestedObject(window,"abc.biz.system.appversion");
	var vendorArray = new Array(); 
	
	/***
	 * textArea에 입력된 휴일정보 등록
	 */
	_appversion.setAppversionData = function(){
		// $("input[name=product_color_code_1][value="+colorCode+"]").attr('checked', 'checked');	// 라디오 버튼 값 세팅
		var $form 			= $("#appversionForm");
		var $appOsCode 		= $('input[name="appOsCode"]:checked');	// OS 종류 라디오 박스
		var $appVerText 	= $('#appVerText');	// OS 버전
		var $siteNo 		= $('input[name="siteNo"]:checked');		// 사이즈 라이오 박스
		var $appRgstYmd 	= $('#appRgstYmd');	// 앱 등록일

//		alert("$appOsCode value >>>" + $appOsCode.val() + "\n$siteNo value >>>" + $siteNo.val() + "\n$appVerText value >>>" + $appVerText.val() + "\n$appRgstYmd value >>>" + $appRgstYmd.val() );
		
		if(abc.text.isBlank($appOsCode.val())){
			alert("OS를 입력하세요");
			$appOsCode.focus();
			return false;
		}else if(abc.text.isBlank($appVerText.val())){
			alert("버전정보를 입력하세요");
			$appVerText.focus();
			return false;
		}else if(abc.text.isBlank($siteNo.val())){
			alert("사이트를 선택하세요");
			$siteNo.focus();
			return false;
		}else if(abc.text.isBlank($appRgstYmd.val())){
			alert("앱등록일을 입력하세요");
			$appRgstYmd.focus();
			return false;
		}else{
			var request = $.ajax({
				url: "/system/appversion/create",
				method: "POST",
				data: $form.serialize(),
				dataType: "json",
				async: false
			});
				 
			request.done(function( data ) {
				if(data.result == abc.consts.BOOLEAN_TRUE){
					alert("저장되었습니다.");
					doAction("search");
				}
			});
				 
			request.fail(function( jqXHR, textStatus ) {
				alert("fail textStatus >>>>>" + jqXHR.responseJSON.message);
				console.log("e :" + e);
			});
		}
	}

	/***
	 * selectbox를 통해 선택한 년도의 휴일정보 가져오기[ajax]
	 */
	_appversion.getAppversionData = function(year){
		var $form = $("#appversionForm");
		$('#insertYear').val(year);

		var request = $.ajax({
			  url: "/system/appversion/read-detail",
			  method: "POST",
			  data: {year: year},
			  dataType: "json",
			  async: false
			});
			 
			request.done(function( data ) {
				var isData = $('#appversionDataArea').val().length > 0;
				
				if(isData) {
					$('#appversionDataArea').val($('#appversionDataArea').val() + ',' + data);
				} else {
					$('#appversionDataArea').val(data);
				}
			});
			 
			request.fail(function( jqXHR, textStatus ) {
				alert("fail textStatus >>>>>" + jqXHR.responseJSON.message);
			});
	}
	
	/***
	 * 초기화
	 */
	_appversion.init = function(){
		location.href="/system/appversion"
	}
	
	/**
	 * 캘린더 팝업 보기
	 */
	_appversion.popupAppversionCalendar = function(){
		var $form = $("#appversionForm").serializeArray();
		var param = new Object();
		
		$form.forEach(function(item, index) {
			param[item.name] = item.value; 
		});
		
		var pop = abc.open.popup({
			url 	:	"/system/appversion/detail-calendar-pop",
			winname :	"detail-calendar-pop",
			method	: 	"get",
			title 	:	"detail-calendar-pop",
			width 	:	645,
			height	:	628,
			params	:	param
		});
	}
	
	/**
	 * 공통휴일 적용 업체 추가하기
	 */
	_appversion.addVendorAppversion = function(){
		var value = $("#vndrNo option:selected").val();
		var text  = $("#vndrNo option:selected").text();
		var vendorHtml = '';
		var dupFlag = false;
		
		if(value == ''){
			alert("업체를 선택하세요");
		}else{
			if(vendorArray.length == 0){
				vendorArray.push(value);
				vendorHtml += '<li id="'+value+'">';
				vendorHtml += '	<span class="subject">'+text+'</span>';
				vendorHtml += '	<button type="button" class="btn-item-del">';
				vendorHtml += '		<span class="ico ico-item-del" id="'+value+'"><span class="offscreen">휴일 삭제</span></span>';
				vendorHtml += '	</button>';
				vendorHtml += '</li>';
				$('#addVendorArea').append(vendorHtml);
			}else{
				$.each(vendorArray, function(i, val){
					if($.inArray(value,vendorArray) != -1){	// 중복된 것이 없을 때
						dupFlag = true;
						return false;
					}
				});
				
				if(!dupFlag){
					vendorArray.push(value);
					vendorHtml += '<li id="'+value+'">';
					vendorHtml += '	<span class="subject">'+text+'</span>';
					vendorHtml += '	<button type="button" class="btn-item-del">';
					vendorHtml += '		<span class="ico ico-item-del" id="'+value+'"><span class="offscreen">휴일 삭제</span></span>';
					vendorHtml += '	</button>';
					vendorHtml += '</li>';
					$('#addVendorArea').append(vendorHtml);
				}else{
					alert("이미 추가한 업체입니다.");
				}
			}
		}
	}
})();