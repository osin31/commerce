(function($) {

    /**
     * ajax 설정
     */

    //ajax 통신시 cache가 남아 있어 데이터가 갱신되지 않을 경우 방지.
    //데이터 직렬화 옵션. 특정 key에 해당 하는 값이 array에 담아 서버에 전송할 경우. 자동으로 직렬화 해줌.
    /* ex) var params = {
			sample = ["a","b","c","d"],
			sample2 = "값"
		};

		결과 : sample=a&sample=b&sample=c&sample2=값
	*/

    $.ajaxSetup({
        type: "POST", //기본 POST
        cache: false,
        traditional: true
    });

    var _ajax = $.ajax;

    $.ajax = function(p) {

    	if(p.contentType == "application/json"){
    		p.traditional = false;
    		p.data = JSON.stringify(p.data);
    	}

        var _error = null;
        if ($.type(p.error) == "function") { //error 함수 처리
            _error = p.error;
        };

        p.error = function(jqXHR, textStatus, errorThrown) {
        	if (jqXHR.status == 401 && !(jqXHR.responseJSON.message == 'dupLoginId' || jqXHR.responseJSON.message == '10007')) {
        		if(!abc.text.allNull(jqXHR.responseJSON.message)){
        			alert(jqXHR.responseJSON.message);
        		}
        		$(location).attr('href', "/");
        		return;
        	}

        	var m = null;
            try {
                m = $.parseJSON(jqXHR.responseText);
            } catch (e) {
                m = {};
            };

            if (_error == null) {
                if ($.type(m.error_message) == "string") {
                    alert(m.error_message);
                };
            } else {
                m["status"] = jqXHR.status;
                m["statusText"] = jqXHR.statusText;
                m["status"] = jqXHR.status;
                _error(m);
            };

        };

        return _ajax(p);
    };

    // ajax global event processing image
    $(document).ajaxStart(function() {
    	abc.namespace.front.backOffice.showLoader();
    }).ajaxStop(function() {
    	abc.namespace.front.backOffice.hideLoader();
    });

	$.paramObject = function(object){

		var setParameterName = function(upperParameterName, parameterName){
			var combineName = "";
			if(parameterName == null || parameterName == ""){
				combineName = upperParameterName;
			}else if(upperParameterName != null && upperParameterName !== ""){
				combineName = upperParameterName+"."+parameterName;
			}else{
				combineName = parameterName;
			}
			return combineName;
		};


		var setParamArray = function(upperParameterName, parameterName, paramObject, tempObject, isArray){
			var tempObjectValue = paramObject[setParameterName(upperParameterName, parameterName)];

			console.log(upperParameterName, parameterName, paramObject, tempObject, isArray)
			if(!$.isArray(tempObjectValue)){
				tempObjectValue = paramObject[setParameterName(upperParameterName, parameterName)] = [];
				tempObjectValue.push(tempObject);
			}else{
				tempObjectValue.push(tempObject);
			}
		};

		var paramObject = {};

		var parse = function(upperParameterName, p, isArray) {

			if($.isPlainObject(p)) {

				for (var parameterName in p) {

					var tempObject = p[parameterName];
					var combineParameterName = setParameterName(upperParameterName, parameterName);

					if($.isArray(tempObject)) {
						parse(combineParameterName,  tempObject, true);
					}else{
						if($.isPlainObject(tempObject)){
							parse(parameterName, tempObject, false);
						}else{
							if(isArray){
								setParamArray(upperParameterName,parameterName,paramObject,tempObject,isArray);
							}else{
								paramObject[combineParameterName] = tempObject;
							}
						}
					}
				}

			}else if($.isArray(p)){

				var length = p.length;
				for (var i = 0; i < length; i++) {

					var tempArray = p[i];

					if($.isPlainObject(tempArray)) {

						if(upperParameterName != null && upperParameterName != ""){

							var upperParameterNameArr = upperParameterName.split(".");
							if(upperParameterName.length > 2){
								upperParameterName = upperParameterNameArr[upperParameterNameArr.length-1];
								console.log(upperParameterNameArr,upperParameterName)
							}
						}
						parse(upperParameterName, tempArray, true);
					}else{
						setParamArray(upperParameterName,parameterName,paramObject,tempArray,isArray);
					}
				}
			}
		};

		parse(null, object);
		return paramObject;
	};

    /**
     * 폼 객체 제정의
     */
	$.form = function(selector, context) {

		var _org = $(selector, context);

		var _f = {};


		//onsubmit시 자동으로 submit되는것 막음.
		$(selector).on("submit", function() {
			return false;
		});

		_f.submit = function(p){

			if (p.valid != null && !p.valid(_org)) {
				return false;
			};

			$(_org).ajaxSubmit(p);
		};


		/**
		 * 폼 내용 전체를 기본 값으로 초기화 한다..
		 *
		 * $('#myFormId').reset();
		 */
		_f.reset = function(p){
			_org.resetForm();
		};


		/**
		 * 폼 내용 전체를 초기화 한다.
		 *
		 * $('#myFormId').clearFields();
		 */
		_f.clear = function(p){
			_org.clearForm();
		};


		/**
		 * 폼 양식의 일부만 지울 때 사용
		 * myFormId 폼 안의 specialFields 클래스를 가지고 있는 field 초기화
		 *
		 * $('#myFormId .specialFields').clearFields();
		 */
		_f.clearFields = function(p){
			_org.clearFields();
		};

		return _f;
	}


    if (!String.prototype.startsWith) {
        String.prototype.startsWith = function(search, pos) {
            return this.substr(!pos || pos < 0 ? 0 : +pos, search.length) === search;
        };
    }


    if (!String.prototype.endsWith) {
        String.prototype.endsWith = function(searchString, position) {
            var subjectString = this.toString();
            if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
                position = subjectString.length;
            }
            position -= searchString.length;
            var lastIndex = subjectString.indexOf(searchString, position);
            return lastIndex !== -1 && lastIndex === position;
        };
    }


    if (!String.prototype.trim) {
        String.prototype.trim = function() {
            return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
        };
    }


    if (!String.prototype.format) {
        String.prototype.format = function() {
            var args = arguments;
            return this.replace(/{(\d+)}/g, function(match, number) {
                return typeof args[number] != 'undefined' ? args[number] : match;
            });
        };
    }

    String.prototype.replaceAll = function(from, to) {
        return this.replace(new RegExp(from, "g"), to);
    };

    String.prototype.string = function(len) {
        var s = '',
            i = 0;
        while (i++ < len) {
            s += this;
        }
        return s;
    };

    String.prototype.zf = function(len) {
        return "0".string(len - this.length) + this;
    };

    String.prototype.formatter = function(value, format) {
        var fs = format.split("");
        var r = "";
        var i = 0;
        $.each(fs, function(x, c) {
            if (fs[x] == "#") {
                if ($.type(value[i]) != "undefined") {
                    r += value[i];
                };
                i++;
            } else {
                r += fs[x];
            };
        });
        if (i != value.length) {
            r += value.substring(i);
        };
        return r;
    };

    /**
     * yyyyMMddhhmmss 형식의 문자열을 yyyy.MM.dd hh:mm:ss 형식으로 변환한다.
     */
    String.prototype.displayDateFormat = function() {
    	return this.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1.$2.$3 $4:$5:$6');
    }

    /*
     * 한글은 2byte 처리한 길이를 리턴한다.
     */
    String.prototype.uniLength = function() {
    	var strByteLength = 0;
    	strByteLength = (function(s,b,i,c){
			for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?2:c>>7?2:1);
			return b;
		})(this);

    	return strByteLength;
    }

    /***
     * 날짜 형식을 지정한다.
     *
     * @param t 날짜 형식
     */
    Date.prototype.format = function(f) {
        if (!this.valueOf()) return " ";
        f = f || abc.consts.DEFAULT_DATETIME_PATTERN;

        var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
        var d = this;

        return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
            switch ($1) {
                case "yyyy":
                    return d.getFullYear();
                case "yy":
                    return (d.getFullYear() % 1000).zf(2);
                case "MM":
                    return (d.getMonth() + 1).zf(2);
                case "dd":
                    return d.getDate().zf(2);
                case "E":
                    return weekName[d.getDay()];
                case "HH":
                    return d.getHours().zf(2);
                case "hh":
                    return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case "mm":
                    return d.getMinutes().zf(2);
                case "ss":
                    return d.getSeconds().zf(2);
                case "a/p":
                    return d.getHours() < 12 ? "오전" : "오후";
                default:
                    return $1;
            }
        });
    };


    Number.prototype.zf = function(len) {
        return this.toString().zf(len);
    };


    /**
     * 숫자 형식을 지정 한다.
     *
     * format( "#,##0.####", 1234567.890 );  // output: "1,234,567.89"
     * format( "$ #,###.00", -1234567.890 ); // output: "$ -1,234,567.89"
     */
    Number.prototype.format = function(pattern) {
        return format(pattern, this);
    };

})(jQuery);