(function() {

	var _store = abc.object.createNestedObject(window,"abc.biz.display.store");

	/**
	 * 초기화
	 */
	_store.init = function() {

		_store.event();

		new abc.biz.display.common.processImage({
			file: '#pcImageFile',
			name: '#imageName'
		});
		new abc.biz.display.common.processImage({
			file: '#mobileImageFile',
			name: '#imageName'
		});
	}

	/**
	 * 이벤트
	 */
	_store.event = function(){

		// 지역구분
		$('#areaNo').on('change', function() {
			var areaNo = $(this).val();
			var param = { 'areaNo' : areaNo };

			if(areaNo == '') {
				$('#areaDtlSeq').html($('<option>').text('구/군')).prop('selected', true);
				return;
			}

			$.ajax({
				type : 'post',
				url : '/display/store/area',
				data : param
			}).done(function(data) {
				$('#areaDtlSeq').html($('<option>').text('구/군')).val('');

				if(data.length == 0){
					$('#areaDtlSeq option').eq(0).prop('selected', true);
				}

				$.each(data, function(i,v) {
					var $option = $('<option>').val(v.areaDtlSeq).text(v.areaDtlName);
					$('#areaDtlSeq').append($option);
				});
			}).fail(function(e) {
				console.log("e :" + e);
			})
		});

		// 우편번호 찾기
		$('#postPopup').on('click', function() {
			abc.postPopup(function(result) {
				$('#postCodeText').val(result.postCode);
				$('#postAddrText').val(result.postAddress);
			});
		});

		// 지도검색
		$('#searchMap').on('click', function() {

			var params = {
				'xPointText' : $('input[name=pointX]').val(),
				'yPointText' : $('input[name=pointY]').val(),
				'pointX' : $('input[name=pointX]').val(),
				'pointY' : $('input[name=pointY]').val()
			};

			var pop = abc.open.popup({
				url 	:	'/display/store/map',
				winname :	'map-pop',
				method	: 	'get',
				title 	:	'map-pop',
				width 	:	840,
				height	:	660,
				params	:	params
			});
		});

		// 지도키워드검색
		$('#searchKeywordMap').on('click', function() {
			var pop = abc.open.popup({
				url 	:	'/display/store/map/keyword',
				winname :	'map-keyword-pop',
				method	: 	'get',
				title 	:	'map-keyword-pop',
				width 	:	840,
				height	:	595
			});
		});

		// 영업 중단여부
		$('#businessStopRsnCode').on('change', function() {
			if ($(this).val() != '10000') {
				$('.businessStopArea').hide();
			} else {
				$('.businessStopArea').show();
			}
		});

		// 매장픽업 가능여부
		$('input[name=pickupPsbltYn]').on('click', function() {

			if ($(this).val() == 'Y') {
				$('#businessStopTh').show();
				$('#businessStopTd').show();
			} else {
				$('#businessStopTh').hide();
				$('#businessStopTd').hide();
				$('#businessStopRsnCode option:first').prop('selected', true);
				$('.businessStopArea').hide();
			}

		});

		// 배너 이미지 삭제
		$('.btn-file-del.store-image-del').on('click', function() {
			// 이미지 정보 삭제
			$(this).parent('li').siblings('li').find('input[name*=ImageName]').val('');
			$(this).parent('li').siblings('li').find('input[name*=ImagePathText]').val('');
			$(this).parent('li').siblings('li').find('input[name*=ImageUrl]').val('');
		});

		// 저장
		$('#saveBtn').on('click', function() {

			var phone = $('input[name=telNoText1]').val() + "-" + $('input[name=telNoText2]').val() + "-" + $('input[name=telNoText3]').val();
			$('#telNoText').val(phone);

			var businessStartTime = $('#businessStartHour').val() + $('#businessStartMinute').val();
			var businessEndTime = $('#businessEndHour').val() + $('#businessEndMinute').val();

		//	$('input[name=businessStartTime]').val(businessStartTime);
		//	$('input[name=businessEndTime]').val(businessEndTime);
			var url = "/display/store/save";
			var form = $.form(document.forms.storeForm);

			form.submit({
				url : url,
				method : "POST",
				valid	: function($f){
					return true;
				},
				success : function(data) {

					alert('저장되었습니다.');

					location.href = '/display/store';
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});
		});

		$("[name=siteNo]").on("change",function(){
			if($(this).val() == "10000") {
				$("input:radio[name=storeTypeCode]").prop("disabled",false);
				$("#radioStoreForm9").prop("disabled",true);
				$("#radioStoreForm1").prop("checked",true);
			}
			else if($(this).val() == "10001") {
				$("input:radio[name=storeTypeCode]").prop("disabled",true);
				$("#radioStoreForm9").prop("disabled",false);
				$("#radioStoreForm9").prop("checked",true);
			} else {
				$("[name=storeTypeCode]").prop("disabled",false);
			}
		});

		$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});

		// 오프라인 매장 삭제
		$('#storeDeleteBtn').on('click', function() {

			var flag = confirm('오프라인 매장을 삭제하시겠습니까?');

			if (flag) {
				var storeNo = abc.param.getParams().storeNo;

				$.ajax({
					type : 'post',
					url : '/display/store/delete',
					data : {'storeNo':storeNo}
				}).done(function(data) {
					alert('삭제되었습니다.');
					location.href = '/display/store';
				}).fail(function(e) {
					console.log("e :" + e);
				});
			}
		});

		// 숫자만 입력 (매장 ID)
		$("#storeIdText").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
	}

	/**
	 * 좌표 callback
	 */
	_store.pointCallback = function(result) {

		$('input[name=pointY]').val(result.yPoint);
		$('input[name=pointX]').val(result.xPoint);
	}

	$(function() {
		_store.init();
	});

})();