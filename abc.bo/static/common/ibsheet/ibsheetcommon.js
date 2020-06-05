//ibsheet 관련 공통함수

/*
 * 공통 조회 함수
 * paramlist (json object)
 * json 변수명 (유형) : 설명
 * sheet (Array[sheetname1,sheetname2,....]) : 시트 이름 (여러개 인 경우 모두 넣을 것)
 * url (String): 조회 url
 * append (boolean) : 기존 데이터에 신규데이터를 append할지 여부
 * sync (boolean): sync 여부
 * subparam (String): 조회 조건
 */
function DataSearch(param){
	var isLogin = isLoginCheck();

	if(isLogin){	// 로그인이 되어 있을 때에는 그리드 데이터를 search
		if(param.sheet!=null){
			var opt = {};
			var qstring = "";
			//단일 시트에 대한 조회
			if(typeof(param.sheet)=="string"||param.sheet.length==1){
				//해당 폼에서 조회조건 추출
				if(param.subparam){
					qstring = param.subparam;
				}
				//조회방식 동기/비동기 여부 (default : 비동기)
				if(param.sync){
					opt["Sync"] = param.sync;
				}

				//기존데이터에 append 여부 (default: false)
				if(param.append){
					opt["Append"] = param.append;
				}
				//시트객체
				var sheetid=param.sheet;
				//시트 조회
				window[sheetid].DoSearch(param.url,qstring,opt);

			}else{
				//여러개 시트 동시 조회
				//해당 폼에서 조회조건 추출
				if(param.subparam){
					qstring = param.subparam;
				}
				//시트 조회

				//맨 앞에 시트를 통해 ajax 통신
				var jsonString = window[param.sheet[0]].GetSearchData(param.url,qstring);
				//돌아온 결과를 json 객체로 바꾼다.
				var jsonObj = JSON.parse(jsonString);

				//각 시트에 조회된 데이터를 순차적으로 로딩한다.
				for(var i=0;i<param.sheet.length;i++){
					window[param.sheet[i]].LoadSearchData(  jsonObj[param.sheet[i]] );
				}
			}
		}else if(param.chart!=null){
			window[param.chart].DoSearch(param.url,{"Param":param.subparam});
			window[param.chart].Draw();
		}
		
		SearchEndCallBack(param);
	}else{
		alert('접근권한이 없습니다.');
		$(location).attr('href', "/");
	}
}

/*
 * 공통 조회 함수
 * paramlist (json object)
 * json 변수명 (유형) : 설명
 * sheet (Array[sheetname1,sheetname2,....]) : 시트 이름
 * onePageRow(Int) : 한번에 조회해 오는 개수 (default:100)
 * url (String): 조회 url
 * sync (boolean) : sync 여부(default async)
 * useDoSearchPaging (boolean) : DoSearchPaging()함수 사용여부 (default true)
 * page (Int) : 조회할 페이지
 * subparam (String): 조회 조건
 */
function DataSearchPaging(param){
	var isLogin = isLoginCheck();

	if(isLogin){	// 로그인이 되어 있을 때에는 그리드 데이터를 search
		var searchCondition = {"Param":""};

		var qstring = "";
		//단일 시트에 대한 조회

		//해당 폼에서 조회조건 추출
		if(param.subparam){
			searchCondition["Param"]= param.subparam;
		}

		if (param.sheet == null) {
			alert("검색결과를 적용할 Sheet명을 입력하세요.");
			return false;
		}

		//Sheet 목록 개수 재 적용
		if(typeof(param.sheet)=="string"){
			window[param.sheet].SetPageCount(param.onePageRow);
		}else{
			window[param.sheet[0]].SetPageCount(param.onePageRow);
		}

		if(param.onePageRow){
			searchCondition["Param"] = searchCondition["Param"]+"&onepagerow="+param.onePageRow;
		}else{
			searchCondition["Param"] = searchCondition["Param"]+"&onepagerow=100";
		}

		if(param.sync){
			searchCondition["Sync"] =param.sync;
		}

		searchCondition["UseWaitImage"] = 1;
		
		if(param.CPage){
			searchCondition["CPage"] = param.CPage;	// 그리드의 현재 페이지 번호를 세팅
		}

		//시트 조회
		if(typeof(param.sheet)=="string" || param.sheet.length==1){
			window[param.sheet].DoSearchPaging(param.url,searchCondition);
		}else{
			window[param.sheet[0]].DoSearchPaging(param.url,searchCondition);
		}
		
		SearchEndCallBack(param);
	}else{
		alert('접근권한이 없습니다.');
		$(location).attr('href', "/");
	}
}

