<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		
		abc.biz.member.member.point.memberNo = "${member.memberNo}";
		$("#etcSavedCode").attr("disabled", true);
		
		$("#validateCode").change(function(){
			var checkedVal = $(this).val();
			if(checkedVal == "0"){
				$("#etcSavedCode").attr("disabled", true);
			}else{
				$("#etcSavedCode").attr("disabled", false);
			}
		});
		
		<%-- 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.member.member.point.savePoint();
		});
		
		<%-- 적립/사용 내용 체크 --%>
		$("#saveUseContText").keyup(function(e){
			var content = $(this).val();
			abc.biz.member.member.point.comemtLengthCheck(content);
		});
		
		<%-- 관리자 코멘트 체크 --%>
		$("#adminMemoText").keyup(function(e){
			var content = $(this).val();
			abc.biz.member.member.point.adminMemoLengthCheck(content);
		});
	});
	
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>포인트 관리자 적립/차감</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">포인트 관리자 적립/차감</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="savePointForm" name="savePointForm" method="post" onsubmit="return false;">
			<table class="tbl-row">
				<caption>포인트 관리자 적립/차감</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">포인트 종류</th>
						<td class="input">
							<select class="ui-sel" id="validateCode" name="validateCode" title="포인트 종류 선택">
								<option value="0">고객응대</option>
								<option value="1">기타</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">적립/차감 내용</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="saveType" id="radioUse01" value="S">
										<label for="radioUse01">적립</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="saveType" id="radioUse02" value="U">
										<label for="radioUse02">차감</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->

							<!-- S : ip-text-box -->
							<span class="ip-text-box full-size">
								<input type="text" class="ui-input num-unit100000000" id="point" name="point" title="point 입력">
								<span class="text">Point</span>
								<input type="text" class="ui-input text-unit20" id="saveUseContText" name="saveUseContText" title="내용 입력" placeholder="고객님이 확인하실 내용 입력">
								<span class="text">(<span id="comentCounter">0</span>/20자)</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">통합멤버십 코드</th>
						<td class="input">
							<input type="text" id="etcSavedCode" name="etcSavedCode" class="ui-input" title="통합멤버십 코드 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">관리자 코멘트</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input text-unit30" id="adminMemoText" name="adminMemoText" title="관리자 코멘트 입력">
								<span class="text">(<span id="adminMemoCounter">0</span>/30자)</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="saveBtn" class="btn-normal btn-link">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/member/abcmart.member.member.point.js<%=_DP_REV%>">
</script>