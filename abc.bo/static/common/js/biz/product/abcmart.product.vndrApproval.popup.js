(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.vndrApproval.popup");
	
	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function(){
		
		//승인반려 클릭
		$("#approval-return").click(function() {
			var checkedRows = opener.abc.biz.product.vndrApproval.sheet.list.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var vndrApprovalList = [];
			var returnRsnText = $('textarea[name="returnRsnText"]').val();
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				var item = opener.abc.biz.product.vndrApproval.sheet.list.GetRowData(row);
				item.aprvStatCode = opener.abc.biz.product.vndrApproval.APRV_STAT_CODE_REJECT;
				item.returnRsnText = returnRsnText;
				item.rgsterNo = item.rgsterNo;
				vndrApprovalList.push(item);
			}
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/vndrApproval/batchModify",
				data : $.paramObject(vndrApprovalList)
			})
			.done(function(data){
				alert('승인반려 처리되었습니다.');
				opener.abc.biz.product.vndrApproval.getList();
				window.close();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		});
	}
	
	$(function() {
		_object.init();
	});
	
})();