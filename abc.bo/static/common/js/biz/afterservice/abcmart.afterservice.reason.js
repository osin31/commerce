
(function() {
	
	var _afterServiceReason = abc.object.createNestedObject(window,"abc.biz.afterservice.reason");
	
	/**
	 * dbo.OC_AS_ACCEPT_PRODUCT 
	 * AS_ACCEPT_CONT_TEXT	varchar(500)	AS접수내용
	 * 한글기준 2바이트 200자 기준. 추후에 컬러 ㅁ다시 확인하여. 진행. 
	 */
	_afterServiceReason.stringLengthCheck = function(obj) {
		if(obj.length <= 200){
			$('#counter').html(obj.length);
		}else{
			alert("AS접수내용은 200자를 초과할 수 없습니다.");
			$("#reasonText").val(obj.substring(0, 200));
			return false;
		}
	}
	
	/**
	 * 닫기 버튼 클릭시
	 */
	_afterServiceReason.checkClosed = function(){
    }
	
	/**
	 * 등록 버튼 클릭시 
	 */
	$("#reg").click(function(){	
		if($('#reasonText').val() == null || $('#reasonText').val() == "" ){
			alert("등록된 내용이 없습니다.");
		}else{
			$('#asAcceptContText',opener.document).val($('#reasonText').val());
			self.close();
		}
	});
	
	/**
	 * 파일 VIEW
	 */
	$(document).on("click","[name=popupImage]",function(){
		var atchFileUrl = $(this).parent().find('[name=atchFileUrl]').val(); // DB에 저장된 실 URL
		//abc.open.open(atchFileUrl,'imagePopup','');
		var pop = abc.open.popup({
					url 	:	atchFileUrl,
					winname :	"imagePopup", 	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
					method	: 	"get",
					title 	:	"A/S image view",
					width 	:	700,
					height	:	500
			  });
	});
	
})();