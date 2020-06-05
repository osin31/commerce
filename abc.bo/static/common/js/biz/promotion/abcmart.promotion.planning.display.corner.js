(function() {

	var _corner = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display.corner");
	var params = abc.param.getParams();
	
	/**
	 * 초기화
	 */
	_corner.init = function() {
		
		_corner.event();
		
		new abc.biz.display.common.processImage({
			file: '#imageFile',
			name: '#imageName'
		});
		
		if(params.selectRow != null && params.selectRow != ''){
			
			var data = opener.abc.biz.promotion.planning.display.manage.getGridRow(params.selectRow);
			var imageFileWrap = $('#imageFile').parents('.file-wrap');
			
			console.log(data);
			
			$.each(data, function(i,v){
				var el = document.forms.cornerForm[i];
				
				if(el){
					el.value = v;
				}
				
				if(i == 'cornerNameDispType'){
					if(v == 'I'){
						$('.image-upload-area').show();
					}else{
						$('.image-upload-area').hide();						
					}
				}
				if(i == 'imageUrl'){
					if(v){
						var img = new Image();
						$(img).attr('src', v);
						imageFileWrap.find('.img-wrap').append(img);
					}
				}
			});
			
			 /*if (data.imageUrl != null || data.imageUrl != '') {
				 var img = new Image();
					$(img).attr('src', data.imageUrl);
					imageFileWrap.find('.img-wrap').append(img);
			 }*/
		}
	}
	
	/**
	 * 이벤트
	 */
	_corner.event = function(){
		
		$('input[name=cornerNameDispType]').on('click', function() {
			
			if ($(this).val() == 'I') {
				$('.image-upload-area').show();
			} else {
				$('.image-upload-area').hide();
			}
		});
		
		/** 코너 저장 */
		$('#saveBtn').on('click', function() {
			
			var jsonData = {};
			
			$.each($(document.forms.cornerForm).serializeArray(), function(i,v) {
				jsonData[v.name] = v.value;
			});
			
			if (jsonData.plndpCornerName == '') {
				alert('코너명을 입력해주세요.');
				return false;
			}
			if (jsonData.cornerDescText == '') {
				alert('코너설명을 입력해주세요.');
				return false;
			}
			if (jsonData.sortSeq == '') {
				alert('노출 순서를 입력해주세요.');
				return false;
			}
			
			if($("input[name=cornerNameDispType]:checked").val() == "I" && $("#imageFile").val() == ""){
				alert("이미지는 필수입니다.");
				return false;
			}
			
			if ($('#imageFile').val() != null && $('#imageFile').val() != '') {
				jsonData.imageFile = $('#imageFile').clone();
				jsonData.imageUrl = $('#imageFile').parents('.file-wrap').find('.img-wrap img').attr('src');				
			}
			
			if(params.selectRow != null && params.selectRow != ''){
				jsonData.type = 'update';
			} else {
				jsonData.type = 'insert';
			}
			
			// callback
			var cb = 'abc.biz.promotion.planning.display.manage.cornerCallback';
			var opFunc = new Function('return opener.'+cb)();
			opFunc(jsonData);
			window.close();
		});
		
	}
	
	$(function() {
		_corner.init();
	});
	
})();