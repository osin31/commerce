<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sample form</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>폼 전송 <br>

	싱글 업로드 : <br>
	<form id="a" action="/sample/file/upload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="file" name="upload_file" />
	</form><br>
	
	멀티 업로드 파라메터명이 같을 경우: <br>
	<form id="b" action="/sample/file/multiUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="file" name="upload_file" />
		<input type="file" name="upload_file" />
	</form>
	
	
	멀티 업로드 파라메터명이 다를 경우: <br>
	<form id="c" action="/sample/file/otherParamMultiUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="file" name="upload_file1" />
		<input type="file" name="upload_file2" />
	</form>
	
	
	
	제네릭 타입 싱글 업로드 - VO 에  file 파라메터 명이 있을 경우: <br>
	<form id="d" action="/sample/file/genericUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="file" name="upload_file" />
	</form><br>
	
	제네릭 타입 멀티 업로드 - VO 에  file 파라메터 명이 있을 경우 : <br>
	<form id="e" action="/sample/file/genericMultiUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="hidden" name="prdNo" value="223121111"/>
		<input type="hidden" name="prdNm" value="아디다스 신발 "/>
		<input type="hidden" name="item" value="B10001"/>
		<input type="file" name="upload_file" />
		<input type="file" name="upload_file" />
	</form>
	
	Parameter.create() 타입 싱글 업로드 - VO 에  file 파라메터 명이 있을 경우: <br>
	<form id="f" action="/sample/file/createUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="file" name="upload_file" />
	</form><br>
	
	Parameter.createArray() - VO 에  file 파라메터 명이 있을 경우 : <br>
	<form id="g" action="/sample/file/createMultiUpload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="hidden" name="prdNo" value="223121111"/>
		<input type="hidden" name="prdNm" value="아디다스 신발 "/>
		<input type="hidden" name="item" value="B10001"/>
		<input type="file" name="upload_file" />
		<input type="file" name="upload_file" />
	</form><br>
	
	
	파일 업로드 확장자 및 mimeType 검증 : <br>
	<form id="h" action="/sample/file/validUploadExtMimeType" method="post" enctype="multipart/form-data">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="hidden" name="prdNo" value="223121111"/>
		<input type="hidden" name="prdNm" value="아디다스 신발 "/>
		<input type="hidden" name="item" value="B10001"/>
		<input type="file" name="upload_file" />
		<input type="file" name="upload_file" />
	</form>
	
	
	<br><br><br>
	
	<button id="singleUpload"> 싱글 업로드 </button>
	<button id="multiUpload">멀티 업로드 파라메터명이 같을 경우</button>
	<button id="otherParammultiUpload">멀티 업로드 파라메터명이 다를 경우</button>
	<button id="singleUploadNoParam">제네릭 타입 싱글 업로드 - VO 에  file 파라메터 명이 있을 경우</button>
	<button id="multiUploadNoParam">제네릭 타입 멀티 업로드 - VO 에  file 파라메터 명이 있을 경우 </button>
	<button id="createUpload">Parameter.create() 타입 싱글 업로드 - VO 에  file 파라메터 명이 있을 경우</button>
	<button id="createArrayUpload">Parameter.createArray() 타입 멀티 업로드 - VO 에  file 파라메터 명이 있을 경우 </button>
	<button id="validUploadExtMimeType">파일 업로드 확장자 및 mimeType 검증</button>
<script>

$(document).ready(function(){
	$("#singleUpload").click(function(){
		$("#a").submit();
	});
	
	$("#multiUpload").click(function(){
		$("#b").submit();
	});
	
	$("#otherParammultiUpload").click(function(){
		$("#c").submit();
	});
	
	$("#singleUploadNoParam").click(function(){
		$("#d").submit();
	});
	
	$("#multiUploadNoParam").click(function(){
		$("#e").submit();
	});
	
	$("#createUpload").click(function(){
		$("#f").submit();
	});
	
	$("#createArrayUpload").click(function(){
		$("#g").submit();
	});
	
	$("#validUploadExtMimeType").click(function(){
		$("#h").submit();
	});
});
</script>
</body>
</html>