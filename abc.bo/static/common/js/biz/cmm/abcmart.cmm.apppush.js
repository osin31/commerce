/**
 *
 */

(function(){
	var _appPush = abc.object.createNestedObject(window,"abc.biz.cmm.app.push");

	/***************************************************************************
	*  app push 관리 목록.
	***************************************************************************/

	// app push 그리드.
	_appPush.appPushGridSheet = function() {
		var initAppPushGridSheet = {};
		var pageCount = $('#pageCount').val();

		initAppPushGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initAppPushGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initAppPushGridSheet.Cols = [
				{Header:"",					Type:"Text",		SaveName:"pushMesgNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"번호",				Type:"Seq",			SaveName:"",						Width: 20,		Align:"Center",		Edit:0,		Sort:0 			}
			,	{Header:"발송형태",			Type:"Text",		SaveName:"sendTypeName",			Width: 35,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"진행상태",			Type:"Text",		SaveName:"pushIngStatCode",			Width: 35,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"사이트",				Type:"Text",		SaveName:"siteName",				Width: 50,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"메시지 제목",			Type:"Html",		SaveName:"pushTitleText",			Width: 180,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"전송건수",			Type:"Int",			SaveName:"pushSendCount",			Width: 40,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"대상자관리",			Type:"Int",	 		SaveName:"sendTrgtMemberCount",		Width: 40,		Align:"Center",		Edit:0,		Sort:0, 		}
			,	{Header:"대상자관리",			Type:"Text",		SaveName:"sendManagement",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer",	DefaultValue:"발송관리"}
			,	{Header:"발송(예정)일시",		Type:"Date",		SaveName:"sendIrDtm",				Width: 70,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"작성자",				Type:"Text",		SaveName:"maskingRgsterInfo",				Width: 0,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"작성일시",			Type:"Date",		SaveName:"rgstDtm",					Width: 70,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"수정자",				Type:"Text",		SaveName:"maskingModerInfo",				Width: 0,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"수정일시",			Type:"Date",		SaveName:"modDtm",					Width: 70,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("appPushGrid"), "appPushGridSheet", "100%", "429px");
		IBS_InitSheet(appPushGridSheet, initAppPushGridSheet);
		// Grid 건수 정보 표시
		appPushGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		appPushGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		appPushGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		appPushGridSheet.SetExtendLastCol(1);

	};

	_appPush.doActionAppPush = function(sAction) {
		var form = $('#appPushForm');
		var _url = '/cmm/msg/apppush';
		var _method = '';
		var formData = new FormData(form[0]);

		switch(sAction) {
			case 'search' :
				if(typeof appPushGridSheet != "undefined"){
					_method = '/read-list';

					abc.date.searchValidate();

					appPushGridSheet.SetPageCount($('#pageCount').val());

					var pageCount = $('#pageCount').val();
					var param = { url : _url + _method
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.appPushForm)
							, CPage : $("#listPageNum").val()
							, sheet : 'appPushGridSheet' };

					DataSearchPaging(param);
					$('#listPageNum').val('1');
				}

			break;

			case 'save' :
				_method = abc.text.isBlank($('#pushMesgNo').val()) ? '/save' : '/modify';

				if(!_appPush.saveValidation()) {
					return;
				}

				$.ajax({
					processData: false,
					contentType: false,
					url : _url + _method,
					method : 'post',
					data : formData
				}).done(function(data){
					alert(data.message);
					location.href= '/cmm/msg/apppush';
				}).fail(function(jqXHR, textStatus, errorThrown){
					alert('저장에 실패하였습니다. '+jqXHR.responseJSON.message);
				});

			break;

			case 'delete' :
				_method = '/delete';

				if(confirm('삭제 하시겠습니까?')){
					$.ajax({
						url : _url+_method,
						method : 'post',
						data : form.serialize()
					}).done(function(data){
						alert(data.pushMesgNo+':'+data.message);
						location.href = '/cmm/msg/apppush';

					}).fail(function(jqXHR, textStatus, errorThrown){
						alert(jqXHR.responseJSON.message);
						return false;
					});
				}

			break;
		}
	};

	// app push 메인화면 이벤트 함수.
	_appPush.mainEvent = function() {

		// 기간검색 초기값.
		abc.date.month($('input:text[name=fromDate]'));
		abc.date.month($('input:text[name=toDate]'));

		// 캘린더 버튼 설정(일)
		$('#toDay').off().on('click', function(){
			abc.date.days(this);
		});

		// 캘린더 버튼 설정(주)
		$('#toWeek').off().on('click', function(){
			abc.date.week(this);
		});

		// 캘린더 버튼 설정(월)
		$('#toMonth').off().on('click', function(){
			abc.date.month(this);
		});

		// 캘린더 버튼 설정(1년)
		$('#toYear').off().on('click', function(){
			abc.date.year(this);
		});

		$('#appPushSearch').off().on('click', function(){
			_appPush.doActionAppPush('search');
		});

		// 검색 초기화.
		$('#appPushReset').off().on('click', function(){
			$('#appPushForm')[0].reset();
			$("#toMonth").trigger("click");
		});

		$('#appPushCreateForm').off().on('click', function(){
			location.href = '/cmm/msg/apppush/create-form';
		});
	};


	// ============================================================= 등록/상세/수정

	// 첨부파일 처리 시작
	_appPush.checkExtStr = '';
	_appPush.maxSize = '';


	// app push 등록 폼화면 이벤트 함수.
	_appPush.saveFormEvent = function(){
		var form = $('#appPushForm');

		// 첨부파일 등록 이벤트.
		$('#imageFile').off('change').on('change', function(){
			$('#isFileChange').val(abc.consts.BOOLEAN_TRUE);

			var fileId = '#' + this.id;
			var imageName = $(fileId)[0].files[0].name;

			if(!_appPush.validateFile(fileId, _appPush.checkExtStr, _appPush.maxSize)){
				return false;
			}

			var html  = 	'<li id="dispImgNameLi">';
				html += 		'<input type="hidden" name="imageName" value="' + imageName + '">';
				html += 		'<span class="subject" id="imageName">' + imageName + '</span>';
				html += 		'<button type="button" name="removeImageBtn" class="btn-file-del" id="removeImageBtn">';
				html +=				'<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>';
				html +=			'</button>';
				html += 	'</li>';

			$('#dispImgNameLi').remove();
			$('#ul-file-list').append(html);
			_appPush.preViewImage(this);
			$('#imageLi').addClass('hidden');
		});


		// 저장 클릭시
		$('#saveAppPushBtn').off().on('click', function(){
			_appPush.doActionAppPush('save');
		});

		// 목록 클릭시
		$('#appPushListBtn').off().on('click', function(){
			appPushForm.action = '/cmm/msg/apppush';
			appPushForm.submit();
		});

		// 삭제 클릭시
		$('#deleteAppPushBtn').off().on('click', function(){
			_appPush.doActionAppPush('delete');
		});

		// 발송관리 클릭시
		$('#sendManageBtn').off().on('click', function() {
			var url = '/cmm/msg/apppush/send-managemnet';
			_params = {};
			_params.pushMesgNo = $('#pushMesgNo').val();

			abc.open.popup({
				url 	:	url,
				winname :	'app-push-send-management',
				title 	:	'sendMangement',
				width 	:	1600,
				height	:	900,
				params : _params
			});

			return false;
		});


		// 첨부파일 삭제버튼
		$(document).off('click', '#removeImageBtn').on('click', '#removeImageBtn', function(){
			$('#imageFile').val('');
			$('#dispImage').prop('src', '').hide();
			$('#imageLi').removeClass('hidden');
			$(this).parent().remove();
			$('#isFileChange').val(abc.consts.BOOLEAN_TRUE);
		});

		if(abc.text.allNull($("#pushMesgNo").val())){
			$("input:radio[name='siteNo'][value='10000']").prop('checked', true);
		}

		if(!abc.text.isBlank($('input:hidden[name=imageName]').val())) {
			$('#imageLi').addClass('hidden');
		}

		_appPush.checkExtStr = abc.consts.MEMBER_COUNSEL_FILE_EXT;
		_appPush.maxSize = abc.consts.MEMBER_COUNSEL_FILE_MAX_MB;

		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
	};


	/**
	 *  첨부파일 validate 메소드
	 */
	_appPush.validateFile = function(fileId, checkExtStr, maxSize) {

		//파일정보
		var fileObj= $(fileId)[0].files[0];
		var img_all_names, ext, size;
		var isSizeValid = false;

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

		return true;
	};

	// 이미지 사이즈 체크 콜백 (640 x 320)
	_appPush.imageSizeValidation = function(fileId, width, height, result) {
		if(width > 640 || height > 320) {
			alert('이미지 사이즈를 확인해주세요.(640x320)');
			$('#removeImageBtn').trigger('click');
		}else {
			$('#dispImage').prop('src', result).show();
		}
	}

	// 이미지 미리보기.
	_appPush.preViewImage = function(input, imgId) {
		if(input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e){
				var img = new Image();
				img.onload = function() {
					// 사이즈 체크
					_appPush.imageSizeValidation('#'+input.id, this.width, this.height, e.target.result);

				}
				img.src = e.target.result;
			}
			reader.readAsDataURL(input.files[0]);
		}
	};

	// app push 메시지 등록 validation
	_appPush.saveValidation = function() {
		var pushTitleText	= $('#pushTitleText');
		var contText 		= $('#contText');
		var trgtUrl  		= $('#trgtUrl');

		if(abc.text.isBlank(pushTitleText.val())) {
			alert('메시지 제목을 입력해주세요.');
			pushTitleText.focus();
			return false;
		}

		if (abc.text.isLimitLength(pushTitleText.val(), 100)) {
			alert('메시지 제목은 100Byte 까지 입력 가능 합니다.');
			pushTitleText.focus();
			return false;
		}

		if(abc.text.isBlank(contText.val())) {
			alert('내용을 입력 해주세요.');
			contText.focus();
			return false;
		}

		if (abc.text.isLimitLength(contText.val(), 500)) {
			alert('메시지 내용은 500Byte 까지 입력 가능 합니다.');
			contText.focus();
			return false;
		}

		if(abc.text.isBlank(trgtUrl.val())) {
			alert('Target Url을 입력 해주세요.');
			trgtUrl.focus();
			return false;
		}

		if (abc.text.isLimitLength(trgtUrl.val(), 200)) {
			alert('Target URL은 200Byte 까지 입력 가능 합니다.');
			trgtUrl.focus();
			return false;
		}

		return true;
	};

	// 템플릿 문자 내용 길이 체크
	_appPush.contTextLengthCheck = function (str) {
		var strByteLength = str.uniLength();

		$("#mesgContTextByteLength").val(strByteLength);

		if (strByteLength > 500) {
			alert("500Byte 까지만 입력 가능 합니다.");
			$("#contText").val(abc.text.substringByte(str, 500));
			$("#mesgContTextByteLength").val($("#mesgContText").val().uniLength());
		}
	}


})();