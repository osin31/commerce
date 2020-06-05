/**
 *	App Push 발송 관리
 */
(function(){
	var _sendManagement = abc.object.createNestedObject(window,"abc.biz.cmm.send.management");


	/***************************************************************************
	*  app push 발송 관리
	***************************************************************************/

	// 엑셀 업로드 변수
	_sendManagement.checkExtStr = abc.consts.PUSH_TRGT_MEMBER_FILE_EXT;
	_sendManagement.maxSize = abc.consts.PUSH_TRGT_MEMBER_FILE_MAX_MB;

	// 대상자 확인 후 상태 변경 체크 변수
	_sendManagement.isCheckTarget = false;


	// 발송 대상 그리드.
	_sendManagement.sendTargetGridSheet = function() {
		var initSendTargetGridSheet = {};
		var pageCount = $('#pageCount').val();

		initSendTargetGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSendTargetGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSendTargetGridSheet.Cols = [
				{Header:"",		Type:"Text",	SaveName:"memberNo",		Width: 0,	Align:"Center",	Edit:0,	Hidden:1}
			,	{Header:"",		Type:"Text",	SaveName:"pushMesgNo",		Width: 0,	Align:"Center",	Edit:0,	Hidden:1}
			,	{Header:"번호",	Type:"Seq",		SaveName:"",				Width: 10,	Align:"Center",	Edit:0,	Sort:0 	}
			,	{Header:"아이디",	Type:"Text",	SaveName:"loginIdInfo",			Width: 30,	Align:"Center",	Edit:0,	Sort:0	}
			,	{Header:"이름",	Type:"Text",	SaveName:"memberNameInfo",		Width: 30,	Align:"Center",	Edit:0,	Sort:0	}
			,	{Header:"삭제",	Type:"Text",	SaveName:"deleteTarget",	Width: 10,	Align:"Center",	Edit:0,	Sort:0,	FontBold:1,	FontUnderline:1, Cursor:"Pointer", DefaultValue:"삭제"}
		];

		createIBSheet2(document.getElementById("sendTargetGrid"), "sendTargetGridSheet", "100%", "429px");
		IBS_InitSheet(sendTargetGridSheet, initSendTargetGridSheet);
		// Grid 건수 정보 표시
		sendTargetGridSheet.SetCountPosition(3);
		// Grid width 자동 조절
		sendTargetGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		sendTargetGridSheet.SetExtendLastCol(1);

	};

	// 발송유형(즉시|예약)에 따른 날짜 영역 노출
	$('input[name=sendType]').off().on('change', function() {
		//_sendManagement.isCheckTarget = false;

		if(this.value === abc.consts.CM_PUSH_MESSAGE_SEND_TYPE_I) {
			$('#reserveDateForm').addClass('hidden');
		} else if(this.value === abc.consts.CM_PUSH_MESSAGE_SEND_TYPE_R) {
			$('#reserveDateForm').removeClass('hidden');
		}
	});

	// 발송구분(테스트 발송|일반 발송)에 따른 발송대상 wrap 노출
	$('input[name=sendGbnType]').off().on('change', function() {
		_sendManagement.isCheckTarget = false;

		if(this.value === abc.consts.CM_PUSH_SEND_GBN_TYPE_TEST) {
			$('ul[name=testGbnTypeArea], div[name=testGbnTypeArea]').removeClass('hidden');
			$('li[name=nomalGbnTypeArea], table[name=nomalGbnTypeArea]').addClass('hidden');
			$('input[name=pushSendTrgtCode]').eq(0).trigger('click');
		} else if(this.value === abc.consts.CM_PUSH_SEND_GBN_TYPE_REGULAR) {
			$('ul[name=testGbnTypeArea], div[name=testGbnTypeArea]').addClass('hidden');
			$('li[name=nomalGbnTypeArea], table[name=nomalGbnTypeArea]').removeClass('hidden');
		}
	});

	// 발송대상 구분(지정사용자|app설치회원|전체)에 다른 excelWrap 노출
	$('input[name=pushSendTrgtCode]').off().on('change', function() {
		_sendManagement.isCheckTarget = false;

		if(this.value === abc.consts.PUSH_SEND_TRGT_CODE_FILEUPLOAD) {
			$('#excelWrap').show();
		}else {
			$('#excelWrap').hide();
		}
	});

	// 대상자 확인, 대상자 삭제, 발송, 발송취소,
	_sendManagement.doActionAppPush = function(sAction, params) {
		var url = '/cmm/msg/apppush';
		var pageCount = 10;
		var form = $.form(document.forms.sendManageForm);

		switch(sAction) {
		// 대상자 확인
			case 'search' :
				url += '/read-target-list';
				var trgtCode = $('input[name=pushSendTrgtCode]:checked').val();

				form.submit({
					type : 'POST',
					url  : url,
				    success : function(data) {
				    	$('#targetCount').text(data.targetCount);
						if(!abc.text.isBlank(data)) {
							sendTargetGridSheet.LoadSaveData(data);
						}

				    },
				    error: function(e){
				    	alert("대상자 확인에 실패했습니다. " + e.message);
				    }
				});
			break;

		// 대상자 삭제
			case 'delete' :
				url += '/delete-target';

				$.ajax({
					type : 'GET',
					url  : url,
					data : params,
				}).done(function(data, textStatus, jqXHR) {
					if(!abc.text.isBlank(data)) {
						sendTargetGridSheet.LoadSaveData(data);
					}else {
						sendTargetGridSheet.RemoveAll();
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("대상자 삭제에 실패했습니다. " + jqXHR.responseJSON.message);
				});


			break;

		// 발송
			case 'send'   :
				url += '/send-app-push';
				var trgtCode = $('input[name=pushSendTrgtCode]:checked').val();

				form.submit({
					type : 'POST',
					url  : url,
				    success : function(data) {
				    	alert(data.message);

						if (opener != null) {
							opener.parent.abc.biz.cmm.app.push.doActionAppPush('search');
							window.self.close();
						}

				    },
				    error : function(e){
				    	alert("발송에 실패하였습니다. " + e.message);
				    }
				});
			break;

		// 발송취소
			case 'cancel' :
				url += '/cancel-send';
				var pushMesgNo = {'pushMesgNo' : $('#pushMesgNo').val()};

				$.ajax({
					type : 'GET',
					url  : url,
					data : pushMesgNo
				}).done(function(data, textStatus, jqXHR) {
					alert(data.message);

					if (opener != null) {
						opener.parent.abc.biz.cmm.app.push.doActionAppPush('search');
						window.self.close();
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("발송취소에 실패했습니다. " + jqXHR.responseJSON.message);
				});
			break;
		}
	};


	// 엑셀 이름 보여주기
	$('#excelFile').off().on('change', function(){
		if(!abc.text.allNull($(this).val())) {
			if(!_sendManagement.validateFile('#'+this.id, _sendManagement.checkExtStr, _sendManagement.maxSize)) {
				return false;
			}
		}

		$('#fileDisp').removeClass('hidden').children('a').text($(this)[0].files[0].name);
	});

	// 보여주기 한 첨부 엑셀 삭제
	$('#fileDelBtn').off().on('click', function() {
		$('#excelFile').val('');
		$('#fileDisp').addClass('hidden');
	});

	// 대상자 확인 클릭시
	$('#targetCheckBtn').off().on('click', function(){
		if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_PROGRESS
				|| $('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_WAITTING) {
			alert('이미 발송 또는 예약발송 진행 중 입니다.');
			return false;
		}else if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_SUCCESS) {
			alert('이미 발송 완료된 메시지입니다.');
			return false;
		}

		if($('input[name=pushSendTrgtCode]:checked').val() === abc.consts.PUSH_SEND_TRGT_CODE_FILEUPLOAD) {
			if(abc.text.isBlank($('#excelFile').val())) {
				alert('파일을 등록해주세요.');
				return false;
			}
		}

		// 대상자 확인
		_sendManagement.doActionAppPush('search');

		// 대상자 확인 완료
		_sendManagement.isCheckTarget = true;
		return false;
	});

	// 발송/예약발송 클릭시
	$('#appPushSend').off().on('click', function() {
		// 발송 진행상태에 따른 alert 처리
		if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_PROGRESS
				|| $('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_WAITTING) {
			alert('이미 발송 또는 예약발송 진행 중 입니다.');
			return false;
		}else if($('#pushIngStatCode').val() === abc.consts.PUSH_ING_STAT_CODE_SUCCESS) {
			alert('이미 발송 완료된 메시지입니다.');
			return false;
		}

		// 예약발송일시 날짜입력 체크
		if($('input[name=sendType]:checked').val() === abc.consts.CM_PUSH_MESSAGE_SEND_TYPE_R) {
			if(abc.text.isBlank($('input[name=pushDate]').val())) {
				alert('예약발송 날짜를 입력해주세요.');
				return false;
			}
		}else {
			// 즉시발송일경우 날짜데이터 초기화
			$('#pushDate').val('');
			$('select[name=pushHours]').val('00');
			$('select[name=pushMin]').val('00');
		}

		// 발송대상이 지정사용자(파일업로드)일 경우 대상자 확인여부 체크
		if($('input[name=pushSendTrgtCode]:checked').val() === abc.consts.PUSH_SEND_TRGT_CODE_FILEUPLOAD
				&& $('input[name=sendGbnType]:checked').val() === abc.consts.CM_PUSH_SEND_GBN_TYPE_TEST
				&& sendTargetGridSheet.GetDataLastRow() < 0) {
			alert('대상자 확인 부탁드립니다.');
			return false;
		}

		// 대상자 확인 후 값 변경 체크
		if(!_sendManagement.isCheckTarget) {
			alert('대상자 확인 부탁드립니다.');
			return false;
		}

		// 대상자 확인후 필요 없는 엑셀파일 폼전송 전에 초기화
		$('#excelFile').remove();

		// 발송
		_sendManagement.doActionAppPush('send');

		return false;

	});

	// 발송취소 클릭시
	$('#cancelSend').off().on('click', function() {
		_sendManagement.doActionAppPush('cancel');
	});


	// 엑셈 폼 다운로드 클릭시
	$('#excelFormDown').off().on('click', function() {
		location.href='/cmm/msg/apppush/excel-form-down';
		return false;
	});


	/**
	 *  첨부파일 validate 메소드
	 */
	_sendManagement.validateFile = function(fileId, checkExtStr, maxSize) {

		//파일정보
		var fileObj= $(fileId)[0].files[0];
		var img_all_names, ext, size;

		if(fileObj != null || fileObj != undefined){
			img_all_names = fileObj['name'].split('.');
			ext = img_all_names[img_all_names.length -1].toLowerCase();//확장자
			size = fileObj['size'];//파일사이즈
		}

		if(!(checkExtStr.indexOf(ext) > -1)) {

			alert(checkExtStr + ' 해당 확장자의 파일만 업로드 가능합니다.');

			$(fileId).replaceWith( $(fileId).clone(true) ); //초기화
			$(fileId).val('');

			return false;
		}

		if(size >= parseInt(1048576 * maxSize)) {
			alert(maxSize + "MB 이하의 파일만 가능합니다.");

			$(fileId).replaceWith( $(fileId).clone(true) ); //초기화
			$(fileId).val('');

			return false;
		}

		return true;
	};

})();