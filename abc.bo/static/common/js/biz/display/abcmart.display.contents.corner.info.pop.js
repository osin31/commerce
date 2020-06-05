(function() {

	var _cornerInfoPop = abc.object.createNestedObject(window,"abc.biz.display.content.corner.info.pop");

	_cornerInfoPop.data = { selectGridId : '', sellStatCodes : {}};

	/**
	 * 초기화
	 */
	_cornerInfoPop.init = function(){

		var chkObj = opener.location.pathname.indexOf('category') > -1 ? [$('#dispTmplNo'), '전시카테고리관리'] : [$('#dispPageNo'), '전시페이지관리'];
		if(abc.text.isBlank(chkObj[0].val())){
			alert('상위 페이지 정보를 찾을 수 없습니다. '+chkObj[1]+' 새로고침 후 다시 등록 부탁드립니다.');
			opener.location.reload();
			window.close();
		}

		_cornerInfoPop.sheet.init();
		_cornerInfoPop.event();

		//상품상태코드 설정
		var sellStatCodes = $.parseJSON(document.searchForm.codeMap.value).SELL_STAT_CODE;
		var codes = sellStatCodes.code.split('|');
		var texts = sellStatCodes.text.split('|');

		$.each(codes, function(i,v) {
			_cornerInfoPop.data.sellStatCodes[v] = texts[i];
		})
	}


	/**
	 *  IBSheet 초기화
	 */
	_cornerInfoPop.sheet = {id : {}};
	_cornerInfoPop.sheet.init = function(){

		$('.ibsheet-wrap').children().each(function(i,v){
			var id = $(this).attr('id');
			var contTypeCode = $(this).data('contTypeCode');
			var setSeq = $(this).data('setSeq');

			_cornerInfoPop.sheet.create(id, contTypeCode.toString(), setSeq);
		});

		_cornerInfoPop.sheet.event()
	}

	/**
	 * ib sheet 동적으로 생성
	 */
	_cornerInfoPop.sheet.create = function(id, contTypeCode, setSeq){

		createIBSheet2(document.getElementById(id), id, "100%", "250px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();
		var url = '/display/contents/popup/corner-set/list';

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};

		initSheet.Cols = [
			 {Header:"", Type:"CheckBox", SaveName:"checked", Width: 5, Align:"Center", Edit:1}
			, {Header:"노출순서", Type:"Text", SaveName:"sortSeq", Width: 6, Align:"Center", Edit:1}
			, {Header:"", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Hidden:1}
			, {Header:"", Type:"Status", SaveName:"status", Width: 10, Align:"Center", Hidden:1}
			, {Header:"", Type:"Text", SaveName:"contTypeCode", Width: 10, Align:"Center", Hidden:1}
			, {Header:"", Type:"Text", SaveName:"contTypeSeq", Width: 10, Align:"Center", Hidden:1}
			, {Header:"전시여부", Type:"Text", SaveName:"dispYn", Width: 10, Align:"Center", Hidden:1}
		];

		var cols = [];
		var endCols = [
			  {Header:"전시시작일", Type:"Date", SaveName:"dispStartYmd", Width: 15, Align:"Center", Edit:1, Format:"yyyy.MM.dd"}
			, {Header:"전시종료일", Type:"Date", SaveName:"dispEndYmd", Width: 15, Align:"Center", Edit:1, Format:"yyyy.MM.dd"}
		];

		document.searchForm.contTypeCode.value = contTypeCode;
		document.searchForm.dispTmplCornerSetSeq.value = setSeq;

		switch (contTypeCode) {
		case '10000':
			//전시세트명
			cols = [
				 {Header:"전시세트명", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			];

			break;
		case '10001':
			//상품
			cols = [
				 {Header:"상품코드", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0}
				, {Header:"이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight:100, ImgWidth: 100 }
				, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 10, Align:"Center", Edit:0}
				, {Header:"자사(입점사)", Type:"Text", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0}
				, {Header:"채널(사이트)", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
				, {Header:"카테고리", Type:"Text", SaveName:"stdrCtgrName", Width: 10, Align:"Center", Edit:0}
				, {Header:"판매상태", Type:"Text", SaveName:"sellStatCode", Width: 10, Align:"Center", Edit:0}
				, {Header:"주문가능수량", Type:"Text", SaveName:"orderPsbltQty", Width: 10, Align:"Center", Edit:0}
				, {Header:"옵션가용률", Type:"Text", SaveName:"availabilityRate", Width: 10, Align:"Center", Edit:0}
				, {Header:"정상가", Type:"Text", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
				, {Header:"판매가", Type:"Text", SaveName:"sellAmt", Width: 10, Align:"Center", Edit:0}
				, {Header:"할인율", Type:"Text", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
			];
			break;
		case '10002':
			//이미지
			cols = [
				 {Header:"제목", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"이미지", Type:"Image", SaveName:"imageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight:100, ImgWidth: 100 }
				, {Header:"이미지명", Type:"Text", SaveName:"addInfo2", Width: 10, Align:"Center", Edit:0}
				, {Header:"연결 URL", Type:"Text", SaveName:"addInfo7", Width: 15, Align:"Center", Edit:0}
				, {Header:"", Type:"Text", SaveName:"addInfo4", Width: 10, Align:"Center", Hidden:1}
				, {Header:"", Type:"Text", SaveName:"addInfo3", Width: 10, Align:"Center", Hidden:1}
				, {Header:"텍스트컬러코드", Type:"Text", SaveName:"addInfo8", Width: 10, Align:"Center", Hidden:1}
				, {Header:"텍스트컬러", Type:"Text", SaveName:"addInfo8Name", Width: 10, Align:"Center", Edit:0}
				, {Header:"대체텍스트", Type:"Text", SaveName:"altrnText", Width: 10, Align:"Center", Hidden:1}
				, {Header:"target", Type:"Text", SaveName:"addInfo6", Width: 10, Align:"Center", Hidden:1}
				, {Header:"연결방법", Type:"Text", SaveName:"addInfo5", Width: 10, Align:"Center", Hidden:1}
				, {Header:"배경컬러", Type:"Text", SaveName:"addInfo9", Width: 10, Align:"Center", Hidden:1}
				, {Header:"컬러값", Type:"Text", SaveName:"addInfo10", Width: 10, Align:"Center", Hidden:1}
			];
			break;
		case '10003':
			//동영상
			cols = [
				 {Header:"제목", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"동영상 섬네일", Type:"Image", SaveName:"imageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight:100, ImgWidth: 100 }
				, {Header:"이미지명", Type:"Text", SaveName:"addInfo2", Width: 10, Align:"Center", Edit:0}
				, {Header:"연결 URL", Type:"Text", SaveName:"addInfo9", Width: 10, Align:"Center", Edit:0}
				, {Header:"", Type:"Text", SaveName:"addInfo4", Width: 10, Align:"Center", Hidden:1}
				, {Header:"", Type:"Text", SaveName:"addInfo3", Width: 10, Align:"Center", Hidden:1}
				, {Header:"연결방법", Type:"Text", SaveName:"addInfo5", Width: 10, Align:"Center", Hidden:1}
				, {Header:"target", Type:"Text", SaveName:"addInfo6", Width: 10, Align:"Center", Hidden:1}
				, {Header:"동영상명", Type:"Text", SaveName:"addInfo7", Width: 10, Align:"Center", Hidden:1}
				, {Header:"동영상경로", Type:"Text", SaveName:"addInfo8", Width: 10, Align:"Center", Hidden:1}
				, {Header:"대체텍스트", Type:"Text", SaveName:"altrnText", Width: 10, Align:"Center", Hidden:1}

			];
			break;
		case '10004':
			//텍스트
			cols = [
				 {Header:"텍스트", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"연결 URL", Type:"Text", SaveName:"addInfo4", Width: 10, Align:"Center", Edit:0}
				, {Header:"연결방법", Type:"Text", SaveName:"addInfo2", Width: 10, Align:"Center", Hidden:1}
				, {Header:"렌딩페이지유형", Type:"Text", SaveName:"addInfo3", Width: 10, Align:"Center", Hidden:1}
				, {Header:"gnb강조", Type:"Text", SaveName:"addInfo5", Width: 10, Align:"Center", Hidden:1}
			];
			break;
		case '10005':
			//HTML
			cols = [
				 {Header:"제목", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				 , {Header:"내용", Type:"Text", SaveName:"addInfo10", Width: 10, Align:"Center", Hidden:1}
			];
			break;
		case '10006':
			//개발콘텐츠
			cols = [
				 {Header:"제목", Type:"Text", SaveName:"addInfo1", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			];
			break;
		case 'T':
			//전시코너명(텍스트)
			cols = [
				{Header:"전시코너명", Type:"Text", SaveName:"dispCornerName", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"카테고리코너명순번", Type:"Text", SaveName:"ctgrCornerNameSeq", Width: 10, Align:"Center", Hidden:1}
				, {Header:"페이지코너명순번", Type:"Text", SaveName:"dispPageCornerNameSeq", Width: 10, Align:"Center", Hidden:1}
			];
			url = '/display/contents/popup/corner-name/list';
//			initSheet.Cols[1].Hidden = 1;
			break;
		case 'I':
			//전시코너명(이미지)
			cols = [
				{Header:"전시코너명", Type:"Text", SaveName:"dispCornerName", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"카테고리코너명순번", Type:"Text", SaveName:"ctgrCornerNameSeq", Width: 10, Align:"Center", Hidden:1}
				, {Header:"페이지코너명순번", Type:"Text", SaveName:"dispPageCornerNameSeq", Width: 10, Align:"Center", Hidden:1}
				, {Header:"이미지명", Type:"Text", SaveName:"imageName", Width: 10, Align:"Center", Hidden:1}
				, {Header:"이미지", Type:"Image", SaveName:"imageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight:100, ImgWidth: 100 }
				, {Header:"대체텍스트", Type:"Text", SaveName:"altrnText", Width: 10, Align:"Center", Hidden:1}
			];
			url = '/display/contents/popup/corner-name/list';
//			initSheet.Cols[1].Hidden = 1;
			break;
		default:
			break;
		}

		var list = eval(id);
		list.imageFileInputs = {};
		list.movieFileInputs = {};

		_cornerInfoPop.sheet.id[id] = list;

		initSheet.Cols = initSheet.Cols.concat(cols).concat(endCols);
		IBS_InitSheet( list, initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		//col 넓이 자동
		list.FitColWidth();
		//

		_cornerInfoPop.getList(url, id, contTypeCode );

	}

	/**
	 * IBSheet 이벤트
	 */
	_cornerInfoPop.sheet.event = function(){


		$.each(_cornerInfoPop.sheet.id, function(i,v){
			var list = v;
			window[i+'_OnClick'] = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
				if ( row != 0) {
					var contTypeCode = $('#'+list.id).data('contTypeCode');

					var colName = '';
					if(contTypeCode.length == 1){
						colName = 'dispCornerName';
					}else{
						colName = 'addInfo1';
					}


					if ( list.ColSaveName(col) == colName && value != "" ) {

						if(contTypeCode == 10001){
							return false;
						}


						_cornerInfoPop.openCornerPop({
							"contTypeCode" : contTypeCode.toString(),
							"gridId" : list.id,
							"ctgrCornerNameSeq" : list.GetRowData(row).ctgrCornerNameSeq,
							"ctgrNo" : $('#ctgrNo').val(),
							"dispPageNo" : $('#dispPageNo').val(),
							"dispTmplNo" : $('#dispTmplNo').val(),
							"dispTmplCornerSeq" : $('#dispTmplCornerSeq').val(),
							"contTypeSeq" : list.GetRowData(row).contTypeSeq,
							"selectRow" : row
						});
					}
				}
			};

			window[i+'_OnSearchEnd'] = function(code, msg, stCode, stMsg, responseText) {
				var contTypeCode = $('#'+list.id).data('contTypeCode');
				if(contTypeCode == '10001'){
					var rowData = list.ExportData({"Type":"json"}).data;
					var prdtNoArr = [];
					$.each(rowData, function(k,m){
						prdtNoArr.push(m.addInfo1);
					});

					if(prdtNoArr.length == 0) return false;

					$.ajax({
						url : "/product/product",
						method : "POST",
						data : { "prdtCodeList" : prdtNoArr, "prdtCodeType" : "prdt-code-online", "rowsPerPage" : 99 }
					}).done(function(data) {

						$.each(rowData, function(j,m){
							$.each(data.Data, function(i,v) {
								if(m.addInfo1 == v.prdtNo){
									// 가용율 계산
									availabilityRate = Math.ceil(v.useYnQty / v.totalOptionCount * 100);

									console.log(v);

									list.SetRowData(j+1, {
										"chnnlName" : v.chnnlName+'('+v.siteName+')',
										"titleImageUrl" : v.titleImageUrl,
										"mmnyPrdtYn" : v.mmnyPrdtYn == 'Y' ? '자사' : '입점사',
										"stdrCtgrName" : v.stdrCtgrName,
										"sellStatCode" : _cornerInfoPop.data.sellStatCodes[v.sellStatCode],
										"orderPsbltQty" : v.orderPsbltQty,
										"availabilityRate" : availabilityRate+'%',
										"normalAmt" : abc.text.isMakeComma(v.normalAmt == null ? 0 : v.normalAmt)+'원',
										"sellAmt" : abc.text.isMakeComma(v.sellAmt == null ? 0 : v.sellAmt)+'원',
										"prdtName" : v.prdtName,
										"onlnDscntRate" : v.onlnDscntRate+'%'
									}, {StatusMode : 0});

									//매핑한 데이터 삭제
									data.Data.splice(i,1);
									return false;
								}
							});
						});

					}).fail(function(data) {
						console.log(data);
					});

				}
			}
		});
	}

	/**
	 * 이벤트
	 */
	_cornerInfoPop.event = function(){

		/**
		 * 상세 팝업
		 */
		$('.open-reg-popup').on('click', function(){

			var contTypeCode = $(this).parent().data('contTypeCode').toString();
			var gridId = $(this).parent().data('gridId').toString();

			_cornerInfoPop.openCornerPop({
				"contTypeCode" : contTypeCode,
				"gridId" : gridId
			});

		});


		/**
		 * 집계보기 팝업
		 */
		$(".open-popular-popup").on('click', function(){

			var contTypeCode = $(this).parent().data('contTypeCode').toString();
			var gridId = $(this).parent().data('gridId').toString();

			var params = {
				contTypeCodeName : '코너 콘텐츠 등록(인기상품)',
				contTypeCode : contTypeCode,
				gridId : gridId,
			}

			var options = {
					url 	:	"/display/contents/popup/corner-product-popular-pop",
					method : "get",
					winname : "corner-product-popular-pop",
					title : "corner-product-popular-pop",
					params	: params
			};

			_cornerInfoPop.data.selectGridId = params.gridId;
			abc.productPopularSearchPopup(true, 'abc.biz.display.content.corner.info.pop.productCallback');

		});

		/**
		 * 저장
		 */
		$('.btn-save').on('click', function(){
			var contTypeCode = $(this).parent().data('contTypeCode').toString();
			var gridId = $(this).parent().data('gridId').toString();
			var setSeq = $(this).parent().data('setSeq').toString();

			var list = _cornerInfoPop.sheet.id[gridId];
			var jsonData = list.GetSaveJson().data;
			var allowTypes = ['Text', 'Date', 'Seq', 'Status'];

			if(jsonData.length == 0) {
				alert('저장 할 데이터가 없습니다.');
				return false;
			}

			var chkDate = false;

			for(var i = 0 ; i< jsonData.length; i++){
				console.log(jsonData[i].dispEndYmd);
				console.log(jsonData[i].dispStartYmd);
				if(jsonData[i].dispStartYmd > jsonData[i].dispEndYmd){
					chkDate = true;
				}
			}

			if(chkDate){
				alert("종료일이 시작일을 앞 설 수 없습니다.");
				return false;
			}

			document.searchForm.contTypeCode.value = contTypeCode;
			document.searchForm.dispTmplCornerSetSeq.value = setSeq;

			//초기화
			document.saveForm.innerHTML = '';

			var url = '/display/contents/popup/save/name';
			var getListUrl = '/display/contents/popup/corner-name/list';
			var arrNameAndDot = 'list.';
			if(contTypeCode.length > 1){
				url = '/display/contents/popup/save/set';
				getListUrl = '/display/contents/popup/corner-set/list';
				arrNameAndDot = 'set.';
			}

			//Input에 저장할 데이터 세팅
			$.each(jsonData, function(idx, json){
			    $.each(json, function(i, v){

			        var type = _cornerInfoPop.getColType(list, i);
			        //그리드 데이터 중 필요없는 데이터 skip
			        if(allowTypes.indexOf(type) > -1){

			            var input = $('<input>').attr({
				            type : 'hidden',
				            name : arrNameAndDot+i,
				            value : v
			            });

			            $(document.saveForm).append(input);
			        }else{
			        	return true;
			        }

			        //한번만 세팅 되어야 될 데이터들
			        if(i == 'seq'){
		        		var imageFileInput = list.imageFileInputs[v];
		        		if(imageFileInput){
		        			imageFileInput.attr('name', arrNameAndDot+'imageUpload');
		        			$(document.saveForm).append(imageFileInput);
		        		}

		        		var movieFileInput = list.movieFileInputs[v];
		        		if(movieFileInput){
		        			movieFileInput.attr('name', arrNameAndDot+'movieUpload');
		        			$(document.saveForm).append(movieFileInput);
		        		}

			            //카테고리 번호
			            var input = $('<input>').attr({type: 'hidden', name: arrNameAndDot+'ctgrNo', value: $('#ctgrNo').val()});
			            $(document.saveForm).append(input);
			            //전시페이지 번호
			            var input = $('<input>').attr({type: 'hidden', name: arrNameAndDot+'dispPageNo', value: $('#dispPageNo').val()});
			            $(document.saveForm).append(input);
			            //전시템플릿 번호
			            input = $('<input>').attr({type: 'hidden', name: arrNameAndDot+'dispTmplNo', value: $('#dispTmplNo').val()});
			            $(document.saveForm).append(input);
			            //전시템플릿코너 번호
			            input = $('<input>').attr({type: 'hidden', name: arrNameAndDot+'dispTmplCornerSeq', value: $('#dispTmplCornerSeq').val()});
			            $(document.saveForm).append(input);
			            //전시템플릿코너 셋 번호
			            input = $('<input>').attr({type: 'hidden', name: arrNameAndDot+'dispTmplCornerSetSeq', value: setSeq});
			            $(document.saveForm).append(input);
			        }
			    })
			});

			//콘텐츠타입코드
            input = $('<input>').attr({type: 'hidden', name: 'contTypeCode', value: contTypeCode});
            $(document.saveForm).append(input);
            //카테고리 번호
            var input = $('<input>').attr({type: 'hidden', name: 'ctgrNo', value: $('#ctgrNo').val()});
            $(document.saveForm).append(input);
            //전시페이지 번호
            var input = $('<input>').attr({type: 'hidden', name: 'dispPageNo', value: $('#dispPageNo').val()});
            $(document.saveForm).append(input);


			//form 전송
			var form = $.form(document.saveForm);
			form.submit({
				url	: url,
				method	: "POST",
				valid	: function($f){
					return true;
				},
				success : function(data){
					alert('저장 되었습니다.');
		    		_cornerInfoPop.getList(getListUrl, gridId );
				},
				error	: function(e){
					alert(e.message);
			    	console.log(e);
				}
			});
		});

		//삭제
		$('.btn-del').on('click', function(){
			var contTypeCode = $(this).parent().data('contTypeCode').toString();
			var gridId = $(this).parent().data('gridId').toString();
			var setSeq = $(this).parent().data('setSeq').toString();

			var list = _cornerInfoPop.sheet.id[gridId];
			var rowData = list.ExportData({"Type":"json"}).data;

			//초기화
			document.saveForm.innerHTML = '';

			var delSeq = [];
			var delRowSeqStr = '';
			$.each(rowData, function(i,v){
				if(v.checked == 1){
					delRowSeqStr += v.seq + '|';
					console.log(v.contTypeSeq, v.ctgrCornerNameSeq, v.dispPageCornerNameSeq);
					var seq ="";
					if(v.contTypeSeq){
						seq = v.contTypeSeq;
					} else if (v.ctgrCornerNameSeq){
						seq = v.ctgrCornerNameSeq;
					} else {
						seq = v.dispPageCornerNameSeq;
					}
					delSeq.push(seq);
				}
			});

			if(delSeq.length > 0){

				var cfm = confirm("삭제 하시겠습니까?");

				if(cfm){
				//	debugger;
					console.log(rowData);
					var url = '/display/contents/popup/delete/name';
					var arrNameAndDot = 'list.';

					if(contTypeCode.length > 1){
						url = '/display/contents/popup/delete/set';
						arrNameAndDot = 'set.';
					}

					//카테고리 번호
					var input = $('<input>').attr({type: 'hidden', name: 'ctgrNo', value: $('#ctgrNo').val()});
					$(document.saveForm).append(input);
					//전시페이지 번호
		            var input = $('<input>').attr({type: 'hidden', name: 'dispPageNo', value: $('#dispPageNo').val()});
		            $(document.saveForm).append(input);
					//전시템플릿 번호
					input = $('<input>').attr({type: 'hidden', name: 'dispTmplNo', value: $('#dispTmplNo').val()});
					$(document.saveForm).append(input);
					//전시템플릿코너 번호
					input = $('<input>').attr({type: 'hidden', name: 'dispTmplCornerSeq', value: $('#dispTmplCornerSeq').val()});
					$(document.saveForm).append(input);
					//전시템플릿코너 셋 번호
					input = $('<input>').attr({type: 'hidden', name: 'dispTmplCornerSetSeq', value: setSeq});
					$(document.saveForm).append(input);
					//콘텐츠타입코드
					input = $('<input>').attr({type: 'hidden', name: 'contTypeCode', value: contTypeCode});
					$(document.saveForm).append(input);

					//form 전송
					var form = $.form(document.saveForm);
					form.submit({
						url	: url,
						method	: "POST",
						dataType : "json",
						data : $.paramObject({ delSeq : delSeq }),
						valid	: function($f){
							return true;
						},
						success : function(data){
							alert('삭제되었습니다.');
							list.RowDelete(delRowSeqStr);
						},
						error	: function(e){
							alert(e.message);
							console.log(e);
						}
					});
				}
			}else{
				alert('삭제할 상품을 선택해주세요.');
				list.RowDelete(delRowSeqStr);
			}
		});

		/**
		 * 전시일시 일괄 적용 이벤트
		 */
		$('.apply-select-item').on('click', function(e){

			var parent = $(this).parents('.term-date-wrap');
			var gridId = parent.parent().data('gridId').toString();
			var dispStartYmd = parent.find('.dispStartYmd').val();
			var dispEndYmd = parent.find('.dispEndYmd').val();
			var list = _cornerInfoPop.sheet.id[gridId];
			var rowData = list.ExportData({"Type":"json"}).data;

			if(dispStartYmd == ''){
				alert('일괄적용 시 전시시작일은 필수입니다.');
				return false;
			}else if(dispEndYmd == ''){
				alert('일괄적용 시 전시종료일은 필수입니다.');
				return false;
			}

			if(dispStartYmd.replaceAll('\\.','') > dispEndYmd.replaceAll('\\.','')){
				alert('전시종료일이 전시시작일보다 이전입니다.');
				return false;
			}
			var checkedCount = 0;
			$.each(rowData, function(i,v){
				if(v.checked == 1){
					checkedCount++;
					list.SetRowData(i+1, {
						"dispStartYmd": dispStartYmd,
						"dispEndYmd" : dispEndYmd
					});
				}
			});

			if(checkedCount == 0){
				alert('일괄 적용할 항목을 선택해 주세요.');
				return false;
			}
		});

	}

	/**
	 * col의 타입 조회
	 */
	_cornerInfoPop.getColType = function(list, name){

	    var type;
	    $.each(list.Cols, function(i,v){
	        if(name == v.SaveName){
	            type = v.Type;
	            return false;
	        }
	    });

	    return type;
	}

	/**
	 * 리스트 조회
	 */
	_cornerInfoPop.getList = function(url, sheet, contTypeCode){

		var pageCount = 50; // 한페이지내 결과 로우 갯수
		var param = { url : url
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.searchForm)
			, sheet : sheet };

		DataSearchPaging(param);
	}

	/**
	 * 등록 팝업
	 */
	_cornerInfoPop.callback = function(data){

		var list = abc.biz.display.content.corner.info.pop.sheet.id[data.gridId];
		var insertRowNumber = list.RowCount() + 1;
		var initialRowData = {};	// 추가될 행 데이터
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정

		initialRowData = {
				"dispStartYmd": data.dispYn == 'N' ? '' : data.dispStartYmd,
				"dispEndYmd" : data.dispYn == 'N' ? '' : data.dispEndYmd,
				"sortSeq" : data.sortSeq,
				"dispYn" : data.dispYn,
				"contTypeCode" : data.contTypeCode
			};

		switch (data.contTypeCode) {
		case '10000':
			//전시세트명
			initialRowData.addInfo1 = data.addInfo1;
			break;
		case '10001':
			//상품
			//상품 검색 팝업
			return false;
			break;
		case '10002':
			//이미지
			initialRowData.addInfo1 = data.addInfo1;
			initialRowData.addInfo2 = data.imageFileName;
			initialRowData.addInfo3 = data.imageFilePath;
			initialRowData.imageUrl = data.imageUrl;
			initialRowData.addInfo4 = data.imageUrl;
			initialRowData.addInfo5 = data.addInfo5;
			initialRowData.addInfo6 = data.addInfo6;
			initialRowData.addInfo7 = data.addInfo7 != null ? data.addInfo7 : '';
			initialRowData.addInfo8 = data.addInfo8;
			initialRowData.addInfo9 = data.addInfo9;
			initialRowData.addInfo10 = data.addInfo10;
			initialRowData.addInfo8Name = data.addInfo8 == 'N' ? '선택안함' : data.addInfo8 == 'B' ? '블랙' : '화이트' ;
			initialRowData.altrnText = data.altrnText;

			break;
		case '10003':
			//동영상
			initialRowData.addInfo1 = data.addInfo1;
			initialRowData.addInfo2 = data.imageFileName;
			initialRowData.addInfo3 = data.imageFilePath;
			initialRowData.addInfo4 = data.imageUrl;
			initialRowData.imageUrl = data.imageUrl;
			initialRowData.addInfo5 = data.addInfo5;
			initialRowData.addInfo6 = data.addInfo6;
			initialRowData.addInfo7 = data.movieFileName;
			initialRowData.addInfo8 = data.movieFilePath;
			if (data.addInfo5 == 'U') {
				initialRowData.addInfo9 = data.addInfo9;
			} else if (data.addInfo5 == 'D') {
				initialRowData.addInfo9 = data.movieUrl;
			}
			initialRowData.altrnText = data.altrnText;

			break;
		case '10004':
			//텍스트
			initialRowData.addInfo1 = data.addInfo1;
			initialRowData.addInfo2 = data.addInfo2;
			initialRowData.addInfo3 = data.addInfo3;
			initialRowData.addInfo4 = data.addInfo4;
			initialRowData.addInfo5 = data.addInfo5;
			break;
		case '10005':
			//HTML
			initialRowData.addInfo1 = data.addInfo1;
			initialRowData.addInfo10 = data.addInfo10;
			break;
		case 'T':
			//전시코너명(텍스트)
			initialRowData.dispCornerName = data.dispCornerName;
			break;
		case 'I':

			initialRowData.dispCornerName = data.dispCornerName;
			initialRowData.imageUrl = data.imageUrl;
			initialRowData.imageName = data.imageFileName;
			initialRowData.imagePathText = data.imageFilePath;
			initialRowData.altrnText = data.altrnText;

			break;
		default:
			break;
		}


		if(data.selectRow != null && data.selectRow != ''){
			list.SetRowData(data.selectRow, initialRowData);
		}else{
			list.SetRowData(insertRowNumber, initialRowData, rowOption);
		}

		if(data.imageUpload != null && data.imageUpload != ''){
			if(data.imageUpload.val() != null && data.imageUpload.val() != ''){
				var rowSeq = list.GetRowData(data.selectRow != null && data.selectRow != '' ? data.selectRow : insertRowNumber).seq;
				list.imageFileInputs[rowSeq] = data.imageUpload;
			}
		}
		if(data.movieUpload != null && data.movieUpload != ''){
			if(data.movieUpload.val() != null && data.movieUpload.val() != ''){
				var rowSeq = list.GetRowData(data.selectRow != null && data.selectRow != '' ? data.selectRow : insertRowNumber).seq;
				list.movieFileInputs[rowSeq] = data.movieUpload;
			}
		}
	}

	/**
	 * 코너 데이터 입력 팝업 호출
	 */
	_cornerInfoPop.openCornerPop = function(params){

		var options = {
				url 	:	"/display/contents/popup/corner-set-pop",
				method : "get",
				winname : "corner-set-pop",
				title : "corner-set-pop",
				width   : '855',
				params	: params
		};

		switch (params.contTypeCode) {
		case '10000':
			//전시세트명
			options.height = '315';
			options.params.contTypeCodeName = '전시세트명 등록';
			break;
		case '10001':
			//상품
			//상품 검색 팝업
			_cornerInfoPop.data.selectGridId = params.gridId;
			abc.productSearchPopup(true, 'abc.biz.display.content.corner.info.pop.productCallback');
			return false;
			break;
		case '10002':
			//이미지
			options.height = '715';
			options.params.contTypeCodeName = '이미지 등록';
			break;
		case '10003':
			//동영상
			options.height = '510';
			options.params.contTypeCodeName = '동영상 등록';
			break;
		case '10004':
			//텍스트
			options.height = '470';
			options.params.contTypeCodeName = '텍스트 등록';
			break;
		case '10005':
			//HTML
			options.height = '635';
			options.params.contTypeCodeName = 'HTML 등록';
			break;
		case '10006':
			//개발콘텐츠
			options.params.title = '';
			options.width = '';
			options.height = '';
			return false;
			break;
		case 'T':
			//전시코너명(텍스트)
			options.url = "/display/contents/popup/corner-name-pop";
			options.height = '315';
			options.params.contTypeCodeName = '전시코너명(텍스트) 등록';
			break;
		case 'I':
			//전시코너명(이미지)
			options.url = "/display/contents/popup/corner-name-pop";
			options.height = '430';
			options.params.contTypeCodeName = '전시코너명(이미지) 등록';
			break;
		default:
			break;
		}

		abc.open.popup(options);
	}

	/**
	 * 해당 그리드의 row data return
	 */
	_cornerInfoPop.getGridRow = function(gridId, row){
		if(gridId == null || gridId == '' || row == null || row == '') return false;
		return _cornerInfoPop.sheet.id[gridId].GetRowData(row);
	}

	/**
	 * 상품 데이터 callback
	 */
	_cornerInfoPop.productCallback = function(data){

		var list = abc.biz.display.content.corner.info.pop.sheet.id[_cornerInfoPop.data.selectGridId];
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정

		var gridList = list.ExportData({"Type":"json"}).data;

		for(var i in data) {
			var v = data[i];
			console.log(v);

			var insertRowNumber = list.RowCount() + 1;
			var flag = false;

			for(var j in gridList){

				var w = gridList[j];

				if(w.addInfo1 == v.prdtNo){	// 값이 중복되면
					alert('중복된 상품은 제외하고 등록됩니다.');
					flag = true;
				}

			};

			if(flag) {
				continue;
			}

			list.SetRowData(insertRowNumber, {
				'checked' : true,
				'sortSeq' : insertRowNumber,
				'addInfo1' : v.prdtNo,
				'dispYn' : 'Y',
				"dispStartYmd": '',
				"dispEndYmd" : '',
				"contTypeCode" : '10001',
				"chnnlName" : v.chnnlName+'('+v.siteName+')',
				"titleImageUrl" : v.titleImageUrl,
				"mmnyPrdtYn" : v.mmnyPrdtYn == 'Y' ? '자사' : '입점사',
				"stdrCtgrName" : v.stdrCtgrName,
				"sellStatCode" : _cornerInfoPop.data.sellStatCodes[v.sellStatCode],
				"orderPsbltQty" : v.orderPsbltQty,
				"availabilityRate" : v.availabilityRate+'%',
				"normalAmt" : v.normalAmt,
				"sellAmt" : v.sellAmt,
				"prdtName" : v.prdtName,
				"onlnDscntRate" : v.onlnDscntRate+'%'
			}, rowOption);

//			list.SetRowData(1,{'checked' : false})
		};


	}

	$(function() {
		_cornerInfoPop.init();
	});

})();