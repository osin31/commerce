/***
 * 금칙어.
 * 
 */
(function() {

	var _forbiddenword = abc.object.createNestedObject(window,"abc.biz.cmm.forbiddenword");
	
	/***
	 * 금칙어 저장
	 */
	_forbiddenword.save = function() {
		var $form = $("#forbiddenWordForm");
		var $text = $form.find("textarea");
		
		//중복 금칙어 필터.
		var arr = $text.val().split(",").map(function(item) {
			return item.trim();
		});
		
		var uniqueWords = arr.filter(function(items,i,a){
		    return i==a.indexOf(items);
		}).join(', ');
		$text.val(uniqueWords);
		
		$.ajax({
			type:"post",
			url: "/cmm/forbidden-word/update",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			alert("저장되었습니다.");
			location.reload();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
})();