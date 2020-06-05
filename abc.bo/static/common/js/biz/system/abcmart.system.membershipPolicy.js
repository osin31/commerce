/***
 * member 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _membershipPolicy = abc.object.createNestedObject(window,"abc.biz.system.membershipPolicy");

	/***
	 * 멤버십 회원 혜택 정책 목록
	 */
	_membershipPolicy.getMembershipPolicyList = function(){
		location.href = "/cmm/membership-policy?pageCount="+$('#pageCount').val();
	}
	/***
	 * 멤버십 회원 혜택 정책 등록
	 */
	_membershipPolicy.setMembershipPolicyData = function(){
		$('#plcySeq').val('');

		onlinePolicyForm.action = "/cmm/membership-policy/membership-policy-pop";
		onlinePolicyForm.submit();
	}

	/***
	 * 멤버십 혜택 영역의 등록 html 페이지를 로딩한다.
	 */
	_membershipPolicy.getBenefitArea = function(param){
		var $form 			= $("#onlinePolicyForm");
		var idx 			= Number($('#benefitIndex').val()) + 1;
		var plcySeq			= param.plcySeq;
		var mbshpGradeCode	= param.mbshpGradeCode;
		var mbshpGradeName	= param.mbshpGradeName;	// 등록에서만 넘겨줌
		var addPointAmt		= param.addPointAmt;
		var freeGiftSeq		= param.freeGiftSeq;
		var mbshpPlcyDtlSeq	= param.addPointSeq;
		
//		console.log("plcySeq >>>" + plcySeq+"\nmbshpPlcyDtlSeq >>>" + mbshpPlcyDtlSeq
//				   +"\naddPointAmt >>>" + addPointAmt+"\nfreeGiftSeq >>>" + freeGiftSeq+"\nmbshpGradeCode >>>" + mbshpGradeCode
//				   +"\nmbshpGradeName >>>" + mbshpGradeName);
		
		$('#plcySeq').val(plcySeq);
		$('#addPointAmt').val(addPointAmt);
		$('#mbshpGradeCode').val(mbshpGradeCode);
		$('#mbshpGradeName').val(mbshpGradeName);		// 등록에서만 넘겨줌
		$('#mbshpPlcyDtlSeq').val(mbshpPlcyDtlSeq);		// 상세에서 등급의 이름을 가져오는 쿼리에서 사용하기 위해서 세팅
		$('#freeGiftSeq').val(freeGiftSeq);
	  	$('#benefitIndex').val(idx);
	  	
	  	var request = $.ajax({
			        type: "POST"
			      , url: "/cmm/membership-policy/membership-benefit-area"
			      , data: $form.serialize()
			      , dataType: "html"
		          , async: false
	  	});
	  	request.done(function(data) {
	  		$("#benefitArea").append(data);
	  		$('#addPointAmt_'+idx).val(addPointAmt);
	  		if(freeGiftSeq != ''){	// 사은품 번호가 존재할 때만 사은품 상품 관련 영역 호출 함수 실행
	  			_membershipPolicy.getBenefitAreaCpn(freeGiftSeq);
	  		}else{
	  			$('#cpn_nodata_'+idx).show();
	  		}
	  	});

	  	request.fail(function(jqXHR, textStatus) {
	  		alert("에러가 발생했습니다.");
            var idx 	= Number($('#benefitIndex').val()) - 1;
    	  	$('#benefitIndex').val(idx);
	  	});
	}

	/***
	 * 멤버십 혜택 영역의 상세 페이지의 쿠폰 영역 데이터 세팅
	 */
	_membershipPolicy.getBenefitAreaCpn = function(mbshpPlcyDtlSeq){
		$('#mbshpPlcyDtlSeq').val(mbshpPlcyDtlSeq);
		var $form 	= $("#onlinePolicyForm");
		var benefitIndex = $('#benefitIndex').val();
		var request = $.ajax({
			type: "POST"
				, url: "/cmm/membership-policy/membership-benefit-area-cpn"
				, data: $form.serialize()
				, async: false
		});
		request.done(function(data) {
			_membershipPolicy.setCpnJsonData(data, benefitIndex, "D");
		});

		request.fail(function(jqXHR, textStatus) {
			alert("에러가 발생했습니다.");
		});
	}

	/***
	 * 루프를 돌면서 ID 태그의 개수를 반환
	 * 혜택, 쿠폰은 적어도 1개 이상있어야한다. 1개 미만으로 삭제하려고 할 때 체크하기 위한 함수
	 */
	_membershipPolicy.getLoopCount = function(tag, obj){
		var result = new Object();
		result.cnt = 0;
		result.lastIdx = 0;

		$(tag+'[id^="'+obj+'"]').each(function(i,e) { //
			var id 		= $(e).attr('id');
			var idx 	= id.substring(id.lastIndexOf('_')+1);
			result.cnt += 1;
			result.lastIdx = idx;
		});
		return result;
	}

	/***
	 * 조건여부 라디오 버튼 클릭
	 */
	_membershipPolicy.setCondtnApply = function(obj){
		var val 	= $(obj).val();
		var id 		= $(obj).attr('id');
		var idx 	= id.substring(id.lastIndexOf('_')+1);

		if(val == 'Y'){
			$("#buyAmt_"+idx).prop("disabled", false);
			$("#buyQty_"+idx).prop("disabled", false);
			$("#buyAmt_"+idx).focus();
		}else{
			$("#buyAmt_"+idx).val('');
			$("#buyQty_"+idx).val('');
			$("#buyAmt_"+idx).prop("disabled", true);
			$("#buyQty_"+idx).prop("disabled", true);
		}
		// 삭제 후에 '추가', '삭제' 버튼의 컨트롤 관련 함수 호출 필요
	}

	/***
	 * 멤버십 혜택 초기화
	 */
	_membershipPolicy.init = function(){
		location.reload();
	}

	/***
	 * 멤버십 혜택 쿠폰 세팅
	 */
	_membershipPolicy.setCpnJsonData = function(data, benefitIndex, type){
		var cpnHtml = '';	// 생성 html
		var result  = _membershipPolicy.getLoopCount('tr', 'prdt_data_'+benefitIndex);	// 태그의 개수, last index 데이터 저장 변수
		var plcySeq = $('#plcySeq').val();	// key
		var flag    = false;	// 쿠폰 데이터/no data 기준 falg
		var cpnRowIndex = 1;	// 쿠폰의 행을 추가 할 때 기본 index

		if(type == 'D'){	// 상세일 때 쿠폰 데이터 세팅의 조건
			flag = (plcySeq == '' || plcySeq == 'null' || typeof plcySeq === 'undefined' || data == '') ? true : false;
		}else if(type == 'C'){	// '검색' 버튼을 눌러서 가져온 데이터의 세팅 조건json의 구조와 benefitIndex가 다를 수 있으므로 해당 데이터의 세팅 작업이 필요
			flag 		= false;
			data 		= data.data;
			cpnRowIndex = cpnRowIndex + parseInt(result.lastIdx);
		}
		
		if(data.length > 3 || result.cnt > 2){
			alert('사은품은 최대 3개까지 등록 가능합니다.');
			return;
		}

		if(flag){
			$('#cpn_nodata_'+benefitIndex).show();
		}else{
			for(i=0; i<data.length; i++){
				cpnHtml = '';
				cpnHtml += '<tr id="prdt_data_'+benefitIndex+'_'+(i+cpnRowIndex)+'">';
				cpnHtml += '	<td>'+data[i].prdtNo+',  benefitIndex >>>'+ (i+cpnRowIndex);
				cpnHtml += '    <input type="hidden" name="prdtNo_'+benefitIndex+'_'+(i+cpnRowIndex)+'" id="prdtNo_'+benefitIndex+'_'+(i+cpnRowIndex)+'" value="'+data[i].prdtNo+'" />     </td>';
				cpnHtml += '	<td>'+data[i].cpnName+'</td>';
				cpnHtml += '	<td class="input">';
				cpnHtml += '		<select class="ui-sel" title="상품 수량 선택" id="payQty_'+benefitIndex+'_'+(i+cpnRowIndex)+'" name="payQty_'+benefitIndex+'_'+(i+cpnRowIndex)+'">';
				for(j=1; j<=10; j++){
					cpnHtml += '			<option value="'+j+'">'+j+'개</option>';
				}
				cpnHtml += '		</select>';
				cpnHtml += '	</td>';
				cpnHtml += '	<td class="input clear-float text-center">';
				cpnHtml += '		<button type="button" class="btn-sm btn-del" id="cpn_delete_'+benefitIndex+'_'+(i+cpnRowIndex)+'" value="'+benefitIndex+'_'+(i+cpnRowIndex)+'">삭제</button>';
				cpnHtml += '	</td>';
				cpnHtml += '</tr>';

				$('#cpn_nodata_'+benefitIndex).hide();
				$('#cpn_tbody_'+benefitIndex).append(cpnHtml);
				if(type == 'D'){	// 상세[D]에서 '추가' 버튼으로 추가한 쿠폰 개수는 pay_qty[발급수량]을 세팅해주고 그렇지 않으면 기본 1개로 세팅
					$('#payQty_'+benefitIndex+'_'+(i+cpnRowIndex)).val(data[i].payQty);
				}
			}
		}
	}

	/***
	 * 멤버십 혜택 영역의 '삭제' 버튼 클릭
	 */
	_membershipPolicy.setBenefitCpnDelete = function(idx){
		var arrIdx = idx.split("_");
		if(arrIdx.length < 2){
			alert("에러가 발생하였습니다.");
		}else{
			$('#prdt_data_'+arrIdx[0]+'_'+arrIdx[1]).remove();	// '삭제' 버튼을 클릭한 쿠폰 행 삭제
			var result = _membershipPolicy.getLoopCount('tr', 'prdt_data_'+arrIdx[0]);

			if(result.cnt == 0){
				$('#cpn_nodata_'+arrIdx[0]).show();
			}
		}
	}

	/***
	 * 온라인 품절보상 영역의 '삭제' 버튼 클릭
	 */
	_membershipPolicy.setSoldoutCpnDelete = function(idx){
		$('#soldout_prdt_data_'+idx).remove();	// '삭제' 버튼을 클릭한 쿠폰 행 삭제
		var result = _membershipPolicy.getLoopCount('tr', 'soldout_prdt_data_');

		if(result.cnt == 0){
			$('#soldout_cpn_nodata').show();
		}
	}

	/***
	 * 품절보상 영역 라디오버튼 클릭 시 display
	 */
	_membershipPolicy.setSoldOutCmpnsType = function(val){
		if(val == 'P'){
			$('#soldout_point_area').show();
			$('#soldout_cpn_area').hide();
		}else{
			$('#soldout_point_area').hide();
			$('#soldout_cpn_area').show();
		}
	}

	/***
	 * noData 행 세팅
	 * 실제 개발을 입히게 되면 팝업을 통해서 쿠폰 리스트 데이터를 가져와서(json 방식)
	 * 해당 영역[혜택, 보상]에 쿠폰 행을 add 한다.(쿠폰 검색 팝업이 없기 때문에 - 수정필요)
	 */
	_membershipPolicy.getPrdtData  = function(benefitIndex){
		var jsonObj = new Object();
		var jobj 	= new Object();
		var jsonArr = new Array();

		// loop Start
		jobj.prdtNo		= "1000"+benefitIndex;
		jobj.cpnName	= "사은품"+benefitIndex;
		jsonArr.push(jobj);
		// loop End

		jsonObj.data = jsonArr;
		jsonObj.aaa = "기타 데이터";

		if(benefitIndex == ''){
		}else{
			_membershipPolicy.setCpnJsonData(jsonObj, benefitIndex, "C");
		}
	}

	/***
	 * 멤버십 혜택 영역 데이터를 등록한다.
	 */
	_membershipPolicy.setBenefitCpnCreate = function(){
		var $form 	= $("#onlinePolicyForm");

		// validation 체크 result = true, false
		var validationFlag = _membershipPolicy.isValidate();
		//var validationFlag = true;

		if(validationFlag){
			_membershipPolicy.setParameter();	// 멤버십 혜택 영역에 대한 파라미터 세팅
		  	var request = $.ajax({
						        type: "POST"
						      , url: "/cmm/membership-policy/create"
						      , data: $form.serialize()
					          , async: false
		  	});
		  	request.done(function(data, textStatus, jqXHR) {
		  		if(jqXHR.status == '200'){
		  			alert('저장되었습니다.');
		  			location.href = "/cmm/membership-policy/membership-policy-pop?plcySeq="+jqXHR.responseJSON.plcySeq;
		  		}
//		  		console.log("jqXHR.responseJSON.plcySeq >>>" + jqXHR.responseJSON.plcySeq);
		  	});

		  	request.fail(function(jqXHR, textStatus) {
		  		alert(jqXHR.responseJSON.message);
		  	});
		}
	}

	/***
	 * 멤버십 혜택 영역 validation 체크
	 */
	_membershipPolicy.isValidate = function(){
		var plcyStartYmd = $('#plcyStartYmd').val().replace(/[.]/gi,'');
		var plcyEndYmd 	 = $('#plcyEndYmd').val().replace(/[.]/gi,'');
		var benefitIndex = $('#benefitIndex').val();
		
		if(plcyStartYmd == '' || plcyEndYmd == ''){	// date if start
			alert("적용기간 날짜를 입력하세요");
			return false;
		}else{
			var d 		 = new Date();
			var fullYear = d.getFullYear();
			var month 	 =  (d.getMonth()+1) < 10 ? "0"+(d.getMonth()+1) : (d.getMonth()+1).toString();
			var day 	 =  (d.getDate()) < 10 ? "0"+(d.getDate()) : (d.getDate()).toString();
			var today 	 =  fullYear+ month +day;
			
			if(plcyStartYmd <= today){
				alert("시작날짜는 오늘보다 커야 합니다.");
				return false;
			}else if(plcyStartYmd >= plcyEndYmd){
				alert("종료날짜가 더 작습니다.");
				return false;
			}
		}	// date if end
		
		var memberJoinPointAmt 			= $('#memberJoinPointAmt').val().replace(/[.]/gi,'');			// 회원가입 포인트
		var firstBuyPointAmt 			= $('#firstBuyPointAmt').val().replace(/[.]/gi,'');				// 첫구매 포인트
		var buyPointRate 				= $('#buyPointRate').val().replace(/[.]/gi,'');					// 구매 포인트율
		var annvrFirstBuyPointMultCount = $('#annvrFirstBuyPointMultCount').val().replace(/[.]/gi,'');	// 기념일 첫구매 포인트 배수
		var minUsePointAmt 				= $('#minUsePointAmt').val().replace(/[.]/gi,'');				// 최소 사용포인트
		var pointExtnYearCount 			= $('#pointExtnYearCount').val().replace(/[.]/gi,'');			// 포인트 자동 소멸 년 수
		var prdtRvwPointAmt 			= $('#prdtRvwPointAmt').val().replace(/[.]/gi,'');				// 일반후기 포인트
		var photoPrdtRvwPointAmt 		= $('#photoPrdtRvwPointAmt').val().replace(/[.]/gi,'');			// 포토후기 포인트
		var monthBestRvwCount 			= $('#monthBestRvwCount').val().replace(/[.]/gi,'');			// 월별 베스트 후기 수
		var monthBestRvwPointAmt 		= $('#monthBestRvwPointAmt').val().replace(/[.]/gi,'');			// 월별 베스트 후기 포인트
		var onlnSaveTermCount 			= $('#onlnSaveTermCount').val().replace(/[.]/gi,'');			// 온라인 배송완료 일수
		var oflnSaveTermCount 			= $('#oflnSaveTermCount').val().replace(/[.]/gi,'');			// 오프라인 배송완료 일수
		var birthDayCpnNo 				= $('#birthDayCpnNo').val().replace(/[.]/gi,'');				// 생일쿠폰
		var annvrFirstBuyPointPayYn 	= $('[name="annvrFirstBuyPointPayYn"]:checked').val();			// 기념일 첫구매 사용여부
		
		if(memberJoinPointAmt == ''){
			alert("회원가입 포인트를 입력하세요");
			$('#memberJoinPointAmt').focus();
			return false;
		}else if(firstBuyPointAmt == ''){
			alert("첫구매 포인트를 입력하세요");
			$('#firstBuyPointAmt').focus();
			return false;
		}else if(buyPointRate == ''){
			alert("구매 포인트율을 입력하세요");
			$('#buyPointRate').focus();
			return false;
		}else if(annvrFirstBuyPointPayYn == 'Y' && annvrFirstBuyPointMultCount == ''){
			alert("기념일 첫구매 포인트 배수를 입력하세요");
			$('#annvrFirstBuyPointMultCount').focus();
			return false;
		}else if(minUsePointAmt == ''){
			alert("최소 사용포인트를 입력하세요");
			$('#minUsePointAmt').focus();
			return false;
		}else if(pointExtnYearCount == ''){
			alert("포인트 자동 소멸 년 수를 입력하세요");
			$('#pointExtnYearCount').focus();
			return false;
		}else if(prdtRvwPointAmt == ''){
			alert("일반후기 포인트를 입력하세요");
			$('#prdtRvwPointAmt').focus();
			return false;
		}else if(photoPrdtRvwPointAmt == ''){
			alert("포토후기 포인트를 입력하세요");
			$('#photoPrdtRvwPointAmt').focus();
			return false;
		}else if(monthBestRvwCount == ''){
			alert("월별 베스트 후기 수를 입력하세요");
			$('#monthBestRvwCount').focus();
			return false;
		}else if(monthBestRvwPointAmt == ''){
			alert("월별 베스트 후기 포인트를 입력하세요");
			$('#monthBestRvwPointAmt').focus();
			return false;
		}else if(onlnSaveTermCount == ''){
			alert("온라인 배송완료 일수를 입력하세요");
			$('#onlnSaveTermCount').focus();
			return false;
		}else if(oflnSaveTermCount == ''){
			alert("오프라인 배송완료 일수를 입력하세요");
			$('#oflnSaveTermCount').focus();
			return false;
		}else if(birthDayCpnNo == ''){
			alert("생일쿠폰를 입력하세요");
			$('#birthDayCpnNo').focus();
			return false;
		}
		if(annvrFirstBuyPointPayYn == 'N'){
			$('#annvrFirstBuyPointMultCount').val('0');
		}
		return true;
	}
	
	/***
	 * 멤버십 혜택 영역 validation 체크
	 */
	_membershipPolicy.setParameter = function(){
		// 각 영역[혜택, 보상]의 쿠폰 개수를 세팅
		var benefitIndex = $('#benefitIndex').val();
		var val = "";

		for(i=1; i<=benefitIndex; i++){	// '추가' 버튼을 누를 때 마다 benefitIndex가 +1된 수만큼 loop를 돌면서 파라미터 세팅
			var prdtNoArr = "";
			var payQtyArr = "";
			var soldoutCpnNoArr = "";

			val = $('#benefit_cpn_search_'+i).val();	// 영역의 검색버튼의 값을 체크(삭제했다면 없음)

			if(typeof val != "undefined"){	// 혜택 영역이 있다면 쿠폰의 갯수를 체크[영역의 삭제 여부를 체크]
				$('[id^="prdtNo_'+i+'"]').each(function(loopIdx,e) {
					var id 		= $(e).attr('id');
					var upderBarIndex 	= id.substring(id.indexOf('_')+1);
					// 각 영역의 prdtNo을 |로 연결된 파라미터로 생성(i:benefitIdx,cpnIdx: 각 혜택 영역의 쿠폰 인덱스)
					var prdtNo = $('#'+id).val();
					var payQty = $('#payQty_'+upderBarIndex).val();
					prdtNoArr += (prdtNo+"|");
					payQtyArr += (payQty+"|");
				});
				$("form:eq(0)").append('<input type="hidden" name="prdtNoArr_'+i+'" value="'+prdtNoArr+'" />');
				$("form:eq(0)").append('<input type="hidden" name="payQtyArr_'+i+'" value="'+payQtyArr+'" />');
			}
		}

		$('[id^="soldoutCpnNo_"]').each(function(loopIdx,e) {
			var id 		= $(e).attr('id');
			var soleoutCpnNo = $('#'+id).val();
			soldoutCpnNoArr += (soleoutCpnNo+"|");
		});
		$("form:eq(0)").append('<input type="hidden" name="soldoutCpnNoArr" value="'+soldoutCpnNoArr+'" />');

		//validateon result true
		return true;
	}

	/***
	 * 멤버십 혜택 영역 validation 체크
	 */
	_membershipPolicy.setDeleteArea = function(idx){
		var result = _membershipPolicy.getLoopCount('table', 'DeleteBenefitArea');

		if(result.cnt > 1){
			$('#DeleteBtnArea_'+idx).remove();
			$('#DeleteBenefitArea_'+idx).remove();
			result = _membershipPolicy.getLoopCount('table', 'DeleteBenefitArea');

			if(result.cnt >= 1){
	  			$('[id^="DeleteBenefitArea"]').each(function(i,e) { //
	  				var id 		= $(e).attr('id');
	  				var idx 	= id.substring(id.lastIndexOf('_')+1);

	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnAdd_'+idx).show();
	  					$('#btnDel_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnAdd_'+idx).show();
		  					$('#btnDel_'+idx).show();
	  					}else{
	  						$('#btnAdd_'+idx).hide();
		  					$('#btnDel_'+idx).show();
	  					}
	  				}
	  			});
	  		}
		}else{
			alert("삭제할 수 없습니다.");
		}
	}

	/***
	 * 멤버십 혜택 쿠폰 검색
	 */
	_membershipPolicy.getCpnSearch = function(idx){
		var cpnNo = $('#birthDayCpnNo').val();
		if(cpnNo != ''){
			alert('이미 생일 쿠폰이 있습니다.');
			return;
		}else{
			var cpnName  = '쿠폰명';
			cpnNo 		 = '12345';
			var htmlData = ''; 
			
			htmlData += '<span class="subject">['+cpnNo+'] '+cpnName+'</span>';
			htmlData += '<button type="button" class="btn-item-del" id="birthDayCpnNoDel"><span class="ico ico-item-del"><span class="offscreen">쿠폰 삭제</span></span></button>';
			$('#birthDayCpnNoArea').html(htmlData);
			$('#birthDayCpnNo').val(cpnNo);
		}
	}

})();