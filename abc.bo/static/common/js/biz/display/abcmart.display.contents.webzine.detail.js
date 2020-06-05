(function() {

	var _webzineDetail = abc.object.createNestedObject(window,"abc.biz.display.contents.webzine.detail");
	
	_webzineDetail.data = {
			addImageCount : 1,
			addProductCount : 1,
			changedImageSeq : [],
			sellStatCodes : {}
	};
	_webzineDetail.init = function(){
		
		//에디터 초기화
		CKEDITOR.replace( 'wbznInfo', {
			  uploadUrl: '/display/contents/editer/upload'
		});
		
		_webzineDetail.changeLayoutByWebzineType($('[name=wbznType]:checked').val());
		_webzineDetail.changeLayoutByMovieUpload($('[name=movieUploadYn]:checked').val());
		
		_webzineDetail.event();
		
		new abc.biz.display.common.processImage({
			file: '#pcImageFile',
			name: '#pcImageName'
		});
		
		new abc.biz.display.common.processImage({
			file: '#mobileImageFile',
			name: '#mobileImageName'
		});
		
		new abc.biz.display.common.processImage({
			file: '#movieFile',
			name: '#movieName',
			type: 'M'
		});
		
		var addImage = $('#append-add-image');
		
		if(addImage.find('tr').length > 0){
			_webzineDetail.data.addImageCount = addImage.find('tr').length+1;
			
			addImage.find('tr').each(function(i,v){
				
				new abc.biz.display.common.processImage({
					file: '#addImageFile'+i,
					name: '#addImageName'+i,
					allow: 'jpg, jpeg, png, gif, bmp'
				});
			})
		}
		
		//상품상태코드 설정
		var sellStatCodes = $.parseJSON(document.frm.sellStatCodes.value).SELL_STAT_CODE;
		var codes = sellStatCodes.code.split('|');
		var texts = sellStatCodes.text.split('|');
		
		$.each(codes, function(i,v) {
			_webzineDetail.data.sellStatCodes[v] = texts[i];
		})
		
	}
	
	/**
	 * 버튼 이벤트 추가
	 */
	_webzineDetail.event = function(){
		
		//초기화
		$('#refresh-data').on('click', function(e){
			console.log('refresh-data');
		});
		
		//상품 등록
		$('#add-webzine-product').on('click', function(e){
			abc.productSearchPopup(true, 'abc.biz.display.contents.webzine.detail.productCallback');
		});
		
		//연관상품 등록
		$('#del-webzine-product').on('click', function(e){
			var checked = $('[id^=productChecked]:checked');
			var productLayer = checked.parents('.product-layer');
			
			if(productLayer.length == 0){
				alert('삭제할 연관상품를 선택해주세요.');
				return false;
			}
			
			var cfm = confirm('삭제하시겠습니까?');
			if(cfm)	productLayer.remove();
		});
		
		//삭제
		$('#del-webzine').on('click', function(e){
			console.log('del webzine');
		});
		//목록
		$('#go-list').on('click', function(e){
			console.log('go list');
		});
		
		//저장
		$('#save-webzine').on('click', function(e){
			
//			if(_webzineDetail.valid()){
			document.forms.frm.wbznInfo.innerText = CKEDITOR.instances.wbznInfo.getData();
			
			var url = '/display/contents/save';
			if($('#wbznSeq').val() != null && $('#wbznSeq').val() != ''){
				url = '/display/contents/modify';
			}
			
			var form = $.form(document.forms.frm);
			
			form.submit({
				url	: url,
				method	: "POST",
				data : $.paramObject({
					"changedImageSeq" : _webzineDetail.data.changedImageSeq
				}),
				valid	: function($f){
					return true;
				},
				success : function(data){
					alert('저장 되었습니다.');
		    		location.href = '/display/contents/webzine';
				},
				error	: function(e){
					alert(e.message);
			    	console.log(e);
				}
			});

//			}
			
		});

		//웹진 유형 변경
		$(document.frm.wbznType).on('change', function(){
			_webzineDetail.changeLayoutByWebzineType(this.value);
		});
		
		//연결동영상 유형 변경
		$(document.frm.movieUploadYn).on('change', function(){
			_webzineDetail.changeLayoutByMovieUpload(this.value);
		});
		
		$(document.frm.dispYn).on('change', function(){
			
			if(this.value == 'N'){
				document.frm.dispStartYmdDot.value = '';
			}
			
		});
		
		$(document.frm.dispYn).on('click', function(){
			if(this.value == 'N'){
				$(document.frm.dispStartYmdDot).attr('disabled', true);
			}else{
				$(document.frm.dispStartYmdDot).attr('disabled', false);
			}
		});
		
		
		$('#add-add-image').on('click', function(e){
			var tmpl = document.templateOneRoot("#add-image-tmpl");
			
			tmpl.find('#chkProd').attr('id', 'chkProd'+_webzineDetail.data.addImageCount).next().attr('for', 'chkProd'+_webzineDetail.data.addImageCount);
			tmpl.find('#addImageFile').attr('id', 'addImageFile'+_webzineDetail.data.addImageCount).next().attr('for', 'addImageFile'+_webzineDetail.data.addImageCount);
			tmpl.find('#addImageName').attr('id', 'addImageName'+_webzineDetail.data.addImageCount).next().attr('for', 'addImageName'+_webzineDetail.data.addImageCount);
			
			$('#append-add-image').append(tmpl);
			
			new abc.biz.display.common.processImage({
				file: '#addImageFile'+_webzineDetail.data.addImageCount,
				name: '#addImageName'+_webzineDetail.data.addImageCount,
				allow : 'jpg, jpeg, png, gif, bmp'
			});
			
			_webzineDetail.data.addImageCount++;
		});
		
		$('#del-add-image').on('click', function(e){
			var checked = $('[id^=chkProd]:checked');
			var imageLayer = checked.parents('.add-image-layer');
			
			if(imageLayer.length == 0){
				alert('삭제할 이미지를 선택해주세요.');
				return false;
			}
			
			$.each(checked, function(i,v){
				_webzineDetail.data.changedImageSeq.push($(v).prev().val());
			})
			
			
			var cfm = confirm('삭제하시겠습니까?');
			if(cfm)	imageLayer.remove();
		});
		
		$('#addImageCheckedAll').on('click', function(e){
			
			if(this.checked){
				$('[id^=chkProd]').prop('checked', true);
			}else{
				$('[id^=chkProd]').prop('checked', false);
			}
		});
		
		$('#productCheckedAll').on('click', function(e){
			
			if(this.checked){
				$('[id^=productChecked]').prop('checked', true);
			}else{
				$('[id^=productChecked]').prop('checked', false);
			}
		});
		
		$('body').on('click', '.open-product-popup', function(e){
			
			var data = $(this).data();
			abc.readonlyProductDetailPopup({ prdtNo : data.prdtNo });
		});
	}
	
	
	/**
	 * 웹진 유형 별 layout 변경
	 */
	_webzineDetail.changeLayoutByWebzineType = function(wbznType){

		//카탈로그
		if(wbznType == 'C'){
			$('#area-webzine-no').show();
			$('.area-add-image').show();
			$('#area-movie').show();
			$('#area-news-content').hide();
			if($('[name=movieUploadYn]:checked').val() == 'Y'){
				$('#area-movieUpload-N ul').hide();
			}else{
				$('#area-movieUpload-N ul').show();
			}
			$('#area-movie th span').removeClass('th-required');
			
			$('.pc-thumb-size').text('권장사이즈 590*590 (최대 10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
			$('.mo-thumb-size').text('권장사이즈 660*660 (10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
		}
		//동영상
		else if(wbznType == 'M'){
			$('#area-webzine-no').hide();
			$('.area-add-image').hide();
			$('#area-movie').show();
			$('#area-news-content').hide();
			$('#area-movieUpload-N ul').hide();
			$('#area-movie th span').addClass('th-required');
			
			$('.pc-thumb-size').text('권장사이즈 590*332 (최대 10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
			$('.mo-thumb-size').text('권장사이즈 660*370 (10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
		}
		//협찬 및 기사 
		else if(wbznType == 'N'){
			$('#area-webzine-no').hide();
			$('.area-add-image').hide();
			$('#area-movie').hide();
			$('#area-news-content').show();
			$('#area-movieUpload-N .ul').hide();
			$('#area-movie th span').removeClass('th-required');
			
			$('.pc-thumb-size').text('권장사이즈 388*220 (최대 10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
			$('.mo-thumb-size').text('권장사이즈 660*370 (10MB까지 등록가능 파일유형 : jpg, jpeg, png, gif, bmp)');
		}
	}
	
	/**
	 * 동영상 업로드 방법 별 layout 변경
	 */
	_webzineDetail.changeLayoutByMovieUpload = function(movieUploadYn){
		
		//직접 업로드
		if(movieUploadYn == 'Y'){
			$('#area-movieUpload-Y').show();
			$('#area-movieUpload-N').hide();
			$('#area-movieUpload-N ul').hide();
		}
		//URL 입력
		else{
			$('#area-movieUpload-Y').hide();
			$('#area-movieUpload-N').show();
			if($('[name=wbznType]:checked').val() == 'C'){
				$('#area-movieUpload-N ul').show();
			}else{
				$('#area-movieUpload-N ul').hide();
			}
		}
	}
	
	_webzineDetail.productCallback = function(data){
		
		console.log(data);
		
		var duplicationCheck = false;
		$.each(data, function(i,v){
			var tmpl = document.templateOneRoot("#product-tmpl");
			
			//중복체크
			$('#append-product').find('[name*=prdtNo]').each(function(k,m){
				
				if(m.value == v.prdtNo){
					alert('같은 상품이 등록되어 있습니다.');
					duplicationCheck = true;
					return false;
				}
				
			});
			
			if(duplicationCheck) return false;
			
			tmpl.find('#productChecked').attr('id', 'productChecked'+_webzineDetail.data.addProductCount).next().attr('for', 'productChecked'+_webzineDetail.data.addProductCount);
			tmpl.find('[name*=prdtNo]').val(v.prdtNo);
			tmpl.find('.prdtNo').text(v.prdtNo);
			tmpl.find('.prdtName').text(v.prdtName);
			tmpl.find('.prdtCategory').text(v.stdrCtgrName);
			tmpl.find('.sellStatCodeName').text(_webzineDetail.data.sellStatCodes[v.sellStatCode]);
			tmpl.find('.prdtImage').css('background-image', 'url('+v.titleImageUrl+')');
			tmpl.find('.dispYn').text(v.dispYn);
			
			tmpl.find('.open-product-popup').data(v);
			
			$('#append-product').append(tmpl);
			
			_webzineDetail.data.addProductCount++;
		})
	}
	
	$(function() {
		_webzineDetail.init();
	});
})();