/*
 * 공통 저장 함수
 * paramlist (json object)
 * json 변수명 (유형) : 설명
 * sheet  (Array[sheetname1,sheetname2,....]) : 시트 이름 (여러개 인 경우 모두 넣을 것)
 * url (String): 조회 url
 * subparam (String): 조회 조건
 * sync (boolean) : sync 여부
 * quest (boolean) : 저장하시겠습니까? 컨펌 여부.
 * col (int or String) : 특정 컬럼 기준 저장
 * callback : 저장이후 호출할 function 객체
 * Mode 파라미터 추가_20190118 최경호 추가 : 1=배열형태(paramA=1,paramA=2,paramA=3) 2=스트링형태 ‡기반(paramA=1‡2‡3) 기본 1이고, Mode를 2로주면 스트링형태로 submit을 요청함
 */
function DataSave(param){
    var qstring = "";
    var opt = {}; //default 값
    var mode = (typeof(param.mode) == "undefined" ? 1: param.mode);	// 그리드의 헤더 컬럼의 값을 어떤 형태로 넘길지 결정하는 파라미터(1:배열, 2:‡로연결된 스트링)

    //단일 시트에 대한 저장
    if(typeof(param.sheet)=="string"||param.sheet.length==1){
           var _sheet;

           if(typeof(param.sheet)=="string"){
                   _sheet = param.sheet;
           }else{
                   _sheet = param.sheet[0];
           }

           if(param.sync){
                   opt["Sync"] = param.sync;
           }

           if(param.quest){
                   opt["Quest"] = param.quest;
           }

           //OnSaveEnd 오버라이드
		   var dummySaveEnd=null;
		   if(typeof(window[_sheet+"_OnSaveEnd"])!="undefined"){
			   dummySaveEnd = window[_sheet+"_OnSaveEnd"];
		   }

		   window[_sheet+"_OnSaveEnd"] = function(Code, Msg, StCode, StMsg, Response){
			   var response = null;
			   if(dummySaveEnd){
				   if (Response) {
					   response = JSON.parse(Response);
					   Msg = response.message;
				   }
			   }
			   dummySaveEnd(Code, Msg, StCode, StMsg, response);
		   }

           //해당 폼에서 조회조건 추출
           if(param.subparam){
                   qstring = param.subparam;
           }
           //IBSheet 각 컬럼에 대한 SAVENAME 전달
           qstring +=  "&"+_sheet+"_SAVENAME="+IBS_ConcatSaveName(window[_sheet]);
           //시트 저장
           opt["Param"] = qstring;
           opt["Mode"] = mode;
           opt["Delim"]= "‡";
           opt["Quest"]= "false";

           window[_sheet].DoSave(param.url,opt);

    }else{
           //여러개 시트 동시 저장
           //해당 폼내용 추출
           if(param.subparam){
                   qstring = param.subparam;
           }

           var sheetSaveString = "";
           //각시트의 수정된 내용과 각컬럼의 SaveName을 담는다.
           for(var i=0;i<param.sheet.length;i++){
                   var tempStr = "";

                   tempStr =  window[param.sheet[i]].GetSaveString({"Prefix":param.sheet[i]+"_","Mode":mode,"Delim":"‡"});
                   sheetSaveString += tempStr;
                   //오류 확인
                   if(tempStr==""&&window[param.sheet[i]].IsDataModified()){
                          return;
                   }
                   if(qstring!=""){
                          qstring += "&"+tempStr;
                   }else{
                          qstring = tempStr;
                   }
                   //IBSheet 각 컬럼에 대한 SAVENAME 전달
                   qstring +=  "&"+param.sheet[i]+"_SAVENAME="+IBS_ConcatSaveName(window[param.sheet[i]]);
           }
           qstring += "&MULTISAVE=true";


           //수정한 내용이 없으면 저장을 중단한다.
           if(sheetSaveString==""){
                   infoAlert(window[param.sheet[0]].Lang.Text.SYS_EmptySaveContent);
                   return;
           }


           //마지막 시트 객체
           var _sheet = param.sheet[param.sheet.length-1];
           //OnSaveEnd 오버라이드
           if(typeof(param.callback)=="function"){

				try{
					var dummySaveEnd=null;
					//OnSaveEnd 를 dummySaveEnd 담는다.
					if(typeof(window[_sheet+"_OnSaveEnd"])!="undefined"){

						dummySaveEnd = window[_sheet+"_OnSaveEnd"];

					}
					//OnSaveEnd 오버라이드
					window[_sheet+"_OnSaveEnd"] = function(Code, Msg, StCode, StMsg){
						if(dummySaveEnd){
							dummySaveEnd(Code, Msg, StCode, StMsg); //기존에 정의한 OnSaveEnd구문을 동작시킨다.
						}
						//OnSaveEnd()이벤트 호출 이후 callback에 정의한 내용을 구동시킨다.
						param.callback();
					}
				}catch(e){
					errorAlert("DataSave ERROR:\n"+e.message);
				}
           }

           if(param.quest){
                   confirmAlert( window[param.sheet[0]].Lang.Text.SYS_SaveConfirm, function(){
	                	   //맨 앞에 시트를 통해 ajax 통신
	                	   var jsonString = window[param.sheet[0]].GetSaveData(param.url,qstring);

	                	   //저장 성공/실패 여부를 각 시트에 동일하게 반영한다.
	                	   for(var i=0;i<param.sheet.length;i++){
	                		   window[param.sheet[i]].LoadSaveData( jsonString );
	                	   }
                   		}
                   	);
           }else{
                   //맨 앞에 시트를 통해 ajax 통신
                   var jsonString = window[param.sheet[0]].GetSaveData(param.url,qstring);

                   //저장 성공/실패 여부를 각 시트에 동일하게 반영한다.
                   for(var i=0;i<param.sheet.length;i++){
                          window[param.sheet[i]].LoadSaveData( jsonString );
                   }
           }
    }
}

