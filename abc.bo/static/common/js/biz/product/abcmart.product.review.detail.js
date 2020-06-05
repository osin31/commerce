(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.review.detail");

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

		//초기화
		$("#clear").click(function(e) {
			var prdtRvwSeq = $('input[name="prdtRvwSeq"]').val();
			abc.biz.product.utils.initFormData("detail-form");
			$('#aswr-cont-text').val('');
			$('input[name="prdtRvwSeq"]').val(prdtRvwSeq);
			$('#cnsl-type-code').change();
		});

		// 삭제
		$("#delete").click(function(e) {
			if(!confirm("정말 삭제하시겠습니까?")) { return false; }
			e.preventDefault();

			$.form("#detail-form").submit({
				url : "/product/review/delete",
				type : "POST",
				dataType : "json",
				success : function(data) {
					alert("삭제되었습니다.");
					opener.abc.biz.product.review.getList();
					window.close();
				},
				error : function(e) {
					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
					}
					console.log(e);
				}
			});
		});

		// 저장
		$("#save").click(function(e) {
			e.preventDefault();

			$.form("#detail-form").submit({
				url : "/product/review/save",
				type : "POST",
				dataType : "json",
				success : function(data) {
					alert("답변이 등록되었습니다.");
					opener.abc.biz.product.review.getList();
					window.close();
				},
				error : function(e) {
					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
					}
					console.log(e);
				}
			});
		});

		// 상담유형 선택 시
		$("#cnsl-type-code").off().on('change', function(f) {
			if($(this).val() == "") {
				$('#cnsl-type-dtl-code').hide();
				abc.getCounselScriptTitle("", "", "counsel-script-list");
			} else {
				$('#cnsl-type-dtl-code').show();
				abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $(this).val(), "cnsl-type-dtl-code");
			}
		});

		// 상담분류 선택 시
		$("#cnsl-type-dtl-code").off().on('change', function(f) {
			abc.getCounselScriptTitle($("#cnsl-type-code").val(), $(this).val(), "counsel-script-list");
		});

		// 스크립트제목 선택 시
		$("#counsel-script-list").off().on('change', function(f) {
			abc.getCounselScript($(this).val(), "aswr-cont-text");
		});

		// 스크립트 조회
		//abc.getCounselScriptTitle($("#cnsl-type-code").val(), $("#cnsl-type-dtl-code").val(), "counsel-script-list");

	}

	$(function() {
		_object.init();
	});

})();