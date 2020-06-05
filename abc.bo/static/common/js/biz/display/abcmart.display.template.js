(function() {

	var _template = abc.object.createNestedObject(window,"abc.biz.display.template");
	
	/**
	 * 초기화
	 */
	_template.init = function(){

		abc.biz.display.common.checkBoxAll();
		
		//200102 localStorage 채널 데이터 선택 안됨에 따른 수정
		var storageData = $.parseJSON(getLocalStorage("param"));
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		//200102 localStorage 채널 데이터 선택 안됨에 따른 수정
		if(storageData != null){
			_template.getChannelListBySiteNo(storageData[0].value, storageData[1].value); // siteNo, chnnlNo
		}
		
		_template.sheet.init();
		_template.event();
		
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_template.sheet = {};
	_template.sheet.init = function(){
		createIBSheet2(document.getElementById("templateSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header:"번호", Type:"Seq", SaveName:"", Width: 5, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"", Type:"Text", SaveName:"", Width: 10, Align:"Center", Edit:0, Hidden:1, Sort:0}
			, {Header:"사이트" , Type:"Text", SaveName:"siteName", Width: 10,  Align:"Left", Edit:0, FontBold:1, Sort:0}
			, {Header:"채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"템플릿 유형", Type:"Text", SaveName:"tmplTypeName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"디바이스", Type:"Text", SaveName:"deviceName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"템플릿코드", Type:"Text", SaveName:"dispTmplNo", Width: 10, Align:"Center",  Edit:0, Sort:0}
			, {Header:"템플릿명", Type:"Text", SaveName:"tmplName", Width: 20, Align:"Center",  Edit:0, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"사용여부", Type:"Text", SaveName:"useYnName", Width: 10, Align:"Center",  Edit:0, Sort:0}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"작성일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자", Type:"Text", SaveName:"moderInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_template.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_template.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "tmplName" && value != "" ) {
					abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					var url = "/display/template/detail?dispTmplNo=";
					location.href = url + list.GetRowData(row).dispTmplNo;
				}
			}
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_template.event = function(){
		
		//사이트 번호 변경 시 채널 조회
		$('#siteNo').on('change', function(e){
			_template.getChannelListBySiteNo(this.value);
		});
		
		$(document.forms.searchForm).on('submit', function(){
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_template.getList();
			return false;
		});
		
		// 검색조건 초기화
		$('#clear-form').on('click', function(e){
			$('#searchForm')[0].reset();
			$('#siteNo').trigger('change'); // 사이트 채널 selectbox 초기화
		});
		
		$('#selTermsModule').on('change', function(e){
			_template.getList();
		})
		
	}
	
	/**
	 * 채널 조회
	 */
	_template.getChannelListBySiteNo = function(siteNo, savingChnnlNo){
			
		$.ajax({
			type :"get",
			url : "/display/template/get-channel-info",
			data : {"siteNo" : siteNo},
			async: false
		})
		.done(function(data){
			
			//채널 정보 초기화
			$('#chnnlNo').append('')
			
			var channelInfoTempTmpl = $('<div><option>');
			channelInfoTempTmpl.children('option').attr('value' , '').text('채널 전체');
			
			if(data.length > 0){
				
				$.each(data, function(i,v){
					var option = $('<option>').text(v.chnnlName).attr('value', v.chnnlNo);
					channelInfoTempTmpl.append(option);
				});
			}
			
			$('#chnnlNo').html(channelInfoTempTmpl.html());
			
			//200102 localStorage 채널 데이터 선택 안됨에 따른 수정
			if(!abc.text.allNull(savingChnnlNo)){
				$("#chnnlNo").val(savingChnnlNo);
			}
		})
		.fail(function(e){
			console.log(e);
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_template.getList = function(){

			
		var pageCount = $('#selTermsModule').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/display/template/list"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list"
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_template.init();
		_template.getList();
	});
	
})();