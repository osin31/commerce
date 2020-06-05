var _object = abc.object.createNestedObject(window,"abc.biz.product.utils");

_object.initFormData = new function() {
	/**
	 * form data 초기화
	 */

    var data = {
            rootElementId : null,
            isClearInputHiddenType : false,  // input태그 type속성 값이 hidden인 경우 초기화 여부 설정.
            doNotChecked : null  // group 형식 input태그에서 첫번째 요소를 체크하지 않아야 하는 태그 name값을 배열로 입력
    }

    var property = {
            exceptionInputTypes : ".button.reset.submit.",  // input 태그 초기화 예외 type
            inputGroupTypes : ".checkbox.radio.",    // input 태그 그룹화 type
            parentTagInputGroupType : "td"    // input 그룹형태 태그들이 위치한 부모 태그 이름
    }

    var option = {
            isClearInputHiddenType : false  // input태그 type속성 값이 hidden인 경우 초기화 여부 설정.
    }

    field = function(){
         /**
         *
         * 매개변수
         * - (필수) string 또는 number형 값. 검색영역 태그ID
         * - (선택) boolean형 값. hidden타입 초기화 여부. 기본값은 false.
         * - (선택) object(array)형 값. 배열객체값은 체크/라디오 타입 중 초기항목을 선택해야 하는 경우 해당태그 name을 배열값으로 입력. 기본값은 null
         *
         * 사용예
         * - field("search"); // 태그 ID가 "search"인 노드 하위영역 값을 초기화함
         * - field("search", true); // 태그 ID가 "search"인 노드 하위영역을 초기화하며 <input type="hidden"/>도 값을 초기화함
         * - field("search", ["field1", "field2"]); // 태그 ID가 "search"인 노드 하위영역을 초기화하며 태그이름이 "field1", "field2"인 노드는 첫번째 노드값을 선택함
         * - field("search", true, ["field1", "field2"]);
         */
        if(arguments.length !== 0) {
            for(var i=0 ; i<arguments.length ; i++) {
                switch(typeof arguments[i]) {
                    case "string" : data.rootElementId = arguments[i]; break;
                    case "number" : data.rootElementId = arguments[i]; break;
                    case "boolean" : data.isClearInputHiddenType = arguments[i]; break;
                    case "object" : data.doNotChecked = arguments[i]; break;
                    default : console.log("unsupported argument. (type : " + typeof arguments[i] + ", data : " + arguments[i]);
                }
            }
        }

        if(data.rootElementId != null && data.rootElementId !== undefined) {
            var root = $("#" + data.rootElementId);

            if(root.length > 0) {
                traverse(root);
            } else {
                console.log("검색영역 root 요소를 찾을 수 없습니다.");
            }

        } else {
            console.log("검색 영역 태그 ID가 없습니다.");
        }

        return "cleared search area form.";
    }

    var traverse = function(parent){
        /**
         * 트리 탐색을 수행합니다.
         */
        var child = parent.children();

        if(child.length !== 0) {
            for(var i=0 ; i<child.length ; i++) {
                traverse(child.eq(i));
            }
        } else {
        }

        run(parent);
    }

    var run = function(node){
         /**
         * 처리작업을 수행합니다.
         */
        var tagName = node.prop("tagName").toLowerCase();

        if(tagName === "input") {
            checkInputTag(node);
        } else if(tagName === "select") {
            checkSelectTag(node);
        }
    }

    var checkInputGroupType = function(node, inputType){
        /**
         * 그룹형태 태그는 상위태그가 <td/>에 포함된 형태로 존재해야 함. 아래는 그것에 대응하도록 작업중..
         */
        var inputGroupTypeNodeWrapper = node.closest(property.parentTagInputGroupType);
        if(inputGroupTypeNodeWrapper.length > 0) {
            inputGroupTypeNodeWrapper.find("input[type=" + inputType + "]").prop("checked", false);
            inputGroupTypeNodeWrapper.find("input[type=" + inputType + "]:first").prop("checked", true);
        } else {
            console.log("there is no <ul> tag.");
        }
    }

    var checkInputTag = function(node){
        var inputType = node.attr("type").toLowerCase();
        var notReadonly = !(node.attr("readonly") === "readonly" || node.attr("disabled") === "disabled");

        if(notReadonly) {

            if(property.exceptionInputTypes.indexOf(inputType) > -1) {
                // 예외 type은 초기화 하지 않음
                // console.log("예외 input type 입니다. (" + inputType + ")");
            } else if(property.inputGroupTypes.indexOf(inputType) > -1) {
                // 그룹 type은 첫번쨰 항목 선택
                checkInputGroupType(node, inputType);
                // console.log("그룹 input type 입니다. (" + inputType + ")");
            } else {
                node.val("");
                // console.log("초기화 대상 input type 입니다. (" + inputType + ")");
            }
        } else {
            // console.log("읽기전용 input type 입니다. (" + inputType + ")");
        }
    }

    var checkSelectTag = function(node){
        node.prop("selectedIndex", 0);
    }

    return field;
}