function DataAllSave(param){
	var qstring = "";
	var opt = {}; //default 값
	var mode = (typeof(param.mode) == "undefined" ? 1: param.mode);	// 그리드의 헤더 컬럼의 값을 어떤 형태로 넘길지 결정하는 파라미터(1:배열, 2:‡로연결된 스트링)

	//단일 시트에 대한 저장
	if(typeof(param.sheet)=="string"||param.sheet.length==1){
		var _sheet;

		if(typeof(param.sheet)=="string"){
			_sheet = param.sheet;
		}else{
			_sheet = param.sheet[0];
		}

		if(param.sync){
			opt["Sync"] = param.sync;
		}

		if(param.quest){
			opt["Quest"] = param.quest;
		}

		//OnSaveEnd 오버라이드
		if(typeof(param.callback)=="function"){

			try{
				var dummySaveEnd=null;
				//OnSaveEnd 를 dummySaveEnd 담는다.
				if(typeof(window[_sheet+"_OnSaveEnd"])!="undefined"){

					dummySaveEnd = window[_sheet+"_OnSaveEnd"];

				}
				//OnSaveEnd 오버라이드
				window[_sheet+"_OnSaveEnd"] = function(Code, Msg, StCode, StMsg){
					if(dummySaveEnd){
						dummySaveEnd(Code, Msg, StCode, StMsg); //기존에 정의한 OnSaveEnd구문을 동작시킨다.
					}
					//OnSaveEnd()이벤트 호출 이후 callback에 정의한 내용을 구동시킨다.
					param.callback();
				}
			}catch(e){
				errorAlert("DataSave ERROR:\n"+e.message);
			}
		}



		//해당 폼에서 조회조건 추출
		if(param.subparam){
			qstring = param.subparam;
		}
		//IBSheet 각 컬럼에 대한 SAVENAME 전달
		qstring +=  "&"+_sheet+"_SAVENAME="+IBS_ConcatSaveName(window[_sheet]);
		//시트 저장
		opt["Param"] = qstring;
		opt["Mode"] = mode;
		opt["Delim"]= "‡";
		opt["Quest"]= "false";

			window[_sheet].DoAllSave(param.url,opt);
	}else{
		//여러개 시트 동시 저장
        //해당 폼내용 추출
        if(param.subparam){
                qstring = param.subparam;
        }

        var sheetSaveString = "";
        //각시트의 수정된 내용과 각컬럼의 SaveName을 담는다.
        for(var i=0;i<param.sheet.length;i++){
                var tempStr = "";

                tempStr =  window[param.sheet[i]].GetSaveString({"Prefix":param.sheet[i]+"_","Mode":mode,"Delim":"‡","AllSave":1});
                sheetSaveString += tempStr;
                //오류 확인
//                if(tempStr==""&&window[param.sheet[i]].IsDataModified()){
//                       return;
//                }
                if(qstring!=""){
                       qstring += "&"+tempStr;
                }else{
                       qstring = tempStr;
                }
                //IBSheet 각 컬럼에 대한 SAVENAME 전달
                qstring +=  "&"+param.sheet[i]+"_SAVENAME="+IBS_ConcatSaveName(window[param.sheet[i]]);
        }
        qstring += "&MULTISAVE=true";

		//마지막 시트 객체
		var _sheet = param.sheet[param.sheet.length-1];
		//OnSaveEnd 오버라이드
		if(typeof(param.callback)=="function"){

			try{
				var dummySaveEnd=null;
				//OnSaveEnd 를 dummySaveEnd 담는다.
				if(typeof(window[_sheet+"_OnSaveEnd"])!="undefined"){

					dummySaveEnd = window[_sheet+"_OnSaveEnd"];

				}
				//OnSaveEnd 오버라이드
				window[_sheet+"_OnSaveEnd"] = function(Code, Msg, StCode, StMsg){
					if(dummySaveEnd){
						dummySaveEnd(Code, Msg, StCode, StMsg); //기존에 정의한 OnSaveEnd구문을 동작시킨다.
					}
					//OnSaveEnd()이벤트 호출 이후 callback에 정의한 내용을 구동시킨다.
					param.callback();
				}
			}catch(e){
				errorAlert("DataSave ERROR:\n"+e.message);
			}
		}


		if(param.quest){
			confirmAlert( window[param.sheet[0]].Lang.Text.SYS_SaveConfirm, function(){
				//맨 앞에 시트를 통해 ajax 통신
				var jsonString = window[param.sheet[0]].GetSaveData(param.url,qstring);

				//저장 성공/실패 여부를 각 시트에 동일하게 반영한다.
				for(var i=0;i<param.sheet.length;i++){
					window[param.sheet[i]].LoadSaveData( jsonString );
				}
			}
			);
		}else{
			//맨 앞에 시트를 통해 ajax 통신
			var jsonString = window[param.sheet[0]].GetSaveData(param.url,qstring);

			//저장 성공/실패 여부를 각 시트에 동일하게 반영한다.
			for(var i=0;i<param.sheet.length;i++){
				window[param.sheet[i]].LoadSaveData( jsonString );
			}
		}
	}
}

