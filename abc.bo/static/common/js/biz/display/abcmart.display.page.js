(function() {

	var _page = abc.object.createNestedObject(window,"abc.biz.display.page");
	
	/**
	 * 초기화
	 */
	_page.data = { newFlag : false };
	_page.init = function() {
		
		_page.event();
		_page.initDpCategorySheet();
		
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_page.initDpCategorySheet = function() {
		
		// ====================================================================================================
		// 전시 카테고리 목록
		var initDpCategorySheet = {};
		
		initDpCategorySheet.Cfg = { DeferredVScroll: 1, UseJsonTreeLevel:1};
		initDpCategorySheet.Cols = [
				{Header:"전시페이지명", 		Type:"Text",	SaveName:"dispPageName",			TreeCol:1,	TreeCheck: 0, Edit:0, FontUderline:1, Cursor:"Pointer", Sort:0, Width: 100}
			  , {Header:"상태",			Type:"Status",	SaveName:"status",					Hidden:1,	Edit:0}
			  , {Header:"level",		Type:"Text",	SaveName:"level",					Hidden:1,	Edit:0}
			  , {Header:"상위전시카테고리번호",	Type:"Text",	SaveName:"upDispPageNo",			Hidden:1,	Edit:0}
			  , {Header:"전시시작일",		Type:"Date",	SaveName:"dispStartDtm",			Hidden:1,	Edit:0, Format:"yyyy.MM.dd"}
			  , {Header:"코드상세번호",		Type:"Text",	SaveName:"codeDtlNo",				Hidden:1,	Edit:0}
			  , {Header:"코드추가정보",		Type:"Text",	SaveName:"addInfo2",				Hidden:1,	Edit:0}
			  , {Header:"전시페이지번호",	Type:"Text",	SaveName:"dispPageNo",				Hidden:1,	Edit:0}
			  , {Header:"사이트번호",		Type:"Text",	SaveName:"siteNo",					Hidden:1,	Edit:0}
			  , {Header:"채널번호",		Type:"Text",	SaveName:"chnnlNo",					Hidden:1,	Edit:0}
			  , {Header:"전시페이지유형코드",	Type:"Text",	SaveName:"dispPageTypeCode",		Hidden:1,	Edit:0}
			  , {Header:"PC전시템플릿번호",	Type:"Text",	SaveName:"pcDispTmplNo",			Hidden:1,	Edit:0}
			  , {Header:"PC전시템플릿이름",	Type:"Text",	SaveName:"pcDispTmplName",			Hidden:1,	Edit:0}
			  , {Header:"PC전시템플릿이미지",	Type:"Text",	SaveName:"pcPreviewImageUrl",		Hidden:1,	Edit:0}
			  , {Header:"PC전시Url",		Type:"Text",	SaveName:"pcDispUrl",				Hidden:1,	Edit:0}
			  , {Header:"MO전시템플릿번호",	Type:"Text",	SaveName:"mobileDispTmplNo",		Hidden:1,	Edit:0}
			  , {Header:"MO전시템플릿이름",	Type:"Text",	SaveName:"mobileDispTmplName",		Hidden:1,	Edit:0}
			  , {Header:"MO전시템플릿이미지",	Type:"Text",	SaveName:"mobilePreviewImageUrl",	Hidden:1,	Edit:0}
			  , {Header:"MO전시Url",		Type:"Text",	SaveName:"mobileDispUrl",			Hidden:1,	Edit:0}
			  , {Header:"브랜드번호",		Type:"Text",	SaveName:"brandNo",					Hidden:1,	Edit:0}
			  , {Header:"전시여부",		Type:"Text",	SaveName:"dispYn",					Hidden:1,	Edit:0}
			  , {Header:"사용여부",		Type:"Text",	SaveName:"useYn",					Hidden:1,	Edit:0}
			  , {Header:'등록자번호',		Type:"Text",	SaveName:"rgsterNo",				Hidden:1,	Edit:0}
			  , {Header:'등록자이름',		Type:"Text",	SaveName:"rgsterInfo",				Hidden:1,	Edit:0}
			  , {Header:'등록일시',		Type:"Date",	SaveName:"rgstDtm",					Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자번호',		Type:"Text",	SaveName:"moderNo",					Hidden:1,	Edit:0}
			  , {Header:'수정자이름',		Type:"Text",	SaveName:"moderInfo",				Hidden:1,	Edit:0}
			  , {Header:'수정일시',		Type:"Date",	SaveName:"modDtm",					Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("pageSheet"), "tree", "100%", "100%");
		IBS_InitSheet(tree, initDpCategorySheet);
		tree.FitColWidth();
		
		_page.doActionDpPage();
//		tree.SetRowExpanded(1, 1);
	}
	
	_page.doActionDpPage = function(){
		var param = { url : "/display/page/list"
			, subparam : FormQueryStringEnc(document.searchForm)
			, sheet : "tree"
		};
			
		DataSearch(param);
	}
	
	/**
	 * 이벤트
	 */
	_page.event = function(){
		
		tree_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {
			
			if (!_page.data.newFlag) {
				parentRow = Row;
				focus = parentRow + 1;
				
				if(tree.GetRowLevel(Row) > 2) {
					_page.setDpPageData(parentRow);
					$("#saveBtn").show();
					//3뎁스 일 경우만 콘첸츠 등록 할 수 있도록 변경
					if(tree.GetRowLevel(Row) == 2){
						$('.tab-wrap').tabs("disable", 1);
					}
				} else { 
					$('.tab-wrap').tabs("disable", 1);
					_page.initPage();
					$('#saveBtn').hide();
				}
			} else if(_page.data.newFlag && Row != _page.data.newRowIndex) {
				var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
				
				if (cfm) {
					_page.data.newFlag = false;
					tree.RowDelete(_page.data.newRowIndex, 0);
					$('.tab-wrap').tabs("enable", 1);
					tree.SetSelectRow(Row);
					if (tree.GetRowLevel(Row) == 2) _page.setDpPageData(Row);
				} else {
					tree.SetSelectRow(_page.data.newRowIndex);
				}
			}
		}
		
		tree_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			tree.ShowTreeLevel(0);
			tree.ShowTreeLevel(1);
			
			if(_page.isSelectRow > 0){
				tree.SetSelectRow(_page.isSelectRow);
				_page.setDpPageData(_page.isSelectRow); 
			}
			_page.isSelectRow = 0;
//			_page.checkAddPageByChnnlNo();
		}

		
		
		$('.searchTmpl').on('click', function() {
			
			if($('[name=dispYn]:checked').val() == 'Y'){
				if($("[name='dispPageNo']").val() === "") {
					// 최초등록
				} else {
					// 수정
					alert('전시 중인 경우 템플릿은 수정할 수 없습니다.');
					return false;
				}
			}
			
			var deviceCode = $(this).hasClass('forPc') ? '10000' : '10001';
			
			abc.biz.display.common.openTemplatePopup({
				callback : 'abc.biz.display.page.templateCallback',
				deviceCode : deviceCode,
				menuType : 'P',
				chnnlNo : $('select[name=chnnlNo]').val()
			});
		});
		
		/** 페이지 추가 */
		$('#addPage').on('click', function() {
			
			_page.addPage();
		});
		
		/** 페이지 등록 */
		$('#saveBtn').on('click', function() {
			
			_page.savePage();
		});
		
		/** 검색 */
		$('#searchForm select').focus(function() {
			prev_val = $(this).val();
		}).change(function() {
			$('#pageArea').click();
			_page.isSelectRow = 1;
			if (!_page.data.newFlag){
				_page.doActionDpPage();
			} else {
				var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
				
				if (cfm) {
					_page.data.newFlag = false;
					tree.RowDelete(_page.data.newRowIndex, 0);
					_page.doActionDpPage();
					$('.tab-wrap').tabs("enable", 1);
				} else {
					$(this).val(prev_val);
				}
			}	
		});
		
		/** 템플릿 보기 */
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
		
		// 사용여부, 전시여부 변경시 템플릿 삭제 안되도록
		$('#pageForm').on('change', '[name=dispYn]', function(){
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');

			if($('[name=dispYn]:checked').val() == 'N'){
				$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
				$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
			}
		});
	}
	
	/**
	 * 메뉴 grid action
	 */
	/*_page.doActionDpPage = function(focusRow){
		
		tree.RemoveAll();
		
		// root
		var idx = tree.DataInsert();
		var chnnl = $('select[name=chnnlNo] option:selected');
		var page = $('select[name=dispPageTypeCode] option:selected').val();
		tree.SetRowData(idx, {
			dispPageName : chnnl.text()
		});
		
		var codes = abc.biz.display.page.combo["DISP_PAGE_TYPE_CODE"].code.split('|');
		var names = abc.biz.display.page.combo["DISP_PAGE_TYPE_CODE"].text.split('|');
		
		if(page == '') {
			$.each(codes, function(i, v) {
				var type = tree.DataInsert(++idx, 1);
				tree.SetRowData(type, {
					dispPageName : names[i],
					dispPageTypeCode : codes[i],
					chnnlNo : chnnl.val()
				});
				
				tree.SetRowExpanded(type, 0);
				var param = {
					'dispPageTypeCode' : codes[i],
					'chnnlNo' : chnnl.val(),
					'useYn' : $('#searchForm select[name=useYn]').val()
				};
				_page.getPageData(param, type);
				
			});			
		} else {
			var code = codes.indexOf(page);
			
			var type = tree.DataInsert(++idx, 1);
			tree.SetRowData(type, {
				dispPageName : names[code],
				dispPageTypeCode : codes[code],
				chnnlNo : chnnl.val()
			});
			
			tree.SetRowExpanded(type, 0);
		}
		
		tree.SetSelectRow(focusRow == null ? 1 : focusRow);
	}
	
	_page.getPageData = function(param, type){
		
		var parent = type;
		
		$.ajax({
			dataType : "json",
			type :"POST",
			url : '/display/page/list',
			data : param,
			async : false
		})
		.done(function(d){
			
			if(d.length > 0) {
				$.each(d, function(i, v){
					v.rgstDtm = new Date(v.rgstDtm).format(abc.consts.DEFAULT_DATETIME_PATTERN);
					v.modDtm = new Date(v.modDtm).format(abc.consts.DEFAULT_DATETIME_PATTERN);
					v.dispStartDtm = new Date(v.dispStartDtm).format("yyyy.MM.dd");
					
					var page = tree.DataInsert(++type, v.level);
					tree.SetRowData(page, v);
				});
				
				tree.SetRowExpanded(parent, 0);
				$('#saveBtn').show();
			}else{
				$('#saveBtn').hide();
			}
		})
		.fail(function(e){
			alert(e.responseJSON.message);
			console.log(e);
		});		
		
	}*/
	
//	_page.checkAddPageByChnnlNo = function(){
//		if($('select[name=chnnlNo]').val() == '10000'){
//			$('#addPage').hide();
//		} else {
//			$('#addPage').show();
//		}
//	}
	
	/**
	 * 템플릿 popup callback
	 */
	_page.templateCallback = function(data) {
		
		if(data.deviceName == 'Mobile') {
			$('#mobileDispTmplName').val(data.tmplName);
			$('#mobileDispTmplNo').val(data.dispTmplNo);
			$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
			abc.biz.display.page.contents.doActionDpContents("mobile");
		} else {
			$('#pcDispTmplName').val(data.tmplName);
			$('#pcDispTmplNo').val(data.dispTmplNo);
			$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
			abc.biz.display.page.contents.doActionDpContents("pc");
		}
		
		if($('#mobileDispTmplNo').val() != '' || $('#pcDispTmplNo').val() != '') {
			$('.tab-wrap').tabs("disable", 1);
		} else {
			$('.tab-wrap').tabs("enable", 1);
		}
	}
	
	/**
	 * 선택된 그리드정보를 메뉴정보 영역에 세팅
	 */
	_page.setDpPageData = function(Row) {
		
		$('#pageArea').click();
	
		var data = tree.GetRowData(Row);
		data.dispStartDtm = _page.convertDispStartDtm(data.dispStartDtm);
		data.rgstDtm = _page.convertRgstModDtm(data.rgstDtm);
		data.modDtm = _page.convertRgstModDtm(data.modDtm);
		
		console.log(data);
		
		var chnnl = $('select[name=chnnlNo] option:selected').text();
		var pRow = tree.GetParentRow(Row);
		var type = tree.GetRowData(pRow).dispPageName;
		var upType = tree.GetRowData(tree.GetParentRow(pRow)).dispPageName;
		console.log(upType);
		
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
		
		$('input[name=dispPageNo]').val(data.dispPageNo);
		$('input[name=upDispPageNo]').val(data.upDispPageNo);
		$('input[name=dispStartDtm]').val(data.dispStartDtm);
		$('input[name=dispPageTypeCode]').val(data.dispPageTypeCode);
		$('input[name=siteNo]').val(data.siteNo);
		$('input[name=chnnlNo]').val(data.chnnlNo);
		$('input[name=pcDispUrl]').val(data.pcDispUrl);
		$('input[name=mobileDispUrl]').val(data.mobileDispUrl);
		
		if(Row > 1){
			$('#pagePath').html(chnnl + ' > ' + upType + ' > ' + type);
			$('#dispPageNoField').html(data.dispPageNo);
			$('input[name=dispPageName]').val(data.dispPageName);
		}else{
			$('#pagePath').html('');
			$('#dispPageNoField').html('');
			$('input[name=dispPageName]').val('');
			$("#saveBtn").hide();
		}
//			$('#upDispPageNoField').html(data.upDispPageNo);
		
		$('#pcDispTmplNo').val(data.pcDispTmplNo);
		$('#pcDispTmplName').val(data.pcDispTmplName);
		if (data.pcDispTmplNo != null && data.pcDispTmplNo != '') {
			$('.pc-disp-tmpl-row .ip-search-box').addClass('selected');
		} else {
			$('.pc-disp-tmpl-row .ip-search-box').removeClass('selected');
		}
		$('#mobileDispTmplNo').val(data.mobileDispTmplNo);
		$('#mobileDispTmplName').val(data.mobileDispTmplName);
		if (data.mobileDispTmplNo != null && data.mobileDispTmplNo != '') {
			$('.mobile-disp-tmpl-row .ip-search-box').addClass('selected');
		} else {
			$('.mobile-disp-tmpl-row .ip-search-box').removeClass('selected');
		}
		

		if(data.useYn == 'Y') {
			$('#radioUseY').prop('checked', true);
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
		
		
		$('#rgsterName').html(data.rgsterInfo);
		$('#moderName').html(data.moderInfo);
		$('#rgstDtm').html(data.rgstDtm);
		$('#modDtm').html(data.modDtm);
		
		// 전시 컨텐츠 관리 영역
		if(data.pcDispTmplNo != '' || data.mobileDispTmplNo != '') {
			$('.tab-wrap').tabs("enable", 1);
			
			abc.biz.display.page.contents.doActionDpContents("pc");
			abc.biz.display.page.contents.doActionDpContents("mobile");
		} else {
			$('.tab-wrap').tabs("disable", 1);
		}		
	}
	
	//전시 시작일 셋
	_page.convertDispStartDtm = function(dateP){
		dateP = dateP.slice(0,4)+"."+dateP.slice(4);
		dateP = dateP.slice(0,7)+"."+dateP.slice(7);
		
		return dateP;
	}
	
	//작성자 수정자 셋
	_page.convertRgstModDtm = function(dateP){
		dateP = dateP.slice(0,4)+"."+dateP.slice(4);
		dateP = dateP.slice(0,7)+"."+dateP.slice(7);
		dateP = dateP.slice(0,10)+" "+dateP.slice(10);
		dateP = dateP.slice(0,13)+":"+dateP.slice(13);
		dateP = dateP.slice(0,16)+":"+dateP.slice(16);
		
		return dateP;
	}
	/**
	 * 전시 페이지 추가 (row insert)
	 */
	_page.addPage = function() {
		
		if(_page.data.newFlag) {
			alert('이미 등록중인 페이지가 있습니다.');
			return;
		}
		
		var parent = tree.GetSelectRow();
		var parentData = tree.GetRowData(parent);
		var rowLevel = tree.GetRowLevel(parent); 
		var depth1Row = tree.GetParentRow(parent);
		
		if(rowLevel < 1) {
			alert('템플릿 유형을 선택 후 클릭해주세요.');
			return;
		} else if(rowLevel == 1) {
			alert('페이지 유형은 공통코드에서 등록 가능합니다.');
			return;
		} else if(rowLevel > 2) {
			alert('마지막 페이지 입니다.');
			return;
		}
		
		if( $('select[name=chnnlNo] option:selected').val() != abc.consts.SITE_CHNN_ART){
			if(parentData.addInfo2 == abc.consts.BOOLEAN_TRUE){
				alert("해당페이지는 A-RT페이지에서 관리 됩니다.");
				return;
			}
		}
		
		$('#pageArea').click();
		$('.tab-wrap').tabs("disable", 1);
		
		_page.data.newFlag = true;
		
		abc.biz.display.common.initFormData('pageForm', false);
		
		var chnnl = $('select[name=chnnlNo] option:selected').text();
		
		_page.initPage();
		_page.getSelectedSiteNoByChnnl();
		$('#pagePath').html(chnnl + ' > ' + tree.GetRowData(depth1Row).dispPageName + ' > ' + parentData.dispPageName);
		/*$('#upDispPageNoField').text(parentData.dispPageNo);
		$('input[name=upDispPageNo]').val(parentData.dispPageNo);
		$('input[name=siteNo]').val(parentData.siteNo);						//기존
		$('input[name=chnnlNo]').val(parentData.chnnlNo);
		$('input[name=dispPageTypeCode]').val(parentData.dispPageTypeCode);
		*/
		$('input[name=dispPageTypeCode]').val(parentData.codeDtlNo);
		
		var child = tree.GetLastChildRow(parent);
		var target = child > 0 ? child + 1 : parent + 1;
		var added = tree.DataInsert(target);		
		
		tree.SetRowExpanded(parent, 1);
		
		if (rowLevel == 1) {
			target++;
		}
		
		tree.SetRowData(target, {
			dispPageName:'전시페이지'
		}, {Level : tree.GetRowLevel(tree.GetSelectRow())+1});
		
		_page.data.newRowIndex = added;	
		
		$('#saveBtn').show();
	}
	
	_page.initPage = function(){
		
		$('#pagePath').html('');
		$('#dispPageNoField').html('');
		$('#upDispPageNoField').html('');
		$('#rgsterName').html('');
		$('#rgstDtm').html('');
		$('#moderName').html('');
		$('#modDtm').html('');
		$('#pcDispTmplName').val('');
		$('#pcDispTmplNo').val('');
		$('#mobileDispTmplName').val('');
		$('#mobileDispTmplNo').val('');
		$('#upDispPageNoField').text('');
		
		$('input[name=dispPageNo]').val('');
		$('input[name=pcDispUrl]').val('');
		$('input[name=mobileDispUrl]').val('');
		$('input[name=upDispPageNo]').val('');
		$('input[name=dispStartDtm]').val('');
		$('input[name=dispPageName]').val('');
		$('input[name=upDispPageNo]').val('');
		$('input[name=siteNo]').val('');
		$('input[name=chnnlNo]').val('');
		$('input[name=dispPageTypeCode]').val();
	}
	
	_page.getSelectedSiteNoByChnnl = function(){
		var chnnlNo = $('select[name=chnnlNo] option:selected').val();
		$.ajax({
			type :"get",
			url : '/display/page/getSelectedSiteNoByChnnl?chnnlNo='+chnnlNo,
			async : false
		}).done(function(data, textStatus, jqXHR) {
			$('input[name=siteNo]').val(data);
			$('input[name=chnnlNo]').val(chnnlNo);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	_page.isSave = false;
	
	/**
	 * 전시 페이지 저장
	 */
	_page.savePage = function() {
		
		var url = $('input[name=dispPageNo]').val() != '' ? 'modify' : 'add';
		var form = $.form(document.forms.pageForm);
		
		form.submit({
			url : '/display/page/' + url,
			method : "POST",
			valid	: function($f){
				return true;
			},
			success : function(data) {
				
				alert('저장되었습니다.');
				_page.data.newFlag = false;
								
				var selectRow = tree.GetSelectRow(); // 추가된 행 
				
				_page.doActionDpPage();
				
//				_page.setDpPageData(selectRow);	// 등록된 정보로 새로고침. 현업요청.
				_page.isSelectRow = selectRow;	//등록된 정보로 새로 고침 현업 요청
				
				$('#saveBtn').show();
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			}
		});
	}
	
	$(function() {
		_page.init();
	});
	
})();