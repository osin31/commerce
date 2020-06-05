(function() {

	var _cornerNamePop = abc.object.createNestedObject(window,"abc.biz.display.content.corner.name.pop");
	var params = abc.param.getParams();
	
	/**
	 * 초기화
	 */
	_cornerNamePop.init = function(){
		_cornerNamePop.event();
		
		_cornerNamePop.imageUpload = new abc.biz.display.common.processImage({
			file: '#imageUpload',
			name: '#imageName'
		});
		
		//상위 팝업 그리드에서 데이터 가져와 세팅(수정 시)
		if(params.selectRow != ''){
			var data = opener.abc.biz.display.content.corner.info.pop.getGridRow(params.gridId, params.selectRow);
			var imageFileWrap = $('#imageUpload').parents('.file-wrap');
			
			$.each(data, function(i,v){
				var el = document.frm[i];
				
				if(el){
					el.value = v;
				}
				
				if(i == 'imageName'){
					imageFileWrap.find('.subject').text(v);
				}else if(i == 'imageUrl'){
					if(v){
						var img = new Image();
						$(img).attr('src', v);
						imageFileWrap.find('.img-wrap').append(img);
					}
				}
			});
		}
	}
	
	/**
	 * 이벤트
	 */
	_cornerNamePop.event = function(){
		
		//저장 시 상위 팝업으로 데이터 return 
		$('.btn-save').on('click', function(){
			
			if(!_cornerNamePop.valid()){
				return false;
			}
			
			var data = _cornerNamePop.makeJsonData();
			
			data.imageUpload = $('#imageUpload').clone();
			data.imageSrc = $('#imageUpload').parents('.file-wrap').find('.img-wrap img').attr('src');
			data.gridId = params.gridId;
			data.selectRow = params.selectRow;
			data.contTypeCode = params.contTypeCode;
			
			opener.abc.biz.display.content.corner.info.pop.callback(data);
			
			window.close();
		});
		
		
		$(document.frm.dispYn).on('click', function(){
			
			_cornerNamePop.LayerDisplayYn(this.value);
		});
		
		_cornerNamePop.LayerDisplayYn($('[name=dispYn]:checked').val());
	}
	
	/**
	 * 전시 여부 이벤트
	 */
	_cornerNamePop.LayerDisplayYn = function(v){
		if(v == 'Y'){
			$('[name=dispStartYmd]').attr('disabled', false);
			$('[name=dispEndYmd]').attr('disabled', false);
		}else{
			$('[name=dispStartYmd]').attr('disabled', true).val('');
			$('[name=dispEndYmd]').attr('disabled', true).val('');
		}
	}
	
	/**
	 * form 데이터 json으로 변경
	 */
	_cornerNamePop.makeJsonData = function(){
		
		var jsonData = {};
		
		$.each($(document.frm).serializeArray(), function(i,v){
			jsonData[v.name] = v.value;
		});
		
		return jsonData;
	}
	
	/**
	 * 유효성 체크
	 */
	_cornerNamePop.valid = function(){
		var frm = document.frm;
		
		if(frm.dispYn.value == 'Y'){
			if(frm.dispStartYmd.value == ''){
				alert('전시일때 전시시작일은 필수입니다.');
				return false;
			}else if(frm.dispEndYmd.value == ''){
				alert('전시일때 전시종료일은 필수입니다.');
				return false;
			}
			
			if(frm.dispStartYmd.value.replaceAll('\\.','') > frm.dispEndYmd.value.replaceAll('\\.','')){
				alert('전시종료일이 전시시작일보다 이전입니다.');
				return false;
			}
		}
		
		if(frm.dispCornerName.value == ''){
			alert('전시코너명은 필수입니다.');
			return false;
		}
		
		if(params.contTypeCode == 'I'){
			if(frm.imageName.value == ''){
				alert('이미지는 필수입니다.');
				return false;
			}
		}
		
		return true;
	}
	
	$(function() {
		_cornerNamePop.init();
	});
	
})();