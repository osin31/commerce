/***
 * member 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _holiday 	= abc.object.createNestedObject(window,"abc.biz.system.holiday");
	var vendorArray = new Array();

	/***
	 * textArea에 입력된 휴일정보 등록
	 */
	_holiday.setHolidayData = function(){
		var $form 	= $("#holidayForm");
		var $obj 	= $('#holidayDataArea');

		var valFlag = _holiday.isAuthority(true);

		valFlag = (valFlag == true) ? _holiday.isValidation($obj) : false;	// validation

		if(valFlag){
			$('#insertYear').val($obj.val().substring(0, 4));
			// loop로 vendorArray의 배열로 hidden 파라미터를 만들어서 넘기기

			var request = $.ajax({
				url: "/cmm/holiday/create",
				method: "POST",
				data: $form.serialize(),
				async: false
			});

			request.done(function(data, textStatus, jqXHR) {
				if(jqXHR.status == '200'){
		  			alert('저장되었습니다.');
		  			$('#holidayDataArea').val("");
					$('#year').val($('#insertYear').val());
					doAction("search");
		  		}
			});

			request.fail(function( jqXHR, textStatus ) {
				alert("fail textStatus >>>>>" + jqXHR.responseJSON.message);
			});
		}
	}

	/***
	 * 휴일정보 날짜 validation 체크
	 */
	_holiday.isValidation = function(obj){
		var valFlag = true;
		var val = obj.val();
		var arrVal = val.split(',');
		var preVal = "";
		var ymdInputData = new Array();
		var ymdDupData = new Array();
		var dupFlag  = false;

		if(val == ''){
			alert('8자리 년도 데이터를 입력하세요');
			obj.focus();
			return false;
		}else{
			$.each(arrVal, function(i, val){
				var year  = val.substring(0, 4);
				var month = val.substring(4, 6);
				var day   = val.substring(6, 8);
				var d 		 = new Date();
				var fullYear = d.getFullYear();

				if($.inArray(val, ymdInputData ) == -1){
					ymdInputData.push(val);
	            }else{
	            	dupFlag = true;
	            	ymdDupData.push(val);
	            }

				// 8자리 체크
				if(val.length != 8){
					alert("년도 데이터는 8자리로 입력하세요.\nex)20190101");
					valFlag = false;
					return false;
				}else{
					if(fullYear > year){	// 년도 체크
						alert("입력년도가 올해보다 작습니다.\n" + val);
						valFlag = false;
						return false;
					}else if(month > 12){	// 월 체크
						alert("월 정보가 잘못되었습니다.\n" + val);
						valFlag = false;
						return false;
					}else{
						var lastDay = new Date(year, month, 0).getDate();
						
						if(day > lastDay){
							alert("날짜 정보가 잘못되었습니다.\n" + val);
							valFlag = false;
							return false;
						}
					}

					if(i == 0){
						preVal = val.substring(0, 4);
					}else{
						if(preVal != year){
							alert("휴일관리 등록은 년도별로 등록하게 되어있습니다.");
							valFlag = false;
							return false;	// each loop 빠져나가기
						}
					}
					preVal = val.substring(0, 4);
				}

			});
			/* 중복 데이터 허용
			if(dupFlag && valFlag){
				var message = "";
				var lastIdx = ymdDupData.length;
				$.each(ymdDupData, function(i, val){
					var deli = "";
					if((i+1)<lastIdx){
						deli = ", ";
					}
					message += (val+deli);
				});
				alert(message + "년도 데이터가 중복됩니다.");
                valFlag = false;
				return false;
			}*/
		}
		return valFlag;

	}

	/***
	 * selectbox를 통해 선택한 년도의 휴일정보 가져오기[ajax]
	 */
	_holiday.getHolidayData = function(year){
		var $form = $("#holidayForm");
		$('#insertYear').val(year);

		var request = $.ajax({
			  url: "/cmm/holiday/read-detail",
			  method: "POST",
			  data: $form.serialize(),
			  async: false
			});

			request.done(function(data) {
				var isData = $('#holidayDataArea').val().length > 0;
				var hldyYmdData = '';
				var delimiter = '';

				for(i=0;i<data.length;i++){
					if(i > 0) delimiter = ',';
					hldyYmdData += (delimiter +data[i].yyyyMm);
				}

				if(isData) {	// 입력한 데이터가 있는경우 기존 데이터+ 불러온 데이터
					$('#holidayDataArea').val($('#holidayDataArea').val() + ',' + hldyYmdData);
				} else {
					$('#holidayDataArea').val(hldyYmdData);
				}
			});

			request.fail(function(jqXHR, textStatus) {
				alert("fail textStatus >>>>>" + jqXHR.responseJSON.message);
			});
	}

	/***
	 * 휴일관리 초기화
	 */
	_holiday.init = function(){
		$.form("#holidayForm").reset();
	}


	/**
	 * 캘린더 팝업 보기
	 * type:S[시스템]
	 */
	_holiday.popupHolidayCalendar = function(){
		var param = new Object();
		var authType = $('#authType').val();
		var year = $('#year').val();
		var formData = $('#holidayForm').serializeArray();
		
		$.each(formData, function(index, item){
			param[item.name] = item.value;
		});
		
		if(year == ''){
			alert("년도를 선택하고 '캘린더 보기'를 클릭하세요.");
			$('#year').focus();
			return;
		}else{
			var pop = abc.open.popup({
				url 	:	"/cmm/holiday/detail-calendar-pop",
				winname :	"detail-calendar-pop",
				method	: 	"post",
				title 	:	"detail-calendar-pop",
				width 	:	645,
				height	:	628,
				params	:	param
			});
		}
	}

	/**
	 * 공통휴일 적용 업체 추가하기
	 */
	_holiday.addVendorHoliday = function(){
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

	/**
	 * 공통휴일 적용 업체 삭제하기
	 */
	_holiday.deleteVendorHoliday = function(obj){
		var id = $(obj).attr('id');	// id 속성값 가져오기
        $("li").remove('#'+id);	// li html 영역 삭제
        vendorArray.splice(vendorArray.indexOf(id), 1);	// 벤터 ID 값 삭제
	}

	/***
	 * 로그인한 관리자가 휴일관리 사용할 수 있는 권한인지 체크
	 * 휴일관리 화면을 시스템 관리자와 업체 관리자가 같이 사용하다보니
	 * 저장, 삭제의 권한이 있는 관리자만 기능을 사용할 수 있게 체크하기 위한 함수
	 * 총괄 관리자는 시스템 휴일관리 기능 사용, 업체 관리자 그룹은 업체 휴일관리 기능 사용
	 * 총괄 관리자에게 업체 휴일관리의 기능을 줄 수 없는 이유:관리자 세션에서 업체번호[vndrNo]를 가져와야하지만 총괄 관리자는 업체번호가 없음
	 * 기능을 주기 위해서는 화면에서 업체를 선택할 수 있도록 수정해야함.
	 */
	_holiday.isAuthority = function(isMessage){
		var loginRole 		= $('#role').val();				// 로그인한 관리자의 권한[ROLE_10000:총괄관리자그룹코드, ROLE_30000:업체그룹코드]
		var roleAdminGroup 	= $('#roleAdminGroup').val();	// 총괄 관리자 권한 그룹 코드[ROLE_10000]
		var roleVenderGroup = $('#roleVenderGroup').val();	// 업체 권한 그룹 코드[ROLE_30000]
		var authType 		= $('#authType').val();			// 화면의 권한 타입[B,P]

		// 총괄 관리자 휴일 관리 화면일 때
		if(authType == 'B'){
			if(loginRole == roleVenderGroup){
				if(isMessage){
					alert("시스템 관리자 기능입니다.");
				}
				return false;
			}
		}else if(authType == 'P'){	// 업체 휴일관리 화면일 때
			if(loginRole == roleAdminGroup){
				if(isMessage){
					alert("업체 관리자 기능입니다.");
				}
				return false;
			}
		}
		return true;
	}

})();