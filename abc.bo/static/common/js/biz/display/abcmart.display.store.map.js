(function() {

	var _map = abc.object.createNestedObject(window,"abc.biz.display.map");
	
	/**
	 * 초기화
	 */
	_map.init = function() {
		
		_map.event();
	}
	
	/**
	 * 이벤트
	 */
	_map.event = function(){
		
		var xp = $('#xPoint').val() != '' ? $('#xPoint').val() : '37.56125235718595';
		var yp = $('#yPoint').val() != '' ? $('#yPoint').val() : '126.98378665863116';
		
		//지도를 삽입할 HTML 요소 또는 HTML 요소의 id를 지정합니다.
		var mapDiv = document.getElementById('map'); // 'map'으로 선언해도 동일
		var point = new naver.maps.LatLng(xp, yp);

		var option = {
		        center: point, //지도의 초기 중심 좌표
		        zoom: 12, //지도의 초기 줌 레벨
		        minZoom: 1, //지도의 최소 줌 레벨
		        zoomControl: true, //줌 컨트롤의 표시 여부
		        zoomControlOptions: { //줌 컨트롤의 옵션
		            position: naver.maps.Position.TOP_RIGHT
		        }
		}
		
		map = new naver.maps.Map(mapDiv, option);
		
		// marker
		var markerOptions = {
		    position: point.destinationPoint(90, 15),
		    map: map
		};

		marker = new naver.maps.Marker(markerOptions);
		
		naver.maps.Event.addListener(marker, 'click', function(e) {
			$('#xPoint').val(e.coord.y);
			$('#yPoint').val(e.coord.x);							
		});
		
		// 검색
		$('#searchBtn').on('click', function() {
			
			var url = "/display/store/map/info";
			var form = $.form(document.forms.searchForm);
			var storeSearchWord = $(document.searchForm.searchStoreName).val();
			
			x = '';
			y = '';
			
			// 네이버 api
			naver.maps.Service.geocode({query:storeSearchWord}, function(status, response) {
				
				if (response.v2.addresses.length > 0) { // 검색 결과값 있음
					x = response.v2.addresses[0].x;
					y = response.v2.addresses[0].y;
					
					_map.setMapArea(x, y);
				} else { // 검색 결과값 없음
					form.submit({
						url : url,
						method : "POST",
						valid	: function($f){
							return true;
						},
						success : function(data) {
							
							if (data.places != null && data.places.length > 0) {
								var place = data.places[0];
								
								x = place.x;
								y = place.y;
								
								_map.setMapArea(x, y);
							} else {
								alert('검색어가 포함된 매장이 없습니다.');
							}
						},
						error : function(e) {
							alert(e.message);
					    	console.log(e);
						}
					});
				}
			});			
		});
		
		$(document.searchForm).on('submit', function(){
			
//			$('#searchBtn').trigger('click');			
			return false;
		})
		
		$('#saveBtn').on('click', function() {
			
			var xPoint = $('#xPoint').val();
			var yPoint = $('#yPoint').val();
			
			if(!xPoint){
				alert("X Point를 입력해주세요");
				$('#xPoint').focus();
				return;
			}
			if(!yPoint){
				alert("Y Point를 입력해주세요");
				$('#yPoint').focus();
				return;
			}
			
			var data = {
				'xPoint' : xPoint,
				'yPoint' : yPoint
			};
			
			console.log(data);
			
			var cb = 'abc.biz.display.store.pointCallback';
			var opFunc = new Function('return opener.'+cb)();
			opFunc(data);
			window.close();
			
		});
		
	}
	
	/**
	 * 맵 설정
	 */
	_map.setMapArea = function(x, y){
		var point = new naver.maps.LatLng(+y, +x);
		map.setCenter(point);
		map.setZoom(12);
		marker.setPosition(point);
		
		$('#xPoint').val(point.y);
		$('#yPoint').val(point.x);		
		
		naver.maps.Event.addListener(marker, 'click', function(e) {
			$('#xPoint').val(+y);
			$('#yPoint').val(+x);
		});
	}
	
	$(function() {
		_map.init();
	});
	
})();