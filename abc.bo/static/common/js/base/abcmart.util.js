/***
 * utils 성 함수 정의.
 *
 */
(function() {

	/* 에이비씨 마트 쇼핑 몰 약자.*/
	if(!window.abc){
		window.abc = {};
	}

	window.abc.object = {};

	var _object = window.abc.object;

    /**
	 * depth 가 있는 변수를 기본 초기화한다 (fn.item1.item2)
     * @param base 초기화할 기본변수
     * @param items dot 이 포함되어 있는 변수명 (abc.util)
	 * @see http://stackoverflow.com/questions/5484673/javascript-how-to-dynamically-create-nested-objects-using-object-names-given-by
     */
	_object.createNestedObject = function(base, items){
    	items = items.split('.');

        $.each(items, function(i, v){
            base = base[v] = (base[v] || {});
        });

        return base;
    };



    /**
     * text util
     */

	var _text = _object.createNestedObject(window,"abc.text");

	_text.isBlank = function (str) {
	    return (!str || /^\s*$/.test(str));
	};

	_text.isUndefined = function(str){
		return (str == undefined);
	};

	_text.isNull = function(str){
		return (str == null);
	};

	// <%-- null 체크 --%>
	_text.allNull = function(obj) {
		return (typeof obj != "undefined" && obj != null && obj != "") ? false : true;
	}

	/**
	 * 텍스트 형태의 날짜 체크(예 19990101)
	 */
	_text.isDate = function(obj) {
		var date_regex = /^(19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$/;
		return date_regex.test(obj);
	}

	/**
	 * URL 패턴 체크
	 */
	_text.isUrl = function(str) {
		var regexp = /(http(s)?:\/\/)([a-z0-9\w]+\.*)+[a-z0-9]{1,5}/gi;
		return regexp.test(str);
	}

	/**
	 * 키 입력 체크(숫자 영문만 가능)
	 */
	_text.validateOnlyAlphabetNum = function(obj){
		if(!(event.keyCode >=37 && event.keyCode<=40)){
			var inputVal = $(obj).val();
			$("#"+obj.id).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}
	}

	/**
	 * 키 입력 체크(한글, 영문만 가능)
	 */
	_text.validateOnlyString = function(obj){
		if(!(event.keyCode >=37 && event.keyCode<=40)){
			var inputVal = $(obj).val();
			$("#"+obj.id).val(inputVal.replace(/[^(ㄱ-힣a-zA-Z)]/gi,''));
		}
	}

	/**
	 * 키 입력 체크(숫자만 가능)
	 */
	_text.validateOnlyNumber = function(obj){
		if(!(event.keyCode >=37 && event.keyCode<=40)){
			var inputVal = $(obj).val();
			$("#"+obj.id).val(inputVal.replace(/[^0-9]/gi,''));
		}
	}

	/**
	 * 키 입력 체크(숫자, '-'만 가능)
	 */
	_text.validateOnlyNumberHyphen = function(obj){
		if(!(event.keyCode >=37 && event.keyCode<=40)){
			var inputVal = $(obj).val();
			$("#"+obj.id).val(inputVal.replace(/[^0-9\-]/gi,''));
		}
	}

	/**
	 * 키 입력 체크(한글, 영문, 숫자만 가능하고 replace 시키진 않는다.)
	 */
	_text.validateCheckStringAndNum = function(str) {
		var regexp = /^[가-힣a-zA-Z0-9]+$/;
		return regexp.test(str);
	}

	/**
	 * 키 입력 체크(영문, 숫자만 가능하고 replace 시키진 않는다.)
	 */
	_text.validateCheckAlphabetAndNum = function(str) {
		var regexp = /^[a-zA-Z0-9]+$/;
		return regexp.test(str);
	}

	/**
	 * 문자열 내에 특수문자 제거
	 */
	_text.validateStringSignRemove = function(obj){
		var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
		return obj.replace(regExp, "");
	}
	
	/**
	 * 오늘일자 가져오기(YYYYMMDD)
	 */
	_text.todayFormat = function(){
		var date = new Date(); 
		var year = date.getFullYear(); 
		var month = new String(date.getMonth()+1); 
		var day = new String(date.getDate());
		
		// 한자리수일 경우 0을 채워준다. 
		if(month.length == 1){ 
		  month = "0" + month; 
		} 
		if(day.length == 1){ 
		  day = "0" + day; 
		}
		
		return year + "" + month + "" + day; 
	}

	/**
	 * 패스워드 체크
	 */
	_text.validatePassword = function(value){
		var pswdStr = value;
		var pswdStrLength = pswdStr.length;

		/* 모든 조건은 실패라는 가정하에 */
		var isChkOk1 = false;
		var isChkOk2 = false;
		var isChkOk3 = false;
		var isChkOk4 = true;
		var isChkOk5 = true;
		var isChkOk6 = true; /* 공백확인할 변수 */
		var pt1 = /^.*(?=.{10,20})(?=.*[a-zA-Z])(?=.*[0-9]).*$/;
		var pt2 = /^.*(?=.{10,20})(?=.*[a-zA-Z])(?=.*\W).*$/;
		var pt3 = /^.*(?=.{10,20})(?=.*[0-9])(?=.*\W).*$/;
		var pt4 = /^.*(?=.{10,20})(?=.*\s).*$/; /* 공백확인할 정규화 */
		/* 10글자 이상부터 검사 */
		if(pswdStr.length < 10) {
			return '비밀번호는 10글자 이상, 20글자 이하이어야 합니다.';
		}
		if(pswdStr.length > 20) {
			return '비밀번호는 10글자 이상, 20글자 이하이어야 합니다.';
		}
		/* pt1 : 10~20글자 && 영문 포함 && 숫자 포함 */
		if (pt1.test(pswdStr)) { isChkOk1 = true; }
		/* pt2 : 10~20글자 && 영문 포함 && 특수문자 포함 */
		if (pt2.test(pswdStr)) { isChkOk2 = true; }
		/* pt3 : 10~20글자 && 숫자 포함 && 특수문자 포함 */
		if (pt3.test(pswdStr)) { isChkOk3 = true; }
		/* pt4 : 공백 일경우 false */
		if (pt4.test(pswdStr)) {isChkOk6 = false; }
		/* 반복문자가 3번이상 패턴 검사 */
		var alpNumSpc = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "{", "]", "}", "\\", "|", "'", "\"", ";", ":", "/", "?", ".", ">", ",", "<"];
		for (var i = 0; i < alpNumSpc.length; i++) {
			var pt = new RegExp(((i > 35)? "\\" + alpNumSpc[i] : alpNumSpc[i]) + "{3,}", "g");
			if (pt.test(pswdStr)){
				isChkOk4 = false;
				break;
			}
		}
		/* 아이디와 비밀번호 동일여부 체크 */
		if($("#loginId").val() == pswdStr || $("#loginId").val() == pswdStr){
			isChkOk5 = false;
		}
		/* 결과 집계 */
		if (!isChkOk1 && !isChkOk2 && !isChkOk3) {
			return '비밀번호는 영문, 숫자, 특수문자 중 2개 이상 조합이어야 합니다.';
		}
		if (!isChkOk4) {
			return '동일 문자 3회 이상 사용할 수 없습니다.';
		}
		if (!isChkOk5) {
			return '아이디와 동일한 비밀번호를 사용할 수 없습니다.';
		}
		if (!isChkOk6) {
			return '비밀번호에 공백은 포함될 수 없습니다.';
		}

		return null;
	}
	
	/**
	 * 이미지 파일 확장자 체크
	 */
	_text.confirmImgFileExtension = function(file) {
		var reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;

	  	if(file.match(reg)) {
	  		return false;
		} else {
			return true;
		}
	}

	/**
	 * 사업자번호 유효성을 검사한다.
	 */
	_text.checkBizID = function(str) {
		var sum = 0;
		var aBizID = new Array(10);
		var checkID = new Array("1", "3", "7", "1", "3", "7", "1", "3", "5");

		for ( var i = 0; i < 10; i++) {
			aBizID[i] = str.substring(i, i + 1);
		}
		for ( var i = 0; i < 9; i++) {
			sum += aBizID[i] * checkID[i];
		}
		sum = sum + parseInt((aBizID[8] * 5) / 10);
		temp = sum % 10;
		temp1 = 0;

		if (temp != 0) {
			temp1 = 10 - temp;
		} else {
			temp1 = 0;
		}
		if (temp1 != aBizID[9]) {
			return false;
		}
		return true;
	};

	/**
	 * 법인등록번호 유효성을 검사한다.
	 */
	_text.checkCorpID = function(sRegNo) {
		var re = /-/g;
		sRegNo = sRegNo.replace('-','');

		if (sRegNo.length != 13){
			return false;
		}

		/*
		 * 법인등록번호 유효성이 예외사항이 많아
		 * 자리수만 체크하는걸로 대체
		 */
		/*
		var arr_regno  = sRegNo.split("");
		var arr_wt   = new Array(1,2,1,2,1,2,1,2,1,2,1,2);
		var iSum_regno  = 0;
		var iCheck_digit = 0;

		for (i = 0; i < 12; i++){
			iSum_regno +=  eval(arr_regno[i]) * eval(arr_wt[i]);
		}

		iCheck_digit = 10 - (iSum_regno % 10);

		iCheck_digit = iCheck_digit % 10;

		if (iCheck_digit != arr_regno[12]){
			return false;
		}
		*/
		return true;

	};

	/**
	 * 내외국인 주민등록번호 유효성을 검사한다.
	 */
	_text.checkPersonID = function(str) {

		var checkID = new Array(2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5);
		var i = 0, sum = 0;
		var temp = 0;
		var yy = "";

		if (str.length != 13) {
			return false;
		}
		for (i = 0; i < 13; i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				return false;
			}
		}

		// foreigner PersonID Pass
		if (str.substring(6, 13) == "5000000" || str.substring(6, 13) == "6000000" || str.substring(6, 13) == "7000000" || str.substring(6, 13) == "8000000") {
			return true;
		}
		for (i = 0; i < 12; i++) {
			sum += str.charAt(i) * checkID[i];
		}
		temp = sum - Math.floor(sum / 11) * 11;
		temp = 11 - temp;
		temp = temp - Math.floor(temp / 10) * 10;

		// 나이 (-) 체크
		if (str.charAt(6) == '1' || str.charAt(6) == '2' || str.charAt(6) == '5' || str.charAt(6) == '6') {
			yy = "19";
		} else {
			yy = "20";
		}
		if (parseInt(common_util.getCurrentDate('yyyy')) - parseInt(yy + str.substring(0, 2)) < 0) {
			return false;
		}

		// 외국인 주민번호 체크로직 추가
		if (str.charAt(6) != '5' && str.charAt(6) != '6' && str.charAt(6) != '7' && str.charAt(6) != '8') {
			if (temp == eval(str.charAt(12))) {
				return true;
			} else {
				return false;
			}
		} else {
			if ((temp + 2) % 10 == eval(str.charAt(12))) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	};

	/**
	 * 메일주소의 유효성 검사를 한다.
	 */
	_text.isEmail = function(str) {
		if (typeof str != "undefined" && str != "") {
			var format = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

			if (format.test(str)) {
				return	true;
			} else {
				return	false;
			}
		}
		return	true;
	};

	/**
	 * 핸드폰번호 유효성 검사
	 */
	_text.isPhoneNum = function(str) {
		var regPhoneNum = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})([0-9]{3,4})([0-9]{4})$/;
		if(regPhoneNum.test(str)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 입력한 문자열 길이와 제한 문자열 길이 비교
	 * 한글은 2length로 처리됨에 유의한다.
	 */
	_text.isLimitLength = function(str, limit) {
		var strByteLength = str.uniLength();
		if (strByteLength > limit) {
			return true;
		} else {
			return false;
		}
	}

	_text.substringByte = function(str, limit) {
		var size = 0;
		var charCode = "";

		for(var i = 0; i < str.length; i++) {
			charCode = str.charAt(i);
			size += charCode.uniLength();
			if(size > limit) break;
		}

		return str.substring(0, i);
	}

	/**
	 * 숫자형태 문자열에 콤마
	 * */
	_text.isMakeComma = function(str) {
		var regexp = /\B(?=(\d{3})+(?!\d))/g;
		return str.toString().replace(regexp, ',');
	}

	/**
	 * 해당 앨리먼트 몽땅 콤마
	 */
	_text.isMakeCommaForAll = function(el) {
		el.each(function(index, item) {
			$(item).text(_text.isMakeComma($(item).text()));
		});
	}

	/**
	 * 전화번호 형식으로 변경 (정규식)
	 * phoneNum : 전화번호
	 * type : 0을 보내면 가운데자리를 숨겨준다
	 * */
	_text.phoneFomatter = function(phoneNum,type){
		var formatNum = '';
		var num = _text.validateStringSignRemove(phoneNum); //특수문자 제거
		if(num.length==11){
			if(type==0){
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			}else{
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			}
		}else if(num.length==8){
			formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
		}else{
			if(num.indexOf('02')==0){
				if(type==0){
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
				}else{
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
				}
			}else{
				if(type==0){
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
				}else{
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
				}
			}
		}
		return formatNum;
	}



    /**
     * popup util
     */

	var _open = _object.createNestedObject(window,"abc.open");

	var win = null;

	/***
	 *
	 *	새창을 열어야 할 때 사용한다.
	 *	이때 파라메터 값을 넘길 수 있으며 기본 메소드는 get 이다. 간단한 파라메터는 get, 넘기려는 파라메터가 많을 경우 post로 처리한다.
	 *
	 *	popup() 함수는 window.open 객체를 리턴 하며 해당 값으로 close()를 호출 하여 창을 닫을 수 있다.
	 *
	 * var pop = abc.open.popup({
	 * 		url 	:	"/member/test",
	 * 		winname :	"testpop" 	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
	 * 		metho	: 	"get"
	 *		title 	:	"팝업 제목",
	 *		width 	:	400,
	 *		height	:	400,
	 *		params	:	{a : 1,b : 2} or { a : [1,2,3], b : [4,5,6]}
	 *
	 * });
	 *
	 * pop.close();
	 *
	 */
	_open.popup = function(pin) {
		var defaultProps = {
			winname : "_POPUP_",
			title : "",
			params : {},
			method: "get",
			scrollbars : true,
			resizable : true,
			action : false
		};


		if (!pin.width) {
			pin.height = "400";
		};

		if (!pin.height) {
			pin.height = "400";
		};

		pin = $.extend(defaultProps, pin || {});

		var winHeight = document.body.clientHeight;	// 현재창의 높이
		var winWidth = document.body.clientWidth;	// 현재창의 너비
		var winX = window.screenLeft;	// 현재창의 x좌표
		var winY = window.screenTop-20;	// 현재창의 y좌표

		var intLeft = winX + (winWidth - Number((pin.width + "").replace(/px/, '')))/2;
		var intTop = winY + (winHeight - Number((pin.height + "").replace(/px/, '')))/2;

		win = null;

		if (pin.method == "get") {
			var openUrl = pin.url;
			var params = "";
			$.each(pin.params, function(name, value) {

				if ($.isArray(value)) {
					$.each(value, function(index, value) {
						params += ("&" + name + "=" + encodeURI(value));
					});

				} else if (typeof (value) == "object") {
					if(value != null ){
						$.each(value, function(name, value) {
							params += ("&" + name + "=" + encodeURI(value));
						});
					}
				} else {
					params += ("&" + escape(name) + "=" + encodeURI(value));
				};
			});

			openUrl += ("?title=" + pin.title + params);

			win = window.open(openUrl, pin.winname, "location=no,menubar=no, scrollbars="
					+ (pin.scrollbars ? "yes" : "no") + ", resizable="
					+ (pin.resizable ? "yes" : "no") + ", status="+(pin.status ? "yes" : "no")+", width="
					+ pin.width + ", height=" + pin.height + ",top=" + (pin.top ? pin.top : intTop)
					+ ",left=" + (pin.left ? pin.left : intLeft) + ",fullscreen=" + (pin.type == "full" ? "yes" : "no") + "");
		} else if (pin.method == "post") {
			var openUrl = "";

			win = window.open(openUrl, pin.winname, "location=no,menubar=no, scrollbars="
					+ (pin.scrollbars ? "yes" : "no") + ", resizable="
					+ (pin.resizable ? "yes" : "no") + ", status="+(pin.status ? "yes" : "no")+", width="
					+ pin.width + ", height=" + pin.height + ",top=" + (pin.top ? pin.top : intTop)
					+ ",left=" + (pin.left ? pin.left : intLeft)  + ",fullscreen=" + (pin.type == "full" ? "yes" : "no") + "");

			if (win != null) {
				//form을 만들자.
				$("#_POPUP_FORM_").remove();	//기존에 만들어진 폼이 있다면 일단 삭제....
				//pin.params.title = pin.title;
				var form = $("<form id=\"_POPUP_FORM_\" method=\"post\" action=\"" + pin.url + "\" target=\"" + pin.winname + "\"></form>");

				if(pin.params instanceof Array){
					var paramsLength  = pin.params.length;

					for (var i = 0; i < paramsLength; i++) {
						$.each(pin.params[i], function(name, value) {
							var input = $("<input type=\"hidden\" />").attr("name", name).val(value);
							form.append(input);
						});
					}

				}else{
					$.each(pin.params, function(name, value) {
						var input = $("<input type=\"hidden\" />").attr("name", name).val(value);
						form.append(input);
					});
				}

				$("body").append(form);

				form.each(function() {
					this.submit();
				});
			};
		} else {
			alert("죄송합니다. 팝업 정보가 올바르지 않습니다.");
			return false;
		};

		// 포커스
		if (win != undefined && win != null) {
			win.focus();
		};

		if ($.isFunction(pin.callback)) {
			pin.callback(win);
		};

		return win;
	};

	//추후 디자인 구현시 작업.
	_open.popupLayer = function(){

	};

	/**
	 * param util
	 */
	var _param = _object.createNestedObject(window,"abc.param");
	_param.getParams = function(iUrl) {
		var url = decodeURIComponent(_text.allNull(iUrl) ? location.href : iUrl);

		var params;
		params = url.substring( url.indexOf('?')+1, url.length );
		params = params.split("&");

		var size = params.length;
		var param = {}, key, value;
		for(var i=0 ; i < size ; i++) {
			key = params[i].split("=")[0];
			value = params[i].split("=")[1];
			param[key] = (_text.isBlank(value) ? value : value.replace("#", ""));
		}

		return param;
	}




	var _date = _object.createNestedObject(window,"abc.date");

	/**
	 * 달력에서 받은 날짜를 표준 형태로 변환
	 */
	_date.changeDate = function(e){
		var date;
		var year, month, day;
		year = e.substring(0, 4);
		month = e.substring(5, 7);
		day = e.substring(8, 10);

		date = new Date(year, month-1, day);
		return date;
	}

	/**
	 * 날짜 기간 설정 : 어제
	 */
	_date.yesterday = function(e){
		var _Today = new Date();
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');

		_Today.setDate(_Today.getDate() - 1);
		$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
		$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
	}

	/**
	 * 날짜 기간 설정 : 오늘
	 */
	_date.days = function(e){
		var _Today = new Date();
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');

		$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
		$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
	}

	/**
	 * 날짜 기간 설정 : 1주일
	 */
	_date.week = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setDate(_Today.getDate() - 6);		// 7일기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setDate(inputDate.getDate() - 6); // 7일기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 날짜 기간 설정 : 한달
	 */
	_date.month = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setMonth(_Today.getMonth() - 1);
			_Today.setDate(_Today.getDate() + 1);		//1달 기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setMonth(inputDate.getMonth() - 1);
			inputDate.setDate(inputDate.getDate() + 1);	//1달 기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 날짜 기간 설정 : 1년
	 */
	_date.year = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setYear(_Today.getFullYear() - 1);
			_Today.setDate(_Today.getDate() + 1);		//1년 기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setYear(inputDate.getFullYear() - 1);
			inputDate.setDate(inputDate.getDate() + 1);	//1년 기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 날짜 기간 설정 : 전체
	 */
	_date.all = function(e){
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');

		$("#"+toDateId).val("");
		$("#"+fromDateId).val("");
	}

	_date.compareDate = function(firstDate, secondDate) {

	    var result = 0;
	    var pattern = /[^(0-9)]/gi;
	    var startDate = firstDate.replace(pattern, "");
	    var endDate = secondDate.replace(pattern, "");
	    if(startDate > endDate){
	        result = 1;
	    }else if(startDate < endDate){
	        result = -1;
	    }
	    return result;
	};
	//검색조건 날짜 validate
	_date.searchValidate = function(){
		var dateArea = $(".term-date-wrap");
		var resultValidate = true;

		dateArea.each(function(idx){
			var fromDateId = $(this).find('input').eq(0).attr('id');
			var toDateId = $(this).find('input').eq(1).attr('id');
			if(!abc.text.isBlank($("#"+fromDateId).val()) && !abc.text.isBlank($("#"+toDateId).val())){
				if(_date.compareDate($("#"+fromDateId).val(), $("#"+toDateId).val()) > 0){
					alert("시작일보다 이전날짜를 선택할 수 없습니다.");
					$("#"+fromDateId).focus();
					resultValidate =false;
					return false;

				}
				return true;
			}else if(abc.text.isBlank($("#"+fromDateId).val()) && abc.text.isBlank($("#"+toDateId).val())){
				return true;
			}else if(abc.text.isBlank($("#"+fromDateId).val())){
				alert("검색 시작일을 설정해주세요");
				$("#"+fromDateId).focus();
				resultValidate = false;
				return false;
			}else if(abc.text.isBlank($("#"+toDateId).val())){
				alert("검색 종료일을 설정해주세요");
				$("#"+toDateId).focus();
				resultValidate = false;
				return false;
			}
		})
		return resultValidate;

	}


	/**
	 * 두 날짜 차이 계산
	 */
	_date.dateDiffAbs = function(_date1, _date2) {
		var diffDate_1;
		if(_date1 instanceof Date){
			diffDate_1 = _date1;
		}else{
			var tmpDate = abc.text.validateStringSignRemove(_date1).substring(0, 8);
			var dt = tmpDate.substring(0, 4)+ "-" + tmpDate.substring(4, 6) + "-" + tmpDate.substring(6, 8);
			diffDate_1 = new Date(dt);
		}

		var diffDate_2;
		if(_date2 instanceof Date){
			diffDate_2 = _date2;
		}else{
			var tmpDate = abc.text.validateStringSignRemove(_date2).substring(0, 8);
			var dt = tmpDate.substring(0, 4)+ "-" + tmpDate.substring(4, 6) + "-" + tmpDate.substring(6, 8);
			diffDate_2 = new Date(dt);
		}

		diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
		diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

		var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
		diff = Math.ceil(diff / (1000 * 3600 * 24));

		return diff;
	}

	_date.dateDiff = function(_date1, _date2) {

		var diffDate_1;
		if(_date1 instanceof Date){
			diffDate_1 = _date1;
		}else{
			var tmpDate = abc.text.validateStringSignRemove(_date1).substring(0, 8);
			var dt = tmpDate.substring(0, 4)+ "-" + tmpDate.substring(4, 6) + "-" + tmpDate.substring(6, 8);
			diffDate_1 = new Date(dt);
		}

		var diffDate_2;
		if(_date2 instanceof Date){
			diffDate_2 = _date2;
		}else{
			var tmpDate = abc.text.validateStringSignRemove(_date2).substring(0, 8);
			var dt = tmpDate.substring(0, 4)+ "-" + tmpDate.substring(4, 6) + "-" + tmpDate.substring(6, 8);
			diffDate_2 = new Date(dt);
		}

		diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
		diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

		var diff = diffDate_2.getTime() - diffDate_1.getTime();
		diff = Math.ceil(diff / (1000 * 3600 * 24));

		return diff;
	}

	/**
	 * yyyy-mm-dd 형식 반환
	 */
	_date.formatDate = function(date) {
		var d = new Date(date),
		month = '' + (d.getMonth() + 1),
		day = '' + d.getDate(),
		year = d.getFullYear();

		if (month.length < 2) month = '0' + month;
		if (day.length < 2) day = '0' + day;

		return [year, month, day].join('-');
	}

	/**
	 * 날짜 기간 설정 : 어제(통계용/당일 미포함)
	 */
	_date.statDays = function(e){
		var _Today = new Date();
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		_Today.setDate(_Today.getDate() - 1);

		$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
		$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
	}

	/**
	 * 날짜 기간 설정 : 1주일(통계용/당일 미포함)
	 */
	_date.statWeek = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();
		_Today.setDate(_Today.getDate() - 1);

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setDate(_Today.getDate() - 6);
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setDate(inputDate.getDate() - 6);
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 날짜 기간 설정 : 한달(통계용/당일 미포함)
	 */
	_date.statMonth = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();
		_Today.setDate(_Today.getDate() - 1);

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setMonth(_Today.getMonth() - 1);
			_Today.setDate(_Today.getDate() + 1);
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setMonth(inputDate.getMonth() - 1);
			inputDate.setDate(inputDate.getDate() + 1);
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 날짜 기간 설정 : 1년(통계용/당일 미포함)
	 */
	_date.statYear = function(e){
		var _Today = new Date();
		var inputDate;
		var fromDateId = $(e).parent().parent().find('input').eq(0).attr('id');
		var toDateId = $(e).parent().parent().find('input').eq(1).attr('id');
		var toDateVal = $("#"+toDateId).val();
		_Today.setDate(_Today.getDate() - 1);

		if(_text.allNull(toDateVal)){
			$("#"+toDateId).val($.datepicker.formatDate($("#"+toDateId).datepicker("option", "dateFormat"), _Today));
			_Today.setMonth(_Today.getMonth() - 1);
			_Today.setDate(_Today.getDate() + 1);
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), _Today));
		}else{
			_text.validateStringSignRemove(toDateVal);
			inputDate = _date.changeDate(toDateVal);
			inputDate.setYear(inputDate.getFullYear() - 1);
			inputDate.setDate(inputDate.getDate() + 1);	//1년 기준
			$("#"+fromDateId).val($.datepicker.formatDate($("#"+fromDateId).datepicker("option", "dateFormat"), inputDate));
		}
	}

	/**
	 * 끝 날짜 설정 : 오늘이면 오늘까지 어제면 어제까지 설정(통계용)
	 */
	_date.setMaxDate = function($div) {
		$div.each(function(i, v) {
			var date = new Date();
			$(this).find('.btn-group a').each(function(i,v) {
				switch (v.text) {
				case '오늘':
					break;
				case '어제':
					date.setDate(date.getDate()-1);
					break;
				default:
					break;
				}
			});
			$(this).find('input:last').datepicker("option", "maxDate",  date);
		});
	}




	/**
	 * 쿠키
	 */
	var _cookie = _object.createNestedObject(window,"abc.cookie");

	_cookie.setCookie = function(name, value, expiredays) {
		var todayDate = new Date();
		todayDate = new Date(parseInt(todayDate.getTime() / 86400000) * 86400000 + 54000000);

	    if (todayDate > new Date()) {
	    	expiredays = expiredays - 1;
	    }

	    todayDate.setDate(todayDate.getDate() + expiredays);
	    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    }

	_cookie.getCookie = function(name) {
		var nameOfCookie = name + "=";
		var x = 0;
		while (x <= document.cookie.length) {
			var y = (x+nameOfCookie.length);

			if (document.cookie.substring(x, y) == nameOfCookie) {
				if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1) {
					endOfCookie = document.cookie.length;
				}
				return unescape(document.cookie.substring( y, endOfCookie));
			}
			x = document.cookie.indexOf(" ", x) + 1;

			if (x == 0) {
				break;
			}
		}
		return "";
	}

})();