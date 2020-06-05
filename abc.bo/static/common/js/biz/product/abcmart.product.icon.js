(function() {

	var _icon = abc.object.createNestedObject(window,"abc.biz.product.icon");
	
	/**
	 * 초기화
	 */
	_icon.init = function(){
		
		this.initIconSheet();
		this.event();
	}
	
	/**
	 *  IBSheet 초기화(상품아이콘 목록)
	 */
	_icon.initIconSheet = function() {
		createIBSheet2(document.getElementById("iconSheet"), "list", "100%", "370px");
		var pageCount = $('#pageCount').val();
		
		var initIconSheet = {};
		initIconSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initIconSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initIconSheet.Cols = [
				{Header:"NO", 		Type:"Seq",		SaveName:"seq",					Width:20,	Align:"Center"}
			  , {Header:"상태",		Type:"Status",	SaveName:"status",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"아이콘순번",	Type:"Text",	SaveName:"prdtIconSeq",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"사이트구분",	Type:"Combo",	SaveName:"siteNo",				Width:50,	Align:"Center",	Edit:1}
			  , {Header:"아이콘코드",	Type:"Text",	SaveName:"insdMgmtInfoText",	Width:40,	Align:"Center",	Edit:1, KeyField:true}
			  , {Header:"아이콘명",		Type:"Text",	SaveName:"dispIconName",		Width:60,	Align:"Center",	Edit:1, KeyField:true, Cursor:'Pointer'}
			  , {Header:"아이콘이미지",	Type:"Text",	SaveName:"iconName",			Width:60,	Align:"Center",	Hidden:1,	Edit:1, KeyField:true}
			  , {Header:"아이콘이미지",	Type:"Html",	SaveName:"iconImg",				Width:190,	Align:"Left",	Edit:0, KeyField:true}
			  , {Header:"아이콘경로",	Type:"Text",	SaveName:"iconPathText",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"아이콘URL",	Type:"Text",	SaveName:"iconUrl",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"대체텍스트",	Type:"Text",	SaveName:"altrnText",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"아이콘이름",	Type:"Text",	SaveName:"imageName",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:"적용순서",		Type:"Int",		SaveName:"applyPrior",			Width:30,	Align:"Center",	Edit:1, KeyField:true}
			  , {Header:"사용여부",		Type:"Combo",	SaveName:"useYn",				Width:40,	Align:"Center",	Edit:1, ComboText:"사용|사용안함", ComboCode : "Y|N"}
			  , {Header:"적용구분",		Type:"Combo",	SaveName:"autoApplyYn",			Width:30,	Align:"Center",	Edit:0, ComboText:"수동|자동", ComboCode : "N|Y"}
			  , {Header:'등록자이름',	Type:"Text",	SaveName:"rgsterInfo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록자번호',	Type:"Text",	SaveName:"rgsterNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록일시',		Type:"Date",	SaveName:"rgstDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'최종수정자',	Type:"Text",	SaveName:"moderInfo",			Width:60,	Align:"Center",	Edit:0}
			  , {Header:'최종수정자번호',	Type:"Text",	SaveName:"moderNo",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'최종수정일시',	Type:"Date",	SaveName:"modDtm",				Width:80,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list, initIconSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		list.SetDataRowHeight(50);
		
		list.InitDataCombo(0, "siteNo", this.siteCombo.text, this.siteCombo.code);
		
		this.doActionIcon("search");
		
		// 아이콘 대상 상품 리스트
		createIBSheet2(document.getElementById("prdtSheet"), "prdtList", "100%", "370px");
		var prdtPageCount = $('#prdtPageCount').val();
		
		var initPrdtSheet = {};
		initPrdtSheet.Cfg = {SearchMode:smServerPaging2, Page:prdtPageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initPrdtSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initPrdtSheet.Cols = [
				{Header:"온라인상품코드",	Type:"Text",	SaveName:"prdtNo",			Width:20,	Align:"Center", Edit:0}
			  , {Header:"상품이미지",	Type:"Image",	SaveName:"imageUrl",		Width:30,	Align:"Center",	Edit:0, ImgHeight: 50}
			  , {Header:"표준카테고리",	Type:"Text",	SaveName:"stdCtgrName",		Width:20,	Align:"Center",	Edit:0}
			  , {Header:"전시채널",		Type:"Text",	SaveName:"chnnlName",		Width:20,	Align:"Center",	Edit:0}
			  , {Header:"판매상태",		Type:"Combo",	SaveName:"sellStatCode",	Width:20,	Align:"Center",	Edit:0}
			  , {Header:"전시여부",		Type:"Combo",	SaveName:"dispYn",			Width:20,	Align:"Center",	Edit:0, ComboText:"전시|전시안함", ComboCode : "Y|N"}
		];
		
		IBS_InitSheet(prdtList, initPrdtSheet);
		prdtList.SetCountPosition(3);
		prdtList.SetPagingPosition(2);
		prdtList.FitColWidth();
		prdtList.SetExtendLastCol(1);
		prdtList.SetDataRowHeight(50);
		
		prdtList.InitDataCombo(0, "sellStatCode", this.codeCombo.SELL_STAT_CODE.text, this.codeCombo.SELL_STAT_CODE.code);
				
	}
	
	/**
	 * 메뉴 grid action
	 */
	_icon.doActionIcon = function(sAction){
		switch (sAction) {
		case "search":			
			var pageCount = $("#pageCount").val();
			var param = { url : "/product/icon/list/read"
						, sheet : "list"
						, onePageRow : pageCount
						, subparam : 'rowsPerPage='+$("#pageCount").val()
			};
			DataSearchPaging(param);
			break;
		case "product":
					
			var seq = list.GetRowData(list.GetSelectRow()).prdtIconSeq;
			
			var prdtPageCount = $("#prdtPageCount").val();
			var prdtParam = { url : "/product/icon/mapping-product/list"
				, sheet : "prdtList"
				, onePageRow : prdtPageCount
				, subparam : 'prdtIconSeq=' + seq
			};
			DataSearchPaging(prdtParam);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_icon.event = function() {
		
		list_OnRowSearchEnd = function(row) {
			
			abc.biz.product.icon.setImageCol(row, row, row);
			
			if(list.GetCellValue(row,"autoApplyYn") == "Y" ){
				list.SetRowData(row, {button : '수동전환'}, {StatusMode : 0});
				list.SetCellEditable(row, "insdMgmtInfoText", 0);
			}
		}
		
		list_OnClick = function(Row, Col) {
			
			if(Row > 0 && list.ColSaveName(Col) == "dispIconName") {
				
				var seq = list.GetRowData(list.GetSelectRow()).prdtIconSeq;
				
				if(seq != null && seq != '') {
					
					_icon.doActionIcon('product');
				}				
			}
		}
		
		list_OnSearchEnd = function() {
			/** 이미지 업로드 */
			$(document).on("change", "input[id^=inputFile]", function() {
				if(this.files && this.files[0]) {
					
					var ext = this.files[0].name.split('.').splice(-1)[0];
					if(ext != null) ext = ext.toLocaleLowerCase();
					
					if("jpg, jpeg, png, gif, bmp".indexOf(ext) < 0){
						alert('허용되지 않는 파일입니다.');
						return false;
					}
					
					
					thumbnail = $(this).parents('li').find('.iconImage');
					thumbnail.attr('src', '');
					
					var reader = new FileReader();
					reader.onload = function(e){
						thumbnail.attr('src', e.target.result);
					}
					
					reader.readAsDataURL(this.files[0]);
									
					var form = document.getElementById('iconImageForm'+list.GetRowData(list.GetSelectRow()).prdtIconSeq);
					
					var url = "/product/icon/save/image";
					var imageForm = $.form(form);
					
					imageForm.submit({
						url	: url,
						method	: "POST",
						async : false,
						success : function(data){
							
							var row = list.GetSelectRow();
							list.SetRowData(row, { iconName:data.iconName, iconPathText:data.iconPathText, iconUrl:data.iconUrl });
							abc.biz.product.icon.setImageCol(row, row, row);
						},
						error	: function(e){
							alert(e.message);
					    	console.log(e);
						}
					});				
				}
			});
			
			/** 이미지 팝업 */
			$(document).on("click", "img[id^=iconImage]", function() {
				
				if (list.GetRowData(list.GetSelectRow()).iconUrl == '' || list.GetRowData(list.GetSelectRow()).iconUrl == null) {
					return false;
				}
				
				var url = list.GetRowData(list.GetSelectRow()).iconUrl;
				var params = {};

				abc.open.popup({
							url 	:	url,
							winname :	"icon-image-pop",
							method	: 	"get",
							title 	:	"아이콘 이미지",
							//width 	:	500,
							//height	:	500,
							params	:	params
				});
			});
			
			/** 이미지 삭제 */
			$(document).on('click', '.icon-image-del', function() {			
				// 데이터 삭제
				list.SetRowData(list.GetSelectRow(), { iconName:'', iconPathText:'', iconUrl:''  });
				
				var selector = '#iconImageDelBtn'+list.GetSelectRow();
				
				$(selector).siblings().html(''); // 이미지명 초기화
				$(selector).parents('ul').find('img').attr('src', ''); // 미리보기 초기화
				$(selector).parents('ul').find('input[type=file]').val(''); // 파일 input 초기화
				$(selector).hide(); // 삭제버튼 hide
			});
		}
		
		$("#pageCount").on("change", function() {
			
			abc.biz.product.icon.initIconSheet();
		});
		
		
		$("#addIconBtn").on("click", function() {
						
			list.DataInsert(-1);
			
			var row = list.GetSelectRow();
			abc.biz.product.icon.setImageCol(row, row, list.GetDataLastRow() + 1);
		});
		
		$("#saveIconBtn").on("click", function() {
			
			abc.biz.product.icon.saveIcon();
		});		
	}
	
	/**
	 * 상품아이콘 등록/수정
	 */
	_icon.saveIcon = function() {
		
		var dupIconCode = list.ColValueDup('insdMgmtInfoText');
		if(dupIconCode > 0) {
			alert('아이콘코드값은 중복될 수 없습니다. ('+String(dupIconCode) + '번째 행)');			
			return false;
		}
		
		var dupApplyPrior = list.ColValueDup('applyPrior');
		if(dupApplyPrior > 0) {
			alert('적용 순위는 중복될 수 없습니다. ('+String(dupApplyPrior) + '번째 행)');			
			return false;
		}
		
		var url = '/product/icon/save';
		var param = { 'Col' : 'status', 'CallBack' : function() {
			_icon.doActionIcon('search');
		} };
		
		list.DoSave(url, param);
	}
	
	/**
	 * 첨부파일 Html 설정
	 */
	_icon.setImageCol = function(targetRow, originalRow, index) {
		
		var tmpl = $('#icon-image-upload-tmpl').clone();
		tmpl = $(tmpl.html());
		tmpl = $(tmpl);
		
		tmpl.find('form').attr('id', 'iconImageForm'+list.GetRowData(originalRow).prdtIconSeq);
		tmpl.find('img').attr('src', list.GetRowData(originalRow).iconUrl).css({width : '100', height : '100'});
		tmpl.find('img').attr('id', 'iconImage' + index);
		tmpl.find('#imageName').html(list.GetRowData(originalRow).iconName);
		tmpl.find('input[type=file]').attr('id', 'inputFile' + index);
		tmpl.find('input[type=hidden]').val(list.GetRowData(originalRow).iconPathText);
		tmpl.find('label').attr('for', 'inputFile' + index);
		tmpl.find('.icon-image-del').attr('id', 'iconImageDelBtn'+index)
		
		if (list.GetRowData(originalRow).prdtIconSeq == '') {
			tmpl.find('.icon-image-del').hide();
		}
		
		list.SetRowData(targetRow, { iconImg : tmpl.html()}, {StatusMode : 0});
	}
	
	$(function() {
		
		_icon.init();
	});
	
})();