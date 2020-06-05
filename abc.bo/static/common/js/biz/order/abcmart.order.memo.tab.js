/**
 * 주문 관리자 메모 목록
 */
(function(){
	
	var _memo = abc.object.createNestedObject(window,"abc.biz.order.memo");
	_memo.orderNo = "";
	
	_memo.init = function(orderNo) {
		_memo.orderNo = orderNo;
		$("#btnMemoRegist").click(function(){
			_memo.regist();
		})
		
		$("#viewAll").click(function(){
			_memo.filter();
		})
		
		$("#viewOrder").click(function(){
			_memo.filter(abc.biz.order.const.ORDER);
		})
		
		$("#viewClaim").click(function(){
			_memo.filter(abc.biz.order.const.CLAIM);
		})
		$("#viewAs").click(function(){
			_memo.filter(abc.biz.order.const.AS);
		})
		this.list();
	}
	
	_memo.regist = function(){
		if($("textarea[name=memoText]").val() == null || $("textarea[name=memoText]").val() == ""){
			alert("관리자 메모를 입력하세요.");
			$("textarea[name=memoText]").focus();
		} else {
			$.ajax({
				type :"post",
				url : "/order/regist-memo",
				data: $("#memoForm").serialize()
			})
			.done(function(result){
				_memo.reLoad();
			})
			.fail(function(e){
				
			});
		}
	}
	
	_memo.list = function(){
		$.ajax({
			type :"post",
			url : "/order/read-memo-info-tab/list",
			data: { "orderNo" : _memo.orderNo }
		})
		.done(function(result){
			//console.log(result);
			var totalMemoCount = result.Total;
			var orderMemoCount = 0;
			var claimMemoCount = 0;
			var asMemoCount = 0;
			var data = result.Data;
			var codeList = result.codeList;
			var orderMemoGbnCode = {};
			var claimGbnCode = {};
			//var asGbnCode = {};
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
			
			//if(codeList != null && codeList.AS_GBN_CODE != null){
			//	codeList.AS_GBN_CODE.forEach( function(value, index, array){
			//		asGbnCode[value.codeDtlNo] = value.codeDtlName;
			//	});
			//}
			
			data.forEach( function(value, index, array){
				  var memo = value;
				  var memoId = memo.memoGbn+memo.orderMemoSeq;
				  
				  // 2020.02.06 : seq 추가 요청 반영
				  var memoSeq = memo.orderMemoSeq;
				  var orderNo = memo.orderNo;
				  $("#memoList").append(
						  '<li name="'+memo.memoGbn+'">'
						  +'	<span class="msg-list-info">'
						  +'		<span class="user-info">'
						  +'			<span class="memo-type" id="memoType'+memoId+'"></span>'
						  
						  // 2020.02.06 : seq 추가 요청 반영
						  +'			<span class="memo-seq">'+memoSeq+'&nbsp;/&nbsp;</span>'
						  
						  +'			<span class="user-id"></span>'
						  +'			<span class="user-name"><a href="javascript:void(0);" class="link" id="adminPop'+memoId+'">'+memo.adminId+'('+memo.adminName+')</a></span>'
						  +'		</span>'
						  +'		<span class="regist-date-wrap" id="delBtn'+memoId+'">'
					      +'            <input type="hidden" id="delOrd'+orderNo+'">'
						  +'			<span class="regist-date">'+new Date(memo.rgstDtm).format()+'</span>'						  
						  +'		</span>'
						  +'	</span>'
						  +'	<p class="msg-desc">'+memo.memoText
						  +'	</p>'
						  +'</li>');
				 
				  if(memo.memoGbn == abc.biz.order.const.AS){
					  asMemoCount ++;
					  $("#memoType"+memoId).html("AS"+" ");
				  }else if(memo.memoGbn == abc.biz.order.const.CLAIM){
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
						 _memo.memoDelete(memo.memoGbn, memo.orderMemoSeq ,memo.orderNo) 
					 });
				 }
				 
				 $('#adminPop'+memoId).on('click', function(){
					 abc.adminDetailPopup(memo.rgsterNo);
				 });
			});
			
			$("#totalMemoCnt").html("("+totalMemoCount+")");
			$("#orderMemoCnt").html("("+orderMemoCount+")");
			$("#claimMemoCnt").html("("+claimMemoCount+")");
			$("#asMemoCnt").html("("+asMemoCount+")");
			
		})
		.fail(function(e){
			
		});
	}
	
	_memo.filter = function(_gbnType) {
		switch (_gbnType) {
			case abc.biz.order.const.ORDER:
				var firstLi = $($("#memoList li[name="+abc.biz.order.const.ORDER+"]")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				
				$("#memoList li[name="+abc.biz.order.const.CLAIM+"]").hide();
				$("#memoList li[name="+abc.biz.order.const.AS+"]").hide();
				$("#memoList li[name="+abc.biz.order.const.ORDER+"]").show();
				break;
			case abc.biz.order.const.CLAIM:
				var firstLi = $($("#memoList li[name="+abc.biz.order.const.CLAIM+"]")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				
				$("#memoList li[name="+abc.biz.order.const.CLAIM+"]").show();
				$("#memoList li[name="+abc.biz.order.const.ORDER+"]").hide();
				$("#memoList li[name="+abc.biz.order.const.AS+"]").hide();
				break;
			case abc.biz.order.const.AS:
				var firstLi = $($("#memoList li[name="+abc.biz.order.const.AS+"]")[0]);
				firstLi.css('border-top',0);
				firstLi.css('margin-top',0);
				firstLi.css('padding-top',0);
				$("#memoList li[name="+abc.biz.order.const.AS+"]").show();
				$("#memoList li[name="+abc.biz.order.const.CLAIM+"]").hide();
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
		
	_memo.memoDelete = function(_gbnType, _seq, _orderNo){	
		if(confirm("삭제하시겠습니까?")) {
			var postUrl;
			 if(_gbnType == abc.biz.order.const.AS){
				 postUrl="/afterservice/afterservice/update-ocAsAccept-memo";
		    }else if(_gbnType == abc.biz.order.const.CLAIM){
				postUrl="/claim/claim/delete-claim-memo";
			}else{	
				postUrl="/order/delete-memo";
			}
			$.ajax({
				type :"post",
				url : postUrl,
				data: { "orderMemoSeq" : _seq, "orderNo" : _orderNo , "click": "orderDetail" }
				
			}).done(function(result){
				_memo.reLoad();
			})
			.fail(function(e){
				
			});
		}
	}
		
	_memo.reLoad = function() {
		$.ajax({
			type :"post",
			url : "/order/read-memo-info-tab",
			data : { orgOrderNo : _memo.orderNo , orderNo : _memo.orderNo},
			dataType : "html"
		})
		.done(function(data){
			$("#tabMemoArea").html(data);
		})
		.fail(function(e){
			alert("--------------fail ");
		});
	}
	
	_memo.adminDetailPop = function(adminNo){
		var param = "adminNo="+adminNo;
		window.abc.adminDetailPopup(null,null,null,null,param);
	}
	
})();