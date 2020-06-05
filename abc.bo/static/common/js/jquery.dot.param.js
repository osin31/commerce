/***
 * jQuery.param 함수를 재정의.
 * json 객체의 키 value를 서버에서 인식 할 수 있게 변경 한다.
 * 
 * 
 * ex) json object 에서 서버로 보내는 key-value 객체로 변환.
 * 
 * 1) json 객체에 담겨진 파라메터 정보.
 * var param = {
 * 		timestampCus: 2019-03-21 03:14:51,
 * 		sample2 : {
 * 			sample3 : {
 * 				sample3Value1 : "stringSample3Value1",
 * 				sample3Value3 :["stringArrayValue3","stringArrayValue3-1"],
 * 				sample4 : {
 * 					sample3Value1 : "depth4-stringSample4Value1",
 * 					sample3Value3 : ["depth4-stringArrayValue3", "depth4-stringArrayValue3-1"]
 * 				}
 * 			}
 * 		}
 * 		arraySample2 :[
 * 
 * 			{
 * 				sample3 : {
 * 					sample4: {
 * 						sample3Value1 : "arraySample2[0]-depth4-stringSample4Value1",
 * 						sample3Value3 : ["arraySample2[0]-depth4-stringArrayValue3", "arraySample2[0]-depth4-stringArrayValue3-1"]		
 * 					}
 * 				}
 * 			}
 * 		]
 * };
 * 
 * 2) json에 담긴 파라메터 정보를 서버에 .(dot) 형식의 key와 value로 전달.
 * 
 * timestampCus: 2019-03-21 03:14:51
 * sample2.sample3.sample3Value1: stringSample3Value1
 * sample2.sample3.sample3Value3[0]: stringArrayValue3
 * sample2.sample3.sample3Value3[1]: stringArrayValue3-1
 * sample2.sample3.sample4.sample3Value1: depth4-stringSample4Value1
 * sample2.sample3.sample4.sample3Value3[0]: depth4-stringArrayValue3
 * sample2.sample3.sample4.sample3Value3[1]: depth4-stringArrayValue3-1
 * arraySample2[0].sample3.sample4.sample3Value1: arraySample2[0]-depth4-stringSample4Value1
 * arraySample2[0].sample3.sample4.sample3Value3[0]: arraySample2[0]-depth4-stringArrayValue3
 * arraySample2[0].sample3.sample4.sample3Value3[1]: arraySample2[0]-depth4-stringArrayValue3-1
 * 
 * @author 장진철 (zerocooldog@zen9.co.kr)
 * @see https://gist.github.com/migimunz/61557b7fab233604ba46 <- 출처
 */
(function() {
	
	var r20 = /%20/g,
	rbracket = /\[\]$/,
	rCRLF = /\r?\n/g,
	rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
	rsubmittable = /^(?:input|select|textarea|keygen)/i,
	rIdentifier = /^[$A-Z_][0-9A-Z_$]*$/i;
	
	var param = function( a, traditional, dotNotation, targetObject ) {
		
		if((a instanceof FormData)){
			return a;
		}
		
		//파라메터 값을 저장할 객체가 FormData인지 판단한다.
		var isFormData = (targetObject instanceof FormData);
		
		var prefix,
			s = targetObject,
			add = function( key, value ) {
				// If value is a function, invoke it and return its value
				//FormData일 경우 객체값 그대로 집어 넣는다.
				if(isFormData){
					s.append(key, value)
				} else {
					value = jQuery.isFunction( value ) ? value() : ( value == null ? "" : value );
					s[ s.length ] = encodeURIComponent( key ) + "=" + encodeURIComponent( value );				
				}

			};

		// Set traditional to true for jQuery <= 1.3.2 behavior.
		if ( traditional === undefined ) {
			traditional = jQuery.ajaxSettings && jQuery.ajaxSettings.traditional;
		}
		if ( dotNotation === undefined ) {
//	 		dotNotation = jQuery.ajaxSettings && jQuery.ajaxSettings.dotNotation;
			dotNotation = true;
		}

		// If an array was passed in, assume that it is an array of form elements.
		if ( jQuery.isArray( a ) || ( a.jquery && !jQuery.isPlainObject( a ) ) ) {
			// Serialize the form elements
			jQuery.each( a, function() {
				add( this.name, this.value );
			});

		} else {
			// If traditional, encode the "old" way (the way 1.3.2 or older
			// did it), otherwise encode params recursively.
			for ( prefix in a ) {
				buildParams( prefix, a[ prefix ], traditional, dotNotation, add );
			}
		}

		// Return the resulting serialization
		return (isFormData) ? s : s.join( "&" ).replace( r20, "+" );
	};
	
	/**
	 * 일반 문자열 파라메터 처리.
	 * 4번째 매개변수에 배열을 넣어준다.
	 */
	jQuery.param = function( a, traditional, dotNotation ) {
		return param( a, traditional, dotNotation, []);
	}

	/**
	 * FormData 파라메터 처리.
	 * 4번째 매개변수에 FormData를 넣어준다.
	 */
	jQuery.paramFiles = function( a, traditional, dotNotation ) {
		
        if(!window.File || !window.FormData){
            console.error("File 또는 FormFile 객체를 지원하지 않아 해당 함수를 사용할 수 없습니다.");
            return;
        }
        
		return param( a, traditional, dotNotation, new FormData());
	};

	function buildParams( prefix, obj, traditional, dotNotation, add ) {
		var name;

		if ( jQuery.isArray( obj ) ) {
			// Serialize array item.
			jQuery.each( obj, function( i, v ) {
				if ( traditional || rbracket.test( prefix ) ) {
					// Treat each array item as a scalar.
					add( prefix, v );

				} else {
					// Item is non-scalar (array or object), encode its numeric index.
					// buildParams( prefix + "[" + ( typeof v === "object" ? i : "" ) + "]", v, traditional, dotNotation, add ); //old 
					// [] 처리 없이 [0] 처럼 index번호를 부여한다.
					buildParams( prefix + "[" + (i) + "]", v, traditional, dotNotation, add );
				}
			});

		} else if ( !traditional && jQuery.type( obj ) === "object" && !(obj instanceof File) ) {
			// Serialize object item.
			for ( name in obj ) {
				if( dotNotation && rIdentifier.test(name) ) {
					buildParams( prefix + '.' + name, obj[name], traditional, dotNotation, add )
				} else {
					buildParams( prefix + "[" + name + "]", obj[ name ], traditional, dotNotation, add );
				}
			}

		} else {
			// Serialize scalar item.
			add( prefix, obj );
		}
	}
	
})();