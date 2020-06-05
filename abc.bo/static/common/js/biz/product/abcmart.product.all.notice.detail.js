(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.all.notice.detail");
	
	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		
		CKEDITOR.replace("all-notc-cont-text", { uploadUrl: "/product/allNotice/editer/upload" }); // 공지내용 초기화
		
		// 시작기간~끝기간 설정
		if($('[name="vndrPrdtAllNotcSeq"]').val()) {
			// 등록된 기간이 있는 경우 해당 기간으로 시작과 끝으로 설정
			// 시작기간 설정
			$('#fromDate').datepicker( "option", "maxDate", $('#toDate').val());
			// 끝기간 설정
			$('#toDate').datepicker( "option", "minDate", $('#fromDate').val()).on("change", function(){
				$('#fromDate').datepicker("option", "maxDate", $.datepicker.parseDate("yy.mm.dd", $(this).val()));
			});
		} else {
			abc.biz.product.utils.setSearchStartAndEndDate($('#fromDate'), $('#toDate'));
		}
		
		// 저장
		$("#save").click(function(e) {
			e.preventDefault();
			
			$("#all-notc-cont-text").text(CKEDITOR.instances["all-notc-cont-text"].getData());
			
			var option = {
					url : "/product/allNotice/save",
					type : "POST",
					dataType : "json",
					success : function(data) {
						alert("상품전체공지가 "+ ($('input[name="type"]').val() == 'C' ? '등록' : '수정') +"되었습니다.");
						location.href = "/product/allNotice";
					},
					error : function(e) {
						if(e != null && e.message != null && e.message != "") {
							alert(e.message);
						}
						console.log(e);
					}
				};
			$.form("#detail-form").submit(option);
		});
		
		// 등록인 경우 데이터 입력 시
		$('#detail-form').change(function(e) {
			_object.dataChangeFlag = true;
		});
		
		$('#list').click(function() {
			if($('input[name="type"]').val() == "C") {
				if(CKEDITOR.instances["all-notc-cont-text"].getData()) {
					_object.dataChangeFlag = true;
				}
			}
			if(_object.dataChangeFlag) {
				if(!confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?')) { return false; }
			} 
			location.href = "/product/allNotice";
		});
		
	}
	
	$(function() {
		_object.init();
	});
	
})();