_object.image = {
	noImageUrl : "/static/images/common/no_image.png",	// 이미지 없음 URL
	noImage : function(element) {
		/**
		 * img 태그에 이미지 없음 URL 설정
		 *
		 * element : 이미지 태그
		 */
		element.src = _object.image.noImageUrl;
	},
	getNoImageUrl : function() {
		/**
		 * 상품 이미지 없음 URL 반환
		 */
		return _object.image.noImageUrl;
	}
}

_object.processImage = function(option){
	/**
	 * 이미지 처리
	 */

	var options;
	var limitSize = 10485760;	// 파일크기제한. 10MB.

	init = function(option){

		options = $.extend({
			file		: "",
			name	: "",
			type	: "I",
			allow: ''
        }, option);

		if(options.allow == ''){
			options.allow = $(options.file).closest('td').prev().text().trim();
		}

		var fileWrap = $(options.file).parents(".file-wrap");

		options.delButton = fileWrap.find(".btn-file-del");
		options.previewArea = fileWrap.find(".img-wrap");
		options.nameText = fileWrap.find(".subject");

		if(options.nameText.text() === "") {
			options.nameText.hide();
			options.delButton.hide();
		}

		uploadImage();
		removeImage();
	}

	var removeImage = function(){
		$(options.delButton).on("click", function(e){
			$(options.previewArea).html("");
			$(options.nameText).text("");
			$(options.file).val("");
			$(options.delButton).hide();
			$(options.name).val("");
		});
	}

	var uploadImage = function(){
		$(options.file).on("change", function(e){

			var img = new Image();
			var reader = new FileReader();
			if(this.files && this.files[0]){
				var file = this.files[0];

				var reader = new FileReader();
				reader.onload = function(e){

					if(options.type === "I") {
						if(abc.text.confirmImgFileExtension(file.name.toLocaleLowerCase()) ){
							alert('허용되지 않는 파일입니다.');
							$(options.nameText).text('');
							$(options.name).val('');
							$(options.delButton).hide();
							$(options.previewArea).children('img').remove();
							return false;
						}

						if(e.total > limitSize){
							alert("최대 10MB까지 등록 가능 합니다.");
							$(options.nameText).text("");
							$(options.name).val("");
							$(options.delButton).hide();
							$(options.previewArea).children("img").remove();
							return false;
						}
					}

					$(img).attr("src", e.target.result);
					$(options.previewArea).children("img").remove();
					$(options.previewArea).append(img);
				}

				reader.readAsDataURL(this.files[0]);
				$(options.nameText).text(this.files[0].name);
				$(options.name).val(this.files[0].name);
				$(options.nameText).show();
				$(options.delButton).show();
			}
		});
	}

	init(option);
}

