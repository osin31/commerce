(function() {

	var _standard = abc.object.createNestedObject(window,"abc.biz.product.category.standard");

	_standard.data = {
			focusRow : 1
	}

	/**
	 * 2020.01.30 : 채널별 카테고리 최대 LEVEL
	 */
	_standard.MAX_CTGR_LEVEL_ART = "";
	_standard.MAX_CTGR_LEVEL_ABC = "";
	_standard.MAX_CTGR_LEVEL_GS = "";
	_standard.MAX_CTGR_LEVEL_OTS = "";
	_standard.MAX_CTGR_LEVEL_KIDS = "";
	
	/**
	 * 초기화
	 */
	_standard.init = function(){

		_standard.initStandardCategorySheet();
		_standard.event();

		$("#stdCtgrName").prop("disabled",true);
		$("[name=useYn]").prop("disabled",true);
		$("#itemCode").prop("disabled",true);

	}

	/**
	 *  IBSheet 초기화(표준 카테고리 목록)
	 */
	_standard.initStandardCategorySheet = function() {
		var initStandardCategorySheet = {};
		initStandardCategorySheet.Cfg = {};
		initStandardCategorySheet.Cols = [
				{Header:"표준카테고리명", 	Type:"Html",	SaveName:"stdCtgrName",			Width:20,	Align:"Left",	TreeCol:1,	Edit:0, FontUderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:"상태",			Type:"Status",	SaveName:"status",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"메뉴LEVEL",		Type:"Text",	SaveName:"level",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"상위표준카테고리명",	Type:"Text",	SaveName:"upStdCtgrName",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"표준카테고리번호",	Type:"Text",	SaveName:"stdCtgrNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"상위표준카테고리번호",	Type:"Text",	SaveName:"upStdCtgrNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"품목코드",			Type:"Text",	SaveName:"itemCode",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"내부관리정보",		Type:"Text",	SaveName:"insdMgmtInfoText",	Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"정렬순번",			Type:"Text",	SaveName:"sortSeq",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"사용여부",			Type:"Text",	SaveName:"useYn",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록자이름',		Type:"Text",	SaveName:"rgsterInfo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록자번호',		Type:"Text",	SaveName:"rgsterNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록일시',			Type:"Date",	SaveName:"rgstDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자이름',		Type:"Text",	SaveName:"moderInfo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'수정자번호',		Type:"Text",	SaveName:"moderNo",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'수정일시',			Type:"Date",	SaveName:"modDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'상품속성코드',			Type:"Text",	SaveName:"sabangnetPrdtAttrText",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'사방넷 대카테고리',			Type:"Text",	SaveName:"sabangnetCtgr1Text",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'사방넷 중카테고리',			Type:"Text",	SaveName:"sabangnetCtgr2Text",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'사방넷 소카테고리',			Type:"Text",	SaveName:"sabangnetCtgr3Text",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'사방넷 세카테고리',			Type:"Text",	SaveName:"sabangnetCtgr4Text",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'leaf category',			Type:"Text",	SaveName:"leafCtgrYn",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  ];

		createIBSheet2(document.getElementById("standardSheet"), "tree", "100%", "100%");
		IBS_InitSheet(tree, initStandardCategorySheet);
		tree.FitColWidth();
		this.doActionStandardCategory("search");
	}

	/**
	 * 메뉴 grid action
	 */
	_standard.doActionStandardCategory = function(sAction){
		switch (sAction) {
			case "search":
				var param = { url : "/product/category-standard/list/read"
							, sheet : "tree"
							, subparam : 'isMasterPage=Y'
							, callback : function() {
								tree.SetSelectRow(_standard.data.focusRow);
								_standard.setStandardCategoryData(_standard.data.focusRow);
							}
				};
				DataSearch(param);

				break;
		}
	}

	/**
	 * 이벤트
	 */
	_standard.event = function() {

		var focusRow = 1;

		tree_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {

			if (Row != 1) {

				abc.biz.product.category.standard.setStandardCategoryData(Row);
				focusRow = Row;

				$('#saveStandardCategoryBtn').show();
				$('.handling-precaution-box').show();

				_standard.getHandlingPrecautionList();

				if (tree.GetRowData(Row).level == 1) {
					$('.handling-precaution-box').show();
				} else {
					$('.handling-precaution-box').hide();
				}
			} else {

				$('#saveStandardCategoryBtn').hide();
				$('.handling-precaution-area').html('');
				$('.handling-precaution-box').hide();

				$("#moderInfo").text("");
				$("#modDtm").text("");
				$("#stdCtgrNoField").text("");
				$("#standardCategoryForm")[0].reset();

			}

			if(tree.GetSelectRow() == 1){
				$("#stdCtgrName").prop("disabled",true);
				$("[name=useYn]").prop("disabled",true);
				$("#itemCode").prop("disabled",true);

			} else {
				$("#stdCtgrName").prop("disabled",false);
				$("[name=useYn]").prop("disabled",false);
				$("#itemCode").prop("disabled",false);
			}

//			if ($("#level").val() == 1) {
//				$("#display-category").show(); // 전시 카테고리 설정 영역
//			} else {
//				$("#display-category").hide(); // 전시 카테고리 설정 영역
//			}

			if(!$("#leafCtgrY").is(":checked")){
				$("#sabangnetPrdtAttrText").prop("disabled",true);
				$("#sabangnetCtgr1Text").prop("disabled",true);
				$("#sabangnetCtgr2Text").prop("disabled",true);
				$("#sabangnetCtgr3Text").prop("disabled",true);
				$("#sabangnetCtgr4Text").prop("disabled",true);
				$("#display-category").hide(); // 전시 카테고리 설정 영역
			}else {
				$("#sabangnetPrdtAttrText").prop("disabled",false);
				$("#sabangnetCtgr1Text").prop("disabled",false);
				$("#sabangnetCtgr2Text").prop("disabled",false);
				$("#sabangnetCtgr3Text").prop("disabled",false);
				$("#sabangnetCtgr4Text").prop("disabled",false);
				$("#display-category").show(); // 전시 카테고리 설정 영역
			}
		}

		tree_OnSearchEnd = function() {

			var rows = tree.GetDataLastRow();

			for(i = 0; i < rows; i++) {
				if(i != 1)
					tree.SetRowExpanded(i, 0);
			}

			tree.SetSelectRow(focusRow);

			if(focusRow == 1) {
				$('#saveStandardCategoryBtn').hide();
				$('.handling-precaution-box').hide();
			} else {
				abc.biz.product.category.standard.setStandardCategoryData(focusRow);
				$('#saveStandardCategoryBtn').show();
				$('.handling-precaution-box').show();

				if (tree.GetRowData(focusRow).level == 1) {
					$('.handling-precaution-box').show();
				} else {
					$('.handling-precaution-box').hide();
				}
			}

		}

		$("#addStandardCategoryBtn").click(function(e){
			abc.biz.product.category.standard.addStandardCategory();

			$("#stdCtgrName").prop("disabled",false);
			$("[name=useYn]").prop("disabled",false);
			$("#itemCode").prop("disabled",false);

//			if ($("#level").val() == 1) {
//				$("#display-category").show(); // 전시 카테고리 설정 영역
//			} else {
//				$("#display-category").hide(); // 전시 카테고리 설정 영역
//			}

			if(!$("#leafCtgrY").is(":checked")){
				$("#sabangnetPrdtAttrText").prop("disabled",true);
				$("#sabangnetCtgr1Text").prop("disabled",true);
				$("#sabangnetCtgr2Text").prop("disabled",true);
				$("#sabangnetCtgr3Text").prop("disabled",true);
				$("#sabangnetCtgr4Text").prop("disabled",true);
				$("#display-category").hide(); // 전시 카테고리 설정 영역
			}else {
				$("#sabangnetPrdtAttrText").prop("disabled",false);
				$("#sabangnetCtgr1Text").prop("disabled",false);
				$("#sabangnetCtgr2Text").prop("disabled",false);
				$("#sabangnetCtgr3Text").prop("disabled",false);
				$("#sabangnetCtgr4Text").prop("disabled",false);
				$("#display-category").show(); // 전시 카테고리 설정 영역
			}

		});

		$("#saveStandardCategoryBtn").click(function(e){
			abc.biz.product.category.standard.saveStandardCategory();
		});

		$("#saveSortBtn").click(function(e){
			abc.biz.product.category.standard.saveSort();
		});

		$("#nextBtn, #prevBtn").click(function(e){
			var param = '';

			if($(this).hasClass('next')) {
				param = 'next';
			} else if($(this).hasClass('prev')) {
				param = 'prev';
			}

			abc.biz.product.category.standard.changeSort(param);
			abc.biz.product.category.standard.setStandardCategoryData(tree.GetSelectRow());
		});

		// 취급주의사항 추가
		$('#addPrecaution').on('click', function() {

			var tmplInx = $(".handling-precaution-area").children().length;
			var tmpl = document.templateOneRoot('#handling-precaution-tmpl');
			var tmplClone = tmpl.clone();

			tmplClone.find('.stdCtgrNo').val($('input[name=stdCtgrNo]').val());
			tmplClone.find('.ui-chk .delYn').attr('id', 'chkMaterial' + tmplInx);
			tmplClone.find('.ui-chk label').attr('for', 'chkMaterial' + tmplInx);

			$('.handling-precaution-area').append(tmplClone);

		});

		// 취급주의사항 선택항목삭제
		$('#removePrecaution').on('click', function() {

			$.each($('input[name=delYn]:checked'), function(i,v) {
				$(v).parents('tr').remove();

				var removeSeq = $(v).siblings('.handlPrecauSeq').val();

				if (removeSeq != null) {
					var $input = $('<input>', {'name' : 'removeSeqs', 'type' : 'hidden', 'value' : removeSeq});

					$('.handling-precaution-area').prepend($input);
				}
			});
		});

		// 전시카테고리 이벤트 설정
		abc.biz.display.common.setDpCategoryEvent();

		$(document).on("click", "[data-button]", function(e) {
			var type = $(this).data("button");
			switch(type) {
			case "add-element" :
				// 전시 카테고리 추가
				if(abc.text.isBlank($("#display-category-channel option:selected").val())) {
					alert("전시 채널을 선택해 주세요.");
					$("#display-category-channel").focus();
					return;
				}

				//var selectedLastDisplayCategoryNo = $(".dp-category-select").not(":disabled").last().val();
				//console.log($(".dp-category-select").last()[0].length)
				/*
				if(abc.text.isBlank(selectedLastDisplayCategoryNo)) {
					alert("전시 카테고리를 선택해 주세요.");
					return;
				}*/
				
				/**
				 * 2020.01.30 상품 전시 카테고리 validate 변경
				 */
				var depthName = "";
				var categorySelectYn = true;
				var categoryChnnlNo = $("#display-category-channel").val();
				var maxCtgrLevel = 0;
				
				// Const.java 에서 내린 채널별 카테고리 MAX LEVEL
				if( categoryChnnlNo == abc.consts.SITE_CHNN_ART ){
					
					maxCtgrLevel = _standard.MAX_CTGR_LEVEL_ART;
					
				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_ABC ){
					
					maxCtgrLevel = _standard.MAX_CTGR_LEVEL_ABC;
					
				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_GS ){
					
					maxCtgrLevel = _standard.MAX_CTGR_LEVEL_GS;
					
				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_OTS ){
					
					maxCtgrLevel = _standard.MAX_CTGR_LEVEL_OTS;
					
				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_KIDS ){
					
					maxCtgrLevel = _standard.MAX_CTGR_LEVEL_KIDS; // 미정
				}
				
				$(".dp-category-select").each(function(i,v){
					var selectOptn = $(v).val();
					if(abc.text.isBlank(selectOptn)){
						var classArray = $(v).attr('class').split(' ');
						depthName = classArray[2];
						var ctgrLevel = Number(depthName.substring(0,1));
						if(ctgrLevel <= maxCtgrLevel){
							categorySelectYn = false;
							return false;
						}
					}
				});
				console.log("depthName : " + depthName);
				console.log("categorySelectYn : " + categorySelectYn);
				if(categorySelectYn == false) {
					alert("전시 카테고리를 선택해주세요.");
					$("." + depthName).focus();
					return;
				}
				
				// 등록된 전시카테고리와 중복 비교
				if($("#display-category-area").find("tr").find("[name='displayCategoryList.ctgrNo'][value='" + $("select[name='ctgrNo']").val() + "']").length > 0) {
					alert("이미 등록되어 있는 전시 카테고리입니다.");
					return;
				}

				// 데이터 없음 메시지 제거
				$("#display-category-area").find("[data-empty='display-category']").remove();

				// 전시 카테고리 값 초기화
				$(".dp-category-select").attr("selected", "selected");

				var target = $(this).data("target");
				var param = {
//					siteNo	: isMmnyPrdt === true ? $("#display-category-channel>option:selected").data("site-no") : $("#display-category-channel").data("site-no"),
					chnnlNo	: $("#display-category-channel").val(),
					ctgrNo	: $("select[name='ctgrNo']").val()
				};

				$.ajax({
					url				: "/noacl/display/display-category/list",
					method			: "POST",
					data			: param
				}).done(function(data) {
					console.log(data);
					
					var template = $("#display-category-template").clone();

					template = $(template.html());
					template = $(template);

					template.find("#display-category-checkbox").attr("id", "display-category-checkbox" + data.ctgrNo);	// 체크박스 아이디 변경
					template.find("label[for='display-category-checkbox']").attr("for", "display-category-checkbox" + data.ctgrNo);	// 레에블 아이디 변경

					template.find("[data-template=display-category-channel-name]").text(data.chnnlName);	// 채널이름
					template.find("[data-template=display-category-category-path]").text(data.ctgrNamePath);	// 카테고리 경로
					template.find("[data-template=display-category-ctgr-no]").val(data.ctgrNo);	// 카테고리 번호

					$("#" + target).append(template);	// 템플릿을 카테고리영역에 추가

				}).fail(function(data) {
					console.log("선택된 전시 카테고리 조회 실패");
					console.log(data);
				});
				break;
			case "remove-elements" :
				// 전시 카테고리 선택삭제
				var target = $(this).data("target");
				var selector = "#" + target;
				var removeRowElements = $(selector).find("input[type='checkbox']:checked").closest("tr");

				$(selector).find("input[type='checkbox']:checked").closest("tr").remove();

				var emptyYn = $(selector).find("display-category").closest("tr").length;

				if ($(selector).find("input[type='checkbox']").closest("tr").length <= 0 && emptyYn == 0) {
					var _html = "<tr data-empty=\"display-category\">";
					_html    +=	"	<td class=\"text-center\" colspan=\"3\">";
					_html    +=	"		<span>등록된 전시 카테고리가 없습니다.</span>";
					_html    +=	"	</td>";
					_html    +=	"</tr>";

					$("#" + target).append(_html);	// 템플릿을 카테고리영역에 추가
				}

				break;
			}

		});

		$(document).on("click", "[data-checkbox]", function(e) {
			var type = $(this).data("checkbox");
			switch(type) {
			case "check-all" :
				// 전체선택
				var selector = "[data-target=" + $(this).data("target") + "]";
				var isChecked = $(this).is(":checked");
				$(selector).prop("checked", isChecked);

				break;
			}
		});

		// 취급주의사항 checkbox event
		$('#chkMaterialAll').on('click', function() {

			if ($('input[name=delYn]').length == $('input[name=delYn]:checked').length) {
				$('input[name=delYn]').prop('checked', false);
			} else {
				$('input[name=delYn]').prop('checked', true);
			}
		});
		$(document).on('click', 'input[name=delYn]', function() {

			if ($('input[name=delYn]').length == $('input[name=delYn]:checked').length) {
				$('#chkMaterialAll').prop('checked', true);
			} else {
				$('#chkMaterialAll').prop('checked', false);
			}
		});

//		if ($("#level").val() == 1) {
//			$("#display-category").show(); // 전시 카테고리 설정 영역
//		} else {
//			$("#display-category").hide(); // 전시 카테고리 설정 영역
//		}

		//사방넷 disabled 처리
		$("[name=leafCtgrYn]").on("click",function(){
			if(!$("#leafCtgrY").is(":checked")){
				$("#sabangnetPrdtAttrText").prop("disabled",true);
				$("#sabangnetCtgr1Text").prop("disabled",true);
				$("#sabangnetCtgr2Text").prop("disabled",true);
				$("#sabangnetCtgr3Text").prop("disabled",true);
				$("#sabangnetCtgr4Text").prop("disabled",true);
			}else {
				$("#sabangnetPrdtAttrText").prop("disabled",false);
				$("#sabangnetCtgr1Text").prop("disabled",false);
				$("#sabangnetCtgr2Text").prop("disabled",false);
				$("#sabangnetCtgr3Text").prop("disabled",false);
				$("#sabangnetCtgr4Text").prop("disabled",false);
			}
		});

		// Leaf Category Y 설정
		$('#leafCtgrY').on('click', function() {
			var selectedRow = tree.GetSelectRow();
			var data = tree.GetRowData(selectedRow);

			if (data.level != '0') { // root 카테고리가 아닌 경우,
				if (tree.IsHaveChild(selectedRow)) { // 하위 카테고리가 존재하는 경우,
					// Leaf Category N -> Y 변경 불가능
					alert('하위 카테고리가 존재하는 경우 Leaf Category로 설정할 수 없습니다.');
					return false;
				}
			}
			$("#display-category").show(); // 전시 카테고리 설정 영역
			_standard.getHandlingPrecautionList();
		});
		// Leaf Category N 설정
		$('#leafCtgrN').on('click', function() {
			$("#display-category").hide(); // 전시 카테고리 설정 영역
		});
	}

	/**
	 * 선택된 그리드정보를 메뉴정보 영역에 세팅
	 */
	_standard.setStandardCategoryData = function(Row) {

		tree.SetSelectRow(Row);

		var info = tree.GetRowData(Row);

		$("#status").val(abc.consts.CRUD_U);
		$("#level").val(info.level);

		$("#stdCtgrNo").val(info.stdCtgrNo);
		$("#stdCtgrNoField").text(info.stdCtgrNo);
		$("#upStdCtgrNo").val(info.upStdCtgrNo);
		var decodedName  = $('<textarea />').html(info.stdCtgrName).text();
		$("#stdCtgrName").val(decodedName);
		$("#upStdCtgrName").text(info.upStdCtgrName);

		$("#leafCtgrYn").val(info.leafCtgrYn);
		if(info.leafCtgrYn == 'Y' || info.leafCtgrYn == ''){
			$("#leafCtgrY").prop('checked', true);
			$("#leafCtgrN").prop('checked', false);
		}else{
			$("#leafCtgrY").prop('checked', false);
			$("#leafCtgrN").prop('checked', true);
		}

		$("#sabangnetPrdtAttrText").val(info.sabangnetPrdtAttrText);
		$("#sabangnetCtgr1Text").val(info.sabangnetCtgr1Text);
		$("#sabangnetCtgr2Text").val(info.sabangnetCtgr2Text);
		$("#sabangnetCtgr3Text").val(info.sabangnetCtgr3Text);
		$("#sabangnetCtgr4Text").val(info.sabangnetCtgr4Text);


		$("#useYn").val(info.useYn);
		if(info.useYn == 'Y' || info.useYn == ''){
			$("#radioUseY").prop('checked', true);
			$("#radioUseN").prop('checked', false);
		}else{
			$("#radioUseY").prop('checked', false);
			$("#radioUseN").prop('checked', true);
		}

		if(info.itemCode != "") {
			var data = info.itemCode;
			$("#itemCode option[value="+data+"]").prop('selected', true);
		} else {
			$("#itemCode option:first").prop('selected', true);
		}

		if(Row != 1) $("#moderInfo").text(info.moderInfo);
		else $("#moderInfo").text('');
		$("#modDtm").text(tree.GetCellText(Row, 'modDtm'));
	}

	/**
	 * 카테고리 등록 input 초기화
	 */
	_standard.addStandardCategory = function() {
		var level = Number($("#level").val());

		$('#saveStandardCategoryBtn').show();

		if(level == 3) {
			alert('3depth의 하위 카테고리는 등록할 수 없습니다.');
			return;
		}

		var data = tree.GetRowData(tree.GetSelectRow());
		if (data.leafCtgrYn == 'Y') { // 리프 카테고리인 경우,
			alert('Leaf Category인 경우 하위 카테고리를 등록할 수 없습니다.');
			return;
		}

		$("#upStdCtgrName").text($("#stdCtgrName").val());
		$("#upStdCtgrNo").val($("#stdCtgrNoField").text());
		$("#stdCtgrName").val("");
		$("#stdCtgrNoField").text("");
		$("#useYn").val("Y");
		$("#radioUseY").prop('checked', true);
		$("#itemCode option:first").prop('selected', true);
		$("#moderInfo").text("");
		$("#modDtm").text("");
		$("#status").val(abc.consts.CRUD_C);
		$('.handling-precaution-box').hide();


		$("#leafCtgrYn").val("Y");
		$("#leafCtgrY").prop('checked', true);
		$("#sabangnetPrdtAttrText").val("");
		$("#sabangnetCtgr1Text").val("");
		$("#sabangnetCtgr2Text").val("");
		$("#sabangnetCtgr3Text").val("");
		$("#sabangnetCtgr4Text").val("");
	}

	/**
	 * 카테고리 등록/수정
	 */
	_standard.saveStandardCategory = function() {

		if(!this.validation()) {
			return;
		}

		var url = "/product/category-standard/save";
		var form = $.form(document.forms.standardCategoryForm);

		form.submit({
			url : url,
			method : "POST",
			valid	: function($f){
				return true;
			},
			success : function(data) {
				alert('저장되었습니다.');

				var selectRow = tree.GetSelectRow();
				if ($('input[name=status]').val() == 'C') { // 등록
					var updateRow = tree.GetLastChildRow(selectRow) > 0 ? tree.GetLastChildRow(selectRow) + 1 : selectRow + 1;

					_standard.data.focusRow = updateRow;
				} else { // 수정
					_standard.data.focusRow = selectRow;
				}

				$('#saveStandardCategoryBtn').hide();
				$('.handling-precaution-box').hide();

				_standard.doActionStandardCategory("search");
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			}
		});

		$.ajax({
			type : 'post',
			url : url,
			data : { 'stdCtgrNo' : stdCtgrNo },
		})
		.done(function(data){

			$('.handling-precaution-area').html('');

			if (data.length > 0) {

				var tmpl = document.templateOneRoot('#handling-precaution-tmpl');

				$.each(data, function(i,v) {

					var tmplClone = tmpl.clone();

					tmplClone.find('.stdCtgrNo').val(v.stdCtgrNo);
					tmplClone.find('.handlPrecauSeq').val(v.handlPrecauSeq);
					tmplClone.find('.titleText').val(v.titleText);
					tmplClone.find('.contText').html(v.contText);

					tmplClone.find('.ui-chk .delYn').attr('id', 'chkMaterial' + i);
					tmplClone.find('.ui-chk label').attr('for', 'chkMaterial' + i);

					$('.handling-precaution-area').append(tmplClone);
				});
			}

		})
		.fail(function(e){
			console.log(e)
		});
	}

	/**
	 * 카테고리 정렬 순서 변경
	 */
	_standard.changeSort = function(direction) {
		var fromRow = tree.GetSelectRow();
		var toRow;
		var focusRow;

		if(direction == 'prev') {
			toRow = tree.GetPrevSiblingRow(fromRow);
		} else if(direction == 'next') {
			toRow = tree.GetNextSiblingRow(fromRow);
		}

		if(toRow > 0) {
			var tmp = tree.GetRowData(toRow).sortSeq;
			tree.SetRowData(toRow, { sortSeq : tree.GetRowData(fromRow).sortSeq });
			tree.SetRowData(fromRow, { sortSeq : tmp });

			if(fromRow > toRow) {
				tree.DataMove(toRow, fromRow);
				focusRow = toRow;
			} else {
				tree.DataMove(fromRow, toRow);
				focusRow = (tree.GetChildRows(fromRow) != "" ? tree.GetChildRows(fromRow).split('|').length : 0)  + fromRow + 1;
			}

			tree.SetSelectRow(focusRow);
		} else {
			return;
		}
	}

	/**
	 * 카테고리 정렬 순서 저장
	 */
	_standard.saveSort = function() {

		var url = "/product/category-standard/save/sort";
		var param = { Col : "status", CallBack : function() {
			_standard.doActionStandardCategory('search')
		} };
		tree.DoSave(url, param);
	}

	/**
	 * validation
	 */
	_standard.validation = function() {

		if(abc.text.isBlank($("#stdCtgrName").val())) {
			alert("표준 카테고리명을 입력하세요.");
			return false;
		}

		// Leaf Category
		if ($('#status').val() == 'C' && $('input[name=leafCtgrYn]:checked').val() == 'N') {
			var flag = confirm('하위 카테고리가 존재하지 않는 경우 Leaf Category로 설정하시길 권장드립니다. 이대로 저장하시겠습니까?');

			if (!flag) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 취급주의사항 리스트 조회
	 */
	_standard.getHandlingPrecautionList = function() {

		var stdCtgrNo = $('input[name=stdCtgrNo]').val();
		var leafCtgrYn = $('input:radio[name=leafCtgrYn]:checked').val(); // Leaf Category 여부
		var ctgrLevel = $('input[name=level]').val();
		var target = "display-category-area";
		$("#" + target).html('');

		var _html = "<tr data-empty=\"display-category\">";
		_html    +=	"	<td class=\"text-center\" colspan=\"3\">";
		_html    +=	"		<span>등록된 전시 카테고리가 없습니다.</span>";
		_html    +=	"	</td>";
		_html    +=	"</tr>";

		$("#" + target).append(_html);	// 템플릿을 카테고리영역에 추가

		$.ajax({
			type : 'post',
			url : '/product/category-standard/list/handling-precaution',
			data : { 'stdCtgrNo' : stdCtgrNo, 'leafCtgrYn' : leafCtgrYn, 'ctgrLevel' : ctgrLevel },
		})
		.done(function(data){
			$('.handling-precaution-area').html('');

			if (data.handlingPrecautionList != null && data.handlingPrecautionList.length > 0) {

				var tmpl = document.templateOneRoot('#handling-precaution-tmpl');

				$.each(data.handlingPrecautionList, function(i,v) {

					var tmplClone = tmpl.clone();

					tmplClone.find('.stdCtgrNo').val(v.stdCtgrNo);
					tmplClone.find('.handlPrecauSeq').val(v.handlPrecauSeq);
					tmplClone.find('.titleText').val(v.titleText);
					tmplClone.find('.contText').html(v.contText);

					tmplClone.find('.ui-chk .delYn').attr('id', 'chkMaterial' + i);
					tmplClone.find('.ui-chk label').attr('for', 'chkMaterial' + i);

					$('.handling-precaution-area').append(tmplClone);
				});
			}

			if (data.dispCategoryList != null && data.dispCategoryList.length > 0) {
				$("#display-category-area").find("[data-empty='display-category']").remove();
				var ctgrList = data.dispCategoryList;

				$.each(ctgrList, function(i,v) {
					var template = $("#display-category-template").clone();

					template = $(template.html());
					template = $(template);
					template.find("#display-category-checkbox").attr("id", "display-category-checkbox" + v.ctgrNo);	// 체크박스 아이디 변경
					template.find("label[for='display-category-checkbox']").attr("for", "display-category-checkbox" + v.ctgrNo);	// 레에블 아이디 변경

					template.find("[data-template=display-category-channel-name]").text(v.chnnlName);	// 채널이름
					template.find("[data-template=display-category-category-path]").text(v.ctgrNamePath);	// 카테고리 경로
					template.find("[data-template=display-category-ctgr-no]").val(v.ctgrNo);	// 카테고리 번호

					$("#" + target).append(template);	// 템플릿을 카테고리영역에 추가
				});
			} else {
				// 데이터 없음 메시지 제거
				//$("#display-category-area").find("[data-empty='display-category']").remove();
			}

		})
		.fail(function(e){
			console.log(e)
		});
	}

	/**
	 * textarea maxlength
	 */
	_standard.setMaxLength = function(obj) {

		var maxLength = parseInt(obj.getAttribute('maxlength'));

		if (obj.value.length > maxLength) {
			obj.value = obj.value.substring(0, maxLength);
		}
	}

	$(function() {

		_standard.init();
	});

})();