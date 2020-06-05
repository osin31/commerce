(function() {

	var _cornerPop = abc.object.createNestedObject(window,"abc.biz.display.template.corner.pop");
	
	/**
	 * 초기화
	 */
	
	_cornerPop.data = {
			checkIdx : 1
	};
	
	_cornerPop.init = function(){
		_cornerPop.event();
		_cornerPop.setCornerDetailData();
		
		abc.biz.display.common.checkBoxAll();
	}
	
	/**
	 * 이벤트
	 */
	_cornerPop.event = function(){
	
		//저장
		$('#save-corner').on('click', function(e){

			if(_cornerPop.valid()){
				window.opener.abc.biz.display.template.detail.cornerCallback(_cornerPop.makeFormArrayToJson(), abc.param.getParams().selectRow);
				window.close();
			}
		});
		
		$('#add-corner-set').on('click', function(e){
			_cornerPop.addCornerSet();
		});
		
		$('#del-corner-set').on('click', function(e){
			_cornerPop.delCornerSet();
		});
		
        $('body').on('keyup', '.set-name-count', function(e){
        	
        	var val = Number(this.value);
        	
        	if(val > 1 || val < 0){
        		this.value = 0;
        	}
        });
	}

	_cornerPop.valid = function(){
	
		var frm = document.frm;
	    
	    //코너명 입력 여부
	    if(frm.cornerName.value.trim() == ''){
	        alert('코너명은 필수 입니다.');
	        return false;
	    }

	    //코너설명 입력 여부	
	    if(frm.noteText.value.trim() == ''){
	        alert('코너설명은 필수 입니다.');
	        return false;
	    }
	    

	    if($('#append-corner-set').children().length == 0){
	        alert('세트는 필수 입니다.');
	        return false;
	    }    
	    
	    var sortValid = true;
	    var sortArr = [];
	    //노출순서
	    $.each($(document.frm.sortSeq), function(i,v){
	        if(v.value == ''){
	            alert('노출순서는 필수 입니다.');
	            sortValid = false;
	            return false;
	        }
	        
	        if(v.value == 0){
	        	alert('노출순서는 1부터 가능합니다.');
	        	sortValid = false;
	        	return false;
	        }
	        
	        if($.inArray(v.value, sortArr ) == -1){
	             sortArr.push(v.value);
	        }else{
	            alert('노출순서는 중복이 불가합니다.');
	            sortValid = false;
	            return false;
	        }
	    });
	    
	    if(!sortValid) return false;
	    
	    //세트 수
	    var setSize = $('td.only-chk').length;
	    //항목 수
	    var totalSize = document.frm.dispContCount.length;
	    //세트별 항목 수
	    var typeCount = totalSize/setSize;
	    //세트별 전체 노출 콘텐츠 수
	    var contTotalCount = 0;
	    
	    $.each($(document.frm.dispContCount), function(i,v){
	        if(v.value == ''){
	            alert('노출 콘텐츠 수는 필수 입니다.');
	            sortValid = false;
	            return false;
	        }else{
        		//새로운 세트 시작
	        	contTotalCount += v.value;//191213 세트의 마지막 컬럼 콘텐츠수 가져 오지 못하여 위로 올림
	        	if(i > 0 && (i+1)%(typeCount) == 0){
	        		if(contTotalCount == 0){
//	        			if(contTotalCount == (setSize > 1 ? 1 : 0)){
	        			alert('노출 콘텐츠 수를 입력해 주세요.');
	        			sortValid = false;
	        			return false;
	        		}
	        		contTotalCount = 0;
	        	}
	        }
	    });

	    if(!sortValid) return false;
	    	
	    return true;
	}
	
	/**
	 * 세트 추가 
	 */
	_cornerPop.addCornerSet = function(){
		
		var tmpl = document.templateSeveralRoot('#set-corner-tmpl');

		_cornerPop.data.checkIdx++;
		
		var id = 'chk'+_cornerPop.data.checkIdx;
		
		tmpl.find('#chkConfig').attr('id', id);
		tmpl.find('#'+id).siblings().attr('for', id);
		tmpl.children().addClass(id);
		
		if($('#append-corner-set').children().length > 0){
			$('#append-corner-set').children().eq(0).find('[name=dispContCount]').val(0);
			tmpl.children().eq(0).find('[name=dispContCount]').val(0);
		}
		
		$('#append-corner-set').append(tmpl);
		
	}
	
	/**
	 * 세트 삭제
	 */
	_cornerPop.delCornerSet = function(){
		
		if($('#check-all-item').is(':checked')){
			$('#append-corner-set').find('tr').remove();
		}else{
			
			var rows = $('.check-item:checked').parents('tr');
			if(rows.length == 0) {
				alert('삭제 할 세트를 선택해 주세요.');
				return false;
			}
			
			 //세트 수
		    var setSize = $('td.only-chk').length;
		    //항목 수
		    var totalSize = document.frm.dispContCount.length;
		    //세트별 항목 수
		    var typeCount = totalSize/setSize;
		    
			$.each(rows, function(i,v){
				
				for(var j = 1; j < typeCount ; j++){
					$(this).next().remove();
				}
				
				$(this).remove();
				
				if($('.check-item').length == 1){
					$('#append-corner-set').children().eq(0).find('[name=dispContCount]').val(0);
				}
				
			});
			
			_cornerPop.data.checkIdx++;
		}
	}
	
	_cornerPop.setCornerDetailData = function(){
		
		var frm = document.frm;
		
		if(frm.dispTmplCornerSeq.value == '' && abc.param.getParams().selectRow != null){
			var rowData = opener.abc.biz.display.template.detail.getCornerDetailData(abc.param.getParams().selectRow);
			var setArr = rowData.setArr != '' ? $.parseJSON(rowData.setArr) : '';
			
			//코너 정보
			$.each(rowData, function(i,v){
				if(frm[i]) frm[i].value = v;
			});
			
			//세트 정보
			console.log(setArr);
			var setSize = 0;
			var sortSeq = 0;
			var tmpl;
			
			$.each(setArr, function(i,v){
				
				//set size 계산
				if(i == 0 || sortSeq == 0 || sortSeq != v.sortSeq) {
					
					if(i > 0){
						$('#append-corner-set').append(tmpl);
					}
					
					tmpl = null;
					tmpl = document.templateSeveralRoot('#set-corner-tmpl');
					
					_cornerPop.data.checkIdx++;
					
					var id = 'chk'+_cornerPop.data.checkIdx;
					
					tmpl.find('#chkConfig').attr('id', id);
					tmpl.find('#'+id).siblings().attr('for', id);
					tmpl.children().addClass(id);
					
					setSize++;
					sortSeq = v.sortSeq;
					
				}
				
				tmpl.find('[name=sortSeq]').val(v.sortSeq);
				
				var tr = tmpl.find('[value='+v.contTypeCode+']').parents('tr');
				tr.find('[name=contTypeCodeName]').val(v.contTypeCodeName);
				tr.find('[name=dispContCount]').val(v.dispContCount);
				
				//마지막
				if(i == setArr.length-1){
					$('#append-corner-set').append(tmpl);
				}
				
			});
		}
	}
	
	_cornerPop.makeFormArrayToJson = function(){
		
		var jsonData = {};
		var arr = $(document.frm).serializeArray();
		var set = [];
		var setObj = {};
		var sortSeq = null;
		var newRowCheck = null;

		$.each(arr, function(i,v){
		    
		    //corner data
		    if(i < 6){
		        jsonData[v.name] = v.value;    
		    }
		    //corner set data
		    else{
		        //노출 순서를 기준으로 세트 구성
		        if(v.name == 'sortSeq'){
		        	sortSeq = v.value;
		        }
		        
		        //콘텐츠 유형이 변경 시 마다 arr push
		        if(v.name == 'contTypeCode'){

		            if(newRowCheck != null && newRowCheck != v.value){
		                set.push(setObj);
		                setObj = {};
		            }

		            newRowCheck = v.value;
		            setObj.sortSeq = sortSeq;
		        }
		        //노출 순서 제외
		        if(v.name != 'sortSeq') setObj[v.name] = v.value;        
		    }

		    if(i == arr.length-1){
		        set.push(setObj);
		        jsonData.set = set;
		    }
		});
		
		jsonData.setCount = $('[name=sortSeq]').length;
		jsonData.selectRow = abc.param.getParams().selectRow;
		
		return jsonData;
	}
	
	$(function() {
		_cornerPop.init();
	});
	
})();