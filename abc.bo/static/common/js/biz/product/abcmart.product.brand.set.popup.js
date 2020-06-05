(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.brand.set.pop");
	var params = abc.param.getParams();
	console.log(params);

	/**
	 * 초기화
	 */
	_object.init = function(){
		_object.event();

		$(".ico-fdel").hide();	// 모든 첨부파일 삭제 아이콘 숨기기
		if($("[data-brand-set-image=file-name]").text() != "") {
			$("[data-brand-set-image=remove]").show();
		}
		if($("[data-brand-set-image=file-name]").text() != "") {
			$("[data-brand-set-image=remove]").show();
		}

		_object.LayerDisplayYn("Y");
		if(params.contTypeCode == "10002"){
			_object.imageLayerLinkType("U");
		}
		if(params.contTypeCode == "10003"){
			_object.movieLayerLinkType("U");
		}
	}

	/**
	 * 이벤트
	 */
	_object.event = function(){

		new abc.biz.product.utils.processImage({
			file: '#imageUpload',
			name: '#addInfo2'
		});
		new abc.biz.product.utils.processImage({
			file: '#movieUpload',
			name: '#addInfo17',
			type: "M",
			allow :"mp4"
		});

		/*
		// 대표이미지(color) 이미지 첨부 시 파일정보 노출
		$("[data-brand-set-image=file]").change(function(e) {
			console.log("imgUpload > " + $(this).val());
			$('#addInfo2').val($(this).val());

			console.log("files > " + this.files + " / " + this.files[0]);
			// 파일정보 읽어오기
			if(this.files && this.files[0]){
				console.log("files > " + $(this).files);
				var image = new Image();
				var fileReader = new FileReader();
				fileReader.onload = function(e){
					$(image).attr("src", e.target.result);
				}
				fileReader.readAsDataURL(this.files[0]);
				$("[data-brand-set-image=image-area]").html("");	// 기존 노출 이미지 제거
				$("[data-brand-set-image=image-area]").append(image);	// 이미지 노출
				$("[data-brand-set-image=file-name]").html("");	// 첨부파일명 제거
				$("[data-brand-set-image=file-name]").text(this.files[0].name);	// 첨부파일명 출력
				$("[data-brand-set-image=remove]").show();	// 이미지 삭제 링크 보이기
			}
		});
		*/

		$('#imageUpload').on('change', function(){
			console.log("imgUpload > " + $(this).val());
			$('#addInfo2').val($(this).val());

			console.log("files > " + this.files + " / " + this.files[0]);
			// 파일정보 읽어오기
			if(this.files && this.files[0]){
				$('.subject').html('');	// 첨부파일명 제거
				$('.subject').text(this.files[0].name);	// 첨부파일명 출력
				$('.ico-fdel').show();	// 이미지 삭제 링크 보이기
			}
		});

		$('#del-image').on('click', function() {
			// 필드 클리어
			$('#imageUpload').val('');
			$('.subject').html('');	// 첨부파일명 제거
			$('.ico-fdel').hide();	// 이미지 삭제 링크 보이기
		});

		$('.btn-save').on('click', function(){
			if(!_object.valid()){
				return false;
			}

			//form 전송
			var option = {
				url : "/product/brand/page/upload/file",
				type : "POST",
				dataType : "json",
				success : function(data) {
					console.log(JSON.stringify(data));
					alert(data.Message);
					if(data.success){
						var sheetName = $('[name=sheetName]').val();

						switch(sheetName){
						case 'mallBrandPageVisualPcALLList':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPagePcALL");
							break;
						case 'mallBrandPageVisualMoALLList':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPageMoALL");
							break;
						case 'mallBrandPageVisualPc10001List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPagePc10001");
							break;
						case 'mallBrandPageVisualMo10001List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPageMo10001");
							break;
						case 'mallBrandPageVisualPc10002List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPagePc10002");
							break;
						case 'mallBrandPageVisualMo10002List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPageMo10002");
							break;
						case 'mallBrandPageVisualPc10004List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPagePc10004");
							break;
						case 'mallBrandPageVisualMo10004List':
							opener.abc.biz.product.brand.detail.doAction("readMallBrandPageMo10004");
							break;
						case 'otsBrandPageVisualPcList':
							opener.abc.biz.product.brand.detail.doAction("readOtsBrandPagePc");
							break;
						case 'otsBrandPageVisualMoList':
							opener.abc.biz.product.brand.detail.doAction("readOtsBrandPageMo");
							break;
						}

						window.close();
					}

				},
				error : function(e) {
					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
					}
					console.log(e);
				}
			};
			$.form("#frm").submit(option);


		});

		$(document.frm.dispYn).on('click', function(){
			_object.LayerDisplayYn(this.value);
		});

		if(params.contTypeCode == "10002"){
			//링크연결방법 이벤트
			$(document.frm.addInfo5).on('click', function(){
				var v = $(this).val();
				_object.imageLayerLinkType(v, this);
			});
		}
		if(params.contTypeCode == "10003"){
			$(document.frm.addInfo5).on('click', function(i,v){
				var v = $(this).val();
				_object.movieLayerLinkType(v);
			});
		}

	}

	_object.LayerDisplayYn = function(v){
		if(v == 'Y'){
			$(document.frm.dispStartYmd).attr('disabled', false);
			$(document.frm.dispEndYmd).attr('disabled', false);
		}else{
			$(document.frm.dispStartYmd).attr('disabled', true).val('');
			$(document.frm.dispEndYmd).attr('disabled', true).val('');
		}
	}

	_object.imageLayerLinkType = function(v, pl){

		if(v == 'U'){
			$('[name=addInfo7]').eq(0).prop('disabled', false);
			$('[name=addInfo7]').eq(1).prop('disabled', true);
			$('[name=addInfo7]').eq(2).prop('disabled', true);

			$('.link-type-url').show();
			$('.link-type-event').hide();
			$('.link-type-map').hide();
		}else if(v == 'M'){
			$('[name=addInfo7]').eq(0).prop('disabled', true);
			$('[name=addInfo7]').eq(1).prop('disabled', false);
			$('[name=addInfo7]').eq(2).prop('disabled', true);

			$('.link-type-url').hide();
			$('.link-type-event').hide();
			$('.link-type-map').show();
		}else if(v == 'E' || v == 'P' || v == 'B'){

			var data = $(pl).data();

			if(!data){
				data = $('[name=addInfo5][value='+v+']').data();
			}

			$('[name=addInfo7]').eq(2).attr({title : data.hint, placeholder : data.hint }).siblings('.link-url').text(data.linkUrl);

			$('[name=addInfo7]').eq(0).prop('disabled', true);
			$('[name=addInfo7]').eq(1).prop('disabled', true);
			$('[name=addInfo7]').eq(2).prop('disabled', false);

			$('.link-type-url').hide();
			$('.link-type-event').show();
			$('.link-type-map').hide();
		}else{
			$('[name=addInfo7]').eq(0).prop('disabled', true);
			$('[name=addInfo7]').eq(1).prop('disabled', true);
			$('[name=addInfo7]').eq(2).prop('disabled', true);

			$('.link-type-url').hide();
			$('.link-type-event').hide();
			$('.link-type-map').hide();
		}
	}

	_object.movieLayerLinkType = function(v){

		if(v == 'U'){
			$('.layer-url-input').show();
			$('.layer-movie-upload').hide();
		}else{
			$('.layer-url-input').hide();
			$('.layer-movie-upload').show();
		}
	}

	_object.textLayerLinkType = function(v){
		if(v == 'U'){
			$('.link-type-url').show();
		}else{
			$('.link-type-url').hide();
		}
	}

	_object.makeJsonData = function(){

		var jsonData = {};

		$.each($(document.frm).serializeArray(), function(i,v){
			jsonData[v.name] = v.value;
		});

		return jsonData;
	}

	_object.valid = function(){
		if($("[name=dispYn]:checked").val() == 'Y'){
			if($("[name=dispStartYmd]").val() == ''){
				alert('전시일때 전시시작일은 필수입니다.');
				return false;
			}else if($("[name=dispEndYmd]").val() == ''){
				alert('전시일때 전시종료일은 필수입니다.');
				return false;
			}

			if($("[name=dispStartYmd]").val().replaceAll('\\.','') > $("[name=dispEndYmd]").val().replaceAll('\\.','')){
				alert('전시종료일이 전시시작일보다 이전입니다.');
				return false;
			}
		}

		if($("[name=sortSeq]").val() == ''){
			alert('노출순서는 필수입니다.');
			return false;
		}else if(!$.isNumeric($("[name=sortSeq]").val())){
			alert('노출순서는 숫자만 입력 가능합니다.');
			return false;
		}


		switch (params.contTypeCode) {
		case '10002':
			//이미지
			if($("[name=addInfo1]").val() == ''){
				alert('제목은 필수입니다.');
				return false;
			}

			if($("[name=addInfo2]").val() == ''){
				alert('이미지는 필수입니다.');
				return false;
			}

			if($("[name=addInfo5]:checked").val() != 'N'){
				var idx = $("[name=addInfo5]:checked").val() == "U" ? 0 : $("[name=addInfo5]:checked").val() == "M" ? 1 : 2;
				console.log("idx > " + idx);
				if($("[name=addInfo7]").eq(idx).val() == ''){
					alert('랜딩 URL은 필수입니다.');
					return false;
				}

				console.log("addInfo7 > " + idx + " / " + abc.text.isUrl($("[name=addInfo7]").eq(idx).val()));
				if(idx < 2 && !abc.text.isUrl($("[name=addInfo7]").eq(idx).val())){
					alert('랜딩 URL 형식이 아닙니다.');
					return false;
				}
			}

			if($("[name=addInfo8]").val() == ''){
				alert('텍스트 컬러는 필수입니다.');
				return false;
			}

			break;
		case '10003':
			//동영상
			if($("[name=addInfo1]").val() == ''){
				alert('제목은 필수입니다.');
				return false;
			}

			if($("[name=addInfo2]").val() == ''){
				alert('동영상 썸네일은 필수입니다.');
				return false;
			}

			if($("[name=addInfo5]:checked").val() == 'U'){
				if($("[name=addInfo9]").val() == ''){
					alert('URL은 필수입니다.');
					return false;
				}
			}else{
				if($("[name=addInfo17]").val() == ''){
					alert('동영상은 필수입니다.');
					return false;
				}
			}

			break;
		default:
			break;
		}

		return true;

	}

	$(function() {
		_object.init();
	});

})();