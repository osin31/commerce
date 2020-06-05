/***
 * 권한그룹 관리.
 *
 */
(function() {
	/*************************************************************************
	 *					권한그룹관리 : 권한그룹 등록/수정
	 *************************************************************************/
	var _authGroupReg = abc.object.createNestedObject(window,"abc.biz.system.admin.authgroupreg");

	_authGroupReg.init = function(){
		//사용여부 checkbox 세팅
		if($("#useYn").val() == 'N'){
			$("#radioUseY").prop('checked', false);
			$("#radioUseN").prop('checked', true);
		}else{
			$("#radioUseY").prop('checked', true);
			$("#radioUseN").prop('checked', false);
			$("#useYn").val("Y");
		}
		if($("#status").val() == abc.consts.CRUD_U){
			//권한그룹명  카운트 세팅
			_authGroupReg.authNameCount();

			//권한적용시스템 value 세팅
			$("#selAuthApplySystemType").val($("#authApplySystemType").val());
			$("#selAuthApplySystemType").prop('disabled', 'disabled');
		}
	}

	/**
	 * 메뉴명 카운터
	 */
	_authGroupReg.authNameCount = function(){

		var content = $("#authName").val();
		$("#authNameCnt").html("("+content.length+" / 20자)");

		if (content.length > 20){
			alert("최대 20자까지 입력 가능합니다.");
			$("#authName").val("");
			$("#authName").val(content.substring(0, 20));
			$('#authNameCnt').html("(20 / 20자)");
		}
	}

	_authGroupReg.noteTextCount = function(e) {
		var content = $("#noteText").val();
		if (abc.text.isLimitLength(content, 500)) {
			alert("최대 500자까지 입력 가능합니다.");
			$("#noteText").val(abc.text.substringByte(content, 500));
			return false;
		}
	}

	//권한그룹 삭제
	_authGroupReg.authGroupRemove = function (){
		if (!confirm('삭제 하시겠습니까?')) {
			return;
		}

		$.ajax({
			type :"post",
			url : "/system/admin/authority/delete",
			data: $("#frmSearch").serialize()
		})
		.done(function(data){
			alert(data.Message);
			if(data.result == abc.consts.BOOLEAN_TRUE){
				opener.parent.abc.biz.system.admin.authority.doActionAuthGroup('search');
				window.self.close();
			}
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
			alert("에러가 발생했습니다.");
		});
	}
	//권한그룹 저장
	_authGroupReg.authGroupSave = function (){
		if (!this.regValidate()) {
			return;
		}

		if (!confirm('저장하시겠습니까?')) {
			return;
		}

		var str = $('#noteText').val();
		str = str.replace(/(?:\r\n|\r|\n)/g, '<br/>');
		$('#noteText').val(str);

		$.ajax({
			type :"post",
			url : "/system/admin/authority/update",
			data: $("#frmSearch").serialize()
		})
		.done(function(data){
			alert(data.Message);
			opener.parent.abc.biz.system.admin.authority.doActionAuthGroup('search');
			window.self.close();
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
			alert("에러가 발생했습니다.");
		});
	}

	_authGroupReg.regValidate = function(){

		if(abc.text.isBlank($("#authName").val())){
			alert("권한 그룹명을 입력해주세요");
			$("#authName").focus();
			return false;
		}

		if(abc.text.isBlank($("#authApplySystemType").val())){
			alert("권한 적용 시스템을 선택해주세요");
			$("#selAuthApplySystemType").focus();
			return false;
		}

		return true;
	}

})();