/*
 * 아이비시트 초기화 이후에 페이지 인덱스를 만들어 주는 함수
 * ibsheetinfo.js 에 IBS_InitSheet를 호출한 뒤, 페이지 인덱스에 해당하는 부분을 만든다.
 */
function IBS_InitSheet2(sheet,initSheet,divobj){
	IBS_InitSheet(sheet,initSheet);
	var sheetid = sheet.id;

	 //시트 하단에 page index가 보여질 div객체를 생성한다.
	 var idx_div = document.createElement("div");
	 idx_div.id = sheetid+"_index_div";
	 idx_div.style.backGroundColor="#FF0000";
	 idx_div.style.width = "100%";
	 idx_div.style.height = "24px";

	 //현재 페이지를 저장하기 위한 input
	 var input_current = document.createElement("input");
	 input_current.type = "hidden";
	 input_current.id = sheetid+"_current_page";
	 input_current.name = sheetid+"_current_page";
	 input_current.value = "1";
	 idx_div.appendChild(input_current);

	 //전체 건수를 저장하기 위한 input
	 var input_total_row = document.createElement("input");
	 input_total_row.type = "hidden";
	 input_total_row.id = sheetid+"_total_rows";
	 input_total_row.name = sheetid+"_total_rows";
	 idx_div.appendChild(input_total_row);

	//div객체 안에 테이블을 두고 테이블 안에 조회건수표시/페이지인덱스를 둔다.
	var idx_table = document.createElement("table");
	idx_table.style.width = "100%";
	var idx_tbody = document.createElement("tbody");
	idx_tbody.style.width = "100%";
	var idx_tr = document.createElement("tr");
	idx_tr.style.width="100%";


	//왼쪽에 건수정보를 표시?
	var left_td = document.createElement("td");
	left_td.id = sheetid+"_td_SearchCount";
	left_td.style.width = "170px";


	//가운데 pageIndex 표시
	var center_td = document.createElement("td");
	center_td.id = sheetid+"_td_PageIndex";
	center_td.style.width = "auto";
	//오른쪽에 한번에 표시할 record 수 설정
	var right_td = document.createElement("td");
	right_td.id = sheetid+"_td_OnePageCnt";
	right_td.style.width = "170px";
	right_td.style.textAlign  = "right";

	//오른쪽에 들어갈 select
	var onePageCnt = document.createElement("select");
	onePageCnt.id = sheetid+"_OnePageCnt";
	onePageCnt.name = sheetid+"_OnePageCnt";

	var tArr = [10,20,30,50,100];
	for(var i=0;i<tArr.length;i++){
	    var option = document.createElement("option");
	option.text = tArr[i]+"건";
	    option.value = tArr[i];
	    onePageCnt.add(option, i);
	}

	addEventHandler(onePageCnt,"change",function(){
		window[sheetid].RemoveAll();
		document.getElementById(sheetid+"_current_page").value = "1";
		//기존 페이지 인덱스 영역을 clear 하자.
		clearPageIndex(sheetid);
	},"");


	right_td.appendChild(onePageCnt);

	idx_tr.appendChild(left_td);
	idx_tr.appendChild(center_td);
	idx_tr.appendChild(right_td);
	idx_tbody.appendChild(idx_tr);
	idx_table.appendChild(idx_tbody);
	idx_div.appendChild(idx_table);

	divobj.parentNode.appendChild(idx_div);


	window[sheetid].SetCountInfoElement(sheetid+"_td_SearchCount");


	var code = "td#"+sheetid+"_td_PageIndex"+"{height:20px; width:300px; float:left;margin-left:40%;}"
	+"td#"+sheetid+"_td_PageIndex"+" ul{margin:0; padding:0; list-style:none; float:left; }"
	+"td#"+sheetid+"_td_PageIndex"+" li{margin:0 1px; background-color:#e7ebf1; padding: 4px; display: block; float: left; width: auto; height:10px; border-top:1px solid #235d9d; border-left:1px solid #235d9d; border-right:1px solid #235d9d;border-bottom:1px solid #235d9d;border-radius:30px;align:center;textalign:center}"
	+"td#"+sheetid+"_td_PageIndex"+" li:hover {background-color:#5588FF;cursor:pointer}"


	+"td#"+sheetid+"_td_PageIndex"+" a {z-index: 10;	display: block;	float: left; text-decoration: none; white-space: nowrap; width: auto; text-align:center; color:#444444;}"

	+"td#"+sheetid+"_td_PageIndex"+" ul.pageindex li a:hover span {background-color:#e7ebf1; color:#000000;}"
	+"td#"+sheetid+"_td_PageIndex"+" li.current{background-color:#1a4677;}"
	+"td#"+sheetid+"_td_PageIndex"+" li.current a{color:#e7ebf1;}"
	+"#"+sheetid+'_OnePageCnt{border:1px solid #999999;color:#666666;margin:0px;overflow:visible;text-align:start;text-indent:0px;line-height:18px;white-space:pre;font-size:11px;font-family:/*IBFN*/"Dotum", "Helvetica", "AppleGothic", sans-serif;}'
	+"#"+sheetid+'_index_div{font-size:/*IBFS*/11px; font-family:/*IBFN*/"Dotum", "Helvetica", "AppleGothic", sans-serif;}';
	insertCss(code);

	//상태 변화에 따른 배경색 지정
	sheet.SetRowBackColorU("#DDDDFF");//수정시
	sheet.SetRowBackColorI("#EEFFEE");//신규입력
	sheet.SetRowBackColorD("#FFDDDD");//삭제시

}