_object.selectStandardCategory = function(stdCtgrNo) {
	/**
	 * 표준 카테고리(소)를 이용하여 표준 카테고리 데이터를 설정
	 */

	if(_object.isNotEmpty(stdCtgrNo)) {
		// 기존 표준카테고리가 있는 경우

		$.ajax({
			dataType	: "json",
			type		: "POST",
			url			: "/noacl/product/category-standard/list/map",
			data		: { "stdCtgrNo" : stdCtgrNo, "vndrNo" : $('#vndr-no').val() }
		}).done(function(data){
			pathArr = data.pathArr;
			for (var i = 0; i < pathArr.length; i++) {
				cls = i + 1 + "depth";
				var str = cls == "1depth" ? "대" : cls == "2depth" ? "중" : "소";
				$(".stdCtgrSel."+cls).html($("<option>").text(str + "카테고리 선택"));
				$.each(data[cls], function(idx, val) {
					slt = pathArr[i] == val.stdCtgrNo ? true : false;
					var elementOption = $("<option>").attr({ "data-item-code" : val.itemCode, "value" : val.stdCtgrNo, "selected" : slt }).text(val.stdCtgrName);
					$(".stdCtgrSel."+cls).append(elementOption);
				});
			}
			abc.biz.product.product.detail.precaution.init();	// 상품정보고시 소재값 설정
		}).fail(function(e){
			alert(e.responseJSON.message);
			console.log(e);
		});

		// 표준카테고리 조회 없이 이벤트만 설정
		abc.biz.display.common.setStdCategoryEvent({ initWithoutList : true, callback : abc.biz.product.product.detail.noticeInfo.callback.initByStdCtgrChange });

	} else {
		// 기존 표준카테고리가 없는 경우

		// 표준카테고리 조회 및 이벤트 설정
		abc.biz.display.common.setStdCategoryEvent({ callback : abc.biz.product.product.detail.noticeInfo.callback.initByStdCtgrChange });
	}
}

_object.isEmpty = function(data) {
	/**
	 * 객체가 비었는지 확인
	 *
	 * data : 요소
	 */
	var result = false;
	if(typeof data === "undefined" || data == null || data == "" || data.length == 0) {
		result = true;
	}
	return result;
}

_object.isNotEmpty = function(data) {
	/**
	 * 객체가 비어있지 않은지 확인
	 *
	 * data : 요소
	 */
	return !_object.isEmpty(data);
}

_object.setTextIfSourceChecked = function(sourceSelector, targetSelector, trueValue, falseValue) {
	/**
	 * sourceSelector 선택여부에 따라 targetSelector에 값을 설정
	 * sourceSelector가 체크된 경우 trueValue
	 * sourceSelector가 체크되지 않은 경우 falseValue
	 *
	 * sourceSelector : 소스 선택자
	 * targetSelector : 대상 선택자
	 * trueValue : 참값
	 * falseValue : 거짓값
	 */
	var selector = "#" + sourceSelector;
	var checked = $(selector).is(":checked");
	var value = null;

	if(checked) {
		value = trueValue;
	} else if(abc.biz.product.utils.isNotEmpty(falseValue)) {
		value = falseValue;
	}

	$("#" + targetSelector).val(value);
	console.log("대상을 확인하여 값을 설정하였습니다. (소스선택자 : " + sourceSelector + ", 대상선택자 : " + targetSelector + ", 설정값 : " + value);
	console.log("설정 데이터 확인 : " + $("#" + targetSelector).val());
	return;
}

_object.setTextIfSourceCheckedElseDoNothing = function(sourceSelector, targetSelector, trueValue) {
	var selector = "#" + sourceSelector;
	var checked = $(selector).is(":checked");
	var value = null;

	if(checked) {
		value = trueValue;
	}

	if(abc.biz.product.utils.isNotEmpty(value)) {
		$("#" + targetSelector).val(value);
		console.log("대상을 확인하여 값을 설정하였습니다. (소스선택자 : " + sourceSelector + ", 대상선택자 : " + targetSelector + ", 설정값 : " + value);
		console.log("설정 데이터 확인 : " + $("#" + targetSelector).val());
	} else {
		console.log("대상이 선택되지 않았습니다. (소스선택자 : " + sourceSelector + ")");
	}
	return;
}

_object.setYnIfSourceChecked = function(sourceSelector, targetSelector) {
	/**
	 * sourceSelector 선택여부에 따라 targetSelector에 값을 설정
	 * sourceSelector가 체크된 경우 "Y"
	 * sourceSelector가 체크되지 않은 경우 "N"
	 *
	 * sourceSelector : 소스 선택자
	 * targetSelector : 대상 선택자
	 */
	return _object.setTextIfSourceChecked(sourceSelector, targetSelector, "Y", "N");
}

