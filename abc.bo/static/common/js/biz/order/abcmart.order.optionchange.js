/**
 * 주문 관리자 메모 목록
 */
(function(){
	
	var _memo = abc.object.createNestedObject(window,"abc.biz.order.memo");
	var _option = abc.object.createNestedObject(window,"abc.biz.order.optionchange");
	_memo.orderNo = "";
	
	_memo.init = function(orderNo) {
		_memo.orderNo = orderNo;
		$("#btnMemoRegist").click(function(){
			_memo.regist();
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
				console.log(result);
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
			data: { "orderNo" : _memo.orderNo, "listType" : "option" }
		})
		.done(function(result){
			var data = result.Data;
			var list = $("#memoList");
			var adminNo = result.adminNo;

			data.forEach( function(value, index, array){
				  var memo = value;
				  if(memo.orderMemoGbnCode == '10001'){
					  $("#memoList").append(
							  '<li>'
							  +'	<span class="msg-list-info">'
							  +'		<span class="user-info">'
							  +'			<<span class="user-id"></span>'
							  +'			<span class="user-name"><a href="javascript:void(0);" class="link" id="adminPop'+memo.orderMemoSeq+'">'+memo.adminId+'('+memo.adminName+')</a></span>'
							  +'		</span>'
							  +'		<span class="regist-date-wrap" id="delBtn'+memo.orderMemoSeq+'">'
							  +'			<span class="regist-date">'+new Date(memo.rgstDtm).format()+'</span>'						  
							  +'		</span>'
							  +'	</span>'
							  +'	<p class="msg-desc">'+memo.memoText
							  +'	</p>'
							  +'</li>');
					 if(adminNo == memo.rgsterNo) {
						 $('#delBtn'+memo.orderMemoSeq).append('<a href="javascript:void(0);" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>');
						 $('#delBtn'+memo.orderMemoSeq +'> a').on('click', function(){ 
							 _memo.delete(memo.orderMemoSeq) 
						 });
					 }
				  }
				  
				  var memoSeq = $("input[name=orderMemoSeq]");
				  if(memoSeq.val() == null ||  memoSeq.val() == "")
					  memoSeq.val(memo.orderMemoSeq + 1);
				  
				  $('#adminPop'+memo.orderMemoSeq).on('click', function(){
					  _memo.adminDetailPop(adminNo);
				  })
			});
			
		})
		.fail(function(e){
			
		});
	}
	
	_memo.delete = function(_seq){		
		if(confirm("삭제?")) {
			$.ajax({
				type :"post",
				url : "/order/delete-memo",
				data: { "orderMemoSeq" : _seq, "orderNo" : _memo.orderNo }
			}).done(function(result){
				_memo.reLoad();
			})
			.fail(function(e){
				
			});
		}
	}
		
	_memo.reLoad = function() {		
		$("#memoList").children().remove();
		$("input[name=orderMemoSeq]").val();
		_memo.list();
	}
	
	_memo.adminDetailPop = function(adminNo){
		var param = "adminNo="+adminNo;
		window.abc.adminDetailPopup(null,null,null,null,param);
	}
	
	$(document).on("change", "#option_size", function(event){
		
		var addOptn = $("#addOption").val();
		var selected = $("#option_size option:selected");
		
		var prdtOptnNo = selected.val();
		
		var selectVal = $("#option_size").find("option[value='" + prdtOptnNo + "']").text();
		
		console.log ("prdtOptnNo " , prdtOptnNo );
		console.log ("selectVal " , selectVal );
		console.log ("addOptn " , addOptn );
		
		var prdtNo = $("#prdtNo").val();
		var mmnyPrdtYn = $("#mmnyPrdtYn").val();
		var promoNo = $("#promoNo").val();
		var layerType = $("#layerType").val();
		var dlvyTypeCode = $("#dlvyTypeCode").val();
		
		if (addOptn > 0 ){
			// 추가 옵션 
			
			var params = {
					prdtNo		: prdtNo
				, mmnyPrdtYn 	: mmnyPrdtYn
				, selectVal 	: selectVal
				, promoNo 		: promoNo
				, layerType 	: layerType
				, dlvyTypeCode	: dlvyTypeCode
			};
			
			$.ajax({
				type :"post"
				,url : "/order/order-option-add"
				,data : params
				,async : false
			})
			.done(function(data){
				
				$("#addOption").val(data.addOptionSize); // 기본정보
				
				//addOption
				var select = $("#option_add");
				select.find("option").remove();
				
				console.log ("data.addOption" , data.addOption );
				
				data.addOption.forEach(function (item, index, array) {
					
					var optnName ; 
					var optnValue ; 
					var disableFlag = false ;
					
					optnValue = item.prdtOptnNo; 
				
					if (item.stockTotQty <= 0){
						optnName = "[품절]" + item.addOptn2Text; 
						disableFlag = true;
					}else{
						optnName = item.addOptn2Text; 
					} 
					
					console.log (" item.optnAddAmt >>>>>>"  ,  item.optnAddAmt );
					
					// 금액 비교? 확인 필요 
					var option =new Option(optnName, optnValue , false, false)
					$(option).prop('disabled',disableFlag);  // 옵션 비활성화
					$(option).attr('data-qty' , item.stockTotQty);
					$(option).attr('data-optnAddAmt', item.optnAddAmt ); // 선택 추가 금액
					
					select.append( option );
					
				});
			
				select.prepend(new Option("선택하세요.", "" , false, true));
			})
			.fail(function(e){
				console.log("e :" + e);
				return false;
			});
			
		}
		
	});
	
	$(document).on("click", "#optChangeSave", function(event){
		var param = abc.param.getParams();
		
		console.log ("param > " , param );
		console.log ("$(#addOption).val()  > " , $("#addOption").val()  );
		
		var orgPrdtOptnNo = $("#prdtOptnNo").val();
		var orgOptnAddAmt = $("#optnAddAmt").val();
		var addOptnFlag = $("#addOption").val() > 0 ? true : false ; 
		
		var selected ; 
		var prdtOptnNo ; 
		var stockQty = 0;  
		var orderQty =  parseInt($("#orderQty").val()); 
		var prdtNo =  $("#prdtNo").val(); 
		
		var optnTxt ; // 기본옵션명
		var optnAddTxt ; // 추가 옵션명
		
		console.log ("addOption val >. " ,$("#addOption").val());
		console.log ("addOptnFlag >. " ,addOptnFlag);
		if(addOptnFlag){
			selected = $("#option_add option:selected"); // 추가옵션
			console.log ("// option_add");
		}else{
			selected = $("#option_size option:selected"); // 기본
			console.log ("// option_size");
		}
		
		prdtOptnNo = selected.val();
		stockQty = parseInt(selected.data('qty'));
		optnAddAmt = parseInt(selected.data('optnaddamt'));
		
		if (prdtOptnNo == "" ) {
			var txt = addOptnFlag == 0 ? "사이즈" : "추가옵션"  ; 
			alert(txt+"선택하세요.");
			return; 
		}
		
		console.log("orgPrdtOptnNo / prdtOptnNo" , orgPrdtOptnNo , prdtOptnNo);
		if (orgPrdtOptnNo == prdtOptnNo ){
			alert("동일한 옵션입니다.");
			return; 
		}
		
		console.log("orgOptnAddAmt / optnAddAmt" , orgOptnAddAmt ,  optnAddAmt );
		if (orgOptnAddAmt != optnAddAmt) {
			alert("추가 옵션 금액이 상이 합니다.확인 바랍니다.");
			return; 
		}
		
		
		if ( stockQty < orderQty  ) {
			alert( "재고 수량이 부족합니다." );
			return;
		}
		
		var optnTxt ; // 기본옵션명
		var optnAddTxt ; // 추가 옵션명
		
		optnAddTxt = $("#option_add option:selected").text(); // 추가옵션
		optnTxt = $("#option_size option:selected").text();
		// callback  주문번호 , 상품번호 ,옵션명, 옵션번호
		
		if(addOptnFlag){
			optnName = optnTxt  + " / " + optnAddTxt;
		}else{
			optnName = optnTxt  ;
		}
		
		console.log( ">>>>>>>3 optnName  " , optnName);
		
		// 바닥창 콜백 함수 호출하면서 파라미터 전달
		var cbf = param.callbackFunction;
		var openerFunc = null;

		console.log ("typeof(opener.window[cbf]) " ,  typeof(opener.window[cbf]));
		
		if(typeof(opener.window[cbf])!="undefined"){
			openerFunc = opener.window[cbf];
		}
	
		console.log (" return prdtOptnNo  " ,  prdtOptnNo);
		var rtnData = {
				prdtOptnNo : prdtOptnNo
			   ,optnName : optnName
			   ,toParams : param
		}
		openerFunc(rtnData);
		window.close();
		
	});
	
	
	
	
	
})();