/* page index */
function makePageIndex(sheetid){
	var current_page = document.getElementById(sheetid+"_current_page").value;
	if(isNaN(current_page)){
		current_page = 0;
	}else{
		current_page = ~~(current_page);
	}

	try{
		//기존 페이지 인덱스 영역을 clear 하자.
		clearPageIndex(sheetid);

		var sr = window[sheetid].GetTotalRows(); //전체 조회 건수
		var page = document.getElementById(sheetid+"_OnePageCnt").value; //페이지당 레코드 수

		var pagecnt = 5; //!!!!!!!!!!!!!중요 한번에 표시할 페이지 개수
		var lastpage = Math.ceil(sr/page); //마지막 페이지 인덱스

		var li = null;
		var tt = null;
		var a = null;
		var ul = document.createElement("ul");

		var k=1;
		if(current_page!= 1){
			  li = document.createElement("li");
			  a = document.createElement("a");
				tt =  document.createTextNode("<<");
				addEventHandler(li,"click",pagemove,{methodName:"GoToFirstPage",page:1,sheet:sheetid});
				a.appendChild(tt);
				li.appendChild(a);
				ul.appendChild(li);
				li = document.createElement("li");
				a = document.createElement("a");
				addEventHandler(li,"click",pagemove,{methodName:"GoToPrevPage",page:current_page-1,sheet:sheetid});
				tt =  document.createTextNode("<");
				a.appendChild(tt);
				li.appendChild(a);
				ul.appendChild(li);
		}


		var sr = (Math.ceil(current_page/pagecnt)*pagecnt)-(pagecnt-1);

		for(var i=sr;i<(sr+pagecnt);i++){
			if(i<=lastpage){
				li = document.createElement("li");
				a = document.createElement("a");
				tt =  document.createTextNode(i);
				addEventHandler(li,"click",pagemove,{methodName:"GoToPageNum",page:i,sheet:sheetid});
				if(i==current_page)		li.className = "current";
				a.appendChild(tt);
				li.appendChild(a);
				ul.appendChild(li);
			}
		}

		if(current_page < lastpage){
				a = document.createElement("a");
			  li = document.createElement("li");
				tt =  document.createTextNode(">");
				addEventHandler(li,"click",pagemove,{methodName:"GoToNextPage",page:current_page+1,sheet:sheetid});
				a.appendChild(tt);
				li.appendChild(a);
				ul.appendChild(li);
				li = document.createElement("li");
				tt =  document.createTextNode(">>");
				a = document.createElement("a");
				addEventHandler(li,"click",pagemove,{methodName:"GoToLastPage",page:lastpage,sheet:sheetid});
				a.appendChild(tt);
				li.appendChild(a);
				ul.appendChild(li);
		}

		ul.className = "pageindex";
		document.getElementById(sheetid+"_td_PageIndex").appendChild(ul);
	}catch(ex){
		errorAlert("error\n:"+ex.message);
	}
}

