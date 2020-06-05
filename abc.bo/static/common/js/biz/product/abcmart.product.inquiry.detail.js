(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.inquiry.detail");

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

		// 삭제
		$("#delete").click(function(e) {
			e.preventDefault();
			if(!confirm("정말 삭제하시겠습니까?")) { return false; }
			var option = {
				url : "/product/inquiry/delete",
				type : "POST",
				dataType : "json",
				success : function(data) {
					alert("삭제되었습니다.");
					opener.abc.biz.product.inquiry.getList();
					window.close();
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

		// 저장
		$("#save").click(function(e) {
			e.preventDefault();
			var option = {
				url : "/product/inquiry/save",
				type : "POST",
				dataType : "json",
				success : function(data) {
					alert("답변이 등록되었습니다.");
					opener.abc.biz.product.inquiry.getList();
					window.close();
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

		// 메모 저장
		$("#memo-save").click(function(e) {
			e.preventDefault();
			var inquiry = {
				  memoText : $('textarea[name="memoText"]').val()
				, prdtInqrySeq : $('input[name="prdtInqrySeq"]').val()
			};
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/inquiry/saveMemo",
				data : $.paramObject(inquiry)
			})
			.done(function(data){
				alert("메모가 등록되었습니다.");
				$('textarea[name="memoText"]').val('');
				abc.biz.product.inquiry.detail.getMemoList();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		})

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

		// 초기화 클릭 시
		$('#clear').off().on('click', function() {
			var prdtNo = $('input[name="prdtNo"]').val();
			var prdtInqrySeq = $('input[name="prdtInqrySeq"]').val();

			$('#aswr-cont-text').val('');
			abc.biz.product.utils.initFormData("detail-form");

			$('input[name="prdtNo"]').val(prdtNo);
			$('input[name="prdtInqrySeq"]').val(prdtInqrySeq);
			$('input[name="aswrStatCode"]').prop('checked', false);
			$('#cnsl-type-code').change();
		});

		// 스크립트 조회
		abc.getCounselScriptTitle($("#cnsl-type-code").val(), $("#cnsl-type-dtl-code").val(), "counsel-script-list");
	}

	/**
	 * 메모 삭제
	 */
	_object.deleteMemo = function(prdtInqryMemoSeq) {
		if(!confirm("정말 삭제하시겠습니까?")) { return false; }
		var inquiry = {
			  prdtInqryMemoSeq : prdtInqryMemoSeq
			, prdtInqrySeq : $('input[name="prdtInqrySeq"]').val()
		};
		$.ajax({
			dataType : "json",
			type :"POST",
			url : "/product/inquiry/deleteMemo",
			data : $.paramObject(inquiry)
		})
		.done(function(data){
			alert("메모가 삭제되었습니다.");
			abc.biz.product.inquiry.detail.getMemoList();
		})
		.fail(function(e){
			console.log(e);
			alert(e.responseJSON.message);
		});
	}

	/**
	 * 메모 목록 조회
	 */
	_object.getMemoList = function() {
		$.ajax({
			dataType : "json",
			type :"POST",
			url : "/product/inquiry/getMemoList",
			data : $.paramObject( { prdtInqrySeq : $('input[name="prdtInqrySeq"]').val() } )
		})
		.done(function(data){
			$('#memo-list').empty();
			var tmpl = document.templateOneRoot('#inquiry-memo-tmpl');
			for(var i=0; i<data.length; i++) {
				var item = data[i];
				var clone = tmpl.clone();

				clone.find('.user-id').html('<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup(&quot;' + item.rgsterNo + '&quot;)">' + item.rgsterInfo + '</a></span>');
				clone.find('.regist-date').html( _object.timestampToString(item.rgstDtm) );

				if(_object.adminNo == item.rgsterNo) {
					clone.find('.btn-msg-list-del').on('click', function() {
						_object.deleteMemo(item.prdtInqryMemoSeq);
					});
				} else {
					clone.find('.btn-msg-list-del').remove();
				}
				clone.find('.msg-desc').html(item.memoText);

				$('#memo-list').append(clone);
			}
		})
		.fail(function(e){
			console.log(e);
			alert(e.responseJSON.message);
		});
	}

	/**
	 * Timestamp to String
	 * Timestamp -> yyyy-mm-dd hh:mm:ss 형태로 바꿈
	 */
	_object.timestampToString = function(timestamp) {
		var date 	= new Date(timestamp);
		var _year 	= date.getFullYear();
		var _month 	= ((date.getMonth()+1).toString().length == 2) ? date.getMonth()+1 : "0"+(date.getMonth()+1);
		var _day 	= (date.getDate().toString().length == 2) ? date.getDate() : "0"+date.getDate();
		var _hour 	= (date.getHours().toString().length == 2) ? date.getHours() : "0"+date.getHours();
		var _minute = (date.getMinutes().toString().length == 2) ? date.getMinutes() : "0"+date.getMinutes();
		var _second = (date.getSeconds().toString().length == 2) ? date.getSeconds() : "0"+date.getSeconds();
		return _year +'-'+ _month +'-'+ _day +' '+ _hour +':'+ _minute +':'+ _second;
	}

	$(function() {
		_object.init();
	});

})();