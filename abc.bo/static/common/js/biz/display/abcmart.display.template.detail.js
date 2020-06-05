(function() {

	var _templateDetail = abc.object.createNestedObject(window,"abc.biz.display.template.detail");
	
	/**
	 * 초기화
	 */
	
	_templateDetail.data = {
			corner : []
	};
	
	_templateDetail.init = function(){
		_templateDetail.sheet.init();
		_templateDetail.event();
		
		_templateDetail.data.dispTmplNo = $('#dispTmplNo').val();
		
		//수정일 경우 채널 세팅
		if(_templateDetail.data.dispTmplNo != ''){
			var savingChnnlNo = $('#chnnlNo').data('savingChnnlNo');
			_templateDetail.getChannelListBySiteNo($('#siteNo').val(), savingChnnlNo);
		}
		
		new abc.biz.display.common.processImage({
			file: '#imageFile',
			name: '#imageName'
		});
		
		_templateDetail.getCornerList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_templateDetail.sheet = {};
	_templateDetail.sheet.init = function(){
		createIBSheet2(document.getElementById("cornerSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};

		var sortSeqYn;
		if($("[name=tmplTypeCode]").val() =="10003"){
			sortSeqYn = 1;
		} else {
			sortSeqYn = 0;
		}
		
		initSheet.Cols = [
			 {Header:"", Type:"CheckBox", SaveName:"checked", Width: 5, Align:"Center", 	Edit:1, Sort:0}
			, {Header:"", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Status", SaveName:"status", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Text", SaveName:"setArr", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Text", SaveName:"dispTmplNo", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Text", SaveName:"cornerNameDispType", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Text", SaveName:"setCount", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"", Type:"Text", SaveName:"tmplTypeCode", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"노출순서", Type:"Text", SaveName:"sortSeq", Width: 10, Align:"Center", Edit:sortSeqYn}
			, {Header:"코너번호", Type:"Text", SaveName:"dispTmplCornerSeq", Width: 10, Align:"Center", Edit:0}
			, {Header:"코너명", Type:"Html", SaveName:"cornerName", Width: 20, Align:"Center",  Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"전시대상", Type:"Text", SaveName:"cornerSetName", Width: 10, Align:"Center",  Edit:0}
			, {Header:"사용여부", Type:"Text", SaveName:"useYnName", Width: 10, Align:"Center",  Edit:0}
			, {Header:"사용여부", Type:"Text", SaveName:"useYn", Width: 10, Align:"Center",  Edit:0, Hidden:1}
			, {Header:"코너설명", Type:"Html", SaveName:"noteText", Width: 10, Align:"Center",  Edit:0}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 10, Align:"Center", Edit:0}
			, {Header:"작성일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자", Type:"Text", SaveName:"moderInfo", Width: 10, Align:"Center", Edit:0}
			, {Header:"수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_templateDetail.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_templateDetail.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "cornerName" && value != "" ) {
					
					var param = {
							dispTmplCornerSeq: list.GetRowData(row).dispTmplCornerSeq,
							dispTmplNo: list.GetRowData(row).dispTmplNo,
							selectRow : row
					}
					_templateDetail.openCornerPopup(param);
				}
			}
		}
		
		list_OnSearchEnd = function(){
			for(var i = 0 ; i < list.GetDataRows() ; i++){
				list.SetRowData(i+1, {
					'tmplTypeCode' : $("[name=tmplTypeCode]").val()
				});
			}
			
			for(var inx = 0; inx < list.GetDataLastRow(); inx++){
				if(list.GetRowData(inx+1).sortSeq == 0){
					list.SetCellValue(inx+1, "sortSeq", "");
				}
			}
		}

	}
	
	/**
	 * 이벤트
	 */
	_templateDetail.event = function(){
	
		//사이트 번호 변경 시 채널 조회
		$('#siteNo').on('change', function(e){
			_templateDetail.getChannelListBySiteNo(this.value);
		});
		
		//저장
		$('#save-template').on('click', function(e){
			
			var regexp = /([a-z0-9\w]+\.*)+[a-z0-9]{1,5}/gi;
			if(!regexp.test(frm.tmplUrl.value)){
				alert('템플릿 URL 형식이 아닙니다.');
				return false;
			}			

			var url = '/display/template/save';
			if($('#dispTmplNo').val() != null && $('#dispTmplNo').val() != ''){
				url = '/display/template/modify';
			}

			var cornerWithSet = list.GetSaveJson().data;
			
			$('#append-corner').remove();

			var appendDiv = $('<div>').attr('id', 'append-corner');
			var hidden = $('<input>').attr('type', 'hidden');
			
			var excludeKey = ['rgstDtm', 'modDtm'];
			
			$.each(cornerWithSet, function(i,v){
				$.each(v, function(j, m){
					if(j == 'setArr' && m != ''){
						$.each($.parseJSON(v.setArr), function(k, n){
							$.each(n, function(u, b){
								var cl = hidden.clone();
								appendDiv.append(cl.attr({ 'name' : 'corner['+i+'].set['+k+'].'+u, 'value' : b }));		
							});
						})
					}else{
						if($.inArray(j, excludeKey ) < 0){
							var cl = hidden.clone();
							appendDiv.append(cl.attr({ 'name' : 'corner['+i+'].'+j, 'value' : m }));	
						}
					}
				})

			});
			$(document.frm).append(appendDiv);
			
			
			var cfm = confirm("저장하시겠습니까?");
			
			if(cfm){
			
				var form = $.form(document.forms.frm);
				form.submit({
					url	: url,
					method	: "POST",
					valid	: function($f){
						return true;
					},
					success : function(data){
						alert('저장 되었습니다.');
						location.replace('/display/template/detail?dispTmplNo='+data);
//			    		location.href = 
					},
					error	: function(e){
						alert(e.message);
				    	console.log(e);
					}
				});
			}
			
		});
		
		//코너 등록/수정 팝업 
		$('#open-corner-popup').on('click', function(e){
			_templateDetail.openCornerPopup({});
		});
		
		//코너 삭제
		$('#del-corner').on('click', function(e){
			
			var cfm = confirm("삭제 하시겠습니까?");
			
			if(cfm){
				var rowData = list.ExportData({"Type":"json"}).data;
				
				//수정
				if(_templateDetail.data.dispTmplNo != ''){
					
					var dispTmplCornerSeqArr = [];
					var delRowSeqStr = '';
					$.each(rowData, function(i,v){
						if(v.checked == 1){
							dispTmplCornerSeqArr.push(v.dispTmplCornerSeq);
							delRowSeqStr += v.seq + '|';
						}
					});
					
					if(dispTmplCornerSeqArr.length == 0) return false;
					
					$.ajax({
						dataType : "json",
						type :"POST",
						url : "/display/template/corner/delete",
						data : { dispTmplCornerSeqArr: dispTmplCornerSeqArr, dispTmplNo : frm.dispTmplNo.value}
					})
					.done(function(data){
						alert('삭제 되었습니다.');
						list.RowDelete(delRowSeqStr);
					})
					.fail(function(e){
						alert(e.responseJSON.message);
					});
				}
				//등록
				else{
					
					$.each(rowData, function(i,v){
						if(v.checked == 1){
							list.RowDelete(delRowSeqStr);
						}
					});
				}
			}
			
		});
		
		$("[name=tmplTypeCode]").on('change', function(e){
			list.SetColEditable(8,this.value == '10003' ? 1 : 0);
			
			for(var i = 0 ; i < list.GetDataRows() ; i++){
				list.SetRowData(i+1, {
					'tmplTypeCode' : $("[name=tmplTypeCode]").val()
				});
			}
			
			if(this.value != '10003'){
				for(var inx = 0; inx <= list.GetDataLastRow(); inx++){
					console.log(list.GetCellValue(inx+1));
					list.SetCellValue(inx+1, "sortSeq", "");
				}
			}
			
		});
	}
	
	_templateDetail.openCornerPopup = function(param){
		var pop = abc.open.popup({
			url 	:	"/display/template/corner-pop",
			winname :	"corner-pop",
			method	: 	"get",
			title 	:	"corner-pop",
			width 	:	930,
			height	:	850,
			params	:	param
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_templateDetail.getCornerList = function(){
			
		var pageCount = 15; // 한페이지내 결과 로우 갯수
		var param = { url : "/display/template/corner/list"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.frm)
			, sheet : "list" };
		
		DataSearchPaging(param);
		
	}
	
	/**
	 * 채널 조회
	 */
	_templateDetail.getChannelListBySiteNo = function(siteNo, savingChnnlNo){
			
		$.ajax({
			type :"get",
			url : "/display/template/get-channel-info",
			data : {"siteNo" : siteNo},
		})
		.done(function(data){
			
			//채널 정보 초기화
			$('#chnnlNo').append('')
			
			var channelInfoTempTmpl = $('<div><option>');
			channelInfoTempTmpl.children('option').attr('value' , '').text('채널 선택');
			
			if(data.length > 0){
				
				$.each(data, function(i,v){
					var option = $('<option>').text(v.chnnlName).attr('value', v.chnnlNo);
					if(savingChnnlNo == v.chnnlNo){
						option.attr('selected', 'selected');
					}
					channelInfoTempTmpl.append(option);
				});
			}
			
			$('#chnnlNo').html(channelInfoTempTmpl.html());
		})
		.fail(function(e){
			console.log(e);
		});
	}

	/**
	 * 코너 callback
	 */
	_templateDetail.cornerCallback = function(obj, selectRow){
		console.log(obj);
		
		var insertRowNumber = list.RowCount() + 1;
		var initialRowData = {};	// 추가될 행 데이터
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		var seq;
		
		if(selectRow != null){
			seq = list.GetRowData(selectRow).seq;
		}else{
			seq = list.GetRowData(list.RowCount()).seq+1;
		}
		
		$.each(obj.set, function(i,v){
			v.seq = seq;
		});
		
		initialRowData = {
			"setArr": JSON.stringify(obj.set), 
			"cornerName" : obj.cornerName, 
			"cornerNameDispType" : obj.cornerNameDispType, 
			"cornerSetName" : _templateDetail.makeCornerSetName(obj.set), 
			"useYnName" : obj.useYn == 'Y' ? '사용' : '사용안함',
			"useYn" : obj.useYn,
			"setCount" : obj.setCount,
			"noteText" : obj.noteText,
			"tmplTypeCode" : $("[name=tmplTypeCode]").val()
		};

		
		if(selectRow != null){
			list.SetRowData(selectRow, initialRowData);
		}else{
			list.SetRowData(insertRowNumber, initialRowData, rowOption);
			seq = list.GetRowData(insertRowNumber).seq;
		}

//		_templateDetail.data.corner.push(obj);
		
	}
	
	/**
	 * 전시 대상 중복제거 된 이름 생성
	 */
	_templateDetail.makeCornerSetName = function(arr){
		var cornerSetNameArr = [];
		
		$.each(arr, function(i,v){
			if(+v.dispContCount > 0){
				if($.inArray(v.contTypeCodeName, cornerSetNameArr ) == -1) cornerSetNameArr.push(v.contTypeCodeName);
			}
		});
		
		return cornerSetNameArr.toString();
		
	}
	
	/**
	 * 저장할 데이터 생성
	 */
	_templateDetail.makeSaveTemplateData = function(){
		
		var cornerWithSet = list.GetSaveJson().data;
		
		//코너 노출 순서 
		$.each(cornerWithSet, function(i,v){
			if(v.setArr != ''){
				v.set = $.parseJSON(v.setArr);
			}
		});
		
		var jsonData = {};
		/*var arr = $(document.frm).serializeArray();
		
		$.each(arr, function(i,v){
			jsonData[v.name] = v.value;
		});*/
		
		jsonData.dpDisplayTemplate = cornerWithSet;
		
		return jsonData;
	}
	
	_templateDetail.getCornerDetailData = function(row){
		
		var rowData = list.GetRowData(row);
		
		return rowData;
	}

	
	$(function() {
		_templateDetail.init();
	});
	
})();