_object.setNyIfSourceChecked = function(sourceSelector, targetSelector) {
	/**
	 * sourceSelector 선택여부에 따라 targetSelector에 값을 설정
	 * sourceSelector가 체크된 경우 "N"
	 * sourceSelector가 체크되지 않은 경우 "Y"
	 *
	 * sourceSelector : 소스 선택자
	 * targetSelector : 대상 선택자
	 */
	return _object.setTextIfSourceChecked(sourceSelector, targetSelector, "N", "Y");
}

_object.setZeroIfSourceChecked = function(sourceSelector, targetSelector) {
	/**
	 * sourceSelector 선택여부에 따라 targetSelector에 값을 설정
	 * sourceSelector가 체크된 경우 "0"
	 * sourceSelector가 체크되지 않은 경우 ""
	 *
	 * sourceSelector : 소스 선택자
	 * targetSelector : 대상 선택자
	 */
	return _object.setTextIfSourceCheckedElseDoNothing(sourceSelector, targetSelector, "0");
}

_object.convertDate = function(dateId, hourId, minuteId, isStart) {
	/**
	 * 날짜와 시간, 분 정보가 분리된 형태로 입력될 경우, 해당 데이터를 조합하여 날짜형식으로 반환
	 * ex) convertDate("date-start", "hour-start", "minute-start", true); // 시작일자 생성
	 * ex) convertDate("date-end", "hour-end", "minute-end", false); // 종료일자 생성
	 *
	 * @param dateId : 날짜정보 태그 ID값
	 * @param hourId : 시간정보 태그 ID값
	 * @param minuteId : 분정보 태그 ID값
	 * @param isStart : 시작일자여부, milisecond 값에 영향
	 */
	var combinedString = null;
	var dateTimeDivider = " ";
	var timeDivider = ":";
	var date = $("#" + dateId).val();
	var hour = $("#" + dateId).val();
	var minute = $("#" + dateId).val();
	var second = null;
	var milisecond = null;

	if(isStart) {
		second = "00";
		milisecond = "000";
	} else {
		second = "59";
		milisecond = "999";
	}
	combinedString.concat(date, dateTimeDivider, hour, timeDivider, minute, timeDivider, second, timeDivider, milisecond);
	console.log("=====생성된 날짜 정보");
	console.log("시작일자여부 : " + isStart);
	console.log("날짜 : " + combinedString);
	console.log("==============");
	return new Date(combinedString);
}

