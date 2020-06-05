(function() {

	var _otsDetail = abc.object.createNestedObject(window,"abc.biz.display.contents.ots.detail");
	
	_otsDetail.data = {
			addProductCount : 1,
			sellStatCodes : {}
	};
	_otsDetail.init = function(){
		
		//에디터 초기화	
		CKEDITOR.replace( 'wbznInfo', {
			  uploadUrl: '/display/contents/editer/upload'
		});
		
		_otsDetail.event();
		
		new abc.biz.display.common.processImage({
			file: '#pcImageFile',
			name: '#pcImageName'
		});
		
		new abc.biz.display.common.processImage({
			file: '#mobileImageFile',
			name: '#mobileImageName'
		});
		
		//상품상태코드 설정
		var sellStatCodes = $.parseJSON(document.frm.sellStatCodes.value).SELL_STAT_CODE;
		var codes = sellStatCodes.code.split('|');
		var texts = sellStatCodes.text.split('|');
		
		$.each(codes, function(i,v) {
			_otsDetail.data.sellStatCodes[v] = texts[i];
		})
	}
	
	/**
	 * 버튼 이벤트 추가
	 */
	_otsDetail.event = function(){
		
		//초기화
		$('#refresh-data').on('click', function(e){
			console.log('refresh-data');
		});
		
		//상품 등록
		$('#add-webzine-product').on('click', function(e){
			abc.productSearchPopup(true, 'abc.biz.display.contents.ots.detail.productCallback');
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
			console.log('save webzine');
			
			document.forms.frm.wbznInfo.innerText = CKEDITOR.instances.wbznInfo.getData();
			
			if(document.forms.frm.wbznInfo.innerText == ''){
				alert("본문은 필수입니다.");
				return;
			}
			var url = '/display/contents/save';
			if($('#wbznSeq').val() != null && $('#wbznSeq').val() != ''){
				url = '/display/contents/modify';
			}
			
			var form = $.form(document.forms.frm);
			
			form.submit({
				url	: url,
				method	: "POST",
				valid	: function($f){
					return true;
				},
				success : function(data){
					alert('저장 되었습니다.');
		    		location.href = '/display/contents/ots/';
				},
				error	: function(e){
					alert(e.message);
			    	console.log(e);
				}
			});
			
		});

		$(document.frm.dispYn).on('click', function(){
			if(this.value == 'N'){
				$(document.frm.dispStartYmdDot).attr('disabled', true);
			}else{
				$(document.frm.dispStartYmdDot).attr('disabled', false);
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
	
	_otsDetail.productCallback = function(data){
		
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
			
			tmpl.find('#productChecked').attr('id', 'productChecked'+_otsDetail.data.addProductCount).next().attr('for', 'productChecked'+_otsDetail.data.addProductCount);
			tmpl.find('[name*=prdtNo]').val(v.prdtNo);
			tmpl.find('.prdtNo').text(v.prdtNo);
			tmpl.find('.prdtName').text(v.prdtName);
			tmpl.find('.prdtCategory').text(v.stdrCtgrName);
			tmpl.find('.sellStatCodeName').text(_otsDetail.data.sellStatCodes[v.sellStatCode]);
			tmpl.find('.prdtImage').css('background-image', 'url('+v.titleImageUrl+')');
			
			tmpl.find('.open-product-popup').data(v);
			
			$('#append-product').append(tmpl);
			
			_otsDetail.data.addProductCount++;
		})
	}
	
	$(function() {
		_otsDetail.init();
	});
})();