function clearPageIndex(sheetid){
	if (document.getElementById(sheetid+"_td_PageIndex").firstChild) {
		document.getElementById(sheetid+"_td_PageIndex").removeChild(document.getElementById(sheetid+"_td_PageIndex").firstChild);
	}
}

//페이지 인덱스 클릭시 호출함수.
function pagemove(param){

	var mn = param.methodName;
	var page = eval(param.page);
	var sheet = param.sheet;


	//현재 선택한 페이지 저장
	document.getElementById(sheet+"_current_page").value = page;
	//조회 함수 호출
	eval(sheet+"_search("+page+")");
	//페이지 인덱스 부분 다시 그리기
	makePageIndex(sheet);
}

//이벤트 추가
function addEventHandler (obj,evtName,func,param) {

  if (obj.addEventListener) {   // all browsers except IE before version 9
      obj.addEventListener (evtName, function(){func(param)} , false );
  }
  else {
      if (obj.attachEvent) {    // IE before version 9
          obj.attachEvent ("on"+evtName, function(){func(param)});
      }
  }
}

//css class 추가
function insertCss( code ) {
    var style = document.createElement('style');
    style.type = 'text/css';

    if (style.styleSheet) {
        // IE
        style.styleSheet.cssText = code;
    } else {
        // Other browsers
        style.innerHTML = code;
    }

    document.getElementsByTagName("head")[0].appendChild( style );
}