_object.grid = {
	/**
	 * 그리드 관련 유틸
	 */
	util : {
		addRow : function(sheetName, rowData) {
			/**
			 * 행 추가
			 *
			 * sheetName : 시트명
			 * rowData : 기본입력정보
			 */
			var sheet = eval(sheetName);
			sheet.SetRowData(sheet.RowCount() + 1, rowData, { Add : 1 });
		},
		removeRows : function(sheetName) {
			/**
			 * 그리드에서 선택된 항목(컬럼명이 checked여야 함)을 삭제
			 *
			 * sheetName : 시트명
			 */
			var sheet = eval(sheetName);
			var checkedRowString = sheet.FindCheckedRow("checked");
			var rows = checkedRowString.split("|");
			if(rows.length > 0) {
				for(var i=rows.length ; i>0 ; i--) {
					// row 고유값을 기준으로 삭제하지 않으므로 역순으로 제거
					sheet.RowDelete(rows[i-1]);
				}
			}
		},
		removeAll : function(sheetName) {
			/**
			 * 그리드 모든 항목을 제거
			 *
			 * sheetName : 시트명
			 */
			var sheet = eval(sheetName);
			sheet.RemoveAll();
		},
		updateCellValue : function(sheetName, cellName, setValue) {
			/**
			 * 특정 cell 값 모두 변경
			 *
			 * sheetName : 시트명
			 * cellName : 대상셀이름
			 * setValue : 설정값
			 */
			var sheet = eval(sheetName);
			for(var i=1 ; i<sheet.RowCount()+1 ; i++) {
				console.log(sheetName + " 그리드에 대한 " + i + "번째 row " + cellName + " 값을 변경합니다. (" + sheet.GetCellValue(i, cellName) + " -> " + setValue + ")");
				sheet.SetCellValue(i, cellName, setValue);
				console.log("변경결과 : " + sheet.GetCellValue(i, cellName));
			}
		},
		decorateUnderline : function(sheetName, cellNames) {
			/**
			 * 배열로 전달된 cellName에 밑줄 설정
			 *
			 * sheetName : 시트명
			 * cellName : 대상셀이름 배열
			 */
			var sheet = eval(sheetName);
			for(var i=0 ; i<cellNames.length ; i++) {
				sheet.SetColFontUnderline(cellNames[i], 1);
			}
			console.log(sheetName + " 그리드에 대한 " + cellNames.join(", ") + "에 밑줄 설정 완료.");
		}
	},
	setUserInfo : function(list, row, targetFieldKey, fieldId, fieldName) {
		/**
		 * 사용자ID(사용자이름)패턴으로 설정
		 *
		 * list : 그리드ID
		 * row : 행번호
		 * targetFieldKey : 패턴 설정 헤더명
		 * fieldId : ID가 지정된 헤더명
		 * fieldName : 이름이 지정된 헤더명
		 */
		var rowData = {};

		// 등록자 정보 생성
		var id = list.GetRowData(row)[fieldId];
		var name = list.GetRowData(row)[fieldName];
		var userInfo = id + "(" + name + ")";
		rowData[targetFieldKey] = userInfo;

		if(_object.isNotEmpty(id)) {
			list.SetRowData(row, rowData);
		}
	},
	setUserIdWithName : function(type, list, row) {
		/**
		 * 사용자ID(사용자이름)패턴으로 설정
		 *
		 * type : 유형. 등록자 또는 수정자
		 * list : 그리드ID
		 * row : 행번호
		 */
		var rowData = {};

		switch(type) {
		case "rgster" :
			// 등록자 정보 생성
			var id = list.GetRowData(row).rgsterId;
			var name = list.GetRowData(row).rgsterName;
			var userInfo = id + "(" + name + ")";
			rowData = { rgsterInfo : userInfo };
			break;
		case "moder" :
			// 수정자 정보 생성
			var id = list.GetRowData(row).moderId;
			var name = list.GetRowData(row).moderName;
			var userInfo = id + "(" + name + ")";
			rowData = { moderInfo : userInfo };
			break;
		default :
			console.log("알 수 없는 유형입니다. " + type);
			return;
		}

		if(_object.isNotEmpty(id)) {
			list.SetRowData(row, rowData);
		}
	},
	setRgsterInfo : function(list, row) {
		/**
		 * "ID(NAME)" 패턴으로 데이터를 생성한 이후 "rgsterInfo"에 설정
		 *
		 * list : 그리드ID
		 * row : 행번호
		 */
		_object.grid.setUserIdWithName("rgster", list, row);
	},
	setModerInfo : function(list, row) {
		/**
		 * "ID(NAME)" 패턴으로 데이터를 생성한 이후 "moderInfo"에 설정
		 *
		 * list : 그리드ID
		 * row : 행번호
		 */
		abc.biz.product.utils.grid.setUserIdWithName("moder", list, row);
	},
	create : function(sheetObject, elementId, varName, width, height, pageCount, headerArray) {
		/**
		 * 그리드 생성
		 * ex) abc.biz.product.utils.grid.create(_object.sheet, "sheet-id", "list", "100%", "429px", null, [ { Header:"", Type:"Text", SaveName:"test" } ]);
		 *
		 * sheetObject : 시트객체
		 * elementId : 그리드가 생성될 태그 ID
		 * varName : 그리드 변수 명
		 * width : 그리드 너비
		 * height : 그리드 높이
		 * pageCount : 한 페이지에 보여 줄 row 갯수. null 사용 가능.
		 * headerArray : 헤더정보. 배열 내 json형태로 정의된 정보.
		 */
		var element = document.getElementById(elementId);

		if(_object.isNotEmpty(element)) {

			// ==============================
			// 시트 정보 생성 시작
			var initSheet = {};

			// 시트 환경설정. 페이징 카운트 유무에 따른 분기.
			if(_object.isNotEmpty(pageCount)) {
				initSheet.Cfg = {
					SearchMode	: smServerPaging2,
					Page		: pageCount,
					MaxSort		: 1
				};
			} else {
				initSheet.Cfg = {
					SearchMode	: smServerPaging2,
					MaxSort		: 1
				};
			}

			// initHeaders 메서드에서 설정한 옵션을 초기화 이후 변경하고자 할 때 사용
			initSheet.HeaderMode = {
				ColMove		: 0,	// 마우스 드래그를 이용한 컬럼 이동 허용 여부
				ColResize	: 1,	// 마우스 드래그르 이요한 컬럼 리사이즈 허용 여부
				HeaderCheck	: 1,	// 헤더 전체체크 사용 여부
				Sort		: 1		// 헤더 클릭을 통한 컬럼 정렬 옵션. 0:정렬사용안함, 1:정렬사용, 2:정렬아이콘만표시처리, 3:colSpan아닌헤더셀만정렬사용
			};

			initSheet.Cols = headerArray;	// 헤더 설정
			// 시트 정보 생성 끝
			// ==============================

			createIBSheet2(element, varName, width, height);	// 시트 생성
			IBS_InitSheet(eval(varName), initSheet);			// 시트 초기설정

			window[varName].SetCountPosition(3);					// 건수 정보가 출력되는 위치 설정. 0:사용안함, 1:좌측상단, 2:우측상단, 3:좌측하단, 4:우측하단
			window[varName].SetPagingPosition(2);					// 페이지 네비게이션 출력 위치 설정. 0:사용안함, 1:좌측, 2:우측
			window[varName].FitColWidth();						// 시트 너비에 맞게 각 컬럼 비율 재조정.
			window[varName].SetExtendLastCol(0);					// 마지막 컬럼 너비를 전체 너비에 맞게 자동으로 맞출 것인지 여부 설정. 0:설정안함, 1:설정

			// 리사이징 이벤트
			window[varName + "_OnSmartResize" ] = function(width, height) {
				window[varName].FitColWidth();
			};

			sheetObject[varName] = window[varName];
		}
		console.log("그리드 생성 완료. 그리드명 : " + varName);
	}
}

