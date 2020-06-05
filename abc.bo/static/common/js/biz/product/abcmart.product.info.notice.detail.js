(function() {

	var _info = abc.object.createNestedObject(window,"abc.biz.product.info");
	
	/**
	 * 초기화
	 */
	_info.init = function(){
		
		// 에디터 초기화
		CKEDITOR.replace( 'infoNotcWriteInfo' );
		
		this.event();
	}	
	
	/**
	 * 이벤트
	 */
	_info.event = function() {
		
		// 저장
		$("#saveBtn").on("click", function() {
			
			if(!_info.validation()) {
				return;
			}
			
			document.forms.infoForm.infoNotcWriteInfo.innerText = CKEDITOR.instances.infoNotcWriteInfo.getData();
			
			var form = $.form(document.forms.infoForm);
			form.submit({
				url : '/product/info-notice/dupCheck',
				method : 'POST',
				valid	: function($f){
					return true;
				},
				success : function(data) {
					if (data != null && data.prdtInfoNotcSeq != null && data.prdtInfoNotcSeq != '') {
						// 중복 데이터가 있는 경우
						if (confirm('이미 존재하는 항목입니다. 덮어쓰기 하시겠습니까?')) {
							$('input[name=prdtInfoNotcSeq]').val(data.prdtInfoNotcSeq); // 기존 데이터의 seq 설정
							_info.save(); // update
						}
					} else {
						// 중복 데이터가 없는 경우
						_info.save(); // 저장
					}
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});
		});
		
		$("#prdtInfoNotcCode").on("change", function() {
			
			var $selected = $('#prdtInfoNotcCode option:selected');
			
			if ($selected.val() != '') {				
				$("input[name=infoNotcName]").val($selected.text());
				$("input[name=infoNotcName]").show();
			} else {
				$("input[name=infoNotcName]").hide();
			}
		});
	}
	
	/**
	 * 상품정보고시 저장
	 */
	_info.save = function() {
		var form = $.form(document.forms.infoForm);
		form.submit({
			url : '/product/info-notice/save/detail',
			method : 'POST',
			valid	: function($f){
				return true;
			},
			success : function(data) {
				alert('저장되었습니다.');
				location.href = '/product/info-notice';
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			},
			always : function() {
				$('input[name=delSeq]').val('');
			}
		});
	}
	
	/**
	 * validation
	 */
	_info.validation = function() {
		
		if(abc.text.isBlank($("#prdtInfoNotcCode").val())) {
			alert("항목을 선택해주세요.");
			return false;
		}
		
		if(abc.text.isBlank($("#infoNotcName").val())) {
			alert("항목명을 입력해주세요.");
			return false;
		}
		
		if(abc.text.isBlank($("#sortSeq").val())) {
			alert("노출 우선순위를 입력해주세요.");
			return false;
		}		
		
		return true;
	}
	
	$(function() {
		
		_info.init();
	});
	
})();