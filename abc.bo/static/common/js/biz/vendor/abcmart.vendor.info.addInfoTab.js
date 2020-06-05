/***
 * 입점관리 > 입점사 정보등록
 *
 */
(function() {

	var _addInfo = abc.object.createNestedObject(window,"abc.biz.vendor.info.add");
	_addInfo.vendorInfo;
	_addInfo.vendorCategoryList;
	_addInfo.vendorBrandList;

	//화면초기화
	_addInfo.init = function(){

		var chnableIds = [];
		var hideIds = [];

		if($("#upAuthNo").val() == "ROLE_30000"){

			$('input').attr("readonly", true);
			$(':checkbox').attr('disabled', true);
			$(':radio').attr('disabled', true);
			$('select').attr("disabled", true);

			chnableIds = ["allNotcDispY", "allNotcDispN", "iAllNotcStartYmd", "iAllNotcEndYmd"];

			hideIds = ["commissionMoreBtn", "delCommissionBtn", "delDiscountBrandChkBtn", "discountBrandMoreBtn"
					 , "addDiscountBrandBtn_", "delDiscountBrandBtn_", "postSearchBtn_", "vedorAddInfoSave"
					 ];

		}else if($("#popUnYn").val() == 'Y'){
			$('input').attr("readonly", true);
			$(':checkbox').attr('disabled', true);
			$(':radio').attr('disabled', true);
			$('select').attr("disabled", true);

			hideIds = ["commissionMoreBtn", "delCommissionBtn", "delDiscountBrandChkBtn", "discountBrandMoreBtn"
					 , "addDiscountBrandBtn_", "delDiscountBrandBtn_", "postSearchBtn_", "vedorAddInfoSave", "vendorInfoGoList1"
					 ];

		}

		for(var i in chnableIds){
			$("[id^="+chnableIds[i]+"]").attr("readonly", false);
			$("[id^="+chnableIds[i]+"]").attr("disabled", false);
		}


		for(var i in hideIds){
			$("[id^="+hideIds[i]+"]").hide();
		}

		_addInfo.event();
	}


	// 화면이벤트
	_addInfo.event = function(){

		//예외수수료 적용이력 조회
		$("#commissionHistoryPop").click(function(){
			_addInfo.commissionHistoryPopup();
		});

		//예외수수료 행추가
		$("#commissionMoreBtn").click(function(){
			if(abc.text.allNull(_addInfo.vendorCategoryList)){
				alert("등록된 표준카테고리가 없습니다");
				return;
			}

			if(abc.text.allNull(_addInfo.vendorBrandList)){
				alert("등록된 브랜드가 없습니다");
				return;
			}

			var cnt = $("#tbodyCommission tr").length + 1;
			var strHtml = "";
				strHtml += '<tr id="trCommission_'+cnt+'">';
				strHtml += '<input type="hidden" id="commissionDelYn_'+cnt+'" name="exceptionCommissionList.delYn" value="N">';
				strHtml += '<input type="hidden" id="vndrExceptCmsnSeq_ '+cnt+'"name="exceptionCommissionList.vndrExceptCmsnSeq">';
				strHtml += '<input type="hidden" id="commissionRowIndex_'+cnt+'" name="exceptionCommissionList.commissionRowIndex" value="'+cnt+'" >';
				strHtml += '<input type="hidden" id="commissionApplyStartYmd_'+cnt+'" name="exceptionCommissionList.applyStartYmd">';
				strHtml += '<input type="hidden" id="commissionApplyEndYmd_'+cnt+'" name="exceptionCommissionList.applyEndYmd">';
				strHtml += '<input type="hidden" id="sApplyStartYmd_'+cnt+'" value="">';
				strHtml += '	<td class="only-chk">';
				strHtml += '		<span class="ui-chk">';
				strHtml += '			<input id="chkCommission_'+cnt+'" type="checkbox" name="chkCommission" >';
				strHtml += '			<label for="chkCommission_'+cnt+'"></label>';
				strHtml += '			</span>';
				strHtml += '	</td>';
				strHtml += '	<td class="input">';
				strHtml += '		<select class="ui-sel" name="exceptionCommissionList.stdCtgrNo" id="commissionStdCtgrNo_'+cnt+'" custoum="required" title="표준카테고리 선택">';
				strHtml += '			<option value="">선택</option>';
				for(var i in _addInfo.vendorCategoryList){
					strHtml += '		<option value="'+_addInfo.vendorCategoryList[i].stdCtgrNo+'">'+_addInfo.vendorCategoryList[i].stdCtgrName+'</option>';
				}
				strHtml += '		</select>';
				strHtml += '	</td>';
				strHtml += '	<td class="input text-center clear-float">';
				strHtml += '		<select class="ui-sel" name="exceptionCommissionList.brandNo" id="commissionBrandNo_'+cnt+'" custoum="required" title="브랜드 선택">';
				strHtml += '			<option value="">선택</option>';
				for(var i in _addInfo.vendorBrandList){
					strHtml += '		<option value="'+_addInfo.vendorBrandList[i].brandNo+'">'+_addInfo.vendorBrandList[i].brandName+'</option>';
				}
				strHtml += '		</select>';
				strHtml += '	</td>';
				strHtml += '	<td class="input clear-float text-center">';
				strHtml += '		<span class="ip-text-box">';
				strHtml += '			<input type="text" class="ui-input num-unit100" id="cmsnRate_'+cnt+'" name="exceptionCommissionList.cmsnRate" custoum="required" title="적용대상 수수료율 입력" onkeyup="abc.biz.vendor.info.add.validateOnlyNumber(this)">';
				strHtml += '			<span class="text">%</span>';
				strHtml += '		</span>';
				strHtml += '	</td>';
				strHtml += '	<td class="input">';
				strHtml += '		<span class="term-date-wrap">';
				strHtml += '			<span class="date-box">';
				strHtml += '				<input type="text" id="iCommissionApplyStartYmd_'+cnt+'" data-role="datepicker" class="ui-cal js-ui-cal" custoum="required" title="시작일 선택" >';
				strHtml += '			</span>';
				strHtml += '			<span class="text">~</span>';
				strHtml += '			<span class="date-box">';
				strHtml += '				<input type="text" id="iCommissionApplyEndYmd_'+cnt+'" data-role="datepicker" class="ui-cal js-ui-cal" custoum="required" title="종료일 선택">';
				strHtml += '			</span>';
				strHtml += '		</span>';
				strHtml += '	</td>';
				strHtml += '	<td class="input">';
				strHtml += '		<input type="text" class="ui-input" name="exceptionCommissionList.noteText" title="관리자 메모 입력">';
				strHtml += '	</td>';
				strHtml += '</tr>';

			$("#tbodyCommission").append(strHtml);

			$(".ui-cal").each(function(){
				abc.namespace.front.backOffice.setDatepicker(this);
			});
		});

		// 예외수수료 전체체크
		$("#chkCommissionAll").click(function(){
			$("input[name=chkCommission]").prop("checked", this.checked);
		});

		//예외수수료 삭제
		$("#delCommissionBtn").click(function(){
			var bPass = true;
			$("input[name=chkCommission]").each(function(){
				if($(this).is(":checked") && $(this).attr('id') != 'chkCommissionAll'){
					var idx= $(this).attr('id').split("_").pop();
					var commissionApplyStartYmd = $("#iCommissionApplyStartYmd_"+idx).val();
					var sApplyStartYmd = $("#sApplyStartYmd_"+idx).val();
					if(abc.date.dateDiff(new Date(), commissionApplyStartYmd) <= 0
						&& !abc.text.allNull(sApplyStartYmd)){
						bPass = false;
						alert("현재 진행중인 정보는 삭제 할수 없습니다");
						return false;
					}

					$("#commissionDelYn_"+idx).val('Y');
					$("#trCommission_"+idx).hide();
				}
				if(!bPass){
					return false;
				}

			});
		});

		//예외수수료 적용대상수수료율
		$("input[id^=cmsnRate_]").keyup(function(){
			_addInfo.validateOnlyNumber(this);
		});

		//임직원 할인여부 행추가
		$("#discountBrandMoreBtn").click(function(){
			if(abc.text.allNull(_addInfo.vendorBrandList)){
				alert("등록된 브랜드가 없습니다");
				return;
			}

			var cnt = $("#tbodyDiscountBrand tr").length;
			var strHtml = "";
				strHtml += '<tr name="trDiscount_'+cnt+'">';
				strHtml += '<input type="hidden" id="chkDiscountBrandDelYn_'+cnt+'" name="chkDiscountBrandDelYn" value="N">';
				strHtml += '	<td class="only-chk" rowspan="2">';
				strHtml += '		<span class="ui-chk">';
				strHtml += '			<input id="chkBrand_'+cnt+'" type="checkbox" name="chkBrand" >';
				strHtml += '			<label for="chkBrand_'+cnt+'"></label>';
				strHtml += '		</span>';
				strHtml += '	</td>';
				strHtml += '	<td class="input clear-float text-center" rowspan="2">';
				strHtml += '		<span class="ip-text-box">';
				strHtml += '			<input type="text" id="iDscntRate_'+cnt+'" name="iDscntRate" class="ui-input num-unit100" title="적용 할인율 입력" onkeyup="abc.biz.vendor.info.add.validateOnlyNumber(this)">';
				strHtml += '			<span class="text">%</span>';
				strHtml += '		</span>';
				strHtml += '	</td>';

				strHtml += '	<td class="input">';
				strHtml += '		<span class="ip-text-box">';
				strHtml += '			<select class="ui-sel" id="selDiscountBrandNo_'+cnt+'" title="브랜드 선택">';
				strHtml += '				<option value="">선택</option>';
				for(var i in _addInfo.vendorBrandList){
					strHtml += '			<option value="'+_addInfo.vendorBrandList[i].brandNo+'">'+_addInfo.vendorBrandList[i].brandName+'</option>';
				}
				strHtml += '			</select>';
				strHtml += '			<a href="javascript:void(0);" onclick="abc.biz.vendor.info.add.addDiscountBrandNo('+cnt+')" class="btn-sm btn-func">추가</a>';
				strHtml += '		</span>';
				strHtml += '	</td>';
				strHtml += '</tr>';
				strHtml += '<tr name="trDiscount_'+cnt+'">';
				strHtml += '	<td class="input">';
				strHtml += '		<ul class="item-list" id="ulDiscountBrand_'+cnt+'">';
				strHtml += '		</ul>';
				strHtml += '	</td>';
				strHtml += '</tr>';

			$("#tbodyDiscountBrand").append(strHtml);
		});

		//임직원 할인여부 전체선택
		$("#chkBrandAll").click(function(){
			$("input[name=chkBrand]").prop("checked", this.checked);
		});

		//임직원 할인여부 삭제
		$("#delDiscountBrandChkBtn").click(function(){
			$("input[name=chkBrand]").each(function(){
				if($(this).is(":checked")){
					var idx= $(this).attr('id').split("_").pop();
					$("[id^=discountBrandDelYn_"+idx+"]").val('Y');
					$("#chkDiscountBrandDelYn_"+idx).val("Y");
					$("[name=trDiscount_"+idx+"]").hide();
				}
			});
		});

		//임직원 할인여부 브랜드추가
		$("[id^=addDiscountBrandBtn]").click(function(){
			var idx = $(this).attr("id").split("_").pop();
			_addInfo.addDiscountBrandNo(idx);
		});

		//임직원 할인여부 브랜드삭제
		$("[id^=delDiscountBrandBtn]").click(function(){
			_addInfo.delDiscountBrandNo($(this));
		});

		//임직원 적용대상 할인율
		$("[name=iDscntRate]").keyup(function(){
			_addInfo.validateOnlyNumber(this);
		});

		//우편번호
		$("#postSearchBtn_D").click(function(){
			abc.postPopup(abc.biz.vendor.info.add.setPostCodeD);
		});
		$("#postSearchBtn_W").click(function(){
			abc.postPopup(abc.biz.vendor.info.add.setPostCodeW);
		});

		//출고지 설정
		$("input[name=exWrhsSetup]").change(function(){
			if($(this).val() == 'V'){
				_addInfo.setVendorAddress("D");
			}else{
				_addInfo.dlvyAddressReset("D");
			}
		});

		//회수지 설정
		$("input[name=inWrhsSetup]").change(function(){
			if($(this).val() == 'V'){
				_addInfo.setVendorAddress("W");
			}else if($(this).val() == 'D'){
				if(abc.text.allNull($("#postCodeText_D").val()) ){
					alert("기본출고지를 입력해 주세요");
					return;
				}
				$("#postCodeText_W").val($("#postCodeText_D").val());
				$("#postAddrText_W").val($("#postAddrText_D").val());
				$("#dtlAddrText_W").val($("#dtlAddrText_D").val());
			}else{
				_addInfo.dlvyAddressReset("W");
			}
		});


		//회수지 전화번호 숫자만 입력하게
		$("#telNoText_W").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});

		//입점사목록가기
		$("#vendorInfoGoList1").click(function(){
			var msg = "입력한 내용을 저장하지 않고 목록화면으로 이동합니다 ";
			if(!confirm(msg)){
				return;
			}

			vendorSearchForm.method = "post";
			vendorSearchForm.action = "/vendor/info";
			vendorSearchForm.submit();
		});

		// 입점사 부가정보 저장
		$("#vedorAddInfoSave").click(function(){
			if (!confirm('저장하시겠습니까?')) {
				return;
			}
			if($("#upAuthNo").val() != "ROLE_30000"){
				if(!_addInfo.saveValidation()){
					return;
				}
			}

			$("input[id^=iDscntRate_]").each(function(){
				var idx = $(this).attr("id").split("_").pop();
				var dscntRate = $(this).val();
				$("[id^=dscntRate_"+idx+"]").each(function(){
					$(this).val(dscntRate);
				});
			});


			$("input[id^=iCommissionApplyStartYmd_]").each(function(){
				var targetIdx = $(this).attr("id").split("_").pop();
				var applyStartYmd = abc.text.validateStringSignRemove($(this).val());
				$("#commissionApplyStartYmd_"+targetIdx).val(applyStartYmd);
			});

			$("input[id^=iCommissionApplyEndYmd_]").each(function(){
				var targetIdx = $(this).attr("id").split("_").pop();
				var applyEndYmd = abc.text.validateStringSignRemove($(this).val());
				$("#commissionApplyEndYmd_"+targetIdx).val(applyEndYmd);
			});

			var url="";
			if($("#upAuthNo").val() == "ROLE_30000"){
				url = "/vendor/info/update-addtab-po";
			}else{
				url = "/vendor/info/update-addtab";
			}

			$.ajax({
				type :"post",
				url : url,
				data : $("#vendorAddForm").serialize()
			})
			.done(function(data){
				if(data.result == 'Y'){
					alert("저장하였습니다.");
					$("#vendorAddForm").remove();
					abc.biz.vendor.info.base.vendorAddInfo();
				}else{
					alert("저장에 실패했습니다.");
				}
			})
			.fail(function(e){
				console.log("e : " + JSON.stringify(e));
				alert("저장에 실패했습니다.");
			});
		});
	}

	_addInfo.addDiscountBrandNo = function(idx){
		var isDup = false;
		var brandNo = $("#selDiscountBrandNo_"+idx+" option:selected").val();
		var brandName= $("#selDiscountBrandNo_"+idx+" option:selected").text();
		if(abc.text.allNull(brandNo)){
			return;
		}
		$("[id^=discountBrandNo]").each(function(){
			if( ($(this).val() == brandNo) && ($(this).parent().find('[id^=discountBrandDelYn_]').val() == 'N') ){
				isDup = true;
				alert("이미 적용되어 있는 브랜드입니다.");
				return;
			}
		});
		if(isDup){
			$("#selDiscountBrandNo_"+idx+" option:eq(0)").attr("selected", "selected");
			return;
		}
		var liCnt = $("#ulDiscountBrand_"+idx).find('li').length + 1;
		var strHtml = "";
			strHtml += '<li>';
			strHtml += '	<input type="hidden" name="employeeDiscountList.discountBrandIndex" value="'+idx+''+liCnt+'">';
			strHtml += '	<input type="hidden" id="discountBrandNo'+idx+'_'+liCnt+'" name="employeeDiscountList.brandNo" value="'+brandNo+'">';
			strHtml += '	<input type="hidden" name="employeeDiscountList.vndrBrandEmpDscntSeq">';
			strHtml += '	<input type="hidden" id="dscntRate_'+idx+'_'+liCnt+'" name="employeeDiscountList.dscntRate">';
			strHtml += '	<input type="hidden" id="discountBrandDelYn_'+idx+'_'+liCnt+'" name="employeeDiscountList.delYn" value="N">';
			strHtml += '	<span class="subject">'+brandName+'</span>';
			strHtml += '	<button type="button" id="delDiscountBrandBtn_'+idx+'_'+liCnt+'" onclick="abc.biz.vendor.info.add.delDiscountBrandNo(this)" class="btn-item-del">';
			strHtml += '		<span class="ico ico-item-del"><span class="offscreen">해당 브랜드 삭제</span></span>';
			strHtml += '	</button>';
			strHtml += '</li>';

		$("#ulDiscountBrand_"+idx).append(strHtml);

		$("#selDiscountBrandNo_"+idx+" option:eq(0)").attr("selected", "selected");
	}

	_addInfo.delDiscountBrandNo = function(el){

		var idx = $(el).attr("id").split("_");
		$("#discountBrandDelYn_"+idx[1]+"_"+idx[2]).val("Y");
		$(el).parent().hide();

		var isEmpty= true;
		$(el).parent().parent().find('li').each(function(){
			if($(this).find('[id^=discountBrandDelYn_]').val() == 'N'){
				isEmpty = false;
				return;
			}
		});
		if(isEmpty){
			$("[name=trDiscount_"+idx[1]+"]").hide();
		}
	}

	_addInfo.validateOnlyNumber = function(el){
		abc.text.validateOnlyNumber(el);

		if($(el).val() < 0 || $(el).val() > 100){
			alert("1~100까지의 숫자만 입력가능합니다");
			$(el).val("");
			$(el).focus();
		}
	}

	_addInfo.dlvyAddressReset = function(wrhsDlvyGbnType){
		$("#postSearchBtn_" + wrhsDlvyGbnType).show();
		$("#postCodeText_" + wrhsDlvyGbnType).val("");
		$("#postAddrText_" + wrhsDlvyGbnType).val("");
		$("#dtlAddrText_" + wrhsDlvyGbnType).val("");
	}

	_addInfo.setVendorAddress = function(wrhsDlvyGbnType){
		$("#postSearchBtn_" + wrhsDlvyGbnType).hide();
		$("#postCodeText_" + wrhsDlvyGbnType).val(_addInfo.vendorInfo.postCodeText);
		$("#postAddrText_" + wrhsDlvyGbnType).val(_addInfo.vendorInfo.postAddrText);
		$("#dtlAddrText_" + wrhsDlvyGbnType).val(_addInfo.vendorInfo.dtlAddrText);
	}

	_addInfo.saveValidation = function(){
		//예외 수수료 정보
		var bPass = true;
		$("[id^=trCommission_]").each(function(){
			var delYn = $(this).find("[id^=commissionDelYn_]").val();
			if(delYn != 'Y'){
				//필수값 체크
				$(this).find("[custoum=required]").each(function(){
					if(abc.text.allNull($(this).val())){
						bPass = false;
						alert($(this).attr('title') + "을 하세요");
						$(this).focus();
						return false;
					}
				});

				//적용기간 체크
				var vndrExceptCmsnSeq = $(this).find("[id^=vndrExceptCmsnSeq_]").val();
				var commissionApplyStartYmd = $(this).find("[id^=iCommissionApplyStartYmd_]").val();
				var commissionApplyEndYmd= $(this).find("[id^=iCommissionApplyEndYmd_]").val();
				var sApplyStartYmd = $(this).find("[id^=sApplyStartYmd_]").val();

				if (bPass){
					if(abc.date.dateDiff(new Date(), commissionApplyStartYmd) <= 0
							&&  abc.text.validateStringSignRemove(commissionApplyStartYmd) != abc.text.validateStringSignRemove(sApplyStartYmd.substring(0,10))
							&& !abc.text.allNull(sApplyStartYmd)){
						bPass = false;
						alert("적용기간은 익일 이후로 설정 가능합니다");
						$(this).find("[id^=iCommissionApplyStartYmd_]").focus();
						return false;
					}else if(abc.date.dateDiff(new Date(), commissionApplyStartYmd) <= 0
							&& abc.text.allNull(sApplyStartYmd)){
						bPass = false;
						alert("적용기간은 익일 이후로 설정 가능합니다");
						$(this).find("[id^=iCommissionApplyStartYmd_]").focus();
						return false;
					}
					if(abc.date.dateDiff(commissionApplyStartYmd, commissionApplyEndYmd) < 0){
						bPass = false;
						alert("적용기간 시작일이 종료일보다 클수 없습니다.");
						$(this).find("[id^=iCommissionApplyEndYmd_]").focus();
						return false;
					}
				}

				//중복체크
				if(bPass){
					var commissionRowIndex = $(this).find("[id^=commissionRowIndex_]").val();
					var stdCtgrNo = $(this).find("[id^=commissionStdCtgrNo_]").val();
					var brandNo = $(this).find("[id^=commissionBrandNo_]").val();

					$("[id^=trCommission_]").each(function(){
						var tmpDelYn = $(this).find("[id^=commissionDelYn_]").val();
						if(tmpDelYn != 'Y'){
							var tmpCommissionRowIndex = $(this).find("[id^=commissionRowIndex]").val();
							var tmpStdCtgrNo = $(this).find("[id^=commissionStdCtgrNo_]").val();
							var tmpBrandNo = $(this).find("[id^=commissionBrandNo_]").val();
							var tmpCommissionApplyStartYmd = $(this).find("[id^=iCommissionApplyStartYmd_]").val();
							var tmpCommissionApplyEndYmd= $(this).find("[id^=iCommissionApplyEndYmd_]").val();
							if( commissionRowIndex != tmpCommissionRowIndex && stdCtgrNo == tmpStdCtgrNo && brandNo== tmpBrandNo){

								var checkApplyStartYmd = abc.date.dateDiff(tmpCommissionApplyStartYmd, commissionApplyEndYmd);
								var checkApplyEndYmd = abc.date.dateDiff(tmpCommissionApplyEndYmd, commissionApplyStartYmd);

								if(checkApplyStartYmd >= 0 && checkApplyEndYmd <= 0){
									bPass = false;
									alert("적용기간이 중복됩니다");
									$(this).find("[id^=iCommissionApplyStartYmd_]").focus();
									return false;
								}
							}
						}
					});
				}

				if (!bPass){
					return false;
				}
			}
		});

		if (!bPass){
			return false;
		}


		//임직원 할인여부
		$("#tbodyDiscountBrand tr").each(function(){

			var delYn = $(this).find("[name=chkDiscountBrandDelYn]").val();
			if(delYn == 'N'){
				var dscntRate = $(this).find("[name=iDscntRate]").val();
				if(abc.text.allNull(dscntRate)){
					bPass = false;
					alert("적용대상 할인율을 입력해주세요");
					$(this).find("[name=iDscntRate]").focus();
					return false;
				}
				if(bPass){
					$(this).next('tr').find("[id^=ulDiscountBrand_]").each(function(){
						if(typeof $(this).find("[id^=discountBrandDelYn_]").val() === "undefined"){
							bPass = false;
							return false;
						}else if($(this).find("[id^=discountBrandDelYn_]").val() == 'N'){
							bPass = true;
							return false;
						}
					});
				}

				if(!bPass){
					alert("적용브랜드를 선택해 주세요");
					$(this).find("[id^=selDiscountBrandNo_]").focus();
					return false;
				}

			}
		});
		if(!bPass){
			return false;
		}


		//입점사 기본출고지/ 기본회수지 설정
		if(abc.text.allNull($("#postCodeText_D").val())){
			alert("출고지 주소를 입력하세요");
			$("#postCodeText_D").focus();
			return false;
		}
		if(abc.text.allNull($("#postCodeText_W").val())){
			alert("회수지 주소를 입력하세요");
			$("#postCodeText_W").focus();
			return false;
		}


		return true;
	}
	// 우편번호 세팅
	_addInfo.setPostCodeD = function(data){
		$("#postCodeText_D").val(data.postCode);
		$("#postAddrText_D").val(data.postAddress);
//        $("#buildingCode").val(data.buildingCode); 건물번호
		$("#dtlAddrText_D").focus();  // 상세주소에 포커스
	}

	// 우편번호 세팅
	_addInfo.setPostCodeW = function(data){
		$("#postCodeText_W").val(data.postCode);
		$("#postAddrText_W").val(data.postAddress);
//        $("#buildingCode").val(data.buildingCode); 건물번호
		$("#dtlAddrText_W").focus();  // 상세주소에 포커스
	}


	_addInfo.commissionHistoryPopup = function() {
		var url = "/vendor/info/commission-history-pop";
		var _params = {};
		_params.vndrNo = $("#vndrNo").val();

		abc.open.popup({
			winname :	"commissionHistoryPopup",
			url 	:	url,
			width 	:	1300,
			height	:	650,
			params	:	_params
		});
	}



})();