_object.editor = {
	/**
	 * 에디터 관련 유틸
	 */
	create : function(elementIdArray) {
		/**
		 * 에디터 초기설정 작업
		 * 예) abc.biz.product.utils.editor.create["a-editor", "b-editor"]
		 *
		 * elementIdArray : 에디터가 설정될 요소ID
		 */
		if(_object.isNotEmpty(elementIdArray)) {
			for(var i=0 ; i<elementIdArray.length ; i++) {
				if(_object.isNotEmpty(document.getElementById(elementIdArray[i]))) {
					CKEDITOR.replace(elementIdArray[i]);
				}
			}
			console.log("에디터 초기화 완료. " + elementIdArray);
		} else {
			console.log("ID정보가 없습니다. " + elementIdArray);
		}
	},
	createWithUpload : function(elementIdArray, uploadUrlArray) {
		/**
		 * 에디터 초기설정 작업
		 * 예) abc.biz.product.utils.editor.create["a-editor", "b-editor"]
		 *
		 * elementIdArray : 에디터가 설정될 요소ID
		 * uploadUrlArray : 에디터 이미지 첨부 URL 경로
		 */
		if(_object.isNotEmpty(elementIdArray)) {
			for(var i=0 ; i<elementIdArray.length ; i++) {
				if(_object.isNotEmpty(document.getElementById(elementIdArray[i]))) {
					CKEDITOR.replace(elementIdArray[i], { uploadUrl: uploadUrlArray[i] });
				}
			}
			console.log("에디터 초기화 완료. " + elementIdArray + "(이미지업로드URL : " + elementIdArray + ")");
		} else {
			console.log("ID정보가 없습니다. " + elementIdArray);
		}
	},
	setData : function(elementIdArray) {
		/**
		 * 에디터에 입력된 내용을 태그요소 값에 입력합니다.
		 * 예) abc.biz.product.utils.editor.setData(["a-editor", "b-editor"]);
		 *
		 * elementIdArray : 에디터가 설정될 요소ID 및 엘리먼트 ID (두 ID가 동일해야 함)
		 */
		if(_object.isNotEmpty(elementIdArray)) {
			for(var i=0 ; i<elementIdArray.length ; i++) {
				if(_object.isNotEmpty(document.getElementById(elementIdArray[i]))) {
					$("#" + elementIdArray[i]).text(CKEDITOR.instances[elementIdArray[i]].getData());
				}
			}
			console.log("에디터 데이터 설정 완료. " + elementIdArray);
		} else {
			console.log("ID정보가 없습니다. " + elementIdArray);
		}
	}
}

