	var userMenuList = {};
	var param = {};
	var MouseEventObj = new Object();

	$(document).ready(function(){
		param = abc.param.getParams();

		if(abc.text.allNull(param.menuNo) && location.pathname.indexOf("/") == -1){
			param = abc.param.getParams(document.referrer);
		}
		userMenu();

	});

	var userMenu = function(){
		$.ajax({
			type :"get"
		  , url: "/noacl/user-menu"
		})
		.done(function(result){
			//GNB, LNB
			userMenuList = result.userMenuList;
			userGnbMenu(userMenuList);

			var menuNoList = [];
			for(var i in userMenuList){
				if(abc.text.allNull(param.menuNo) && !abc.text.allNull(userMenuList[i].menuUrl)){
					if(abc.text.validateStringSignRemove(location.pathname).indexOf(abc.text.validateStringSignRemove(userMenuList[i].menuUrl)) != -1
							&& userMenuList[i].haveChild == 0){
						menuNoList = userMenuList[i].allPathMenuNo.split("^");
					}
				}else{
					if(userMenuList[i].menuNo == param.menuNo){
						menuNoList = userMenuList[i].allPathMenuNo.split("^");
					}
				}
			}

			userLnbMenu(menuNoList[1]);
			userMenuDetail(menuNoList[2]);
			userMenuSubDetail(menuNoList[3]);

			//즐겨찾기
			userFavoriteMenuList(result.favoriteMenuList);
		})
		.fail(function(e){
			console.log("e :" + e);
		});
	}

	/**
	 * GNB
	 */
	var userGnbMenu = function(userMenuList){
		var strHtmlGnb = "";
		if(!abc.text.allNull(userMenuList) && userMenuList.length > 0){
			strHtmlGnb += '<ul class="gnb">';

			for(var i in userMenuList){
				if(userMenuList[i].menuGbnType == 'G'){
					var userMenuObj = userMenuList[i];
					strHtmlGnb += '<li id="gnb_'+userMenuList[i].menuGbnUpMenuNo+'">';
					strHtmlGnb += ' <a href="javascript:void(0);" onclick="userLnbMenu('+userMenuList[i].menuGbnUpMenuNo+');" >';
					strHtmlGnb +=		userMenuList[i].menuName+'</a>';
					strHtmlGnb += '</li>';
				}
			}

			strHtmlGnb += '</ul>';
		}
		$("#gnbWrapArea").html(strHtmlGnb);
	}

	/**
	 * LNB 2Depth
	 */
	var userLnbMenu = function(menuGbnUpMenuNo){
		$(".gnb > li").removeClass("active");
		$("#gnb_"+menuGbnUpMenuNo).addClass("active");

		var strHtmlLnb = '<ul class="lnb">';

		for(var i in userMenuList){
			if(userMenuList[i].menuGbnType == 'M' && userMenuList[i].menuGbnUpMenuNo == menuGbnUpMenuNo){
				var upMenuNo = "";
				var url = "'"+userMenuList[i].menuUrl+"'";
				upMenuNo = userMenuList[i].menuNo;

				if(userMenuList[i].haveChild > 0){
					strHtmlLnb += '<li id="lnb_'+userMenuList[i].menuNo+'">';
				}else{
					strHtmlLnb += '<li id="lnb_'+userMenuList[i].menuNo+'" class="lnb-sub-none">';
				}

				strHtmlLnb += ' <a href="javascript:void(0);" onclick="userMenuDetail('+userMenuList[i].menuNo+','+ url +','+userMenuList[i].haveChild+');" >';
				strHtmlLnb +=		userMenuList[i].menuName+'</a>';

				if(userMenuList[i].haveChild > 0){
					strHtmlLnb += '		<ul class="lnb-sub">';
					for(var j in userMenuList){
						if(userMenuList[j].menuGbnType == 'S' && userMenuList[j].upMenuNo == upMenuNo){
							var subUrl = "'"+userMenuList[j].menuUrl+"'";
							strHtmlLnb += '			<li id="lnbSub_'+userMenuList[j].menuNo+'">';
							strHtmlLnb += '				<a href="javascript:void(0);" onclick="userMenuSubDetail('+userMenuList[j].menuNo+', '+ subUrl +');" >';
							strHtmlLnb +=					userMenuList[j].menuName+'</a>';
							strHtmlLnb += '			</li>';
						}
					}
					strHtmlLnb += '		</ul>';
				}

				strHtmlLnb += '</li>';
			}
		}

		strHtmlLnb += '</ul>';
		$("#lnbWrapArea").html(strHtmlLnb);


	}

	/**
	 * LNB 3Depth
	 */
	var userMenuDetail= function(menuNo, menuUrl, haveChild){
		if(typeof menuNo != "undefined" && menuNo != ''){
			$(".lnb > li").removeClass("active");
			$("#lnb_"+menuNo).addClass("active");
			if(typeof menuUrl != "undefined" && menuUrl != '' && haveChild == 0){
				menuUrl = menuUrl+"?menuNo="+menuNo;
				$(location).attr('href', menuUrl);
			}
		}
	}
	/**
	 * LNB 4Depth
	 */
	var userMenuSubDetail = function(menuNo, menuUrl){
		if(typeof menuNo != "undefined" && menuNo != ''){
			$(".lnb-sub > li").removeClass("active");
			$("#lnbSub_"+menuNo).addClass("active");
			if(typeof menuUrl != "undefined" && menuUrl != ''){
				localStorage.removeItem('param');
				localStorage.removeItem('listPageNum');
				localStorage.removeItem('detailMove');
				
				menuUrl = menuUrl+"?menuNo="+menuNo;
				$(location).attr('href', menuUrl);
			}
		}
	}

	/**
	 * LNB 즐겨찾기 바로가기
	 */
	var userFavoriteMenuDetail = function(menuUrl, MenuNo1Depth, tMenuNo, mMenuNo, sMenuNo){
		if(typeof menuUrl != "undefined" && menuUrl != ''){
			if(typeof sMenuNo != "undefined" && sMenuNo != ''){
				menuUrl = menuUrl+"?menuNo="+sMenuNo;
			}else{
				menuUrl = menuUrl+"?menuNo="+mMenuNo;
			}
			$(location).attr('href', menuUrl);
		}
	}

	/**
	 * LNB 즐겨찾기 삭제
	 */
	var delUserFavoriteMenu = function(menuNo){
		if(confirm("즐겨찾기를 삭제하시겠습니까?") == false){
			return;
		}
		var favoritesParam = {};
		favoritesParam.menuNo = menuNo;
		favoritesParam.status = abc.consts.CRUD_D;
		favoritesMenuUpdate(favoritesParam);
	}

	/**
	 * LNB 즐겨찾기 목록
	 */
	var userFavoriteMenuList = function(favoriteMenuList){
		var strHtml = "";
		$(".btn-favorites").removeClass("active");
		strHtml += '<ul class="favorites-list">';
		if(!abc.text.allNull(favoriteMenuList) && favoriteMenuList.length > 0){
			for(var i in favoriteMenuList){
				var menuUrl = "'"+favoriteMenuList[i].menuUrl+"'";
				if(favoriteMenuList[i].menuNo == param.menuNo){
					$(".btn-favorites").addClass("active");
				}
				strHtml += '<li>';
				strHtml += '	<a href="javascript:void(0);" onclick="userFavoriteMenuDetail('+ menuUrl +', '+ favoriteMenuList[i].allPathMenuNo.split("^") +');" >';
				strHtml +=			favoriteMenuList[i].menuName+'</a>';
				strHtml += '	<a href="javascript:void(0);"  class="btn-favorite-del" onclick="delUserFavoriteMenu('+favoriteMenuList[i].menuNo +');" >';
				strHtml += '	<i class="ico ico-del"><span class="offscreen">즐겨찾기 삭제</span></i></a>';
				strHtml += '</li>';
			}
		}else{
			strHtml += '<li><a>즐겨찾기 메뉴가 없습니다</a></li>';
		}
		strHtml += '</ul>';
		$("#favoritesWrapArea").html(strHtml);
	}

	/**
	 * 즐겨찾기 등록/삭제
	 */
	var favoritesMenuUpdate = function(favoritesParam){
		var url = "";
		if(favoritesParam.status == abc.consts.CRUD_D){
			//즐겨찾기 삭제
			url = "/noacl/favorites-menu/remove"
		}else{
			//즐겨찾기 추가
			url = "/noacl/favorites-menu/create"
		}

		$.ajax({
			type :"post",
			url : url,
			data: favoritesParam
		})
		.done(function(data){
			alert(data.resultMsg);
			userFavoriteMenuList(data.favoriteMenuList);
		})
		.fail(function(e){
			console.log("e :" + e);
		});
	}

	/**
	 * GNB 메뉴에 있는 사용자명 클릭 시 관리자 팝업 활성화
	 */
	var useInfoDetailView = function(adminNo){
		var url = "/noacl/admin-detail-pop";
		var params = {}
		params.adminNo = adminNo;

		abc.open.popup({
			url		:	url,
			width	:	645,
			height	:	840,
			params	:	params
		});
	}

	window.onload = function () {
		/**
		 * 즐겨찾기 등록/삭제
		 */
		$(".btn-favorites").click(function(e){
			var favoritesParam = {};
			favoritesParam.menuNo = param.menuNo;

			var url = "";

			if($(this).hasClass('active')){
				//즐겨찾기 삭제
				if(confirm("즐겨찾기를 삭제하시겠습니까?") == false){
					$(this).addClass('active');
					e.stopPropagation();
					return;
				}
				$(this).removeClass('active');
				$(this).find('.offscreen').text('즐겨찾기 해제');
				favoritesParam.status = abc.consts.CRUD_D;
			}else{
				//즐겨찾기 추가
				if(confirm("즐겨찾기를 추가하시겠습니까?") == false){
					$(this).removeClass('active');
					e.stopPropagation();
					return;
				}
				$(this).addClass('active');
				$(this).find('.offscreen').text('즐겨찾기 등록');
				favoritesParam.status = abc.consts.CRUD_C;
			}
			favoritesMenuUpdate(favoritesParam);
		});

		//홈버튼 클릭시 메인화면 이동
		$(".navi > .home > a").click(function(e){
			$(location).attr('href', "/");
		});
	}