/**
 * 파일명:        abc.front.js
 * 설  명:        공통 자바스크립트
 * 작성자:        glim
 * 최초작성일:    2018/11/30
 * 최종수정일 :
 * Comment
 * ABC 마트 2.0 백오피스 퍼블리싱 스크립트.
 *
 */

var abc = abc || {};
abc.namespace = abc.namespace || {};
abc.namespace.front = abc.namespace.front || {};

abc.namespace.front.backOffice = abc.namespace.front.backOffice || (function () {
	var _front;
	_front = {};

	/**
	 * 가로스크롤 생성 시 position Fixed인 LNB, Header의 margin-left 계산
	 */
	function calcFixedPositionScrollMargin() {
		var sl = -$(window).scrollLeft();
		$('.js-lnb-container').css('margin-left', sl);
		$('.js-header-container').css('margin-left', sl);
		$('.js-btn-lnb-toggle').css('margin-left', sl);
	}

	/**
	 * lnb 메뉴 리스트 클릭 이벤트 설정
	 */
	function setLNBMenu() {
		if ($('.js-lnb-wrap').length > 0) {
			$('.js-lnb-wrap .lnb li > a').on('click', function (event) {
				toggleLNBMenu(event.currentTarget);
			});

			$('.js-btn-lnb-toggle').on('click', function (event) {
				toggleLNBZone(event.currentTarget);
			});
		}
	}

	/**
	 * lnb 메뉴 리스트 toggle
	 * @param {HTMLElement} target - 클릭 된 1뎁스 메뉴
	 */
	function toggleLNBMenu(target) {
		var container = $(target).parent();
		container.toggleClass('active');
	}

	/**
	 * lnb 영역 toggle
	 * @param {HTMLElement} target - LNB 메뉴 열기/닫기 버튼
	 */
	function toggleLNBZone(target) {
		if ($('.wrap').hasClass('lnb-deactive') === true) {
			$('.wrap').removeClass('lnb-deactive');
			$(target).find('.offscreen').text('메뉴닫기');
		} else {
			$('.wrap').addClass('lnb-deactive');
			$(target).find('.offscreen').text('메뉴열기');
		}
	}


	/**
	 * 즐겨찾기 토글 버튼 이벤트 설정
	 */
	function setFavoriteBtn() {
		// abc.global 선언 여부로 개발기 아닌 경우에만 실행
		if (abc.global === undefined) {
			$('.btn-favorites').on('click', function (event) {
				toggleFavoriteBtn(event.currentTarget);
			});
		}
	}

	/**
	 * 즐겨찾기 토글 버튼 상태 설정
	 * @param {HTMLElement} target - 선택 된 즐겨찾기 버튼
	 */
	function toggleFavoriteBtn(target) {
		if ($(target).hasClass('active') === true) {
			$(target).removeClass('active');
			$(target).find('.offscreen').text('즐겨찾기 등록');
		} else {
			$(target).addClass('active');
			$(target).find('.offscreen').text('즐겨찾기 해제');
		}
	}

	/**
	 * Search box 영역 열기/닫기 버튼 이벤트 설정
	 * @param selector {text} 검색영역 여/닫기 토글 버튼 선택자(default: .btn-search-toggle)
	 */
	function setSearchZoneViewToggleBtn(selector) {
		selector = selector || '.btn-search-toggle';
		$(selector).on('click', function (event) {
			toggleSearchZone(event.currentTarget);
		});
	}

	/**
	 * Search box 영역 열기/닫기
	 * @param {HTMLElement} target - 검색영역 토글 버튼
	 */

	var preResizeGridHeight;

	function toggleSearchZone(target) {
		var container;
		container = $(target).closest('.search-wrap');
		searchWrapHeight = container.height();
		if ($(container).hasClass('active') === true) {
			$(container).removeClass('active');
			$(container).find('.offscreen').text('열기');
			resizeGridHeight(preResizeGridHeight);
		} else {
			$(container).addClass('active');
			$(container).find('.offscreen').text('닫기');
			resizeGridHeight(searchWrapHeight);
		}
	}

	/**
	 * Search box 영역 열기/닫기에 따른 그리드 div 높이 조절
	 * @param {int} resize - 검색영역 height
	 */
	function resizeGridHeight(resize) {
		if (resize > 0) preResizeGridHeight = -(resize);
		var height = $(".ibsheet-wrap").children("div [id]").height();
		$(".ibsheet-wrap").children("div").height(height + resize);
	}

	/**
	 * Search box 추가 검색영역 열기/닫기 버튼 이벤트 설정
	 * @param selector {text} 검색영역 여/닫기 토글 버튼 선택자(default: .btn-addition-toggle)
	 */
	function setSearchAdditionViewToggleBtn(selector) {
		selector = selector || '.btn-addition-toggle';
		$(selector).on('click', function (event) {
			toggleSearchAddition(this);
		});
	}

	/**
	 * Search box 추가 검색영역 열기/닫기
	 * @param {HTMLElement} target - 추가 검색영역 토글 버튼
	 */
	function toggleSearchAddition(target) {
		var container;
		container = $(target).closest('.addition-btn-wrap');
		if ($(container).hasClass('active') === true) {
			$(container).removeClass('active');
			$(target).find('.offscreen').text('열기');
			$(target).find('.fa').removeClass('fa-caret-up').addClass('fa-caret-down');
		} else {
			$(container).addClass('active');
			$(target).find('.offscreen').text('닫기');
			$(target).find('.fa').removeClass('fa-caret-down').addClass('fa-caret-up');
		}
	}

	/**
	 * jQuery UI 탭 설정
	 */
	function setTabs() {
		if ($('.tab-wrap:not(.anchor-tab)').length > 0) {
			$('.tab-wrap:not(.anchor-tab)').each(function (index) {
				var disabledTabs;
				disabledTabs = [];
				$(this).find('.tab-link').each(function (index) {
					if ($(this).hasClass('tab-disabled')) {
						disabledTabs.push(index);
					}
				});

				$(this).tabs({
					disabled: disabledTabs
				});
			})
		}

		if ($('.tab-wrap.anchor-tab').length > 0) {
			$('.tab-wrap.anchor-tab').each(function (index) {
				$(this).find('.tab-link').on('click', function (event) {
					var scrollOffset, scrollTop, scrollTarget, isWrapScroll;
					scrollOffset = 0;
					scrollTarget = $(window);
					isWrapScroll = $(event.currentTarget).closest('.anchor-tab-wrap').length > 0;

					event.preventDefault();

					if (isWrapScroll) {
						scrollTarget = $(event.currentTarget).closest('.anchor-tab-wrap');
						scrollOffset = scrollTarget.offset().top;
						scrollTarget.scrollTop(0);
					} else {
						// 윈도우 팝업
						if ($('body').hasClass('window-body')) {
							scrollOffset += parseInt($('.window-content').css('padding-top'));
						} else {
							scrollOffset += $('.header-container').outerHeight();
							scrollOffset += parseInt($('.content-box').css('padding-top'));
						}
					}
					scrollTop = $($(event.currentTarget).attr('href')).offset().top - scrollOffset;

					scrollTarget.scrollTop(scrollTop);
				});
			});
		}
	}

	/**
	 * 특정 버튼, 링크 등에 대한 클릭 이벤트 preventDefault 처리
	 */
	function setPreventBtnEvents() {
		var btnList;
		btnList = [
			'.disabled',			// 비활성화 된 버튼
			'.js-btn',				// 스크립트 핸들링 버튼
			'.btn-tree-toggle',		// 트리 체크 메뉴 확장버튼
			'.js-treemap-list.no-check .menu-name'	// 트리 메뉴 메뉴명
		];
		$(btnList.join(', ')).on('click', function (event) {
			event.preventDefault();
		});
	}

	/**
	 * 테이블 캡션 생성
	 */
	function setTableCaption() {
		$('table[class*="tbl-col"], table[class*="tbl-row"], table[class*="tbl-search"]').each(function (index) {
			var table, tableClass, captionText, captionComplex, theadHeader, tbodyHeader, bodyToHeadIdxs, hasThead,
				captionSubFix;
			table = $(this);
			tableClass = $(this).attr('class');
			captionTextOrigin = $(this).find('caption').text();
			captionComplex = '';
			captionSubFix = '';
			theadHeader = [];
			tbodyHeader = [];
			bodyToHeadIdxs = [];
			hasThead = false;
			// 180123 수정 : match 값 수정
			if (tableClass.match('-input')) {
				// console.log('has _ip!!');
				captionSubFix = '을(를) 입력하는 표입니다.';
			} else if (tableClass.match('-search')) {
				captionSubFix = '에 대해 검색합니다.';
			} else {
				// console.log('no _ip!!');
				captionSubFix = '을(를) 나타낸 표입니다.';
			}


			// thead th값 추출
			if ($(this).find('thead th').length > 0) {
				$(this).find('thead th').each(function (index) {
					theadHeader.push($(this).text());
				});
			}
			// tbody th값 추출
			if ($(this).find('tbody th').length > 0) {
				$(this).find('tbody th').each(function (index) {
					// tbody th가 thead th의 서브 헤더인 경우(thead th와 tbody th가 둘 다 존재하는 경우)
					if (theadHeader.length > 0) {
						if (tbodyHeader[$(this).index()] === undefined) {
							tbodyHeader[$(this).index()] = theadHeader[$(this).index()] + ' 컬럼의 하위로';
						}
						tbodyHeader[$(this).index()] += ' ' + $(this).text();
					} else {
						tbodyHeader.push($(this).text());
					}
				});

				tbodyHeader = tbodyHeader.filter(function (n) {
					return n !== undefined
				});
			}
			// console.log(theadHeader);
			// console.log(tbodyHeader);

			if (theadHeader.length > 0 && tbodyHeader.length > 0) {
				captionComplex += theadHeader.join(', ') + ' ' + tbodyHeader.join(', ');
			} else if (theadHeader.length > 0) {
				captionComplex += theadHeader.join(', ');
			} else if (tbodyHeader.length > 0) {
				captionComplex += tbodyHeader.join(', ');
			}

			//console.log(captionTextOrigin + ' 목록이며 ' + captionComplex +  ' 을(를) 나타낸 표입니다.');
			$(this).find('caption').text(captionTextOrigin + ' 테이블이며 ' + captionComplex + captionSubFix);
		});
	}

	/**
	 * 이메일 입력 폼 도메인 Select 변경 컨트롤
	 */
	function setMailDomainSelect() {
		$('.domain-sel').each(function (index) {
			changeDomainSelected(this);
		});
		// $('.email-ip .mail-domain').prop('disabled', true);

		$('.domain-sel').on('change', function (event) {
			changeDomainSelected(this);
		});
	}

	function changeDomainSelected(target) {
		var mailDomainInput;
		mailDomainInput = $(target).prev('.email-ip').find('.mail-domain');
		if (mailDomainInput.length > 0) {
			mailDomainInput.prop('disabled', ($(target).val() !== 'manual'));

			if ($(target).val() !== 'manual') {
				mailDomainInput.val($(target).val());
			}
		}
	}

	/**
	 * 윈도우 팝업 생성
	 * @param {string} url - 윈도우 팝업으로 노출 될 화면 URL
	 * @param {number} customWidth - 윈도우 팝업 넓이값(default: screen.availWidth - 10)
	 * @param {number} customHeight - 윈도우 팝업 높이값(default: screen.availHeight - 60)
	 */
	function showWindowPopup(url, customWidth, customHeight) {
		var popWidth, popHeight, options;
		popWidth = screen.availWidth - 10;
		popHeight = screen.availHeight - 60;
		if (customWidth !== undefined && customWidth !== null && !isNaN(customWidth) && customWidth > 0) {
			popWidth = customWidth;
		}
		if (customHeight !== undefined && customHeight !== null && !isNaN(customHeight) && customHeight > 0) {
			popHeight = customHeight;
		}
		options = 'resizable=yes,scrollbars=yes,status=no,left=0,top=0,width=' + popWidth + ', height=' + popHeight;
		window.open(url, '', options);
	}

	/**
	 * 레이어 팝업 설정 (jquery UI Dialog)
	 * @param selector {string} 레이어 팝업으로 생성할 컨테이너 셀렉터(default: .ui-dialog-contents)
	 * @param btnSelector {string} 레이어 팝업을 띄우기 위한 버튼 셀렉터(default: .btn-dialog)
	 */
	function setUIDialog(selector, btnSelector) {
		selector = selector || '.ui-dialog-contents';
		btnSelector = btnSelector || '.btn-dialog';
		if ($(selector).length > 0) {
			if ($('body .ui-dialog-container').length === 0) {
				$('body').append('<div id="dialogContainer" class="ui-dialog-container"><div class="ui-dialog-container-inner"></div></div>');
			}
			$(selector).each(function (index) {
				var sizeClass, isShowClose;
				sizeClass = '';
				isShowClose = true;
				if ($(this).data('size') !== undefined) {
					sizeClass = ' ' + $(this).data('size');
				}
				if ($(this).data('showClose') !== undefined) {
					console.log($(this).data('showClose'));
					isShowClose = $(this).data('showClose');
				}
				$(this).dialog({
					appendTo: '.ui-dialog-container-inner',
					autoOpen: false,
					modal: true,
					dialogClass: isShowClose ? '' : 'hide-close',
					resizable: false,
					draggable: false,
					width: 'auto',
					minHeight: 'none',
					classes: {
						'ui-dialog': 'ui-corner-all' + sizeClass,
					},
					position: null,
					open: function (event, ui) {
						console.log('open');
						$('body').addClass('dialog-open');
					},
					close: function (event, ui) {
						// console.log('close');
						$('body').removeClass('dialog-open');
					}
				});
			});
		}

		if ($(btnSelector).length > 0) {
			$(btnSelector).each(function (index) {
				$(this).on('click', function () {
					$($(this).data('target')).dialog('open');
				});
			});
		}
	}

	/**
	 * 퍼블리싱 페이지에 IBSheet 적용
	 */
	function setIBSheetTemplate() {
		$('.ibsheet-wrap:not(.manual)').each(function (index) {
			var gridID, dummyDivStyles;
			gridID = 'gridID' + index;
			dummyDivStyles = $(this).find('> div').eq(0).css({'background': ''}).attr('style');
			$(this).empty();
			$(this).append('<div id="' + gridID + '" style="' + dummyDivStyles + '"></div>');

			createIBSheet2(document.getElementById(gridID), 'mySheet' + index);

			var initSheet = {};
			// NOTE: AutoFitColWidth : 컬럼 넓이 자동 처리 옵션값. init : 생성될 때 사이즈로 고정, resize: 화면 리사이즈에 대응함
			initSheet.Cfg = {SearchMode: smServerPaging2, Page: 10, DeferredVScroll: 1, AutoFitColWidth: 'init'};
			initSheet.HeaderMode = {Sort: 1, ColMove: 0, ColResize: 1};
			initSheet.Cols = [
				{Header: '상태', Type: 'Status', SaveName: 'status', Width: 0, Align: '', Edit: 0}
				, {Header: '정렬순서', Type: 'Int', SaveName: 'sortSeq', Width: 20, Align: 'Center', Edit: 0}
				, {Header: '코드그룹번호', Type: 'Text', SaveName: 'codeField', Width: 20, Align: 'Center', Edit: 0}
				, {Header: '코드그룹명', Type: 'Text', SaveName: 'codeName', Width: 15, Align: 'Center', Edit: 0}
				, {Header: '그룹구분', Type: 'Text', SaveName: 'systemCodeYn', Width: 30, Align: 'Center', Edit: 0}
			];

			IBS_InitSheet(window['mySheet' + index], initSheet);
			window['mySheet' + index].SetCountPosition(3);
			window['mySheet' + index].SetPagingPosition(2);
			window['mySheet' + index].FitColWidth();
			window['mySheet' + index].FitColWidth();
		});
	}

	/**
	 * selectize.js 플러그인 설정
	 * @param {string|HTMLElement} selector Search Dropdown으로 변경할 select 선택자
	 * @param {string} labelField 화면에 노출 될 텍스트 필드 명
	 * @param {string} valueField 선택 된 옵션의 값
	 * @param {function} serverQuery 서버 값을 가져올 함수 인자값은 query(컴포넌트에서 입력된 텍스트 값), callback(조회 결과값을 전달할 callback 함수)
	 * @param {boolean} preload 최초 생성시 serverQuery 함수를 실행할 것인지 여부. 기본 false.
	 * @returns {*|jQuery} 생성된 selectize 객체
	 */
	function setSearchDropdown(selector, labelField, valueField, serverQuery, preload) {
		var selectObject;
		preload = preload || false;

		selectObject = $(selector).selectize({
			labelField: labelField,
			valueField: valueField,
			searchField: labelField,
			placeholder: $(selector).data('placeholder'),
			preload: preload,
			load: function (query, callback) {
				serverQuery(query, callback);
			}
		});
		return selectObject;
	}

	function searchDummyOption(query, callback) {
		$.ajax({
			url: '/html/guide/dummy_dropdown_data.json',
			type: 'GET',
			dataType: 'json',
			error: function () {
			},
			success: function (res) {
				callback(res.data);
			}
		});
	}

	/**
	 * star-rating 플러그인 설정
	 * @param selector {string} StarRating으로 변경할 input 선택자(default: .ip-rating)
	 */
	function setStarRating(selector) {
		selector = selector || '.ip-rating';

		$(selector).rating({
			language: 'ko',
			showCaption: false,
			showClear: false,
			min: 0,
			max: 5,
			step: 1,
		});
	}

	/**
	 *
	 * @param selector {string} Datepicker로 변경할 input 선택자(default: .js-ui-cal)
	 */
	function setDatepicker(selector) {
		selector = selector || '.js-ui-cal';

		$(selector).on('input', function (event) {
			this.value = this.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
		});
		$(selector).datepicker({
			onClose: function(dateText, inst) {
				// 20190908 형태의 날짜 입력에 대한 대응
				if(dateText && dateText.length === 8) {
					$(this).datepicker('setDate', new Date(dateText.substring(0, 4), dateText.substring(4, 6) - 1, dateText.substring(6, 8)));
				}
				// onClose 시 날짜형식을 유지하도록 하는 트릭
				$(this).datepicker('option', 'dateFormat', $(this).datepicker('option', 'dateFormat'));
			},
		});
	}

	function showLoader() {
		if ($('.loading-layer').length < 1) {
			$('body').append(
				'<div class="loading-layer">'
				+ '	<div class="img-wrap">'
				+ '		<svg class="svg-loader" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="60px" height="60px" viewBox="0 0 60 60">'
				+ '			<rect class="svg-loader-bg" x="7" y="7" rx="23" ry="23" width="46" height="46" stroke-width="13" />'
				+ '			<rect class="svg-loader-stroke" x="7" y="7" rx="23" ry="23" width="46" height="46" stroke-width="14" />'
				+ '		</svg>'
				+ '	</div>'
				+ '</div>'
			);
		}
		$('.loading-layer').removeClass('hide');
	}

	function hideLoader() {
		$('.loading-layer').addClass('hide');
	}

	// public 함수 선언
	_front.showWindowPopup = showWindowPopup;
	_front.setUIDialog = setUIDialog;
	_front.setSearchZoneViewToggleBtn = setSearchZoneViewToggleBtn;
	_front.setSearchAdditionViewToggleBtn = setSearchAdditionViewToggleBtn;
	_front.setSearchDropdown = setSearchDropdown;
	_front.setStarRating = setStarRating;
	_front.setDatepicker = setDatepicker;
	_front.showLoader = showLoader;
	_front.hideLoader = hideLoader;

	$(document).ready(function () {
		//캘린더 초기화
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd',
			monthNames: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			dayNamesMin: ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'],
			showOn: 'both',
			buttonImage: '/static/images/ui/ui_icon_calendar.png',
			buttonImageOnly: false,
			currentText: 'Now',
			changeYear: true,
			changeMonth: true,
			showMonthAfterYear: true,
			showOtherMonths: true,
			yearRange: 'c-5:c+5',
			dateFormat: 'yy.mm.dd',
			beforeShow: function () {
				if ($(this).prop('disabled') === true) {
					return false;
				}
				//disable add
				$(this).siblings('.ui-datepicker-trigger').css('background-position', '-14px 0');
			},
			onClose: function () {
				$(this).siblings('.ui-datepicker-trigger').css('background-position', '0 0');
			}
		});

		if ($('.treemap-list').length > 0) {
			$('.treemap-list li').each(function (n) {
				$(this).find('> .row > a.btn-tree-toggle').on('click', function (event) {
					var targetLI, btnIcon;
					targetLI = $(this).closest('li'); //li
					btnIcon = $(this).find('> i'); // icon

					if (targetLI.hasClass('hide') === true) { //트리맵 아이콘 열렸을때
						targetLI.removeClass('hide');
						btnIcon.find('.offscreen').text('펼치기');

					} else { //닫혔을 때
						targetLI.addClass('hide');
						btnIcon.find('.offscreen').text('닫기');
					}
				});
			});
		}

		$(window).on('scroll', function (event) {
			if ($(window).width() < 1280) {
				calcFixedPositionScrollMargin();
			}
		});

		$(window).on('resize', function (event) {
			if ($(window).width() < 1280) {
				calcFixedPositionScrollMargin();
			}
		});


		if ($(window).width() < 1280) {
			calcFixedPositionScrollMargin();
		}

		setLNBMenu();
		setFavoriteBtn();
		setSearchZoneViewToggleBtn();
		setSearchAdditionViewToggleBtn();
		setTabs();
		setPreventBtnEvents();
		setTableCaption();
		setMailDomainSelect();
		setUIDialog();
		setStarRating();
		setDatepicker();

		// abc.global 선언 여부로 개발기 아닌 경우에만 실행
		if (abc.global === undefined) {
			setIBSheetTemplate();

			$('.search-dropdown').each(function (index) {
				$(this).addClass('ui-sel');
				$(this).append('<option>Search Dropdown</option>');
				return;
				setSearchDropdown(this, 'label', 'value', searchDummyOption, true);
			});
		}
	});

	return _front;
})();
