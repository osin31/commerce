/***
 * member 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _onlinepolicy = abc.object.createNestedObject(window,"abc.biz.system.onlinepolicy");
	var onlineCouponArr 	= new Array();
	var membershipCouponArr = new Array();
	var soldoutCouponArr 	= new Array();
	var couponObject 		= null;
	var _benefitIndex 		= '';
	var isChange 			= false;

	//시행일자 validate
	$("#plcyApplyYmd").datepicker({
		constrainInput: false ,
		showOn: "both" ,
		minDate : "1"
	});

	//시행일자 숫자만 입력
	$("#plcyApplyYmd").off().on('input', function(event){
		abc.text.validateOnlyNumber(this);
	});

	//데이터 피커 셋팅
	_onlinepolicy.datePickerSet = function(dateP){
		dateP = dateP.slice(0,4)+"."+dateP.slice(4);
		dateP = dateP.slice(0,7)+"."+dateP.slice(7);

		return dateP;
	}

	$("input, select").change(function(){
		isChange  = true;
	});

	/***
	 * 온라인 회원 정책 목록
	 */
	_onlinepolicy.getOnlinePolicyList = function(){
		if(isChange){
			if(confirm("등록(수정) 된 내용은 저장 되지 않습니다. 이동하시겠습니까?")){
				location.href = "/cmm/policy?pageCount="+$('#pageCount').val();
			}
		}else{
			location.href = "/cmm/policy?pageCount="+$('#pageCount').val();
		}
	}
	/***
	 * 온라인 회원 정책 등록
	 */
	_onlinepolicy.setOnlinePolicyData = function(){
		$('#plcySeq').val('');

		onlinePolicyForm.action = "/cmm/policy/online-policy-pop";
		onlinePolicyForm.submit();
	}

	/***
	 * 온라인 혜택 영역의 등록 html 페이지를 로딩한다.
	 */
	_onlinepolicy.getBenefitArea = function(plcyDtlSeq){
		var $form 	= $("#onlinePolicyForm");
		var idx 	= Number($('#benefitIndex').val()) + 1;
	  	var result = _onlinepolicy.getLoopCount('table', 'DeleteBenefitArea');

	  	if(idx > 0){
	  		$('#addBenefitArea').hide();
	  	}
	  	
	  	if(result.cnt > 2){
	  		alert("통합멤버십 회원 온라인 혜택은 최대 3개까지 설정 가능합니다.");
	  		return;
	  	}else{
	  		$('#benefitIndex').val(idx);
	  		$('#plcyDtlSeq').val(plcyDtlSeq);
		  	var request = $.ajax({
						        type: "POST"
						      , url: "/cmm/policy/online-benefit-area"
						      , data: $form.serialize()
						      , dataType: "html"
					          , async: false
		  	});
		  	request.done(function(data) {
		  		$("#benefitArea").append(data);
		  		result = _onlinepolicy.getLoopCount('table', 'DeleteBenefitArea');

		  		if(result.cnt >= 1){
		  			$('[id^="DeleteBenefitArea"]').each(function(i,e) {
		  				var id 		= $(e).attr('id');
		  				var idx 	= id.substring(id.lastIndexOf('_')+1);

		  				if(result.cnt==1){
		  					$('#btnAdd_'+idx).show();
		  					$('#btnDel_'+idx).show();
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
		  		$('#commentArea').show();
		  		_onlinepolicy.getBenefitAreaCpn(plcyDtlSeq);
		  	});

		  	request.fail(function(jqXHR, textStatus, errorThrown) {
		  		console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
	            var idx 	= Number($('#benefitIndex').val()) - 1;
	    	  	$('#benefitIndex').val(idx);
		  	});
	  	}
	}

	/***
	 * 온라인 혜택 영역의 상세 페이지의 쿠폰 영역 데이터 세팅
	 */
	_onlinepolicy.getBenefitAreaCpn = function(plcyDtlSea){
		var $form 	= $("#onlinePolicyForm");
		var benefitIndex = $('#benefitIndex').val();
		var request = $.ajax({
			type: "POST"
				, url: "/cmm/policy/online-benefit-area-cpn"
				, data: $form.serialize()
				, async: false
		});
		request.done(function(data) {
			_onlinepolicy.setCpnJsonData(data, benefitIndex, "D");
		});

		request.fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	/***
	 * 루프를 돌면서 ID 태그의 개수를 반환
	 * 혜택, 쿠폰은 적어도 1개 이상있어야한다. 1개 미만으로 삭제하려고 할 때 체크하기 위한 함수
	 */
	_onlinepolicy.getLoopCount = function(tag, obj){
		var result 		= new Object();
		result.cnt 		= 0;
		result.lastIdx 	= 0;

		$(tag+'[id^="'+obj+'"]').each(function(i,e) { //
			var id 			= $(e).attr('id');
			var idx 		= id.substring(id.lastIndexOf('_')+1);
			result.cnt 		+= 1;
			result.lastIdx 	= idx;
		});
		return result;
	}

	/***
	 * 조건여부 라디오 버튼 클릭
	 */
	_onlinepolicy.setCondtnApply = function(obj){
		var val	= $(obj).val();
		var id 	= $(obj).attr('id');
		var idx = id.substring(id.lastIndexOf('_')+1);

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
	 * 온라인 혜택 초기화
	 * 해당 페이지는 동적으로 영역 및 혜택의 정보를 html로 add, delete 하기 때문에 reset()보다 reload를 사용함
	 * reset할 경우 추가한 영역이 그대로 남아있고 추가시 생성한 파라미터 정보가 남아있으므로 초기화는 reload로 함
	 */
	_onlinepolicy.init = function(){
		location.reload();
	}

	/***
	 * 온라인 혜택 쿠폰 데이터 세팅
	 */
	_onlinepolicy.setCpnJsonData = function(data, benefitIndex, type){
		var cpnHtml = '';	// 생성 html
		var result  = _onlinepolicy.getLoopCount('tr', 'cpn_data_'+benefitIndex);	// 태그의 개수, last index 데이터 저장 변수
		var plcySeq = $('#plcySeq').val();	// key
		var flag    = false;	// 쿠폰 데이터/no data 기준 falg
		var cpnRowIndex = 1;	// 쿠폰의 행을 추가 할 때 기본 index

		if(type == 'D'){	// 상세일 때 쿠폰 데이터 세팅의 조건
			flag = (plcySeq == '' || plcySeq == 'null' || typeof plcySeq === 'undefined' || data == '') ? true : false;
		}else if(type == 'C'){	// '검색' 버튼을 눌러서 가져온 데이터의 세팅 조건json의 구조와 benefitIndex가 다를 수 있으므로 해당 데이터의 세팅 작업이 필요
			flag 		= false;
			cpnRowIndex = cpnRowIndex + parseInt(result.lastIdx);
		}

		if(data.length > 5 || (result.cnt+data.length) > 5){
			alert('혜택은 최대 5개까지 등록 가능합니다.');
			return;
		}

		if(flag){
			$('#cpn_nodata_'+benefitIndex).show();
		}else{
			// 쿠폰 검색 팝업을 통해서 선택한 N개의 쿠폰중 유효기간이 지나지 않은 쿠폰만 추가되고, 그렇지 않은 쿠폰은 alert 메세지로 알려주고 추가하지 않는다.
			var couponMsg 	= "";	// 쿠폰 추가 메세지
			var couponFlag 	= true;	// 쿠폰 추가 여부 플래그[사용할 수 있는 쿠폰인지 여부를 체크]
			
			for(i=0; i<data.length; i++){
				couponFlag = true;
				var cpnNo 		= data[i].cpnNo;
				var cpnName 	= data[i].cpnName;
				var validDate 	= data[i].validDate;
				var date = new Date();
				var compareDate = 0;
				
				if(data[i].validEndDtm != ''){	// 발급 후, 사용하는 쿠폰은 유효기간 체크를 하면 무조건 -가 되므로 제외
					compareDate = data[i].validEndDtm - date.getTime();
					compareDate = Math.floor(compareDate / (1000 * 60 * 60 * 24));
				}
				
				if(compareDate < 0){
					couponFlag = false;
					
					if(couponMsg == ''){
						couponMsg += cpnName; 
					}else{
						couponMsg += "\n" + cpnName;
					}
				}
				
				if(couponFlag){
					cpnHtml = '';
					cpnHtml += '<tr id="cpn_data_'+benefitIndex+'_'+(i+cpnRowIndex)+'">';
					cpnHtml += '	<td>'+data[i].cpnNo;
					cpnHtml += '    <input type="hidden" name="cpnNo_'+benefitIndex+'_'+(i+cpnRowIndex)+'" id="cpnNo_'+benefitIndex+'_'+(i+cpnRowIndex)+'" value="'+data[i].cpnNo+'" />     </td>';
					cpnHtml += '	<td>'+data[i].cpnName+'</td>';
					cpnHtml += '	<td class="text-center">' + validDate + '</td>';
					cpnHtml += '	<td class="input">';
					cpnHtml += '		<select class="ui-sel" title="쿠폰 수량 선택" id="payQty_'+benefitIndex+'_'+(i+cpnRowIndex)+'" name="payQty_'+benefitIndex+'_'+(i+cpnRowIndex)+'">';
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
			
			if(couponMsg != ''){
				alert(couponMsg + " 쿠폰은 유효기간이 지났습니다.");
			}
		}
	}

	/***
	 * 온라인 혜택 영역의 '삭제' 버튼 클릭
	 */
	_onlinepolicy.setBenefitCpnDelete = function(idx){
		var arrIdx = idx.split("_");
		if(arrIdx.length < 2){
			alert("에러가 발생하였습니다.");
		}else{
			$('#cpn_data_'+arrIdx[0]+'_'+arrIdx[1]).remove();	// '삭제' 버튼을 클릭한 쿠폰 행 삭제
			var result = _onlinepolicy.getLoopCount('tr', 'cpn_data_'+arrIdx[0]);

			if(result.cnt == 0){
				$('#cpn_nodata_'+arrIdx[0]).show();
			}
		}
	}

	/***
	 * 온라인 품절보상 영역의 '삭제' 버튼 클릭
	 */
	_onlinepolicy.setSoldoutCpnDelete = function(idx){
		$('#soldout_cpn_data_'+idx).remove();	// '삭제' 버튼을 클릭한 쿠폰 행 삭제
		var result = _onlinepolicy.getLoopCount('tr', 'soldout_cpn_data_');

		if(result.cnt == 0){
			$('#soldout_cpn_nodata').show();
		}
	}

	/***
	 * noData 행 세팅
	 * 실제 개발을 입히게 되면 팝업을 통해서 쿠폰 리스트 데이터를 가져와서(json 방식)
	 * 해당 영역[혜택, 보상]에 쿠폰 행을 add 한다.(쿠폰 검색 팝업이 없기 때문에 - 수정필요)
	 */
	_onlinepolicy.getCouponData = function(benefitIndex){
		var jsonObj = new Object();
		var jobj 	= new Object();
		var jsonArr = new Array();
		_benefitIndex = benefitIndex;

		abc.couponSearchPopup(true, "abc.biz.system.onlinepolicy.appendBenefitCoupon");
	}

	/***
	 * 온라인 혜택 쿠폰 영역 세팅
	 */
	_onlinepolicy.appendBenefitCoupon = function(d){
		var couponArr = [];
		var dupFlag   = false;

		// 기등록 되어 있는 쿠폰ID를 가져와서 array에 담음
		$('[id^="cpn_data_'+ _benefitIndex +'"]').each(function(i,e) {
			couponArr.push($(e).children().first().text().replace(/ /gi,''));
		});
		
		// 쿠폰 팝업을 통해 선택한 쿠폰만큼 돌면서 기등록된 쿠폰과 중복여부를 체크한다.
		for(i=0; i<d.length; i++){
			var cpnNo 		= d[i].cpnNo;
			
			if($.inArray(cpnNo, couponArr) != -1){
				alert(cpnNo + " 쿠폰이 중복됩니다.");
				dupFlag = true;
				break; // for 문 종료
			}
		}

		// 중복플래그[dupFlag]가 true면 쿠폰 세팅을 멈춘다.
		if(dupFlag){
			return;
		}

		// 중복이 없을 때에만 쿠폰 세팅을 한다.
		_onlinepolicy.setCpnJsonData(d, _benefitIndex, "C");
	}

	/***
	 * 온라인 혜택 영역 데이터를 등록한다.
	 */
	_onlinepolicy.setBenefitCpnCreate = function(){
		var $form 	= $("#onlinePolicyForm");

		// validation 체크
		var validationFlag = _onlinepolicy.isValidate();

		if(validationFlag){
			_onlinepolicy.setParameter();	// 온라인 혜택 영역에 대한 파라미터 세팅

			var request = $.ajax({
					        type: "post"
					      , url: "/cmm/policy/create"
					      , data: $form.serialize()
				          , async: false
		  	});
		  	request.done(function(data, textStatus, jqXHR) {
		  		if(jqXHR.status == '200'){
		  			alert('저장되었습니다.');
		  			location.href = "/cmm/policy/online-policy-pop?plcySeq="+jqXHR.responseJSON.plcySeq;
		  		}
		  	});

		  	request.fail(function(jqXHR, textStatus, errorThrown) {
		  		console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		  	});
		}
	}

	/***
	 * 온라인 혜택 영역 validation 체크
	 */
	_onlinepolicy.isValidate = function(){
		var plcyApplyYmd 		= $('#plcyApplyYmd').val().replace(/[.]/gi,'');
		var benefitIndex 		= $('#benefitIndex').val();
		var couponIssueCount 	= $('#couponIssueCount').val();
		var plcySeq 			= $('#plcySeq').val();

		if(plcyApplyYmd == ''){	// s : 시행날짜
			alert("시행날짜를 입력하세요");
			return false;
		}else{
			var d 		 = new Date();
			var fullYear = d.getFullYear();
			var month 	 = (d.getMonth()+1) < 10 ? "0"+(d.getMonth()+1) : (d.getMonth()+1).toString();
			var day 	 = (d.getDate()) < 10 ? "0"+(d.getDate()) : (d.getDate()).toString();
			var today 	 = fullYear+ month +day;

			if(plcyApplyYmd <= today){
				alert("시행일자는 명일날짜부터 가능합니다.");
				return false;
			}
		}	// e : 시행날짜

		var onlnMemberJoinCpnNo 		= $('#onlnMemberJoinCpnNo').val().replace(/[.]/gi,'');			// 온라인 회원가입쿠폰
		var memberJoinPointAmt 			= $('#memberJoinPointAmt').val().replace(/[.]/gi,'');			// 회원가입 포인트
		var firstBuyPointAmt 			= $('#firstBuyPointAmt').val().replace(/[.]/gi,'');				// 첫구매 포인트
		var buyPointRate 				= $('#buyPointRate').val().replace(/[.]/gi,'');					// 구매 포인트율
		var annvrFirstBuyPointMultCount = $('#annvrFirstBuyPointMultCount').val().replace(/[.]/gi,'');	// 기념일 첫구매 포인트 배수
		var annvrFirstBuyPointPayYn 	= $('[name="annvrFirstBuyPointPayYn"]:checked').val();			// 기념일 첫구매 사용여부
		var minUsePointAmt 				= $('#minUsePointAmt').val().replace(/[.]/gi,'');				// 최소 사용포인트
		var pointExtnYearCount 			= $('#pointExtnYearCount').val().replace(/[.]/gi,'');			// 포인트 자동 소멸 년 수
		var prdtRvwPointAmt 			= $('#prdtRvwPointAmt').val().replace(/[.]/gi,'');				// 일반후기 포인트
		var photoPrdtRvwPointAmt 		= $('#photoPrdtRvwPointAmt').val().replace(/[.]/gi,'');			// 포토후기 포인트
		var monthBestRvwCount 			= $('#monthBestRvwCount').val().replace(/[.]/gi,'');			// 월별 베스트 후기 수
		var monthBestRvwPointAmt 		= $('#monthBestRvwPointAmt').val().replace(/[.]/gi,'');			// 월별 베스트 후기 포인트
		var onlnSaveTermCount 			= $('#onlnSaveTermCount').val().replace(/[.]/gi,'');			// 온라인 배송완료 일수
		var oflnSaveTermCount 			= $('#oflnSaveTermCount').val().replace(/[.]/gi,'');			// 오프라인 배송완료 일수
		var mbshpMemberJoinCpnNo 		= $('#mbshpMemberJoinCpnNo').val().replace(/[.]/gi,'');			// 통합멤버십 회원가입 쿠폰
		var birthDayCpnNo 				= $('#birthDayCpnNo').val().replace(/[.]/gi,'');				// 생일쿠폰

		if(onlnMemberJoinCpnNo == ''){
			alert("온라인 회원가입쿠폰을 입력하세요");
			$('#onlnMemberJoinCpnNoBtn').focus();
			return false;
		}else if(memberJoinPointAmt == ''){
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
		}else if(mbshpMemberJoinCpnNo == ''){
			alert("통합 멤버십 회원가입 쿠폰을 입력하세요");
			$('#mbshpMemberJoinCpnNoBtn').focus();
			return false;
		}else if(birthDayCpnNo == ''){
			alert("생일쿠폰를 입력하세요");
			$('#birthDayCpnNoBtn').focus();
			return false;
		}
		
		if(annvrFirstBuyPointPayYn == 'N'){
			$('#annvrFirstBuyPointMultCount').val('0');
		}

		// 온라인 혜택 영역 validation start
		if(plcySeq == '' || benefitIndex > 0){	// 등록이거나 혜택 영역의 데이터가 있을 경우만 validation 체크
			for(i=1; i<=benefitIndex; i++){
				var benefitArea 	= $('#DeleteBenefitArea_'+i).val();

				if(typeof benefitArea != "undefined"){	// 쿠폰 혜택 영역이 존재 할 때만
					var result = _onlinepolicy.getLoopCount('tr', 'cpn_data_'+i);
					var buyAmt = $('#buyAmt_'+i).val().replace(/ /gi,'');
					var buyQty = $('#buyQty_'+i).val().replace(/ /gi,'');

					if((buyAmt == '' || buyAmt < 1) && result.cnt > 0){	// 온라인 혜택 '조건' 항목 구매금액 체크
						alert("구매 금액를 입력하세요");
						$('#buyAmt_'+i).focus();
						return false;
					}
					if((buyQty == '' || buyQty < 1) && result.cnt > 0){	// 온라인 혜택 '조건' 항목 구매건수 체크
						alert("구매 건수를 입력하세요");
						$('#buyQty_'+i).focus();
						return false;
					}

					if((result.cnt == 0 && buyAmt > 0)
					|| (result.cnt == 0 && buyQty > 0)){	// 쿠폰이 없는데, 구매금액 또는 구매건수만 입력한 경우
						alert("통합멤버십 회원 온라인 혜택 쿠폰은 최소1개 이상 최대 "+ couponIssueCount +"개까지 설정해 주세요");
						return false;
					}else if(result.cnt > couponIssueCount){	// 온라인 혜택 '혜택' 항목의 쿠폰 최대개수 체크
						alert("통합멤버십 회원 온라인 혜택 쿠폰은 최소1개 이상 최대 "+ couponIssueCount +"개까지 설정해 주세요");
						return false;
					}
				}
			}
		}
		// 온라인 혜택 영역 validation end

		return true;
	}

	/***
	 * 혜택 영역 혜택 영역의 n개 쿠폰 파라미터 세팅
	 */
	_onlinepolicy.setParameter = function(){
		// 각 영역[혜택, 보상]의 쿠폰 개수를 세팅
		var benefitIndex 		= $('#benefitIndex').val();
		var val 				= "";

		for(i=1; i<=benefitIndex; i++){	// '추가' 버튼을 누를 때 마다 benefitIndex가 +1된 수만큼 loop를 돌면서 파라미터 세팅
			var cpnNoArr = "";
			var payQtyArr = "";

			val = $('#benefit_cpn_search_'+i).val();	// 영역의 검색버튼의 값을 체크(삭제했다면 없음)

			if(typeof val != "undefined"){	// 혜택 영역이 있다면 쿠폰의 갯수를 체크[영역의 삭제 여부를 체크]
				$('[id^="cpnNo_'+i+'"]').each(function(loopIdx,e) {
					var id 		= $(e).attr('id');
					var upderBarIndex 	= id.substring(id.indexOf('_')+1);
					// 각 영역의 cpnNo을 |로 연결된 파라미터로 생성(i:benefitIdx,cpnIdx: 각 혜택 영역의 쿠폰 인덱스)
					var cpnNo = $('#'+id).val();
					var payQty = $('#payQty_'+upderBarIndex).val();
					cpnNoArr += (cpnNo+"|");
					payQtyArr += (payQty+"|");
				});
				$("form:eq(0)").append('<input type="hidden" name="cpnNoArr_'+i+'" value="'+cpnNoArr+'" />');
				$("form:eq(0)").append('<input type="hidden" name="payQtyArr_'+i+'" value="'+payQtyArr+'" />');
			}
		}
	}

	/***
	 * 온라인 혜택 영역 validation 체크
	 */
	_onlinepolicy.setDeleteArea = function(idx){

		if(confirm("해당 영역 삭제시 내용은 복구되지 않습니다. 삭제하시겠습니까?")){
			var result = _onlinepolicy.getLoopCount('table', 'DeleteBenefitArea');

//			if(result.cnt > 1){	// 20190613:1개만 남았을 때도 삭제할수있게해줌
				$('#DeleteBtnArea_'+idx).remove();
				$('#DeleteBenefitArea_'+idx).remove();
				result = _onlinepolicy.getLoopCount('table', 'DeleteBenefitArea');

				if(result.cnt >= 1){
		  			$('[id^="DeleteBenefitArea"]').each(function(i,e) { //
		  				var id 		= $(e).attr('id');
		  				var idx 	= id.substring(id.lastIndexOf('_')+1);

		  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
		  					$('#btnAdd_'+idx).show();
		  					$('#btnDel_'+idx).show();
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
		  		}else{
		  			$('#addBenefitArea').show();
		  			$('#commentArea').hide();
		  			
		  		}
//			}else{
//				alert("삭제할 수 없습니다.");
//			}
		}
	}

	/***
	 * 쿠폰 세팅[공통]
	 */
	_onlinepolicy.setOnlineCoupon = function(couponBtnObj, couponList){
		var couponObj 		= $(couponBtnObj).val();
		var couponNameValue = $('#'+couponObj).val();
		var couponHtml 		= "";

		for(i=0; i<couponList.length; i++){
			var cpnNo 		= couponList[i].cpnNo;
			var cpnName 	= couponList[i].cpnName;
			var validDate 	= couponList[i].validDate;
			var date = new Date();
			var compareDate = 0;
			
			if(couponList[i].validEndDtm != ''){	// 발급 후, 사용하는 쿠폰은 유효기간 체크를 하면 무조건 -가 되므로 제외
				compareDate = couponList[i].validEndDtm - date.getTime();
				compareDate = Math.floor(compareDate / (1000 * 60 * 60 * 24));
			}
			
			if(compareDate < 0){
				alert('해당 쿠폰은 유효기간이 지났습니다.');
				return;
			}else{
				if(couponNameValue != ''){	// 쿠폰이 있을 경우 해당 쿠폰을 삭제
					$(couponBtnObj).parent().next().remove();
				}
				
				$('#'+couponObj).val(cpnNo);	// 쿠폰번호를 세팅
				// 쿠폰 데이터 세팅
				couponHtml	= '';
				couponHtml  += '<li>';
				couponHtml  += '	<span class="subject">['+cpnNo+'] '+cpnName+' (쿠폰 유효기간 '+ validDate +')</span>';
				couponHtml  += '	<button type="button" class="btn-item-del" id="'+couponObj+'Del" name="'+couponObj+'Del" value="'+couponObj+'" >';
				couponHtml  += '		<span class="ico ico-item-del" id="'+couponObj+'" name="'+couponObj+'"><span class="offscreen">쿠폰 삭제</span></span>';
				couponHtml  += '	</button>';
				couponHtml  += '</li>';
				$('#'+couponObj+'Area').append(couponHtml);
			}	// end if
		}	// end for
	}

	/***
	 * 쿠폰 검색 팝업을 통해서 쿠폰 json List 데이터를 받는다.
	 */
	_onlinepolicy.getCouponSearch = function(couponBtnObj){
		var couponObj 		= $(couponBtnObj).val();

		couponObject = couponBtnObj;
		abc.couponSearchPopup(false, "abc.biz.system.onlinepolicy.appendCoupon");
	}

	/***
	 * 쿠폰 검색 팝업의 callback 함수
	 */
	_onlinepolicy.appendCoupon = function(d) {
		_onlinepolicy.setOnlineCoupon(couponObject, d);
	}

	/**
	 * 쿠폰 영역 삭제하기[공통]
	 */
	_onlinepolicy.deleteOnlineCoupon = function(obj){
		var couponObj = $(obj).val();
		$(obj).parent().remove();
		$('#'+couponObj).val('');
	}
})();