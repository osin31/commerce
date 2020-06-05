(function() {

	var _category = abc.object.createNestedObject(window,"abc.biz.display.category");
	
	/**
	 * 초기화
	 */
	_category.data = { focusRow : 1, newFlag : false };
	_category.init = function() {
		
		_category.event();
		_category.initDpCategorySheet();
		
		new abc.biz.display.common.processImage({
			file: '#imageFile01',
			name: '#imageName'
		});
		new abc.biz.display.common.processImage({
			file: '#imageFile02',
			name: '#imageName'
		});
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_category.initDpCategorySheet = function() {
		
		// ====================================================================================================
		// 전시 카테고리 목록
		var initDpCategorySheet = {};
		
		initDpCategorySheet.Cfg = {};
		initDpCategorySheet.Cols = [
				{Header:"카테고리명", 		Type:"Text",	SaveName:"ctgrName",			TreeCol:1,	LevelSaveName:"ctgrLevel",	Edit:0, FontUderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:"상태",			Type:"Status",	SaveName:"status",				Hidden:1,	Edit:1}
			  , {Header:"LEVEL",		Type:"Text",	SaveName:"level",				Hidden:1,	Edit:1, LevelSaveName:"ctgrLevel"}
			  , {Header:"카테고리번호",		Type:"Text",	SaveName:"ctgrNo",				Hidden:1,	Edit:1}
			  , {Header:"상위카테고리번호",	Type:"Text",	SaveName:"upCtgrNo",			Hidden:1,	Edit:1}
			  , {Header:"Leaf Ctgr 여부",	Type:"Text",	SaveName:"leafCtgrYn",			Hidden:1,	Edit:1}
			  , {Header:"표준카테고리번호",	Type:"Text",	SaveName:"stdCtgrNo",			Hidden:1,	Edit:1}
			  , {Header:"PC전시템플릿번호",	Type:"Text",	SaveName:"pcDispTmplNo",		Hidden:1,	Edit:1}
			  , {Header:"PC전시템플릿명",	Type:"Text",	SaveName:"pcDispTmplName",		Hidden:1,	Edit:1}
			  , {Header:"PC전시템플릿이미지",	Type:"Text",	SaveName:"pcPreviewImageUrl",	Hidden:1,	Edit:1}
			  , {Header:"MO전시템플릿번호",	Type:"Text",	SaveName:"mobileDispTmplNo",	Hidden:1,	Edit:1}
			  , {Header:"MO전시템플릿명",	Type:"Text",	SaveName:"mobileDispTmplName",	Hidden:1,	Edit:1}
			  , {Header:"MO전시템플릿이미지",	Type:"Text",	SaveName:"mobilePreviewImageUrl",Hidden:1,	Edit:1}
			  , {Header:"카테고리명전시유형",	Type:"Text",	SaveName:"ctgrNameDispType",	Hidden:1,	Edit:1}
			  , {Header:"PC이미지명",		Type:"Text",	SaveName:"pcImageName",			Hidden:1,	Edit:1}
			  , {Header:"PC이미지경로",		Type:"Text",	SaveName:"pcImagePathText",		Hidden:1,	Edit:1}
			  , {Header:"PC이미지URL",		Type:"Text",	SaveName:"pcImageUrl",			Hidden:1,	Edit:1}
			  , {Header:"PC대체텍스트",		Type:"Text",	SaveName:"pcAltrnText",			Hidden:1,	Edit:1}
			  , {Header:"MOBILE이미지명",	Type:"Text",	SaveName:"mobileImageName",		Hidden:1,	Edit:1}
			  , {Header:"MOBILE이미지경로",	Type:"Text",	SaveName:"mobileImagePathText",	Hidden:1,	Edit:1}
			  , {Header:"MOBILE이미지URL",	Type:"Text",	SaveName:"mobileImageUrl",		Hidden:1,	Edit:1}
			  , {Header:"MOBILE대체텍스트",	Type:"Text",	SaveName:"mobileAltrnText",		Hidden:1,	Edit:1}
			  , {Header:"사이트번호",		Type:"Text",	SaveName:"siteNo",				Hidden:1,	Edit:1}
			  , {Header:"채널번호",			Type:"Text",	SaveName:"chnnlNo",				Hidden:1,	Edit:1}
			  , {Header:"검색필터사용여부",	Type:"Text",	SaveName:"srchFilterUseYn",		Hidden:1,	Edit:1}
			  , {Header:"정렬순번",			Type:"Text",	SaveName:"sortSeq",				Hidden:1,	Edit:1}
			  , {Header:"전시여부",			Type:"Text",	SaveName:"dispYn",				Hidden:1,	Edit:1}
			  , {Header:"사용여부",			Type:"Text",	SaveName:"useYn",				Hidden:1,	Edit:1}
			  , {Header:"name경로",		Type:"Text",	SaveName:"ctgrNamePath",		Hidden:1,	Edit:1}
			  , {Header:"no경로",			Type:"Text",	SaveName:"ctgrNoPath",			Hidden:1,	Edit:1}
			  , {Header:"chnnl경로",		Type:"Text",	SaveName:"chnnlPath",			Hidden:1,	Edit:1}
			  , {Header:'등록자번호',		Type:"Text",	SaveName:"rgsterNo",			Hidden:1,	Edit:1}
			  , {Header:'등록일시',			Type:"Date",	SaveName:"rgstDtm",				Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자번호',		Type:"Text",	SaveName:"moderNo",				Hidden:1,	Edit:1}
			  , {Header:'수정일시',			Type:"Date",	SaveName:"modDtm",				Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("dpCtgrSheet"), "tree", "100%", "100%");
		IBS_InitSheet(tree, initDpCategorySheet);
		tree.FitColWidth();
		tree.ShowTreeLevel(1,1);
		this.doActionDpCategory("search");		
	}
	
	/**
	 * 메뉴 grid action
	 */
	_category.doActionDpCategory = function(sAction){
		
		var idx = tree.GetSelectRow();
		var data = tree.GetRowData(idx);
		var chnnlName = $('#chnnlNo option:selected').text();
		$('input[name=chnnlName]').val(chnnlName);
		
		switch (sAction) {
		case "search":
			
			var param = { url : "/display/category/list/read"
						, sheet : "tree"
						, subparam : FormQueryStringEnc(document.forms.searchForm)
						, callback : function() {
							tree.SetSelectRow(_category.data.focusRow);
							_category.setDpCategoryData(_category.data.focusRow);
						}
			};
			DataSearch(param);	
			
			break;
		}
		
		_category.getFrontDomain();
	}
	
	/**
	 * 이벤트
	 */
	_category.event = function(){
		
		var focusRow = 1;
		
		tree_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {
			
			if (Row != 0) {
				if (!_category.data.newFlag) { // 일반
					_category.setDpCategoryData(Row);
					
					$('#categoryArea').click();
					
					if(tree.GetRowData(Row).ctgrNo == '') {
						$('.tab-wrap').tabs("disable", 1);
					} else {
						$('.tab-wrap').tabs("enable", 1);				
					}
					
					if(tree.GetRowData(Row).stdCtgrNo != '') {
						_category.doActionDpCategory("product");
					}
				} else if(_category.data.newFlag && Row != _category.data.newRowIndex) {
					var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
					
					if (cfm) {
						
						_category.data.newFlag = false;
						tree.RowDelete(_category.data.newRowIndex, 0);
						$('.tab-wrap').tabs("enable", 1);
						tree.SetSelectRow(Row);
						_category.setDpCategoryData(Row);
						
					} else {
						tree.SetSelectRow(_category.data.newRowIndex);
					}
				}
				
				abc.biz.display.category.contents.doActionDpContents("pc");
				abc.biz.display.category.contents.doActionDpContents("mobile");
			}			
		}
		
		tree_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response) {
			var rows = tree.GetDataLastRow();
			
			for(i = 0; i < rows; i++) {
				if(i != 1)
					tree.SetRowExpanded(i, 0);
			}
			
			_category.setDpCategoryData(focusRow);
			abc.biz.display.category.contents.doActionDpContents("pc");
			abc.biz.display.category.contents.doActionDpContents("mobile");
		}
		
		// 카테고리 등록 Btn
		$('.addCtgrBtn').on('click', function() {
			
			if(_category.data.newFlag) {
				alert('이미 등록중인 카테고리가 있습니다.');
				return;
			}		
			
			_category.addCtgr();
			
		});
		
		// 카테고리 저장 Btn
		$('#saveBtn').on('click', function() {
			var dispYn = $('input:radio[name=dispYn]:checked').val();
			
			// TODO 사용>사용안함
			tree.GetRowData(tree.GetSelectRow()).ctgrNo;
			
			var ctgrNo = $('input[name=ctgrNo]').val();
			var param = { 'ctgrNo' : ctgrNo };
			$.ajax({
				type: "POST",
				url: "/display/category/check",
				data: param,
				dataType: "json",
				error : function(error){
					alert(error + "error");
				},
				success : function(result){
					if(dispYn =="N" && result == true){
						alert("지정된 상품이 존재합니다.");
					} else {
						_category.saveCtgr();
					}
				}
			});
			
		});
		
		// 카테고리 순서 변경 Btn
		$("#nextBtn, #prevBtn").click(function(e){
			var param = '';
			
			if($(this).hasClass('next')) {
				param = 'next';
			} else if($(this).hasClass('prev')) {
				param = 'prev';
			}			
			
			_category.changeSort(param);
		});
		
		// 카테고리 순서 저장 Btn
		$('.saveSortBtn').on('click', function() {
			
			_category.saveSort();
		});
		
		// Leaf Category 여부 (표준카테고리 지정 Y:활성 N:비활성)
		$('input[name=leafCtgrYn]').on('click', function() {
			
			$select = $('.stdCtgrArea select');
			
			if($(this).val() == 'Y') {
				$('#productWrap').show();
			} else {		
				$('#productWrap').hide();
			}
		});
		
		// leaf category Y 체크
		$('#radioLeafY').on('click', function() {
			var selectedRow = tree.GetSelectRow();
			var data = tree.GetRowData(selectedRow);
			
			if (data.ctgrLevel != '0') { // root 카테고리가 아닌 경우,
				if (tree.IsHaveChild(selectedRow)) { // 하위 카테고리가 존재하는 경우,
					// Leaf Category N -> Y 변경 불가능
					alert('하위 카테고리가 존재하는 경우 Leaf Category로 설정할 수 없습니다.');
					return false;
				}
			}
		});
		
		// 카테고리명 노출 타입
		$('input[name=ctgrNameDispType]').on('click', function() {
						
			if($(this).val() == 'T') {
				$('#pcImageArea').hide();
				$('#moImageArea').hide();
			} else {
				$('#pcImageArea').show();
				$('#moImageArea').show();
			}
		});
		
		// 템플릿 popup
		$('.searchTmpl').on('click', function() {
			
			if($('[name=dispYn]:checked').val() == 'Y'){
				if(!abc.text.allNull($('input[name=ctgrNo]').val())){
					alert('전시 중인 경우 템플릿은 수정할 수 없습니다.');
					return false;
				}
			}
			
			var deviceCode = $(this).hasClass('forPc') ? '10000' : '10001';
			
			abc.biz.display.common.openTemplatePopup({
				callback : 'abc.biz.display.category.templateCallback',
				deviceCode : deviceCode,
				menuType : 'C',
				chnnlNo : $('#chnnlNo').val()
			});
		});
		
		// 이미지 삭제
		$('.btn-file-del').on('click', function() {
			
			var pre = $(this).attr('id') == 'pcDelBtn' ? 'pc' : 'mobile';
			
			$('input[name='+pre+'ImageFile]').val();
			$('#'+pre+'ImageName').html('');
			$('#'+pre+'DelBtn').hide();
			$('#'+pre+'Image').html('');
		});
				
		// 사이트 채널/사용유무 선택
		$('#searchForm select').focus(function() {
			prev_val = $(this).val();
		}).change(function() {
			
			if (!_category.data.newFlag){
				
				_category.doActionDpCategory('search');
			} else {
				var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
				
				if (cfm) {
					_category.data.newFlag = false;
					tree.RowDelete(_category.data.newRowIndex, 0);
					_category.doActionDpCategory('search');
					$('input[name=chnnlName]').val();
					$('.tab-wrap').tabs("enable", 1);
				} else {
					$(this).val(prev_val);
				}
			}	
		});
		
		// 상품 불러오기
		$('#loadProduct').on('click', function() {
			
			$('#productWrap').show();
		});
		
		// 템플릿 보기
		$('.preview-btn').on('click', function() {
			
			var data = tree.GetRowData(tree.GetSelectRow());
			var url;
			
			if ($(this).hasClass('pc-type')) {
				url = data.pcPreviewImageUrl;
			} else {
				url = data.mobilePreviewImageUrl;
			}
			
			if (!(url == 'undefined' || url == '' || url == null)) {
				var params = {};
				
				abc.open.popup({
					url 	:	url,
					winname :	"template-image-pop",
					method	: 	"get",
					title 	:	"템플릿 보기",
					params	:	params
				});
			}
		});
		
		// 템플릿 삭제
		$('.reset-disp-tmpl-btn').on('click', function() {
			
			$(this).siblings('.disp-tmpl-no,.disp-tmpl-name').val('');
			$(this).parents('.ip-search-box').removeClass('selected');
		});
		
		// 전시여부 변경시 템플릿 삭제 안되도록
		$('#ctgrForm').on('change', '[name=dispYn]', function(){
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');

			if($('[name=dispYn]:checked').val() == 'N'){
				$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
				$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
			}
		});
	}
	
	/**
	 * 템플릿 popup callback
	 */
	_category.templateCallback = function(data) {
		
		if(data.deviceName == 'Mobile') {
			$('#mobileDispTmplName').val(data.tmplName);
			$('#mobileDispTmplNo').val(data.dispTmplNo);
			$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
			abc.biz.display.category.contents.doActionDpContents("mobile");
		} else {
			$('#pcDispTmplName').val(data.tmplName);
			$('#pcDispTmplNo').val(data.dispTmplNo);
			$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
			abc.biz.display.category.contents.doActionDpContents("pc");
		}
	}
	
	/**
	 * 선택된 그리드정보를 메뉴정보 영역에 세팅
	 */
	_category.setDpCategoryData = function(Row) {
		
		var data = tree.GetRowData(Row);
		
		_category.doActionDpCategory('product');
		
		//PC 템플릿 보기 처리
		if(data.pcPreviewImageUrl == null || data.pcPreviewImageUrl == ''){
			$('.preview-btn.pc-type').hide()
		}else{
			$('.preview-btn.pc-type').show()
		}
		//mobile 템플릿 보기 처리		
		if(data.mobilePreviewImageUrl == null || data.mobilePreviewImageUrl == ''){
			$('.preview-btn.mobile-type').hide()
		}else{
			$('.preview-btn.mobile-type').show()
		}
		
		// hidden
		$('input[name=siteNo]').val(data.siteNo);
		$('input[name=chnnlNo]').val(data.chnnlNo);
		$('input[name=ctgrNo]').val(data.ctgrNo);
		$('input[name=upCtgrNo]').val(data.upCtgrNo);
		$('input[name=stdCtgrNo]').val(data.stdCtgrNo);
		$('input[name=ctgrLevel]').val(data.ctgrLevel);
		$('input[name=pcImagePathText]').val(data.pcImagePathText);
		$('input[name=pcImageName]').val(data.pcImageName);
		$('input[name=mobileImagePathText]').val(data.mobileImagePathText);
		$('input[name=mobileImageName]').val(data.mobileImageName);
		
		var chnnlName = $('input[name=chnnlName]').val();
		
		var arr = data.ctgrNamePath.split(' > ');
		if (arr.length > 1) {
			arr.pop();
			path = chnnlName + ' > ' + arr.join(' > ');
		} else {
			path = data.level == 0 ? '' : chnnlName;
		}
		$('#ctgrPath').html(path);
		$('#ctgrNoArea').html(data.ctgrNo);
		$('#ctgrName').val(data.ctgrName);
		
//		if(data.useYn == 'Y' || data.useYn == '') $('#radioUseY').prop('checked', true);
//		else $('#radioUseN').prop('checked', true);
//		
//		if(data.dispYn == 'Y' || data.dispYn == '') $('#radioDisplayY').prop('checked', true);
//		else $('#radioDisplayN').prop('checked', true);
		
		if(data.srchFilterUseYn == 'Y') $('#radioFilterY').prop('checked', true);
		else if(data.srchFilterUseYn == 'N' || data.srchFilterUseYn == '') $('#radioFilterN').prop('checked', true);
		
		$stdCtgr = $('.stdCtgrArea select');
		
		if(data.leafCtgrYn == 'Y') {
			$('#radioLeafY').prop('checked', true);
			
			$('#productWrap').show();
		} else if(data.leafCtgrYn == 'N' || data.leafCtgrYn == '') {
			$('#radioLeafN').prop('checked', true);
			
			$('#productWrap').hide();
		}
		
		if (data.pcDispTmplName != null && data.pcDispTmplName != '') {
			$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
		} else {
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
		}
		
		$('#pcDispTmplName').val(data.pcDispTmplName);
		$('#pcDispTmplNo').val(data.pcDispTmplNo);
		
		
		if (data.mobileDispTmplName != null && data.mobileDispTmplName != '') {
			$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
		} else {
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');
		}		
		
		$('#mobileDispTmplName').val(data.mobileDispTmplName);
		$('#mobileDispTmplNo').val(data.mobileDispTmplNo);
		
		if(data.useYn == 'Y') {
			$('#radioUseY').prop('checked', true);
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');
		} else {
			$('#radioUseN').prop('checked', true);
		}
		
		if(data.dispYn == 'Y') {
			$('#radioDisplayY').prop('checked', true);
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');
		} else {
			$('#radioDisplayN').prop('checked', true);
		}
		
		
		if(data.ctgrNameDispType == 'I') {
			$('#radioCategoryI').prop('checked', true);
			$('#pcImageArea').show();
			$('#moImageArea').show();
			
			$('#pcImageName').html(data.pcImageName);
			$('#pcAltrnText').val(data.pcAltrnText);
			$('#pcImage').html($('<img>', {src:data.pcImageUrl}));
			
			$('#mobileImageName').html(data.mobileImageName);
			$('#mobileAltrnText').val(data.mobileAltrnText);
			$('#mobileImage').html($('<img>', {src:data.mobileImageUrl}));
			
			$('#pcDelBtn').show();
			$('#mobileDelBtn').show();
		} else if(data.ctgrNameDispType == 'T' || data.ctgrNameDispType == '') {
			$('#radioCategoryT').prop('checked', true);
			$('#pcImageArea').hide();
			$('#moImageArea').hide();
			
			$('#pcImageArea input').val('');
			$('#moImageArea input').val('');
			$('#pcImageArea a').html('');
			$('#moImageArea a').html('');			
			
			$('#pcDelBtn').hide();
			$('#mobileDelBtn').hide();
			
			$('.img-wrap').html('');
		}
	}
	
	/**
	 * 카테고리 등록(row insert)
	 */
	_category.addCtgr = function() {
		
		$('#categoryArea').click();
		$('.tab-wrap').tabs("disable", 1);
		
		var parent = tree.GetSelectRow();
		var parentData = tree.GetRowData(parent);
		
		if(parentData.chnnlNo == abc.consts.SITE_CHNN_ART && parentData.ctgrLevel >= abc.consts.MAX_CTGR_LEVEL_ART) {
			alert(abc.consts.MAX_CTGR_LEVEL_ART + 'depth의 하위 카테고리는 등록할 수 없습니다.');
			return;
		} else if(parentData.chnnlNo != abc.consts.SITE_CHNN_ART && parentData.chnnlNo == abc.consts.SITE_CHNN_GS && parentData.ctgrLevel >= abc.consts.MAX_CTGR_LEVEL_GS) {
			alert(abc.consts.MAX_CTGR_LEVEL_GS + 'depth의 하위 카테고리는 등록할 수 없습니다.');
			return;
		} else if(parentData.chnnlNo != abc.consts.SITE_CHNN_ART && parentData.chnnlNo != abc.consts.SITE_CHNN_GS && parentData.ctgrLevel >= abc.consts.MAX_CTGR_LEVEL_ABC) {
			alert(abc.consts.MAX_CTGR_LEVEL_ABC + 'depth의 하위 카테고리는 등록할 수 없습니다.');
			return;
		}
		
		if($('input[name=leafCtgrYn]:checked').val() == 'Y') {
			alert('Leaf Category에는 하위 카테고리를 등록할 수 없습니다.');
			return;
		}
		
		_category.data.newFlag = true;
		
		abc.biz.display.common.initFormData('ctgrForm', false, ['leafCtgrYn']);
		
		var chnnlName = $('input[name=chnnlName]').val();
		var path = parentData.level == 0 ? chnnlName : chnnlName + ' > ' + parentData.ctgrNamePath;
		
		$('#ctgrPath').html(path);
		
		$('#ctgrNoArea').html('');
		$('#pcDispTmplName').val('');
		$('#pcDispTmplNo').val('');
		$('#mobileDispTmplName').val('');
		$('#mobileDispTmplNo').val('');
		
		$('input[name=ctgrNo]').val('');
		
		$('input[name=siteNo]').val(parentData.siteNo);
		$('input[name=chnnlNo]').val(parentData.chnnlNo);
		$('input[name=upCtgrNo]').val(parentData.ctgrNo);
		$('input[name=ctgrLevel]').val(parentData.ctgrLevel + 1);
		
		$('#imageFile01').val('');
		$('#imageFile02').val('');
		$('.ico-fdel').hide();
		$('.img-wrap').html('');
		$('#pcImageName').html('');
		$('#mobileImageName').html('');
		
		$('#pcImageArea').hide();
		$('#moImageArea').hide();
		
		$('#radioLeafN').prop('checked', true);
		$('#radioFilterN').prop('checked', true);
		
		var child = tree.GetLastChildRow(parent);
		var target = child > 0 ? child + 1 : parent + 1;
		var added = tree.DataInsert(target);
		
		tree.SetRowExpanded(parent, 1);
		tree.SetRowData(added, {
			ctgrName:'새 전시 카테고리'
		});
	}
	
	/**
	 * 카테고리 등록(저장)
	 */
	_category.saveCtgr = function() {
				
		var url = "/display/category/save";
		var form = $.form(document.forms.ctgrForm);
		
		_category.data.focusRow = tree.GetSelectRow();
		
		$('input[name=siteNo]').val($('#chnnlNo option:selected').data('siteNo'));
		
		form.submit({
			url : url,
			method : "POST",
			valid	: function($f){
				return true;
			},
			success : function(data) {
				
				alert('저장되었습니다.');
				_category.data.newFlag = false;
				_category.doActionDpCategory('search');
			},
			error : function(e) {
				if(e != null && e.message != null && e.message != "") {
					alert(e.message);
				}
				
				if (e.fieldName != null && e.fieldName != '') {
					var $el = $('[name='+e.fieldName+']');
					
					if ($el.length > 0) {
						$el.focus();
					}
				}
			}
		});
	}
	
	/**
	 * 카테고리 정렬 순서 변경
	 */
	_category.changeSort = function(direction) {
		var fromRow = tree.GetSelectRow();
		var toRow;
		var focusRow;
		
		if(direction == 'prev') { 
			toRow = tree.GetPrevSiblingRow(fromRow);			
		} else if(direction == 'next') {
			toRow = tree.GetNextSiblingRow(fromRow);
		}
		
		if(toRow > 0) {
			if(tree.GetRowData(fromRow).ctgrNo != '' && tree.GetRowData(toRow).ctgrNo != '') {
				var tmp = tree.GetRowData(toRow).sortSeq;
				tree.SetRowData(toRow, { sortSeq : tree.GetRowData(fromRow).sortSeq });
				tree.SetRowData(fromRow, { sortSeq : tmp });				
			}
			
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
	_category.saveSort = function() {
		
		var url = "/display/category/save/sort";
		var param = { Col : "status", CallBack : function(){
			_category.doActionDpCategory('search');
		}};
		
		tree.DoSave(url, param);
	}
	
	_category.getFrontDomain = function(){
		
		$.ajax({
			type: "POST",
			url: "/display/category/front-domain",
			data: {
				chnnlNo : $('#chnnlNo').val()
			},
			dataType: "json",
			error : function(error){
				alert(error + "error");
			},
			success : function(result){
				_category.data.domain = result;
			}
		});
		
	}
	
	$(function() {
		_category.init();
	});
	
})();