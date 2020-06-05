(function() {

	var _displayCommon = abc.object.createNestedObject(window,"abc.biz.display.common");

	_displayCommon.init = function(){
		_displayCommon.setTemplatePrototype();
	}

	/**
	 * 검색영역 날짜 이벤트
	 * startDate : 시작일시 영역 Selector
	 * endDate : 시작일시 영역 Selector
	 * btnGroup : 버튼 영역 Seelctor
	 */
	_displayCommon.searchFormCalendarBtnEvent = function(startDate, endDate, btnGroup){

		$(btnGroup).on('click', 'a', function(){

			var endDay = new Date();
			var startDay = new Date();
			var endYmd = endDay.format('yyyy.MM.dd');

			//오늘
			if($(this).hasClass('calendar-today')){
				$(endDate).val(endYmd);
				$(startDate).val(endYmd);
			}
			//어제
			else if($(this).hasClass('calendar-yesterday')){
				startDay.setDate(startDay.getDate()-1);

				$(endDate).val(endYmd);
				$(startDate).val(startDay.format('yyyy.MM.dd'));
			}
			//일주일
			else if($(this).hasClass('calendar-week')){

				startDay.setDate(startDay.getDate()-6);

				$(endDate).val(endYmd);
				$(startDate).val(startDay.format('yyyy.MM.dd'));
			}
			//한달
			else if($(this).hasClass('calendar-month')){
				startDay.setMonth(startDay.getMonth()-1);
				startDay.setDate(startDay.getDate() + 1);  //한달 시작일 +1 수정
				
				$(endDate).val(endYmd);
				$(startDate).val(startDay.format('yyyy.MM.dd'));
			}
			//1년
			else if($(this).hasClass('calendar-year')){
				startDay.setYear(startDay.getFullYear()-1);
				startDay.setDate(startDay.getDate() + 1);	//1년 시작일 +1 수정
				
				$(endDate).val(endYmd);
				$(startDate).val(startDay.format('yyyy.MM.dd'));
			}
			//전체
			else{
				$(endDate).val('');
				$(startDate).val('');
			}
		});

		_displayCommon.checkDateValid(startDate, endDate);

	}

	/**
	 * 기간 선택 유효성 체크 이벤트
	 * startData : String, 시작일 input selector
	 * endDate : String, 종료일 input selector
	 */
	_displayCommon.checkDateValid = function(startDate, endDate) {
		$(startDate+','+endDate).on('change', function() {
			if ($(startDate).val() != '' && $(endDate).val() != '') {

				var startDtm = new Date($(startDate).val().replaceAll('\\.', '/'));
				var endDtm = new Date($(endDate).val().replaceAll('\\.', '/'));
				var $this = $(this);

				if (startDtm > endDtm) {
					alert('종료일이 시작일을 앞설 수 없습니다.');
					$this.focus();
				}
			}
		});
	}

	/**
	 * 체크박스 전체 체크 이벤트
	 * allId : 전체 input id
	 * itemsClass : 그 외 item id
	 */
	_displayCommon.checkBoxAll = function(options){

		options = $.extend({
            allId: '#check-all-item',
            itemsClass: '.check-item'
        }, options);

		//화면진입시 전체 체크 여부 확인
		if($(options.allId).is(':checked')){
    		$(options.itemsClass).prop('checked', true);
		}

//		else{
//			$(options.itemsClass).prop('checked', false);
//		}

//		else if($(options.itemsClass+':checked').length == 0) {
//    		$(options.itemsClass).prop('checked', false);
//    	}

        $(options.allId).on('click', function (e) {

        	if($(this).is(':checked')){
        		$(options.itemsClass).prop('checked', true);
        	}else{
        		$(options.itemsClass).prop('checked', false);
        	}

        });

        $('body').on('click', function (e) {
		    options.itemsLength = $(options.itemsClass).length;

		    if ($(options.itemsClass).not(options.allId).length == options.itemsLength) {
		        $(options.itemsClass + ':checked').prop('checked', true);
		        $(options.allId).prop('checked', true);
		    }
		    if($(options.itemsClass + ':checked').not(options.allId).length != options.itemsLength){
		        $(options.allId).prop('checked', false);
		    }

        });
	}

	/**
	 *  템플릿 테그 초기화
	 */
	_displayCommon.setTemplatePrototype = function(){

		Document.prototype.templateOneRoot = function(id){

		    return $($(id).html().trim());
		};

		Document.prototype.templateSeveralRoot = function(id){

		    return $($(id).html());
		};

	}

	/**
	 * 표준 카테고리 select 선택 이벤트
	 */
	_displayCommon.setStdCategoryEvent = new function(){
		var options;
		var self = this;

		init = function(option){

			options = $.extend({
				area: '.stdCtgrArea',
				selectClass : '.stdCtgrSel',
				initWithoutList : false,
				initWithoutEvent : false,
				callback : function() { console.log("Standard Category default callback."); }
	        }, option);

			if(!options.initWithoutList) {
				self.getList($('.stdCtgrSel.1depth'), true);
			}
			if(!options.initWithoutEvent) {
				self.event();
			}
		};

		self.getList = function(selectEl , isFirst){

			var selectArea;
			var upStdCtgrNo = $(selectEl).val();
			var param = {};

			$(selectEl).nextAll('.stdCtgrSel').each(function(i,v){
				var str = $(this).hasClass('1depth') ? '대' : $(this).hasClass('2depth') ? '중' : '소';
				$(this).html('<option value="">'+str+'카테고리 선택</option>');
			});

			if(!isFirst && upStdCtgrNo == '') {
				return;
			}

			if(isFirst){
				param = {'level':1, 'vndrNo':$('#vndr-no').val()};
				selectArea = $(selectEl);
			}else{
				param = {'upStdCtgrNo':upStdCtgrNo};
				selectArea = $(selectEl).next();
			}

			$.ajax({
				type : 'post',
				url : '/noacl/product/category-standard/list/path',
				data : param
			}).done(function(data) {
				var txt = '';
				var str = selectArea.hasClass('1depth') ? '대' : selectArea.hasClass('2depth') ? '중' : '소';
				selectArea.html('<option value>'+str+'카테고리 선택</option>');
				$.each(data, function(i,v) {
					txt = '<option value="'+v.stdCtgrNo+'" data-item-code="' + v.itemCode + '">'+v.stdCtgrName+'</option>';
					selectArea.append(txt);
				});
			}).fail(function(e) {
				console.log("e :" + e);
			})
		}

		self.event = function(){
			$(options.selectClass).on('change', function() {

				if($(this).hasClass('3depth')) {
					options.callback($(this));
					return false;
				}

				self.getList(this);
			});
		}

		return init;
	};

	/**
	 * 전시카테고리 select 선택 이벤트
	 */
	_displayCommon.setDpCategoryEvent = new function(option) {
		var self = this;
		// BO 상품상세 페이지에서 표준카테고리에 매핑된 전시카테고리를 가져올 때 처음에는 태우지 않기 위한 flag
		var isFirst = true;

		init = function(option) {

			options = $.extend({
				area : '.dp-category-area',
				select : '.dp-category-select'
	        }, option);

			self.event();

			$('.dp-category-select.chnnl-no').trigger('change');
		};

		self.getList = function(selectEl, isChnnl) {
			var selected = $(selectEl).val();
			var param = {};

			$(selectEl).nextAll('.dp-category-select').each(function(i,v) {
				var str = $(this).hasClass('1depth') ? '대' : $(this).hasClass('2depth') ? '중' : $(this).hasClass('3depth') ? '소' : '세';
				$(this).html('<option value="">'+str+'카테고리 선택</option>');
			});

			if (selected == '') {

				if (!($(selectEl).prev().hasClass('chnnl-no'))) {

					$('.dp-category-select').attr('name', '');
					$(selectEl).prev().attr('name', 'ctgrNo');
				}
				return;
			}

			if (isChnnl) {
				if (! abc.text.isUndefined($("input[name='mmnyPrdtYn']").val())) {
//					if ("N" === $("input[name='mmnyPrdtYn']").val()) { // PO 상품 등록시 표카에 등록된 전시 카테고리만 노출 하기위해 추가
					var stdCtgrNo = $("select[name='stdCtgrNo']").val() != "" ? $("select[name='stdCtgrNo']").val() : $('#std-ctgr-no').val();
					param = { 'ctgrLevel' : 1, 'chnnlNo' : selected, 'mmnyPrdtYn' : $("input[name='mmnyPrdtYn']").val(), 'stdCtgrNo' :  stdCtgrNo};
//				}
				} else {
					param = { 'ctgrLevel' : 1, 'chnnlNo' : selected };
				}
			} else {
				if (! abc.text.isUndefined($("input[name='mmnyPrdtYn']").val())) {
//					if ("N" === $("input[name='mmnyPrdtYn']").val()) { // PO 상품 등록시 표카에 등록된 전시 카테고리만 노출 하기위해 추가
					param = { 'upCtgrNo' : selected, 'chnnlNo' : $("input[name='chnnlNo']").val(), 'mmnyPrdtYn' : $("input[name='mmnyPrdtYn']").val(), 'stdCtgrNo' : $("select[name='stdCtgrNo']").val() };
//				}
				} else {
					param = { 'upCtgrNo' : selected };
				}
			}

			var targetSelect = $(selectEl).next();
			var nextSelects = $(selectEl).nextAll();

			/*
			 * 상품 상세의 표준카테고리 3뎁스 변경에 따른 전시카테고리 1뎁스 세팅의 이벤트 처리
			 * "" 값을 세팅 시 하위에 전시카테고리를 모두 가져오는 오류를 수정하기 위해 추가
			*/
			if($(".stdCtgrSel").is('#prdDtdStdCtgrNo') && $('#prdDtdStdCtgrNo').val() == "" && !isFirst){
				$('.ui-sel.dp-category-select.1depth').html('<option value="">대카테고리 선택</option>');
				$('.ui-sel.dp-category-select.2depth').html('<option value="">중카테고리 선택</option>');
				$('.ui-sel.dp-category-select.3depth').html('<option value="">소카테고리 선택</option>');
				return;
			}
			isFirst = false;

			$.ajax({
				type : 'post',
				url : '/noacl/display/display-category/list',
				data : param
			}).done(function(data) {

				var txt = '';

				var type = targetSelect.hasClass('1depth') ? '대' : targetSelect.hasClass('2depth') ? '중' : targetSelect.hasClass('3depth') ? '소' : '세';
				targetSelect.html('<option value>'+type+'카테고리 선택</option>');

				if (data.leafCtgrYn == 'Y') {
					$.each(nextSelects, function(i,v) {
						$(v).prop('disabled', true);
					});
				} else {
					$.each(nextSelects, function(i,v) {
						$(v).prop('disabled', false);
					});
					$.each(data, function(i,v) {
						txt = '<option value="'+v.ctgrNo+'">'+v.ctgrName+'</option>';
						targetSelect.append(txt);
					});
				}

			}).fail(function(e) {
				console.log("e :" + e);
			})
		}

		self.event = function() {
			$(document).on('change', options.select, function() {
				// ctgrNo 초기화
				$('.dp-category-select').attr('name', '');

				var lastDepth = $('.dp-category-select.chnnl-no').val() == '10000' ? '4depth' : '3depth';

				if ($(this).hasClass(lastDepth)) {

					if ($(this).val() == '') {
						$(this).prev().attr('name', 'ctgrNo');
					} else {
						$(this).attr('name', 'ctgrNo');
					}
					return false;
				}

				if ($(this).hasClass('chnnl-no')) {

					if($(this).val() == '10000') {
						if (!$('.dp-category-select.4depth').length > 0) {
							$('.dp-category-select.3depth').attr('name', '');
							$(options.area).find('.3depth').after($('<select>', {
								'class' : 'ui-sel dp-category-select 4depth'
							}));
						}
					} else {
						$('.dp-category-select.4depth').remove();
					}

					$('.dp-category-select.1depth').attr('name', 'ctgrNo');
					self.getList(this, true);
				} else {

					$(this).attr('name', 'ctgrNo');
					self.getList(this);
				}
			});
		}

		return init;
	}

	/**
	 * 템플릿 팝업 조회
	 * callback : callback 함수
	 * deviceCode : 공통코드에 있는 deviceTypeCd 값
	 * menuType : 전시카테고리 = C, 전시페이지 = P
	 * chnnlNo : 채널 정보
	 */
	_displayCommon.openTemplatePopup = function(params){

		/*예)
		 * abc.biz.display.common.openTemplatePopup({
			 callback : 'abc.biz.display.template.callback',
			 deviceCode : '10000',
			 menuType : 'C'
			})*/

		params = $.extend({
			callback: '',
			deviceCode: '',
			menuType: '',
			chnnlNo : ''
        }, params);

		if(typeof params.callback != "string"){
			alert("콜백함수명이 문자열이 아닙니다");
			return;
		}

		var pop = abc.open.popup({
			url 	:	"/display/template/pop",
			winname :	"template-pop",
			method	: 	"get",
			title 	:	"template-pop",
			width 	:	950,
			height	:	900,
			params	:	params
		});
	}

	/**
	 * form data 초기화
	 */
	_displayCommon.initFormData = new function(){

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
	            } else if(inputType == 'hidden' && !data.isClearInputHiddenType){

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

	/**
	 * 이미지 처리
	 */
	_displayCommon.processImage = function(option){

		var options;
		var limitSize = 10485760;

		init = function(option){

			options = $.extend({
				file: '',
				name: '',
				type: 'I',
				allow: ''
	        }, option);

			if(options.allow == ''){
				options.allow = $(options.file).closest('td').prev().text().trim();
			}

			var fileWrap = $(options.file).parents('.file-wrap');

			options.delButton = fileWrap.find('.btn-file-del');
			options.previewArea = fileWrap.find('.img-wrap');
			options.nameText = fileWrap.find('.subject');

			uploadImage();
			removeImage();
		}

		var removeImage = function(){
			$(options.delButton).on('click', function(e){
				$(options.previewArea).html('');
				$(options.nameText).text('');
				$(options.file).val('');
				$(options.delButton).hide();
				$(options.name).val('');
			});
		}

		var uploadImage = function(){
			$(options.file).on('change', function(e){

				var img = new Image();
				var reader = new FileReader();
				if(this.files && this.files[0]){
					var file = this.files[0];

					var reader = new FileReader();
					reader.onload = function(e){

						if(options.type == 'I'){

							var ext = file.name.split('.').splice(-1)[0];
							if(ext != null) ext = ext.toLocaleLowerCase();

							if(options.allow.indexOf(ext) < 0 ){
								alert('허용되지 않는 파일입니다.');
								$(options.nameText).text('');
								$(options.name).val('');
								$(options.delButton).hide();
								$(options.previewArea).children('img').remove();
								return false;
							}


							if(e.total > limitSize){
								alert('최대 10MB까지 등록 가능 합니다.');
								$(options.nameText).text('');
								$(options.name).val('');
								$(options.delButton).hide();
								$(options.previewArea).children('img').remove();
								return false;
							}
						}


						$(img).attr('src', e.target.result);
						$(options.previewArea).children('img').remove();
						$(options.previewArea).append(img);
					}

					reader.readAsDataURL(this.files[0]);
					$(options.nameText).text(this.files[0].name);
					$(options.name).val(this.files[0].name);
					$(options.delButton).show();
				}
			});
		}

		init(option);
	}

	/**
	 * 전시 코너 정보 팝업
	 */
	_displayCommon.openCornerInfoPopup = function(params){

		params = $.extend({
			dispTmplCornerSeq: '',
			dispTmplNo: '',
			deviceTypeName : ''
        }, params);

		var pop = abc.open.popup({
			url 	:	"/display/contents/popup/corner-info-pop",
			winname :	"corner-info-pop",
			method	: 	"get",
			title 	:	"corner-info-pop",
			width 	:	1040,
			height	:	900,
			params	:	params
		});
	}

	/**
	 * localStorage를 통해 form data 저장
	 */
	_displayCommon.setFormParameter = new function(){

		var _form;
		var lsKey = 'param';
		var lsPageNumKey = 'listPageNum';
		var detailMoveKey = 'detailMove';
		var listUrlKey = 'listUrl';

		this.setForm = function(form){
			_form = $(form);
		}


		// parameter 저장
		this.saveParam = function(pageNum){
			var formData = _form.serializeArray();
			setLocalStorage(lsPageNumKey, pageNum == null ? 1 : pageNum);
			setLocalStorage(lsKey, JSON.stringify(formData));
			setLocalStorage(listUrlKey, location.pathname);
		}

		// form data set
		this.setParam = function(){
			console.log("setParam >>>" + "\ngetLocalStorage(detailMoveKey) >>>" + getLocalStorage(detailMoveKey) + "\ngetLocalStorage(listUrlKey) >>>" + getLocalStorage(listUrlKey) + "\nlocation.pathname >>>" + location.pathname);

			if(getLocalStorage(detailMoveKey) != 'Y' || getLocalStorage(listUrlKey) != location.pathname){
				removeLocalStorage(lsKey);
				removeLocalStorage(lsPageNumKey);
				removeLocalStorage(detailMoveKey);
				return false;
			}

			var formData = getLocalStorage(lsKey)
			var listPageNum = 1;

			if(formData != null){
				formData = $.parseJSON(formData);

				//form 안에 체크박스 checked false 처리
				_form.find('[type=checkbox]').prop('checked', false);


				$.each(formData, function(i,v){

					if(v.name == 'listPageNum') {
						listPageNum = v.listPageNum;
					}

					var el = _form.find('[name='+v.name+']');

					if(el == null  || el.length == 0) return true;

					var type = $(el).attr('type');

					if(type == 'select'){
						if(el.find('option').length > 0){
							el.find('[value='+v.value+']').prop('selected', true);
						}
					}else if(type == 'checkbox' || type == 'radio'){
						if(v.value != ''){
							_form.find('[name='+v.name+'][value='+v.value+']').prop('checked', true);
						}
					}else if($(v).is('textarea')){
						el.val(v.value);
					}else{
						el.val(v.value)
					}

				});

				//세팅 완료 후 제거
				//removeLocalStorage(lsKey);
				removeLocalStorage(detailMoveKey);
			}

			return listPageNum;
		}

		//LocalStorage 저장
		setLocalStorage = function(key, value){
			localStorage.setItem(key, value);
		}
		//LocalStorage 조회
		getLocalStorage = function(key){
			return localStorage.getItem(key);
		}
		//LocalStorage 삭제
		removeLocalStorage = function(key){
			localStorage.removeItem(key);
		}

		this.isParam = function(key){
			if(getLocalStorage(key) == null) return false;
			else return true;
		}

		this.getPageNum = function(){
			var pageNum = getLocalStorage(lsPageNumKey);
			removeLocalStorage(lsPageNumKey);
			return pageNum;
		}

		this.setPageNum = function(pageNum){
			localStorage.setItem(lsPageNumKey, pageNum);
		}

		this.setDetailMove = function(){
			setLocalStorage(detailMoveKey, 'Y');
		}
//
//		this.removeDetailMovie = function(){
//			removeLocalStorage(detailMoveKey);
//		}

	}


	$(function() {
		_displayCommon.init();
	});

})();