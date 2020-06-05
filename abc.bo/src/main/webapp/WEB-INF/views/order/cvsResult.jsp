<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ui:decorator name="none"/>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
// 편의점 결과 정보
var cvsResult = {"store_code"     : "${store_code}",
                 "ho_code"        : "${ho_code}" ,
                 "store_address1" : "${store_address1}",
                 "store_address2" : "${store_address2}",
                 "post_no"        : "${post_no}",
                 "code_f"         : "${code_f}",
                 "dd_zone"        : "${dd_zone}",
                 "dd_bag"         : "${dd_bag}",
                 "codePany"       : "${codePany}",
                 "codeName"       : "${codeName}",
                 "codeTel"        : "${codeTel}",
                 "eupmyeon"       : "${eupmyeon}"
                 };
opener.opener.setCVSResult(cvsResult);  // 주문서작성페이지의 값 return
window.close();        //현재화면(팝업2) 닫음 setCVSResult
opener.window.close(); //팝업1 닫음 
</script>
</head>
<body>
</body>
</html>