_object.number = {
	/**
	 * 숫자 관련 유틸
	 */
	comma : function(value) {
		/**
		 * 천단위 콤마
		 *
		 * value : 숫자값
		 */
		value = _object.number.getNumberOnly(value);
		return value.toString().replace(/,/gi, "").toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	},
	unit : {
		won : function(value) {
			/**
			 * 인자값 뒷부분에 "원" 단위를 추가
			 *
			 * value : 숫자값
			 */
			return (value.length === (value.lastIndexOf("원") + 1)) ? value : value + "원";
		}
	},
	getNumberOnly : function(value) {
		/**
		 * 인자값에서 숫자만 추출하여 반환
		 *
		 * value : 문자열
		 */
		var result = value.toString().replace(/[^0-9]/gi, "");
		if($.isNumeric(result)) {
			result = parseInt(result);
		} else {
			result = 0;
		}
		return result;
	},
	setDecoratePrice : function(value) {
		/**
		 * 인자값에 콤마와 원단위를 추가하여 반환
		 *
		 * value : 선택자
		 */
		return _object.number.unit.won(_object.number.comma(value));
	},
	setUndecoratePrice : function(value) {
		/**
		 * 인자값에 콤마와 원단위 제거하여 반환
		 *
		 * value : 선택자
		 */
		return _object.number.getNumberOnly(value);
	},
	setDecoratePriceBySelector : function(selector) {
		/**
		 * selector 대상 값에 콤마와 원단위를 추가
		 *
		 * selector : 선택자
		 */
		$(selector).val(_object.number.unit.won(_object.number.comma($(selector).val())));
	},
	setUndecoratePriceBySelector : function(selector) {
		/**
		 * selector 대상 값에 콤마와 원단위 제거
		 *
		 * selector : 선택자
		 */
		$(selector).val(_object.number.getNumberOnly($(selector).val()));
	},
	getDiscountPrice : function(price, discountRate) {
		/**
		 * 입력가격(price)에 할인율(discountRate)이 적용된 가격을 반환
		 *
		 * price : 가격
		 * discountRate : 할인율
		 */
		return Math.floor(price - (price * (discountRate * 0.01)));
	}
}

_object.parameter = {
	/**
	 * parameter 유틸
	 */
	getParameter : function(findParamName) {
		/**
		 * 파라미터 값을 반환
		 *
		 * findParamName : 파라미터 이름
		 */
		var queryString = window.location.search.slice(1);
		var queryArray = queryString.split("&");
		var result = null;

		for(var i=0 ; i<queryArray.length ; i++) {
			var query = queryArray[i].split("=");
			if(query.length === 2) {
				if(query[0] === findParamName) {
					result = query[1];
					break;
				}
			} else {
				console.log("파라미터 key/value 형태가 올바르지 않습니다. " + queryArray[i]);
			}
		}
		return result;
	},
	getParameters : function() {
		/**
		 * 모든 파라미터를 key/value 형태로 반환
		 */
		var result = {};

		var splitedData = window.location.search.slice(1).split("&");

		if(splitedData.length > 0) {

			for(var i=0 ; i<splitedData.length ; i++) {

				var parameter = splitedData[i].split("=");
				if(parameter.length === 2) {
					result[parameter[0]] = parameter[1];
				}
			}
		}

		return result;
	},
	getDecodedParameter : function(findParamName) {
		/**
		 * 디코딩된 파라미터 값을 반환
		 *
		 * findParamName : 파라미터 이름
		 */
		return decodeURI(_object.parameter.getParameter(findParamName));
	},
	getCallback : function(callbackParameterName) {
		/**
		 * URL에서 callback 인자값을 찾아 반환
		 *
		 * callbackParameterName : 콜백 파라미터 이름
		 */
		return _object.parameter.getParameter(callbackParameterName);
	},
	runCallback : function(callbackParameterName) {
		/**
		 * URL에서 callback 인자값을 찾아 수행
		 *
		 * callbackParameterName : 콜백 파라미터 이름
		 */
		var callback = _object.parameter.getCallback(callbackParameterName);
		var result = null;

		if(callback) {
			callback = "window.opener." + callback;
			result = new Function("return " + callback)();
		}
		return result;
	},
	isMultiple : function(multipleParameterName) {
		/**
		 * URL에서 다중선택 인자값을 찾아 true/false 반환
		 *
		 * multipleParameterName : 다중선택여부 파라미터 이름
		 */
		return _object.parameter.getParameter("isMultiple") === "true" ? true : false;
	}
}

