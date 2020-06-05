(function() {

	var _cornerSetPop = abc.object.createNestedObject(window,"abc.biz.display.content.corner.set.pop");
	var params = abc.param.getParams();

	/**
	 * 초기화
	 */
	_cornerSetPop.init = function(){

		if(params.contTypeCode == "10005"){
			//에디터 초기화
			CKEDITOR.replace( 'addInfo10' );
		}

		_cornerSetPop.imageUpload = new abc.biz.display.common.processImage({
			file: '#imageUpload',
			name: '#addInfo2'
		});
		_cornerSetPop.movieUpload = new abc.biz.display.common.processImage({
			file: '#movieUpload',
			name: '#addInfo7',
			type: 'M'
		});

		//상위 팝업 그리드에서 데이터 가져와 세팅(수정 시)
		if(params.selectRow != ''){
			var data = opener.abc.biz.display.content.corner.info.pop.getGridRow(params.gridId, params.selectRow);
			var imageFileWrap = $('#imageUpload').parents('.file-wrap');
			var movieFileWrap = $('#movieUpload').parents('.file-wrap');

			$.each(data, function(i,v){
				var el = $('[name='+i+']');

				if(el){

					if(params.contTypeCode == '10002'){
						if( i == "addInfo7"){
							if(data.addInfo5 == 'U'){
								el.eq(0).val(v);
							}else{
								el.eq(1).val(v);
							}

							return true;
						}
					}

					if(el.attr('type') == 'radio'){
						if(v != null && v != ''){
							$('[name='+i+'][value='+v+']').attr('checked', true);
						}
					}else{
						el.val(v);
					}
				}

				if(imageFileWrap.length > 0){
					if(i == 'addInfo2'){
						imageFileWrap.find('.subject').text(v);
					}else if(i == 'addInfo4'){
						if(v){
							var img = new Image();
							$(img).attr('src', v);
							imageFileWrap.find('.img-wrap').append(img);
						}
					}

					$('#del-image').show();
				}

				if(movieFileWrap.length > 0){
					if(i == 'addInfo7'){
						movieFileWrap.find('.subject').text(v);
					}
				}
			});
		}

		_cornerSetPop.event();
	}

	/**
	 * 이벤트
	 */
	_cornerSetPop.event = function(){

		//저장
		$('.btn-save').on('click', function(){

			if(!_cornerSetPop.valid()){
				return false;
			}


			if($('#imageUpload').val() != '' || $('#movieUpload').val() != ''){
				var form = $.form(document.frm);
				form.submit({
					url : "/display/contents/popup/file/upload",
					method : "POST",
					valid	: function($f){
						return true;
					},
					success : function(data) {
						console.log(data);
						_cornerSetPop.setDataAndCallback(data);
					},
					error : function(e) {
						alert(e.message);
				    	console.log(e);
					}
				});
			}else{
				_cornerSetPop.setDataAndCallback();
			}


		});

		/**
		 * 전시 여부 이벤트
		 */
		$(document.frm.dispYn).on('click', function(){

			_cornerSetPop.LayerDisplayYn(this.value);
		});

		_cornerSetPop.LayerDisplayYn($('[name=dispYn]:checked').val());

		// 유형 별 이벤트 처리 ----------------------
		if(params.contTypeCode == "10002"){
			//링크연결방법 이벤트
			$('[name=addInfo5]').on('click', function(){
				var val = $(this).val();
				_cornerSetPop.imageLayerLinkType(val, this);
			});
			_cornerSetPop.imageLayerLinkType($('[name=addInfo5]:checked').val(), $('[name=addInfo5]:checked').get(0));

			//배경 color 사용여부
			$('[name=addInfo9]').on('click', function(){
				var val = $(this).val();
				_cornerSetPop.imageLayerColorType(val, this);
			});
			_cornerSetPop.imageLayerColorType($('[name=addInfo9]:checked').val());

		}
		if(params.contTypeCode == "10003"){
			$('[name=addInfo5]').on('click', function(i,v){
				var val = $(this).val();
				_cornerSetPop.movieLayerLinkType(val);
			});

			_cornerSetPop.movieLayerLinkType($('[name=addInfo5]:checked').val());
		}

		if(params.contTypeCode == "10004"){

			$('[name=addInfo2]').on('click', function(i,v){
				var val = $(this).val();
				_cornerSetPop.textLayerLinkType(val);
			});
			_cornerSetPop.textLayerLinkType($('[name=addInfo2]:checked').val());
		}


		// 유형 별 이벤트 처리 ----------------------
	}

	/**
	 * 데이터 초기 세팅 및 callback 처리
	 */
	_cornerSetPop.setDataAndCallback = function(v){

		var data = _cornerSetPop.makeJsonData();
		if(v != undefined){
			data.imageUrl = v.imageUrl;
			data.imageFileName = v.imageFileName;
			data.imageFilePath = v.imageFilePath;
			data.movieUrl = v.movieUrl;
			data.movieFileName = v.movieFileName;
			data.movieFilePath = v.movieFilePath;
		}
		if(data.altrnText == ''){
			data.altrnText = v.altrnText;
		}

		if(params.contTypeCode == "10005"){
			data.addInfo10 = CKEDITOR.instances.addInfo10.getData();
		}

		data.gridId = params.gridId;
		data.selectRow = params.selectRow;

		if(data.addInfo2 == "N"){
			data.addInfo4 = "";
		}

		opener.abc.biz.display.content.corner.info.pop.callback(data);

		window.close();
	}

	/**
	 * 전시여부
	 */
	_cornerSetPop.LayerDisplayYn = function(v){
		if(v == 'Y'){
			$('[name=dispStartYmd]').attr('disabled', false);
			$('[name=dispEndYmd]').attr('disabled', false);
		}else{
			$('[name=dispStartYmd]').attr('disabled', true).val('');
			$('[name=dispEndYmd]').attr('disabled', true).val('');
		}
	}

	/**
	 * 이미지 타입 링크 연결 방식
	 */
	_cornerSetPop.imageLayerLinkType = function(v, pl){

		if(v == 'U'){
			$('[name=addInfo7]').eq(0).prop('disabled', false);
			$('[name=addInfo7]').eq(1).prop('disabled', true);
			$('[name=addInfo7]').eq(1).val('');

			$('.link-type-url').show();
			$('.link-type-event').hide();
		}else if(v == 'E' || v == 'P' || v == 'B'){

			var data = $(pl).data();

			if(!data){
				data = $('[name=addInfo5][value='+v+']').data();
			}

			$('[name=addInfo7]').eq(1).attr({title : data.hint, placeholder : data.hint }).siblings('.link-url').text(data.linkUrl);

			$('[name=addInfo7]').eq(0).prop('disabled', true);
			$('[name=addInfo7]').eq(1).prop('disabled', false);
			$('[name=addInfo7]').eq(0).val('');

			$('.link-type-url').hide();
			$('.link-type-event').show();
		}else{
			$('[name=addInfo7]').eq(0).prop('disabled', true);
			$('[name=addInfo7]').eq(1).prop('disabled', true);
			//연결 안함 햇을 경우 url 값 초기화 및 target 현재창으로 변경
			$('[name=addInfo7]').val('');
			$('[name="addInfo6"]').val('S');

			$('.link-type-url').hide();
			$('.link-type-event').hide();
		}
	}

	/**
	 * 동영상 링크 연결 방식
	 */
	_cornerSetPop.movieLayerLinkType = function(v){

		if(v == 'U'){
			$('.layer-url-input').show();
			$('.layer-movie-upload').hide();
			$('#addInfo7').val('');
		}else{
			$('.layer-url-input').hide();
			$('.layer-movie-upload').show();
		}
	}

	/**
	 * 텍스트 링크 연결 방식
	 */
	_cornerSetPop.textLayerLinkType = function(v){

		if(v == 'U'){
			$('.link-type-url').show();
		}else{
			$('.link-type-url').hide();
		}
	}

	/**
	 * form 데이터 json으로 변경
	 */
	_cornerSetPop.makeJsonData = function(){

		var jsonData = {};

		$.each($(document.frm).serializeArray(), function(i,v){
			jsonData[v.name] = v.value;
		});

		return jsonData;
	}

	/**
	 * 이미지 타입 컬러값 처리
	 */
	_cornerSetPop.imageLayerColorType = function(v){

		if(v == 'Y'){
			$('[name=addInfo10]').attr('disabled', false);
		}else{
			$('[name=addInfo10]').attr('disabled', true);
		}

	}

	//url 유효성 체크
//	_cornerSetPop.checkLandingUrl = function (strUrl) {
//		var expUrl = /^http[s]?\:\/\//i;
//
//		return expUrl.test(strUrl);
//	}

	/**
	 * 유효성( 저장 시 상위 팝업으로 가기 때문에 스크립트로 처리)
	 */
	_cornerSetPop.valid = function(){
		var frm = document.frm;

		if($('[name=dispYn]:checked').val() == 'Y'){
			if($('[name=dispStartYmd]').val() == ''){
				alert('전시일때 전시시작일은 필수입니다.');
				return false;
			}else if($('[name=dispEndYmd]').val() == ''){
				alert('전시일때 전시종료일은 필수입니다.');
				return false;
			}

			if($('[name=dispStartYmd]').val().replaceAll('\\.','') > $('[name=dispEndYmd]').val().replaceAll('\\.','')){
				alert('전시종료일이 전시시작일보다 이전입니다.');
				return false;
			}
		}

		if($('[name=sortSeq]').val() == ''){
			alert('노출순서는 필수입니다.');
			return false;
		}else if(!$.isNumeric($('[name=sortSeq]').val())){
			alert('노출순서는 숫자만 입력 가능합니다.');
			return false;
		}


		switch (params.contTypeCode) {
		case '10000':
			//전시세트명
			if($('[name=addInfo1]').val() == ''){
				alert('전시세트명은 필수입니다.');
				return false;
			}
			break;
		case '10002':
			//이미지
			if($('[name=addInfo1]').val() == ''){
				alert('제목은 필수입니다.');
				return false;
			}

			if($('[name=addInfo2]').val() == ''){
				alert('이미지는 필수입니다.');
				return false;
			}

			if($('[name=addInfo5]:checked').val() != 'N'){
				var idx = $('[name=addInfo5]:checked').val() == "U" ? 0 : 1;
				if($('[name=addInfo7]').eq(idx).val() == ''){
					alert('랜딩 URL은 필수입니다.');
					return false;
				}
//				else{
//					var landingUrl = $('[name=addInfo7]').eq(idx).val();
//					if($('[name=addInfo5]:checked').val() == "U"){
//						if(!_cornerSetPop.checkLandingUrl(landingUrl)){
//							alert('유효하지 않는 랜딩 URL 입니다.');
//							return false;
//						}
//					}
//				}
			}

			if($('[name=addInfo8]').val() == ''){
				alert('텍스트 컬러는 필수입니다.');
				return false;
			}

			if($('[name=addInfo9]:checked').val() == 'Y'){
				if($('[name=addInfo10]').val() == ''){
					alert('컬러값을 입력해 주세요.');
					return false;
				}
			}

			break;
		case '10003':
			//동영상
			if($('[name=addInfo1]').val() == ''){
				alert('제목은 필수입니다.');
				return false;
			}

			if($('[name=addInfo2]').val() == ''){
				alert('동영상 썸네일은 필수입니다.');
				return false;
			}

			if($('[name=addInfo5]:checked').val() == 'U'){
				if($('[name=addInfo9]').val() == ''){
					alert('URL은 필수입니다.');
					return false;
				}
			}else{
				if($('[name=addInfo7]').val() == ''){
					alert('동영상은 필수입니다.');
					return false;
				}
			}

			break;
		case '10004':
			//텍스트
			if($('[name=addInfo1]').val() == ''){
				alert('텍스트는 필수입니다.');
				return false;
			}

			if($('[name=addInfo2]:checked').val() == 'U'){
				if($('[name=addInfo4]').val() == ''){
					alert('URL은 필수입니다.');
					return false;
				}
			}

			break;
		case '10005':
			//HTML
			if($('[name=addInfo1]').val() == ''){
				alert('제목은 필수입니다.');
				return false;
			}

			if(CKEDITOR.instances.addInfo10.getData() == ''){
				alert('HTML은 필수입니다.');
				return false;
			}
			break;
		default:
			break;
		}

		return true;

	}

	$(function() {
		_cornerSetPop.init();
	});

})();