/*
 * 그리드 search전에 로그인 여부[true/false]를 리턴
 * 세션이 종료되었을 경우 alert 메세지 후, 로그인 페이지로 포워딩 하기 위해서 사용
 *
 * 그리드에서 search 시 사용하는 DataSearch, DataSearchPaging 함수에서
 * 세션이 종료 되어도 로그인 페이지로 가지 않고 리턴이 됨. 따라서 DataSearch, DataSearchPaging 함수 실행 전에 먼저 세션 여부를 체크함
 */
function isLoginCheck(){
	var isLogin = false

	$.ajax({
		type :"post",
		url  : "/noacl/login-check",
		async: false,
		dataType : "json"
	})
	.done(function(data){
		if (data == true) {
			isLogin = true;
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log('loginCheck ERROR');
	});

	return isLogin;
}

//ajax에서 세션종료시 로그인페이지 이동
function SearchEndCallBack(param){
	var count = 0;
	
	//OnSearchEnd 오버라이드
	var sheetid=param.sheet;
		try{
			var dummySearchEnd=null;
			//OnSearchEnd 를 dummySearchEnd 담는다.
			if(typeof(window[sheetid+"_OnSearchEnd"])!="undefined"){

				dummySearchEnd = window[sheetid+"_OnSearchEnd"];

			}
			//SmartResize 오버라이드
			window[sheetid+"_OnSearchEnd"] = function(Code, Msg, StCode, StMsg, Response){
				console.log("Code >> "+ Code);
				console.log("StCode >> "+ StCode);
				if(count <= 0){
					if(StCode == 401){
						count++;
						alert('접근권한이 없습니다.');
						return false;
					}else if(StCode >= 500){
						count++;
						alert('오류가 발생하였습니다.\n시스템관리자에게 문의해주세요');
						return false;
					}
				}
				var response = null;
//				console.log(window[sheetid].Result && window[sheetid].Result.ResponseText);
				if (window[sheetid].Result && window[sheetid].Result.ResponseText) {
					response = JSON.parse(window[sheetid].Result.ResponseText);
					Msg = response.message;
				}
				
				if(dummySearchEnd){
					dummySearchEnd(Code, Msg, StCode, StMsg, response); //기존에 정의한 OnSearchEnd구문을 동작시킨다.
				}
				param.callback();
				
			};
		}catch(e){
			console.log("SearchEndCallBack ERROR: "+e.message);
		}
}