_object.setSearchStartAndEndDate = function($fromDate,$toDate) {
	/**
	 * 기간 시작기간이 끝기간을 넘지않게 설정
	 *
	 * $fromDate : 시작일자
	 * $toDate : 종료일자
	 */
	// 시작기간 설정
//	$fromDate.datepicker("option", "maxDate", new Date());
	// 끝기간 설정
//	$toDate.datepicker("option", "minDate", $fromDate.datepicker( "option", "maxDate"));
//	$toDate.on("change", function() {
//		$(this).datepicker("option", "minDate", $.datepicker.parseDate("yy.mm.dd", $fromDate.val()));
//		$fromDate.datepicker("option", "maxDate", $.datepicker.parseDate("yy.mm.dd", $(this).val()));
//	});
}

_object.setPrdtCodeKeywordLimit = function($prdtCodeKeyword,limitCnt) {
	/**
	 * 상품코드 설정한 갯수까지 검색 제한
	 */
	// 따로 넘기지 않으면 100개 제한
	if(abc.text.allNull(limitCnt)){
		limitCnt = 100;
	}
	$prdtCodeKeyword.keyup(function (e) {
		var prdtCodeList = $(this).val();
		if(prdtCodeList) {
			if(limitCnt < prdtCodeList.split('\n').length) {
				alert(limitCnt + '개까지 가능합니다.');
				 $(this).val(prdtCodeList.split('\n').slice(0,limitCnt).join('\n'));
			}
		}
	});

}

_object.findDuplicateInSheet = function(sheet, sheetKey, data, dataKey, prdtNo) {
	/**
	 * 중복된 항목 찾기 / True: 중복, False: 중복아님
	 * prdtNo는 optional
	 */

	var isDuplicate = false;
	var sheetList = sheet.ExportData({ Type : "json" }).data;

	for(var i in sheetList) {
		var item = sheetList[i];

		if(item[sheetKey] == data[dataKey] || data[dataKey] == prdtNo) {
			isDuplicate = true;
			break;
		}
	}

	return isDuplicate;

}

_object.date = {
	convertDate : function(format, value) {
		/**
		 * format 유형으로 된 value문자열 날짜값을 Date객체로 변경
		 */
		var result = null;

		switch(format) {
		case "yyyy.mm.dd hh:mm:ss" :
			value = value.replace(" ", "T");	// ISO 규약으로 변경
			value = value.replace(".", "-");	// 일자구분자 변경
			value = value.concat("+09:00");	// 초, UTC시간차 설정
			result = new Date(value);
			break;
		case "yyyy.mm.dd hh:mm" :
			value = value.replace(" ", "T");	// ISO 규약으로 변경
			value = value.replace(/\./g, "-")	// 일자구분자 변경
			value = value.concat(":00+09:00");	// 초, UTC시간차 설정
			result = new Date(value);
			break;
		case "yyyy.mm.dd" :
			value = value.replace(/\./g, "-")	// 일자구분자 변경
			value = value.concat("T00:00:00+09:00");	// 초, UTC시간차 설정
			result = new Date(value);
			break;
		default :
			console.log("유형이 올바르지 않습니다. " + format);
		}

		return result;
	}
}