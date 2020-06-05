/**
 * 주문 관리자 메모 목록
 */
(function(){
	
	var _vendorMemo = abc.object.createNestedObject(window,"abc.biz.order.vendor.memo");
	_vendorMemo.orderNo = "";
	_vendorMemo.init = function(orderNo) {
		_vendorMemo.orderNo = orderNo;
		$("#btnMemoRegist").click(function(){
			_vendorMemo.regist();
		})
		
		$("#viewAll").click(function(){
			_vendorMemo.filter();
		})
		
		$("#viewOrder").click(function(){
			_vendorMemo.filter(abc.biz.order.const.ORDER);
		})
		
		$("#viewClaim").click(function(){
			_vendorMemo.filter(abc.biz.order.const.CLAIM);
		})
		this.list();
	}
	
	_vendorMemo.regist = function(){
		if($("textarea[name=memoText]").val() == null || $("textarea[name=memoText]").val() == ""){
			alert("관리자 메모를 입력하세요.");
			$("textarea[name=memoText]").focus();
		} else {
			$.ajax({
				type :"post",
				url : "/delivery/vendor/order/regist-vendor-memo",
				data: $("#memoForm").serialize()
			})
			.done(function(result){
				_vendorMemo.reLoad();
			})
			.fail(function(e){
				
			});
		}
	}
	
	_vendorMemo.list = function(){
		$.ajax({
			type :"post",
			url : "/delivery/vendor/order/read-memo-vendor-info-tab/list",
			data: { "orderNo" : _vendorMemo.orderNo }
		})
		.done(function(result){
			var totalMemoCount = result.Total;
			var orderMemoCount = 0;
			var claimMemoCount = 0;
			var data = result.Data;
			var codeList = result.codeList;
			var orderMemoGbnCode = {};
			var claimGbnCode = {};
			var list = $("#memoList");
			var adminNo = result.adminNo;
			
			if(codeList != null && codeList.ORDER_MEMO_GBN_CODE != null){
				codeList.ORDER_MEMO_GBN_CODE.forEach( function(value, index, array){
					orderMemoGbnCode[value.codeDtlNo] = value.codeDtlName;
				});
			}
			
			if(codeList != null && codeList.CLM_GBN_CODE != null){
				codeList.CLM_GBN_CODE.forEach( function(value, index, array){
					claimGbnCode[value.codeDtlNo] = value.codeDtlName;
				});
			}
			data.forEach( function(value, index, array){
				  var memo = value;
				  var memoId = memo.memoGbn+memo.orderMemoSeq;
				  
				  $("#memoList").append(
						  '<li name="'+memo.memoGbn+'">'
						  +'	<span class="msg-list-info">'
						  +'		<span class="user-info">'
						  +'			<span class="memo-type" id="memoType'+memoId+'"></span>'
						  +'			<span class="user-id"></span>'
						  +'			<span class="user-name">'+memo.adminId+'('+memo.adminName+')</a></span>'
						  +'		</span>'
						  +'		<span class="regist-date-wrap" id="delBtn'+memoId+'">'
						  +'			<span class="regist-date">'+new Date(memo.rgstDtm).format()+'</span>'						  
						  +'		</span>'
						  +'	</span>'
						  +'	<p class="msg-desc">'+memo.memoText
						  +'	</p>'
						  +'</li>');
				 
				 if(memo.memoGbn == abc.biz.order.const.CLAIM){
					  claimMemoCount ++;
					  $("#memoType"+memoId).html(claimGbnCode[value.orderMemoGbnCode]+" ");
				  } else {
					  var memoSeq = $("input[name=orderMemoSeq]");
					  if( memoSeq.val() == "0")
						  memoSeq.val(memo.orderMemoSeq + 1);
					  orderMemoCount ++;
					  $("#memoType"+memoId).html(orderMemoGbnCode[value.orderMemoGbnCode]+" ");
				  }
				  
				 if(adminNo == memo.rgsterNo) {
					 $('#delBtn'+memoId).append('<a href="javascript:void(0);" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>');
					 $('#delBtn'+memoId +'> a').on('click', function(){ 
						 _vendorMemo.delete(memo.memoGbn, memo.orderMemoSeq) 
					 });
				 }
				 
				 
			});
			
			$("#totalMemoCnt").html("("+totalMemoCount+")");
			$("#orderMemoCnt").html("("+orderMemoCount+")");
			$("#claimMemoCnt").html("("+claimMemoCount+")");
			
		})
		.fail(function(e){
			
		});
	}
	
	_vendorMemo.filter = function(_gbnType) {
		switch (_gbnType) {
			case abc.biz.order.const.ORDER:
				var firstLi = $($("#memoList li[name="+abc.biz.order.const.ORDER+"]")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				
				$("#memoList li[name="+abc.biz.order.const.CLAIM+"]").hide();
				$("#memoList li[name="+abc.biz.order.const.ORDER+"]").show();
				break;
			case abc.biz.order.const.CLAIM:
				var firstLi = $($("#memoList li[name="+abc.biz.order.const.CLAIM+"]")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				
				$("#memoList li[name="+abc.biz.order.const.CLAIM+"]").show();
				$("#memoList li[name="+abc.biz.order.const.ORDER+"]").hide();
				break;
			default :	
				$("#memoList li").css('margin-top',8);
				$("#memoList li").css('padding-top',9);
				$("#memoList li").css('border-top','1px solid #ddd');
				
				var firstLi = $($("#memoList li")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				
				$("#memoList li").show();
				break;
		}
	}
		
	_vendorMemo.delete = function(_gbnType, _seq){		
		if(confirm("삭제하세겠습니까?")) {
			var postUrl;
			if(_gbnType == abc.biz.order.const.CLAIM)
				postUrl="/claim/read-memo-vendor-info-tab/delete-memo"
			else
				postUrl="/order/delete-memo";
			
			$.ajax({
				type :"post",
				url : postUrl,
				data: { "orderMemoSeq" : _seq, "orderNo" : _vendorMemo.orderNo }
			}).done(function(result){
				_vendorMemo.reLoad();
			})
			.fail(function(e){
				
			});
		}
	}
		
	_vendorMemo.reLoad = function() {
		$.ajax({
			type :"post",
			url : "/delivery/vendor/order/read-memo-vendor-info-tab",
			data : { orgOrderNo : _vendorMemo.orderNo , orderNo : _vendorMemo.orderNo},
			dataType : "html"
		})
		.done(function(data){
			$("#tabMemoVendorArea").html(data);
		})
		.fail(function(e){
			alert("--------------fail ");
		});
	}
	
})();