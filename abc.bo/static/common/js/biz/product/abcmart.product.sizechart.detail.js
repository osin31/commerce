(function() {

	var _sizechartDetail = abc.object.createNestedObject(window,"abc.biz.product.sizechart.detail");
	
	_sizechartDetail.init = function(){
		_sizechartDetail.event();
		
		new abc.biz.display.common.processImage({
			file: '#inputFile',
			name: '#image-name'
		});
	}
	
	
	/**
	 * 버튼 이벤트 추가
	 */
	_sizechartDetail.event = function(){
		//삭제
		/*$('#del-sizechart').on('click', function(e){
			if($('#sizeChartSeq').val() != null && $('#sizeChartSeq').val() != ''){
				$(document.forms.frm).ajaxForm({
				    'dataType': 'json',
				    'type': 'POST',
				    'url': '/product/sizechart/remove',
				    'success': function(d) {
				    	alert('삭제 되었습니다.');
			    		location.href = '/product/sizechart';
				    },
				    'error': function(e){
				    	alert('실패했습니다.');
				    	console.log(e);
				    }
				});
				
				$(document.forms.frm).submit();
			}
		});*/
		
		//목록
		$('#go-list').on('click', function(e){
			location.href='/product/sizechart';
		});
		
		//저장
		$('#save-sizechart').on('click', function(e){
			console.log('save sizechart');
			
			if(_sizechartDetail.valid()){
				var url = '/product/sizechart/save';
				if($('#sizeChartSeq').val() != null && $('#sizeChartSeq').val() != ''){
					url = '/product/sizechart/modify';
				}
				
				var form = $.form(document.forms.frm);
				
				form.submit({
				    'type': 'POST',
				    'url': url,
				    'success': function(d) {
				    	alert('저장 되었습니다.');
			    		location.href = '/product/sizechart';
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}
			
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, "abc.biz.product.sizechart.detail.callbackFindBrand");
		});
		
		$('[name=brandAssignYn]').on('change', function(){
			$('.ip-search-box').hide();
			if($(this).val() == 'Y') $('.ip-search-box').show();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('frm', false);
			$('#del-image').trigger('click');
		});
		
	}
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_sizechartDetail.callbackFindBrand = function(data) {
		$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
	
	/**
	 * 유효성 체크 
	 */
	_sizechartDetail.valid = function(){
		
		var frm = document.forms.frm;
		
		if(frm.stdCtgrNo.value == ''){
			alert('표준 카테고리 선택은 필수 입니다.');
			return false;
		}
		
		if($('#image-name').text() == '') {
			alert('이미지는 필수 입니다.');
			return false;
		}
		
		return true;
	}
	
	$(function() {
		_sizechartDetail.init();
	});
})();